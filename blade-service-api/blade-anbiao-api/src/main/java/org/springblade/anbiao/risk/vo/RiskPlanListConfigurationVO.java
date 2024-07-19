package org.springblade.anbiao.risk.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RiskPlanListConfigurationVO", description = "RiskPlanListConfigurationVO对象")
public class RiskPlanListConfigurationVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "风险配置表id")
	private String rcId;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "风险推送频率；1：按日，2：按月")
	private Integer type;

	@ApiModelProperty(value = "小时，每日几点")
	private String hours ;

	@ApiModelProperty(value = "日期，每月的几号（按月才有），多个以英文逗号隔开")
	private String date;

	@ApiModelProperty(value = "推送等级，1,2,3")
	private Integer level;

	private List<RiskPlanListConfigurationVO> planList;

}
