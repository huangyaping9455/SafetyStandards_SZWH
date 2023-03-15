package org.springblade.anbiao.lawsRegulations.mapper;

import org.springblade.anbiao.lawsRegulations.entity.AnbiaoLawsRegulations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.anbiao.lawsRegulations.page.AnBiaoLawsRegulationsPage;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyp
 * @since 2023-03-10
 */
public interface AnbiaoLawsRegulationsMapper extends BaseMapper<AnbiaoLawsRegulations> {

	/**
	 * 添加法律法规
	 * @param anBiaoLawsRegulations
	 * @return
	 */
	boolean insertLawsRegulationsSelective(AnbiaoLawsRegulations anBiaoLawsRegulations);

	/**
	 * 法律法规列表
	 * @param anBiaoLawsRegulationsPage
	 * @return
	 */
	List<AnbiaoLawsRegulations> selectlawsRegulationsGetAll(AnBiaoLawsRegulationsPage anBiaoLawsRegulationsPage);
	int selectlawsRegulationsGetAllTotal(AnBiaoLawsRegulationsPage anBiaoLawsRegulationsPage);

	/**
	 * 根据数据获取法律法规详情
	 * @param id
	 * @return
	 */
	AnbiaoLawsRegulations selectlawsRegulationsById(Integer id,String name);

	/**
	 * 编辑法律法规
	 * @param anBiaoLawsRegulations
	 * @return
	 */
	boolean updateLawsRegulationsSelective(AnbiaoLawsRegulations anBiaoLawsRegulations);

}
