package org.springblade.anbiao.vehicleDelete.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.vehicleDelete.entity.AnbiaoVehicleDeleteInfo;
import org.springblade.anbiao.vehicleDelete.page.VehicleDeletePage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *车辆删除日志记录
 * @author author
 * @since 2023-11-10
 */
public interface AnbiaoVehicleDeleteInfoMapper extends BaseMapper<AnbiaoVehicleDeleteInfo> {

	List<AnbiaoVehicleDeleteInfo> selectTJPage(VehicleDeletePage vehicleDeletePage);
	int selectTJTotal(VehicleDeletePage vehicleDeletePage);

}
