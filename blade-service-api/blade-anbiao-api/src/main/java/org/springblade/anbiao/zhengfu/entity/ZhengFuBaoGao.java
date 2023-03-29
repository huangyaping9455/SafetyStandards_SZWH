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
 * @创建时间 2020/9/15
 * @描述
 */
@Data
@ApiModel(value = "ZhengFuBaoGao对象", description = "ZhengFuBaoGao对象")
public class ZhengFuBaoGao implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "目录id")
	private Integer muluid;

	@ApiModelProperty(value = "地区名称")
	private String areaname;

	@ApiModelProperty(value = "政府运管局Id")
	private String zhengfuid;

	@ApiModelProperty(value = "政府运管局名称")
	private String zhengfuname;

	@ApiModelProperty(value = "报告名称")
	private String name;

	@ApiModelProperty(value = "操作人")
	private String caozuoren;

	@ApiModelProperty(value = "推送时间")
	private String createtimeshow;

	@ApiModelProperty(value = "统计时间段")
	private String countdate;


}
