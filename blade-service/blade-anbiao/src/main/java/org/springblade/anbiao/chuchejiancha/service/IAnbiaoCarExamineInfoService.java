package org.springblade.anbiao.chuchejiancha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.chuchejiancha.page.AnBiaoCheckCarPage;
import org.springblade.anbiao.chuchejiancha.page.AnbiaoCarExamineInfoPage;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoTZVO;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoVO;
import org.springblade.anbiao.chuchejiancha.vo.SafetyCheckMingXiVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
public interface IAnbiaoCarExamineInfoService extends IService<AnbiaoCarExamineInfo> {

	AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoVO> selectCarExamineInfoPage(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);

	AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoVO> selectCarExamineInfoPageTubingend(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);

	List<AnbiaoCarExamineInfoVO> selectAnBiaoCheckCarALLPage(AnBiaoCheckCarPage anbiaoCarExamineInfoPage);

	/**
	 * 日常检查台账
	 * @param anbiaoCarExamineInfoPage
	 * @return
	 */
	AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoTZVO> selectCarExamineInfoTZPage(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);

	List<AnbiaoCarExamineInfoTZVO> selectDeptVeh(AnBiaoCheckCarPage anbiaoCarExamineInfoPage);

	List<AnbiaoCarExamineInfoTZVO>  selectDeptVehExamine(AnBiaoCheckCarPage anbiaoCarExamineInfoPage);

	List<SafetyCheckMingXiVO>  selectSafetyCheckMingXi(SafetyCheckMingXiVO safetyCheckMingXiVO);

	List<SafetyCheckMingXiVO>  selectSafetyCheckScore(SafetyCheckMingXiVO safetyCheckMingXiVO);

	List<AnbiaoCarExamineInfoTZVO> selectDayCarExamine(String deptId, String date);

	List<AnbiaoCarExamineInfoTZVO> selectCarExamineDay(String deptId, String date);

}
