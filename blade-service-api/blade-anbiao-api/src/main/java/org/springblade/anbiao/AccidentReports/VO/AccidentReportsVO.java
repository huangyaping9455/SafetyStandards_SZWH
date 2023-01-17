package org.springblade.anbiao.AccidentReports.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description : 事故通用返回
 * @Author : long
 * @Date :2022/11/4 14:36
 */
@Data
public class AccidentReportsVO {
	private String id;
	private String deptName;
	private String 	shigufashengdidian;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date shigufashengshijian;
	private String shiguxingzhi;
	private String shigufenlei;
	private String shiguzeren;
	private String 	chepaihao;
	private String jiashiyuan;
	private String AccidentName;
	private Integer siwang;
	private String shigugaikuang;
	@ApiModelProperty(value = "单位id",required = true)
	private Integer deptId;
	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date caozuoshijian;
	private String fujian;
	private BigDecimal jianjiejingjisunshi;
	private BigDecimal caichansunshi;
	private String shangwangcaichansunshi;
	private String shiguzhaopian1;
	private String shiguzhaopian2;
	private String shiguzhaopian3;
	private String shiguzhijieyuanyin;
	private Integer shoushang;
	private String zerenfenxi;
	private String caozuorenid;
	private String cheliangid;
}
