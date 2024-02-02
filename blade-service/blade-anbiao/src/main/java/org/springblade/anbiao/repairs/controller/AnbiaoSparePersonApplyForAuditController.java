package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStorePerson;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePersonApplyForAudit;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStore;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePersonApplyForAudit;
import org.springblade.anbiao.repairs.page.AnbiaoSparePersonApplyForAuditPage;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePartsStorePersonService;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePartsStoreService;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePersonApplyForAuditService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *人员备件审核  前端控制器
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/sparePersonApplyForAudit")
@Api(value = "报修-人员备件审核管理", tags = "报修-人员备件审核管理")
public class AnbiaoSparePersonApplyForAuditController {

	private IAnbiaoSparePersonApplyForAuditService sparePersonApplyForAuditService;

	private IAnbiaoSparePartsStoreService sparePartsStoreService;

	private IAnbiaoSparePartsStorePersonService sparePartsStorePersonService;

	@PostMapping("/list")
	@ApiLog("人员备件审核管理-分页")
	@ApiOperation(value = "人员备件审核管理-分页", notes = "传入AnbiaoSparePersonApplyForAuditPage", position = 1)
	public R<AnbiaoSparePersonApplyForAuditPage<AnbiaoSparePersonApplyForAudit>> list(@RequestBody AnbiaoSparePersonApplyForAuditPage anbiaoSparePersonApplyForAuditPage) {
		AnbiaoSparePersonApplyForAuditPage<AnbiaoSparePersonApplyForAudit> pages = sparePersonApplyForAuditService.selectAllPage(anbiaoSparePersonApplyForAuditPage);
		return R.data(pages);
	}

