package org.springblade.anbiao.cheliangguanli.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "车辆保险详细信息")
public class VehicleBaoxianInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "车辆保险主要信息")
	private VehicleBaoxian baoxian;

	@ApiModelProperty(value = "车辆保险明细信息")
	private List<VehicleBaoxianMingxi> baoxianMingxis;
}
