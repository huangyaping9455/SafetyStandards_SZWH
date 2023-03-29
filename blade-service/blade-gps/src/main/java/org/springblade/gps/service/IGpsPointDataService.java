/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.gps.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springblade.gps.entity.*;
import org.springblade.gps.page.VehiclePTPage;

import java.util.List;

/**
 * gps点位数据 服务类
 *
 * @author hyp
 * @since 2019-05-17
 */
public interface IGpsPointDataService extends IService<T> {

    /**
     * 获取点位数据
     * @param beginTime
     * @param endTime
     * @param vehId
     * @return
     */
    List<VehilePTData> selectPointData(String beginTime, String endTime, String vehId);

	/**
	 * 获取车辆树
	 *
	 * @param company
	 * @return
	 */
	List<VehicleNode> tree(String company);

	/**
	 * 根据企业ID获取车辆最新定位
	 * @param vehiclePTPage
	 * @return
	 */
	VehiclePTPage<TpvehData> selectTpvehdataAllPage(VehiclePTPage vehiclePTPage);

	/**
	 * 根据企业ID获取车辆实时在线离线数量
	 * @param deptId
	 * @param date
	 * @return
	 */
	TpvehDataCount selectTpvehdataCount(@Param("deptId") Integer deptId,@Param("date") Integer date);

	/**
	 * 根据企业ID获取车辆最新定位(政府)
	 * @param vehiclePTPage
	 * @return
	 */
	VehiclePTPage<ZFTpvehData> selectZFTpvehdataAllPage(VehiclePTPage vehiclePTPage);

	/**
	 * 根据车辆、日期获取车辆行驶里程及行驶时间
	 * @param date
	 * @param vehId
	 * @return
	 */
	List<VehTravel> selectVehTravel(@Param("date") String date,@Param("vehId") String vehId);

}
