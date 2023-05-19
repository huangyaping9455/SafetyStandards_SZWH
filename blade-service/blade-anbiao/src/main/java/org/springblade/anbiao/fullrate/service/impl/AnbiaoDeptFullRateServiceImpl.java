package org.springblade.anbiao.fullrate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.fullrate.entity.AnbiaoDeptFullRate;
import org.springblade.anbiao.fullrate.mapper.AnbiaoDeptFullRateMapper;
import org.springblade.anbiao.fullrate.page.DeptFullRatePage;
import org.springblade.anbiao.fullrate.service.IAnbiaoDeptFullRateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyp
 * @since 2023-05-15
 */
@Service
@AllArgsConstructor
public class AnbiaoDeptFullRateServiceImpl extends ServiceImpl<AnbiaoDeptFullRateMapper, AnbiaoDeptFullRate> implements IAnbiaoDeptFullRateService {

	private AnbiaoDeptFullRateMapper deptFullRateMapper;

	@Override
	public DeptFullRatePage<AnbiaoDeptFullRate> selectDayTJ(DeptFullRatePage deptFullRatePage) {
		Integer total = deptFullRateMapper.selectDayTJotal(deptFullRatePage);
		if(deptFullRatePage.getSize()==0){
			if(deptFullRatePage.getTotal()==0){
				deptFullRatePage.setTotal(total);
			}
			if(deptFullRatePage.getTotal()==0){
				return deptFullRatePage;
			}else {
				List<AnbiaoDeptFullRate> AnbiaoDeptFullRateList = deptFullRateMapper.selectDayTJ(deptFullRatePage);
				deptFullRatePage.setRecords(AnbiaoDeptFullRateList);
				return deptFullRatePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%deptFullRatePage.getSize()==0){
				pagetotal = total / deptFullRatePage.getSize();
			}else {
				pagetotal = total / deptFullRatePage.getSize() + 1;
			}
		}
		if (pagetotal >= deptFullRatePage.getCurrent()) {
			deptFullRatePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (deptFullRatePage.getCurrent() > 1) {
				offsetNo = deptFullRatePage.getSize() * (deptFullRatePage.getCurrent() - 1);
			}
			deptFullRatePage.setTotal(total);
			deptFullRatePage.setOffsetNo(offsetNo);
			List<AnbiaoDeptFullRate> AnbiaoDeptFullRateList = deptFullRateMapper.selectDayTJ(deptFullRatePage);
			deptFullRatePage.setRecords(AnbiaoDeptFullRateList);
		}
		return deptFullRatePage;
	}
}
