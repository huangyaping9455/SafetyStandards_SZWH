package org.springblade.anbiao.chuchejiancha.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamine;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfoRemark;
import org.springblade.anbiao.chuchejiancha.page.AnBiaoCheckCarPage;
import org.springblade.anbiao.chuchejiancha.page.AnbiaoCarExamineInfoPage;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineInfoRemarkService;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineInfoService;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineService;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoVO;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.weixiu.entity.FittingEntity;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiucheliang.service.MaintenanceService;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoHiddenDangerService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.hutool.core.date.DateUtil.now;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/carExamineInfo")
@Api(value = "安全检查数据", tags = "安全检查数据")
public class AnbiaoCarExamineInfoController {

	private IAnbiaoCarExamineInfoService iAnbiaoCarExamineInfoService;

	private IAnbiaoCarExamineInfoRemarkService iAnbiaoCarExamineInfoRemarkService;

	private IFileUploadClient fileUploadClient;

	private IAnbiaoCarExamineService iAnbiaoCarExamineService;

	private MaintenanceService maintenanceService;

	private IOrganizationsService organizationsService;

	private IAnbiaoHiddenDangerService hiddenDangerService;

	@PostMapping("/saveCarExamineInfo")
	@ApiLog("安全检查数据-新增")
	@ApiOperation(value = "安全检查数据-新增", notes = "传入AnbiaoCarExamineInfo", position = 1)
	public R saveCarExamineInfo(@RequestBody AnbiaoCarExamineInfo anbiaoCarExamineInfo) {
		R rs = new R();
		boolean ii = false;

		anbiaoCarExamineInfo.setIsdelete(0);
		anbiaoCarExamineInfo.setCreatetime(DateUtil.now());
		anbiaoCarExamineInfo.setDate(DateUtil.now().substring(0,10));

		QueryWrapper<AnbiaoCarExamineInfo> examineQueryWrapper = new QueryWrapper<AnbiaoCarExamineInfo>();
//		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getType, anbiaoCarExamineInfo.getType());
//		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getStatus, 1);
		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getDeptid, anbiaoCarExamineInfo.getDeptid());
		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getIsdelete, 0);
		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getDate, anbiaoCarExamineInfo.getDate());
		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getVehid, anbiaoCarExamineInfo.getVehid());
		examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getJsyid, anbiaoCarExamineInfo.getJsyid());
		AnbiaoCarExamineInfo deail = iAnbiaoCarExamineInfoService.getBaseMapper().selectOne(examineQueryWrapper);
		if(deail == null){
			if(anbiaoCarExamineInfo.getStatus() == 0 || anbiaoCarExamineInfo.getStatus() == 6){
				anbiaoCarExamineInfo.setStatus(0);
				anbiaoCarExamineInfo.setIsdelete(0);
				ii = iAnbiaoCarExamineInfoService.save(anbiaoCarExamineInfo);
				if (ii == true) {
					rs.setCode(200);
					rs.setSuccess(true);
					rs.setMsg("编辑成功");
				} else {
					rs.setCode(500);
					rs.setSuccess(false);
					rs.setMsg("编辑失败");
				}
			}else{
				anbiaoCarExamineInfo.setStatus(1);
				ii = iAnbiaoCarExamineInfoService.save(anbiaoCarExamineInfo);
				if(ii == true) {
					deail = iAnbiaoCarExamineInfoService.getBaseMapper().selectOne(examineQueryWrapper);
					if (deail != null) {
						List<AnbiaoCarExamineInfoRemark> examineInfos = anbiaoCarExamineInfo.getAnbiaoCarExamineInfoRemarkList();
						AnbiaoCarExamineInfo finalDeail = deail;
						OrganizationsVO dept = organizationsService.selectByDeptId(deail.getDeptid().toString());
						examineInfos.forEach(item-> {
							AnbiaoCarExamineInfoRemark remark = new AnbiaoCarExamineInfoRemark();
							remark.setExamid(finalDeail.getId());
							remark.setStatus(1);
							remark.setXiangid(item.getXiangid());
							remark.setFlgremark(item.getFlgremark());
							remark.setFlgimg(item.getFlgimg());
							remark.setTrueimg(item.getTrueimg());
							remark.setTrueremark(item.getTrueremark());
							remark.setCreatetime(DateUtil.now());
							remark.setCreateid(finalDeail.getJsyid());
							boolean is = iAnbiaoCarExamineInfoRemarkService.save(remark);
							if (is == true) {
								new Thread(new Runnable() {
									@Override
									public void run() {
										//向隐患登记、维修登记档案信息表中添加数据；
										MaintenanceEntity maintenanceDTO = new MaintenanceEntity();
										maintenanceDTO.setDeptId(finalDeail.getDeptid());
										maintenanceDTO.setDepID(finalDeail.getDeptid());
										maintenanceDTO.setVehicleId(finalDeail.getVehid());
										maintenanceDTO.setDriverId(finalDeail.getJsyid());
										maintenanceDTO.setSendDate(finalDeail.getDate());
										maintenanceDTO.setMaintainDictId(0);
										maintenanceDTO.setAcbMaintenanceContent(finalDeail.getRemark());
										maintenanceDTO.setAcbRepairReason(remark.getFlgremark());
										maintenanceDTO.setMaintenanceDeptName(dept.getDeptName());
										maintenanceDTO.setAcbBeforeMaintenance(remark.getFlgimg());
										maintenanceDTO.setCreateid(finalDeail.getJsyid());
										maintenanceDTO.setCreatetime(DateUtil.now());
										maintenanceService.insertOne(maintenanceDTO);

										AnbiaoHiddenDanger danger = new AnbiaoHiddenDanger();
										danger.setAhdDelete("0");
										danger.setAhdRectificationSituation("0");
										danger.setAhdCreateTime(DateUtil.now());
										danger.setAhdDeptIds(finalDeail.getDeptid().toString());
										danger.setAhdVehicleIds(finalDeail.getVehid());
										danger.setAhdType("0");
										danger.setAhdDriverIds(finalDeail.getJsyid());
										danger.setAhdDriverName(finalDeail.getCreatename());
										danger.setAhdDiscovererName(finalDeail.getCreatename());
										danger.setAhdDiscoveryTime(finalDeail.getDate());
										danger.setAhdHiddendangerEnclosure(remark.getFlgimg());
										danger.setAhdCreateTime(DateUtil.now());
										danger.setAhdCreateByIds(finalDeail.getJsyid());
										danger.setAhdDescribe(remark.getFlgremark());
										danger.setAhdAddress("车辆设备");
										hiddenDangerService.save(danger);
									}
								}).start();
								rs.setCode(200);
								rs.setSuccess(true);
								rs.setMsg("新增成功");
							} else {
								rs.setCode(500);
								rs.setSuccess(false);
								rs.setMsg("新增失败");
							}
						});
					}
				}
			}
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("该数据已添加");
		}
		return rs;
	}

	@PostMapping("/updateCarExamineXiang")
	@ApiLog("安全检查数据-编辑")
	@ApiOperation(value = "安全检查数据-编辑", notes = "传入anbiaoCarExamineInfo", position = 2)
	public R updateCarExamineXiang(@RequestBody AnbiaoCarExamineInfo anbiaoCarExamineInfo) {
		R rs = new R();
		boolean ii = false;

		anbiaoCarExamineInfo.setUpdateid(anbiaoCarExamineInfo.getJsyid());
		anbiaoCarExamineInfo.setIsdelete(0);
		anbiaoCarExamineInfo.setUpdatetime(DateUtil.now());
		if(anbiaoCarExamineInfo.getStatus() == 0){
			anbiaoCarExamineInfo.setStatus(0);
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if (ii == true) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("编辑成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("编辑失败");
			}
		}else{
			anbiaoCarExamineInfo.setStatus(1);
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if(ii == true) {
				List<AnbiaoCarExamineInfoRemark> examineInfos = anbiaoCarExamineInfo.getAnbiaoCarExamineInfoRemarkList();
				examineInfos.forEach(item-> {
					AnbiaoCarExamineInfoRemark remark = new AnbiaoCarExamineInfoRemark();
					remark.setStatus(1);
					remark.setXiangid(item.getXiangid());
					remark.setExamid(item.getExamid());
					remark.setFlgremark(item.getFlgremark());
					remark.setFlgimg(item.getFlgimg());
					remark.setTrueimg(item.getTrueimg());
					remark.setTrueremark(item.getTrueremark());
					remark.setUpdatetime(DateUtil.now());
					remark.setUpdateid(anbiaoCarExamineInfo.getJsyid());
					remark.setId(item.getId());
					boolean is = iAnbiaoCarExamineInfoRemarkService.updateById(remark);
					if (is == true) {
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("编辑成功");
					} else {
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("编辑失败");
					}
				});
			}
		}
		return rs;
	}

	@GetMapping("/removeCarExamineXiang")
	@ApiOperation(value = "安全检查数据-删除", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
		@ApiImplicitParam(name = "caozuorenid", value = "操作人ID", required = true)
	})
	public R removeCarExamineXiang(Integer Id,String caozuorenid) {
		AnbiaoCarExamineInfo anbiaoCarExamineInfo = new AnbiaoCarExamineInfo();
		anbiaoCarExamineInfo.setUpdateid(caozuorenid);
		anbiaoCarExamineInfo.setUpdatetime(DateUtil.now());
		anbiaoCarExamineInfo.setIsdelete(1);
		anbiaoCarExamineInfo.setId(Id);
		return R.data(iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo));
	}

	@GetMapping("/getById")
	@ApiOperation(value = "安全检查数据-根据ID获取详情", notes = "传入Id", position = 4)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true)})
	public R getById(Integer Id) {
		AnbiaoCarExamineInfo deail = iAnbiaoCarExamineInfoService.getById(Id);
		if(deail != null){
			//车辆照片
			if(StrUtil.isNotEmpty(deail.getVehimg()) && deail.getVehimg().contains("http") == false){
				deail.setVehimg(fileUploadClient.getUrl(deail.getVehimg()));
			}
			//检查人电子签名
			if(StrUtil.isNotEmpty(deail.getJcrsignatrue()) && deail.getJcrsignatrue().contains("http") == false){
				deail.setJcrsignatrue(fileUploadClient.getUrl(deail.getJcrsignatrue()));
			}
			//审核人电子签名
			if(StrUtil.isNotEmpty(deail.getShenhesignatrue()) && deail.getShenhesignatrue().contains("http") == false){
				deail.setShenhesignatrue(fileUploadClient.getUrl(deail.getShenhesignatrue()));
			}
			//整改责任人电子签名
			if(StrUtil.isNotEmpty(deail.getZgzrrsignatrue()) && deail.getZgzrrsignatrue().contains("http") == false){
				deail.setZgzrrsignatrue(fileUploadClient.getUrl(deail.getZgzrrsignatrue()));
			}
			//整改验收人电子签名
			if(StrUtil.isNotEmpty(deail.getZgysrsignatrue()) && deail.getZgysrsignatrue().contains("http") == false){
				deail.setZgysrsignatrue(fileUploadClient.getUrl(deail.getZgysrsignatrue()));
			}
			QueryWrapper<AnbiaoCarExamineInfoRemark> remarkQueryWrapper = new QueryWrapper<AnbiaoCarExamineInfoRemark>();
			remarkQueryWrapper.lambda().eq(AnbiaoCarExamineInfoRemark::getExamid, deail.getId());
			remarkQueryWrapper.lambda().eq(AnbiaoCarExamineInfoRemark::getStatus, 1);
			List<AnbiaoCarExamineInfoRemark> remark = iAnbiaoCarExamineInfoRemarkService.getBaseMapper().selectList(remarkQueryWrapper);
			if(remark != null){
				deail.setAnbiaoCarExamineInfoRemarkList(remark);
				remark.forEach(item-> {
					QueryWrapper<AnbiaoCarExamine> examineQueryWrapper = new QueryWrapper<AnbiaoCarExamine>();
					examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getId, item.getXiangid());
					examineQueryWrapper.lambda().eq(AnbiaoCarExamine::getIsdelete, 0);
					AnbiaoCarExamine xiangdeail = iAnbiaoCarExamineService.getBaseMapper().selectOne(examineQueryWrapper);
					if(xiangdeail != null){
						item.setXiangname(xiangdeail.getName());
					}
					//异常附件
					if(StrUtil.isNotEmpty(item.getFlgimg()) && item.getFlgimg().contains("http") == false){
						item.setFlgimg(fileUploadClient.getUrl(item.getFlgimg()));
					}
					//整改附件
					if(StrUtil.isNotEmpty(item.getTrueimg()) && item.getTrueimg().contains("http") == false){
						item.setTrueimg(fileUploadClient.getUrl(item.getTrueimg()));
					}
				});
			}
		}
		return R.data(deail);
	}

	@PostMapping("/updateCarExamineStatus")
	@ApiLog("安全检查数据-审核、整改、验收")
	@ApiOperation(value = "安全检查数据-审核、整改、验收", notes = "传入anbiaoCarExamineInfo", position = 5)
	public R updateCarExamineStatus(@RequestBody AnbiaoCarExamineInfo anbiaoCarExamineInfo, BladeUser user) {
		R rs = new R();

		boolean ii = false;
		if(anbiaoCarExamineInfo.getStatus() == 2){
			anbiaoCarExamineInfo.setShenherenid(user.getUserId());
			anbiaoCarExamineInfo.setShenhenrenname(user.getUserName());
			anbiaoCarExamineInfo.setShenheshijian(DateUtil.now());
			anbiaoCarExamineInfo.setStatus(2);
			anbiaoCarExamineInfo.setShenhesignatrue(anbiaoCarExamineInfo.getShenhesignatrue());
			anbiaoCarExamineInfo.setId(anbiaoCarExamineInfo.getId());
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if (ii == true) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("审核成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("审核失败");
			}
		}

		if(anbiaoCarExamineInfo.getStatus() == 3){
			anbiaoCarExamineInfo.setShenherenid(user.getUserId());
			anbiaoCarExamineInfo.setShenhenrenname(user.getUserName());
			anbiaoCarExamineInfo.setShenheshijian(DateUtil.now());
			anbiaoCarExamineInfo.setStatus(3);
			anbiaoCarExamineInfo.setShenhesignatrue(anbiaoCarExamineInfo.getShenhesignatrue());
			anbiaoCarExamineInfo.setId(anbiaoCarExamineInfo.getId());
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if (ii == true) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("审核成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("审核失败");
			}
		}

		if(anbiaoCarExamineInfo.getStatus() == 4){
			anbiaoCarExamineInfo.setShenheshijian(DateUtil.now());
			anbiaoCarExamineInfo.setStatus(4);
			anbiaoCarExamineInfo.setZgzrrsignatrue(anbiaoCarExamineInfo.getZgzrrsignatrue());
			anbiaoCarExamineInfo.setId(anbiaoCarExamineInfo.getId());
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if(ii == true) {
				List<AnbiaoCarExamineInfoRemark> examineInfos = anbiaoCarExamineInfo.getAnbiaoCarExamineInfoRemarkList();
				examineInfos.forEach(item-> {
					AnbiaoCarExamineInfoRemark remark = new AnbiaoCarExamineInfoRemark();
					remark.setXiangid(item.getXiangid());
					remark.setTrueremark(item.getTrueremark());
					remark.setTrueimg(item.getTrueimg());
					remark.setUpdatetime(DateUtil.now());
					remark.setUpdateid(anbiaoCarExamineInfo.getJsyid());
					remark.setId(item.getId());
					boolean is = iAnbiaoCarExamineInfoRemarkService.updateById(remark);
					if (is == true) {
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("整改成功");
					} else {
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("整改失败");
					}
				});
			}
		}

		if(anbiaoCarExamineInfo.getStatus() == 5){
			anbiaoCarExamineInfo.setYanshourenid(user.getUserId());
			anbiaoCarExamineInfo.setYanshourenname(user.getUserName());
			anbiaoCarExamineInfo.setZgshenheshijian(DateUtil.now());
			anbiaoCarExamineInfo.setZgysrsignatrue(anbiaoCarExamineInfo.getZgysrsignatrue());
			anbiaoCarExamineInfo.setFujianremark(anbiaoCarExamineInfo.getFujianremark());
			anbiaoCarExamineInfo.setStatus(5);
			anbiaoCarExamineInfo.setShenhesignatrue(anbiaoCarExamineInfo.getShenhesignatrue());
			anbiaoCarExamineInfo.setId(anbiaoCarExamineInfo.getId());
			ii = iAnbiaoCarExamineInfoService.updateById(anbiaoCarExamineInfo);
			if (ii == true) {
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("验收归档成功");
			} else {
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("验收归档失败");
			}
		}

		return rs;
	}

	@PostMapping("/getCarExamineInfoList")
	@ApiLog("安全检查数据-分页列表")
	@ApiOperation(value = "安全检查数据-分页列表", notes = "传入AnbiaoCarExamineInfoPage", position = 6)
	public R<AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoVO>> getCarExamineInfoList(@RequestBody AnbiaoCarExamineInfoPage carExamineInfoPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}
		AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoVO> list= iAnbiaoCarExamineInfoService.selectCarExamineInfoPage(carExamineInfoPage);
		return R.data(list);
	}

	@PostMapping("/saveCarExamineInfoDept")
	@ApiLog("安全检查数据-一键打卡（企管端）")
	@ApiOperation(value = "安全检查数据-一键打卡（企管端）", notes = "传入anbiaoCarExamineInfo", position = 7)
	public R saveCarExamineInfoDept(@RequestBody AnbiaoCarExamineInfo anbiaoCarExamineInfo, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}

		boolean ii = false;

		String[] idsss = anbiaoCarExamineInfo.getVehid().split(",");
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
			anbiaoCarExamineInfo.setIsdelete(0);
			anbiaoCarExamineInfo.setCreatetime(DateUtil.now());

			QueryWrapper<AnbiaoCarExamineInfo> examineQueryWrapper = new QueryWrapper<AnbiaoCarExamineInfo>();
			examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getDeptid, anbiaoCarExamineInfo.getDeptid());
			examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getIsdelete, 0);
			examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getDate, anbiaoCarExamineInfo.getDate());
			examineQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getVehid, idsss[i]);
			AnbiaoCarExamineInfo deail = iAnbiaoCarExamineInfoService.getBaseMapper().selectOne(examineQueryWrapper);
			if(deail == null){
				anbiaoCarExamineInfo.setVehid(idsss[i]);
				anbiaoCarExamineInfo.setJsyid(user.getUserId().toString());
				if(anbiaoCarExamineInfo.getStatus() == 0){
					anbiaoCarExamineInfo.setStatus(0);
					anbiaoCarExamineInfo.setIsdelete(0);
					ii = iAnbiaoCarExamineInfoService.save(anbiaoCarExamineInfo);
					if (ii == true) {
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("编辑成功");
					} else {
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("编辑失败");
					}
				}else{
					anbiaoCarExamineInfo.setStatus(1);
					ii = iAnbiaoCarExamineInfoService.save(anbiaoCarExamineInfo);
					if(ii == true) {
						deail = iAnbiaoCarExamineInfoService.getBaseMapper().selectOne(examineQueryWrapper);
						if (deail != null) {
							List<AnbiaoCarExamineInfoRemark> examineInfos = anbiaoCarExamineInfo.getAnbiaoCarExamineInfoRemarkList();
							AnbiaoCarExamineInfo finalDeail = deail;
							examineInfos.forEach(item-> {
								AnbiaoCarExamineInfoRemark remark = new AnbiaoCarExamineInfoRemark();
								remark.setExamid(finalDeail.getId());
								remark.setStatus(1);
								remark.setXiangid(item.getXiangid());
								remark.setFlgremark(item.getFlgremark());
								remark.setFlgimg(item.getFlgimg());
								remark.setTrueimg(item.getTrueimg());
								remark.setTrueremark(item.getTrueremark());
								remark.setCreatetime(DateUtil.now());
								remark.setCreateid(finalDeail.getJsyid());
								boolean is = iAnbiaoCarExamineInfoRemarkService.save(remark);
								if (is == true) {
									rs.setCode(200);
									rs.setSuccess(true);
									rs.setMsg("新增成功");
								} else {
									rs.setCode(500);
									rs.setSuccess(false);
									rs.setMsg("新增失败");
								}
							});
						}
					}
				}
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("该数据已添加");
			}
		}
		return rs;
	}

	@PostMapping("/getCarExamineInfoDateList")
	@ApiLog("安全检查数据-日历统计表")
	@ApiOperation(value = "安全检查数据-日历统计表", notes = "传入AnBiaoCheckCarPage", position = 8)
	public R getCarExamineInfoDateList(@RequestBody AnBiaoCheckCarPage carExamineInfoPage, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(401);
			return rs;
		}
		List<AnbiaoCarExamineInfo> list= iAnbiaoCarExamineInfoService.selectAnBiaoCheckCarALLPage(carExamineInfoPage);
		return R.data(list);
	}

}
