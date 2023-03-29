package org.springblade.gps.feign;

import org.springblade.gps.entity.*;
import org.springblade.gps.page.VehiclePTPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@FeignClient(
	//定义Feign指向的service-id
	value = "blade-gps",
	//定义hystrix配置类
	fallback = GpsPointDataClientFallback.class
)
public interface IGpsPointDataClient {

	/**
	 * 接口前缀
	 */
	String API_PREFIX = "gps/gpsdata";

	/**
	 * 获取详情
	 *
	 * @param company 企业名称
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getVehiclePT2")
	List<VehiclePT> getVehiclePT2(@RequestParam("company") String company);

	/**
	 * 根据企业ID获取车辆实时在线离线数量
	 * @param deptId
	 * @param date
	 * @return
	 */
	@GetMapping(API_PREFIX+"/selectTpvehdataCount")
	TpvehDataCount selectTpvehdataCount(@RequestParam("deptId") Integer deptId,@RequestParam("date") Integer date);

	/**
	 * 根据企业ID获取车辆最新定位(政府)
	 * @param vehiclePTPage
	 * @return
	 */
	@PostMapping(API_PREFIX+"/selectZFTpvehdataAllPage")
	VehiclePTPage<ZFTpvehData> selectZFTpvehdataAllPage(@Valid @RequestBody VehiclePTPage vehiclePTPage);


}
