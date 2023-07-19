package org.springblade.anbiao.repairs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPerson;
import org.springblade.anbiao.repairs.mapper.AnbiaoRepairsPersonMapper;
import org.springblade.anbiao.repairs.page.AnbiaoRepairsDeptPage;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsPersonService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2023-07-17
 */
@Service
@AllArgsConstructor
public class AnbiaoRepairsPersonServiceImpl extends ServiceImpl<AnbiaoRepairsPersonMapper, AnbiaoRepairsPerson> implements IAnbiaoRepairsPersonService {

	private AnbiaoRepairsPersonMapper repairsPersonMapper;

	@Override
	public AnbiaoRepairsDeptPage<AnbiaoRepairsPerson> selectPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage) {
		Integer total = repairsPersonMapper.selectTotal(anbiaoRepairsDeptPage);
		if(anbiaoRepairsDeptPage.getSize()==0){
			if(anbiaoRepairsDeptPage.getTotal()==0){
				anbiaoRepairsDeptPage.setTotal(total);
			}

			if(anbiaoRepairsDeptPage.getTotal()==0){
				return anbiaoRepairsDeptPage;
			}else{
				List<AnbiaoRepairsPerson> repairsPersonList = repairsPersonMapper.selectPage(anbiaoRepairsDeptPage);
				anbiaoRepairsDeptPage.setRecords(repairsPersonList);
				return anbiaoRepairsDeptPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoRepairsDeptPage.getSize()==0){
				pagetotal = total / anbiaoRepairsDeptPage.getSize();
			}else {
				pagetotal = total / anbiaoRepairsDeptPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoRepairsDeptPage.getCurrent()) {
			anbiaoRepairsDeptPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoRepairsDeptPage.getCurrent() > 1) {
				offsetNo = anbiaoRepairsDeptPage.getSize() * (anbiaoRepairsDeptPage.getCurrent() - 1);
			}
			anbiaoRepairsDeptPage.setTotal(total);
			anbiaoRepairsDeptPage.setOffsetNo(offsetNo);
			List<AnbiaoRepairsPerson> repairsPersonList = repairsPersonMapper.selectPage(anbiaoRepairsDeptPage);
			anbiaoRepairsDeptPage.setRecords(repairsPersonList);
		}
		return anbiaoRepairsDeptPage;
	}

	@Override
	public List<AnbiaoRepairsPerson> selectPersonByDeptId(String deptId) {
		return repairsPersonMapper.selectPersonByDeptId(deptId);
	}

	@Override
	public boolean updatePassWord(String password, String id) {
		return repairsPersonMapper.updatePassWord(password, id);
	}

	@Override
	public AnbiaoRepairsPerson getPerson(String account, String password) {
		return repairsPersonMapper.getPerson(account, password);
	}
}
