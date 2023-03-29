package org.springblade.alarm.page;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.common.BasePage;


/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 * @date 2019/11/1318:31
 */
@Data
@ApiModel(value = "AlarmPage对象", description = "AlarmPage对象")
public class AlarmPlatePage extends BasePage {
	@ApiModelProperty(value = "开始时间（yyyy-MM-dd）", required = true)
	private String beginTime;
	@ApiModelProperty(value = "结束时间（yyyy-MM-dd）", required = true)
	private String endTime;
	@ApiModelProperty(value = "企业名称", required = true)
	private String company;
	@ApiModelProperty(value = "报警类型", required = true)
	private String alarmType;
	@ApiModelProperty(value = "车牌", required = true)
	private String plateNumber;
	@ApiModelProperty(value = "车牌颜色", required = true)
	private String color;
}
