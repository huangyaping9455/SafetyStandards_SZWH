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
 * @创建时间 2020/7/21
 * @描述
 */
@Data
@ApiModel(value = "ZhengFuDongTaiJianKong对象", description = "ZhengFuDongTaiJianKong对象")
public class ZhengFuDongTaiJianKong implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "地区名称")
	private String areaname;

	@ApiModelProperty(value = "政府运管局Id")
	private String zhengfuid;

	@ApiModelProperty(value = "政府运管局名称")
	private String zhengfuname;

	@ApiModelProperty(value = "车辆总数")
	private Integer vehnum;

	@ApiModelProperty(value = "上线车辆数")
	private Integer sxvehnum;

	@ApiModelProperty(value = "在线车辆数")
	private Integer zaixiannum;

	@ApiModelProperty(value = "离线车辆数")
	private Integer lxvehnum;

	@ApiModelProperty(value = "上线率")
	private String onlineRate = "0.00%";

}
