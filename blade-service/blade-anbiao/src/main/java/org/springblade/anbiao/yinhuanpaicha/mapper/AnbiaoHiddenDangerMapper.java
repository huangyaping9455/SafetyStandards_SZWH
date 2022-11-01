package org.springblade.anbiao.yinhuanpaicha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;
import org.springblade.anbiao.yinhuanpaicha.page.AnbiaoHiddenDangerPage;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;

import java.util.List;

/**
 * <p>
 * 隐患排查信息 Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
public interface AnbiaoHiddenDangerMapper extends BaseMapper<AnbiaoHiddenDanger> {

	List<AnbiaoHiddenDangerVO> selectPage(AnbiaoHiddenDangerPage anbiaoHiddenDangerPage);
	int selectTotal(AnbiaoHiddenDangerPage anbiaoHiddenDangerPage);



}
