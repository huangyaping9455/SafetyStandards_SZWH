package org.springblade.anbiao.labor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/3 22:09
 */
@Data
@TableName("anbiao_labor_insurance")
public class LaborEntity {

	private String 	aliIds;
	private Integer aliDeptIds;
	private String aliName;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private String aliIssueDate;
	private Integer aliIssueQuantity;
	private Integer aliCollectedAmount;
	private Integer aliCollectedQuantity;
	private Integer aliIssuePeopleNumber;
	private Date aliValidFrom;
	private Date aliExpiryDate;
	private String aliStatus;
	private String aliApplicationScope;
	private String aliDelete;
	private Date aliCreateTime;
	private String aliCreateByIds;
	private String aliCreateByName;
	private Date aliUpdateTime;
	private String aliUpdateByIds;
	private String aliUpdateByName;

	private String deptId;
	private List<Labor> labor;
}
