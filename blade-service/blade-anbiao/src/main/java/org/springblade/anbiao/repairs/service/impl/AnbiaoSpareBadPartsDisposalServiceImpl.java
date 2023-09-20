package org.springblade.anbiao.repairs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareBadPartsDisposal;
import org.springblade.anbiao.repairs.mapper.AnbiaoSpareBadPartsDisposalMapper;
import org.springblade.anbiao.repairs.page.AnbiaoSpareBadPartsDisposalPage;
import org.springblade.anbiao.repairs.service.IAnbiaoSpareBadPartsDisposalService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 坏件处理记录 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@Service
@AllArgsConstructor
public class AnbiaoSpareBadPartsDisposalServiceImpl extends ServiceImpl<AnbiaoSpareBadPartsDisposalMapper, AnbiaoSpareBadPartsDisposal> implements IAnbiaoSpareBadPartsDisposalService {

	private AnbiaoSpareBadPartsDisposalMapper mapper;

	@Override
	public AnbiaoSpareBadPartsDisposalPage<AnbiaoSpareBadPartsDisposal> selectAllPage(AnbiaoSpareBadPartsDisposalPage AnbiaoSpareBadPartsDisposalPage) {
		Integer total = mapper.selectAllTotal(AnbiaoSpareBadPartsDisposalPage);
		if(AnbiaoSpareBadPartsDisposalPage.getSize()==0){
			if(AnbiaoSpareBadPartsDisposalPage.getTotal()==0){
				AnbiaoSpareBadPartsDisposalPage.setTotal(total);
			}

			if(AnbiaoSpareBadPartsDisposalPage.getTotal()==0){
				return AnbiaoSpareBadPartsDisposalPage;
			}else{
				List<AnbiaoSpareBadPartsDisposal> repairsInfos = mapper.selectAllPage(AnbiaoSpareBadPartsDisposalPage);
				AnbiaoSpareBadPartsDisposalPage.setRecords(repairsInfos);
				return AnbiaoSpareBadPartsDisposalPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%AnbiaoSpareBadPartsDisposalPage.getSize()==0){
				pagetotal = total / AnbiaoSpareBadPartsDisposalPage.getSize();
			}else {
				pagetotal = total / AnbiaoSpareBadPartsDisposalPage.getSize() + 1;
			}
		}
		if (pagetotal >= AnbiaoSpareBadPartsDisposalPage.getCurrent()) {
			AnbiaoSpareBadPartsDisposalPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (AnbiaoSpareBadPartsDisposalPage.getCurrent() > 1) {
				offsetNo = AnbiaoSpareBadPartsDisposalPage.getSize() * (AnbiaoSpareBadPartsDisposalPage.getCurrent() - 1);
			}
			AnbiaoSpareBadPartsDisposalPage.setTotal(total);
			AnbiaoSpareBadPartsDisposalPage.setOffsetNo(offsetNo);
			List<AnbiaoSpareBadPartsDisposal> repairsInfos = mapper.selectAllPage(AnbiaoSpareBadPartsDisposalPage);
			AnbiaoSpareBadPartsDisposalPage.setRecords(repairsInfos);
		}
		return AnbiaoSpareBadPartsDisposalPage;
	}

	@Override
	public AnbiaoSpareBadPartsDisposal selectMaxXuhao(String deptId) {
		return mapper.selectMaxXuhao(deptId);
	}
}
