/*
 * Copyright 20L19-20L20L www.0L1n16.com
 *
 */
package org.springblade.train.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: [企业统计分析列表响应实体]
 * @author 呵呵哒
 */
@Data
public class UnitStatisticsListModel implements Serializable {
	
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
	 * 所属行业
	 */
	private String tradeKindName;
	
	/**
	 * 课程数量
	 */
	private Long courseNumber;
	
	/**
	 * 总学员人数
	 */
	private Long totalNumber = 0L;
	
	/**
	 * 总参与学员人数
	 */
	private Long totalParticipateNumber = 0L;
	
	/**
	 * 已完成学习人次
	 */
	private Long completedNumber = 0L;
	
	/**
	 * 未完成学习人次
	 */
	private Long outstandingNumber = 0L;
	
	/**
	 * 逾期人次
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

}
