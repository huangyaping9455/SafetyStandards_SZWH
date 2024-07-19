package org.springblade.anbiao.messagePush;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiDetail;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiDetailService;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiService;
import org.springblade.anbiao.config.wechat.AccessToken;
import org.springblade.anbiao.config.wechat.HttpUtils;
import org.springblade.anbiao.config.wechat.RedisCache;
import org.springblade.anbiao.config.wechat.WechatProperties;
import org.springblade.anbiao.deptUserWecat.entity.AnbiaoDeptUserWechatInfo;
import org.springblade.anbiao.deptUserWecat.service.IAnbiaoDeptUserWechatInfoService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDeptConfigurationPlanService;
import org.springblade.anbiao.risk.vo.JiashiyuanRiskAllVO;
import org.springblade.common.tool.StringUtils;
import org.springblade.common.tool.wx.util.WxgzhHttpUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/wechatMessagePush")
@Api(value = "微信公众号消息推送", tags = "微信公众号消息推送")
public class WechatMessagePushController {

	public HttpUtils getHttpUtils;

	public static HttpUtils httpUtils;

	private final WechatProperties wechatProperties;

	private IJiaShiYuanService jiaShiYuanService;
	private IOrganizationsService organizationsService;
	private IAnbiaoRiskDeptConfigurationPlanService riskDeptConfigurationPlanService;
	private IAnbiaoDeptUserWechatInfoService deptUserWechatInfoService;
	private RedisCache redisCache;

	private IAnbiaoAnquanhuiyiService anbiaoAnquanhuiyiService;

	private IAnbiaoAnquanhuiyiDetailService anquanhuiyiDetailService;

	private IJiaShiYuanService iJiaShiYuanService;


	@PostConstruct
	public void init(){
		httpUtils = getHttpUtils;
	}

	@GetMapping("/getOpenId")
	@ApiLog("获取openID-微信")
	@ApiOperation(value = "获取openID-微信", notes = "传入code", position = 1)
	public R getOpenId(String code) throws Exception {
		R rs = new R();
		String appid = "wx25b7d86c96470f49";
		String secret = "5b76df7322cfbb075b0abaf5879a00bc";
		String openid = WxgzhHttpUtils.getOpenID(code,appid,secret);
		rs.setData(openid);
		rs.setCode(200);
		rs.setSuccess(true);
		return rs;
	}

