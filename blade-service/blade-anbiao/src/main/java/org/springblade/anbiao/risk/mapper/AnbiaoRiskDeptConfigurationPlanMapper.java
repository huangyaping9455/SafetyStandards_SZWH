package org.springblade.anbiao.risk.mapper;

import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfigurationPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-06-17
 */
public interface AnbiaoRiskDeptConfigurationPlanMapper extends BaseMapper<AnbiaoRiskDeptConfigurationPlan> {

	List<AnbiaoRiskDeptConfigurationPlan> selectByList(String Id);


}
