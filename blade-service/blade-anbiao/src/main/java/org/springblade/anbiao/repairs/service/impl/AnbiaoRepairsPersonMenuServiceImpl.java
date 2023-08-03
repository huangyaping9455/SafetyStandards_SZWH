package org.springblade.anbiao.repairs.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPersonMenu;
import org.springblade.anbiao.repairs.mapper.AnbiaoRepairsPersonMenuMapper;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsPersonMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2023-08-03
 */
@Service
@AllArgsConstructor
public class AnbiaoRepairsPersonMenuServiceImpl extends ServiceImpl<AnbiaoRepairsPersonMenuMapper, AnbiaoRepairsPersonMenu> implements IAnbiaoRepairsPersonMenuService {

	private AnbiaoRepairsPersonMenuMapper personMenuMapper;

	@Override
	public List<AnbiaoRepairsPersonMenu> selectRepairsPersonMenu(String driverId) {
		return personMenuMapper.selectRepairsPersonMenu(driverId);
	}
}
