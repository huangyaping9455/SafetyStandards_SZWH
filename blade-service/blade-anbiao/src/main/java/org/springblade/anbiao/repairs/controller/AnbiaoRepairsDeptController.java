package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsDept;
import org.springblade.anbiao.repairs.page.AnbiaoRepairsDeptPage;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsDeptService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
@RequestMapping("/anbiao/repairsDept")
@Api(value = "报修-企业、维修单位绑定关系信息", tags = "报修-企业、维修单位绑定关系信息")
public class AnbiaoRepairsDeptController {

	private IAnbiaoRepairsDeptService deptService;

	@PostMapping("/insert")
	@ApiLog("企业、维修单位绑定关系信息-新增、编辑")
	@ApiOperation(value = "企业、维修单位绑定关系信息-新增、编辑", notes = "传入AnbiaoRepairsDept", position = 1)
	public R insert(@RequestBody AnbiaoRepairsDept repairsDept, BladeUser user) {
		R r = new R();
		boolean ii = false;
		//根据deptIds字符串分别截取
		String[] deptIds_idsss = repairsDept.getRpDeptIds().split(",");
		//去除素组中重复的数组
		List<String> deptIds_listid = new ArrayList<String>();
		for (int i=0; i<deptIds_idsss.length; i++) {
			if(!deptIds_listid.contains(deptIds_idsss[i])) {
				deptIds_listid.add(deptIds_idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] deptIds_idss= deptIds_listid.toArray(new String[1]);
		if(deptIds_idss.length > 0) {
			for (int i = 0; i < deptIds_idss.length; i++) {
				repairsDept.setRpRepDeptId(Integer.parseInt(deptIds_idss[i]));
				QueryWrapper<AnbiaoRepairsDept> dangerQueryWrapper = new QueryWrapper<AnbiaoRepairsDept>();
				dangerQueryWrapper.lambda().eq(AnbiaoRepairsDept::getRpDeptId, repairsDept.getRpDeptId());
				dangerQueryWrapper.lambda().eq(AnbiaoRepairsDept::getRpRepDeptId, repairsDept.getRpRepDeptId());
				dangerQueryWrapper.lambda().eq(AnbiaoRepairsDept::getRpDelete, 0);
				AnbiaoRepairsDept deail = deptService.getBaseMapper().selectOne(dangerQueryWrapper);
				if(deail == null){
					repairsDept.setRpDelete(0);
					if(user != null){
						repairsDept.setRpCreatename(user.getUserName());
						repairsDept.setRpCreateid(user.getUserId());
					}
					repairsDept.setRpCreatetime(DateUtil.now());
					ii = deptService.save(repairsDept);
					if(ii){
						r.setMsg("新增成功");
						r.setCode(200);
						r.setSuccess(true);
					}else{
						r.setMsg("新增失败");
						r.setCode(500);
						r.setSuccess(false);
					}
				}else{
					repairsDept.setRpId(deail.getRpId());
					repairsDept.setRpDelete(0);
					if(user != null){
						repairsDept.setRpUpdatename(user.getUserName());
						repairsDept.setRpUpdateid(user.getUserId());
					}
					repairsDept.setRpUpdatetime(DateUtil.now());
					ii = deptService.updateById(repairsDept);
					if(ii){
						r.setMsg("新增成功");
						r.setCode(200);
						r.setSuccess(true);
					}else{
						r.setMsg("新增失败");
						r.setCode(500);
						r.setSuccess(false);
					}
				}
			}
			return r;
		}else{
			QueryWrapper<AnbiaoRepairsDept> dangerQueryWrapper = new QueryWrapper<AnbiaoRepairsDept>();
			dangerQueryWrapper.lambda().eq(AnbiaoRepairsDept::getRpDeptId, repairsDept.getRpDeptId());
			dangerQueryWrapper.lambda().eq(AnbiaoRepairsDept::getRpRepDeptId, repairsDept.getRpRepDeptId());
			dangerQueryWrapper.lambda().eq(AnbiaoRepairsDept::getRpDelete, 0);
			AnbiaoRepairsDept deail = deptService.getBaseMapper().selectOne(dangerQueryWrapper);
			if(deail == null){
				repairsDept.setRpDelete(0);
				if(user != null){
					repairsDept.setRpCreatename(user.getUserName());
					repairsDept.setRpCreateid(user.getUserId());
				}
				repairsDept.setRpCreatetime(DateUtil.now());
				ii = deptService.save(repairsDept);
				if(ii){
					r.setMsg("新增成功");
					r.setCode(200);
					r.setSuccess(true);
				}else{
					r.setMsg("新增失败");
					r.setCode(500);
					r.setSuccess(false);
				}
			}else{
				repairsDept.setRpId(deail.getRpId());
				repairsDept.setRpDelete(0);
				if(user != null){
					repairsDept.setRpUpdatename(user.getUserName());
					repairsDept.setRpUpdateid(user.getUserId());
				}
				repairsDept.setRpUpdatetime(DateUtil.now());
				ii = deptService.updateById(repairsDept);
				if(ii){
					r.setMsg("新增成功");
					r.setCode(200);
					r.setSuccess(true);
				}else{
					r.setMsg("新增失败");
					r.setCode(500);
					r.setSuccess(false);
				}
			}
		}
		return r;
	}

	@PostMapping("/del")
	@ApiLog("企业、维修单位绑定关系信息-删除")
	@ApiOperation(value = "企业、维修单位绑定关系信息-删除", notes = "传入id", position = 2)
	public R del(@RequestBody AnbiaoRepairsDept repairsDept, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoRepairsDept> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRepairsDept::getRpId, repairsDept.getRpId());
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoRepairsDept::getRpDelete, 0);
		AnbiaoRepairsDept deal = deptService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
		if (deal != null) {
			deal.setRpDelete(1);
			deal.setRpUpdatetime(DateUtil.now());
			deal.setRpUpdatename(user.getUserName());
			deal.setRpUpdateid(user.getUserId());
			deptService.updateById(deal);
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
	@ApiLog("企业、维修单位绑定关系信息-分页")
	@ApiOperation(value = "企业、维修单位绑定关系信息-分页", notes = "传入AnbiaoRepairsDeptPage", position = 3)
	public R<AnbiaoRepairsDeptPage<AnbiaoRepairsDept>> list(@RequestBody AnbiaoRepairsDeptPage anbiaoRepairsDeptPage) {
		AnbiaoRepairsDeptPage<AnbiaoRepairsDept> pages = deptService.selectPage(anbiaoRepairsDeptPage);
		return R.data(pages);
	}



}
