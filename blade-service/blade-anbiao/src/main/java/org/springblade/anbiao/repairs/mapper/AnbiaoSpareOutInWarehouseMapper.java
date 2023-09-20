package org.springblade.anbiao.repairs.mapper;

import org.springblade.anbiao.repairs.entity.AnbiaoSpareOutInWarehouse;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareOutInWarehouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.repairs.page.AnbiaoSpareOutInWarehousePage;

import java.util.List;

/**
 * <p>
 * 出入备件库审核 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface AnbiaoSpareOutInWarehouseMapper extends BaseMapper<AnbiaoSpareOutInWarehouse> {

	List<AnbiaoSpareOutInWarehouse> selectAllPage(AnbiaoSpareOutInWarehousePage anbiaoSpareOutInWarehousePage);
	int selectAllTotal(AnbiaoSpareOutInWarehousePage anbiaoSpareOutInWarehousePage);

	AnbiaoSpareOutInWarehouse selectMaxXuhao(String deptId);

}
