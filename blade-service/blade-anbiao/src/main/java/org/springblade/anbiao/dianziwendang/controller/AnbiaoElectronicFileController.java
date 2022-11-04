package org.springblade.anbiao.dianziwendang.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.dianziwendang.entity.AnbiaoElectronicFile;
import org.springblade.anbiao.dianziwendang.service.IAnbiaoElectronicFileService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 电子文档表 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-11-03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/electronicFile")
@Api(value = "电子文档信息管理",tags = "电子文档信息管理")
public class AnbiaoElectronicFileController extends BladeController {

	private IAnbiaoElectronicFileService service;

	private IFileUploadClient fileUploadClient;

	@PostMapping("/insert")
	@ApiLog(value = "电子文档信息管理-新增、编辑")
	@ApiOperation(value = "电子文档信息管理-新增、编辑",notes = "传入AnbiaoElectronicFile",position = 1)
	public R insert(@RequestBody AnbiaoElectronicFile file, BladeUser user){
		R r = new R();
		QueryWrapper<AnbiaoElectronicFile> fileQueryWrapper = new QueryWrapper<AnbiaoElectronicFile>();
		fileQueryWrapper.lambda().eq(AnbiaoElectronicFile::getAefDelete,"0");
		fileQueryWrapper.lambda().eq(AnbiaoElectronicFile::getAefName,file.getAefName());
		fileQueryWrapper.lambda().eq(AnbiaoElectronicFile::getAefType,file.getAefType());
		fileQueryWrapper.lambda().eq(AnbiaoElectronicFile::getAefPersonType,file.getAefPersonType());
		fileQueryWrapper.lambda().eq(AnbiaoElectronicFile::getAefDeptId,file.getAefDeptId());
		AnbiaoElectronicFile electronicFile = service.getBaseMapper().selectOne(fileQueryWrapper);
		if (electronicFile == null){
			file.setAefDelete(0);
			if(user != null){
				file.setAefCreateByIds(user.getUserId().toString());
				file.setAefCreateByName(user.getUserName());
			}
			file.setAefCreateTime(DateUtil.now());
			boolean i = service.save(file);
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
			file.setAefId(electronicFile.getAefId());
			if (user != null){
				file.setAefUpdateByIds(user.getUserId().toString());
				file.setAefUpdateByName(user.getUserName());
			}
			file.setAefUpdateTime(DateUtil.now());
			boolean i = service.updateById(file);
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
	@ApiOperation(value = "电子文档信息管理-根据ID获取详情",notes = "传入数据Id",position = 2)
	public R getById(String Id){
		R r = new R();
		AnbiaoElectronicFile deail = service.getById(Id);
		if(deail != null){
			//附件
			if (StrUtil.isNotEmpty(deail.getAefImg()) && deail.getAefImg().contains("http") == false) {
				deail.setAefImg(fileUploadClient.getUrl(deail.getAefImg()));
			}
			r.setSuccess(true);
			r.setCode(200);
			r.setData(deail);
			r.setMsg("获取成功");
		}else{
			r.setSuccess(false);
			r.setCode(500);
			r.setMsg("暂无数据");
		}
		return r;
	}


}
