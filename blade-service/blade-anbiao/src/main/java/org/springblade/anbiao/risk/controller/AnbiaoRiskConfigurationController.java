package org.springblade.anbiao.risk.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.risk.entity.AnbiaoRiskConfiguration;
import org.springblade.anbiao.risk.service.IAnbiaoRiskConfigurationService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/anbiao/riskConfiguration")
public class AnbiaoRiskConfigurationController {

	private IAnbiaoRiskConfigurationService anbiaoRiskConfigurationService;

	@PostMapping("/insert")
	@ApiLog("插入风险配置信息")
	@ApiOperation(value = "插入风险配置信息", notes = "传入id,yujingxiang,shuoming", position = 1)
	public R insert( String id,String yujingxiang, String shuoming, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRiskConfiguration> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getId, id);
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getIsDeleted, 0);
		AnbiaoRiskConfiguration deal = anbiaoRiskConfigurationService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
		if (deal == null) {
			AnbiaoRiskConfiguration anbiaoRiskConfiguration = new AnbiaoRiskConfiguration();
			anbiaoRiskConfiguration.setYujingxiang(yujingxiang);
			anbiaoRiskConfiguration.setShuoming(shuoming);
			anbiaoRiskConfiguration.setCreattime(DateUtil.now());
			anbiaoRiskConfiguration.setChuangjianren(user.getUserName());
			anbiaoRiskConfiguration.setIsDeleted("0");
			boolean save = anbiaoRiskConfigurationService.save(anbiaoRiskConfiguration);
			if (save == true) {
				r.setMsg("新增成功");
				r.setCode(200);
				r.setSuccess(true);
				r.setData(anbiaoRiskConfiguration);
				return r;
			} else {
				r.setMsg("新增失败");
				r.setCode(500);
				r.setSuccess(false);
				r.setData(anbiaoRiskConfiguration);
				return r;
			}
		} else {
			deal.setYujingxiang(yujingxiang);
			deal.setShuoming(shuoming);
			deal.setUpdatetime(DateUtil.now());
			deal.setCaozuoren(user.getUserName());
			int i = anbiaoRiskConfigurationService.getBaseMapper().updateById(deal);
			if (i>0){
				r.setMsg("更新成功");
				r.setCode(200);
				r.setSuccess(true);
				r.setData(deal);
				return r;
			}else {
				r.setMsg("更新失败");
				r.setCode(500);
				r.setSuccess(false);
				r.setData(deal);
				return r;
			}
		}

	}


	@PostMapping("/select")
	@ApiLog("查看风险配置信息")
	@ApiOperation(value = "查看风险配置信息", notes = "传入id", position = 1)
	public R select(  BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRiskConfiguration> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getIsDeleted, 0);
		List<AnbiaoRiskConfiguration> anbiaoRiskConfigurations = anbiaoRiskConfigurationService.getBaseMapper().selectList(anbiaoRiskConfigurationQueryWrapper);
		if (anbiaoRiskConfigurations != null) {
			r.setMsg("查询成功");
			r.setCode(200);
			r.setSuccess(true);
			r.setData(anbiaoRiskConfigurations);
			return r;

		} else {
			r.setMsg("无对应配置");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}

	}

	@PostMapping("/del")
	@ApiLog("删除风险配置信息")
	@ApiOperation(value = "删除风险配置信息", notes = "传入id", position = 1)
	public R del( String id, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRiskConfiguration> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getId, id);
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getIsDeleted, 0);
		AnbiaoRiskConfiguration deal = anbiaoRiskConfigurationService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
		if (deal != null) {
			deal.setIsDeleted("1");
			deal.setUpdatetime(DateUtil.now());
			deal.setCaozuoren(user.getUserName());
			anbiaoRiskConfigurationService.getBaseMapper().updateById(deal);
			r.setMsg("删除成功");
			r.setCode(200);
			r.setSuccess(true);
			r.setData(deal);
			return r;
		} else {
			r.setMsg("无对应配置");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}

	}

}


