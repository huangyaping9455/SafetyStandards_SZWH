package org.springblade.train.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@TableName("relation_student_course")
public class CourseStudent implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/** 课程id */
	@TableField("rel_unit_course_id")
	private int courseId ;

	/** 学生id */
	@TableField("student_id")
	private int studentId ;

	/** 课程状态(0未完成，1已完成，2未通过，3通过) */
	@TableField("course_status")
	private int courseStatus ;

	/** 支付标识（0：免费，1：待支付，2：已支付） */
	@TableField("pay_flag")
	private int payFlag ;

	/** 订单号 */
	@TableField("order_no")
	private String orderNo ;

	@TableField("begin_time")
	private String begin_time;

	@TableField("end_time")
	private String end_time;

	@TableField("assign_date")
	private String assign_date;

	@TableField("pay_time")
	private String pay_time;

	@TableField("total_pay")
	private String total_pay;


}
