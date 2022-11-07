package org.springblade.anbiao.jiaoyupeixun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTrainingDetail;
import org.springblade.anbiao.jiaoyupeixun.mapper.AnbiaoSafetyTrainingDetailMapper;
import org.springblade.anbiao.jiaoyupeixun.service.IAnbiaoSafetyTrainingDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 安全会议参会记录 服务实现类
 * </p>
 *
 * @author hyp
 * @since 2022-11-01
 */
@Service
@AllArgsConstructor
public class AnbiaoSafetyTrainingDetailServiceImpl extends ServiceImpl<AnbiaoSafetyTrainingDetailMapper, AnbiaoSafetyTrainingDetail> implements IAnbiaoSafetyTrainingDetailService {

	private AnbiaoSafetyTrainingDetailMapper mapper;

	@Override
	public void deleteByAadAstIds(String aadAstIds) {
		mapper.deleteByAadAstIds(aadAstIds);
	}
}
