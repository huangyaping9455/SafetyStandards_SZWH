package org.springblade.train.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName AuthenticatedUser
 * @Description 经过认证的用户信息
 * @author Hxz
 *
 */
@Data
public class AuthenticatedUser  {

	private static final long serialVersionUID = -3627216546882240437L;
	/**
	 * 用户ID
	 */
	@Setter
	@Getter
	private Integer userId = 0;
	/**
	 * 登录名
	 */
	@Setter
	private String username = "";
	/**
	 * 用户姓名
	 */
	@Setter
	@Getter
	private String realname = "";
	/**
	 * 密码
	 */
	@Setter
	private String password = "";
	/**
	 * Unit ID
	 */
	@Setter
	@Getter
	private Integer unitId = 0;
	/**
	 * 服务商ID
	 */
	@Setter
	@Getter
	private Integer serverId = 0;
	/**
	 * 所属政府ID
	 */
	@Setter
	@Getter
	private Integer govId = 0;
	/**
	 * 区域ID
	 */
	@Getter
	@Setter
	private Integer AreaId = 0;
	/**
	 * 用户类型
	 */
	@Getter
	@Setter
	private UserTypeEnum userType = UserTypeEnum.UNKNOW;
	/**
	 * 登录IP
	 */
	@Setter
	@Getter
	private String sourceIP = "";

	/**
	 * 账号可用
	 */
	@Setter
	private boolean enable = true;

	/**
	 * 账号未过期
	 */
	@Setter
	private boolean accountNonExpired = true;

	/**
	 * 学员审核状态 0：未审核  1：审核通过  2：审核不通过
	 */
	@Setter
	@Getter
	private Integer status = 0;


	/**
	 * 用户持有的权限角色组
	 */
	@Setter
	@Getter
	private List<String> roles;

}
