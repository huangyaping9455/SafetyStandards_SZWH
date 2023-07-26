package org.springblade.anbiao.deptrisk.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 * @create 2023-07-25 21:46
 */
@Data
public class DeptRisk {

	@ApiModelProperty(value = "车辆数")
	private Integer clNum = 0;
	@ApiModelProperty(value = "驾驶员数")
	private Integer jsyNum = 0;
	@ApiModelProperty(value = "企业数")
	private Integer qyNum = 0;
	@ApiModelProperty(value = "风险数")
	private Integer riskNum = 0;
	@ApiModelProperty(value = "企业ID")
	private Integer deptId;
	@ApiModelProperty(value = "企业名称")
	private String deptName;
	@ApiModelProperty(value = "入职登记表完成率")
	private String ruzhiAvg;
	@ApiModelProperty(value = "身份证完成率")
	private String sfzAvg;
	@ApiModelProperty(value = "驾驶证完成率")
	private String jszAvg;
	@ApiModelProperty(value = "从业资格证完成率")
	private String cyzgzAvg;
	@ApiModelProperty(value = "体检信息完成率")
	private String tjAvg;
	@ApiModelProperty(value = "岗前培训信息完成率")
	private String gqpxAvg;
	@ApiModelProperty(value = "无责证明信息完成率")
	private String wzzmAvg;
	@ApiModelProperty(value = "安全责任书信息完成率")
	private String aqzrsAvg;
	@ApiModelProperty(value = "危害告知书信息完成率")
	private String whgzsAvg;
	@ApiModelProperty(value = "劳动合同信息完成率")
	private String ldhtAvg;
	@ApiModelProperty(value = "驾驶员风险数")
	private Integer jsyRiskNum = 0;
	@ApiModelProperty(value = "车辆风险数")
	private Integer clRiskNum = 0;
	@ApiModelProperty(value = "日期")
	private String day;
}
