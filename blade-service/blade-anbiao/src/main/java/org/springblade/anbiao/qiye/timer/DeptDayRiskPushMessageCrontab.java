package org.springblade.anbiao.qiye.timer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiDetailService;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiService;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineInfoService;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoTZVO;
import org.springblade.anbiao.config.wechat.AccessToken;
import org.springblade.anbiao.config.wechat.HttpUtils;
import org.springblade.anbiao.config.wechat.RedisCache;
import org.springblade.anbiao.deptUserWecat.entity.AnbiaoDeptUserWechatInfo;
import org.springblade.anbiao.deptUserWecat.service.IAnbiaoDeptUserWechatInfoService;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.labor.VO.LaborMonthVO;
import org.springblade.anbiao.labor.service.laborService;
import org.springblade.anbiao.messagePush.NewBacklogMessage;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDeptConfigurationPlanService;
import org.springblade.anbiao.train.entity.TrainInfo;
import org.springblade.anbiao.train.service.ITrainInfoService;
import org.springblade.common.tool.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 */
@Component
@Slf4j
@AllArgsConstructor
public class DeptDayRiskPushMessageCrontab {

	private static final Object KEY = new Object();
	private static boolean taskFlag = false;
	private IJiaShiYuanService jiaShiYuanService;
	private IOrganizationsService organizationsService;
	private IAnbiaoRiskDeptConfigurationPlanService riskDeptConfigurationPlanService;
	private IAnbiaoDeptUserWechatInfoService deptUserWechatInfoService;

	private IAnbiaoAnquanhuiyiService anbiaoAnquanhuiyiService;

	private IAnbiaoAnquanhuiyiDetailService anquanhuiyiDetailService;

	private laborService laborService;

	private IAnbiaoCarExamineInfoService carExamineInfoService;

	private ITrainInfoService trainInfoService;


	private IJiaShiYuanService iJiaShiYuanService;

	public HttpUtils httpUtils;

	private RedisCache redisCache;


