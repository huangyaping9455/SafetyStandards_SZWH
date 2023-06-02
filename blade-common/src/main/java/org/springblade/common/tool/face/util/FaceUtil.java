package org.springblade.common.tool.face.util;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author hyp
 * @create 2023-06-01 15:04
 */
public class FaceUtil {

	//设置APPID/AK/SK
	private static String APP_ID = "34259647";
	private static String API_KEY = "QCq0tGmAtOMiWz5Po9t3s0hs";
	private static String SECRET_KEY = "Q60kc4kcjvBpTnS3ChoGAXeoIXcQ3NBP";

	public static JSONObject SeachUserUrl(String faceUrl,String seachUrl){
		// 初始化一个AipFace
		AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
		// 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//		client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//		client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

		/** START  获取读取 图片Base64编码 **/
		// 方法一：
//		String image1 = "找一张有人脸的图片，在线图片生成Base64编码 然后复制到这里";
//		String image2 = "找一张有人脸的图片，在线图片生成Base64编码 然后复制到这里";

		// 方法二：
		// 读本地图片
		byte[] bytes = FileUtils.fileToBytes(faceUrl);
		byte[] bytes2 = FileUtils.fileToBytes(seachUrl);
		// 将字节转base64
		String image1 = Base64.encodeBase64String(bytes);
		String image2 = Base64.encodeBase64String(bytes2);
		/** END 获取读取 图片Base64编码 **/

		// 人脸对比
		// image1/image2也可以为url或facetoken, 相应的imageType参数需要与之对应。
		MatchRequest req1 = new MatchRequest(image1, "BASE64");
		MatchRequest req2 = new MatchRequest(image2, "BASE64");
		ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
		requests.add(req1);
		requests.add(req2);

		JSONObject res = client.match(requests);
		return res;
	}

	public static void main(String[] args) {
		String bytes = "D:\\BS\\static\\AttachFiles\\2023\\05\\10\\12.jpg";
		String bytes2 = "D:\\BS\\static\\AttachFiles\\2023\\05\\10\\13.jpg";
		JSONObject jsonObject = FaceUtil.SeachUserUrl(bytes,bytes2);
		JSONObject jsonObject1 = jsonObject.getJSONObject("result");
		String score = jsonObject1.get("score").toString();
		System.out.println(score);

		// 人脸检测
		// 传入可选参数调用接口
//		HashMap<String, String> options = new HashMap<String, String>();
//		options.put("face_field", "age");
//		options.put("max_face_num", "2");
//		options.put("face_type", "LIVE");
//		options.put("liveness_control", "LOW");
//		JSONObject res2 = client.detect(image1, "BASE64", options);
//		System.out.println(res2.toString(2));
	}

}
