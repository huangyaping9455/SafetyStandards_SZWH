/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 课程下拉框
 */
@Data
public class CourseSelectModel implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 课程名称
     */
    private String name;

    /**
     * 来源id
     */
    private Integer sourceId;

    /**
     * 是否可以修改记录 1可修改 0 只能修改部分业务属性
     */
    private Integer isOperation;
}
