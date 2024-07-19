package org.springblade.anbiao.qiye.timer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.anbiao.config.wechat.AccessToken;
import org.springblade.anbiao.config.wechat.HttpUtils;
import org.springblade.anbiao.config.wechat.RedisCache;
import org.springblade.anbiao.config.wechat.WechatProperties;
import org.springblade.anbiao.deptUserWecat.entity.AnbiaoDeptUserWechatInfo;
import org.springblade.anbiao.deptUserWecat.service.IAnbiaoDeptUserWechatInfoService;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.messagePush.NewBacklogMessage;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDeptConfigurationPlanService;
import org.springblade.anbiao.risk.vo.JiashiyuanRiskAllVO;
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
public class SynchronousPushMessageCrontab {

	private static final Object KEY = new Object();
	private static boolean taskFlag = false;
	private IJiaShiYuanService jiaShiYuanService;
	private IOrganizationsService organizationsService;
	private IAnbiaoRiskDeptConfigurationPlanService riskDeptConfigurationPlanService;
	private IAnbiaoDeptUserWechatInfoService deptUserWechatInfoService;

	public HttpUtils httpUtils;

	private WechatProperties wechatProperties;

	private RedisCache redisCache;


	public void run(NewBacklogMessage newBacklogMessage) throws Exception {
		//默认都是用正式模板
		String messageTemplate = "{" +
			"\"touser\":\"{0}\"," +
			"\"template_id\":\""+newBacklogMessage.getTemplateId()+"\"," +
			"\"url\":\"http://weixin.qq.com/download"+"\","
			+ "\"miniprogram\":{"
			+ "\"appid\":\""+newBacklogMessage.getAppidUrl()+"\","
			+ "\"pagepath\":\""+newBacklogMessage.getPageUrl()+"\"}"+","
			+ "\"data\":{"
			+ "\"time3\": {\"value\":\"{1}\",\"color\":\"#173177\"},"
			+ "\"thing5\": {\"value\":\"{2}\",\"color\":\"#173177\"}}}";
		// 获取token
		AccessToken accessToken = httpUtils.getAccessToken(wechatProperties.getMappid(),wechatProperties.getMsecret());
		String URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
		String sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
		// 获取关注用户
//		List<String> users = httpUtils.getUsers(wechatProperties.getMappid(),wechatProperties.getMsecret());
//		if (users != null && users.size() > 0) {
//			System.out.println(users);
			System.out.println("推送的用户opendId是："+newBacklogMessage.getOpenId());
			String postData = messageTemplate.replace("{0}", newBacklogMessage.getOpenId())
				.replace("{1}", newBacklogMessage.getAlarmTime())
				.replace("{2}", newBacklogMessage.getAlarmRemark());
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
					redisCache.del(wechatProperties.getMappid());
					// 获取token
					accessToken = httpUtils.getAccessToken(wechatProperties.getMappid(),wechatProperties.getMsecret());
					URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
					sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
					// 获取关注用户
//		List<String> users = httpUtils.getUsers(wechatProperties.getMappid(),wechatProperties.getMsecret());
//		if (users != null && users.size() > 0) {
//			System.out.println(users);
					System.out.println("推送的用户opendId是："+newBacklogMessage.getOpenId());
					postData = messageTemplate.replace("{0}", newBacklogMessage.getOpenId())
						.replace("{1}", newBacklogMessage.getAlarmTime())
						.replace("{2}", newBacklogMessage.getAlarmRemark());
					log.info("[发送模板信息]sendTemplateMessage:"+postData);
					ss = JSONUtil.toJsonStr(postData);
					System.out.println(ss);
					jsonObject = HttpUtils.httpsRequest(sendUrl, "POST", ss);
					log.info("[发送模板信息] sendTemplateMessage result:"+jsonObject);
				}
			}
