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

	private String asi_ids;

	private String asi_dept_ids;

	private String dept_name;

	private Integer asi_year;

	private BigDecimal asi_last_years_turnover;

	private BigDecimal asi_extraction_proportion;

	private BigDecimal asi_withdrawal_amount;

	private BigDecimal asi_accrued_amount;

	private BigDecimal asi_amount_used;

	private BigDecimal asi_remaining_amount;

	private char asi_delete;

	private Date asi_create_time;

	private String asi_create_by_ids;

	private String 	asi_create_by_name;

	private Date asi_update_time;

	private String asi_update_by_ids;

	private String asi_update_by_name;

}
