/**
 * Copyright (C), 2015-2021
 * FileName: PostUtil
 * Author:   呵呵哒
 * Date:     2021/12/18 11:32
 * Description:
 */
package org.springblade.common.tool;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @创建人 hyp
 * @创建时间 2021/12/18
 * @描述
 */
@Slf4j
public class PostUtil {

    /**
     * 以post方式调用第三方接口,以form-data 形式  发送数据
     * @param url  post请求url
     * @param paramMap 表单里其他参数
     * @return
     */
    public static String doPost(String url, Map<String, String> paramMap) {
        // 创建Http实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建HttpPost实例
        HttpPost httpPost = new HttpPost(url);
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            //表单中参数
            for(Map.Entry<String, String> entry: paramMap.entrySet()) {
                builder.addPart(entry.getKey(),new StringBody(entry.getValue(), ContentType.create("text/plain", Consts.UTF_8)));
                if(entry.getKey().equals("Authorization")){
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            // 执行提交
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 返回
                String res = EntityUtils.toString(response.getEntity(), java.nio.charset.Charset.forName("UTF-8"));
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调用HttpPost失败！" + e.toString());
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("关闭HttpPost连接失败！");
                }
            }
        }
        return null;
    }

    /**
     * 以post方式调用第三方接口,以Body 形式  发送数据
     * @param postUrl  post请求url
     * @param params 表单里其他参数
     * @return
     */
    private static String postMsg(String postUrl,String params) {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        String responseMsg = "";
        try {
            URL realUrl = new URL(postUrl);
            connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(3 * 1000);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/x-www-form-urlencoded;charset=UTF-8");
            // 是否输入参数
            connection.setDoOutput(true);
            connection.connect();
            if(!"".equals(params)){
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(params);
                writer.close();
            }
            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                throw new RuntimeException("Http Request StatusCode(" + statusCode + ") Invalid.");
            }
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            responseMsg = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return responseMsg;
    }

	public static String postNewMsg(String postUrl,String params) throws IOException {
		//请求url
//		String serverURL = "http://r.bn.cloudjoytech.com.cn/sec/getLearnRecord";
		String serverURL = postUrl;
		URL url = new URL(serverURL);

		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		//header参数connection.setRequestProperty("键","值");
		connection.setRequestProperty("Content-Type", "application/json");

		connection.connect();
		OutputStreamWriter writer = new
			OutputStreamWriter(connection.getOutputStream(),"UTF-8");
//        //获取当前时间戳
//        JSONArray jsonArray = new JSONArray();
//        JSONObject parm1 = new JSONObject();
//        parm1.put("company","");
//        parm1.put("luser","");
//        parm1.put("lmonth","10");
//        parm1.put("lyear","2021");
//        //请求包含数组时时，先将数组参数放入JSONArray
//        jsonArray.put(parm1);

		System.out.println(params);
		//body参数
//		JSONObject parm2 = new JSONObject();
//		parm2.put("company",params);
//		parm2.put("luser","");
//		parm2.put("lmonth","10");
//		parm2.put("lyear","2021");
		writer.write(params);
		writer.flush();
		StringBuffer sbf = new StringBuffer();
		String strRead = null;
		// 返回结果-字节输入流转换成字符输入流，控制台输出字符
		InputStream is = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		while ((strRead = reader.readLine()) != null) {
			sbf.append(strRead);
			sbf.append("\r\n");
		}
		reader.close();
		connection.disconnect();
		String results = sbf.toString();
		System.out.println(results);
		return results;
	}

	/***
	 *
	 * @Description post请求
	 * @Date 14:35 2020/9/25
	 * @Param [stringBuilder, params]
	 * @return java.lang.String
	 **/
	public static String postSet(HttpEntity params, HttpPost httpPost) throws IOException {
		httpPost.setEntity(params);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String result = EntityUtils.toString(entity, "UTF-8");
			return result;
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		String url = "https://edu.sztoosun.com:18005/picApi/group1/upload";
		HttpPost httpPost = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		String fileUrl = "D:/media/transcoding/01212.mp4";
		File file = new File(fileUrl);
		builder.addTextBody("output","json");
		builder.addTextBody("path","/media/20220428/1651130363524");
		builder.addBinaryBody("file", file);
		HttpEntity multipart = builder.build();
		String responseJson = PostUtil.postSet(multipart, httpPost);
		System.out.println(responseJson);
		JsonNode res = JSONUtils.string2JsonNode(responseJson);
		String words = res.get("path").asText();
		System.out.println("文件路径为："+words);
		if(words != null){
			//先删除文件
			int indexs = fileUrl.lastIndexOf("/");
			boolean result = DeleteFileUtils.deleteFileOrDirectory(fileUrl);
			if(result){
				String str3 = fileUrl.substring(0, indexs);
				//再删除文件夹
				result = DeleteFileUtils.deleteFileOrDirectory(str3);
			}
			System.out.println(result);
		}
	}

}
