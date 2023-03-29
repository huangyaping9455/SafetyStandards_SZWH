/**
 * Copyright (C), 2015-2020,
 * FileName: ZhengFuBaoJingTongJi
 * Author:   呵呵哒
 * Date:     2020/7/7 20:29
 * Description:
 */
package org.springblade.anbiao.zhengfu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建时间 2020/7/7
 * @描述
 */
@Data
@ApiModel(value = "ZhengFuBaoJingVehicle对象", description = "ZhengFuBaoJingVehicle对象")
public class ZhengFuBaoJingVehicle implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "地区名称")
	private String areaname;

	@ApiModelProperty(value = "企业id")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "报警数")
	private Integer baojingcishu;

	@ApiModelProperty(value = "政府运管局Id")
	private String zhengfuid;

	@ApiModelProperty(value = "政府运管局名称")
	private String zhengfuname;

	@ApiModelProperty(value = "车辆牌照")
	private String plate;

	@ApiModelProperty(value = "日期")
	private String times;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

}
