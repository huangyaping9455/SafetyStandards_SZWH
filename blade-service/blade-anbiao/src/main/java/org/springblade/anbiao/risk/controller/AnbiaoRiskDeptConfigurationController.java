package org.springblade.anbiao.risk.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfiguration;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfigurationPlan;
import org.springblade.anbiao.risk.page.RiskDeptConfigurationPage;
import org.springblade.anbiao.risk.service.IAnbiaoRiskConfigurationService;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDeptConfigurationPlanService;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDeptConfigurationService;
import org.springblade.anbiao.risk.vo.RiskDeptConfigurationListVO;
import org.springblade.anbiao.risk.vo.RiskPlanListConfigurationVO;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lmh
 * @since 2023-03-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/riskDeptConfiguration")
@Api(value = "企业风险配置信息", tags = "企业风险配置信息")
public class AnbiaoRiskDeptConfigurationController {

	private IAnbiaoRiskDeptConfigurationService anbiaoRiskDeptConfigurationService;
	private IAnbiaoRiskConfigurationService riskConfigurationService;
	private ISysClient iSysClient;
	private IAnbiaoRiskDeptConfigurationPlanService riskDeptConfigurationPlanService;


	@PostMapping("/insert")
	@ApiLog("插入企业风险配置信息")
	@ApiOperation(value = "插入企业风险配置信息", notes = "传入rcId，deptId", position = 1)
	public R insert(@RequestBody AnbiaoRiskDeptConfiguration riskDeptConfiguration, BladeUser user) {
		R r = new R();
		//企业
		String[] deptIds = riskDeptConfiguration.getDeptId().split(",");
		//去除数组中重复的数组
		List<String> listdeptid = new ArrayList<>();
		for (int i = 0; i < deptIds.length; i++) {
			if(!listdeptid.contains(deptIds[i])){
				listdeptid.add(deptIds[i]);
			}
		}
		//返回一个包含所有指定对象类型的数组
		String[] deptIdss = listdeptid.toArray(new String[1]);
		//风险项
		String[] rcIds = riskDeptConfiguration.getRcId().split(",");
		//去除数组中重复的数组
		List<String> listdeptid2 = new ArrayList<>();
		for (int i = 0; i < rcIds.length; i++) {
			if(!listdeptid2.contains(rcIds[i])){
				listdeptid2.add(rcIds[i]);
			}
		}
		//返回一个包含所有指定对象类型的数组
		String[] rcIdss = listdeptid2.toArray(new String[1]);
		boolean save = false;
		for (int ii = 0; ii < deptIdss.length; ii++) {
			riskDeptConfiguration.setDeptId(deptIdss[ii]);
			for (int j = 0; j < rcIdss.length; j++) {
				riskDeptConfiguration.setRcId(rcIdss[j]);
				QueryWrapper<AnbiaoRiskDeptConfiguration> riskDeptConfigurationQueryWrapper = new QueryWrapper<>();
				riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getRcId, riskDeptConfiguration.getRcId());
				riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getDeptId, riskDeptConfiguration.getDeptId());
				riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getIsDeleted, 0);
				AnbiaoRiskDeptConfiguration deal = anbiaoRiskDeptConfigurationService.getBaseMapper().selectOne(riskDeptConfigurationQueryWrapper);
				if (deal == null) {
					AnbiaoRiskDeptConfiguration anbiaoRiskDeptConfiguration = new AnbiaoRiskDeptConfiguration();
					anbiaoRiskDeptConfiguration.setRcId(riskDeptConfiguration.getRcId());
					anbiaoRiskDeptConfiguration.setDeptId(riskDeptConfiguration.getDeptId());
					anbiaoRiskDeptConfiguration.setCreattime(DateUtil.now());
					anbiaoRiskDeptConfiguration.setChuangjianren(user.getUserName());
					anbiaoRiskDeptConfiguration.setStatus("1");
					anbiaoRiskDeptConfiguration.setIsDeleted("0");
					save = anbiaoRiskDeptConfigurationService.save(anbiaoRiskDeptConfiguration);
					if(save){
						List<AnbiaoRiskDeptConfigurationPlan> riskDeptConfigurationPlanList = riskDeptConfiguration.getRiskDeptConfigurationPlanList();
						if(riskDeptConfigurationPlanList != null && riskDeptConfigurationPlanList.size() > 0 ) {
							for (int i = 0; i < riskDeptConfigurationPlanList.size(); i++) {
								QueryWrapper<AnbiaoRiskDeptConfigurationPlan> anbiaoRiskDeptConfigurationPlanQueryWrapper = new QueryWrapper<>();
								anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getRdcId, anbiaoRiskDeptConfiguration.getId());
								anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getLevel, riskDeptConfigurationPlanList.get(i).getLevel());
								anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getStatus, 1);
								anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getIsDeleted, 0);
								AnbiaoRiskDeptConfigurationPlan deptConfigurationPlan = riskDeptConfigurationPlanService.getBaseMapper().selectOne(anbiaoRiskDeptConfigurationPlanQueryWrapper);
								if (deptConfigurationPlan == null) {
									AnbiaoRiskDeptConfigurationPlan riskDeptConfigurationPlan1 = new AnbiaoRiskDeptConfigurationPlan();
									riskDeptConfigurationPlan1.setCreatetime(DateUtil.now());
									riskDeptConfigurationPlan1.setChuangjianren(user.getUserName());
									riskDeptConfigurationPlan1.setStatus(1);
									riskDeptConfigurationPlan1.setRdcId(anbiaoRiskDeptConfiguration.getId());
									riskDeptConfigurationPlan1.setLevel(riskDeptConfigurationPlanList.get(i).getLevel());
									riskDeptConfigurationPlan1.setUserId(riskDeptConfigurationPlanList.get(i).getUserId());
									riskDeptConfigurationPlan1.setDriverId(riskDeptConfigurationPlanList.get(i).getDriverId());
									riskDeptConfigurationPlan1.setDate(riskDeptConfigurationPlanList.get(i).getDate());
									riskDeptConfigurationPlan1.setType(riskDeptConfigurationPlanList.get(i).getType());
									riskDeptConfigurationPlan1.setDays(riskDeptConfigurationPlanList.get(i).getDays());
									save = riskDeptConfigurationPlanService.save(riskDeptConfigurationPlan1);
									if (save == true) {
										r.setMsg("新增成功");
										r.setCode(200);
										r.setSuccess(true);
									} else {
										r.setMsg("新增失败");
										r.setCode(500);
										r.setSuccess(false);
										return r;
									}
								}else{
									deptConfigurationPlan.setUpdatetime(DateUtil.now());
									deptConfigurationPlan.setCaozuoren(user.getUserName());
									deptConfigurationPlan.setRdcId(anbiaoRiskDeptConfiguration.getId());
									deptConfigurationPlan.setLevel(riskDeptConfigurationPlanList.get(i).getLevel());
									deptConfigurationPlan.setUserId(riskDeptConfigurationPlanList.get(i).getUserId());
									deptConfigurationPlan.setDriverId(riskDeptConfigurationPlanList.get(i).getDriverId());
									deptConfigurationPlan.setDate(riskDeptConfigurationPlanList.get(i).getDate());
									deptConfigurationPlan.setType(riskDeptConfigurationPlanList.get(i).getType());
									deptConfigurationPlan.setDays(riskDeptConfigurationPlanList.get(i).getDays());
									save = riskDeptConfigurationPlanService.updateById(deptConfigurationPlan);
									if (save == true) {
										r.setMsg("编辑成功");
										r.setCode(200);
										r.setSuccess(true);
										r.setData(deal);
									} else {
										r.setMsg("编辑失败");
										r.setCode(500);
										r.setSuccess(false);
										r.setData(deal);
										return r;
									}
								}
							}
						}
					}else {
						r.setMsg("新增失败");
						r.setCode(500);
						r.setSuccess(false);
						r.setData(deal);
						return r;
					}
				}else{
					save = anbiaoRiskDeptConfigurationService.updateById(deal);
					if(save){
						List<AnbiaoRiskDeptConfigurationPlan> riskDeptConfigurationPlanList = riskDeptConfiguration.getRiskDeptConfigurationPlanList();
						if(riskDeptConfigurationPlanList != null && riskDeptConfigurationPlanList.size() > 0 ) {
							for (int i = 0; i < riskDeptConfigurationPlanList.size(); i++) {
								QueryWrapper<AnbiaoRiskDeptConfigurationPlan> anbiaoRiskDeptConfigurationPlanQueryWrapper = new QueryWrapper<>();
								anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getRdcId, deal.getId());
								anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getLevel, riskDeptConfigurationPlanList.get(i).getLevel());
								anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getStatus, 1);
								anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getIsDeleted, 0);
								AnbiaoRiskDeptConfigurationPlan deptConfigurationPlan = riskDeptConfigurationPlanService.getBaseMapper().selectOne(anbiaoRiskDeptConfigurationPlanQueryWrapper);
								if (deptConfigurationPlan == null) {
									AnbiaoRiskDeptConfigurationPlan riskDeptConfigurationPlan1 = new AnbiaoRiskDeptConfigurationPlan();
									riskDeptConfigurationPlan1.setCreatetime(DateUtil.now());
									riskDeptConfigurationPlan1.setChuangjianren(user.getUserName());
									riskDeptConfigurationPlan1.setStatus(1);
									riskDeptConfigurationPlan1.setRdcId(deal.getId());
									riskDeptConfigurationPlan1.setLevel(riskDeptConfigurationPlanList.get(i).getLevel());
									riskDeptConfigurationPlan1.setUserId(riskDeptConfigurationPlanList.get(i).getUserId());
									riskDeptConfigurationPlan1.setDriverId(riskDeptConfigurationPlanList.get(i).getDriverId());
									riskDeptConfigurationPlan1.setDate(riskDeptConfigurationPlanList.get(i).getDate());
									riskDeptConfigurationPlan1.setType(riskDeptConfigurationPlanList.get(i).getType());
									deptConfigurationPlan.setDays(riskDeptConfigurationPlanList.get(i).getDays());
									save = riskDeptConfigurationPlanService.save(riskDeptConfigurationPlan1);
									if (save == true) {
										r.setMsg("新增成功");
										r.setCode(200);
										r.setSuccess(true);
									} else {
										r.setMsg("新增失败");
										r.setCode(500);
										r.setSuccess(false);
										return r;
									}
								}else{
									deptConfigurationPlan.setUpdatetime(DateUtil.now());
									deptConfigurationPlan.setCaozuoren(user.getUserName());
									deptConfigurationPlan.setRdcId(deal.getId());
									deptConfigurationPlan.setLevel(riskDeptConfigurationPlanList.get(i).getLevel());
									deptConfigurationPlan.setUserId(riskDeptConfigurationPlanList.get(i).getUserId());
									deptConfigurationPlan.setDriverId(riskDeptConfigurationPlanList.get(i).getDriverId());
									deptConfigurationPlan.setDate(riskDeptConfigurationPlanList.get(i).getDate());
									deptConfigurationPlan.setType(riskDeptConfigurationPlanList.get(i).getType());
									deptConfigurationPlan.setDays(riskDeptConfigurationPlanList.get(i).getDays());
									save = riskDeptConfigurationPlanService.updateById(deptConfigurationPlan);
									if (save == true) {
										r.setMsg("编辑成功");
										r.setCode(200);
										r.setSuccess(true);
										r.setData(deal);
									} else {
										r.setMsg("编辑失败");
										r.setCode(500);
										r.setSuccess(false);
										r.setData(deal);
										return r;
									}
								}
							}
						}
					}else {
						r.setMsg("编辑失败");
						r.setCode(500);
						r.setSuccess(false);
						r.setData(deal);
						return r;
					}
				}
			}
		}
		if (save == true) {
			r.setMsg("操作成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		} else {
			r.setMsg("操作失败");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	@PostMapping("/update")
	@ApiLog("开关企业风险配置信息")
	@ApiOperation(value = "开关企业风险配置信息", notes = "传入rcId，deptId", position = 1)
	public R update(@RequestBody AnbiaoRiskDeptConfiguration riskDeptConfiguration, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRiskDeptConfiguration> riskDeptConfigurationQueryWrapper = new QueryWrapper<>();
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getRcId, riskDeptConfiguration.getRcId());
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getDeptId, riskDeptConfiguration.getDeptId());
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getIsDeleted, "0");
		AnbiaoRiskDeptConfiguration deal = anbiaoRiskDeptConfigurationService.getBaseMapper().selectOne(riskDeptConfigurationQueryWrapper);
		if (deal != null) {
			if (deal.getStatus().equals("0")){
				deal.setStatus("1");
				deal.setUpdatetime(DateUtil.now());
				deal.setCaozuoren(user.getUserName());
				int i = anbiaoRiskDeptConfigurationService.getBaseMapper().updateById(deal);
				if (i>0){
					r.setMsg("开启权限成功");
					r.setCode(200);
					r.setSuccess(true);
					return r;
				}else {
					r.setMsg("开启权限失败");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}else {
				deal.setStatus("0");
				deal.setUpdatetime(DateUtil.now());
				deal.setCaozuoren(user.getUserName());
				int i = anbiaoRiskDeptConfigurationService.getBaseMapper().updateById(deal);
				if (i>0){
					r.setMsg("关闭权限成功");
					r.setCode(200);
					r.setSuccess(true);
					return r;
				}else {
					r.setMsg("关闭权限失败");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
		}else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}

	@PostMapping("/del")
	@ApiLog("删除企业风险配置信息")
	@ApiOperation(value = "删除企业风险配置信息", notes = "传入rcId，deptId", position = 1)
	public R del(@RequestBody AnbiaoRiskDeptConfiguration riskDeptConfiguration, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRiskDeptConfiguration> riskDeptConfigurationQueryWrapper = new QueryWrapper<>();
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getRcId, riskDeptConfiguration.getRcId());
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getDeptId, riskDeptConfiguration.getDeptId());
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getIsDeleted, "0");
		AnbiaoRiskDeptConfiguration deal = anbiaoRiskDeptConfigurationService.getBaseMapper().selectOne(riskDeptConfigurationQueryWrapper);
		if (deal != null) {
			deal.setUpdatetime(DateUtil.now());
			deal.setCaozuoren(user.getUserName());
			deal.setIsDeleted("1");
			int i = anbiaoRiskDeptConfigurationService.getBaseMapper().updateById(deal);
			if (i > 0) {
				r.setMsg("删除企业风险权限成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			} else {
				r.setMsg("删除企业风险权限失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}

	}

	@PostMapping("/select")
	@ApiLog("查看企业风险配置信息")
	@ApiOperation(value = "查看企业风险配置信息", notes = "传入deptId", position = 1)
	public R select( @RequestBody AnbiaoRiskDeptConfiguration riskDeptConfiguration, BladeUser user) {
		R r = new R();
		Dept dept = iSysClient.selectDeptById(Integer.parseInt(riskDeptConfiguration.getDeptId()));
		if(dept!=null){
			QueryWrapper<AnbiaoRiskDeptConfiguration> riskDeptConfigurationQueryWrapper = new QueryWrapper<>();
			riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getDeptId, riskDeptConfiguration.getDeptId());
			riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getIsDeleted, 0);
			List<AnbiaoRiskDeptConfiguration> anbiaoRiskDeptConfigurations = anbiaoRiskDeptConfigurationService.getBaseMapper().selectList(riskDeptConfigurationQueryWrapper);

			if (anbiaoRiskDeptConfigurations!=null){
				r.setMsg("查询成功");
				r.setCode(200);
				r.setSuccess(true);
				r.setData(anbiaoRiskDeptConfigurations);
				return r;
			}else {
				r.setMsg("企业无风险权限");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}
		}else {
			r.setMsg("企业不存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("分页-企业风险配置")
	@ApiOperation(value = "分页-企业风险配置", notes = "传入RiskDeptConfigurationPage", position = 5)
	public R<RiskDeptConfigurationPage<RiskDeptConfigurationListVO>> list(@RequestBody RiskDeptConfigurationPage riskDeptConfigurationPage) {
		RiskDeptConfigurationPage<RiskDeptConfigurationListVO> pages = anbiaoRiskDeptConfigurationService.selectPageList(riskDeptConfigurationPage);
		return R.data(pages);
	}

	@GetMapping("/detail")
	@ApiLog("风险配置信息详情")
	@ApiOperation(value = "风险配置信息详情", notes = "传入数据ID", position = 6)
	public R detail(String Id) {
		R r = new R();
		QueryWrapper<AnbiaoRiskDeptConfiguration> riskDeptConfigurationQueryWrapper = new QueryWrapper<AnbiaoRiskDeptConfiguration>();
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getId, Id);
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getIsDeleted, 0);
		AnbiaoRiskDeptConfiguration anbiaoRiskDeptConfigurations = anbiaoRiskDeptConfigurationService.getBaseMapper().selectOne(riskDeptConfigurationQueryWrapper);
		if (anbiaoRiskDeptConfigurations != null){
//			QueryWrapper<AnbiaoRiskDeptConfigurationPlan> anbiaoRiskDeptConfigurationPlanQueryWrapper = new QueryWrapper<AnbiaoRiskDeptConfigurationPlan>();
//			anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getRdcId, anbiaoRiskDeptConfigurations.getId());
//			anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getIsDeleted, 0);
//			List<AnbiaoRiskDeptConfigurationPlan> deptConfigurationPlan = riskDeptConfigurationPlanService.getBaseMapper().selectList(anbiaoRiskDeptConfigurationPlanQueryWrapper);
			List<AnbiaoRiskDeptConfigurationPlan> deptConfigurationPlan = riskDeptConfigurationPlanService.selectByList(anbiaoRiskDeptConfigurations.getId());
			if (deptConfigurationPlan != null && deptConfigurationPlan.size() > 0) {
				anbiaoRiskDeptConfigurations.setRiskDeptConfigurationPlanList(deptConfigurationPlan);
			}
			r.setMsg("查询成功");
			r.setCode(200);
			r.setSuccess(true);
			r.setData(anbiaoRiskDeptConfigurations);
			return r;
		}else {
			r.setMsg("暂无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}

	@PostMapping("/insert_planList")
	@ApiLog("插入企业风险配置信息")
	@ApiOperation(value = "插入企业风险配置信息", notes = "传入rcId，deptId", position = 1)
	public R insertPlanList(@RequestBody RiskPlanListConfigurationVO riskPlanListConfigurationVO, BladeUser user) {
		R r = new R();
		boolean save = false;
		List<RiskPlanListConfigurationVO> planList = riskPlanListConfigurationVO.getPlanList();
		if(planList != null && planList.size() > 0){
			for (int j = 0; j < planList.size(); j++) {
				QueryWrapper<AnbiaoRiskDeptConfiguration> riskDeptConfigurationQueryWrapper = new QueryWrapper<>();
				riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getRcId, planList.get(j).getRcId());
				riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getDeptId, planList.get(j).getDeptId());
				riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getIsDeleted, 0);
				AnbiaoRiskDeptConfiguration deal = anbiaoRiskDeptConfigurationService.getBaseMapper().selectOne(riskDeptConfigurationQueryWrapper);
				if (deal == null) {
					AnbiaoRiskDeptConfiguration anbiaoRiskDeptConfiguration = new AnbiaoRiskDeptConfiguration();
					anbiaoRiskDeptConfiguration.setRcId(planList.get(j).getRcId());
					anbiaoRiskDeptConfiguration.setDeptId(planList.get(j).getDeptId());
					anbiaoRiskDeptConfiguration.setCreattime(DateUtil.now());
					anbiaoRiskDeptConfiguration.setChuangjianren(user.getUserName());
					anbiaoRiskDeptConfiguration.setStatus("1");
					anbiaoRiskDeptConfiguration.setIsDeleted("0");
					save = anbiaoRiskDeptConfigurationService.save(anbiaoRiskDeptConfiguration);
					if(save){
						QueryWrapper<AnbiaoRiskDeptConfigurationPlan> anbiaoRiskDeptConfigurationPlanQueryWrapper = new QueryWrapper<>();
						anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getRdcId, anbiaoRiskDeptConfiguration.getId());
						anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getType, planList.get(j).getType());
						anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getLevel, planList.get(j).getLevel());
						anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getStatus, 1);
						anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getIsDeleted, 0);
						AnbiaoRiskDeptConfigurationPlan deptConfigurationPlan = riskDeptConfigurationPlanService.getBaseMapper().selectOne(anbiaoRiskDeptConfigurationPlanQueryWrapper);
						if (deptConfigurationPlan == null) {
							AnbiaoRiskDeptConfigurationPlan riskDeptConfigurationPlan1 = new AnbiaoRiskDeptConfigurationPlan();
							riskDeptConfigurationPlan1.setCreatetime(DateUtil.now());
							riskDeptConfigurationPlan1.setChuangjianren(user.getUserName());
							riskDeptConfigurationPlan1.setStatus(1);
							riskDeptConfigurationPlan1.setRdcId(anbiaoRiskDeptConfiguration.getId());
							riskDeptConfigurationPlan1.setLevel(planList.get(j).getLevel());
							riskDeptConfigurationPlan1.setUserId(anbiaoRiskDeptConfiguration.getChuangjianren());
							riskDeptConfigurationPlan1.setDate(planList.get(j).getDate());
							riskDeptConfigurationPlan1.setType(planList.get(j).getType());
							riskDeptConfigurationPlan1.setHours(planList.get(j).getHours());
							save = riskDeptConfigurationPlanService.save(riskDeptConfigurationPlan1);
							if (save == true) {
								r.setMsg("操作成功");
								r.setCode(200);
								r.setSuccess(true);
							} else {
								r.setMsg("操作失败");
								r.setCode(500);
								r.setSuccess(false);
								return r;
							}
						}else{
							deptConfigurationPlan.setUpdatetime(DateUtil.now());
							deptConfigurationPlan.setCaozuoren(user.getUserName());
							deptConfigurationPlan.setRdcId(anbiaoRiskDeptConfiguration.getId());
							deptConfigurationPlan.setLevel(planList.get(j).getLevel());
							deptConfigurationPlan.setUserId(anbiaoRiskDeptConfiguration.getChuangjianren());
							deptConfigurationPlan.setDate(planList.get(j).getDate());
							deptConfigurationPlan.setType(planList.get(j).getType());
							deptConfigurationPlan.setHours(planList.get(j).getHours());
							save = riskDeptConfigurationPlanService.updateById(deptConfigurationPlan);
							if (save == true) {
								r.setMsg("操作成功");
								r.setCode(200);
								r.setSuccess(true);
								r.setData(deal);
							} else {
								r.setMsg("操作失败");
								r.setCode(500);
								r.setSuccess(false);
								r.setData(deal);
								return r;
							}
						}
					}else {
						r.setMsg("新增失败");
						r.setCode(500);
						r.setSuccess(false);
						r.setData(deal);
						return r;
					}
				}else{
					deal.setRcId(planList.get(j).getRcId());
					deal.setDeptId(planList.get(j).getDeptId());
					deal.setCreattime(DateUtil.now());
					deal.setChuangjianren(user.getUserName());
					deal.setStatus("1");
					deal.setIsDeleted("0");
					save = anbiaoRiskDeptConfigurationService.updateById(deal);
					if(save){
						QueryWrapper<AnbiaoRiskDeptConfigurationPlan> anbiaoRiskDeptConfigurationPlanQueryWrapper = new QueryWrapper<>();
						anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getRdcId, deal.getId());
						anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getType, planList.get(j).getType());
						anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getLevel, planList.get(j).getLevel());
						anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getStatus, 1);
						anbiaoRiskDeptConfigurationPlanQueryWrapper.lambda().eq(AnbiaoRiskDeptConfigurationPlan::getIsDeleted, 0);
						AnbiaoRiskDeptConfigurationPlan deptConfigurationPlan = riskDeptConfigurationPlanService.getBaseMapper().selectOne(anbiaoRiskDeptConfigurationPlanQueryWrapper);
						if (deptConfigurationPlan == null) {
							AnbiaoRiskDeptConfigurationPlan riskDeptConfigurationPlan1 = new AnbiaoRiskDeptConfigurationPlan();
							riskDeptConfigurationPlan1.setCreatetime(DateUtil.now());
							riskDeptConfigurationPlan1.setChuangjianren(user.getUserName());
							riskDeptConfigurationPlan1.setStatus(1);
							riskDeptConfigurationPlan1.setRdcId(deal.getId());
							riskDeptConfigurationPlan1.setLevel(planList.get(j).getLevel());
							riskDeptConfigurationPlan1.setUserId(deal.getChuangjianren());
							riskDeptConfigurationPlan1.setDate(planList.get(j).getDate());
							riskDeptConfigurationPlan1.setType(planList.get(j).getType());
							riskDeptConfigurationPlan1.setHours(planList.get(j).getHours());
							save = riskDeptConfigurationPlanService.save(riskDeptConfigurationPlan1);
							if (save == true) {
								r.setMsg("新增成功");
								r.setCode(200);
								r.setSuccess(true);
							} else {
								r.setMsg("新增失败");
								r.setCode(500);
								r.setSuccess(false);
								return r;
							}
						}else{
							deptConfigurationPlan.setUpdatetime(DateUtil.now());
							deptConfigurationPlan.setCaozuoren(user.getUserName());
							deptConfigurationPlan.setRdcId(deal.getId());
							deptConfigurationPlan.setLevel(planList.get(j).getLevel());
							deptConfigurationPlan.setUserId(deal.getChuangjianren());
							deptConfigurationPlan.setDate(planList.get(j).getDate());
							deptConfigurationPlan.setType(planList.get(j).getType());
							deptConfigurationPlan.setHours(planList.get(j).getHours());
							save = riskDeptConfigurationPlanService.updateById(deptConfigurationPlan);
							if (save == true) {
								r.setMsg("操作成功");
								r.setCode(200);
								r.setSuccess(true);
								r.setData(deal);
							} else {
								r.setMsg("操作失败");
								r.setCode(500);
								r.setSuccess(false);
								r.setData(deal);
								return r;
							}
						}
					}else {
						r.setMsg("操作失败");
						r.setCode(500);
						r.setSuccess(false);
						r.setData(deal);
						return r;
					}
				}
			}
		}else{
			r.setMsg("操作成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}

		if (save == true) {
			r.setMsg("操作成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		} else {
			r.setMsg("操作失败");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	@GetMapping("/defaultDetail")
	@ApiLog("默认风险配置信息详情")
	@ApiOperation(value = "默认风险配置信息详情", notes = "传入数据ID", position = 6)
	public R defaultDetail(String deptId) {
		R r = new R();
		List<RiskDeptConfigurationListVO> deptConfigurationListVOList = anbiaoRiskDeptConfigurationService.selectDeptDefault("1");
		if(deptConfigurationListVOList != null && deptConfigurationListVOList.size() > 0){
			r.setMsg("获取成功");
			r.setCode(200);
			r.setSuccess(true);
			r.setData(deptConfigurationListVOList);
			return r;
		}else {
			r.setMsg("获取成功，暂无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}


}
