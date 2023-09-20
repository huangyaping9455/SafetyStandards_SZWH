package org.springblade.anbiao.repairs.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStorePerson;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePersonApplyForAudit;
import org.springblade.anbiao.repairs.mapper.AnbiaoSparePersonApplyForAuditMapper;
import org.springblade.anbiao.repairs.page.AnbiaoSparePersonApplyForAuditPage;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePersonApplyForAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@Service
@AllArgsConstructor
public class AnbiaoSparePersonApplyForAuditServiceImpl extends ServiceImpl<AnbiaoSparePersonApplyForAuditMapper, AnbiaoSparePersonApplyForAudit> implements IAnbiaoSparePersonApplyForAuditService {

	private AnbiaoSparePersonApplyForAuditMapper mapper;

	@Override
	public AnbiaoSparePersonApplyForAuditPage<AnbiaoSparePersonApplyForAudit> selectAllPage(AnbiaoSparePersonApplyForAuditPage anbiaoSparePersonApplyForAuditPage) {
		Integer total = mapper.selectAllTotal(anbiaoSparePersonApplyForAuditPage);
		if(anbiaoSparePersonApplyForAuditPage.getSize()==0){
			if(anbiaoSparePersonApplyForAuditPage.getTotal()==0){
				anbiaoSparePersonApplyForAuditPage.setTotal(total);
			}

			if(anbiaoSparePersonApplyForAuditPage.getTotal()==0){
				return anbiaoSparePersonApplyForAuditPage;
			}else{
				List<AnbiaoSparePersonApplyForAudit> repairsInfos = mapper.selectAllPage(anbiaoSparePersonApplyForAuditPage);
				anbiaoSparePersonApplyForAuditPage.setRecords(repairsInfos);
				return anbiaoSparePersonApplyForAuditPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoSparePersonApplyForAuditPage.getSize()==0){
				pagetotal = total / anbiaoSparePersonApplyForAuditPage.getSize();
			}else {
				pagetotal = total / anbiaoSparePersonApplyForAuditPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoSparePersonApplyForAuditPage.getCurrent()) {
			anbiaoSparePersonApplyForAuditPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoSparePersonApplyForAuditPage.getCurrent() > 1) {
				offsetNo = anbiaoSparePersonApplyForAuditPage.getSize() * (anbiaoSparePersonApplyForAuditPage.getCurrent() - 1);
			}
			anbiaoSparePersonApplyForAuditPage.setTotal(total);
			anbiaoSparePersonApplyForAuditPage.setOffsetNo(offsetNo);
			List<AnbiaoSparePersonApplyForAudit> repairsInfos = mapper.selectAllPage(anbiaoSparePersonApplyForAuditPage);
			anbiaoSparePersonApplyForAuditPage.setRecords(repairsInfos);
		}
		return anbiaoSparePersonApplyForAuditPage;
	}

	@Override
	public AnbiaoSparePersonApplyForAudit selectMaxXuhao(String deptId) {
		return mapper.selectMaxXuhao(deptId);
	}
}