	//企业会议日常风险（每日）
	private void deptMeetingRiskDay(String openId,String deptId) throws IOException, ParseException {
		if(StringUtils.isNotEmpty(deptId)){
			List<AnbiaoAnquanhuiyi> anbiaoAnquanhuiyiList = anbiaoAnquanhuiyiService.selectHuiYiMonth(deptId,DateUtil.now());
			if(anbiaoAnquanhuiyiList != null && anbiaoAnquanhuiyiList.size() > 0 ){
				anbiaoAnquanhuiyiList.forEach(item->{

					String month = item.getHuiyikaishishijian()+"月";

					NewBacklogMessage newBacklogMessage = new NewBacklogMessage();
					newBacklogMessage.setOpenId(openId);
					newBacklogMessage.setTemplateId("0mD7DcYJf7gga9osMe3wWDfRDs5fSngNPXOageEA7iM");
					newBacklogMessage.setDeptName(item.getDeptname());
					newBacklogMessage.setAlarmTime(DateUtil.now());
					newBacklogMessage.setAlarmRemark(month+"未进行安全会议签到的人员有："+item.getNum()+"人");
					newBacklogMessage.setAppidUrl("wx2893c20e8065e5af");
					newBacklogMessage.setPageUrl("https://swhaq.com/#/pages/risk/index?dept="+item.getDeptId()+"&id="+item.getId()+"&ardTitle=未进行安全会议签到的人员");
					try {
						runDeptDayRisk(newBacklogMessage);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			}
		}
	}

	//企业劳保日常风险（每日）
	private void deptLabourRiskDay(String openId,String deptId) throws IOException, ParseException {
		if(StringUtils.isNotEmpty(deptId)){
			List<LaborMonthVO> laborMonthVOList = laborService.selectLaborMonth(deptId,DateUtil.now());
			if(laborMonthVOList != null && laborMonthVOList.size() > 0 ){
				laborMonthVOList.forEach(item->{

					String month = item.getAliIssueDate()+"月";

					NewBacklogMessage newBacklogMessage = new NewBacklogMessage();
					newBacklogMessage.setOpenId(openId);
					newBacklogMessage.setTemplateId("0mD7DcYJf7gga9osMe3wWDfRDs5fSngNPXOageEA7iM");
					newBacklogMessage.setDeptName(item.getDeptname());
					newBacklogMessage.setAlarmTime(DateUtil.now());
					newBacklogMessage.setAlarmRemark(month+"未领取劳保用品的人员有："+item.getNum()+"人");
					newBacklogMessage.setAppidUrl("wx2893c20e8065e5af");
					newBacklogMessage.setPageUrl("https://swhaq.com/#/pages/risk/index?dept="+item.getAliDeptIds()+"&id="+item.getAliIds()+"&ardTitle=未领取劳保用品的人员");
					try {
						runDeptDayRisk(newBacklogMessage);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			}
		}
	}

	//企业车辆出车检查日常风险（每日）
	private void deptVehCheckRiskDay(String openId,String deptId) throws IOException, ParseException {
		if(StringUtils.isNotEmpty(deptId)){
			List<AnbiaoCarExamineInfoTZVO> carExamineInfoTZVOList = carExamineInfoService.selectDayCarExamine(deptId,DateUtil.now());
			if(carExamineInfoTZVOList != null && carExamineInfoTZVOList.size() > 0 ){
				carExamineInfoTZVOList.forEach(item->{

					String day = item.getDays()+"日";

					NewBacklogMessage newBacklogMessage = new NewBacklogMessage();
					newBacklogMessage.setOpenId(openId);
					newBacklogMessage.setTemplateId("0mD7DcYJf7gga9osMe3wWDfRDs5fSngNPXOageEA7iM");
					newBacklogMessage.setDeptName(item.getDeptName());
					newBacklogMessage.setAlarmTime(DateUtil.now());
					newBacklogMessage.setAlarmRemark(day+"未进行每日出车检查的车辆："+item.getNum()+"辆");
					newBacklogMessage.setAppidUrl("wx2893c20e8065e5af");
					newBacklogMessage.setPageUrl("https://swhaq.com/#/pages/risk/index?dept="+item.getDeptId()+"&ardTitle=未进行每日出车检查的车辆");
					try {
						runDeptDayRisk(newBacklogMessage);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			}
		}
	}

	//企业安全培训日常风险（每日）
	private void deptTrainRiskDay(String openId,String deptId) throws IOException, ParseException {
		if(StringUtils.isNotEmpty(deptId)){
			List<TrainInfo> trainInfoList = trainInfoService.getDeptWait(deptId);
			if(trainInfoList != null && trainInfoList.size() > 0 ){
				trainInfoList.forEach(item->{
					String month = item.getYue()+"月";

					NewBacklogMessage newBacklogMessage = new NewBacklogMessage();
					newBacklogMessage.setOpenId(openId);
					newBacklogMessage.setTemplateId("0mD7DcYJf7gga9osMe3wWDfRDs5fSngNPXOageEA7iM");
					newBacklogMessage.setDeptName(item.getDeptName());
					newBacklogMessage.setAlarmTime(DateUtil.now());
					newBacklogMessage.setAlarmRemark(month+"未完成安全培训的人员有："+trainInfoList.size()+"人");
					newBacklogMessage.setAppidUrl("wx2893c20e8065e5af");
					newBacklogMessage.setPageUrl("https://swhaq.com/#/pages/risk/index?dept="+item.getDeptId()+"&id="+item.getId()+"&ardTitle=未完成安全培训的人员");
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
		String deptName = "深圳市深威豪科技有限公司";
		//默认都是用正式模板
		String messageTemplate = "{" +
			"\"touser\":\"{0}\"," +
			"\"template_id\":\""+newBacklogMessage.getTemplateId()+"\"," +
			"\"url\":\""+newBacklogMessage.getPageUrl()+"\","
			+ "\"miniprogram\":{"
//			+ "\"appid\":\""+newBacklogMessage.getAppidUrl()+"\","
			+ "\"pagepath\":\""+newBacklogMessage.getPageUrl()+"\"}"+","
			+ "\"data\":{"
			+ "\"thing2\": {\"value\":\"{1}\",\"color\":\"#173177\"},"
			+ "\"time3\": {\"value\":\"{2}\",\"color\":\"#173177\"},"
			+ "\"thing4\": {\"value\":\"{3}\",\"color\":\"#173177\"}}}";
		// 获取token
		AccessToken accessToken = httpUtils.getAccessToken("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
		String URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
		String sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
		// 获取关注用户
//		List<String> users = httpUtils.getUsers("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
//		if (users != null && users.size() > 0) {
//			System.out.println(users);
		System.out.println("推送的用户opendId是："+newBacklogMessage.getOpenId());
		String postData = messageTemplate.replace("{0}", newBacklogMessage.getOpenId())
			.replace("{1}", deptName)
			.replace("{2}", newBacklogMessage.getAlarmTime())
			.replace("{3}", newBacklogMessage.getAlarmRemark());
		log.info("[发送模板信息]sendTemplateMessage:"+postData);
		String ss = JSONUtil.toJsonStr(postData);
		System.out.println(ss);
		JSONObject jsonObject = HttpUtils.httpsRequest(sendUrl, "POST", ss);
		log.info("[发送模板信息] sendTemplateMessage result:"+jsonObject);
		System.out.println(jsonObject.get("errcode").toString());
		if(jsonObject.get("errcode").toString().equals("42001")){
			String errmsg = jsonObject.get("errmsg").toString();
			System.out.println(errmsg);
			if(errmsg.contains("access_token")){
				redisCache.del("wx25b7d86c96470f49");
				// 获取token
				accessToken = httpUtils.getAccessToken("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
				URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
				sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
				// 获取关注用户
//		List<String> users = httpUtils.getUsers("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
//		if (users != null && users.size() > 0) {
//			System.out.println(users);
				System.out.println("推送的用户opendId是："+newBacklogMessage.getOpenId());
				postData = messageTemplate.replace("{0}", newBacklogMessage.getOpenId())
					.replace("{1}", deptName)
					.replace("{2}", newBacklogMessage.getAlarmTime())
					.replace("{3}", newBacklogMessage.getAlarmRemark());
				log.info("[发送模板信息]sendTemplateMessage:"+postData);
				ss = JSONUtil.toJsonStr(postData);
				System.out.println(ss);
				jsonObject = HttpUtils.httpsRequest(sendUrl, "POST", ss);
				log.info("[发送模板信息] sendTemplateMessage result:"+jsonObject);
			}
		}
//		}
	}


	//每6小时执行一次
//	@Scheduled(cron = "0 0 */6 * * ?")
	//每天凌晨14,16,18点执行一次
	@Scheduled(cron = "0 0 16 * * ? ")
	//每5分钟执行一次
//	@Scheduled(cron = "0 */2 * * * ?")
	public void configureTasks_static_data() {
		synchronized (KEY) {
			if (DeptDayRiskPushMessageCrontab.taskFlag) {
				System.out.println("定时任务-执行微信公众号消息推送已经启动"+DateUtil.now());
				log.info("定时任务-执行微信公众号消息推送已经启动", DateUtil.now());
				return;
			}
			DeptDayRiskPushMessageCrontab.taskFlag = true;
		}
		log.warn("定时任务-执行微信公众号消息推送更新开始", DateUtil.now());
		try {
			System.out.println("执行微信公众号消息推送");

			//获取绑定推送的企业用户信息
			QueryWrapper<AnbiaoDeptUserWechatInfo> deptUserWechatInfoQueryWrapper = new QueryWrapper<AnbiaoDeptUserWechatInfo>();
			deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getStatus, 1);
			deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getType, 2);
			deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getIsDeleted, 0);
			List<AnbiaoDeptUserWechatInfo> deptUserWechatInfoList = deptUserWechatInfoService.getBaseMapper().selectList(deptUserWechatInfoQueryWrapper);
			if (deptUserWechatInfoList != null && deptUserWechatInfoList.size() > 0 ) {
				deptUserWechatInfoList.forEach(deptitem-> {
					//根据用户ID获取所属企业ID deptitem.getYhId()  1445----7243f9920ece84e16ebbbff44f22fbab
					AnbiaoDeptUserWechatInfo userWechatInfo = deptUserWechatInfoService.selectByUser(deptitem.getYhId());
					if(userWechatInfo != null){
						//获取发布会议的企业信息
						List<AnbiaoAnquanhuiyi> anbiaoAnquanhuiyiList = anbiaoAnquanhuiyiService.selectHuiYiMonth(userWechatInfo.getDeptId(),DateUtil.now());
						if(anbiaoAnquanhuiyiList != null && anbiaoAnquanhuiyiList.size() > 0 ){
							anbiaoAnquanhuiyiList.forEach(item-> {
								try {
									deptMeetingRiskDay(deptitem.getYhGzhOpenid(),item.getDeptId().toString());
								} catch (IOException e) {
									throw new RuntimeException(e);
								} catch (ParseException e) {
									throw new RuntimeException(e);
								}
							});
						}

						//获取发布劳保的企业信息
						List<LaborMonthVO> laborMonthVOList = laborService.selectLaborMonth(userWechatInfo.getDeptId(),DateUtil.now());
						if(laborMonthVOList != null && laborMonthVOList.size() > 0 ){
							laborMonthVOList.forEach(item-> {
								try {
									deptLabourRiskDay(deptitem.getYhGzhOpenid(),item.getAliDeptIds().toString());
								} catch (IOException e) {
									throw new RuntimeException(e);
								} catch (ParseException e) {
									throw new RuntimeException(e);
								}
							});
						}

						//获取未进行安全检查的车辆信息
						List<AnbiaoCarExamineInfoTZVO> carExamineInfoTZVOList = carExamineInfoService.selectDayCarExamine(userWechatInfo.getDeptId(),DateUtil.now());
						if(carExamineInfoTZVOList != null && carExamineInfoTZVOList.size() > 0 ){
							carExamineInfoTZVOList.forEach(item-> {
								try {
									deptVehCheckRiskDay(deptitem.getYhGzhOpenid(),item.getDeptId());
								} catch (IOException e) {
									throw new RuntimeException(e);
								} catch (ParseException e) {
									throw new RuntimeException(e);
								}
							});
						}

						//获取未完成安全培训的人员信息
						List<TrainInfo> trainInfoList = trainInfoService.getDeptWait(userWechatInfo.getDeptId());
						if(trainInfoList != null && trainInfoList.size() > 0 ){
							trainInfoList.forEach(item-> {
								try {
									deptTrainRiskDay(deptitem.getYhGzhOpenid(),item.getDeptId());
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
			System.out.println("执行完成");
		} catch (Exception e) {
			log.error("定时任务-执行微信公众号消息推送-执行出错", e.getMessage());
		}
		DeptDayRiskPushMessageCrontab.taskFlag = false;
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}
}
