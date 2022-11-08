package org.springblade.anbiao.jiaoyupeixun.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTrainingDetail;
import org.springblade.anbiao.jiaoyupeixun.service.IAnbiaoSafetyTrainingDetailService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 安全会议参会记录 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-11-01
 */
@RestController
@RequestMapping("/anbiao/safetytraindetail")
@AllArgsConstructor
@Api(value = "安全生产培训-培训详情", tags = "安全生产培训-培训详情")
public class AnbiaoSafetyTrainingDetailController {

	private IAnbiaoSafetyTrainingDetailService service;

	/**
	 * 签到
	 */
	@PostMapping("/signIn")
	@ApiLog("安全生产培训-签到")
	@ApiOperation(value = "安全生产培训-签到", notes = "传入AnbiaoSafetyTraining", position = 1)
	public R signIn(@RequestBody AnbiaoSafetyTrainingDetail train, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSafetyTrainingDetail> trainingDetailQueryWrapper = new QueryWrapper<AnbiaoSafetyTrainingDetail>();
		trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAadAstIds, train.getAadAstIds());
		trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAadApIds, train.getAadApIds());
		trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAddApBeingJoined, "0");
		AnbiaoSafetyTrainingDetail trainingDetail = service.getBaseMapper().selectOne(trainingDetailQueryWrapper);
		if(trainingDetail != null) {
			train.setAddTime(DateUtil.now());
			train.setAadAstIds(train.getAadIds());
			train.setAddApBeingJoined("1");
			train.setAddApHeadPortrait(train.getAddApHeadPortrait());
			train.setAddApAutograph(train.getAddApAutograph());
			boolean is = service.updateById(train);
			if(is){
				r.setMsg("签到成功");
				r.setCode(200);
				r.setSuccess(true);
			}else{
				r.setMsg("签到失败");
				r.setCode(500);
				r.setSuccess(false);
			}
		}
		return r;
	}




}
