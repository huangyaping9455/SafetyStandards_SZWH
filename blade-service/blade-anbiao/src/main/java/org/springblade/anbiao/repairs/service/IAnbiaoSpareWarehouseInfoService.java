package org.springblade.anbiao.repairs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareWarehouseInfo;
import org.springblade.anbiao.repairs.page.AnbiaoSpareWarehouseInfoPage;

import java.util.List;

/**
 * <p>
 * 备件仓库管理 服务类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface IAnbiaoSpareWarehouseInfoService extends IService<AnbiaoSpareWarehouseInfo> {

	AnbiaoSpareWarehouseInfoPage<AnbiaoSpareWarehouseInfo> selectAllPage(AnbiaoSpareWarehouseInfoPage anbiaoSpareWarehouseInfoPage);

	AnbiaoSpareWarehouseInfo selectMaxXuhao(String deptId);

	List<AnbiaoSpareWarehouseInfo> selectByDeptIdList(String deptId);

}
