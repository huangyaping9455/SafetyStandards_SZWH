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
 * @Author: stydm
 * @CreateDate: 2019-10-19$ 16:24$
 * @UpdateUser: stydm
 * @UpdateDate: 2019-10-19$ 16:24$
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("relation_unit_course")
public class CourseUnit implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/** 课程ID */
	@TableField("course_id")
	private Integer courseId ;

	/** 单位id */
	@TableField("unit_id")
	private int unitId ;

	/** 是自有课程 */
	@TableField("is_owner")
	private int isOwner ;

	/** 状态  上架-1，下架-2 */
	@TableField("status")
	private int status ;

	/** 删除标识 删除为1，默认为0 */
	@TableField("deleted")
	private int deleted ;

}
