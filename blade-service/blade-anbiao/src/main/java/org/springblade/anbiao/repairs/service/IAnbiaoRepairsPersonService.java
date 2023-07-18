package org.springblade.anbiao.repairs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPerson;
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
public interface IAnbiaoRepairsPersonService extends IService<AnbiaoRepairsPerson> {

	AnbiaoRepairsDeptPage<AnbiaoRepairsPerson> selectPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);

	List<AnbiaoRepairsPerson> selectPersonByDeptId(String deptId);

}
