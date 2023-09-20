package org.springblade.anbiao.repairs.service;

import org.springblade.anbiao.repairs.entity.AnbiaoSparePartsStorePerson;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.repairs.page.AnbiaoSparePartsStorePersonPage;

import java.util.List;

/**
 * <p>
 * 备件库管理 服务类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
public interface IAnbiaoSparePartsStorePersonService extends IService<AnbiaoSparePartsStorePerson> {

	AnbiaoSparePartsStorePersonPage<AnbiaoSparePartsStorePerson> selectAllPage(AnbiaoSparePartsStorePersonPage anbiaoSparePartsStorePersonPage);

	AnbiaoSparePartsStorePerson selectMaxXuhao(String deptId);

}
