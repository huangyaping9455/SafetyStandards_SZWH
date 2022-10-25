package org.springblade.anbiao.cheliangguanli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.cheliangguanli.entity.VehiclePhone;

import java.util.List;

/**
 * 车辆随车电话 Mapper 接口
 * @author :hyp
 */
public interface VehiclePhoneMapper extends BaseMapper<VehiclePhone> {

	/**
	 * 根据企业ID获取车辆随车电话
	 * @param deptId
	 * @return
	 */
	List<VehiclePhone> selectVehPhoneList(@Param("deptId") Integer deptId);

}
