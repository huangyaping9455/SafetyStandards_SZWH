package org.springblade.anbiao.SafeInvestment.service;

import org.springblade.anbiao.SafeInvestment.VO.SafeInvestmentVO;
import org.springblade.anbiao.SafeInvestment.VO.SafetyInvestmentDetailsVO;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInput;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInputDetailed;
import org.springblade.anbiao.SafeInvestment.page.SafelInfoPage;


import java.util.List;

/**
 * @author long
 * @create 2022-10-28-11:26
 */
public interface SafeInvestmentService {

//	/**
//	 * 安全列表信息
//	 * @param safeInvestmentDTO
//	 * @return
//	 */
//	List<SafeInvestmentVO> selectList(SafeInvestmentDTO safeInvestmentDTO);

	/**
	 * 安全投入详细信息
	 * @param
	 * @return
	 */
	List<SafetyInvestmentDetailsVO> selectAll(String rid );

	/**
	 * 新增 安全投入信息
	 * @param
	 * @return
	 */
	Boolean insertOne(AnbiaoSafetyInput safetyInput);

	/**
	 * 新增安全投入明细
	 * @param
	 * @return
	 */
	Boolean insert(List<AnbiaoSafetyInputDetailed> inputDetailedList);

	/**
	 * 删除
	 * @param asi_dept_ids
	 * @return
	 */
	Boolean delete(String asi_dept_ids);

	/**
	 * 修改
	 * @param anbiaoSafetyInput
	 * @return
	 */
	Boolean updateSafe(AnbiaoSafetyInput anbiaoSafetyInput);

	SafelInfoPage selectPage(SafelInfoPage safelInfoPage);
}
