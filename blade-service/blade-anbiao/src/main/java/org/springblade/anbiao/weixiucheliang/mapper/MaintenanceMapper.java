package org.springblade.anbiao.weixiucheliang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import feign.Param;
import org.mapstruct.Mapper;
import org.springblade.anbiao.AccidentReports.entity.AccidentReportsEntity;
import org.springblade.anbiao.weixiu.VO.MaintenanceEntityV;
import org.springblade.anbiao.weixiu.VO.MaintenanceTZVO;
import org.springblade.anbiao.weixiu.entity.FittingEntity;
import org.springblade.anbiao.weixiu.VO.MaintenanceVO;
import org.springblade.anbiao.weixiu.entity.FittingsEntity;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiu.page.MaintenancePage;
import org.springblade.anbiao.weixiu.page.MaintenanceTZPage;

import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/4 14:33
 */
@Mapper
public interface MaintenanceMapper extends BaseMapper<MaintenanceEntity> {
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
	 * @param maintenancePage
	 * @return
	 */
	MaintenanceEntity selectAll(MaintenancePage maintenancePage );

	List<FittingsEntity> selectC(MaintenancePage maintenancePage);
	int selectTotal(MaintenancePage maintenancePage);

	/**
	 * 新增维修详细信息
	 * @return
	 */
	Boolean insertOne(MaintenanceEntity maintenanceEntity);
	Boolean insertB(FittingEntity fittingDTO);


	/**
	 * 修改维修
	 * @param
	 * @return
	 */
	Boolean updateMain(MaintenanceEntity maintenanceEntity);
	boolean updateB(FittingEntity fittingDTO);

	/**
	 * 删除
	 * @param
	 * @return
	 */
	Boolean deleteMain(MaintenancePage maintenancePage);

	List<MaintenanceEntityV> selectByDateList(@Param("deptId") String deptId,@Param("date") String date,@Param("type") String type);

	/**
	 * 维修隐患整改台账
	 * @param maintenanceTZPage
	 * @return
	 */
	List<MaintenanceTZVO> selectTZTJList(MaintenanceTZPage maintenanceTZPage);
	int selectTZTJTotal(MaintenanceTZPage maintenanceTZPage);

	List<MaintenanceEntity> selectNotAssemblyMaintenance(MaintenanceEntity maintenanceEntity);

	List<MaintenanceEntity> selectAssemblyMaintenance(MaintenanceEntity maintenanceEntity);

	List<AccidentReportsEntity> selectShiGu(AccidentReportsEntity accidentReportsEntity);

}
