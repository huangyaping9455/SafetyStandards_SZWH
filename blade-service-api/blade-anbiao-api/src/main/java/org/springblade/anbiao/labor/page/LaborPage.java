package org.springblade.anbiao.labor.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

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

	@ApiModelProperty(value = "企业名")
	private String ali_name;

	@ApiModelProperty(value = "时间")
	private Date ali_issue_date;

	@ApiModelProperty(value = "数量")
	private Integer ali_issue_quantity;

	@ApiModelProperty(value = "发放人数")
	private Integer ali_issue_people_number;

	@ApiModelProperty(value = "发放人")
	private String ali_application_scope;

	@ApiModelProperty(value = "发放状态")
	private String ali_status;

}
