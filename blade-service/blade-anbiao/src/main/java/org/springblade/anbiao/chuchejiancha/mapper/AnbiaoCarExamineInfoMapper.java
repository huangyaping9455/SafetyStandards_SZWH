package org.springblade.anbiao.chuchejiancha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.chuchejiancha.page.AnBiaoCheckCarPage;
import org.springblade.anbiao.chuchejiancha.page.AnbiaoCarExamineInfoPage;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoTZVO;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoVO;
import org.springblade.anbiao.chuchejiancha.vo.SafetyCheckMingXiVO;
import org.springblade.anbiao.labor.VO.LaborMonthVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
public interface AnbiaoCarExamineInfoMapper extends BaseMapper<AnbiaoCarExamineInfo> {

	List<AnbiaoCarExamineInfoVO> selectCarExamineInfoPage(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);
	int selectCarExamineInfoTotal(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);

	List<AnbiaoCarExamineInfoVO> selectCarExamineInfoPageTubingend(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);
	int selectCarExamineInfoTubingendTotal(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);

	List<AnbiaoCarExamineInfoVO> selectAnBiaoCheckCarALLPage(AnBiaoCheckCarPage anbiaoCarExamineInfoPage);

	/**
	 * 日常检查台账
	 * @param anbiaoCarExamineInfoPage
	 * @return
	 */
	List<AnbiaoCarExamineInfoTZVO> selectCarExamineInfoTZPage(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);
	int selectCarExamineInfoTZTotal(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);

	List<AnbiaoCarExamineInfoTZVO> selectDeptVeh(AnBiaoCheckCarPage anbiaoCarExamineInfoPage);

	List<AnbiaoCarExamineInfoTZVO>  selectDeptVehExamine(AnBiaoCheckCarPage anbiaoCarExamineInfoPage);

	List<SafetyCheckMingXiVO>  selectSafetyCheckMingXi(SafetyCheckMingXiVO safetyCheckMingXiVO);

	List<SafetyCheckMingXiVO>  selectSafetyCheckScore(SafetyCheckMingXiVO safetyCheckMingXiVO);

	List<AnbiaoCarExamineInfoTZVO> selectDayCarExamine(String deptId, String date);

	List<AnbiaoCarExamineInfoTZVO> selectCarExamineDay(String deptId, String date);


}
