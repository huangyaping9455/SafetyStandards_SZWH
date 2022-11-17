package org.springblade.anbiao.anquanhuiyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyiSource;
import org.springblade.anbiao.anquanhuiyi.page.AnQuanHuiYiPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2022-11-17
 */
public interface AnbiaoAnquanhuiyiSourceMapper extends BaseMapper<AnbiaoAnquanhuiyiSource> {

	List<AnbiaoAnquanhuiyiSource> selectGetAll(AnQuanHuiYiPage anQuanHuiYiPage);
	int selectGetAllTotal(AnQuanHuiYiPage anQuanHuiYiPage);

}
