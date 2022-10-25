/**
 * Copyright (C), 2015-2021
 * FileName: HttpRequestsss
 * Author:   呵呵哒
 * Date:     2021/10/29 0:50
 * Description:
 */
package org.springblade.common.tool;

//import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.alibaba.fastjson.JSONArray;
/**
 * @创建人 hyp
 * @创建时间 2021/10/29
 * @描述
 */
public class HttpRequestsss {
    public static void main(String []args) throws IOException, JSONException {
        //请求url
        String serverURL = "http://r.bn.cloudjoytech.com.cn/sec/getLearnRecord";
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

        //body参数
        JSONObject parm2 = new JSONObject();
        parm2.put("company","");
        parm2.put("luser","");
        parm2.put("lmonth","10");
        parm2.put("lyear","2021");
        writer.write(parm2.toString());
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
//        JSONArray jsonArray = (JSONArray) JSONArray.parse(results);
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(results);
        System.out.println(jsonObject.get("data"));
        System.out.println(jsonObject.get("data").toString().length());
        String data = jsonObject.getString("data");
        System.out.println(data);
        JSONArray jsonArray = null;
        jsonArray = jsonObject.getJSONArray("data");//获取数组
        System.out.println(jsonArray);
        System.out.println(jsonArray.size());
        System.out.println(jsonArray.getJSONObject(0).get("company"));
        if(jsonArray.size() > 0){
            for(int i = 0;i<jsonArray.size();i++){
                com.alibaba.fastjson.JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                System.out.println(jsonObject1);
                System.out.println(jsonObject1.getString("company"));
            }
        }
    }
}
