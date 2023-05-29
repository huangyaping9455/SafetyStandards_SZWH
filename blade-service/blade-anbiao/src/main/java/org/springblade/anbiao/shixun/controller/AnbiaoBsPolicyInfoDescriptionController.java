package org.springblade.anbiao.shixun.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.shixun.entity.AnbiaoBsPolicyInfo;
import org.springblade.anbiao.shixun.entity.AnbiaoBsPolicyInfoDescription;
import org.springblade.anbiao.shixun.page.BsPolicyInfoPage;
import org.springblade.anbiao.shixun.service.IAnbiaoBsPolicyInfoDescriptionService;
import org.springblade.anbiao.shixun.service.IAnbiaoBsPolicyInfoService;
import org.springblade.common.tool.DateUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2023-05-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/bsPolicyInfoDescription")
@Api(value = "最新时讯说明", tags = "最新时讯说明")
public class AnbiaoBsPolicyInfoDescriptionController {

	private IAnbiaoBsPolicyInfoDescriptionService bsPolicyInfoDescriptionService;
	private IFileUploadClient fileUploadClient;

	@PostMapping(value = "/insert")
	@ApiLog("最新时讯说明-签名")
	@ApiOperation(value = "最新时讯说明-签名", notes = "传入anbiaoBsPolicyInfoDescription", position = 1)
	public R insert(@RequestBody AnbiaoBsPolicyInfoDescription anbiaoBsPolicyInfoDescription) {

		R r = new R();

		anbiaoBsPolicyInfoDescription.setAbpidTime(DateUtil.now().substring(0, 10));

		boolean save = bsPolicyInfoDescriptionService.save(anbiaoBsPolicyInfoDescription);
		if (save) {
			r.setCode(200);
			r.setMsg("签到成功");
			r.setSuccess(true);
			r.setData(anbiaoBsPolicyInfoDescription);
			return r;
		} else {
			r.setCode(500);
			r.setMsg("签到失败");
			r.setSuccess(false);
			r.setData(anbiaoBsPolicyInfoDescription);
			return r;
		}

	}

	@PostMapping(value = "/detail")
	@ApiLog("最新时讯说明-查询")
	@ApiOperation(value = "最新时讯说明-查询", notes = "传入anbiaoBsPolicyInfoDescription", position = 1)
	public R detail(@RequestBody AnbiaoBsPolicyInfoDescription anbiaoBsPolicyInfoDescription) {

		R r = new R();

		QueryWrapper<AnbiaoBsPolicyInfoDescription> anbiaoBsPolicyInfoDescriptionQueryWrapper = new QueryWrapper<>();
		anbiaoBsPolicyInfoDescriptionQueryWrapper.lambda().eq(AnbiaoBsPolicyInfoDescription::getAbpiId, anbiaoBsPolicyInfoDescription.getAbpiId());
		anbiaoBsPolicyInfoDescriptionQueryWrapper.lambda().eq(AnbiaoBsPolicyInfoDescription::getAbpidIds, anbiaoBsPolicyInfoDescription.getAbpidIds());
		AnbiaoBsPolicyInfoDescription anbiaoBsPolicyInfoDescription1 = bsPolicyInfoDescriptionService.getBaseMapper().selectOne(anbiaoBsPolicyInfoDescriptionQueryWrapper);

		if(anbiaoBsPolicyInfoDescription1!=null){
			//签名
			if (StrUtil.isNotEmpty(anbiaoBsPolicyInfoDescription1.getAbpidAutograph()) && anbiaoBsPolicyInfoDescription1.getAbpidAutograph().contains("http") == false) {
				anbiaoBsPolicyInfoDescription1.setAbpidAutograph(fileUploadClient.getUrl(anbiaoBsPolicyInfoDescription1.getAbpidAutograph()));
			}
			r.setCode(200);
			r.setMsg("数据查询成功");
			r.setSuccess(true);
			r.setData(anbiaoBsPolicyInfoDescription1);
			return r;
		}else {
			r.setCode(200);
			r.setMsg("数据不存在");
			r.setSuccess(true);
			r.setData(anbiaoBsPolicyInfoDescription);
			return r;
		}


	}

}


