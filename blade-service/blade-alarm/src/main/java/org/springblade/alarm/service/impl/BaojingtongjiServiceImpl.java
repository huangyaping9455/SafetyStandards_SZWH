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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.alarm.entity.*;
import org.springblade.alarm.mapper.BaojingtongjiMapper;
import org.springblade.alarm.page.AlarmPage;
import org.springblade.alarm.page.BaojingTJPage;
import org.springblade.alarm.service.IBaojingtongjiService;
import org.springblade.alarm.vo.BaojingtongjiVO;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.enumconstant.EnmuAlarm;
import org.springblade.common.tool.DateUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-07-25
 */
@Service
@AllArgsConstructor
public class BaojingtongjiServiceImpl extends ServiceImpl<BaojingtongjiMapper, BaojingTJ> implements IBaojingtongjiService {
	private BaojingtongjiMapper baojingtongjiMapper;
	private AlarmServer alarmServer;

	@Override
	public IPage<BaojingtongjiVO> selectBaojingtongjiPage(IPage<BaojingtongjiVO> page, BaojingtongjiVO baojingtongji) {
		return page.setRecords(baseMapper.selectBaojingtongjiPage(page, baojingtongji));
	}

	@Override
	public int findtoto(BaojingTJPage baojingTJPage) {
		return baojingtongjiMapper.selectAlarmTotal(baojingTJPage);
	}

	@Override
	public BaojingTJPage selectAll(BaojingTJPage baojingTJPage) {
		Integer total =baojingtongjiMapper.selectAlarmTotal(baojingTJPage);
		if(baojingTJPage.getSize()==0){
			if(baojingTJPage.getTotal()==0){
				baojingTJPage.setTotal(total);
			}
			List<BaojingTJ> list=baojingtongjiMapper.TongjiPage(baojingTJPage);
			int  alarmcount=0;
			for(BaojingTJ a:list){
				alarmcount+=a.getChaosucisu();
			}
			baojingTJPage.setAlarmVehicle(list.size());
			baojingTJPage.setAlarmCount(alarmcount);
			baojingTJPage.setRecords(list);
			return  baojingTJPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			pagetotal = total / baojingTJPage.getSize() + 1;
		}
		if (pagetotal >= baojingTJPage.getCurrent() && pagetotal > 0) {
			baojingTJPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (baojingTJPage.getCurrent() > 1) {
				offsetNo = baojingTJPage.getSize() * (baojingTJPage.getCurrent() - 1);
			}
			baojingTJPage.setTotal(total);
			baojingTJPage.setOffsetNo(offsetNo);
			List<BaojingTJ> list=baojingtongjiMapper.TongjiPage(baojingTJPage);
			int  alarmcount=0;
			for(BaojingTJ a:list){
				 alarmcount+=a.getChaosucisu();
			}
			baojingTJPage.setAlarmVehicle(list.size());
			baojingTJPage.setAlarmCount(alarmcount);
			baojingTJPage.setRecords(list);
		}

		return baojingTJPage;
	}

	@Override
	public BaojingTJPage PilaoAll(BaojingTJPage baojingTJPage) {
		Integer total =baojingtongjiMapper.selectPilaoTotal(baojingTJPage);
		if(baojingTJPage.getSize()==0){
			if(baojingTJPage.getTotal()==0){
				baojingTJPage.setTotal(total);
			}
			List<PiLaoBaojingTJ> list=baojingtongjiMapper.pilaoTongjiPage(baojingTJPage);
			int count=0;
			for(PiLaoBaojingTJ a:list){
				count+=a.getPilaocisu();
			}
			baojingTJPage.setAlarmCount(count);
			baojingTJPage.setAlarmVehicle(list.size());
			baojingTJPage.setRecords(list);
			return  baojingTJPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			pagetotal = total / baojingTJPage.getSize() + 1;
		}
		if (pagetotal >= baojingTJPage.getCurrent() && pagetotal > 0) {
			baojingTJPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (baojingTJPage.getCurrent() > 1) {
				offsetNo = baojingTJPage.getSize() * (baojingTJPage.getCurrent() - 1);
			}

			baojingTJPage.setTotal(total);
			baojingTJPage.setOffsetNo(offsetNo);
			List<PiLaoBaojingTJ> list=baojingtongjiMapper.pilaoTongjiPage(baojingTJPage);
			int count=0;
			for(PiLaoBaojingTJ a:list){
				count+=a.getPilaocisu();
			}
			baojingTJPage.setAlarmCount(count);
			baojingTJPage.setAlarmVehicle(list.size());
			baojingTJPage.setRecords(list);
		}

		return baojingTJPage;
	}

