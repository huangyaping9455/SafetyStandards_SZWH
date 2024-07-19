package org.springblade.anbiao.risk.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfiguration;
import org.springblade.anbiao.risk.mapper.AnbiaoRiskDeptConfigurationMapper;
import org.springblade.anbiao.risk.page.RiskDeptConfigurationPage;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDeptConfigurationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class AnbiaoRiskDeptConfigurationServiceImpl extends ServiceImpl<AnbiaoRiskDeptConfigurationMapper, AnbiaoRiskDeptConfiguration> implements IAnbiaoRiskDeptConfigurationService {

	private AnbiaoRiskDeptConfigurationMapper riskDeptConfigurationMapper;

	@Override
	public RiskDeptConfigurationPage<RiskDeptConfigurationListVO> selectPageList(RiskDeptConfigurationPage riskDeptConfigurationPage) {
		Integer total = riskDeptConfigurationMapper.selectTotal(riskDeptConfigurationPage);
		Integer pagetotal = 0;
		if(riskDeptConfigurationPage.getSize()==0){
			if(riskDeptConfigurationPage.getTotal()==0){
				riskDeptConfigurationPage.setTotal(total);
			}
			if(riskDeptConfigurationPage.getTotal()==0){
				return riskDeptConfigurationPage;
			}else {
				List<RiskDeptConfigurationListVO> riskDeptConfigurationListVOS = riskDeptConfigurationMapper.selectPageList(riskDeptConfigurationPage);
				riskDeptConfigurationPage.setRecords(riskDeptConfigurationListVOS);
				return riskDeptConfigurationPage;
			}
		}
		if (total > 0) {
			if(total%riskDeptConfigurationPage.getSize()==0){
				pagetotal = total / riskDeptConfigurationPage.getSize();
			}else {
				pagetotal = total / riskDeptConfigurationPage.getSize() + 1;
			}
		}
		if (pagetotal < riskDeptConfigurationPage.getCurrent()) {
			return riskDeptConfigurationPage;
		} else {
			riskDeptConfigurationPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (riskDeptConfigurationPage.getCurrent() > 1) {
				offsetNo = riskDeptConfigurationPage.getSize() * (riskDeptConfigurationPage.getCurrent() - 1);
			}
			riskDeptConfigurationPage.setTotal(total);
			riskDeptConfigurationPage.setOffsetNo(offsetNo);
			List<RiskDeptConfigurationListVO> riskDeptConfigurationListVOS = riskDeptConfigurationMapper.selectPageList(riskDeptConfigurationPage);
			return (RiskDeptConfigurationPage<RiskDeptConfigurationListVO>) riskDeptConfigurationPage.setRecords(riskDeptConfigurationListVOS);
		}
	}

	@Override
	public List<RiskDeptConfigurationListVO> selectDeptDefault(String deptId) {
		return riskDeptConfigurationMapper.selectDeptDefault(deptId);
	}
}
