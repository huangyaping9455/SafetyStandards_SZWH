package org.springblade.anbiao.labor.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SafelInfoledgerPage对象", description = "SafelInfoledgerPage对象")
public class laborledgerPage <T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private String aliIds;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "企业id")
	private String deptId;

	@ApiModelProperty(value = "发放日期")
	private String aliIssueDate;

	@ApiModelProperty(value = "用品名称")
	private String aliName;

	@ApiModelProperty(value = "总人数")
	private String aliIssuePeopleNumber;

	@ApiModelProperty(value = "总数量")
	private String aliIssueQuantity;

	@ApiModelProperty(value = "姓名")
	private String alrPersonName;

	@ApiModelProperty(value = "领取数量")
	private String alrReceiptsNumber;

	@ApiModelProperty(value = "领取日期")
	private String alrReceiptDate;

	@ApiModelProperty(value = "签名")
	private String alrPersonAutograph;

	private String date;

}
