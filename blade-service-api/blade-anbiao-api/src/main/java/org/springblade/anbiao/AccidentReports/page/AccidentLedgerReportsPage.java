package org.springblade.anbiao.AccidentReports.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccidentLedgerReportsPage对象", description = "AccidentLedgerReportsPage对象")
public class AccidentLedgerReportsPage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "事故id")
	private String accidentId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "事故发生时间")
	private String shigufashengshijian;

	@ApiModelProperty(value = "事故地点")
	private String shigufashengdidian;

	@ApiModelProperty(value = "事故种类")
	private String shigufenlei;

	@ApiModelProperty(value = "事故性质")
	private String shiguxingzhi;

	@ApiModelProperty(value = "事故责任")
	private String shiguzeren;

	@ApiModelProperty(value = "事故车辆")
	private String chepaihao;

	@ApiModelProperty(value = "事故人员")
	private String jiashiyuan;

	@ApiModelProperty(value = "事故经过")
	private String shigugaikuang;

	@ApiModelProperty(value = "直接经济损失")
	private String caichansunshi;

	@ApiModelProperty(value = "间接经济损失")
	private String jianjiejingjisunshi;

	@ApiModelProperty(value = "事故照片1")
	private String shiguzhaopian1;

	@ApiModelProperty(value = "事故照片2")
	private String shiguzhaopian2;

	@ApiModelProperty(value = "事故照片3")
	private String shiguzhaopian3;

	private String date;
}
