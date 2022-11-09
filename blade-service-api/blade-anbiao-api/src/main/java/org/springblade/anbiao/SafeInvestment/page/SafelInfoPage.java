package org.springblade.anbiao.SafeInvestment.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/8 17:24
 */
@Data
@ApiModel(value = "SsafelPage对象", description = "SsafelPage对象")
public class SafelInfoPage {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "年度")
	private String asi_year;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "提取比例")
	private BigDecimal asi_extraction_proportion;

	@ApiModelProperty(value = "计提金额")
	private BigDecimal asi_accrued_amount;

	@ApiModelProperty(value = "使用金额")
	private BigDecimal asi_amount_used;

	@ApiModelProperty(value = "剩余金额")
	private BigDecimal asi_remaining_amount;

	@ApiModelProperty(value = "提取金额")
	private BigDecimal asi_withdrawal_amount;

	private Integer current;

	private Integer size;
}
