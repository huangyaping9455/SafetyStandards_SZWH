package org.springblade.anbiao.SafeInvestment.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springblade.anbiao.SafeInvestment.VO.SafetyInvestmentDetailsVO;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInput;
import org.springblade.anbiao.SafeInvestment.entity.AnbiaoSafetyInputDetailed;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 传输实体
 * @author long
 * @create 2022-10-28-11:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SafeInvestmentDTO implements Serializable {


	private String asiIds;
	private Integer asiDeptIds;
	private Integer asiYear;
	private String asiAccruedAmount;
	private String asiWithdrawalAmount;
	private String asiExtractionProportion;
	private String asiAmountUsed;
	private String asiNemainingAmount;
	private String asiRemainingAmount;
	private String asiLastYearsTurnover;
	private String deptId;

	private List<SafetyInvestmentDetailsVO> safetyInvestmentDetailsVOS;

}
