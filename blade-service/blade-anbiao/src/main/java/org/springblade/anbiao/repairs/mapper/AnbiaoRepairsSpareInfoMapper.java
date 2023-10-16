package org.springblade.anbiao.repairs.mapper;

import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsSpareInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-09-22
 */
public interface AnbiaoRepairsSpareInfoMapper extends BaseMapper<AnbiaoRepairsSpareInfo> {

	/**
	 * 根据报修单ID获取报修备件
	 * @param rppsRpId
	 * @return
	 */
	List<AnbiaoRepairsSpareInfo> selectByDeptIdList(@Param("rppsRpId") String rppsRpId,@Param("rppsType") String rppsType);

	List<AnbiaoRepairsSpareInfo> selectByType(@Param("rppsRpId") String rppsRpId);


}
