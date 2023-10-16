package org.springblade.anbiao.repairs.service;

import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStore;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.repairs.page.AnbiaoSparePartsStorePage;

import java.util.List;

/**
 * <p>
 * 备件库管理 服务类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface IAnbiaoSparePartsStoreService extends IService<AnbiaoSparePartsStore> {

	AnbiaoSparePartsStorePage<AnbiaoSparePartsStore> selectAllPage(AnbiaoSparePartsStorePage anbiaoSparePartsStorePage);

	AnbiaoSparePartsStore selectMaxXuhao(String deptId);

	List<AnbiaoSparePartsStore> selectByDeptIdList(String deptId,String spWarehouseId);

}
