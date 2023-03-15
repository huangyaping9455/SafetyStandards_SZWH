package org.springblade.anbiao.lawsRegulations.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.anbiao.lawsRegulations.entity.AnbiaoLawsRegulations;
import org.springblade.anbiao.lawsRegulations.page.AnBiaoLawsRegulationsPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyp
 * @since 2023-03-10
 */
public interface IAnbiaoLawsRegulationsService extends IService<AnbiaoLawsRegulations> {

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
	AnBiaoLawsRegulationsPage<AnbiaoLawsRegulations> selectlawsRegulationsGetAll(AnBiaoLawsRegulationsPage anBiaoLawsRegulationsPage);

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
