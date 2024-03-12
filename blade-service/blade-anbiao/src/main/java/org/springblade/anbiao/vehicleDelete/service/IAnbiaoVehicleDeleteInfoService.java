package org.springblade.anbiao.vehicleDelete.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.vehicleDelete.entity.AnbiaoVehicleDeleteInfo;
import org.springblade.anbiao.vehicleDelete.page.VehicleDeletePage;

/**
 * <p>
 *  服务类
 * </p>
 *车辆删除日志记录
 * @author author
 * @since 2023-11-10
 */
public interface IAnbiaoVehicleDeleteInfoService extends IService<AnbiaoVehicleDeleteInfo> {

	VehicleDeletePage<AnbiaoVehicleDeleteInfo> selectTJPage(VehicleDeletePage vehicleDeletePage);

}
