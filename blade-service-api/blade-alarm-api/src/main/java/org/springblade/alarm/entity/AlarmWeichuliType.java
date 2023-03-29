package org.springblade.alarm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 * @date 2019/11/1114:32
 */
@Data
@ApiModel(value = "AlarmWeichuliType", description = "AlarmWeichuliType")
public class AlarmWeichuliType {
	/**
	 * 报警次数
	 */
	@ApiModelProperty(value = "报警次数")
	private Integer number;

	@ApiModelProperty(value = "报警类型")
	private String  AlarmType;

	@ApiModelProperty(value = "时间区间")
	private String times;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getAlarmType() {
		return AlarmType;
	}

	public void setAlarmType(String alarmType) {
		AlarmType = alarmType;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}
}
