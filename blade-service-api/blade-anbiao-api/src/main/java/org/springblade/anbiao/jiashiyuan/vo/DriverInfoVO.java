package org.springblade.anbiao.jiashiyuan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by you on 2022/11/03
 */
@Data
@ApiModel(value = "DriverInfoVO对象", description = "DriverInfoVO对象")
public class DriverInfoVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "驾驶员ID")
	private String id;

	/**
	 * 驾驶员姓名
	 */
	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	/**
	 * 照片
	 */
	@ApiModelProperty(value = "照片")
	private String zhaopian;

	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	/**
	 * 企业ID
	 */
	@ApiModelProperty(value = "企业ID")
	private String deptId;

	/**
	 * 企业名称
	 */
	@ApiModelProperty(value = "企业名称")
	private String deptName;

	/**
	 * openID
	 */
	@ApiModelProperty(value = "openID")
	private String openid;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "挂车车辆牌照")
	private String guacheliangpaizhao;

}
