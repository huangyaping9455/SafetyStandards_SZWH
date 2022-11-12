package org.springblade.anbiao.cheliangguanli.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.entity.VehicleBiangengjilu;

import java.util.List;

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

	@ApiModelProperty(value = "车辆变更记录")
	@TableField(exist = false)
	private List<VehicleBiangengjilu> cheliangbiangengjilu;

}
