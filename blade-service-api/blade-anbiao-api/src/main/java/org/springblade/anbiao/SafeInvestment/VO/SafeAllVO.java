package org.springblade.anbiao.SafeInvestment.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/10 14:34
 */
@Data
public class SafeAllVO {
	private List<SafetyInvestmentDetailsVO> safetyInvestmentDetailsVOS;

	private String asiIds;
	private String asiDeptIds;

	private String asiYear;
	private BigDecimal asiExtractionProportion;
	private BigDecimal asiWithdrawalAmount;
	private BigDecimal asiAccruedAmount;
	private BigDecimal asiAmountUsed;
	private BigDecimal asiLastYearsTurnover;
	private BigDecimal asiRemainingAmount;
	private char asiDelete;
}
