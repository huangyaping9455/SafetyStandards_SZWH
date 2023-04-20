/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.alarm.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.alarm.entity.*;
import org.springblade.alarm.mapper.AlarmsummaryCutofftimeMapper;
import org.springblade.alarm.mapper.DriverbehaviorMapper;
import org.springblade.alarm.page.AlarmPage;
import org.springblade.alarm.page.AlarmTimePage;
import org.springblade.alarm.page.ShishiBaojingTongjiPage;
import org.springblade.alarm.service.IAlarmsummaryCutofftimeService;
import org.springblade.alarm.vo.AlarmsummaryCutofftimeVO;
import org.springblade.alarm.vo.DriverbehaviorVO;
import org.springblade.anbiao.zhengfu.entity.TtsMessageInfo;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.enumconstant.EnmuAlarm;
import org.springblade.common.tool.DateUtils;
import org.springblade.common.tool.GpsToBaiduUtil;
import org.springblade.common.tool.LatLotForLocation;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * gps平台报警 服务实现类
 *
 * @author hyp
 * @since 2019-05-12
 */
@Service
@AllArgsConstructor
public class AlarmsummaryCutofftimeServiceImpl extends ServiceImpl<AlarmsummaryCutofftimeMapper, AlarmsummaryCutofftime> implements IAlarmsummaryCutofftimeService {

    private AlarmsummaryCutofftimeMapper cutofftimeMapper;
    private MongoTemplate mongoTemplate;
	private DriverbehaviorMapper driverbehaviorMapper;
	private AlarmServer alarmServer;

