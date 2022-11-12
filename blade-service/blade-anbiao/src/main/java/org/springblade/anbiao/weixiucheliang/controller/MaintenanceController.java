package org.springblade.anbiao.weixiucheliang.controller;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.weixiu.DTO.FittingDTO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;
import org.springblade.anbiao.weixiucheliang.mapper.MaintenanceMapper;
import org.springblade.anbiao.weixiucheliang.service.MaintenanceService;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static cn.hutool.core.date.DateUtil.now;

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
	public R saList(@RequestBody MaintenancePage maintenancePage){
		MaintenancePage maintenancePage1 = service.selectList(maintenancePage);
		return R.data(maintenancePage1);
	}


	@PostMapping("all")
	public R selectALL(@RequestBody MaintenancePage maintenancePage){
		MaintenanceEntity maintenanceEntities = service.selectAll(maintenancePage);
		List<FittingsEntity> fittingsEntity = service.selectC(maintenancePage);
		maintenanceEntities.setFittingDTOS(fittingsEntity);
		return R.data(maintenanceEntities);
	}

	@PostMapping("insert")
	public R insert(@RequestBody MaintenanceEntity maintenanceDTO){
		R rs = new R();
		Boolean aBoolean1 = service.insertOne(maintenanceDTO);
		if(maintenanceDTO.getMaintainDictId() ==3){
			List<FittingsEntity> fittingDTOS = maintenanceDTO.getFittingDTOS();
			Boolean aBoolean = false;
			for (FittingsEntity list : fittingDTOS) {
				String replace = UUID.randomUUID().toString().replace("-", "");
				FittingDTO fittingDTO = new FittingDTO();
				Long id = maintenanceDTO.getId();
				fittingDTO.setId(replace);
				fittingDTO.setWeihuid(id);
				fittingDTO.setCaozuorenid(maintenanceDTO.getCaozuorenid());
				fittingDTO.setCailiaomingcheng(list.getAwpName());
				fittingDTO.setXinghao(list.getAwpModel());
				fittingDTO.setCaozuoshijian(now());
				fittingDTO.setBeizhu(list.getAwpRemarks());
				fittingDTO.setPeijianID(list.getAwpIds());
				aBoolean = mapper.insertB(fittingDTO);
			}

			if (aBoolean && aBoolean1 ) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("修改成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("修改失败");
			}
		}
		return rs;
	}

	@PostMapping("delete")
	public R delete(@RequestBody MaintenancePage maintenancePage){
		return R.status( service.deleteAccident(maintenancePage));
	}

	@PostMapping("update")
	public R update(@RequestBody MaintenanceEntity maintenanceEntity){
		if(maintenanceEntity.getMaintainDictId() ==3){
			List<FittingsEntity> fittingDTOS = maintenanceEntity.getFittingDTOS();
			Boolean aBoolean = false;
			for (FittingsEntity list : fittingDTOS) {
				FittingDTO fittingDTO = new FittingDTO();
				fittingDTO.setCaozuorenid(maintenanceEntity.getCaozuorenid());
				fittingDTO.setCailiaomingcheng(list.getAwpName());
				fittingDTO.setXinghao(list.getAwpModel());
				fittingDTO.setCaozuoshijian(now());
				fittingDTO.setBeizhu(list.getAwpRemarks());
				fittingDTO.setWeihuid(maintenanceEntity.getId());
				aBoolean = mapper.updateB(fittingDTO);
			}
			if(!aBoolean){
				return R.success("修改失败");
			}
		}
		return R.status(service.updateAccident(maintenanceEntity));
	}
}
