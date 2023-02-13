package org.springblade.anbiao.weixiucheliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import feign.Param;
import org.springblade.anbiao.weixiu.VO.MaintenanceEntityV;
import org.springblade.anbiao.weixiu.VO.MaintenanceTZVO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;
import org.springblade.anbiao.weixiu.page.MaintenanceTZPage;

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

	List<MaintenanceEntityV> selectByDateList(@Param("deptId") String deptId,@Param("date") String date,@Param("type") String type);

	/**
	 * 维修隐患整改台账
	 * @param maintenanceTZPage
	 * @return
	 */
	MaintenanceTZPage<MaintenanceTZVO> selectTZTJList(MaintenanceTZPage maintenanceTZPage);

}
