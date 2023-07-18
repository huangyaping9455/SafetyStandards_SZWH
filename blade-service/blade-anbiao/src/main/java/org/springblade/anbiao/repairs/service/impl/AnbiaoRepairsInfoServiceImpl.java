package org.springblade.anbiao.repairs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsDept;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsInfo;
import org.springblade.anbiao.repairs.mapper.AnbiaoRepairsInfoMapper;
import org.springblade.anbiao.repairs.page.AnbiaoRepairsDeptPage;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsInfoService;
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
public class AnbiaoRepairsInfoServiceImpl extends ServiceImpl<AnbiaoRepairsInfoMapper, AnbiaoRepairsInfo> implements IAnbiaoRepairsInfoService {

	private AnbiaoRepairsInfoMapper repairsInfoMapper;

	@Override
	public AnbiaoRepairsDeptPage<AnbiaoRepairsInfo> selectPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage) {
		Integer total = repairsInfoMapper.selectTotal(anbiaoRepairsDeptPage);
		if(anbiaoRepairsDeptPage.getSize()==0){
			if(anbiaoRepairsDeptPage.getTotal()==0){
				anbiaoRepairsDeptPage.setTotal(total);
			}

			if(anbiaoRepairsDeptPage.getTotal()==0){
				return anbiaoRepairsDeptPage;
			}else{
				List<AnbiaoRepairsInfo> repairsInfos = repairsInfoMapper.selectPage(anbiaoRepairsDeptPage);
				anbiaoRepairsDeptPage.setRecords(repairsInfos);
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
			List<AnbiaoRepairsInfo> repairsInfos = repairsInfoMapper.selectPage(anbiaoRepairsDeptPage);
			anbiaoRepairsDeptPage.setRecords(repairsInfos);
		}
		return anbiaoRepairsDeptPage;
	}

	@Override
	public AnbiaoRepairsInfo selectMaxXuhao(String deptId) {
		return repairsInfoMapper.selectMaxXuhao(deptId);
	}

	@Override
	public AnbiaoRepairsDeptPage<AnbiaoRepairsInfo> selectDriverPage(AnbiaoRepairsDeptPage anbiaoRepairsDeptPage) {
		Integer total = repairsInfoMapper.selectDriverTotal(anbiaoRepairsDeptPage);
		if(anbiaoRepairsDeptPage.getSize()==0){
			if(anbiaoRepairsDeptPage.getTotal()==0){
				anbiaoRepairsDeptPage.setTotal(total);
			}

			if(anbiaoRepairsDeptPage.getTotal()==0){
				return anbiaoRepairsDeptPage;
			}else{
				List<AnbiaoRepairsInfo> repairsInfos = repairsInfoMapper.selectDriverPage(anbiaoRepairsDeptPage);
				anbiaoRepairsDeptPage.setRecords(repairsInfos);
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
			List<AnbiaoRepairsInfo> repairsInfos = repairsInfoMapper.selectDriverPage(anbiaoRepairsDeptPage);
			anbiaoRepairsDeptPage.setRecords(repairsInfos);
		}
		return anbiaoRepairsDeptPage;
	}
}
