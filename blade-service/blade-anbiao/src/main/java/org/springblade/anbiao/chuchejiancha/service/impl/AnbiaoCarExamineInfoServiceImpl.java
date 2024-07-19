package org.springblade.anbiao.chuchejiancha.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.mapper.VehicleMapper;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamine;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfoRemark;
import org.springblade.anbiao.chuchejiancha.mapper.AnbiaoCarExamineInfoMapper;
import org.springblade.anbiao.chuchejiancha.mapper.AnbiaoCarExamineInfoRemarkMapper;
import org.springblade.anbiao.chuchejiancha.page.AnBiaoCheckCarPage;
import org.springblade.anbiao.chuchejiancha.page.AnbiaoCarExamineInfoPage;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineInfoService;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoTZVO;
import org.springblade.anbiao.chuchejiancha.vo.AnbiaoCarExamineInfoVO;
import org.springblade.anbiao.chuchejiancha.vo.SafetyCheckMingXiVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Service
@AllArgsConstructor
public class AnbiaoCarExamineInfoServiceImpl extends ServiceImpl<AnbiaoCarExamineInfoMapper, AnbiaoCarExamineInfo> implements IAnbiaoCarExamineInfoService {

	private AnbiaoCarExamineInfoMapper mapper;

	private AnbiaoCarExamineInfoRemarkMapper remarkMapper;

	private VehicleMapper vehicleMapper;

