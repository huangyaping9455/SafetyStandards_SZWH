package org.springblade.anbiao.jiaoyupeixun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import feign.Param;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTrainingDetail;

import java.util.List;

/**
 * <p>
 * 安全会议参会记录 Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2022-11-01
 */
public interface AnbiaoSafetyTrainingDetailMapper extends BaseMapper<AnbiaoSafetyTrainingDetail> {

	void deleteByAadAstIds(String aadAstIds);
//	void deleteByAadAstIds(@Param("aadAstIds") List<String> aadAstIds);

}
