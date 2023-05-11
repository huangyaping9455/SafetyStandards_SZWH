package org.springblade.gps.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 * @date 2019/11/717:22
 */
@Data
@ApiModel(value = "VehilePTData", description = "VehilePTData 时间段点位信息")
public class VehilePTData {

	@ApiModelProperty(value = "速度")
	private Integer Speed;

	@ApiModelProperty(value = "速度")
	private Integer Velocity;

	@ApiModelProperty(value = "纬度")
	private Double longitude;

	@ApiModelProperty(value = "经度")
	private Double latitude;

	@ApiModelProperty(value = "gps里程表")
	private Integer gpsmileage = 0;

	@ApiModelProperty(value = "gps时间")
	private LocalDateTime GpsTime;

	@ApiModelProperty(value = "车辆id")
	private String VehId;

	@ApiModelProperty(value = "车牌")
	private String plate;

	@ApiModelProperty(value = "车牌颜色")
	private String color;

	@ApiModelProperty(value = "地理位置")
	private String roadName;

	@ApiModelProperty(value = "报警标识")
	private Integer Alarm;

	@ApiModelProperty(value = "报警类型")
	private String AlarmNote;

	@ApiModelProperty(value = "方向")
	private Integer Angle;

	@ApiModelProperty(value = "定位标识")
	private Integer Locate;

	@ApiModelProperty(value = "限速值")
	private Integer limited;

	@ApiModelProperty(value = "百度经度")
	private double bdlongitude;

	@ApiModelProperty(value = "百度纬度")
	private double bdlatitude;


}
