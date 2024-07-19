package org.springblade.anbiao.labor.VO;

import lombok.Data;

import java.util.Date;

/**
 * @Description : 劳保
 * @Author : long
 * @Date :2024/07/03 10:46
 */
@Data
public class LaborMonthVO {

	private String aliIds;
	private String aliName;
	private String aliIssueDate;
	private String deptname;
	private String aliDeptIds;
	private String aadApIds;
	private String aadApName;
	private Integer num = 0;

}
