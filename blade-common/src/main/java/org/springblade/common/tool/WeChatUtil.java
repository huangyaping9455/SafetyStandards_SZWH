package org.springblade.common.tool;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.configurationBean.WechatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 微信小程序工具类
 * @author hyp
 * @create 2022-10-24 16:10
 */
@Slf4j
public class WeChatUtil {

	@Autowired
	private WechatServer wechatServer;




	public static String httpRequest(String requestUrl, String requestMethod, String output) {
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(requestMethod);
			if (null != output) {
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(output.getBytes(StandardCharsets.UTF_8));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str;
			StringBuilder buffer = new StringBuilder();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			connection.disconnect();
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url  发送请求的 URL
	 * @param json 请求参数，请求参数应该是 json 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String httpPost(String url, JSONObject json) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数

			out.print(json);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result=result.concat(line);
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @description: 微信登陆工具类
	 * @author jianfei.ma
	 * @ClassName:GetOpenIdUtil
	 * @date:2020/2/18 001816:43
	 */
	public String getopenid(String appid,String code,String secret) {
		BufferedReader in = null;
		//appid和secret是开发者分别是小程序ID和小程序密钥，开发者通过微信公众平台-》设置-》开发设置就可以直接获取，
		String url="https://api.weixin.qq.com/sns/jscode2session?appid="
			+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
		try{
			URL weChatUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = weChatUrl.openConnection();
			// 设置通用的请求属性
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * @Description 用 code 请求 OpenId
	 * @param  code
	 * @return JsonNode { "session_key": ,"openid": }
	 */
	public JsonNode requestOpenId(String wechatUrl,String appId,String secret,String code) {
		RestTemplate restTemplate = new RestTemplate();
		String url = String.format(wechatUrl, appId,secret, code);
		String response = restTemplate.getForObject(url, String.class);
		return JSONUtils.string2JsonNode(response);
	}

	public static String decrypt(String sessionKey, String iv, String encryptedData) {
		try {
			byte[] aesKey = Base64.getDecoder().decode(sessionKey);
			SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] ivBytes = Base64.getDecoder().decode(iv);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] encryptedDataByes = Base64.getDecoder().decode(encryptedData);
			byte[] original = cipher.doFinal(encryptedDataByes);
			return new String(original);
		} catch (Exception e) {
			log.error("error on decrypt,encryptedData:{},\r\ncause:{}",encryptedData,e);
			return null;
		}
	}

}
