package org.springblade.anbiao.risk.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.risk.entity.AnbiaoRiskConfiguration;
import org.springblade.anbiao.risk.mapper.AnbiaoRiskConfigurationMapper;
import org.springblade.anbiao.risk.page.RiskConfigurationPage;
import org.springblade.anbiao.risk.page.RiskDeptConfigurationPage;
import org.springblade.anbiao.risk.service.IAnbiaoRiskConfigurationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.anbiao.risk.vo.RiskConfigurationVO;
import org.springblade.anbiao.risk.vo.RiskDeptConfigurationListVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lmh
 * @since 2023-03-01
 */
@Service
@AllArgsConstructor
public class AnbiaoRiskConfigurationServiceImpl extends ServiceImpl<AnbiaoRiskConfigurationMapper, AnbiaoRiskConfiguration> implements IAnbiaoRiskConfigurationService {

	private AnbiaoRiskConfigurationMapper riskConfigurationMapper;

	@Override
	public RiskConfigurationPage<RiskConfigurationVO> selectPageList(RiskConfigurationPage riskConfigurationPage) {
		Integer total = riskConfigurationMapper.selectTotal(riskConfigurationPage);
		Integer pagetotal = 0;
		if(riskConfigurationPage.getSize()==0){
			if(riskConfigurationPage.getTotal()==0){
				riskConfigurationPage.setTotal(total);
			}
			if(riskConfigurationPage.getTotal()==0){
				return riskConfigurationPage;
			}else {
				List<RiskConfigurationVO> riskConfigurationVOS = riskConfigurationMapper.selectPageList(riskConfigurationPage);
				riskConfigurationPage.setRecords(riskConfigurationVOS);
				return riskConfigurationPage;
			}
		}
		if (total > 0) {
			if(total%riskConfigurationPage.getSize()==0){
				pagetotal = total / riskConfigurationPage.getSize();
			}else {
				pagetotal = total / riskConfigurationPage.getSize() + 1;
			}
		}
		if (pagetotal < riskConfigurationPage.getCurrent()) {
			return riskConfigurationPage;
		} else {
			riskConfigurationPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (riskConfigurationPage.getCurrent() > 1) {
				offsetNo = riskConfigurationPage.getSize() * (riskConfigurationPage.getCurrent() - 1);
			}
			riskConfigurationPage.setTotal(total);
			riskConfigurationPage.setOffsetNo(offsetNo);
			List<RiskConfigurationVO> riskConfigurationVOS = riskConfigurationMapper.selectPageList(riskConfigurationPage);
			return (RiskConfigurationPage<RiskConfigurationVO>) riskConfigurationPage.setRecords(riskConfigurationVOS);
		}
	}
}
