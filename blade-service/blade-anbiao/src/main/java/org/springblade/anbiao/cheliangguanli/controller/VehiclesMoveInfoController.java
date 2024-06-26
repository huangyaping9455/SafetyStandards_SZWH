package org.springblade.anbiao.cheliangguanli.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.page.VehiclePage;
import org.springblade.anbiao.cheliangguanli.service.*;
import org.springblade.anbiao.cheliangguanli.vo.VehicleListVO;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IOrganizationsClient;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuanDaily;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoCheliangJiashiyuanDailyService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 呵呵哒
 * @program : SafetyStandards
 * @description: VehicleController
 */

@RestController
@RequestMapping("/anbiao/vehiclesMoveInfo")
@AllArgsConstructor
@Api(value = "车辆资料状态记录管理", tags = "车辆资料状态记录管理")
public class VehiclesMoveInfoController {

    private IVehiclesMoveInfoService iVehiclesMoveInfoService;

	private IVehicleService vehicleService;

	private IAnbiaoCheliangJiashiyuanDailyService cheliangJiashiyuanDailyService;

	private IVehicleXingshizhengService vehicleXingshizhengService;

	private IVehicleDaoluyunshuzhengService daoluyunshuzhengService;

	private IVehicleXingnengbaogaoService xingnengbaogaoService;

	private IVehicleDengjizhengshuService dengjizhengshuService;

	private IOrganizationsService organizationsService;

	private IVehicleBiangengjiluService vehicleBiangengjiluService;

	private IFileUploadClient fileUploadClient;

	private IOrganizationsClient orrganizationsClient;


//    @PostMapping("/list")
//	@ApiLog("分页-车辆异动列表")
//    @ApiOperation(value = "分页-车辆异动列表", notes = "传入vehiclesMoveInfoPage", position = 1)
//    public R<VehiclesMoveInfoPage<VehiclesMoveInfo>> list(@RequestBody VehiclesMoveInfoPage vehiclesMoveInfoPage) {
//		R r = new R();
//    	if(StringUtils.isBlank(vehiclesMoveInfoPage.getDeptId())){
//			r.setCode(500);
//    		r.setMsg("deptId不能为空");
//    		return r;
//		}
//
//    	VehiclesMoveInfoPage<VehiclesMoveInfo> pages = iVehiclesMoveInfoService.selectVehiclePage(vehiclesMoveInfoPage);
//        if(pages != null){
//        	r.setMsg("获取成功");
//        	r.setCode(200);
//        	r.setData(pages);
//		}else{
//			r.setMsg("获取失败");
//			r.setCode(500);
//			r.setData("");
//		}
//    	return r;
//    }

//    @PostMapping("/insert")
//	@ApiLog("新增-车辆异动信息")
//    @ApiOperation(value = "新增-车辆异动信息", notes = "传入vehiclesMoveInfo", position = 2)
//    public R insert(@RequestBody VehiclesMoveInfo vehiclesMoveInfo,BladeUser user) {
//    	R r = new R();
//		String date = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
//		vehiclesMoveInfo.setUpdateTime(date);
//		String uuid = UUID.randomUUID().toString().replace("-", "");
//		vehiclesMoveInfo.setId(uuid);
//		vehiclesMoveInfo.setUpdateUserId(user.getUserId().toString());
//		vehiclesMoveInfo.setUpdateUser(user.getUserName());
//		boolean i = iVehiclesMoveInfoService.insertSelective(vehiclesMoveInfo);
//		if(i == true){
//			r.setCode(200);
//			r.setMsg("异动成功");
//		}else{
//			r.setCode(500);
//			r.setMsg("异动失败");
//		}
//        return r;
//    }

