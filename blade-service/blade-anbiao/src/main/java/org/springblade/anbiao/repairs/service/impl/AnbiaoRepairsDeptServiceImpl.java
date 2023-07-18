package org.springblade.anbiao.repairs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsDept;
import org.springblade.anbiao.repairs.mapper.AnbiaoRepairsDeptMapper;
import org.springblade.anbiao.repairs.page.AnbiaoRepairsDeptPage;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsDeptService;
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
public class AnbiaoRepairsDeptServiceImpl extends ServiceImpl<AnbiaoRepairsDeptMapper, AnbiaoRepairsDept> implements IAnbiaoRepairsDeptService {

	private AnbiaoRepairsDeptMapper anbiaoRepairsDeptMapper;

	@Override
	public AnbiaoRepairsDeptPage<AnbiaoRepairsDept> selectPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage) {
		Integer total = anbiaoRepairsDeptMapper.selectTotal(anbiaoRepairsDeptPage);
		if(anbiaoRepairsDeptPage.getSize()==0){
			if(anbiaoRepairsDeptPage.getTotal()==0){
				anbiaoRepairsDeptPage.setTotal(total);
			}

			if(anbiaoRepairsDeptPage.getTotal()==0){
				return anbiaoRepairsDeptPage;
			}else{
				List<AnbiaoRepairsDept> repairsDeptList = anbiaoRepairsDeptMapper.selectPage(anbiaoRepairsDeptPage);
				anbiaoRepairsDeptPage.setRecords(repairsDeptList);
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
			List<AnbiaoRepairsDept> repairsDeptList = anbiaoRepairsDeptMapper.selectPage(anbiaoRepairsDeptPage);
			anbiaoRepairsDeptPage.setRecords(repairsDeptList);
		}
		return anbiaoRepairsDeptPage;
	}
}
