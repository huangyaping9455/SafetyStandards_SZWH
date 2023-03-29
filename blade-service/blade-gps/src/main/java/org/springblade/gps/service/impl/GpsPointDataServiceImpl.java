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
package org.springblade.gps.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springblade.common.tool.GpsGuJiUtil;
import org.springblade.common.tool.GpsToBaiduUtil;
import org.springblade.common.tool.LatLotForLocation;
import org.springblade.gps.entity.*;
import org.springblade.gps.mapper.GpsPointDataMapper;
import org.springblade.gps.page.VehiclePTPage;
import org.springblade.gps.service.IGpsPointDataService;
import org.springblade.gps2.mapper.Gps2PointDataMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * gps点位数据 服务实现类
 *
 * @author hyp
 * @since 2019-05-17
 */
@Service
@Primary
@AllArgsConstructor
@Slf4j
public class GpsPointDataServiceImpl extends ServiceImpl<GpsPointDataMapper, T> implements IGpsPointDataService {
    private GpsPointDataMapper gpsPointDataMapper;
    private Gps2PointDataMapper gps2PointDataMapper;

    @Override
    public List<VehilePTData> selectPointData(String beginTime, String endTime, String vehId) {
		List<VehilePTData> maps = gps2PointDataMapper.selectPointData(beginTime, endTime, vehId);
		if(maps==null || maps.size()==0){
            maps= gps2PointDataMapper.selectPointData(beginTime, endTime, vehId);
        }
		List<VehilePTData> mapss=new ArrayList<>();
        for(int i=0;i<maps.size();i++){
        	if(i+1==maps.size()){
        		break;
			}
			VehilePTData data = maps.get(i);
			VehilePTData data1 = maps.get(i + 1);
			if(GpsGuJiUtil.isloglat(data.getLongitude(),data.getLatitude(),
				data1.getLongitude(),data1.getLatitude(),data.getGpsTime(),data1.getGpsTime())){
				mapss.add(data);
			}
		}
		return mapss;
    }

	//	//去除重复数据的方法
//	private static ArrayList<VehilePTData> removeDuplicateUser(List<VehilePTData> users) {
//		Set<VehilePTData> set = new TreeSet<VehilePTData>(new Comparator<VehilePTData>() {
//			@Override
//			public int compare(VehilePTData o1, VehilePTData o2) {
//				//字符串,则按照asicc码升序排列
//				return o1.getGpsmileage().compareTo(o2.getGpsmileage());
//			}
//		});
//		set.addAll(users);
//		return new ArrayList<VehilePTData>(set);
//	}

	@Override
	public List<VehicleNode> tree(String company){
		return gpsPointDataMapper.getVehiclesByCom(company);
	}

