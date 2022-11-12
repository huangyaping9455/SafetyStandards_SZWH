package org.springblade.anbiao.SafeInvestment.VO;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.protostuff.Exclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 列表返回
 * @author long
 * @create 2022-10-28-11:37
 */
@Data
public class SafeInvestmentVO {

	private String asiIds;

	private String asiDeptIds;

	private String deptName;

	private Integer asiYear;

	private BigDecimal asiLastYearsTurnover;

	private BigDecimal asiExtractionProportion;

	private BigDecimal asiWithdrawalAmount;

	private BigDecimal asiAccruedAmount;

	private BigDecimal asiAmountUsed;

	private BigDecimal asiRemainingAmount;

	private char asiDelete;

	private Date asiCreateTime;

	private String asiCreateByIds;

	private String 	asiCreateByName;

	private Date asiUpdateTime;

	private String asiUpdateByIds;

	private String asiUpdateByName;

}
