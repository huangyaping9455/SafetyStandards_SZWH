package org.springblade.train.tool;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.*;

/**
 * @author hyp
 * @create 2022-04-26 11:47
 * 视频转码工具类
 */
@Slf4j
public class VideoConvertUtil {

	public boolean convert(String inputFile, String outputFile) throws Exception {
		boolean convert = false;
		FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(inputFile);
		Frame captured_frame;
		FFmpegFrameRecorder recorder = null;

		try {
			grabber.start();

			recorder = new FFmpegFrameRecorder(outputFile, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());
//			recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
			recorder.setFormat("mp4");
			recorder.setFrameRate(grabber.getFrameRate());
			recorder.setSampleRate(grabber.getSampleRate());
			recorder.setVideoBitrate(grabber.getVideoBitrate());
			recorder.setAspectRatio(grabber.getAspectRatio());
			recorder.setAudioBitrate(grabber.getAudioBitrate());
			recorder.setAudioOptions(grabber.getAudioOptions());
			recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
//			recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
			recorder.start();

			while (true) {
				captured_frame = grabber.grabFrame();
				if (captured_frame == null) {
					System.out.println("转码完成");
					convert = true;
					break;
				}
				recorder.record(captured_frame);
			}

		} catch (FrameRecorder.Exception e) {
			e.printStackTrace();
		} finally {
			if (recorder != null) {
				try {
					recorder.close();
				} catch (Exception e) {
					System.out.println("recorder.close异常" + e);
				}
			}

			try {
				grabber.close();
			} catch (FrameGrabber.Exception e) {
				System.out.println("frameGrabber.close异常" + e);
			}
		}
		return convert;
	}

	public static boolean isH264AndACC(String absolutePath) throws Exception {
		boolean isH264AndACC = false;
		String path = absolutePath;
		FFmpegFrameGrabber frameGrabber = FFmpegFrameGrabber.createDefault(path);
		try {
			frameGrabber.start();
			log.info("视频格式videoCode: {}", frameGrabber.getVideoCodec());
			log.info("视频格式audioCode: {}", frameGrabber.getAudioCodec());
			if (frameGrabber.getVideoCodec() == avcodec.AV_CODEC_ID_H264
				&& frameGrabber.getAudioCodec() == avcodec.AV_CODEC_ID_AAC) {
				isH264AndACC = true;
			}
		} finally {
			try {
				frameGrabber.close();
			} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
				log.warn("frameGrabber.close异常", e);
			}
		}
		return isH264AndACC;
	}

//	public static void main(String[] args) throws Exception {
////		//原视频地址
////		String url = "https://edu.sztoosun.com:18002/group1/media/20220426/1650941253267/01212.mp4";
////		//转码后的地址
////		String videoSavePath = "C:\\Users\\Administrator\\Desktop\\教育视频\\Desktop\\transcoding";
////		//以某路径实例化一个File对象
////		File file = new File(videoSavePath);
////		if (!file.exists()) {
////			//如果不存在,创建目录
////			file.mkdirs();
////		}
////		//先获取最后一个  / 所在的位置
////		int index1 = url.lastIndexOf(File.separator);
////		//然后获取从最后一个 / 所在索引+1开始 至 字符串末尾的字符
////		String ss1 = url.substring(index1+1);
////		ss1 = ss1.substring(ss1.lastIndexOf("/")+1);
////		videoSavePath = videoSavePath+"\\"+ss1;
////		System.out.println(videoSavePath);
////		boolean h264AndACC = VideoConvertUtil.isH264AndACC(url);
////		log.info("是否是H264：{}", h264AndACC);
////		if(h264AndACC == false){
////			VideoConvertUtil convertUtil = new VideoConvertUtil();
////			log.info("开始时间："+DateUtil.now());
////			boolean ss = convertUtil.convert(url, videoSavePath);
////			if(ss){
////				System.out.println("8888888888888888");
////			}
////			log.info("结束时间："+DateUtil.now());
////		}
//
//		String str = "/group1/media/20220426/1650941253267/01212.mp4";
//
//		//截取_之前字符串
//		int index = str.indexOf("/");//获取第一个_索引
//		String str0 = str.substring(0, index);
//		System.out.println("截取第一个/之前字符串:" + str0);
//
//		index = str.indexOf("/", index + 1);//获取第二个_索引
//		String str1 = str.substring(0, index);
//		System.out.println("截取第二个/之前字符串：" + str1);
//
//		//截取第一个_之后字符串
//		String str01 = str.substring(str0.length() + 1, str.length());
//		System.out.println("截取第一个/之后字符串：" + str01);
//
//		//截取第二个_之后字符串
//		String str2 = str.substring(str1.length() + 1, str.length());
//		System.out.println("截取第二个/之后字符串：" + str2);
//
//		//先获取最后一个  - 所在的位置
//		int indexs = str2.lastIndexOf("/");
//		//获取从0到最后一个 / 之间的字符
//		String str3 = str2.substring(0, indexs);
//		System.out.println("截取最后/之前的字符串：" +str3);
//
//		//截取第一个_之后第三个_之前字符串
//		index = str.indexOf("/", index + 2);//获取第三个_索引
//		String str4 = str.substring(str0.length() + 1, index);
//		System.out.println("截取第一个/之后第三个/之前字符串：" + str4);
//
//	}

}

