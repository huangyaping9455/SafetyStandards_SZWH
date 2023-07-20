package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.repairs.entity.*;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsInfo;
import org.springblade.anbiao.repairs.page.AnbiaoRepairsDeptPage;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsInfoService;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsRemarkService;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsReturnService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

	@PostMapping("/insert")
	@ApiLog("报修单管理-新增、编辑、派单、接单、预约、维修、审核、取消")
	@ApiOperation(value = "报修单管理-新增、编辑、派单、接单、预约、维修、审核、取消", notes = "传入AnbiaoRepairsInfo", position = 1)
	public R insert(@RequestBody AnbiaoRepairsInfo repairsInfo, BladeUser user) {
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
				QueryWrapper<AnbiaoRepairsRemark> repairsRemarkQueryWrapper = new QueryWrapper<AnbiaoRepairsRemark>();
				repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtRpId, repairsInfo.getRpId());
				repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtType, repairsInfo.getRpStatus());
				AnbiaoRepairsRemark repairsRemark = repairsRemarkService.getBaseMapper().selectOne(repairsRemarkQueryWrapper);
				if(repairsRemark == null) {
					AnbiaoRepairsRemark remark = repairsInfo.getRemark();
					remark.setRpdtType(repairsInfo.getRpStatus());
					if (user != null) {
						remark.setRpdtCreatename(user.getUserName());
						remark.setRpdtCreateid(user.getUserId());
					}
					remark.setRpdtCreatetime(DateUtil.now());
					remark.setRpdtDate(DateUtil.now());
					ii = repairsRemarkService.save(remark);
					if (ii) {
						if("2".equals(repairsInfo.getRpStatus())){
							r.setMsg("派单成功");
							r.setCode(200);
							r.setSuccess(true);
						}
						if("3".equals(repairsInfo.getRpStatus())){
							r.setMsg("转单成功");
							r.setCode(200);
							r.setSuccess(true);
						}
						if("4".equals(repairsInfo.getRpStatus())){
							r.setMsg("接单成功");
							r.setCode(200);
							r.setSuccess(true);
						}
						if("6".equals(repairsInfo.getRpStatus())){
							r.setMsg("预约成功");
							r.setCode(200);
							r.setSuccess(true);
						}
						if("8".equals(repairsInfo.getRpStatus())){
							r.setMsg("维修成功");
							r.setCode(200);
							r.setSuccess(true);
						}
						if("9".equals(repairsInfo.getRpStatus()) || "10".equals(repairsInfo.getRpStatus())){
							r.setMsg("审核成功");
							r.setCode(200);
							r.setSuccess(true);
						}
						if("11".equals(repairsInfo.getRpStatus())){
							r.setMsg("取消成功");
							r.setCode(200);
							r.setSuccess(true);
						}
					}else {
						if("2".equals(repairsInfo.getRpStatus())){
							r.setMsg("派单失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if("3".equals(repairsInfo.getRpStatus())){
							r.setMsg("转单失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if("4".equals(repairsInfo.getRpStatus())){
							r.setMsg("接单失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if("6".equals(repairsInfo.getRpStatus())){
							r.setMsg("预约失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if("8".equals(repairsInfo.getRpStatus())){
							r.setMsg("维修失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if("9".equals(repairsInfo.getRpStatus()) || "10".equals(repairsInfo.getRpStatus())){
							r.setMsg("审核失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						if("11".equals(repairsInfo.getRpStatus())){
							r.setMsg("取消失败");
							r.setCode(500);
							r.setSuccess(false);
						}
						return r;
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
			dangerQueryWrapper.lambda().eq(AnbiaoRepairsInfo::getRpStatus, 1);
			AnbiaoRepairsInfo deail = repairsInfoService.getBaseMapper().selectOne(dangerQueryWrapper);
			if(deail == null) {
				repairsInfo.setRpStatus(1);

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
						repairsInfo.setRpNo(xuhao);
					}else if(xuhao.length() >=2 && xuhao.length() < 3){
						xuhao = "00"+xuhao;
						repairsInfo.setRpNo(xuhao);
					}else if(xuhao.length() >=3 && xuhao.length() < 4){
						xuhao = "0"+xuhao;
						repairsInfo.setRpNo(xuhao);
					}else{
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
				if (ii) {
					QueryWrapper<AnbiaoRepairsRemark> repairsRemarkQueryWrapper = new QueryWrapper<AnbiaoRepairsRemark>();
					repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtRpId, repairsInfo.getRpId());
					repairsRemarkQueryWrapper.lambda().eq(AnbiaoRepairsRemark::getRpdtType, repairsInfo.getRpStatus());
					AnbiaoRepairsRemark repairsRemark = repairsRemarkService.getBaseMapper().selectOne(repairsRemarkQueryWrapper);
					if(repairsRemark == null) {
						AnbiaoRepairsRemark remark = repairsInfo.getRemark();
						remark.setRpdtType(repairsInfo.getRpStatus());
						if (user != null) {
							remark.setRpdtCreatename(user.getUserName());
							remark.setRpdtCreateid(user.getUserId());
						}
						remark.setRpdtCreatetime(DateUtil.now());
						remark.setRpdtDate(DateUtil.now());
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
		@ApiImplicitParam(name = "driverId", value = "驾驶员ID", required = true),
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

}