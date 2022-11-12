package org.springblade.anbiao.labor.service;

import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.VO.graphicsVO;
import org.springblade.anbiao.labor.entity.Labor;
import org.springblade.anbiao.labor.entity.LaborEntity;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.labor.page.LaborPage;

import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/3 21:40
 */
public interface laborService {
	/**
	 * 劳保列表信息
	 *
	 * @param
	 * @return
	 */
	LaborPage selectPage(LaborPage laborPage);

	/**
	 * 查询信息
	 * @param laborPage
	 * @return
	 */
	LaborEntity selectAll(LaborPage laborPage);
	List<Labor> selectC(LaborPage laborPage);

	/**
	 * 新增 劳保
	 * @param
	 * @return
	 */
	Boolean insertOne(laborDTO laborDTO);
	Boolean insertA(Labor labor);

	/**
	 * 返回劳保图形统计
	 */
	graphicsVO selectGraphics(String ali_name);

	/**
	 * 删除
	 * @param
	 * @return
	 */
	Boolean deleteAccident(laborDTO laborDTO);

	/**
	 * 修改
	 * @return
	 */
	Boolean updateAccident(LaborEntity laborEntity);
	Boolean updateA(Labor labor);
	Boolean updateL(LaborlingquEntity laborlingqu);
}
