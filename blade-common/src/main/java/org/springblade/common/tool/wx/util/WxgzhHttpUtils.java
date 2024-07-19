package org.springblade.common.tool.wx.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springblade.common.configurationBean.WechatServer;
import org.springblade.common.tool.HttpUtils;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: https 调用工具
 * @author Administrator
 * @create 2023-10-07 10:54
 */
@Slf4j
@Component
@AllArgsConstructor
public class WxgzhHttpUtils {

	/** 获取access_token的接口地址（GET） 限2000（次/天） */
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	private WechatServer wechatServer;


	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		log.debug("[发起https请求]requestUrl=" + requestUrl);
		log.debug("[发起https请求]requestMethod=" + requestMethod);
		log.debug("[发起https请求]outputStr=" + outputStr);
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			}};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
				.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			//if ("GET".equalsIgnoreCase(requestMethod))
			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码

				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();

			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * 获取token
	 * @return
	 * @throws Exception
	 */
	public static String getAccessToken(String appid, String secret) throws Exception{
//		String appid = wechatServer.getWxgzhAppId();
//		String secret = wechatServer.getWxgzhSecret();
		WxgzhAccessToken accessToken = null;
		String requestUrl = ACCESS_TOKEN_URL.replace("{0}",appid).replace("{1}",secret);
		JSONObject jsonObject=null;
		//access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
		//获取access_token 每日限额 2000次,注：如各个业务逻辑点各自去刷新access_token，那么就可能会产生冲突，导致服务不稳定
		jsonObject = httpsRequest(requestUrl, "GET", null);
		log.info("[(刷新)获取access_token]jsonObject="+jsonObject);
		//CacheUtil.put(appid, jsonObject, jsonObject.getInt("expires_in")-7000<200?jsonObject.getInt("expires_in"):200 );//简易定时器 ,200秒内值缓存
		if (null != jsonObject) {
			try {
				accessToken = new WxgzhAccessToken(jsonObject.getString("access_token"), jsonObject.getInteger("expires_in"));
			} catch (JSONException e) {
				log.error("获取token失败 errcode:{} errmsg:{}"+jsonObject.getInteger("errcode")+"errmsg:"+jsonObject.getString("errmsg"),e);
			}
		}
		log.info("[获取access_token]jsonObject="+jsonObject);
		log.info("[获取access_token]accessToken="+accessToken.getToken());
		return jsonObject.getString("access_token");
	}

	public static String getOpenID(String code, String appid, String secret) throws Exception {
		String accessToken = String.valueOf(getAccessToken(appid,secret));
		if (accessToken != null) {
			log.info("==> 先获取code,再获取openid 。code={}", code);
			Map params = new HashMap();
			params.put("appid", appid);
			params.put("secret", secret);
			params.put("grant_type", "authorization_code");
			params.put("code", code);

			String result = HttpGetUtil.httpRequestToString(
				"https://api.weixin.qq.com/sns/oauth2/access_token", params);
			if (result != null) {
				JSONObject jsonObject = JSONObject.parseObject(result);
				String openid = jsonObject.get("openid").toString();
				log.info("==> 获取的 openid={}", openid);
				return openid;
			}
		}
		return null;
	}

//	public static void main(String[] args) throws Exception {
//		getOpenID("061XuCll2MKWBd4JzLnl2SucUW2XuCl5","wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
//	}


}
