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
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("安全生产培训-培训详情-新增（线下）")
	@ApiOperation(value = "安全生产培训-培训详情-新增（线下）", notes = "传入AnbiaoSafetyTrainingDetail", position = 1)
	public R insert(@RequestBody AnbiaoSafetyTrainingDetail trainingDetail, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSafetyTrainingDetail> trainingDetailQueryWrapper = new QueryWrapper<AnbiaoSafetyTrainingDetail>();
		trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAadAstIds, trainingDetail.getAadAstIds());
		trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAadApIds, trainingDetail.getAadApIds());
		trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAddApBeingJoined, "1");
		AnbiaoSafetyTrainingDetail deail = service.getBaseMapper().selectOne(trainingDetailQueryWrapper);
		if(deail == null) {
			trainingDetail.setAddTime(DateUtil.now());
			boolean i = service.save(trainingDetail);
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


}
