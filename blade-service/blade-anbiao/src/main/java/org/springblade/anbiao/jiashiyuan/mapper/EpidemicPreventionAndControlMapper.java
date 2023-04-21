package org.springblade.anbiao.jiashiyuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.jiashiyuan.entity.DeptVehIntoArea;
import org.springblade.anbiao.jiashiyuan.entity.EpidemicPreventionAndControl;
import org.springblade.anbiao.jiashiyuan.entity.IntoArea;
import org.springblade.anbiao.jiashiyuan.page.EpidemicPreventionAndControlPage;
import org.springblade.anbiao.jiashiyuan.page.IntoAreaPage;

import java.util.List;

/**
 * Created by you on 2019/4/23.
 */
public interface EpidemicPreventionAndControlMapper extends BaseMapper<EpidemicPreventionAndControl> {

	/**
	 * 统计列表
	 * @param epidemicPreventionAndControlPage
	 * @return
	 */
	List<EpidemicPreventionAndControl> selectPageList(EpidemicPreventionAndControlPage epidemicPreventionAndControlPage);
	int selectTotal(EpidemicPreventionAndControlPage epidemicPreventionAndControlPage);

	/**
	 * 根据驾驶员ID查询数据
	 * @param jiashiyuanid
	 * @return
	 */
	EpidemicPreventionAndControl selectByIds(String jiashiyuanid,String date);

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
	List<IntoArea> selectDeptMXIntoAreaPMTJ(IntoAreaPage intoAreaPage);
	int selectDeptMXIntoAreaPMTotal(IntoAreaPage intoAreaPage);

	/**
	 * 企业车辆进区域报警及处理情况统计
	 * @param intoAreaPage
	 * @return
	 */
	List<DeptVehIntoArea> selectDeptAreaalarmPageList(IntoAreaPage intoAreaPage);
	int selectDeptAreaalarmTotal(IntoAreaPage intoAreaPage);


}
