package org.springblade.anbiao.labor.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.labor.DTO.laborDTO;
import org.springblade.anbiao.labor.VO.LaborMonthVO;
import org.springblade.anbiao.labor.VO.LaborVO;
import org.springblade.anbiao.labor.VO.LaborledgerVO;
import org.springblade.anbiao.labor.VO.graphicsVO;
import org.springblade.anbiao.labor.entity.Labor;
import org.springblade.anbiao.labor.entity.LaborEntity;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.labor.mapper.laborMapper;
import org.springblade.anbiao.labor.page.LaborPage;
import org.springblade.anbiao.labor.page.laborledgerPage;
import org.springblade.anbiao.labor.service.laborService;
import org.springframework.stereotype.Service;

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
	public LaborPage<LaborVO> selectLaborPage(LaborPage laborPage) {
		Integer total = laborMapper.selectLaborTotal(laborPage);
		Integer pagetotal = 0;
		if(laborPage.getSize()==0){
			if(laborPage.getTotal()==0){
				laborPage.setTotal(total);
			}
			if(laborPage.getTotal()==0){
				return laborPage;
			}else {
				List<LaborVO> laborVOList = laborMapper.selectLaborPage(laborPage);
				laborPage.setRecords(laborVOList);
				return laborPage;
			}
		}
		if (total > 0) {
			if(total%laborPage.getSize()==0){
				pagetotal = total / laborPage.getSize();
			}else {
				pagetotal = total / laborPage.getSize() + 1;
			}
		}
		if (pagetotal < laborPage.getCurrent()) {
			return laborPage;
		} else {
			laborPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (laborPage.getCurrent() > 1) {
				offsetNo = laborPage.getSize() * (laborPage.getCurrent() - 1);
			}
			laborPage.setTotal(total);
			laborPage.setOffsetNo(offsetNo);
			List<LaborVO> laborVOList = laborMapper.selectLaborPage(laborPage);
			laborPage.setRecords(laborVOList);
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
	public List<graphicsVO> selectGraphics(String ali_name,String aliIssueDate,String aliDeptIds) {
		List<graphicsVO> graphicsVOList = laborMapper.selectGrapsihVO(ali_name,aliIssueDate,aliDeptIds);
		return graphicsVOList;
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

	@Override
	public List<LaborEntity> selectInsurance(int aliDeptIds) {
		return laborMapper.selectInsurance(aliDeptIds);
	}

	@Override
	public IPage<LaborledgerVO> selectLedgerList(IPage<LaborledgerVO> page, LaborledgerVO laborledgerVO) {
		return page.setRecords(laborMapper.selectLedgerList(page,laborledgerVO));
	}

	@Override
	public laborledgerPage selectLedgerList(laborledgerPage laborledgerPage) {
		int total = laborMapper.selectLedgerTotal(laborledgerPage);
		Integer AccPagetotal = 0;
		if (laborledgerPage.getSize() == 0) {
			if (laborledgerPage.getTotal() == 0) {
				laborledgerPage.setTotal(total);
			}
			if (laborledgerPage.getTotal() == 0) {
				return laborledgerPage;
			} else {
				List<LaborledgerVO> LaborledgerVOS = laborMapper.selectLedgerPage(laborledgerPage);
				laborledgerPage.setRecords(LaborledgerVOS);
				return laborledgerPage;
			}
		}
		if (total > 0) {
			if (total % laborledgerPage.getSize() == 0) {
				AccPagetotal = total / laborledgerPage.getSize();
			} else {
				AccPagetotal = total / laborledgerPage.getSize() + 1;
			}
		}
		if (AccPagetotal < laborledgerPage.getCurrent()) {
			return laborledgerPage;
		} else {
			laborledgerPage.setPageTotal(AccPagetotal);
			Integer offsetNo = 0;
			if (laborledgerPage.getCurrent() > 1) {
				offsetNo = laborledgerPage.getSize() * (laborledgerPage.getCurrent() - 1);
			}
			laborledgerPage.setTotal(total);
			laborledgerPage.setOffsetNo(offsetNo);
			List<LaborledgerVO> LaborledgerVOS = laborMapper.selectLedgerPage(laborledgerPage);
			laborledgerPage.setRecords(LaborledgerVOS);
			return laborledgerPage;
		}
	}

	@Override
	public List<LaborMonthVO> selectLaborMonth(String deptId, String date) {
		return laborMapper.selectLaborMonth(deptId, date);
	}
}
