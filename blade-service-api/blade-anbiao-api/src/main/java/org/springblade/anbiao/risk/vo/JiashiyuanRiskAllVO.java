package org.springblade.anbiao.risk.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "JiashiyuanRiskAllVO", description = "JiashiyuanRiskAllVO对象")
public class JiashiyuanRiskAllVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "风险标题")
	private String ardTitle;

	@ApiModelProperty(value = "风险内容")
	private String ardContent;

	@ApiModelProperty(value = "发现日期")
	private String ardDiscoveryDate;

	@ApiModelProperty(value = "关联值")
	private String ardAssociationValue;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "风险内容2")
	private String ardContent2;

	@ApiModelProperty(value = "到期时间")
	private String daoqishijian;

	@ApiModelProperty(value = "有效期")
	private String youxiaoqi;

}
