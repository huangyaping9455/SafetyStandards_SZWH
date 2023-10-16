package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareOutInWarehouse;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStore;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareWarehouseInfo;
import org.springblade.anbiao.repairs.page.AnbiaoSpareOutInWarehousePage;
import org.springblade.anbiao.repairs.service.IAnbiaoSpareOutInWarehouseService;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePartsStoreService;
import org.springblade.anbiao.repairs.service.IAnbiaoSpareWarehouseInfoService;
import org.springblade.common.tool.User;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dict;
import org.springblade.system.feign.IDictClient;
import org.springblade.system.feign.ISysClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 出入备件库审核 前端控制器
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/spareOutInWarehouse")
@Api(value = "报修-出、入库备件管理", tags = "报修-出、入库备件管理")
public class AnbiaoSpareOutInWarehouseController {

	private IAnbiaoSpareOutInWarehouseService spareOutInWarehouseService;

	private IAnbiaoSparePartsStoreService sparePartsStoreService;

	private ISysClient iSysClient;

	private IDictClient iDictClient;

	private IAnbiaoSpareWarehouseInfoService warehouseInfoService;

	@PostMapping("/list")
	@ApiLog("出、入库备件管理-分页")
	@ApiOperation(value = "出、入库备件管理-分页", notes = "传入AnbiaoSpareOutInWarehousePage", position = 1)
	public R<AnbiaoSpareOutInWarehousePage<AnbiaoSpareOutInWarehouse>> list(@RequestBody AnbiaoSpareOutInWarehousePage AnbiaoSpareOutInWarehousePage) {
		AnbiaoSpareOutInWarehousePage<AnbiaoSpareOutInWarehouse> pages = spareOutInWarehouseService.selectAllPage(AnbiaoSpareOutInWarehousePage);
		return R.data(pages);
	}

	@PostMapping("/insert")
	@ApiLog("出、入库备件管理-新增")
	@ApiOperation(value = "出、入库备件管理-新增", notes = "传入AnbiaoSpareOutInWarehouseVO", position = 2)
	public R insert(@RequestBody AnbiaoSpareOutInWarehouse spareOutInWarehouse, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		//返回一个包含所有对象的指定类型的数组
//		if(spareOutInWarehouseVO != null && spareOutInWarehouseVO.getSpareOutInWarehouseList().size() > 0){
//			for (int q = 0; q < spareOutInWarehouseVO.getSpareOutInWarehouseList().size(); q++) {
//				AnbiaoSpareOutInWarehouse spareOutInWarehouse = spareOutInWarehouseVO.getSpareOutInWarehouseList().get(q);
				if(StringUtils.isEmpty(spareOutInWarehouse.getSoiDate())){
					spareOutInWarehouse.setSoiDate(DateUtil.now());
				}
				QueryWrapper<AnbiaoSpareOutInWarehouse> dangerQueryWrapper = new QueryWrapper<AnbiaoSpareOutInWarehouse>();
				dangerQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiDeptId, spareOutInWarehouse.getSoiDeptId());
				dangerQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiDate, spareOutInWarehouse.getSoiDate());
				dangerQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiSpNo, spareOutInWarehouse.getSoiSpNo());
				dangerQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiDelete, 0);
				AnbiaoSpareOutInWarehouse deail = spareOutInWarehouseService.getBaseMapper().selectOne(dangerQueryWrapper);
				if (deail == null) {
					spareOutInWarehouse.setSoiDelete(0);
					if(StringUtils.isNotEmpty(spareOutInWarehouse.getSoiSpNo())){
						spareOutInWarehouse.setSoiSpNo(spareOutInWarehouse.getSoiSpNo());
					}
					//获取当前企业当天最大序列号 维修单号：20230620-4587-0001
					String xuhao = "";
					AnbiaoSpareOutInWarehouse AnbiaoSpareOutInWarehouse = spareOutInWarehouseService.selectMaxXuhao(spareOutInWarehouse.getSoiDeptId().toString());
					if (AnbiaoSpareOutInWarehouse != null && StringUtils.isNotEmpty(AnbiaoSpareOutInWarehouse.getSoiNo())) {
						xuhao = AnbiaoSpareOutInWarehouse.getSoiNo();
						Integer xh = Integer.parseInt(xuhao.substring(xuhao.length() - 4));
						xh = xh += 1;
						xuhao = xh.toString();
						if (xuhao.length() < 2) {
							xuhao = "000" + xuhao;
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							xuhao = format.format(new Date()) + "-" + spareOutInWarehouse.getSoiDeptId() + "-" + xuhao;
							spareOutInWarehouse.setSoiNo(xuhao);
						} else if (xuhao.length() >= 2 && xuhao.length() < 3) {
							xuhao = "00" + xuhao;
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							xuhao = format.format(new Date()) + "-" + spareOutInWarehouse.getSoiDeptId() + "-" + xuhao;
							spareOutInWarehouse.setSoiNo(xuhao);
						} else if (xuhao.length() >= 3 && xuhao.length() < 4) {
							xuhao = "0" + xuhao;
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							xuhao = format.format(new Date()) + "-" + spareOutInWarehouse.getSoiDeptId() + "-" + xuhao;
							spareOutInWarehouse.setSoiNo(xuhao);
						} else {
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							xuhao = format.format(new Date()) + "-" + spareOutInWarehouse.getSoiDeptId() + "-" + xuhao;
							spareOutInWarehouse.setSoiNo(xuhao);
						}
					} else {
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date()) + "-" + spareOutInWarehouse.getSoiDeptId() + "-0001";
						spareOutInWarehouse.setSoiNo(xuhao);
					}
					if (user != null) {
						spareOutInWarehouse.setSoiCreatename(user.getUserName());
						spareOutInWarehouse.setSoiCreateid(user.getUserId());
					}
					spareOutInWarehouse.setSoiCreatetime(DateUtil.now());
					spareOutInWarehouse.setSoiUserId(user.getUserId());
					spareOutInWarehouse.setSoiUserName(user.getUserName());
					ii = spareOutInWarehouseService.save(spareOutInWarehouse);
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
					r.setMsg("该记录已添加！");
					r.setCode(200);
					r.setSuccess(true);
					return r;
				}
