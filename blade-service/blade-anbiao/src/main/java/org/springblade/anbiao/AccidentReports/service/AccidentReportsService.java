package org.springblade.anbiao.AccidentReports.service;

import org.springblade.anbiao.AccidentReports.DTO.AccidentReportsDTO;
import org.springblade.anbiao.AccidentReports.VO.AccidentReportsVO;
import org.springblade.anbiao.AccidentReports.page.AccidentPage;

import java.util.Date;
import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/2 13:48
 */
public interface AccidentReportsService {
	/**
	 * 事故报告列表信息
	 *
	 * @param
	 * @param accidentPage
	 * @return
	 */
	List<AccidentReportsVO> selectList(AccidentPage accidentPage);

	/**
	 * 事故报告详细信息
	 *
	 * @param
	 * @return
	 */
	List<AccidentReportsVO> selectAll(String rid);

	/**
	 * 新增 事故报告
	 * @param
	 * @return
	 */
	Boolean insertOne(AccidentReportsDTO accidentReportsDTO);


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
	Boolean updateAccident(AccidentReportsDTO accidentReportsDTO);
}
