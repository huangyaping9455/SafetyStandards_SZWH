package org.springblade.anbiao.repairs.service;

import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsSpareInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-09-22
 */
public interface IAnbiaoRepairsSpareInfoService extends IService<AnbiaoRepairsSpareInfo> {

	/**
	 * 根据报修单ID获取报修备件
	 * @param rppsRpId
	 * @return
	 */
	List<AnbiaoRepairsSpareInfo> selectByDeptIdList(String rppsRpId,String rppsType);

	List<AnbiaoRepairsSpareInfo> selectByType(@Param("rppsRpId") String rppsRpId);

}
