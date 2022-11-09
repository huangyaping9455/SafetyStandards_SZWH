package org.springblade.anbiao.weixiucheliang.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springblade.anbiao.weixiu.DTO.MaintenanceDTO;
import org.springblade.anbiao.weixiu.VO.MaintenanceVO;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;
import org.springblade.anbiao.weixiucheliang.mapper.MaintenanceMapper;
import org.springblade.anbiao.weixiucheliang.service.MaintenanceService;

import java.util.List;

/**
 * @Description : 维修车辆
 * @Author : long
 * @Date :2022/11/4 14:32
 */
public class MaintenanceServiceImpl implements MaintenanceService {
//	private MaintenanceMapper partsMapper;
//
//	@Override
//	public List<MaintenanceVO> selectList(MaintenancePage maintenancePage) {
//		int pageSize = maintenancePage.getSize();
//		int pageNum = maintenancePage.getCurrent();
//		PageHelper.startPage(pageNum,pageSize);
//		List<MaintenanceVO> maintenanceVOS = partsMapper.selectList(maintenancePage);
//		PageInfo<MaintenanceVO> pageInfo = new PageInfo(maintenanceVOS);
//		return pageInfo.getList();
//	}
//
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
//		return partsMapper.deleteMain(id);
//	}
//
//	@Override
//	public Boolean updateAccident(MaintenanceDTO maintenanceDTO) {
//		return partsMapper.updateMain(maintenanceDTO);
//	}
}
