package org.springblade.anbiao.yinhuanpaicha.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDeptInfo;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDeptInfoRemark;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangDeptInfoRemarkService;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoYinhuanpaichaXiangDeptInfoService;
import org.springblade.anbiao.yinhuanpaicha.vo.RemarkVehicleVO;
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
 * @author hyp
 * @since 2022-09-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/yinhuanpaichaXiangDeptInfoRemark")
@Api(value = "企业计划-派发车辆、驾驶员", tags = "企业计划-派发车辆、驾驶员")
public class AnbiaoYinhuanpaichaXiangDeptInfoRemarkController {

	private IAnbiaoYinhuanpaichaXiangDeptInfoRemarkService iAnbiaoYinhuanpaichaXiangDeptInfoRemarkService;

	private IAnbiaoYinhuanpaichaXiangDeptInfoService iAnbiaoYinhuanpaichaXiangDeptInfoService;

	@PostMapping("/save")
	@ApiLog("企业计划-派发车辆、驾驶员-新增")
	@ApiOperation(value = "企业计划-派发车辆、驾驶员-新增", notes = "传入anbiaoYinhuanpaichaXiangDeptInfoRemark", position = 1)
	public R save(@RequestBody AnbiaoYinhuanpaichaXiangDeptInfoRemark anbiaoYinhuanpaichaXiangDeptInfoRemark, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setCreateid(user.getUserId());
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setIsdelete(0);
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setCreatetime(DateUtil.now());
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setStatus(0);

		List<RemarkVehicleVO> remarkVehicleVOList = anbiaoYinhuanpaichaXiangDeptInfoRemark.getRemarkVehicleVOList();
		remarkVehicleVOList.forEach(item-> {
			QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfoRemark> unitWrapper = new QueryWrapper<AnbiaoYinhuanpaichaXiangDeptInfoRemark>();
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfoRemark::getVehid, item.getVehid());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfoRemark::getJsyid, item.getJsyid());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfoRemark::getDataid, anbiaoYinhuanpaichaXiangDeptInfoRemark.getDataid());
			unitWrapper.lambda().eq(AnbiaoYinhuanpaichaXiangDeptInfoRemark::getIsdelete, 0);
			AnbiaoYinhuanpaichaXiangDeptInfoRemark deail = iAnbiaoYinhuanpaichaXiangDeptInfoRemarkService.getBaseMapper().selectOne(unitWrapper);
			if(deail == null){
				anbiaoYinhuanpaichaXiangDeptInfoRemark.setVehid(item.getVehid());
				anbiaoYinhuanpaichaXiangDeptInfoRemark.setJsyid(item.getJsyid());
				boolean ii = iAnbiaoYinhuanpaichaXiangDeptInfoRemarkService.save(anbiaoYinhuanpaichaXiangDeptInfoRemark);
				if(ii == true){
					AnbiaoYinhuanpaichaXiangDeptInfo anbiaoYinhuanpaichaXiangDeptInfo = new AnbiaoYinhuanpaichaXiangDeptInfo();
					anbiaoYinhuanpaichaXiangDeptInfo.setId(anbiaoYinhuanpaichaXiangDeptInfoRemark.getDataid());
					anbiaoYinhuanpaichaXiangDeptInfo.setStatus(1);
					ii = iAnbiaoYinhuanpaichaXiangDeptInfoService.updateById(anbiaoYinhuanpaichaXiangDeptInfo);
					if(ii == true){
						rs.setCode(200);
						rs.setSuccess(true);
						rs.setMsg("新增成功");
					}else{
						rs.setCode(500);
						rs.setSuccess(false);
						rs.setMsg("新增失败");
					}
				}else{
					rs.setCode(500);
					rs.setSuccess(false);
					rs.setMsg("新增失败");
				}
			}else{
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("新增成功");
			}
		});
		return rs;
	}

	@PostMapping("/update")
	@ApiLog("企业计划-派发车辆、驾驶员--编辑")
	@ApiOperation(value = "企业计划-派发车辆、驾驶员--编辑", notes = "传入anbiaoYinhuanpaichaXiangDeptInfoRemark", position = 2)
	public R update(@RequestBody AnbiaoYinhuanpaichaXiangDeptInfoRemark anbiaoYinhuanpaichaXiangDeptInfoRemark, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setUpdateid(user.getUserId());
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setIsdelete(0);
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setUpdatetime(DateUtil.now());
		return R.data(iAnbiaoYinhuanpaichaXiangDeptInfoRemarkService.updateById(anbiaoYinhuanpaichaXiangDeptInfoRemark));
	}

	@GetMapping("/remove")
	@ApiLog("企业计划-派发车辆、驾驶员-删除")
	@ApiOperation(value = "企业计划-派发车辆、驾驶员-删除", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true) })
	public R remove(Integer Id, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}
		AnbiaoYinhuanpaichaXiangDeptInfoRemark anbiaoYinhuanpaichaXiangDeptInfoRemark = new AnbiaoYinhuanpaichaXiangDeptInfoRemark();
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setUpdateid(user.getUserId());
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setUpdatetime(DateUtil.now());
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setIsdelete(1);
		anbiaoYinhuanpaichaXiangDeptInfoRemark.setId(Id);
		return R.data(iAnbiaoYinhuanpaichaXiangDeptInfoRemarkService.updateById(anbiaoYinhuanpaichaXiangDeptInfoRemark));
	}



}
