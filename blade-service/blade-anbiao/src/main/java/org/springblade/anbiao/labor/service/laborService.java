package org.springblade.anbiao.labor.service;

import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.VO.LaborVO;
import org.springblade.anbiao.labor.VO.graphicsVO;
import org.springblade.anbiao.labor.page.LaborPage;

import java.util.Date;
import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/3 21:40
 */
public interface laborService {
	/**
	 * 劳保列表信息
	 * @param
	 * @return
	 */
	List<LaborVO> selectList(LaborPage laborPage);

//	/**
//	 * 劳保详细信息
//	 * @param
//	 * @return
//	 */
//	List<LaborVO> selectAll();

	/**
	 * 新增 劳保
	 * @param
	 * @return
	 */
	Boolean insertOne(laborDTO laborDTO);

	/**
	 * 返回劳保图形统计
	 */
	graphicsVO selectGraphics(String ali_name);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Boolean deleteAccident(String id);

	/**
	 * 修改
	 * @return
	 */
	Boolean updateAccident(laborDTO laborDTO);
}
