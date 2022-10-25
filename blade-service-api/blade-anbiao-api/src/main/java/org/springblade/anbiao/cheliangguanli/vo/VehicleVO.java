package org.springblade.anbiao.cheliangguanli.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;

/**
 * 视图实体类
 * @program: SafetyStandards
 * @description: vehicle
 * @author: hyp
 * @create2021-04-22 14:00
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "vehicleVO对象", description = "vehicleVO对象")
public class VehicleVO extends Vehicle {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "预警/超期提醒说明")
	private String shuoming;

}
