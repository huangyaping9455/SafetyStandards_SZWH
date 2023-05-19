/**
 * Copyright (C), 2015-2020,
 * FileName: AnbiaoIssueAbarbeitungPage
 * Description:
 */
package org.springblade.anbiao.issueAbarbeitung.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AnbiaoIssueAbarbeitungPage对象", description = "AnbiaoIssueAbarbeitungPage对象")
public class AnbiaoIssueAbarbeitungPage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "运管Id", required = true)
	private String deptId;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "省")
	private String province;

	@ApiModelProperty(value = "市")
	private String city;

	@ApiModelProperty(value = "县")
	private String country;

	@ApiModelProperty(value = "状态（0：待处理，1：待审核，2：审核通过，3：审核未通过）")
	private Integer status;

	@ApiModelProperty(value = "状态（0：未读，1：已读）")
	private Integer isRead;

	@ApiModelProperty(value = "限期整改时间")
	private String date;

	@ApiModelProperty(value = "限期整改开始时间")
	private String beginDate;

	@ApiModelProperty(value = "限期整改结束时间")
	private String endDate;

	@ApiModelProperty(value = "通知开始时间")
	private String createBeginTime;

	@ApiModelProperty(value = "通知结束时间")
	private String createEndTime;

	@ApiModelProperty(value = "下发整改ID")
	private String issueId;
}
