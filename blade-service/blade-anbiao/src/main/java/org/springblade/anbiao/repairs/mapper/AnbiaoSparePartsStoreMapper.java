package org.springblade.anbiao.repairs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStore;
import org.springblade.anbiao.repairs.page.AnbiaoSparePartsStorePage;

import java.util.List;

/**
 * <p>
 * 备件库管理 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface AnbiaoSparePartsStoreMapper extends BaseMapper<AnbiaoSparePartsStore> {

	List<AnbiaoSparePartsStore> selectAllPage(AnbiaoSparePartsStorePage anbiaoSparePartsStorePage);
	int selectAllTotal(AnbiaoSparePartsStorePage anbiaoSparePartsStorePage);

	AnbiaoSparePartsStore selectMaxXuhao(String deptId);

	List<AnbiaoSparePartsStore> selectByDeptIdList(String deptId,String spWarehouseId);

}
