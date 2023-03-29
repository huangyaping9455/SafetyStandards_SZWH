package org.springblade.alarm.feign;

import org.springblade.alarm.entity.BaoBiaoAlarmhandleresultDriver;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 */
@Component
public class IAlarmClient implements IAlarmClientBack {

	@Override
	public Boolean updateStatus(@Valid BaoBiaoAlarmhandleresultDriver baoBiaoAlarmhandleresultDriver) {
		return null;
	}
}
