package org.springblade.anbiao.risk.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="LedgerDetailVO对象", description="LedgerDetailVO对象")
public class LedgerDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业id")
	private Integer deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "风险内容")
	private String ardContent;

	@ApiModelProperty(value = "风险数量")
	private String number;
}
