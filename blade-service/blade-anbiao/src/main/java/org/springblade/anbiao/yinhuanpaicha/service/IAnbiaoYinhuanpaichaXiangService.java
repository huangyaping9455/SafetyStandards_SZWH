package org.springblade.anbiao.yinhuanpaicha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiang;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2022-09-08
 */
public interface IAnbiaoYinhuanpaichaXiangService extends IService<AnbiaoYinhuanpaichaXiang> {


	YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiang> selectYinhuanpaichaXiangPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);

}
