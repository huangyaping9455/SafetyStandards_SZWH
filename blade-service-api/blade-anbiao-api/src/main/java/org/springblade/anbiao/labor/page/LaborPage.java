package org.springblade.anbiao.labor.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/8 18:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "劳保分页对象", description = "劳保分页对象")
public class LaborPage extends BasePage{

	@ApiModelProperty(value = "物品名")
	private String aliName;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date aliIssueDate;

	@ApiModelProperty(value = "数量")
	private Integer aliIssueQuantity;

	@ApiModelProperty(value = "发放人数")
	private Integer ali_issue_people_number;

	@ApiModelProperty(value = "发放人")
	private String aliApplicationScope;

	@ApiModelProperty(value = "发放状态")
	private String aliStatus;

	@ApiModelProperty(value = "单位id",required = true)
	private Integer deptId;

	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private String asiYear;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	private String 	aliIds;

	private String Name;
	@ApiModelProperty(value = "企业id")
	private String ali_dept_ids;
}
