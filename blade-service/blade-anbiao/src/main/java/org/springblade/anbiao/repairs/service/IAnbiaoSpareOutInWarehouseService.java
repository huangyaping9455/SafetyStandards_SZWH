package org.springblade.anbiao.repairs.service;

import org.springblade.anbiao.repairs.entity.AnbiaoSpareOutInWarehouse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.repairs.page.AnbiaoSpareOutInWarehousePage;

import java.util.List;

/**
 * <p>
 * 出入备件库审核 服务类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface IAnbiaoSpareOutInWarehouseService extends IService<AnbiaoSpareOutInWarehouse> {

	AnbiaoSpareOutInWarehousePage<AnbiaoSpareOutInWarehouse> selectAllPage(AnbiaoSpareOutInWarehousePage anbiaoSpareOutInWarehousePage);

	AnbiaoSpareOutInWarehouse selectMaxXuhao(String deptId);

}
