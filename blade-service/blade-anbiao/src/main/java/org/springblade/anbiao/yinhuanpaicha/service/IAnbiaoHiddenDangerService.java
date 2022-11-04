package org.springblade.anbiao.yinhuanpaicha.service;

import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.yinhuanpaicha.page.AnbiaoHiddenDangerPage;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;

import java.util.List;

/**
 * <p>
 * 隐患排查信息 服务类
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
public interface IAnbiaoHiddenDangerService extends IService<AnbiaoHiddenDanger> {

	AnbiaoHiddenDangerPage<AnbiaoHiddenDangerVO> selectPage(AnbiaoHiddenDangerPage anbiaoHiddenDangerPage);

}
