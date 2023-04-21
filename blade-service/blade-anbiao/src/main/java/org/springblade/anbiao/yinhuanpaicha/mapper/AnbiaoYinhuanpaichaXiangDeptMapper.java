package org.springblade.anbiao.yinhuanpaicha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDept;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoYinhuanpaichaXiangDeptVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2022-09-14
 */
public interface AnbiaoYinhuanpaichaXiangDeptMapper extends BaseMapper<AnbiaoYinhuanpaichaXiangDept> {

	List<AnbiaoYinhuanpaichaXiangDeptVO> selectYinhuanpaichaXiangDeptPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);
	int selectYinhuanpaichaXiangDeptTotal(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);




}
