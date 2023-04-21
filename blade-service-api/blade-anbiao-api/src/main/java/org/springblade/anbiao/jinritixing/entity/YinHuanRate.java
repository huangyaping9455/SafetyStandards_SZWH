package org.springblade.anbiao.jinritixing.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 * @create2022-09-15
 **/
@Data
@ApiModel(value = "YinHuanRate对象", description = "YinHuanRate对象")
public class YinHuanRate implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业 id",required = true)
    private Integer deptId;

    @ApiModelProperty(value = "上线率")
    private String upLineRate;

	@ApiModelProperty(value = "轨迹完整率")
	private String IntactPositionRate;

	@ApiModelProperty(value = "轨迹漂移率")
	private String DriftPositionRate;

	@ApiModelProperty(value = "数据合格率")
	private String QualifiedPositionRate;

	@ApiModelProperty(value = "统计日期")
	private String date;

}
