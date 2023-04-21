package org.springblade.common.tool;

/**
 * @author hyp
 * @create 2022-04-07 17:34
 */

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Base64Helper {
	/**
	 * 编码
	 * @param byteArray
	 * @return
	 */
	public static String encode(byte[] byteArray) {
		return new String(new Base64().encode(byteArray));
	}

	/**
	 * 解码
	 * @param base64EncodedString
	 * @return ss
	 */
	public static byte[] decode(String base64EncodedString) {
		return new Base64().decode(base64EncodedString);
	}


	/**
	 * 本地图片转换成base64字符串
	 * @param imgFile	图片对象
	 * @return
	 * @author huangshikai
	 */
	public static String ImageToBase64ByLocal(File imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;

		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);

			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		String base64Str = encoder.encode(data);
		base64Str.replaceAll("(\r\n|\r|\n|\n\r)", "");//替换base64后的字符串中的回车换行
		return base64Str;// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 本地图片转换成base64字符串
	 * @param imgFilePath 本地图片存放路径
	 * @return
	 * @author huangshikai
	 */
	public static String ImageToBase64ByLocal(String imgFilePath){
		return ImageToBase64ByLocal(new File(imgFilePath));
	}

	/**
	 * 在线图片转换成base64字符串
	 * @param imgURL	图片线上路径
	 * @return
	 * @author huangshikai
	 */
	public static String ImageToBase64ByOnline(String imgURL) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try {
			// 创建URL
			URL url = new URL(imgURL);
			byte[] by = new byte[1024];
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
			// 将内容读取内存中
			int len = -1;
			while ((len = is.read(by)) != -1) {
				data.write(by, 0, len);
			}
			// 关闭流
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		String base64Str = encoder.encode(data.toByteArray());
		base64Str.replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", "");//替换base64后的字符串中的回车换行
		return base64Str;// 返回Base64编码过的字节数组字符串
	}

	/**
	 * base64字符串转换成图片
	 * @param imgStr		base64字符串
	 * @param imgFilePath	图片存放路径
	 * @return
	 * @author huangshikai
	 */
	public static boolean Base64ToImage(String imgStr,String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
		if (StringUtils.isEmpty(imgStr)) // 图像数据为空
			return false;

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr.replace("data:image/png;base64,", ""));
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}

			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	public static void main(String[] args) throws Exception {

		//本地图片地址
//		String url = "C:\\\\Users\\\\Administrator\\\\Desktop\\\\疫情防控\\\\1649322508315.png";
		//在线图片地址
//		String string = "http://bpic.588ku.com//element_origin_min_pic/17/03/03/7bf4480888f35addcf2ce942701c728a.jpg";
		String string = "http://150.138.133.178:8204//AttachFiles/2022/04/driverApp/1649322508315.png";

//		String str = ImageToBase64ByLocal(url);

		String ste = ImageToBase64ByOnline(string);

//		System.out.println(str);

//		Base64ToImage(str,"C:/Users/Administrator/Desktop/test1.jpg");

		System.out.println(ste);
		Base64ToImage(ste, "C:/Users/Administrator/Desktop/test2.jpg");
	}
}

