package org.springblade.anbiao.weixiucheliang.service.impl;


import org.springblade.anbiao.weixiu.DTO.MaintenanceDTO;
import org.springblade.anbiao.weixiu.VO.MaintenanceVO;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;
import org.springblade.anbiao.weixiucheliang.mapper.MaintenanceMapper;
import org.springblade.anbiao.weixiucheliang.service.MaintenanceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description : 维修车辆
 * @Author : long
 * @Date :2022/11/4 14:32
 */
@Service
public class MaintenanceServiceImpl implements MaintenanceService {
	private MaintenanceMapper partsMapper;

	@Override
	public List<MaintenanceVO> selectList(MaintenancePage maintenancePage) {

		return null;
	}

	@Override
	public List<MaintenanceEntity> selectAll(String rid) {
		return partsMapper.selectAll(rid);
	}

	@Override
	public Boolean insertOne(MaintenanceDTO maintenanceDTO) {
		return partsMapper.insertOne(maintenanceDTO);
	}

	@Override
	public Boolean deleteAccident(String id) {
		return partsMapper.deleteMain(id);
	}

	@Override
	public Boolean updateAccident(MaintenanceDTO maintenanceDTO) {
		return partsMapper.updateMain(maintenanceDTO);
	}
}
