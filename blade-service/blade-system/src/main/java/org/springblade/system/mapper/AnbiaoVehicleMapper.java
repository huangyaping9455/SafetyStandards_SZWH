package org.springblade.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.cheliangguanli.entity.VehicleImg;
import org.springblade.system.entity.AnbiaoVehicle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.system.entity.AnbiaoVehicleImg;

/**
 * <p>
 * 车辆信息表 Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2023-06-06
 */
public interface AnbiaoVehicleMapper extends BaseMapper<AnbiaoVehicle> {

	AnbiaoVehicleImg getByVehImg(@Param("vehId") String vehId);

}
