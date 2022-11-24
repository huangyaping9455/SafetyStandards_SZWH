/*
 * Copyright 2019-2020 www.01n16.com
 *
 */
package org.springblade.train.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>ClassName: UnitStatisticsSummaryModel
 * <p>Description: [企业统计分析数量统计响应实体]
*/
@Data
public class UnitStatisticsSummaryModel implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 总企业数
	 */
	private Long totalNumber = 0L;
	
	/**
	 * 已完企业数
	 */
	private Long completedNumber = 0L;
	
	/**
	 * 未完企业数
	 */
	private Long outstandingNumber = 0L;
	
	/**
	 * 逾期企业数
	 */
	private Long overdueNumber = 0L;

}
