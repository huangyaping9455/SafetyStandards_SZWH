package org.springblade.anbiao.risk.service;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfiguration;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.risk.page.RiskDeptConfigurationPage;
import org.springblade.anbiao.risk.vo.RiskDeptConfigurationListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lmh
 * @since 2023-03-01
 */
public interface IAnbiaoRiskDeptConfigurationService extends IService<AnbiaoRiskDeptConfiguration> {

	RiskDeptConfigurationPage<RiskDeptConfigurationListVO> selectPageList(RiskDeptConfigurationPage riskDeptConfigurationPage);
}
