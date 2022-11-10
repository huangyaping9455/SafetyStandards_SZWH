package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuan;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoCheliangJiashiyuanService;
import org.springblade.anbiao.jiashiyuan.service.impl.AnbiaoCheliangJiashiyuanServiceImpl;
import org.springblade.anbiao.jiashiyuan.vo.CheliangJiashiyuanVO;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lmh
 * @since 2022-11-08
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan/cheliangjiashiyuan")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--车辆-驾驶员绑定信息", tags = "驾驶员资料管理--车辆-驾驶员绑定信息")
public class AnbiaoCheliangJiashiyuanController {

	private IAnbiaoCheliangJiashiyuanService cheliangJiashiyuanService;

	private AnbiaoCheliangJiashiyuanServiceImpl cheliangJiashiyuanServiceImpl;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-车辆-驾驶员绑定信息")
	@ApiOperation(value = "新增-车辆驾驶员绑定信息", notes = "传入AnbiaoCheliangJiashiyuan")
	public R Insert(@RequestBody AnbiaoCheliangJiashiyuan cheliangJiashiyuan, BladeUser user){
		R r = new R();
		QueryWrapper<AnbiaoCheliangJiashiyuan> cheliangJiashiyuanQueryWrapper = new QueryWrapper<>();
		cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuan::getJiashiyuanid,cheliangJiashiyuan.getJiashiyuanid());
		cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuan::getVehid,cheliangJiashiyuan.getVehid());
		AnbiaoCheliangJiashiyuan deail = cheliangJiashiyuanService.getBaseMapper().selectOne(cheliangJiashiyuanQueryWrapper);
		System.out.println(deail);
		if (deail == null){
			if (cheliangJiashiyuan!=null){
				cheliangJiashiyuan.setCreatetime(DateUtil.now());
				cheliangJiashiyuanService.save(cheliangJiashiyuan);
			}else {
				r.setMsg("绑定失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
	}else {
		r.setMsg("已有相同绑定");
		r.setCode(500);
		r.setSuccess(false);
		return r;
	}
		r.setMsg("绑定成功");
		r.setCode(200);
		r.setSuccess(true);
		return r;
	}

	/**
	 * 根据人员id查询绑定车辆信息
	 */
	@PostMapping("/detail")
	@ApiLog("查询-车辆-驾驶员绑定信息")
	@ApiOperation(value = "查询-车辆驾驶员绑定信息", notes = "传入jiashiyuanid")
	public String detail(String jiashiyuanid,BladeUser user){

		List<CheliangJiashiyuanVO> cheliangJiashiyuanVOS = cheliangJiashiyuanServiceImpl.SelectByJiashiyuanID(jiashiyuanid);
		return JSON.toJSONString(cheliangJiashiyuanVOS);
	}

	/**
	 * 删除
	 */
	@GetMapping("/del")
	@ApiLog("删除-车辆-驾驶员绑定信息")
	@ApiOperation(value = "删除-车辆-驾驶员绑定信息", notes = "传入jiashiyuanid，Vehid")
	public R del(String jiashiyuanid,String vehid,BladeUser user){
		R r = new R();
		QueryWrapper<AnbiaoCheliangJiashiyuan> cheliangJiashiyuanQueryWrapper = new QueryWrapper<>();
		cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuan::getJiashiyuanid,jiashiyuanid);
		cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuan::getVehid,vehid);
		AnbiaoCheliangJiashiyuan deail = cheliangJiashiyuanService.getBaseMapper().selectOne(cheliangJiashiyuanQueryWrapper);
		if (deail!=null){
			cheliangJiashiyuanService.getBaseMapper().delete(cheliangJiashiyuanQueryWrapper);
			r.setMsg("删除成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}else {
			r.setMsg("绑定关系不存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

}
