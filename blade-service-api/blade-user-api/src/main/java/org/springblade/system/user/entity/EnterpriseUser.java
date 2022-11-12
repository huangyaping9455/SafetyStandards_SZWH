package org.springblade.system.user.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "企管端小程序登陆参数")
public class EnterpriseUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 登陆方式
	 */
	@ApiModelProperty(value = "登陆方式（0.账号登陆,1.微信登陆）")
	private String loginType;

	/**
	 * 用户账号
	 */
	@ApiModelProperty(value = "用户名")
	private String name;

	/**
	 * 用户密码
	 */
	@ApiModelProperty(value = "用户密码")
	private String password;

	/**
	 * 微信Code
	 */
	@ApiModelProperty(value = "微信Code")
	private String code;
}