	@GetMapping("/detail")
	@ApiLog("风险配置信息详情")
	@ApiOperation(value = "风险配置信息详情", notes = "传入openId", position = 2)
	public R detail(String openId) {
		R r = new R();
		NewBacklogMessage newBacklogMessage = new NewBacklogMessage();
		newBacklogMessage.setOpenId(openId);
		newBacklogMessage.setTemplateId("brSq7nJI830o_V0Ry6yTPMFv0CDyTUwMbsE_xzOPdx0");
		newBacklogMessage.setAlarmType("非常紧急");
		newBacklogMessage.setAlarmName("风险预警");
		newBacklogMessage.setAlarmTime("2024-06-23"); //2024年06月19日 17:19
		newBacklogMessage.setAlarmRemark("驾驶证信息未完善");
		newBacklogMessage.setAppidUrl("wx2893c20e8065e5af");
		newBacklogMessage.setPageUrl("pages/home/index?jsyId=a2df7094136f03dee55a7fb71618072a");

		try {
			run(newBacklogMessage);
//			run2(newBacklogMessage);
//			test_run(newBacklogMessage);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		r.setMsg("推送成功");
		r.setCode(200);
		r.setSuccess(true);
		return r;
	}

	public static void run(NewBacklogMessage newBacklogMessage) throws Exception {
		//默认都是用正式模板
		String pagepath = "http://rdkj.4008001238.com/customer/index.html#/?user_id=271&customer_id=880&merchant_id=1";
		String messageTemplate = "{" +
			"\"touser\":\"{0}\"," +
			"\"template_id\":\""+newBacklogMessage.getTemplateId()+"\"," +
			"\"url\":\"http://weixin.qq.com/download"+"\","
			+ "\"miniprogram\":{"
			+ "\"appid\":\""+newBacklogMessage.getAppidUrl()+"\","
			+ "\"pagepath\":\""+newBacklogMessage.getPageUrl()+"\"}"+","
			+ "\"data\":{"
			+ "\"short_thing1\": {\"value\":\"{1}\",\"color\":\"#00BFFF\"},"
			+ "\"short_thing2\": {\"value\":\"{2}\",\"color\":\"#00FFFF\"},"
			+ "\"time3\": {\"value\":\"{3}\",\"color\":\"#173177\"},"
			+ "\"thing5\": {\"value\":\"{4}\",\"color\":\"#173177\"}}}";
		// 获取token
		AccessToken accessToken = httpUtils.getAccessToken("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
		String URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
		String sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
		// 获取关注用户
		List<String> users = httpUtils.getUsers("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
		if (users != null && users.size() > 0) {

			System.out.println(users);
			// 获取天气
//			WeatherModel weather = httpUtils.getWeather();
			// 并行遍历推送
			users.stream().parallel().forEach(x->{
				System.out.println(x);
				if (x.equals(newBacklogMessage.getOpenId())) {
					String postData = messageTemplate.replace("{0}", x)
						.replace("{1}", newBacklogMessage.getAlarmType())
						.replace("{2}", newBacklogMessage.getAlarmName())
						.replace("{3}", newBacklogMessage.getAlarmTime())
						.replace("{4}", newBacklogMessage.getAlarmRemark());
					log.info("[发送模板信息]sendTemplateMessage:"+postData);
					String ss = JSONUtil.toJsonStr(postData);
					System.out.println(ss);
					JSONObject jsonObject = HttpUtils.httpsRequest(sendUrl, "POST", ss);
					log.info("[发送模板信息] sendTemplateMessage result:"+jsonObject);
				}
			});
		}
	}

	public static void run2(NewBacklogMessage newBacklogMessage) throws Exception {

		AccessToken accessToken = httpUtils.getAccessToken("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
		String URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
		String sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
		// 获取关注用户
		List<String> users = httpUtils.getUsers("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
		if (users != null && users.size() > 0) {
			System.out.println(users);
			// 获取天气
//			WeatherModel weather = httpUtils.getWeather();
			// 并行遍历推送
			users.stream().parallel().forEach(x -> {
				System.out.println(x);
				if (x != null) {

					JSONObject param = new JSONObject();
					// Map<String, Object> param = new HashMap<>(8);
					param.put("touser", x);
					param.put("template_id", newBacklogMessage.getTemplateId());

					JSONObject data = new JSONObject();

					JSONObject thing1 = new JSONObject();
					thing1.put("value", "人员类");
					JSONObject thing5 = new JSONObject();
					thing5.put("value", "驾驶员信息未完善");
					JSONObject thing2 = new JSONObject();
					thing2.put("value", "驾驶员信息未完善");
					JSONObject date4 = new JSONObject();
					String dateFormat = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
					date4.put("value", "2023-04-07 08:25:02");

					data.put("short_thing1", thing1);
					data.put("short_thing2", thing2);
					data.put("time3", date4);
					data.put("thing5", thing5);

					param.put("data", data);

					String send = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
					String s = JSONUtil.toJsonStr(param);
					String body = HttpUtil.createPost(sendUrl).body(s)
						.header("Content-type", "application/json")
						.execute()
						.charset("UTF-8")
						.body();
					System.out.println(param);
					System.out.println(body);
				}
			});
		}
	}


//	public static void test_run(NewBacklogMessage newBacklogMessage) throws Exception {
//		System.out.println("test++++++++++++++++++++++++++++++++++++++++++++");
//		//默认都是用正式模板
//		String templateId = "FX5QBFSvlg9f-I0DchW0uF4NUdfd7_rJFNr7IdCcz2Q";
//		String appidUrl = "wx0404affd3250861e";
//		String pagepath = "http://rdkj.4008001238.com/customer/index.html#/?user_id=271&customer_id=880&merchant_id=1";
//		String messageTemplate = "{" +
//			"\"touser\":\"{0}\"," +
//			"\"template_id\":\""+templateId+"\"," +
//			"\"url\":\"http://weixin.qq.com/download"+"\","
//			+ "\"miniprogram\":{"
////			+ "\"appid\":\""+appidUrl+"\","
//			+ "\"pagepath\":\""+pagepath+"\"}"+","
//			+ "\"data\":{"
//			+ "\"city\": {\"value\":\"{1}\",\"color\":\"#00BFFF\"},"
//			+ "\"weatherDATA\": {\"value\":\"{2}\",\"color\":\"#00FFFF\"},"
//			+ "\"temperature\": {\"value\":\"{3}\",\"color\":\"#173177\"},"
//			+ "\"wind\": {\"value\":\"{4}\",\"color\":\"#173177\"}}}";
//		// 获取token
//		AccessToken accessToken = httpUtils.getAccessToken("wx8d72399b90d7b5fa","9369b95d0b0f9c0b7a72d9eb754227bc");
//		String URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
//		String sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
//		// 获取关注用户
//		List<String> users = httpUtils.getUsers("wx8d72399b90d7b5fa","9369b95d0b0f9c0b7a72d9eb754227bc");
//		if (users != null && users.size() > 0) {
//			System.out.println(users);
//			// 获取天气
////			WeatherModel weather = httpUtils.getWeather();
//			// 并行遍历推送
//			users.stream().parallel().forEach(x->{
//				System.out.println(x);
//				if (x != null) {
//					String postData = messageTemplate.replace("{0}", x)
//						.replace("{1}", newBacklogMessage.getAlarmType())
//						.replace("{2}", newBacklogMessage.getAlarmName())
//						.replace("{3}", newBacklogMessage.getAlarmTime())
//						.replace("{4}", newBacklogMessage.getAlarmRemark());
//					log.info("[发送模板信息]sendTemplateMessage:"+postData);
//					System.out.println(postData);
//					JSONObject jsonObject = HttpUtils.httpsRequest(sendUrl, "POST", postData);
//					log.info("[发送模板信息] sendTemplateMessage result:"+jsonObject);
//				}
//			});
//		}
//	}

//	public static void main(String[] args)  {
//
//		JSONObject param = new JSONObject();
//		// Map<String, Object> param = new HashMap<>(8);
//		param.put("touser", "oc4jx6k9dzvvL8WspJHDbNfH2fbk");
//		param.put("template_id", "brSq7nJI830o_V0Ry6yTPBX1oiqK3LNzRG4N");
//
//		JSONObject data = new JSONObject();
//
//		JSONObject thing1 = new JSONObject();
//		thing1.put("value", "000003");
//		JSONObject thing5 = new JSONObject();
//		thing5.put("value", "倾斜");
//		JSONObject thing2 = new JSONObject();
//		thing2.put("value", "设备向【内侧】倾斜1.85°，请确认；");
//		JSONObject date4 = new JSONObject();
//		String dateFormat = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
//		date4.put("value", dateFormat);
//		JSONObject thing15 = new JSONObject();
//		thing15.put("value", "郑州金水区");
//
//		data.put("short_thing1", thing1);
//		data.put("short_thing2", thing2);
//		data.put("time3", date4);
////		data.put("thing15", thing15);
//		data.put("thing5", thing5);
//
//		param.put("data", data);
//
//		String send = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
//		String sendUrl = send + "?access_token="
//			+ "81_9pgYCZEiWJQUZKm-ZVSRtrW9NnGrufRAkGMg1wNI1Tumoh1X6lH22i7gB5E6ZR9zW9x4TKAg4iaSzjGh-5g9WZhYWJBfUj2PRMpQC66p2AVo58td-sIb1uOEIHsNMZbAHAFER";
//		String s = JSONUtil.toJsonStr(param);
//		String body = HttpUtil.createPost(sendUrl).body(s)
//			.header("Content-type", "application/json")
//			.execute()
//			.charset("UTF-8")
//			.body();
//		System.out.println(param);
//		System.out.println(body);
//	}


	public void runDriver(NewBacklogMessage newBacklogMessage) throws Exception {
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
		AccessToken accessToken = httpUtils.getAccessToken("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
		String URL_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
		String sendUrl = URL_TEMPLATE_MSG_SEND.replace("{0}", accessToken.getToken());
		// 获取关注用户
//		List<String> users = httpUtils.getUsers("wx25b7d86c96470f49","5b76df7322cfbb075b0abaf5879a00bc");
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
						runDriver(newBacklogMessage);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			}
		}
	}

	@GetMapping("/pushDetail")
	@ApiLog("风险配置信息详情")
	@ApiOperation(value = "风险配置信息详情", notes = "传入openId", position = 2)
	public void pushDetail() {
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
	}

	//企业日常风险（每日）
	private void deptRiskDay(String openId,String deptId) throws IOException, ParseException {
		if(StringUtils.isNotEmpty(deptId)){
			List<AnbiaoAnquanhuiyi> anbiaoAnquanhuiyiList = anbiaoAnquanhuiyiService.selectHuiYiMonth(deptId,DateUtil.now());
			if(anbiaoAnquanhuiyiList != null && anbiaoAnquanhuiyiList.size() > 0 ){
				anbiaoAnquanhuiyiList.forEach(item->{
					QueryWrapper<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetailQueryWrapper = new QueryWrapper<>();
					anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAadAaIds, item.getId());
					anquanhuiyiDetailQueryWrapper.lambda().eq(AnbiaoAnquanhuiyiDetail::getAddApBeingJoined, 0);
					List<AnbiaoAnquanhuiyiDetail> details = anquanhuiyiDetailService.getBaseMapper().selectList(anquanhuiyiDetailQueryWrapper);
					String details1 = "";
					for (AnbiaoAnquanhuiyiDetail a:details) {
						if(!"0".equals(a.getAadApType())){
							QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,"0");
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId,a.getAadApIds());
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId,item.getDeptId());
							JiaShiYuan jiaShiYuan = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
							if (jiaShiYuan!=null){
								details1 += a.getAadApName()+",";
							}
						}else{
							details1 += a.getAadApName()+",";
						}
					}
					item.setAadApName(details1);

					String month = item.getHuiyikaishishijian()+"月";

					NewBacklogMessage newBacklogMessage = new NewBacklogMessage();
					newBacklogMessage.setOpenId(openId);
					newBacklogMessage.setTemplateId("0mD7DcYJf7gga9osMe3wWDfRDs5fSngNPXOageEA7iM");
					newBacklogMessage.setDeptName(item.getDeptname());
					newBacklogMessage.setAlarmTime(DateUtil.now());
					newBacklogMessage.setAlarmRemark(month+"未进行安全会议签到的人员有："+details.size()+"人");
					newBacklogMessage.setAppidUrl("wx2893c20e8065e5af");
					newBacklogMessage.setPageUrl("/packages/mine/dossier/dossier-details/index?dept="+item.getDeptId()+"&id="+item.getId());
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
			"\"template_id\":\""+newBacklogMessage.getTemplateId()+"\"," +
			"\"url\":\"http://weixin.qq.com/download"+"\","
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
			.replace("{1}", newBacklogMessage.getDeptName())
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
					.replace("{1}", newBacklogMessage.getDeptName())
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

	@GetMapping("/pushDeptDetail")
	@ApiLog("风险配置信息详情（企业日常风险信息）")
	@ApiOperation(value = "风险配置信息详情（企业日常风险信息）", notes = "传入openId", position = 2)
	public void pushDeptDetail() {
		//获取绑定推送的企业用户信息
		QueryWrapper<AnbiaoDeptUserWechatInfo> deptUserWechatInfoQueryWrapper = new QueryWrapper<AnbiaoDeptUserWechatInfo>();
		deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getStatus, 1);
		deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getType, 2);
		deptUserWechatInfoQueryWrapper.lambda().eq(AnbiaoDeptUserWechatInfo::getIsDeleted, 0);
		List<AnbiaoDeptUserWechatInfo> deptUserWechatInfoList = deptUserWechatInfoService.getBaseMapper().selectList(deptUserWechatInfoQueryWrapper);
		if (deptUserWechatInfoList != null && deptUserWechatInfoList.size() > 0 ) {
			deptUserWechatInfoList.forEach(deptitem-> {
				//根据用户ID获取所属企业ID
				AnbiaoDeptUserWechatInfo userWechatInfo = deptUserWechatInfoService.selectByUser(deptitem.getYhId());
				if(userWechatInfo != null){
					//获取发布会议的企业信息
					List<AnbiaoAnquanhuiyi> anbiaoAnquanhuiyiList = anbiaoAnquanhuiyiService.selectHuiYiMonth(userWechatInfo.getDeptId(),DateUtil.now());
					if(anbiaoAnquanhuiyiList != null && anbiaoAnquanhuiyiList.size() > 0 ){
						anbiaoAnquanhuiyiList.forEach(item-> {
							try {
								deptRiskDay(deptitem.getYhGzhOpenid(),item.getDeptId().toString());
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
