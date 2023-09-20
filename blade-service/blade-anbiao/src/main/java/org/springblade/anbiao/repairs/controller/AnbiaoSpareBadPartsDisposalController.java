package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareBadPartsDisposal;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStore;
import org.springblade.anbiao.repairs.page.AnbiaoSpareBadPartsDisposalPage;
import org.springblade.anbiao.repairs.service.IAnbiaoSpareBadPartsDisposalService;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePartsStoreService;
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
 * 坏件处理记录 前端控制器
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/sareBadPartsDisposal")
@Api(value = "报修-坏件处理记录管理", tags = "报修-坏件处理记录管理")
public class AnbiaoSpareBadPartsDisposalController {

	private IAnbiaoSpareBadPartsDisposalService spareBadPartsDisposalService;

	private IAnbiaoSparePartsStoreService sparePartsStoreService;

	@PostMapping("/list")
	@ApiLog("坏件处理记录管理-分页")
	@ApiOperation(value = "坏件处理记录管理-分页", notes = "传入AnbiaoSpareBadPartsDisposalPage", position = 1)
	public R<AnbiaoSpareBadPartsDisposalPage<AnbiaoSpareBadPartsDisposal>> list(@RequestBody AnbiaoSpareBadPartsDisposalPage AnbiaoSpareBadPartsDisposalPage) {
		AnbiaoSpareBadPartsDisposalPage<AnbiaoSpareBadPartsDisposal> pages = spareBadPartsDisposalService.selectAllPage(AnbiaoSpareBadPartsDisposalPage);
		return R.data(pages);
	}

