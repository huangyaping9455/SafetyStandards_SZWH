package org.springblade.train.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.common.BasePage;

import java.util.List;

/**
 * @author 呵呵哒
 */
@Data
@ApiModel(value = "UnitStatisticsPage对象", description = "UnitStatisticsPage对象")
public class UnitStatisticsPage extends BasePage {

	@ApiModelProperty(value = "所属行业Id")
	private Integer tradeKindId;

	@ApiModelProperty(value = "企业Id")
	private Integer unitId;

	@ApiModelProperty(value = "企业名称")
	private String unitName;

	@ApiModelProperty(value = "学员名称")
	private String realName;

	@ApiModelProperty(value = "课程Id")
	private Integer courseId;

	@ApiModelProperty(value = "课程关联Id")
	private Integer relUnitCourseId;

	@ApiModelProperty(value = "权限企业Id")
	private List<Integer> authUnitId;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

}
