package org.springblade.anbiao.risk.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.risk.entity.AnbiaoRiskConfiguration;
import org.springblade.anbiao.risk.page.RiskConfigurationPage;
import org.springblade.anbiao.risk.page.RiskDeptConfigurationPage;
import org.springblade.anbiao.risk.service.IAnbiaoRiskConfigurationService;
import org.springblade.anbiao.risk.vo.RiskConfigurationVO;
import org.springblade.anbiao.risk.vo.RiskDeptConfigurationListVO;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
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
@RequestMapping("/anbiao/riskConfiguration")
@Api(value = "风险配置信息", tags = "风险配置信息")
public class AnbiaoRiskConfigurationController {

	private IAnbiaoRiskConfigurationService anbiaoRiskConfigurationService;

	@PostMapping("/insert")
	@ApiLog("插入风险配置信息")
	@ApiOperation(value = "插入风险配置信息", notes = "传入id,yujingxiang,shuoming", position = 1)
	public R insert( String yujingxiang, String shuoming, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRiskConfiguration> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getYujingxiang, yujingxiang);
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getShuoming, shuoming);
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
			r.setMsg("风险配置已存在");
			r.setCode(200);
			r.setSuccess(true);
			r.setData(yujingxiang);
			return r;
		}

	}

	@PostMapping("/update")
	@ApiLog("修改风险配置信息")
	@ApiOperation(value = "修改风险配置信息", notes = "传入id,yujingxiang,shuoming", position = 1)
	public R update( String id,String yujingxiang, String shuoming, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRiskConfiguration> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getId, id);
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRiskConfiguration::getIsDeleted, 0);
		AnbiaoRiskConfiguration deal = anbiaoRiskConfigurationService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
		if (deal != null) {
			deal.setYujingxiang(yujingxiang);
			deal.setShuoming(shuoming);
			deal.setUpdatetime(DateUtil.now());
			deal.setCaozuoren(user.getUserName());
			boolean save = anbiaoRiskConfigurationService.updateById(deal);
			if (save == true) {
				r.setMsg("修改成功");
				r.setCode(200);
				r.setSuccess(true);
				r.setData(deal);
				return r;
			} else {
				r.setMsg("修改失败");
				r.setCode(500);
				r.setSuccess(false);
				r.setData(deal);
				return r;
			}
		} else {
			r.setMsg("风险配置不存在");
			r.setCode(200);
			r.setSuccess(true);
			return r;
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


	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiLog("分页-风险配置")
	@ApiOperation(value = "分页-风险配置", notes = "传入RiskDeptConfigurationPage", position = 5)
	public R<RiskConfigurationPage<RiskConfigurationVO>> list(@RequestBody RiskConfigurationPage riskConfigurationPage) {
		RiskConfigurationPage<RiskConfigurationVO> pages = anbiaoRiskConfigurationService.selectPageList(riskConfigurationPage);
		return R.data(pages);
	}
}

