package org.springblade.anbiao.jiaoyupeixun.mapper;

import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.jiaoyupeixun.page.AnbiaoSafetyTrainingPage;
import org.springblade.anbiao.jiaoyupeixun.vo.AnbiaoSafetyTrainingVO;

import java.util.List;

/**
 * <p>
 * 安全生产培训(线下) Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
public interface AnbiaoSafetyTrainingMapper extends BaseMapper<AnbiaoSafetyTraining> {

	List<AnbiaoSafetyTrainingVO> selectListPage(AnbiaoSafetyTrainingPage anbiaoSafetyTrainingPage);
	int selectTotal(AnbiaoSafetyTrainingPage anbiaoSafetyTrainingPage);



}
