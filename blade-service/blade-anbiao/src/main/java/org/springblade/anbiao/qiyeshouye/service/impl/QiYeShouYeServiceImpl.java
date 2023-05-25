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
import org.springblade.anbiao.lawsRegulations.page.AnBiaoLawsRegulationsPage;
import org.springblade.anbiao.qiyeshouye.entity.*;
import org.springblade.anbiao.qiyeshouye.mapper.QiYeShouYeMapper;
import org.springblade.anbiao.qiyeshouye.page.AnBiaoDeptWechatInfoPage;
import org.springblade.anbiao.qiyeshouye.page.AnBiaoWeeksHiddenTroublePage;
import org.springblade.anbiao.qiyeshouye.page.QiYeShouYePage;
import org.springblade.anbiao.qiyeshouye.service.IQiYeShouYeService;
import org.springblade.anbiao.zhengfu.entity.ThisMonthInfo;
import org.springblade.anbiao.zhengfu.entity.ZhengFuBaoJingTongJiLv;
import org.springblade.system.entity.Dept;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/7/4
 * @描述
 */
@Service
@AllArgsConstructor
public class QiYeShouYeServiceImpl extends ServiceImpl<QiYeShouYeMapper, QiYeShouYe> implements IQiYeShouYeService {

	private QiYeShouYeMapper qiYeShouYeMapper;

	@Override
	public List<Dept> QiYeList(Integer deptId) {
		return qiYeShouYeMapper.QiYeList(deptId);
	}

	@Override
	public QiYeShouYe selectMonthVehcile(String deptId) {
		return qiYeShouYeMapper.selectMonthVehcile(deptId);
	}

	@Override
	public QiYeShouYe selectYearAlarm(String deptId,String year) {
		return qiYeShouYeMapper.selectYearAlarm(deptId,year);
	}

	@Override
	public List<QiYeShouYe> selectYearAlarmTendency(String deptId,String year) {
		return qiYeShouYeMapper.selectYearAlarmTendency(deptId,year);
	}

	@Override
	public QiYeShouYe selectSevenAlarmStatistics(String deptId) throws UnsupportedEncodingException {
		List<QiYeShouYe> qiYeShouYes = qiYeShouYeMapper.selectSevenAlarmStatistics(deptId);
		QiYeShouYe qiYeShouYeList = new QiYeShouYe();
		Integer sevenzongbaojingshu = 0;
		Integer sevenzongweichulishu = 0;
		Integer sevengpsweichulishu = 0;
		Integer sevenshebeiweichulishu = 0;
		Integer sevenzongchulishu = 0;
		for (int i=0;i<qiYeShouYes.size();i++) {
			sevenzongbaojingshu += qiYeShouYes.get(i).getBaojingcishu();
			sevenzongweichulishu += qiYeShouYes.get(i).getWeichulizongshu();
			sevengpsweichulishu += qiYeShouYes.get(i).getBdweichulishu();
			sevenshebeiweichulishu += qiYeShouYes.get(i).getSbweichulishu();
			sevenzongchulishu += qiYeShouYes.get(i).getChulizongshu();
		}
		if(sevenzongchulishu == 0 && sevenzongbaojingshu ==0){
			qiYeShouYeList.setSevenchulilv("100%");
		}else{
			double lv = (sevenzongchulishu*1.0/sevenzongbaojingshu);
			//##.00%   百分比格式，后面不足2位的用0补齐
			DecimalFormat df1 = new DecimalFormat("0.00%");
			String sevenchulilv = df1.format(lv);
			byte[] b = sevenchulilv.getBytes("UTF-8");
			String n = new String(b,"UTF-8");
			qiYeShouYeList.setSevenchulilv(n);
		}
		qiYeShouYeList.setSevenzongbaojingshu(sevenzongbaojingshu);
		qiYeShouYeList.setSevenzongweichulishu(sevenzongweichulishu);
		qiYeShouYeList.setSevengpsweichulishu(sevengpsweichulishu);
		qiYeShouYeList.setSevenshebeiweichulishu(sevenshebeiweichulishu);
		qiYeShouYeList.setSevenList(qiYeShouYes);
		return qiYeShouYeList;
	}

	@Override
	public List<QiYeYunWeiShouYe> selectOperational(String deptId) {
		return qiYeShouYeMapper.selectOperational(deptId);
	}

