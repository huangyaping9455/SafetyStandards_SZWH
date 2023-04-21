package org.springblade.common.tool;

import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * @author hyp
 * @create 2022-04-06 15:00
 * 通用文字识别
 */
public class GeneralBasic {

	/**
	 * 重要提示代码中所需工具类
	 * FileUtil,Base64Util,HttpUtil,GsonUtils请从
	 * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	 * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	 * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	 * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
	 * 下载
	 */
	public static String generalBasic(String filePath) {
		// 请求url
		String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
		try {
			// 本地文件路径
//			filePath = "C:\\Users\\Administrator\\Desktop\\疫情防控\\xck2.jpg";
			if(filePath.contains("http")){
				ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.POST, url);
				// 设置header参数
				request.addHeaderParameter("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
				request.addQueryParameter("access_token", AuthService.getAuth());
				// 设置jsonBody参数
				String jsonBody = "url="+filePath+"&detect_direction=false";
				request.setJsonBody(jsonBody);
				ApiExplorerClient client = new ApiExplorerClient();
				try {
					ApiExplorerResponse response = client.sendRequest(request);
					// 返回结果格式为Json字符串
					String result = response.getResult();
					System.out.println(result);
					return result;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				byte[] imgData = FileUtil.readFileByBytes(filePath);
				String imgStr = Base64Util.encode(imgData);
				String imgParam = URLEncoder.encode(imgStr, "UTF-8");

				String param = "image=" + imgParam;

				// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
				//[调用鉴权接口获取的token]
//			String accessToken = "24.76628684f867585404be3a741c302d47.2592000.1651818659.282335-16343883";
				String accessToken = AuthService.getAuth();

				String result = HttpUtil.post(url, accessToken, param);
				System.out.println(result);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		//行程卡
		String ss = GeneralBasic.generalBasic("C:\\Users\\Administrator\\Desktop\\疫情防控\\xck3.jpg");
//		String ss = GeneralBasic.generalBasic("http://150.138.133.178:8204/AttachFiles/2022/04/driverApp/1649399490462.jpg");
		System.out.println(ss);
		JsonNode res = JSONUtils.string2JsonNode(ss);
		JsonNode lists = res.get("words_result");
		Iterator<JsonNode> elements = lists.elements();
		String remark = "";
		while(elements.hasNext()) {
			JsonNode node = elements.next();
			String words = node.get("words").asText();
			if(words.contains("途经：")){
				remark += node.get("words").asText();
				String str1 = remark.substring(0, remark.indexOf("："));
				String str2 = remark.substring(str1.length() + 1, remark.length());
				System.out.println(str1);
				System.out.println(str2);
				System.out.println("途经："+remark);
			}
			if(words.contains("（注：*")){
				if(words.contains("（注：*")){
					remark += node.get("words").asText();
					remark = remark.substring(0, remark.indexOf("（"));
				}
				System.out.println(remark);
				String str1 = remark.substring(0, remark.indexOf("："));
				String str2 = remark.substring(str1.length()+1, remark.length());
				System.out.println(str2);
				System.out.println("最近14天经过风险区域");
			}
			if(words.contains("绿色行程卡")){
				System.out.println("无风险");
			}
			if(words.contains("黄色行程卡")){
				System.out.println("中风险");
			}
			if(words.contains("红色行程卡")){
				System.out.println("高风险");
			}
		}

		//健康码
//		String ss = GeneralBasic.generalBasic();
//		System.out.println(ss);
//		JsonNode res = JSONUtils.string2JsonNode(ss);
//		JsonNode lists = res.get("words_result");
//		Iterator<JsonNode> elements = lists.elements();
//		String remark = "";
//		while(elements.hasNext()) {
//			JsonNode node = elements.next();
//			String words = node.get("words").asText();
//			if(words.contains("绿码")){
//				remark = node.get("words").asText();
//			}
//			if(words.contains("黄码")){
//				remark = node.get("words").asText();
//			}
//			if(words.contains("红码")){
//				remark = node.get("words").asText();
//			}
//		}
//		System.out.println(remark);
	}

	/**
	 *
	 * 远程读取image转换为Base64字符串
	 * @param imgUrl
	 * @return
	 */
	private static String ImageBase64(String imgUrl) {
		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		try{
			url = new URL(imgUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			httpUrl.getInputStream();
			is = httpUrl.getInputStream();

			outStream = new ByteArrayOutputStream();
			//创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			//每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			//使用一个输入流从buffer里把数据读取出来
			while( (len=is.read(buffer)) != -1 ){
				//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			// 对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();
			String base64Str = encoder.encode(outStream.toByteArray());
			base64Str.replaceAll("\r\n|\r|\n|\n\r", "");//替换base64后的字符串中的回车换行
			base64Str.replaceAll("\r\n", "");
			base64Str.replaceAll("\r", "");
			base64Str.replaceAll("\n", "");
			return Base64.encodeBase64String(outStream.toByteArray());
//			return base64Str;// 返回Base64编码过的字节数组字符串
//			return new BASE64Encoder().encode(outStream.toByteArray());
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outStream != null)
			{
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpUrl != null)
			{
				httpUrl.disconnect();
			}
		}
		imgUrl.replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", "");//替换base64后的字符串中的回车换行
		return imgUrl;// 返回Base64编码过的字节数组字符串
	}
}
