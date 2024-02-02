package org.springblade.anbiao.violateDiscipline.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.violateDiscipline.entity.AnbiaoViolateDiscipline;
import org.springblade.anbiao.violateDiscipline.page.AnbiaoViolateDisciplinePage;
import org.springblade.anbiao.violateDiscipline.service.IAnbiaoViolateDisciplineService;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.tool.StringUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 违规违纪材料上传登记 前端控制器
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/violateDiscipline")
@Api(value = "违规违纪材料上传登记管理", tags = "违规违纪材料上传登记管理")
public class AnbiaoViolateDisciplineController {

	private IAnbiaoViolateDisciplineService violateDisciplineService;

	private FileServer fileServer;

	private IFileUploadClient fileUploadClient;

	@PostMapping("/insert")
	@ApiLog("违规违纪材料上传登记管理-新增、编辑")
	@ApiOperation(value = "违规违纪材料上传登记管理-新增、编辑", notes = "传入AnbiaoViolateDiscipline", position = 1)
	public R insert(@RequestBody AnbiaoViolateDiscipline violateDiscipline, BladeUser user) {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isEmpty(violateDiscipline.getVdDate())){
			violateDiscipline.setVdDate(DateUtil.now());
		}
		QueryWrapper<AnbiaoViolateDiscipline> dangerQueryWrapper = new QueryWrapper<AnbiaoViolateDiscipline>();
		dangerQueryWrapper.lambda().eq(AnbiaoViolateDiscipline::getVdDeptId, violateDiscipline.getVdDeptId());
		dangerQueryWrapper.lambda().eq(AnbiaoViolateDiscipline::getVdDelete, 0);
		dangerQueryWrapper.lambda().eq(AnbiaoViolateDiscipline::getVdDate, violateDiscipline.getVdDate());
		dangerQueryWrapper.lambda().eq(AnbiaoViolateDiscipline::getVdJsyId, violateDiscipline.getVdJsyId());
		AnbiaoViolateDiscipline deail = violateDisciplineService.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null) {
			violateDiscipline.setVdCreatetime(DateUtil.now());
			if(user != null){
				violateDiscipline.setVdCreatename(user.getUserName());
				violateDiscipline.setVdCreateid(user.getUserId());
			}
			ii = violateDisciplineService.save(violateDiscipline);
			if (ii) {
				r.setMsg("新增成功");
				r.setCode(200);
				r.setSuccess(true);
			} else {
				r.setMsg("新增失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}else{
			violateDiscipline.setVdId(deail.getVdId());
			if(user != null){
				violateDiscipline.setVdUpdatename(user.getUserName());
				violateDiscipline.setVdUpdateid(user.getUserId());
			}
			violateDiscipline.setVdUpdatetime(DateUtil.now());
			ii = violateDisciplineService.updateById(violateDiscipline);
			if(ii){
				r.setMsg("编辑成功");
				r.setCode(200);
				r.setSuccess(true);
			}else{
				r.setMsg("编辑失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}
		return r;
	}

	@PostMapping("/del")
	@ApiLog("违规违纪材料上传登记管理-删除")
	@ApiOperation(value = "违规违纪材料上传登记管理-删除", notes = "传入id", position = 2)
	public R del(@RequestBody AnbiaoViolateDiscipline violateDiscipline, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoViolateDiscipline> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoViolateDiscipline::getVdId, violateDiscipline.getVdId());
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoViolateDiscipline::getVdDelete, 0);
		AnbiaoViolateDiscipline deal = violateDisciplineService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
		if (deal != null) {
			deal.setVdDelete(1);
			deal.setVdUpdatetime(DateUtil.now());
			if(user != null){
				deal.setVdUpdatename(user.getUserName());
				deal.setVdUpdateid(user.getUserId());
			}
			boolean ii = violateDisciplineService.updateById(deal);
			if (ii) {
				r.setMsg("删除成功");
				r.setCode(200);
				r.setSuccess(true);
			} else {
				r.setMsg("删除失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		} else {
			r.setMsg("无数据");
			r.setCode(200);
			r.setSuccess(true);
			return r;
		}
		return r;
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("违规违纪材料上传登记管理-分页")
	@ApiOperation(value = "违规违纪材料上传登记管理-分页", notes = "传入AnbiaoViolateDisciplinePage", position = 3)
	public R<AnbiaoViolateDisciplinePage<AnbiaoViolateDiscipline>> list(@RequestBody AnbiaoViolateDisciplinePage AnbiaoViolateDisciplinePage) {
		AnbiaoViolateDisciplinePage<AnbiaoViolateDiscipline> pages = violateDisciplineService.selectALLPage(AnbiaoViolateDisciplinePage);
		return R.data(pages);
	}

	@GetMapping("/detail")
	@ApiLog("违规违纪材料上传登记管理--详情")
	@ApiOperation(value = "违规违纪材料上传登记管理--详情",notes = "传入数据Id", position = 4)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true)})
	public R detail(String Id){
		R r = new R();
		AnbiaoViolateDiscipline deail = violateDisciplineService.getById(Id);
		if (deail != null){
			//附件
			if(StrUtil.isNotEmpty(deail.getVdFujian()) && deail.getVdFujian().contains("http") == false){
				deail.setVdFujian(fileUploadClient.getUrl(deail.getVdFujian()));
			}
			r.setData(deail);
			r.setCode(200);
			r.setMsg("获取成功");
			return r;
		}else {
			r.setCode(500);
			r.setMsg("暂无数据");
			return r;
		}
	}

	/**
	 * 分页
	 */
	@PostMapping("/getTjList")
	@ApiLog("违规违纪材料上传登记管理-分页（统计）")
	@ApiOperation(value = "违规违纪材料上传登记管理-分页（统计）", notes = "传入AnbiaoViolateDisciplinePage", position = 5)
	public R<AnbiaoViolateDisciplinePage<AnbiaoViolateDiscipline>> getTJALLPage(@RequestBody AnbiaoViolateDisciplinePage AnbiaoViolateDisciplinePage) {
		AnbiaoViolateDisciplinePage<AnbiaoViolateDiscipline> pages = violateDisciplineService.selectTJALLPage(AnbiaoViolateDisciplinePage);
		return R.data(pages);
	}


}
