package org.springblade.train.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel(value = "CourseInfo对象", description = "CourseInfo对象")
public class CourseInfo implements Serializable {

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

	@ApiModelProperty(value = "累计注册人员")
    private String registerNumber;

	@ApiModelProperty(value = "开始时间")
    private String beginTime;

	@ApiModelProperty(value = "结束时间")
    private String endTime;

	@ApiModelProperty(value = "应学课时")
    private String duration ;

	@ApiModelProperty(value = "应学人数")
	private int shouldLearnNumber = 0 ;

	@ApiModelProperty(value = "在学人数")
	private int inStudyNumber = 0 ;

	@ApiModelProperty(value = "完成人数")
	private int finishStudyCount = 0 ;

	@ApiModelProperty(value = "未完成人数")
	private int unfinishedNumber = 0 ;

	@ApiModelProperty(value = "考试分数：0为不考试，大于0的数据为及格线")
	private int score = 0 ;

}
