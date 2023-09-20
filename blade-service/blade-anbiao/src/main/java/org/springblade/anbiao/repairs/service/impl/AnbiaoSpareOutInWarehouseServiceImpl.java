package org.springblade.anbiao.repairs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareOutInWarehouse;
import org.springblade.anbiao.repairs.mapper.AnbiaoSpareOutInWarehouseMapper;
import org.springblade.anbiao.repairs.page.AnbiaoSpareOutInWarehousePage;
import org.springblade.anbiao.repairs.service.IAnbiaoSpareOutInWarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 出入备件库审核 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@Service
@AllArgsConstructor
public class AnbiaoSpareOutInWarehouseServiceImpl extends ServiceImpl<AnbiaoSpareOutInWarehouseMapper, AnbiaoSpareOutInWarehouse> implements IAnbiaoSpareOutInWarehouseService {

	private AnbiaoSpareOutInWarehouseMapper mapper;

	@Override
	public AnbiaoSpareOutInWarehousePage<AnbiaoSpareOutInWarehouse> selectAllPage(AnbiaoSpareOutInWarehousePage anbiaoSpareOutInWarehousePage) {
		Integer total = mapper.selectAllTotal(anbiaoSpareOutInWarehousePage);
		if(anbiaoSpareOutInWarehousePage.getSize()==0){
			if(anbiaoSpareOutInWarehousePage.getTotal()==0){
				anbiaoSpareOutInWarehousePage.setTotal(total);
			}

			if(anbiaoSpareOutInWarehousePage.getTotal()==0){
				return anbiaoSpareOutInWarehousePage;
			}else{
				List<AnbiaoSpareOutInWarehouse> repairsInfos = mapper.selectAllPage(anbiaoSpareOutInWarehousePage);
				anbiaoSpareOutInWarehousePage.setRecords(repairsInfos);
				return anbiaoSpareOutInWarehousePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoSpareOutInWarehousePage.getSize()==0){
				pagetotal = total / anbiaoSpareOutInWarehousePage.getSize();
			}else {
				pagetotal = total / anbiaoSpareOutInWarehousePage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoSpareOutInWarehousePage.getCurrent()) {
			anbiaoSpareOutInWarehousePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoSpareOutInWarehousePage.getCurrent() > 1) {
				offsetNo = anbiaoSpareOutInWarehousePage.getSize() * (anbiaoSpareOutInWarehousePage.getCurrent() - 1);
			}
			anbiaoSpareOutInWarehousePage.setTotal(total);
			anbiaoSpareOutInWarehousePage.setOffsetNo(offsetNo);
			List<AnbiaoSpareOutInWarehouse> repairsInfos = mapper.selectAllPage(anbiaoSpareOutInWarehousePage);
			anbiaoSpareOutInWarehousePage.setRecords(repairsInfos);
		}
		return anbiaoSpareOutInWarehousePage;
	}

	@Override
	public AnbiaoSpareOutInWarehouse selectMaxXuhao(String deptId) {
		return mapper.selectMaxXuhao(deptId);
	}
}
