package org.springblade.anbiao.risk.mapper;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfiguration;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.risk.page.RiskDeptConfigurationPage;
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

public interface AnbiaoRiskDeptConfigurationMapper extends BaseMapper<AnbiaoRiskDeptConfiguration> {

	/**
	 * 自定义分页
	 * @param
	 * @return
	 */
	List<RiskDeptConfigurationListVO> selectPageList(RiskDeptConfigurationPage riskDeptConfigurationPage);

	/**
	 * 统计
	 * @param
	 * @return
	 */
	int selectTotal(RiskDeptConfigurationPage riskDeptConfigurationPage);
}
