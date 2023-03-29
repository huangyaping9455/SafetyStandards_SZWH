package org.springblade.gps.feign;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import org.springblade.gps.entity.TpvehDataCount;
import org.springblade.gps.entity.VehiclePT;
import org.springblade.gps.entity.ZFTpvehData;
import org.springblade.gps.page.VehiclePTPage;
import org.springblade.gps.service.IGpsPointDataService;
import org.springblade.gps.util.RedisOps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 呵呵哒
 * @program: SpringBlade
 * @description: BlogClientImpl
 **/
@RestController
@AllArgsConstructor
public class GpsPointDataClientImpl implements IGpsPointDataClient {

	private IGpsPointDataService gpsPointDataService;

	@Override
	@GetMapping(API_PREFIX + "/getVehiclePT2")
	public List<VehiclePT> getVehiclePT2(String company) {
		String s = RedisOps.get(company);
		String substring = s.substring(s.indexOf('['), s.lastIndexOf(']')+1);
		JSONArray objects = JSONUtil.parseArray(substring);
		List<VehiclePT> vehiclePTS = JSONUtil.<VehiclePT>toList(objects, VehiclePT.class);

		System.out.println(vehiclePTS);
		return vehiclePTS;
	}

    @Override
	@GetMapping(API_PREFIX+"/selectTpvehdataCount")
    public TpvehDataCount selectTpvehdataCount(Integer deptId, Integer date) {
		TpvehDataCount tpvehDataCount = gpsPointDataService.selectTpvehdataCount(deptId, date);
        return tpvehDataCount;
    }

	/**
	 * 根据企业ID获取车辆最新定位(政府)
	 * @param vehiclePTPage
	 * @return
	 */
	@Override
	@PostMapping(API_PREFIX+"/selectZFTpvehdataAllPage")
	public VehiclePTPage<ZFTpvehData> selectZFTpvehdataAllPage(@RequestBody VehiclePTPage vehiclePTPage) {
		VehiclePTPage<ZFTpvehData> zfTpvehDataVehiclePTPage = gpsPointDataService.selectZFTpvehdataAllPage(vehiclePTPage);
		if(zfTpvehDataVehiclePTPage == null){
			return null;
		}else{
			List<ZFTpvehData> zfTpvehDataList = zfTpvehDataVehiclePTPage.getRecords();
			return zfTpvehDataVehiclePTPage;
		}
	}



}
