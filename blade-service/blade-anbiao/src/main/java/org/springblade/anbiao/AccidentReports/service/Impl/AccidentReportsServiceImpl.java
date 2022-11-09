package org.springblade.anbiao.AccidentReports.service.Impl;
;
import org.springblade.anbiao.AccidentReports.DTO.AccidentReportsDTO;
import org.springblade.anbiao.AccidentReports.VO.AccidentReportsVO;
import org.springblade.anbiao.AccidentReports.mapper.AccidentReportsMapper;
import org.springblade.anbiao.AccidentReports.page.AccidentPage;
import org.springblade.anbiao.AccidentReports.service.AccidentReportsService;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/2 13:48
 */
@Service
public class AccidentReportsServiceImpl implements AccidentReportsService {


	private AccidentReportsMapper mapper;

	@Override
	public List<AccidentReportsVO> selectList(AccidentPage accidentPage) {

		return null;
	}

	@Override
	public List<AccidentReportsVO> selectAll(String rid) {
		return mapper.selectAll(rid);
	}

	@Override
	public Boolean insertOne(AccidentReportsDTO accidentReportsDTO) {
		return mapper.insertOne(accidentReportsDTO);
	}

	@Override
	public Boolean deleteAccident(String id) {
		return mapper.deleteAcc(id);
	}

	@Override
	public Boolean updateAccident(AccidentReportsDTO accidentReportsDTO) {
		return mapper.updateAcc(accidentReportsDTO);
	}
}
