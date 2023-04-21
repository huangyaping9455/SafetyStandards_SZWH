package org.springblade.anbiao.yinhuanpaicha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDept;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoYinhuanpaichaXiangDeptVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2022-09-14
 */
public interface IAnbiaoYinhuanpaichaXiangDeptService extends IService<AnbiaoYinhuanpaichaXiangDept> {

	YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO> selectYinhuanpaichaXiangDeptPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);

}
