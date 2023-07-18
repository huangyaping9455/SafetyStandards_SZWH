package org.springblade.anbiao.repairs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPerson;
import org.springblade.anbiao.repairs.page.AnbiaoRepairsDeptPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-07-17
 */
public interface AnbiaoRepairsPersonMapper extends BaseMapper<AnbiaoRepairsPerson> {

	List<AnbiaoRepairsPerson> selectPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);
	int selectTotal(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);

	List<AnbiaoRepairsPerson> selectPersonByDeptId(String deptId);

}
