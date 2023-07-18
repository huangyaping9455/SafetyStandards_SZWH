package org.springblade.anbiao.repairs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsInfo;
import org.springblade.anbiao.repairs.page.AnbiaoRepairsDeptPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-07-17
 */
public interface IAnbiaoRepairsInfoService extends IService<AnbiaoRepairsInfo> {

	AnbiaoRepairsDeptPage<AnbiaoRepairsInfo> selectPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);

	AnbiaoRepairsInfo selectMaxXuhao(String deptId);

	AnbiaoRepairsDeptPage<AnbiaoRepairsInfo> selectDriverPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);

}
