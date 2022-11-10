package org.springblade.anbiao.SafeInvestment.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date endTime;

	private String asi_dept_ids;
	private Integer asi_year;
	private String asi_accrued_amount;
	private String asi_withdrawal_amount;
	private String asi_extraction_proportion;
	private String asi_amount_used;
	private String asi_remaining_amount;
	private String deptId;

	private List<AnbiaoSafetyInputDetailed> inputDetailedList;

}
