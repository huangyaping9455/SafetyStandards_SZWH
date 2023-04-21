package org.springblade.anbiao.jiashiyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.jiashiyuan.entity.DeptVehIntoArea;
import org.springblade.anbiao.jiashiyuan.entity.EpidemicPreventionAndControl;
import org.springblade.anbiao.jiashiyuan.entity.IntoArea;
import org.springblade.anbiao.jiashiyuan.mapper.EpidemicPreventionAndControlMapper;
import org.springblade.anbiao.jiashiyuan.page.EpidemicPreventionAndControlPage;
import org.springblade.anbiao.jiashiyuan.page.IntoAreaPage;
import org.springblade.anbiao.jiashiyuan.service.IEpidemicPreventionAndControlService;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.enumconstant.EnmuAlarm;
import org.springblade.common.tool.DateUtils;
import org.springblade.common.tool.GpsToBaiduUtil;
import org.springblade.common.tool.LatLotForLocation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by you on 2019/4/23.
 */
@Service
@AllArgsConstructor
public class EpidemicPreventionAndControlImpl extends ServiceImpl<EpidemicPreventionAndControlMapper, EpidemicPreventionAndControl> implements IEpidemicPreventionAndControlService {

	private EpidemicPreventionAndControlMapper epidemicPreventionAndControlMapper;

	private AlarmServer alarmServer;

	@Override
	public EpidemicPreventionAndControlPage<EpidemicPreventionAndControl> selectPageList(EpidemicPreventionAndControlPage epidemicPreventionAndControlPage) {
		Integer total = epidemicPreventionAndControlMapper.selectTotal(epidemicPreventionAndControlPage);
		Integer pagetotal = 0;
		if(epidemicPreventionAndControlPage.getSize()==0){
			if(epidemicPreventionAndControlPage.getTotal()==0){
				epidemicPreventionAndControlPage.setTotal(total);
			}
			if(epidemicPreventionAndControlPage.getTotal()==0){
				return epidemicPreventionAndControlPage;
			}else {
				List<EpidemicPreventionAndControl> epidemicPreventionAndControlList = epidemicPreventionAndControlMapper.selectPageList(epidemicPreventionAndControlPage);
				epidemicPreventionAndControlPage.setRecords(epidemicPreventionAndControlList);
				return epidemicPreventionAndControlPage;
			}
		}
		if (total > 0) {
			if(total%epidemicPreventionAndControlPage.getSize()==0){
				pagetotal = total / epidemicPreventionAndControlPage.getSize();
			}else {
				pagetotal = total / epidemicPreventionAndControlPage.getSize() + 1;
			}
		}
		if (pagetotal < epidemicPreventionAndControlPage.getCurrent()) {
			return epidemicPreventionAndControlPage;
		} else {
			epidemicPreventionAndControlPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (epidemicPreventionAndControlPage.getCurrent() > 1) {
				offsetNo = epidemicPreventionAndControlPage.getSize() * (epidemicPreventionAndControlPage.getCurrent() - 1);
			}
			epidemicPreventionAndControlPage.setTotal(total);
			epidemicPreventionAndControlPage.setOffsetNo(offsetNo);
			List<EpidemicPreventionAndControl> epidemicPreventionAndControlList = epidemicPreventionAndControlMapper.selectPageList(epidemicPreventionAndControlPage);
			epidemicPreventionAndControlPage.setRecords(epidemicPreventionAndControlList);
		}
		return epidemicPreventionAndControlPage;
	}

	@Override
	public EpidemicPreventionAndControl selectById(String jiashiyuanid,String date) {
		return epidemicPreventionAndControlMapper.selectByIds(jiashiyuanid,date);
	}

	@Override
	public boolean insertSelective(EpidemicPreventionAndControl epidemicPreventionAndControl) {
		return epidemicPreventionAndControlMapper.insertSelective(epidemicPreventionAndControl);
	}

	@Override
	public boolean updateSelective(EpidemicPreventionAndControl epidemicPreventionAndControl) {
		return epidemicPreventionAndControlMapper.updateSelective(epidemicPreventionAndControl);
	}