	@PostMapping("/insert")
	@ApiLog("坏件处理记录管理-新增、编辑")
	@ApiOperation(value = "坏件处理记录管理-新增、编辑", notes = "传入AnbiaoSpareBadPartsDisposal", position = 2)
	public R insert(@RequestBody AnbiaoSpareBadPartsDisposal spareBadPartsDisposal, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		spareBadPartsDisposal.setSbpDate(DateUtil.now());
		if(StringUtils.isNotEmpty(spareBadPartsDisposal.getSbpId())){
			if (user != null) {
				spareBadPartsDisposal.setSbpUpdatename(user.getUserName());
				spareBadPartsDisposal.setSbpUpdateid(user.getUserId());
			}
			spareBadPartsDisposal.setSbpUpdatetime(DateUtil.now());
			ii = spareBadPartsDisposalService.updateById(spareBadPartsDisposal);
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
			QueryWrapper<AnbiaoSpareBadPartsDisposal> dangerQueryWrapper = new QueryWrapper<AnbiaoSpareBadPartsDisposal>();
			dangerQueryWrapper.lambda().eq(AnbiaoSpareBadPartsDisposal::getSbpDeptId, spareBadPartsDisposal.getSbpDeptId());
			dangerQueryWrapper.lambda().eq(AnbiaoSpareBadPartsDisposal::getSbpDate, spareBadPartsDisposal.getSbpDate());
			dangerQueryWrapper.lambda().eq(AnbiaoSpareBadPartsDisposal::getSbpSpNo, spareBadPartsDisposal.getSbpSpNo());
			AnbiaoSpareBadPartsDisposal deail = spareBadPartsDisposalService.getBaseMapper().selectOne(dangerQueryWrapper);
			if (deail == null) {
				spareBadPartsDisposal.setSbpDelete(0);
				//获取当前企业当天最大序列号 维修单号：20230620-4587-0001
				String xuhao = "";
				AnbiaoSpareBadPartsDisposal AnbiaoSpareBadPartsDisposal = spareBadPartsDisposalService.selectMaxXuhao(spareBadPartsDisposal.getSbpDeptId().toString());
				if (AnbiaoSpareBadPartsDisposal != null && StringUtils.isNotEmpty(AnbiaoSpareBadPartsDisposal.getSbpNo())) {
					xuhao = AnbiaoSpareBadPartsDisposal.getSbpNo();
					Integer xh = Integer.parseInt(xuhao.substring(xuhao.length() - 4));
					xh = xh += 1;
					xuhao = xh.toString();
					if (xuhao.length() < 2) {
						xuhao = "000" + xuhao;
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date()) + "-" + spareBadPartsDisposal.getSbpDeptId() + "-" + xuhao;
						spareBadPartsDisposal.setSbpNo(xuhao);
					} else if (xuhao.length() >= 2 && xuhao.length() < 3) {
						xuhao = "00" + xuhao;
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date()) + "-" + spareBadPartsDisposal.getSbpDeptId() + "-" + xuhao;
						spareBadPartsDisposal.setSbpNo(xuhao);
					} else if (xuhao.length() >= 3 && xuhao.length() < 4) {
						xuhao = "0" + xuhao;
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date()) + "-" + spareBadPartsDisposal.getSbpDeptId() + "-" + xuhao;
						spareBadPartsDisposal.setSbpNo(xuhao);
					} else {
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date()) + "-" + spareBadPartsDisposal.getSbpDeptId() + "-" + xuhao;
						spareBadPartsDisposal.setSbpNo(xuhao);
					}
				} else {
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					xuhao = format.format(new Date()) + "-" + spareBadPartsDisposal.getSbpDeptId() + "-0001";
					spareBadPartsDisposal.setSbpNo(xuhao);
				}
				if (user != null) {
					spareBadPartsDisposal.setSbpCreatename(user.getUserName());
					spareBadPartsDisposal.setSbpCreateid(user.getUserId());
				}
				spareBadPartsDisposal.setSbpCreatetime(DateUtil.now());
				ii = spareBadPartsDisposalService.save(spareBadPartsDisposal);
				if (ii) {
					QueryWrapper<AnbiaoSparePartsStore> anbiaoSparePartsStoreQueryWrapper = new QueryWrapper<AnbiaoSparePartsStore>();
					anbiaoSparePartsStoreQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpDeptId, spareBadPartsDisposal.getSbpDeptId());
					anbiaoSparePartsStoreQueryWrapper.lambda().eq(AnbiaoSparePartsStore::getSpNo, spareBadPartsDisposal.getSbpSpNo());
					AnbiaoSparePartsStore deailInfo = sparePartsStoreService.getBaseMapper().selectOne(anbiaoSparePartsStoreQueryWrapper);
					if (deailInfo != null) {
						if(StringUtils.isNotEmpty(deailInfo.getSpId())){
							if (user != null) {
								deailInfo.setSpUpdatename(user.getUserName());
								deailInfo.setSpUpdateid(user.getUserId());
							}
							deailInfo.setSpUpdatetime(DateUtil.now());
							deailInfo.setSpBadProductsNum(spareBadPartsDisposal.getSbpNum());
							ii = sparePartsStoreService.updateById(deailInfo);
							if(ii){
								r.setMsg("新增成功");
								r.setCode(200);
								r.setSuccess(true);
								return r;
							}else {
								r.setMsg("新增失败");
								r.setCode(500);
								r.setSuccess(false);
								return r;
							}
						}
					}
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
		}
		return r;
	}

	@PostMapping("/del")
	@ApiLog("坏件处理记录管理-删除")
	@ApiOperation(value = "坏件处理记录管理-删除", notes = "传入AnbiaoSpareBadPartsDisposal", position = 3)
	public R del(@RequestBody AnbiaoSpareBadPartsDisposal spareBadPartsDisposal, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSpareBadPartsDisposal> AnbiaoSpareBadPartsDisposalQueryWrapper = new QueryWrapper<>();
		AnbiaoSpareBadPartsDisposalQueryWrapper.lambda().eq(AnbiaoSpareBadPartsDisposal::getSbpId, spareBadPartsDisposal.getSbpId());
		AnbiaoSpareBadPartsDisposalQueryWrapper.lambda().eq(AnbiaoSpareBadPartsDisposal::getSbpDelete, 0);
		AnbiaoSpareBadPartsDisposal deal = spareBadPartsDisposalService.getBaseMapper().selectOne(AnbiaoSpareBadPartsDisposalQueryWrapper);
		if (deal != null) {
			deal.setSbpDelete(1);
			deal.setSbpUpdatetime(DateUtil.now());
			deal.setSbpUpdatename(user.getUserName());
			deal.setSbpUpdateid(user.getUserId());
			spareBadPartsDisposalService.updateById(deal);
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


}
