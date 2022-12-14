/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.train.controller;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springblade.common.configurationBean.TrainServer;
import org.springblade.common.tool.DeleteFileUtils;
import org.springblade.common.tool.PostUtil;
import org.springblade.common.tool.StringUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springblade.train.config.JSONUtils;
import org.springblade.train.entity.*;
import org.springblade.train.page.ScholarEducationPage;
import org.springblade.train.page.UnitStatisticsPage;
import org.springblade.train.service.*;
import org.springblade.train.tool.VideoConvertUtil;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/train/scholarEducation")
@Api(value = "教育培训--学习统计分析", tags = "教育培训--学习统计分析")
public class ScholarEducationController extends BladeController {

	private IScholarEducationService scholarEducationService;

	private ITrainService trainService;

	private IStudyRecordAnalysisService studyRecordAnalysisService;

	private ITradeKindService tradeKindService;

	private IUnitStatisticsService unitStatisticsService;

	private TrainServer trainServer;

	private IWaitCompletedService iWaitCompletedService;

	/**
	 * 学习统计分析--查询学员学历分析数据
	 * @param scholarEducationPage
	 * @return
	 */
	@PostMapping("/getScholarEducationList")
	@ApiOperation(value = "学习统计分析--查询学员学历分析数据", notes = "学习统计分析--查询学员学历分析数据", position = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "scholarEducationPage", value = "数据对象", required = true)
	})
	public R<List<ScholarEducationModel>> getScholarEducationList(@RequestBody ScholarEducationPage scholarEducationPage) {
		R rs = new R();
		//根据企业名称查询教育平台是否包含该企业
		Unit unit = trainService.getUnitByName(scholarEducationPage.getUnitName());
		if(unit == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前所属单位未在教育系统中");
			return rs;
		}else{
			scholarEducationPage.setUnitId(unit.getId().toString());
		}
		ScholarEducationPage resultList = scholarEducationService.getScholarEducationList(scholarEducationPage);
		if(resultList != null){
			rs.setData(resultList);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("学习统计分析--获取学员学历分析数据成功");
		}else{
			rs.setData(null);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("学习统计分析--获取学员学历分析数据成功,暂无数据");
		}
		return rs;
	}

	/**
	 * 学习统计分析--查询学员结业证明
	 * @param json
	 * @return
	 */
	@PostMapping("/getCertificateOfCompletiont")
	@ApiOperation(value = "学习统计分析--查询学员结业证明", notes = "学习统计分析--查询学员结业证明", position = 2)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "json", value = "数据对象", required = true)
	})
	public R getCertificateOfCompletiont(@RequestBody String json) {
		R rs = new R();
		JsonNode node = JSONUtils.string2JsonNode(json);
		Integer studentId;
		String courseIds;
		List<Integer> courseIdList;
		if (node.hasNonNull("studentId") && node.hasNonNull("courseIds") && node.hasNonNull("unitName")) {
			studentId = node.get("studentId").asInt();
			courseIds = node.get("courseIds").asText();
			courseIdList = new ArrayList<Integer>();
			if(courseIds != null && !"".equals(courseIds)) {
				String str[] = courseIds.split(",");
				for (String string : str) {
					courseIdList.add(Integer.valueOf(string));
				}
			}
		} else {
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("参数错误,参数不全");
			return rs;
		}
		CertificateModel certificateModel = scholarEducationService.getCertificateOfCompletiont(studentId,courseIdList);
		if(certificateModel != null){
			rs.setData(certificateModel);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("学习统计分析--获取学员结业证明数据成功");
		}else{
			rs.setData(null);
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("学习统计分析--获取学员结业证明数据失败");
		}
		return rs;
	}

	/**
	 * 学习统计分析-每日学习时长查询
	 * @param json
	 * @return
	 */
	@PostMapping("/getStudyRecordAnalysisList")
	@ApiOperation(value = "学习记录分析-每日学习时长查询", notes = "学习记录分析-每日学习时长查询", position = 3)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", value = "日期类型（1=天 ,2=周,3=月,4=年）", required = true),
		@ApiImplicitParam(name = "startTime", value = "开始时间", required = true),
		@ApiImplicitParam(name = "endTime", value = "结束时间", required = true),
		@ApiImplicitParam(name = "unitName", value = "企业名称", required = true),
		@ApiImplicitParam(name = "courseId", value = "课程Id", required = false)
	})
	public R getStudyRecordAnalysisList(@RequestBody String json) {
		R rs  = new R();
		try {
			JsonNode node = JSONUtils.string2JsonNode(json);
			String unitName = node.get("unitName").asText();
			Unit unit = trainService.getUnitByName(unitName);
			if(unit == null){
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("当前所属单位未在教育系统中");
				return rs;
			}
			//&& node.hasNonNull("courseId")
			if (node.hasNonNull("type") && unit != null && unit.getId() != null
					&& node.hasNonNull("startTime") && node.hasNonNull("endTime")) {
				HashMap<String, String> param = new HashMap<String, String>();
				// 查询类型 1=天 ,2=周,3=月,4=年
				param.put("type", node.get("type").asText());
				// 课程Id
				param.put("courseId", node.get("courseId").asText());
				// 开始时间
				param.put("startTime", node.get("startTime").asText());
				// 结束时间
				param.put("endTime", node.get("endTime").asText());
				// 企业Id
				param.put("unitId", unit.getId().toString());

				List<HashMap<String, Object>> list = studyRecordAnalysisService.getStudyRecordAnalysis(param);
				if(list != null){
					rs.setSuccess(true);
					rs.setCode(200);
					rs.setMsg("学习统计分析-每日学习时长查询成功");
					rs.setData(list);
				}else{
					rs.setSuccess(true);
					rs.setCode(200);
					rs.setMsg("学习统计分析-每日学习时长查询成功,暂无数据");
					rs.setData("");
				}
			} else {
				rs.setSuccess(false);
				rs.setCode(500);
				rs.setMsg("参数错误，参数不全");
				rs.setData("");
			}
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setCode(500);
			rs.setMsg("学习统计分析-每日学习时长查询异常");
			rs.setData("");
		}
		return rs;
	}

	/**
	 * 学习统计分析-小时分布图查询
	 * @param json
	 * @return
	 */
	@PostMapping("/getStudyRecordAnalysisHour")
	@ApiOperation(value = "学习统计分析-小时分布图查询", notes = "学习统计分析-小时分布图查询", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", value = "日期类型（1=天 ,2=周,3=月,4=年）", required = true),
		@ApiImplicitParam(name = "startTime", value = "开始时间", required = true),
		@ApiImplicitParam(name = "endTime", value = "结束时间", required = true),
		@ApiImplicitParam(name = "unitName", value = "企业名称", required = true),
		@ApiImplicitParam(name = "courseId", value = "课程Id", required = false)
	})
	public R getStudyRecordAnalysisHour(@RequestBody String json) {
		R rs  = new R();
		try {
			JsonNode node = JSONUtils.string2JsonNode(json);
			String unitName = node.get("unitName").asText();
			Unit unit = trainService.getUnitByName(unitName);
			if(unit == null){
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("当前所属单位未在教育系统中");
				return rs;
			}
			//&& node.hasNonNull("courseId")
			if (node.hasNonNull("type") && unit != null && unit.getId() != null
					&& node.hasNonNull("startTime") && node.hasNonNull("endTime")) {
				HashMap<String, String> param = new HashMap<String, String>();
				// 查询类型 1=天 ,2=周,3=月,4=年
				param.put("type", node.get("type").asText());
				// 课程Id
				param.put("courseId", node.get("courseId").asText());
				// 开始时间
				param.put("startTime", node.get("startTime").asText());
				// 结束时间
				param.put("endTime", node.get("endTime").asText());
				// 企业Id
				param.put("unitId", unit.getId().toString());

				StudyStatHoursModel result = studyRecordAnalysisService.getStudyRecordAnalysisHour(param);
				if(result != null){
					rs.setSuccess(true);
					rs.setCode(200);
					rs.setMsg("学习统计分析-小时学习时长查询成功");
					rs.setData(result);
				}else{
					rs.setSuccess(false);
					rs.setCode(200);
					rs.setMsg("学习统计分析-小时学习时长查询成功,暂无数据");
					rs.setData("");
				}
			} else {
				rs.setSuccess(false);
				rs.setCode(500);
				rs.setMsg("参数错误，参数不全");
				rs.setData("");
			}
		}catch (Exception e) {
			rs.setSuccess(false);
			rs.setCode(500);
			rs.setMsg("学习统计分析-小时学习时长查询异常");
			rs.setData("");
		}
		return rs;
	}

	/**
	 * 学习统计分析-获取课程
	 * @return
	 */
	@GetMapping("/getCourseSelectList")
	@ApiOperation(value = "学习统计分析-获取课程", notes = "学习统计分析-获取课程", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "课程名称", required = false),
		@ApiImplicitParam(name = "unitName", value = "企业名称", required = true),
		@ApiImplicitParam(name = "courseKindId", value = "课程类型ID"),
		@ApiImplicitParam(name = "isOp", value = "是否自有课程",required = false),
		@ApiImplicitParam(name = "isUse", value = "是否有考题", required = false),
		@ApiImplicitParam(name = "serverId", value = "所属服务商 默认0，无服务商", required = false),
		@ApiImplicitParam(name = "type", value = "类型 营运商-0，政府-1，代理商-2，企业-3", required = false),
		@ApiImplicitParam(name = "governmentId", value = "所属政府Id", required = false)
	})
	public R getCourseSelectList(String name, String unitName, Integer courseKindId, Integer isOp, Integer isUse, Integer serverId, Integer type, Integer governmentId) {
		R rs  = new R();
		try {
			HashMap<String, Object> map = new HashMap<>();
			map.put("name", name);
			Unit unit = trainService.getUnitByName(unitName);
			if(unit == null){
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("当前所属单位未在教育系统中");
				return rs;
			}
			if (unit != null && unit.getId() != null) {
				map.put("unit_id", unit.getId());
			}
			if (type != null) {
				map.put("type", type);
			}
			if (courseKindId != null) {
				map.put("course_kind_id", courseKindId);
			}
			if (isOp != null) {
				if (isOp == 0) {
					map.put("notOperation", 1);
				} else {
					map.put("Operation", 1);
				}
			}
			if (serverId != null) {
				map.put("server_id", serverId);
			}
			if (isUse != null) {
				if (isUse == 0) {
					map.put("notHave", 1);
				} else {
					map.put("have", 1);
				}
			}
			if (null != governmentId) {
				map.put("government_id", governmentId);
			}
			List<CourseSelectModel> courseList = studyRecordAnalysisService.getCourseSelectList(map);
			if(courseList != null){
				rs.setSuccess(true);
				rs.setCode(200);
				rs.setMsg("学习统计分析-课程下拉框查询成功");
				rs.setData(courseList);
			}else{
				rs.setSuccess(true);
				rs.setCode(200);
				rs.setMsg("学习统计分析-课程下拉框查询成功,暂无数据");
				rs.setData("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setSuccess(false);
			rs.setCode(500);
			rs.setMsg("学习统计分析-课程下拉框查询失败");
			rs.setData("");
		}
		return rs;
	}

	/**
	 * 学习统计分析--查询行业类型
	 * @return
	 */
	@GetMapping("/getTradeKindList")
	@ApiOperation(value = "学习统计分析--查询行业类型", notes = "学习统计分析--查询行业类型", position = 6)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "行业名称", required = false)
	})
	public R<List<ScholarEducationModel>> getTradeKindList(String name) {
		R rs = new R();
		try {
			// 行业名称
			List<TradeKind> list = tradeKindService.selectTradeKindList(name);
			if(list != null){
				rs.setSuccess(true);
				rs.setCode(200);
				rs.setMsg("学习统计分析-行业类型查询成功");
				rs.setData(list);
			}else{
				rs.setSuccess(true);
				rs.setCode(200);
				rs.setMsg("学习统计分析-行业类型查询成功,暂无数据");
				rs.setData("");
			}
		} catch (Exception e) {
			rs.setSuccess(true);
			rs.setCode(500);
			rs.setMsg("学习统计分析-行业类型查询失败");
			rs.setData("");
		}
		return rs;
	}

	/**
	 * 学习统计分析--企业统计分析数量统计
	 * @return
	 */
	@GetMapping("/getsummaryStatsCount")
	@ApiOperation(value = "学习统计分析--企业统计分析数量统计", notes = "学习统计分析--企业统计分析数量统计", position = 7)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "unitName", value = "企业名称", required = true)
	})
	public R<List<ScholarEducationModel>> getsummaryStatsCount(String unitName) {
		R rs = new R();
		String unitId;
		//根据企业名称查询教育平台是否包含该企业
		Unit unit = trainService.getUnitByName(unitName);
		if(unit == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前所属单位未在教育系统中");
			return rs;
		}else{
			unitId =unit.getId().toString();
		}
		// 行业名称
		UnitStatisticsSummaryModel unitStatisticsSummaryModel = unitStatisticsService.summaryStats(unitId);
		if(unitStatisticsSummaryModel != null){
			rs.setSuccess(true);
			rs.setCode(200);
			rs.setMsg("学习统计分析-企业统计分析数量统计成功");
			rs.setData(unitStatisticsSummaryModel);
		}else{
			rs.setSuccess(true);
			rs.setCode(200);
			rs.setMsg("学习统计分析-企业统计分析数量统计成功,暂无数据");
			rs.setData("");
		}
		return rs;
	}

	/**
	 * 学习统计分析--查询企业统计分析数据
	 * @param unitStatisticsPage
	 * @return
	 */
	@PostMapping("/getUnitStatisticsList")
	@ApiOperation(value = "学习统计分析--查询企业统计分析数据", notes = "学习统计分析--查询企业统计分析数据", position = 8)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "unitStatisticsPage", value = "数据对象", required = true)
	})
	public R<List<UnitStatisticsListModel>> getUnitStatisticsList(@RequestBody UnitStatisticsPage unitStatisticsPage) {
		R rs = new R();
		//根据企业名称查询教育平台是否包含该企业
		Unit unit = trainService.getUnitByName(unitStatisticsPage.getUnitName());
		if(unit == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前所属单位未在教育系统中");
			return rs;
		}else{
			unitStatisticsPage.setUnitId(unit.getId());
		}
		try {
			UnitStatisticsPage resultList = unitStatisticsService.getUnitStatisticsList(unitStatisticsPage);
			if(resultList != null){
				rs.setData(resultList);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("学习统计分析--获取企业统计分析数据成功");
			}else{
				rs.setData("");
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("学习统计分析--获取企业统计分析数据成功,暂无数据");
			}
		}catch (Exception e){
			rs.setData("");
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("学习统计分析--获取企业统计分析数据失败");
		}
		return rs;
	}

	/**
	 * 学习统计分析--查询企业统计分析数据
	 * @param unitStatisticsPage
	 * @return
	 */
	@PostMapping("/getUnitStatisticsDetailList")
	@ApiOperation(value = "学习统计分析--查询企业统计分析详情列表", notes = "学习统计分析--查询企业统计分析详情列表", position = 8)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "unitStatisticsPage", value = "数据对象", required = true)
	})
	public R<List<UnitStatisticsDetailListModel>> getUnitStatisticsDetailList(@RequestBody UnitStatisticsPage unitStatisticsPage) {
		R rs = new R();
		//根据企业名称查询教育平台是否包含该企业
		Unit unit = trainService.getUnitByName(unitStatisticsPage.getUnitName());
		if(unit == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前所属单位未在教育系统中");
			return rs;
		}else{
			unitStatisticsPage.setUnitId(unit.getId());
		}
		try {
			UnitStatisticsPage resultList = unitStatisticsService.getUnitStatisticsDetailList(unitStatisticsPage);
			if(resultList != null){
				rs.setData(resultList);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("学习统计分析--获取企业统计分析详情列表数据成功");
			}else{
				rs.setData("");
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("学习统计分析--获取企业统计分析详情列表数据成功,暂无数据");
			}
		}catch (Exception e){
			rs.setData("");
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("学习统计分析--获取企业统计分析详情列表数据失败");
		}
		return rs;
	}

	/**
	 * 视频转码
	 * @return
	 */
	@GetMapping("/executeVideoTranscode")
	@ApiOperation(value = "视频转码", notes = "视频转码", position = 9)
	public R executeVideoTranscode(String result) {
		R rs = new R();
		if(StringUtils.isBlank(result)){
			result = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
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
//					String videoSavePath = "C:\\Users\\Administrator\\Desktop\\教育视频\\Desktop\\transcoding";

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
						log.info("结束时间："+ DateUtil.now());
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
						JsonNode res = org.springblade.common.tool.JSONUtils.string2JsonNode(responseJson);
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
									System.out.println(fileUrl_result);
									rs.setMsg("转码成功");
									rs.setSuccess(true);
									rs.setCode(500);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return rs;
	}


}