//			}
//		}
		return r;
	}

	@PostMapping("/upadte")
	@ApiLog("出、入库备件管理-编辑")
	@ApiOperation(value = "出、入库备件管理-编辑", notes = "传入AnbiaoSpareOutInWarehouse", position = 3)
	public R upadte(@RequestBody AnbiaoSpareOutInWarehouse spareOutInWarehouse, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isNotEmpty(spareOutInWarehouse.getSoiId())){
			if (user != null) {
				spareOutInWarehouse.setSoiUpdatename(user.getUserName());
				spareOutInWarehouse.setSoiUpdateid(user.getUserId());
			}
			spareOutInWarehouse.setSoiUpdatetime(DateUtil.now());
			if(StringUtils.isEmpty(spareOutInWarehouse.getSoiDate())){
				spareOutInWarehouse.setSoiDate(DateUtil.now());
			}
			ii = spareOutInWarehouseService.updateById(spareOutInWarehouse);
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
			r.setMsg("暂无数据");
			r.setCode(200);
			r.setSuccess(true);
		}
		return r;
	}

	@PostMapping("/del")
	@ApiLog("出、入库备件管理-删除")
	@ApiOperation(value = "出、入库备件管理-删除", notes = "传入AnbiaoSpareOutInWarehouse", position = 4)
	public R del(@RequestBody AnbiaoSpareOutInWarehouse spareOutInWarehouse, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSpareOutInWarehouse> AnbiaoSpareOutInWarehouseQueryWrapper = new QueryWrapper<>();
		AnbiaoSpareOutInWarehouseQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiId, spareOutInWarehouse.getSoiId());
		AnbiaoSpareOutInWarehouseQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiDelete, 0);
		AnbiaoSpareOutInWarehouse deal = spareOutInWarehouseService.getBaseMapper().selectOne(AnbiaoSpareOutInWarehouseQueryWrapper);
		if (deal != null) {
			deal.setSoiDelete(1);
			deal.setSoiUpdatetime(DateUtil.now());
			deal.setSoiUpdatename(user.getUserName());
			deal.setSoiUpdateid(user.getUserId());
			spareOutInWarehouseService.updateById(deal);
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

	@PostMapping("/audit")
	@ApiLog("出、入库备件管理-入库审核")
	@ApiOperation(value = "出、入库备件管理-入库审核", notes = "传入AnbiaoSpareOutInWarehouse", position = 5)
	public R audit(@RequestBody AnbiaoSpareOutInWarehouse spareOutInWarehouse, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSpareOutInWarehouse> AnbiaoSpareOutInWarehouseQueryWrapper = new QueryWrapper<>();
		AnbiaoSpareOutInWarehouseQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiId, spareOutInWarehouse.getSoiId());
		AnbiaoSpareOutInWarehouseQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiDelete, 0);
		AnbiaoSpareOutInWarehouseQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiAuditStatus, 0);
		AnbiaoSpareOutInWarehouse deal = spareOutInWarehouseService.getBaseMapper().selectOne(AnbiaoSpareOutInWarehouseQueryWrapper);
		if (deal != null) {
			deal.setSoiAuditStatus(spareOutInWarehouse.getSoiAuditStatus());
			if (user != null) {
				deal.setSoiAuditUserId(user.getUserId().toString());
				deal.setSoiAuditUserName(user.getUserName());
			}
			if(StringUtils.isEmpty(deal.getSoiAuditDate())){
				deal.setSoiAuditDate(DateUtil.now());
			}
			deal.setSoiUpdatetime(DateUtil.now());
			deal.setSoiUpdatename(user.getUserName());
			deal.setSoiUpdateid(user.getUserId());
			boolean ii = spareOutInWarehouseService.updateById(deal);
			if(ii){
				if(spareOutInWarehouse.getSoiAuditStatus() != null && spareOutInWarehouse.getSoiAuditStatus() == 2){
					if(ii){
						r.setMsg("审核成功");
						r.setCode(200);
						r.setSuccess(true);
						return r;
					}else {
						r.setMsg("审核失败");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}else{
					QueryWrapper<AnbiaoSparePartsStore> dangerQueryWrapper = new QueryWrapper<AnbiaoSparePartsStore>();
					dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDeptId, deal.getSoiDeptId());
					dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpName, deal.getSoiSpName());
					dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDelete, 0);
					AnbiaoSparePartsStore deail = sparePartsStoreService.getBaseMapper().selectOne(dangerQueryWrapper);
					if (deail == null) {
						deail = new AnbiaoSparePartsStore();
						deail.setSpDelete(0);
						deail.setSpDeptId(deal.getSoiDeptId());
						if(StringUtils.isNotEmpty(deal.getSoiSpNo())){
							deail.setSpNo(deal.getSoiSpNo());
						}else{
							//获取当前企业当天最大序列号 维修单号：20230620-4587-0001
							String xuhao = "";
							AnbiaoSparePartsStore AnbiaoSparePartsStore = sparePartsStoreService.selectMaxXuhao(deal.getSoiDeptId().toString());
							if (AnbiaoSparePartsStore != null && StringUtils.isNotEmpty(AnbiaoSparePartsStore.getSpNo())) {
								xuhao = AnbiaoSparePartsStore.getSpNo().toString();
								Integer xh = Integer.parseInt(xuhao.substring(xuhao.length() - 4));
								xh = xh += 1;
								xuhao = xh.toString();
								if (xuhao.length() < 2) {
									xuhao = "000" + xuhao;
									SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
									xuhao = format.format(new Date()) + "-" + deail.getSpDeptId() + "-" + xuhao;
									deail.setSpNo(xuhao);
								} else if (xuhao.length() >= 2 && xuhao.length() < 3) {
									xuhao = "00" + xuhao;
									SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
									xuhao = format.format(new Date()) + "-" + deail.getSpDeptId() + "-" + xuhao;
									deail.setSpNo(xuhao);
								} else if (xuhao.length() >= 3 && xuhao.length() < 4) {
									xuhao = "0" + xuhao;
									SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
									xuhao = format.format(new Date()) + "-" + deail.getSpDeptId() + "-" + xuhao;
									deail.setSpNo(xuhao);
								} else {
									SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
									xuhao = format.format(new Date()) + "-" + deail.getSpDeptId() + "-" + xuhao;
									deail.setSpNo(xuhao);
								}
							} else {
								SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
								xuhao = format.format(new Date()) + "-" + deail.getSpDeptId() + "-0001";
								deail.setSpNo(xuhao);
							}
						}

						if (user != null) {
							deail.setSpCreatename(user.getUserName());
							deail.setSpCreateid(user.getUserId());
						}
						deail.setSpCreatetime(DateUtil.now());
						deail.setSpName(deal.getSoiSpName());
						deail.setSpSpecification(deal.getSoiSpSpecification());
						deail.setSpModel(deal.getSoiSpModel());
						deail.setSpBrand(deal.getSoiBrand());
						deail.setSpClassify(deal.getSoiClassify());
						deail.setSpUnit(deal.getSoiUnit());
						deail.setSpCostPrice(deal.getSoiCostPrice());
						deail.setSpWarehouse(deal.getSoiWarehouse());
						deail.setSpWarehouseId(deal.getSoiWarehouseId());
						deail.setSpDeptId(deal.getSoiDeptId());
						deail.setSpGoodProductsNum(deal.getSoiSpGoodProductsNum());
						deail.setSpBadProductsNum(deal.getSoiSpBadProductsNum());
						ii = sparePartsStoreService.save(deail);
						if(ii){
							r.setMsg("审核成功");
							r.setCode(200);
							r.setSuccess(true);
							return r;
						}else {
							r.setMsg("审核失败");
							r.setCode(500);
							r.setSuccess(false);
							return r;
						}
					}else {
						if(StringUtils.isNotEmpty(deail.getSpId())){
							if (user != null) {
								deail.setSpUpdatename(user.getUserName());
								deail.setSpUpdateid(user.getUserId());
							}
							deail.setSpUpdatetime(DateUtil.now());
							deail.setSpName(deal.getSoiSpName());
							deail.setSpSpecification(deal.getSoiSpSpecification());
							deail.setSpModel(deal.getSoiSpModel());
							deail.setSpBrand(deal.getSoiBrand());
							deail.setSpClassify(deal.getSoiClassify());
							deail.setSpUnit(deal.getSoiUnit());
							deail.setSpCostPrice(deal.getSoiCostPrice());
							deail.setSpWarehouse(deal.getSoiWarehouse());
							deail.setSpWarehouseId(deal.getSoiWarehouseId());
							deail.setSpDeptId(deal.getSoiDeptId());
							deail.setSpGoodProductsNum(deal.getSoiSpGoodProductsNum()+deail.getSpGoodProductsNum());
							deail.setSpBadProductsNum(deal.getSoiSpBadProductsNum()+deail.getSpBadProductsNum());
							ii = sparePartsStoreService.updateById(deail);
							if(ii){
								r.setMsg("审核成功");
								r.setCode(200);
								r.setSuccess(true);
								return r;
							}else {
								r.setMsg("审核失败");
								r.setCode(500);
								r.setSuccess(false);
								return r;
							}
						}
					}
				}
			}else{
				r.setMsg("审核失败");
				r.setCode(500);
				r.setSuccess(false);
			}
			return r;
		} else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}

	@PostMapping("/outAudit")
	@ApiLog("出、入库备件管理-出库审核")
	@ApiOperation(value = "出库备件管理-出库审核", notes = "传入AnbiaoSpareOutInWarehouse", position = 6)
	public R outAudit(@RequestBody AnbiaoSpareOutInWarehouse spareOutInWarehouse, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSpareOutInWarehouse> AnbiaoSpareOutInWarehouseQueryWrapper = new QueryWrapper<>();
		AnbiaoSpareOutInWarehouseQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiId, spareOutInWarehouse.getSoiId());
		AnbiaoSpareOutInWarehouseQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiDelete, 0);
		AnbiaoSpareOutInWarehouseQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiAuditStatus, 0);
		AnbiaoSpareOutInWarehouse deal = spareOutInWarehouseService.getBaseMapper().selectOne(AnbiaoSpareOutInWarehouseQueryWrapper);
		if (deal != null) {
			deal.setSoiAuditStatus(spareOutInWarehouse.getSoiAuditStatus());
			if (user != null) {
				deal.setSoiAuditUserId(user.getUserId().toString());
				deal.setSoiAuditUserName(user.getUserName());
			}
			if(StringUtils.isEmpty(deal.getSoiAuditDate())){
				deal.setSoiAuditDate(DateUtil.now());
			}
			deal.setSoiUpdatetime(DateUtil.now());
			deal.setSoiUpdatename(user.getUserName());
			deal.setSoiUpdateid(user.getUserId());
			boolean ii = spareOutInWarehouseService.updateById(deal);
			if(ii){
				QueryWrapper<AnbiaoSparePartsStore> dangerQueryWrapper = new QueryWrapper<AnbiaoSparePartsStore>();
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDeptId, deal.getSoiDeptId());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpName, deal.getSoiSpName());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpNo, deal.getSoiSpNo());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDelete, 0);
				AnbiaoSparePartsStore deail = sparePartsStoreService.getBaseMapper().selectOne(dangerQueryWrapper);
				if (deail == null) {
					r.setMsg("暂无数据");
					r.setCode(200);
					r.setSuccess(true);
					return r;
				}else {
					if(spareOutInWarehouse.getSoiAuditStatus() != null && spareOutInWarehouse.getSoiAuditStatus() == 2){
						if(ii){
							r.setMsg("审核成功");
							r.setCode(200);
							r.setSuccess(true);
							return r;
						}else {
							r.setMsg("审核失败");
							r.setCode(500);
							r.setSuccess(false);
							return r;
						}
					}else{
						if(StringUtils.isNotEmpty(deail.getSpId())){
							if (user != null) {
								deail.setSpUpdatename(user.getUserName());
								deail.setSpUpdateid(user.getUserId());
							}
							deail.setSpUpdatetime(DateUtil.now());
							deail.setSpGoodProductsNum(deail.getSpGoodProductsNum() - deal.getSoiSpGoodProductsNum());
							ii = sparePartsStoreService.updateById(deail);
							if(ii){
								r.setMsg("审核成功");
								r.setCode(200);
								r.setSuccess(true);
								return r;
							}else {
								r.setMsg("审核失败");
								r.setCode(500);
								r.setSuccess(false);
								return r;
							}
						}
					}
				}
			}else{
				r.setMsg("审核失败");
				r.setCode(500);
				r.setSuccess(false);
			}
			return r;
		} else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}

	@PostMapping("/spareImport")
	@ApiLog("入库备件管理--数据导入")
	@ApiOperation(value = "入库备件管理--数据导入", notes = "file", position = 7)
	public R spareImport(@RequestParam(value = "file") MultipartFile file, BladeUser user, Integer deptId) throws ParseException {
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("用户权限验证失败");
			rs.setData(null);
			rs.setSuccess(false);
			return rs;
		}
		ExcelReader reader = null;
		try {
			reader = ExcelUtil.getReader(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//验证数据错误条数
		int bb = 0;
		//错误信息
		String errorStr = "";
		List<Map<String, Object>> readAll = reader.readAll();
		if (readAll.size() > 2000) {
			errorStr += "导入数据超过2000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(400);
			return rs;
		}
		List<AnbiaoSpareOutInWarehouse> spareOutInWarehouseList = new ArrayList<>();
		for (Map<String, Object> a : readAll) {
			AnbiaoSpareOutInWarehouse spareOutInWarehouse = new AnbiaoSpareOutInWarehouse();
			spareOutInWarehouse.setSoiType(1);
			//验证仓库名称是否存在
			String soiWarehouse = String.valueOf(a.get("仓库名称")).trim();
			if (StringUtils.isEmpty(soiWarehouse) || soiWarehouse.equals("null")) {
				spareOutInWarehouse.setMsg("仓库名称不能为空;");
				errorStr += "仓库名称不能为空;";
				bb++;
			}else{
				QueryWrapper<AnbiaoSpareWarehouseInfo> spareWarehouseInfoQueryWrapper = new QueryWrapper<AnbiaoSpareWarehouseInfo>();
				spareWarehouseInfoQueryWrapper.lambda().eq(AnbiaoSpareWarehouseInfo::getWaName, soiWarehouse);
				spareWarehouseInfoQueryWrapper.lambda().eq(AnbiaoSpareWarehouseInfo::getWaDelete, 0);
				AnbiaoSpareWarehouseInfo deail = warehouseInfoService.getBaseMapper().selectOne(spareWarehouseInfoQueryWrapper);
				if (deail != null) {
					spareOutInWarehouse.setSoiWarehouseId(deail.getWaId());
					spareOutInWarehouse.setSoiWarehouse(deail.getWaName());
					spareOutInWarehouse.setSoiDeptId(deail.getWaDeptId());
				}else{
					spareOutInWarehouse.setMsg("该仓库不存在;");
					errorStr += "该仓库不存在;";
					bb++;
				}
			}
//			//验证所属企业是否存在
//			String deptname = String.valueOf(a.get("所属企业")).trim();
//			if (StringUtils.isBlank(deptname)) {
//				spareOutInWarehouse.setMsg("所属企业不能为空;");
//				errorStr += "所属企业不能为空;";
//				bb++;
//			}
//			dept = iSysClient.getDeptByName(deptname.trim());
//			if (dept != null) {
//				spareOutInWarehouse.setSoiDeptId(Integer.valueOf(dept.getId()));
//			} else {
//				spareOutInWarehouse.setMsg(deptname + "所属企业不存在;");
//				errorStr += deptname + "所属企业不存在;";
//				rs.setMsg(errorStr);
//				rs.setCode(500);
//				rs.setSuccess(false);
//				rs.setData(spareOutInWarehouseList);
//				return rs;
//			}
			//验证备件名称不能为空
			String soiSpName = String.valueOf(a.get("备件名称")).trim();
			if (StringUtils.isEmpty(soiSpName) || soiSpName.equals("null")) {
				spareOutInWarehouse.setMsg("备件名称不能为空;");
				errorStr += "备件名称不能为空;";
				bb++;
			} else {
				spareOutInWarehouse.setSoiSpName(soiSpName);
			}
			//验证备件编码不能为空
			String soiSpNo = String.valueOf(a.get("备件编码")).trim();
			if (StringUtils.isEmpty(soiSpNo) || soiSpNo.equals("null")) {
				spareOutInWarehouse.setMsg("备件编码不能为空;");
				errorStr += "备件编码不能为空;";
				bb++;
			} else {
				spareOutInWarehouse.setSoiSpNo(soiSpNo);
			}
			//验证品牌
			String soiBrand = String.valueOf(a.get("品牌")).trim();
			if (StringUtils.isNotBlank(soiBrand) && !soiBrand.equals("null")) {
				spareOutInWarehouse.setSoiBrand(soiBrand);
			}
			//验证规格
			String soiSpSpecification = String.valueOf(a.get("规格")).trim();
			if (StringUtils.isNotBlank(soiSpSpecification) && !soiSpSpecification.equals("null")) {
				spareOutInWarehouse.setSoiSpSpecification(soiSpSpecification);
			}
			//验证型号
			String soiSpModel = String.valueOf(a.get("型号")).trim();
			if (StringUtils.isNotBlank(soiSpModel) && !soiSpModel.equals("null")) {
				spareOutInWarehouse.setSoiSpModel(soiSpModel);
			}
			//验证单位
			String soiUnit = String.valueOf(a.get("单位")).trim();
			if (StringUtils.isNotEmpty(soiUnit) && !soiUnit.equals("null")) {
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("repairsUnit", null);
				if(dictVOList.size() > 0){
					for (int i = 0; i < dictVOList.size(); i++) {
						ss = dictVOList.get(i).getDictValue().equals(soiUnit);
						if (ss == true) {
							break;
						}
					}
					if (ss == true) {
						dictVOList = iDictClient.getDictByCode("repairsUnit", soiUnit);
						spareOutInWarehouse.setSoiUnit(Integer.parseInt(dictVOList.get(0).getDictKey()));
					} else {
						errorStr += soiUnit + ",该单位异常,请校验”;";
						spareOutInWarehouse.setMsg(soiUnit + ",该单位异常,请校验;");
						bb++;
					}
				}else{
					bb++;
				}
			}
			//验证成本价格
			String soiCostPrice = String.valueOf(a.get("成本价格")).trim();
			if (StringUtils.isNotBlank(soiCostPrice) && !soiCostPrice.equals("null")) {
				spareOutInWarehouse.setSoiCostPrice(Double.parseDouble(soiCostPrice));
			}
			//验证良品库存数量
			String soiSpGoodProductsNum = String.valueOf(a.get("良品库存数量")).trim();
			if (StringUtils.isNotBlank(soiSpGoodProductsNum) && !soiSpGoodProductsNum.equals("null")) {
				spareOutInWarehouse.setSoiSpGoodProductsNum(Integer.parseInt(soiSpGoodProductsNum));
			}
			//验证坏件库存数量
			String soiSpBadProductsNum = String.valueOf(a.get("坏件库存数量")).trim();
			if (StringUtils.isNotBlank(soiSpBadProductsNum) && !soiSpBadProductsNum.equals("null")) {
				spareOutInWarehouse.setSoiSpBadProductsNum(Integer.parseInt(soiSpBadProductsNum));
			}
			//验证Excel导入时，是否存在重复数据
			for (AnbiaoSpareOutInWarehouse item : spareOutInWarehouseList) {
				if (item.getSoiSpName().equals(soiSpName) && item.getSoiDeptId().equals(spareOutInWarehouse.getSoiDeptId()) && item.getSoiWarehouseId().equals(spareOutInWarehouse.getSoiWarehouseId())) {
					errorStr += soiSpName + "备件名称重复；";
					spareOutInWarehouse.setMsg(soiSpName + "备件名称重复；");
					bb++;
				}
			}
			if(user != null){
				spareOutInWarehouse.setSoiCreatetime(DateUtil.now());
				spareOutInWarehouse.setSoiCreateid(user.getUserId());
				spareOutInWarehouse.setSoiCreatename(user.getUserName());
				spareOutInWarehouse.setSoiUserId(user.getUserId());
				spareOutInWarehouse.setSoiUserName(user.getUserName());
			}
			spareOutInWarehouse.setSoiDate(DateUtil.now());
			spareOutInWarehouse.setSoiDeptId(spareOutInWarehouse.getSoiDeptId());
			spareOutInWarehouseList.add(spareOutInWarehouse);
		}
		if (bb > 0) {
			rs.setMsg(errorStr);
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setData(spareOutInWarehouseList);
			return rs;
		} else {
			if(spareOutInWarehouseList != null && spareOutInWarehouseList.size() > 0){
				for (int q = 0; q < spareOutInWarehouseList.size(); q++) {
				AnbiaoSpareOutInWarehouse spareOutInWarehouse = spareOutInWarehouseList.get(q);
					if(StringUtils.isEmpty(spareOutInWarehouse.getSoiDate())){
						spareOutInWarehouse.setSoiDate(DateUtil.now());
					}
					QueryWrapper<AnbiaoSpareOutInWarehouse> dangerQueryWrapper = new QueryWrapper<AnbiaoSpareOutInWarehouse>();
					dangerQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiDeptId, spareOutInWarehouse.getSoiDeptId());
					dangerQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiSpNo, spareOutInWarehouse.getSoiSpNo());
					dangerQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiDate, spareOutInWarehouse.getSoiDate());
					dangerQueryWrapper.lambda().eq(AnbiaoSpareOutInWarehouse::getSoiDelete, 0);
					AnbiaoSpareOutInWarehouse deail = spareOutInWarehouseService.getBaseMapper().selectOne(dangerQueryWrapper);
					if (deail == null) {
						spareOutInWarehouse.setSoiDelete(0);
						if(StringUtils.isNotEmpty(spareOutInWarehouse.getSoiSpNo())){
							spareOutInWarehouse.setSoiSpNo(spareOutInWarehouse.getSoiSpNo());
						}
						//获取当前企业当天最大序列号 维修单号：20230620-4587-0001
						String xuhao = "";
						AnbiaoSpareOutInWarehouse AnbiaoSpareOutInWarehouse = spareOutInWarehouseService.selectMaxXuhao(spareOutInWarehouse.getSoiDeptId().toString());
						if (AnbiaoSpareOutInWarehouse != null && StringUtils.isNotEmpty(AnbiaoSpareOutInWarehouse.getSoiNo())) {
							xuhao = AnbiaoSpareOutInWarehouse.getSoiNo();
							Integer xh = Integer.parseInt(xuhao.substring(xuhao.length() - 4));
							xh = xh += 1;
							xuhao = xh.toString();
							if (xuhao.length() < 2) {
								xuhao = "000" + xuhao;
								SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
								xuhao = format.format(new Date()) + "-" + spareOutInWarehouse.getSoiDeptId() + "-" + xuhao;
								spareOutInWarehouse.setSoiNo(xuhao);
							} else if (xuhao.length() >= 2 && xuhao.length() < 3) {
								xuhao = "00" + xuhao;
								SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
								xuhao = format.format(new Date()) + "-" + spareOutInWarehouse.getSoiDeptId() + "-" + xuhao;
								spareOutInWarehouse.setSoiNo(xuhao);
							} else if (xuhao.length() >= 3 && xuhao.length() < 4) {
								xuhao = "0" + xuhao;
								SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
								xuhao = format.format(new Date()) + "-" + spareOutInWarehouse.getSoiDeptId() + "-" + xuhao;
								spareOutInWarehouse.setSoiNo(xuhao);
							} else {
								SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
								xuhao = format.format(new Date()) + "-" + spareOutInWarehouse.getSoiDeptId() + "-" + xuhao;
								spareOutInWarehouse.setSoiNo(xuhao);
							}
						} else {
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							xuhao = format.format(new Date()) + "-" + spareOutInWarehouse.getSoiDeptId() + "-0001";
							spareOutInWarehouse.setSoiNo(xuhao);
						}


						if (user != null) {
							spareOutInWarehouse.setSoiCreatename(user.getUserName());
							spareOutInWarehouse.setSoiCreateid(user.getUserId());
						}
						boolean ii = spareOutInWarehouseService.save(spareOutInWarehouse);
						if (ii) {
							rs.setMsg("导入成功");
							rs.setCode(200);
							rs.setSuccess(true);
						} else {
							rs.setMsg("导入失败");
							rs.setCode(500);
							rs.setSuccess(false);
							return rs;
						}
					}else{
						if(StringUtils.isNotEmpty(deail.getSoiId())){
							spareOutInWarehouse.setSoiId(deail.getSoiId());
							if (user != null) {
								spareOutInWarehouse.setSoiUpdatename(user.getUserName());
								spareOutInWarehouse.setSoiUpdateid(user.getUserId());
							}
							spareOutInWarehouse.setSoiUpdatetime(DateUtil.now());
							if(StringUtils.isEmpty(spareOutInWarehouse.getSoiDate())){
								spareOutInWarehouse.setSoiDate(DateUtil.now());
							}
							boolean ii = spareOutInWarehouseService.updateById(spareOutInWarehouse);
							if (ii) {
								rs.setMsg("导入成功");
								rs.setCode(200);
								rs.setSuccess(true);
							} else {
								rs.setMsg("导入失败");
								rs.setCode(500);
								rs.setSuccess(false);
								return rs;
							}
						}else {
							rs.setMsg("暂无数据");
							rs.setCode(200);
							rs.setSuccess(true);
							return rs;
						}
					}
				}
			}else{
				rs.setMsg("导入成功");
				rs.setCode(200);
				rs.setSuccess(true);
			}
			return rs;
		}
	}


}
