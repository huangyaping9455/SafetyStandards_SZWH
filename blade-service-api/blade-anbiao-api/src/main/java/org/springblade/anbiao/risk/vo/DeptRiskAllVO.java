package org.springblade.anbiao.risk.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "DeptRiskAllVO", description = "DeptRiskAllVO对象")
public class DeptRiskAllVO implements Serializable {

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "驾驶员风险数")
	private Integer jsyrisknum = 0;

	@ApiModelProperty(value = "车辆风险数")
	private Integer clrisknum = 0;
}
