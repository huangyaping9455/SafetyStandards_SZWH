package org.springblade.anbiao.dianziwendang.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.dianziwendang.entity.AnbiaoElectronicFileDeail;
import org.springblade.anbiao.dianziwendang.service.IAnbiaoElectronicFileDeailService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 电子文档签名记录表 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-11-03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/electronicFileDeail")
@Api(value = "电子文档签名信息",tags = "电子文档签名信息")
public class AnbiaoElectronicFileDeailController {

	private IAnbiaoElectronicFileDeailService service;

	private IFileUploadClient fileUploadClient;

	@PostMapping("/insert")
	@ApiLog(value = "电子文档签名信息-新增、编辑")
	@ApiOperation(value = "电子文档签名信息-新增、编辑",notes = "传入AnbiaoElectronicFileDeail",position = 1)
	public R insert(@RequestBody AnbiaoElectronicFileDeail fileDeail, BladeUser user){
		R r = new R();
		QueryWrapper<AnbiaoElectronicFileDeail> fileDeailQueryWrapper = new QueryWrapper<AnbiaoElectronicFileDeail>();
		fileDeailQueryWrapper.lambda().eq(AnbiaoElectronicFileDeail::getAadAefIds,fileDeail.getAadAefIds());
		fileDeailQueryWrapper.lambda().eq(AnbiaoElectronicFileDeail::getAadApIds,fileDeail.getAadApIds());
		fileDeailQueryWrapper.lambda().eq(AnbiaoElectronicFileDeail::getAadApType,fileDeail.getAadApType());
		fileDeailQueryWrapper.lambda().eq(AnbiaoElectronicFileDeail::getAddTime,fileDeail.getAddTime());
		AnbiaoElectronicFileDeail deail = service.getBaseMapper().selectOne(fileDeailQueryWrapper);
		if (deail == null){
			boolean i = service.save(fileDeail);
			if (i){
				r.setMsg("添加成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}else{
				r.setMsg("添加失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}else{
			fileDeail.setAadAefIds(deail.getAadAefIds());
			boolean i = service.updateById(fileDeail);
			if (i){
				r.setMsg("修改成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}else{
				r.setMsg("修改失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}
	}

	@GetMapping("/getById")
	@ApiLog(value = "电子文档信息管理-根据ID获取详情")
	@ApiOperation(value = "电子文档信息管理-根据ID获取详情",notes = "传入文档aefId",position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "aefId", value = "文档数据Id", required = true),
		@ApiImplicitParam(name = "jsyId", value = "驾驶员Id")})
	public R getById(String aefId,String jsyId){
		R r = new R();
		QueryWrapper<AnbiaoElectronicFileDeail> fileDeailQueryWrapper = new QueryWrapper<AnbiaoElectronicFileDeail>();
		fileDeailQueryWrapper.lambda().eq(AnbiaoElectronicFileDeail::getAadAefIds, aefId);
		if(StringUtils.isNotEmpty(jsyId)){
			fileDeailQueryWrapper.lambda().eq(AnbiaoElectronicFileDeail::getAadApIds, jsyId);
		}
		List<AnbiaoElectronicFileDeail> fileDeailList = service.getBaseMapper().selectList(fileDeailQueryWrapper);
		if(fileDeailList != null){
			fileDeailList.forEach(item-> {
				//附件
				if (StrUtil.isNotEmpty(item.getAddApAutograph()) && item.getAddApAutograph().contains("http") == false) {
					item.setAddApAutograph(fileUploadClient.getUrl(item.getAddApAutograph()));
				}
			});
			r.setSuccess(true);
			r.setCode(200);
			r.setData(fileDeailList);
			r.setMsg("获取成功");
		}else{
			r.setSuccess(false);
			r.setCode(500);
			r.setMsg("暂无数据");
		}
		return r;
	}

}
