package org.springblade.anbiao.weixiupeijian.mapper;

import org.springblade.anbiao.weixiu.DTO.MaintenanceDTO;
import org.springblade.anbiao.weixiu.VO.MaintenanceVO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;

import java.util.List;

/**
 * @Description : 维修
 * @Author : long
 * @Date :2022/11/4 14:30
 */
public interface RepairPartsMapper {
	/**
	 * 配件列表
	 * @param
	 * @return
	 */
	List<FittingsEntity> selectList();


//	/**
//	 * 维修详细信息
//	 *
//	 * @param
//	 * @return
//	 */
//	List<MaintenanceEntity> selectAll(String id );
//
//
//	/**
//	 * 新增维修详细信息
//	 * @return
//	 */
//	Boolean insertOne(MaintenanceDTO maintenanceDTO);
//
//
//
//	/**
//	 * 修改维修
//	 * @param
//	 * @return
//	 */
//	Boolean updateRepair(MaintenanceDTO MaintenanceDTO);
//
//	/**
//	 * 删除
//	 * @param
//	 * @return
//	 */
//	Boolean deleteRepair(String id);
}
