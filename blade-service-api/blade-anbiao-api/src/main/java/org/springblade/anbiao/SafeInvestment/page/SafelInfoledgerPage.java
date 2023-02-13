package org.springblade.anbiao.SafeInvestment.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SafelInfoledgerPage对象", description = "SafelInfoledgerPage对象")
public class SafelInfoledgerPage<T> extends BasePage<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "年度")
	private String asiYear;

	@ApiModelProperty(value = "上年度营业收入")
	private BigDecimal asiLastYearsTurnover;

	@ApiModelProperty(value = "提取比例")
	private BigDecimal asiExtractionProportion;

	@ApiModelProperty(value = "最少提取金额")
	private BigDecimal asiWithdrawalAmount;

	@ApiModelProperty(value = "计提金额")
	private BigDecimal asiAccruedAmount;

	@ApiModelProperty(value = "使用金额")
	private BigDecimal asiAmountUsed;

	@ApiModelProperty(value = "剩余金额")
	private BigDecimal asiRemainingAmount;

	@ApiModelProperty(value = "单位id",required = true)
	private Integer asiDeptIds;

	@ApiModelProperty(value = "项目名称")
	private String asidEntryName;

	@ApiModelProperty(value = "经办人")
	private String asidHandledByName;

	@ApiModelProperty(value = "投入范围")
	private String asidInvestmentScope;

	@ApiModelProperty(value = "使用时间")
	private String asidInvestmentDare;

	@ApiModelProperty(value = "使用金额")
	private String asidAmountUsed;

}
