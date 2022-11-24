package org.springblade.train.config;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.configurationBean.TrainServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class FacePostProcessor {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private FaceProperties faceProperties;

    private TrainServer trainServer;

    /**
     * 请求
     * @param params {userName:zhangsan,password:123456}
     */
    public String postMsg(String postUrl,String params) {
        // request
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        String responseMsg = "";
        try {
            // connection
            URL realUrl = new URL(postUrl);
            connection = (HttpURLConnection) realUrl.openConnection();

            // connection setting
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(3 * 1000);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");

            // do param
            connection.setDoOutput(true);// 是否输入参数

//			byte[] bypes = params.toString().getBytes();
//			connection.getOutputStream().write(bypes);// 输入参数

            // do connection
            connection.connect();

            if(!"".equals(params)){
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(params);
                writer.close();
            }


            //Map<String, List<String>> map = connection.getHeaderFields();

            // valid StatusCode
            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                throw new RuntimeException("Http Request StatusCode(" + statusCode + ") Invalid.");
            }

            // result
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

    public String getAccessToken(){
        Object token = redisTemplate.opsForValue().get(CacheConstants.BD_FACE_ACCESS_TOKEN);
        if(Objects.nonNull(token)){
            return token.toString();
        }
        String url = trainServer.getAccessTokenUrl();
        url = url +"&client_id="+trainServer.getClientId();
        url = url +"&client_secret="+trainServer.getClientSecret();
        try {

            String ss = postMsg(url,"");

            JsonNode node = JSONUtils.string2JsonNode(ss);
            token = node.get("access_token").asText("");
            redisTemplate.opsForValue().set(CacheConstants.BD_FACE_ACCESS_TOKEN,token,1, TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token.toString();
    }

    @Async
    public void asyncMgrFace(String postUrl,String params){
        String res = this.postMsg(postUrl,params);
        JsonNode node = JSONUtils.string2JsonNode(res);

        Integer errorCode = node.get("error_code").asInt(-1);

        // 223105 是 人脸已存在，后台添加的话不重复提交。
        if (errorCode != 0 && errorCode != 223105){
            log.info("人脸管理出错，返回值：\r\n{} 重新提交：URL={}，\r\n参数={}",res,postUrl,params);
            this.postMsg(postUrl,params);
        }

    }


}
