package org.springblade.anbiao.jiashiyuan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by you on 2019/4/22.
 */
@Data
@ApiModel(value = "JiaShiYuanTable对象", description = "JiaShiYuanTable对象")
public class JiaShiYuanTable implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 驾驶员姓名
	 */
	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private String xingbie;

	/**
	 * 入职日期
	 */
	@ApiModelProperty(value = "入职日期")
	private String ruzhiriqi;

	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	/**
	 * 职位
	 */
	@ApiModelProperty(value = "职位")
	private String zhiwei;

	/**
	 * 从业资格证
	 */
	@ApiModelProperty(value = "从业资格证")
	private String congyezigezheng;

	/**
	 * 从业类别
	 */
	@ApiModelProperty(value = "从业类别")
	private String congyeleibie;

	/**
	 * 从业证有效期
	 */
	@ApiModelProperty(value = "从业证有效期")
	private String congyezhengyouxiaoqi;

	/**
	 * 从业证初领日期
	 */
	@ApiModelProperty(value = "从业证初领日期")
	private String congyezhengchulingri;

	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	/**
	 * 车辆牌照
	 */
	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "企业名称")
	private String deptName;
}
