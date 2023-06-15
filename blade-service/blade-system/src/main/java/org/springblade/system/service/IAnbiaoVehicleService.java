package org.springblade.system.service;

import org.apache.ibatis.annotations.Param;
import org.springblade.system.entity.AnbiaoVehicle;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.system.entity.AnbiaoVehicleImg;

/**
 * <p>
 * 车辆信息表 服务类
 * </p>
 *
 * @author lmh
 * @since 2023-06-06
 */
public interface IAnbiaoVehicleService extends IService<AnbiaoVehicle> {

	AnbiaoVehicleImg getByVehImg(@Param("vehId") String vehId);

}
