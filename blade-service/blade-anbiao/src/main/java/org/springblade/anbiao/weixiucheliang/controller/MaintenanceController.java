package org.springblade.anbiao.weixiucheliang.controller;

import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.weixiu.DTO.MaintenanceDTO;
import org.springblade.anbiao.weixiu.VO.MaintenanceVO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;
import org.springblade.anbiao.weixiucheliang.mapper.MaintenanceMapper;
import org.springblade.anbiao.weixiucheliang.service.MaintenanceService;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description :维修车
 * @Author : long
 * @Date :2022/11/4 14:34
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/Repair/")
public class MaintenanceController {

	private MaintenanceService service;

	private MaintenanceMapper mapper;

	@PostMapping("list")
	public R saList(MaintenancePage maintenancePage){
		List<MaintenanceVO> maintenanceVOS = service.selectList(maintenancePage);
		return R.data(maintenanceVOS);
	}


	@PostMapping("all")
	public R selectALL(@Param("rid") String rid){
		List<MaintenanceEntity> maintenanceEntities = service.selectAll(rid);
		return R.data(maintenanceEntities);
	}

	@PostMapping("insert")
	public R insert(MaintenanceDTO maintenanceDTO,FittingsEntity fittingsEntity){
		if(maintenanceDTO.getMaintain_Dict_Id() ==3){
			Boolean aBoolean = mapper.insertB(fittingsEntity);
			if(!aBoolean){
				return R.success("添加失败");
			}
		}
		return R.status(service.insertOne(maintenanceDTO));
	}

	@PostMapping("delete")
	public R delete(@Param("id") String id){
		return R.status( service.deleteAccident(id));
	}

	@PostMapping("update")
	public R update(MaintenanceDTO maintenanceDTO, FittingsEntity fittingsEntity){
		if(maintenanceDTO.getMaintain_Dict_Id() ==3){
			boolean b = mapper.updateB(fittingsEntity);
			if(!b){
				return R.success("修改失败");
			}
		}
		return R.status(service.updateAccident(maintenanceDTO));
	}
}
