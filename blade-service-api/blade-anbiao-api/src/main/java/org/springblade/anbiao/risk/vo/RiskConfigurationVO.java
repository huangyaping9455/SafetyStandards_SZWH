package org.springblade.anbiao.risk.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RiskConfigurationVO", description = "RiskConfigurationVO对象")
public class RiskConfigurationVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private String id;

	@ApiModelProperty(value = "预警项")
	private String yujingxiang;

	@ApiModelProperty(value = "说明")
	private String shuoming;

	@ApiModelProperty(value = "预警类型")
	private Integer yujingleixing;
}
