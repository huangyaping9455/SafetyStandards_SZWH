package org.springblade.anbiao.labor.service.impl;


import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.VO.LaborVO;
import org.springblade.anbiao.labor.VO.graphicsVO;
import org.springblade.anbiao.labor.mapper.laborMapper;
import org.springblade.anbiao.labor.page.LaborPage;
import org.springblade.anbiao.labor.service.laborService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/3 21:40
 */
@Service
public class laborServiceImpl implements laborService {

	private laborMapper mapper;

	@Override
	public LaborPage selectList(LaborPage laborPage,String id,Date startTime,Date endTime) {
		int total = mapper.selectTotal(laborPage);
		Integer safelPagetotal = 0;
		if (laborPage.getSize() == 0) {
			if (laborPage.getTotal() == 0) {
				laborPage.setTotal(total);
			}
			if (laborPage.getTotal() == 0) {
				return laborPage;
			} else {
				List<LaborVO> laborVOS = mapper.selectList(laborPage,id,startTime,endTime);
				laborPage.setRecords(laborVOS);
				return laborPage;
			}
		}
		if (total > 0) {
			if (total % laborPage.getSize() == 0) {
				safelPagetotal = total / laborPage.getSize();
			} else {
				safelPagetotal = total / laborPage.getSize() + 1;
			}
		}
		if (safelPagetotal < laborPage.getCurrent()) {
			return laborPage;
		} else {
			laborPage.setPageTotal(safelPagetotal);
			Integer offsetNo = 0;
			if (laborPage.getCurrent() > 1) {
				offsetNo = laborPage.getSize() * (laborPage.getCurrent() - 1);
			}
			laborPage.setTotal(total);
			laborPage.setOffsetNo(offsetNo);
			List<LaborVO> laborVOS = mapper.selectList(laborPage,id,startTime,endTime);
			laborPage.setRecords(laborVOS);
			return laborPage;
		}

	}


	@Override
	public Boolean insertOne(laborDTO laborDTO) {
		return mapper.insertOne(laborDTO);
	}

	@Override
	public graphicsVO selectGraphics(String ali_name) {
		graphicsVO graphicsVO = mapper.selectGrapsihVO(ali_name);
		Integer number = graphicsVO.getAli_issue_people_number();
		Integer quantity = graphicsVO.getAli_issue_quantity();
		/**计算百分比**/
		int v = (int) ((new BigDecimal((float) number / quantity).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())*100);
		String str = v+"%";
		if(graphicsVO.getAli_application_scope().equals("管理人员")){
			graphicsVO.setGuanli(str);
		}
		if(graphicsVO.getAli_application_scope().equals("驾驶人员")){
			graphicsVO.setJiashiyuan(str);
		}
		if(graphicsVO.getAli_application_scope().equals("其他人员")){
			graphicsVO.setQita(str);
		}
		return graphicsVO;
	}


	@Override
	public Boolean deleteAccident(String ali_ids) {
		return mapper.deleteLao(ali_ids);
	}

	@Override
	public Boolean updateAccident(laborDTO laborDTO) {
		return mapper.updateLao(laborDTO);
	}
}
