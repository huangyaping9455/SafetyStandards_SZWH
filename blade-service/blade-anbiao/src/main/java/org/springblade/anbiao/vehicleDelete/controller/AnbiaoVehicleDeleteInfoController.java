package org.springblade.anbiao.vehicleDelete.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.VehIccChange.entity.TsVehIccidChangeRecord;
import org.springblade.anbiao.VehIccChange.service.ITsVehIccidChangeRecordService;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.anbiao.vehicleDelete.entity.AnbiaoVehicleDeleteInfo;
import org.springblade.anbiao.vehicleDelete.page.VehicleDeletePage;
import org.springblade.anbiao.vehicleDelete.service.IAnbiaoVehicleDeleteInfoService;
import org.springblade.common.tool.StringUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *车辆删除日志记录
 * @author author
 * @since 2023-11-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/vehicleDeleteInfo")
@Api(value = "车辆删除日志记录管理", tags = "车辆删除日志记录管理")
public class AnbiaoVehicleDeleteInfoController {

	private IAnbiaoVehicleDeleteInfoService vehicleDeleteInfoService;

	private IVehicleService vehicleService;

	private ITsVehIccidChangeRecordService vehIccidChangeRecordService;

	@PostMapping("/insert")
	@ApiLog("车辆删除日志记录-新增")
	@ApiOperation(value = "车辆删除日志记录-新增", notes = "传入AnbiaoVehicleDeleteInfo", position = 1)
	public R insert(@RequestBody AnbiaoVehicleDeleteInfo vehicleDeleteInfo, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		boolean ii = false;
		//根据deptIds字符串分别截取
		String[] vehids_idsss = vehicleDeleteInfo.getVdVehId().split(",");
		//去除素组中重复的数组
		List<String> vehids_listid = new ArrayList<String>();
		for (int i=0; i<vehids_idsss.length; i++) {
			if(!vehids_listid.contains(vehids_idsss[i])) {
				vehids_listid.add(vehids_idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] vehids_idss= vehids_listid.toArray(new String[1]);
		if(vehids_idss.length > 0) {
			for (int i = 0; i < vehids_idss.length; i++) {
				vehicleDeleteInfo.setVdDeptId(vehicleDeleteInfo.getVdDeptId());
				QueryWrapper<AnbiaoVehicleDeleteInfo> dangerQueryWrapper = new QueryWrapper<AnbiaoVehicleDeleteInfo>();
//				dangerQueryWrapper.lambda().eq(AnbiaoVehicleDeleteInfo::getVdDeptId, vehicleDeleteInfo.getVdDeptId());
				dangerQueryWrapper.lambda().eq(AnbiaoVehicleDeleteInfo::getVdVehId, vehids_idss[i]);
				dangerQueryWrapper.lambda().eq(AnbiaoVehicleDeleteInfo::getVdStatus, 0);
				AnbiaoVehicleDeleteInfo deail = vehicleDeleteInfoService.getBaseMapper().selectOne(dangerQueryWrapper);
				if(deail == null){
					if(user != null){
						vehicleDeleteInfo.setVdCreatename(user.getUserName());
						vehicleDeleteInfo.setVdCreateid(user.getUserId());
					}
					VehicleVO vehicleVO = vehicleService.selectByKey(vehids_idss[i]);
					if(vehicleVO != null){
						vehicleDeleteInfo.setVdVehPlate(vehicleVO.getCheliangpaizhao());
						vehicleDeleteInfo.setVdVehColor(vehicleVO.getChepaiyanse());
						vehicleDeleteInfo.setVdDeptId(vehicleVO.getDeptId());
					}
					vehicleDeleteInfo.setVdDate(DateUtil.now());
					vehicleDeleteInfo.setVdCreatetime(DateUtil.now());
					vehicleDeleteInfo.setVdStatus(0);
					vehicleDeleteInfo.setVdVehId(vehids_idss[i]);
					ii = vehicleDeleteInfoService.save(vehicleDeleteInfo);
					if(ii){
						//更新车辆资料
						Vehicle vehicle = new Vehicle();
						vehicle.setId(vehids_idss[i]);
						vehicle.setIsdel(1);
						if(user != null){
							vehicle.setCaozuoren(user.getUserName());
							vehicle.setCaozuorenid(user.getUserId());
						}
						vehicle.setCaozuoshijian(LocalDateTime.now());
						ii = vehicleService.deleteVehicle(vehicle.getIsdel(),vehicle.getCaozuoren(),vehicle.getCaozuorenid().toString(),vehicle.getId(),DateUtil.now());
						if(ii){
							r.setMsg("新增成功");
							r.setCode(200);
							r.setSuccess(true);
						}else{
							r.setMsg("新增失败");
							r.setCode(500);
							r.setSuccess(false);
						}
					}else{
						r.setMsg("新增失败");
						r.setCode(500);
						r.setSuccess(false);
					}
				}else{
					r.setMsg("该数据已添加");
					r.setCode(500);
					r.setSuccess(false);
				}
			}
			return r;
		}else{
			r.setMsg("暂无数据");
			r.setCode(200);
			r.setSuccess(true);
		}
		return r;
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("车辆删除日志记录-分页")
	@ApiOperation(value = "车辆删除日志记录-分页", notes = "传入VehicleDeletePage", position = 2)
	public R<VehicleDeletePage<AnbiaoVehicleDeleteInfo>> list(@RequestBody VehicleDeletePage vehicleDeletePage,BladeUser user) {
		R r = new R();
		if(user == null){
			r.setMsg("未授权");
			r.setCode(500);
			return r;
		}
		VehicleDeletePage<AnbiaoVehicleDeleteInfo> pages = vehicleDeleteInfoService.selectTJPage(vehicleDeletePage);
		return R.data(pages);
	}

	@PostMapping("/update")
	@ApiLog("车辆删除日志记录-还原")
	@ApiOperation(value = "车辆删除日志记录-还原", notes = "传入AnbiaoVehicleDeleteInfo", position = 3)
	public R update(@RequestBody AnbiaoVehicleDeleteInfo vehicleDeleteInfo, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		boolean ii = false;
		//根据deptIds字符串分别截取
		String[] vehids_idsss = vehicleDeleteInfo.getVdVehId().split(",");
		//去除素组中重复的数组
		List<String> vehids_listid = new ArrayList<String>();
		for (int i=0; i<vehids_idsss.length; i++) {
			if(!vehids_listid.contains(vehids_idsss[i])) {
				vehids_listid.add(vehids_idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] vehids_idss= vehids_listid.toArray(new String[1]);
		if(vehids_idss.length > 0) {
			for (int i = 0; i < vehids_idss.length; i++) {
				vehicleDeleteInfo.setVdDeptId(vehicleDeleteInfo.getVdDeptId());
				QueryWrapper<AnbiaoVehicleDeleteInfo> dangerQueryWrapper = new QueryWrapper<AnbiaoVehicleDeleteInfo>();
				dangerQueryWrapper.lambda().eq(AnbiaoVehicleDeleteInfo::getVdVehId, vehids_idss[i]);
				dangerQueryWrapper.lambda().eq(AnbiaoVehicleDeleteInfo::getVdStatus, 0);
				AnbiaoVehicleDeleteInfo deail = vehicleDeleteInfoService.getBaseMapper().selectOne(dangerQueryWrapper);
				if(deail != null){
					if(user != null){
						vehicleDeleteInfo.setVdUpdatename(user.getUserName());
						vehicleDeleteInfo.setVdUpdateid(user.getUserId());
					}
					vehicleDeleteInfo.setVdUpdatetime(DateUtil.now());
					vehicleDeleteInfo.setVdStatus(1);
					vehicleDeleteInfo.setVdId(deail.getVdId());
					ii = vehicleDeleteInfoService.updateById(vehicleDeleteInfo);
					if(ii){
						Vehicle vehicle = new Vehicle();
						vehicle.setId(vehids_idss[i]);
						vehicle.setIsdel(0);
						if(user != null){
							vehicle.setCaozuoren(user.getUserName());
							vehicle.setCaozuorenid(user.getUserId());
						}
						vehicle.setCaozuoshijian(LocalDateTime.now());
						ii = vehicleService.deleteVehicle(vehicle.getIsdel(),vehicle.getCaozuoren(),vehicle.getCaozuorenid().toString(),vehicle.getId(),DateUtil.now());
						if(ii){
							r.setMsg("还原成功");
							r.setCode(200);
							r.setSuccess(true);
						}else{
							r.setMsg("还原失败");
							r.setCode(500);
							r.setSuccess(false);
						}
					}else{
						r.setMsg("还原失败");
						r.setCode(500);
						r.setSuccess(false);
					}
				}else{
					r.setMsg("该数据不存在");
					r.setCode(500);
					r.setSuccess(false);
				}
			}
			return r;
		}else{
			r.setMsg("暂无数据");
			r.setCode(200);
			r.setSuccess(true);
		}
		return r;
	}

	@PostMapping("/updateVeh")
	@ApiLog("车辆更新车牌、车牌颜色、终端号、卡号等信息的变更记录-新增")
	@ApiOperation(value = "车辆更新车牌、车牌颜色、终端号、卡号等信息的变更记录-新增", notes = "传入AnbiaoVehicleDeleteInfo", position = 4)
	public R updateVeh(@RequestBody AnbiaoVehicleDeleteInfo vehicleDeleteInfo, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		boolean ii = false;
		if(vehicleDeleteInfo.getVdStatus() == null){
			vehicleDeleteInfo.setVdStatus(2);
		}
		if(StringUtils.isEmpty(vehicleDeleteInfo.getVdCreatetime())){
			vehicleDeleteInfo.setVdCreatetime(DateUtil.now());
		}

		if(StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewVehPlate())){
			VehicleVO vehicleVO = null;
			if(StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewVehColor())){
				vehicleVO = vehicleService.selectCPYS(vehicleDeleteInfo.getVdNewVehPlate(),vehicleDeleteInfo.getVdNewVehColor());
			}else{
				vehicleVO = vehicleService.selectCPYS(vehicleDeleteInfo.getVdNewVehPlate(),vehicleDeleteInfo.getVdVehColor());
			}
			if(vehicleVO!=null){
				r.setMsg(vehicleVO.getCheliangpaizhao()+"该车已存在");
				r.setCode(500);
				return r;
			}
		}

		VehicleVO vehicleVO1 = vehicleService.selectByZongDuan(vehicleDeleteInfo.getVdNewZhongduanhao());
		if(vehicleVO1 !=null ){
			r.setMsg(vehicleVO1.getZongduanid()+"该终端ID已存在");
			r.setCode(500);
			return r;
		}
		vehicleDeleteInfo.setVdDeptId(vehicleDeleteInfo.getVdDeptId());
		QueryWrapper<AnbiaoVehicleDeleteInfo> dangerQueryWrapper = new QueryWrapper<AnbiaoVehicleDeleteInfo>();
		dangerQueryWrapper.lambda().eq(AnbiaoVehicleDeleteInfo::getVdVehId, vehicleDeleteInfo.getVdVehId());
		dangerQueryWrapper.lambda().eq(AnbiaoVehicleDeleteInfo::getVdStatus, 2);
		dangerQueryWrapper.lambda().eq(AnbiaoVehicleDeleteInfo::getVdCreatetime, vehicleDeleteInfo.getVdCreatetime());
		AnbiaoVehicleDeleteInfo deail = vehicleDeleteInfoService.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null){
			if(user != null){
				vehicleDeleteInfo.setVdCreatename(user.getUserName());
				vehicleDeleteInfo.setVdCreateid(user.getUserId());
			}
			VehicleVO vehicleVO = vehicleService.selectByKey(vehicleDeleteInfo.getVdVehId());
			if(vehicleVO != null){
				vehicleDeleteInfo.setVdVehPlate(vehicleVO.getCheliangpaizhao());
				vehicleDeleteInfo.setVdVehColor(vehicleVO.getChepaiyanse());
				vehicleDeleteInfo.setVdDeptId(vehicleVO.getDeptId());
			}
			vehicleDeleteInfo.setVdDate(DateUtil.now());
			vehicleDeleteInfo.setVdCreatetime(DateUtil.now());
			vehicleDeleteInfo.setVdStatus(2);
			ii = vehicleDeleteInfoService.save(vehicleDeleteInfo);
			if(ii){
				//icc记录表
				String msg = "";
				TsVehIccidChangeRecord vehIccidChangeRecord = new TsVehIccidChangeRecord();
				vehIccidChangeRecord.setVehId(vehicleDeleteInfo.getVdVehId());
				if(StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewSim())){
					if(StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewSim()) && StringUtils.isNotEmpty(vehicleDeleteInfo.getVdSim())){
						msg = "将原SIM卡号："+vehicleDeleteInfo.getVdSim()+"更新为："+vehicleDeleteInfo.getVdNewSim();
					}else{
						msg = "更新SIM卡号为："+vehicleDeleteInfo.getVdNewSim();
					}
				}
				if(StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewVehPlate())){
					msg = "将原车辆牌照："+vehicleDeleteInfo.getVdVehPlate()+"更新为："+vehicleDeleteInfo.getVdNewVehPlate();
				}
				if(StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewVehColor())){
					msg = "将原车牌颜色："+vehicleDeleteInfo.getVdVehColor()+"更新为："+vehicleDeleteInfo.getVdNewVehColor();
				}
				if(StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewZhongduanhao())){
					msg = "将原终端号："+vehicleDeleteInfo.getVdZhongduanhao()+"更新为："+vehicleDeleteInfo.getVdNewZhongduanhao();
				}
				//更新车辆资料
				vehIccidChangeRecord.setIccid(msg);
				vehIccidChangeRecord.setCreateBy(user.getUserName());
				vehIccidChangeRecord.setCreateTime(DateUtil.now());
				vehIccidChangeRecord.setCreateUserid(user.getUserId());
				vehIccidChangeRecordService.save(vehIccidChangeRecord);
				Vehicle vehicle = new Vehicle();
				vehicle.setId(vehicleDeleteInfo.getVdVehId());
				if (StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewVehPlate()) && vehicleDeleteInfo.getVdNewVehPlate() != "null"){
					vehicle.setCheliangpaizhao(vehicleDeleteInfo.getVdNewVehPlate());
				}
				if (StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewVehColor()) && vehicleDeleteInfo.getVdNewVehColor() != "null"){
					vehicle.setChepaiyanse(vehicleDeleteInfo.getVdNewVehColor());
				}
				if (StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewSim()) && vehicleDeleteInfo.getVdNewSim() != "null"){
					vehicle.setSimnum(vehicleDeleteInfo.getVdNewSim());
				}
				if (StringUtils.isNotEmpty(vehicleDeleteInfo.getVdNewZhongduanhao()) && vehicleDeleteInfo.getVdNewZhongduanhao() != "null"){
					vehicle.setZongduanid(vehicleDeleteInfo.getVdNewZhongduanhao());
				}
				if(user != null){
					vehicle.setCaozuoren(user.getUserName());
					vehicle.setCaozuorenid(user.getUserId());
				}
				vehicle.setCaozuoshijian(LocalDateTime.now());
				ii = vehicleService.updateById(vehicle);
				if(ii){
					r.setMsg("更新成功");
					r.setCode(200);
					r.setSuccess(true);
				}else{
					r.setMsg("更新失败");
					r.setCode(500);
					r.setSuccess(false);
				}
			}else{
				r.setMsg("更新失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}else{
			r.setMsg("该数据已添加");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

}
