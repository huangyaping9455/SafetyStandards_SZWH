package org.springblade.train.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.common.BasePage;

/**
 * @author 呵呵哒
 */
@Data
@ApiModel(value = "CourseTestRecordPage对象", description = "CourseTestRecordPage对象")
public class CourseTestRecordPage extends BasePage {

	@ApiModelProperty(value = "学员名称")
	private String studentName;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "学员ID")
	private Integer studentId;

	@ApiModelProperty(value = "课程ID")
	private Integer courseId;

}
