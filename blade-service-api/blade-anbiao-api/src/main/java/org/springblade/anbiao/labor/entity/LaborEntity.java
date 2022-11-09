package org.springblade.anbiao.labor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/3 22:09
 */
@Data
@TableName("anbiao_labor_insurance")
public class LaborEntity {
	@TableId(value = "asi_ids",type = IdType.UUID)
	private String 	ali_ids;
	private Integer ali_dept_ids;
	private String ali_name;
	private Date ali_issue_date;
	private Integer ali_issue_quantity;
	private Integer ali_collected_amount;
	private Integer ali_collected_quantity;
	private Integer ali_issue_people_number;
	private Date ali_valid_from;
	private Date ali_expiry_date;
	private String ali_status;
	private String ali_application_scope;
	private String ali_delete;
	private Date ali_create_time;
	private String ali_create_by_ids;
	private String ali_create_by_name;
	private Date ali_update_time;
	private String ali_update_by_ids;
	private String ali_update_by_name;
}
