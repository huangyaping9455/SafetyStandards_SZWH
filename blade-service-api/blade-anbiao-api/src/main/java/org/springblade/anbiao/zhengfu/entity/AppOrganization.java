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
@ApiModel(value = "AppOrganization对象", description = "AppOrganization对象")
public class AppOrganization implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 企业ID
	 */
	@ApiModelProperty(value = "企业ID")
	private String Id;

	/**
	 * 上级ID
	 */
	@ApiModelProperty(value = "上级ID")
	private String parentId;

	/**
	 * 企业名称
	 */
	@ApiModelProperty(value = "企业名称")
	private String deptName;

	/**
	 * 企业tree_code
	 */
	@ApiModelProperty(value = "企业tree_code")
	private String treeCode;

	/**
	 * 省
	 */
	@ApiModelProperty(value = "省")
	private String province;

	/**
	 * 市
	 */
	@ApiModelProperty(value = "市")
	private String city;

	/**
	 * 县
	 */
	@ApiModelProperty(value = "县")
	private String country;

//	/**
//	 * qiyelist数据
//	 */
//	@ApiModelProperty(value = "qiyelist数据")
//	private List<AppDeptOrganization> qiyelist;
//
//	/**
//	 * xianlist数据
//	 */
//	@ApiModelProperty(value = "xianlist数据")
//	private List<AppOrganization> xianlist;
//
//	/**
//	 *shilist数据
//	 */
//	@ApiModelProperty(value = "shilist数据")
//	private List<AppOrganization> shilist;

	@ApiModelProperty(value = "企业ID")
	private String qiyeid;

	@ApiModelProperty(value = "企业名称")
	private String qiyemingcheng;

	@ApiModelProperty(value = "下级数据")
	private List<AppOrganization> children;

	@ApiModelProperty(value = "运管ID")
	private String yunguanid;

}
