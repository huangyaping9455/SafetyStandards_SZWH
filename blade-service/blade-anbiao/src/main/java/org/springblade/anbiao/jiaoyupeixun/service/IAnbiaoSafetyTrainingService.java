package org.springblade.anbiao.jiaoyupeixun.service;

import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.jiaoyupeixun.page.AnbiaoSafetyTrainingPage;
import org.springblade.anbiao.jiaoyupeixun.vo.AnbiaoSafetyTrainingVO;

import java.util.List;

/**
 * <p>
 * 安全生产培训(线下) 服务类
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
public interface IAnbiaoSafetyTrainingService extends IService<AnbiaoSafetyTraining> {

	AnbiaoSafetyTrainingPage<AnbiaoSafetyTrainingVO> selectPage(AnbiaoSafetyTrainingPage anbiaoSafetyTrainingPage);

}
