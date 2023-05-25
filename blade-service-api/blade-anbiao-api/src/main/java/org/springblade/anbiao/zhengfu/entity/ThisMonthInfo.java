package org.springblade.anbiao.zhengfu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hyp
 * @create 2023-05-25 16:27
 */
@Data
@ApiModel(value = "ThisMonthInfo对象", description = "ThisMonthInfo对象")
public class ThisMonthInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private Integer deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "本月行驶里程")
	private double TravelMileage;

	@ApiModelProperty(value = "台账文件总数")
	private Integer uploadedNum;

	@ApiModelProperty(value = "台账文件完成数")
	private Integer finshNum;

	@ApiModelProperty(value = "动态考核指标得分")
	private double totalScore;

}
