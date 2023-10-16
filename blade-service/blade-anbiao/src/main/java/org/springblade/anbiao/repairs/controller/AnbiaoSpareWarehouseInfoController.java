package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStore;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareWarehouseInfo;
import org.springblade.anbiao.repairs.page.AnbiaoSpareWarehouseInfoPage;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePartsStoreService;
import org.springblade.anbiao.repairs.service.IAnbiaoSpareWarehouseInfoService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 备件仓库管理 前端控制器
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/spareWarehouseInfo")
@Api(value = "报修-备件仓库管理", tags = "报修-备件仓库管理")
public class AnbiaoSpareWarehouseInfoController {

	private IAnbiaoSpareWarehouseInfoService warehouseInfoService;

	private IAnbiaoSparePartsStoreService sparePartsStoreService;

	@PostMapping("/list")
	@ApiLog("备件仓库管理-分页")
	@ApiOperation(value = "备件仓库管理-分页", notes = "传入AnbiaoSpareWarehouseInfoPage", position = 1)
	public R<AnbiaoSpareWarehouseInfoPage<AnbiaoSpareWarehouseInfo>> list(@RequestBody AnbiaoSpareWarehouseInfoPage anbiaoSpareWarehouseInfoPage) {
		AnbiaoSpareWarehouseInfoPage<AnbiaoSpareWarehouseInfo> pages = warehouseInfoService.selectAllPage(anbiaoSpareWarehouseInfoPage);
		return R.data(pages);
	}

	@PostMapping("/insert")
	@ApiLog("备件仓库管理-新增、编辑")
	@ApiOperation(value = "备件仓库管理-新增、编辑", notes = "传入AnbiaoSpareWarehouseInfo", position = 2)
	public R insert(@RequestBody AnbiaoSpareWarehouseInfo spareWarehouseInfo, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isNotEmpty(spareWarehouseInfo.getWaId())){
			if (user != null) {
				spareWarehouseInfo.setWaUpdatename(user.getUserName());
				spareWarehouseInfo.setWaUpdateid(user.getUserId());
			}
			spareWarehouseInfo.setWaUpdatetime(DateUtil.now());
			ii = warehouseInfoService.updateById(spareWarehouseInfo);
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
			QueryWrapper<AnbiaoSpareWarehouseInfo> dangerQueryWrapper = new QueryWrapper<AnbiaoSpareWarehouseInfo>();
			dangerQueryWrapper.lambda().eq(AnbiaoSpareWarehouseInfo::getWaDeptId, spareWarehouseInfo.getWaDeptId());
			dangerQueryWrapper.lambda().eq(AnbiaoSpareWarehouseInfo::getWaName, spareWarehouseInfo.getWaName());
			dangerQueryWrapper.lambda().eq(AnbiaoSpareWarehouseInfo::getWaDelete, 0);
			AnbiaoSpareWarehouseInfo deail = warehouseInfoService.getBaseMapper().selectOne(dangerQueryWrapper);
			if (deail == null) {
				spareWarehouseInfo.setWaDelete(0);
				//获取当前企业当天最大序列号 维修单号：20230620-4587-0001
				String xuhao = "";
				AnbiaoSpareWarehouseInfo AnbiaoSpareWarehouseInfo = warehouseInfoService.selectMaxXuhao(spareWarehouseInfo.getWaDeptId().toString());
				if (AnbiaoSpareWarehouseInfo != null && StringUtils.isNotEmpty(AnbiaoSpareWarehouseInfo.getWaNo())) {
					xuhao = AnbiaoSpareWarehouseInfo.getWaNo();
					Integer xh = Integer.parseInt(xuhao.substring(xuhao.length() - 4));
					xh = xh += 1;
					xuhao = xh.toString();
					if (xuhao.length() < 2) {
						xuhao = "000" + xuhao;
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date()) + "-" + spareWarehouseInfo.getWaDeptId() + "-" + xuhao;
						spareWarehouseInfo.setWaNo(xuhao);
					} else if (xuhao.length() >= 2 && xuhao.length() < 3) {
						xuhao = "00" + xuhao;
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date()) + "-" + spareWarehouseInfo.getWaDeptId() + "-" + xuhao;
						spareWarehouseInfo.setWaNo(xuhao);
					} else if (xuhao.length() >= 3 && xuhao.length() < 4) {
						xuhao = "0" + xuhao;
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date()) + "-" + spareWarehouseInfo.getWaDeptId() + "-" + xuhao;
						spareWarehouseInfo.setWaNo(xuhao);
					} else {
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date()) + "-" + spareWarehouseInfo.getWaDeptId() + "-" + xuhao;
						spareWarehouseInfo.setWaNo(xuhao);
					}
				} else {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date()) + "-" + spareWarehouseInfo.getWaDeptId() + "-0001";
					spareWarehouseInfo.setWaNo(xuhao);
				}
				if (user != null) {
					spareWarehouseInfo.setWaCreatename(user.getUserName());
					spareWarehouseInfo.setWaCreateid(user.getUserId());
				}
				spareWarehouseInfo.setWaCreatetime(DateUtil.now());
				ii = warehouseInfoService.save(spareWarehouseInfo);
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
				r.setMsg("该仓库名称已存在！");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}
		}
		return r;
	}

	@PostMapping("/del")
	@ApiLog("备件仓库管理-删除")
	@ApiOperation(value = "备件仓库管理-删除", notes = "传入AnbiaoSpareWarehouseInfo", position = 3)
	public R del(@RequestBody AnbiaoSpareWarehouseInfo spareWarehouseInfo, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSpareWarehouseInfo> spareWarehouseInfoQueryWrapper = new QueryWrapper<>();
		spareWarehouseInfoQueryWrapper.lambda().eq(AnbiaoSpareWarehouseInfo::getWaId, spareWarehouseInfo.getWaId());
		spareWarehouseInfoQueryWrapper.lambda().eq(AnbiaoSpareWarehouseInfo::getWaDelete, 0);
		AnbiaoSpareWarehouseInfo deal = warehouseInfoService.getBaseMapper().selectOne(spareWarehouseInfoQueryWrapper);
		if (deal != null) {
			QueryWrapper<AnbiaoSparePartsStore> sparePartsStoreQueryWrapper = new QueryWrapper<AnbiaoSparePartsStore>();
			sparePartsStoreQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpWarehouseId, deal.getWaId());
			sparePartsStoreQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDelete, 0);
			List<AnbiaoSparePartsStore> deail = sparePartsStoreService.getBaseMapper().selectList(sparePartsStoreQueryWrapper);
			if (deail.size() < 1) {
				deal.setWaDelete(1);
				deal.setWaUpdatetime(DateUtil.now());
				deal.setWaUpdatename(user.getUserName());
				deal.setWaUpdateid(user.getUserId());
				warehouseInfoService.updateById(deal);
				r.setMsg("删除成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}else{
				r.setMsg("该仓库中还存在备件，不能进行删除！");
				r.setCode(500);
				r.setSuccess(true);
				return r;
			}
		} else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}

	@GetMapping("/getDriverRepairsCount")
	@ApiLog("备件仓库管理-根据企业ID获取仓库列表")
	@ApiOperation(value = "备件仓库管理-根据企业ID获取仓库列表", notes = "企业ID", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "企业ID", required = true)
	})
	public R<List<AnbiaoSpareWarehouseInfo>> getDriverRepairsCount(String deptId) {
		List<AnbiaoSpareWarehouseInfo> spareWarehouseInfos = warehouseInfoService.selectByDeptIdList(deptId);
		return R.data(spareWarehouseInfos);
	}



}
