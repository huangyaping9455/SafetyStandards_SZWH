package org.springblade.anbiao.violateDiscipline.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.violateDiscipline.entity.AnbiaoViolateDiscipline;
import org.springblade.anbiao.violateDiscipline.mapper.AnbiaoViolateDisciplineMapper;
import org.springblade.anbiao.violateDiscipline.page.AnbiaoViolateDisciplinePage;
import org.springblade.anbiao.violateDiscipline.service.IAnbiaoViolateDisciplineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 违规违纪材料上传登记 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
@Service
@AllArgsConstructor
public class AnbiaoViolateDisciplineServiceImpl extends ServiceImpl<AnbiaoViolateDisciplineMapper, AnbiaoViolateDiscipline> implements IAnbiaoViolateDisciplineService {

	private AnbiaoViolateDisciplineMapper disciplineMapper;

	@Override
	public AnbiaoViolateDisciplinePage<AnbiaoViolateDiscipline> selectALLPage(AnbiaoViolateDisciplinePage violateDisciplinePage) {
		Integer total = disciplineMapper.selectALLTotal(violateDisciplinePage);
		if(violateDisciplinePage.getSize()==0){
			if(violateDisciplinePage.getTotal()==0){
				violateDisciplinePage.setTotal(total);
			}

			if(violateDisciplinePage.getTotal()==0){
				return violateDisciplinePage;
			}else{
				List<AnbiaoViolateDiscipline> violateDisciplineList = disciplineMapper.selectALLPage(violateDisciplinePage);
				violateDisciplinePage.setRecords(violateDisciplineList);
				return violateDisciplinePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%violateDisciplinePage.getSize()==0){
				pagetotal = total / violateDisciplinePage.getSize();
			}else {
				pagetotal = total / violateDisciplinePage.getSize() + 1;
			}
		}
		if (pagetotal >= violateDisciplinePage.getCurrent()) {
			violateDisciplinePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (violateDisciplinePage.getCurrent() > 1) {
				offsetNo = violateDisciplinePage.getSize() * (violateDisciplinePage.getCurrent() - 1);
			}
			violateDisciplinePage.setTotal(total);
			violateDisciplinePage.setOffsetNo(offsetNo);
			List<AnbiaoViolateDiscipline> violateDisciplineList = disciplineMapper.selectALLPage(violateDisciplinePage);
			violateDisciplinePage.setRecords(violateDisciplineList);
		}
		return violateDisciplinePage;
	}

	@Override
	public AnbiaoViolateDisciplinePage<AnbiaoViolateDiscipline> selectTJALLPage(AnbiaoViolateDisciplinePage violateDisciplinePage) {
		Integer total = disciplineMapper.selectTJALLTotal(violateDisciplinePage);
		if(violateDisciplinePage.getSize()==0){
			if(violateDisciplinePage.getTotal()==0){
				violateDisciplinePage.setTotal(total);
			}

			if(violateDisciplinePage.getTotal()==0){
				return violateDisciplinePage;
			}else{
				List<AnbiaoViolateDiscipline> violateDisciplineList = disciplineMapper.selectTJALLPage(violateDisciplinePage);
				violateDisciplinePage.setRecords(violateDisciplineList);
				return violateDisciplinePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%violateDisciplinePage.getSize()==0){
				pagetotal = total / violateDisciplinePage.getSize();
			}else {
				pagetotal = total / violateDisciplinePage.getSize() + 1;
			}
		}
		if (pagetotal >= violateDisciplinePage.getCurrent()) {
			violateDisciplinePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (violateDisciplinePage.getCurrent() > 1) {
				offsetNo = violateDisciplinePage.getSize() * (violateDisciplinePage.getCurrent() - 1);
			}
			violateDisciplinePage.setTotal(total);
			violateDisciplinePage.setOffsetNo(offsetNo);
			List<AnbiaoViolateDiscipline> violateDisciplineList = disciplineMapper.selectTJALLPage(violateDisciplinePage);
			violateDisciplinePage.setRecords(violateDisciplineList);
		}
		return violateDisciplinePage;
	}
}