	@Override
	public AlarmCountDay alarmCount(String company) {
		String date = DateUtil.format(new Date(), "yyyy-MM-dd");
		List<Map<String, Object>> gpsList = baojingtongjiMapper.alarmCount(company, date);

		List<Map<String, Object>> zhudongList = baojingtongjiMapper.zhudongCount(company, date);
		AlarmCountDay alarmCountDay=new AlarmCountDay();

		for (Map<String, Object> map : gpsList) {
			String baojingCount = map.get("baojingCount").toString();
		}
		gpsList.forEach(item->{
			String AlarmType=item.get("AlarmType").toString();//报警类型
			Integer Count=Integer.valueOf(item.get("baojingCount").toString());//报警次数

			if("超速报警".equals(AlarmType)){
				alarmCountDay.setGpsChaosuCount(Count);
			}else if("疲劳驾驶报警".equals(AlarmType)){
				alarmCountDay.setGpsPilaoCount(Count);
			}else if("夜间行驶报警".equals(AlarmType)){
				alarmCountDay.setGpsYejianCount(Count);
			}else  if("无数据报警".equals(AlarmType)){
				alarmCountDay.setGpsYichangCount(alarmCountDay.getGpsYichangCount()+Count);
			}else if("不定位报警".equals(AlarmType)){
				alarmCountDay.setGpsYichangCount(alarmCountDay.getGpsYichangCount()+Count);
			}else if("24小时不在线报警".equals(AlarmType)){
				alarmCountDay.setBuzaixianbaojing(Count);
			}else if ("高速禁行".equals(AlarmType)) {
				alarmCountDay.setGaosujinxing(Count);
			}
		});
		zhudongList.forEach(item->{
			String AlarmType=item.get("AlarmType").toString();//报警类型
			Integer Count=Integer.valueOf(item.get("baojingCount").toString());//报警次数
//			if("安徽".equals(alarmServer.getAddressPath())) {
//				if ("疲劳驾驶报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongJiashiyuanpilaoCount(Count);
//				} else if ("接打电话报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongJiedadianhuaCount(Count);
//				} else if ("抽烟报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongChouyanjiashiCount(Count);
//				} else if ("分神报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongFenshenjiashiCount(Count);
//				} else if ("车距过近报警 ".equals(AlarmType)) {
//					alarmCountDay.setZhudongChejuguojinCount(Count);
//				} else if ("车道偏离报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongChedaopianliCount(Count);
//				} else if ("前向碰撞报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongFangpenzhuangCount(Count);
//				} else if ("驾驶员异常报警".equals(AlarmType)) {
//					alarmCountDay.setJiashiyuanyichangbaojing(Count);
//				} else if ("行人碰撞预警".equals(AlarmType)) {
//					alarmCountDay.setXingrenpengzhuangyujing(Count);
//				}
//			}else{
//				if ("疲劳驾驶报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongJiashiyuanpilaoCount(Count);
//				} else if ("接打电话报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongJiedadianhuaCount(Count);
//				} else if ("抽烟报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongChouyanjiashiCount(Count);
//				} else if ("分神驾驶报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongFenshenjiashiCount(Count);
//				} else if ("车距过近报警 ".equals(AlarmType)) {
//					alarmCountDay.setZhudongChejuguojinCount(Count);
//				} else if ("车道偏离报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongChedaopianliCount(Count);
//				} else if ("前向碰撞报警".equals(AlarmType)) {
//					alarmCountDay.setZhudongFangpenzhuangCount(Count);
//				} else if ("驾驶员异常报警".equals(AlarmType)) {
//					alarmCountDay.setJiashiyuanyichangbaojing(Count);
//				} else if ("行人碰撞预警".equals(AlarmType)) {
//					alarmCountDay.setXingrenpengzhuangyujing(Count);
//				}
//			}
			if ("疲劳驾驶报警".equals(AlarmType)) {
				alarmCountDay.setZhudongJiashiyuanpilaoCount(Count);
			} else if ("接打电话报警".equals(AlarmType)) {
				alarmCountDay.setZhudongJiedadianhuaCount(Count);
			} else if ("抽烟报警".equals(AlarmType)) {
				alarmCountDay.setZhudongChouyanjiashiCount(Count);
			} else if ("分神驾驶报警".equals(AlarmType)) {
				alarmCountDay.setZhudongFenshenjiashiCount(Count);
			} else if ("车距过近报警 ".equals(AlarmType)) {
				alarmCountDay.setZhudongChejuguojinCount(Count);
			} else if ("车道偏离报警".equals(AlarmType)) {
				alarmCountDay.setZhudongChedaopianliCount(Count);
			} else if ("前向碰撞报警".equals(AlarmType)) {
				alarmCountDay.setZhudongFangpenzhuangCount(Count);
			} else if ("驾驶员异常报警".equals(AlarmType)) {
				alarmCountDay.setJiashiyuanyichangbaojing(Count);
			} else if ("行人碰撞预警".equals(AlarmType)) {
				alarmCountDay.setXingrenpengzhuangyujing(Count);
			}
		});
		return alarmCountDay;
	}

