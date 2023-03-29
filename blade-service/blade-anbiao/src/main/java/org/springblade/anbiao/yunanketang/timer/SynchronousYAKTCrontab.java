package org.springblade.anbiao.yunanketang.timer;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanTable;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.qiyeshouye.entity.DriverStudyHours;
import org.springblade.anbiao.qiyeshouye.entity.DriverStudyUrl;
import org.springblade.anbiao.yunanketang.service.IDriverStudyUrlService;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.tool.JSONUtils;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Dict;
import org.springblade.system.feign.IDictClient;
import org.springblade.system.feign.ISysClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author hyp
 * @description: TODO
 * @projectName SynchronousYAKTCrontab
 */
@Component
@Slf4j
@AllArgsConstructor
public class SynchronousYAKTCrontab {

	private static final Object KEY = new Object();
	private static boolean taskFlag = false;

	private IJiaShiYuanService iJiaShiYuanService;

	private IDriverStudyUrlService iDriverStudyUrlService;

	private ISysClient iSysClient;

	private AlarmServer alarmServer;

	private IDictClient iDictClient;

	private IOrganizationsService iOrganizationsService;

	//获取token
	private static String fillMethod() throws IOException {
		String ss = "";
		String url = "https://app.gdjtpx.com/client/api/token";
		String token = "''";
		String requestid = "'53533bc9-d7e9-11ec-b5ab-f0d4e2e98821'";
		String signature = "''";
		String account = "'pc1075063'";
		String accesskey = "'sVsNEMIs^lyaZ&_AQ5^tFbvL6gbPBLKP'";
		String json = "{\"header\":{\"token\":" + token + ",\"requestid\":" + requestid + ",\"signature\":" + signature + "},\"body\":{\"account\":" + account + ",\"accesskey\":" + accesskey + "}}";
		System.out.println(json);
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		// json传递
		StringEntity postingString = new StringEntity(json);
		post.setEntity(postingString);
//		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);
		String content = EntityUtils.toString(response.getEntity());
		System.out.println(content);
		JSONObject jsonObject = JSONObject.parseObject(content);
		System.out.println(jsonObject);
		String result = jsonObject.getString("OK");
		System.out.println(result);
		if ("true".equals(result)) {
			ss = jsonObject.getString("Data");
			System.out.println(ss);
		}
		return ss;
	}

