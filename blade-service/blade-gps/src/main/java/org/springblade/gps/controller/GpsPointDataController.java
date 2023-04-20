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
package org.springblade.gps.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.feign.IVehiclepostClientBack;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.configurationBean.GPSServer;
import org.springblade.common.tool.GpsToBaiduUtil;
import org.springblade.common.tool.InterfaceUtil;
import org.springblade.common.tool.LatLotForLocation;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.gps.entity.*;
import org.springblade.gps.page.VehiclePTPage;
import org.springblade.gps.service.IGpsPointDataService;
import org.springblade.gps.util.RedisOps;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.ISysClient;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * gps点位数据 控制器
 * @author hyp
 * @since 2019-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/gps/gpsdata")
@Api(value = "获取gps数据接口", tags = "获取gps数据接口")
public class GpsPointDataController {

    private IGpsPointDataService gpsPointDataService;
    private IVehiclepostClientBack vehiclepostClientBack;
	private GPSServer gpsServer;
	private ISysClient sysClient;
	private FileServer fileServer;
	private AlarmServer alarmServer;

	@GetMapping(value = "/getPointData_YN")
	@ApiLog("获取点位数据-获取gps数据(云南)")
	@ApiOperation(value = "获取点位数据-获取gps数据(云南)", notes = "获取点位数据", position = 1)
	public R getPointData_YN(@ApiParam(value = "车辆牌照", required = true) @RequestParam String vehNo, @ApiParam(value = "开始时间", required = true) @RequestParam String beginTime, @ApiParam(value = "结束时间", required = true) @RequestParam String endTime) throws IOException {
		R rs = new R();
		if(StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)){
			return R.data(null);
		}
		//根据车辆牌照获取车辆ID
		Vehicle vehicle = vehiclepostClientBack.vehileOne(vehNo, null, null);
		if(vehicle != null && StringUtils.isNotBlank(vehicle.getId())){
			String url = gpsServer.getPointurlPrefix() + "getHistory?stime=" + beginTime + "&etime=" + endTime + "&VehId=" + vehicle.getId();
			String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
			JSONObject jsonObject = JSONObject.parseObject(jsonstr);
			List<Map<String,Object>> mapListJson = (List)jsonObject.getJSONArray("data");
			for(int i = 0;i<mapListJson.size();i++){
				double lat = Double.parseDouble(mapListJson.get(i).get("Latitude").toString());
				double lon = Double.parseDouble(mapListJson.get(i).get("Longitude").toString());
				double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
				mapListJson.get(i).put("latitude",zuobiao[0]);
				mapListJson.get(i).put("longitude",zuobiao[1]);
			}
			rs.setSuccess(true);
			rs.setMsg("获取成功");
			rs.setData(mapListJson);
			rs.setCode(200);
			return rs;
		}else{
			rs.setSuccess(false);
			rs.setMsg("该车不存在");
			rs.setData(null);
			rs.setCode(200);
			return rs;
		}
	}

