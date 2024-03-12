package org.springblade.anbiao.vehicleDelete.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VehicleDeletePage对象", description = "VehicleDeletePage对象")
public class VehicleDeletePage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "报修日期")
	private String vdDate;

	@ApiModelProperty(value = "车辆牌照")
	private String vehPlate;


}