    @Override
    public AlarmPage<AlarmsummaryCutofftimeVO> selectAlarmPage(AlarmPage alarmPage) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	if("未申诉".equals(alarmPage.getShifoushenshu()) && "已处理".equals(alarmPage.getShifouchuli())){
			alarmPage.setShifoushenshu("");
		}else if("已申诉".equals(alarmPage.getShifoushenshu()) && "未处理".equals(alarmPage.getShifouchuli())){
			alarmPage.setShifouchuli("");
		}else if("已申诉".equals(alarmPage.getShifoushenshu()) && "已处理".equals(alarmPage.getShifouchuli())){

		}
        Integer total = cutofftimeMapper.selectAlarmTotal(alarmPage);
        if(total==0){
            return alarmPage;
        }
        //导出
		if(alarmPage.getSize()==0){
			if(alarmPage.getTotal()==0){
				alarmPage.setTotal(total);
			}
			List<AlarmsummaryCutofftimeVO> list=cutofftimeMapper.selectAlarmPage(alarmPage);
			list.forEach(item->{
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
			alarmPage.setRecords(list);
			return alarmPage;
		}
		//分页查询
        Integer pagetotal = 0;
        if (total > 0) {
            pagetotal = total / alarmPage.getSize() + 1;
        }
        if (pagetotal >= alarmPage.getCurrent() && pagetotal > 0) {
            alarmPage.setPageTotal(pagetotal);
            Integer offsetNo = 0;
            if (alarmPage.getCurrent() > 1) {
                offsetNo = alarmPage.getSize() * (alarmPage.getCurrent() - 1);
            }
            alarmPage.setTotal(total);
            alarmPage.setOffsetNo(offsetNo);
            List<AlarmsummaryCutofftimeVO> rows = cutofftimeMapper.selectAlarmPage(alarmPage);
            rows.forEach(item->{
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
					}
					else if(item.getRemark()==1){
						EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(item.getChulizhuangtai()));
						item.setChulizhuangtai(byValue.desc);
						item.setShensuzhuangtai("未申诉");
					}
					else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 0){
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(4));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}
					else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 1){
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(5));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}
					else if(item.getRemark() == 0 && item.getShensushenhebiaoshi() == 2){
						EnmuAlarm.ShensuJieguo byValue = EnmuAlarm.ShensuJieguo.getByValue(Integer.valueOf(6));
						item.setShensuzhuangtai(byValue.desc);
						item.setChulizhuangtai("未处理");
						item.setShensumiaoshu(item.getChulimiaoshu());
						item.setShensuxingshi(item.getChulixingshi());
						item.setChulimiaoshu("");
						item.setChulixingshi("");
					}
					else{
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
						EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.parseInt(item.getChulizhuangtai()));
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
				if(!"24小时不在线报警".equals(item.getAlarmType()) && !"不定位报警".equals(item.getAlarmType())){
					if(StringUtils.isBlank(item.getRoadName())){
						double lat = Double.parseDouble(item.getLatitude().toString());
						double lon = Double.parseDouble(item.getLongitude().toString());
						double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
						item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
						item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
						String LocalName= LatLotForLocation.getProvince(item.getLatitude().toString(),item.getLongitude().toString(),alarmServer.getAddressAk());
						item.setRoadName(LocalName);
					}
				}
            });
            alarmPage.setRecords(rows);
            //添加报警统计
            AlarmBaojingTongji baojingTongji = cutofftimeMapper.selectBaojingtongji(alarmPage);
            baojingTongji.setAlarmType(alarmPage.getAlarmType());
            baojingTongji.setCountDate(alarmPage.getBeginTime() + "-" + alarmPage.getEndTime());
            int handlecount = baojingTongji.getHandledCount();
            int alarmcount = baojingTongji.getAlarmCount();
            String handlerate = "0%";
            if (handlecount > 0 && alarmcount > 0) {
                handlerate = (float)Math.round((float) handlecount / (float) alarmcount * 10000)/100 + "%";
            }
            baojingTongji.setHandledRate(handlerate);
            alarmPage.setBaojingTongji(baojingTongji);
        }
        return alarmPage;
    }

    @Override
    public Map<String, Object> selectShishiBaojingTongji(ShishiBaojingTongjiPage page) {
        return cutofftimeMapper.selectShishiBaojingTongji(page);
    }

	@Override
	public AlarmsummaryCutofftimeVO selectAlarmDetail(String id,String baojingleixing) {
		return cutofftimeMapper.selectAlarmDetail(id,baojingleixing);
	}

    @Override
    public BaobiaoAlarmsummaryRecord selectAlarmsummaryRecord(String id,String AlarmType,String type) {
		BaobiaoAlarmsummaryRecord baobiaoAlarmsummaryRecord = new BaobiaoAlarmsummaryRecord();
		BaobiaoAlarmsummaryRecord xiaji = new BaobiaoAlarmsummaryRecord();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//根据报警ID查询报警详情
		AlarmsummaryCutofftimeVO alarmsummaryCutofftimeVO = null;
		DriverbehaviorVO vo = null;
		if("1".equals(type)) {
			//北斗报警详情查询
			alarmsummaryCutofftimeVO = cutofftimeMapper.selectAlarmSMDetail(id, AlarmType);
			if (alarmsummaryCutofftimeVO == null) {
				return null;
			}
			//根据报警ID查询报警生命周期记录
			List<BaobiaoAlarmsummaryRecord> ninety_nine_List = cutofftimeMapper.selectAlarmsummaryRecord(99,alarmsummaryCutofftimeVO.getAlarmGuid());
			List<BaobiaoAlarmsummaryRecord> zero_List = cutofftimeMapper.selectAlarmsummaryRecord(0,alarmsummaryCutofftimeVO.getAlarmGuid());
			List<MonitorContral> monitorContralList = cutofftimeMapper.selectMonitorContralById(alarmsummaryCutofftimeVO.getAlarmGuid());
			if(ninety_nine_List.size() > 0){
				ninety_nine_List.forEach(item->{
					//预警
					if(item.getLevel() == 99){
						baobiaoAlarmsummaryRecord.setWarningTime(item.getBeginTime());
						xiaji.setWarningRemark("平台AI智能下发TTS消息提醒");
					}
				});
			}
//			else{
//				baobiaoAlarmsummaryRecord.setWarningTime(df.format(alarmsummaryCutofftimeVO.getBeginTime()));
//			}
			if(zero_List.size() > 0){
				zero_List.forEach(item->{
					//一级报警
					if(item.getLevel() == 1){
						baobiaoAlarmsummaryRecord.setFirstAlarmTime(item.getBeginTime());
					}
					//二级报警
					if(item.getLevel() == 2){
						baobiaoAlarmsummaryRecord.setSecondAlarmTime(item.getBeginTime());
					}
					//三级报警
					if(item.getLevel() == 3){
						baobiaoAlarmsummaryRecord.setThreeLevelAlarmTime(item.getBeginTime());
					}
					//四级报警
					if(item.getLevel() == 4){
						baobiaoAlarmsummaryRecord.setFourLevelAlarmTime(item.getBeginTime());
					}
					//五级报警
					if(item.getLevel() == 5){
						baobiaoAlarmsummaryRecord.setFirstAlarmTime(item.getBeginTime());
					}
				});
			}
			if(monitorContralList.size() > 0){
				monitorContralList.forEach(item->{
					//一级报警
					if(item.getAlarmLevel() == 1){
						EnmuAlarm.NuclearJieguo byValue = EnmuAlarm.NuclearJieguo.getByValue(item.getContralType());
						xiaji.setFirstAlarmRemark(byValue.desc);
						xiaji.setFirstAlarmRemarkTime(item.getAlarmTime());
					}
					//二级报警
					if(item.getAlarmLevel() == 2){
						EnmuAlarm.NuclearJieguo byValue = EnmuAlarm.NuclearJieguo.getByValue(item.getContralType());
						xiaji.setSecondAlarmRemark(byValue.desc);
						xiaji.setSecondAlarmRemarkTime(item.getAlarmTime());
					}
					//三级报警
					if(item.getAlarmLevel() == 3){
						EnmuAlarm.NuclearJieguo byValue = EnmuAlarm.NuclearJieguo.getByValue(item.getContralType());
						xiaji.setThreeLevelAlarmRemark(byValue.desc);
						xiaji.setThreeLevelAlarmRemarkTime(item.getAlarmTime());
					}
					//四级报警
					if(item.getAlarmLevel() == 4){
						EnmuAlarm.NuclearJieguo byValue = EnmuAlarm.NuclearJieguo.getByValue(item.getContralType());
						xiaji.setFourLevelAlarmRemark(byValue.desc);
						xiaji.setFourLevelAlarmRemarkTime(item.getAlarmTime());
					}
					//五级报警
					if(item.getAlarmLevel() == 5){
						EnmuAlarm.NuclearJieguo byValue = EnmuAlarm.NuclearJieguo.getByValue(item.getContralType());
						xiaji.setFirstAlarmRemark(byValue.desc);
						xiaji.setFirstAlarmRemarkTime(item.getAlarmTime());
					}
				});
			}else{
				List<TtsMessageInfo> ttsMessageInfoList = cutofftimeMapper.selectByAlarmGuId(alarmsummaryCutofftimeVO.getAlarmGuid());
				if(ttsMessageInfoList.size() > 0){
					xiaji.setFirstAlarmRemark(ttsMessageInfoList.get(0).getSendType()+ttsMessageInfoList.get(0).getMessageType());
					xiaji.setFirstAlarmRemarkTime(ttsMessageInfoList.get(0).getTime());
				}
			}
			if(alarmsummaryCutofftimeVO != null) {
				baobiaoAlarmsummaryRecord.setVerifyTime(df.format(alarmsummaryCutofftimeVO.getVerifyTime()));
				baobiaoAlarmsummaryRecord.setVerifyName(alarmsummaryCutofftimeVO.getVerifyName());
				xiaji.setVerifyTime(df.format(alarmsummaryCutofftimeVO.getVerifyTime()));
				xiaji.setVerifyRemark("监控员核实后，推报给企业");
				if ("1".equals(alarmsummaryCutofftimeVO.getEndresult())) {
					if (alarmsummaryCutofftimeVO.getRemark() == 0 && alarmsummaryCutofftimeVO.getShensushenhebiaoshi() == 0) {
						if (alarmsummaryCutofftimeVO.getChulishijian() != null) {
							baobiaoAlarmsummaryRecord.setAppealOfTime(df.format(alarmsummaryCutofftimeVO.getChulishijian()));
						}
						baobiaoAlarmsummaryRecord.setAppealOfName(alarmsummaryCutofftimeVO.getChuliren());
					} else if (alarmsummaryCutofftimeVO.getRemark() == 0 && alarmsummaryCutofftimeVO.getShensushenhebiaoshi() == 1) {
						baobiaoAlarmsummaryRecord.setSecondOfTime(alarmsummaryCutofftimeVO.getShensushenheshijian());
						baobiaoAlarmsummaryRecord.setSecondOfName(alarmsummaryCutofftimeVO.getShensushenheren());
						xiaji.setSecondOfRemark(alarmsummaryCutofftimeVO.getShensushenheyijian());
					} else if (alarmsummaryCutofftimeVO.getRemark() == 0 && alarmsummaryCutofftimeVO.getShensushenhebiaoshi() == 2) {
						baobiaoAlarmsummaryRecord.setSecondOfTime(alarmsummaryCutofftimeVO.getShensushenheshijian());
						baobiaoAlarmsummaryRecord.setSecondOfName(alarmsummaryCutofftimeVO.getShensushenheren());
						xiaji.setSecondOfRemark(alarmsummaryCutofftimeVO.getShensushenheyijian());
					} else if (StringUtils.isNotBlank(alarmsummaryCutofftimeVO.getTwicechulishijian())) {
						baobiaoAlarmsummaryRecord.setTwicechulishijian(alarmsummaryCutofftimeVO.getTwicechulishijian());
						baobiaoAlarmsummaryRecord.setTwicechuliren(alarmsummaryCutofftimeVO.getTwicechuliren());
						xiaji.setTwicechulishiRemark(alarmsummaryCutofftimeVO.getTwicechulixingshi());
					} else {
						if (alarmsummaryCutofftimeVO.getChulishijian() != null) {
							baobiaoAlarmsummaryRecord.setChuJingTime(df.format(alarmsummaryCutofftimeVO.getChulishijian()));
							baobiaoAlarmsummaryRecord.setChuJingName(alarmsummaryCutofftimeVO.getChuliren());
							xiaji.setChuJingRemark(alarmsummaryCutofftimeVO.getChulixingshi());
							xiaji.setChuJingTime(df.format(alarmsummaryCutofftimeVO.getChulishijian()));
						}

//					baobiaoAlarmsummaryRecord.setFinalProcessingTime(alarmsummaryCutofftimeVO.getTwicechulishijian());
//					baobiaoAlarmsummaryRecord.setFinalProcessingName(alarmsummaryCutofftimeVO.getTwicechuliren());
					}
				} else {
					if(alarmsummaryCutofftimeVO.getRemark() != null){
						if (alarmsummaryCutofftimeVO.getRemark() == 0 && alarmsummaryCutofftimeVO.getShensushenhebiaoshi() == 0) {
							if (alarmsummaryCutofftimeVO.getChulishijian() != null) {
								baobiaoAlarmsummaryRecord.setAppealOfTime(df.format(alarmsummaryCutofftimeVO.getChulishijian()));
							}
							baobiaoAlarmsummaryRecord.setAppealOfName(alarmsummaryCutofftimeVO.getChuliren());
						} else if (alarmsummaryCutofftimeVO.getRemark() == 0 && alarmsummaryCutofftimeVO.getShensushenhebiaoshi() == 1) {
							baobiaoAlarmsummaryRecord.setSecondOfTime(alarmsummaryCutofftimeVO.getShensushenheshijian());
							baobiaoAlarmsummaryRecord.setSecondOfName(alarmsummaryCutofftimeVO.getShensushenheren());

						} else if (alarmsummaryCutofftimeVO.getRemark() == 0 && alarmsummaryCutofftimeVO.getShensushenhebiaoshi() == 2) {
							baobiaoAlarmsummaryRecord.setSecondOfTime(alarmsummaryCutofftimeVO.getShensushenheshijian());
							baobiaoAlarmsummaryRecord.setSecondOfName(alarmsummaryCutofftimeVO.getShensushenheren());
							xiaji.setSecondOfRemark(alarmsummaryCutofftimeVO.getShensushenheyijian());
						} else if (StringUtils.isNotBlank(alarmsummaryCutofftimeVO.getTwicechulishijian())) {
							baobiaoAlarmsummaryRecord.setTwicechulishijian(alarmsummaryCutofftimeVO.getTwicechulishijian());
							baobiaoAlarmsummaryRecord.setTwicechuliren(alarmsummaryCutofftimeVO.getTwicechuliren());
							xiaji.setTwicechulishiRemark(alarmsummaryCutofftimeVO.getTwicechulixingshi());
						} else {
							if (alarmsummaryCutofftimeVO.getChulishijian() != null) {
								baobiaoAlarmsummaryRecord.setChuJingTime(df.format(alarmsummaryCutofftimeVO.getChulishijian()));
								baobiaoAlarmsummaryRecord.setChuJingName(alarmsummaryCutofftimeVO.getChuliren());
								xiaji.setChuJingRemark(alarmsummaryCutofftimeVO.getChulixingshi());
								xiaji.setChuJingTime(df.format(alarmsummaryCutofftimeVO.getChulishijian()));
							}
						}
					}
				}
			}
		}else{
			//主动安全报警详情查询
			vo = driverbehaviorMapper.selectAlarmDetail(id,AlarmType);
			if (vo == null) {
				return null;
			}
			//根据报警ID查询报警生命周期记录
			List<BaobiaoAlarmsummaryRecord> ninety_nine_List = cutofftimeMapper.selectAlarmsummaryRecord(99,vo.getAlarmNumber());
			List<BaobiaoAlarmsummaryRecord> zero_List = cutofftimeMapper.selectAlarmsummaryRecord(0,vo.getAlarmNumber());
			List<MonitorContral> monitorContralList = cutofftimeMapper.selectMonitorContralById(vo.getAlarmNumber());
			if(ninety_nine_List.size() > 0){
				ninety_nine_List.forEach(item->{
					//预警
					if(item.getLevel() == 99){
						baobiaoAlarmsummaryRecord.setWarningTime(item.getBeginTime());
						xiaji.setWarningRemark("平台AI智能下发TTS消息提醒");
					}
				});
			}
			if(zero_List.size() > 0){
				zero_List.forEach(item->{
					//一级报警
					if(item.getLevel() == 1){
						baobiaoAlarmsummaryRecord.setFirstAlarmTime(item.getBeginTime());
					}
					//二级报警
					if(item.getLevel() == 2){
						baobiaoAlarmsummaryRecord.setSecondAlarmTime(item.getBeginTime());
					}
					//三级报警
					if(item.getLevel() == 3){
						baobiaoAlarmsummaryRecord.setThreeLevelAlarmTime(item.getBeginTime());
					}
					//四级报警
					if(item.getLevel() == 4){
						baobiaoAlarmsummaryRecord.setFourLevelAlarmTime(item.getBeginTime());
					}
					//五级报警
					if(item.getLevel() == 5){
						baobiaoAlarmsummaryRecord.setFirstAlarmTime(item.getBeginTime());
					}
				});
			}
			if(monitorContralList.size() > 0){
				monitorContralList.forEach(item->{
					//一级报警
					if(item.getAlarmLevel() == 1){
						EnmuAlarm.NuclearJieguo byValue = EnmuAlarm.NuclearJieguo.getByValue(item.getContralType());
						xiaji.setFirstAlarmRemark(byValue.desc);
						xiaji.setFirstAlarmRemarkTime(item.getAlarmTime());
					}
					//二级报警
					if(item.getAlarmLevel() == 2){
						EnmuAlarm.NuclearJieguo byValue = EnmuAlarm.NuclearJieguo.getByValue(item.getContralType());
						xiaji.setSecondAlarmRemark(byValue.desc);
						xiaji.setSecondAlarmRemarkTime(item.getAlarmTime());
					}
					//三级报警
					if(item.getAlarmLevel() == 3){
						EnmuAlarm.NuclearJieguo byValue = EnmuAlarm.NuclearJieguo.getByValue(item.getContralType());
						xiaji.setThreeLevelAlarmRemark(byValue.desc);
						xiaji.setThreeLevelAlarmRemarkTime(item.getAlarmTime());
					}
					//四级报警
					if(item.getAlarmLevel() == 4){
						EnmuAlarm.NuclearJieguo byValue = EnmuAlarm.NuclearJieguo.getByValue(item.getContralType());
						xiaji.setFourLevelAlarmRemark(byValue.desc);
						xiaji.setFourLevelAlarmRemarkTime(item.getAlarmTime());
					}
					//五级报警
					if(item.getAlarmLevel() == 5){
						EnmuAlarm.NuclearJieguo byValue = EnmuAlarm.NuclearJieguo.getByValue(item.getContralType());
						xiaji.setFirstAlarmRemark(byValue.desc);
						xiaji.setFirstAlarmRemarkTime(item.getAlarmTime());
					}
				});
			}else{
				List<TtsMessageInfo> ttsMessageInfoList = cutofftimeMapper.selectByAlarmGuId(vo.getAlarmNumber());
				if(ttsMessageInfoList.size() > 0){
					xiaji.setFirstAlarmRemark(ttsMessageInfoList.get(0).getSendType()+ttsMessageInfoList.get(0).getMessageType());
					xiaji.setFirstAlarmRemarkTime(ttsMessageInfoList.get(0).getTime());
				}
			}
			if(vo != null) {
				baobiaoAlarmsummaryRecord.setVerifyTime(df.format(vo.getInsertTime()));
				xiaji.setVerifyTime(df.format(vo.getInsertTime()));
				xiaji.setVerifyRemark("监控员核实后，推报给企业");
				if ("1".equals(vo.getEndresult())) {
					if (vo.getRemark() == 0 && vo.getShensushenhebiaoshi() == 0) {
						if (vo.getChulishijian() != null) {
							baobiaoAlarmsummaryRecord.setAppealOfTime(df.format(vo.getChulishijian()));
						}
						baobiaoAlarmsummaryRecord.setAppealOfName(vo.getChuliren());
					} else if (vo.getRemark() == 0 && vo.getShensushenhebiaoshi() == 1) {
						baobiaoAlarmsummaryRecord.setSecondOfTime(vo.getShensushenheshijian());
						baobiaoAlarmsummaryRecord.setSecondOfName(vo.getShensushenheren());
						xiaji.setSecondOfRemark(vo.getShensushenheyijian());
					} else if (vo.getRemark() == 0 && vo.getShensushenhebiaoshi() == 2) {
						baobiaoAlarmsummaryRecord.setSecondOfTime(vo.getShensushenheshijian());
						baobiaoAlarmsummaryRecord.setSecondOfName(vo.getShensushenheren());
						xiaji.setSecondOfRemark(vo.getShensushenheyijian());
					} else if (StringUtils.isNotBlank(vo.getTwicechulishijian())) {
						baobiaoAlarmsummaryRecord.setTwicechulishijian(vo.getTwicechulishijian());
						baobiaoAlarmsummaryRecord.setTwicechuliren(vo.getTwicechuliren());
						xiaji.setTwicechulishiRemark(vo.getTwicechulixingshi());
					} else {
						if (vo.getChulishijian() != null) {
							baobiaoAlarmsummaryRecord.setChuJingTime(df.format(vo.getChulishijian()));
							baobiaoAlarmsummaryRecord.setChuJingName(vo.getChuliren());
							xiaji.setChuJingRemark(vo.getChulixingshi());
							xiaji.setChuJingTime(df.format(vo.getChulishijian()));
						}
					}
				} else {
					if(vo.getRemark() != null){
						if (vo.getRemark() == 0 && vo.getShensushenhebiaoshi() == 0) {
							if (vo.getChulishijian() != null) {
								baobiaoAlarmsummaryRecord.setAppealOfTime(df.format(vo.getChulishijian()));
							}
							baobiaoAlarmsummaryRecord.setAppealOfName(vo.getChuliren());
						} else if (vo.getRemark() == 0 && vo.getShensushenhebiaoshi() == 1) {
							baobiaoAlarmsummaryRecord.setSecondOfTime(vo.getShensushenheshijian());
							baobiaoAlarmsummaryRecord.setSecondOfName(vo.getShensushenheren());

						} else if (vo.getRemark() == 0 && vo.getShensushenhebiaoshi() == 2) {
							baobiaoAlarmsummaryRecord.setSecondOfTime(vo.getShensushenheshijian());
							baobiaoAlarmsummaryRecord.setSecondOfName(vo.getShensushenheren());
							xiaji.setSecondOfRemark(vo.getShensushenheyijian());
						} else if (StringUtils.isNotBlank(vo.getTwicechulishijian())) {
							baobiaoAlarmsummaryRecord.setTwicechulishijian(vo.getTwicechulishijian());
							baobiaoAlarmsummaryRecord.setTwicechuliren(vo.getTwicechuliren());
							xiaji.setTwicechulishiRemark(vo.getTwicechulixingshi());
						} else {
							if (vo.getChulishijian() != null) {
								baobiaoAlarmsummaryRecord.setChuJingTime(df.format(vo.getChulishijian()));
								baobiaoAlarmsummaryRecord.setChuJingName(vo.getChuliren());
								xiaji.setChuJingRemark(vo.getChulixingshi());
								xiaji.setChuJingTime(df.format(vo.getChulishijian()));
							}
						}
					}
				}
			}
		}
		baobiaoAlarmsummaryRecord.setXiajiList(xiaji);
    	return baobiaoAlarmsummaryRecord;
    }

    @Override
	public Map<String, Object> selectShifouBaojing(ShishiBaojingTongjiPage shishiBaojingTongjiPage) {
		if(shishiBaojingTongjiPage.getBeginTime() == null){
			//设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			shishiBaojingTongjiPage.setBeginTime(df.format(new Date()));
		}
		if(shishiBaojingTongjiPage.getEndTime() == null){
			//设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			shishiBaojingTongjiPage.setEndTime(df.format(new Date()));
		}
		List<String> strings = cutofftimeMapper.selectShifouBaojing(shishiBaojingTongjiPage);
		Map<String,Object> gpsMap = new HashMap<String,Object>();
		gpsMap.put("chaosu",0);
		gpsMap.put("pilao",0);
		gpsMap.put("yejian",0);
		gpsMap.put("yichang",0);
		gpsMap.put("gaosujinxing",0);
		gpsMap.put("buzaixian",0);
		strings.forEach(item->{
			if("超速报警".equals(item)){
				gpsMap.put("chaosu",1);
			}else if("疲劳驾驶报警".equals(item)){
				gpsMap.put("pilao",1);
			}else if("夜间行驶报警".equals(item)){
				gpsMap.put("yejian",1);
			}else if("高速禁行".equals(item)){
				gpsMap.put("gaosujinxing",1);
			}else if("24小时不在线报警".equals(item)){
				gpsMap.put("buzaixian",1);
			}else if("不定位报警".equals(item) || "无数据报警".equals(item)){
				gpsMap.put("yichang",1);
			}
		});
		return gpsMap;
	}

    @Override
    public AlarmPage selectAlarmMGPage(AlarmPage alarmPage) {
        List<String> companys = cutofftimeMapper.getCompany(alarmPage.getDeptId());
        //子级企业集合是否包含单个企业名称
        Boolean flg = null;
        if(StringUtil.isNotEmpty(alarmPage.getJigouName())){
            flg = companys.contains(alarmPage.getJigouName());
        }
        //报警类型过滤
        Criteria lte = Criteria.where("AlarmType").is(alarmPage.getAlarmType());
        /*
         * 1.如果不存在单个企业条件，则查询所有子级企业
         * 2.企业过滤，如果存在单个企业的条件并且子级企业包含该单个企业，那就以该单个企业的名称检索
         * 3.如果存在单个企业条件，并且该企业不存在于子级企业集合，那么直接返回null，避免查询消耗
         */
        if(flg==null && companys!=null && companys.size()!=0){
            lte.and("company").in(companys);
        }else if(flg!=null && flg){
            lte.and("company").is(alarmPage.getJigouName());
        }else if(flg!=null && !flg){
            return alarmPage;
        }
        //报警时间过滤
        if(StringUtil.isNotEmpty(alarmPage.getBeginTime()) && StringUtil.isNotEmpty(alarmPage.getEndTime())){
            long bgtime = DateUtil.parse(alarmPage.getBeginTime(), "yyyy-MM-dd").getTime();
            long edtime = DateUtil.parse(alarmPage.getEndTime(), "yyyy-MM-dd").getTime()+86400000;
            lte.andOperator( Criteria.where("CutoffTime").gte(bgtime) ,Criteria.where("CutoffTime").lt(edtime));
        }

        Query q = new Query(lte);
        int total = new Long(mongoTemplate.count(q, AlarmsummaryCutofftimeMG.class)).intValue();
        alarmPage.setTotal(total);
        //如果总条数为0，直接返回，避免不必要查询
        if(total<=0){
            return alarmPage;
        }
        //排序条件
        if(StringUtil.isNotEmpty(alarmPage.getOrderColumn())){
            if(alarmPage.getOrder()!=0){
                q.with(Sort.by(Sort.Order.desc(alarmPage.getOrderColumn())));
            }else{
                q.with(Sort.by(Sort.Order.asc(alarmPage.getOrderColumn())));
            }
        }else{
            //默认车辆牌照降序
            q.with(Sort.by(Sort.Order.desc("plateNumber")));
        }
        List<AlarmsummaryCutofftimeMG> a = null;
        //如果pagesize=0则为导出，否则为分页查询
        if(alarmPage.getSize()==0) {
            if (alarmPage.getTotal() >20000) {
                throw new RuntimeException("导出总条数数不能超过20000");
            }else{
                a = mongoTemplate.find(q,AlarmsummaryCutofftimeMG.class);
                for (int i = 0; i < a.size(); i++) {
                    a.get(i).setOrderNum(i+1);
                }
            }
        }else{
            //总页数
            int pagetotal =  total / alarmPage.getSize() + 1;
            //跳过数量
            int offsetNo = 0;
            if (pagetotal >= alarmPage.getCurrent() && pagetotal > 0) {
                alarmPage.setPageTotal(pagetotal);
                if (alarmPage.getCurrent() > 1) {
                    offsetNo = alarmPage.getSize() * (alarmPage.getCurrent() - 1);
                }
                alarmPage.setOffsetNo(offsetNo);
            }
            a = mongoTemplate.find(q.skip(alarmPage.getOffsetNo()).limit(alarmPage.getSize()),AlarmsummaryCutofftimeMG.class);
            for (int i = 0; i < a.size(); i++) {
                a.get(i).setOrderNum(offsetNo+i+1);
            }
        }
        alarmPage.setRecords(a);
        return alarmPage;
    }

	@Override
	public List<AlarmsummaryCutofftime> timeAlarm(AlarmTimePage alarmTimePage) {
		return cutofftimeMapper.timeAlarm(alarmTimePage);
	}

	@Override
	public List<String> findoperattype(String deptId) {
		List<String> findoperattype = cutofftimeMapper.findoperattype(deptId);
		   findoperattype.add("全部");
		return findoperattype;
	}

	@Override
	public List<AlarmsummaryCutofftimeVO> selectAlarmLv(String beginTime, String endTime, String deptName) {
		return cutofftimeMapper.selectAlarmLv(beginTime, endTime, deptName);
	}

	@Override
	public boolean updateBaoBiaoMuLu(String processRate, String deptId, String property, String countdate,String countenddate) {
		return cutofftimeMapper.updateBaoBiaoMuLu(processRate, deptId, property, countdate,countenddate);
	}

	@Override
	public AlarmPage<AlarmsummaryCutofftimeVO> selectStairAlarmPage(AlarmPage alarmPage) {
		Integer total = cutofftimeMapper.selectStairAlarmTotal(alarmPage);
		if(total==0){
			return alarmPage;
		}
		//导出
		if(alarmPage.getSize()==0){
			if(alarmPage.getTotal()==0){
				alarmPage.setTotal(total);
			}
			List<AlarmsummaryCutofftimeVO> list = cutofftimeMapper.selectStairAlarmPage(alarmPage);
			list.forEach(item->{
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
			alarmPage.setRecords(list);
			return alarmPage;
		}
		//分页查询
		Integer pagetotal = 0;
		if (total > 0) {
			pagetotal = total / alarmPage.getSize() + 1;
		}
		if (pagetotal >= alarmPage.getCurrent() && pagetotal > 0) {
			alarmPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (alarmPage.getCurrent() > 1) {
				offsetNo = alarmPage.getSize() * (alarmPage.getCurrent() - 1);
			}
			alarmPage.setTotal(total);
			alarmPage.setOffsetNo(offsetNo);
			List<AlarmsummaryCutofftimeVO> rows = cutofftimeMapper.selectStairAlarmPage(alarmPage);
			rows.forEach(item->{
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
			alarmPage.setRecords(rows);
		}
		return alarmPage;
	}

	@Override
	public List<AlarmsummaryCutofftimeVO> alarmDay(String company, String AlarmType) {
		String date = DateUtil.format(new Date(), "yyyy-MM-dd");
		List<AlarmsummaryCutofftimeVO> list = cutofftimeMapper.alarmDay(company,AlarmType);
		list.forEach(item->{
			if(StringUtils.isBlank(item.getEndresult())){
				if(item.getRemark()==null){
					item.setShensuzhuangtai("未申诉");
					item.setChulizhuangtai("未处理");
					item.setChulimiaoshu("");
					item.setChulixingshi("");
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
					item.setShensuzhuangtai("未申诉");
					item.setChulizhuangtai("未处理");
					item.setChulimiaoshu("");
					item.setChulixingshi("");
				}else if(item.getEndresult().equals("1")){
					EnmuAlarm.ChuliJieguo byValue = EnmuAlarm.ChuliJieguo.getByValue(Integer.valueOf(1));
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
			if(!"24小时不在线报警".equals(item.getAlarmType()) && !"不定位报警".equals(item.getAlarmType())){
				if(StringUtils.isBlank(item.getRoadName())){
					double lat = Double.parseDouble(item.getLatitude().toString());
					double lon = Double.parseDouble(item.getLongitude().toString());
					double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
					item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
					item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
					String LocalName= LatLotForLocation.getProvince(item.getLatitude().toString(),item.getLongitude().toString(),alarmServer.getAddressAk());
					item.setRoadName(LocalName);
				}
			}
		});
		return list;
	}


}
