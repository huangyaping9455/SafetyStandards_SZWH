package org.springblade.anbiao.SafeInvestment.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

import java.math.BigDecimal;

@Data
@ApiModel(value = "SafelInfoledgerVO", description = "SafelInfoledgerVO对象")
public class SafelInfoledgerVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private String asiIds;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

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
	@DateTimeFormat(pattern="yyyy")
	@JsonFormat(pattern = "yyyy",timezone = "GMT+8")
	private Date asidInvestmentDare;

	@ApiModelProperty(value = "使用金额")
	private String asidAmountUsed;

	@ApiModelProperty(value = "第几页", required = true)
	private Integer current;

	@ApiModelProperty(value = "每页显示数", required = true)
	private Integer size;

	@ApiModelProperty(value = "序号")
	private Integer serialNumber;
}
