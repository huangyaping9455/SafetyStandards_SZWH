package org.springblade.anbiao.qiyeshouye.VO;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "EnterpriseSecurityActivitiesVO对象", description = "EnterpriseSecurityActivitiesVO对象")
public class EnterpriseSecurityActivitiesVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "时间")
	private String date;

	@ApiModelProperty(value = "年份")
	private String year;

	@ApiModelProperty(value = "月份")
	private String month;

	@ApiModelProperty(value = "企业Id")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "安全会议分数")
	private String anquanhuiyiScore;

	@ApiModelProperty(value = "安全检查分数")
	private String anquanjianchaScore;

	@ApiModelProperty(value = "动态监控分数")
	private String dongtaijiankongScore;

	@ApiModelProperty(value = "基础台账分数")
	private String jichutaizhangScore;

	@ApiModelProperty(value = "安全培训分数")
	private String anquanpeixunScore;
}
