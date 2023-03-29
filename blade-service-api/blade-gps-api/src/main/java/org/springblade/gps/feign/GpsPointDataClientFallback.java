package org.springblade.gps.feign;

import org.springblade.gps.entity.*;
import org.springblade.gps.page.VehiclePTPage;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: SpringBlade
 * @description: BlogClientFallback
 * @author: 呵呵哒
 **/
@Component
public class GpsPointDataClientFallback implements IGpsPointDataClient {


	@Override
	public List<VehiclePT> getVehiclePT2(String company) {
		return null;
	}

	@Override
	public TpvehDataCount selectTpvehdataCount(Integer deptId, Integer date) {
		return null;
	}

	@Override
	public VehiclePTPage<ZFTpvehData> selectZFTpvehdataAllPage(VehiclePTPage vehiclePTPage) {
		return null;
	}

}
