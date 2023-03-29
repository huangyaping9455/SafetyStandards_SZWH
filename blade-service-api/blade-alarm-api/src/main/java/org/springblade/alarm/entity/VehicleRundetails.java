package org.springblade.alarm.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 呵呵哒
 * @description: TODO
 * @projectName SafetyStandards
 */
@Data
@ApiModel(value = "VehicleRundetails", description = "VehicleRundetails")
public class VehicleRundetails {
	/**
	 * 车牌
	 */
	@ApiModelProperty(value = "车牌")
	private String plate;
	/**
	 * 车牌颜色
	 */
	@ApiModelProperty(value = "车牌颜色")
	private String color;
	/**
	 * 运行时长
	 */
	@ApiModelProperty(value = "运行时长")
	private Integer runSeconds;
	/**
	 * 平均速度
	 */
	@ApiModelProperty(value = "平均速度")
	private Integer pingjunsudu;
	/**
	 * 报告时间
	 */
	@ApiModelProperty(value = "报告时间")
	private String  date;

	/**
	 * 总里程
	 */
	@ApiModelProperty(value = "总里程")
	private BigDecimal licheng;
	/**
	 * 运行时长show
	 */
	@ApiModelProperty(value = "运行时长show")
	private String  xingshishijian;
	/**
	 * 停车时长
	 */
	@ApiModelProperty(value = "停车时长")
	private String stopshijian;
	/**
	 * 开始位置
	 */
	@ApiModelProperty(value = "开始位置")
	private String kaishiweizhi;
	/**
	 * 结束位置
	 */
	@ApiModelProperty(value = "结束位置")
	private String  jieshuweizhi;
	/**
	 * gps车辆id
	 */
	@ApiModelProperty(value = "gps车辆id")
	private String vehid;

}
