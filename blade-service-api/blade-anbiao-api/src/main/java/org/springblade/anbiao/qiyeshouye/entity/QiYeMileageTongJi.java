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
@ApiModel(value = "QiYeMileageTongJi对象", description = "QiYeMileageTongJi对象")
public class QiYeMileageTongJi implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private Integer deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "车辆ID")
	private String vehId;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "统计日期")
	private String statisticsDate;

	@ApiModelProperty(value = "行驶里程")
	private double TravelMileage;

	@ApiModelProperty(value = "平均速度")
	private double averageSpeed;

	@ApiModelProperty(value = "行驶时间")
	private String TravelTimes;

	@ApiModelProperty(value = "开始里程")
	private double travelStartMileage;

	@ApiModelProperty(value = "结束里程")
	private double travelStopMileage;

	@ApiModelProperty(value = "开始位置")
	private String TravelStartPosition;

	@ApiModelProperty(value = "结束位置")
	private String TravelStopPosition;

}