	@Override
	public VehiclePTPage<TpvehData> selectTpvehdataAllPage(VehiclePTPage vehiclePTPage) {
		List<TpvehData> vehilePTData = null;
		System.out.println("请求开始"+new Date());
		log.info("请求开始"+new Date());
		Integer total = gpsPointDataMapper.selectTpvehdataAllTotal(vehiclePTPage);
		System.out.println("请求结束"+new Date());
		log.info("请求结束"+new Date());
		if(vehiclePTPage.getSize()==0){
			if(vehiclePTPage.getTotal()==0){
				vehiclePTPage.setTotal(total);
				vehiclePTPage.setSize(total);
			}
			System.out.println("请求开始"+new Date());
			vehilePTData = gpsPointDataMapper.selectTpvehdataAllPage(vehiclePTPage);
			System.out.println("请求结束"+new Date());
			log.info("请求结束"+new Date());
			try {
				vehilePTData.forEach(item->{
//					if(item.getLatitude() != 0.0 || item.getLongitude() != 0.0 || !"2000-01-01 00:00:00".equals(item.getLastLocateTime())){
					if(!"2000-01-01 00:00:00".equals(item.getLastLocateTime())){
						double[] zuobiao = GpsToBaiduUtil.wgs2bd(item.getLatitude(),item.getLongitude());
						item.setLatitude(zuobiao[0]);
						item.setLongitude(zuobiao[1]);
//						item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue());
//						item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue());
//						String LocalName= LatLotForLocation.getProvince(Double.toString(item.getLatitude()),Double.toString(item.getLongitude()));
//						item.setLocationName(LocalName);
					}
				});
			}catch (Exception e){
				System.out.println(e.getMessage());
			}
			vehiclePTPage.setList(vehilePTData);
			vehiclePTPage.setTotalss(total);
			vehiclePTPage.setPageTotal(vehiclePTPage.getPageTotal());
			vehiclePTPage.setTotal(vehiclePTPage.getPage());
			vehiclePTPage.setPageTotal(vehiclePTPage.getPagesize());
			return vehiclePTPage;
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%vehiclePTPage.getSize()==0){
				pagetotal = total / vehiclePTPage.getSize();
			}else {
				pagetotal = total / vehiclePTPage.getSize() + 1;
			}
//			System.out.println("请求开始"+new Date());
//			log.info("请求开始"+new Date());
//			vehilePTData = gpsPointDataMapper.selectTpvehdataAllPage(vehiclePTPage);
//			System.out.println("请求结束"+new Date());
//			log.info("请求结束"+new Date());
//			vehiclePTPage.setList(vehilePTData);
//			vehiclePTPage.setTotalss(total);
//			vehiclePTPage.setPageTotal(vehiclePTPage.getPageTotal());
//			vehiclePTPage.setTotal(vehiclePTPage.getPage());
//			vehiclePTPage.setPageTotal(vehiclePTPage.getPagesize());
		}
		if (pagetotal >= vehiclePTPage.getCurrent()) {
			vehiclePTPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (vehiclePTPage.getCurrent() > 1) {
				offsetNo = vehiclePTPage.getSize() * (vehiclePTPage.getCurrent() - 1);
			}
			vehiclePTPage.setTotal(total);
			vehiclePTPage.setOffsetNo(offsetNo);
			System.out.println("请求开始"+new Date());
			log.info("请求开始"+new Date());
			vehilePTData = gpsPointDataMapper.selectTpvehdataAllPage(vehiclePTPage);
			System.out.println("请求结束"+new Date());
			log.info("请求结束"+new Date());
		}
		System.out.println("转换地理位置开始"+new Date());
		log.info("转换地理位置开始"+new Date());
		try {
			vehilePTData.forEach(item->{
//					if(item.getLatitude() != 0.0 || item.getLongitude() != 0.0 || !"2000-01-01 00:00:00".equals(item.getLastLocateTime())){
				if(!"2000-01-01 00:00:00".equals(item.getLastLocateTime())){
					double[] zuobiao = GpsToBaiduUtil.wgs2bd(item.getLatitude(),item.getLongitude());
					item.setLatitude(zuobiao[0]);
					item.setLongitude(zuobiao[1]);
//					item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue());
//					item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue());
//					String LocalName= LatLotForLocation.getProvince(Double.toString(item.getLatitude()),Double.toString(item.getLongitude()));
//					item.setLocationName(LocalName);
				}
			});
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		vehiclePTPage.setList(vehilePTData);
		vehiclePTPage.setTotalss(total);
		vehiclePTPage.setPageTotal(vehiclePTPage.getPageTotal());
		vehiclePTPage.setTotal(vehiclePTPage.getPage());
		vehiclePTPage.setPageTotal(vehiclePTPage.getPagesize());
		System.out.println("转换地理位置完成"+new Date());
		log.info("转换地理位置完成"+new Date());
		return vehiclePTPage;
	}

	@Override
	public TpvehDataCount selectTpvehdataCount(Integer deptId, Integer date) {
		return gpsPointDataMapper.selectTpvehdataCount(deptId, date);
	}

	@Override
	public VehiclePTPage<ZFTpvehData> selectZFTpvehdataAllPage(VehiclePTPage vehiclePTPage) {
		Integer total = gpsPointDataMapper.selectZFTpvehdataAllTotal(vehiclePTPage);
		if(vehiclePTPage.getSize()==0){
			if(vehiclePTPage.getTotal()==0){
				vehiclePTPage.setTotal(total);
			}
			if(vehiclePTPage.getTotal()==0){
				return vehiclePTPage;
			}else {
				List<ZFTpvehData> vehlist = gpsPointDataMapper.selectZFTpvehdataAllPage(vehiclePTPage);
				vehiclePTPage.setRecords(vehlist);
				return vehiclePTPage;
			}
		}
		Integer pagetotal = 0;
		if (total > 0) {
			if(total%vehiclePTPage.getSize()==0){
				pagetotal = total / vehiclePTPage.getSize();
			}else {
				pagetotal = total / vehiclePTPage.getSize() + 1;
			}
		}
		if (pagetotal >= vehiclePTPage.getCurrent()) {
			vehiclePTPage.setPageTotal(pagetotal);
			Integer offsetNo = 0;
			if (vehiclePTPage.getCurrent() > 1) {
				offsetNo = vehiclePTPage.getSize() * (vehiclePTPage.getCurrent() - 1);
			}
			vehiclePTPage.setTotal(total);
			vehiclePTPage.setOffsetNo(offsetNo);
			List<ZFTpvehData> vehlist = gpsPointDataMapper.selectZFTpvehdataAllPage(vehiclePTPage);
			vehiclePTPage.setRecords(vehlist);
		}
		return vehiclePTPage;
	}

	@Override
	public List<VehTravel> selectVehTravel(String date, String vehId) {
		return gpsPointDataMapper.selectVehTravel(date, vehId);
	}


}
