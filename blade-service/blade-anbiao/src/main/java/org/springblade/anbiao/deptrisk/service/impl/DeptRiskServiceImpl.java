package org.springblade.anbiao.deptrisk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.deptrisk.entity.DeptRisk;
import org.springblade.anbiao.deptrisk.mapper.DeptRiskMapper;
import org.springblade.anbiao.deptrisk.service.IDeptRiskService;
import org.springblade.anbiao.fullrate.entity.AnbiaoDeptFullRate;
import org.springblade.anbiao.fullrate.mapper.AnbiaoDeptFullRateMapper;
import org.springblade.anbiao.fullrate.page.DeptFullRatePage;
import org.springblade.anbiao.fullrate.service.IAnbiaoDeptFullRateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyp
 * @since 2023-05-15
 */
@Service
@AllArgsConstructor
public class DeptRiskServiceImpl extends ServiceImpl<DeptRiskMapper, DeptRisk> implements IDeptRiskService {

	private DeptRiskMapper deptRiskMapper;

	@Override
	public DeptRisk selectDeptNum(Integer deptId) {
		return deptRiskMapper.selectDeptNum(deptId);
	}

	@Override
	public DeptRisk selectDeptRiskNum(Integer deptId) {
		return deptRiskMapper.selectDeptRiskNum(deptId);
	}

	@Override
	public List<DeptRisk> selectDeptListRiskNum(Integer deptId) {
		return deptRiskMapper.selectDeptListRiskNum(deptId);
	}

	@Override
	public List<DeptRisk> selectDeptAvg(Integer deptId) {
		return deptRiskMapper.selectDeptAvg(deptId);
	}

	@Override
	public DeptRisk selectJsyRiskCount(Integer deptId) {
		return deptRiskMapper.selectJsyRiskCount(deptId);
	}

	@Override
	public DeptRisk selectClRiskCount(Integer deptId) {
		return deptRiskMapper.selectClRiskCount(deptId);
	}

	@Override
	public List<DeptRisk> selectRiskTendency(String beginTime, String endTime, Integer deptId) {
		return deptRiskMapper.selectRiskTendency(beginTime, endTime, deptId);
	}
}
