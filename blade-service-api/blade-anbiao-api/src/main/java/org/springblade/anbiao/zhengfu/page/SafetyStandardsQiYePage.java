/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuZhuTiPage
 * Author:   呵呵哒
 * Date:     2020/6/20 16:14
 * Description:
 */
package org.springblade.anbiao.zhengfu.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @创建人 hyp
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SafetyStandardsQiYePage对象", description = "SafetyStandardsQiYePage对象")
public class SafetyStandardsQiYePage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "政府运管ID")
	private String deptId;

	@ApiModelProperty(value = "政府运管下级ID")
	private String xiaJiDeptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "达标状态")
	private String dabiaostatus;

	@ApiModelProperty(value = "省")
	private String province;

	@ApiModelProperty(value = "市")
	private String city;

	@ApiModelProperty(value = "县")
	private String country;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getXiaJiDeptId() {
		return xiaJiDeptId;
	}

	public void setXiaJiDeptId(String xiaJiDeptId) {
		this.xiaJiDeptId = xiaJiDeptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDabiaostatus() {
		return dabiaostatus;
	}

	public void setDabiaostatus(String dabiaostatus) {
		this.dabiaostatus = dabiaostatus;
	}
}