	@PostMapping("/del")
	@ApiLog("人员备件审核管理-删除")
	@ApiOperation(value = "人员备件审核管理-删除", notes = "传入AnbiaoSparePersonApplyForAudit", position = 2)
	public R del(@RequestBody AnbiaoSparePersonApplyForAudit sparePartsStorePerson, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSparePersonApplyForAudit> AnbiaoSparePersonApplyForAuditQueryWrapper = new QueryWrapper<>();
		AnbiaoSparePersonApplyForAuditQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpId, sparePartsStorePerson.getSpId());
		AnbiaoSparePersonApplyForAuditQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpDelete, 0);
		AnbiaoSparePersonApplyForAudit deal = sparePersonApplyForAuditService.getBaseMapper().selectOne(AnbiaoSparePersonApplyForAuditQueryWrapper);
		if (deal != null) {
			deal.setSpDelete(1);
			deal.setSpUpdatetime(DateUtil.now());
			deal.setSpUpdatename(user.getUserName());
			deal.setSpUpdateid(user.getUserId());
			boolean ii = sparePersonApplyForAuditService.updateById(deal);
			if(ii){
				r.setMsg("删除成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}else {
				r.setMsg("删除失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		} else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
	}

	@PostMapping("/insert")
	@ApiLog("人员备件审核管理-新增")
	@ApiOperation(value = "人员备件审核管理-新增", notes = "传入AnbiaoSparePersonApplyForAudit", position = 2)
	public R insert(@RequestBody AnbiaoSparePersonApplyForAudit sparePersonApplyForAudit, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isEmpty(sparePersonApplyForAudit.getSpDate())){
			sparePersonApplyForAudit.setSpDate(DateUtil.now());
		}
		QueryWrapper<AnbiaoSparePartsStorePerson> storePersonQueryWrapper = new QueryWrapper<AnbiaoSparePartsStorePerson>();
		storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppDeptId, sparePersonApplyForAudit.getSpDeptId());
		storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppSpNo, sparePersonApplyForAudit.getSoiSpNo());
		storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppPersonid, sparePersonApplyForAudit.getSpPersonId());
		AnbiaoSparePartsStorePerson sparePartsStorePerson = sparePartsStorePersonService.getBaseMapper().selectOne(storePersonQueryWrapper);
		if (sparePartsStorePerson != null) {
			if(sparePartsStorePerson.getSppGoodProductsNum() < 2 ){
				QueryWrapper<AnbiaoSparePersonApplyForAudit> dangerQueryWrapper = new QueryWrapper<AnbiaoSparePersonApplyForAudit>();
				dangerQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpAuditStatus, 0);
				dangerQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpPersonId, sparePersonApplyForAudit.getSpPersonId());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSoiSpNo, sparePersonApplyForAudit.getSoiSpNo());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpType, 1);
				AnbiaoSparePersonApplyForAudit deail = sparePersonApplyForAuditService.getBaseMapper().selectOne(dangerQueryWrapper);
				if (deail != null) {
					r.setMsg("该记录已添加，请联系管理员进行审核！");
					r.setCode(200);
					r.setSuccess(true);
					return r;
				}
			}
		}

		QueryWrapper<AnbiaoSparePersonApplyForAudit> dangerQueryWrapper = new QueryWrapper<AnbiaoSparePersonApplyForAudit>();
		dangerQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpDeptId, sparePersonApplyForAudit.getSpDeptId());
		dangerQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpDate, sparePersonApplyForAudit.getSpDate());
		dangerQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSoiSpNo, sparePersonApplyForAudit.getSoiSpNo());
		dangerQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpType, sparePersonApplyForAudit.getSpType());
		AnbiaoSparePersonApplyForAudit deail = sparePersonApplyForAuditService.getBaseMapper().selectOne(dangerQueryWrapper);
		if (deail == null) {
			sparePersonApplyForAudit.setSpDelete(0);
			//获取当前企业当天最大序列号 维修单号：20230620-4587-0001
			String xuhao = "";
			AnbiaoSparePersonApplyForAudit AnbiaoSparePersonApplyForAudit = sparePersonApplyForAuditService.selectMaxXuhao(sparePersonApplyForAudit.getSpDeptId().toString());
			if (AnbiaoSparePersonApplyForAudit != null && StringUtils.isNotEmpty(AnbiaoSparePersonApplyForAudit.getSpNo())) {
				xuhao = AnbiaoSparePersonApplyForAudit.getSpNo();
				Integer xh = Integer.parseInt(xuhao.substring(xuhao.length() - 4));
				xh = xh += 1;
				xuhao = xh.toString();
				if (xuhao.length() < 2) {
					xuhao = "000" + xuhao;
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date()) + "-" + sparePersonApplyForAudit.getSpDeptId() + "-" + xuhao;
					sparePersonApplyForAudit.setSpNo(xuhao);
				} else if (xuhao.length() >= 2 && xuhao.length() < 3) {
					xuhao = "00" + xuhao;
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date()) + "-" + sparePersonApplyForAudit.getSpDeptId() + "-" + xuhao;
					sparePersonApplyForAudit.setSpNo(xuhao);
				} else if (xuhao.length() >= 3 && xuhao.length() < 4) {
					xuhao = "0" + xuhao;
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date()) + "-" + sparePersonApplyForAudit.getSpDeptId() + "-" + xuhao;
					sparePersonApplyForAudit.setSpNo(xuhao);
				} else {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date()) + "-" + sparePersonApplyForAudit.getSpDeptId() + "-" + xuhao;
					sparePersonApplyForAudit.setSpNo(xuhao);
				}
			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				xuhao = format.format(new Date()) + "-" + sparePersonApplyForAudit.getSpDeptId() + "-0001";
				sparePersonApplyForAudit.setSpNo(xuhao);
			}
			if (user != null) {
				sparePersonApplyForAudit.setSpCreatename(user.getUserName());
				sparePersonApplyForAudit.setSpCreateid(user.getUserId());
			}
			sparePersonApplyForAudit.setSpCreatetime(DateUtil.now());
			ii = sparePersonApplyForAuditService.save(sparePersonApplyForAudit);
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
		return r;
	}

	@PostMapping("/upadte")
	@ApiLog("人员备件审核管理-编辑")
	@ApiOperation(value = "人员备件审核管理-编辑", notes = "传入AnbiaoSparePersonApplyForAudit", position = 3)
	public R upadte(@RequestBody AnbiaoSparePersonApplyForAudit sparePersonApplyForAudit, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isNotEmpty(sparePersonApplyForAudit.getSpId())){
			if (user != null) {
				sparePersonApplyForAudit.setSpUpdatename(user.getUserName());
				sparePersonApplyForAudit.setSpUpdateid(user.getUserId());
			}
			sparePersonApplyForAudit.setSpUpdatetime(DateUtil.now());
			if(StringUtils.isEmpty(sparePersonApplyForAudit.getSpDate())){
				sparePersonApplyForAudit.setSpDate(DateUtil.now());
			}
			ii = sparePersonApplyForAuditService.updateById(sparePersonApplyForAudit);
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

	@PostMapping("/audit")
	@ApiLog("人员备件审核管理-员工归还审核")
	@ApiOperation(value = "人员备件审核管理-员工归还审核", notes = "传入AnbiaoSparePersonApplyForAudit", position = 5)
	public R audit(@RequestBody AnbiaoSparePersonApplyForAudit sparePersonApplyForAudit, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSparePersonApplyForAudit> AnbiaoSparePersonApplyForAuditQueryWrapper = new QueryWrapper<>();
		AnbiaoSparePersonApplyForAuditQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpId, sparePersonApplyForAudit.getSpId());
		AnbiaoSparePersonApplyForAuditQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpDelete, 0);
		AnbiaoSparePersonApplyForAuditQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpAuditStatus, 0);
		AnbiaoSparePersonApplyForAudit deal = sparePersonApplyForAuditService.getBaseMapper().selectOne(AnbiaoSparePersonApplyForAuditQueryWrapper);
		if (deal != null) {
			deal.setSpAuditStatus(sparePersonApplyForAudit.getSpAuditStatus());
			if (user != null) {
				deal.setSpAuditUserId(user.getUserId().toString());
				deal.setSpAuditUserName(user.getUserName());
			}
			if(StringUtils.isEmpty(deal.getSpAuditDate())){
				deal.setSpAuditDate(DateUtil.now());
			}
			deal.setSpUpdatetime(DateUtil.now());
			deal.setSpUpdatename(user.getUserName());
			deal.setSpUpdateid(user.getUserId());
			boolean ii = sparePersonApplyForAuditService.updateById(deal);
			if(ii){
				//总库存管理
				QueryWrapper<AnbiaoSparePartsStore> dangerQueryWrapper = new QueryWrapper<AnbiaoSparePartsStore>();
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDeptId, deal.getSpDeptId());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpNo, deal.getSoiSpNo());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDelete, 0);
				AnbiaoSparePartsStore deail = sparePartsStoreService.getBaseMapper().selectOne(dangerQueryWrapper);
				if (deail == null) {
					r.setMsg("暂无数据");
					r.setCode(200);
					r.setSuccess(true);
					return r;
				}else {
					if(sparePersonApplyForAudit.getSpAuditStatus() != null && sparePersonApplyForAudit.getSpAuditStatus() == 2){
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
							deail.setSpGoodProductsNum(deal.getSpNum()+deail.getSpGoodProductsNum());
							ii = sparePartsStoreService.updateById(deail);
							if(ii){
								r.setMsg("审核成功");
								r.setCode(200);
								r.setSuccess(true);
							}else {
								r.setMsg("审核失败");
								r.setCode(500);
								r.setSuccess(false);
								return r;
							}
						}
						//员工库存管理
						QueryWrapper<AnbiaoSparePartsStorePerson> storePersonQueryWrapper = new QueryWrapper<AnbiaoSparePartsStorePerson>();
						storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppDeptId, deail.getSpDeptId());
						storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppSpNo, deail.getSpNo());
						storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppPersonid, deal.getSpPersonId());
						storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppDelete, 0);
						AnbiaoSparePartsStorePerson sparePartsStorePerson = sparePartsStorePersonService.getBaseMapper().selectOne(storePersonQueryWrapper);
						if (sparePartsStorePerson == null) {
							r.setMsg("暂无数据");
							r.setCode(200);
							r.setSuccess(true);
							return r;
						}else {
							if (user != null) {
								sparePartsStorePerson.setSppUpdatename(user.getUserName());
								sparePartsStorePerson.setSppUpdateid(user.getUserId());
							}
							sparePartsStorePerson.setSppUpdatetime(DateUtil.now());
							if(StringUtils.isEmpty(sparePartsStorePerson.getSppDate())){
								sparePartsStorePerson.setSppDate(DateUtil.now());
							}
							sparePartsStorePerson.setSppGoodProductsNum(sparePartsStorePerson.getSppGoodProductsNum()-deal.getSpNum());
							ii = sparePartsStorePersonService.updateById(sparePartsStorePerson);
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
	@ApiLog("人员备件审核管理-员工领料审核")
	@ApiOperation(value = "出库备件管理-员工领料审核", notes = "传入AnbiaoSparePersonApplyForAudit", position = 6)
	public R outAudit(@RequestBody AnbiaoSparePersonApplyForAudit sparePersonApplyForAudit, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSparePersonApplyForAudit> AnbiaoSparePersonApplyForAuditQueryWrapper = new QueryWrapper<>();
		AnbiaoSparePersonApplyForAuditQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpId, sparePersonApplyForAudit.getSpId());
		AnbiaoSparePersonApplyForAuditQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpDelete, 0);
		AnbiaoSparePersonApplyForAuditQueryWrapper.lambda().eq(AnbiaoSparePersonApplyForAudit::getSpAuditStatus, 0);
		AnbiaoSparePersonApplyForAudit deal = sparePersonApplyForAuditService.getBaseMapper().selectOne(AnbiaoSparePersonApplyForAuditQueryWrapper);
		if (deal != null) {
			deal.setSpAuditStatus(sparePersonApplyForAudit.getSpAuditStatus());
			if (user != null) {
				deal.setSpAuditUserId(user.getUserId().toString());
				deal.setSpAuditUserName(user.getUserName());
			}
			if(StringUtils.isEmpty(deal.getSpAuditDate())){
				deal.setSpAuditDate(DateUtil.now());
			}
			deal.setSpUpdatetime(DateUtil.now());
			deal.setSpUpdatename(user.getUserName());
			deal.setSpUpdateid(user.getUserId());
			boolean ii = sparePersonApplyForAuditService.updateById(deal);
			if(ii){
				QueryWrapper<AnbiaoSparePartsStore> dangerQueryWrapper = new QueryWrapper<AnbiaoSparePartsStore>();
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDeptId, deal.getSpDeptId());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpNo, deal.getSoiSpNo());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDelete, 0);
				AnbiaoSparePartsStore deail = sparePartsStoreService.getBaseMapper().selectOne(dangerQueryWrapper);
				if (deail == null) {
					r.setMsg("暂无数据");
					r.setCode(200);
					r.setSuccess(true);
					return r;
				}else {
					if(sparePersonApplyForAudit.getSpAuditStatus() != null && sparePersonApplyForAudit.getSpAuditStatus() == 2){
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
						//总库存管理
						if(StringUtils.isNotEmpty(deail.getSpId())){
							if (user != null) {
								deail.setSpUpdatename(user.getUserName());
								deail.setSpUpdateid(user.getUserId());
							}
							deail.setSpUpdatetime(DateUtil.now());
							deail.setSpGoodProductsNum(deail.getSpGoodProductsNum()-deal.getSpNum());
							ii = sparePartsStoreService.updateById(deail);
							if(ii){
								r.setMsg("审核成功");
								r.setCode(200);
								r.setSuccess(true);
							}else {
								r.setMsg("审核失败");
								r.setCode(500);
								r.setSuccess(false);
								return r;
							}
						}
						//员工库存管理
						QueryWrapper<AnbiaoSparePartsStorePerson> storePersonQueryWrapper = new QueryWrapper<AnbiaoSparePartsStorePerson>();
						storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppDeptId, deail.getSpDeptId());
						storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppSpNo, deail.getSpNo());
						storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppPersonid, deal.getSpPersonId());
						storePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppDelete, 0);
						AnbiaoSparePartsStorePerson person = sparePartsStorePersonService.getBaseMapper().selectOne(storePersonQueryWrapper);
						if (person == null) {
							person = new AnbiaoSparePartsStorePerson();
							person.setSppDelete(0);
							person.setSppDeptId(deal.getSpDeptId());
							//获取当前企业当天最大序列号 维修单号：20230620-4587-0001
							String xuhao = "";
							AnbiaoSparePartsStorePerson AnbiaoSparePartsStorePerson = sparePartsStorePersonService.selectMaxXuhao(deail.getSpDeptId().toString());
							if (AnbiaoSparePartsStorePerson != null && StringUtils.isNotEmpty(AnbiaoSparePartsStorePerson.getSppNo())) {
								xuhao = AnbiaoSparePartsStorePerson.getSppNo();
								Integer xh = Integer.parseInt(xuhao.substring(xuhao.length() - 4));
								xh = xh += 1;
								xuhao = xh.toString();
								if (xuhao.length() < 2) {
									xuhao = "000" + xuhao;
									SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
									xuhao = format.format(new Date()) + "-" + person.getSppDeptId() + "-" + xuhao;
									person.setSppNo(xuhao);
								} else if (xuhao.length() >= 2 && xuhao.length() < 3) {
									xuhao = "00" + xuhao;
									SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
									xuhao = format.format(new Date()) + "-" + person.getSppDeptId() + "-" + xuhao;
									person.setSppNo(xuhao);
								} else if (xuhao.length() >= 3 && xuhao.length() < 4) {
									xuhao = "0" + xuhao;
									SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
									xuhao = format.format(new Date()) + "-" + person.getSppDeptId() + "-" + xuhao;
									person.setSppNo(xuhao);
								} else {
									SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
									xuhao = format.format(new Date()) + "-" + person.getSppDeptId() + "-" + xuhao;
									person.setSppNo(xuhao);
								}
							} else {
								SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
								xuhao = format.format(new Date()) + "-" + person.getSppDeptId() + "-0001";
								person.setSppNo(xuhao);
							}
							if (user != null) {
								person.setSppCreatename(user.getUserName());
								person.setSppCreateid(user.getUserId());
							}
							person.setSppCreatetime(DateUtil.now());
							person.setSppDeptId(deail.getSpDeptId());
							person.setSppName(deail.getSpName());
							person.setSppSpecification(deail.getSpSpecification());
							person.setSppModel(deail.getSpModel());
							person.setSppBrand(deail.getSpBrand());
							person.setSppClassify(deail.getSpClassify());
							person.setSppUnit(deail.getSpUnit());
							person.setSppPersonid(deal.getSpPersonId());
							person.setSppPersonname(deal.getSpPersonName());
							if (StringUtils.isEmpty(person.getSppDate())) {
								person.setSppDate(DateUtil.now());
							}
							person.setSppSpNo(deal.getSoiSpNo());
							person.setSppGoodProductsNum(person.getSppGoodProductsNum() + deal.getSpNum());
							ii = sparePartsStorePersonService.save(person);
							if (ii) {
								r.setMsg("审核成功");
								r.setCode(200);
								r.setSuccess(true);
								return r;
							} else {
								r.setMsg("审核失败");
								r.setCode(500);
								r.setSuccess(false);
								return r;
							}
						}else {
							person.setSppDeptId(deail.getSpDeptId());
							person.setSppName(deail.getSpName());
							person.setSppSpecification(deail.getSpSpecification());
							person.setSppModel(deail.getSpModel());
							person.setSppBrand(deail.getSpBrand());
							person.setSppClassify(deail.getSpClassify());
							person.setSppUnit(deail.getSpUnit());
							person.setSppPersonid(deal.getSpPersonId());
							person.setSppPersonname(deal.getSpPersonName());
							if (user != null) {
								person.setSppUpdatename(user.getUserName());
								person.setSppUpdateid(user.getUserId());
							}
							person.setSppUpdatetime(DateUtil.now());
							if(StringUtils.isEmpty(person.getSppDate())){
								person.setSppDate(DateUtil.now());
							}
							person.setSppGoodProductsNum(person.getSppGoodProductsNum()+deal.getSpNum());
							ii = sparePartsStorePersonService.updateById(person);
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

}
