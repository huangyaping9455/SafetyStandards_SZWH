package org.springblade.anbiao.lawsRegulations.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.lawsRegulations.entity.AnbiaoLawsRegulations;
import org.springblade.anbiao.lawsRegulations.mapper.AnbiaoLawsRegulationsMapper;
import org.springblade.anbiao.lawsRegulations.page.AnBiaoLawsRegulationsPage;
import org.springblade.anbiao.lawsRegulations.service.IAnbiaoLawsRegulationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyp
 * @since 2023-03-10
 */
@Service
@AllArgsConstructor
public class AnbiaoLawsRegulationsServiceImpl extends ServiceImpl<AnbiaoLawsRegulationsMapper, AnbiaoLawsRegulations> implements IAnbiaoLawsRegulationsService {

	private AnbiaoLawsRegulationsMapper lawsRegulationsMapper;

	@Override
	public boolean insertLawsRegulationsSelective(AnbiaoLawsRegulations anBiaoLawsRegulations) {
		return lawsRegulationsMapper.insertLawsRegulationsSelective(anBiaoLawsRegulations);
	}

	@Override
	public AnBiaoLawsRegulationsPage<AnbiaoLawsRegulations> selectlawsRegulationsGetAll(AnBiaoLawsRegulationsPage anBiaoLawsRegulationsPage) {
		Integer total = lawsRegulationsMapper.selectlawsRegulationsGetAllTotal(anBiaoLawsRegulationsPage);
		if(anBiaoLawsRegulationsPage.getSize()==0){
			if(anBiaoLawsRegulationsPage.getTotal()==0){
				anBiaoLawsRegulationsPage.setTotal(total);
			}
			if(anBiaoLawsRegulationsPage.getTotal()==0){
				return anBiaoLawsRegulationsPage;
			}else{
				List<AnbiaoLawsRegulations> anBiaoLawsRegulationsList = lawsRegulationsMapper.selectlawsRegulationsGetAll(anBiaoLawsRegulationsPage);
				anBiaoLawsRegulationsPage.setRecords(anBiaoLawsRegulationsList);
				return anBiaoLawsRegulationsPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anBiaoLawsRegulationsPage.getSize()==0){
				pagetotal = total / anBiaoLawsRegulationsPage.getSize();
			}else {
				pagetotal = total / anBiaoLawsRegulationsPage.getSize() + 1;
			}
		}
		if (pagetotal >= anBiaoLawsRegulationsPage.getCurrent()) {
			anBiaoLawsRegulationsPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anBiaoLawsRegulationsPage.getCurrent() > 1) {
				offsetNo = anBiaoLawsRegulationsPage.getSize() * (anBiaoLawsRegulationsPage.getCurrent() - 1);
			}
			anBiaoLawsRegulationsPage.setTotal(total);
			anBiaoLawsRegulationsPage.setOffsetNo(offsetNo);
			List<AnbiaoLawsRegulations> anBiaoWeeksHiddenTroubleList = lawsRegulationsMapper.selectlawsRegulationsGetAll(anBiaoLawsRegulationsPage);
			anBiaoLawsRegulationsPage.setRecords(anBiaoWeeksHiddenTroubleList);
		}
		return anBiaoLawsRegulationsPage;
	}

	@Override
	public AnbiaoLawsRegulations selectlawsRegulationsById(Integer id, String name) {
		return lawsRegulationsMapper.selectlawsRegulationsById(id, name);
	}

	@Override
	public boolean updateLawsRegulationsSelective(AnbiaoLawsRegulations anBiaoLawsRegulations) {
		return lawsRegulationsMapper.updateLawsRegulationsSelective(anBiaoLawsRegulations);
	}
}
