package org.springblade.anbiao.anquanhuiyi.service;

import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiSource;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2022-11-17
 */
public interface IAnbiaoAnquanhuiyiSourceService extends IService<AnbiaoAnquanhuiyiSource> {


	AnQuanHuiYiPage<AnbiaoAnquanhuiyiSource> selectGetAll(AnQuanHuiYiPage anQuanHuiYiPage);

}
