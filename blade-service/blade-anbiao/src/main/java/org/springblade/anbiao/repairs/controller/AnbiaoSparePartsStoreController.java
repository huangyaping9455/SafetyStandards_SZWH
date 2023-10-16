package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStore;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStore;
import org.springblade.anbiao.repairs.page.AnbiaoSparePartsStorePage;
import org.springblade.anbiao.repairs.page.AnbiaoSparePartsStorePage;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePartsStoreService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 备件库管理 前端控制器
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/sparePartsStore")
@Api(value = "报修-备件库管理", tags = "报修-备件库管理")
public class AnbiaoSparePartsStoreController {

	private IAnbiaoSparePartsStoreService sparePartsStoreService;

	private IFileUploadClient fileUploadClient;

	@PostMapping("/list")
	@ApiLog("备件库管理-分页")
	@ApiOperation(value = "备件库管理-分页", notes = "传入AnbiaoSparePartsStorePage", position = 1)
	public R<AnbiaoSparePartsStorePage<AnbiaoSparePartsStore>> list(@RequestBody AnbiaoSparePartsStorePage anbiaoSparePartsStorePage) {
		AnbiaoSparePartsStorePage<AnbiaoSparePartsStore> pages = sparePartsStoreService.selectAllPage(anbiaoSparePartsStorePage);
		return R.data(pages);
	}

