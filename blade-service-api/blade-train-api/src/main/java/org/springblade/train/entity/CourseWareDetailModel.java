/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 课程课件信息
 */
@Data
public class CourseWareDetailModel implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    private Integer courseId;
    
    /**
     * 课件id
     */
    private Integer courseWareId;

    /**
     * 原始名称
     */
    private String originalName;

    /**
     * 课件code
     */
    private Long code;

    /**
     * 时长duration
     */
    private Integer duration;

    /**
     * 多媒体地址
     */
    private String mediaUrl;

    /**
     * 多媒体地址
     */
    private String sourceFile;

    /**
     * 课件别名
     */
    private String aliasName;

    /**
     * 课件类型
     */
    private int courseType ;

}
