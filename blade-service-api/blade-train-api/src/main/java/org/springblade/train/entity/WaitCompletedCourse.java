package org.springblade.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 待完成课程
 */
@Data
public class WaitCompletedCourse implements Serializable {
	//课程id
	private Integer id;
	//企业课程id
	private Integer relUnitCourseId;
	//课程名称
	private String name;
	//课程描述
	private String description;
	//课程推广图片
	private String advertiseImage;
	//课程学习-开始时间
	private String beginTime;
	//课程学习-结束时间
	private String endTime;
	//验证方式 不验证-0，人脸验证-1，位置验证-2，人脸位置-3
	private Integer verification;
	//视频课件数
	private Integer videoCount;
	//文件课件数
	private Integer fileCount;
	//直播课件数
	private Integer directCount;
	//课程时长(秒)
	private Integer duration;
	//已学习时长
	private Integer studyDuration;
	//课程状态(0未完成，1已完成，2未通过，3通过)
	private Integer courseStatus;
	//课程价格
	private float price;
	//学员ID
	private Integer studentId;
}
