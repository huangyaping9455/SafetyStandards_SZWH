package org.springblade.anbiao.yinhuanpaicha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiang;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2022-09-08
 */
public interface AnbiaoYinhuanpaichaXiangMapper extends BaseMapper<AnbiaoYinhuanpaichaXiang> {


	List<AnbiaoYinhuanpaichaXiang> selectYinhuanpaichaXiangPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);
	int selectYinhuanpaichaXiangTotal(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);


}
