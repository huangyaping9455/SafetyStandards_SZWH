package org.springblade.anbiao.SafeInvestment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.SafeInvestment.VO.SafeInvestmentVO;
import org.springblade.anbiao.SafeInvestment.VO.SafetyInvestmentDetailsVO;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInput;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInputDetailed;
import org.springblade.anbiao.SafeInvestment.mapper.SafeInvestmentMapper;
import org.springblade.anbiao.SafeInvestment.page.SafelInfoPage;
import org.springblade.anbiao.SafeInvestment.service.SafeInvestmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author long
 * @create 2022-10-28-11:28
 */
@Service
@AllArgsConstructor
public class SafeInvestmentServiceImpl implements SafeInvestmentService {

	private SafeInvestmentMapper safeInvestmentMapper;


	/**
	 * 安全投入详细信息
	 * @param
	 * @return
	 */
	@Override
	public List<SafetyInvestmentDetailsVO> selectAll(String rid) {
		return safeInvestmentMapper.selectAll(rid);
	}

	@Override
	public Boolean insertOne(AnbiaoSafetyInput safetyInput) {
		return safeInvestmentMapper.insertOne(safetyInput);
	}

	@Override
	public Boolean insert(List<AnbiaoSafetyInputDetailed> inputDetailedList) {
		return safeInvestmentMapper.insertInput(inputDetailedList);
	}

	@Override
	public Boolean delete(String asi_dept_ids) {
		return safeInvestmentMapper.deleteSafe(asi_dept_ids);
	}

	@Override
	public Boolean updateSafe(AnbiaoSafetyInput anbiaoSafetyInput) {
		return safeInvestmentMapper.updateSafe(anbiaoSafetyInput);
	}

	/**
	 * 安全列表信息 分页
	 *
	 * @param
	 * @return
	 */
	@Override
	public List<SafeInvestmentVO> selectPage(SafelInfoPage safelInfoPage) {
		int pageSize = safelInfoPage.getSize();
		int pageNum = safelInfoPage.getCurrent();
		PageHelper.startPage(pageNum,pageSize);
		List<SafeInvestmentVO> safeInvestmentVOS = safeInvestmentMapper.selectList(safelInfoPage);
		PageInfo<SafeInvestmentVO> pageInfo = new PageInfo(safeInvestmentVOS);
		return pageInfo.getList();
	}
}



