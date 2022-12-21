package org.springblade.anbiao.labor.VO;

import lombok.Data;

import java.util.Date;

/**
 * @Description : 劳保
 * @Author : long
 * @Date :2022/11/4 10:46
 */
@Data
public class LaborVO {
	private String aliName;
	private Date aliIssueDate;
	private Integer aliIssueQuantity;
	private Integer aliIssuePeopleNumber;
	private String aliStatus;
	private String aliApplicationScope;
	private String detName;
	private String aliIds;
	private String alrPersonAutograph;
	private String alrReceiptDate;
	private String alrReceiptsNumber;

}
