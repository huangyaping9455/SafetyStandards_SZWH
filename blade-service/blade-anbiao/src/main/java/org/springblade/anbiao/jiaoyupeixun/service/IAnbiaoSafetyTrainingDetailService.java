package org.springblade.anbiao.jiaoyupeixun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import feign.Param;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTrainingDetail;

import java.util.List;

/**
 * <p>
 * 安全会议参会记录 服务类
 * </p>
 *
 * @author hyp
 * @since 2022-11-01
 */
public interface IAnbiaoSafetyTrainingDetailService extends IService<AnbiaoSafetyTrainingDetail> {

	void deleteByAadAstIds(String aadAstIds);

}
