package org.springblade.anbiao.repairs.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStorePerson;
import org.springblade.anbiao.repairs.page.AnbiaoSparePartsStorePersonPage;
import org.springblade.anbiao.repairs.service.IAnbiaoSparePartsStorePersonService;
import org.springblade.anbiao.repairs.vo.AnbiaoSparePartsStorePersonVO;
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
 * 人员备件库管理 前端控制器
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/sparePartsStorePerson")
@Api(value = "报修-人员备件库管理", tags = "报修-人员备件库管理")
public class AnbiaoSparePartsStorePersonController {

	private IAnbiaoSparePartsStorePersonService sparePartsStorePersonService;

	@PostMapping("/list")
	@ApiLog("人员备件库管理-分页")
	@ApiOperation(value = "人员备件库管理-分页", notes = "传入AnbiaoSparePartsStorePersonPage", position = 1)
	public R<AnbiaoSparePartsStorePersonPage<AnbiaoSparePartsStorePerson>> list(@RequestBody AnbiaoSparePartsStorePersonPage anbiaoSparePartsStorePersonPage) {
		AnbiaoSparePartsStorePersonPage<AnbiaoSparePartsStorePerson> pages = sparePartsStorePersonService.selectAllPage(anbiaoSparePartsStorePersonPage);
		return R.data(pages);
	}

	@PostMapping("/insert")
	@ApiLog("人员备件库管理-新增")
	@ApiOperation(value = "人员备件库管理-新增", notes = "传入AnbiaosparePartsStorePersonVO", position = 2)
	public R insert(@RequestBody AnbiaoSparePartsStorePersonVO sparePartsStorePersonVO, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		//返回一个包含所有对象的指定类型的数组
		if(sparePartsStorePersonVO != null && sparePartsStorePersonVO.getSparePartsStorePersonList().size() > 0){
			for (int q = 0; q < sparePartsStorePersonVO.getSparePartsStorePersonList().size(); q++) {
				AnbiaoSparePartsStorePerson sparePartsStorePerson = sparePartsStorePersonVO.getSparePartsStorePersonList().get(q);
				QueryWrapper<AnbiaoSparePartsStorePerson> dangerQueryWrapper = new QueryWrapper<AnbiaoSparePartsStorePerson>();
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppDeptId, sparePartsStorePerson.getSppDeptId());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppDate, sparePartsStorePerson.getSppDate());
				dangerQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppSpNo, sparePartsStorePerson.getSppSpNo());
				AnbiaoSparePartsStorePerson deail = sparePartsStorePersonService.getBaseMapper().selectOne(dangerQueryWrapper);
				if (deail == null) {
					sparePartsStorePerson.setSppDelete(0);
					//获取当前企业当天最大序列号 维修单号：20230620-4587-0001
					String xuhao = "";
					AnbiaoSparePartsStorePerson AnbiaoSparePartsStorePerson = sparePartsStorePersonService.selectMaxXuhao(sparePartsStorePerson.getSppDeptId().toString());
					if (sparePartsStorePerson != null && StringUtils.isNotEmpty(sparePartsStorePerson.getSppNo())) {
						xuhao = sparePartsStorePerson.getSppNo();
						Integer xh = Integer.parseInt(xuhao.substring(xuhao.length() - 4));
						xh = xh += 1;
						xuhao = xh.toString();
						if (xuhao.length() < 2) {
							xuhao = "000" + xuhao;
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							xuhao = format.format(new Date()) + "-" + sparePartsStorePerson.getSppDeptId() + "-" + xuhao;
							sparePartsStorePerson.setSppNo(xuhao);
						} else if (xuhao.length() >= 2 && xuhao.length() < 3) {
							xuhao = "00" + xuhao;
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							xuhao = format.format(new Date()) + "-" + sparePartsStorePerson.getSppDeptId() + "-" + xuhao;
							sparePartsStorePerson.setSppNo(xuhao);
						} else if (xuhao.length() >= 3 && xuhao.length() < 4) {
							xuhao = "0" + xuhao;
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							xuhao = format.format(new Date()) + "-" + sparePartsStorePerson.getSppDeptId() + "-" + xuhao;
							sparePartsStorePerson.setSppNo(xuhao);
						} else {
							SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
							xuhao = format.format(new Date()) + "-" + sparePartsStorePerson.getSppDeptId() + "-" + xuhao;
							sparePartsStorePerson.setSppNo(xuhao);
						}
					} else {
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						xuhao = format.format(new Date()) + "-" + sparePartsStorePerson.getSppDeptId() + "-0001";
						sparePartsStorePerson.setSppNo(xuhao);
					}
					if (user != null) {
						sparePartsStorePerson.setSppCreatename(user.getUserName());
						sparePartsStorePerson.setSppCreateid(user.getUserId());
					}
					sparePartsStorePerson.setSppCreatetime(DateUtil.now());
					ii = sparePartsStorePersonService.save(AnbiaoSparePartsStorePerson);
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
			}
		}
		return r;
	}

	@PostMapping("/upadte")
	@ApiLog("人员备件库管理-编辑")
	@ApiOperation(value = "人员备件库管理-编辑", notes = "传入AnbiaoSparePartsStorePerson", position = 3)
	public R upadte(@RequestBody AnbiaoSparePartsStorePerson sparePartsStorePerson, BladeUser user) throws InterruptedException {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isNotEmpty(sparePartsStorePerson.getSppId())){
			if (user != null) {
				sparePartsStorePerson.setSppUpdatename(user.getUserName());
				sparePartsStorePerson.setSppUpdateid(user.getUserId());
			}
			sparePartsStorePerson.setSppUpdatetime(DateUtil.now());
			if(StringUtils.isEmpty(sparePartsStorePerson.getSppDate())){
				sparePartsStorePerson.setSppDate(DateUtil.now());
			}
			ii = sparePartsStorePersonService.updateById(sparePartsStorePerson);
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
	@ApiLog("人员备件库管理-删除")
	@ApiOperation(value = "人员备件库管理-删除", notes = "传入AnbiaoSparePartsStorePerson", position = 4)
	public R del(@RequestBody AnbiaoSparePartsStorePerson sparePartsStorePerson, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSparePartsStorePerson> AnbiaoSparePartsStorePersonQueryWrapper = new QueryWrapper<>();
		AnbiaoSparePartsStorePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppId, sparePartsStorePerson.getSppId());
		AnbiaoSparePartsStorePersonQueryWrapper.lambda().eq(AnbiaoSparePartsStorePerson::getSppDelete, 0);
		AnbiaoSparePartsStorePerson deal = sparePartsStorePersonService.getBaseMapper().selectOne(AnbiaoSparePartsStorePersonQueryWrapper);
		if (deal != null) {
			deal.setSppDelete(1);
			deal.setSppUpdatetime(DateUtil.now());
			deal.setSppUpdatename(user.getUserName());
			deal.setSppUpdateid(user.getUserId());
			boolean ii = sparePartsStorePersonService.updateById(deal);
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

}
