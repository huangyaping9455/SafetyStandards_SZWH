package org.springblade.train.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "StudentProve对象", description = "StudentProve对象")
public class StudentProve implements Serializable {

	@ApiModelProperty("学员Id")
	private Integer id;

	@ApiModelProperty("学员姓名")
	private String realName;

	@ApiModelProperty("学员性别 0:未知 1:男 2:女")
	private Integer sex;

	@ApiModelProperty("正脸照")
	private String fullFacePhoto;

	@ApiModelProperty("身份证号码")
    private String identifyNumber;

	@ApiModelProperty("所属企业名称")
    private String unitName;

	@ApiModelProperty("岗位")
    private String station;

	@ApiModelProperty("课程名称")
    private String courseName;

	@ApiModelProperty("学习时长")
    private Integer studyDuration;

	@ApiModelProperty("考试时间")
    private String examTime;

	@ApiModelProperty("考试得分")
    private Integer examScore;

	@ApiModelProperty("学完时间")
    private String finishStudyTime;

	@ApiModelProperty("考试结果 1：通过，0：不通过")
    private Integer result;

	@ApiModelProperty("电子签名")
	private String signatrue;

	@ApiModelProperty("手机号码")
	private String cellphone;

	@ApiModelProperty("车辆牌照")
	private String platenumber;

	@ApiModelProperty("开始学习时间")
	private String StudyTime;

	@ApiModelProperty("分数线")
	private Integer score;
}
