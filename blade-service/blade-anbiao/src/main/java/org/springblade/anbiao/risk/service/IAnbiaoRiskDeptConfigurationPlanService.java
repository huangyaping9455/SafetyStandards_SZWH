package org.springblade.anbiao.risk.service;

import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfigurationPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.risk.vo.RiskDeptConfigurationListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-06-17
 */
public interface IAnbiaoRiskDeptConfigurationPlanService extends IService<AnbiaoRiskDeptConfigurationPlan> {

	List<AnbiaoRiskDeptConfigurationPlan> selectByList(String Id);

}
