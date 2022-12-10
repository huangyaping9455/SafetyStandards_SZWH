package org.springblade.anbiao.AccidentReports.service.Impl;
;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AccidentReportsServiceImpl implements AccidentReportsService {

	private AccidentReportsMapper mapper;

	@Override
	public AccidentPage selectList(AccidentPage accidentPage) {
		int total = mapper.selectTotal(accidentPage);
		Integer AccPagetotal = 0;
		if (accidentPage.getSize() == 0) {
			if (accidentPage.getTotal() == 0) {
				accidentPage.setTotal(total);
			}
			if (accidentPage.getTotal() == 0) {
				return accidentPage;
			} else {
				List<AccidentReportsVO> accidentReportsVOS = mapper.selectList(accidentPage);
				accidentPage.setRecords(accidentReportsVOS);
				return accidentPage;
			}
		}
		if (total > 0) {
			if (total % accidentPage.getSize() == 0) {
				AccPagetotal = total / accidentPage.getSize();
			} else {
				AccPagetotal = total / accidentPage.getSize() + 1;
			}
		}
		if (AccPagetotal < accidentPage.getCurrent()) {
			return accidentPage;
		} else {
			accidentPage.setPageTotal(AccPagetotal);
			Integer offsetNo = 0;
			if (accidentPage.getCurrent() > 1) {
				offsetNo = accidentPage.getSize() * (accidentPage.getCurrent() - 1);
			}
			accidentPage.setTotal(total);
			accidentPage.setOffsetNo(offsetNo);
			List<AccidentReportsVO> accidentReportsVOS = mapper.selectList(accidentPage);
			accidentPage.setRecords(accidentReportsVOS);
			return accidentPage;
		}
	}

	@Override
	public AccidentReportsVO selectAll(AccidentPage accidentPage) {
		return mapper.selectAll(accidentPage);
	}

	@Override
	public Boolean insertOne(AccidentReportsDTO accidentReportsDTO) {
		return mapper.insertOne(accidentReportsDTO);
	}

	@Override
	public Boolean deleteAccident(AccidentPage accidentPage) {
		return mapper.deleteAcc(accidentPage);
	}

	@Override
	public Boolean updateAccident(AccidentReportsDTO accidentReportsDTO) {
		return mapper.updateAcc(accidentReportsDTO);
	}
}
