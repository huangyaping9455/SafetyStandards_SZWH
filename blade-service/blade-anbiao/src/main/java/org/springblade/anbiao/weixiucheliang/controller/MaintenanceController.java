package org.springblade.anbiao.weixiucheliang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.labor.entity.Labor;
import org.springblade.anbiao.labor.entity.LaborEntity;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.weixiu.entity.FittingEntity;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;
import org.springblade.anbiao.weixiucheliang.mapper.MaintenanceMapper;
import org.springblade.anbiao.weixiucheliang.service.FittingService;
import org.springblade.anbiao.weixiucheliang.service.MaintenanceService;
import org.springblade.core.log.annotation.ApiLog;
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

	private FittingService fittingService;

	@PostMapping("list")
	@ApiLog("查询-维修列表")
	@ApiOperation(value = "查询维修列表", notes = "MaintenancePage ", position = 1)
	public R saList(@RequestBody MaintenancePage maintenancePage){
		MaintenancePage maintenancePage1 = service.selectList(maintenancePage);
		return R.data(maintenancePage1);
	}


	@PostMapping("all")
	@ApiLog("查询-配件")
	@ApiOperation(value = "查询维修", notes = "MaintenancePage ", position = 2)
	public R selectALL(@RequestBody MaintenancePage maintenancePage){
		MaintenanceEntity maintenanceEntities = service.selectAll(maintenancePage);
		List<FittingsEntity> fittingsEntity = service.selectC(maintenancePage);
		maintenanceEntities.setFittingDTOS(fittingsEntity);
		return R.data(maintenanceEntities);
	}

	@PostMapping("insert")
	@ApiLog("添加-配件")
	@ApiOperation(value = "添加维修", notes = "MaintenanceEntity ", position = 3)
	public R insert(@RequestBody MaintenanceEntity maintenanceDTO){
		R rs = new R();
		Boolean aBoolean1 = service.insertOne(maintenanceDTO);
		if(maintenanceDTO.getMaintainDictId() ==3){
			List<FittingsEntity> fittingDTOS = maintenanceDTO.getFittingDTOS();
			Boolean aBoolean = false;
			for (FittingsEntity list : fittingDTOS) {
				String replace = UUID.randomUUID().toString().replace("-", "");
				FittingEntity fittingDTO = new FittingEntity();
				Long id = maintenanceDTO.getId();
				fittingDTO.setId(replace);
				fittingDTO.setWeihuid(id);
				fittingDTO.setCaozuorenid(maintenanceDTO.getCaozuorenid());
				fittingDTO.setCailiaomingcheng(list.getAwpName());
				fittingDTO.setXinghao(list.getAwpModel());
				fittingDTO.setCaozuoshijian(now());
				fittingDTO.setBeizhu(list.getAwpRemarks());
				fittingDTO.setPeijianid(list.getAwpIds());
				aBoolean = mapper.insertB(fittingDTO);
			}

			if (aBoolean && aBoolean1 ) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("新增成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("新增失败");
			}
		}else{
			if ( aBoolean1 ) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("新增成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("新增失败");
			}
		}
		return rs;
	}

	@PostMapping("delete")
	@ApiLog("删除-配件")
	@ApiOperation(value = "删除维修", notes = "MaintenancePage ", position = 4)
	public R delete(@RequestBody MaintenancePage maintenancePage){
		return R.status( service.deleteAccident(maintenancePage));
	}

	@PostMapping("update")
	@ApiLog("修改-配件")
	@ApiOperation(value = "修改维修", notes = "MaintenanceEntity ", position = 5)
	public R update(@RequestBody MaintenanceEntity maintenanceEntity){
		if(maintenanceEntity.getMaintainDictId() ==3){
			R r = new R();
			QueryWrapper<MaintenanceEntity> maintenanceEntityQueryWrapper = new QueryWrapper<>();
			maintenanceEntityQueryWrapper.lambda().eq(MaintenanceEntity::getId, maintenanceEntity.getId());
			MaintenanceEntity maintenanceEntity1 = service.getBaseMapper().selectOne(maintenanceEntityQueryWrapper);
			if (maintenanceEntity1 != null) {
				maintenanceEntity1.setId(maintenanceEntity.getId());
				maintenanceEntity1.setDeptId(maintenanceEntity.getDepID());
				maintenanceEntity1.setDriverId(maintenanceEntity.getDriverId());
				maintenanceEntity1.setVehicleId(maintenanceEntity.getVehicleId());
				maintenanceEntity1.setMaintainDictId(maintenanceEntity.getMaintainDictId());
				maintenanceEntity1.setExpectedCompletion(maintenanceEntity.getExpectedCompletion());
				maintenanceEntity1.setSendDate(maintenanceEntity.getSendDate());
				maintenanceEntity1.setAcbCenterMaintenance(maintenanceEntity.getAcbCenterMaintenance());
				maintenanceEntity1.setAcbAfterMaintenance(maintenanceEntity.getAcbAfterMaintenance());
				maintenanceEntity1.setActualCompletionDate(maintenanceEntity.getActualCompletionDate());
				maintenanceEntity1.setInRangeMileage(maintenanceEntity.getInRangeMileage());
				maintenanceEntity1.setInTheOil(maintenanceEntity1.getInTheOil());
				maintenanceEntity1.setNextMaintenanceDate(maintenanceEntity.getNextMaintenanceDate());
				maintenanceEntity1.setNextMaintenanceMileage(maintenanceEntity.getNextMaintenanceMileage());
				maintenanceEntity1.setMaintenanceDeptName(maintenanceEntity.getMaintenanceDeptName());
				maintenanceEntity1.setFujian(maintenanceEntity.getFujian());
				maintenanceEntity1.setSendRepairPersonId(maintenanceEntity.getSendRepairPersonId());
				maintenanceEntity1.setPickUpVehicleDate(maintenanceEntity.getPickUpVehicleDate());
				maintenanceEntity1.setPickUpVehicleDriverId(maintenanceEntity.getPickUpVehicleDriverId());
				maintenanceEntity1.setCaozuoren(maintenanceEntity.getCaozuoren());
				maintenanceEntity1.setCaozuorenid(maintenanceEntity.getCaozuorenid());
				maintenanceEntity1.setCaozuoshijian(now());
				maintenanceEntity1.setRemark(maintenanceEntity.getRemark());
				maintenanceEntity1.setSubtotalOfMaterialQuantity(maintenanceEntity.getSubtotalOfMaterialQuantity());
				maintenanceEntity1.setSubtotalOfWarrantyItems(maintenanceEntity.getSubtotalOfWarrantyItems());
				maintenanceEntity1.setMaterialSubtotal(maintenanceEntity.getMaterialSubtotal());
				maintenanceEntity1.setCreatetime(now());
				maintenanceEntity1.setCreateperson(maintenanceEntity.getCreateperson());
				maintenanceEntity1.setCreateid(maintenanceEntity.getCreateid());
				maintenanceEntity1.setAcbMaintenanceContent(maintenanceEntity.getAcbMaintenanceContent());
				maintenanceEntity1.setAcbRepairReason(maintenanceEntity.getAcbRepairReason());
				int i = service.getBaseMapper().updateById(maintenanceEntity1);
				if (i > 0) {
					QueryWrapper<FittingEntity> fittingEntityQueryWrapper = new QueryWrapper<>();
					fittingEntityQueryWrapper.lambda().eq(FittingEntity::getWeihuid, maintenanceEntity.getId());
					fittingService.getBaseMapper().delete(fittingEntityQueryWrapper);
					List<FittingsEntity> fittingEntities = maintenanceEntity.getFittingDTOS();
					for (FittingsEntity list : fittingEntities) {
						FittingEntity fitting = new FittingEntity();
						fitting.setWeihuid(maintenanceEntity.getId());
						fitting.setCaozuorenid(maintenanceEntity.getCaozuorenid());
						fitting.setCaozuoren(maintenanceEntity.getCaozuoren());
						fitting.setCailiaomingcheng(list.getAwpName());
						fitting.setPeijianid(list.getPeijianId());
						fitting.setXiaoji(list.getAwpModel());
						fitting.setShuliang(String.valueOf(fittingEntities.size()));
						fitting.setBeizhu(list.getAwpRemarks());
						fitting.setIsdelete(maintenanceEntity.getIsDeleted().toString());
						boolean b = fittingService.save(fitting);
						if (b) {
							r.setMsg("更新成功");
							r.setCode(200);
							r.setSuccess(false);
						} else {
							r.setMsg("更新失败");
							r.setCode(500);
							r.setSuccess(false);
						}

					}
				}
			} else {
				r.setMsg("该数据不存在");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
			return r;
		}
		return R.status(service.updateAccident(maintenanceEntity));
	}
}