//	@GetMapping(value = "/getPointDataNew")
//	@ApiLog("获取点位数据-获取gps数据(new)")
//	@ApiOperation(value = "获取点位数据-获取gps数据(new)", notes = "获取点位数据", position = 1)
//	public R getPointDataNew(@ApiParam(value = "车辆id", required = true) @RequestParam String vehid,
//							 @ApiParam(value = "开始时间", required = true) @RequestParam String beginTime,
//							 @ApiParam(value = "结束时间", required = true) @RequestParam String endTime,
//							 @ApiParam(value = "标识（0、GPS；1、主动安全）", required = true) @RequestParam String mark) throws IOException {
//		if(StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)){
//			return R.data(null);
//		}
////		if (mark == 0) {
//			String url = gpsServer.getPointurlPrefix() + "getHistory?stime=" + beginTime + "&etime=" + endTime + "&VehId=" + vehid;
//			String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
//			JSONObject jsonObject = JSONObject.parseObject(jsonstr);
//
//			List<Map<String,Object>> mapListJson = (List)jsonObject.getJSONArray("data");
//			for(int i = 0;i<mapListJson.size();i++){
//				double lat = Double.parseDouble(mapListJson.get(i).get("Latitude").toString());
//				double lon = Double.parseDouble(mapListJson.get(i).get("Longitude").toString());
//				double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
//				mapListJson.get(i).put("latitude",zuobiao[0]);
//				mapListJson.get(i).put("longitude",zuobiao[1]);
//			}
//			return R.data(mapListJson);
////		} else {
////			String url = gpsServer.getPointurlPrefix() + "getHistory?stime=" + beginTime + "&etime=" + endTime + "&VehId=" + vehid;
////			String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
////			JSONObject jsonObject = JSONObject.parseObject(jsonstr);
////
////			List<Map<String,Object>> mapListJson = (List)jsonObject.getJSONArray("data");
////			for(int i = 0;i<mapListJson.size();i++){
////				double lat = Double.parseDouble(mapListJson.get(i).get("Latitude").toString());
////				double lon = Double.parseDouble(mapListJson.get(i).get("Longitude").toString());
////				double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
////				mapListJson.get(i).put("latitude",zuobiao[0]);
////				mapListJson.get(i).put("longitude",zuobiao[1]);
////			}
////			return R.data(mapListJson);
////		}
//	}

	@GetMapping(value = "/getPointDataNew")
	@ApiLog("获取点位数据-获取gps数据(new)")
	@ApiOperation(value = "获取点位数据-获取gps数据(new)", notes = "获取点位数据", position = 1)
	public R getPointDataNew(@ApiParam(value = "车辆id", required = true) @RequestParam String vehid,
							 @ApiParam(value = "开始时间", required = true) @RequestParam String beginTime,
							 @ApiParam(value = "结束时间", required = true) @RequestParam String endTime,
							 @ApiParam(value = "标识（0、GPS；1、主动安全）", required = true) @RequestParam int mark) throws IOException {
		if(StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)){
			return R.data(null);
		}
		if (mark == 0) {
			String url = gpsServer.getPointurlPrefix() + "getHistory?stime=" + beginTime + "&etime=" + endTime + "&VehId=" + vehid;
			String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
			JSONObject jsonObject = JSONObject.parseObject(jsonstr);

			List<Map<String,Object>> mapListJson = (List)jsonObject.getJSONArray("data");
			for(int i = 0;i<mapListJson.size();i++){
				mapListJson.get(i).put("speed",mapListJson.get(i).get("Velocity").toString());
				mapListJson.get(i).put("gpsTime",mapListJson.get(i).get("GpsTime").toString());
				double lat = Double.parseDouble(mapListJson.get(i).get("Latitude").toString());
				double lon = Double.parseDouble(mapListJson.get(i).get("Longitude").toString());
				double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
				mapListJson.get(i).put("latitude",zuobiao[0]);
				mapListJson.get(i).put("longitude",zuobiao[1]);
			}
			return R.data(mapListJson);
		}else{
			List<VehilePTData> list = gpsPointDataService.selectPointData(beginTime, endTime, vehid);
			list.forEach(item->{
				double lat = Double.parseDouble(item.getLatitude().toString());
				double lon = Double.parseDouble(item.getLongitude().toString());
				double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
				item.setLatitude(zuobiao[0]);
				item.setLongitude(zuobiao[1]);
			});
			return R.data(list);
		}
	}

	@GetMapping(value = "/getPointData")
	@ApiLog("获取点位数据-获取gps数据")
	@ApiOperation(value = "获取点位数据-获取gps数据", notes = "获取点位数据", position = 1)
	public R getPointData(@ApiParam(value = "车辆id", required = true) @RequestParam String vehid, @ApiParam(value = "开始时间", required = true) @RequestParam String beginTime, @ApiParam(value = "结束时间", required = true) @RequestParam String endTime, @ApiParam(value = "标识（0、GPS；1、主动安全）", required = true) @RequestParam int mark) throws IOException {
		if(StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)){
			return R.data(null);
		}
		if (mark == 0) {
			List<VehilePTData> list = gpsPointDataService.selectPointData(beginTime, endTime, vehid);

			for(VehilePTData data:list){
				double lat=data.getLatitude();
				double lon=data.getLongitude();
				double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
				data.setLatitude(zuobiao[0]);
				data.setLongitude(zuobiao[1]);
			}

			return R.data(list);
		} else {
//            String url = "http://202.100.168.30:8096/PointPosition/QueryDataPoint?BeginTime=" + beginTime + "&EndTime=" + endTime + "&VehId=" + vehid;
//            String url = "http://60.171.241.126:7096/PointPosition/QueryDataPoint?BeginTime=" + beginTime + "&EndTime=" + endTime + "&VehId=" + vehid;
			String url = gpsServer.getPointurlPrefix() + "PointPosition/QueryDataPoint?BeginTime=" + beginTime + "&EndTime=" + endTime + "&VehId=" + vehid;
			String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
			JSONArray jsonArray = JSONArray.parseArray(jsonstr.replace("cph", "plate").replace("Lon", "longitude").replace("Lat", "latitude"));

			List<Map<String,Object>> mapListJson = (List)jsonArray;
			for(int i = 0;i<mapListJson.size();i++){
				double lat = Double.parseDouble(mapListJson.get(i).get("latitude").toString());
				double lon = Double.parseDouble(mapListJson.get(i).get("longitude").toString());
				double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
				mapListJson.get(i).put("latitude",zuobiao[0]);
				mapListJson.get(i).put("longitude",zuobiao[1]);
			}

			return R.data(mapListJson);
		}
	}

	@GetMapping(value = "/getAvgTrend")
	@ApiLog("获取点位数据-平均速度走势")
	@ApiOperation(value = "获取点位数据-平均速度走势", notes = "平均速度走势", position = 1)
	public R getAvgTrend(@ApiParam(value = "车辆id", required = true) @RequestParam String vehid, @ApiParam(value = "开始时间", required = true) @RequestParam String beginTime, @ApiParam(value = "结束时间", required = true) @RequestParam String endTime, @ApiParam(value = "标识（0、GPS；1、主动安全）", required = true) @RequestParam int mark,@ApiParam(value = "刻度数", required = true) @RequestParam int scaleNum) throws IOException {
		if(StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)){
			return R.data(null);
		}
		if (mark == 0) {
			List<VehilePTData> list = gpsPointDataService.selectPointData(beginTime, endTime, vehid);
			int size = list.size() / scaleNum;
			List<Map<String,Object>> rList = new ArrayList<Map<String, Object>>();
			if(size == 0){
				int sumSpeed = 0;
				LocalDateTime time = null;
				for (int l = 0; l < 1; l++) {
					VehilePTData vehilePTData = list.get(l);
					if(vehilePTData.getSpeed()!=0){
						time = vehilePTData.getGpsTime();
						sumSpeed+=vehilePTData.getSpeed();
					};
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("avgSpeed", sumSpeed);
					map.put("firstTime", time);
					rList.add(map);
				}
			}else {
				int sumSpeed = 0;
				for (int i = 0; i < scaleNum; i++) {
					LocalDateTime time = list.get(i * size + 1).getGpsTime();
					for (int l = i * size + 1; l < (i + 1) * size; l++) {
						VehilePTData vehilePTData = list.get(l);
						if (vehilePTData.getSpeed() != 0) {
							sumSpeed += vehilePTData.getSpeed();
						}
					}
					Map<String, Object> map = new HashMap<String, Object>();
					if (sumSpeed == 0) {
						map.put("avgSpeed", 0);
						map.put("firstTime", time);
					}else {
						int avgSpeed = sumSpeed / size;
						map.put("avgSpeed", avgSpeed);
						map.put("firstTime", time);
					}
					rList.add(map);
				}
			}
			return R.data(rList);
		}
			return R.data(null);
	}


    @GetMapping("/getImageData")
	@ApiLog("获取图片视频数据-获取gps数据")
    @ApiOperation(value = "获取图片视频数据-获取gps数据", notes = "获取图片视频数据", position = 2)
    public R getImageData(@ApiParam(value = "报警编号", required = true) @RequestParam String alarmNumber, @ApiParam(value = "报警类型", required = true) @RequestParam String alarmType) throws IOException {
		if(StringUtils.isBlank(alarmNumber)){
			return R.data(null);
		}
		String url;
        if ("车距过近报警".equals(alarmType) || "车道偏离报警".equals(alarmType) || "前向碰撞报警".equals(alarmType)) {
//            url = "http://202.100.168.30:8096/AdasAlarm/AdasQueryMedias?AlarmNumber=" + alarmNumber;
//            url = "http:/60.171.241.126:7096/AdasAlarm/AdasQueryMedias?AlarmNumber=" + alarmNumber;
			url = gpsServer.getImgurlPrefix() + "AdasAlarm/AdasQueryMedias?AlarmNumber=" + alarmNumber;
        } else {
//            url = "http://202.100.168.30:8096/BsdAlarm/DsmQueryMedias?AlarmNumber=" + alarmNumber;
//            url = "http://60.171.241.126:7096/BsdAlarm/DsmQueryMedias?AlarmNumber=" + alarmNumber;
			url = gpsServer.getImgurlPrefix() + "BsdAlarm/DsmQueryMedias?AlarmNumber=" + alarmNumber;
        }
        JSONObject jsonObject = JSONObject.parseObject(InterfaceUtil.getUrlData(url));
        return R.data(jsonObject);
    }

	@GetMapping("/getVehiclePT")
	@ApiLog("获取车辆实时点位信息")
	@ApiOperation(value = "获取车辆实时点位信息", notes = "获取车辆实时点位信息", position = 2)
	public R getVehiclePT(@ApiParam(value = "单位名称", required = true) @RequestParam String company
		,@ApiParam(value = "车辆状态") @RequestParam String vehicleStatus
		,@ApiParam(value = "车辆牌照") @RequestParam  String cpn)  {
		String s = RedisOps.get(company);
		if(s==null){
			List<VehiclePT> vehiclePTS=new ArrayList<>();
			return  R.data(vehiclePTS);
		}
		String substring = s.substring(s.indexOf('['), s.lastIndexOf(']')+1);
		cn.hutool.json.JSONArray objects = JSONUtil.parseArray(substring);
		List<VehiclePT> vehiclePTS = JSONUtil.<VehiclePT>toList(objects, VehiclePT.class);
		if(!"".equals(cpn)){
			List<VehiclePT> data=new ArrayList<>();
			Pattern pattern = Pattern.compile(cpn);
			for(int a=0;a<vehiclePTS.size();a++){
				Matcher matcher = pattern.matcher(vehiclePTS.get(a).getCph());
				if(matcher.find()){
					data.add(vehiclePTS.get(a));
				}
			}
			return  R.data(data);
		}
		for(VehiclePT item:vehiclePTS){
			if("运行".equals(item.getStatus()) && item.getAcc()==1){
				item.setAccShow("在线-运行-acc开");
			}else if("运行".equals(item.getStatus()) && item.getAcc()==0){
				item.setAccShow("在线-停车-acc关");
			}else if("离线".equals(item.getStatus()) && item.getAcc()==0){
				item.setAccShow("离线-离线-acc关");
			}else if("离线".equals(item.getStatus()) && item.getAcc()==1){
				item.setAccShow("在线-停车-acc开");
			}
			double lat=Double.valueOf(item.getLatitude().toString());
			double lon=Double.valueOf(item.getLongitude().toString());
			double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
			item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
			item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
			String LocalName=LatLotForLocation.getProvince(item.getLatitude().toString(),item.getLongitude().toString(),alarmServer.getAddressAk());
			item.setLocationName(LocalName);
		}
		Iterator<VehiclePT> it = vehiclePTS.iterator();
		while(it.hasNext()){
			VehiclePT v = it.next();
			String ss = v.getStatus();
			if(StringUtils.isNotEmpty(vehicleStatus) && !vehicleStatus.equals(ss)){
				it.remove();
			}
		}
		System.out.println(vehiclePTS);
		return R.data(vehiclePTS);
	}

    @PostMapping("/getVehiclePage")
    @ApiLog("获取车辆实时点位信息(分页)")
    @ApiOperation(value = "获取车辆实时点位信息(分页)", notes = "获取车辆实时点位信息(分页)", position = 2)
    public R<VehiclePTPage<VehiclePT>> getVehiclePage(@RequestBody VehiclePTPage vehiclePTPage)  {
		String company = vehiclePTPage.getCompany();
        String s = RedisOps.get(company);
        if(s==null){
			VehiclePTPage vehiclePTS=new VehiclePTPage();
			return  R.data(vehiclePTS);
		}
        String substring = s.substring(s.indexOf('['), s.lastIndexOf(']')+1);
        cn.hutool.json.JSONArray objects = JSONUtil.parseArray(substring);
        List<VehiclePT> vehiclePTS = JSONUtil.<VehiclePT>toList(objects, VehiclePT.class);
		if(!"".equals(vehiclePTPage.getCpn())){
			String cph = vehiclePTPage.getCpn();
			List<VehiclePT> data=new ArrayList<>();
			for(int a=0;a<vehiclePTS.size();a++){
				if(cph.contains(vehiclePTS.get(a).getCph())){
					data.add(vehiclePTS.get(a));
				}
			}
			vehiclePTS = data;
		}
		int total = vehiclePTS.size();
		int size =  vehiclePTPage.getSize();
		int pagetotal = (total / size) + ((total % size > 0) ? 1 : 0);
		vehiclePTPage.setTotal(total);
		vehiclePTPage.setPageTotal(pagetotal);
		// 根据页码取数据
		List<VehiclePT> vehiclePTList = new ArrayList<>();
		int fromIndex = (vehiclePTPage.getCurrent()-1)*size;
		int toIndex = vehiclePTPage.getCurrent()*size;
		if(toIndex>total){
			toIndex = total;
		}
		vehiclePTList = vehiclePTS.subList(fromIndex, toIndex);

		for(VehiclePT item:vehiclePTList){

			if("运行".equals(item.getStatus()) && item.getAcc()==1){
				item.setAccShow("在线-运行-acc开");
			}else if("运行".equals(item.getStatus()) && item.getAcc()==0){
				item.setAccShow("在线-停车-acc关");
			}else if("离线".equals(item.getStatus()) && item.getAcc()==0){
				item.setAccShow("离线-离线-acc关");
			}else if("离线".equals(item.getStatus()) && item.getAcc()==1){
				item.setAccShow("在线-停车-acc开");
			}
			double lat=Double.valueOf(item.getLatitude().toString());
			double lon=Double.valueOf(item.getLongitude().toString());
			double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
			item.setLatitude(new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
			item.setLongitude(new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
			String LocalName=LatLotForLocation.getProvince(item.getLatitude().toString(),item.getLongitude().toString(),alarmServer.getAddressAk());
			item.setLocationName(LocalName);

		}
		Iterator<VehiclePT> it = vehiclePTList.iterator();
		while(it.hasNext()){
			VehiclePT v = it.next();
			String ss = v.getStatus();
			if(StringUtils.isNotEmpty(vehiclePTPage.getVehicleStatus()) && !vehiclePTPage.getVehicleStatus().equals(ss)){
				it.remove();
			}
		}
		vehiclePTPage.setRecords(vehiclePTList);
        return R.data(vehiclePTPage);
    }

//	@GetMapping("/getVehicleNewPage")
//	@ApiLog("获取车辆实时点位信息new(分页)")
//	@ApiOperation(value = "获取车辆实时点位信息new(分页)", notes = "获取车辆实时点位信息new(分页)", position = 2)
//	public R getVehicleNewPage(VehiclePTPage vehiclePTPage) throws IOException {
//		String url="";
//		if("在线".equals(vehiclePTPage.getVehicleStatus())){
//			vehiclePTPage.setVehicleStatus("2");
//			url = gpsServer.getSpreadurlPrefix() + "SpreadLocation?dept=" + vehiclePTPage.getDeptId()+ "&page=" + vehiclePTPage.getPage()+ "&size=" + vehiclePTPage.getPagesize()+ "&zaixian=" + vehiclePTPage.getVehicleStatus() ;
//		}else if("离线".equals(vehiclePTPage.getVehicleStatus())){
//			vehiclePTPage.setVehicleStatus("3");
//			url = gpsServer.getSpreadurlPrefix() + "SpreadLocation?dept=" + vehiclePTPage.getDeptId()+ "&page=" + vehiclePTPage.getPage()+ "&size=" + vehiclePTPage.getPagesize()+ "&zaixian=" + vehiclePTPage.getVehicleStatus() ;
//		}else if("停用".equals(vehiclePTPage.getVehicleStatus())){
//			vehiclePTPage.setVehicleStatus("3");
//			url = gpsServer.getSpreadurlPrefix() + "SpreadLocation?dept=" + vehiclePTPage.getDeptId()+ "&page=" + vehiclePTPage.getPage()+ "&size=" + vehiclePTPage.getPagesize()+ "&zhuangtai=" + vehiclePTPage.getVehicleStatus() ;
//		}else if("报废".equals(vehiclePTPage.getVehicleStatus())){
//			vehiclePTPage.setVehicleStatus("4");
//			url = gpsServer.getSpreadurlPrefix() + "SpreadLocation?dept=" + vehiclePTPage.getDeptId()+ "&page=" + vehiclePTPage.getPage()+ "&size=" + vehiclePTPage.getPagesize()+ "&zhuangtai=" + vehiclePTPage.getVehicleStatus() ;
//		}else{
//			vehiclePTPage.setVehicleStatus("");
//			url = gpsServer.getSpreadurlPrefix() + "SpreadLocation?dept=" + vehiclePTPage.getDeptId() + "&page=" + vehiclePTPage.getPage()+ "&size=" + vehiclePTPage.getPagesize();
//		}
//		if (!StringUtils.isBlank(vehiclePTPage.getCpn())){
//			url = gpsServer.getSpreadurlPrefix() + "SpreadLocation?dept=" + vehiclePTPage.getDeptId()+ "&page=" + vehiclePTPage.getPage()+ "&size=" + vehiclePTPage.getPagesize()+ "&cph=" + vehiclePTPage.getCpn() ;
//		}
//		String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
//		if(jsonstr.equals("0")){
//			return R.data(null);
//		}
//		JSONObject jsonObject = JSONObject.parseObject(jsonstr);
//		if(jsonObject != null){
//			Integer total = (Integer) jsonObject.get("total");
//			Integer pageTotal = (Integer) jsonObject.get("pageTotal");
//			Integer page = (Integer) jsonObject.get("page");
//			Integer size = (Integer) jsonObject.get("size");
//			vehiclePTPage.setTotalss(total);
//			vehiclePTPage.setPageTotal(pageTotal);
//			vehiclePTPage.setTotal(page);
//			vehiclePTPage.setPageTotal(size);
//		}else{
//			return R.data(null);
//		}
//
//		List<Map<String,Object>> vehiclePTList = (List)jsonObject.getJSONArray("data");
//		if(vehiclePTList.size() > 0){
//			for(int i = 0;i<vehiclePTList.size();i++){
//				String vehid = (String) vehiclePTList.get(i).get("vehicleID");
//				Vehicle vo = vehiclepostClientBack.getvehileYYS(vehid);
//				if(vo == null || vo.getYunyingshang() == null){
//					vehiclePTList.get(i).put("yunYingShang",null);
//				}else{
//					vehiclePTList.get(i).put("yunYingShang",vo.getYunyingshang());
//				}
//				vehiclePTList.get(i).put("vehicleID",vehiclePTList.get(i).get("vehicleID"));
//				vehiclePTList.get(i).put("cph",vehiclePTList.get(i).get("vehicleNo"));
//				vehiclePTList.get(i).put("platecolor",vehiclePTList.get(i).get("vehicleColor"));
//
//				vehiclePTList.get(i).put("deviceID",vehiclePTList.get(i).get("DeviceID"));
//				vehiclePTList.get(i).put("deptID",vehiclePTList.get(i).get("DeptID"));
//				vehiclePTList.get(i).put("company",vehiclePTList.get(i).get("DeptName"));
//				vehiclePTList.get(i).put("gpstime",vehiclePTList.get(i).get("Gpstime"));
//
//				double lat = Double.parseDouble(vehiclePTList.get(i).get("Latitude").toString());
//				double lon = Double.parseDouble(vehiclePTList.get(i).get("Longitude").toString());
//				double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
//				vehiclePTList.get(i).put("latitude",new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
//				vehiclePTList.get(i).put("longitude",new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
//
//				String LocalName=LatLotForLocation.getProvince(vehiclePTList.get(i).get("Latitude").toString(),vehiclePTList.get(i).get("Longitude").toString());
//				vehiclePTList.get(i).put("locationName",LocalName);
//
//				vehiclePTList.get(i).put("velocity",vehiclePTList.get(i).get("Velocity"));
//				vehiclePTList.get(i).put("angle",vehiclePTList.get(i).get("Angle"));
//				vehiclePTList.get(i).put("high",vehiclePTList.get(i).get("High"));
//				Integer alarm = (Integer) vehiclePTList.get(i).get("Alarm");
//				if(alarm == 0){
//					vehiclePTList.get(i).put("alarm","未报警");
//				}else{
//					vehiclePTList.get(i).put("alarm","报警");
//				}
//
//				vehiclePTList.get(i).put("alarmNote",vehiclePTList.get(i).get("AlarmNote"));
//				Integer dingwei = (Integer) vehiclePTList.get(i).get("LocalFlag");
//				if(dingwei == 0){
//					vehiclePTList.get(i).put("LocalFlag","不定位");
//				}else{
//					vehiclePTList.get(i).put("LocalFlag","定位");
//				}
//				Integer zaixian = (Integer) vehiclePTList.get(i).get("zaixian");
//				if(zaixian == 2){
//					vehiclePTList.get(i).put("zaixian","上线");
//				}else{
//					vehiclePTList.get(i).put("zaixian","未上线");
//				}
//				vehiclePTList.get(i).put("status",vehiclePTList.get(i).get("VehState"));
//				vehiclePTList.get(i).put("shiyongxingzhi",vehiclePTList.get(i).get("shiyongxingzhi"));
//			}
//		}else{
//			return R.data(null);
//		}
//		vehiclePTPage.setList(vehiclePTList);
//		return R.data(vehiclePTPage);
//	}

	@GetMapping("/getVehicleNewPage")
	@ApiLog("获取车辆实时点位信息new(分页)")
	@ApiOperation(value = "获取车辆实时点位信息new(分页)", notes = "获取车辆实时点位信息new(分页)", position = 2)
	public R<VehiclePTPage<TpvehData>> getVehicleNewPage(
		@ApiParam(value = "企业ID", required = true) @RequestParam Integer deptId,
		@ApiParam(value = "页数") @RequestParam Integer page,
		@ApiParam(value = "每页条数") @RequestParam Integer pagesize,
		@ApiParam(value = "车辆运行状态") @RequestParam String vehicleStatus,
		@ApiParam(value = "车辆牌照") String cpn
	) throws IOException {
		R rs = new R();
		VehiclePTPage vehiclePTPage = new VehiclePTPage();
		vehiclePTPage.setDeptId(deptId);
		if(pagesize.equals("0")){
			vehiclePTPage.setPage(0);
			vehiclePTPage.setPagesize(0);
			vehiclePTPage.setCurrent(0);
			vehiclePTPage.setSize(0);
		}else {
			vehiclePTPage.setCurrent(page);
			vehiclePTPage.setSize(pagesize);
			vehiclePTPage.setPage(page);
			vehiclePTPage.setPagesize(pagesize);
		}
		vehiclePTPage.setVehicleStatus(vehicleStatus);
		vehiclePTPage.setCpn(cpn);
		vehiclePTPage.setDate(fileServer.getMaxOfflineTime());
		Date begindate = new Date();
		System.out.println("请求开始"+begindate);
		VehiclePTPage<TpvehData> tpvehDataList = null;
		try {
			tpvehDataList = gpsPointDataService.selectTpvehdataAllPage(vehiclePTPage);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			rs = R.fail(e.getMessage());
		}

		Date endate = new Date();
		System.out.println("请求结束"+endate);
		System.out.println(endate.getTime()-begindate.getTime());
		rs.setMsg("获取成功");
		rs.setCode(200);
		rs.setData(tpvehDataList);
		System.out.println(tpvehDataList);
		return rs;
	}

	@PostMapping("/getVehlicPTNewdetail")
	@ApiLog("获取车辆实时点位信息详情(new)")
	@ApiOperation(value = "获取车辆实时点位信息详情(new)", notes = "获取车辆实时点位信息详情(new)", position = 2)
	public R getVehlicPTNewdetail(@RequestBody VehicleDetailPage vehicleDetailPage) throws IOException {
		String url="";
		//详情车辆
		List<VehicleForPlate> list = vehicleDetailPage.getList();
		url = gpsServer.getSpreadurlPrefix() + "SpreadLocation?dept=" + vehicleDetailPage.getDeptId() + "&page=0&size=0";
		String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
		if(jsonstr.equals("0")){
			return R.data(null);
		}
		JSONObject jsonObject = JSONObject.parseObject(jsonstr);
		List<Map<String,Object>> vehiclePTList = (List)jsonObject.getJSONArray("data");
		for(int i = 0;i<vehiclePTList.size();i++){
			String vehid = (String) vehiclePTList.get(i).get("vehicleID");
			Vehicle vo = vehiclepostClientBack.getvehileYYS(vehid);
			if(vo == null || vo.getYunyingshang() == null){
				vehiclePTList.get(i).put("yunYingShang",null);
			}else{
				vehiclePTList.get(i).put("yunYingShang",vo.getYunyingshang());
			}
			vehiclePTList.get(i).put("vehicleID",vehiclePTList.get(i).get("vehicleID"));
			vehiclePTList.get(i).put("cph",vehiclePTList.get(i).get("vehicleNo"));
			vehiclePTList.get(i).put("platecolor",vehiclePTList.get(i).get("vehicleColor"));

			vehiclePTList.get(i).put("deviceID",vehiclePTList.get(i).get("DeviceID"));
			vehiclePTList.get(i).put("deptID",vehiclePTList.get(i).get("DeptID"));
			vehiclePTList.get(i).put("company",vehiclePTList.get(i).get("DeptName"));
			vehiclePTList.get(i).put("gpstime",vehiclePTList.get(i).get("Gpstime"));

			double lat = Double.parseDouble(vehiclePTList.get(i).get("Latitude").toString());
			double lon = Double.parseDouble(vehiclePTList.get(i).get("Longitude").toString());
			double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
			vehiclePTList.get(i).put("latitude",new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
			vehiclePTList.get(i).put("longitude",new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
			String LocalName=LatLotForLocation.getProvince(vehiclePTList.get(i).get("Latitude").toString(),vehiclePTList.get(i).get("Longitude").toString(),alarmServer.getAddressAk());
			vehiclePTList.get(i).put("locationName",LocalName);
			vehiclePTList.get(i).put("velocity",vehiclePTList.get(i).get("Velocity"));
			vehiclePTList.get(i).put("angle",vehiclePTList.get(i).get("Angle"));
			vehiclePTList.get(i).put("high",vehiclePTList.get(i).get("High"));
			Integer alarm = (Integer) vehiclePTList.get(i).get("Alarm");
			if(alarm == 0){
				vehiclePTList.get(i).put("alarm","未报警");
			}else{
				vehiclePTList.get(i).put("alarm","报警");
			}
			vehiclePTList.get(i).put("alarmNote",vehiclePTList.get(i).get("AlarmNote"));
			Integer dingwei = (Integer) vehiclePTList.get(i).get("LocalFlag");
			if(dingwei == 0){
				vehiclePTList.get(i).put("LocalFlag","不定位");
			}else{
				vehiclePTList.get(i).put("LocalFlag","定位");
			}
			Integer zaixian = (Integer) vehiclePTList.get(i).get("zaixian");
			if(zaixian == 2){
				vehiclePTList.get(i).put("zaixian","运行");
			}else{
				vehiclePTList.get(i).put("zaixian","不在线");
			}
			vehiclePTList.get(i).put("status",vehiclePTList.get(i).get("VehState"));
			vehiclePTList.get(i).put("shiyongxingzhi",vehiclePTList.get(i).get("shiyongxingzhi"));

			for(int j = 0;j<list.size();j++){
				if(vehiclePTList.get(i).get("vehicleNo").equals(list.get(j).getCph()) && vehiclePTList.get(i).get("vehicleColor").equals(list.get(j).getPlatecolor())){
					String cheliangpaizhao= (String) vehiclePTList.get(i).put("cph",vehiclePTList.get(i).get("vehicleNo"));
					String chepaiyanse= (String) vehiclePTList.get(i).put("platecolor",vehiclePTList.get(i).get("vehicleColor"));
					Vehicle vehicle = vehiclepostClientBack.vehileOne(cheliangpaizhao, chepaiyanse, vehicleDetailPage.getDeptId());
					vehiclePTList.get(i).put("vehicledata",vehicle);
				}
			}
		}
		return R.data(vehiclePTList);
	}


	@PostMapping("/getVehlicPTdetail")
	@ApiLog("获取车辆实时点位信息详情")
	@ApiOperation(value = "获取车辆实时点位信息详情", notes = "获取车辆实时点位信息详情", position = 2)
	public R getVehlicPTdetail(@RequestBody  VehicleDetailPage vehicleDetailPage){
		String s = RedisOps.get(vehicleDetailPage.getCompany());
		String substring = s.substring(s.indexOf('['), s.lastIndexOf(']')+1);
		cn.hutool.json.JSONArray objects = JSONUtil.parseArray(substring);
		//运行车辆
		List<VehiclePT> vehiclePTS = JSONUtil.<VehiclePT>toList(objects, VehiclePT.class);
		//详情车辆
		List<VehicleForPlate> list = vehicleDetailPage.getList();
		for(VehiclePT item:vehiclePTS){

			if("运行".equals(item.getStatus()) && item.getAcc()==1){
				item.setAccShow("在线-运行-acc开");
			}else if("运行".equals(item.getStatus()) && item.getAcc()==0){
				item.setAccShow("在线-停车-acc关");
			}else if("离线".equals(item.getStatus()) && item.getAcc()==0){
				item.setAccShow("离线-离线-acc关");
			}else if("离线".equals(item.getStatus()) && item.getAcc()==1){
				item.setAccShow("在线-停车-acc开");

			}

		}
		List<VehiclePT> data=new ArrayList<>();
		 for(VehicleForPlate ve:list){
				String cpn=ve.getCph();
				String color=ve.getPlatecolor();
			for(VehiclePT pt:vehiclePTS){
				if(pt.getCph().equals(cpn) && pt.getPlatecolor().equals(color)){
					data.add(pt);
				}
			}
		 }
		 for(VehiclePT a:data){
			 double lat=Double.valueOf(a.getLatitude().toString());
			 double lon=Double.valueOf(a.getLongitude().toString());
			 double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
			 a.setLatitude(new BigDecimal(zuobiao[0]));
			 a.setLongitude(new BigDecimal(zuobiao[1]));
			String LocalName=LatLotForLocation.getProvince(a.getLatitude().toString(),a.getLongitude().toString(),alarmServer.getAddressAk());
			a.setLocationName(LocalName);
			String cheliangpaizhao=a.getCph();
			String chepaiyanse=a.getPlatecolor();
			 Vehicle vehicle = vehiclepostClientBack.vehileOne(cheliangpaizhao, chepaiyanse, vehicleDetailPage.getDeptId());
			 a.setVehicledata(vehicle);

		 }
			return  R.data(data);
	}

	@GetMapping("/getVehicleTree")
	@ApiLog("获取车辆树")
	@ApiOperation(value = "获取车辆树", notes = "获取车辆树", position = 2)
	public R getVehicleTree(@ApiParam(value = "单位名称", required = true) @RequestParam String company
		,@ApiParam(value = "车辆牌照") @RequestParam  String cph)  {
		Dept dept = sysClient.getDeptByName(company);
		if(dept==null){
			List<VehicleNode> vehicles=new ArrayList<>();
			return  R.data(vehicles);
		}
		List<VehicleNode> vehicles=gpsPointDataService.tree(company);
		List<VehicleNode> data = new ArrayList<>();

		if(!"".equals(cph)){
			Pattern pattern = Pattern.compile(cph);
			for(int a=0;a<vehicles.size();a++){
				Matcher matcher = pattern.matcher(vehicles.get(a).getTitle());
				if(matcher.find()){
					data.add(vehicles.get(a));
				}
			}
		}else{
			data = vehicles;
		}
		Iterator<VehicleNode> it = data.iterator();
		while(it.hasNext()){
			VehicleNode v = it.next();
			v.setParentId(dept.getId());
		}
		VehicleNode vehicle = new VehicleNode();
		vehicle.setParentId(0);
		vehicle.setId(dept.getId());
		vehicle.setTitle(company);
		vehicle.setChildren(data);
		return R.data(vehicle);
	}


//	@GetMapping("/getHNVehicleNewPage")
//	@ApiLog("获取车辆实时点位信息new(河南)")
//	@ApiOperation(value = "获取车辆实时点位信息new(河南)", notes = "获取车辆实时点位信息new(河南)", position = 2)
//	public R getHNVehicleNewPage(VehiclePTPage vehiclePTPage) throws IOException {
//		String url="";
//		url = "http://39.98.38.60:30002/getHistory?LineId="+ vehiclePTPage.getLineId();
//		String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
//		if(jsonstr.equals("0")){
//			return R.data(null);
//		}
//		JSONObject jsonObject = JSONObject.parseObject(jsonstr);
//		Integer total = (Integer) jsonObject.get("total");
//
//		vehiclePTPage.setTotal(total);
//		List<Map<String,Object>> vehiclePTList = (List)jsonObject.getJSONArray("data");
//		for(int i = 0;i<vehiclePTList.size();i++){
//			vehiclePTList.get(i).put("vehicleNo",vehiclePTList.get(i).get("vehicleNo"));
//			vehiclePTList.get(i).put("GpsTime",vehiclePTList.get(i).get("GpsTime"));
//			double lat = Double.parseDouble(vehiclePTList.get(i).get("Latitude").toString());
//			double lon = Double.parseDouble(vehiclePTList.get(i).get("Longitude").toString());
//			double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
//			vehiclePTList.get(i).put("latitude",new BigDecimal(zuobiao[0]).setScale(6,BigDecimal.ROUND_HALF_UP));
//			vehiclePTList.get(i).put("longitude",new BigDecimal(zuobiao[1]).setScale(6,BigDecimal.ROUND_HALF_UP));
//		}
//		vehiclePTPage.setList(vehiclePTList);
//		return R.data(vehiclePTPage);
//	}

	@GetMapping("/getVehTravel")
	@ApiLog("根据车辆、日期获取车辆行驶里程及行驶时间")
	@ApiOperation(value = "根据车辆、日期获取车辆行驶里程及行驶时间", notes = "根据车辆、日期获取车辆行驶里程及行驶时间", position = 20)
	public R getVehTravel(@ApiParam(value = "日期", required = true) @RequestParam String date
		,@ApiParam(value = "车辆Id") @RequestParam String vehId)  {
		List<VehTravel> vehicles=gpsPointDataService.selectVehTravel(date, vehId);
		return R.data(vehicles);
	}

	@GetMapping(value = "/getPointDataDeail")
	@ApiLog("获取点位数据-轨迹回放列表")
	@ApiOperation(value = "获取点位数据-轨迹回放列表", notes = "获取点位数据", position = 31)
	public R getPointDataDeail(@ApiParam(value = "车辆id", required = true) @RequestParam String vehid,
							 @ApiParam(value = "开始时间", required = true) @RequestParam String beginTime,
							 @ApiParam(value = "结束时间", required = true) @RequestParam String endTime,
							 @ApiParam(value = "车辆牌照", required = true) @RequestParam String cheliangpaizhao,
						     @ApiParam(value = "车牌颜色", required = true) @RequestParam String chepaiyanse
						 ) throws IOException {
		if(StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)){
			return R.data(null);
		}
		String url = gpsServer.getPointurlPrefix() + "getHistory?stime=" + beginTime + "&etime=" + endTime + "&VehId=" + vehid;
		String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
		JSONObject jsonObject = JSONObject.parseObject(jsonstr);

		String data = jsonObject.get("data").toString();
		System.out.println("------------------------------------");
		System.out.println(data);
		List<VehilePTData> vehilePTData = JSONObject.parseArray(data, VehilePTData.class);
		Iterator<VehilePTData> iterator = vehilePTData.iterator();
		while (iterator.hasNext()) {
			VehilePTData ve = iterator.next();
//			if (ve.getVelocity() < 1) {
//				iterator.remove();
//			}
		}
		for(int i = 0;i<vehilePTData.size();i++){
			vehilePTData.get(i).setPlate(cheliangpaizhao);
			vehilePTData.get(i).setColor(chepaiyanse);
			if(vehilePTData.get(i).getVelocity() > 0){
				double[] doubles = GpsToBaiduUtil.wgs2bd(vehilePTData.get(i).getLatitude(), vehilePTData.get(i).getLongitude());
				vehilePTData.get(i).setLatitude(doubles[0]);
				vehilePTData.get(i).setLongitude(doubles[1]);
//				String LocalName= LatLotForLocation.getProvince(vehilePTData.get(i).getLatitude().toString(),vehilePTData.get(i).getLongitude().toString());
//				vehilePTData.get(i).setRoadName(LocalName);
			}
		}
		return R.data(vehilePTData);
	}


	@GetMapping(value = "/getPointDataShow")
	@ApiLog("获取点位数据-轨迹回放列表-地理位置解析")
	@ApiOperation(value = "获取点位数据-轨迹回放列表-地理位置解析", notes = "获取点位数据", position = 32)
	public R getPointDataShow(@ApiParam(value = "经度", required = true) @RequestParam String longitude,
							   @ApiParam(value = "纬度", required = true) @RequestParam String latitude
	) throws IOException {
		if(StringUtils.isBlank(longitude) || StringUtils.isBlank(latitude)){
			return R.data(null);
		}
		VehilePTData vehilePTData = new VehilePTData();
		String LocalName= LatLotForLocation.getProvince(latitude,longitude,alarmServer.getAddressAk());
		vehilePTData.setRoadName(LocalName);
		return R.data(vehilePTData);
	}

}
