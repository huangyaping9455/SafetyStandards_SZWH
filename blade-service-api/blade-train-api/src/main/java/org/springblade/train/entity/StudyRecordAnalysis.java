package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ClassName: StudyRecordAnalysis
 * Description: [学习记录分析实体类]
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_study_record_analysis")
public class StudyRecordAnalysis implements Serializable{

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	/**
	 *ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	public Integer Id;

	/**
	 * 企业ID
	 */
	@TableField("unit_id")
	public Integer unitId;
	/**
	 * 课程ID
	 */
	@TableField("course_id")
	public Integer courseId;
	/**
	 * 日期
	 */
	@TableField("date")
	public String date;
	/**
	 * 每天总时长
	 */
	@TableField("total_time")
	public Integer totaltime=0;
	/**
	 * 0点
	 */
	@TableField("hour0")
	public Integer hour0=0;
	
	@TableField("hour1")
	public Integer hour1=0;
	
	@TableField("hour2")
	public Integer hour2=0;
	
	@TableField("hour3")
	public Integer hour3=0;
	
	@TableField("hour4")
	public Integer hour4=0;
	
	@TableField("hour5")
	public Integer hour5=0;
	
	@TableField("hour6")
	public Integer hour6=0;
	
	@TableField("hour7")
	public Integer hour7=0;
	
	@TableField("hour8")
	public Integer hour8=0;
	
	@TableField("hour9")
	public Integer hour9=0;
	
	@TableField("hour10")
	public Integer hour10=0;
	
	@TableField("hour11")
	public Integer hour11=0;
	
	@TableField("hour12")
	public Integer hour12=0;
	
	@TableField("hour13")
	public Integer hour13=0;
	
	@TableField("hour14")
	public Integer hour14=0;
	
	@TableField("hour15")
	public Integer hour15=0;
	
	@TableField("hour16")
	public Integer hour16=0;
	
	@TableField("hour17")
	public Integer hour17=0;
	
	@TableField("hour18")
	public Integer hour18=0;
	
	@TableField("hour19")
	public Integer hour19=0;
	
	@TableField("hour20")
	public Integer hour20=0;
	
	@TableField("hour21")
	public Integer hour21=0;
	
	@TableField("hour22")
	public Integer hour22=0;
	
	@TableField("hour23")
	public Integer hour23=0;
	
}
