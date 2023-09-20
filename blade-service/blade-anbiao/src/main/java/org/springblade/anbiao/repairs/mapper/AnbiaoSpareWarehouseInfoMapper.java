package org.springblade.anbiao.repairs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareWarehouseInfo;
import org.springblade.anbiao.repairs.page.AnbiaoSpareWarehouseInfoPage;

import java.util.List;

/**
 * <p>
 * 备件仓库管理 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface AnbiaoSpareWarehouseInfoMapper extends BaseMapper<AnbiaoSpareWarehouseInfo> {

	List<AnbiaoSpareWarehouseInfo> selectAllPage(AnbiaoSpareWarehouseInfoPage anbiaoSpareWarehouseInfoPage);
	int selectAllTotal(AnbiaoSpareWarehouseInfoPage anbiaoSpareWarehouseInfoPage);

	AnbiaoSpareWarehouseInfo selectMaxXuhao(String deptId);

	List<AnbiaoSpareWarehouseInfo> selectByDeptIdList(String deptId);

}
