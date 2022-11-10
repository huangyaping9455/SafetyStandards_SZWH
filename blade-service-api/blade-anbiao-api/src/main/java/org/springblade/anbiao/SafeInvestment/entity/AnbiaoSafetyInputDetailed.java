package org.springblade.anbiao.SafeInvestment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/1 16:19
 */
@Data
@TableName("anbiao_safety_input_detailed")
public class AnbiaoSafetyInputDetailed implements Serializable {
	private static final long serialVersionUID = 1L;

	private String asid_ids;

	private String asid_asi_ids;

	private String asid_entry_name;

	private String asid_handled_by_ids;

	private String asid_handled_by_name;

	private String asid_investment_scope;

	private String asid_investment_dare;

	private String 	asid_amount_used;

	private String asid_delete;
}
