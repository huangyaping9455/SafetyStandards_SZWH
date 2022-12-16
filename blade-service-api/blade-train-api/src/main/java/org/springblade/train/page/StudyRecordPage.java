package org.springblade.train.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.common.BasePage;

/**
 * @author 呵呵哒
 */
@Data
@ApiModel(value = "StudyRecordPage对象", description = "StudyRecordPage对象")
public class StudyRecordPage extends BasePage {

	@ApiModelProperty(value = "学员ID")
	private Integer studentId;
	@ApiModelProperty(value = "企业名称")
	private String deptName;
	@ApiModelProperty(value = "学员姓名")
	private String driverName;

}
