package org.springblade.anbiao.shixun.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.shixun.entity.AnbiaoBsPolicyInfo;
import org.springblade.anbiao.shixun.page.BsPolicyInfoPage;
import org.springblade.anbiao.shixun.service.IAnbiaoBsPolicyInfoService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author hyp
 * @since 2022-11-04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/bsPolicyInfo")
@Api(value = "最新时讯", tags = "最新时讯")
public class AnbiaoBsPolicyInfoController {

	private IAnbiaoBsPolicyInfoService BsPolicyInfoService;

	@PostMapping(value = "/getBsPolicyInfoAll")
	@ApiLog("最新时讯列表")
	@ApiOperation(value = "最新时讯列表", notes = "传入BsPolicyInfoPage",position = 1)
	public R<BsPolicyInfoPage<AnbiaoBsPolicyInfo>> getBsPolicyInfoAll(@RequestBody BsPolicyInfoPage BsPolicyInfoPage) {
		//排序条件
		////默认操作时间降序
		if(BsPolicyInfoPage.getOrderColumns()==null){
			BsPolicyInfoPage.setOrderColumn("caozuoshijian");
		}else{
			BsPolicyInfoPage.setOrderColumn(BsPolicyInfoPage.getOrderColumns());
		}
		BsPolicyInfoPage<AnbiaoBsPolicyInfo> pages = BsPolicyInfoService.selectGetAll(BsPolicyInfoPage);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("最新时讯列表-新增")
	@ApiOperation(value = "最新时讯列表-新增", notes = "传入AnbiaoBsPolicyInfo", position = 2)
	public R insert(@RequestBody AnbiaoBsPolicyInfo bsPolicyInfo, BladeUser user) {
		R r = new R();
		if (user == null) {
			r.setCode(500);
			r.setMsg("未授权");
			r.setSuccess(false);
			return r;
		}
		QueryWrapper<AnbiaoBsPolicyInfo> bsPolicyInfoQueryWrapper = new QueryWrapper<AnbiaoBsPolicyInfo>();
		bsPolicyInfoQueryWrapper.lambda().eq(AnbiaoBsPolicyInfo::getDeptId, bsPolicyInfo.getDeptId());
		bsPolicyInfoQueryWrapper.lambda().eq(AnbiaoBsPolicyInfo::getIsdelete, 0);
		bsPolicyInfoQueryWrapper.lambda().eq(AnbiaoBsPolicyInfo::getBiaoti, bsPolicyInfo.getBiaoti());
		bsPolicyInfoQueryWrapper.lambda().eq(AnbiaoBsPolicyInfo::getBiaoqian, bsPolicyInfo.getBiaoqian());
		AnbiaoBsPolicyInfo deail = BsPolicyInfoService.getBaseMapper().selectOne(bsPolicyInfoQueryWrapper);
		if(deail == null) {
			bsPolicyInfo.setCaozuoren(user.getUserName());
			bsPolicyInfo.setCaozuorenid(user.getUserId().toString());
			bsPolicyInfo.setCaozuoshijian(DateUtil.now());
			bsPolicyInfo.setIsdelete(0);
			bsPolicyInfo.setShifouqiyong("启用");
			if (StringUtils.isBlank(bsPolicyInfo.getBiaoqian())){
				bsPolicyInfo.setBiaoqian("时讯");
			}
			boolean i = BsPolicyInfoService.save(bsPolicyInfo);
			if(i){
				r.setMsg("新增成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}else{
				r.setMsg("新增失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}else{
			r.setMsg("该数据已存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入数据Id", position = 3)
	public R<AnbiaoBsPolicyInfo> detail(Integer Id) {
		R r = new R();
		AnbiaoBsPolicyInfo detail = BsPolicyInfoService.getById(Id);
		if(detail != null){
			r.setMsg("获取成功");
			r.setData(detail);
			r.setCode(200);
		}else{
			r.setMsg("获取成功,暂无数据");
			r.setCode(200);
		}
		return r;
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入BsPolicyInfo", position = 4)
	public R update(@RequestBody AnbiaoBsPolicyInfo bsPolicyInfo, BladeUser user) {
		R rs = new R();
		if (user == null) {
			rs.setCode(500);
			rs.setMsg("未授权");
			rs.setSuccess(false);
			return rs;
		}

		bsPolicyInfo.setCaozuoren(user.getUserName());
		bsPolicyInfo.setCaozuorenid(user.getUserId().toString());
		bsPolicyInfo.setCaozuoshijian(DateUtil.now());
		boolean i = BsPolicyInfoService.updateById(bsPolicyInfo);
		if(i == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("编辑成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("编辑失败");
		}
		return rs;
	}

	/**
	 * 删除
	 */
	@GetMapping("/remove")
	@ApiLog("删除")
	@ApiOperation(value = "删除", notes = "传入数据Id", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Id", value = "数据Id", required = true)
	})
	public R remove(@RequestParam Integer Id, BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}

		AnbiaoBsPolicyInfo bsPolicyInfo = new AnbiaoBsPolicyInfo();
		bsPolicyInfo.setCaozuoren(user.getUserName());
		bsPolicyInfo.setCaozuorenid(user.getUserId().toString());
		bsPolicyInfo.setCaozuoshijian(DateUtil.now());
		bsPolicyInfo.setId(Id);
		bsPolicyInfo.setIsdelete(1);
		boolean i = BsPolicyInfoService.updateById(bsPolicyInfo);
		if(i == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("删除成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("删除失败");
		}
		return rs;
	}

	/**
	 * 停用、启用
	 */
	@GetMapping("/updateStatus")
	@ApiLog("停用、启用")
	@ApiOperation(value = "停用、启用", notes = "传入数据Id", position = 6)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Id", value = "数据Id", required = true),
		@ApiImplicitParam(name = "status", value = "状态（启用，禁用）", required = true)
	})
	public R updateStatus(@RequestParam Integer Id, String status,BladeUser user) {
		R rs = new R();
		if(user == null){
			rs.setMsg("未授权");
			rs.setCode(500);
			return rs;
		}

		AnbiaoBsPolicyInfo bsPolicyInfo = new AnbiaoBsPolicyInfo();
		bsPolicyInfo.setCaozuoren(user.getUserName());
		bsPolicyInfo.setCaozuorenid(user.getUserId().toString());
		bsPolicyInfo.setCaozuoshijian(DateUtil.now());
		bsPolicyInfo.setId(Id);
		if("启用".equals(status)){
			bsPolicyInfo.setShifouqiyong("启用");
		}else{
			bsPolicyInfo.setShifouqiyong("禁用");
		}
		boolean i = BsPolicyInfoService.updateById(bsPolicyInfo);
		if(i == true){
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("更新成功");
		}else{
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("更新失败");
		}
		return rs;
	}

}
