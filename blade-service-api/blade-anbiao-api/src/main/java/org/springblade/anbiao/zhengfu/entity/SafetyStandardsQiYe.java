/**
 * Copyright (C), 2015-2020,
 * FileName: GpsVehicle
 * Author:   呵呵哒
 * Date:     2020/7/3 10:29
 * Description:
 */
package org.springblade.anbiao.zhengfu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 */
@Data
@ApiModel(value = "SafetyStandardsQiYe对象", description = "SafetyStandardsQiYe对象")
public class SafetyStandardsQiYe implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "机构类型")
	private String jigouleixing;

	@ApiModelProperty(value = "政府运管名称")
	private String zhengfuname;

	@ApiModelProperty(value = "政府运管ID")
	private String zhengfuid;

	@ApiModelProperty(value = "企业ID")
	private String qiyeid;

	@ApiModelProperty(value = "企业名称")
	private String qiyemingcheng;

	@ApiModelProperty(value = "地区名称")
	private String areaname;

	@ApiModelProperty(value = "达标状态")
	private String dabiaostatus;

	@ApiModelProperty(value = "当前分值")
	private Integer totalpoints;

}
