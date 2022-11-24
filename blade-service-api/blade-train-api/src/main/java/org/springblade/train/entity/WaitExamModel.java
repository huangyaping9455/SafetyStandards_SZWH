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
public class WaitExamModel implements Serializable {
	
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

    /** 开始时间 */
    private String beginTime ;

    /** 当前时间 */
    private String nowTime ;

    /** 结束时间 */
    private String endTime ;

    /** 学员ID */
    private int studentId ;

    /** 学员名称 */
    private String studentName ;

    /** 完成率 */
    private int studyRate ;

    /** 验证方式 不验证-0，人脸验证-1，位置验证-2，人脸位置-3 */
    private int verification ;

    /** 课程状态(0未完成，1已完成，2未通过，3通过) */
    private int courseStatus ;

    /** 头像 */
    private String fullFacePhoto ;

    /** 来源id */
    private int sourceId;

    /** 考试总分 */
    private int totalScores ;

    /** 考试及格分数 */
    private int score ;

    /** 考题数量 */
    private int questionCount ;

    /** 考试时间 */
    private int examDuration ;

    /** 已获得最高分 */
    private int maxScore ;

}
