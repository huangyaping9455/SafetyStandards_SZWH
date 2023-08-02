package org.springblade.anbiao.repairs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsDept;
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
public interface AnbiaoRepairsDeptMapper extends BaseMapper<AnbiaoRepairsDept> {

	List<AnbiaoRepairsDept> selectPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);
	int selectTotal(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);

	List<AnbiaoRepairsDept> selectBXDept(String deptId);


}
