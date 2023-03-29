/**
 * Copyright (C), 2015-2020,
 * FileName: 北斗Vehicle
 * Author:   呵呵哒
 * Date:     2020/7/3 10:29
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @描述
 */
@Data
@ApiModel(value = "QiYeMonthMileageTongJi对象", description = "QiYeMonthMileageTongJi对象")
public class QiYeMonthMileageTongJi implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "行驶总里程")
	private double TravelMileage;

	@ApiModelProperty(value = "统计日期")
	private String date;

	@ApiModelProperty(value = "01") private double one;
	@ApiModelProperty(value = "02") private double two;
	@ApiModelProperty(value = "03") private double three;
	@ApiModelProperty(value = "04") private double four;
	@ApiModelProperty(value = "05") private double five;
	@ApiModelProperty(value = "06") private double six;
	@ApiModelProperty(value = "07") private double seven;
	@ApiModelProperty(value = "08") private double eight;
	@ApiModelProperty(value = "09") private double nine;
	@ApiModelProperty(value = "10") private double ten;
	@ApiModelProperty(value = "11") private double eleven;
	@ApiModelProperty(value = "12") private double twelve;
	@ApiModelProperty(value = "13") private double thirteen;
	@ApiModelProperty(value = "14") private double fourteen;
	@ApiModelProperty(value = "15") private double fifteen;
	@ApiModelProperty(value = "16") private double sixteen;
	@ApiModelProperty(value = "17") private double seventeen;
	@ApiModelProperty(value = "18") private double eighteen;
	@ApiModelProperty(value = "19") private double nineteen;
	@ApiModelProperty(value = "20") private double twenty;
	@ApiModelProperty(value = "21") private double twentyone;
	@ApiModelProperty(value = "22") private double twentytwo;
	@ApiModelProperty(value = "23") private double twentythree;
	@ApiModelProperty(value = "24") private double twentyfour;
	@ApiModelProperty(value = "25") private double twentyfive;
	@ApiModelProperty(value = "26") private double twentysix;
	@ApiModelProperty(value = "27") private double twentyseven;
	@ApiModelProperty(value = "28") private double twentyeight;
	@ApiModelProperty(value = "29") private double twentynine;
	@ApiModelProperty(value = "30") private double thirty;
	@ApiModelProperty(value = "31") private double thirtyone;

}