	@Override
	public AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoVO> selectCarExamineInfoPage(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage) {
		Integer total = mapper.selectCarExamineInfoTotal(anbiaoCarExamineInfoPage);
		if(anbiaoCarExamineInfoPage.getSize()==0){
			if(anbiaoCarExamineInfoPage.getTotal()==0){
				anbiaoCarExamineInfoPage.setTotal(total);
			}
			List<AnbiaoCarExamineInfoVO> infoList = mapper.selectCarExamineInfoPage(anbiaoCarExamineInfoPage);
			if(anbiaoCarExamineInfoPage.getJsyId() != null){
				infoList.forEach(item-> {
					if(item.getCheliangpaizhao() == null || item.getChepaiyanse() == null){
						QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
						vehicleQueryWrapper.lambda().eq(Vehicle::getId, item.getVehid());
						vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
						Vehicle deail = vehicleMapper.selectOne(vehicleQueryWrapper);
						if(deail != null){
							item.setCheliangpaizhao(deail.getCheliangpaizhao());
							item.setChepaiyanse(deail.getChepaiyanse());
						}
					}

					if(item.getGcheliangpaizhao() == null){
						QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
						vehicleQueryWrapper.lambda().eq(Vehicle::getId, item.getGvehid());
						vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
						Vehicle deail = vehicleMapper.selectOne(vehicleQueryWrapper);
						if(deail != null){
							item.setGcheliangpaizhao(deail.getCheliangpaizhao());
						}
					}
					if(item.getStatusshow() == null){
						item.setStatusshow("未完成");
					}
					if(item.getStatus() == 6){
						item.setStatusshow("已完成");
					}
				});
			}
			anbiaoCarExamineInfoPage.setRecords(infoList);
			return anbiaoCarExamineInfoPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoCarExamineInfoPage.getSize()==0){
				pagetotal = total / anbiaoCarExamineInfoPage.getSize();
			}else {
				pagetotal = total / anbiaoCarExamineInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoCarExamineInfoPage.getCurrent()) {
			anbiaoCarExamineInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoCarExamineInfoPage.getCurrent() > 1) {
				offsetNo = anbiaoCarExamineInfoPage.getSize() * (anbiaoCarExamineInfoPage.getCurrent() - 1);
			}
			anbiaoCarExamineInfoPage.setTotal(total);
			anbiaoCarExamineInfoPage.setOffsetNo(offsetNo);
			List<AnbiaoCarExamineInfoVO> infoList = mapper.selectCarExamineInfoPage(anbiaoCarExamineInfoPage);
			if(anbiaoCarExamineInfoPage.getJsyId() != null){
				infoList.forEach(item-> {
					if(item.getCheliangpaizhao() == null || item.getChepaiyanse() == null){
						QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
						vehicleQueryWrapper.lambda().eq(Vehicle::getId, item.getVehid());
						vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
						Vehicle deail = vehicleMapper.selectOne(vehicleQueryWrapper);
						if(deail != null){
							item.setCheliangpaizhao(deail.getCheliangpaizhao());
							item.setChepaiyanse(deail.getChepaiyanse());
						}
					}

					if(item.getGcheliangpaizhao() == null){
						QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
						vehicleQueryWrapper.lambda().eq(Vehicle::getId, item.getGvehid());
						vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
						Vehicle deail = vehicleMapper.selectOne(vehicleQueryWrapper);
						if(deail != null){
							item.setGcheliangpaizhao(deail.getCheliangpaizhao());
						}
					}

					if(item.getStatusshow() == null){
						item.setStatusshow("未完成");
					}
					if(item.getStatus() != null && item.getStatus() == 6){
						item.setStatusshow("已完成");
					}
				});
			}
			anbiaoCarExamineInfoPage.setRecords(infoList);
		}
		return anbiaoCarExamineInfoPage;
	}

	@Override
	public AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoVO> selectCarExamineInfoPageTubingend(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage) {
		Integer total = mapper.selectCarExamineInfoTubingendTotal(anbiaoCarExamineInfoPage);
		if(anbiaoCarExamineInfoPage.getSize()==0){
			if(anbiaoCarExamineInfoPage.getTotal()==0){
				anbiaoCarExamineInfoPage.setTotal(total);
			}
			List<AnbiaoCarExamineInfoVO> infoList = mapper.selectCarExamineInfoPageTubingend(anbiaoCarExamineInfoPage);
			if(anbiaoCarExamineInfoPage.getJsyId() != null){
				infoList.forEach(item-> {
					if(item.getCheliangpaizhao() == null || item.getChepaiyanse() == null){
						QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
						vehicleQueryWrapper.lambda().eq(Vehicle::getId, item.getVehid());
						vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
						Vehicle deail = vehicleMapper.selectOne(vehicleQueryWrapper);
						if(deail != null){
							item.setCheliangpaizhao(deail.getCheliangpaizhao());
							item.setChepaiyanse(deail.getChepaiyanse());
						}
					}

					if(item.getGcheliangpaizhao() == null){
						QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
						vehicleQueryWrapper.lambda().eq(Vehicle::getId, item.getGvehid());
						vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
						Vehicle deail = vehicleMapper.selectOne(vehicleQueryWrapper);
						if(deail != null){
							item.setGcheliangpaizhao(deail.getCheliangpaizhao());
						}
					}
					if(item.getStatusshow() == null){
						item.setStatusshow("未完成");
					}
					if(item.getStatus() == 6){
						item.setStatusshow("已完成");
					}
				});
			}
			anbiaoCarExamineInfoPage.setRecords(infoList);
			return anbiaoCarExamineInfoPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoCarExamineInfoPage.getSize()==0){
				pagetotal = total / anbiaoCarExamineInfoPage.getSize();
			}else {
				pagetotal = total / anbiaoCarExamineInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoCarExamineInfoPage.getCurrent()) {
			anbiaoCarExamineInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoCarExamineInfoPage.getCurrent() > 1) {
				offsetNo = anbiaoCarExamineInfoPage.getSize() * (anbiaoCarExamineInfoPage.getCurrent() - 1);
			}
			anbiaoCarExamineInfoPage.setTotal(total);
			anbiaoCarExamineInfoPage.setOffsetNo(offsetNo);
			List<AnbiaoCarExamineInfoVO> infoList = mapper.selectCarExamineInfoPageTubingend(anbiaoCarExamineInfoPage);
			if(anbiaoCarExamineInfoPage.getJsyId() != null){
				infoList.forEach(item-> {
					if(item.getCheliangpaizhao() == null || item.getChepaiyanse() == null){
						QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
						vehicleQueryWrapper.lambda().eq(Vehicle::getId, item.getVehid());
						vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
						Vehicle deail = vehicleMapper.selectOne(vehicleQueryWrapper);
						if(deail != null){
							item.setCheliangpaizhao(deail.getCheliangpaizhao());
							item.setChepaiyanse(deail.getChepaiyanse());
						}
					}

					if(item.getGcheliangpaizhao() == null){
						QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
						vehicleQueryWrapper.lambda().eq(Vehicle::getId, item.getGvehid());
						vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
						Vehicle deail = vehicleMapper.selectOne(vehicleQueryWrapper);
						if(deail != null){
							item.setGcheliangpaizhao(deail.getCheliangpaizhao());
						}
					}

					if(item.getStatusshow() == null){
						item.setStatusshow("未完成");
					}
					if(item.getStatus() != null && item.getStatus() == 6){
						item.setStatusshow("已完成");
					}
				});
			}
			anbiaoCarExamineInfoPage.setRecords(infoList);
		}
		return anbiaoCarExamineInfoPage;
	}

	@Override
	public List<AnbiaoCarExamineInfoVO> selectAnBiaoCheckCarALLPage(AnBiaoCheckCarPage anbiaoCarExamineInfoPage) {
		List<AnbiaoCarExamineInfoVO> infoList = mapper.selectAnBiaoCheckCarALLPage(anbiaoCarExamineInfoPage);
		if(infoList.size()>0){
			infoList.forEach(item-> {
				QueryWrapper<AnbiaoCarExamineInfoRemark> remarkQueryWrapper = new QueryWrapper<AnbiaoCarExamineInfoRemark>();
				remarkQueryWrapper.lambda().eq(AnbiaoCarExamineInfoRemark::getExamid,item.getId());
				List<AnbiaoCarExamineInfoRemark> examineInfoRemarkList = remarkMapper.selectList(remarkQueryWrapper);
				item.setAnbiaoCarExamineInfoRemarkList(examineInfoRemarkList);
			});
		}
		return infoList;
	}

	@Override
	public AnbiaoCarExamineInfoPage<AnbiaoCarExamineInfoTZVO> selectCarExamineInfoTZPage(AnbiaoCarExamineInfoPage anbiaoCarExamineInfoPage) {
		Integer total = mapper.selectCarExamineInfoTZTotal(anbiaoCarExamineInfoPage);
		if(anbiaoCarExamineInfoPage.getSize()==0){
			if(anbiaoCarExamineInfoPage.getTotal()==0){
				anbiaoCarExamineInfoPage.setTotal(total);
			}
			List<AnbiaoCarExamineInfoTZVO> infoList = mapper.selectCarExamineInfoTZPage(anbiaoCarExamineInfoPage);
			anbiaoCarExamineInfoPage.setRecords(infoList);
			return anbiaoCarExamineInfoPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anbiaoCarExamineInfoPage.getSize()==0){
				pagetotal = total / anbiaoCarExamineInfoPage.getSize();
			}else {
				pagetotal = total / anbiaoCarExamineInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= anbiaoCarExamineInfoPage.getCurrent()) {
			anbiaoCarExamineInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anbiaoCarExamineInfoPage.getCurrent() > 1) {
				offsetNo = anbiaoCarExamineInfoPage.getSize() * (anbiaoCarExamineInfoPage.getCurrent() - 1);
			}
			anbiaoCarExamineInfoPage.setTotal(total);
			anbiaoCarExamineInfoPage.setOffsetNo(offsetNo);
			List<AnbiaoCarExamineInfoTZVO> infoList = mapper.selectCarExamineInfoTZPage(anbiaoCarExamineInfoPage);
			anbiaoCarExamineInfoPage.setRecords(infoList);
		}
		return anbiaoCarExamineInfoPage;
	}

	@Override
	public List<AnbiaoCarExamineInfoTZVO> selectDeptVeh(AnBiaoCheckCarPage anbiaoCarExamineInfoPage) {
		List<AnbiaoCarExamineInfoTZVO> carExamineInfoTZVOS = mapper.selectDeptVeh(anbiaoCarExamineInfoPage);
		carExamineInfoTZVOS.forEach(item-> {
			anbiaoCarExamineInfoPage.setVehId(item.getVehId());
			List<AnbiaoCarExamineInfoTZVO> carExamineInfoTZVOList = mapper.selectDeptVehExamine(anbiaoCarExamineInfoPage);
			if(carExamineInfoTZVOList != null){
				item.setExamineInfoTZVOList(carExamineInfoTZVOList);
			}
		});
		return carExamineInfoTZVOS;
	}

	@Override
	public List<AnbiaoCarExamineInfoTZVO> selectDeptVehExamine(AnBiaoCheckCarPage anbiaoCarExamineInfoPage) {
		return mapper.selectDeptVehExamine(anbiaoCarExamineInfoPage);
	}

	@Override
	public List<SafetyCheckMingXiVO> selectSafetyCheckMingXi(SafetyCheckMingXiVO safetyCheckMingXiVO) {
		return mapper.selectSafetyCheckMingXi(safetyCheckMingXiVO);
	}

	@Override
	public List<SafetyCheckMingXiVO> selectSafetyCheckScore(SafetyCheckMingXiVO safetyCheckMingXiVO) {
		return mapper.selectSafetyCheckScore(safetyCheckMingXiVO);
	}

	@Override
	public List<AnbiaoCarExamineInfoTZVO> selectDayCarExamine(String deptId, String date) {
		return mapper.selectDayCarExamine(deptId, date);
	}

	@Override
	public List<AnbiaoCarExamineInfoTZVO> selectCarExamineDay(String deptId, String date) {
		return mapper.selectCarExamineDay(deptId, date);
	}


}
