package org.springblade.anbiao.yinhuanpaicha.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;
import org.springblade.anbiao.yinhuanpaicha.page.AnbiaoHiddenDangerPage;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoHiddenDangerService;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 隐患排查信息 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/anbiao/hiddendanger")
@AllArgsConstructor
@Api(value = "隐患排查信息", tags = "隐患排查信息")
public class AnbiaoHiddenDangerController {

	private IAnbiaoHiddenDangerService service;

	private IFileUploadClient fileUploadClient;

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("隐患排查信息-新增、编辑")
	@ApiOperation(value = "隐患排查信息-新增", notes = "传入AnbiaoHiddenDanger", position = 1)
	public R insert(@RequestBody AnbiaoHiddenDanger danger, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoHiddenDanger> dangerQueryWrapper = new QueryWrapper<AnbiaoHiddenDanger>();
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdDeptIds, danger.getAhdDeptIds());
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdVehicleIds, danger.getAhdVehicleIds());
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdType, danger.getAhdType());
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdDelete, "0");
		dangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdDiscoveryTime, danger.getAhdDiscoveryTime());
		AnbiaoHiddenDanger deail = service.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null){
			danger.setAhdDelete("0");
			if(user != null){
				danger.setAhdCreateByName(user.getUserName());
				danger.setAhdCreateByIds(user.getUserId().toString());
			}
			if (danger.getAhdAddress() == null){
				danger.setAhdAddress("车辆设备");
			}
			danger.setAhdCreateTime(DateUtil.now());
			boolean i = service.save(danger);
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
	 * 编辑
	 */
	@PostMapping("/update")
	@ApiLog("隐患排查信息-编辑")
	@ApiOperation(value = "隐患排查信息-编辑", notes = "传入AnbiaoHiddenDanger", position = 1)
	public R update(@RequestBody AnbiaoHiddenDanger danger, BladeUser user) {
		R r = new R();
		if(user != null){
			danger.setAhdUpdateByName(user.getUserName());
			danger.setAhdUpdateByIds(user.getUserId().toString());
		}
		danger.setAhdUpdateTime(DateUtil.now());
		danger.setAhdIds(danger.getAhdIds());
		boolean i = service.updateById(danger);
		if(i){
			r.setMsg("编辑成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}else{
			r.setMsg("编辑失败");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 * 编辑
	 */
	@GetMapping("/audit")
	@ApiLog("隐患排查信息-审核")
	@ApiOperation(value = "隐患排查信息-审核", notes = "传入数据Id、是否整改((0.否,1.是))", position = 1)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
		@ApiImplicitParam(name = "status", value = "是否整改((0.否,1.是)", required = true)
	})
	public R audit( String Id,Integer status, BladeUser user) {
		R r = new R();
		AnbiaoHiddenDanger danger = new AnbiaoHiddenDanger();
		if(user != null){
			danger.setAhdAuditName(user.getUserName());
			danger.setAhdAuditId(user.getUserId());
		}
		danger.setAhdAuditTime(DateUtil.now());
		danger.setAhdRectificationSituation(status.toString());
		danger.setAhdIds(Id);
		boolean i = service.updateById(danger);
		if(i){
			r.setMsg("审核成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}else{
			r.setMsg("审核失败");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	@PostMapping("/getHiddenDangerPage")
	@ApiLog("隐患排查信息-分页列表")
	@ApiOperation(value = "隐患排查信息-分页列表", notes = "传入AnbiaoHiddenDangerPage", position = 2)
	public R<AnbiaoHiddenDangerPage<AnbiaoHiddenDangerVO>> getHiddenDangerPage(@RequestBody AnbiaoHiddenDangerPage anbiaoHiddenDangerPage, BladeUser user) {
		R rs = new R();
		AnbiaoHiddenDangerPage<AnbiaoHiddenDangerVO> list= service.selectPage(anbiaoHiddenDangerPage);
		return R.data(list);
	}

	@GetMapping("/remove")
	@ApiOperation(value = "隐患排查信息-删除", notes = "传入Id", position = 3)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
	})
	public R remove(String Id,BladeUser user) {
		R r = new R();
		AnbiaoHiddenDanger danger = new AnbiaoHiddenDanger();
		if(user != null) {
			danger.setAhdUpdateByName(user.getUserName());
			danger.setAhdUpdateByIds(user.getUserId().toString());
		}
		danger.setAhdUpdateTime(DateUtil.now());
		danger.setAhdDelete("1");
		danger.setAhdIds(Id);
		boolean i = service.updateById(danger);
		if(i){
			r.setMsg("删除成功");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}else{
			r.setMsg("删除失败");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	@GetMapping("/getById")
	@ApiOperation(value = "隐患排查信息-根据ID获取详情", notes = "传入Id", position = 4)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true)})
	public R getById(String Id) {
		AnbiaoHiddenDanger deail = service.getById(Id);
		if(deail != null) {
			//隐患附件(以英文分号分割)
			if (StrUtil.isNotEmpty(deail.getAhdHiddendangerEnclosure()) && deail.getAhdHiddendangerEnclosure().contains("http") == false) {
				deail.setAhdHiddendangerEnclosure(fileUploadClient.getUrl(deail.getAhdHiddendangerEnclosure()));
			}
			//整改附件(以英文分号分割)
			if (StrUtil.isNotEmpty(deail.getAhdRectificationEnclosure()) && deail.getAhdRectificationEnclosure().contains("http") == false) {
				deail.setAhdRectificationEnclosure(fileUploadClient.getUrl(deail.getAhdRectificationEnclosure()));
			}
		}
		return R.data(deail);
	}

}
