/*
 * Copyright 20L19-20L20L www.0L1n16.com
 *
 */
package org.springblade.train.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: [企业统计分析列表详情响应实体]
*/
@Data
public class UnitStatisticsDetailListModel implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 企业Id
	 */
	private String unitId;
	
	/**
	 * 企业名称
	 */
	private String unitName;
	
	/**
	 * 所属行业名称
	 */
	private String tradeKindName;
	
	/**
	 * 课程名称
	 */
	private String courseName;
	
	/**
	 * 总学员人数
	 */
	private Long totalNumber = 0L;
	
	/**
	 * 总学员人数
	 */
	private Long totalParticipateNumber = 0L;
	
	/**
	 * 已完成学习人数
	 */
	private Long completedNumber = 0L;
	
	/**
	 * 未完成学习人数
	 */
	private Long outstandingNumber = 0L;
	
	/**
	 * 逾期人数
	 */
	private Long overdueNumber = 0L;
	
	/**
	 * 完成比率(保留2位小数)
	 */
	// private Double completedRate = 0.0D;
	
	/**
	 * 逾期比率(保留2位小数)
	 */
	// private Double overdueRate = 0.0D;
	
	/**
	 * 开始学习时间
	 */
	private Date startTime;
	
	/**
	 * 全部完成时间
	 */
	private Date endTime;

}