	@Override
	public QiYeYunWeiShouYe selectMarkRemind(String deptId) {
		return qiYeShouYeMapper.selectMarkRemind(deptId);
	}

	@Override
	public QiYeYunWeiShouYe selectQiYeCount() {
		return qiYeShouYeMapper.selectQiYeCount();
	}

	@Override
	public QiYeYunWeiShouYe selectABQiYeCount() {
		return qiYeShouYeMapper.selectABQiYeCount();
	}

	@Override
	public List<QiYeAnBiao> selectScheduleReminders(String deptId, String dateTime) {
		return qiYeShouYeMapper.selectScheduleReminders(deptId, dateTime);
	}

	@Override
	public List<QiYeAnBiaoMuLu> selectQiYeAnBiaoMuLu(String deptId) {
		return qiYeShouYeMapper.selectQiYeAnBiaoMuLu(deptId);
	}

	@Override
	public QiYeAnBiaoPeriodRate selectPeriodControlRates(String deptId) {
		return qiYeShouYeMapper.selectPeriodControlRates(deptId);
	}

	@Override
	public List<QiYeAnBiaoSafetyTips> selectSafetyTips(String deptId) {
		return qiYeShouYeMapper.selectSafetyTips(deptId);
	}

	@Override
	public QiYeAnBiaoSafetyTips selectSafetyTipsZNum(String deptId, Integer xiangId) {
		return qiYeShouYeMapper.selectSafetyTipsZNum(deptId, xiangId);
	}

	@Override
	public QiYeAnBiaoSafetyTips selectSafetyTipsNum(String deptId, Integer xiangId) {
		return qiYeShouYeMapper.selectSafetyTipsNum(deptId, xiangId);
	}

	@Override
	public int selectGetByIdOnDeptId(String deptId) {
		return qiYeShouYeMapper.selectGetByIdOnDeptId(deptId);
	}

	@Override
	public int insertSelective(PersonLearnInfo personLearnInfo) {
		return qiYeShouYeMapper.insertSelective(personLearnInfo);
	}

	@Override
	public PersonLearnInfo getByName(Integer lyear, Integer lmonth, String username, String deptname) {
		return qiYeShouYeMapper.getByName(lyear, lmonth, username, deptname);
	}

	@Override
	public int updateSelective(PersonLearnInfo personLearnInfo) {
		return qiYeShouYeMapper.updateSelective(personLearnInfo);
	}

	@Override
	public QiYeShouYePage<PersonLearnInfo> selectPersonLearnInfoAll(QiYeShouYePage qiYeShouYePage) {
		Integer total = qiYeShouYeMapper.selectPersonLearnInfoAllTotal(qiYeShouYePage);
		if(qiYeShouYePage.getSize()==0){
			if(qiYeShouYePage.getTotal()==0){
				qiYeShouYePage.setTotal(total);
			}
			if(qiYeShouYePage.getTotal()==0){
				return qiYeShouYePage;
			}else{
				List<PersonLearnInfo> personLearnInfoList = qiYeShouYeMapper.selectPersonLearnInfoAll(qiYeShouYePage);
				qiYeShouYePage.setRecords(personLearnInfoList);
				return qiYeShouYePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeShouYePage.getSize()==0){
				pagetotal = total / qiYeShouYePage.getSize();
			}else {
				pagetotal = total / qiYeShouYePage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeShouYePage.getCurrent()) {
			qiYeShouYePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeShouYePage.getCurrent() > 1) {
				offsetNo = qiYeShouYePage.getSize() * (qiYeShouYePage.getCurrent() - 1);
			}
			qiYeShouYePage.setTotal(total);
			qiYeShouYePage.setOffsetNo(offsetNo);
			List<PersonLearnInfo> personLearnInfoList = qiYeShouYeMapper.selectPersonLearnInfoAll(qiYeShouYePage);
			qiYeShouYePage.setRecords(personLearnInfoList);
		}
		return qiYeShouYePage;
	}

