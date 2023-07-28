package org.springblade.train.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel(value = "StudentInfo对象", description = "StudentInfo对象")
public class StudentInfo implements Serializable {

	@ApiModelProperty(value = "ID")
	private Integer id;

	@ApiModelProperty("学员Id")
	private Integer studentId;

	@ApiModelProperty("学员姓名")
	private String realName;

	@ApiModelProperty("登录名")
	private String userName;

	@ApiModelProperty(value = "岗位")
	private String station;

	@ApiModelProperty(value = "学员性别 0:未知 1:男 2:女")
	private Integer sex;

	@ApiModelProperty(value = "车牌号码")
	private String plateNumber;

	@ApiModelProperty(value = "学习方式：远程课程-1，现场课程-2，现场直播-3，远程直播-4")
	private String studyType;

	@ApiModelProperty(value = "学习进度")
	private String learningProgress;

	@ApiModelProperty(value = "课程总时长")
	private String duration ;

	@ApiModelProperty(value = "已有效学习时长")
	private String studyDuration;

	@ApiModelProperty(value = "状态")
	private String learningStatus;

	@ApiModelProperty(value = "考试分数：0为不考试，大于0的数据为及格线")
	private Integer score;

	@ApiModelProperty(value = "开始时间")
	private String courseBeginTime;

	@ApiModelProperty(value = "结束时间")
	private String courseEndTime;

	@ApiModelProperty(value = "验证方式 不验证-0，人脸验证-1，位置验证-2，人脸位置-3")
	private Integer verification;

	@ApiModelProperty(value = "是否完成考试：0否 大于0是")
	private Integer finishExam;

	@ApiModelProperty(value = "课程ID")
	private Integer courseId;

	@ApiModelProperty(value = "企业名称")
	private String unitName;

}
