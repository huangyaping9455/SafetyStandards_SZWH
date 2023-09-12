package org.springblade.anbiao.problemFeedback.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.problemFeedback.entity.AnbiaoProblemFeedback;
import org.springblade.anbiao.problemFeedback.page.AnbiaoProblemFeedbackPage;
import org.springblade.anbiao.problemFeedback.service.IAnbiaoProblemFeedbackService;
import org.springblade.common.tool.StringUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2023-08-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/problemFeedback")
@Api(value = "驾驶员问题反馈管理", tags = "驾驶员问题反馈管理")
public class AnbiaoProblemFeedbackController {

	private IAnbiaoProblemFeedbackService problemFeedbackService;

	@PostMapping("/insert")
	@ApiLog("驾驶员问题反馈-新增、编辑")
	@ApiOperation(value = "驾驶员问题反馈-新增、编辑", notes = "传入AnbiaoProblemFeedback", position = 1)
	public R insert(@RequestBody AnbiaoProblemFeedback problemFeedback, BladeUser user) {
		R r = new R();
		boolean ii = false;
		if(StringUtils.isEmpty(problemFeedback.getPfDate())){
			problemFeedback.setPfDate(DateUtil.now());
		}
		QueryWrapper<AnbiaoProblemFeedback> dangerQueryWrapper = new QueryWrapper<AnbiaoProblemFeedback>();
		dangerQueryWrapper.lambda().eq(AnbiaoProblemFeedback::getPfDeptId, problemFeedback.getPfDeptId());
		dangerQueryWrapper.lambda().eq(AnbiaoProblemFeedback::getPfDelete, 0);
		dangerQueryWrapper.lambda().eq(AnbiaoProblemFeedback::getPfDriverId, problemFeedback.getPfDriverId());
		dangerQueryWrapper.lambda().eq(AnbiaoProblemFeedback::getPfDate, problemFeedback.getPfDate());
		AnbiaoProblemFeedback deail = problemFeedbackService.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null) {
			problemFeedback.setPfDelete(0);
			problemFeedback.setPfCreatetime(DateUtil.now());
			ii = problemFeedbackService.save(problemFeedback);
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
			problemFeedback.setPfId(deail.getPfId());
			if(user != null){
				problemFeedback.setPfUpdatename(user.getUserName());
				problemFeedback.setPfUpdateid(user.getUserId());
			}
			problemFeedback.setPfUpdatetime(DateUtil.now());
			ii = problemFeedbackService.updateById(problemFeedback);
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
	@ApiLog("驾驶员问题反馈-删除")
	@ApiOperation(value = "驾驶员问题反馈-删除", notes = "传入id", position = 2)
	public R del(@RequestBody AnbiaoProblemFeedback problemFeedback, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoProblemFeedback> anbiaoRiskConfigurationQueryWrapper = new QueryWrapper<>();
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoProblemFeedback::getPfId, problemFeedback.getPfId());
		anbiaoRiskConfigurationQueryWrapper.lambda().eq(AnbiaoProblemFeedback::getPfDelete, 0);
		AnbiaoProblemFeedback deal = problemFeedbackService.getBaseMapper().selectOne(anbiaoRiskConfigurationQueryWrapper);
		if (deal != null) {
			deal.setPfDelete(1);
			deal.setPfUpdatetime(DateUtil.now());
			if(user != null){
				deal.setPfUpdatename(user.getUserName());
				deal.setPfUpdateid(user.getUserId());
			}
			boolean ii = problemFeedbackService.updateById(deal);
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
	@ApiLog("驾驶员问题反馈-分页")
	@ApiOperation(value = "驾驶员问题反馈-分页", notes = "传入AnbiaoProblemFeedbackPage", position = 3)
	public R<AnbiaoProblemFeedbackPage<AnbiaoProblemFeedback>> list(@RequestBody AnbiaoProblemFeedbackPage AnbiaoProblemFeedbackPage) {
		AnbiaoProblemFeedbackPage<AnbiaoProblemFeedback> pages = problemFeedbackService.selectALLPage(AnbiaoProblemFeedbackPage);
		return R.data(pages);
	}

	@PostMapping("/return")
	@ApiLog("驾驶员问题反馈-处理反馈")
	@ApiOperation(value = "驾驶员问题反馈-处理反馈", notes = "传入AnbiaoProblemFeedback", position = 4)
	public R returnInfo(@RequestBody AnbiaoProblemFeedback repairsReturn, BladeUser user) {
		R r = new R();
		boolean ii = false;
		QueryWrapper<AnbiaoProblemFeedback> dangerQueryWrapper = new QueryWrapper<AnbiaoProblemFeedback>();
		dangerQueryWrapper.lambda().eq(AnbiaoProblemFeedback::getPfId, repairsReturn.getPfId());
		AnbiaoProblemFeedback deail = problemFeedbackService.getBaseMapper().selectOne(dangerQueryWrapper);
		if(deail == null) {
			if (user != null) {
				repairsReturn.setPfUpdatename(user.getUserName());
				repairsReturn.setPfUpdateid(user.getUserId());
			}
			repairsReturn.setPfUpdatetime(DateUtil.now());
			repairsReturn.setPfUpdatetime(DateUtil.now());
			ii = problemFeedbackService.save(repairsReturn);
			if (ii) {
				r.setMsg("回访成功");
				r.setCode(200);
				r.setSuccess(true);
			} else {
				r.setMsg("回访失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}else{
			r.setMsg("暂无数据");
			r.setCode(200);
			r.setSuccess(true);
		}
		return r;
	}



}
