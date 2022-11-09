package org.springblade.anbiao.labor.mapper;

import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.VO.LaborVO;
import org.springblade.anbiao.labor.VO.graphicsVO;
import org.springblade.anbiao.labor.page.LaborPage;

import java.util.Date;
import java.util.List;

/**
 * kj0301
 *
 * @Description :
 * @Author : long
 * @Date :2022/11/3 21:40
 */
public interface laborMapper {
	/**
	 * 劳保列表
	 *
	 * @param
	 * @param laborPage
	 * @return
	 */
	List<LaborVO> selectList(LaborPage laborPage);
	int selectTotal(LaborPage laborPage);

//	/**
//	 * 劳保详细信息
//	 *
//	 * @param
//	 * @return
//	 */
//	List<LaborVO> selectAll();

	/**
	 * 图形统计查询
	 * @return
	 */
	graphicsVO selectGrapsihVO(String ali_name);

	/**
	 * 新增劳保详细信息
	 * @return
	 */
	Boolean insertOne(laborDTO laborDTO);



	/**
	 * 修改劳保 后台管理
	 * @param
	 * @return
	 */
	Boolean updateLao(laborDTO laborDTO);

	/**
	 * 删除
	 * @param
	 * @return
	 */
	Boolean deleteLao(String ali_ids);
}
