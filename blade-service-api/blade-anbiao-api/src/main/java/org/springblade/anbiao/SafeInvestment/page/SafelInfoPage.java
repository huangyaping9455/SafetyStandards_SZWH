package org.springblade.anbiao.SafeInvestment.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/8 17:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SsafelPage对象", description = "SsafelPage对象")
public class SafelInfoPage extends BasePage {

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

	@ApiModelProperty(value = "开始时间")
	private String asiYear;


	@ApiModelProperty(value = "单位id",required = true)
	private Integer deptId;
}
