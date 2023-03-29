/**
 * Copyright (C), 2015-2020,
 * FileName: GpsVehicle
 * Author:   呵呵哒
 * Date:     2020/7/3 10:29
 * Description:
 */
package org.springblade.anbiao.zhengfu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/7/27
 * @描述
 */
@Data
@TableName("anbiao_app_updat_version")
@ApiModel(value = "AppVersionInfo对象", description = "AppVersionInfo对象")
public class AppVersionInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private Integer id;

 	@ApiModelProperty(value = "类型（企业APP:0,司机APP:1）")
	private String type;

	@ApiModelProperty(value = "版本号")
	private String version;

	@ApiModelProperty(value = "创建时间")
	private String createtime;

	@ApiModelProperty(value = "创建者ID")
	private String createuserid;

	@ApiModelProperty(value = "创建者姓名")
	private String createuser;

	@ApiModelProperty(value = "安卓地址")
	private String androidurl;

	@ApiModelProperty(value = "iOS地址")
	private String iosurl;

	@ApiModelProperty(value = "描述")
	private String remark;

	@ApiModelProperty(value = "状态（0:正常，1:失效）")
	private String isdeleted;

	@ApiModelProperty(value = "更新者名称")
	private String updateuser;

	@ApiModelProperty(value = "更新时间")
	private String updatetime;

	@ApiModelProperty(value = "手机型号(1 ios 2 安卓)")
	private Integer modletype;

}
