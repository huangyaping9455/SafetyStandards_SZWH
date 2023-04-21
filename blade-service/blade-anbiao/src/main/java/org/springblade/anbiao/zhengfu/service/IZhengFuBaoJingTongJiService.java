/**
 * FileName: IXinXiJiaoHuZhuService
 * Author:   呵呵哒
 * Description:
 */
package org.springblade.anbiao.zhengfu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.zhengfu.entity.*;
import org.springblade.anbiao.zhengfu.page.ZhengFuBaoJingTongJiJieSuanPage;
import org.springblade.anbiao.zhengfu.page.ZhengFuIntoAreaPage;
import org.springblade.anbiao.zhengfu.page.ZhengFuRiskRankingPage;
import org.springblade.doc.safetyproductionfile.entity.AnbiaoSafetyproductionfileNum;
import org.springblade.doc.safetyproductionfile.page.SafetyproductionfileNumPage;

import java.util.List;

/**
 * @author 呵呵哒
 * @描述
 */
public interface IZhengFuBaoJingTongJiService extends IService<ZhengFuBaoJingTongJi> {

	/**
	 * 当月企业报警占比
	 * @param deptId
	 * @return
	 */
	List<ZhengFuBaoJingTongJi> selectGetZFBaoJing(@Param("deptId") String deptId);

	List<ZhengFuBaoJingTongJi>  selectGetZFBaoJing_XiaJi(@Param("xiaJiDeptId") String xiaJiDeptId);
	/**
	 * 当年报警数、未处理数、当年处理率
	 * @param deptId
	 * @return
	 */
	ZhengFuBaoJingTongJi selectGetZFBaoJingYear(@Param("deptId") String deptId);

	List<ZhengFuBaoJingTongJi> selectGetZFBaoJingYear_XiaJi(@Param("xiaJiDeptId") String xiaJiDeptId);
	/**
	 * 当月报警数、未处理数、当年处理率
	 * @param deptId
	 * @return
	 */
	ZhengFuBaoJingTongJi selectGetZFBaoJingMonth(@Param("deptId") String deptId,@Param("date") String date);

	List<ZhengFuBaoJingTongJi> selectGetZFBaoJingMonth_XiaJi(@Param("xiaJiDeptId") String xiaJiDeptId,@Param("date") String date);
	/**
	 * 当年报警趋势、当年处警趋势
	 * @param deptId
	 * @return
	 */
	List<ZhengFuBaoJingTongJi> selectGetZFBaoJingQuShi(@Param("deptId") String deptId,@Param("firstDate") String firstDate, @Param("endDate") String endDate);

	List<ZhengFuBaoJingTongJi> selectGetZFBaoJingQuShi_XiaJi(@Param("xiaJiDeptId") String xiaJiDeptId,@Param("firstDate") String firstDate, @Param("endDate") String endDate);

	/**
	 * 企业报警排名
	 * @param deptId
	 * @return
	 */
	List<ZhengFuBaoJingTongJi> selectGetZFQYBaoJingPaiMing(@Param("deptId") String deptId);

	List<ZhengFuBaoJingTongJi> selectGetZFQYBaoJingPaiMing_XiaJi(@Param("xiaJiDeptId") String xiaJiDeptId);

	/**
	 * 地区报警排名
	 * @param deptId
	 * @return
	 */
	List<ZhengFuBaoJingTongJi> selectGetZFDQBaoJingPaiMing(@Param("deptId") String deptId);

	List<ZhengFuBaoJingTongJi> selectGetZFDQBaoJingPaiMing_XiaJi(@Param("xiaJiDeptId") String xiaJiDeptId);

	/**
	 * 报警结算统计
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage selectGetBJTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);


	/**
	 * 地区报警处理率统计
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage selectGetDqLvTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 企业报警处理率统计
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage selectGetQYLvTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);


	/**
	 * gps报警明细
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage selectGetGPSMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * dms报警明细
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage selectGetDMSMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 报警 vehicle明细
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage selectGetVehicleMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 所有报警明细
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage selectGetAllMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 地区报警排名
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage selectGetDQTJPMTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 车辆报警排名
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage selectGetCLTJPMTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 根据报警ID查询tts消息列表
	 * @param alarmGuId
	 * @return
	 */
	List<TtsMessageInfo> selectByAlarmGuId(String alarmGuId);

	/**
	 *车辆日运行情况统计
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage<ZhengFuRiYunXingTongJi> selectGetCLRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 企业日运行情况统计
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage<ZhengFuRiYunXingTongJi> selectGetQYRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 政府车辆数
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuBaoJingTongJiJieSuan> seletGetVehicleCount(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 政府风险排名统计
	 * @param zhengFuRiskRankingPage
	 * @return
	 */
	ZhengFuRiskRankingPage<ZhengFuRiskRanking> selectGetDQRiskPMTJ(ZhengFuRiskRankingPage zhengFuRiskRankingPage);

	/**
	 * 政府地区进区域报警排名统计
	 * @param zhengFuIntoAreaPage
	 * @return
	 */
	ZhengFuIntoAreaPage<ZhengFuIntoArea> selectDQIntoAreaPMTJ(ZhengFuIntoAreaPage zhengFuIntoAreaPage);

	/**
	 * 政府企业进区域报警排名统计
	 * @param zhengFuIntoAreaPage
	 * @return
	 */
	ZhengFuIntoAreaPage<ZhengFuIntoArea> selectDeptIntoAreaPMTJ(ZhengFuIntoAreaPage zhengFuIntoAreaPage);

	/**
	 * 政府车辆进区域报警排名统计
	 * @param zhengFuIntoAreaPage
	 * @return
	 */
	ZhengFuIntoAreaPage<ZhengFuIntoArea> selectDeptCLIntoAreaPMTJ(ZhengFuIntoAreaPage zhengFuIntoAreaPage);

	/**
	 * 运营商日运行统计
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage<ZhengFuOperatorMonitorTongJi> selectYYSRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 道路运输企业联网联控考核评分
	 * @param
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage<QiYeLWLKTongJi> selectLWLKTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 服务商联网联控考核评分
	 * @param
	 * @return
	 */
	ZhengFuBaoJingTongJiJieSuanPage<QiYeLWLKTongJi> selectFWSLWLKTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 地区车辆总数、上线车辆数、在线车辆数等指标
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuDongTaiJianKong selectGetZFJk(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	SafetyproductionfileNumPage<AnbiaoSafetyproductionfileNum> selectTJ(SafetyproductionfileNumPage safetyproductionfileNumPage);

}
