package org.springblade.gps.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 呵呵哒
 * @projectName SafetyStandards
 */
@Data
@ApiModel(value = "VehTravel", description = "VehTravel")
public class VehTravel {

	@ApiModelProperty(value = "车辆ID")
	private String vehicleId;

	@ApiModelProperty(value = "日期")
	private String date;

	@ApiModelProperty(value = "行驶里程")
	private String travelMileage;

	@ApiModelProperty(value = "行驶时间")
	private String xingshishijian;

	@ApiModelProperty(value = "报警次数")
	private Integer alarmCount;

}
