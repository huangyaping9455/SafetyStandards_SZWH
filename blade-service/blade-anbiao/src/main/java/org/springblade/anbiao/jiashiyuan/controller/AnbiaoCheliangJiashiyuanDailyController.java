package org.springblade.anbiao.jiashiyuan.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuanDaily;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoJiashiyuanAnquanzerenshu;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoCheliangJiashiyuanDailyService;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.common.tool.StringUtil;
import org.springblade.common.tool.StringUtils;
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
 * @since 2022-11-10
 */

@RestController
@RequestMapping("/anbiao/jiashiyuan/cheliangjiashiyuandaily")
@AllArgsConstructor
@Api(value = "驾驶员资料管理--车辆-驾驶员每日信息", tags = "驾驶员资料管理--车辆-驾驶员每日信息")
public class AnbiaoCheliangJiashiyuanDailyController {

	private IAnbiaoCheliangJiashiyuanDailyService cheliangJiashiyuanDailyService;

	private IVehicleService vehicleService;

	private IJiaShiYuanService jiaShiYuanService;


	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-车辆-驾驶员当日信息")
	@ApiOperation(value = "新增-车辆-驾驶员当日信息", notes = "传入AnbiaoCheliangJiashiyuanDaily")
	public R Insert(@RequestBody AnbiaoCheliangJiashiyuanDaily cheliangJiashiyuanDaily, BladeUser user) {
		R r = new R();
		cheliangJiashiyuanDaily.setCreatetime(DateUtil.now());
		String substring = cheliangJiashiyuanDaily.getCreatetime().substring(0,10);
		QueryWrapper<AnbiaoCheliangJiashiyuanDaily> cheliangJiashiyuanDailyQueryWrapper = new QueryWrapper<>();
		cheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getJiashiyuanid, cheliangJiashiyuanDaily.getJiashiyuanid());
		cheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getVehid, cheliangJiashiyuanDaily.getVehid());
		cheliangJiashiyuanDailyQueryWrapper.lambda().ge(AnbiaoCheliangJiashiyuanDaily::getCreatetime, substring);
		List<AnbiaoCheliangJiashiyuanDaily> cheliangJiashiyuanDailies = cheliangJiashiyuanDailyService.getBaseMapper().selectList(cheliangJiashiyuanDailyQueryWrapper);
		if(cheliangJiashiyuanDailies.size() < 1){
			cheliangJiashiyuanDaily.setCreatetime(DateUtil.now());
			if(cheliangJiashiyuanDaily.getVehid() != null && StringUtils.isNotEmpty(cheliangJiashiyuanDaily.getVehid())){
				AnbiaoCheliangJiashiyuanDaily ss = cheliangJiashiyuanDailyService.SelectByID("车头",cheliangJiashiyuanDaily.getJiashiyuanid());
				if(ss != null){
					ss.setVstatus(0);
					ss.setUpdatetime(DateUtil.now());
					cheliangJiashiyuanDailyService.updateById(ss);

//					QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
//					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, cheliangJiashiyuanDaily.getJiashiyuanid());
//					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
//					JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
//					if(deail != null){
//						Vehicle vehicle = new Vehicle();
//						vehicle.setId(cheliangJiashiyuanDaily.getVehid());
//						vehicle.setChezhu(deail.getJiashiyuanxingming());
//						vehicle.setChezhudianhua(deail.getShoujihaoma());
//						vehicleService.updateById(vehicle);
//					}
				}
				cheliangJiashiyuanDaily.setVstatus(1);
			}
			if(cheliangJiashiyuanDaily.getGvehid() != null && StringUtils.isNotEmpty(cheliangJiashiyuanDaily.getGvehid())){
				AnbiaoCheliangJiashiyuanDaily ss = cheliangJiashiyuanDailyService.SelectByID("挂车",cheliangJiashiyuanDaily.getJiashiyuanid());
				if(ss != null) {
					ss.setGstatus(0);
					ss.setUpdatetime(DateUtil.now());
					cheliangJiashiyuanDailyService.updateById(ss);

//					QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
//					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, cheliangJiashiyuanDaily.getJiashiyuanid());
//					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
//					JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
//					if(deail != null){
//						Vehicle vehicle = new Vehicle();
//						vehicle.setId(cheliangJiashiyuanDaily.getGvehid());
//						vehicle.setChezhu(deail.getJiashiyuanxingming());
//						vehicle.setChezhudianhua(deail.getShoujihaoma());
//						vehicleService.updateById(vehicle);
//					}
				}
				cheliangJiashiyuanDaily.setGstatus(1);
			}
			cheliangJiashiyuanDailyService.save(cheliangJiashiyuanDaily);

			r.setMsg("保存成功");
			r.setCode(200);
			r.setSuccess(true);
		}else{
			for (int i = 0; i <= cheliangJiashiyuanDailies.size() - 1; i++) {
				String createtime = cheliangJiashiyuanDailies.get(i).getCreatetime().substring(0, 10);
				if (createtime.equals(substring)) {
					cheliangJiashiyuanDaily.setCreatetime(DateUtil.now());
					cheliangJiashiyuanDaily.setId(cheliangJiashiyuanDailies.get(i).getId());
					if(cheliangJiashiyuanDailies.get(i).getVehid() != null && StringUtils.isNotEmpty(cheliangJiashiyuanDailies.get(i).getVehid())){
						AnbiaoCheliangJiashiyuanDaily ss = cheliangJiashiyuanDailyService.SelectByID("车头",cheliangJiashiyuanDaily.getJiashiyuanid());
						if(ss != null){
							ss.setVstatus(0);
							ss.setUpdatetime(DateUtil.now());
							cheliangJiashiyuanDailyService.updateById(ss);

//							QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
//							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, cheliangJiashiyuanDaily.getJiashiyuanid());
//							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
//							JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
//							if(deail != null){
//								Vehicle vehicle = new Vehicle();
//								vehicle.setId(cheliangJiashiyuanDaily.getVehid());
//								vehicle.setChezhu(deail.getJiashiyuanxingming());
//								vehicle.setChezhudianhua(deail.getShoujihaoma());
//								vehicleService.updateById(vehicle);
//							}
						}
						cheliangJiashiyuanDaily.setVstatus(1);
					}
					if(cheliangJiashiyuanDailies.get(i).getGvehid() != null && StringUtils.isNotEmpty(cheliangJiashiyuanDailies.get(i).getGvehid())){
						AnbiaoCheliangJiashiyuanDaily ss = cheliangJiashiyuanDailyService.SelectByID("挂车",cheliangJiashiyuanDaily.getJiashiyuanid());
						if(ss != null) {
							ss.setGstatus(0);
							ss.setUpdatetime(DateUtil.now());
							cheliangJiashiyuanDailyService.updateById(ss);

//							QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
//							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, cheliangJiashiyuanDaily.getJiashiyuanid());
//							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
//							JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
//							if(deail != null){
//								Vehicle vehicle = new Vehicle();
//								vehicle.setId(cheliangJiashiyuanDaily.getGvehid());
//								vehicle.setChezhu(deail.getJiashiyuanxingming());
//								vehicle.setChezhudianhua(deail.getShoujihaoma());
//								vehicleService.updateById(vehicle);
//							}
						}
						cheliangJiashiyuanDaily.setGstatus(1);
					}
					cheliangJiashiyuanDailyService.updateById(cheliangJiashiyuanDaily);
					r.setMsg("保存成功");
					r.setCode(200);
					r.setSuccess(true);
				}else{
					r.setMsg("保存成功");
					r.setCode(200);
					r.setSuccess(true);
				}
			}
		}
		return r;
	}

	/**
	 * 删除
	 */
	@GetMapping("/del")
	@ApiLog("删除-车辆-驾驶员每日信息")
	@ApiOperation(value = "删除-车辆-驾驶员每日信息", notes = "传入AnbiaoCheliangJiashiyuanDaily")
	public R del(@RequestBody AnbiaoCheliangJiashiyuanDaily anbiaoCheliangJiashiyuanDaily,BladeUser user) {
		R r = new R();
		String substring = anbiaoCheliangJiashiyuanDaily.getCreatetime().substring(0, 10);
		QueryWrapper<AnbiaoCheliangJiashiyuanDaily> cheliangJiashiyuanDailyQueryWrapper = new QueryWrapper<>();
		cheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getJiashiyuanid, anbiaoCheliangJiashiyuanDaily.getJiashiyuanid());
		cheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getVehid, anbiaoCheliangJiashiyuanDaily.getVehid());
		List<AnbiaoCheliangJiashiyuanDaily> cheliangJiashiyuanDailies = cheliangJiashiyuanDailyService.getBaseMapper().selectList(cheliangJiashiyuanDailyQueryWrapper);
		if (cheliangJiashiyuanDailies != null) {
			for (int i = 0; i <= cheliangJiashiyuanDailies.size() - 1; i++) {
				String createtime = cheliangJiashiyuanDailies.get(i).getCreatetime().substring(0, 10);
				if (createtime.equals(substring)) {
					cheliangJiashiyuanDailyService.getBaseMapper().deleteById(cheliangJiashiyuanDailies.get(i).getId());
					r.setMsg("删除成功");
					r.setSuccess(true);
					r.setCode(200);
					return r;
				}
			}
		}
		r.setMsg("数据不存在");
		r.setCode(500);
		r.setSuccess(false);
		return r;
	}

	/**
	 * 根据人员id查询每日绑定车辆信息
	 */
	@PostMapping("/detail")
	@ApiLog("查询-车辆-驾驶员绑定每日信息（当前）")
	@ApiOperation(value = "查询-车辆-驾驶员绑定每日信息（当前）", notes = "传入jiashiyuanid")
	public R<List<AnbiaoCheliangJiashiyuanDaily>> detail(String jiashiyuanid,BladeUser user){
		R r = new R();
		QueryWrapper<AnbiaoCheliangJiashiyuanDaily> cheliangJiashiyuanDailyQueryWrapper = new QueryWrapper<>();
		cheliangJiashiyuanDailyQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuanDaily::getJiashiyuanid,jiashiyuanid);
		cheliangJiashiyuanDailyQueryWrapper.lambda().orderByDesc(AnbiaoCheliangJiashiyuanDaily::getCreatetime);
		cheliangJiashiyuanDailyQueryWrapper.last(" limit 1");
		List<AnbiaoCheliangJiashiyuanDaily> anbiaoCheliangJiashiyuanDailies = cheliangJiashiyuanDailyService.getBaseMapper().selectList(cheliangJiashiyuanDailyQueryWrapper);
		if (anbiaoCheliangJiashiyuanDailies != null){
			r.setData(anbiaoCheliangJiashiyuanDailies);
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("获取成功");
		}else{
			r.setData(null);
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("获取成功，暂无数据");
		}
		return r;
	}
}
