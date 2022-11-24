package org.springblade.train.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 学习记录
 */
@Data
public class StudyRecordApp implements Serializable {
	//课程名称
	private String courseName;
	//课件名称
	private String coursewareName;
	//开始时间
	private String beginTime;
	//结束时间
	private String endTime;
	// 1有效，0无效
	private Integer valid;
	//课件的时长
	private Integer duration;
	//已经学习的时长
	private Integer studyDuration;

	private Integer currentDuration;
	
}
