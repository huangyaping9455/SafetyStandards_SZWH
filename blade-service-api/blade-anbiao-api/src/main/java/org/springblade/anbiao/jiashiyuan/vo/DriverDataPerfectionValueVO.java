package org.springblade.anbiao.jiashiyuan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DriverDataPerfectionValueVO对象", description = "DriverDataPerfectionValueVO对象")
public class DriverDataPerfectionValueVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "项目")
	private String xiangmu;

	@ApiModelProperty(value = "数据")
	private String shuju;

	@ApiModelProperty(value = "公司人数")
	private Integer gongsirenshu;
}
