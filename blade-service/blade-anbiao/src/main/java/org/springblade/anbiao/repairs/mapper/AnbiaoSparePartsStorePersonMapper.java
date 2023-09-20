package org.springblade.anbiao.repairs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStorePerson;
import org.springblade.anbiao.repairs.page.AnbiaoSparePartsStorePersonPage;

import java.util.List;

/**
 * <p>
 * 备件库管理 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface AnbiaoSparePartsStorePersonMapper extends BaseMapper<AnbiaoSparePartsStorePerson> {

	List<AnbiaoSparePartsStorePerson> selectAllPage(AnbiaoSparePartsStorePersonPage anbiaoSparePartsStorePersonPage);
	int selectAllTotal(AnbiaoSparePartsStorePersonPage anbiaoSparePartsStorePersonPage);

	AnbiaoSparePartsStorePerson selectMaxXuhao(String deptId);

}
