package org.springblade.anbiao.config.wechat;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Lanys
 * @Description: 定时推送任务
 * @author Administrator
 * @create 2023-10-07 10:56
 */
@Component
@Slf4j
@AllArgsConstructor
public class WechatPushCrontab {

	private static final Object KEY = new Object();
	private static boolean taskFlag = false;
	@Resource
	public HttpUtils httpUtils;

	public void run(String params) throws Exception {
		//默认都是用正式模板
		String templateId = "oXuOy7ObCR_bS5iRNSjubS-bUJWax3xIW701M-Qlvzo";
		String appidUrl = "wx0404affd3250861e";
		String pagepath = "http://rdkj.4008001238.com/customer/index.html#/?user_id=271&customer_id=880&merchant_id=1";
		String messageTemplate = "{" +
			"\"touser\":\"{0}\"," +
			"\"template_id\":\""+templateId+"\"," +
			"\"url\":\"http://weixin.qq.com/download"+"\","
//			+ "\"miniprogram\":{"
//			+ "\"appid\":\""+appidUrl+"\","
//			+ "\"pagepath\":\""+pagepath+"\"}"+","
			+ "\"data\":{"
			+ "\"date\": {\"value\":\"{1}\",\"color\":\"#00BFFF\"},"
			+ "\"week\": {\"value\":\"{2}\",\"color\":\"#00FFFF\"},"
			+ "\"city\": {\"value\":\"{3}\",\"color\":\"#173177\"},"
			+ "\"temp\": {\"value\":\"{4}\",\"color\":\"#EE212D\"},"
			+ "\"low\": {\"value\":\"{5}\",\"color\":\"#FF6347\"},"
			+ "\"high\": {\"value\":\"{6}\",\"color\":\"#173177\"}}}";
		// 获取token
		AccessToken accessToken = httpUtils.getAccessToken();
		String URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
		String sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
		// 获取关注用户
		List<String> users = httpUtils.getUsers();
		if (users != null && users.size() > 0) {
			// 获取天气
//			WeatherModel weather = httpUtils.getWeather();
			// 并行遍历推送
			users.stream().parallel().forEach(x->{
				if (x != null) {
					String postData = messageTemplate.replace("{0}", x)
//						.replace("{1}", weather.getDate())
//						.replace("{2}", weather.getWeek())
//						.replace("{3}", weather.getCity())
//						.replace("{4}", weather.getText())
//						.replace("{5}", weather.getLow())
//						.replace("{6}", weather.getHigh());

						.replace("{1}", DateUtil.now())
						.replace("{2}", "星期六")
						.replace("{3}", "重庆市")
						.replace("{4}", "阴天")
						.replace("{5}", "15")
						.replace("{6}", "20");
					log.info("[发送模板信息]sendTemplateMessage:"+postData);
					JSONObject jsonObject = HttpUtils.httpsRequest(sendUrl, "POST", postData);
					log.info("[发送模板信息] sendTemplateMessage result:"+jsonObject);
				}
			});
		}
	}

	//每6小时执行一次
//	@Scheduled(cron = "0 0/1 * * * ?")
//	public void configureTasks_static_data() {
//		synchronized (KEY) {
//			if (WechatPushCrontab.taskFlag) {
//				System.out.println("定时任务-执行同步预警数据已经启动"+DateUtil.now());
//				log.info("定时任务-执行同步预警数据已经启动", DateUtil.now());
//				return;
//			}
//			WechatPushCrontab.taskFlag = true;
//		}
//		log.warn("定时任务-执行同步预警数据更新开始", DateUtil.now());
//		try {
//			System.out.println("执行同步预警数据");
//
//			//获取驾驶员风险信息
//			run(null);
//
//			System.out.println("执行完成");
//		} catch (Exception e) {
//			log.error("定时任务-执行同步预警数据-执行出错", e.getMessage());
//		}
//		WechatPushCrontab.taskFlag = false;
//		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
//	}
}
