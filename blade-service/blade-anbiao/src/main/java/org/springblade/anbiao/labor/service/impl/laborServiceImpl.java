package org.springblade.anbiao.labor.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.VO.LaborVO;
import org.springblade.anbiao.labor.VO.graphicsVO;
import org.springblade.anbiao.labor.entity.Labor;
import org.springblade.anbiao.labor.entity.LaborEntity;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.labor.mapper.LaborlingquMapper;
import org.springblade.anbiao.labor.mapper.laborMapper;
import org.springblade.anbiao.labor.page.LaborPage;
import org.springblade.anbiao.labor.service.laborService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/3 21:40
 */
@Service
@AllArgsConstructor
public class laborServiceImpl extends ServiceImpl<laborMapper,LaborEntity> implements laborService {

	private laborMapper laborMapper;

	@Override
	public LaborPage selectPage(LaborPage laborPage) {
		int total = laborMapper.selectTotal(laborPage);
		Integer LaborPagetotal = 0;
		if (laborPage.getSize() == 0) {
			if (laborPage.getTotal() == 0) {
				laborPage.setTotal(total);
			}
			if (laborPage.getTotal() == 0) {
				return laborPage;
			} else {
				List<LaborVO> laborVOS = laborMapper.selectList(laborPage);
				laborPage.setRecords(laborVOS);
				return laborPage;
			}
		}
		if (total > 0) {
			if (total % laborPage.getSize() == 0) {
				LaborPagetotal = total / laborPage.getSize();
			} else {
				LaborPagetotal = total / laborPage.getSize() + 1;
			}
		}
		if (LaborPagetotal < laborPage.getCurrent()) {
			return laborPage;
		} else {
			laborPage.setPageTotal(LaborPagetotal);
			Integer offsetNo = 0;
			if (laborPage.getCurrent() > 1) {
				offsetNo = laborPage.getSize() * (laborPage.getCurrent() - 1);
			}
			laborPage.setTotal(total);
			laborPage.setOffsetNo(offsetNo);
			List<LaborVO> laborVOS = laborMapper.selectList(laborPage);
			laborPage.setRecords(laborVOS);
			return laborPage;
		}
	}

	@Override
	public LaborEntity selectAll(LaborPage laborPage) {
		return laborMapper.selectAll(laborPage);
	}

	@Override
	public List<Labor> selectC(LaborPage laborPage) {
		return laborMapper.selectC(laborPage);
	}


	@Override
	public Boolean insertOne(laborDTO laborDTO) {
		return laborMapper.insertOne(laborDTO);
	}

	@Override
	public Boolean insertA(Labor labor) {
		return laborMapper.insertA(labor);
	}

	@Override
	public graphicsVO selectGraphics(String ali_name) {
		graphicsVO graphicsVO = laborMapper.selectGrapsihVO(ali_name);
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
	public Boolean deleteAccident(laborDTO laborDTO) {
		return laborMapper.deleteLao(laborDTO);
	}

	@Override
	public Boolean updateAccident(LaborEntity laborEntity) {
		return laborMapper.updateLao(laborEntity);
	}

	@Override
	public Boolean updateA(Labor labor) {
		return laborMapper.updateA(labor);
	}

	@Override
	public Boolean updateL(LaborlingquEntity laborlingqu) {
		return laborMapper.updateL(laborlingqu);
	}
}
