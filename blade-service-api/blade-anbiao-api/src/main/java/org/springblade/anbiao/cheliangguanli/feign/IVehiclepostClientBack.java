package org.springblade.anbiao.cheliangguanli.feign;


import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDriver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 * @date 2019/11/1311:22
 */
@FeignClient(
	//定义Feign指向的service-id
	value = "blade-anbiao",
	fallback = IVehiclepostClient.class
)
public interface IVehiclepostClientBack {
	String API_PREFIX = "/anbiao/vehicle";

	@PostMapping(API_PREFIX + "/vehileOne")
	Vehicle vehileOne(@RequestParam("cheliangpaizhao")String cheliangpaizhao, @RequestParam("chepaiyanse")String chepaiyanse, @RequestParam("deptId")Integer deptId);

	@PostMapping(API_PREFIX + "/getvehileYYS")
	Vehicle getvehileYYS(@RequestParam("id") String id);

	/**
	 * 根据车辆ID获取车辆绑定驾驶员信息
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getVehicleDriver")
	VehicleDriver getVehicleDriver(@RequestParam("deptId") String deptId,@RequestParam("cheliangpaizhao") String cheliangpaizhao,@RequestParam("chepaiyanse") String chepaiyanse,@RequestParam("type") String type);

	/**
	 * 根据企业ID获取驾驶员信息
	 * @param deptId
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getDriverByDeptIdList")
	List<VehicleDriver> getDriverByDeptIdList(@RequestParam("deptId") String deptId,@RequestParam("type") String type);

}
