package org.springblade.anbiao.AccidentReports.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description : 事故报告新增
 * @Author : long
 * @Date :2022/11/4 14:36
 */
@Data
public class AccidentReportsDTO {
	private String id;
	private String detName;
	private Integer deptId;
	private String 	shigufashengdidian;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date shigufashengshijian;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date caozuoshijian;
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
	private String ali_delete;
}
