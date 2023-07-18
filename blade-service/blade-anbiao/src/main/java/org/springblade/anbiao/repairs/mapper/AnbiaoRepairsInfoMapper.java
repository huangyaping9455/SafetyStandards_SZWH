package org.springblade.anbiao.repairs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsInfo;
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
public interface AnbiaoRepairsInfoMapper extends BaseMapper<AnbiaoRepairsInfo> {

	List<AnbiaoRepairsInfo> selectPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);
	int selectTotal(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);

	AnbiaoRepairsInfo selectMaxXuhao(String deptId);

	List<AnbiaoRepairsInfo> selectDriverPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);
	int selectDriverTotal(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage);

}
