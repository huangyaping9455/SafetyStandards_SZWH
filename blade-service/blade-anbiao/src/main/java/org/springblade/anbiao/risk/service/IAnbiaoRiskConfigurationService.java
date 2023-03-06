package org.springblade.anbiao.risk.service;

import org.springblade.anbiao.risk.entity.AnbiaoRiskConfiguration;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.risk.page.RiskConfigurationPage;
import org.springblade.anbiao.risk.vo.RiskConfigurationVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lmh
 * @since 2023-03-01
 */
public interface IAnbiaoRiskConfigurationService extends IService<AnbiaoRiskConfiguration> {

	RiskConfigurationPage<RiskConfigurationVO> selectPageList(RiskConfigurationPage riskConfigurationPage);

}