	@PostMapping("/insert")
	@ApiLog("备件库管理-新增")
	@ApiOperation(value = "备件库管理-新增", notes = "传入AnbiaoSparePartsStore", position = 2)
	public R insert(@RequestBody AnbiaoSparePartsStore anbiaoSparePartsStore, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		QueryWrapper<AnbiaoSparePartsStore> dangerQueryWrapper = new QueryWrapper<AnbiaoSparePartsStore>();
		dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDeptId, anbiaoSparePartsStore.getSpDeptId());
		dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpName, anbiaoSparePartsStore.getSpName());
		AnbiaoSparePartsStore deail = sparePartsStoreService.getBaseMapper().selectOne(dangerQueryWrapper);
		if (deail == null) {
			anbiaoSparePartsStore.setSpDelete(0);
			//获取当前企业当天最大序列号 维修单号：20230620-4587-0001
			String xuhao = "";
			AnbiaoSparePartsStore AnbiaoSparePartsStore = sparePartsStoreService.selectMaxXuhao(anbiaoSparePartsStore.getSpDeptId().toString());
			if (AnbiaoSparePartsStore != null && StringUtils.isNotEmpty(AnbiaoSparePartsStore.getSpNo())) {
				xuhao = AnbiaoSparePartsStore.getSpNo().toString();
				Integer xh = Integer.parseInt(xuhao.substring(xuhao.length() - 4));
				xh = xh += 1;
				xuhao = xh.toString();
				if (xuhao.length() < 2) {
					xuhao = "000" + xuhao;
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date()) + "-" + anbiaoSparePartsStore.getSpDeptId() + "-" + xuhao;
					anbiaoSparePartsStore.setSpNo(xuhao);
				} else if (xuhao.length() >= 2 && xuhao.length() < 3) {
					xuhao = "00" + xuhao;
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date()) + "-" + anbiaoSparePartsStore.getSpDeptId() + "-" + xuhao;
					anbiaoSparePartsStore.setSpNo(xuhao);
				} else if (xuhao.length() >= 3 && xuhao.length() < 4) {
					xuhao = "0" + xuhao;
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date()) + "-" + anbiaoSparePartsStore.getSpDeptId() + "-" + xuhao;
					anbiaoSparePartsStore.setSpNo(xuhao);
				} else {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date()) + "-" + anbiaoSparePartsStore.getSpDeptId() + "-" + xuhao;
					anbiaoSparePartsStore.setSpNo(xuhao);
				}
			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				xuhao = format.format(new Date()) + "-" + anbiaoSparePartsStore.getSpDeptId() + "-0001";
				anbiaoSparePartsStore.setSpNo(xuhao);
			}
			if (user != null) {
				anbiaoSparePartsStore.setSpCreatename(user.getUserName());
				anbiaoSparePartsStore.setSpCreateid(user.getUserId());
			}
			anbiaoSparePartsStore.setSpCreatetime(DateUtil.now());
			ii = sparePartsStoreService.save(anbiaoSparePartsStore);
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
		}else {
			r.setMsg("该备件名称已存在！");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
		return r;
	}

	@PostMapping("/update")
	@ApiLog("备件库管理-编辑")
	@ApiOperation(value = "备件库管理-编辑", notes = "传入AnbiaoSparePartsStore", position = 3)
	public R update(@RequestBody AnbiaoSparePartsStore anbiaoSparePartsStore, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isNotEmpty(anbiaoSparePartsStore.getSpId())){
			if (user != null) {
				anbiaoSparePartsStore.setSpUpdatename(user.getUserName());
				anbiaoSparePartsStore.setSpUpdateid(user.getUserId());
			}
			anbiaoSparePartsStore.setSpUpdatetime(DateUtil.now());
			ii = sparePartsStoreService.updateById(anbiaoSparePartsStore);
			if(ii){
				r.setMsg("编辑成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}else {
				r.setMsg("编辑失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}else {
			r.setMsg("暂无数据！");
			r.setCode(200);
			r.setSuccess(true);
		}
		return r;
	}

	@PostMapping("/del")
	@ApiLog("备件库管理-删除")
	@ApiOperation(value = "备件库管理-删除", notes = "传入AnbiaoSparePartsStore", position = 4)
	public R del(@RequestBody AnbiaoSparePartsStore anbiaoSparePartsStore, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSparePartsStore> anbiaoSparePartsStoreQueryWrapper = new QueryWrapper<>();
		anbiaoSparePartsStoreQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpId, anbiaoSparePartsStore.getSpId());
		anbiaoSparePartsStoreQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDelete, 0);
		AnbiaoSparePartsStore deal = sparePartsStoreService.getBaseMapper().selectOne(anbiaoSparePartsStoreQueryWrapper);
		if (deal != null) {
			deal.setSpDelete(1);
			deal.setSpUpdatetime(DateUtil.now());
			deal.setSpUpdatename(user.getUserName());
			deal.setSpUpdateid(user.getUserId());
			sparePartsStoreService.updateById(deal);
			r.setMsg("删除成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		} else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}

	@GetMapping("/getDriverRepairsCount")
	@ApiLog("备件库管理-根据企业ID、仓库ID获取备件列表")
	@ApiOperation(value = "备件库管理-根据企业ID、仓库ID获取备件列表", notes = "企业ID、仓库ID", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "企业ID"),
		@ApiImplicitParam(name = "spWarehouseId", value = "仓库ID")
	})
	public R<List<AnbiaoSparePartsStore>> getDriverRepairsCount(String deptId,String spWarehouseId) {
		List<AnbiaoSparePartsStore> anbiaoSparePartsStores = sparePartsStoreService.selectByDeptIdList(deptId,spWarehouseId);
		return R.data(anbiaoSparePartsStores);
	}

	@GetMapping("/detail")
	@ApiLog("备件库管理-备件详情")
	@ApiOperation(value = "备件库管理-备件详情", notes = "传入数据id", position = 6)
	public R detail(String spId) {
		R r = new R();
		AnbiaoSparePartsStore deail = sparePartsStoreService.getById(spId);
		if (deail != null) {
			if (StrUtil.isNotEmpty(deail.getSpImg()) && deail.getSpImg().contains("http") == false) {
				deail.setSpImg(fileUploadClient.getUrl(deail.getSpImg()));
			}
			r.setData(deail);
			r.setCode(200);
			r.setMsg("获取成功");
			return r;
		} else {
			r.setCode(200);
			r.setMsg("暂无数据");
			return r;
		}
	}

}
