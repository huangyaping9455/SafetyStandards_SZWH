package org.springblade.anbiao.risk.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RiskDeptConfigurationListVO", description = "RiskDeptConfigurationListVO对象")
public class RiskDeptConfigurationListVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private String id;

	@ApiModelProperty(value = "单位ID")
	private String deptId;

	@ApiModelProperty(value = "单位名称")
	private String deptName;

	@ApiModelProperty(value = "风险配置主键")
	private String rcId;

	@ApiModelProperty(value = "风险状态")
	private String status;

	@ApiModelProperty(value = "预警项")
	private String yujingxiang;

	@ApiModelProperty(value = "预警说明")
	private String shuoming;
}
