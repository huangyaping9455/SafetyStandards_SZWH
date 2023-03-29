/**
 * Copyright (C), 2015-2020,
 * FileName: GpsVehicleController
 * Author:   呵呵哒
 * Date:     2020/7/3 9:42
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.qiyeshouye.entity.*;
import org.springblade.anbiao.qiyeshouye.page.*;
import org.springblade.anbiao.qiyeshouye.service.IQiYeTongJiService;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/8/30
 * @描述
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/qiYeTongJi")
@Api(value = "企业平台-报警统计", tags = "企业平台-报警统计")
public class QiYeTongJiController {

	private IQiYeTongJiService iQiYeTongJiService;

	@PostMapping(value = "/getBJTJJS")
	@ApiLog("企业报警统计-报警统计汇总")
	@ApiOperation(value = "企业报警统计-报警统计汇总", notes = "传入qiYeTongJiPage",position = 1)
	public R<QiYeTongJiPage<QiYeTongJi>> getBJTJJS(@RequestBody QiYeTongJiPage qiYeTongJiPage) {
		//排序条件
		////默认报警总数降序
		if(qiYeTongJiPage.getOrderColumns()==null){
			qiYeTongJiPage.setOrderColumn("baojingcishu");
		}else{
			qiYeTongJiPage.setOrderColumn(qiYeTongJiPage.getOrderColumns());
		}
		QiYeTongJiPage<QiYeTongJi> pages = iQiYeTongJiService.selectGetBJTJ(qiYeTongJiPage);
		return R.data(pages);
	}

	@PostMapping(value = "/getBJPMTJ")
	@ApiLog("企业报警统计-车辆报警排名")
	@ApiOperation(value = "企业报警统计-车辆报警排名", notes = "传入qiYeTongJiPage",position = 2)
	public R<QiYeTongJiPage<QiYeTongJi>> getBJPMTJ(@RequestBody QiYeTongJiPage qiYeTongJiPage) {
		//排序条件
		////默认报警总数降序
		if(qiYeTongJiPage.getOrderColumns()==null){
			qiYeTongJiPage.setOrderColumn("baojingcishu");
		}else{
			qiYeTongJiPage.setOrderColumn(qiYeTongJiPage.getOrderColumns());
		}
		QiYeTongJiPage<QiYeTongJi> pages = iQiYeTongJiService.selectBJPMTJ(qiYeTongJiPage);
		return R.data(pages);
	}

	@PostMapping(value = "/getBJRYXTJ")
	@ApiLog("企业报警统计-日运行情况统计")
	@ApiOperation(value = "企业报警统计-日运行情况统计", notes = "传入qiYeTongJiPage",position = 2)
	public R<QiYeTongJiPage<QiYeRiYunXingTongJi>> getBJRYXTJ(@RequestBody QiYeTongJiPage qiYeTongJiPage) {
		//排序条件
		////默认报警总数降序
		if(qiYeTongJiPage.getOrderColumns()==null){
			qiYeTongJiPage.setOrderColumn("");
		}else{
			qiYeTongJiPage.setOrderColumn(qiYeTongJiPage.getOrderColumns());
		}
		QiYeTongJiPage<QiYeRiYunXingTongJi> pages = iQiYeTongJiService.selectGetRYXBJTJ(qiYeTongJiPage);
		return R.data(pages);
	}

	@PostMapping(value = "/getQYOffLineTJ")
	@ApiLog("企业-24小时不在线车辆统计")
	@ApiOperation(value = "企业-24小时不在线车辆统计", notes = "传入qiYeOffLineTongJiPage",position = 5)
	public R<QiYeOffLineTongJiPage<QiYeOffLineTongJi>> getQYOffLineTJ(@RequestBody QiYeOffLineTongJiPage qiYeOffLineTongJiPage) {
		//排序条件
		////默认车辆牌照降序
		if(qiYeOffLineTongJiPage.getOrderColumns()==null){
			qiYeOffLineTongJiPage.setOrderColumn("cheliangpaizhao");
		}else{
			qiYeOffLineTongJiPage.setOrderColumn(qiYeOffLineTongJiPage.getOrderColumns());
		}
		QiYeOffLineTongJiPage<QiYeOffLineTongJi> pages = iQiYeTongJiService.selectGet24HoursOffLineTJ(qiYeOffLineTongJiPage);
		return R.data(pages);
	}

	@PostMapping(value = "/getQYInOutAreaTJ")
	@ApiLog("企业-进出区域统计")
	@ApiOperation(value = "企业-进出区域统计", notes = "传入qiYeInOutAreaPage",position = 6)
	public R<QiYeInOutAreaPage<QiYeInOutAreaTongJi>> getQYInOutAreaTJ(@RequestBody QiYeInOutAreaPage qiYeInOutAreaPage) {
		//排序条件
		////默认车辆牌照降序
		if(qiYeInOutAreaPage.getOrderColumns()==null){
			qiYeInOutAreaPage.setOrderColumn("KeepTime");
		}else{
			qiYeInOutAreaPage.setOrderColumn(qiYeInOutAreaPage.getOrderColumns());
		}
		QiYeInOutAreaPage<QiYeInOutAreaTongJi> pages = iQiYeTongJiService.selectGetInOutAreaTJ(qiYeInOutAreaPage);
		return R.data(pages);
	}

	@PostMapping(value = "/getTrajectoryIntegrityTJ")
	@ApiLog("企业-车辆轨迹完整率报表")
	@ApiOperation(value = "企业-车辆轨迹完整率报表", notes = "传入qiYeInOutAreaPage",position = 8)
	public R<QiYeInOutAreaPage<StatementTongJi>> getTrajectoryIntegrityTJ(QiYeInOutAreaPage qiYeInOutAreaPage) {
		if (qiYeInOutAreaPage.getOrderColumns() == null) {
			qiYeInOutAreaPage.setOrderColumn("statisticsDate");
		} else {
			qiYeInOutAreaPage.setOrderColumn(qiYeInOutAreaPage.getOrderColumns());
		}
		QiYeInOutAreaPage pages = iQiYeTongJiService.selectGetTrajectoryIntegrityTJ(qiYeInOutAreaPage);
		return R.data(pages);
	}

	@PostMapping(value = "/getGuiJiYiChangTJ")
	@ApiLog("企业-轨迹漂移报表")
	@ApiOperation(value = "企业-轨迹漂移报表", notes = "传入qiYeInOutAreaPage",position = 9)
	public R<QiYeInOutAreaPage<TrajectoryAnomalies>> getGuiJiYiChangTJ(QiYeInOutAreaPage qiYeInOutAreaPage) {
		if (qiYeInOutAreaPage.getOrderColumns() == null) {
			qiYeInOutAreaPage.setOrderColumn("cheliangpaizhao");
		} else {
			qiYeInOutAreaPage.setOrderColumn(qiYeInOutAreaPage.getOrderColumns());
		}
		QiYeInOutAreaPage pages = iQiYeTongJiService.selectGuiJiYiChangTJ(qiYeInOutAreaPage);
		return R.data(pages);
	}

	@PostMapping(value = "/getTingCheMingXiTJ")
	@ApiLog("企业-停车明细报表")
	@ApiOperation(value = "企业-停车明细报表", notes = "传入qiYeInOutAreaPage",position = 10)
	public R<QiYeInOutAreaPage<StopVehicle>> getTingCheMingXiTJ(QiYeInOutAreaPage qiYeInOutAreaPage) {
		if (qiYeInOutAreaPage.getOrderColumns() == null) {
			qiYeInOutAreaPage.setOrderColumn("cheliangpaizhao");
		} else {
			qiYeInOutAreaPage.setOrderColumn(qiYeInOutAreaPage.getOrderColumns());
		}
		QiYeInOutAreaPage pages = iQiYeTongJiService.selectTingCheMingXiTJ(qiYeInOutAreaPage);
		return R.data(pages);
	}

	@PostMapping(value = "/getTingCheHuiZongTJ")
	@ApiLog("企业-停车汇总报表")
	@ApiOperation(value = "企业-停车汇总报表", notes = "传入qiYeInOutAreaPage",position = 11)
	public R<QiYeInOutAreaPage<StopVehicle>> getTingCheHuiZongTJ(QiYeInOutAreaPage qiYeInOutAreaPage) {
		if (qiYeInOutAreaPage.getOrderColumns() == null) {
			qiYeInOutAreaPage.setOrderColumn("cheliangpaizhao");
		} else {
			qiYeInOutAreaPage.setOrderColumn(qiYeInOutAreaPage.getOrderColumns());
		}
		QiYeInOutAreaPage pages = iQiYeTongJiService.selectTingCheHuiZongTJ(qiYeInOutAreaPage);
		return R.data(pages);
	}

	@PostMapping(value = "/getTpvehdataTJ")
	@ApiLog("企业-在线车辆详情")
	@ApiOperation(value = "企业-在线车辆详情", notes = "传入qiYeTpvehdataPage",position = 7)
	public R<QiYeTpvehdataPage<QiYeTpvehdataTongJi>> getTpvehdataTJ(@RequestBody QiYeTpvehdataPage qiYeTpvehdataPage) {
		R rs = new R();
		//排序条件
		////默认定位时间降序
		if(qiYeTpvehdataPage.getOrderColumns()==null){
			qiYeTpvehdataPage.setOrderColumn("Systime");
		}else{
			qiYeTpvehdataPage.setOrderColumn(qiYeTpvehdataPage.getOrderColumns());
		}
		QiYeTpvehdataPage<QiYeTpvehdataTongJi> pages = iQiYeTongJiService.selecttpvehdataTJ(qiYeTpvehdataPage);
		if(pages != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(pages);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/getDriverAlarmTJ")
	@ApiLog("企业-企业驾驶员违规统计列表")
	@ApiOperation(value = "企业-企业驾驶员违规统计列表", notes = "传入qiYeDriverAlarmTongJiPage",position = 8)
	public R<QiYeDriverAlarmTongJiPage<QiYeDriverAlarmTongJi>> getDriverAlarmTJ(@RequestBody QiYeDriverAlarmTongJiPage qiYeDriverAlarmTongJiPage) {
		R rs = new R();
		//排序条件
		////默认定位时间降序
		if(qiYeDriverAlarmTongJiPage.getOrderColumns()==null){
			qiYeDriverAlarmTongJiPage.setOrderColumn("alarmnum");
		}else{
			qiYeDriverAlarmTongJiPage.setOrderColumn(qiYeDriverAlarmTongJiPage.getOrderColumns());
		}
		QiYeDriverAlarmTongJiPage<QiYeDriverAlarmTongJi> pages = iQiYeTongJiService.seleDriverAlarmTJ(qiYeDriverAlarmTongJiPage);
		if(pages != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(pages);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/getDriverAlarmXQTJ")
	@ApiLog("企业-企业驾驶员违规统计详情列表")
	@ApiOperation(value = "企业-企业驾驶员违规统计详情列表", notes = "传入qiYeDriverAlarmTongJiPage",position = 9)
	public R<QiYeDriverAlarmTongJiPage<QiYeDriverAlarmTongJi>> getDriverAlarmXQTJ(@RequestBody QiYeDriverAlarmTongJiPage qiYeDriverAlarmTongJiPage) {
		R rs = new R();
		//排序条件
		////默认定位时间降序
		if(qiYeDriverAlarmTongJiPage.getOrderColumns()==null){
			qiYeDriverAlarmTongJiPage.setOrderColumn("endtime");
		}else{
			qiYeDriverAlarmTongJiPage.setOrderColumn(qiYeDriverAlarmTongJiPage.getOrderColumns());
		}
		QiYeDriverAlarmTongJiPage<QiYeDriverAlarmTongJi> pages = iQiYeTongJiService.seleDriverAlarmXQTJ(qiYeDriverAlarmTongJiPage);
		if(pages != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(pages);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/getTTSTJ")
	@ApiLog("企业-企业tts日志报表")
	@ApiOperation(value = "企业-企业tts日志报表", notes = "传入qiYeTTSTongJiPage",position = 10)
	public R<QiYeTTSTongJiPage<QiYeTTSTongJi>> getTTSTJ(@RequestBody QiYeTTSTongJiPage qiYeTTSTongJiPage) {
		R rs = new R();
		//排序条件
		////默认发送时间降序
		if(qiYeTTSTongJiPage.getOrderColumns()==null){
			qiYeTTSTongJiPage.setOrderColumn("gpstime");
		}else{
			qiYeTTSTongJiPage.setOrderColumn(qiYeTTSTongJiPage.getOrderColumns());
		}
		QiYeTTSTongJiPage<QiYeTTSTongJi> qiYeTTSTongJiList = iQiYeTongJiService.selectTTSTJ(qiYeTTSTongJiPage);
		if(qiYeTTSTongJiList != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(qiYeTTSTongJiList);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/getMileageTJ")
	@ApiLog("企业-里程统计报表查询")
	@ApiOperation(value = "企业-里程统计报表查询", notes = "传入qiYeTTSTongJiPage",position = 11)
	public R<QiYeTTSTongJiPage<QiYeMileageTongJi>> getMileageTJ(@RequestBody QiYeTTSTongJiPage qiYeTTSTongJiPage) {
		R rs = new R();
		//排序条件
		////默认发送时间降序
		if(qiYeTTSTongJiPage.getOrderColumns()==null){
			qiYeTTSTongJiPage.setOrderColumn("TravelMileage");
		}else{
			qiYeTTSTongJiPage.setOrderColumn(qiYeTTSTongJiPage.getOrderColumns());
		}
		QiYeTTSTongJiPage<QiYeMileageTongJi> qiYeMileageTongJiList = iQiYeTongJiService.selectMileageTJ(qiYeTTSTongJiPage);
		if(qiYeMileageTongJiList != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(qiYeMileageTongJiList);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/getMonthMileageTJ")
	@ApiLog("企业-企业车辆里程统计表查询")
	@ApiOperation(value = "企业-企业车辆里程统计表查询", notes = "传入qiYeTTSTongJiPage",position = 12)
	public R<QiYeTTSTongJiPage<QiYeMonthMileageTongJi>> getMonthMileageTJ(@RequestBody QiYeTTSTongJiPage qiYeTTSTongJiPage) {
		R rs = new R();
		//排序条件
		////默认发送时间降序
		if(qiYeTTSTongJiPage.getOrderColumns()==null){
			qiYeTTSTongJiPage.setOrderColumn("TravelMileage");
		}else{
			qiYeTTSTongJiPage.setOrderColumn(qiYeTTSTongJiPage.getOrderColumns());
		}
		QiYeTTSTongJiPage<QiYeMonthMileageTongJi> qiYeMonthMileageTongJiList = iQiYeTongJiService.selectMonthMileageTJ(qiYeTTSTongJiPage);
		if(qiYeMonthMileageTongJiList != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(qiYeMonthMileageTongJiList);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/gettChuLiLvTJ")
	@ApiLog("企业-企业报警处理率统计")
	@ApiOperation(value = "企业-企业报警处理率统计", notes = "传入qiYeTongJiPage",position = 13)
	public R<QiYeTongJiPage<DeptBaoJingTongJiLv>> gettChuLiLvTJ(@RequestBody QiYeTongJiPage qiYeTongJiPage) {
		R rs = new R();
		//排序条件
		////默认发送时间降序
		if(qiYeTongJiPage.getOrderColumns()==null){
			qiYeTongJiPage.setOrderColumn("baojingzongshu");
		}else{
			qiYeTongJiPage.setOrderColumn(qiYeTongJiPage.getOrderColumns());
		}
		QiYeTongJiPage<DeptBaoJingTongJiLv> qiYeMonthMileageTongJiList = iQiYeTongJiService.selectChuLiLvTJ(qiYeTongJiPage);
		if(qiYeMonthMileageTongJiList != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(qiYeMonthMileageTongJiList);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/getDeptChaosuTJ")
	@ApiLog("企业-超速报警统计分析")
	@ApiOperation(value = "企业-超速报警统计分析", notes = "传入QiYeAlarmTongJiPage",position = 14)
	public R<QiYeAlarmTongJiPage<DeptChaoSuBaoJingTongJi>> getDeptChaosuTJ(@RequestBody QiYeAlarmTongJiPage qiYeAlarmTongJiPage) {
		R rs = new R();
		//排序条件
		////默认发送时间降序
		if(qiYeAlarmTongJiPage.getOrderColumns()==null){
			qiYeAlarmTongJiPage.setOrderColumn("alarmNum");
		}else{
			qiYeAlarmTongJiPage.setOrderColumn(qiYeAlarmTongJiPage.getOrderColumns());
		}
		QiYeAlarmTongJiPage<DeptChaoSuBaoJingTongJi> deptChaosuTJ = iQiYeTongJiService.selectDeptChaosuTJ(qiYeAlarmTongJiPage);
		if(deptChaosuTJ != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(deptChaosuTJ);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/getDeptPersistentUnpositioningTJ")
	@ApiLog("企业-持续不在线不定位车辆统计分析")
	@ApiOperation(value = "企业-持续不在线不定位车辆统计分析", notes = "传入PersistentUnpositioningTongJiPage",position = 15)
	public R<PersistentUnpositioningTongJiPage<DeptPersistentUnpositioningTongJi>> getDeptPersistentUnpositioningTJ(@RequestBody PersistentUnpositioningTongJiPage persistentUnpositioningTongJiPage) {
		R rs = new R();
		//排序条件
		////默认发送时间降序
		if(persistentUnpositioningTongJiPage.getOrderColumns()==null){
			persistentUnpositioningTongJiPage.setOrderColumn("cheliangpaizhao");
		}else{
			persistentUnpositioningTongJiPage.setOrderColumn(persistentUnpositioningTongJiPage.getOrderColumns());
		}
		PersistentUnpositioningTongJiPage<DeptPersistentUnpositioningTongJi> dptPersistentUnpositioningTongJi = iQiYeTongJiService.selectDeptPersistentUnpositioningTJ(persistentUnpositioningTongJiPage);
		if(dptPersistentUnpositioningTongJi != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(dptPersistentUnpositioningTongJi);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/getDeptPiLaoTJ")
	@ApiLog("企业-疲劳驾驶报警统计分析")
	@ApiOperation(value = "企业-疲劳驾驶报警统计分析", notes = "传入QiYeAlarmTongJiPage",position = 14)
	public R<QiYeAlarmTongJiPage<DeptPiLaoBaoJingTongJi>> getDeptPiLaoTJ(@RequestBody QiYeAlarmTongJiPage qiYeAlarmTongJiPage) {
		R rs = new R();
		//排序条件
		////默认发送时间降序
		if(qiYeAlarmTongJiPage.getOrderColumns()==null){
			qiYeAlarmTongJiPage.setOrderColumn("alarmNum");
		}else{
			qiYeAlarmTongJiPage.setOrderColumn(qiYeAlarmTongJiPage.getOrderColumns());
		}
		QiYeAlarmTongJiPage<DeptPiLaoBaoJingTongJi> deptChaosuTJ = iQiYeTongJiService.selectDeptPiLaoTJ(qiYeAlarmTongJiPage);
		if(deptChaosuTJ != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(deptChaosuTJ);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/getDaKaInfoTJ")
	@ApiLog("企业-驾驶打卡记录统计分析")
	@ApiOperation(value = "企业-驾驶打卡记录统计分析", notes = "传入QiYeAlarmTongJiPage",position = 15)
	public R<QiYeAlarmTongJiPage<JiashiyuanDakaInfo>> getDaKaInfoTJ(@RequestBody QiYeAlarmTongJiPage qiYeAlarmTongJiPage) {
		R rs = new R();
		//排序条件
		////默认打卡时间降序
		if(qiYeAlarmTongJiPage.getOrderColumns()==null){
			qiYeAlarmTongJiPage.setOrderColumn("dakashijian");
		}else{
			qiYeAlarmTongJiPage.setOrderColumn(qiYeAlarmTongJiPage.getOrderColumns());
		}
		QiYeAlarmTongJiPage<JiashiyuanDakaInfo> daKaInfoTJ = iQiYeTongJiService.selectDaKaInfoTJ(qiYeAlarmTongJiPage);
		if(daKaInfoTJ != null){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(daKaInfoTJ);
			rs.setMsg("获取数据成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取数据失败");
		}
		return rs;
	}

	@PostMapping(value = "/getFYBJPMTJ")
	@ApiLog("企业报警统计-车辆报警排名（阜阳）")
	@ApiOperation(value = "企业报警统计-车辆报警排名（阜阳）", notes = "传入qiYeTongJiPage",position = 22)
	public R<QiYeTongJiPage<QiYeTongJi>> getFYBJPMTJ(@RequestBody QiYeTongJiPage qiYeTongJiPage) {
		R rs = new R();
		//排序条件
		////默认报警总数降序
		if(qiYeTongJiPage.getOrderColumns()==null){
			qiYeTongJiPage.setOrderColumn("baojingcishu");
		}else{
			qiYeTongJiPage.setOrderColumn(qiYeTongJiPage.getOrderColumns());
		}
		if(qiYeTongJiPage.getBeginTime() != null && qiYeTongJiPage.getEndTime() != null){
			int between = DateUtils.fun(qiYeTongJiPage.getBeginTime(), qiYeTongJiPage.getEndTime());
			if(between < 0 ){
				rs.setMsg("结束时间不能小于开始时间");
				rs.setCode(500);
				rs.setSuccess(false);
				return rs;
			}else if(between > 31 ){
				rs.setMsg("开始时间与结束时间间隔不能超过31天");
				rs.setCode(500);
				rs.setSuccess(false);
				return rs;
			}
		}
		QiYeTongJiPage<QiYeTongJi> pages = iQiYeTongJiService.selectFYVehAlarmPage(qiYeTongJiPage);
		return R.data(pages);
	}



}

