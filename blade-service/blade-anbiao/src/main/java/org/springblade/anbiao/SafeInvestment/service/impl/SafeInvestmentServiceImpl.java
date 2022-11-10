package org.springblade.anbiao.SafeInvestment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
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
	public List<SafetyInvestmentDetailsVO> selectAll(String asid_asi_ids) {
		return safeInvestmentMapper.selectAll(asid_asi_ids);
	}

	@Override
	public Boolean insertOne(AnbiaoSafetyInput safetyInput) {
		return safeInvestmentMapper.insertOne(safetyInput);
	}

	@Override
	public Boolean insert(AnbiaoSafetyInputDetailed anbiaoSafetyInputDetailed) {
		return safeInvestmentMapper.insertInput(anbiaoSafetyInputDetailed);
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
	public SafelInfoPage selectPage(SafelInfoPage safelInfoPage) {
		int total = safeInvestmentMapper.selectTotal(safelInfoPage);
		Integer safelPagetotal = 0;
		if (safelInfoPage.getSize() == 0) {
			if (safelInfoPage.getTotal() == 0) {
				safelInfoPage.setTotal(total);
			}
			if (safelInfoPage.getTotal() == 0) {
				return safelInfoPage;
			} else {
				List<SafeInvestmentVO> safeInvestmentVOS1 = safeInvestmentMapper.selectList(safelInfoPage);
				safelInfoPage.setRecords(safeInvestmentVOS1);
				return safelInfoPage;
			}
		}
		if (total > 0) {
			if (total % safelInfoPage.getSize() == 0) {
				safelPagetotal = total / safelInfoPage.getSize();
			} else {
				safelPagetotal = total / safelInfoPage.getSize() + 1;
			}
		}
		if (safelPagetotal < safelInfoPage.getCurrent()) {
			return safelInfoPage;
		} else {
			safelInfoPage.setPageTotal(safelPagetotal);
			Integer offsetNo = 0;
			if (safelInfoPage.getCurrent() > 1) {
				offsetNo = safelInfoPage.getSize() * (safelInfoPage.getCurrent() - 1);
			}
			safelInfoPage.setTotal(total);
			safelInfoPage.setOffsetNo(offsetNo);
			List<SafeInvestmentVO> safeInvestmentVOS1 = safeInvestmentMapper.selectList(safelInfoPage);
			safelInfoPage.setRecords(safeInvestmentVOS1);
			return safelInfoPage;
		}
	}

}



