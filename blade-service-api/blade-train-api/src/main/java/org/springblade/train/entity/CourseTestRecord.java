package org.springblade.train.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 考题entity
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("biz_test_record")
public class CourseTestRecord implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 考试结束时间
	 */
	@TableField("end_time")
	private String endTime;

	/**
	 * 考试开始时间
	 */
	@TableField("begin_time")
	private String beginTime;

	/** 学员ID */
	@TableField("student_id")
	private int studentId ;

	/** 学员名称 */
	@TableField(exist = false)
	private String studentName ;

	/** 课程名称 */
	@TableField(exist = false)
	private String courseName ;

	/** 课程id */
	@TableField("rel_unit_course_id")
	private int courseId ;

	/** 考试用时 */
	@TableField("duration")
	private int duration ;

	/** 考试分数 */
	@TableField("score")
	private int score ;

	/** 考试结果 */
	@TableField("result")
	private int result ;
}
