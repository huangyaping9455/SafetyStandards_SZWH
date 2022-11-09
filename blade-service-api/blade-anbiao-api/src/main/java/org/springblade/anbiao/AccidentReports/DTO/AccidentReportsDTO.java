package org.springblade.anbiao.AccidentReports.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description : 事故报告新增
 * @Author : long
 * @Date :2022/11/4 14:36
 */
@Data
public class AccidentReportsDTO {
	private String det_name;
	private String 	shigufashengdidian;
	private Date shigufashengshijian;
	private String shiguxingzhi;
	private String shigufenlei;
	private String shiguzeren;
	private String 	chepaihao;
	private String jiashiyuan;
	private Integer siwang;
	private Integer shoushang;
	private BigDecimal caichansunshi;
	private BigDecimal jianjiejingjisunshi;
	private String shiguzhaopian;
	private String fujian;
	private String shigugaikuang;
	private String shangwangcaichansunshi;
	private String shigubaogaoid;
}
