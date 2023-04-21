package org.springblade.anbiao.yinhuanpaicha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDeptInfo;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoYinhuanpaichaXiangDeptInfoVO;
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
public interface AnbiaoYinhuanpaichaXiangDeptInfoMapper extends BaseMapper<AnbiaoYinhuanpaichaXiangDeptInfo> {


	List<AnbiaoYinhuanpaichaXiangDeptVO> selectYinhuanpaichaDeptPlanPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);
	int selectYinhuanpaichaDeptPlanTotal(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);

	List<AnbiaoYinhuanpaichaXiangDeptInfoVO> selectDeptDriverPlan(Integer deptId,String jsyid,String vehid);

	List<AnbiaoYinhuanpaichaXiangDeptVO> selectDeptYHPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);
	int selectDeptYHTotal(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);

	List<AnbiaoYinhuanpaichaXiangDeptInfoVO> selectYinhuanpaichaDeptPlanRemarkPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);
	int selectYinhuanpaichaDeptPlanRemarkTotal(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);

}
