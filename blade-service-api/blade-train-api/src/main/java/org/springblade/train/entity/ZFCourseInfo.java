package org.springblade.train.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel(value = "ZFCourseInfo对象", description = "ZFCourseInfo对象")
public class ZFCourseInfo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private int id;

	@ApiModelProperty(value = "课程ID")
    private int courseId;

	@ApiModelProperty(value = "课程名称")
    private String courseName;

	@ApiModelProperty(value = "学习月份")
	private String learnmonth;

	@ApiModelProperty(value = "应学人数")
	private Integer yxnum = 0 ;

	@ApiModelProperty(value = "未学人数")
	private Integer wxnum = 0 ;

	@ApiModelProperty(value = "达标人数")
	private Integer dbnum = 0 ;

	@ApiModelProperty(value = "未达标人数")
	private Integer wdbnum = 0 ;

	@ApiModelProperty(value = "企业名称")
    private String deptname;

}
