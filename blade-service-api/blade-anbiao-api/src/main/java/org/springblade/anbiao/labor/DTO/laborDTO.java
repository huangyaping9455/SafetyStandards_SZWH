package org.springblade.anbiao.labor.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springblade.anbiao.labor.entity.Labor;
import org.springblade.anbiao.labor.entity.LaborEntity;
import org.springblade.anbiao.labor.page.LaborPage;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description : 劳保
 * @Author : long
 * @Date :2022/11/4 10:53
 */
@Data
public class laborDTO {
	private Integer aliDeptIds;
	private String aliName;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date aliIssueDate;
	private String 	aliIds;
	private Integer aliIssueDuantity;
	private Integer aliIssuePeopleNumber;
	private String aliStatus;
	private String aliApplicationScope;
	private Integer aliIssueQuantity;
	private String aliCreateTime;
	private String aliCreateByIds;
	private String aliCreateByName;
	private LaborEntity laborEntity;
	private List<Labor> labor;
}
