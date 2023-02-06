package org.springblade.anbiao.labor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.VO.LaborVO;
import org.springblade.anbiao.labor.VO.graphicsVO;
import org.springblade.anbiao.labor.entity.Labor;
import org.springblade.anbiao.labor.entity.LaborEntity;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.labor.page.LaborPage;

import java.util.List;

/**
 * kj0301
 *
 * @Description :
 * @Author : long
 * @Date :2022/11/3 21:40
 */
@Mapper
public interface laborMapper extends BaseMapper<LaborEntity> {
	/**
	 * 劳保列表
	 *
	 * @param
	 * @param laborPage
	 * @return
	 */
	List<LaborVO> selectList(LaborPage laborPage);
	int selectTotal(LaborPage laborPage);

	LaborEntity selectAll(LaborPage laborPage);
	List<Labor> selectC(LaborPage laborPage);
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
	List<graphicsVO> selectGrapsihVO(String ali_name,String aliIssueDate,String aliDeptIds);

	/**
	 * 新增劳保详细信息
	 * @return
	 */
	Boolean insertOne(laborDTO laborDTO);
	Boolean insertA(Labor laborDTO);


	/**
	 * 修改劳保 后台管理
	 * @param
	 * @return
	 */
	Boolean updateLao(LaborEntity laborEntity);

	Boolean updateL(LaborlingquEntity laborlingqu);
	Boolean updateA(Labor labor);
	/**
	 * 删除
	 * @param
	 * @return
	 */
	Boolean deleteLao(laborDTO laborDTO);

	List<LaborEntity> selectInsurance(int aliDeptIds);
}
