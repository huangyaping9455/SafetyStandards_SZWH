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
public class ExamQuestionModel implements Serializable {
	
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

    /** 问题id */
    private int questionId ;

    /** 题目类型id */
    private String questionContent ;

    /** 题目类型(0单选，1多选) */
    private int category ;

    /**
     * 图片地址（可能有多个）
     */
    private String imageUrls;

    /**
     * 视频地址（可能有多个）
     */
    private String videoUrls;

    /** 题目解析 */
    private String analysis ;

    /** 题目类型ID */
    private int subjectTypeId;

    /** 题目类型名称 */
    private String subjectTypeName ;

    /**
     * 答案ID
     */
    private Integer answerId;

    /**
     * 选项编号
     */
    private Integer orderNumber;

    /**
     * 选项内容
     */
    private String answerContent;

    /**
     * 正确答案 答案选项 1，否则0
     */
    private Integer checked;

}
