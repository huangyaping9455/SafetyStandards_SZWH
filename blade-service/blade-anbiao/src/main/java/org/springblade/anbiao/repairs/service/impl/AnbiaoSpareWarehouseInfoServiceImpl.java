package org.springblade.anbiao.repairs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.repairs.entity.AnbiaoSpareWarehouseInfo;
import org.springblade.anbiao.repairs.mapper.AnbiaoSpareWarehouseInfoMapper;
import org.springblade.anbiao.repairs.page.AnbiaoSpareWarehouseInfoPage;
import org.springblade.anbiao.repairs.service.IAnbiaoSpareWarehouseInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 备件仓库管理 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@Service
@AllArgsConstructor
public class AnbiaoSpareWarehouseInfoServiceImpl extends ServiceImpl<AnbiaoSpareWarehouseInfoMapper, AnbiaoSpareWarehouseInfo> implements IAnbiaoSpareWarehouseInfoService {

	private AnbiaoSpareWarehouseInfoMapper mapper;

	@Override
	public AnbiaoSpareWarehouseInfoPage<AnbiaoSpareWarehouseInfo> selectAllPage(AnbiaoSpareWarehouseInfoPage anbiaoSpareWarehouseInfoPage) {
		Integer total = mapper.selectAllTotal(anbiaoSpareWarehouseInfoPage);
		if(anbiaoSpareWarehouseInfoPage.getSize()==0){
			if(anbiaoSpareWarehouseInfoPage.getTotal()==0){
				anbiaoSpareWarehouseInfoPage.setTotal(total);
			}

			if(anbiaoSpareWarehouseInfoPage.getTotal()==0){
				return anbiaoSpareWarehouseInfoPage;
			}else{
				List<AnbiaoSpareWarehouseInfo> repairsInfos = mapper.selectAllPage(anbiaoSpareWarehouseInfoPage);
				anbiaoSpareWarehouseInfoPage.setRecords(repairsInfos);
				return anbiaoSpareWarehouseInfoPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoSpareWarehouseInfoPage.getSize()==0){
				pagetotal = total / anbiaoSpareWarehouseInfoPage.getSize();
			}else {
				pagetotal = total / anbiaoSpareWarehouseInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoSpareWarehouseInfoPage.getCurrent()) {
			anbiaoSpareWarehouseInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoSpareWarehouseInfoPage.getCurrent() > 1) {
				offsetNo = anbiaoSpareWarehouseInfoPage.getSize() * (anbiaoSpareWarehouseInfoPage.getCurrent() - 1);
			}
			anbiaoSpareWarehouseInfoPage.setTotal(total);
			anbiaoSpareWarehouseInfoPage.setOffsetNo(offsetNo);
			List<AnbiaoSpareWarehouseInfo> repairsInfos = mapper.selectAllPage(anbiaoSpareWarehouseInfoPage);
			anbiaoSpareWarehouseInfoPage.setRecords(repairsInfos);
		}
		return anbiaoSpareWarehouseInfoPage;
	}

	@Override
	public AnbiaoSpareWarehouseInfo selectMaxXuhao(String deptId) {
		return mapper.selectMaxXuhao(deptId);
	}

	@Override
	public List<AnbiaoSpareWarehouseInfo> selectByDeptIdList(String deptId) {
		return mapper.selectByDeptIdList(deptId);
	}
}
