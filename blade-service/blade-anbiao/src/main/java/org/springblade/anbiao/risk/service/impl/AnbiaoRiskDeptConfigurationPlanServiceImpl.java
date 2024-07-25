package org.springblade.anbiao.risk.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfigurationPlan;
import org.springblade.anbiao.risk.mapper.AnbiaoRiskDeptConfigurationPlanMapper;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDeptConfigurationPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-17
 */
@Service
@AllArgsConstructor
public class AnbiaoRiskDeptConfigurationPlanServiceImpl extends ServiceImpl<AnbiaoRiskDeptConfigurationPlanMapper, AnbiaoRiskDeptConfigurationPlan> implements IAnbiaoRiskDeptConfigurationPlanService {

	private AnbiaoRiskDeptConfigurationPlanMapper deptConfigurationPlanMapper;

	@Override
	public List<AnbiaoRiskDeptConfigurationPlan> selectByList(String Id) {
		return deptConfigurationPlanMapper.selectByList(Id);
	}

	@Override
	public AnbiaoRiskDeptConfigurationPlan selectYujingxiangByName(String deptId) {
		return deptConfigurationPlanMapper.selectYujingxiangByName(deptId);
	}
}
