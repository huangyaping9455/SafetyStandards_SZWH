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
import org.springblade.alarm.entity.AlarmBaojingTongji;
import org.springblade.alarm.entity.Driverbehavior;
import org.springblade.alarm.entity.DriverbehaviorMG;
import org.springblade.alarm.mapper.AlarmsummaryCutofftimeMapper;
import org.springblade.alarm.mapper.DriverbehaviorMapper;
import org.springblade.alarm.page.AlarmTimePage;
import org.springblade.alarm.page.DriverAlarmPage;
import org.springblade.alarm.page.ShishiBaojingTongjiPage;
import org.springblade.alarm.service.IDriverbehaviorService;
import org.springblade.alarm.vo.DriverbehaviorVO;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 驾驶员行为报警 服务实现类
 *
 * @author hyp
 * @since 2019-05-12
 */
@Service
@AllArgsConstructor
public class DriverbehaviorServiceImpl extends ServiceImpl<DriverbehaviorMapper, Driverbehavior> implements IDriverbehaviorService {
    private DriverbehaviorMapper driverbehaviorMapper;
    private AlarmsummaryCutofftimeMapper alarmsummaryCutofftimeMapper;
	private MongoTemplate mongoTemplate;
	private AlarmServer alarmServer;

    @Override
    public DriverAlarmPage<DriverbehaviorVO> selectAlarmPage(DriverAlarmPage driverAlarmPage) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Integer total = driverbehaviorMapper.selectAlarmTotal(driverAlarmPage);
		if(driverAlarmPage.getSize()==0){
			if(driverAlarmPage.getTotal()==0){
				driverAlarmPage.setTotal(total);
			}
			List<DriverbehaviorVO> list=driverbehaviorMapper.selectAlarmPage(driverAlarmPage);
			list.forEach(item->{
				if(StringUtils.isBlank(item.getEndresult())){
					if(item.getRemark()==null){
						String days = DateUtils.differDays(df.format(item.getGpsTime()));
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
						String days = DateUtils.differDays(df.format(item.getGpsTime()));
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
			driverAlarmPage.setRecords(list);
			return driverAlarmPage;
		}
        Integer pagetotal = 0;
        if (total > 0) {
            pagetotal = total / driverAlarmPage.getSize() + 1;
        }
        if (pagetotal >= driverAlarmPage.getCurrent() && pagetotal > 0) {
            driverAlarmPage.setPageTotal(pagetotal);
            Integer offsetNo = 0;
            if (driverAlarmPage.getCurrent() > 1) {
                offsetNo = driverAlarmPage.getSize() * (driverAlarmPage.getCurrent() - 1);
            }
            driverAlarmPage.setTotal(total);
            driverAlarmPage.setOffsetNo(offsetNo);
            List<DriverbehaviorVO> list = driverbehaviorMapper.selectAlarmPage(driverAlarmPage);
			list.forEach(item->{
				if(StringUtils.isBlank(item.getEndresult())){
					if(item.getRemark()==null){
						String days = DateUtils.differDays(df.format(item.getGpsTime()));
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
						String days = DateUtils.differDays(df.format(item.getGpsTime()));
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
            driverAlarmPage.setRecords(list);
            //添加报警统计
            AlarmBaojingTongji baojingTongji = driverbehaviorMapper.selectBaojingtongji(driverAlarmPage);
            baojingTongji.setAlarmType(driverAlarmPage.getAlarmType());
            baojingTongji.setCountDate(driverAlarmPage.getBeginTime() + "-" + driverAlarmPage.getEndTime());
            int handlecount = baojingTongji.getHandledCount();
            int alarmcount = baojingTongji.getAlarmCount();
            String handlerate = "0%";
            if (handlecount > 0 && alarmcount > 0) {
                handlerate = (float)Math.round((float) handlecount / (float) alarmcount * 10000)/100 + "%";
            }
            baojingTongji.setHandledRate(handlerate);
            driverAlarmPage.setBaojingTongji(baojingTongji);
        }
        return driverAlarmPage;
    }

    @Override
    public Map<String, Object> selectShishiBaojingTongji(ShishiBaojingTongjiPage page) {
        return driverbehaviorMapper.selectShishiBaojingTongji(page);
    }

    @Override
    public DriverbehaviorVO selectAlarmDetail(String id,String AlarmType) {
        return driverbehaviorMapper.selectAlarmDetail(id,AlarmType);
    }

    @Override
    public Map<String, Object> selectShifouBaojing(ShishiBaojingTongjiPage shishiBaojingTongjiPage) {
        List<String> strings = driverbehaviorMapper.selectShifouBaojing(shishiBaojingTongjiPage);
        Map<String,Object> driverMap = new HashMap<String,Object>();
        driverMap.put("fenshen",0);
        driverMap.put("pilaoshipin",0);
        driverMap.put("chouyan",0);
        driverMap.put("dadianhua",0);
		driverMap.put("jiashiyuanyichang",0);
		driverMap.put("chujuguojin",0);
		driverMap.put("chedaopianli",0);
		driverMap.put("qianxiangpengzhuang",0);
		driverMap.put("xingrenpengzhuang",0);
//		if("安徽".equals(alarmServer.getAddressPath())){
//			strings.forEach(item->{
//				if("分神报警".equals(item)){
//					driverMap.put("fenshen",1);
//				}else if("生理疲劳报警".equals(item)){
//					driverMap.put("pilaoshipin",1);
//				}else if("抽烟报警".equals(item)){
//					driverMap.put("chouyan",1);
//				}else if("驾驶员异常报警".equals(item)){
//					driverMap.put("jiashiyuanyichang",1);
//				}else if("接打电话报警".equals(item)){
//					driverMap.put("dadianhua",1);
//				}else if("车距过近报警".equals(item)){
//					driverMap.put("chujuguojin",1);
//				}else if("车道偏离报警".equals(item)){
//					driverMap.put("chedaopianli",1);
//				}else if("前向碰撞报警".equals(item)){
//					driverMap.put("qianxiangpengzhuang",1);
//				}else if("行人碰撞预警".equals(item)){
//					driverMap.put("xingrenpengzhuang",1);
//				}
//			});
//		}else{
//			strings.forEach(item->{
//				if("分神驾驶报警".equals(item)){
//					driverMap.put("fenshen",1);
//				}else if("生理疲劳报警".equals(item)){
//					driverMap.put("pilaoshipin",1);
//				}else if("抽烟报警".equals(item)){
//					driverMap.put("chouyan",1);
//				}else if("驾驶员异常报警".equals(item)){
//					driverMap.put("jiashiyuanyichang",1);
//				}else if("接打电话报警".equals(item)){
//					driverMap.put("dadianhua",1);
//				}else if("车距过近报警".equals(item)){
//					driverMap.put("chujuguojin",1);
//				}else if("车道偏离报警".equals(item)){
//					driverMap.put("chedaopianli",1);
//				}else if("前向碰撞报警".equals(item)){
//					driverMap.put("qianxiangpengzhuang",1);
//				}else if("行人碰撞预警".equals(item)){
//					driverMap.put("xingrenpengzhuang",1);
//				}
//			});
//		}
		strings.forEach(item->{
			if("分神驾驶报警".equals(item)){
				driverMap.put("fenshen",1);
			}else if("生理疲劳报警".equals(item)){
				driverMap.put("pilaoshipin",1);
			}else if("抽烟报警".equals(item)){
				driverMap.put("chouyan",1);
			}else if("驾驶员异常报警".equals(item)){
				driverMap.put("jiashiyuanyichang",1);
			}else if("接打电话报警".equals(item)){
				driverMap.put("dadianhua",1);
			}else if("车距过近报警".equals(item)){
				driverMap.put("chujuguojin",1);
			}else if("车道偏离报警".equals(item)){
				driverMap.put("chedaopianli",1);
			}else if("前向碰撞报警".equals(item)){
				driverMap.put("qianxiangpengzhuang",1);
			}else if("行人碰撞预警".equals(item)){
				driverMap.put("xingrenpengzhuang",1);
			}
		});
        return driverMap;
    }

	@Override
	public List<DriverbehaviorVO> zhudongDay(String company, String AlarmType) {
		String date = DateUtil.format(new Date(), "yyyy-MM-dd");
		List<DriverbehaviorVO> zhudongDays = driverbehaviorMapper.zhudongDay(company, AlarmType, date);
		zhudongDays.forEach(item->{
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
		return zhudongDays;
	}

	@Override
	public DriverAlarmPage selectdriverbehaviorPage(DriverAlarmPage driverAlarmPage) {
		List<String> companys = alarmsummaryCutofftimeMapper.getCompany(driverAlarmPage.getDeptId());
		//子级企业集合是否包含单个企业名称
		Boolean flg = null;
		if(StringUtil.isNotEmpty(driverAlarmPage.getJigouName())){
			flg = companys.contains(driverAlarmPage.getJigouName());
		}
		//报警类型过滤
		Criteria lte = Criteria.where("AlarmType").is(driverAlarmPage.getAlarmType());
		// 增加赛选条件  主动防御核定报警才推送
		lte.and("StateEx").is("核定报警");
		/*
		 * 1.如果不存在单个企业条件，则查询所有子级企业
		 * 2.企业过滤，如果存在单个企业的条件并且子级企业包含该单个企业，那就以该单个企业的名称检索
		 * 3.如果存在单个企业条件，并且该企业不存在于子级企业集合，那么直接返回null，避免查询消耗
		 */
		if(flg==null && companys!=null && companys.size()!=0){
			lte.and("company").in(companys);
		}else if(flg!=null && flg){
			lte.and("company").is(driverAlarmPage.getJigouName());
		}else if(flg!=null && !flg){
			return driverAlarmPage;
		}
		//报警时间过滤
		if(StringUtil.isNotEmpty(driverAlarmPage.getBeginTime()) && StringUtil.isNotEmpty(driverAlarmPage.getEndTime())){
			long bgtime = DateUtil.parse(driverAlarmPage.getBeginTime(), "yyyy-MM-dd").getTime();
			long edtime = DateUtil.parse(driverAlarmPage.getEndTime(), "yyyy-MM-dd").getTime()+86400000;
			lte.andOperator( Criteria.where("GpsTime").gte(bgtime) ,Criteria.where("GpsTime").lt(edtime));
		}
		Query q = new Query(lte);
		int total = new Long(mongoTemplate.count(q, DriverbehaviorMG.class)).intValue();
		driverAlarmPage.setTotal(total);
		//如果总条数为0，直接返回，避免不必要查询
		if(total<=0){
			return driverAlarmPage;
		}
		//排序条件
		if(StringUtil.isNotEmpty(driverAlarmPage.getOrderColumn())){
			if(driverAlarmPage.getOrder()!=0){
				q.with(Sort.by(Sort.Order.desc(driverAlarmPage.getOrderColumn())));
			}else{
				q.with(Sort.by(Sort.Order.asc(driverAlarmPage.getOrderColumn())));
			}
		}else{
			//默认车辆牌照降序
			q.with(Sort.by(Sort.Order.desc("plate")));
		}
		List<DriverbehaviorMG>  a=null;
		//如果pagesize=0则为导出，否则为分页查询
		if(driverAlarmPage.getSize()==0) {
			if (driverAlarmPage.getTotal() >20000) {
				throw new RuntimeException("导出总条数数不能超过20000");
			}else{
				a = mongoTemplate.find(q, DriverbehaviorMG.class);
				for (int i = 0; i < a.size(); i++) {
					a.get(i).setOrderNum(i+1);
				}
			}
		}else{
			//总页数
			int pagetotal =  total / driverAlarmPage.getSize() + 1;
			//跳过数量
			int offsetNo = 0;
			if (pagetotal >= driverAlarmPage.getCurrent() && pagetotal > 0) {
				driverAlarmPage.setPageTotal(pagetotal);
				if (driverAlarmPage.getCurrent() > 1) {
					offsetNo = driverAlarmPage.getSize() * (driverAlarmPage.getCurrent() - 1);
				}
				driverAlarmPage.setOffsetNo(offsetNo);
			}
			a = mongoTemplate.find(q.skip(driverAlarmPage.getOffsetNo()).limit(driverAlarmPage.getSize()),DriverbehaviorMG.class);
			for (int i = 0; i < a.size(); i++) {
				a.get(i).setOrderNum(offsetNo+i+1);
			}
		}
		driverAlarmPage.setRecords(a);
		return driverAlarmPage;
	}

	@Override
	public List<Driverbehavior> timeZhudong(AlarmTimePage alarmTimePage) {
		return driverbehaviorMapper.timeZhudong(alarmTimePage);
	}


}
