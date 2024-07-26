package org.springblade.anbiao.qiye.timer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineInfoService;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoTZVO;
import org.springblade.anbiao.config.wechat.AccessToken;
import org.springblade.anbiao.config.wechat.HttpUtils;
import org.springblade.anbiao.config.wechat.RedisCache;
import org.springblade.anbiao.deptUserWecat.entity.AnbiaoDeptUserWechatInfo;
import org.springblade.anbiao.deptUserWecat.service.IAnbiaoDeptUserWechatInfoService;
import org.springblade.anbiao.messagePush.NewBacklogMessage;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfigurationPlan;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDeptConfigurationPlanService;
import org.springblade.common.tool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfig implements SchedulingConfigurer {

	@Autowired
	private IAnbiaoDeptUserWechatInfoService deptUserWechatInfoService;

	@Autowired
	private IAnbiaoCarExamineInfoService carExamineInfoService;

	@Autowired
	private IAnbiaoRiskDeptConfigurationPlanService riskDeptConfigurationPlanService;

	@Autowired
	public HttpUtils httpUtils;

	@Autowired
	private RedisCache redisCache;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addTriggerTask(
			// 定义执行的任务内容
			() ->{
				System.out.println("执行定时任务");
				try {
//					run("oc4jx6sAkhoV5Nwr9j-WUlUDKI7I", "5498");
					run(null,null);
				} catch (IOException e) {
					throw new RuntimeException(e);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				System.out.println("执行任务：" + System.currentTimeMillis() / 1000);
			} ,
			// 设定触发条件
			triggerContext -> {
				try {
					String cron = runCron();
//						runCron(); "0 0/2 * * * ?";
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					System.out.println(cron);
					// 这里可以自定义触发时间点 "0 32 14 * * ?"
					return new CronTrigger(cron).nextExecutionTime(triggerContext);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		);
	}

	public String runCron() throws Exception {
		AtomicReference<String> new_cron = new AtomicReference<>("");
		//获取绑定推送的企业用户信息
		QueryWrapper<AnbiaoDeptUserWechatInfo> deptUserWechatInfoQueryWrapper = new QueryWrapper<AnbiaoDeptUserWechatInfo>();
		deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getStatus, 1);
		deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getType, 2);
		deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getIsDeleted, 0);
//		deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getYhId, "1445");
		List<AnbiaoDeptUserWechatInfo> deptUserWechatInfoList = deptUserWechatInfoService.getBaseMapper().selectList(deptUserWechatInfoQueryWrapper);
		if (deptUserWechatInfoList != null && deptUserWechatInfoList.size() > 0 ) {
			deptUserWechatInfoList.forEach(deptitem-> {
				AnbiaoRiskDeptConfigurationPlan plan = riskDeptConfigurationPlanService.selectYujingxiangByName(deptitem.getDeptId());
				if(plan == null){
					plan = riskDeptConfigurationPlanService.selectYujingxiangByName("1");
					if(plan != null){
						String hours = plan.getHours();
						int h = Integer.parseInt(StringUtils.substringBefore(hours,":"));
						int s = Integer.parseInt(StringUtils.substringAfter(hours,":"));
						System.out.println(h+"-------"+s);
						String cron = s+" "+h;
						new_cron.set("0 " + cron + " * * ?");
						System.out.println(cron);
					}
				}
			});
		}
		String cron = new_cron.get();
		return cron;
	}

	public void run(String openid, String deptId) throws IOException, ParseException {
		if(StringUtils.isNotEmpty(openid)){
			deptVehCheckRiskDay(openid, deptId);
		}else{
			//获取绑定推送的企业用户信息
			QueryWrapper<AnbiaoDeptUserWechatInfo> deptUserWechatInfoQueryWrapper = new QueryWrapper<AnbiaoDeptUserWechatInfo>();
			deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getStatus, 1);
			deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getType, 2);
			deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getIsDeleted, 0);
