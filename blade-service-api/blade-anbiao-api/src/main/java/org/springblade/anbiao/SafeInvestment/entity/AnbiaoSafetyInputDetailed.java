package org.springblade.anbiao.SafeInvestment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/1 16:19
 */
@Data
@TableName("anbiao_safety_input_detailed")
public class AnbiaoSafetyInputDetailed implements Serializable {
	private static final long serialVersionUID = 1L;

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
