package org.springblade.anbiao.jiashiyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.jiashiyuan.entity.DeptVehIntoArea;
import org.springblade.anbiao.jiashiyuan.entity.EpidemicPreventionAndControl;
import org.springblade.anbiao.jiashiyuan.entity.IntoArea;
import org.springblade.anbiao.jiashiyuan.page.EpidemicPreventionAndControlPage;
import org.springblade.anbiao.jiashiyuan.page.IntoAreaPage;

/**
 * Created by you on 2019/4/22.
 */
public interface IEpidemicPreventionAndControlService extends IService<EpidemicPreventionAndControl> {

	/**
	 * 统计列表
	 * @param epidemicPreventionAndControlPage
	 * @return
	 */
	EpidemicPreventionAndControlPage<EpidemicPreventionAndControl> selectPageList(EpidemicPreventionAndControlPage epidemicPreventionAndControlPage);

	/**
	 * 根据驾驶员ID查询数据
	 * @param jiashiyuanid
	 * @return
	 */
	EpidemicPreventionAndControl selectById(String jiashiyuanid,String date);

	/**
	 * 新增
	 * @param epidemicPreventionAndControl
	 * @return
	 */
	boolean insertSelective(EpidemicPreventionAndControl epidemicPreventionAndControl);

	/**
	 * 修改
	 * @param epidemicPreventionAndControl
	 * @return
	 */
	boolean updateSelective(EpidemicPreventionAndControl epidemicPreventionAndControl);

	/**
	 * 企业端进区域报警明细
	 * @param intoAreaPage
	 * @return
	 */
	IntoAreaPage<IntoArea> selectDeptMXIntoAreaPMTJ(IntoAreaPage intoAreaPage);

	/**
	 * 企业车辆进区域报警及处理情况统计
	 * @param intoAreaPage
	 * @return
	 */
	IntoAreaPage<DeptVehIntoArea> selectDeptAreaalarmPageList(IntoAreaPage intoAreaPage);

}
