package org.springblade.train.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @author 呵呵哒
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VehiclePage对象", description = "VehiclePage对象")
	public class CourseInfoPage<T> extends BasePage<T> {

	@ApiModelProperty(value = "课程名称")
	private String courseName;

	@ApiModelProperty(value = "企业ID")
	private int unitId;

	@ApiModelProperty(value = "课程ID")
	private int courseId;

	@ApiModelProperty(value = "学员姓名")
	private String realname;

}
