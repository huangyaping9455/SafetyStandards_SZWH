package org.springblade.alarm.feign;

import org.springblade.alarm.entity.BaoBiaoAlarmhandleresultDriver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 */
@FeignClient(
	//定义Feign指向的service-id
	value = "blade-alarm",
	fallback = IAlarmClient.class
)
public interface IAlarmClientBack {
	String API_PREFIX = "/alarm";

	@PostMapping(API_PREFIX + "/updateStatus")
	Boolean updateStatus(@Valid @RequestBody BaoBiaoAlarmhandleresultDriver baoBiaoAlarmhandleresultDriver);
}
