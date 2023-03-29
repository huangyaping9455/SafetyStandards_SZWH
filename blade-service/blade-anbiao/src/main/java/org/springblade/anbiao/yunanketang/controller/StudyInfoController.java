package org.springblade.anbiao.yunanketang.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.qiyeshouye.entity.DriverStudyHours;
import org.springblade.anbiao.qiyeshouye.entity.DriverStudyUrl;
import org.springblade.anbiao.yunanketang.service.IDriverStudyUrlService;
import org.springblade.common.tool.JSONUtils;
import org.springblade.common.tool.StringUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Dict;
import org.springblade.system.feign.IDictClient;
import org.springblade.system.feign.ISysClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author hyp
 * @create 2023-03-22 11:13
 */

@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/yunAnKeTang/studyInfo")
@Api(value = "运安课堂教育培训", tags = "运安课堂教育培训")
public class StudyInfoController {

	private IDriverStudyUrlService iDriverStudyUrlService;

	private ISysClient iSysClient;

	private IJiaShiYuanService iJiaShiYuanService;

	private IOrganizationsService iOrganizationsService;

	private IDictClient iDictClient;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "App根据驾驶员Id获取学习地址", notes = "传入jiaShiYuanId", position = 1)
	public R<DriverStudyUrl> detail(String jiaShiYuanId) {
		R r = new R();
		DriverStudyUrl detail = iDriverStudyUrlService.selectByIds(jiaShiYuanId);
		if(detail != null){
			r.setMsg("获取成功");
			r.setData(detail);
			r.setCode(200);
		}else{
			r.setMsg("获取成功,暂无数据");
			r.setCode(200);
		}
		return r;
	}

	//获取token
	private static String fillMethod() throws IOException {
		String ss = "";
		String url = "https://app.gdjtpx.com/client/api/token";
		String token = "''";
		String requestid = "'53533bc9-d7e9-11ec-b5ab-f0d4e2e98821'";
		String signature = "''";
		String account = "'pc1075063'";
		String accesskey = "'sVsNEMIs^lyaZ&_AQ5^tFbvL6gbPBLKP'";
		String json = "{\"header\":{\"token\":"+token+",\"requestid\":"+requestid+",\"signature\":"+signature+"},\"body\":{\"account\":"+account+",\"accesskey\":"+accesskey+"}}";
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
		if("true".equals(result)){
			ss = jsonObject.getString("Data");
			System.out.println(ss);
		}
		return ss;
	}

	@GetMapping(value = "/addDriverStudyUrl")
	@ApiLog("APP-添加学习地址")
	@ApiOperation(value = "APP-添加学习地址", notes = "传入相关参数",position = 2)
	public R addDriverStudyUrl(@ApiParam(value = "身份证号", required = true) @RequestParam String pid) throws Exception{
		R r = new R();
		String token = fillMethod();
		if (token != null){
			token = "\""+token+"\"";
			String url = "https://app.gdjtpx.com/client/api/member/info";
			String requestid = "'53533bc9-d7e9-11ec-b5ab-f0d4e2e98821'";
			String signature = "''";
			pid = "\""+pid+"\"";     //"'50022519851225653x'";
			String json = "{\"header\":{\"token\":"+token+",\"requestid\":"+requestid+",\"signature\":"+signature+"},\"body\":{\"pid\":"+pid+"}}";
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
			JsonNode res = JSONUtils.string2JsonNode(content);
			System.out.println(res);
			String result = jsonObject.getString("OK");
			System.out.println(result);
			if("true".equals(result)){
				DriverStudyUrl driverStudyUrl = new DriverStudyUrl();
				String ss = jsonObject.getString("Data");
				System.out.println(ss);
				System.out.println(res.get("Data").get("ykya").get("member"));
				String ykya_remark = res.get("Data").get("ykya").get("member").toString();
				String yakt_remark = res.get("Data").get("yakt").get("member").toString();
				System.out.println(ykya_remark);
				if(ykya_remark.equals("true")){
					driverStudyUrl.setType("ykya");
					driverStudyUrl.setMember(res.get("Data").get("ykya").get("member").toString());
					driverStudyUrl.setVerified(res.get("Data").get("ykya").get("verified").toString());
					String studyUrl = res.get("Data").get("ykya").get("url").toString();
					studyUrl = studyUrl.replace("\"", "");
					driverStudyUrl.setStudyurl(studyUrl);
				}
				if(yakt_remark.equals("true")){
					driverStudyUrl.setType("yakt");
					driverStudyUrl.setMember(res.get("Data").get("yakt").get("member").toString());
					driverStudyUrl.setVerified(res.get("Data").get("yakt").get("verified").toString());
					String studyUrl = res.get("Data").get("yakt").get("url").toString();
					studyUrl = studyUrl.replace("\"", "");
					driverStudyUrl.setStudyurl(studyUrl);
				}
				if(ykya_remark.equals("true") && yakt_remark.equals("true")){
					driverStudyUrl.setType("ykya,yakt");
					driverStudyUrl.setMember("true");
					driverStudyUrl.setVerified("false");
				}
				pid = pid.replace("\"", "");
				JiaShiYuan driverInfo = iJiaShiYuanService.getjiaShiYuanByOne(null,null, null,pid,null);
				if(driverInfo != null){
					if(StringUtils.isNotBlank(driverStudyUrl.getStudyurl()) && !"null".equals(driverStudyUrl.getStudyurl())) {
						driverStudyUrl.setJiashiyuanid(driverInfo.getId());
						driverStudyUrl.setShenfenzhenghao(driverInfo.getShenfenzhenghao());
						driverStudyUrl.setDeptid(driverInfo.getDeptId().toString());
						driverStudyUrl.setCaozuoshijian(DateUtil.now());
						DriverStudyUrl driverStudyUrl1 = iDriverStudyUrlService.selectByIds(driverInfo.getId());
						if (driverStudyUrl1 == null) {
							boolean i = iDriverStudyUrlService.insertSelective(driverStudyUrl);
							if (i) {
								r.setCode(200);
								r.setSuccess(true);
								r.setMsg("添加信息成功");
							} else {
								r.setCode(500);
								r.setSuccess(false);
								r.setMsg("添加信息失败");
							}
						} else {
							driverStudyUrl.setId(driverStudyUrl1.getId());
							boolean i = iDriverStudyUrlService.updateSelective(driverStudyUrl);
							if (i) {
								r.setCode(200);
								r.setSuccess(true);
								r.setMsg("更新信息成功");
							} else {
								r.setCode(500);
								r.setSuccess(false);
								r.setMsg("更新信息失败");
							}
						}
					}
				}
			}
		}
		return r;
	}

	@GetMapping(value = "/addDriverStudyHours")
	@ApiLog("企业-获取学习记录")
	@ApiOperation(value = "企业-获取学习记录", notes = "传入相关参数",position = 3)
	public R addDriverStudyHours(@ApiParam(value = "统计日期", required = true) @RequestParam String date) throws Exception{
		R r = new R();
		//获取当天的日期
//		String date = LocalDate.now().toString();
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
							String token = fillMethod();
							if (token != null){
								token = "\""+token+"\"";
								String url = "https://app.gdjtpx.com/client/api/ticket/info";
								String requestid = "\"53533bc9-d7e9-11ec-b5ab-f0d4e2e98821\"";
								String signature = "''";
								String code = "\""+item.getJigoubianma()+"\"";     //"'A1043977'";
								String year = "\""+nianfen+"\"";
								String month = "\""+yuefen+"\"";
								String level = "\""+dict.getDictValue()+"\"";
								String json = "{\"header\":{\"token\":"+token+",\"requestid\":"+requestid+",\"signature\":"+signature+"},\"body\":{\"code\":"+code+",\"year\":"+year+",\"month\":"+month+",\"level\":"+level+"}}";
								System.out.println(json);
								HttpClient httpClient = new DefaultHttpClient();
								HttpPost post = new HttpPost(url);
								// json传递
								StringEntity postingString = new StringEntity(json,"UTF-8");
								post.setEntity(postingString);
								//		post.setHeader("Content-type", "application/json");
								HttpResponse response = httpClient.execute(post);
								String content = EntityUtils.toString(response.getEntity());
								System.out.println(content);
								JSONObject jsonObject = JSONObject.parseObject(content);
								System.out.println(jsonObject);
								JsonNode res = JSONUtils.string2JsonNode(content);
								System.out.println(res);
								String result = jsonObject.getString("OK");
								System.out.println(result);
								if("true".equals(result)){
									DriverStudyHours driverStudyHours = new DriverStudyHours();
									String ss = jsonObject.getString("Data");
									System.out.println(ss);
									JsonNode lists = res.get("Data");
									Iterator<JsonNode> elements = lists.elements();
									while(elements.hasNext()) {
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
									}
									driverStudyHours.setCaozuoshijian(DateUtil.now());
									Dept dept = iSysClient.getDeptByName(driverStudyHours.getDeptname());
									if (dept != null) {
										driverStudyHours.setDeptid(dept.getId().toString());
										JiaShiYuan driverInfo = iJiaShiYuanService.getjiaShiYuanByOne(driverStudyHours.getDeptid(), null, null, driverStudyHours.getPid(), null);
										if (driverInfo != null) {
											driverStudyHours.setJiashiyuanid(driverInfo.getId());
											DriverStudyHours driverStudyHours1 = iDriverStudyUrlService.selectStudyHoursByIds(driverInfo.getId(),date);
											if (driverStudyHours1 == null) {
												boolean i = iDriverStudyUrlService.insertStudyHoursSelective(driverStudyHours);
												if (i) {
													r.setCode(200);
													r.setSuccess(true);
													r.setMsg("添加信息成功");
												} else {
													r.setCode(500);
													r.setSuccess(false);
													r.setMsg("添加信息失败");
												}
											} else {
												driverStudyHours.setJiashiyuanid(driverInfo.getId());
												driverStudyHours.setId(driverStudyHours1.getId());
												boolean i = iDriverStudyUrlService.updateStudyHoursSelective(driverStudyHours);
												if (i) {
													r.setCode(200);
													r.setSuccess(true);
													r.setMsg("更新信息成功");
												} else {
													r.setCode(500);
													r.setSuccess(false);
													r.setMsg("更新信息失败");
												}
											}
										}
									}
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}
			});
		}
		return r;
	}

