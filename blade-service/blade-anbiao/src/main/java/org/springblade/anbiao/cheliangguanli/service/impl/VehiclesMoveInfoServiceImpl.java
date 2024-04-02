package org.springblade.anbiao.cheliangguanli.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.VehiclesMoveInfo;
import org.springblade.anbiao.cheliangguanli.mapper.VehiclesMoveInfoMapper;
import org.springblade.anbiao.cheliangguanli.page.VehiclePage;
import org.springblade.anbiao.cheliangguanli.service.IVehiclesMoveInfoService;
import org.springblade.anbiao.cheliangguanli.vo.VehicleListVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车辆自定义接口实现
 */
@Service
@AllArgsConstructor
public class VehiclesMoveInfoServiceImpl extends ServiceImpl<VehiclesMoveInfoMapper, VehiclesMoveInfo> implements IVehiclesMoveInfoService {

    private VehiclesMoveInfoMapper vehiclesMoveInfoMapper;

//	@Override
//	public vehiclePage<VehiclesMoveInfo> selectVehiclePage(vehiclePage vehiclePage) {
//
//		Integer total = vehiclesMoveInfoMapper.selectVehicleTotal(vehiclePage);
//		if(vehiclePage.getSize()==0){
//			if(vehiclePage.getTotal()==0){
//				vehiclePage.setTotal(total);
//			}
//			List<VehiclesMoveInfo> vehiclesMoveInfoList = vehiclesMoveInfoMapper.selectVehiclePage(vehiclePage);
//			vehiclePage.setRecords(vehiclesMoveInfoList);
//			return vehiclePage;
//		}
//		Integer pagetotal = 0;
//		if (total > 0) {
//			if(total%vehiclePage.getSize()==0){
//				pagetotal = total / vehiclePage.getSize();
//			}else {
//				pagetotal = total / vehiclePage.getSize() + 1;
//			}
//		}
//		if (pagetotal >= vehiclePage.getCurrent()) {
//			vehiclePage.setPageTotal(pagetotal);
//			Integer offsetNo = 0;
//			if (vehiclePage.getCurrent() > 1) {
//				offsetNo = vehiclePage.getSize() * (vehiclePage.getCurrent() - 1);
//			}
//			vehiclePage.setTotal(total);
//			vehiclePage.setOffsetNo(offsetNo);
//			List<VehiclesMoveInfo> vehiclesMoveInfoList = vehiclesMoveInfoMapper.selectVehiclePage(vehiclePage);
//			vehiclePage.setRecords(vehiclesMoveInfoList);
//		}
//		return vehiclePage;
//	}

	@Override
	public boolean insertSelective(VehiclesMoveInfo vehiclesMoveInfo) {
		return vehiclesMoveInfoMapper.insertSelective(vehiclesMoveInfo);
	}

	@Override
	public VehiclePage<VehicleListVO> selectPageList(VehiclePage vehiclePage) {
		Integer total = vehiclesMoveInfoMapper.selectTotal(vehiclePage);
		if(vehiclePage.getSize()==0){
			if(vehiclePage.getTotal()==0){
				vehiclePage.setTotal(total);
			}
			List<VehicleListVO> vehiclesMoveInfoList = vehiclesMoveInfoMapper.selectPageList(vehiclePage);
			vehiclePage.setRecords(vehiclesMoveInfoList);
			return vehiclePage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%vehiclePage.getSize()==0){
				pagetotal = total / vehiclePage.getSize();
			}else {
				pagetotal = total / vehiclePage.getSize() + 1;
			}
		}
		if (pagetotal >= vehiclePage.getCurrent()) {
			vehiclePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (vehiclePage.getCurrent() > 1) {
				offsetNo = vehiclePage.getSize() * (vehiclePage.getCurrent() - 1);
			}
			vehiclePage.setTotal(total);
			vehiclePage.setOffsetNo(offsetNo);
			List<VehicleListVO> vehiclesMoveInfoList = vehiclesMoveInfoMapper.selectPageList(vehiclePage);
			vehiclePage.setRecords(vehiclesMoveInfoList);
		}
		return vehiclePage;
	}

	@Override
	public VehiclePage<VehicleListVO> selectGHCPageList(VehiclePage vehiclePage) {
		Integer total = vehiclesMoveInfoMapper.selectGHCTotal(vehiclePage);
		if(vehiclePage.getSize()==0){
			if(vehiclePage.getTotal()==0){
				vehiclePage.setTotal(total);
			}
			List<VehicleListVO> vehiclesMoveInfoList = vehiclesMoveInfoMapper.selectGHCPageList(vehiclePage);
			vehiclePage.setRecords(vehiclesMoveInfoList);
			return vehiclePage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%vehiclePage.getSize()==0){
				pagetotal = total / vehiclePage.getSize();
			}else {
				pagetotal = total / vehiclePage.getSize() + 1;
			}
		}
		if (pagetotal >= vehiclePage.getCurrent()) {
			vehiclePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (vehiclePage.getCurrent() > 1) {
				offsetNo = vehiclePage.getSize() * (vehiclePage.getCurrent() - 1);
			}
			vehiclePage.setTotal(total);
			vehiclePage.setOffsetNo(offsetNo);
			List<VehicleListVO> vehiclesMoveInfoList = vehiclesMoveInfoMapper.selectGHCPageList(vehiclePage);
			vehiclePage.setRecords(vehiclesMoveInfoList);
		}
		return vehiclePage;
	}
}
