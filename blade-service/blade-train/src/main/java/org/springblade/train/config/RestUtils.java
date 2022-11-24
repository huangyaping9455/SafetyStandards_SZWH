package org.springblade.train.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

/*
 * @Title RestUtils
 * @Description
 * @Author hxz
 * @Date 2020/7/14 16:04
 */
public class RestUtils {

    /**
     * 获取图片Base64
     * @param url 图片URL
     * @return
     */
    public static String get(String url){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(url,byte[].class);
        BASE64Encoder base64Encoder = new BASE64Encoder();
        byte[] data = responseEntity.getBody();
        String imageBase64 = base64Encoder.encode(data);
        return imageBase64;
    }
}
