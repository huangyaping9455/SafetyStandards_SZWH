package org.springblade.anbiao.risk.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "JiashiyuanRiskAllPage", description = "JiashiyuanRiskAllPage")
public class JiashiyuanRiskAllPage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "风险标题")
	private String ardTitle;

	@ApiModelProperty(value = "风险内容")
	private String ardContent;

	@ApiModelProperty(value = "发现日期")
	private String ardDiscoveryDate;

	@ApiModelProperty(value = "关联值")
	private String ardAssociationValue;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;
}
