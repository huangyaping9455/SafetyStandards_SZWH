package org.springblade.anbiao.AccidentReports.mapper;

import org.springblade.anbiao.AccidentReports.DTO.AccidentReportsDTO;
import org.springblade.anbiao.AccidentReports.VO.AccidentReportsVO;
import org.springblade.anbiao.AccidentReports.page.AccidentPage;

import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/2 14:10
 */
public interface AccidentReportsMapper {
	/**
	 * 事故报告列表
	 *
	 * @param
	 * @param accidentPage
	 * @return
	 */
	List<AccidentReportsVO> selectList(AccidentPage accidentPage);
//	int selectTotal();

	/**
	 * 事故报告详细信息
	 *
	 * @param
	 * @return
	 */
	List<AccidentReportsVO> selectAll(String id );


	/**
	 * 新增事故报告详细信息
	 * @return
	 */
	Boolean insertOne(AccidentReportsDTO accidentReportsDTO);



	/**
	 * 修改事故报告 后台管理
	 * @param
	 * @return
	 */
	Boolean updateAcc(AccidentReportsDTO accidentReportsDTO);

	/**
	 * 删除
	 * @param
	 * @return
	 */
	Boolean deleteAcc(String id);
}
