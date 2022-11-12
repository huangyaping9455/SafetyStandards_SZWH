package org.springblade.anbiao.SafeInvestment.service;

import org.springblade.anbiao.SafeInvestment.DTO.SafeInvestmentDTO;
import org.springblade.anbiao.SafeInvestment.VO.SafeAllVO;
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
//	List<SafeInvestmentVO> selectPage(SafeInvestmentDTO safeInvestmentDTO);

	List<AnbiaoSafetyInputDetailed> selectd(SafeInvestmentDTO safeInvestmentDTO);

	/**
	 * 安全投入详细信息
	 * @param
	 * @return
	 */
	SafeAllVO selectAll(SafeInvestmentDTO safeInvestmentDTO );

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
	Boolean insert(AnbiaoSafetyInputDetailed anbiaoSafetyInputDetailed);

	/**
	 * 删除
	 * @param
	 * @return
	 */
	Boolean delete(SafeInvestmentDTO safeInvestmentDTO);

	/**
	 * 修改
	 * @param
	 * @return
	 */
	Boolean updateSafe(SafeInvestmentDTO safeInvestmentDTO);
	Boolean updateSafede(SafetyInvestmentDetailsVO safetyInvestmentDetailsVO);

	SafelInfoPage selectPage(SafelInfoPage safelInfoPage);
}
