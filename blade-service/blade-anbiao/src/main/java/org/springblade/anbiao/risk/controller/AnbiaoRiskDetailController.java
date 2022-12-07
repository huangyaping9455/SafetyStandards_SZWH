package org.springblade.anbiao.risk.controller;


import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 安标风险统计信息 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-12-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/risk")
@Api(value = "风险统计信息", tags = "风险统计信息")
public class AnbiaoRiskDetailController {

	private IAnbiaoRiskDetailService riskDetailService;



}
