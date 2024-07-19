package org.springblade.anbiao.risk.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RiskDeptConfigurationListVO", description = "RiskDeptConfigurationListVO对象")
public class RiskDeptConfigurationListVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private String id;

	@ApiModelProperty(value = "单位ID")
	private String deptId;

	@ApiModelProperty(value = "单位名称")
	private String deptName;

	@ApiModelProperty(value = "风险配置主键")
	private String rcId;

	@ApiModelProperty(value = "风险状态")
	private String status;

	@ApiModelProperty(value = "预警项")
	private String yujingxiang;

	@ApiModelProperty(value = "预警说明")
	private String shuoming;

	@ApiModelProperty(value = "预警类型")
	private Integer yujingleixing;

	@ApiModelProperty(value = "风险推送频率；1：按日，2：按月")
	private Integer type;

	@ApiModelProperty(value = "推送等级，1,2,3")
	private Integer level;

	@ApiModelProperty(value = "日期，每月的几号（按月才有），多个以英文逗号隔开")
	private String date;

	@ApiModelProperty(value = "小时，每日几点")
	private String hours;


}
