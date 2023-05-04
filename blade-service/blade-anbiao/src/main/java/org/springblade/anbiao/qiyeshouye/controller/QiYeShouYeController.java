/**
 * Copyright (C), 2015-2020,
 * FileName: GpsVehicleController
 * Author:   呵呵哒
 * Date:     2020/7/3 9:42
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDriver;
import org.springblade.anbiao.cheliangguanli.feign.IVehiclepostClientBack;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanEnterprise;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanEnterpriseService;
import org.springblade.anbiao.qiyeshouye.entity.*;
import org.springblade.anbiao.qiyeshouye.page.QiYeShouYePage;
import org.springblade.anbiao.qiyeshouye.service.IQiYeShouYeService;
import org.springblade.anbiao.zhengfu.entity.Organization;
import org.springblade.anbiao.zhengfu.service.IOrganizationService;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.tool.InterfaceUtil;
import org.springblade.common.tool.PostUtil;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/7/4
 * @描述
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/qiYeShouYe")
@Api(value = "企业平台-首页", tags = "企业平台-首页")
public class QiYeShouYeController {

	private FileServer fileServer;

	private IQiYeShouYeService iQiYeShouYeService;

	private ISysClient sysClient;

	private IOrganizationService iOrganizationService;

	private IJiaShiYuanEnterpriseService iJiaShiYuanEnterpriseService;

	private IVehiclepostClientBack vehiclepostClientBack;

	@GetMapping(value = "/getMonthVehcile")
	@ApiLog("企业平台-本月车辆情况")
	@ApiOperation(value = "企业平台-本月车辆情况",position = 1)
	public R getMonthVehcile(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId) throws IOException {
		return R.data(iQiYeShouYeService.selectMonthVehcile(deptId));
	}

	@GetMapping(value = "/getYearAlarm")
	@ApiLog("企业平台-报警统计（年）")
	@ApiOperation(value = "企业平台-报警统计（年）",position = 2)
	public R getYearAlarm(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId,@ApiParam(value = "年份", required = true) @RequestParam String year) throws IOException {
		return R.data(iQiYeShouYeService.selectYearAlarm(deptId,year));
	}

	@GetMapping(value = "/getYearAlarmTendency")
	@ApiLog("企业平台-处理趋势图")
	@ApiOperation(value = "企业平台-处理趋势图",position = 3)
	public R getYearAlarmTendency(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId,@ApiParam(value = "年份", required = true) @RequestParam String year) throws IOException {
		return R.data(iQiYeShouYeService.selectYearAlarmTendency(deptId,year));
	}

	@GetMapping(value = "/getSevenAlarmStatistics")
	@ApiLog("企业平台-近七天报警统计")
	@ApiOperation(value = "企业平台-近七天报警统计",position = 4)
	public R getSevenAlarmStatistics(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId) throws IOException {
		return R.data(iQiYeShouYeService.selectSevenAlarmStatistics(deptId));
	}

	@GetMapping(value = "/selectOperational")
	@ApiLog("运维端首页-各类型数据统计(顶部)")
	@ApiOperation(value = "运维端首页-各类型数据统计(顶部)",position = 5)
	public R selectOperational(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId) throws IOException {
		return R.data(iQiYeShouYeService.selectOperational(deptId));
	}

	@GetMapping(value = "/selectMarkRemind")
	@ApiLog("企业端-首页-安全达标提醒分数")
	@ApiOperation(value = "企业端-首页-安全达标提醒分数",position = 6)
	public R selectMarkRemind(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId) throws IOException {
		R r = new R();
		QiYeYunWeiShouYe q = iQiYeShouYeService.selectMarkRemind(deptId);
		if(q == null){
			r.setData("未开通");
			r.setCode(200);
			r.setMsg("暂无数据");
		}else{
			if(q.getTotalpoints() >= fileServer.getMarkRemindScore()){
				q.setTotalpointsremark("已达标");
			}else{
				q.setTotalpointsremark("未达标");
			}
			String date = DateUtil.format(new Date(), "yyyy-MM-dd");
			q.setTotalpointstime(date);
			r.setData(q);
			r.setMsg("获取成功");
			r.setSuccess(true);
			r.setCode(200);
		}
		return r;
	}

	@GetMapping(value = "/selectControlRates")
	@ApiLog("企业端-安全达标(超过88%的平台企业)")
	@ApiOperation(value = "企业端-安全达标(超过88%的平台企业)",position = 7)
	public R selectControlRates(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId) throws IOException {
		R r = new R();
		QiYeYunWeiShouYe qiYeYunWeiShouYe = new QiYeYunWeiShouYe();
		int sss = iQiYeShouYeService.selectGetByIdOnDeptId(deptId);
		if(sss > 0 ) {
			QiYeYunWeiShouYe q = iQiYeShouYeService.selectQiYeCount();
			QiYeYunWeiShouYe w = iQiYeShouYeService.selectABQiYeCount();
			QiYeYunWeiShouYe e = iQiYeShouYeService.selectMarkRemind(deptId);
			QiYeYunWeiShouYe qiYreYunWeiShouYe = new QiYeYunWeiShouYe();
			if (e == null) {
				r.setMsg("暂无数据");
				r.setCode(200);
			} else {
//			String.format("%.2f", x1)
				String Rates = String.format("%.2f", (((q.getQiyeshu() - w.getAnbiaoqiyeshu()) * 1.0) / q.getQiyeshu()) * 100) + "%";
				qiYreYunWeiShouYe.setTotalpointsrate(Rates);
				qiYreYunWeiShouYe.setTotalpoints(e.getTotalpoints());
				qiYreYunWeiShouYe.setTotalscore(1000);
				r.setData(qiYreYunWeiShouYe);
				r.setMsg("获取成功");
				r.setSuccess(true);
				r.setCode(200);
			}
		}else{
			qiYeYunWeiShouYe.setTotalpointsrate("0.00%");
			qiYeYunWeiShouYe.setTotalpoints(0);
			qiYeYunWeiShouYe.setTotalscore(1000);
			r.setMsg("该企业未生成安全标准化文件");
			r.setData(qiYeYunWeiShouYe);
			r.setCode(200);
		}
		return r;
	}

	@GetMapping(value = "/selectScheduleReminders")
	@ApiLog("企业端-安全达标-日历待办提醒")
	@ApiOperation(value = "企业端-安全达标-日历待办提醒)",position = 8)
	public R selectScheduleReminders(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId,
								@ApiParam(value = "日期", required = true) @RequestParam String dateTime) throws IOException {
		R r = new R();
		List<QiYeAnBiao> anBiaoList = iQiYeShouYeService.selectScheduleReminders(deptId,dateTime);
		if(anBiaoList == null){
			r.setMsg("暂无数据");
			r.setCode(200);
		}else{
			r.setData(anBiaoList);
			r.setMsg("获取成功");
			r.setSuccess(true);
			r.setCode(200);
		}
		return r;
	}

	@GetMapping(value = "/selectQiYeAnBiaoMuLu")
	@ApiLog("企业端-安全达标-安标目录得分对比表")
	@ApiOperation(value = "企业端-安全达标-安标目录得分对比表)",position = 9)
	public R selectQiYeAnBiaoMuLu(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId) throws IOException {
		R r = new R();
		List<QiYeAnBiaoMuLu> anBiaoMuLuList = iQiYeShouYeService.selectQiYeAnBiaoMuLu(deptId);
		if(anBiaoMuLuList == null){
			r.setMsg("暂无数据");
			r.setCode(200);
		}else{
			r.setData(anBiaoMuLuList);
			r.setMsg("获取成功");
			r.setSuccess(true);
			r.setCode(200);
		}
		return r;
	}

	@GetMapping(value = "/selectPeriodControlRates")
	@ApiLog("企业端-安全达标-安标周期评分达标率")
	@ApiOperation(value = "企业端-安全达标-安标周期评分达标率)",position = 10)
	public R selectPeriodControlRates(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId) throws IOException {
		R r = new R();
		QiYeAnBiaoPeriodRate qiYeAnBiaoPeriodRate = new QiYeAnBiaoPeriodRate();
		int sss = iQiYeShouYeService.selectGetByIdOnDeptId(deptId);
		if(sss > 0 ){
			QiYeAnBiaoPeriodRate anBiaoMuLuList = iQiYeShouYeService.selectPeriodControlRates(deptId);
			if(anBiaoMuLuList == null){
				r.setMsg("暂无数据");
				r.setCode(200);
			}else{
				r.setData(anBiaoMuLuList);
				r.setMsg("获取成功");
				r.setSuccess(true);
				r.setCode(200);
			}
		}else{
			qiYeAnBiaoPeriodRate.setMaxxiangshu(0);
			qiYeAnBiaoPeriodRate.setMaxdabiaoxiangshu(0);
			qiYeAnBiaoPeriodRate.setMindabiaoxiangshu(0);
			qiYeAnBiaoPeriodRate.setMinxiangshu(0);
			qiYeAnBiaoPeriodRate.setDabiaolv("0.00%");
			r.setMsg("该企业未生成安全标准化文件");
			r.setData(qiYeAnBiaoPeriodRate);
			r.setCode(200);
		}
		return r;
	}

	@GetMapping(value = "/selectSafetyTips")
	@ApiLog("企业端-安全达标-安全提示")
	@ApiOperation(value = "企业端-安全达标-安全提示)",position = 11)
	public R selectSafetyTips(@ApiParam(value = "企业ID", required = true) @RequestParam String deptId) throws IOException {
		R r = new R();
		List<QiYeAnBiaoSafetyTips> safetyTips = new ArrayList<>();
		List<QiYeAnBiaoSafetyTips> qiYeAnBiaoSafetyTips = iQiYeShouYeService.selectSafetyTips(deptId);
		if(qiYeAnBiaoSafetyTips == null){
			r.setMsg("暂无数据");
			r.setCode(200);
		}else{
			for (int i = 0;i<qiYeAnBiaoSafetyTips.size();i++){
				QiYeAnBiaoSafetyTips as = new QiYeAnBiaoSafetyTips();
				QiYeAnBiaoSafetyTips znum = iQiYeShouYeService.selectSafetyTipsZNum(deptId,qiYeAnBiaoSafetyTips.get(i).getIds());
				QiYeAnBiaoSafetyTips num = iQiYeShouYeService.selectSafetyTipsNum(deptId,qiYeAnBiaoSafetyTips.get(i).getIds());
				String name = qiYeAnBiaoSafetyTips.get(i).getName();
				as.setName(name);
				as.setTier(num.getTier());
				as.setMinxingnum(znum.getMinxingnum());
				as.setNotdabiaonum(num.getNotdabiaonum());
				as.setIds(qiYeAnBiaoSafetyTips.get(i).getIds());
				as.setDeptId(Integer.parseInt(deptId));
				safetyTips.add(i,as);
			}
			r.setData(safetyTips);
			r.setMsg("获取成功");
			r.setSuccess(true);
			r.setCode(200);
		}
		return r;
	}

	@GetMapping(value = "/getLearnRecord")
	@ApiLog("企业端-教育数据获取入库（云享）")
	@ApiOperation(value = "企业端-教育数据获取入库（云享）)",position = 14)
	public R getLearnRecord(
			@ApiParam(value = "企业名称", required = false) @RequestParam String company,
			 @ApiParam(value = "学习平台", required = false) @RequestParam String luser,
			 @ApiParam(value = "学习月份", required = true) @RequestParam int lmonth,
			 @ApiParam(value = "学习年份", required = true) @RequestParam int lyear
	) throws IOException {
		R rs = new R();
		String url = fileServer.getLearnRecordUrl();
		JSONObject parm2 = new JSONObject();
		parm2.put("company",company);
		parm2.put("luser",luser);
		parm2.put("lmonth",lmonth);
		parm2.put("lyear",lyear);
		String jsonstr = PostUtil.postNewMsg(url,parm2.toString());
		System.out.println(jsonstr);
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonstr);
		System.out.println(jsonObject.get("data"));
		JSONArray jsonArray = null;
		jsonArray = jsonObject.getJSONArray("data");//获取数组
		System.out.println(jsonArray);
		if(jsonArray.size() > 0){
			for(int i = 0;i<jsonArray.size();i++){
				com.alibaba.fastjson.JSONObject jsonObject1 = jsonArray.getJSONObject(i);
				PersonLearnInfo personLearnInfo = new PersonLearnInfo();
				personLearnInfo.setDeptname(jsonObject1.getString("company"));
				Dept dept = sysClient.getDeptByName(personLearnInfo.getDeptname());
				if(dept != null){
					personLearnInfo.setDeptid(dept.getId());
				}
				personLearnInfo.setLearntime(jsonObject1.getString("learningTime"));
				personLearnInfo.setLyear(Integer.parseInt(jsonObject1.getString("lyear")));
				personLearnInfo.setLmonth(Integer.parseInt(jsonObject1.getString("lmonth")));
				personLearnInfo.setLuser(jsonObject1.getString("luser"));
				personLearnInfo.setUserphone(jsonObject1.getString("phone"));
				personLearnInfo.setUsername(jsonObject1.getString("empName"));
				personLearnInfo.setStatus(jsonObject1.getString("completeStatus"));
				personLearnInfo.setUsercard(jsonObject1.getString("idNumber"));
				personLearnInfo.setLearnStudy("线上学习");
				personLearnInfo.setUpdatetime(DateUtil.now());
				//查询是否存在相同记录
				PersonLearnInfo ps = iQiYeShouYeService.getByName(personLearnInfo.getLyear(),personLearnInfo.getLmonth(),personLearnInfo.getUsername(),personLearnInfo.getDeptname());
				if(ps != null){
					iQiYeShouYeService.updateSelective(personLearnInfo);
				}else{
					iQiYeShouYeService.insertSelective(personLearnInfo);
				}
			}
		}
		rs.setCode(200);
		rs.setData(jsonstr);
		return rs;
	}

	@GetMapping(value = "/getLearnRecord_CShou")
	@ApiLog("企业端-教育数据获取入库（长寿）")
	@ApiOperation(value = "企业端-教育数据获取入库（长寿）)",position = 15)
	public R getLearnRecord_CShou(
		@ApiParam(value = "签名", required = true) @RequestParam String sign,
		@ApiParam(value = "时间戳", required = true) @RequestParam String timestamp,
		@ApiParam(value = "学习开始月份 YYYY-MM", required = false) @RequestParam String beginMonth,
		@ApiParam(value = "学习结束月份 YYYY-MM", required = false) @RequestParam String endMonth
	) throws IOException {
		R rs = new R();
		String url = fileServer.getLearnRecordUrl();
		JSONObject parm2 = new JSONObject();
		parm2.put("sign",sign);
		parm2.put("timestamp",timestamp);
		parm2.put("beginMonth",beginMonth);
		parm2.put("endMonth",endMonth);
		String jsonstr = PostUtil.postNewMsg(url,parm2.toString());
		System.out.println(jsonstr);
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonstr);
		System.out.println(jsonObject.get("rows"));
		JSONArray jsonArray = null;
		jsonArray = jsonObject.getJSONArray("rows");//获取数组
		System.out.println(jsonArray);
		if(jsonArray.size() > 0){
			for(int i = 0;i<jsonArray.size();i++){
				com.alibaba.fastjson.JSONObject jsonObject1 = jsonArray.getJSONObject(i);
				PersonLearnInfo personLearnInfo = new PersonLearnInfo();
				personLearnInfo.setDeptname(jsonObject1.getString("orgName"));
				Dept dept = sysClient.getDeptByName(personLearnInfo.getDeptname());
				if(dept != null){
					personLearnInfo.setDeptid(dept.getId());
					personLearnInfo.setLearntime(jsonObject1.getString("chNo"));
					String studyTime = jsonObject1.getString("statYm");
					personLearnInfo.setLyear(Integer.parseInt(studyTime.substring(0, 4)));
					personLearnInfo.setLmonth(Integer.parseInt(studyTime.substring(studyTime.length() - 2)));
					personLearnInfo.setLuser("驾运宝");
//				personLearnInfo.setUserphone(jsonObject1.getString("phone"));
					personLearnInfo.setUsername(jsonObject1.getString("userName"));
					personLearnInfo.setStatus(jsonObject1.getString("finishMark"));
					personLearnInfo.setUsercard(jsonObject1.getString("idcNo"));
					personLearnInfo.setLearnStudy("线上学习");
					personLearnInfo.setUpdatetime(DateUtil.now());
					//查询是否存在相同记录
					PersonLearnInfo ps = iQiYeShouYeService.getByName(personLearnInfo.getLyear(),personLearnInfo.getLmonth(),personLearnInfo.getUsername(),personLearnInfo.getDeptname());
					if(ps != null){
						iQiYeShouYeService.updateSelective(personLearnInfo);
					}else{
						iQiYeShouYeService.insertSelective(personLearnInfo);
					}
				}
			}
		}
		rs.setCode(200);
		rs.setData(jsonstr);
		return rs;
	}

	@PostMapping(value = "/selectPersonLearnInfoAll")
	@ApiLog("企业端-获取学习记录列表")
	@ApiOperation(value = "企业端-获取学习记录列表", notes = "qiYeShouYePage",position = 20)
	public R<QiYeShouYePage<PersonLearnInfo>> selectPersonLearnInfoAll(@RequestBody QiYeShouYePage qiYeShouYePage) {
		//排序条件
		if(qiYeShouYePage.getOrderColumns()==null){
			qiYeShouYePage.setOrderColumn("username");
		}else{
			qiYeShouYePage.setOrderColumn(qiYeShouYePage.getOrderColumns());
		}
		QiYeShouYePage<PersonLearnInfo> pages = iQiYeShouYeService.selectPersonLearnInfoAll(qiYeShouYePage);
		return R.data(pages);
	}

	@PostMapping(value = "/selectZFPersonLearnInfoAll")
	@ApiLog("政府首页-获取学习记录列表")
	@ApiOperation(value = "政府首页-获取学习记录列表", notes = "传入qiYeShouYePage",position = 21)
	public R selectZFPersonLearnInfoAll(@RequestBody QiYeShouYePage qiYeShouYePage) throws IOException {
		R r = new R();
		Organization jb = iOrganizationService.selectGetZFJB(qiYeShouYePage.getDeptId());
		if(!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())){
			qiYeShouYePage.setProvince(qiYeShouYePage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
			qiYeShouYePage.setCity(qiYeShouYePage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCountry())) {
			qiYeShouYePage.setCountry(qiYeShouYePage.getDeptId());
		}

		//排序条件
		if(qiYeShouYePage.getOrderColumns()==null){
			qiYeShouYePage.setOrderColumn("username");
		}else{
			qiYeShouYePage.setOrderColumn(qiYeShouYePage.getOrderColumns());
		}

		QiYeShouYePage<PersonLearnInfo> zfpersonLearnInfo =  iQiYeShouYeService.selectZFPersonLearnInfoAll(qiYeShouYePage);
		if(zfpersonLearnInfo != null){
			r.setData(zfpersonLearnInfo);
			r.setSuccess(true);
			r.setMsg("获取成功");
		}else{
			r.setData(null);
			r.setSuccess(false);
			r.setMsg("获取失败");
		}
		return R.data(r);
	}

	@PostMapping(value = "/selectZFPersonLearnCoutAll")
	@ApiLog("政府-获取学习统计列表")
	@ApiOperation(value = "政府-获取学习统计列表", notes = "传入qiYeShouYePage",position = 22)
	public R selectZFPersonLearnCoutAll(@RequestBody QiYeShouYePage qiYeShouYePage) throws IOException {
		R r = new R();
		Organization jb = iOrganizationService.selectGetZFJB(qiYeShouYePage.getDeptId());
		if(!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())){
			qiYeShouYePage.setProvince(qiYeShouYePage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
			qiYeShouYePage.setCity(qiYeShouYePage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCountry())) {
			qiYeShouYePage.setCountry(qiYeShouYePage.getDeptId());
		}

		//排序条件
		if(qiYeShouYePage.getOrderColumns()==null){
			qiYeShouYePage.setOrderColumn("deptname");
		}else{
			qiYeShouYePage.setOrderColumn(qiYeShouYePage.getOrderColumns());
		}

		QiYeShouYePage<PersonLearnInfo> zfpersonLearnInfo =  iQiYeShouYeService.selectZFPersonLearnCoutAll(qiYeShouYePage);
		if(zfpersonLearnInfo != null){
			r.setData(zfpersonLearnInfo);
			r.setSuccess(true);
			r.setMsg("获取成功");
		}else{
			r.setData(null);
			r.setSuccess(false);
			r.setMsg("获取失败");
		}
		return R.data(r);
	}

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiLog("企业-根据ID查看线下学习信息")
	@ApiOperation(value = "企业-根据ID查看线下学习信息", notes = "传入数据ID", position = 23)
	public R<PersonLearnRemark> detail(Integer Id) {
		R r = new R();
		PersonLearnRemark detail = iQiYeShouYeService.selectByIds(Id);
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

	/**
	 * 新增
	 */
	@PostMapping("/submit")
	@ApiLog("企业-新增线下学习信息")
	@ApiOperation(value = "企业-新增线下学习信息", notes = "传入personLearnRemark", position = 24)
	public R submit(@RequestBody PersonLearnRemark personLearnRemark) {
		R rs = new R();
		personLearnRemark.setUpdatetime(DateUtil.now());
		int j = iQiYeShouYeService.insertOffLearnSelective(personLearnRemark);
		if(j > 0){
			String ids = personLearnRemark.getUserid();
			String[] idsss = ids.split(",");
			//去除素组中重复的数组
			List<String> listid = new ArrayList<String>();
			for (int i=0; i<idsss.length; i++) {
				if(!listid.contains(idsss[i])) {
					listid.add(idsss[i]);
				}
			}
			//返回一个包含所有对象的指定类型的数组
			String[]  idss= listid.toArray(new String[1]);
			for (int i = 0; i < idss.length; i++) {
				JiaShiYuanEnterprise jiaShiYuanEnterprise = iJiaShiYuanEnterpriseService.selectByIds(idss[i]);
				PersonLearnInfo personLearnInfo = new PersonLearnInfo();
				personLearnInfo.setDeptid(jiaShiYuanEnterprise.getDeptId());
				personLearnInfo.setDeptname(jiaShiYuanEnterprise.getDeptName());
				personLearnInfo.setLearntime(personLearnRemark.getLearntime());
				personLearnInfo.setLyear(personLearnRemark.getLyear());
				personLearnInfo.setLmonth(personLearnRemark.getLmonth());
				personLearnInfo.setLuser(personLearnRemark.getLuser());
				personLearnInfo.setUserphone(jiaShiYuanEnterprise.getShoujihaoma());
				personLearnInfo.setUsername(jiaShiYuanEnterprise.getJiashiyuanxingming());
				personLearnInfo.setStatus(personLearnRemark.getStatus());
				personLearnInfo.setUsercard(jiaShiYuanEnterprise.getShenfenzhenghao());
				personLearnInfo.setLearnStudy("线下学习");
				personLearnInfo.setUpdatetime(DateUtil.now());
				iQiYeShouYeService.insertSelective(personLearnInfo);
			}
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("新增线下学习信息成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("新增线下学习信息失败");
		}
		return rs;
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiLog("企业-编辑线下学习信息")
	@ApiOperation(value = "企业-编辑线下学习信息", notes = "传入personLearnRemark", position = 25)
	public R update(@RequestBody PersonLearnRemark personLearnRemark) {
		R rs = new R();
		int i = iQiYeShouYeService.updateOffLearnSelective(personLearnRemark);
		if(i > 0){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("编辑线下学习信息成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("编辑线下学习信息失败");
		}
		return rs;
	}

	@PostMapping(value = "/selectPersonLearnRemarkAll")
	@ApiLog("企业-获取线下学习统计列表")
	@ApiOperation(value = "企业-获取线下学习统计列表", notes = "传入qiYeShouYePage",position = 26)
	public R selectPersonLearnRemarkAll(@RequestBody QiYeShouYePage qiYeShouYePage) throws IOException {
		R r = new R();
		//排序条件
		if(qiYeShouYePage.getOrderColumns()==null){
			qiYeShouYePage.setOrderColumn("username");
		}else{
			qiYeShouYePage.setOrderColumn(qiYeShouYePage.getOrderColumns());
		}
		QiYeShouYePage<PersonLearnRemark> zfpersonLearnInfo =  iQiYeShouYeService.selectPersonLearnRemarkAll(qiYeShouYePage);
		if(zfpersonLearnInfo != null){
			r.setData(zfpersonLearnInfo);
			r.setSuccess(true);
			r.setMsg("获取成功");
		}else{
			r.setData(null);
			r.setSuccess(false);
			r.setMsg("获取失败");
		}
		return R.data(r);
	}

	@GetMapping("/getDriverByDeptIdList")
	@ApiLog("企业--根据企业ID获取驾驶员信息（企业版）")
	@ApiOperation(value = "企业--根据企业ID获取驾驶员信息（企业版）", notes = "传入deptId", position = 27)
	public R<List<VehicleDriver>> getDriverByDeptIdList(String deptId) {
		List<VehicleDriver> detail = vehiclepostClientBack.getDriverByDeptIdList(deptId,"0");
		return R.data(detail);
	}

	@GetMapping(value = "/getVideoUrl")
	@ApiLog("企业端-实时视频获取")
	@ApiOperation(value = "企业端-实时视频获取)",position = 12)
	public R getVideoUrl(@ApiParam(value = "车辆ID", required = true) @RequestParam String vehicleId,
						 @ApiParam(value = "终端ID", required = true) @RequestParam String simNo,
						 @ApiParam(value = "通道", required = true) @RequestParam int channel,
						 @ApiParam(value = "来源", required = true) @RequestParam int stream) throws IOException {
		R rs = new R();
		String url = fileServer.getVideoUrl() + "/videoCommand/realTimeVideo.action?vehicleId=" + vehicleId + "&simNo=" + simNo+ "&channel=" + channel+ "&stream=" + stream;
		String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
//		JSONArray jsonObject = (JSONArray) JSONArray.parse(jsonstr);
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonstr);
		rs.setCode(200);
		rs.setData(jsonObject);
		rs.setMsg("获取成功");
		return rs;
	}

	@GetMapping(value = "/getStopVideoUrl")
	@ApiLog("企业端-实时视频停止")
	@ApiOperation(value = "企业端-实时视频停止)",position = 13)
	public R getStopVideoUrl(@ApiParam(value = "车辆ID", required = true) @RequestParam String vehicleId,
							 @ApiParam(value = "终端ID", required = true) @RequestParam String channelId,
							 @ApiParam(value = "通道", required = true) @RequestParam int videoDataType,
							 @ApiParam(value = "来源", required = true) @RequestParam int streamType) throws IOException {
		R rs = new R();
		String url = fileServer.getVideoUrl() + "/videoCommand/stopRealTimeVideoNew.action?vehicleId=" + vehicleId + "&channelId=" + channelId+ "&videoDataType=" + videoDataType+ "&streamType=" + streamType;
		String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
//		JSONArray jsonObject = (JSONArray) JSONArray.parse(jsonstr);
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonstr);
		rs.setCode(200);
		rs.setData(jsonObject);
		rs.setMsg("获取成功");
		return rs;
	}

	@GetMapping(value = "/getOldVideoUrl")
	@ApiLog("企业端-历史实时视频获取")
	@ApiOperation(value = "企业端-历史实时视频获取)",position = 14)
	public R getOldVideoUrl(@ApiParam(value = "车辆ID", required = true) @RequestParam String vehicleId,
							@ApiParam(value = "通道", required = true) @RequestParam int channel,
							@ApiParam(value = "音视频类型", required = true) @RequestParam int videoType,
							@ApiParam(value = "码流类型", required = true) @RequestParam int streamType,
							@ApiParam(value = "存储类型", required = true) @RequestParam int storeType,
							@ApiParam(value = "开始时间", required = true) @RequestParam String startDate,
							@ApiParam(value = "结束时间", required = true) @RequestParam String endDate) throws IOException {
		R rs = new R();
		String url = fileServer.getVideoUrl() + "/videoResourceSearch/sendRequest.action?vehicleId=" + vehicleId + "&videoType=" + videoType + "&streamType=" + streamType + "&storeType=" + storeType + "&channel=" + channel + "&startDate=" + startDate + "&endDate=" + endDate;
		String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
//		JSONArray jsonObject = (JSONArray) JSONArray.parse(jsonstr);
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonstr);
		rs.setCode(200);
		rs.setData(jsonObject);
		rs.setMsg("获取成功");
		return rs;
	}

	@GetMapping(value = "/getOldVideoHisResult")
	@ApiLog("企业端-历史实时视频区间列表")
	@ApiOperation(value = "企业端-历史实时视频区间列表)",position = 15)
	public R getOldVideoHisResult(@ApiParam(value = "企业ID", required = true) @RequestParam String cmdId,
		@ApiParam(value = "通道", required = true) @RequestParam String seq,
		@ApiParam(value = "开始时间", required = true) @RequestParam String ipAddress) throws IOException {
		R rs = new R();
		String url = fileServer.getVideoUrl() + "/command/getVideoHisResult.action?cmdId=" + cmdId + "&seq=" + seq + "&ipAddress=" + ipAddress;
		String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
//		JSONArray jsonObject = (JSONArray) JSONArray.parse(jsonstr);
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonstr);
		rs.setCode(200);
		rs.setData(jsonObject);
		rs.setMsg("获取成功");
		return rs;
	}

}

