package org.springblade.anbiao.SafeInvestment.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 明细返回
 * @author long
 * @create 2022-10-28-11:45
 */
@Data
public class SafetyInvestmentDetailsVO {


	private String asidIds;

	private String asidAsiIds;

	private String asidEntryName;

	private String asidHandledByIds;

	private String asidHandledByName;

	private String asidInvestmentScope;
	@DateTimeFormat(pattern="yyyy")
	@JsonFormat(pattern = "yyyy",timezone = "GMT+8")
	private Date asidInvestmentDare;

	private String 	asidAmountUsed;

	private String asidDelete;
}