	@PostMapping("/updateDriver")
	@ApiLog("车辆更新状态信息的变更记录-新增")
	@ApiOperation(value = "车辆更新状态信息的变更记录-新增", notes = "传入vehiclesMoveInfo", position = 2)
	public R updateDriver(@RequestBody VehiclesMoveInfo vehiclesMoveInfo, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		boolean ii = false;
		QueryWrapper<VehiclesMoveInfo> dangerQueryWrapper = new QueryWrapper<VehiclesMoveInfo>();
		dangerQueryWrapper.lambda().eq(VehiclesMoveInfo::getVehId, vehiclesMoveInfo.getVehId());
		dangerQueryWrapper.lambda().eq(VehiclesMoveInfo::getType, vehiclesMoveInfo.getType());
		VehiclesMoveInfo deail = iVehiclesMoveInfoService.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null){
			vehiclesMoveInfo.setUpdateUserId(user.getUserId().toString());
			vehiclesMoveInfo.setUpdateUser(user.getUserName());
			vehiclesMoveInfo.setUpdateTime(DateUtil.now());
			ii = iVehiclesMoveInfoService.save(vehiclesMoveInfo);
			if(ii){
				Vehicle vehicle = new Vehicle();
				vehicle.setId(vehiclesMoveInfo.getVehId());
				if(user != null){
					vehicle.setCaozuoren(user.getUserName());
					vehicle.setCaozuorenid(user.getUserId());
				}
				vehicle.setCaozuoshijian(LocalDateTime.now());
				vehicle.setCheliangzhuangtai(vehiclesMoveInfo.getType().toString());
				if(vehiclesMoveInfo.getType() == 0 || vehiclesMoveInfo.getType() == 1 || vehiclesMoveInfo.getType() == 2 || vehiclesMoveInfo.getType() == 3 || vehiclesMoveInfo.getType() == 4 ){
					vehicle.setIsdel(vehiclesMoveInfo.getType());
				}
				if( vehiclesMoveInfo.getType() == 5 ){
					vehicle.setIsdel(vehiclesMoveInfo.getType());
					vehicle.setDeptId(vehiclesMoveInfo.getInOfDeptId());
				}
				ii = vehicleService.updateById(vehicle);
				ii = vehicleService.deleteVehicle(vehicle.getIsdel(),vehicle.getCaozuoren(),vehicle.getCaozuorenid().toString(),vehicle.getId(),DateUtil.now());
				if(ii){
					//解绑人车绑定关系
					QueryWrapper<AnbiaoCheliangJiashiyuanDaily> AnbiaoCheliangJiashiyuanDailyQueryWrapper = new QueryWrapper<>();
					AnbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getVehid,vehiclesMoveInfo.getVehId());
					AnbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getVstatus,"1");
					AnbiaoCheliangJiashiyuanDaily anbiaoCheliangJiashiyuanDaily = cheliangJiashiyuanDailyService.getBaseMapper().selectOne(AnbiaoCheliangJiashiyuanDailyQueryWrapper);
					if (anbiaoCheliangJiashiyuanDaily!=null){
						anbiaoCheliangJiashiyuanDaily.setVstatus(0);
						anbiaoCheliangJiashiyuanDaily.setUpdatetime(DateUtil.now());
						cheliangJiashiyuanDailyService.updateById(anbiaoCheliangJiashiyuanDaily);
					}
					AnbiaoCheliangJiashiyuanDailyQueryWrapper = new QueryWrapper<>();
					AnbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getGvehid,vehiclesMoveInfo.getVehId());
					AnbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getGstatus,"1");
					anbiaoCheliangJiashiyuanDaily = cheliangJiashiyuanDailyService.getBaseMapper().selectOne(AnbiaoCheliangJiashiyuanDailyQueryWrapper);
					if (anbiaoCheliangJiashiyuanDaily!=null){
						anbiaoCheliangJiashiyuanDaily.setGstatus(0);
						anbiaoCheliangJiashiyuanDaily.setUpdatetime(DateUtil.now());
						cheliangJiashiyuanDailyService.updateById(anbiaoCheliangJiashiyuanDaily);
					}
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
			deail.setUpdateUserId(user.getUserId().toString());
			deail.setUpdateUser(user.getUserName());
			deail.setUpdateTime(DateUtil.now());
			ii = iVehiclesMoveInfoService.updateById(deail);
			if(ii){
				Vehicle vehicle = new Vehicle();
				vehicle.setId(vehiclesMoveInfo.getVehId());
				if(user != null){
					vehicle.setCaozuoren(user.getUserName());
					vehicle.setCaozuorenid(user.getUserId());
				}
				vehicle.setCaozuoshijian(LocalDateTime.now());
				vehicle.setCheliangzhuangtai(vehiclesMoveInfo.getType().toString());
				if(vehiclesMoveInfo.getType() == 0 || vehiclesMoveInfo.getType() == 1 || vehiclesMoveInfo.getType() == 2 || vehiclesMoveInfo.getType() == 3 || vehiclesMoveInfo.getType() == 4 ){
					vehicle.setIsdel(vehiclesMoveInfo.getType());
				}
				if( vehiclesMoveInfo.getType() == 5 ){
					vehicle.setIsdel(vehiclesMoveInfo.getType());
					vehicle.setDeptId(vehiclesMoveInfo.getInOfDeptId());
				}
				ii = vehicleService.updateById(vehicle);
				ii = vehicleService.deleteVehicle(vehicle.getIsdel(),vehicle.getCaozuoren(),vehicle.getCaozuorenid().toString(),vehicle.getId(),DateUtil.now());
				if(ii){
					//解绑人车绑定关系
					QueryWrapper<AnbiaoCheliangJiashiyuanDaily> AnbiaoCheliangJiashiyuanDailyQueryWrapper = new QueryWrapper<>();
					AnbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getVehid,vehiclesMoveInfo.getVehId());
					AnbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getVstatus,"1");
					AnbiaoCheliangJiashiyuanDaily anbiaoCheliangJiashiyuanDaily = cheliangJiashiyuanDailyService.getBaseMapper().selectOne(AnbiaoCheliangJiashiyuanDailyQueryWrapper);
					if (anbiaoCheliangJiashiyuanDaily!=null){
						anbiaoCheliangJiashiyuanDaily.setVstatus(0);
						anbiaoCheliangJiashiyuanDaily.setUpdatetime(DateUtil.now());
						cheliangJiashiyuanDailyService.updateById(anbiaoCheliangJiashiyuanDaily);
					}
					AnbiaoCheliangJiashiyuanDailyQueryWrapper = new QueryWrapper<>();
					AnbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getGvehid,vehiclesMoveInfo.getVehId());
					AnbiaoCheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getGstatus,"1");
					anbiaoCheliangJiashiyuanDaily = cheliangJiashiyuanDailyService.getBaseMapper().selectOne(AnbiaoCheliangJiashiyuanDailyQueryWrapper);
					if (anbiaoCheliangJiashiyuanDaily!=null){
						anbiaoCheliangJiashiyuanDaily.setGstatus(0);
						anbiaoCheliangJiashiyuanDaily.setUpdatetime(DateUtil.now());
						cheliangJiashiyuanDailyService.updateById(anbiaoCheliangJiashiyuanDaily);
					}
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
		}
