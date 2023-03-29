/**
 * Copyright (C), 2015-2020,
 * FileName: GpsVehicleController
 * Author:   呵呵哒
 * Date:     2020/7/3 9:42
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.qiyeshouye.entity.ZQHT;
import org.springblade.anbiao.qiyeshouye.page.ZQHTPage;
import org.springblade.anbiao.qiyeshouye.service.IZQHTService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/8/30
 * @描述
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/zQHT")
@Api(value = "企业平台-政企互通", tags = "企业平台-政企互通")
public class ZQHTController {

	private IZQHTService izqhtService;

	@PostMapping(value = "/getTJAll")
	@ApiLog("企业平台-政企互通-统计列表")
	@ApiOperation(value = "企业平台-政企互通-统计列表", notes = "传入zqhtPage",position = 1)
	public R<ZQHTPage<ZQHT>> getTJAll(@RequestBody ZQHTPage zqhtPage) {
		//排序条件
		////默认发起时间降序
		if(zqhtPage.getOrderColumns()==null){
			zqhtPage.setOrderColumn("faqishijian");
		}else{
			zqhtPage.setOrderColumn(zqhtPage.getOrderColumns());
		}
		ZQHTPage<ZQHT> pages = izqhtService.selectGetTJ(zqhtPage);
		return R.data(pages);
	}





}

