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
import java.util.List;

/**
 * <p>Description: [练习题实体]
*/
@Data
@TableName("base_exercises")
public class Exercises implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 课件id
     */
	@TableField("courseware_id")
    private Integer coursewareId;
    
    /**
     * 题目内容
     */
	@TableField("content")
    private String content;
    
    /**
     * 题目分类id
     */
	@TableField("subject_type_id")
    private Integer subjectTypeId;
	
	/**
	 * 题目类型(0单选，1多选)
	 */
	@TableField("category")
	private Integer category;

	/**
	 * 图片地址（可能有多个）
	 */
	@TableField("image_urls")
	private String imageUrls;

	/**
	 * 视频地址（可能有多个）
	 */
	@TableField("video_urls")
	private String videoUrls;
    
    /**
     * 题目解析
     */
	@TableField("analysis")
    private String analysis;
    
    /**
     * 删除标识：删除为1，默认为0
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
	
	/**
	 * 练习题答案
	 */
	@TableField(exist = false)
	private List<ExerciseAnswer> exerciseAnswerList;
    

}
