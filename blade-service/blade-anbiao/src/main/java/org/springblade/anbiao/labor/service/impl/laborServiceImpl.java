package org.springblade.anbiao.labor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springblade.anbiao.SafeInvestment.VO.SafeInvestmentVO;
import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.VO.LaborVO;
import org.springblade.anbiao.labor.VO.graphicsVO;
import org.springblade.anbiao.labor.mapper.laborMapper;
import org.springblade.anbiao.labor.page.LaborPage;
import org.springblade.anbiao.labor.service.laborService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
	public List<LaborVO> selectList(LaborPage laborPage) {
		int pageSize = laborPage.getSize();
		int pageNum = laborPage.getCurrent();
		PageHelper.startPage(pageNum,pageSize);
		List<LaborVO> laborVOS = mapper.selectList(laborPage);
		PageInfo<LaborVO> pageInfo = new PageInfo(laborVOS);
		return pageInfo.getList();
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