//		else{
//			r.setMsg("该数据已添加");
//			r.setCode(500);
//			r.setSuccess(false);
//		}
		return r;
	}

	@GetMapping("/selectByCL")
	@ApiLog("车牌搜索")
	@ApiOperation(value = "车牌搜索", notes = "传入cheliangpaizhao", position = 2)
	public R selectByCL(String cheliangpaizhao) {
		R r = new R();
		QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
		vehicleQueryWrapper.lambda().eq(Vehicle::getCheliangpaizhao, cheliangpaizhao);
		vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
		Vehicle vehicle = vehicleService.getBaseMapper().selectOne(vehicleQueryWrapper);
		if(vehicle != null){
			r.setMsg("该车牌信息已存在");
			r.setCode(500);
			r.setData(null);
		}
		return r;
	}


	@PostMapping("/updateDeptId")
	@ApiLog("车辆资料状态记录管理-车辆异动")
	@ApiOperation(value = "车辆资料状态记录管理-车辆异动", notes = "传入车辆ID,企业ID", position = 10)
	public R updateDeptId(@RequestParam String id, @RequestParam String deptId, @RequestParam String plate,BladeUser user) {
		R r = new R();
		boolean ss = false;
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		String[] idsss = id.split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			//将之前企业的资料进行封存，新增到新的企业下
			//车辆技术档案
			UUID clid = UUID.randomUUID();
			QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
			vehicleQueryWrapper.lambda().eq(Vehicle::getId, id);
//			vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
			Vehicle vehicle = vehicleService.selectByKey(id);
			if(vehicle != null){
				vehicle.setDeptId(Integer.parseInt(deptId));
				vehicle.setId(clid.toString());
				vehicle.setCheliangpaizhao(plate);
				vehicle.setCaozuoren(user.getUserName());
				vehicle.setCaozuorenid(user.getUserId());
				vehicle.setCreatetime(LocalDateTime.now());
				vehicle.setIsdel(0);
				vehicle.setCheliangzhuangtai("0");
				ss = vehicleService.save(vehicle);
			}
			Organizations dept = orrganizationsClient.selectByDeptId(deptId);
			//行驶证
			QueryWrapper<VehicleXingshizheng> xingshizhengQueryWrapper = new QueryWrapper<>();
			xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxAvIds,id);
