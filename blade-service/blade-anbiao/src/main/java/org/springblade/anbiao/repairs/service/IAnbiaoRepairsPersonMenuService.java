package org.springblade.anbiao.repairs.service;

import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPersonMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-08-03
 */
public interface IAnbiaoRepairsPersonMenuService extends IService<AnbiaoRepairsPersonMenu> {

	List<AnbiaoRepairsPersonMenu> selectRepairsPersonMenu(String driverId);

}
