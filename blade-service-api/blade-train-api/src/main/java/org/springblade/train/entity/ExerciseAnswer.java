/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [练习题答案实体]
*/
@Data
@TableName("base_exercise_answer")
public class ExerciseAnswer implements Serializable {
	
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
     * 练习题id
     */
	@TableField("exercise_id")
    private Integer exerciseId;
    
    /**
     * 选项编号
     */
	@TableField("order_number")
    private Integer orderNumber;
    
    /**
     * 选项内容
     */
	@TableField("content")
    private String content;
    
    /**
     * 正确答案 答案选项 1，否则0
     */
	@TableField("checked")
    private Integer checked;
    
    /**
     * 删除为1，默认为0
     */
	@TableField("deleted")
    private Integer deleted;

	/**
	 * 创建人
	 */
	@TableField("created_by")
	private String createdBy;

	/**
	 * 创建时间
	 */
	@TableField("created_time")
	private Date createdTime;

	/**
	 * 更新人
	 */
	@TableField("updated_by")
	private String updatedBy;

	/**
	 * 更新时间
	 */
	@TableField("updated_time")
	private Date updatedTime;
    

}
