package org.springblade.train.timer;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springblade.common.configurationBean.TrainServer;
import org.springblade.common.tool.DeleteFileUtils;
import org.springblade.common.tool.JSONUtils;
import org.springblade.common.tool.PostUtil;
import org.springblade.train.entity.Courseware;
import org.springblade.train.service.IWaitCompletedService;
import org.springblade.train.tool.VideoConvertUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 */
@Component
@Slf4j
@AllArgsConstructor
public class SynchronousVehiclesCrontab {

	private static final Object KEY = new Object();
	private static boolean taskFlag = false;
	private TrainServer trainServer;
	private IWaitCompletedService iWaitCompletedService;

	@Scheduled(cron = "0 */10 * * * ?")
	public void configureTasks_study() {
		synchronized (KEY) {
			if (SynchronousVehiclesCrontab.taskFlag) {
				System.out.println("定时任务-执行视频转码已经启动"+DateUtil.now());
				log.info("定时任务-执行视频转码已经启动", DateUtil.now());
				return;
			}
			SynchronousVehiclesCrontab.taskFlag = true;
		}
		log.warn("定时任务-执行视频转码开始", DateUtil.now());
		try {
			String result = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
//			String result = "2022-04-26";
			//转码后的地址
			List<Courseware> coursewareList = iWaitCompletedService.selectCoursewareByTime(result);
			coursewareList.forEach(item->{
				boolean h264AndACC = false;
				try {
					String sourceFile = trainServer.getFileserver()+item.getSourceFile();
					h264AndACC = VideoConvertUtil.isH264AndACC(sourceFile);
					log.info("是否是H264：{}", h264AndACC);
					//判断视频是否是H264格式
					if(h264AndACC == false){
						VideoConvertUtil convertUtil = new VideoConvertUtil();
						//原视频地址
						String url = item.getSourceFile();

						//转码后的地址
//						String videoSavePath = "C:\\Users\\Administrator\\Desktop\\教育视频\\Desktop\\transcoding";

//						//截取 / 之前字符串,获取第一个_索引
//						int index = url.indexOf("/");
//						//获取第二个 / 索引
//						index = url.indexOf("/", index + 1);
//						String str1 = url.substring(0, index);
//
//						//截取第二个 / 之后字符串
//						String str2 = url.substring(str1.length() + 1, url.length());
//						System.out.println("截取第二个/之后字符串：" + str2);
//
//						//先获取最后一个  - 所在的位置
//						int indexs = str2.lastIndexOf("/");
//
//						//获取从0到最后一个 / 之间的字符
//						String str3 = str2.substring(0, indexs);
//						System.out.println("截取最后/之前的字符串：" +str3);
//
//						String videoSavePath = trainServer.getVideoServer()+str3+"/"+"transcoding";
//						System.out.println(videoSavePath);
//
//						File file = new File(videoSavePath);
//						if (!file.exists()) {
//							//如果不存在,创建目录
//							file.mkdirs();
//						}
//						//先获取最后一个 / 所在的位置
//						int index1 = url.lastIndexOf(File.separator);
//						//然后获取从最后一个 / 所在索引+1开始 至 字符串末尾的字符
//						String ss1 = url.substring(index1+1);
//						ss1 = ss1.substring(ss1.lastIndexOf("/")+1);
//						String outofurl = videoSavePath+"/"+ss1;

						String outofurl = trainServer.getVideoServer();
						File file = new File(outofurl);
						if (!file.exists()) {
							//如果不存在,创建目录
							file.mkdirs();
						}
						//先获取最后一个 / 所在的位置
						int index1 = url.lastIndexOf(File.separator);
						//然后获取从最后一个 / 所在索引+1开始 至 字符串末尾的字符
						String ss1 = url.substring(index1+1);
						ss1 = ss1.substring(ss1.lastIndexOf("/")+1);
						//输出视频转码后的地址
						outofurl = outofurl+"/"+ss1;
						System.out.println("转码视频输出地址："+outofurl);
						boolean status = convertUtil.convert(sourceFile, outofurl);
						if (status == true){
							log.info("结束时间："+DateUtil.now());
							String uploadUrl = trainServer.getUploadVideoServer();
							System.out.println("附件上传地址："+uploadUrl);
							HttpPost httpPost = new HttpPost(uploadUrl);
							MultipartEntityBuilder builder = MultipartEntityBuilder.create();
							String fileUrl = trainServer.getVideoServer()+"/"+ss1;
							File files = new File(fileUrl);
							String result_date = new SimpleDateFormat("yyyyMMdd").format(new Date());
							long result_time = System.currentTimeMillis();
							String path = "/media/"+result_date+"/"+result_time;
							builder.addTextBody("output","json");
							builder.addTextBody("path",path);
							builder.addBinaryBody("file", files);
							HttpEntity multipart = builder.build();
							String responseJson = PostUtil.postSet(multipart, httpPost);
							System.out.println(responseJson);
							JsonNode res = JSONUtils.string2JsonNode(responseJson);
							String words = res.get("path").asText();
							System.out.println("文件路径为："+words);
							if(words != null){
								boolean i = iWaitCompletedService.updateCoursewareById(words,item.getId());
								if(i == false){
									log.info("更新课件视频地址失败："+DateUtil.now());
								}else{
									//先删除文件
									boolean fileUrl_result = DeleteFileUtils.deleteFileOrDirectory(fileUrl);
									if(fileUrl_result){
//										int indexs = fileUrl.lastIndexOf("/");
//										String str3 = fileUrl.substring(0, indexs);
//										//再删除文件夹
//										fileUrl_result = DeleteFileUtils.deleteFileOrDirectory(str3);
										System.out.println(result);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			System.out.println("执行视频转码结束");
		} catch (Exception e) {
			log.error("定时任务-执行视频转码-执行出错", e.getMessage());
		}
		SynchronousVehiclesCrontab.taskFlag = false;
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}

}
