package org.springblade.anbiao.fullrate.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.fullrate.entity.AnbiaoDeptFullRate;
import org.springblade.anbiao.fullrate.page.DeptFullRatePage;
import org.springblade.anbiao.fullrate.service.IAnbiaoDeptFullRateService;
import org.springblade.anbiao.zhengfu.entity.Organization;
import org.springblade.anbiao.zhengfu.service.IOrganizationService;
import org.springblade.core.log.annotation.ApiLog;
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
 * @author hyp
 * @since 2023-05-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/deptFullRate")
@Api(value = "行管端-档案信息完整率", tags = "行管端-档案信息完整率")
public class AnbiaoDeptFullRateController {

	private IOrganizationService iOrganizationService;

	private IAnbiaoDeptFullRateService deptFullRateService;

	@PostMapping(value = "/getDeptFullRateTJ")
	@ApiLog("档案信息完整率列表")
	@ApiOperation(value = "档案信息完整率列表列表", notes = "传入DeptFullRatePage",position = 1)
	public R<DeptFullRatePage<AnbiaoDeptFullRate>> getDeptFullRateTJ(@RequestBody DeptFullRatePage DeptFullRatePage) {
		Organization jb = iOrganizationService.selectGetZFJB(DeptFullRatePage.getDeptId());
		if(!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())){
			DeptFullRatePage.setProvince(DeptFullRatePage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
			DeptFullRatePage.setCity(DeptFullRatePage.getDeptId());
		}

		if(!StringUtils.isBlank(jb.getCountry())) {
			DeptFullRatePage.setCountry(DeptFullRatePage.getDeptId());
		}
		DeptFullRatePage<AnbiaoDeptFullRate> pages = deptFullRateService.selectDayTJ(DeptFullRatePage);
		return R.data(pages);
	}

}
