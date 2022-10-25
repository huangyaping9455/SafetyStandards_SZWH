package org.springblade.common.tool;


import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

public class DesEncryptDecrypt {
    private static String password = "NJZHGDNJZHGD";

    /**
     *
     * @Method: encrypt
     * @Description: 加密数据
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        byte[] bt = encryptByKey(data.getBytes("utf-8"), password);
        System.out.println(bt);
		BASE64Encoder base64en = new BASE64Encoder();
        String strs = new String(base64en.encode(bt));
        return strs;
    }

    private static byte[] encryptByKey(byte[] datasource, String key) {
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes("utf-8"));
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource);
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] decrypt(byte[] src, String key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key.getBytes("utf-8"));
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

}