	@Override
	public IntoAreaPage<IntoArea> selectDeptMXIntoAreaPMTJ(IntoAreaPage intoAreaPage) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Integer total = epidemicPreventionAndControlMapper.selectDeptMXIntoAreaPMTotal(intoAreaPage);
		Integer pagetotal = 0;
		if(intoAreaPage.getSize()==0){
			if(intoAreaPage.getTotal()==0){
				intoAreaPage.setTotal(total);
			}
			if(intoAreaPage.getTotal()==0){
				return intoAreaPage;
			}else {
				List<IntoArea> intoAreaList = epidemicPreventionAndControlMapper.selectDeptMXIntoAreaPMTJ(intoAreaPage);
				intoAreaList.forEach(item->{
					if(StringUtils.isBlank(item.getEndresult())){
						if(item.getRemark()==null){
							String days = DateUtils.differDays(df.format(item.getDate()));
							if( Integer.parseInt(days) >= 8 ){
								item.setShensuzhuangtai("未申诉");
								item.setChulizhuangtai("超期未处理");
								item.setChulimiaoshu("");
								item.setChulixingshi("");
							}else{
								item.setShensuzhuangtai("未申诉");
								item.setChulizhuangtai("未处理");
								item.setChulimiaoshu("");
								item.setChulixingshi("");
							}
						}else if(item.getRemark()==1){
							EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
							item.setChulizhuangtai(byValue.desc);
							item.setShensuzhuangtai("未申诉");
						}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 0){
							EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(4));
							item.setShensuzhuangtai(byValue.desc);
							item.setChulizhuangtai("未处理");
							item.setShensumiaoshu(item.getChulimiaoshu());
							item.setShensuxingshi(item.getChulixingshi());
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 1){
							EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(5));
							item.setShensuzhuangtai(byValue.desc);
							item.setChulizhuangtai("未处理");
							item.setShensumiaoshu(item.getChulimiaoshu());
							item.setShensuxingshi(item.getChulixingshi());
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 2){
							EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(6));
							item.setShensuzhuangtai(byValue.desc);
							item.setChulizhuangtai("未处理");
							item.setShensumiaoshu(item.getChulimiaoshu());
							item.setShensuxingshi(item.getChulixingshi());
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}else{
							EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
							item.setShensuzhuangtai(byValue.desc);
							item.setChulizhuangtai("未处理");
							item.setShensumiaoshu(item.getChulimiaoshu());
							item.setShensuxingshi(item.getChulixingshi());
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}
					}else{
						if(item.getRemark()==null){
							String days = DateUtils.differDays(df.format(item.getDate()));
							if( Integer.parseInt(days) >= 8 ){
								item.setShensuzhuangtai("未申诉");
								item.setChulizhuangtai("超期未处理");
								item.setChulimiaoshu("");
								item.setChulixingshi("");
							}else{
								item.setShensuzhuangtai("未申诉");
								item.setChulizhuangtai("未处理");
								item.setChulimiaoshu("");
								item.setChulixingshi("");
							}
						}else if(item.getEndresult().equals("1")){
							EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
							EnmuAlarm.ShensuJieguo ssbyValue = null;
							if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 0){
								ssbyValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(4));
								item.setShensuzhuangtai(ssbyValue.desc);
							}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 1){
								ssbyValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(5));
								item.setShensuzhuangtai(ssbyValue.desc);
							}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 2){
								ssbyValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(6));
								item.setShensuzhuangtai(ssbyValue.desc);
							}else{
								item.setShensuzhuangtai("未申诉");
							}
							item.setChulizhuangtai(byValue.desc);
						}else if(item.getRemark()==1){
							EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
							item.setChulizhuangtai(byValue.desc);
							item.setShensuzhuangtai("未申诉");
						}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 0){
							EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(4));
							item.setShensuzhuangtai(byValue.desc);
							item.setChulizhuangtai("未处理");
							item.setShensumiaoshu(item.getChulimiaoshu());
							item.setShensuxingshi(item.getChulixingshi());
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 1){
							EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(5));
							item.setShensuzhuangtai(byValue.desc);
							item.setChulizhuangtai("未处理");
							item.setShensumiaoshu(item.getChulimiaoshu());
							item.setShensuxingshi(item.getChulixingshi());
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 2){
							EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(6));
							item.setShensuzhuangtai(byValue.desc);
							item.setChulizhuangtai("未处理");
							item.setShensumiaoshu(item.getChulimiaoshu());
							item.setShensuxingshi(item.getChulixingshi());
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}else{
							EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
							item.setShensuzhuangtai(byValue.desc);
							item.setChulizhuangtai("未处理");
							item.setShensumiaoshu(item.getChulimiaoshu());
							item.setShensuxingshi(item.getChulixingshi());
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}
					}
					if(StringUtils.isBlank(item.getRoadName())){
						double lat = Double.parseDouble(item.getLatitude().toString());
						double lon = Double.parseDouble(item.getLongitude().toString());
						double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
						item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
						item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
						String LocalName= LatLotForLocation.getProvince(item.getLatitude().toString(),item.getLongitude().toString(),alarmServer.getAddressAk());
						item.setRoadName(LocalName);
					}
				});
				intoAreaPage.setRecords(intoAreaList);
				return intoAreaPage;
			}
		}
		if (total > 0) {
			if(total%intoAreaPage.getSize()==0){
				pagetotal = total / intoAreaPage.getSize();
			}else {
				pagetotal = total / intoAreaPage.getSize() + 1;
			}
		}
		if (pagetotal < intoAreaPage.getCurrent()) {
			return intoAreaPage;
		} else {
			intoAreaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (intoAreaPage.getCurrent() > 1) {
				offsetNo = intoAreaPage.getSize() * (intoAreaPage.getCurrent() - 1);
			}
			intoAreaPage.setTotal(total);
			intoAreaPage.setOffsetNo(offsetNo);
			List<IntoArea> intoAreaList = epidemicPreventionAndControlMapper.selectDeptMXIntoAreaPMTJ(intoAreaPage);
			intoAreaList.forEach(item->{
				if(StringUtils.isBlank(item.getEndresult())){
					if(item.getRemark()==null){
						String days = DateUtils.differDays(df.format(item.getDate()));
						if( Integer.parseInt(days) >= 8 ){
							item.setShensuzhuangtai("未申诉");
							item.setChulizhuangtai("超期未处理");
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}else{
							item.setShensuzhuangtai("未申诉");
							item.setChulizhuangtai("未处理");
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}
					}else if(item.getRemark()==1){
						EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
						item.setChulizhuangtai(byValue.desc);
						item.setShensuzhuangtai("未申诉");
					}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 0){
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(4));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 1){
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(5));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 2){
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(6));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}else{
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}
				}else{
					if(item.getRemark()==null){
						String days = DateUtils.differDays(df.format(item.getDate()));
						if( Integer.parseInt(days) >= 8 ){
							item.setShensuzhuangtai("未申诉");
							item.setChulizhuangtai("超期未处理");
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}else{
							item.setShensuzhuangtai("未申诉");
							item.setChulizhuangtai("未处理");
							item.setChulimiaoshu("");
							item.setChulixingshi("");
						}
					}else if(item.getEndresult().equals("1")){
						EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
						EnmuAlarm.ShensuJieguo ssbyValue = null;
						if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 0){
							ssbyValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(4));
							item.setShensuzhuangtai(ssbyValue.desc);
						}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 1){
							ssbyValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(5));
							item.setShensuzhuangtai(ssbyValue.desc);
						}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 2){
							ssbyValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(6));
							item.setShensuzhuangtai(ssbyValue.desc);
						}else{
							item.setShensuzhuangtai("未申诉");
						}
						item.setChulizhuangtai(byValue.desc);
					}else if(item.getRemark()==1){
						EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
						item.setChulizhuangtai(byValue.desc);
						item.setShensuzhuangtai("未申诉");
					}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 0){
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(4));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 1){
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(5));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 2){
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(6));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}else{
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}
				}
				if(StringUtils.isBlank(item.getRoadName())){
					double lat = Double.parseDouble(item.getLatitude().toString());
					double lon = Double.parseDouble(item.getLongitude().toString());
					double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
					item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
					item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
					String LocalName= LatLotForLocation.getProvince(item.getLatitude().toString(),item.getLongitude().toString(),alarmServer.getAddressAk());
					item.setRoadName(LocalName);
				}
			});
			intoAreaPage.setRecords(intoAreaList);
		}
		return intoAreaPage;
	}

	@Override
	public IntoAreaPage<DeptVehIntoArea> selectDeptAreaalarmPageList(IntoAreaPage intoAreaPage) {
		Integer total = epidemicPreventionAndControlMapper.selectDeptAreaalarmTotal(intoAreaPage);
		Integer pagetotal = 0;
		if(intoAreaPage.getSize()==0){
			if(intoAreaPage.getTotal()==0){
				intoAreaPage.setTotal(total);
			}
			if(intoAreaPage.getTotal()==0){
				return intoAreaPage;
			}else {
				List<DeptVehIntoArea> deptVehIntoAreaList = epidemicPreventionAndControlMapper.selectDeptAreaalarmPageList(intoAreaPage);
				intoAreaPage.setRecords(deptVehIntoAreaList);
				return intoAreaPage;
			}
		}
		if (total > 0) {
			if(total%intoAreaPage.getSize()==0){
				pagetotal = total / intoAreaPage.getSize();
			}else {
				pagetotal = total / intoAreaPage.getSize() + 1;
			}
		}
		if (pagetotal < intoAreaPage.getCurrent()) {
			return intoAreaPage;
		} else {
			intoAreaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (intoAreaPage.getCurrent() > 1) {
				offsetNo = intoAreaPage.getSize() * (intoAreaPage.getCurrent() - 1);
			}
			intoAreaPage.setTotal(total);
			intoAreaPage.setOffsetNo(offsetNo);
			List<DeptVehIntoArea> deptVehIntoAreaList = epidemicPreventionAndControlMapper.selectDeptAreaalarmPageList(intoAreaPage);
			intoAreaPage.setRecords(deptVehIntoAreaList);
		}
		return intoAreaPage;
	}
}
