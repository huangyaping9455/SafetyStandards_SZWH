package org.springblade.anbiao.cheliangguanli.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: SafetyStandards
 * @description: Vehicle
 * @author: elvis
 * @create2023-02-7 18:00
 **/
@Data
@ApiModel(value = "VehicleImg对象", description = "VehicleImg对象")
public class VehicleImg implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "行驶证ID")
	private String xszid;

	@ApiModelProperty(value = "行驶证正面附件")
	private String xszzmimg;

	@ApiModelProperty(value = "行驶证反面附件")
	private String xszfmimg;

	@ApiModelProperty(value = "道路运输证ID")
	private String yszid;

	@ApiModelProperty(value = "道路运输证附件")
	private String yszimg;

	@ApiModelProperty(value = "性能检测报告ID")
	private String xnbgid;

	@ApiModelProperty(value = "性能检测报告附件")
	private String xnbgimg;

	@ApiModelProperty(value = "车辆登记证书ID")
	private String djzid;

	@ApiModelProperty(value = "车辆登记证书附件")
	private String djzimg;

	@ApiModelProperty(value = "影像附件数")
	private Integer count = 0;

	@ApiModelProperty(value = "行驶证影像附件数")
	private Integer xszcount = 0;

	@ApiModelProperty(value = "道路运输证影像附件数")
	private Integer yszimgcount = 0;

	@ApiModelProperty(value = "性能检测报告影像附件数")
	private Integer xnbgimgcount = 0;

	@ApiModelProperty(value = "车辆登记证书影像附件数")
	private Integer djzimgcount = 0;

}
