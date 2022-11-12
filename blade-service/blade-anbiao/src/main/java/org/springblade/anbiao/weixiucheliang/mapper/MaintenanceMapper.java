package org.springblade.anbiao.weixiucheliang.mapper;

import org.springblade.anbiao.weixiu.DTO.FittingDTO;
import org.springblade.anbiao.weixiu.DTO.MaintenanceDTO;
import org.springblade.anbiao.weixiu.VO.MaintenanceVO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;

import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/4 14:33
 */
public interface MaintenanceMapper {
	/**
	 * 维修列表
	 *
	 * @param
	 * @param maintenancePage
	 * @return
	 */
	List<MaintenanceVO> selectList(MaintenancePage maintenancePage);


	/**
	 * 维修详细信息
	 *
	 * @param
	 * @param maintenancePage
	 * @return
	 */
	MaintenanceEntity selectAll(MaintenancePage maintenancePage );

	List<FittingsEntity> selectC(MaintenancePage maintenancePage);
	int selectTotal(MaintenancePage maintenancePage);

	/**
	 * 新增维修详细信息
	 * @return
	 */
	Boolean insertOne(MaintenanceEntity maintenanceEntity);
	Boolean insertB(FittingDTO fittingDTO);


	/**
	 * 修改维修
	 * @param
	 * @return
	 */
	Boolean updateMain(MaintenanceEntity maintenanceEntity);
	boolean updateB(FittingDTO fittingDTO);

	/**
	 * 删除
	 * @param
	 * @return
	 */
	Boolean deleteMain(MaintenancePage maintenancePage);
}
