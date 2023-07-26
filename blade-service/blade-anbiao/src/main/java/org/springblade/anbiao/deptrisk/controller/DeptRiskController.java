package org.springblade.anbiao.deptrisk.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.deptrisk.entity.DeptRisk;
import org.springblade.anbiao.deptrisk.service.IDeptRiskService;
import org.springblade.anbiao.dianziwendang.entity.AnbiaoElectronicFile;
import org.springblade.anbiao.dianziwendang.service.IAnbiaoElectronicFileService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 企业风险看板
 * </p>
 *
 * @author hyp
 * @since 2023-07-26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/deptRisk")
@Api(value = "企业风险看板",tags = "企业风险看板")
public class DeptRiskController extends BladeController {

	private IDeptRiskService service;

	@GetMapping("/getDeptNum")
	@ApiLog(value = "企业风险看板-获取企业数、车辆数、驾驶员数")
	@ApiOperation(value = "企业风险看板-获取企业数、车辆数、驾驶员数",notes = "传入deptId",position = 1)
	public R getDeptNum(Integer deptId){
		R r = new R();
		DeptRisk deail = service.selectDeptNum(deptId);
		if(deail != null){
			r.setSuccess(true);
			r.setCode(200);
			r.setData(deail);
			r.setMsg("获取成功");
		}else{
			r.setSuccess(false);
			r.setCode(200);
			r.setMsg("暂无数据");
		}
		return r;
	}

	@GetMapping("/getDeptRiskNum")
	@ApiLog(value = "企业风险看板-获取总风险数")
	@ApiOperation(value = "企业风险看板-获取总风险数",notes = "传入deptId",position = 2)
	public R getDeptRiskNum(Integer deptId){
		R r = new R();
		DeptRisk deail = service.selectDeptRiskNum(deptId);
		if(deail != null){
			r.setSuccess(true);
			r.setCode(200);
			r.setData(deail);
			r.setMsg("获取成功");
		}else{
			r.setSuccess(false);
			r.setCode(200);
			r.setMsg("暂无数据");
		}
		return r;
	}

	@GetMapping("/getDeptListRiskNum")
	@ApiLog(value = "企业风险看板-获取企业风险数排名")
	@ApiOperation(value = "企业风险看板-获取企业风险数排名",notes = "传入deptId",position = 3)
	public R getDeptListRiskNum(Integer deptId){
		R r = new R();
		List<DeptRisk> deail = service.selectDeptListRiskNum(deptId);
		if(deail != null){
			r.setSuccess(true);
			r.setCode(200);
			r.setData(deail);
			r.setMsg("获取成功");
		}else{
			r.setSuccess(false);
			r.setCode(200);
			r.setMsg("暂无数据");
		}
		return r;
	}

	@GetMapping("/getRiskTendency")
	@ApiLog(value = "企业风险看板-获取风险趋势分析")
	@ApiOperation(value = "企业风险看板-获取风险趋势分析",notes = "传入beginTime,endTime,deptId",position = 4)
	public R getRiskTendency(String beginTime, String endTime, Integer deptId){
		R r = new R();
		List<DeptRisk> deail = service.selectRiskTendency(beginTime, endTime, deptId);
		if(deail != null){
			r.setSuccess(true);
			r.setCode(200);
			r.setData(deail);
			r.setMsg("获取成功");
		}else{
			r.setSuccess(false);
			r.setCode(200);
			r.setMsg("暂无数据");
		}
		return r;
	}

	@GetMapping("/getDeptAvg")
	@ApiLog(value = "企业风险看板-获取企业数据完善情况")
	@ApiOperation(value = "企业风险看板-获取企业数据完善情况",notes = "传入deptId",position = 5)
	public R getDeptAvg(Integer deptId){
		R r = new R();
		List<DeptRisk> deail = service.selectDeptAvg(deptId);
		if(deail != null){
			r.setSuccess(true);
			r.setCode(200);
			r.setData(deail);
			r.setMsg("获取成功");
		}else{
			r.setSuccess(false);
			r.setCode(200);
			r.setMsg("暂无数据");
		}
		return r;
	}



}
