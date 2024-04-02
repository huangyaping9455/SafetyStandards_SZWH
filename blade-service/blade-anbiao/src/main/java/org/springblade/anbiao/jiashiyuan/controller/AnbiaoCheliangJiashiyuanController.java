package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuan;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanVehiclePage;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoCheliangJiashiyuanService;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.jiashiyuan.service.impl.AnbiaoCheliangJiashiyuanServiceImpl;
import org.springblade.anbiao.jiashiyuan.vo.CheliangJiashiyuanVO;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO;
import org.springblade.common.tool.JSONUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

	private IJiaShiYuanService jiaShiYuanService;

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
	@ApiOperation(value = "查询-车辆驾驶员绑定信息", notes = "传入deptId、jiashiyuanid、shiyongxingzhi")
	public R detail(@RequestBody String json,BladeUser user){
		R r = new R();
			//获取参数
		JsonNode node = JSONUtils.string2JsonNode(json);
		if (node.get("deptId") != null && !node.get("deptId").isNull() && !node.get("deptId").asText().isEmpty()){
			Integer deptId = Integer.parseInt(node.get("deptId").asText());
//			String jiashiyuanid = node.get("jiashiyuanid").asText();
			if (node.get("shiyongxingzhi") != null && !node.get("shiyongxingzhi").isNull() && !node.get("shiyongxingzhi").asText().isEmpty()){
				String shiyongxingzhi = node.get("shiyongxingzhi").asText();
				List<CheliangJiashiyuanVO> cheliangJiashiyuanVOS = cheliangJiashiyuanServiceImpl.SelectByJiashiyuanID(shiyongxingzhi,deptId);
				if(cheliangJiashiyuanVOS != null && cheliangJiashiyuanVOS.size() > 0){
//					cheliangJiashiyuanVOS.forEach(item-> {
//						if (item.getJiashiyuanid() != null && item.getJiashiyuanid().equals(jiashiyuanid)) {
//							item.setStatus(-1);
//						}
//					});
					r.setMsg("获取成功");
					r.setCode(200);
					r.setSuccess(true);
					r.setData(cheliangJiashiyuanVOS);
				}else{
					r.setMsg("获取成功，暂无数据");
					r.setCode(200);
					r.setSuccess(true);
					r.setData("");
				}
				return r;
			}else {
				r.setMsg("请传入使用性质");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		if (node.get("jiashiyuanid") != null && !node.get("jiashiyuanid").isNull() && !node.get("jiashiyuanid").asText().isEmpty()){
			String jiashiyuanid = node.get("jiashiyuanid").asText();
			if (node.get("shiyongxingzhi") != null && !node.get("shiyongxingzhi").isNull() && !node.get("shiyongxingzhi").asText().isEmpty()){
				String shiyongxingzhi = node.get("shiyongxingzhi").asText();
				QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId,jiashiyuanid);
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,0);
				JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
				Integer deptId = null;
				if(deail != null){
					deptId = deail.getDeptId();
				}
				List<CheliangJiashiyuanVO> cheliangJiashiyuanVOS = cheliangJiashiyuanServiceImpl.SelectByJiashiyuanID(shiyongxingzhi,deptId);
				if(cheliangJiashiyuanVOS != null && cheliangJiashiyuanVOS.size() > 0){
					cheliangJiashiyuanVOS.forEach(item-> {
						if (item.getJiashiyuanid() != null && item.getJiashiyuanid().equals(jiashiyuanid)) {
							item.setStatus(-1);
						}
					});
					r.setMsg("获取成功");
					r.setCode(200);
					r.setSuccess(true);
					r.setData(cheliangJiashiyuanVOS);
				}else{
					r.setMsg("获取成功，暂无数据");
					r.setCode(200);
					r.setSuccess(true);
					r.setData("");
				}
				return r;
			}else {
				r.setMsg("请传入使用性质");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}else {
			r.setMsg("请传入驾驶员id");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
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

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("分页-驾驶员车辆绑定关系")
	@ApiOperation(value = "分页-驾驶员车辆绑定关系", notes = "传入JiaShiYuanVehiclePage", position = 5)
	public R<JiaShiYuanVehiclePage<CheliangJiashiyuanVO>> list(@RequestBody JiaShiYuanVehiclePage jiaShiYuanVehiclePage) {
		JiaShiYuanVehiclePage<CheliangJiashiyuanVO> pages = cheliangJiashiyuanService.selectPageList(jiaShiYuanVehiclePage);
		return R.data(pages);
	}

	@GetMapping("/selectByDept")
	@ApiLog("根据企业ID获取集团性企业信息")
	@ApiOperation(value = "根据企业ID获取集团性企业信息", notes = "传入deptId", position = 6)
	public R<List<CheliangJiashiyuanVO>> selectByDept(Integer deptId) {
		List<CheliangJiashiyuanVO> dept = cheliangJiashiyuanService.SelectByDept(deptId);
		return R.data(dept);
	}

}
