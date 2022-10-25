package org.springblade.anbiao.jiashiyuan.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * Created by you on 2019/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "JiaShiYuanPage对象", description = "JiaShiYuanPage对象")
public class JiaShiYuanPage<T> extends BasePage<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "单位id",required = true)
	private Integer deptId;

	@ApiModelProperty(value = "单位名称")
	private String deptName;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "驾驶员类型",required = true)
	private String jiashiyuanleixing;

	@ApiModelProperty(value = "ids值为‘1’时，表示从预警提醒钻取数据")
	private String ids;

	@ApiModelProperty(value = "预警统计日期")
	private String tongjiriqi;

	@ApiModelProperty(value = "预警项id")
	private String tixingxiangqingid;

	@ApiModelProperty(value = "预警类型")
	private String tixingleixing;

	@ApiModelProperty(value = "预警类型")
	private String biaoshi;

	@ApiModelProperty(value = "日期状态")
	private String congyezhengchulingristatus;

	@ApiModelProperty(value = "日期状态")
	private String congyezhengyouxiaoqistatus;

	@ApiModelProperty(value = "从业类别状态")
	private String congyeleibiestatus;

	@ApiModelProperty(value = "从业类别(传入对应的值)")
	private String congyeleibie;
	private String[] idss;

	@ApiModelProperty(value = "准驾车型")
	private String zhunjiachexing;

	@ApiModelProperty(value = "从业证初领日期")
	private String congyezhengchulingri;

	@ApiModelProperty(value = "从业证有效期")
	private String congyezhengyouxiaoqi;

	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	@ApiModelProperty(value = "性别")
	private String xingbie;

	@ApiModelProperty(value = "所属地市")
	private String areaName;




}