	@Override
	public QiYeShouYePage<PersonLearnInfo> selectZFPersonLearnInfoAll(QiYeShouYePage qiYeShouYePage) {
		Integer total = qiYeShouYeMapper.selectZFPersonLearnInfoAllTotal(qiYeShouYePage);
		if(qiYeShouYePage.getSize()==0){
			if(qiYeShouYePage.getTotal()==0){
				qiYeShouYePage.setTotal(total);
			}
			if(qiYeShouYePage.getTotal()==0){
				return qiYeShouYePage;
			}else{
				List<PersonLearnInfo> personLearnInfoList = qiYeShouYeMapper.selectZFPersonLearnInfoAll(qiYeShouYePage);
				qiYeShouYePage.setRecords(personLearnInfoList);
				return qiYeShouYePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeShouYePage.getSize()==0){
				pagetotal = total / qiYeShouYePage.getSize();
			}else {
				pagetotal = total / qiYeShouYePage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeShouYePage.getCurrent()) {
			qiYeShouYePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeShouYePage.getCurrent() > 1) {
				offsetNo = qiYeShouYePage.getSize() * (qiYeShouYePage.getCurrent() - 1);
			}
			qiYeShouYePage.setTotal(total);
			qiYeShouYePage.setOffsetNo(offsetNo);
			List<PersonLearnInfo> personLearnInfoList = qiYeShouYeMapper.selectZFPersonLearnInfoAll(qiYeShouYePage);
			qiYeShouYePage.setRecords(personLearnInfoList);
		}
		return qiYeShouYePage;
	}

	@Override
	public QiYeShouYePage<PersonLearnInfo> selectZFPersonLearnCoutAll(QiYeShouYePage qiYeShouYePage) {
		Integer total = qiYeShouYeMapper.selectZFPersonLearnCoutAllTotal(qiYeShouYePage);
		if(qiYeShouYePage.getSize()==0){
			if(qiYeShouYePage.getTotal()==0){
				qiYeShouYePage.setTotal(total);
			}
			if(qiYeShouYePage.getTotal()==0){
				return qiYeShouYePage;
			}else{
				List<PersonLearnInfo> personLearnInfoList = qiYeShouYeMapper.selectZFPersonLearnCoutAll(qiYeShouYePage);
				qiYeShouYePage.setRecords(personLearnInfoList);
				return qiYeShouYePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeShouYePage.getSize()==0){
				pagetotal = total / qiYeShouYePage.getSize();
			}else {
				pagetotal = total / qiYeShouYePage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeShouYePage.getCurrent()) {
			qiYeShouYePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeShouYePage.getCurrent() > 1) {
				offsetNo = qiYeShouYePage.getSize() * (qiYeShouYePage.getCurrent() - 1);
			}
			qiYeShouYePage.setTotal(total);
			qiYeShouYePage.setOffsetNo(offsetNo);
			List<PersonLearnInfo> personLearnInfoList = qiYeShouYeMapper.selectZFPersonLearnCoutAll(qiYeShouYePage);
			qiYeShouYePage.setRecords(personLearnInfoList);
		}
		return qiYeShouYePage;
	}

	@Override
	public int insertOffLearnSelective(PersonLearnRemark personLearnRemark) {
		return qiYeShouYeMapper.insertOffLearnSelective(personLearnRemark);
	}

	@Override
	public int updateOffLearnSelective(PersonLearnRemark personLearnRemark) {
		return qiYeShouYeMapper.updateOffLearnSelective(personLearnRemark);
	}

	@Override
	public PersonLearnRemark selectByIds(Integer Id) {
		return qiYeShouYeMapper.selectByIds(Id);
	}

	@Override
	public QiYeShouYePage<PersonLearnRemark> selectPersonLearnRemarkAll(QiYeShouYePage qiYeShouYePage) {
		Integer total = qiYeShouYeMapper.selectPersonLearnRemarkAllTotal(qiYeShouYePage);
		if(qiYeShouYePage.getSize()==0){
			if(qiYeShouYePage.getTotal()==0){
				qiYeShouYePage.setTotal(total);
			}
			if(qiYeShouYePage.getTotal()==0){
				return qiYeShouYePage;
			}else{
				List<PersonLearnRemark> personLearnInfoList = qiYeShouYeMapper.selectPersonLearnRemarkAll(qiYeShouYePage);
				qiYeShouYePage.setRecords(personLearnInfoList);
				return qiYeShouYePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%qiYeShouYePage.getSize()==0){
				pagetotal = total / qiYeShouYePage.getSize();
			}else {
				pagetotal = total / qiYeShouYePage.getSize() + 1;
			}
		}
		if (pagetotal >= qiYeShouYePage.getCurrent()) {
			qiYeShouYePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (qiYeShouYePage.getCurrent() > 1) {
				offsetNo = qiYeShouYePage.getSize() * (qiYeShouYePage.getCurrent() - 1);
			}
			qiYeShouYePage.setTotal(total);
			qiYeShouYePage.setOffsetNo(offsetNo);
			List<PersonLearnRemark> personLearnInfoList = qiYeShouYeMapper.selectPersonLearnRemarkAll(qiYeShouYePage);
			qiYeShouYePage.setRecords(personLearnInfoList);
		}
		return qiYeShouYePage;
	}

