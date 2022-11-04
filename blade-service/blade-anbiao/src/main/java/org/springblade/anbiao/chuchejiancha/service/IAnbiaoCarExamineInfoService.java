package org.springblade.anbiao.chuchejiancha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.chuchejiancha.page.AnbiaoCarExamineInfoPage;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
public interface IAnbiaoCarExamineInfoService extends IService<AnbiaoCarExamineInfo> {

	AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfo> selectCarExamineInfoPage(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage);

}
