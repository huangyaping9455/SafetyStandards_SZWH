package org.springblade.common.tool;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author hyp
 * @create 2023-02-18 12:13
 */
public class xioo {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception
	{

		String url  = "http://api.qirui.com:7891/mt";

		//apiKey和APISecret
		String apiKey    = "2332120010";
		String apiSecret = "c8c3a176c79227798b92";

		//接受手机号
		String mobile    = "13224068005";
		//短信内容(【签名】+短信内容)，系统提供的测试签名和内容，如需要发送自己的短信内容请在启瑞云平台申请短信签名
		String message   = "【短信提醒】您的验证码是:5277";

		StringBuilder sb = new StringBuilder(2000);
		sb.append(url);
		sb.append("?dc=15");
		sb.append("&sm=").append(URLEncoder.encode(message, "utf8"));
		sb.append("&da=").append(mobile);
		sb.append("&un=").append(apiKey);
		sb.append("&pw=").append(apiSecret);
		sb.append("&tf=3&rd=1&rf=2");   //短信内容编码为 urlencode+utf8

		String request = sb.toString();
		//System.out.println(request);


		CloseableHttpClient client = HttpClients.createDefault();

		HttpGet httpGet = new HttpGet(request);

		CloseableHttpResponse response = client.execute(httpGet);

		String respStr = null;

		HttpEntity entity = response.getEntity();
		if(entity != null) {
			respStr = EntityUtils.toString(entity, "UTF-8");
		}
		System.out.println(respStr);

	}
}