	@Override
	public boolean insertDeptWechatSelective(AnBiaoDeptWechatInfo anBiaoDeptWechatInfo) {
		return qiYeShouYeMapper.insertDeptWechatSelective(anBiaoDeptWechatInfo);
	}

	@Override
	public boolean updateDeptWechatSelective(AnBiaoDeptWechatInfo anBiaoDeptWechatInfo) {
		return qiYeShouYeMapper.updateDeptWechatSelective(anBiaoDeptWechatInfo);
	}

	@Override
	public AnBiaoDeptWechatInfoPage<AnBiaoDeptWechatInfo> selectDeptWechatGetAll(AnBiaoDeptWechatInfoPage anBiaoDeptWechatInfoPage) {
		Integer total = qiYeShouYeMapper.selectDeptWechatGetAllTotal(anBiaoDeptWechatInfoPage);
		if(anBiaoDeptWechatInfoPage.getSize()==0){
			if(anBiaoDeptWechatInfoPage.getTotal()==0){
				anBiaoDeptWechatInfoPage.setTotal(total);
			}
			if(anBiaoDeptWechatInfoPage.getTotal()==0){
				return anBiaoDeptWechatInfoPage;
			}else{
				List<AnBiaoDeptWechatInfo> anBiaoDeptWechatInfoList = qiYeShouYeMapper.selectDeptWechatGetAll(anBiaoDeptWechatInfoPage);
				anBiaoDeptWechatInfoPage.setRecords(anBiaoDeptWechatInfoList);
				return anBiaoDeptWechatInfoPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anBiaoDeptWechatInfoPage.getSize()==0){
				pagetotal = total / anBiaoDeptWechatInfoPage.getSize();
			}else {
				pagetotal = total / anBiaoDeptWechatInfoPage.getSize() + 1;
			}
		}
		if (pagetotal >= anBiaoDeptWechatInfoPage.getCurrent()) {
			anBiaoDeptWechatInfoPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anBiaoDeptWechatInfoPage.getCurrent() > 1) {
				offsetNo = anBiaoDeptWechatInfoPage.getSize() * (anBiaoDeptWechatInfoPage.getCurrent() - 1);
			}
			anBiaoDeptWechatInfoPage.setTotal(total);
			anBiaoDeptWechatInfoPage.setOffsetNo(offsetNo);
			List<AnBiaoDeptWechatInfo> anBiaoDeptWechatInfoList = qiYeShouYeMapper.selectDeptWechatGetAll(anBiaoDeptWechatInfoPage);
			anBiaoDeptWechatInfoPage.setRecords(anBiaoDeptWechatInfoList);
		}
		return anBiaoDeptWechatInfoPage;
	}

	@Override
	public boolean deleteDeptWechatInfo(String updateTime,Integer updateUser,Integer Id) {
		return qiYeShouYeMapper.deleteDeptWechatInfo(updateTime, updateUser, Id);
	}

	@Override
	public AnBiaoDeptWechatInfo selectDeptWechatInfoById(Integer id,String weChatCode,String deptId) {
		return qiYeShouYeMapper.selectDeptWechatInfoById(id,weChatCode,deptId);
	}

	@Override
	public int selectMaxId() {
		return qiYeShouYeMapper.selectMaxId();
	}

	@Override
	public boolean insertDeptWechatRemarkSelective(AnBiaoDeptWechatRemark anBiaoDeptWechatRemark) {
		return qiYeShouYeMapper.insertDeptWechatRemarkSelective(anBiaoDeptWechatRemark);
	}

	@Override
	public boolean updateDeptWechatRemarkSelective(AnBiaoDeptWechatRemark anBiaoDeptWechatRemark) {
		return qiYeShouYeMapper.updateDeptWechatRemarkSelective(anBiaoDeptWechatRemark);
	}

	@Override
	public boolean deleteDeptWechatRemark(String updateTime, Integer updateUser, Integer Id) {
		return qiYeShouYeMapper.deleteDeptWechatRemark(updateTime, updateUser, Id);
	}

	@Override
	public List<AnBiaoDeptWechatRemark> selectDeptWechatRemarkById(Integer Id, String alarmtype, String reminder) {
		return qiYeShouYeMapper.selectDeptWechatRemarkById(Id, alarmtype, reminder);
	}

	@Override
	public boolean insertWeeksHiddenTroubleSelective(AnBiaoWeeksHiddenTrouble anBiaoWeeksHiddenTrouble) {
		return qiYeShouYeMapper.insertWeeksHiddenTroubleSelective(anBiaoWeeksHiddenTrouble);
	}

	@Override
	public boolean updateWeeksHiddenTroubleSelective(AnBiaoWeeksHiddenTrouble anBiaoWeeksHiddenTrouble) {
		return qiYeShouYeMapper.updateWeeksHiddenTroubleSelective(anBiaoWeeksHiddenTrouble);
	}

	@Override
	public AnBiaoWeeksHiddenTrouble selectWeeksHiddenTroubleById(Integer Id, Integer type, Integer deptId, String vehId, String bignDate, String endDate) {
		return qiYeShouYeMapper.selectWeeksHiddenTroubleById(Id, type, deptId, vehId, bignDate, endDate);
	}


	@Override
	public AnBiaoWeeksHiddenTroublePage<AnBiaoWeeksHiddenTrouble> selectWeeksHiddenTroubleGetAll(AnBiaoWeeksHiddenTroublePage anBiaoWeeksHiddenTroublePage) {
		Integer total = qiYeShouYeMapper.selectWeeksHiddenTroubleGetAllTotal(anBiaoWeeksHiddenTroublePage);
		if(anBiaoWeeksHiddenTroublePage.getSize()==0){
			if(anBiaoWeeksHiddenTroublePage.getTotal()==0){
				anBiaoWeeksHiddenTroublePage.setTotal(total);
			}
			if(anBiaoWeeksHiddenTroublePage.getTotal()==0){
				return anBiaoWeeksHiddenTroublePage;
			}else{
				List<AnBiaoWeeksHiddenTrouble> anBiaoWeeksHiddenTroubleList = qiYeShouYeMapper.selectWeeksHiddenTroubleGetAll(anBiaoWeeksHiddenTroublePage);
				anBiaoWeeksHiddenTroublePage.setRecords(anBiaoWeeksHiddenTroubleList);
				return anBiaoWeeksHiddenTroublePage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%anBiaoWeeksHiddenTroublePage.getSize()==0){
				pagetotal = total / anBiaoWeeksHiddenTroublePage.getSize();
			}else {
				pagetotal = total / anBiaoWeeksHiddenTroublePage.getSize() + 1;
			}
		}
		if (pagetotal >= anBiaoWeeksHiddenTroublePage.getCurrent()) {
			anBiaoWeeksHiddenTroublePage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (anBiaoWeeksHiddenTroublePage.getCurrent() > 1) {
				offsetNo = anBiaoWeeksHiddenTroublePage.getSize() * (anBiaoWeeksHiddenTroublePage.getCurrent() - 1);
			}
			anBiaoWeeksHiddenTroublePage.setTotal(total);
			anBiaoWeeksHiddenTroublePage.setOffsetNo(offsetNo);
			List<AnBiaoWeeksHiddenTrouble> anBiaoWeeksHiddenTroubleList = qiYeShouYeMapper.selectWeeksHiddenTroubleGetAll(anBiaoWeeksHiddenTroublePage);
			anBiaoWeeksHiddenTroublePage.setRecords(anBiaoWeeksHiddenTroubleList);
		}
		return anBiaoWeeksHiddenTroublePage;
	}

	@Override
	public ZhengFuBaoJingTongJiLv selectMonthAlarm(Integer deptId, String date) {
		return qiYeShouYeMapper.selectMonthAlarm(deptId, date);
	}

	@Override
	public ThisMonthInfo selectThisMonthInfo(Integer deptId) {
		return qiYeShouYeMapper.selectThisMonthInfo(deptId);
	}

}
