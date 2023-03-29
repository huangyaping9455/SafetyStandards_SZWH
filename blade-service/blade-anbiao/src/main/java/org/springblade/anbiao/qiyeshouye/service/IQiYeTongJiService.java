/**
 * Copyright (C), 2015-2020,
 * FileName: IXinXiJiaoHuZhuService
 * Author:   呵呵哒
 * Date:     2020/6/20 17:18
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.qiyeshouye.entity.*;
import org.springblade.anbiao.qiyeshouye.page.*;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/7/4
 * @描述
 */
public interface IQiYeTongJiService extends IService<QiYeTongJi> {

	/**
	 * 报警统计汇总
	 * @param qiYeTongJiPage
	 * @return
	 */
	QiYeTongJiPage selectGetBJTJ(QiYeTongJiPage qiYeTongJiPage);

	/**
	 * 车辆报警排名
	 * @param qiYeTongJiPage
	 * @return
	 */
	QiYeTongJiPage selectBJPMTJ(QiYeTongJiPage qiYeTongJiPage);

	/**
	 * 日运行情况统计
	 * @param qiYeTongJiPage
	 * @return
	 */
	QiYeTongJiPage selectGetRYXBJTJ(QiYeTongJiPage qiYeTongJiPage);

	/**
	 * 24小时不在线统计
	 * @param qiYeOffLineTongJiPage
	 * @return
	 */
	QiYeOffLineTongJiPage<QiYeOffLineTongJi> selectGet24HoursOffLineTJ(QiYeOffLineTongJiPage qiYeOffLineTongJiPage);

	/**
	 * 进出区域统计
	 * @param qiYeInOutAreaPage
	 * @return
	 */
	QiYeInOutAreaPage<QiYeInOutAreaTongJi> selectGetInOutAreaTJ(QiYeInOutAreaPage qiYeInOutAreaPage);

	/**
	 *车辆轨迹完整率报表
	 * @param qiYeInOutAreaPage
	 * @return
	 */
	QiYeInOutAreaPage<StatementTongJi> selectGetTrajectoryIntegrityTJ(QiYeInOutAreaPage qiYeInOutAreaPage);

	/**
	 * 轨迹漂移报表
	 * @param qiYeInOutAreaPage
	 * @return
	 */
	QiYeInOutAreaPage<TrajectoryAnomalies> selectGuiJiYiChangTJ(QiYeInOutAreaPage qiYeInOutAreaPage);

	/**
	 * 停车明细报表
	 * @param qiYeInOutAreaPage
	 * @return
	 */
	QiYeInOutAreaPage<StopVehicle> selectTingCheMingXiTJ(QiYeInOutAreaPage qiYeInOutAreaPage);

	/**
	 * 停车汇总报表
	 * @param qiYeInOutAreaPage
	 * @return
	 */
	QiYeInOutAreaPage<StopVehicle> selectTingCheHuiZongTJ(QiYeInOutAreaPage qiYeInOutAreaPage);

	/**
	 * 企业在线车辆详情
	 * @param qiYeTpvehdataPage
	 * @return
	 */
	QiYeTpvehdataPage<QiYeTpvehdataTongJi> selecttpvehdataTJ(QiYeTpvehdataPage qiYeTpvehdataPage);

	/**
	 * 企业驾驶员违规统计列表
	 * @param qiYeDriverAlarmTongJiPage
	 * @return
	 */
	QiYeDriverAlarmTongJiPage<QiYeDriverAlarmTongJi> seleDriverAlarmTJ(QiYeDriverAlarmTongJiPage qiYeDriverAlarmTongJiPage);

	/**
	 * 企业驾驶员违规统计详情列表
	 * @param qiYeDriverAlarmTongJiPage
	 * @return
	 */
	QiYeDriverAlarmTongJiPage<QiYeDriverAlarmTongJi> seleDriverAlarmXQTJ(QiYeDriverAlarmTongJiPage qiYeDriverAlarmTongJiPage);

	/**
	 * 企业tts日志报表查询
	 * @param qiYeTTSTongJiPage
	 * @return
	 */
	QiYeTTSTongJiPage<QiYeTTSTongJi> selectTTSTJ(QiYeTTSTongJiPage qiYeTTSTongJiPage);

	/**
	 * 里程统计报表查询
	 * @param qiYeTTSTongJiPage
	 * @return
	 */
	QiYeTTSTongJiPage<QiYeMileageTongJi> selectMileageTJ(QiYeTTSTongJiPage qiYeTTSTongJiPage);

	/**
	 * 企业车辆里程统计表查询
	 * @param qiYeTTSTongJiPage
	 * @return
	 */
	QiYeTTSTongJiPage<QiYeMonthMileageTongJi> selectMonthMileageTJ(QiYeTTSTongJiPage qiYeTTSTongJiPage);

	/**
	 * 报警处理率统计
	 * @param qiYeTongJiPage
	 * @return
	 */
	QiYeTongJiPage<DeptBaoJingTongJiLv> selectChuLiLvTJ(QiYeTongJiPage qiYeTongJiPage);

	/**
	 * 超速报警统计分析
	 * @param qiYeAlarmTongJiPage
	 * @return
	 */
	QiYeAlarmTongJiPage<DeptChaoSuBaoJingTongJi> selectDeptChaosuTJ(QiYeAlarmTongJiPage qiYeAlarmTongJiPage);

	/**
	 * 持续不在线不定位车辆
	 * @param persistentUnpositioningTongJiPage
	 * @return
	 */
	PersistentUnpositioningTongJiPage<DeptPersistentUnpositioningTongJi> selectDeptPersistentUnpositioningTJ(PersistentUnpositioningTongJiPage persistentUnpositioningTongJiPage);

	/**
	 * 疲劳驾驶报警统计分析
	 * @param qiYeAlarmTongJiPage
	 * @return
	 */
	QiYeAlarmTongJiPage<DeptPiLaoBaoJingTongJi> selectDeptPiLaoTJ(QiYeAlarmTongJiPage qiYeAlarmTongJiPage);

	/**
	 * 驾驶打卡记录
	 * @param qiYeAlarmTongJiPage
	 */
	QiYeAlarmTongJiPage<JiashiyuanDakaInfo> selectDaKaInfoTJ(QiYeAlarmTongJiPage qiYeAlarmTongJiPage);

	/**
	 * 车辆报警排名（阜阳）
	 * @param qiYeTongJiPage
	 * @return
	 */
	QiYeTongJiPage<QiYeTongJi> selectFYVehAlarmPage(QiYeTongJiPage qiYeTongJiPage);

}
