package org.springblade.anbiao.anquanhuiyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;

import java.util.List;

/**
 * <p>
 * 安全会议记录表 Mapper 接口
 * </p>
 *
 * @author lmh
 * @since 2022-11-01
 */
public interface AnbiaoAnquanhuiyiMapper extends BaseMapper<AnbiaoAnquanhuiyi> {

	List<AnbiaoAnquanhuiyi> selectGetAll(AnQuanHuiYiPage anQuanHuiYiPage);
	int selectGetAllTotal(AnQuanHuiYiPage anQuanHuiYiPage);
}
