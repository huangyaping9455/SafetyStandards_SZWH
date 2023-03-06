package org.springblade.anbiao.risk.mapper;

import org.springblade.anbiao.risk.entity.AnbiaoRiskConfiguration;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.risk.page.RiskConfigurationPage;
import org.springblade.anbiao.risk.page.RiskDeptConfigurationPage;
import org.springblade.anbiao.risk.vo.RiskConfigurationVO;
import org.springblade.anbiao.risk.vo.RiskDeptConfigurationListVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2023-03-01
 */
public interface AnbiaoRiskConfigurationMapper extends BaseMapper<AnbiaoRiskConfiguration> {

	/**
	 * 自定义分页
	 * @param
	 * @return
	 */
	List<RiskConfigurationVO> selectPageList(RiskConfigurationPage riskConfigurationPage);

	/**
	 * 统计
	 * @param
	 * @return
	 */
	int selectTotal(RiskConfigurationPage riskConfigurationPage);
}
