package org.springblade.anbiao.risk.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;
import org.springblade.anbiao.risk.entity.AnbiaoRiskConfiguration;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfiguration;
import org.springblade.anbiao.risk.page.RiskDeptConfigurationPage;
import org.springblade.anbiao.risk.service.IAnbiaoRiskConfigurationService;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDeptConfigurationService;
import org.springblade.anbiao.risk.vo.RiskDeptConfigurationListVO;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springframework.web.bind.annotation.*;

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
public class AnbiaoRiskDeptConfigurationController {

	private IAnbiaoRiskDeptConfigurationService anbiaoRiskDeptConfigurationService;
	private IAnbiaoRiskConfigurationService riskConfigurationService;
	private ISysClient iSysClient;

	@PostMapping("/insert")
	@ApiLog("插入企业风险配置信息")
	@ApiOperation(value = "插入企业风险配置信息", notes = "传入rcId，deptId", position = 1)
	public R insert(String rcId, String deptId, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRiskConfiguration> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getId,rcId);
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getIsDeleted,"0");
		AnbiaoRiskConfiguration anbiaoRiskConfiguration = riskConfigurationService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
		if (anbiaoRiskConfiguration!=null){
			Dept dept = iSysClient.selectDeptById(Integer.parseInt(deptId));
			if (dept!=null){
			QueryWrapper<AnbiaoRiskDeptConfiguration> riskDeptConfigurationQueryWrapper = new QueryWrapper<>();
			riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getRcId, rcId);
			riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getDeptId, deptId);
			riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getIsDeleted, 0);
			AnbiaoRiskDeptConfiguration deal = anbiaoRiskDeptConfigurationService.getBaseMapper().selectOne(riskDeptConfigurationQueryWrapper);
			if (deal == null) {
				AnbiaoRiskDeptConfiguration anbiaoRiskDeptConfiguration = new AnbiaoRiskDeptConfiguration();
				anbiaoRiskDeptConfiguration.setRcId(rcId);
				anbiaoRiskDeptConfiguration.setDeptId(deptId);
				anbiaoRiskDeptConfiguration.setCreattime(DateUtil.now());
				anbiaoRiskDeptConfiguration.setChuangjianren(user.getUserName());
				anbiaoRiskDeptConfiguration.setStatus("1");
				anbiaoRiskDeptConfiguration.setIsDeleted("0");
				boolean save = anbiaoRiskDeptConfigurationService.save(anbiaoRiskDeptConfiguration);
				if (save == true) {
					r.setMsg("新增权限成功");
					r.setCode(200);
					r.setSuccess(true);
					r.setData(anbiaoRiskDeptConfiguration);
					return r;
				} else {
					r.setMsg("新增权限失败");
					r.setCode(500);
					r.setSuccess(false);
					r.setData(anbiaoRiskDeptConfiguration);
					return r;
				}
			}else {
				r.setMsg("权限已存在");
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
		}else {
			r.setMsg("对应权限不存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	@PostMapping("/update")
	@ApiLog("开关企业风险配置信息")
	@ApiOperation(value = "开关企业风险配置信息", notes = "传入rcId，deptId", position = 1)
	public R update(String rcId, String deptId, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRiskDeptConfiguration> riskDeptConfigurationQueryWrapper = new QueryWrapper<>();
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getRcId, rcId);
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getDeptId, deptId);
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
			r.setMsg("无对应权限关系");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}

	@PostMapping("/del")
	@ApiLog("删除企业风险配置信息")
	@ApiOperation(value = "删除企业风险配置信息", notes = "传入rcId，deptId", position = 1)
	public R del(String rcId, String deptId, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRiskDeptConfiguration> riskDeptConfigurationQueryWrapper = new QueryWrapper<>();
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getRcId, rcId);
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getDeptId, deptId);
		riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getIsDeleted, 0);
		AnbiaoRiskDeptConfiguration deal = anbiaoRiskDeptConfigurationService.getBaseMapper().selectOne(riskDeptConfigurationQueryWrapper);
		if (deal != null) {
			QueryWrapper<AnbiaoRiskConfiguration> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
			anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getId,rcId);
			anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getIsDeleted,"0");
			AnbiaoRiskConfiguration anbiaoRiskConfiguration = riskConfigurationService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
			deal.setUpdatetime(DateUtil.now());
			deal.setCaozuoren(user.getUserName());
			deal.setIsDeleted("1");
			int i = anbiaoRiskDeptConfigurationService.getBaseMapper().updateById(deal);
			if (i > 0) {
				r.setMsg("删除企业风险权限成功");
				r.setCode(200);
				r.setSuccess(true);
				r.setData(anbiaoRiskConfiguration.getYujingxiang());
				return r;
			} else {
				r.setMsg("删除企业风险权限失败");
				r.setCode(500);
				r.setSuccess(false);
				r.setData(anbiaoRiskConfiguration.getYujingxiang());
				return r;
			}
		}else {
			r.setMsg("无对应企业风险配置");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}

	}

	@PostMapping("/select")
	@ApiLog("查看企业风险配置信息")
	@ApiOperation(value = "查看企业风险配置信息", notes = "传入deptId", position = 1)
	public R select( String deptId, BladeUser user) {
		R r = new R();
		Dept dept = iSysClient.selectDeptById(Integer.parseInt(deptId));
		if(dept!=null){
			QueryWrapper<AnbiaoRiskDeptConfiguration> riskDeptConfigurationQueryWrapper = new QueryWrapper<>();
			riskDeptConfigurationQueryWrapper.lambda().eq(AnbiaoRiskDeptConfiguration::getDeptId, deptId);
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
	@GetMapping("/list")
	@ApiLog("分页-企业风险配置")
	@ApiOperation(value = "分页-企业风险配置", notes = "传入RiskDeptConfigurationPage", position = 5)
	public R<RiskDeptConfigurationPage<RiskDeptConfigurationListVO>> list(@RequestBody RiskDeptConfigurationPage riskDeptConfigurationPage) {
		RiskDeptConfigurationPage<RiskDeptConfigurationListVO> pages = anbiaoRiskDeptConfigurationService.selectPageList(riskDeptConfigurationPage);
		return R.data(pages);
	}
}
