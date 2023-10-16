package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineVO;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.repairs.entity.*;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsInfo;
import org.springblade.anbiao.repairs.page.AnbiaoRepairsDeptPage;
import org.springblade.anbiao.repairs.service.*;
import org.springblade.anbiao.zhengfu.entity.Organization;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2023-07-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/repairsInfo")
@Api(value = "报修-报修单管理", tags = "报修-报修单管理")
public class AnbiaoRepairsInfoController {

	private IAnbiaoRepairsInfoService repairsInfoService;

	private IAnbiaoRepairsRemarkService repairsRemarkService;

	private IAnbiaoRepairsReturnService repairsReturnService;

	private IOrganizationsService organizationService;

	private IVehicleService vehicleService;

	private IAnbiaoRepairsPersonService personService;

	private IAnbiaoRepairsSpareInfoService repairsSpareInfoService;

	private IAnbiaoSparePartsStorePersonService sparePartsStorePersonService;


	@PostMapping("/insert")
	@ApiLog("报修单管理-新增、编辑、派单、接单、预约、维修、审核、取消")
	@ApiOperation(value = "报修单管理-新增、编辑、派单、接单、预约、维修、审核、取消", notes = "传入AnbiaoRepairsInfo", position = 1)
	public R insert(@RequestBody AnbiaoRepairsInfo repairsInfo, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isNotEmpty(repairsInfo.getRpId())){
			if (user != null) {
				repairsInfo.setRpUpdatename(user.getUserName());
				repairsInfo.setRpUpdateid(user.getUserId());
			}
			repairsInfo.setRpUpdatetime(DateUtil.now());
			ii = repairsInfoService.updateById(repairsInfo);
			if (ii) {
				//添加设备使用记录
				List<AnbiaoRepairsSpareInfo> repairsSpareInfoList = repairsInfo.getRepairsSpareInfoList();
				if(repairsSpareInfoList != null && repairsSpareInfoList.size() > 0){
					for (int i = 0; i < repairsSpareInfoList.size(); i++) {
						AnbiaoRepairsSpareInfo repairsSpareInfo = repairsSpareInfoList.get(i);
						repairsSpareInfo.setRppsCreatetime(DateUtil.now());
						QueryWrapper<AnbiaoRepairsSpareInfo> repairsSpareInfoQueryWrapper = new QueryWrapper<AnbiaoRepairsSpareInfo>();

						repairsSpareInfoQueryWrapper.lambda().eq(AnbiaoRepairsSpareInfo::getRppsRpId, repairsInfo.getRpId());
						repairsSpareInfoQueryWrapper.lambda().eq(AnbiaoRepairsSpareInfo::getRppsSppNo, repairsSpareInfo.getRppsSppNo());
						repairsSpareInfoQueryWrapper.lambda().eq(AnbiaoRepairsSpareInfo::getRppsSpNo, repairsSpareInfo.getRppsSpNo());
						repairsSpareInfoQueryWrapper.lambda().eq(AnbiaoRepairsSpareInfo::getRppsNum, repairsSpareInfo.getRppsNum());
						repairsSpareInfoQueryWrapper.lambda().eq(AnbiaoRepairsSpareInfo::getRppsCreatetime, repairsSpareInfo.getRppsCreatetime());
						AnbiaoRepairsSpareInfo repairsSpareInfo1 = repairsSpareInfoService.getBaseMapper().selectOne(repairsSpareInfoQueryWrapper);
						System.out.println(repairsSpareInfo1);
						if(repairsSpareInfo1 == null && repairsSpareInfo.getRppsId() == null) {
							ii = repairsSpareInfoService.save(repairsSpareInfo);
						}

					}
				}
				QueryWrapper<AnbiaoRepairsRemark> repairsRemarkQueryWrapper = new QueryWrapper<AnbiaoRepairsRemark>();
				repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtRpId, repairsInfo.getRpId());
				repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtType, repairsInfo.getRpStatus());
				repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtDate, repairsInfo.getRpDate());
				AnbiaoRepairsRemark repairsRemark = repairsRemarkService.getBaseMapper().selectOne(repairsRemarkQueryWrapper);
				if(repairsRemark == null) {
					AnbiaoRepairsRemark remark = repairsInfo.getRemark();
					remark.setRpdtRpId(repairsInfo.getRpId());
					remark.setRpdtType(repairsInfo.getRpStatus());
					if (user != null) {
						remark.setRpdtCreatename(user.getUserName());
						remark.setRpdtCreateid(user.getUserId());
					}
					remark.setRpdtCreatetime(DateUtil.now());
					remark.setRpdtDate(DateUtil.now());
					ii = repairsRemarkService.save(remark);
					if (ii) {
						if(2 == repairsInfo.getRpStatus()){
							r.setMsg("派单成功");
							r.setCode(200);
							r.setSuccess(true);
						}
						if(3 == repairsInfo.getRpStatus()){
							System.out.println("转单");
							System.out.println(DateUtil.now());
							TimeUnit.SECONDS.sleep(2);
							remark = repairsInfo.getRemark();
							remark.setRpdtRpId(repairsInfo.getRpId());
							remark.setRpdtType(2);
							if (user != null) {
								remark.setRpdtCreatename(user.getUserName());
								remark.setRpdtCreateid(user.getUserId());
							}
							remark.setRpdtCreatetime(DateUtil.now());
							remark.setRpdtDate(DateUtil.now());
							ii = repairsRemarkService.save(remark);
							if (user != null) {
								repairsInfo.setRpUpdatename(user.getUserName());
								repairsInfo.setRpUpdateid(user.getUserId());
							}
							repairsInfo.setRpUpdatetime(DateUtil.now());
							ii = repairsInfoService.updateById(repairsInfo);
							if (ii) {
								r.setMsg("转单成功");
								r.setCode(200);
								r.setSuccess(true);
							}else{
								r.setMsg("转单失败");
								r.setCode(500);
								r.setSuccess(false);
							}
						}
						if(4 == repairsInfo.getRpStatus()){
							r.setMsg("接单成功");
							r.setCode(200);
							r.setSuccess(true);
						}
						if(6 == repairsInfo.getRpStatus() || 7 == repairsInfo.getRpStatus()){
							repairsInfo.setRpYyDate(remark.getRpdtDate());
							repairsInfo.setRpYyAddress(remark.getRpdtYwAddress());
							ii = repairsInfoService.updateById(repairsInfo);
							if (ii) {
								r.setMsg("预约成功");
								r.setCode(200);
								r.setSuccess(true);
							}else{
								r.setMsg("预约失败");
								r.setCode(500);
								r.setSuccess(false);
							}
						}
						if(8 == repairsInfo.getRpStatus()){
							r.setMsg("维修成功");
							r.setCode(200);
							r.setSuccess(true);
						}
						if(9 == repairsInfo.getRpStatus() || 10 == repairsInfo.getRpStatus()){
							if(10 == repairsInfo.getRpStatus()){
								//根据设备使用记录更新员工库存数据
								repairsSpareInfoList = repairsInfo.getRepairsSpareInfoList();
								if(repairsSpareInfoList != null && repairsSpareInfoList.size() > 0){
									for (int i = 0; i < repairsSpareInfoList.size(); i++) {
										AnbiaoRepairsSpareInfo repairsSpareInfo = repairsSpareInfoList.get(i);
//										repairsSpareInfo.setRppsType(1);
										repairsSpareInfo.setRppsId(repairsSpareInfo.getRppsId());
										ii = repairsSpareInfoService.updateById(repairsSpareInfo);
										//审核通过更新员工库存
										if(repairsSpareInfo.getRppsType() == 1){
											//员工库存管理
											QueryWrapper<AnbiaoSparePartsStorePerson> storePersonQueryWrapper = new QueryWrapper<AnbiaoSparePartsStorePerson>();
//										storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppDeptId, repairsSpareInfo.getDeptId());
											storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppSpNo, repairsSpareInfo.getRppsSpNo());
											storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppPersonid, repairsSpareInfo.getRppsCreateid());
											storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppDelete, 0);
											AnbiaoSparePartsStorePerson person = sparePartsStorePersonService.getBaseMapper().selectOne(storePersonQueryWrapper);
											if (person != null) {
												if (user != null) {
													person.setSppUpdatename(user.getUserName());
													person.setSppUpdateid(user.getUserId());
												}
												person.setSppUpdatetime(DateUtil.now());
												person.setSppGoodProductsNum(person.getSppGoodProductsNum()-repairsSpareInfo.getRppsNum());
												ii = sparePartsStorePersonService.updateById(person);
												if(ii){
													r.setMsg("审核成功");
													r.setCode(200);
													r.setSuccess(true);
												}
											}
										}
									}
								}
							}else{
								r.setMsg("审核成功");
								r.setCode(200);
								r.setSuccess(true);
							}
						}
						if(11 == repairsInfo.getRpStatus()){
							r.setMsg("取消成功");
							r.setCode(200);
							r.setSuccess(true);
						}
					}else {
						if(2 == repairsInfo.getRpStatus()){
							r.setMsg("派单失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if(3 == repairsInfo.getRpStatus()){
							r.setMsg("转单失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if(4 == repairsInfo.getRpStatus()){
							r.setMsg("接单失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if(6 == repairsInfo.getRpStatus() || 7 == repairsInfo.getRpStatus()){
							r.setMsg("预约失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if(8 == repairsInfo.getRpStatus()){
							r.setMsg("维修失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if(9 == repairsInfo.getRpStatus() || 10 == repairsInfo.getRpStatus()){
							r.setMsg("审核失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if(11 == repairsInfo.getRpStatus()){
							r.setMsg("取消失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						return r;
					}
				}else{
					if(3 == repairsInfo.getRpStatus()){
						AnbiaoRepairsRemark remark = repairsInfo.getRemark();
						repairsRemarkQueryWrapper = new QueryWrapper<AnbiaoRepairsRemark>();
						repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtRpId, repairsInfo.getRpId());
						repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtType, repairsInfo.getRpStatus());
						repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtDate, remark.getRpdtDate());
						repairsRemark = repairsRemarkService.getBaseMapper().selectOne(repairsRemarkQueryWrapper);
						if(repairsRemark == null) {
							System.out.println("转单");
							System.out.println(DateUtil.now());
							TimeUnit.SECONDS.sleep(2);
							remark = repairsInfo.getRemark();
							remark.setRpdtRpId(repairsInfo.getRpId());
							remark.setRpdtType(2);
							if (user != null) {
								remark.setRpdtCreatename(user.getUserName());
								remark.setRpdtCreateid(user.getUserId());
							}
							remark.setRpdtCreatetime(DateUtil.now());
							remark.setRpdtDate(DateUtil.now());
							ii = repairsRemarkService.save(remark);
							if (user != null) {
								repairsInfo.setRpUpdatename(user.getUserName());
								repairsInfo.setRpUpdateid(user.getUserId());
							}
							repairsInfo.setRpUpdatetime(DateUtil.now());
							ii = repairsInfoService.updateById(repairsInfo);
							if (ii) {
								r.setMsg("转单成功");
								r.setCode(200);
								r.setSuccess(true);
							} else {
								r.setMsg("转单失败");
								r.setCode(500);
								r.setSuccess(false);
							}
						}
					}else if(repairsInfo.getRpStatus() == 6 || repairsInfo.getRpStatus() == 8){
						AnbiaoRepairsRemark remark = repairsInfo.getRemark();
						repairsRemarkQueryWrapper = new QueryWrapper<AnbiaoRepairsRemark>();
						repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtRpId, repairsInfo.getRpId());
						repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtType, repairsInfo.getRpStatus());
						repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtDate, remark.getRpdtDate());
						repairsRemark = repairsRemarkService.getBaseMapper().selectOne(repairsRemarkQueryWrapper);
						if(repairsRemark == null) {
							remark.setRpdtRpId(repairsInfo.getRpId());
							remark.setRpdtType(repairsInfo.getRpStatus());
							if (user != null) {
								remark.setRpdtCreatename(user.getUserName());
								remark.setRpdtCreateid(user.getUserId());
							}
							remark.setRpdtCreatetime(DateUtil.now());
							remark.setRpdtDate(DateUtil.now());
							ii = repairsRemarkService.save(remark);
							if (ii) {
								if(6 == repairsInfo.getRpStatus() || 7 == repairsInfo.getRpStatus()) {
									r.setMsg("预约成功");
									r.setCode(200);
									r.setSuccess(true);
								}
								if(8 == repairsInfo.getRpStatus()) {
									r.setMsg("维修成功");
									r.setCode(200);
									r.setSuccess(true);
								}
							}else {
								if(6 == repairsInfo.getRpStatus() || 7 == repairsInfo.getRpStatus()) {
									r.setMsg("预约失败");
									r.setCode(500);
									r.setSuccess(false);
									return r;
								}
								if(8 == repairsInfo.getRpStatus()) {
									r.setMsg("维修失败");
									r.setCode(500);
									r.setSuccess(false);
									return r;
								}
							}
						}
					}else{
						r.setMsg("编辑成功");
						r.setCode(200);
						r.setSuccess(true);
					}
				}
			} else {
				r.setMsg("编辑失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}else{
			QueryWrapper<AnbiaoRepairsInfo> dangerQueryWrapper = new QueryWrapper<AnbiaoRepairsInfo>();
			dangerQueryWrapper.lambda().eq(AnbiaoRepairsInfo::getRpDeptId, repairsInfo.getRpDeptId());
			dangerQueryWrapper.lambda().eq(AnbiaoRepairsInfo::getRpNo, repairsInfo.getRpNo());
			dangerQueryWrapper.lambda().eq(AnbiaoRepairsInfo::getRpStatus, 0);
			AnbiaoRepairsInfo deail = repairsInfoService.getBaseMapper().selectOne(dangerQueryWrapper);
			if(deail == null) {
				repairsInfo.setRpStatus(0);

				//获取当前企业当天最大序列号 维修单号：20230620-4587-0001
				String xuhao = "";
				AnbiaoRepairsInfo anbiaoRepairsInfo = repairsInfoService.selectMaxXuhao(repairsInfo.getRpDeptId().toString());
				if(anbiaoRepairsInfo != null && StringUtils.isNotEmpty(anbiaoRepairsInfo.getRpNo())){
					xuhao = anbiaoRepairsInfo.getRpNo();
					Integer xh = Integer.parseInt(xuhao.substring(xuhao.length() - 4));
					xh = xh+=1;
					xuhao = xh.toString();
					if(xuhao.length() < 2){
						xuhao = "000"+xuhao;
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date())+"-"+repairsInfo.getRpDeptId()+"-"+xuhao;
						repairsInfo.setRpNo(xuhao);
					}else if(xuhao.length() >=2 && xuhao.length() < 3){
						xuhao = "00"+xuhao;
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date())+"-"+repairsInfo.getRpDeptId()+"-"+xuhao;
						repairsInfo.setRpNo(xuhao);
					}else if(xuhao.length() >=3 && xuhao.length() < 4){
						xuhao = "0"+xuhao;
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date())+"-"+repairsInfo.getRpDeptId()+"-"+xuhao;
						repairsInfo.setRpNo(xuhao);
					}else{
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date())+"-"+repairsInfo.getRpDeptId()+"-"+xuhao;
						repairsInfo.setRpNo(xuhao);
					}
				}else{
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date())+"-"+repairsInfo.getRpDeptId()+"-0001";
					repairsInfo.setRpNo(xuhao);
				}
				if (user != null) {
					repairsInfo.setRpCreatename(user.getUserName());
					repairsInfo.setRpCreateid(user.getUserId());
				}
				repairsInfo.setRpCreatetime(DateUtil.now());
				ii = repairsInfoService.save(repairsInfo);
