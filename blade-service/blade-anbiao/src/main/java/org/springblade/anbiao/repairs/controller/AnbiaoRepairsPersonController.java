package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPerson;
import org.springblade.anbiao.repairs.page.AnbiaoRepairsDeptPage;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsPersonService;
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
 * @author author
 * @since 2023-07-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/repairsPerson")
@Api(value = "报修-维修人员管理", tags = "报修-维修人员管理")
public class AnbiaoRepairsPersonController {

	private IAnbiaoRepairsPersonService personService;

	@PostMapping("/insert")
	@ApiLog("维修人员管理-新增、编辑")
	@ApiOperation(value = "维修人员管理-新增、编辑", notes = "传入AnbiaoRepairsDept", position = 1)
	public R insert(@RequestBody AnbiaoRepairsPerson repairsPerson, BladeUser user) {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isNotEmpty(repairsPerson.getRpId())){
			if (user != null) {
				repairsPerson.setRpUpdatename(user.getUserName());
				repairsPerson.setRpUpdateid(user.getUserId());
			}
			repairsPerson.setRpUpdatetime(DateUtil.now());
			ii = personService.updateById(repairsPerson);
			if (ii) {
				r.setMsg("编辑成功");
				r.setCode(200);
				r.setSuccess(true);
			} else {
				r.setMsg("编辑失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}else{
			QueryWrapper<AnbiaoRepairsPerson> dangerQueryWrapper = new QueryWrapper<AnbiaoRepairsPerson>();
			dangerQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpDeptid, repairsPerson.getRpDeptid());
			dangerQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpAccount, repairsPerson.getRpAccount());
			dangerQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpDelete, 0);
			AnbiaoRepairsPerson deail = personService.getBaseMapper().selectOne(dangerQueryWrapper);
			if(deail == null) {
				dangerQueryWrapper = new QueryWrapper<AnbiaoRepairsPerson>();
				dangerQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpDeptid, repairsPerson.getRpDeptid());
				dangerQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpName, repairsPerson.getRpName());
				dangerQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpPhone, repairsPerson.getRpPhone());
				dangerQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpDelete, 0);
				deail = personService.getBaseMapper().selectOne(dangerQueryWrapper);
				if(deail == null) {
					repairsPerson.setRpDelete(0);
					if (user != null) {
						repairsPerson.setRpCreatename(user.getUserName());
						repairsPerson.setRpCreateid(user.getUserId());
					}
					repairsPerson.setRpCreatetime(DateUtil.now());
					ii = personService.save(repairsPerson);
					if (ii) {
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
					r.setMsg("该联系人、联系电话已存在");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}else{
				r.setMsg("该登录账号已存在");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}
		return r;
	}

	@PostMapping("/del")
	@ApiLog("维修人员管理-删除")
	@ApiOperation(value = "维修人员管理-删除", notes = "传入id", position = 2)
	public R del(@RequestBody AnbiaoRepairsPerson repairsPerson, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRepairsPerson> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpId, repairsPerson.getRpId());
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRepairsPerson::getRpDelete, 0);
		AnbiaoRepairsPerson deal = personService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
		if (deal != null) {
			deal.setRpDelete(1);
			deal.setRpUpdatetime(DateUtil.now());
			deal.setRpUpdatename(user.getUserName());
			deal.setRpUpdateid(user.getUserId());
			personService.updateById(deal);
			r.setMsg("删除成功");
			r.setCode(200);
			r.setSuccess(true);
			r.setData(deal);
			return r;
		} else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("维修人员管理-分页")
	@ApiOperation(value = "维修人员管理-分页", notes = "传入AnbiaoRepairsDeptPage", position = 3)
	public R<AnbiaoRepairsDeptPage<AnbiaoRepairsPerson>> list(@RequestBody AnbiaoRepairsDeptPage anbiaoRepairsDeptPage) {
		AnbiaoRepairsDeptPage<AnbiaoRepairsPerson> pages = personService.selectPage(anbiaoRepairsDeptPage);
		return R.data(pages);
	}

	@GetMapping("/getPersonByDeptId")
	@ApiLog("维修人员管理-根据企业ID获取维修人员")
	@ApiOperation(value = "维修人员管理-根据企业ID获取维修人员", notes = "传入企业ID", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "单位id", required = true)
	})
	public R<List<AnbiaoRepairsPerson>> getPersonByDeptId(String deptId) {
		List<AnbiaoRepairsPerson> list= personService.selectPersonByDeptId(deptId);
		return R.data(list);
	}

}
