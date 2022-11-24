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
 * @Description: 抓拍图片记录
 */
@Data
@TableName("biz_snapshot")
public class Snapshot implements Serializable{
	
	private static final long serialVersionUID = 1L;
	  
	//id
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	//学员id
	@TableField("student_id")
	private Integer studentId;
	//课程id
	@TableField("rel_unit_course_id")
	private Integer relUnitCourseId;
	//课件id
	@TableField("courseware_id")
	private Integer coursewareId;
	//照片地址
	@TableField("photo_url")
	private String photoUrl;
	//拍照时间
	@TableField("create_time")
	private String createTime;
}
