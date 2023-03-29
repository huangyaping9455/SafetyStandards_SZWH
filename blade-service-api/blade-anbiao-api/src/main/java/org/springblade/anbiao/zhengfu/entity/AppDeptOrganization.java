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
import java.util.List;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/7/3
 * @描述
 */
@Data
@ApiModel(value = "AppDeptOrganization对象", description = "AppDeptOrganization对象")
public class AppDeptOrganization implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private String qiyeid;

	@ApiModelProperty(value = "企业名称")
	private String qiyemingcheng;

	@ApiModelProperty(value = "下级数据")
	private List<AppDeptOrganization> children;

}
