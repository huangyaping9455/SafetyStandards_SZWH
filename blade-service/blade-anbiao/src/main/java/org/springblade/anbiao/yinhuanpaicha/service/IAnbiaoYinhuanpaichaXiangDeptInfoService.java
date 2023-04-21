package org.springblade.anbiao.yinhuanpaicha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDeptInfo;
import org.springblade.anbiao.yinhuanpaicha.page.YinhuanpaichaXiangPage;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoYinhuanpaichaXiangDeptInfoVO;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoYinhuanpaichaXiangDeptVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2022-09-14
 */
public interface IAnbiaoYinhuanpaichaXiangDeptInfoService extends IService<AnbiaoYinhuanpaichaXiangDeptInfo> {

	YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO> selectYinhuanpaichaDeptPlanPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);

	List<AnbiaoYinhuanpaichaXiangDeptInfoVO> selectDeptDriverPlan(Integer deptId,String jsyid,String vehid);

	YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptVO> selectDeptYHPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);

	YinhuanpaichaXiangPage<AnbiaoYinhuanpaichaXiangDeptInfoVO> selectYinhuanpaichaDeptPlanRemarkPage(YinhuanpaichaXiangPage yinhuanpaichaXiangPage);

}
