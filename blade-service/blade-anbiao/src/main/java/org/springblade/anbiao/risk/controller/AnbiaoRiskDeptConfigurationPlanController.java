package org.springblade.anbiao.risk.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.risk.entity.AnbiaoRiskConfiguration;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDeptConfigurationPlan;
import org.springblade.anbiao.risk.service.IAnbiaoRiskConfigurationService;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDeptConfigurationPlanService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-06-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/riskConfigurationPlan")
@Api(value = "风险信息推送设置", tags = "风险信息推送设置")
public class AnbiaoRiskDeptConfigurationPlanController {



}
