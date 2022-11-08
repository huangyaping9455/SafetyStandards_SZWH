package org.springblade.anbiao.SafeInvestment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description : 实体类
 * @Author : long
 * @Date :2022/11/1 16:16
 */
@Data
@TableName("anbiao_safety_input")
public class AnbiaoSafetyInput implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(value = "asi_ids",type = IdType.UUID)
	private String asi_ids;

	private String asi_dept_ids;

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