//				//添加设备使用记录
//				List<AnbiaoRepairsSpareInfo> repairsSpareInfoList = repairsInfo.getRepairsSpareInfoList();
//				if(repairsSpareInfoList != null && repairsSpareInfoList.size() > 0){
//					for (int i = 0; i < repairsSpareInfoList.size(); i++) {
//						AnbiaoRepairsSpareInfo repairsSpareInfo = repairsSpareInfoList.get(i);
//						if (user != null) {
//							repairsSpareInfo.setRppsCreatename(user.getUserName());
//							repairsSpareInfo.setRppsCreateid(user.getUserId());
//						}
//						repairsSpareInfo.setRppsCreatetime(DateUtil.now());
//						ii = repairsSpareInfoService.save(repairsSpareInfo);
//					}
//				}
				if (ii) {
					QueryWrapper<AnbiaoRepairsRemark> repairsRemarkQueryWrapper = new QueryWrapper<AnbiaoRepairsRemark>();
					repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtRpId, repairsInfo.getRpId());
					repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtType, repairsInfo.getRpStatus());
					AnbiaoRepairsRemark repairsRemark = repairsRemarkService.getBaseMapper().selectOne(repairsRemarkQueryWrapper);
					if(repairsRemark == null) {
						AnbiaoRepairsRemark remark = repairsInfo.getRemark();
						if(remark == null){
							remark = new AnbiaoRepairsRemark();
						}
						remark.setRpdtType(repairsInfo.getRpStatus());
						if (user != null) {
							remark.setRpdtCreatename(user.getUserName());
							remark.setRpdtCreateid(user.getUserId());
						}
						remark.setRpdtCreatetime(DateUtil.now());
						remark.setRpdtDate(DateUtil.now());
						remark.setRpdtRpId(repairsInfo.getRpId());
						ii = repairsRemarkService.save(remark);
						if (ii) {
							r.setMsg("新增成功");
							r.setCode(200);
							r.setSuccess(true);
						}else {
							r.setMsg("新增失败");
							r.setCode(500);
							r.setSuccess(false);
							return r;
						}
					}
				} else {
					r.setMsg("新增失败");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		}
		return r;
	}

	@PostMapping("/return")
	@ApiLog("报修单管理-回访")
	@ApiOperation(value = "报修单管理-回访", notes = "传入AnbiaoRepairsReturn", position = 2)
	public R returnInfo(@RequestBody AnbiaoRepairsReturn repairsReturn, BladeUser user) {
		R r = new R();
		boolean ii = false;
		QueryWrapper<AnbiaoRepairsReturn> dangerQueryWrapper = new QueryWrapper<AnbiaoRepairsReturn>();
		dangerQueryWrapper.lambda().eq(AnbiaoRepairsReturn::getRetRpId, repairsReturn.getRetRpId());
		AnbiaoRepairsReturn deail = repairsReturnService.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null) {
			if (user != null) {
				repairsReturn.setRetCreatename(user.getUserName());
				repairsReturn.setRetCreateid(user.getUserId());
			}
			repairsReturn.setRetCreatetime(DateUtil.now());
			repairsReturn.setRetDate(DateUtil.now());
			ii = repairsReturnService.save(repairsReturn);
			if (ii) {
				r.setMsg("回访成功");
				r.setCode(200);
				r.setSuccess(true);
			} else {
				r.setMsg("回访失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}
		return r;
	}

	@GetMapping("/returnRemark")
	@ApiLog("报修单管理-回访详情")
	@ApiOperation(value = "报修单管理-回访详情", notes = "传入运单Id", position = 2)
	public R returnRemark(String Id, BladeUser user) {
		R r = new R();
		boolean ii = false;
		QueryWrapper<AnbiaoRepairsReturn> dangerQueryWrapper = new QueryWrapper<AnbiaoRepairsReturn>();
		dangerQueryWrapper.lambda().eq(AnbiaoRepairsReturn::getRetRpId, Id);
		AnbiaoRepairsReturn deail = repairsReturnService.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail != null) {
			r.setMsg("获取成功");
			r.setCode(200);
			r.setData(deail);
			r.setSuccess(true);
		} else {
			r.setMsg("获取成功，暂无数据");
			r.setCode(200);
			r.setData(null);
			r.setSuccess(true);
		}
		return r;
	}

	@GetMapping("/vewInfo")
	@ApiLog("报修单管理-获取运单详情")
	@ApiOperation(value = "报修单管理-获取运单详情", notes = "传入数据ID", position = 2)
	public R vewInfo(String Id, BladeUser user) {
		R r = new R();
		AnbiaoRepairsInfo deail = repairsInfoService.getBaseMapper().selectById(Id);
		if(deail != null) {
			List<AnbiaoRepairsSpareInfo> repairsSpareTypeList = repairsSpareInfoService.selectByType(deail.getRpId());
//			if(repairsSpareTypeList.size() > 1){
//				//运单备件信息 第一次
//				List<AnbiaoRepairsSpareInfo> repairsSpareInfo = repairsSpareInfoService.selectByDeptIdList(deail.getRpId(),"2");
//				if(repairsSpareInfo != null && repairsSpareInfo.size() > 0){
//					deail.setRepairsSpareInfoList1(repairsSpareInfo);
//				}
//				//运单备件信息 二次
//				List<AnbiaoRepairsSpareInfo> repairsSpareInfoList1 = repairsSpareInfoService.selectByDeptIdList(deail.getRpId(),"1");
//				if(repairsSpareInfoList1 != null && repairsSpareInfoList1.size() > 0){
//					deail.setRepairsSpareInfoList2(repairsSpareInfoList1);
//				}
//			}else{
//				//运单备件信息 一次
//				List<AnbiaoRepairsSpareInfo> repairsSpareInfo = repairsSpareInfoService.selectByDeptIdList(deail.getRpId(),"1");
//				if(repairsSpareInfo != null){
//					deail.setRepairsSpareInfoList1(repairsSpareInfo);
//				}
//			}
			QueryWrapper<Organizations> organizationQueryWrapper = new QueryWrapper<Organizations>();
			organizationQueryWrapper.lambda().eq(Organizations::getDeptId, deail.getRpDeptId());
			organizationQueryWrapper.lambda().eq(Organizations::getIsdelete,0);
			Organizations organizations = organizationService.getBaseMapper().selectOne(organizationQueryWrapper);
			if(organizations != null){
				deail.setDeptName(organizations.getDeptName());
			}

			QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
			vehicleQueryWrapper.lambda().eq(Vehicle::getId, deail.getRpVehid());
			vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel,0);
			Vehicle vehicle = vehicleService.getBaseMapper().selectOne(vehicleQueryWrapper);
			if(vehicle != null){
				deail.setCheliangpaizhao(vehicle.getCheliangpaizhao());
				deail.setChepaiyanse(vehicle.getChepaiyanse());
			}
			QueryWrapper<AnbiaoRepairsRemark> remarkQueryWrapper = new QueryWrapper<AnbiaoRepairsRemark>();
			remarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtRpId, deail.getRpId());
			remarkQueryWrapper.lambda().orderByDesc(AnbiaoRepairsRemark::getRpdtDate);
			List<AnbiaoRepairsRemark> remark = repairsRemarkService.getBaseMapper().selectList(remarkQueryWrapper);
			if(remark != null){
				remark.forEach(item -> {
					if(StringUtils.isNotEmpty(item.getRpdtPersonId())){
						QueryWrapper<AnbiaoRepairsPerson> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
						anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpId, item.getRpdtPersonId());
						anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpDelete, 0);
						AnbiaoRepairsPerson deal = personService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
						if (deal != null) {
							item.setRpdtPersonName(deal.getRpName());
						}
					}
				});
				deail.setRepairsRemarkList(remark);
			}
			//添加设备使用记录
			QueryWrapper<AnbiaoRepairsSpareInfo> repairsSpareInfoQueryWrapper = new QueryWrapper<AnbiaoRepairsSpareInfo>();
			repairsSpareInfoQueryWrapper.lambda().eq(AnbiaoRepairsSpareInfo::getRppsRpId, deail.getRpId());
			repairsSpareInfoQueryWrapper.lambda().eq(AnbiaoRepairsSpareInfo::getRppsDelete, 0);
			repairsSpareInfoQueryWrapper.lambda().orderByAsc(AnbiaoRepairsSpareInfo::getRppsCreatetime);
			List<AnbiaoRepairsSpareInfo> repairsSpareInfoList = repairsSpareInfoService.getBaseMapper().selectList(repairsSpareInfoQueryWrapper);
			if(repairsSpareInfoList != null){
				deail.setRepairsSpareInfoList(repairsSpareInfoList);
			}
			r.setMsg("获取成功");
			r.setData(deail);
			r.setCode(200);
			r.setSuccess(true);
		}else {
			r.setMsg("暂无数据");
			r.setData(null);
			r.setCode(200);
			r.setSuccess(false);
		}
		return r;
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("报修单管理-分页")
	@ApiOperation(value = "报修单管理-分页", notes = "传入AnbiaoRepairsDeptPage", position = 3)
	public R<AnbiaoRepairsDeptPage<AnbiaoRepairsInfo>> list(@RequestBody AnbiaoRepairsDeptPage anbiaoRepairsDeptPage) {
		AnbiaoRepairsDeptPage<AnbiaoRepairsInfo> pages = repairsInfoService.selectPage(anbiaoRepairsDeptPage);
		return R.data(pages);
	}

	@PostMapping("/getDriverRepairsList")
	@ApiLog("报修单管理-分页(报修人员端)")
	@ApiOperation(value = "报修单管理-分页(报修人员端)", notes = "传入AnbiaoRepairsDeptPage", position = 4)
	public R<AnbiaoRepairsDeptPage<AnbiaoRepairsInfo>> getDriverRepairsList(@RequestBody AnbiaoRepairsDeptPage anbiaoRepairsDeptPage) {
		AnbiaoRepairsDeptPage<AnbiaoRepairsInfo> pages = repairsInfoService.selectDriverPage(anbiaoRepairsDeptPage);
		return R.data(pages);
	}

	@GetMapping("/getDriverRepairsCount")
	@ApiLog("维修人员管理-根据维修人员ID获取维修工单数")
	@ApiOperation(value = "维修人员管理-根据维修人员ID获取维修工单数", notes = "维修人员ID、工单类型", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "driverId", value = "维修人员ID", required = true),
		@ApiImplicitParam(name = "rpType", value = "工单类型，1：新装工单，2：维修工单", required = true)
	})
	public R<AnbiaoRepairsInfo> getDriverRepairsCount(String driverId,Integer rpType) {
		AnbiaoRepairsDeptPage anbiaoRepairsDeptPage = new AnbiaoRepairsDeptPage();
		anbiaoRepairsDeptPage.setCurrent(0);
		anbiaoRepairsDeptPage.setSize(0);
		anbiaoRepairsDeptPage.setDriverId(driverId);
		anbiaoRepairsDeptPage.setRpType(rpType);
		AnbiaoRepairsDeptPage<AnbiaoRepairsInfo> pages = repairsInfoService.selectDriverPage(anbiaoRepairsDeptPage);
		AnbiaoRepairsInfo anbiaoRepairsInfo = new AnbiaoRepairsInfo();
		anbiaoRepairsInfo.setCount(pages.getRecords().size());
		return R.data(anbiaoRepairsInfo);
	}

	@PostMapping("/technicalAudit")
	@ApiLog("报修单管理-技术审核")
	@ApiOperation(value = "报修单管理-技术审核", notes = "传入AnbiaoRepairsInfo", position = 6)
	public R technicalAudit(@RequestBody AnbiaoRepairsInfo repairsInfo, BladeUser user) {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isNotEmpty(repairsInfo.getRpId())){
			if (user != null) {
				repairsInfo.setRpUpdatename(user.getUserName());
				repairsInfo.setRpUpdateid(user.getUserId());
			}
			if(repairsInfo.getRpClassify() == 1){
				repairsInfo.setRpStatus(1);
			}else{
				repairsInfo.setRpStatus(12);
			}
			repairsInfo.setRpUpdatetime(DateUtil.now());
			ii = repairsInfoService.updateById(repairsInfo);
			if (ii) {
				QueryWrapper<AnbiaoRepairsRemark> repairsRemarkQueryWrapper = new QueryWrapper<AnbiaoRepairsRemark>();
				repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtRpId, repairsInfo.getRpId());
				if(repairsInfo.getRpClassify() == 2){
					repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtType, 12);
				}else{
					repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtType, 13);
				}
				AnbiaoRepairsRemark repairsRemark = repairsRemarkService.getBaseMapper().selectOne(repairsRemarkQueryWrapper);
				if(repairsRemark == null) {
					AnbiaoRepairsRemark remark = repairsInfo.getRemark();
					remark.setRpdtRpId(repairsInfo.getRpId());
					if(repairsInfo.getRpClassify() == 2){
						remark.setRpdtType(12);
					}else{
						remark.setRpdtType(13);
					}
					if (user != null) {
						remark.setRpdtCreatename(user.getUserName());
						remark.setRpdtCreateid(user.getUserId());
					}
					remark.setRpdtCreatetime(DateUtil.now());
					remark.setRpdtDate(DateUtil.now());
					ii = repairsRemarkService.save(remark);
					if (ii) {
						r.setMsg("保存成功");
						r.setCode(200);
						r.setSuccess(true);
					}else{
						r.setMsg("保存失败");
						r.setCode(500);
						r.setSuccess(false);
					}
				}
			}
		}else{
			r.setMsg("参数不能为空");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		return r;
	}

	@PostMapping("/technicalAuditEnd")
	@ApiLog("报修单管理-远程维护-结单")
	@ApiOperation(value = "报修单管理-远程维护-结单", notes = "传入报修单ID", position = 7)
	public R technicalAuditEnd(@RequestBody AnbiaoRepairsInfo repairsInfo, BladeUser user) {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isNotEmpty(repairsInfo.getRpId())){
			if (user != null) {
				repairsInfo.setRpUpdatename(user.getUserName());
				repairsInfo.setRpUpdateid(user.getUserId());
			}
			repairsInfo.setRpStatus(14);
			repairsInfo.setRpUpdatetime(DateUtil.now());
			ii = repairsInfoService.updateById(repairsInfo);
			if (ii) {
				QueryWrapper<AnbiaoRepairsRemark> repairsRemarkQueryWrapper = new QueryWrapper<AnbiaoRepairsRemark>();
				repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtRpId, repairsInfo.getRpId());
				repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtType, 14);
				repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtDate, DateUtil.now());
				AnbiaoRepairsRemark repairsRemark = repairsRemarkService.getBaseMapper().selectOne(repairsRemarkQueryWrapper);
				if(repairsRemark == null) {
					AnbiaoRepairsRemark remark = repairsInfo.getRemark();
					remark.setRpdtRpId(repairsInfo.getRpId());
					remark.setRpdtType(14);
					if (user != null) {
						remark.setRpdtCreatename(user.getUserName());
						remark.setRpdtCreateid(user.getUserId());
					}
					remark.setRpdtCreatetime(DateUtil.now());
					remark.setRpdtDate(DateUtil.now());
					ii = repairsRemarkService.save(remark);
					if (ii) {
						r.setMsg("保存成功");
						r.setCode(200);
						r.setSuccess(true);
					}else{
						r.setMsg("保存失败");
						r.setCode(500);
						r.setSuccess(false);
					}
				}
			}else{
				r.setMsg("保存失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}else{
			r.setMsg("参数不能为空");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		return r;
	}

	@PostMapping("/del")
	@ApiLog("报修单管理-删除")
	@ApiOperation(value = "报修单管理-删除", notes = "传入AnbiaoRepairsInfo", position = 8)
	public R del(@RequestBody AnbiaoRepairsInfo repairsInfo, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRepairsInfo> repairsInfoQueryWrapper = new QueryWrapper<>();
		repairsInfoQueryWrapper.lambda().eq(AnbiaoRepairsInfo::getRpId, repairsInfo.getRpId());
		repairsInfoQueryWrapper.lambda().eq(AnbiaoRepairsInfo::getRpIsdelete, 0);
		AnbiaoRepairsInfo deal = repairsInfoService.getBaseMapper().selectOne(repairsInfoQueryWrapper);
		if (deal != null) {
			deal.setRpIsdelete(1);
			deal.setRpUpdatetime(DateUtil.now());
			deal.setRpUpdatename(user.getUserName());
			deal.setRpUpdateid(user.getUserId());
			repairsInfoService.updateById(deal);
			r.setMsg("删除成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		} else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}


}