//			xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxDelete,"0");
			VehicleXingshizheng xingshizheng = vehicleXingshizhengService.getBaseMapper().selectOne(xingshizhengQueryWrapper);
			if(xingshizheng != null){
				UUID uuid = UUID.randomUUID();
				xingshizheng.setAvxIds(uuid.toString());
				xingshizheng.setAvxAvIds(clid.toString());
				xingshizheng.setAvxOwner(dept.getDeptName());
				xingshizheng.setAvxPlateNo(plate);
				xingshizheng.setAvxDelete("0");
				xingshizheng.setAvxCreateTime(LocalDateTime.now());
				xingshizheng.setAvxCreateByIds(user.getUserId().toString());
				xingshizheng.setAvxCreateByName(user.getUserName());
				xingshizheng.setAvxFileNo(null);
				xingshizheng.setAvxValidUntil(null);
				xingshizheng.setAvxValidUntil2(null);
				xingshizheng.setAvxCopyEnclosure(null);
				xingshizheng.setAvxOriginalEnclosure(null);
				ss = vehicleXingshizhengService.save(xingshizheng);
			}
			//道路运输证
			QueryWrapper<VehicleDaoluyunshuzheng> daoluyunshuzhengQueryWrapper = new QueryWrapper<>();
			daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdAvIds,id);
//			daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdDelete,0);
			VehicleDaoluyunshuzheng daoluyunshuzheng = daoluyunshuzhengService.getBaseMapper().selectOne(daoluyunshuzhengQueryWrapper);
			if (daoluyunshuzheng !=null){
				UUID uuid = UUID.randomUUID();
				daoluyunshuzheng.setAvdIds(uuid.toString());
				daoluyunshuzheng.setAvdAvIds(clid.toString());
				daoluyunshuzheng.setAvdPlateNo(plate);
				daoluyunshuzheng.setAvdDelete("0");
				daoluyunshuzheng.setAvdCreateByName(user.getUserName());
				daoluyunshuzheng.setAvdCreateByIds(user.getUserId().toString());
				daoluyunshuzheng.setAvdCreateTime(DateUtil.now());
				daoluyunshuzheng.setAvdRoadTransportCertificateNo(null);
				daoluyunshuzheng.setAvdValidUntil(null);
				daoluyunshuzheng.setAvdValidUntil2(null);
				daoluyunshuzheng.setAvdEnclosure(null);
				ss = daoluyunshuzhengService.save(daoluyunshuzheng);
			}
			//车辆综合性能检测报告
			QueryWrapper<VehicleXingnengbaogao> xingnengbaogaoQueryWrapper = new QueryWrapper<>();
			xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxAvIds,id);
//			xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxDelete,0);
			VehicleXingnengbaogao xingnengbaogao = xingnengbaogaoService.getBaseMapper().selectOne(xingnengbaogaoQueryWrapper);
			if (xingnengbaogao !=null){
				UUID uuid = UUID.randomUUID();
				xingnengbaogao.setAvxIds(uuid.toString());
				xingnengbaogao.setAvxAvIds(clid.toString());
				xingnengbaogao.setAvxFileNo(plate);
				xingnengbaogao.setAvxDelete("0");
				xingnengbaogao.setAvxCreateByName(user.getUserName());
				xingnengbaogao.setAvxCreateByIds(user.getUserId().toString());
				xingnengbaogao.setAvxCreateTime(LocalDateTime.now());
				ss = xingnengbaogaoService.save(xingnengbaogao);
			}
			//车辆登记证书
			QueryWrapper<VehicleDengjizhengshu> dengjizhengshuQueryWrapper = new QueryWrapper<>();
			dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdVehicleIds,id);
//			dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdDelete,0);
			VehicleDengjizhengshu dengjizhengshu = dengjizhengshuService.getBaseMapper().selectOne(dengjizhengshuQueryWrapper);
			if (dengjizhengshu !=null){
				UUID uuid = UUID.randomUUID();
				dengjizhengshu.setAvdIds(uuid.toString());
				dengjizhengshu.setAvdVehicleIds(clid.toString());
				dengjizhengshu.setAvxFileNo(plate);
				dengjizhengshu.setAvdDelete("0");
				dengjizhengshu.setAvdCreateByName(user.getUserName());
				dengjizhengshu.setAvdCreateByIds(user.getUserId().toString());
				dengjizhengshu.setAvdCreateTime(DateUtil.now());
				ss = dengjizhengshuService.save(dengjizhengshu);
			}
