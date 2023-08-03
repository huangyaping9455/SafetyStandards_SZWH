package org.springblade.anbiao.repairs.mapper;

import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPersonMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-08-03
 */
public interface AnbiaoRepairsPersonMenuMapper extends BaseMapper<AnbiaoRepairsPersonMenu> {


	List<AnbiaoRepairsPersonMenu> selectRepairsPersonMenu(String driverId);

}