//	public static void main(String[] args) throws IOException, ParseException {
//		String date = LocalDate.now().toString();
//		System.out.println(date);
//
////		String url = "https://app.gdjtpx.com/client/api/token";
////		String token = "''";
////		String requestid = "'53533bc9-d7e9-11ec-b5ab-f0d4e2e98821'";
////		String signature = "''";
////		String account = "'pc1075063'";
////		String accesskey = "'sVsNEMIs^lyaZ&_AQ5^tFbvL6gbPBLKP'";
////		String json = "{\"header\":{\"token\":"+token+",\"requestid\":"+requestid+",\"signature\":"+signature+"},\"body\":{\"account\":"+account+",\"accesskey\":"+accesskey+"}}";
////		System.out.println(json);
////		HttpClient httpClient = new DefaultHttpClient();
////		HttpPost post = new HttpPost(url);
////		// json传递
////		StringEntity postingString = new StringEntity(json);
////		post.setEntity(postingString);
//////		post.setHeader("Content-type", "application/json");
////		HttpResponse response = httpClient.execute(post);
////		String content = EntityUtils.toString(response.getEntity());
////		System.out.println(content);
////		JSONObject jsonObject = JSONObject.parseObject(content);
////		System.out.println(jsonObject);
////		String result = jsonObject.getString("OK");
////		System.out.println(result);
////		if("true".equals(result)){
////			System.out.println(result);
////		}
//
////		String token = fillMethod();
////		token =  "\""+token+"\"";
////		String url = "https://app.gdjtpx.com/client/api/member/info";
////		String requestid = "'53533bc9-d7e9-11ec-b5ab-f0d4e2e98821'";
////		String signature = "''";
////		String pid = "'50022519851225653x'";
////		String json = "{\"header\":{\"token\":"+token+",\"requestid\":"+requestid+",\"signature\":"+signature+"},\"body\":{\"pid\":"+pid+"}}";
////		System.out.println(json);
////		HttpClient httpClient = new DefaultHttpClient();
////		HttpPost post = new HttpPost(url);
////		// json传递
////		StringEntity postingString = new StringEntity(json);
////		post.setEntity(postingString);
////		//		post.setHeader("Content-type", "application/json");
////		HttpResponse response = httpClient.execute(post);
////		String content = EntityUtils.toString(response.getEntity());
////		System.out.println(content);
////		JSONObject jsonObject = JSONObject.parseObject(content);
////		System.out.println(jsonObject);
////		JsonNode res = JSONUtils.string2JsonNode(content);
////		System.out.println(res);
////		String result = jsonObject.getString("OK");
////		System.out.println(result);
////		if("true".equals(result)){
////			DriverStudyUrl driverStudyUrl = new DriverStudyUrl();
////			String ss = jsonObject.getString("Data");
////			System.out.println(ss);
////			JsonNode ykya_lists = res.get("Data").get("ykya");
////			Iterator<JsonNode> ykya_elements = ykya_lists.elements();
////			while(ykya_elements.hasNext()) {
////				JsonNode node = ykya_elements.next();
////				if("true".equals(node.get("member"))){
////					driverStudyUrl.setType("ykya");
////					driverStudyUrl.setMember(node.get("member").toString());
////					driverStudyUrl.setVerified(node.get("verified").toString());
////				}
////			}
////			JsonNode yakt_lists = res.get("Data").get("yakt");
////			Iterator<JsonNode> yakt_elements = yakt_lists.elements();
////			while(yakt_elements.hasNext()) {
////				JsonNode node = yakt_elements.next();
////				if("true".equals(node.get("member"))){
////					driverStudyUrl.setType("yakt");
////					driverStudyUrl.setMember(node.get("member").toString());
////					driverStudyUrl.setVerified(node.get("verified").toString());
////				}
////			}
////			String studyUrl = jsonObject.getString("url");
////			driverStudyUrl.setStudyurl(studyUrl);
////			JiaShiYuan driverInfo = iJiaShiYuanService.getjiaShiYuanByOne(null,null, null,pid,null);
////		}
//
//	}


	}
