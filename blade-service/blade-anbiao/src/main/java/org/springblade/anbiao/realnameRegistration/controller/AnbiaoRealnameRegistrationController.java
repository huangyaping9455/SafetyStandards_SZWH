package org.springblade.anbiao.realnameRegistration.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.anbiao.realnameRegistration.entity.AnbiaoRealnameRegistration;
import org.springblade.anbiao.realnameRegistration.entity.AnbiaoRealnameRegistrationInfo;
import org.springblade.anbiao.realnameRegistration.page.AnbiaoRealnameRegistrationPage;
import org.springblade.anbiao.realnameRegistration.service.IAnbiaoRealnameRegistrationInfoService;
import org.springblade.anbiao.realnameRegistration.service.IAnbiaoRealnameRegistrationService;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.tool.StringUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 乘客信息登记表 前端控制器
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/realnameRegistration")
@Api(value = "乘客信息登记管理", tags = "乘客信息登记管理")
public class AnbiaoRealnameRegistrationController {

	private IAnbiaoRealnameRegistrationService realnameRegistrationService;

	private IAnbiaoRealnameRegistrationInfoService realnameRegistrationInfoService;

	private IVehicleService vehicleService;

	@PostMapping("/insert")
	@ApiLog("乘客信息登记管理-新增、编辑")
	@ApiOperation(value = "乘客信息登记管理-新增、编辑", notes = "传入AnbiaoRealnameRegistration", position = 1)
	public R insert(@RequestBody AnbiaoRealnameRegistration realnameRegistration, BladeUser user) {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isEmpty(realnameRegistration.getRnrBeginTime())){
			realnameRegistration.setRnrBeginTime(DateUtil.now());
		}
		QueryWrapper<AnbiaoRealnameRegistration> dangerQueryWrapper = new QueryWrapper<AnbiaoRealnameRegistration>();
		dangerQueryWrapper.lambda().eq(AnbiaoRealnameRegistration::getRnrDeptId, realnameRegistration.getRnrDeptId());
		dangerQueryWrapper.lambda().eq(AnbiaoRealnameRegistration::getRnrDelete, 0);
		dangerQueryWrapper.lambda().eq(AnbiaoRealnameRegistration::getRnrVehId, realnameRegistration.getRnrVehId());
		dangerQueryWrapper.lambda().eq(AnbiaoRealnameRegistration::getRnrBeginTime, realnameRegistration.getRnrBeginTime());
		AnbiaoRealnameRegistration deail = realnameRegistrationService.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null) {
			realnameRegistration.setRnrCreatetime(DateUtil.now());
			if(user != null){
				realnameRegistration.setRnrCreatename(user.getUserName());
				realnameRegistration.setRnrCreateid(user.getUserId());
			}
			ii = realnameRegistrationService.save(realnameRegistration);
			if (ii) {
				List<AnbiaoRealnameRegistrationInfo> remarkList = realnameRegistration.getRealnameRegistrationInfoList();
				if(remarkList.size() > 0 && remarkList != null){
					remarkList.forEach(item-> {
						QueryWrapper<AnbiaoRealnameRegistrationInfo> remarkQueryWrapper = new QueryWrapper<AnbiaoRealnameRegistrationInfo>();
						remarkQueryWrapper.lambda().eq(AnbiaoRealnameRegistrationInfo::getRnriRnrId, realnameRegistration.getRnrId());
						remarkQueryWrapper.lambda().eq(AnbiaoRealnameRegistrationInfo::getRnriCardNumber, item.getRnriCardNumber());
						remarkQueryWrapper.lambda().eq(AnbiaoRealnameRegistrationInfo::getRnriDelete, 0);
						AnbiaoRealnameRegistrationInfo inspectionRemark = realnameRegistrationInfoService.getBaseMapper().selectOne(remarkQueryWrapper);
						if(inspectionRemark == null) {
							AnbiaoRealnameRegistrationInfo realnameRegistrationRemark = new AnbiaoRealnameRegistrationInfo();
							realnameRegistrationRemark.setRnriRnrId(realnameRegistration.getRnrId());
							realnameRegistrationRemark.setRnriName(item.getRnriName());
							realnameRegistrationRemark.setRnriCardNumber(item.getRnriCardNumber());
							realnameRegistrationRemark.setRnriPhone(item.getRnriPhone());
							realnameRegistrationRemark.setRnriDate(item.getRnriDate());
							realnameRegistrationRemark.setRnriAddress(item.getRnriAddress());
							realnameRegistrationRemark.setRnriEndDate(item.getRnriEndDate());
							realnameRegistrationRemark.setRnriEndAddress(item.getRnriEndAddress());
							realnameRegistrationRemark.setRnriDelete(0);
							realnameRegistrationRemark.setRnriCreateid(realnameRegistration.getRnrCreateid());
							realnameRegistrationRemark.setRnriCreatename(realnameRegistrationRemark.getRnriCreatename());
							realnameRegistrationRemark.setRnriCreatetime(DateUtil.now());
							realnameRegistrationInfoService.getBaseMapper().insert(realnameRegistrationRemark);
						}
					});
				}
				r.setMsg("新增成功");
				r.setCode(200);
				r.setSuccess(true);
			} else {
				r.setMsg("新增失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}else{
			realnameRegistration.setRnrId(deail.getRnrId());
			if(user != null){
				realnameRegistration.setRnrUpdatename(user.getUserName());
				realnameRegistration.setRnrUpdateid(user.getUserId());
			}
			realnameRegistration.setRnrUpdatetime(DateUtil.now());
			ii = realnameRegistrationService.updateById(realnameRegistration);
			if(ii){
				List<AnbiaoRealnameRegistrationInfo> remarkList = realnameRegistration.getRealnameRegistrationInfoList();
				if(remarkList.size() > 0 && remarkList != null){
					remarkList.forEach(item-> {
						QueryWrapper<AnbiaoRealnameRegistrationInfo> remarkQueryWrapper = new QueryWrapper<AnbiaoRealnameRegistrationInfo>();
						remarkQueryWrapper.lambda().eq(AnbiaoRealnameRegistrationInfo::getRnriRnrId, realnameRegistration.getRnrId());
						remarkQueryWrapper.lambda().eq(AnbiaoRealnameRegistrationInfo::getRnriCardNumber, item.getRnriCardNumber());
						remarkQueryWrapper.lambda().eq(AnbiaoRealnameRegistrationInfo::getRnriDelete, 0);
						AnbiaoRealnameRegistrationInfo inspectionRemark = realnameRegistrationInfoService.getBaseMapper().selectOne(remarkQueryWrapper);
						if(inspectionRemark == null) {
							AnbiaoRealnameRegistrationInfo realnameRegistrationRemark = new AnbiaoRealnameRegistrationInfo();
							realnameRegistrationRemark.setRnriRnrId(realnameRegistration.getRnrId());
							realnameRegistrationRemark.setRnriName(item.getRnriName());
							realnameRegistrationRemark.setRnriCardNumber(item.getRnriCardNumber());
							realnameRegistrationRemark.setRnriPhone(item.getRnriPhone());
							realnameRegistrationRemark.setRnriDate(item.getRnriDate());
							realnameRegistrationRemark.setRnriAddress(item.getRnriAddress());
							realnameRegistrationRemark.setRnriEndDate(item.getRnriEndDate());
							realnameRegistrationRemark.setRnriEndAddress(item.getRnriEndAddress());
							realnameRegistrationRemark.setRnriDelete(0);
							realnameRegistrationRemark.setRnriCreateid(realnameRegistration.getRnrCreateid());
							realnameRegistrationRemark.setRnriCreatename(realnameRegistrationRemark.getRnriCreatename());
							realnameRegistrationRemark.setRnriCreatetime(DateUtil.now());
							realnameRegistrationInfoService.getBaseMapper().insert(realnameRegistrationRemark);
						}else{
							AnbiaoRealnameRegistrationInfo realnameRegistrationRemark = new AnbiaoRealnameRegistrationInfo();
							realnameRegistrationRemark.setRnriId(inspectionRemark.getRnriId());
							realnameRegistrationRemark.setRnriRnrId(realnameRegistration.getRnrId());
							realnameRegistrationRemark.setRnriName(item.getRnriName());
							realnameRegistrationRemark.setRnriCardNumber(item.getRnriCardNumber());
							realnameRegistrationRemark.setRnriPhone(item.getRnriPhone());
							realnameRegistrationRemark.setRnriDate(item.getRnriDate());
							realnameRegistrationRemark.setRnriAddress(item.getRnriAddress());
							realnameRegistrationRemark.setRnriEndDate(item.getRnriEndDate());
							realnameRegistrationRemark.setRnriEndAddress(item.getRnriEndAddress());
							realnameRegistrationRemark.setRnriUpdateid(realnameRegistration.getRnrUpdateid());
							realnameRegistrationRemark.setRnriUpdatename(realnameRegistration.getRnrUpdatename());
							realnameRegistrationRemark.setRnriUpdatetime(DateUtil.now());
							realnameRegistrationInfoService.getBaseMapper().updateById(realnameRegistrationRemark);
						}
					});
				}
				r.setMsg("编辑成功");
				r.setCode(200);
				r.setSuccess(true);
			}else{
				r.setMsg("编辑失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}
		return r;
	}

	@PostMapping("/del")
	@ApiLog("乘客信息登记管理-删除")
	@ApiOperation(value = "乘客信息登记管理-删除", notes = "传入id", position = 2)
	public R del(@RequestBody AnbiaoRealnameRegistration realnameRegistration, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRealnameRegistration> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRealnameRegistration::getRnrId, realnameRegistration.getRnrId());
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRealnameRegistration::getRnrDelete, 0);
		AnbiaoRealnameRegistration deal = realnameRegistrationService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
		if (deal != null) {
			deal.setRnrDelete(1);
			deal.setRnrUpdatetime(DateUtil.now());
			if(user != null){
				deal.setRnrUpdatename(user.getUserName());
				deal.setRnrUpdateid(user.getUserId());
			}
			boolean ii = realnameRegistrationService.updateById(deal);
			if (ii) {
				List<AnbiaoRealnameRegistrationInfo> remarkList = realnameRegistration.getRealnameRegistrationInfoList();
				if(remarkList.size() > 0 && remarkList != null) {
					remarkList.forEach(item -> {
						QueryWrapper<AnbiaoRealnameRegistrationInfo> remarkQueryWrapper = new QueryWrapper<AnbiaoRealnameRegistrationInfo>();
						remarkQueryWrapper.lambda().eq(AnbiaoRealnameRegistrationInfo::getRnriRnrId, deal.getRnrId());
						remarkQueryWrapper.lambda().eq(AnbiaoRealnameRegistrationInfo::getRnriDelete, 0);
						AnbiaoRealnameRegistrationInfo inspectionRemark = realnameRegistrationInfoService.getBaseMapper().selectOne(remarkQueryWrapper);
						if (inspectionRemark != null) {
							inspectionRemark.setRnriDelete(1);
							realnameRegistrationInfoService.getBaseMapper().updateById(inspectionRemark);
						}
					});
				}
				r.setMsg("删除成功");
				r.setCode(200);
				r.setSuccess(true);
			} else {
				r.setMsg("删除失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		} else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
		return r;
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("乘客信息登记管理-分页")
	@ApiOperation(value = "乘客信息登记管理-分页", notes = "传入AnbiaoRealnameRegistrationPage", position = 3)
	public R<AnbiaoRealnameRegistrationPage<AnbiaoRealnameRegistration>> list(@RequestBody AnbiaoRealnameRegistrationPage AnbiaoRealnameRegistrationPage) {
		AnbiaoRealnameRegistrationPage<AnbiaoRealnameRegistration> pages = realnameRegistrationService.selectALLPage(AnbiaoRealnameRegistrationPage);
		return R.data(pages);
	}

	@GetMapping("/detail")
	@ApiLog("乘客信息登记管理--详情")
	@ApiOperation(value = "乘客信息登记管理--详情",notes = "传入数据Id", position = 4)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true)})
	public R detail(String Id){
		R r = new R();
		AnbiaoRealnameRegistration deail = realnameRegistrationService.getById(Id);
		if (deail != null){
			QueryWrapper<AnbiaoRealnameRegistrationInfo> remarkQueryWrapper = new QueryWrapper<AnbiaoRealnameRegistrationInfo>();
			remarkQueryWrapper.lambda().eq(AnbiaoRealnameRegistrationInfo::getRnriRnrId, deail.getRnrId());
			remarkQueryWrapper.lambda().eq(AnbiaoRealnameRegistrationInfo::getRnriDelete, 0);
			List<AnbiaoRealnameRegistrationInfo> inspectionRemark = realnameRegistrationInfoService.getBaseMapper().selectList(remarkQueryWrapper);
			if(inspectionRemark.size() > 0 && inspectionRemark != null){
				deail.setRealnameRegistrationInfoList(inspectionRemark);
			}
			r.setData(deail);
			r.setCode(200);
			r.setMsg("获取成功");
			return r;
		}else {
			r.setCode(500);
			r.setMsg("暂无数据");
			return r;
		}
	}


}