	@Override
	public BaojingTJPage selectbudingwei(BaojingTJPage baojingTJPage) {
		Integer total =baojingtongjiMapper.selectbudinweiTotal(baojingTJPage);
		if(baojingTJPage.getSize()==0){
			if(baojingTJPage.getTotal()==0){
				baojingTJPage.setTotal(total);
			}
			List<BudingweiTongji> list=baojingtongjiMapper.budingwei(baojingTJPage);
			List<BudingweiCount> budingweicount = baojingtongjiMapper.budingweicount(baojingTJPage);
			baojingTJPage.setAlarmVehicle(budingweicount.size());
			int index=0;
			for(BudingweiCount a:budingweicount){
				index+=a.getNumber();
			}
			baojingTJPage.setAlarmVehicle(budingweicount.size());
			baojingTJPage.setAlarmCount(index);
			baojingTJPage.setRecords(list);
			return  baojingTJPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			pagetotal = total / baojingTJPage.getSize() + 1;
		}
		if (pagetotal >= baojingTJPage.getCurrent() && pagetotal > 0) {
			baojingTJPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (baojingTJPage.getCurrent() > 1) {
				offsetNo = baojingTJPage.getSize() * (baojingTJPage.getCurrent() - 1);
			}
			baojingTJPage.setTotal(total);
			baojingTJPage.setOffsetNo(offsetNo);
			List<BudingweiTongji> list=baojingtongjiMapper.budingwei(baojingTJPage);
			List<BudingweiCount> budingweicount = baojingtongjiMapper.budingweicount(baojingTJPage);
			baojingTJPage.setAlarmVehicle(budingweicount.size());
			int index=0;
			for(BudingweiCount a:budingweicount){
				index+=a.getNumber();
			}
			baojingTJPage.setAlarmCount(index);
			baojingTJPage.setRecords(list);
		}

		return baojingTJPage;


	}

	@Override
	public BaojingTJPage selectbuzaixian(BaojingTJPage baojingTJPage) {

		Integer total =baojingtongjiMapper.selectbuzaixianTotal(baojingTJPage);
		if(baojingTJPage.getSize()==0){
			if(baojingTJPage.getTotal()==0){
				baojingTJPage.setTotal(total);
			}
			List<BudingweiTongji> list=baojingtongjiMapper.buzaixian(baojingTJPage);
			List<BudingweiCount> budingweicount = baojingtongjiMapper.buzaixiancount(baojingTJPage);
			baojingTJPage.setAlarmVehicle(budingweicount.size());
			int index=0;
			for(BudingweiCount a:budingweicount){
				index+=a.getNumber();
			}
			baojingTJPage.setAlarmVehicle(budingweicount.size());
			baojingTJPage.setAlarmCount(index);
			baojingTJPage.setRecords(list);
			return  baojingTJPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			pagetotal = total / baojingTJPage.getSize() + 1;
		}
		if (pagetotal >= baojingTJPage.getCurrent() && pagetotal > 0) {
			baojingTJPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (baojingTJPage.getCurrent() > 1) {
				offsetNo = baojingTJPage.getSize() * (baojingTJPage.getCurrent() - 1);
			}
			baojingTJPage.setTotal(total);
			baojingTJPage.setOffsetNo(offsetNo);
			List<BudingweiTongji> list=baojingtongjiMapper.buzaixian(baojingTJPage);
			List<BudingweiCount> budingweicount = baojingtongjiMapper.buzaixiancount(baojingTJPage);
			baojingTJPage.setAlarmVehicle(budingweicount.size());
			int index=0;
			for(BudingweiCount a:budingweicount){
				index+=a.getNumber();
			}
			baojingTJPage.setAlarmCount(index);
			baojingTJPage.setRecords(list);
		}

		return baojingTJPage;
	}

	@Override
	public AlarmPage<BaojingTJMX> selectAlarmTJMXPage(AlarmPage alarmPage) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Integer total = baojingtongjiMapper.selectAlarmTJMXTotal(alarmPage);
		if(alarmPage.getSize()==0){
			if(alarmPage.getTotal()==0){
				alarmPage.setTotal(total);
			}
			if(alarmPage.getTotal()==0){
				return alarmPage;
			}else{
				List<BaojingTJMX> baojingTJMXList = baojingtongjiMapper.selectAlarmTJMXPage(alarmPage);
				baojingTJMXList.forEach(item->{
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
				});
				alarmPage.setRecords(baojingTJMXList);
				return alarmPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%alarmPage.getSize()==0){
				pagetotal = total / alarmPage.getSize();
			}else {
				pagetotal = total / alarmPage.getSize() + 1;
			}
		}
		if (pagetotal >= alarmPage.getCurrent()) {
			alarmPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (alarmPage.getCurrent() > 1) {
				offsetNo = alarmPage.getSize() * (alarmPage.getCurrent() - 1);
			}
			alarmPage.setTotal(total);
			alarmPage.setOffsetNo(offsetNo);
			List<BaojingTJMX> baojingTJMXList = baojingtongjiMapper.selectAlarmTJMXPage(alarmPage);
			baojingTJMXList.forEach(item->{
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
			});
			alarmPage.setRecords(baojingTJMXList);
		}
		return alarmPage;
	}

}
