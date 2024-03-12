package org.springblade.anbiao.vehicleDelete.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.vehicleDelete.entity.AnbiaoVehicleDeleteInfo;
import org.springblade.anbiao.vehicleDelete.mapper.AnbiaoVehicleDeleteInfoMapper;
import org.springblade.anbiao.vehicleDelete.page.VehicleDeletePage;
import org.springblade.anbiao.vehicleDelete.service.IAnbiaoVehicleDeleteInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *车辆删除日志记录
 * @author author
 * @since 2023-11-10
 */
@Service
@AllArgsConstructor
public class AnbiaoVehicleDeleteInfoServiceImpl extends ServiceImpl<AnbiaoVehicleDeleteInfoMapper, AnbiaoVehicleDeleteInfo> implements IAnbiaoVehicleDeleteInfoService {

	private AnbiaoVehicleDeleteInfoMapper vehicleDeleteInfoMapper;

	@Override
	public VehicleDeletePage<AnbiaoVehicleDeleteInfo> selectTJPage(VehicleDeletePage vehicleDeletePage) {
		Integer total = vehicleDeleteInfoMapper.selectTJTotal(vehicleDeletePage);
		if(vehicleDeletePage.getSize()==0){
			if(vehicleDeletePage.getTotal()==0){
				vehicleDeletePage.setTotal(total);
			}

			if(vehicleDeletePage.getTotal()==0){
				return vehicleDeletePage;
			}else{
				List<AnbiaoVehicleDeleteInfo> repairsInfos = vehicleDeleteInfoMapper.selectTJPage(vehicleDeletePage);
				vehicleDeletePage.setRecords(repairsInfos);
				return vehicleDeletePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%vehicleDeletePage.getSize()==0){
				pagetotal = total / vehicleDeletePage.getSize();
			}else {
				pagetotal = total / vehicleDeletePage.getSize() + 1;
			}
		}
		if (pagetotal >= vehicleDeletePage.getCurrent()) {
			vehicleDeletePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (vehicleDeletePage.getCurrent() > 1) {
				offsetNo = vehicleDeletePage.getSize() * (vehicleDeletePage.getCurrent() - 1);
			}
			vehicleDeletePage.setTotal(total);
			vehicleDeletePage.setOffsetNo(offsetNo);
			List<AnbiaoVehicleDeleteInfo> repairsInfos = vehicleDeleteInfoMapper.selectTJPage(vehicleDeletePage);
			vehicleDeletePage.setRecords(repairsInfos);
		}
		return vehicleDeletePage;
	}
}
