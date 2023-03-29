/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuZhuServiceImpl
 * Author:   呵呵哒
 * Date:     2020/6/20 17:18
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.qiyeshouye.entity.*;
import org.springblade.anbiao.qiyeshouye.mapper.QiYeTongJiMapper;
import org.springblade.anbiao.qiyeshouye.page.*;
import org.springblade.anbiao.qiyeshouye.service.IQiYeTongJiService;
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
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/7/4
 * @描述
 */
@Service
@AllArgsConstructor
public class QiYeTongJiServiceImpl extends ServiceImpl<QiYeTongJiMapper, QiYeTongJi> implements IQiYeTongJiService {

	private QiYeTongJiMapper qiYeTongJiMapper;

	private AlarmServer alarmServer;

	@Override
	public QiYeTongJiPage selectGetBJTJ(QiYeTongJiPage qiYeTongJiPage) {
		Integer total = qiYeTongJiMapper.selectGetBJTJTotal(qiYeTongJiPage);
		if(qiYeTongJiPage.getSize()==0){
			if(qiYeTongJiPage.getTotal()==0){
				qiYeTongJiPage.setTotal(total);
			}
			if(qiYeTongJiPage.getTotal()==0){
				return qiYeTongJiPage;
			}else {
				List<QiYeTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selectGetBJTJ(qiYeTongJiPage);
				qiYeTongJiPage.setRecords(zhengFuBaoJingTongJiList);
				return qiYeTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeTongJiPage.getSize()==0){
				pagetotal = total / qiYeTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeTongJiPage.getCurrent()) {
			qiYeTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeTongJiPage.getSize() * (qiYeTongJiPage.getCurrent() - 1);
			}
			qiYeTongJiPage.setTotal(total);
			qiYeTongJiPage.setOffsetNo(offsetNo);
			List<QiYeTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selectGetBJTJ(qiYeTongJiPage);
			qiYeTongJiPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return qiYeTongJiPage;
	}

	@Override
	public QiYeTongJiPage selectBJPMTJ(QiYeTongJiPage qiYeTongJiPage) {
		Integer total = qiYeTongJiMapper.selectBJTJPMTotal(qiYeTongJiPage);
		if(qiYeTongJiPage.getSize()==0){
			if(qiYeTongJiPage.getTotal()==0){
				qiYeTongJiPage.setTotal(total);
			}
			if(qiYeTongJiPage.getTotal()==0){
				return qiYeTongJiPage;
			}else {
				List<QiYeTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selectBJPMTJ(qiYeTongJiPage);
				qiYeTongJiPage.setRecords(zhengFuBaoJingTongJiList);
				return qiYeTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeTongJiPage.getSize()==0){
				pagetotal = total / qiYeTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeTongJiPage.getCurrent()) {
			qiYeTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeTongJiPage.getSize() * (qiYeTongJiPage.getCurrent() - 1);
			}
			qiYeTongJiPage.setTotal(total);
			qiYeTongJiPage.setOffsetNo(offsetNo);
			List<QiYeTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selectBJPMTJ(qiYeTongJiPage);
			qiYeTongJiPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return qiYeTongJiPage;
	}

	@Override
	public QiYeTongJiPage selectGetRYXBJTJ(QiYeTongJiPage qiYeTongJiPage) {
		Integer total = qiYeTongJiMapper.selectGetRYXBJTJTotal(qiYeTongJiPage);
		if(qiYeTongJiPage.getSize()==0){
			if(qiYeTongJiPage.getTotal()==0){
				qiYeTongJiPage.setTotal(total);
			}
			if(qiYeTongJiPage.getTotal()==0){
				return qiYeTongJiPage;
			}else {
				List<QiYeRiYunXingTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selectGetRYXBJTJ(qiYeTongJiPage);
				qiYeTongJiPage.setRecords(zhengFuBaoJingTongJiList);
				return qiYeTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeTongJiPage.getSize()==0){
				pagetotal = total / qiYeTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeTongJiPage.getCurrent()) {
			qiYeTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeTongJiPage.getSize() * (qiYeTongJiPage.getCurrent() - 1);
			}
			qiYeTongJiPage.setTotal(total);
			qiYeTongJiPage.setOffsetNo(offsetNo);
			List<QiYeRiYunXingTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selectGetRYXBJTJ(qiYeTongJiPage);
			qiYeTongJiPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return qiYeTongJiPage;
	}

	@Override
	public QiYeOffLineTongJiPage<QiYeOffLineTongJi> selectGet24HoursOffLineTJ(QiYeOffLineTongJiPage qiYeOffLineTongJiPage) {
		Integer total = qiYeTongJiMapper.selectGet24HoursOffLineTJTotal(qiYeOffLineTongJiPage);
		if(qiYeOffLineTongJiPage.getSize()==0){
			if(qiYeOffLineTongJiPage.getTotal()==0){
				qiYeOffLineTongJiPage.setTotal(total);
			}
			if(qiYeOffLineTongJiPage.getTotal()==0){
				return qiYeOffLineTongJiPage;
			}else {
				List<QiYeOffLineTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selectGet24HoursOffLineTJ(qiYeOffLineTongJiPage);
				qiYeOffLineTongJiPage.setRecords(zhengFuBaoJingTongJiList);
				return qiYeOffLineTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeOffLineTongJiPage.getSize()==0){
				pagetotal = total / qiYeOffLineTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeOffLineTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeOffLineTongJiPage.getCurrent()) {
			qiYeOffLineTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeOffLineTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeOffLineTongJiPage.getSize() * (qiYeOffLineTongJiPage.getCurrent() - 1);
			}
			qiYeOffLineTongJiPage.setTotal(total);
			qiYeOffLineTongJiPage.setOffsetNo(offsetNo);
			List<QiYeOffLineTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selectGet24HoursOffLineTJ(qiYeOffLineTongJiPage);
			qiYeOffLineTongJiPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return qiYeOffLineTongJiPage;
	}

	@Override
	public QiYeInOutAreaPage<QiYeInOutAreaTongJi> selectGetInOutAreaTJ(QiYeInOutAreaPage qiYeInOutAreaPage) {
		Integer total = qiYeTongJiMapper.selectGetInOutAreaTJTotal(qiYeInOutAreaPage);
		if(qiYeInOutAreaPage.getSize()==0){
			if(qiYeInOutAreaPage.getTotal()==0){
				qiYeInOutAreaPage.setTotal(total);
			}
			if(qiYeInOutAreaPage.getTotal()==0){
				return qiYeInOutAreaPage;
			}else {
				List<QiYeInOutAreaTongJi> qiYeInOutAreaTongJiList = qiYeTongJiMapper.selectGetInOutAreaTJ(qiYeInOutAreaPage);
				qiYeInOutAreaPage.setRecords(qiYeInOutAreaTongJiList);
				return qiYeInOutAreaPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeInOutAreaPage.getSize()==0){
				pagetotal = total / qiYeInOutAreaPage.getSize();
			}else {
				pagetotal = total / qiYeInOutAreaPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeInOutAreaPage.getCurrent()) {
			qiYeInOutAreaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeInOutAreaPage.getCurrent() > 1) {
				offsetNo = qiYeInOutAreaPage.getSize() * (qiYeInOutAreaPage.getCurrent() - 1);
			}
			qiYeInOutAreaPage.setTotal(total);
			qiYeInOutAreaPage.setOffsetNo(offsetNo);
			List<QiYeInOutAreaTongJi> qiYeInOutAreaTongJiList = qiYeTongJiMapper.selectGetInOutAreaTJ(qiYeInOutAreaPage);
			qiYeInOutAreaPage.setRecords(qiYeInOutAreaTongJiList);
		}
		return qiYeInOutAreaPage;
	}

	@Override
	public QiYeInOutAreaPage<StatementTongJi> selectGetTrajectoryIntegrityTJ(QiYeInOutAreaPage qiYeInOutAreaPage) {
		Integer total = qiYeTongJiMapper.selectGetTrajectoryIntegrityTotal(qiYeInOutAreaPage);
		if(qiYeInOutAreaPage.getSize()==0){
			if(qiYeInOutAreaPage.getTotal()==0){
				qiYeInOutAreaPage.setTotal(total);
			}
			if(qiYeInOutAreaPage.getTotal()==0){
				return qiYeInOutAreaPage;
			}else{
				List<StatementTongJi> statementTongJiList = qiYeTongJiMapper.selectGetTrajectoryIntegrityTJ(qiYeInOutAreaPage);
				qiYeInOutAreaPage.setRecords(statementTongJiList);
				return qiYeInOutAreaPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeInOutAreaPage.getSize()==0){
				pagetotal = total / qiYeInOutAreaPage.getSize();
			}else {
				pagetotal = total / qiYeInOutAreaPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeInOutAreaPage.getCurrent()) {
			qiYeInOutAreaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeInOutAreaPage.getCurrent() > 1) {
				offsetNo = qiYeInOutAreaPage.getSize() * (qiYeInOutAreaPage.getCurrent() - 1);
			}
			qiYeInOutAreaPage.setTotal(total);
			qiYeInOutAreaPage.setOffsetNo(offsetNo);
			List<StatementTongJi> statementTongJiList = qiYeTongJiMapper.selectGetTrajectoryIntegrityTJ(qiYeInOutAreaPage);
			qiYeInOutAreaPage.setRecords(statementTongJiList);
		}
		return qiYeInOutAreaPage;
	}

	@Override
	public QiYeInOutAreaPage<TrajectoryAnomalies> selectGuiJiYiChangTJ(QiYeInOutAreaPage qiYeInOutAreaPage) {
		Integer total = qiYeTongJiMapper.selectGuiJiYiChangTotal(qiYeInOutAreaPage);
		if(qiYeInOutAreaPage.getSize()==0){
			if(qiYeInOutAreaPage.getTotal()==0){
				qiYeInOutAreaPage.setTotal(total);
			}
			if(qiYeInOutAreaPage.getTotal()==0){
				return qiYeInOutAreaPage;
			}else{
				List<TrajectoryAnomalies> trajectoryAnomalies = qiYeTongJiMapper.selectGuiJiYiChangTJ(qiYeInOutAreaPage);
				qiYeInOutAreaPage.setRecords(trajectoryAnomalies);
				return qiYeInOutAreaPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeInOutAreaPage.getSize()==0){
				pagetotal = total / qiYeInOutAreaPage.getSize();
			}else {
				pagetotal = total / qiYeInOutAreaPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeInOutAreaPage.getCurrent()) {
			qiYeInOutAreaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeInOutAreaPage.getCurrent() > 1) {
				offsetNo = qiYeInOutAreaPage.getSize() * (qiYeInOutAreaPage.getCurrent() - 1);
			}
			qiYeInOutAreaPage.setTotal(total);
			qiYeInOutAreaPage.setOffsetNo(offsetNo);
			List<TrajectoryAnomalies> trajectoryAnomalies = qiYeTongJiMapper.selectGuiJiYiChangTJ(qiYeInOutAreaPage);
			qiYeInOutAreaPage.setRecords(trajectoryAnomalies);
		}
		return qiYeInOutAreaPage;
	}

	@Override
	public QiYeInOutAreaPage<StopVehicle> selectTingCheMingXiTJ(QiYeInOutAreaPage qiYeInOutAreaPage) {
		Integer total = qiYeTongJiMapper.selectTingCheMingXiTotal(qiYeInOutAreaPage);
		if(qiYeInOutAreaPage.getSize()==0){
			if(qiYeInOutAreaPage.getTotal()==0){
				qiYeInOutAreaPage.setTotal(total);
			}
			if(qiYeInOutAreaPage.getTotal()==0){
				return qiYeInOutAreaPage;
			}else{
				List<StopVehicle> stopVehicleList = qiYeTongJiMapper.selectTingCheMingXiTJ(qiYeInOutAreaPage);
				qiYeInOutAreaPage.setRecords(stopVehicleList);
				return qiYeInOutAreaPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeInOutAreaPage.getSize()==0){
				pagetotal = total / qiYeInOutAreaPage.getSize();
			}else {
				pagetotal = total / qiYeInOutAreaPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeInOutAreaPage.getCurrent()) {
			qiYeInOutAreaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeInOutAreaPage.getCurrent() > 1) {
				offsetNo = qiYeInOutAreaPage.getSize() * (qiYeInOutAreaPage.getCurrent() - 1);
			}
			qiYeInOutAreaPage.setTotal(total);
			qiYeInOutAreaPage.setOffsetNo(offsetNo);
			List<StopVehicle> stopVehicleList = qiYeTongJiMapper.selectTingCheMingXiTJ(qiYeInOutAreaPage);
			qiYeInOutAreaPage.setRecords(stopVehicleList);
		}
		return qiYeInOutAreaPage;
	}

	@Override
	public QiYeInOutAreaPage<StopVehicle> selectTingCheHuiZongTJ(QiYeInOutAreaPage qiYeInOutAreaPage) {
		Integer total = qiYeTongJiMapper.selectTingCheHuiZongTotal(qiYeInOutAreaPage);
		if(qiYeInOutAreaPage.getSize()==0){
			if(qiYeInOutAreaPage.getTotal()==0){
				qiYeInOutAreaPage.setTotal(total);
			}
			if(qiYeInOutAreaPage.getTotal()==0){
				return qiYeInOutAreaPage;
			}else{
				List<StopVehicle> stopVehicleList = qiYeTongJiMapper.selectTingCheHuiZongTJ(qiYeInOutAreaPage);
				qiYeInOutAreaPage.setRecords(stopVehicleList);
				return qiYeInOutAreaPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeInOutAreaPage.getSize()==0){
				pagetotal = total / qiYeInOutAreaPage.getSize();
			}else {
				pagetotal = total / qiYeInOutAreaPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeInOutAreaPage.getCurrent()) {
			qiYeInOutAreaPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeInOutAreaPage.getCurrent() > 1) {
				offsetNo = qiYeInOutAreaPage.getSize() * (qiYeInOutAreaPage.getCurrent() - 1);
			}
			qiYeInOutAreaPage.setTotal(total);
			qiYeInOutAreaPage.setOffsetNo(offsetNo);
			List<StopVehicle> stopVehicleList = qiYeTongJiMapper.selectTingCheHuiZongTJ(qiYeInOutAreaPage);
			qiYeInOutAreaPage.setRecords(stopVehicleList);
		}
		return qiYeInOutAreaPage;
	}

	@Override
	public QiYeTpvehdataPage<QiYeTpvehdataTongJi> selecttpvehdataTJ(QiYeTpvehdataPage qiYeTpvehdataPage) {
		Integer total = qiYeTongJiMapper.selectGettpvehdataTJTotal(qiYeTpvehdataPage);
		if(qiYeTpvehdataPage.getSize()==0){
			if(qiYeTpvehdataPage.getTotal()==0){
				qiYeTpvehdataPage.setTotal(total);
			}

			if(qiYeTpvehdataPage.getTotal()==0){
				return qiYeTpvehdataPage;
			}else{
				List<QiYeTpvehdataTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selecttpvehdataTJ(qiYeTpvehdataPage);
				zhengFuBaoJingTongJiList.forEach(item->{
					if("0".equals(item.getCheliangzhuangtai())){
						item.setAlarm("在用");
					}
					if("1".equals(item.getCheliangzhuangtai())){
						item.setAlarm("停用");
					}

					if("1".equals(item.getAlarm())){
						item.setAlarm("报警");
					}else{
						item.setAlarm("未报警");
					}

					if("1".equals(item.getLocate())){
						item.setLocate("定位");
					}else{
						item.setLocate("不定位");
					}
                    if(!item.getLatitude().equals(0) || !item.getLongitude().equals(0)){
                        double lat = Double.parseDouble(item.getLatitude().toString());
                        double lon = Double.parseDouble(item.getLongitude().toString());
                        double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
                        item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
                        item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
                        String LocalName= LatLotForLocation.getProvince(item.getLatitude().toString(),item.getLongitude().toString(),alarmServer.getAddressAk());
                        item.setLocalName(LocalName);
                    }
				});
				qiYeTpvehdataPage.setRecords(zhengFuBaoJingTongJiList);
				return qiYeTpvehdataPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeTpvehdataPage.getSize()==0){
				pagetotal = total / qiYeTpvehdataPage.getSize();
			}else {
				pagetotal = total / qiYeTpvehdataPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeTpvehdataPage.getCurrent()) {
			qiYeTpvehdataPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeTpvehdataPage.getCurrent() > 1) {
				offsetNo = qiYeTpvehdataPage.getSize() * (qiYeTpvehdataPage.getCurrent() - 1);
			}
			qiYeTpvehdataPage.setTotal(total);
			qiYeTpvehdataPage.setOffsetNo(offsetNo);
			List<QiYeTpvehdataTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selecttpvehdataTJ(qiYeTpvehdataPage);
			zhengFuBaoJingTongJiList.forEach(item->{
				if("0".equals(item.getCheliangzhuangtai())){
					item.setAlarm("在用");
				}
				if("1".equals(item.getCheliangzhuangtai())){
					item.setAlarm("停用");
				}

				if("1".equals(item.getAlarm())){
					item.setAlarm("报警");
				}else{
					item.setAlarm("未报警");
				}

				if("1".equals(item.getLocate())){
					item.setLocate("定位");
				}else{
					item.setLocate("不定位");
				}
				if(!item.getLatitude().equals(0) || !item.getLongitude().equals(0)){
                    double lat = Double.parseDouble(item.getLatitude().toString());
                    double lon = Double.parseDouble(item.getLongitude().toString());
                    double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
                    item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
                    item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
                    String LocalName= LatLotForLocation.getProvince(item.getLatitude().toString(),item.getLongitude().toString(),alarmServer.getAddressAk());
                    item.setLocalName(LocalName);
                }
			});
			qiYeTpvehdataPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return qiYeTpvehdataPage;
	}

	@Override
	public QiYeDriverAlarmTongJiPage<QiYeDriverAlarmTongJi> seleDriverAlarmTJ(QiYeDriverAlarmTongJiPage qiYeDriverAlarmTongJiPage) {
		Integer total = qiYeTongJiMapper.selectDriverAlarmTJTotal(qiYeDriverAlarmTongJiPage);
		if(qiYeDriverAlarmTongJiPage.getSize()==0){
			if(qiYeDriverAlarmTongJiPage.getTotal()==0){
				qiYeDriverAlarmTongJiPage.setTotal(total);
			}

			if(qiYeDriverAlarmTongJiPage.getTotal()==0){
				return qiYeDriverAlarmTongJiPage;
			}else{
				List<QiYeDriverAlarmTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.seleDriverAlarmTJ(qiYeDriverAlarmTongJiPage);
				qiYeDriverAlarmTongJiPage.setRecords(zhengFuBaoJingTongJiList);
				return qiYeDriverAlarmTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeDriverAlarmTongJiPage.getSize()==0){
				pagetotal = total / qiYeDriverAlarmTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeDriverAlarmTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeDriverAlarmTongJiPage.getCurrent()) {
			qiYeDriverAlarmTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeDriverAlarmTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeDriverAlarmTongJiPage.getSize() * (qiYeDriverAlarmTongJiPage.getCurrent() - 1);
			}
			qiYeDriverAlarmTongJiPage.setTotal(total);
			qiYeDriverAlarmTongJiPage.setOffsetNo(offsetNo);
			List<QiYeDriverAlarmTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.seleDriverAlarmTJ(qiYeDriverAlarmTongJiPage);
			qiYeDriverAlarmTongJiPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return qiYeDriverAlarmTongJiPage;
	}

	@Override
	public QiYeDriverAlarmTongJiPage<QiYeDriverAlarmTongJi> seleDriverAlarmXQTJ(QiYeDriverAlarmTongJiPage qiYeDriverAlarmTongJiPage) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Integer total = qiYeTongJiMapper.selectDriverAlarmTJXQTotal(qiYeDriverAlarmTongJiPage);
		if(qiYeDriverAlarmTongJiPage.getSize()==0){
			if(qiYeDriverAlarmTongJiPage.getTotal()==0){
				qiYeDriverAlarmTongJiPage.setTotal(total);
			}

			if(qiYeDriverAlarmTongJiPage.getTotal()==0){
				return qiYeDriverAlarmTongJiPage;
			}else{
				List<QiYeDriverAlarmTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.seleDriverAlarmXQTJ(qiYeDriverAlarmTongJiPage);
				zhengFuBaoJingTongJiList.forEach(item->{
					if(StringUtils.isBlank(item.getEndresult())){
						if(item.getRemark()==null){
							String days = DateUtils.differDays(df.format(item.getCutoffTime()));
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
							String days = DateUtils.differDays(df.format(item.getCutoffTime()));
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
				});
				qiYeDriverAlarmTongJiPage.setRecords(zhengFuBaoJingTongJiList);
				return qiYeDriverAlarmTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeDriverAlarmTongJiPage.getSize()==0){
				pagetotal = total / qiYeDriverAlarmTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeDriverAlarmTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeDriverAlarmTongJiPage.getCurrent()) {
			qiYeDriverAlarmTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeDriverAlarmTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeDriverAlarmTongJiPage.getSize() * (qiYeDriverAlarmTongJiPage.getCurrent() - 1);
			}
			qiYeDriverAlarmTongJiPage.setTotal(total);
			qiYeDriverAlarmTongJiPage.setOffsetNo(offsetNo);
			List<QiYeDriverAlarmTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.seleDriverAlarmXQTJ(qiYeDriverAlarmTongJiPage);
			zhengFuBaoJingTongJiList.forEach(item->{
				if(StringUtils.isBlank(item.getEndresult())){
					if(item.getRemark()==null){
						String days = DateUtils.differDays(df.format(item.getCutoffTime()));
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
						String days = DateUtils.differDays(df.format(item.getCutoffTime()));
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
			});
			qiYeDriverAlarmTongJiPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return qiYeDriverAlarmTongJiPage;
	}

	@Override
	public QiYeTTSTongJiPage<QiYeTTSTongJi> selectTTSTJ(QiYeTTSTongJiPage qiYeTTSTongJiPage) {
		Integer total = qiYeTongJiMapper.selectTTSTotal(qiYeTTSTongJiPage);
		if(qiYeTTSTongJiPage.getSize()==0){
			if(qiYeTTSTongJiPage.getTotal()==0){
				qiYeTTSTongJiPage.setTotal(total);
			}
			if(qiYeTTSTongJiPage.getTotal()==0){
				return qiYeTTSTongJiPage;
			}else {
				List<QiYeTTSTongJi> qiYeTTSTongJiList = qiYeTongJiMapper.selectTTSTJ(qiYeTTSTongJiPage);
				qiYeTTSTongJiPage.setRecords(qiYeTTSTongJiList);
				return qiYeTTSTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeTTSTongJiPage.getSize()==0){
				pagetotal = total / qiYeTTSTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeTTSTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeTTSTongJiPage.getCurrent()) {
			qiYeTTSTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeTTSTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeTTSTongJiPage.getSize() * (qiYeTTSTongJiPage.getCurrent() - 1);
			}
			qiYeTTSTongJiPage.setTotal(total);
			qiYeTTSTongJiPage.setOffsetNo(offsetNo);
			List<QiYeTTSTongJi> qiYeTTSTongJiList = qiYeTongJiMapper.selectTTSTJ(qiYeTTSTongJiPage);
			qiYeTTSTongJiPage.setRecords(qiYeTTSTongJiList);
		}
		return qiYeTTSTongJiPage;
	}

	@Override
	public QiYeTTSTongJiPage<QiYeMileageTongJi> selectMileageTJ(QiYeTTSTongJiPage qiYeTTSTongJiPage) {
		Integer total = qiYeTongJiMapper.selectMileageTotal(qiYeTTSTongJiPage);
		if(qiYeTTSTongJiPage.getSize()==0){
			if(qiYeTTSTongJiPage.getTotal()==0){
				qiYeTTSTongJiPage.setTotal(total);
			}
			if(qiYeTTSTongJiPage.getTotal()==0){
				return qiYeTTSTongJiPage;
			}else {
				List<QiYeMileageTongJi> qiYeMileageTongJiList = qiYeTongJiMapper.selectMileageTJ(qiYeTTSTongJiPage);
				qiYeTTSTongJiPage.setRecords(qiYeMileageTongJiList);
				return qiYeTTSTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeTTSTongJiPage.getSize()==0){
				pagetotal = total / qiYeTTSTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeTTSTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeTTSTongJiPage.getCurrent()) {
			qiYeTTSTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeTTSTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeTTSTongJiPage.getSize() * (qiYeTTSTongJiPage.getCurrent() - 1);
			}
			qiYeTTSTongJiPage.setTotal(total);
			qiYeTTSTongJiPage.setOffsetNo(offsetNo);
			List<QiYeMileageTongJi> qiYeMileageTongJiList = qiYeTongJiMapper.selectMileageTJ(qiYeTTSTongJiPage);
			qiYeTTSTongJiPage.setRecords(qiYeMileageTongJiList);
		}
		return qiYeTTSTongJiPage;
	}

	@Override
	public QiYeTTSTongJiPage<QiYeMonthMileageTongJi> selectMonthMileageTJ(QiYeTTSTongJiPage qiYeTTSTongJiPage) {
		Integer total = qiYeTongJiMapper.selectMonthMileageTotal(qiYeTTSTongJiPage);
		if(qiYeTTSTongJiPage.getSize()==0){
			if(qiYeTTSTongJiPage.getTotal()==0){
				qiYeTTSTongJiPage.setTotal(total);
			}
			if(qiYeTTSTongJiPage.getTotal()==0){
				return qiYeTTSTongJiPage;
			}else {
				List<QiYeMonthMileageTongJi> qiYeMonthMileageTongJiList = qiYeTongJiMapper.selectMonthMileageTJ(qiYeTTSTongJiPage);
				qiYeTTSTongJiPage.setRecords(qiYeMonthMileageTongJiList);
				return qiYeTTSTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeTTSTongJiPage.getSize()==0){
				pagetotal = total / qiYeTTSTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeTTSTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeTTSTongJiPage.getCurrent()) {
			qiYeTTSTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeTTSTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeTTSTongJiPage.getSize() * (qiYeTTSTongJiPage.getCurrent() - 1);
			}
			qiYeTTSTongJiPage.setTotal(total);
			qiYeTTSTongJiPage.setOffsetNo(offsetNo);
			List<QiYeMonthMileageTongJi> qiYeMonthMileageTongJiList = qiYeTongJiMapper.selectMonthMileageTJ(qiYeTTSTongJiPage);
			qiYeTTSTongJiPage.setRecords(qiYeMonthMileageTongJiList);
		}
		return qiYeTTSTongJiPage;
	}

	@Override
	public QiYeTongJiPage<DeptBaoJingTongJiLv> selectChuLiLvTJ(QiYeTongJiPage qiYeTongJiPage) {
		Integer total = qiYeTongJiMapper.selectChuLiLvTotal(qiYeTongJiPage);
		if(qiYeTongJiPage.getSize()==0){
			if(qiYeTongJiPage.getTotal()==0){
				qiYeTongJiPage.setTotal(total);
			}
			if(qiYeTongJiPage.getTotal()==0){
				return qiYeTongJiPage;
			}else {
				List<DeptBaoJingTongJiLv> qiYeMonthMileageTongJiList = qiYeTongJiMapper.selectChuLiLvTJ(qiYeTongJiPage);
				qiYeTongJiPage.setRecords(qiYeMonthMileageTongJiList);
				return qiYeTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeTongJiPage.getSize()==0){
				pagetotal = total / qiYeTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeTongJiPage.getCurrent()) {
			qiYeTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeTongJiPage.getSize() * (qiYeTongJiPage.getCurrent() - 1);
			}
			qiYeTongJiPage.setTotal(total);
			qiYeTongJiPage.setOffsetNo(offsetNo);
			List<DeptBaoJingTongJiLv> qiYeMonthMileageTongJiList = qiYeTongJiMapper.selectChuLiLvTJ(qiYeTongJiPage);
			qiYeTongJiPage.setRecords(qiYeMonthMileageTongJiList);
		}
		return qiYeTongJiPage;
	}

	@Override
	public QiYeAlarmTongJiPage<DeptChaoSuBaoJingTongJi> selectDeptChaosuTJ(QiYeAlarmTongJiPage qiYeAlarmTongJiPage) {
		Integer total = qiYeTongJiMapper.selectDeptChaosuTotal(qiYeAlarmTongJiPage);
		if(qiYeAlarmTongJiPage.getSize()==0){
			if(qiYeAlarmTongJiPage.getTotal()==0){
				qiYeAlarmTongJiPage.setTotal(total);
			}
			if(qiYeAlarmTongJiPage.getTotal()==0){
				return qiYeAlarmTongJiPage;
			}else {
				List<DeptChaoSuBaoJingTongJi> deptChaoSuBaoJingTongJiList = qiYeTongJiMapper.selectDeptChaosuTJ(qiYeAlarmTongJiPage);
				qiYeAlarmTongJiPage.setRecords(deptChaoSuBaoJingTongJiList);
				return qiYeAlarmTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeAlarmTongJiPage.getSize()==0){
				pagetotal = total / qiYeAlarmTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeAlarmTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeAlarmTongJiPage.getCurrent()) {
			qiYeAlarmTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeAlarmTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeAlarmTongJiPage.getSize() * (qiYeAlarmTongJiPage.getCurrent() - 1);
			}
			qiYeAlarmTongJiPage.setTotal(total);
			qiYeAlarmTongJiPage.setOffsetNo(offsetNo);
			List<DeptChaoSuBaoJingTongJi> deptChaoSuBaoJingTongJiList = qiYeTongJiMapper.selectDeptChaosuTJ(qiYeAlarmTongJiPage);
			qiYeAlarmTongJiPage.setRecords(deptChaoSuBaoJingTongJiList);
		}
		return qiYeAlarmTongJiPage;
	}

	@Override
	public PersistentUnpositioningTongJiPage<DeptPersistentUnpositioningTongJi> selectDeptPersistentUnpositioningTJ(PersistentUnpositioningTongJiPage persistentUnpositioningTongJiPage) {
		Integer total = qiYeTongJiMapper.selectDeptPersistentUnpositioningTotal(persistentUnpositioningTongJiPage);
		if(persistentUnpositioningTongJiPage.getSize()==0){
			if(persistentUnpositioningTongJiPage.getTotal()==0){
				persistentUnpositioningTongJiPage.setTotal(total);
			}
			if(persistentUnpositioningTongJiPage.getTotal()==0){
				return persistentUnpositioningTongJiPage;
			}else {
				List<DeptPersistentUnpositioningTongJi> deptChaoSuBaoJingTongJiList = qiYeTongJiMapper.selectDeptPersistentUnpositioningTJ(persistentUnpositioningTongJiPage);
				persistentUnpositioningTongJiPage.setRecords(deptChaoSuBaoJingTongJiList);
				return persistentUnpositioningTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%persistentUnpositioningTongJiPage.getSize()==0){
				pagetotal = total / persistentUnpositioningTongJiPage.getSize();
			}else {
				pagetotal = total / persistentUnpositioningTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= persistentUnpositioningTongJiPage.getCurrent()) {
			persistentUnpositioningTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (persistentUnpositioningTongJiPage.getCurrent() > 1) {
				offsetNo = persistentUnpositioningTongJiPage.getSize() * (persistentUnpositioningTongJiPage.getCurrent() - 1);
			}
			persistentUnpositioningTongJiPage.setTotal(total);
			persistentUnpositioningTongJiPage.setOffsetNo(offsetNo);
			List<DeptPersistentUnpositioningTongJi> deptChaoSuBaoJingTongJiList = qiYeTongJiMapper.selectDeptPersistentUnpositioningTJ(persistentUnpositioningTongJiPage);
			persistentUnpositioningTongJiPage.setRecords(deptChaoSuBaoJingTongJiList);
		}
		return persistentUnpositioningTongJiPage;
	}

	@Override
	public QiYeAlarmTongJiPage<DeptPiLaoBaoJingTongJi> selectDeptPiLaoTJ(QiYeAlarmTongJiPage qiYeAlarmTongJiPage) {
		Integer total = qiYeTongJiMapper.selectDeptPiLaoTotal(qiYeAlarmTongJiPage);
		if(qiYeAlarmTongJiPage.getSize()==0){
			if(qiYeAlarmTongJiPage.getTotal()==0){
				qiYeAlarmTongJiPage.setTotal(total);
			}
			if(qiYeAlarmTongJiPage.getTotal()==0){
				return qiYeAlarmTongJiPage;
			}else {
				List<DeptPiLaoBaoJingTongJi> deptPiLaoBaoJingTongJiList = qiYeTongJiMapper.selectDeptPiLaoTJ(qiYeAlarmTongJiPage);
				qiYeAlarmTongJiPage.setRecords(deptPiLaoBaoJingTongJiList);
				return qiYeAlarmTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeAlarmTongJiPage.getSize()==0){
				pagetotal = total / qiYeAlarmTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeAlarmTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeAlarmTongJiPage.getCurrent()) {
			qiYeAlarmTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeAlarmTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeAlarmTongJiPage.getSize() * (qiYeAlarmTongJiPage.getCurrent() - 1);
			}
			qiYeAlarmTongJiPage.setTotal(total);
			qiYeAlarmTongJiPage.setOffsetNo(offsetNo);
			List<DeptPiLaoBaoJingTongJi> deptPiLaoBaoJingTongJiList = qiYeTongJiMapper.selectDeptPiLaoTJ(qiYeAlarmTongJiPage);
			qiYeAlarmTongJiPage.setRecords(deptPiLaoBaoJingTongJiList);
		}
		return qiYeAlarmTongJiPage;
	}

	@Override
	public QiYeAlarmTongJiPage<JiashiyuanDakaInfo> selectDaKaInfoTJ(QiYeAlarmTongJiPage qiYeAlarmTongJiPage) {
		Integer total = qiYeTongJiMapper.selectDaKaInfoTotal(qiYeAlarmTongJiPage);
		if(qiYeAlarmTongJiPage.getSize()==0){
			if(qiYeAlarmTongJiPage.getTotal()==0){
				qiYeAlarmTongJiPage.setTotal(total);
			}
			if(qiYeAlarmTongJiPage.getTotal()==0){
				return qiYeAlarmTongJiPage;
			}else {
				List<JiashiyuanDakaInfo> dakaInfoList = qiYeTongJiMapper.selectDaKaInfoTJ(qiYeAlarmTongJiPage);
				qiYeAlarmTongJiPage.setRecords(dakaInfoList);
				return qiYeAlarmTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeAlarmTongJiPage.getSize()==0){
				pagetotal = total / qiYeAlarmTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeAlarmTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeAlarmTongJiPage.getCurrent()) {
			qiYeAlarmTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeAlarmTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeAlarmTongJiPage.getSize() * (qiYeAlarmTongJiPage.getCurrent() - 1);
			}
			qiYeAlarmTongJiPage.setTotal(total);
			qiYeAlarmTongJiPage.setOffsetNo(offsetNo);
			List<JiashiyuanDakaInfo> dakaInfoList = qiYeTongJiMapper.selectDaKaInfoTJ(qiYeAlarmTongJiPage);
			qiYeAlarmTongJiPage.setRecords(dakaInfoList);
		}
		return qiYeAlarmTongJiPage;
	}

	@Override
	public QiYeTongJiPage<QiYeTongJi> selectFYVehAlarmPage(QiYeTongJiPage qiYeTongJiPage) {
		Integer total = qiYeTongJiMapper.selectFYVehAlarmTotal(qiYeTongJiPage);
		if(qiYeTongJiPage.getSize()==0){
			if(qiYeTongJiPage.getTotal()==0){
				qiYeTongJiPage.setTotal(total);
			}
			if(qiYeTongJiPage.getTotal()==0){
				return qiYeTongJiPage;
			}else {
				List<QiYeTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selectFYVehAlarmPage(qiYeTongJiPage);
				qiYeTongJiPage.setRecords(zhengFuBaoJingTongJiList);
				return qiYeTongJiPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeTongJiPage.getSize()==0){
				pagetotal = total / qiYeTongJiPage.getSize();
			}else {
				pagetotal = total / qiYeTongJiPage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeTongJiPage.getCurrent()) {
			qiYeTongJiPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeTongJiPage.getCurrent() > 1) {
				offsetNo = qiYeTongJiPage.getSize() * (qiYeTongJiPage.getCurrent() - 1);
			}
			qiYeTongJiPage.setTotal(total);
			qiYeTongJiPage.setOffsetNo(offsetNo);
			List<QiYeTongJi> zhengFuBaoJingTongJiList = qiYeTongJiMapper.selectFYVehAlarmPage(qiYeTongJiPage);
			qiYeTongJiPage.setRecords(zhengFuBaoJingTongJiList);
		}
		return qiYeTongJiPage;
	}

}
