package org.springblade.anbiao.cheliangguanli.feign;

import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDriver;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 * @date 2019/11/1311:23
 */
@Component
public class IVehiclepostClient implements IVehiclepostClientBack {
	@Override
	public Vehicle vehileOne(String cheliangpaizhao, String chepaiyanse, Integer deptId) {
		return null;
	}

	@Override
	public Vehicle getvehileYYS(String id) {
		return null;
	}

	@Override
	public VehicleDriver getVehicleDriver(String deptId,String cheliangpaizhao, String chepaiyanse,String type) {
		return null;
	}

	@Override
	public List<VehicleDriver> getDriverByDeptIdList(String deptId,String type) {
		return null;
	}

}
