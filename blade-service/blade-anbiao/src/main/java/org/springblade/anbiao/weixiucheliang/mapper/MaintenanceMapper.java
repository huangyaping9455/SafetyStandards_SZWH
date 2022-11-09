package org.springblade.anbiao.weixiucheliang.mapper;

import org.springblade.anbiao.weixiu.DTO.MaintenanceDTO;
import org.springblade.anbiao.weixiu.VO.MaintenanceVO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;

import java.util.Date;
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
	 * @return
	 */
	List<MaintenanceEntity> selectAll(String id );


	/**
	 * 新增维修详细信息
	 * @return
	 */
	Boolean insertOne(MaintenanceDTO maintenanceDTO);
	Boolean insertB(FittingsEntity fittingsEntity);


	/**
	 * 修改维修
	 * @param
	 * @return
	 */
	Boolean updateMain(MaintenanceDTO MaintenanceDTO);
	boolean updateB(FittingsEntity fittingsEntity);

	/**
	 * 删除
	 * @param
	 * @return
	 */
	Boolean deleteMain(String id);
}
