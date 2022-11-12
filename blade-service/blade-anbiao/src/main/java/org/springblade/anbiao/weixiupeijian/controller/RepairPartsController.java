package org.springblade.anbiao.weixiupeijian.controller;

import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.weixiu.DTO.MaintenanceDTO;
import org.springblade.anbiao.weixiu.VO.MaintenanceVO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;
import org.springblade.anbiao.weixiupeijian.service.RepairPartsService;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/4 14:30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/Reapairs/")
public class RepairPartsController {
	@Autowired
	private RepairPartsService service;

	@PostMapping("list")
	public R saList(){
		List<FittingsEntity> maintenanceVOS = service.selectList();
		return R.data(maintenanceVOS);
	}


//	@PostMapping("all")
//	public R selectALL(@Param("rid") String rid){
//		List<MaintenanceEntity> maintenanceEntities = service.selectAll(rid);
//		return R.data(maintenanceEntities);
//	}
//
//	@PostMapping("insert")
//	public R insert(MaintenanceDTO maintenanceDTO){
//		return R.status(service.insertOne(maintenanceDTO));
//	}
//
//	@PostMapping("delete")
//	public R delete(@Param("id") String id){
//		return R.status( service.deleteAccident(id));
//	}
//
//	@PostMapping("update")
//	public R update(MaintenanceDTO maintenanceDTO){
//		return R.status(service.updateAccident(maintenanceDTO));
//	}
}
