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
public class StudentApplyModel implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;


    /** 申请时间 */
    private String applyTime ;

    /** 考试开始时间 */
    private String examBeginTime ;

    /** 考试结束时间 */
    private String examEndTime ;

    /** 学员ID */
    private int studentId ;

    /** 学员名称 */
    private String studentName ;

    /** 考试分数 */
    private int score ;

    /** 状态 (0 已申请 1已安排考试 2 考核未通过 3 考核通过) */
    private int status ;

}
