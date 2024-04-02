package org.springblade.anbiao.cheliangguanli.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.page.VehiclePage;
import org.springblade.anbiao.cheliangguanli.service.*;
import org.springblade.anbiao.cheliangguanli.vo.VehicleListVO;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuanDaily;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoCheliangJiashiyuanDailyService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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


	private IFileUploadClient fileUploadClient;


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
		vehiclesMoveInfo.setUpdateUserId(vehiclesMoveInfo.getUpdateUserId());
		vehiclesMoveInfo.setUpdateUser(vehiclesMoveInfo.getUpdateUser());
		vehiclesMoveInfo.setUpdateTime(vehiclesMoveInfo.getUpdateTime());
		QueryWrapper<VehiclesMoveInfo> dangerQueryWrapper = new QueryWrapper<VehiclesMoveInfo>();
		dangerQueryWrapper.lambda().eq(VehiclesMoveInfo::getVehId, vehiclesMoveInfo.getVehId());
		dangerQueryWrapper.lambda().eq(VehiclesMoveInfo::getType, vehiclesMoveInfo.getType());
		VehiclesMoveInfo deail = iVehiclesMoveInfoService.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null){
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
				if(vehiclesMoveInfo.getType() == 1 || vehiclesMoveInfo.getType() == 3 || vehiclesMoveInfo.getType() == 4 ){
					vehicle.setIsdel(vehiclesMoveInfo.getType());
				}
				ii = vehicleService.updateById(vehicle);
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
			r.setMsg("该数据已添加");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	@PostMapping("/updateDeptId")
	@ApiLog("车辆资料状态记录管理-车辆异动")
	@ApiOperation(value = "车辆资料状态记录管理-车辆异动", notes = "传入车辆ID,企业ID", position = 10)
	public R updateDeptId(@RequestParam String id, @RequestParam String deptId, BladeUser user) {
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
			vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
			Vehicle vehicle = vehicleService.getBaseMapper().selectOne(vehicleQueryWrapper);
			if(vehicle != null){
				vehicle.setDeptId(Integer.parseInt(deptId));
				vehicle.setId(clid.toString());
				vehicle.setCaozuoren(user.getUserName());
				vehicle.setCaozuorenid(user.getUserId());
				vehicle.setCreatetime(LocalDateTime.now());
				vehicle.setIsdel(0);
				vehicle.setCheliangzhuangtai("0");
				ss = vehicleService.save(vehicle);
			}
			//行驶证
			QueryWrapper<VehicleXingshizheng> xingshizhengQueryWrapper = new QueryWrapper<>();
			xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxAvIds,id);
			xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxDelete,"0");
			VehicleXingshizheng xingshizheng = vehicleXingshizhengService.getBaseMapper().selectOne(xingshizhengQueryWrapper);
			if(xingshizheng != null){
				xingshizheng.setAvxAvIds(clid.toString());
				xingshizheng.setAvxDelete("0");
				xingshizheng.setAvxCreateTime(LocalDateTime.now());
				xingshizheng.setAvxCreateByIds(user.getUserId().toString());
				xingshizheng.setAvxCreateByName(user.getUserName());
				ss = vehicleXingshizhengService.save(xingshizheng);
			}
			//道路运输证
			QueryWrapper<VehicleDaoluyunshuzheng> daoluyunshuzhengQueryWrapper = new QueryWrapper<>();
			daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdAvIds,id);
			daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdDelete,0);
			VehicleDaoluyunshuzheng daoluyunshuzheng = daoluyunshuzhengService.getBaseMapper().selectOne(daoluyunshuzhengQueryWrapper);
			if (daoluyunshuzheng !=null){
				daoluyunshuzheng.setAvdAvIds(clid.toString());
				daoluyunshuzheng.setAvdDelete("0");
				daoluyunshuzheng.setAvdCreateByName(user.getUserName());
				daoluyunshuzheng.setAvdCreateByIds(user.getUserId().toString());
				daoluyunshuzheng.setAvdCreateTime(DateUtil.now());
				ss = daoluyunshuzhengService.save(daoluyunshuzheng);
			}
			//车辆综合性能检测报告
			QueryWrapper<VehicleXingnengbaogao> xingnengbaogaoQueryWrapper = new QueryWrapper<>();
			xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxAvIds,id);
			xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxDelete,0);
			VehicleXingnengbaogao xingnengbaogao = xingnengbaogaoService.getBaseMapper().selectOne(xingnengbaogaoQueryWrapper);
			if (xingnengbaogao !=null){
				xingnengbaogao.setAvxAvIds(clid.toString());
				xingnengbaogao.setAvxDelete("0");
				xingnengbaogao.setAvxCreateByName(user.getUserName());
				xingnengbaogao.setAvxCreateByIds(user.getUserId().toString());
				xingnengbaogao.setAvxCreateTime(LocalDateTime.now());
				ss = xingnengbaogaoService.save(xingnengbaogao);
			}
			//车辆登记证书
			QueryWrapper<VehicleDengjizhengshu> dengjizhengshuQueryWrapper = new QueryWrapper<>();
			dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdVehicleIds,id);
			dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdDelete,0);
			VehicleDengjizhengshu dengjizhengshu = dengjizhengshuService.getBaseMapper().selectOne(dengjizhengshuQueryWrapper);
			if (dengjizhengshu !=null){
				dengjizhengshu.setAvdVehicleIds(clid.toString());
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
	public R<VehiclePage<VehicleListVO>> list(@RequestBody VehiclePage vehiclePage) {
		VehiclePage<VehicleListVO> pages = iVehiclesMoveInfoService.selectPageList(vehiclePage);
		return R.data(pages);
	}

	@PostMapping("/getGHCPageList")
	@ApiLog("分页-公海池车辆资料")
	@ApiOperation(value = "分页-公海池车辆资料", notes = "传入vehiclePage", position = 6)
	public R<VehiclePage<VehicleListVO>> getGHCPageList(@RequestBody VehiclePage vehiclePage) {
		VehiclePage<VehicleListVO> pages = iVehiclesMoveInfoService.selectGHCPageList(vehiclePage);
		return R.data(pages);
	}

}
