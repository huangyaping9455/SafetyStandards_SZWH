package org.springblade.anbiao.weixiupeijian.service;

import org.springblade.anbiao.weixiu.DTO.MaintenanceDTO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;

import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/4 14:28
 */
public interface RepairPartsService {
	/**
	 * 维修列表信息
	 *
	 * @param
	 * @param
	 * @return
	 */
	List<FittingsEntity> selectList();

//	/**
//	 * 维修详细信息
//	 * @param
//	 * @return
//	 */
//	List<MaintenanceEntity> selectAll(String rid);
//
//	/**
//	 * 新增 维修
//	 * @param
//	 * @return
//	 */
//	Boolean insertOne(MaintenanceDTO maintenanceDTO);
//
//
//	/**
//	 * 删除
//	 * @param id
//	 * @return
//	 */
//	Boolean deleteAccident(String id);
//
//	/**
//	 * 修改
//	 * @return
//	 */
//	Boolean updateAccident(MaintenanceDTO maintenanceDTO);
}
