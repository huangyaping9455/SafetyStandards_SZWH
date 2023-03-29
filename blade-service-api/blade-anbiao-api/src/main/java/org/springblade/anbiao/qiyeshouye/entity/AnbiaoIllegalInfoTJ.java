package org.springblade.anbiao.qiyeshouye.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "AnbiaoIllegalInfoTJ对象", description = "AnbiaoIllegalInfoTJ对象")
public class AnbiaoIllegalInfoTJ implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "驾驶员ID")
	private String jsyId;
	@ApiModelProperty(value = "企业ID")
	private Integer deptId;
	@ApiModelProperty(value = "车辆ID")
	private String vehId;
	@ApiModelProperty(value = "违规日期")
	private String date;
	@ApiModelProperty(value = "违规地点")
	private String address;
	@ApiModelProperty(value = "违法行为")
	private Integer unlawfulAct;
	@ApiModelProperty(value = "违法行为")
	private String unlawfulActShow;
	@ApiModelProperty(value = "企业名称")
	private String deptName;
	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;
	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;
	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;
	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;
	@ApiModelProperty(value = "违法违章数据来源")
	private Integer dataSources;
	@ApiModelProperty(value = "违法违章数据来源")
	private String dataSourcesShow;

}
