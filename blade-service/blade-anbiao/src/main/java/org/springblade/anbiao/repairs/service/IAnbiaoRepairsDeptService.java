package org.springblade.anbiao.repairs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsDept;
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
public interface IAnbiaoRepairsDeptService extends IService<AnbiaoRepairsDept> {

	AnbiaoRepairsDeptPage<AnbiaoRepairsDept> selectPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);

	List<AnbiaoRepairsDept> selectBXDept(String deptId);

}