//			ss = vehicleService.updateDeptId(deptId,idsss[i]);
			if (ss){
				r.setMsg("更新成功");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("更新失败");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

	@PostMapping("/list")
	@ApiLog("分页-非营运车辆资料")
	@ApiOperation(value = "分页-非营运车辆资料", notes = "传入vehiclePage", position = 5)
	public R<VehiclePage<VehicleListVO>> list(@RequestBody VehiclePage vehiclePage,BladeUser user) {
		R r = new R();
		if (user == null) {
			r.setCode(401);
			r.setMsg("用户权限验证失败");
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		VehiclePage<VehicleListVO> pages = iVehiclesMoveInfoService.selectPageList(vehiclePage);
		return R.data(pages);
	}

	@PostMapping("/getGHCPageList")
	@ApiLog("分页-公海池车辆资料")
	@ApiOperation(value = "分页-公海池车辆资料", notes = "传入vehiclePage", position = 6)
	public R<VehiclePage<VehicleListVO>> getGHCPageList(@RequestBody VehiclePage vehiclePage,BladeUser user) {
		R r = new R();
		if (user == null) {
			r.setCode(401);
			r.setMsg("用户权限验证失败");
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
		VehiclePage<VehicleListVO> pages = iVehiclesMoveInfoService.selectGHCPageList(vehiclePage);
		return R.data(pages);
	}

	/**
	 * 查询详情
	 */
	@GetMapping("/detail")
	@ApiLog("详情-车辆资料管理")
	@ApiOperation(value = "详情-车辆资料管理", notes = "传入id、type", position = 4)
	@ApiImplicitParams({@ApiImplicitParam(name = "id", value = "车辆ID", required = true),
		@ApiImplicitParam(name = "type", value = "分类（1：车辆行驶证，2：道路运输证，3：车辆综合性能检测报告，4:车辆技术档案,5:车辆登记证书）", required = true),
	})
	public R detail(String id, int type,BladeUser user) {
		R r = new R();
		if (user == null) {
			r.setCode(401);
			r.setMsg("用户权限验证失败");
			r.setData(null);
			r.setSuccess(false);
			return r;
		}
//		QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
//		vehicleQueryWrapper.lambda().eq(Vehicle::getId,id);
		Vehicle detal = vehicleService.selectByKey(id);
		if (detal != null) {
			///车辆行驶证///
			if (type == 1) {
				QueryWrapper<VehicleXingshizheng> xingshizhengQueryWrapper = new QueryWrapper<>();
				xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxAvIds, detal.getId());
				VehicleXingshizheng xingshizheng = vehicleXingshizhengService.getBaseMapper().selectOne(xingshizhengQueryWrapper);
				if (xingshizheng != null) {
					if (StrUtil.isNotEmpty(xingshizheng.getAvxCopyEnclosure()) && !xingshizheng.getAvxCopyEnclosure().contains("http")) {
						xingshizheng.setAvxCopyEnclosure(fileUploadClient.getUrl(xingshizheng.getAvxCopyEnclosure()));
					}
					if (StrUtil.isNotEmpty(xingshizheng.getAvxOriginalEnclosure()) && !xingshizheng.getAvxOriginalEnclosure().contains("http")) {
						xingshizheng.setAvxOriginalEnclosure(fileUploadClient.getUrl(xingshizheng.getAvxOriginalEnclosure()));
					}
					OrganizationsVO dept = organizationsService.selectByDeptId(detal.getDeptId().toString());
					xingshizheng.setDeptName(dept.getDeptName());

					r.setData(xingshizheng);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///道路运输证///
			if (type == 2) {
				QueryWrapper<VehicleDaoluyunshuzheng> daoluyunshuzhengQueryWrapper = new QueryWrapper<>();
				daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdAvIds, detal.getId());
				VehicleDaoluyunshuzheng daoluyunshuzheng = daoluyunshuzhengService.getBaseMapper().selectOne(daoluyunshuzhengQueryWrapper);
				if (daoluyunshuzheng != null) {
					if (StrUtil.isNotEmpty(daoluyunshuzheng.getAvdEnclosure()) && !daoluyunshuzheng.getAvdEnclosure().contains("http")) {
						daoluyunshuzheng.setAvdEnclosure(fileUploadClient.getUrl(daoluyunshuzheng.getAvdEnclosure()));
					}
					VehicleVO detail = vehicleService.selectByKey(detal.getId());
					daoluyunshuzheng.setAvxPlateNo(detail.getCheliangpaizhao());

					r.setData(daoluyunshuzheng);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///车辆综合性能检测报告///
			if (type == 3) {
				QueryWrapper<VehicleXingnengbaogao> xingnengbaogaoQueryWrapper = new QueryWrapper<>();
				xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxAvIds, detal.getId());
				VehicleXingnengbaogao xingnengbaogao = xingnengbaogaoService.getBaseMapper().selectOne(xingnengbaogaoQueryWrapper);
				if (xingnengbaogao != null) {
					if (StrUtil.isNotEmpty(xingnengbaogao.getAvxEnclosure()) && !xingnengbaogao.getAvxEnclosure().contains("http")) {
						xingnengbaogao.setAvxEnclosure(fileUploadClient.getUrl(xingnengbaogao.getAvxEnclosure()));
					}
					VehicleVO detail = vehicleService.selectByKey(detal.getId());
					xingnengbaogao.setAvxFileNo(detail.getCheliangpaizhao());

					r.setData(xingnengbaogao);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///车辆技术档案///
			if (type == 4) {
				//车辆照片
				if (StrUtil.isNotEmpty(detal.getCheliangzhaopian()) && detal.getCheliangzhaopian().contains("http") == false) {
					detal.setCheliangzhaopian(fileUploadClient.getUrl(detal.getCheliangzhaopian()));
				}
				//燃料消耗附件
				if (StrUtil.isNotEmpty(detal.getRanliaoxiaohaofujian()) && detal.getRanliaoxiaohaofujian().contains("http") == false) {
					detal.setRanliaoxiaohaofujian(fileUploadClient.getUrl(detal.getRanliaoxiaohaofujian()));
				}
				//行驶证附件
				if (StrUtil.isNotEmpty(detal.getXingshifujian()) && detal.getXingshifujian().contains("http") == false) {
					detal.setXingshifujian(fileUploadClient.getUrl(detal.getXingshifujian()));
				}

				VehicleInfo v = new VehicleInfo();
				v.setId(detal.getId());
				v.setDeptId(detal.getDeptId());
				v.setCheliangpaizhao(detal.getCheliangpaizhao());
				v.setChepaiyanse(detal.getChepaiyanse());
				v.setJiashiyuanid(detal.getJiashiyuanid());
				v.setChezhu(detal.getChezhu());
				v.setChezhudianhua(detal.getChezhudianhua());
				v.setJingyingxukezhenghao(detal.getDaoluyunshuzheng());
				DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				if (StringUtils.isNotBlank(detal.getDaoluyunshuzhengchulingriqi()) && !detal.getDaoluyunshuzhengchulingriqi().equals("null")) {
					v.setJyxkzyouxiaoqiStart(detal.getDaoluyunshuzhengchulingriqi());
				}
				if (StringUtils.isNotBlank(detal.getDaoluyunshuzhengyouxiaoqi()) && !detal.getDaoluyunshuzhengyouxiaoqi().equals("null")) {
					v.setJyxkzyouxiaoqiEnd(detal.getDaoluyunshuzhengyouxiaoqi());
				}
				v.setJingjileixing(detal.getJingjileixing());
				v.setJingyingzuzhifangshi(detal.getJingyingzuzhifangshi());
				v.setJingyingfanwei(detal.getCheliangyunyingleixing());
				v.setJingyingluxian(detal.getTeamno());
				v.setChelianghuodefangshi(detal.getChelianghuoqufangshi());
				v.setWeihuzhouqi(detal.getWeihuzhouqi());
				v.setCheliangzhaopian(detal.getCheliangzhaopian());
				v.setCheliangleixing(detal.getCheliangleixing());
				v.setCheliangxinghao(detal.getXinghao());
				v.setCheliangyanse(detal.getCheshenyanse());
				v.setChejiahao(detal.getChejiahao());
				v.setShifouguochan(detal.getShifoujinkou());
				v.setFadongjihao(detal.getFadongjihao());
				v.setRanliaozhonglei(detal.getRanliaoleibie());
				v.setFadongjixinghao(detal.getFadongjixinghao());
				v.setPailianggonglv(detal.getPaifangbiaozhun());
				v.setZhizaochangmingcheng(detal.getZhizhaochangshang());
				v.setZhuanxiangxingshi(detal.getZhuanxiangfangshi());
				v.setLunju(detal.getLunju());
				v.setLuntaishu(detal.getLuntaishu());
				v.setLuntaiguige(detal.getLuntaiguige());
				v.setTongbantanhuangpianshu(detal.getGangbantanhuangpianshu());
				v.setZhouju(detal.getZhouju());
				v.setZhoushu(detal.getChezhoushu());
				v.setWaikuochicun(detal.getCheliangwaikuochicun());
				v.setHuoxiangneibuchicun(detal.getHuoxiangneibuchicun());
				v.setZongzhiliang(detal.getZongzhiliang());
				v.setHedingzaizhiliang(detal.getHedingzaizhiliang());
				v.setHedingzaikeshu(detal.getHedingzaike());
				v.setZhunqianyinzongzhiliang(detal.getZhunqianyinzongzhiliang());
				v.setJiashishizaikeshu(detal.getJiashishizaike());
				v.setShiyongxingzhi(detal.getShiyongxingzhi());
				v.setCheliangchuchangriqi(detal.getChuchangriqi());
				v.setCheliangpinpai(detal.getCheliangpinpai());
				v.setFrontlunju(detal.getFrontlunju());
				v.setZongduanid(detal.getZongduanid());
				v.setZongduanxinghao(detal.getZongduanxinghao());
				v.setPlatformconnectiontype(detal.getPlatformconnectiontype());
				v.setTerminalprotocoltype(detal.getTerminalprotocoltype());
				v.setZhongduanchangshang(detal.getZhongduanchangshang());
				v.setVideochannelnum(detal.getVideochannelnum());
				v.setZhongduanleixing(detal.getZhongduanleixing());
				v.setYunyingshang(detal.getYunyingshang());
				v.setYunyingshangmingcheng(detal.getYunyingshangmingcheng());
				v.setSimnum(detal.getSimnum());
				v.setYunyingshangjieruma(detal.getYunyingshangjieruma());
				v.setChangpai(detal.getChangpai());
				v.setBencinianshenriqi(detal.getBencinianshenriqi());
				v.setXiacinianshenriqi(detal.getXiacinianshenriqi());
				v.setBencinianjianriqi(detal.getBencinianjianriqi());
				v.setXiacinianjianriqi(detal.getXiacinianjianriqi());
				v.setBencijipingriqi(detal.getBencijipingriqi());
				v.setXiacijipingriqi(detal.getXiacijipingriqi());
				v.setBaoxiandaoqishijian(detal.getBaoxiandaoqishijian());
				v.setBaofeiriqi(detal.getBaofeiriqi());
				v.setQiangzhibaofeishijian(detal.getBaofeiriqi());

				QueryWrapper<VehicleBiangengjilu> biangengjiluQueryWrapper = new QueryWrapper<VehicleBiangengjilu>();
				biangengjiluQueryWrapper.lambda().eq(VehicleBiangengjilu::getAvbjVehicleId, detal.getId());
				List<VehicleBiangengjilu> deail = vehicleBiangengjiluService.getBaseMapper().selectList(biangengjiluQueryWrapper);
				if (deail != null) {
					v.setCheliangbiangengjilu(deail);
				}
				r.setData(v);
				r.setCode(200);
				r.setMsg("获取成功");
				return r;
			}

			///车辆登记证书///
			if (type == 5) {
				QueryWrapper<VehicleDengjizhengshu> dengjizhengshuQueryWrapper = new QueryWrapper<>();
				dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdVehicleIds,detal.getId());
				VehicleDengjizhengshu dengjizhengshu = dengjizhengshuService.getBaseMapper().selectOne(dengjizhengshuQueryWrapper);
				if(dengjizhengshu != null){
					if(StrUtil.isNotEmpty(dengjizhengshu.getAvdEnclosure()) && !dengjizhengshu.getAvdEnclosure().contains("http")){
						dengjizhengshu.setAvdEnclosure(fileUploadClient.getUrl(dengjizhengshu.getAvdEnclosure()));
					}
					VehicleVO detail = vehicleService.selectByKey(detal.getId());
					dengjizhengshu.setAvxFileNo(detail.getCheliangpaizhao());

					r.setData(dengjizhengshu);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(200);
					r.setMsg("暂无数据");
					return r;
				}
			}
		} else {
			r.setMsg("暂无数据");
			r.setCode(200);
			return r;
		}
		return r;
	}

}
