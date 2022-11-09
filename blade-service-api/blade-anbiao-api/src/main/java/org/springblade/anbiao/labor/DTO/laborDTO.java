package org.springblade.anbiao.labor.DTO;

import lombok.Data;

import java.util.Date;

/**
 * @Description : 劳保
 * @Author : long
 * @Date :2022/11/4 10:53
 */
@Data
public class laborDTO {
	private String ali_dept_ids;
	private String ali_name;
	private Date ali_issue_date;
	private Integer ali_issue_quantity;
	private Integer ali_issue_people_number;
	private String ali_status;
	private String ali_application_scope;
}
