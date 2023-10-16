package org.springblade.anbiao.repairs.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsSpareInfo;
import org.springblade.anbiao.repairs.mapper.AnbiaoRepairsSpareInfoMapper;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsSpareInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2023-09-22
 */
@Service
@AllArgsConstructor
public class AnbiaoRepairsSpareInfoServiceImpl extends ServiceImpl<AnbiaoRepairsSpareInfoMapper, AnbiaoRepairsSpareInfo> implements IAnbiaoRepairsSpareInfoService {

	private AnbiaoRepairsSpareInfoMapper mapper;

	@Override
	public List<AnbiaoRepairsSpareInfo> selectByDeptIdList(String rppsRpId,String rppsType) {
		return mapper.selectByDeptIdList(rppsRpId,rppsType);
	}

	@Override
	public List<AnbiaoRepairsSpareInfo> selectByType(String rppsRpId) {
		return mapper.selectByType(rppsRpId);
	}
}
