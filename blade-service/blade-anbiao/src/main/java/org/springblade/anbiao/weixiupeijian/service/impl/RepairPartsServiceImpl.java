package org.springblade.anbiao.weixiupeijian.service.impl;


import lombok.AllArgsConstructor;
import org.springblade.anbiao.weixiu.DTO.MaintenanceDTO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiupeijian.mapper.RepairPartsMapper;
import org.springblade.anbiao.weixiupeijian.service.RepairPartsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/4 14:28
 */
@Service
@AllArgsConstructor
public class RepairPartsServiceImpl implements RepairPartsService {
	private RepairPartsMapper partsMapper;

	@Override
	public List<FittingsEntity> selectList() {

		return partsMapper.selectList();
	}

//	@Override
//	public List<MaintenanceEntity> selectAll(String rid) {
//		return partsMapper.selectAll(rid);
//	}
//
//	@Override
//	public Boolean insertOne(MaintenanceDTO maintenanceDTO) {
//		return partsMapper.insertOne(maintenanceDTO);
//	}
//
//	@Override
//	public Boolean deleteAccident(String id) {
//		return partsMapper.deleteRepair(id);
//	}
//
//	@Override
//	public Boolean updateAccident(MaintenanceDTO maintenanceDTO) {
//		return partsMapper.updateRepair(maintenanceDTO);
//	}
}
