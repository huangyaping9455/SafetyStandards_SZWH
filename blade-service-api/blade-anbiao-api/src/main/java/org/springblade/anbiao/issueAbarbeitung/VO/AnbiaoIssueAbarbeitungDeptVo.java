package org.springblade.anbiao.issueAbarbeitung.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.anbiao.issueAbarbeitung.entity.AnbiaoIssueAbarbeitungDept;

/**
 * @author hyp
 * @create 2023-04-03 16:28
 */
@Data
@ApiModel(value = "AnbiaoIssueAbarbeitungDeptVo对象", description = "AnbiaoIssueAbarbeitungDeptVo对象")
public class AnbiaoIssueAbarbeitungDeptVo extends AnbiaoIssueAbarbeitungDept {

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "限期整改时间")
	private String rectificationTime;

	@ApiModelProperty(value = "是否强制时间整改，1；")
	private Integer isAbarbeitung = 0;

	@ApiModelProperty(value = "整改要求")
	private String rectificationRequirement;

	@ApiModelProperty(value = "创建时间")
	private String createTime;

}
