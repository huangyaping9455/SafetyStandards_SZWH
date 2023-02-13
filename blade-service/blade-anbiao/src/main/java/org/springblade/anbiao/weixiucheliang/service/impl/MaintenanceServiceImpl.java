package org.springblade.anbiao.weixiucheliang.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.weixiu.VO.MaintenanceEntityV;
import org.springblade.anbiao.weixiu.VO.MaintenanceTZVO;
import org.springblade.anbiao.weixiu.VO.MaintenanceVO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;
import org.springblade.anbiao.weixiu.page.MaintenanceTZPage;
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
@AllArgsConstructor
public class MaintenanceServiceImpl extends ServiceImpl<MaintenanceMapper,MaintenanceEntity> implements MaintenanceService {
	private MaintenanceMapper partsMapper;

	@Override
	public MaintenancePage selectList(MaintenancePage maintenancePage) {
		int total = partsMapper.selectTotal(maintenancePage);
		Integer safelPagetotal = 0;
		if (maintenancePage.getSize() == 0) {
			if (maintenancePage.getTotal() == 0) {
				maintenancePage.setTotal(total);
			}
			if (maintenancePage.getTotal() == 0) {
				return maintenancePage;
			} else {
				List<MaintenanceVO> maintenanceVOS = partsMapper.selectList(maintenancePage);
				maintenancePage.setRecords(maintenanceVOS);
				return maintenancePage;
			}
		}
		if (total > 0) {
			if (total % maintenancePage.getSize() == 0) {
				safelPagetotal = total / maintenancePage.getSize();
			} else {
				safelPagetotal = total / maintenancePage.getSize() + 1;
			}
		}
		if (safelPagetotal < maintenancePage.getCurrent()) {
			return maintenancePage;
		} else {
			maintenancePage.setPageTotal(safelPagetotal);
			Integer offsetNo = 0;
			if (maintenancePage.getCurrent() > 1) {
				offsetNo = maintenancePage.getSize() * (maintenancePage.getCurrent() - 1);
			}
			maintenancePage.setTotal(total);
			maintenancePage.setOffsetNo(offsetNo);
			List<MaintenanceVO> maintenanceVOS = partsMapper.selectList(maintenancePage);
			maintenancePage.setRecords(maintenanceVOS);
			return maintenancePage;
		}
	}

	@Override
	public MaintenanceEntity selectAll(MaintenancePage maintenancePage) {
		return partsMapper.selectAll(maintenancePage);
	}

	@Override
	public List<FittingsEntity> selectC(MaintenancePage maintenancePage) {
		return partsMapper.selectC(maintenancePage);
	}

	@Override
	public Boolean insertOne(MaintenanceEntity maintenanceDTO) {
		return partsMapper.insertOne(maintenanceDTO);
	}

	@Override
	public Boolean deleteAccident(MaintenancePage maintenancePage) {
		return partsMapper.deleteMain(maintenancePage);
	}

	@Override
	public Boolean updateAccident(MaintenanceEntity maintenanceEntity) {
		return partsMapper.updateMain(maintenanceEntity);
	}

	@Override
	public List<MaintenanceEntityV> selectByDateList(String deptId, String date, String type) {
		return partsMapper.selectByDateList(deptId, date, type);
	}

	@Override
	public MaintenanceTZPage<MaintenanceTZVO> selectTZTJList(MaintenanceTZPage maintenanceTZPage) {
		int total = partsMapper.selectTZTJTotal(maintenanceTZPage);
		Integer safelPagetotal = 0;
		if (maintenanceTZPage.getSize() == 0) {
			if (maintenanceTZPage.getTotal() == 0) {
				maintenanceTZPage.setTotal(total);
			}
			if (maintenanceTZPage.getTotal() == 0) {
				return maintenanceTZPage;
			} else {
				List<MaintenanceTZVO> maintenanceVOS = partsMapper.selectTZTJList(maintenanceTZPage);
				maintenanceTZPage.setRecords(maintenanceVOS);
				return maintenanceTZPage;
			}
		}
		if (total > 0) {
			if (total % maintenanceTZPage.getSize() == 0) {
				safelPagetotal = total / maintenanceTZPage.getSize();
			} else {
				safelPagetotal = total / maintenanceTZPage.getSize() + 1;
			}
		}
		if (safelPagetotal < maintenanceTZPage.getCurrent()) {
			return maintenanceTZPage;
		} else {
			maintenanceTZPage.setPageTotal(safelPagetotal);
			Integer offsetNo = 0;
			if (maintenanceTZPage.getCurrent() > 1) {
				offsetNo = maintenanceTZPage.getSize() * (maintenanceTZPage.getCurrent() - 1);
			}
			maintenanceTZPage.setTotal(total);
			maintenanceTZPage.setOffsetNo(offsetNo);
			List<MaintenanceTZVO> maintenanceVOS = partsMapper.selectTZTJList(maintenanceTZPage);
			maintenanceTZPage.setRecords(maintenanceVOS);
			return maintenanceTZPage;
		}
	}

}