//			deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getYhId, "1445");
			List<AnbiaoDeptUserWechatInfo> deptUserWechatInfoList = deptUserWechatInfoService.getBaseMapper().selectList(deptUserWechatInfoQueryWrapper);
			if (deptUserWechatInfoList != null && deptUserWechatInfoList.size() > 0 ) {
				deptUserWechatInfoList.forEach(deptitem-> {
					//根据用户ID获取所属企业ID deptitem.getYhId()  1445----7243f9920ece84e16ebbbff44f22fbab
					AnbiaoDeptUserWechatInfo userWechatInfo = deptUserWechatInfoService.selectByUser(deptitem.getYhId());
					if (userWechatInfo != null) {
						//获取未进行安全检查的车辆信息
						List<AnbiaoCarExamineInfoTZVO> carExamineInfoTZVOList = carExamineInfoService.selectDayCarExamine(userWechatInfo.getDeptId(), DateUtil.now());
						if (carExamineInfoTZVOList != null && carExamineInfoTZVOList.size() > 0) {
							carExamineInfoTZVOList.forEach(item -> {
								try {
									deptVehCheckRiskDay(deptitem.getYhGzhOpenid(), item.getDeptId());
								} catch (IOException e) {
									throw new RuntimeException(e);
								} catch (ParseException e) {
									throw new RuntimeException(e);
								}
							});
						}
					}
				});
			}
		}
	}

	//企业车辆出车检查日常风险（每日）
	private void deptVehCheckRiskDay(String openId, String deptId) throws IOException, ParseException {
		if (StringUtils.isNotEmpty(deptId)) {
			List<AnbiaoCarExamineInfoTZVO> carExamineInfoTZVOList = carExamineInfoService.selectDayCarExamine(deptId, DateUtil.now());
			if (carExamineInfoTZVOList != null && carExamineInfoTZVOList.size() > 0) {
				carExamineInfoTZVOList.forEach(item -> {

					String day = item.getDays() + "日";

					NewBacklogMessage newBacklogMessage = new NewBacklogMessage();
					newBacklogMessage.setOpenId(openId);
					newBacklogMessage.setTemplateId("0mD7DcYJf7gga9osMe3wWDfRDs5fSngNPXOageEA7iM");
					newBacklogMessage.setDeptName(item.getDeptName());
					newBacklogMessage.setAlarmTime(DateUtil.now());
					newBacklogMessage.setAlarmRemark(day + "未进行安全检查的车辆：" + item.getNum() + "辆");
					newBacklogMessage.setAppidUrl("wx2893c20e8065e5af");
					newBacklogMessage.setPageUrl("https://swhaq.com/#/pages/risk/index?dept=" + item.getDeptId() + "&ardTitle=未进行安全检查的车辆");
					try {
						runDeptDayRisk(newBacklogMessage);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			}
		}
	}

	public void runDeptDayRisk(NewBacklogMessage newBacklogMessage) throws Exception {
		//默认都是用正式模板
		String messageTemplate = "{" +
			"\"touser\":\"{0}\"," +
			"\"template_id\":\"" + newBacklogMessage.getTemplateId() + "\"," +
			"\"url\":\"" + newBacklogMessage.getPageUrl() + "\","
			+ "\"miniprogram\":{"
//			+ "\"appid\":\""+newBacklogMessage.getAppidUrl()+"\","
			+ "\"pagepath\":\"" + newBacklogMessage.getPageUrl() + "\"}" + ","
			+ "\"data\":{"
			+ "\"thing2\": {\"value\":\"{1}\",\"color\":\"#173177\"},"
			+ "\"time3\": {\"value\":\"{2}\",\"color\":\"#173177\"},"
			+ "\"thing4\": {\"value\":\"{3}\",\"color\":\"#173177\"}}}";
		// 获取token
		AccessToken accessToken = httpUtils.getAccessToken("wx25b7d86c96470f49", "5b76df7322cfbb075b0abaf5879a00bc");
		String URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
		String sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
		// 获取关注用户
//		List<String> users = httpUtils.getUsers("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
//		if (users != null && users.size() > 0) {
//			System.out.println(users);
		System.out.println("推送的用户opendId是：" + newBacklogMessage.getOpenId());
		String postData = messageTemplate.replace("{0}", newBacklogMessage.getOpenId())
			.replace("{1}", newBacklogMessage.getDeptName())
			.replace("{2}", newBacklogMessage.getAlarmTime())
			.replace("{3}", newBacklogMessage.getAlarmRemark());
		log.info("[发送模板信息]sendTemplateMessage:" + postData);
		String ss = JSONUtil.toJsonStr(postData);
		System.out.println(ss);
		JSONObject jsonObject = HttpUtils.httpsRequest(sendUrl, "POST", ss);
		log.info("[发送模板信息] sendTemplateMessage result:" + jsonObject);
		System.out.println(jsonObject.get("errcode").toString());
		int code = Integer.parseInt(jsonObject.get("errcode").toString());
		if (code != 0) {
			String errmsg = jsonObject.get("errmsg").toString();
			System.out.println(errmsg);
			if (errmsg.contains("access_token")) {
				redisCache.del("wx25b7d86c96470f49");
				// 获取token
				accessToken = httpUtils.getAccessToken("wx25b7d86c96470f49", "5b76df7322cfbb075b0abaf5879a00bc");
				URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
				sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
				// 获取关注用户
//		List<String> users = httpUtils.getUsers("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
//		if (users != null && users.size() > 0) {
//			System.out.println(users);
				System.out.println("推送的用户opendId是：" + newBacklogMessage.getOpenId());
				postData = messageTemplate.replace("{0}", newBacklogMessage.getOpenId())
					.replace("{1}", newBacklogMessage.getDeptName())
					.replace("{2}", newBacklogMessage.getAlarmTime())
					.replace("{3}", newBacklogMessage.getAlarmRemark());
				log.info("[发送模板信息]sendTemplateMessage:" + postData);
				ss = JSONUtil.toJsonStr(postData);
				System.out.println(ss);
				jsonObject = HttpUtils.httpsRequest(sendUrl, "POST", ss);
				log.info("[发送模板信息] sendTemplateMessage result:" + jsonObject);
			}
		}
//		}
	}

}
