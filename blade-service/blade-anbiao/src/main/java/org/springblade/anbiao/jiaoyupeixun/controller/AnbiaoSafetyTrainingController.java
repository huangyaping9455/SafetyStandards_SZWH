package org.springblade.anbiao.jiaoyupeixun.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTrainingDetail;
import org.springblade.anbiao.jiaoyupeixun.page.AnbiaoSafetyTrainingPage;
import org.springblade.anbiao.jiaoyupeixun.service.IAnbiaoSafetyTrainingDetailService;
import org.springblade.anbiao.jiaoyupeixun.service.IAnbiaoSafetyTrainingService;
import org.springblade.anbiao.jiaoyupeixun.vo.AnbiaoSafetyTrainingVO;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 安全生产培训(线下) 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/anbiao/safetytrain")
@AllArgsConstructor
@Api(value = "安全生产培训", tags = "安全生产培训")
public class AnbiaoSafetyTrainingController {

	private IAnbiaoSafetyTrainingService service;

	private IAnbiaoSafetyTrainingDetailService detailService;

	private IFileUploadClient fileUploadClient;


	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("安全生产培训-新增（线下）")
	@ApiOperation(value = "安全生产培训-新增（线下）", notes = "传入AnbiaoSafetyTraining", position = 1)
	public R insert(@RequestBody AnbiaoSafetyTraining train, BladeUser user) {
		R r = new R();
		QueryWrapper<AnbiaoSafetyTraining> trainQueryWrapper = new QueryWrapper<AnbiaoSafetyTraining>();
		trainQueryWrapper.lambda().eq(AnbiaoSafetyTraining::getAstDeptIds, train.getAstDeptIds());
		trainQueryWrapper.lambda().eq(AnbiaoSafetyTraining::getAstTrainingCategory, train.getAstTrainingCategory());
		trainQueryWrapper.lambda().eq(AnbiaoSafetyTraining::getAstTrainingEndTime, train.getAstTrainingEndTime());
		trainQueryWrapper.lambda().eq(AnbiaoSafetyTraining::getAstDelete, "0");
		trainQueryWrapper.lambda().eq(AnbiaoSafetyTraining::getAstTrainingForm, "1");
		AnbiaoSafetyTraining deail = service.getBaseMapper().selectOne(trainQueryWrapper);
		if(deail == null) {
			if (user != null) {
				train.setAstCreateByName(user.getUserName());
				train.setAstDelete("0");
				train.setAstCreateByIds(user.getUserId().toString());
			}
			train.setAstCreateTime(DateUtil.now());
			train.setAstTrainingForm("1");
			train.setAstDelete("0");
			boolean i = service.save(train);
			if(i){
				deail = service.getBaseMapper().selectOne(trainQueryWrapper);
				if(deail != null) {
					List<AnbiaoSafetyTrainingDetail> safetyTrainingDetailList = train.getSafetyTrainingDetailList();
					AnbiaoSafetyTraining finalDeail = deail;
					safetyTrainingDetailList.forEach(item-> {
						AnbiaoSafetyTrainingDetail safetyTrainingDetail = new AnbiaoSafetyTrainingDetail();

						QueryWrapper<AnbiaoSafetyTrainingDetail> trainingDetailQueryWrapper = new QueryWrapper<AnbiaoSafetyTrainingDetail>();
						trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAadAstIds, finalDeail.getAstIds());
						trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAadApIds, item.getAadApIds());
						trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAddApBeingJoined, "0");
						AnbiaoSafetyTrainingDetail trainingDetail = detailService.getBaseMapper().selectOne(trainingDetailQueryWrapper);
						if(trainingDetail == null) {
							safetyTrainingDetail.setAddTime(DateUtil.now());
							safetyTrainingDetail.setAadAstIds(finalDeail.getAstIds());
							safetyTrainingDetail.setAadApIds(item.getAadApIds());
							safetyTrainingDetail.setAadApName(item.getAadApName());
							safetyTrainingDetail.setAadApType(item.getAadApType());
							safetyTrainingDetail.setAddApBeingJoined("0");
							boolean is = detailService.save(safetyTrainingDetail);
							if(is){
								r.setMsg("新增成功");
								r.setCode(200);
								r.setSuccess(true);
							}else{
								r.setMsg("新增失败");
								r.setCode(500);
								r.setSuccess(false);
							}
						}
					});
				}
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
		return r;
	}

	/**
	 * 编辑
	 */
	@PostMapping("/update")
	@ApiLog("安全生产培训-编辑（线下）")
	@ApiOperation(value = "安全生产培训-编辑（线下）", notes = "传入AnbiaoSafetyTraining", position = 2)
	public R update(@RequestBody AnbiaoSafetyTraining train, BladeUser user) {
		R r = new R();
		if(user != null) {
			train.setAstUpdateByName(user.getUserName());
			train.setAstUpdateByIds(user.getUserId().toString());
		}
		train.setAstUpdateTime(DateUtil.now());
		train.setAstIds(train.getAstIds());
		boolean i = service.updateById(train);
		if(i){
			detailService.deleteByAadAstIds(train.getAstIds());
			List<AnbiaoSafetyTrainingDetail> safetyTrainingDetailList = train.getSafetyTrainingDetailList();
			safetyTrainingDetailList.forEach(item-> {
				AnbiaoSafetyTrainingDetail safetyTrainingDetail = new AnbiaoSafetyTrainingDetail();

				QueryWrapper<AnbiaoSafetyTrainingDetail> trainingDetailQueryWrapper = new QueryWrapper<AnbiaoSafetyTrainingDetail>();
				trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAadAstIds, train.getAstIds());
				trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAadApIds, item.getAadApIds());
				trainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAddApBeingJoined, "0");
				AnbiaoSafetyTrainingDetail trainingDetail = detailService.getBaseMapper().selectOne(trainingDetailQueryWrapper);
				if(trainingDetail == null) {
					safetyTrainingDetail.setAddTime(DateUtil.now());
					safetyTrainingDetail.setAadAstIds(train.getAstIds());
					safetyTrainingDetail.setAadApIds(item.getAadApIds());
					safetyTrainingDetail.setAadApName(item.getAadApName());
					safetyTrainingDetail.setAadApType(item.getAadApType());
					safetyTrainingDetail.setAddTime(item.getAddTime());
					safetyTrainingDetail.setAddApBeingJoined("0");
					boolean is = detailService.save(safetyTrainingDetail);
					if(is){
						r.setMsg("新增成功");
						r.setCode(200);
						r.setSuccess(true);
					}else{
						r.setMsg("新增失败");
						r.setCode(500);
						r.setSuccess(false);
					}
				}
			});
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

	@PostMapping("/getSafetyTrainingPage")
	@ApiLog("安全生产培训-分页列表（线下）")
	@ApiOperation(value = "安全生产培训-分页列表（线下）", notes = "传入AnbiaoSafetyTrainingPage", position = 3)
	public R<AnbiaoSafetyTrainingPage<AnbiaoSafetyTrainingVO>> getSafetyTrainingPage(@RequestBody AnbiaoSafetyTrainingPage safetyTrainingPage, BladeUser user) {
		R rs = new R();
		AnbiaoSafetyTrainingPage<AnbiaoSafetyTrainingVO> list= service.selectPage(safetyTrainingPage);
		return R.data(list);
	}

	@GetMapping("/remove")
	@ApiOperation(value = "安全生产培训-删除", notes = "传入Id", position = 4)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
	})
	public R remove(String Id,BladeUser user) {
		R r = new R();
		AnbiaoSafetyTraining safetyTraining = new AnbiaoSafetyTraining();
		if(user != null) {
			safetyTraining.setAstUpdateByName(user.getUserName());
			safetyTraining.setAstUpdateByIds(user.getUserId().toString());
		}
		safetyTraining.setAstUpdateTime(DateUtil.now());
		safetyTraining.setAstDelete("1");
		safetyTraining.setAstIds(Id);
		boolean i = service.updateById(safetyTraining);
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
	@ApiOperation(value = "安全生产培训-根据ID获取详情", notes = "传入Id、jsyId", position = 5)
	@ApiImplicitParams({ @ApiImplicitParam(name = "Id", value = "数据Id", required = true),
		@ApiImplicitParam(name = "jsyId", value = "驾驶员Id")})
	public R getById(String Id,String jsyId) {
		AnbiaoSafetyTraining deail = service.getById(Id);
		if(deail != null) {
			//现场照片(以英文分号分割)
			if (StrUtil.isNotEmpty(deail.getAstSitePhotos()) && deail.getAstSitePhotos().contains("http") == false) {
				deail.setAstSitePhotos(fileUploadClient.getUrl(deail.getAstSitePhotos()));
			}
			//附件(以英文分号分割)
			if (StrUtil.isNotEmpty(deail.getAstEnclosure()) && deail.getAstEnclosure().contains("http") == false) {
				deail.setAstEnclosure(fileUploadClient.getUrl(deail.getAstEnclosure()));
			}

			//获取参加培训人员详情
			QueryWrapper<AnbiaoSafetyTrainingDetail> queryWrapper = new QueryWrapper<AnbiaoSafetyTrainingDetail>();
			queryWrapper.lambda().like(StringUtils.isNotEmpty(deail.getAstIds()), AnbiaoSafetyTrainingDetail::getAadAstIds, deail.getAstIds());
			//司机端只能看到自己的详情
			if(StringUtils.isNotEmpty(jsyId)){
				queryWrapper.lambda().like(StringUtils.isNotEmpty(jsyId), AnbiaoSafetyTrainingDetail::getAadApIds, jsyId);
			}
			List<AnbiaoSafetyTrainingDetail> safetyTrainingDetail = detailService.getBaseMapper().selectList(queryWrapper);
			if(safetyTrainingDetail != null) {
				safetyTrainingDetail.forEach(item->{
					//参培人头像
					if (StrUtil.isNotEmpty(item.getAddApHeadPortrait()) && item.getAddApHeadPortrait().contains("http") == false) {
						item.setAddApHeadPortrait(fileUploadClient.getUrl(item.getAddApHeadPortrait()));
					}
					//参培人签名
					if (StrUtil.isNotEmpty(item.getAddApAutograph()) && item.getAddApAutograph().contains("http") == false) {
						item.setAddApAutograph(fileUploadClient.getUrl(item.getAddApAutograph()));
					}
				});
				deail.setSafetyTrainingDetailList(safetyTrainingDetail);
			}
		}
		return R.data(deail);
	}

	/**
	 * 签到
	 */
	@PostMapping("/signIn")
	@ApiLog("签到-安全生产培训")
	@ApiOperation(value = "签到-安全生产培训",notes = "传入AnbiaoSafetyTrainingDetail")
	public R SignIn(@RequestBody AnbiaoSafetyTrainingDetail safetyTrainingDetail, BladeUser user){
		R r = new R();
		QueryWrapper<AnbiaoSafetyTrainingDetail> safetyTrainingDetailQueryWrapper = new QueryWrapper<>();
		safetyTrainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAadAstIds,safetyTrainingDetail.getAadAstIds());
		safetyTrainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAadApIds,safetyTrainingDetail.getAadApIds());
		safetyTrainingDetailQueryWrapper.lambda().eq(AnbiaoSafetyTrainingDetail::getAddApBeingJoined,"0");
		AnbiaoSafetyTrainingDetail detail = detailService.getBaseMapper().selectOne(safetyTrainingDetailQueryWrapper);
		if (detail != null){
			safetyTrainingDetail.setAddTime(DateUtil.now());
			safetyTrainingDetail.setAddApBeingJoined("1");
			return R.status(detailService.updateById(safetyTrainingDetail));
		}else{
			r.setCode(500);
			r.setMsg("暂无数据");
			return r;
		}
	}

}
