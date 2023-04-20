package org.springblade.alarm.feign;

import lombok.AllArgsConstructor;
import org.springblade.gps.feign.IGpsPointDataClient;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 * @date 2019/12/417:36
 */

@RestController
@AllArgsConstructor
public class VehicleStopClient implements  IVehicleStopClientBack{
	private IGpsPointDataClient gpsPointDataClient;
//	private IStopvehicleService stopvehicleService;

}
