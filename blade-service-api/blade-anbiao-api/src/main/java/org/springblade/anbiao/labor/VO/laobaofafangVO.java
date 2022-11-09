package org.springblade.anbiao.labor.VO;

import lombok.Data;

import java.util.Date;

/**
 * @Description : 企管发放
 * @Author : long
 * @Date :2022/11/4 11:02
 */
@Data
public class laobaofafangVO {
	private String ali_name;
	private Date ali_issue_date;
	private Integer ali_issue_quantity;
	private Integer ali_issue_people_number;
	private String ali_application_scope;
}
