package org.springblade.anbiao.repairs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStorePerson;
import org.springblade.anbiao.repairs.mapper.AnbiaoSparePartsStorePersonMapper;
import org.springblade.anbiao.repairs.page.AnbiaoSparePartsStorePersonPage;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePartsStorePersonService;
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
public class AnbiaoSparePartsStorePersonServiceImpl extends ServiceImpl<AnbiaoSparePartsStorePersonMapper, AnbiaoSparePartsStorePerson> implements IAnbiaoSparePartsStorePersonService {

	private AnbiaoSparePartsStorePersonMapper mapper;

	@Override
	public AnbiaoSparePartsStorePersonPage<AnbiaoSparePartsStorePerson> selectAllPage(AnbiaoSparePartsStorePersonPage anbiaoSparePartsStorePersonPage) {
		Integer total = mapper.selectAllTotal(anbiaoSparePartsStorePersonPage);
		if(anbiaoSparePartsStorePersonPage.getSize()==0){
			if(anbiaoSparePartsStorePersonPage.getTotal()==0){
				anbiaoSparePartsStorePersonPage.setTotal(total);
			}

			if(anbiaoSparePartsStorePersonPage.getTotal()==0){
				return anbiaoSparePartsStorePersonPage;
			}else{
				List<AnbiaoSparePartsStorePerson> repairsInfos = mapper.selectAllPage(anbiaoSparePartsStorePersonPage);
				anbiaoSparePartsStorePersonPage.setRecords(repairsInfos);
				return anbiaoSparePartsStorePersonPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoSparePartsStorePersonPage.getSize()==0){
				pagetotal = total / anbiaoSparePartsStorePersonPage.getSize();
			}else {
				pagetotal = total / anbiaoSparePartsStorePersonPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoSparePartsStorePersonPage.getCurrent()) {
			anbiaoSparePartsStorePersonPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoSparePartsStorePersonPage.getCurrent() > 1) {
				offsetNo = anbiaoSparePartsStorePersonPage.getSize() * (anbiaoSparePartsStorePersonPage.getCurrent() - 1);
			}
			anbiaoSparePartsStorePersonPage.setTotal(total);
			anbiaoSparePartsStorePersonPage.setOffsetNo(offsetNo);
			List<AnbiaoSparePartsStorePerson> repairsInfos = mapper.selectAllPage(anbiaoSparePartsStorePersonPage);
			anbiaoSparePartsStorePersonPage.setRecords(repairsInfos);
		}
		return anbiaoSparePartsStorePersonPage;
	}

	@Override
	public AnbiaoSparePartsStorePerson selectMaxXuhao(String deptId) {
		return mapper.selectMaxXuhao(deptId);
	}
}
