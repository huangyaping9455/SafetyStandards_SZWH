package org.springblade.alarm.feign;

import lombok.AllArgsConstructor;
import org.springblade.alarm.entity.BaoBiaoAlarmhandleresultDriver;
import org.springblade.alarm.service.IBaoBiaoAlarmhandleresultDriverService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class AlarmClient implements IAlarmClientBack {

	private IBaoBiaoAlarmhandleresultDriverService iBaoBiaoAlarmhandleresultDriverService;

	@Override
	public Boolean updateStatus(@Valid @RequestBody BaoBiaoAlarmhandleresultDriver baoBiaoAlarmhandleresultDriver) {
		return iBaoBiaoAlarmhandleresultDriverService.updateStatus(baoBiaoAlarmhandleresultDriver);
	}
}
