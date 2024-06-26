package org.springblade.anbiao.cheliangguanli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.cheliangguanli.entity.VehiclesMoveInfo;
import org.springblade.anbiao.cheliangguanli.page.VehiclePage;
import org.springblade.anbiao.cheliangguanli.vo.VehicleListVO;

import java.util.List;

/**
 * 车辆 Mapper 接口
 */
public interface VehiclesMoveInfoMapper extends BaseMapper<VehiclesMoveInfo> {

    /**
     * 自定义分页
     * @param vehiclesMoveInfoPage
     * @return
     */
//    List<VehiclesMoveInfo> selectVehiclePage(VehiclesMoveInfoPage vehiclesMoveInfoPage);
//    int selectVehicleTotal(VehiclesMoveInfoPage vehiclesMoveInfoPage);

	/**
	 * 车辆异动
	 * @return
	 */
	boolean insertSelective(VehiclesMoveInfo vehiclesMoveInfo);

	List<VehicleListVO> selectPageList(VehiclePage vehiclePage);
	int selectTotal(VehiclePage vehiclePage);

	List<VehicleListVO> selectGHCPageList(VehiclePage vehiclePage);
	int selectGHCTotal(VehiclePage vehiclePage);


}
