package org.springblade.train.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 课程表拓展表entity
 */
@Data
@TableName("base_course_ext")
public class CourseExt implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "rel_unit_course_id", type = IdType.INPUT)
	private Integer relUnitCourseId ;

	/** 课程付费 免费-0，企业付款-1，学员付款-2 */
	@TableField("course_fee")
	private int courseFee ;

	/** 上课方式 远程课程-1，现场课程-2，现场直播-3，远程直播-4 */
	@TableField("study_type")
	private int studyType ;

	/** 考试分数 0为不考试，大于0的数据为及格线 */
	@TableField("score")
	private int score ;

	/** 验证方式 不验证-0，人脸验证-1，位置验证-2，人脸位置-3 */
	@TableField("verification")
	private int verification ;

	/** 开始时间 */
	@TableField("begin_time")
	private String beginTime ;

	/** 结束时间 */
	@TableField("end_time")
	private String endTime ;

	/** 考试时间 */
	@TableField("exam_duration")
	private int examDuration ;

	/** 考试总分 */
	@TableField("total_scores")
	private int totalScores ;

	/** 课程价格 */
	@TableField("price")
	private double price ;

}
