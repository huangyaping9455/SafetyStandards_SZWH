package org.springblade.anbiao.jiashiyuan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JiaShiYuanLedgerVO {
	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "企业主键")
	private String deptId;

	@ApiModelProperty(value = "驾驶员人数")
	private String jiashiyuanrenshu;

	@ApiModelProperty(value = "车辆数量")
	private String cheliangshuliang;

	@ApiModelProperty(value = "意外险保险金额")
	private String yiwaixianzongbaoxianjine;

	@ApiModelProperty(value = "意外险保费金额")
	private String yiwaixianzongbaofeijine;

	@ApiModelProperty(value = "车辆保险金额")
	private String chexianzongbaoxianjine;

	@ApiModelProperty(value = "车辆保费金额")
	private String chexianzongbaofeijine;

	@ApiModelProperty(value = "交强险保险金额")
	private String jiaoqiangxianzongjine;

	@ApiModelProperty(value = "交强险保费金额")
	private String jiaoqiangxianzongbaofeijine;

	@ApiModelProperty(value = "货柜险保险金额")
	private String huoguizongbaoxianjine;

	@ApiModelProperty(value = "货柜险保费金额")
	private String huoguizongbaofeijine;

	@ApiModelProperty(value = "安责险保险金额")
	private String anzezongbaoxianjine;

	@ApiModelProperty(value = "安责险保费金额")
	private String anzezongbaofeijine;

	@ApiModelProperty(value = "其他保险金额（人员）")
	private String qitazongbaoxianjine;

	@ApiModelProperty(value = "其他保费金额（人员）")
	private String qitazongbaofeijine;

	@ApiModelProperty(value = "其他保险金额（企业）")
	private String qitazongbaoxianjineqiye;

	@ApiModelProperty(value = "其他保费金额（企业）")
	private String qitazongbaofeijineqiye;

	@ApiModelProperty(value = "总金额")
	private String totalTmount;
}