	//获取学习地址
	private void addDriverStudyUrl(String token) throws IOException {
		if (token != null){
			String new_token = "\""+token+"\"";
			String url = "https://app.gdjtpx.com/client/api/member/info";
			String requestid = "'53533bc9-d7e9-11ec-b5ab-f0d4e2e98821'";
			String signature = "''";
			List<JiaShiYuanTable> jiaShiYuanTableList = iJiaShiYuanService.jiaShiYuanTableList(null);
			if (jiaShiYuanTableList != null) {
				jiaShiYuanTableList.forEach(item->{
					try{
						if(item.getShenfenzhenghao() != null){
							//"'50022519851225653x'";
							String pid = "\"" + item.getShenfenzhenghao() + "\"";
							String json = "{\"header\":{\"token\":" + new_token + ",\"requestid\":" + requestid + ",\"signature\":" + signature + "},\"body\":{\"pid\":" + pid + "}}";
							System.out.println(json);
							HttpClient httpClient = new DefaultHttpClient();
							HttpPost post = new HttpPost(url);
							// json传递
							StringEntity postingString = null;
							try {
								postingString = new StringEntity(json);
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
							post.setEntity(postingString);
					//		post.setHeader("Content-type", "application/json");
							HttpResponse response = null;
							try {
								response = httpClient.execute(post);
							} catch (IOException e) {
								e.printStackTrace();
							}
							String content = null;
							try {
								content = EntityUtils.toString(response.getEntity());
							} catch (IOException e) {
								e.printStackTrace();
							}
							System.out.println(content);
							System.out.println("1111111"+pid);
							if(!"DOCTYPE".contains(content)) {
								JSONObject jsonObject = JSONObject.parseObject(content);
								System.out.println(jsonObject);
								JsonNode res = JSONUtils.string2JsonNode(content);
								System.out.println(res);
								String result = jsonObject.getString("OK");
								System.out.println(result);
								if ("true".equals(result)) {
									DriverStudyUrl driverStudyUrl = new DriverStudyUrl();
									String ss = jsonObject.getString("Data");
									System.out.println(ss);
									System.out.println(res.get("Data").get("ykya").get("member"));
									String ykya_remark = res.get("Data").get("ykya").get("member").toString();
									String yakt_remark = res.get("Data").get("yakt").get("member").toString();
									System.out.println(ykya_remark);
									if (ykya_remark.equals("true")) {
										driverStudyUrl.setType("ykya");
										driverStudyUrl.setMember(res.get("Data").get("ykya").get("member").toString());
										driverStudyUrl.setVerified(res.get("Data").get("ykya").get("verified").toString());
									}
									if (yakt_remark.equals("true")) {
										driverStudyUrl.setType("yakt");
										driverStudyUrl.setMember(res.get("Data").get("yakt").get("member").toString());
										driverStudyUrl.setVerified(res.get("Data").get("yakt").get("verified").toString());
										String studyUrl = res.get("Data").get("yakt").get("url").toString();
										studyUrl = studyUrl.replace("\"", "");
										driverStudyUrl.setStudyurl(studyUrl);
									}
									if (ykya_remark.equals("true") && yakt_remark.equals("true")) {
										driverStudyUrl.setType("ykya,yakt");
										driverStudyUrl.setMember("true");
										driverStudyUrl.setVerified("false");
									}
									pid = pid.replace("\"", "");
									JiaShiYuan driverInfo = iJiaShiYuanService.getjiaShiYuanByOne(null, null, null, pid, null);
									if (driverInfo != null) {
										if (StringUtils.isNotBlank(driverStudyUrl.getStudyurl()) && !"null".equals(driverStudyUrl.getStudyurl())) {
											driverStudyUrl.setJiashiyuanid(driverInfo.getId());
											driverStudyUrl.setShenfenzhenghao(driverInfo.getShenfenzhenghao());
											driverStudyUrl.setDeptid(driverInfo.getDeptId().toString());
											driverStudyUrl.setCaozuoshijian(DateUtil.now());
											DriverStudyUrl driverStudyUrl1 = iDriverStudyUrlService.selectByIds(driverInfo.getId());
											if (driverStudyUrl1 == null) {
												iDriverStudyUrlService.insertSelective(driverStudyUrl);
											} else {
												driverStudyUrl.setId(driverStudyUrl1.getId());
												iDriverStudyUrlService.updateSelective(driverStudyUrl);
											}
										}
									}
								}
							}
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				});
			}
		}
	}

	//获取学习记录
	private void addDriverStudyHours(String code,String year,String month,String level,String date,String token) throws IOException {
		if (token != null){
			token = "\""+token+"\"";
			String url = "https://app.gdjtpx.com/client/api/ticket/info";
			String requestid = "\"53533bc9-d7e9-11ec-b5ab-f0d4e2e98821\"";
			String signature = "''";
			code = "\""+code+"\"";     //"'A1043977'";
			year = "\""+year+"\"";
			month = "\""+month+"\"";
			level = "\""+level+"\"";
			level = level;
			String json = "{\"header\":{\"token\":"+token+",\"requestid\":"+requestid+",\"signature\":"+signature+"},\"body\":{\"code\":"+code+",\"year\":"+year+",\"month\":"+month+",\"level\":"+level+"}}";
//			String json = "{\"header\":{\"token\":\"f30e9af79abb46dfa1d8391e814695b7\",\"requestid\":'53533bc9-d7e9-11ec-b5ab-f0d4e2e98821',\"signature\":''},\"body\":{\"code\":\"A1043977\",\"level\":\"道路危险货物运输押运人员\",\"year\":\"2022\",\"month\":\"5\"}}";
			System.out.println(json);
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			// json传递
			StringEntity postingString = new StringEntity(json,"UTF-8");
			//设置编码格式
//			postingString.setContentType("UTF-8");
			post.setEntity(postingString);
//			post.setHeader("Content-Type", "application/json;charset=utf8");
			HttpResponse response = httpClient.execute(post);
			String content = EntityUtils.toString(response.getEntity());
			System.out.println(content);
			JSONObject jsonObject = JSONObject.parseObject(content);
			System.out.println(jsonObject);
			JsonNode res = JSONUtils.string2JsonNode(content);
			System.out.println(res);
			String result = jsonObject.getString("OK");
			System.out.println(result);
			if("true".equals(result)) {
				DriverStudyHours driverStudyHours = new DriverStudyHours();
				String ss = jsonObject.getString("Data");
				System.out.println(ss);
				JsonNode lists = res.get("Data");
				if (lists.size() > 0) {
					try{
						Iterator<JsonNode> elements = lists.elements();
						while (elements.hasNext()) {
							JsonNode node = elements.next();
							System.out.println(node.toString());
							driverStudyHours.setComid(node.get("id").asText().replace("\"", ""));
							System.out.println(node.get("member").get("pid").asText().replace("\"", ""));
							driverStudyHours.setPid(node.get("member").get("pid").asText().replace("\"", ""));
							driverStudyHours.setName(node.get("member").get("name").toString().replace("\"", ""));
							driverStudyHours.setCode(node.get("company").get("code").toString().replace("\"", ""));
							driverStudyHours.setDeptname(node.get("company").get("name").toString().replace("\"", ""));
							driverStudyHours.setCourse(node.get("course").toString().replace("\"", ""));
							driverStudyHours.setDate(node.get("date").toString().replace("\"", ""));
							driverStudyHours.setTotalhours(node.get("totalHours").doubleValue());
							driverStudyHours.setDonehours(node.get("doneHours").doubleValue());
							driverStudyHours.setCaozuoshijian(DateUtil.now());
							Dept dept = iSysClient.getDeptByName(driverStudyHours.getDeptname());
							if (dept != null) {
								driverStudyHours.setDeptid(dept.getId().toString());
								JiaShiYuan driverInfo = iJiaShiYuanService.getjiaShiYuanByOne(driverStudyHours.getDeptid(), null, null, driverStudyHours.getPid(), null);
								if (driverInfo != null) {
									driverStudyHours.setJiashiyuanid(driverInfo.getId());
									DriverStudyHours driverStudyHours1 = iDriverStudyUrlService.selectStudyHoursByIds(driverInfo.getId(),date);
									if (driverStudyHours1 == null) {
										iDriverStudyUrlService.insertStudyHoursSelective(driverStudyHours);
									} else {
										driverStudyHours.setJiashiyuanid(driverInfo.getId());
										driverStudyHours.setId(driverStudyHours1.getId());
										iDriverStudyUrlService.updateStudyHoursSelective(driverStudyHours);
									}
								}
	//							else{
	//								iDriverStudyUrlService.insertStudyHoursSelective(driverStudyHours);
	//							}
							}
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}
	}

	//每5分钟执行一次
//	@Scheduled(cron = "0 */5 * * * ?")
	//每2小时执行一次
	@Scheduled(cron = "0 * */2 * * ?")
	//每天凌晨5点执行一次
//	@Scheduled(cron = "0 0 5 * * ?")
	public void configureTasks_study() {
		synchronized (KEY) {
			if (SynchronousYAKTCrontab.taskFlag) {
				System.out.println("定时任务-执行同步赛唯教育数据已经启动" + DateUtil.now());
				log.info("定时任务-执行同步赛唯教育数据已经启动", DateUtil.now());
				return;
			}
			SynchronousYAKTCrontab.taskFlag = true;
		}
		log.warn("定时任务-执行同步赛唯教育数据更新开始", DateUtil.now());
		try {
			System.out.println("执行同步赛唯教育数据");
			if (StringUtils.isNotBlank(alarmServer.getAddressPath()) && "sw".equals(alarmServer.getAddressPath())){
				//获取最近5天的日期
//				String [] days = DateUtils.test(5).toArray(new String[0]);
//				for (int i=0;i<days.length;i++){
//					System.out.println(days[i]);
//				}
				//获取token
				String token = fillMethod();
				//获取学习地址
				addDriverStudyUrl(token);

				//获取当天的日期
				String date = LocalDate.now().toString();
				List<OrganizationsVO> organizationsVOS = iOrganizationsService.selectByDeptName();
				if(organizationsVOS.size() >0){
					organizationsVOS.forEach(item->{
						Calendar now = Calendar.getInstance();
						//获取当前年份
						int nian = now.get(Calendar.YEAR);
						//获取当前月份
						int yue = now.get(Calendar.MONTH) + 1;
						List<Dict> dictVOList = iDictClient.getDictByCode("jiapeileixing",null);
						if(dictVOList.size() > 0){
							dictVOList.forEach(dict->{
								//获取学习记录
								try {
									String nianfen = Integer.toString(nian);
									String yuefen = Integer.toString(yue);
									addDriverStudyHours(item.getJigoubianma(),nianfen,yuefen,dict.getDictValue(),date,token);
								} catch (IOException e) {
									e.printStackTrace();
								}
							});
						}
					});
				}
			}
			System.out.println("执行完成");
		} catch (Exception e) {
			log.error("定时任务-执行同步赛唯教育数据-执行出错", e.getMessage());
		}
		SynchronousYAKTCrontab.taskFlag = false;
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}
}

