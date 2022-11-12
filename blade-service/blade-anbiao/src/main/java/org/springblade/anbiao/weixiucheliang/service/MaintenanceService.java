package org.springblade.anbiao.weixiucheliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;

import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/4 14:31
 */

public interface MaintenanceService  extends IService<MaintenanceEntity> {
	/**
	 * 维修列表信息
	 *
	 * @param
	 * @param maintenancePage
	 * @return
	 */
	MaintenancePage selectList(MaintenancePage maintenancePage);

	/**
	 * 维修详细信息
	 * @param
	 * @return
	 */
	MaintenanceEntity selectAll(MaintenancePage maintenancePage);

	/**
	 * 配件
	 *
	 * @param maintenancePage
	 * @return
	 */
	List<FittingsEntity> selectC(MaintenancePage maintenancePage);

	/**
	 * 新增 维修
	 * @param
	 * @return
	 */
	Boolean insertOne(MaintenanceEntity maintenanceDTO);


	/**
	 * 删除
	 * @param
	 * @return
	 */
	Boolean deleteAccident(MaintenancePage maintenancePage);

	/**
	 * 修改
	 * @return
	 */
	Boolean updateAccident(MaintenanceEntity maintenanceEntity);
}
