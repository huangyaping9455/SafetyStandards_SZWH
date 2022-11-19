package org.springblade.anbiao.cheliangguanli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.page.VehiclePage;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;

import java.util.List;

/**
 * 车辆 Mapper 接口
 *
 * @author :hyp
 */
public interface VehicleMapper extends BaseMapper<Vehicle> {

    /**
     * 自定义分页
     *
     * @param vehiclePage
     * @return
     */
    List<VehicleVO> selectVehiclePage(VehiclePage vehiclePage);

	/**
	 * 车牌搜索
	 * @param deptId
	 * @param cheliangpaizhao
	 * @return
	 */
	List<VehicleCP>  selectCL(String deptId, String cheliangpaizhao);

    /**
     * 统计
     *
     * @param vehiclePage
     * @return
     */
    int selectVehicleTotal(VehiclePage vehiclePage);

    /**
     * 根据id获取数据
     * *@param id
     *
     * @return
     */
    VehicleVO selectByKey(String id);
	/**
	* @Description: 根据车辆牌照和车牌颜色获取数据
	* @Param: [cheliangpaizhao, chepaiyanse]
	* @return: org.springblade.anbiao.cheliangguanli.vo.VehicleVO
	*/
	VehicleVO selectByCPYS(String cheliangpaizhao,String chepaiyanse);
	VehicleVO selectCPYS(String cheliangpaizhao,String chepaiyanse);
	VehicleVO selectDeptCPYS(String cheliangpaizhao,String chepaiyanse,String deptId);

    /**
     * 自定义删除
     * @param id
     * @return
     */
    boolean deleteVehicle(String id);

	/**
	 * 自定义停用
	 * @param id
	 * @return
	 */
	boolean updateVehicleOutStatus(String id);

	/**
	 * 自定义启用
	 * @param id
	 * @return
	 */
	boolean updateVehicleSignStatus(String id);

	/**
	 * 自定义报废
	 *
	 * @param id
	 * @return
	 */
	boolean updateVehicleScrapStatus(String id);

	/**
	 * 车辆统计 车牌
	 */
	int  vehicleDayCount(Integer deptId);
	/**
	 * 闲置车辆统计 车牌
	 */
	int xianzhiVehcleCount(Integer deptId);
	/**
	 * 根据车牌 车牌颜色查询详情
	 */
	Vehicle  vehileOne(@Param("cheliangpaizhao") String cheliangpaizhao,@Param("chepaiyanse") String chepaiyanse,@Param("deptId") Integer deptId);

	/**
	 * 车辆资料增量接口
	 * @param caozuoshijian
	 * @return
	 */
	List<VehicleVO> selectVehicleAll(@Param("caozuoshijian") String caozuoshijian);

	/**
	 * 阜阳测试
	 * @param caozuoshijian
	 * @param deptId
	 * @return
	 */
	List<VehicleVO> selectVehicleAll_FY(@Param("caozuoshijian") String caozuoshijian,@Param("deptId") String deptId);
    List<VehicleVO> selectVehicleAll_NFY(@Param("caozuoshijian") String caozuoshijian,@Param("deptId") String deptId);


	/**
	 * 根据车辆id查询相关运营商信息
	 * @param id
	 * @return
	 */
	VehicleVO selectByYYS(@Param("id") String id);

	/**
	 * 车辆异动
	 * @param deptId
	 * @param id
	 * @return
	 */
	boolean updateDeptId(@Param("deptId") String deptId,@Param("id") String id);

	/**
	 * 验证车辆颜色是否存在
	 * @param color
	 * @return
	 */
	VehicleVO selectVehicleColor(@Param("color") String color);

	/**
	 * 导入车辆
	 * @param vehicle
	 * @return
	 */
	boolean insertSelective(Vehicle vehicle);

	/**
	 * 根据企业ID、车辆牌照、车牌颜色修改相应车辆信息
	 * @param vehicle
	 * @return
	 */
	boolean updateSelective(Vehicle vehicle);

	/**
	 * 验证车辆终端id是否存在
	 * @param id
	 * @return
	 */
	VehicleVO selectByZongDuan(@Param("id") String id);

	/**
	 * 各地市车辆变更统计
	 * @param vehiclePage
	 * @return
	 */
	List<VehicleGDSTJ> selectGDSVehiclePage(VehiclePage vehiclePage);
	int selectGDSVehicleTotal(VehiclePage vehiclePage);

	/**
	 * 各地市车辆变更明细统计
	 * @param vehiclePage
	 * @return
	 */
	List<VehicleGDSTJ> selectGDSMXVehiclePage(VehiclePage vehiclePage);
	int	selectGDSMXVehicleTotal(VehiclePage vehiclePage);

	/**
	 * 根据企业ID获取当前企业所有车辆信息
	 * @param deptId
	 * @return
	 */
	List<Vehicle> vehileList(@Param("deptId") Integer deptId);

	/**
	 * 根据车辆ID修正车辆终端ID
	 * @param zongduanid
	 * @param caozuoren
	 * @param caozuorenid
	 * @param id
	 * @return
	 */
	boolean updateVehicleZongDuanId(@Param("zongduanid") String zongduanid,
									@Param("caozuoren") String caozuoren,
									@Param("caozuorenid") String caozuorenid,
									@Param("id") String id
	);

	/**
	 * 根据车辆ID、驾驶员ID查询是否存在记录
	 * @param vehid
	 * @param jiashiyuanid
	 * @return
	 */
	CheliangJiashiyuan getVehidAndJiashiyuanId(@Param("vehid") String vehid,
											   @Param("jiashiyuanid") String jiashiyuanid
	);

	/**
	 * 添加车辆驾驶员绑定关系
	 * @param cheliangJiashiyuan
	 * @return
	 */
	boolean insertVehidAndJiashiyuan(CheliangJiashiyuan cheliangJiashiyuan);

	/**
	 * 根据企业ID查询企业车辆数
	 * @param deptId
	 * @return
	 */
	VehicleGDSTJ getVehidNumByDeptId(String deptId);

	/**
	 * 宿州--获取平台所有企业数据接口
	 * @return
	 */
	List<SUZHOUDept> getDeptList();

	/**
	 * 宿州--车辆基础数据接口
	 * @param deptId
	 * @return
	 */
	List<SUZHOUVehicle> getSUZHOUVehicleList(@Param("deptId") String deptId);

	/**
	 * 宿州--统计数据接口
	 * @param deptId
	 * @return
	 */
	List<SUZHOUVehicleTJ> getSUZHOUVehicleTJList(@Param("deptId") String deptId);

	/**
	 * 根据车辆ID获取车辆绑定驾驶员信息
	 * @return
	 */
	VehicleDriver getVehicleDriver(@Param("deptId") String deptId,@Param("cheliangpaizhao") String cheliangpaizhao,@Param("chepaiyanse") String chepaiyanse,@Param("type") String type);

	/**
	 *根据企业ID获取驾驶员信息
	 * @param deptId
	 * @return
	 */
	List<VehicleDriver> getDriverByDeptIdList(@Param("deptId") String deptId,@Param("type") String type);


}
