package org.springblade.anbiao.repairs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStore;
import org.springblade.anbiao.repairs.mapper.AnbiaoSparePartsStoreMapper;
import org.springblade.anbiao.repairs.page.AnbiaoSparePartsStorePage;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePartsStoreService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 备件库管理 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@Service
@AllArgsConstructor
public class AnbiaoSparePartsStoreServiceImpl extends ServiceImpl<AnbiaoSparePartsStoreMapper, AnbiaoSparePartsStore> implements IAnbiaoSparePartsStoreService {

	private AnbiaoSparePartsStoreMapper mapper;

	@Override
	public AnbiaoSparePartsStorePage<AnbiaoSparePartsStore> selectAllPage(AnbiaoSparePartsStorePage anbiaoSparePartsStorePage) {
		Integer total = mapper.selectAllTotal(anbiaoSparePartsStorePage);
		if(anbiaoSparePartsStorePage.getSize()==0){
			if(anbiaoSparePartsStorePage.getTotal()==0){
				anbiaoSparePartsStorePage.setTotal(total);
			}

			if(anbiaoSparePartsStorePage.getTotal()==0){
				return anbiaoSparePartsStorePage;
			}else{
				List<AnbiaoSparePartsStore> repairsInfos = mapper.selectAllPage(anbiaoSparePartsStorePage);
				anbiaoSparePartsStorePage.setRecords(repairsInfos);
				return anbiaoSparePartsStorePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoSparePartsStorePage.getSize()==0){
				pagetotal = total / anbiaoSparePartsStorePage.getSize();
			}else {
				pagetotal = total / anbiaoSparePartsStorePage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoSparePartsStorePage.getCurrent()) {
			anbiaoSparePartsStorePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoSparePartsStorePage.getCurrent() > 1) {
				offsetNo = anbiaoSparePartsStorePage.getSize() * (anbiaoSparePartsStorePage.getCurrent() - 1);
			}
			anbiaoSparePartsStorePage.setTotal(total);
			anbiaoSparePartsStorePage.setOffsetNo(offsetNo);
			List<AnbiaoSparePartsStore> repairsInfos = mapper.selectAllPage(anbiaoSparePartsStorePage);
			anbiaoSparePartsStorePage.setRecords(repairsInfos);
		}
		return anbiaoSparePartsStorePage;
	}

	@Override
	public AnbiaoSparePartsStore selectMaxXuhao(String deptId) {
		return mapper.selectMaxXuhao(deptId);
	}

	@Override
	public List<AnbiaoSparePartsStore> selectByDeptIdList(String deptId) {
		return mapper.selectByDeptIdList(deptId);
	}
}
