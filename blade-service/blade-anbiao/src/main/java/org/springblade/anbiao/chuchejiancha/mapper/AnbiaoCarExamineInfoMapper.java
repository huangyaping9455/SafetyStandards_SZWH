package org.springblade.anbiao.chuchejiancha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.chuchejiancha.page.AnBiaoCheckCarPage;
import org.springblade.anbiao.chuchejiancha.page.AnbiaoCarExamineInfoPage;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
public interface AnbiaoCarExamineInfoMapper extends BaseMapper<AnbiaoCarExamineInfo> {

	List<AnbiaoCarExamineInfo> selectCarExamineInfoPage(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);
	int selectCarExamineInfoTotal(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);


	List<AnbiaoCarExamineInfo> selectAnBiaoCheckCarALLPage(AnBiaoCheckCarPage anbiaoCarExamineInfoPage);


}
