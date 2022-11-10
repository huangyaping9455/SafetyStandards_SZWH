package org.springblade.anbiao.SafeInvestment.mapper;

import org.springblade.anbiao.SafeInvestment.DTO.SafeInvestmentDTO;
import org.springblade.anbiao.SafeInvestment.VO.SafeInvestmentVO;
import org.springblade.anbiao.SafeInvestment.VO.SafetyInvestmentDetailsVO;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInput;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInputDetailed;
import org.springblade.anbiao.SafeInvestment.page.SafelInfoPage;

import java.util.List;

/**
 * @author long
 * @create 2022-10-28-11:28
 */
public interface SafeInvestmentMapper {

	/**
	 * 安全列表
	 * @param safelInfoPage
	 * @return
	 */
	List<SafeInvestmentVO> selectList(SafelInfoPage safelInfoPage);
	int selectTotal(SafelInfoPage safelInfoPage);

	/**
	 * 安全投入详细信息
	 *
	 * @param
	 * @return
	 */
	List<SafetyInvestmentDetailsVO> selectAll(String asid_asi_ids );


	/**
	 * 新增安全投入详细信息
	 * @return
	 */
	Boolean insertOne(AnbiaoSafetyInput anbiaoSafetyInput);


	/**
	 * 新增安全投入明细
	 * @param
	 * @return
	 */
	Boolean insertInput(AnbiaoSafetyInputDetailed anbiaoSafetyInputDetailed);

	/**
	 * 修改安全投入列表  后台管理
	 * @param anbiaoSafetyInput
	 * @return
	 */
	Boolean updateSafe(AnbiaoSafetyInput anbiaoSafetyInput);

	/**
	 * 删除
	 * @param asi_dept_ids
	 * @return
	 */
	Boolean deleteSafe(String asi_dept_ids);
}
