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
package org.springblade.anbiao.zhengfu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
 * @创建人 呵呵哒
 * @描述
 */
public interface ZhengFuBaoJingTongJiMapper extends BaseMapper<ZhengFuBaoJingTongJi> {

	/**
	 *
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
	 * @return XjDeptId
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
	List<ZhengFuBaoJingTongJiJieSuan> selectGetBJTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectGetBJTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 地区报警处理率统计
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuBaoJingTongJiLv> selectGetDqLvTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectGetDqLvTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 企业报警处理率统计
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuBaoJingTongJiLv> selectGetQYLvTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectGetQYLvTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * gps报警明细
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuBaoJingMingXi> selectGetGPSMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectGetGPSMXTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * dms报警明细
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuBaoJingMingXi> selectGetDMSMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectGetDMSMXTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 报警 vehicle明细
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuBaoJingVehicle> selectGetVehicleMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectGetVehicleMXTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 所有报警明细
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuBaoJingMingXi> selectGetAllMXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectGetAllMXTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 根据政府id查询是否包含下级
	 * @param deptId
	 * @return
	 */
	List<ZhengFuBaoJingTongJi> selectGetZFYG(@Param("deptId") String deptId);

	/**
	 * 根据政府id查询地区报警车辆数
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuBaoJingTongJiLv> selectGetZFBJCLS(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 地区报警排名
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuBaoJingTongJiJieSuan> selectGetDQTJPMTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectDQTJPMTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 车辆报警排名
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuBaoJingTongJiJieSuan> selectGetCLTJPMTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectCLTJPMTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

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
	List<ZhengFuRiYunXingTongJi> selectGetCLRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectCLRYXTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 企业日运行情况统计
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuRiYunXingTongJi> selectGetQYRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectQYRYXTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

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
	List<ZhengFuRiskRanking> selectGetDQRiskPMTJ(ZhengFuRiskRankingPage zhengFuRiskRankingPage);
	int selectDQRiskPMTotal(ZhengFuRiskRankingPage zhengFuRiskRankingPage);

	/**
	 * 政府地区进区域报警排名统计
	 * @param zhengFuIntoAreaPage
	 * @return
	 */
	List<ZhengFuIntoArea> selectDQIntoAreaPMTJ(ZhengFuIntoAreaPage zhengFuIntoAreaPage);
	int selectDQIntoAreaPMTotal(ZhengFuIntoAreaPage zhengFuIntoAreaPage);

	/**
	 * 政府企业进区域报警排名统计
	 * @param zhengFuIntoAreaPage
	 * @return
	 */
	List<ZhengFuIntoArea> selectDeptIntoAreaPMTJ(ZhengFuIntoAreaPage zhengFuIntoAreaPage);
	int selectDeptIntoAreaPMTotal(ZhengFuIntoAreaPage zhengFuIntoAreaPage);

	/**
	 * 政府车辆进区域报警排名统计
	 * @param zhengFuIntoAreaPage
	 * @return
	 */
	List<ZhengFuIntoArea> selectDeptCLIntoAreaPMTJ(ZhengFuIntoAreaPage zhengFuIntoAreaPage);
	int selectDeptCLIntoAreaPMTotal(ZhengFuIntoAreaPage zhengFuIntoAreaPage);

	/**
	 * 获取企业车辆数
	 * @param zhengFuIntoAreaPage
	 * @return
	 */
	List<ZhengFuIntoArea> selectDeptVehCount(ZhengFuIntoAreaPage zhengFuIntoAreaPage);

	/**
	 * 获取地区车辆数
	 * @param zhengFuIntoAreaPage
	 * @return
	 */
	List<ZhengFuIntoArea> selectDQVehCount(ZhengFuIntoAreaPage zhengFuIntoAreaPage);

	/**
	 * 运营商日运行统计
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	List<ZhengFuOperatorMonitorTongJi> selectYYSRYXTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectYYSRYXTJTotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 道路运输企业联网联控考核评分
	 * @param
	 * @return
	 */
	List<QiYeLWLKTongJi> selectLWLKTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectLWLKTJotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 服务商联网联控考核评分
	 * @param
	 * @return
	 */
	List<QiYeLWLKTongJi> selectFWSLWLKTJ(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);
	int selectFWSLWLKTJotal(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	/**
	 * 地区车辆总数、上线车辆数、在线车辆数等指标
	 * @param zhengFuBaoJingTongJiJieSuanPage
	 * @return
	 */
	ZhengFuDongTaiJianKong selectGetZFJk(ZhengFuBaoJingTongJiJieSuanPage zhengFuBaoJingTongJiJieSuanPage);

	List<AnbiaoSafetyproductionfileNum> selectTJ(SafetyproductionfileNumPage safetyproductionfileNumPage);
	int selectTotal(SafetyproductionfileNumPage safetyproductionfileNumPage);

}
