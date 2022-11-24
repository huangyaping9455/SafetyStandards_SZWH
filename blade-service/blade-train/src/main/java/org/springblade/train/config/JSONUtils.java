/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * <p>Description: [JSON报文与对象相互转换工具类]
*/
@Slf4j
public class JSONUtils {
	private static ObjectMapper objectMapper = new ObjectMapper();

    // 日起格式化
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    
    /**
     * 
     * <p>@Title：obj2String
     * <p>@Description: [对象转JSON字符串]
     * @param obj 对象
     * @return
     * @CreateDate: 2020年02月20日 15:21:51
     */
    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }

    
    /**
     * 
     * <p>@Title：obj2StringPretty
     * <p>@Description: [对象转Json格式字符串(格式化的Json字符串)]
     * @param obj 对象
     * @return
     * @CreateDate: 2020年02月20日 15:22:32
     */
    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }
    
    
    /**
     * 
     * <p>@Title：string2Obj
     * <p>@Description: [字符串转换为自定义对象]
     * @param str 要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     * @CreateDate: 2020年02月20日 15:22:55
     */
    @SuppressWarnings("unchecked")
	public static <T> T string2Obj(String str, Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }
    
    
    /**
     * 
     * <p>@Title：string2Obj
     * <p>@Description: [JSON字符串转对象]
     * @param str
     * @param typeReference 指定类型
     * @return
     * @CreateDate: 2020年02月20日 15:23:17
     */
    @SuppressWarnings("unchecked")
	public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    
    /**
     * 
     * <p>@Title：string2Obj
     * <p>@Description: [JSON字符串转集合对象]
     * @param str 字符串
     * @param collectionClazz 集合类
     * @param elementClazzes 集合范型对象
     * @return
     * @CreateDate: 2020年02月20日 15:24:30
     */
    public static <T> T string2Obj(String str, Class<?> collectionClazz, Class<?>... elementClazzes) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.warn("Parse String to Object error : {}" + e.getMessage());
            return null;
        }
    }
    
    /**
     * @Description 将Json字符串转换为 JsonNode对象
     * @param str json字符串
     * @return JsonNode 
     * @throws
     */
    public static JsonNode string2JsonNode(String str) {
    	try {
			return objectMapper.readTree(str);
		} catch (Exception e) {
			return null;
		}
    }
}
