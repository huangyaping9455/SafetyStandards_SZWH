package org.springblade.anbiao.anquanhuiyi.service;

import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;

import java.util.List;

/**
 * <p>
 * 安全会议记录表 服务类
 * </p>
 *
 * @author lmh
 * @since 2022-11-01
 */
public interface IAnbiaoAnquanhuiyiService extends IService<AnbiaoAnquanhuiyi> {

	AnQuanHuiYiPage<AnbiaoAnquanhuiyi> selectGetAll(AnQuanHuiYiPage anQuanHuiYiPage);



}
