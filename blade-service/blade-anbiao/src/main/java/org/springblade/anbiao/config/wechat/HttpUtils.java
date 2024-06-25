package org.springblade.anbiao.config.wechat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: https 调用工具
 * @author Administrator
 * @create 2023-10-07 10:54
 */
@Slf4j
@Component
@AllArgsConstructor
public class HttpUtils {

	private final RedisCache redisCache;

	private final WechatProperties wechatProperties;

	private final Weather weather;

	/** 获取access_token的接口地址（GET） 限2000（次/天） */
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	/** 获取关注用户id */
	public final static String USER_GET_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token={0}";
	/** 获取天气情况 */
	public final static String WEATHER = "https://api.map.baidu.com/weather/v1/?data_type={0}&ak={1}&district_id={2}";

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
	public AccessToken getAccessToken(String appid,String secret) throws Exception{
//		String appid = wechatProperties.getMappid();
//		String secret = wechatProperties.getMsecret();
		AccessToken accessToken = null;
		String requestUrl = ACCESS_TOKEN_URL.replace("{0}",appid).replace("{1}",secret);
		JSONObject jsonObject=null;
		if(null== redisCache.get(appid)){
			//access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
			//获取access_token 每日限额 2000次,注：如各个业务逻辑点各自去刷新access_token，那么就可能会产生冲突，导致服务不稳定
			jsonObject = httpsRequest(requestUrl, "GET", null);
			log.info("[(刷新)获取access_token]jsonObject="+jsonObject);
			//CacheUtil.put(appid, jsonObject, jsonObject.getInt("expires_in")-7000<200?jsonObject.getInt("expires_in"):200 );//简易定时器 ,200秒内值缓存
			redisCache.set(appid, jsonObject.toString(), jsonObject.getInteger("expires_in")-7000<200?jsonObject.getInteger("expires_in"):200);
		}
		jsonObject = JSONObject.parseObject((String) redisCache.get(appid));

		if (null != jsonObject) {
			try {
				accessToken = new AccessToken(jsonObject.getString("access_token"), jsonObject.getInteger("expires_in"));
			} catch (JSONException e) {
				log.error("获取token失败 errcode:{} errmsg:{}"+jsonObject.getInteger("errcode")+"errmsg:"+jsonObject.getString("errmsg"),e);
			}
		}
		log.info("[获取access_token]jsonObject="+jsonObject);
		log.info("[获取access_token]accessToken="+accessToken.getToken());
		return accessToken;
	}

	/**
	 * 获取关注用户
	 * @return
	 */
	public List<String> getUsers(String appid,String secret) {
		try {
			log.info("------------ [获取用户信息] ---------------");
			String token = null;
			if(null== redisCache.get(appid)){
				AccessToken accessToken = this.getAccessToken(appid,secret);
				if (accessToken != null) {
					token = accessToken.getToken();
				}
			} else {
				JSONObject jsonObject = JSONObject.parseObject((String) redisCache.get(appid));
				token = jsonObject.getString("access_token");
			}
			if (token != null) {
				String requestUrl = USER_GET_URL.replace("{0}",token);
				JSONObject jsonObject = HttpUtils.httpsRequest(requestUrl, "GET", null);
				String dataJson = jsonObject.getString("data");
				JSONObject data = JSONObject.parseObject(dataJson);
				System.out.println(jsonObject.getString("errcode"));
				if(data == null && "42001".equals(jsonObject.getString("errcode"))){
					redisCache.del(appid);
				}
				JSONArray openid = data.getJSONArray("openid");
				List<String> list = new ArrayList<>();
				if (openid != null && openid.size() > 0) {
					for (int i = 0; i < openid.size(); i++) {
						Object obj = openid.get(i);
						if (obj != null) {
							list.add(obj.toString());
						}
					}
				}
				return list;
			}
		} catch (Exception e) {
			log.error("[获取关注用户信息]数据异常：",e);
		}
		return null;
	}

	/**
	 * 获取天气
	 * @return
	 */
	public WeatherModel getWeather(){
		try{
			log.info("------------ [获取天气] ------------");
			String requestUrl = WEATHER.replace("{0}", weather.getDataType()).replace("{1}", weather.getAk())
				.replace("{2}", weather.getDistrictId());
			JSONObject jsonObject = HttpUtils.httpsRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				WeatherModel weather = new WeatherModel();
				JSONObject jsonObj = jsonObject.getJSONObject("result");
				JSONObject object = jsonObj.getJSONObject("location");
				if (object != null) {
					weather.setProvince(object.getString("province"));
					weather.setCity(object.getString("city"));
					weather.setName(object.getString("name"));
				}
				object = jsonObj.getJSONObject("now");
				if (object != null) {
					weather.setText(object.getString("text"));
					weather.setWind_class(object.getString("wind_class"));
					weather.setWind_dir(object.getString("wind_dir"));
					weather.setTemp(object.getString("temp"));
				}
				JSONArray forecasts = jsonObj.getJSONArray("forecasts");
				if (!CollectionUtils.isEmpty(forecasts)) {
					Map<String, Object> obj = (Map<String,Object>)forecasts.get(0);
					if (obj != null) {
						weather.setTextNight(obj.get("text_night").toString());
						weather.setHigh(obj.get("high") != null ? obj.get("high").toString():null);
						weather.setLow(obj.get("low") != null ? obj.get("low").toString():null);
						weather.setDate(obj.get("date").toString());
						weather.setWeek(obj.get("week").toString());
					}
				}
				return weather;
			}
		} catch (Exception e) {
			log.error("[获取天气信息]数据异常：",e);
		}
		return null;
	}

}