//		}
	}



	//驾驶员风险（每日）
	private void driverRiskDay(String openId,String deptId,String jsyId) throws IOException, ParseException {
		if(StringUtils.isNotEmpty(openId)){
			List<JiashiyuanRiskAllVO>  jiashiyuanRiskAllVOList = jiaShiYuanService.selectRiskByDriverId(deptId,jsyId);
			if(jiashiyuanRiskAllVOList != null && jiashiyuanRiskAllVOList.size() > 0 ){
				jiashiyuanRiskAllVOList.forEach(item->{
					NewBacklogMessage newBacklogMessage = new NewBacklogMessage();
					newBacklogMessage.setOpenId(openId);
					newBacklogMessage.setTemplateId("brSq7nJI830o_V0Ry6yTPMFv0CDyTUwMbsE_xzOPdx0");
					newBacklogMessage.setAlarmTime(item.getArdDiscoveryDate());
					newBacklogMessage.setAlarmRemark(item.getArdTitle());
					newBacklogMessage.setAppidUrl("wx2893c20e8065e5af");
					newBacklogMessage.setPageUrl("/packages/mine/dossier/dossier-details/index?userId="+item.getArdAssociationValue()+"&ardTitle="+item.getArdTitle()+"&id="+item.getVehicleId()+"&code="+item.getCheliangpaizhao());
					try {
						run(newBacklogMessage);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			}
		}
	}

	//每6小时执行一次
//	@Scheduled(cron = "0 0 */6 * * ?")
	//每天凌晨9点执行一次
	@Scheduled(cron = "0 0 9 * * ?")
	//每5分钟执行一次
//	@Scheduled(cron = "0 */1 * * * ?")
	public void configureTasks_static_data() {
		synchronized (KEY) {
			if (SynchronousPushMessageCrontab.taskFlag) {
				System.out.println("定时任务-执行微信公众号消息推送已经启动"+DateUtil.now());
				log.info("定时任务-执行微信公众号消息推送已经启动", DateUtil.now());
				return;
			}
			SynchronousPushMessageCrontab.taskFlag = true;
		}
		log.warn("定时任务-执行微信公众号消息推送更新开始", DateUtil.now());
		try {
			System.out.println("执行微信公众号消息推送");

			//获取绑定的从业人员信息
			QueryWrapper<AnbiaoDeptUserWechatInfo> deptUserWechatInfoQueryWrapper = new QueryWrapper<AnbiaoDeptUserWechatInfo>();
			deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getStatus, 1);
			deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getType, 1);
			deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getIsDeleted, 0);
			List<AnbiaoDeptUserWechatInfo> deptUserWechatInfoList = deptUserWechatInfoService.getBaseMapper().selectList(deptUserWechatInfoQueryWrapper);
			if (deptUserWechatInfoList != null && deptUserWechatInfoList.size() > 0 ) {
				//获取推送数据
				deptUserWechatInfoList.forEach(item->{
					QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, item.getYhId());
					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
					JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
					if(deail != null){
						try {
							driverRiskDay(item.getYhGzhOpenid(),deail.getDeptId().toString(),deail.getId());

						} catch (IOException e) {
							throw new RuntimeException(e);
						} catch (ParseException e) {
							throw new RuntimeException(e);
						}
					}

				});
			}

			//获取每日推送数据
//			QueryWrapper<AnbiaoRiskDeptConfigurationPlan> anbiaoRiskDeptConfigurationPlanQueryWrapper = new QueryWrapper<>();
//			anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getStatus, 1);
//			anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getType, 1);
//			anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getIsDeleted, 0);
//			List<AnbiaoRiskDeptConfigurationPlan> riskDeptConfigurationPlanList = riskDeptConfigurationPlanService.getBaseMapper().selectList(anbiaoRiskDeptConfigurationPlanQueryWrapper);
//			if (riskDeptConfigurationPlanList != null && riskDeptConfigurationPlanList.size() > 0) {
//				riskDeptConfigurationPlanList.forEach(item->{
//
//				});
//			}

			System.out.println("执行完成");
		} catch (Exception e) {
			log.error("定时任务-执行微信公众号消息推送-执行出错", e.getMessage());
		}
		SynchronousPushMessageCrontab.taskFlag = false;
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}


}
