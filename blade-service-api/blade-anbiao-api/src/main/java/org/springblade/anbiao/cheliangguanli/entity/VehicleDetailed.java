package org.springblade.anbiao.cheliangguanli.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "车辆详细对象",description = "车辆详细对象")
public class VehicleDetailed implements Serializable {

	@ApiModelProperty(value = "车辆主键")
	private String vehicleId;

	@ApiModelProperty(value = "车辆行驶证信息")
	private VehicleXingshizheng xingshizheng;

	@ApiModelProperty(value = "车辆道路运输证")
	private VehicleDaoluyunshuzheng daoluyunshuzheng;

	@ApiModelProperty(value = "车辆性能检测报告")
	private VehicleXingnengbaogao xingnengbaogao;

	@ApiModelProperty(value = "车辆技术评定")
	private VehicleJishupingding jishupingding;

	@ApiModelProperty(value = "车辆保险信息")
	private VehicleBaoxian baoxian;

	@ApiModelProperty(value = "车辆登记证书")
	private VehicleDengjizhengshu dengjizhengshu;
}
