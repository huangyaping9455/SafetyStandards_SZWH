package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 考试记录
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("biz_test_record")
public class ExamRecord extends Model<ExamRecord> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//id
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	//学员id
	@TableField("student_id")
	private Integer studentId;
	//学员
	@TableField(exist = false)
	private Student student;
	//课程id
	@TableField("rel_unit_course_id")
	private Integer relUnitCourseId;
	//课程
	@TableField(exist = false)
	private Course course;
	//开始时间
	@TableField("begin_time")
	private String beginTime;
	//结束时间
	@TableField("end_time")
	private String endTime;
	//持续时长(秒)
	@TableField("duration")
	private Integer duration;
	//考试分数
	@TableField("score")
	private Integer score;
	//考试结果 1：通过，0：不通过
	@TableField("result")
	private Integer result;
	// 1有效，0无效
	@TableField("valid")
	private Integer valid;
	//人工审核时间
	@TableField("check_time")
	private String checkTime;
}
