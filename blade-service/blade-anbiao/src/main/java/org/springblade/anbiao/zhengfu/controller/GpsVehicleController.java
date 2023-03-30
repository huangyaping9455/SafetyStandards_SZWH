/**
 * FileName: GpsVehicleController
 * Author:   呵呵哒
 */
package org.springblade.anbiao.zhengfu.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.zhengfu.entity.Organization;
import org.springblade.anbiao.zhengfu.page.GpsVehiclePage;
import org.springblade.anbiao.zhengfu.service.IOrganizationService;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.tool.GpsToBaiduUtil;
import org.springblade.common.tool.InterfaceUtil;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.gps.entity.TpvehDataCount;
import org.springblade.gps.entity.ZFTpvehData;
import org.springblade.gps.feign.IGpsPointDataClient;
import org.springblade.gps.page.VehiclePTPage;
import org.springblade.system.feign.ISysClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 呵呵哒
 * @描述
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/gpsVehicle")
@Api(value = "政府-车辆统计", tags = "政府-车辆统计")
public class GpsVehicleController {

	private FileServer fileServer;
	private IOrganizationService iOrganizationService;
	private ISysClient iSysClient;
	private IGpsPointDataClient iGpsPointDataClient;

//	@GetMapping(value = "/getVehicleList")
//	@ApiLog("政府-获取统计车辆数据")
//	@ApiOperation(value = "政府-获取统计车辆数据", notes = "传入相关参数",position = 1)
//	public R getVehicleList(@ApiParam(value = "企业ID", required = true) @RequestParam String dept,
//							@ApiParam(value = "在线状态（1:全部;2:上线;3:未上线;）") @RequestParam int zaixian,
//							@ApiParam(value = "车辆状态（1:全部;2:监控车辆;3:停用;4:在用;）", required = false) @RequestParam int zhuangtai,
//							@ApiParam(value = "第几页(从1开始)", required = false) @RequestParam int page,
//							@ApiParam(value = "每页条数", required = false) @RequestParam int pagesize,
//							@ApiParam(value = "车辆牌照", required = false) @RequestParam String cph,
//							@ApiParam(value = "企业名称", required = false) @RequestParam String deptname) throws IOException {
//		GpsVehiclePage gpsVehiclePage = new GpsVehiclePage();
//		List<ZhengFuOrganization> zhengFuOrganizations = null;
//		Organization jb = iOrganizationService.selectGetZFJB(dept);
//		if(!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())){
//			zhengFuOrganizations = iOrganizationService.selectGetZF(dept,null,null,null);
//		}
//
//		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
//			zhengFuOrganizations = iOrganizationService.selectGetZF(null,dept,null,null);
//		}
//
//		if(!StringUtils.isBlank(jb.getCountry())){
//			zhengFuOrganizations = iOrganizationService.selectGetZF(null,null,dept,null);
//		}
//
//		if(zhengFuOrganizations.size() < 1){
//			R r = new R();
//			String[] aa = new String[0];
//			r.setData(aa);
//			r.setCode(200);
//			r.setMsg("获取成功");
//			return r;
//		}else {
//			String deptId = "";
//			String deptIds = "";
//			String deptIds_name = "";
//			if(StringUtils.isBlank(deptname.trim())) {
//				for (int i = 0; i < zhengFuOrganizations.size(); i++) {
//					if(zhengFuOrganizations.get(i).getJigouleixing().equals("qiye")){
//						deptIds = zhengFuOrganizations.get(i).getQiyeid();
//						deptId += deptIds+ ',';
//					}
//				}
//			}else{
//				List<Dept> depts = iSysClient.getByName(deptname.trim());
//				for(int j=0; j<depts.size(); j++){
//					deptIds_name = depts.get(j).getId().toString();
//					for (int i = 0; i < zhengFuOrganizations.size(); i++) {
//						deptIds = zhengFuOrganizations.get(i).getQiyeid();
//						if(deptIds.contains(deptIds_name)){
//							deptId += deptIds+ ',';
//							System.out.println(deptId);
//						}else{
//							deptId += "";
//						}
//					}
////					if(depts.size() < 2){
////						deptId += deptIds;
////					}else{
////						deptId += deptIds+ ',';
////					}
//				}
//			}
//			gpsVehiclePage.setDept(deptId);
//			gpsVehiclePage.setZaixian(zaixian);
//			gpsVehiclePage.setZhuangtai(zhuangtai);
//			gpsVehiclePage.setCph(cph.trim());
//			gpsVehiclePage.setPage(page);
//			gpsVehiclePage.setPagesize(pagesize);
//			String url = "";
//			if (page < 1 && pagesize < 1) {
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai();
//			} else {
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize();
//			}
//
//			if(!StringUtils.isBlank(gpsVehiclePage.getCph())){
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize()+ "&cph=" + gpsVehiclePage.getCph();
//			}
//
//			if(!StringUtils.isBlank(gpsVehiclePage.getDeptname())){
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize();
//			}
//
//			if(!StringUtils.isBlank(gpsVehiclePage.getCph()) && !StringUtils.isBlank(gpsVehiclePage.getDeptname())){
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize()+ "&cph=" + gpsVehiclePage.getCph();
//			}
//
//			String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
//			JSONObject jsonArrays = JSONArray.parseObject(jsonstr);
//			return R.data(jsonArrays);
//		}
//	}

//	@GetMapping(value = "/getVehicleList")
//	@ApiLog("政府-获取统计车辆数据")
//	@ApiOperation(value = "政府-获取统计车辆数据", notes = "传入相关参数",position = 1)
//	public R getVehicleList(@ApiParam(value = "企业ID", required = true) @RequestParam String dept,
//							@ApiParam(value = "在线状态（1:全部;2:上线;3:未上线;）") @RequestParam int zaixian,
//							@ApiParam(value = "车辆状态（1:全部;2:监控车辆;3:停用;4:在用;）", required = false) @RequestParam int zhuangtai,
//							@ApiParam(value = "第几页(从1开始)", required = false) @RequestParam int page,
//							@ApiParam(value = "每页条数", required = false) @RequestParam int pagesize,
//							@ApiParam(value = "车辆牌照", required = false) @RequestParam String cph,
//							@ApiParam(value = "使用性质", required = false) @RequestParam String shiyongxingzhi,
//							@ApiParam(value = "企业名称", required = false) @RequestParam String deptname) throws IOException {
//		GpsVehiclePage gpsVehiclePage = new GpsVehiclePage();
//		List<ZhengFuOrganization> zhengFuOrganizations = null;
//		Organization jb = iOrganizationService.selectGetZFJB(dept);
//		if(!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())){
//			zhengFuOrganizations = iOrganizationService.selectGetZF(dept,null,null,null);
//		}
//
//		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
//			zhengFuOrganizations = iOrganizationService.selectGetZF(null,dept,null,null);
//		}
//
//		if(!StringUtils.isBlank(jb.getCountry())){
//			zhengFuOrganizations = iOrganizationService.selectGetZF(null,null,dept,null);
//		}
//
//		if(zhengFuOrganizations.size() < 1){
//			R r = new R();
//			String[] aa = new String[0];
//			r.setData(aa);
//			r.setCode(200);
//			r.setMsg("获取成功");
//			return r;
//		}else {
//			String deptId = "";
//			String deptIds = "";
//			String deptIds_name = "";
//			if(StringUtils.isBlank(deptname.trim())) {
//				for (int i = 0; i < zhengFuOrganizations.size(); i++) {
//					if(zhengFuOrganizations.get(i).getJigouleixing().equals("qiye")){
//						deptIds = zhengFuOrganizations.get(i).getQiyeid();
//						deptId += deptIds+ ',';
//					}
//				}
//			}else{
//				List<Dept> depts = iSysClient.getByName(deptname.trim());
//				for(int j=0; j<depts.size(); j++){
//					deptIds_name = depts.get(j).getId().toString();
//					for (int i = 0; i < zhengFuOrganizations.size(); i++) {
//						deptIds = zhengFuOrganizations.get(i).getQiyeid();
//						if(deptIds.contains(deptIds_name)){
//							deptId += deptIds+ ',';
//							System.out.println(deptId);
//						}else{
//							deptId += "";
//						}
//					}
////					if(depts.size() < 2){
////						deptId += deptIds;
////					}else{
////						deptId += deptIds+ ',';
////					}
//				}
//			}
//			gpsVehiclePage.setDept(deptId);
//			gpsVehiclePage.setZaixian(zaixian);
//			gpsVehiclePage.setZhuangtai(zhuangtai);
//			gpsVehiclePage.setCph(cph.trim());
//			gpsVehiclePage.setPage(page);
//			gpsVehiclePage.setPagesize(pagesize);
//			gpsVehiclePage.setShiyongxingzhi(shiyongxingzhi);
//			String url = "";
//			if (page < 1 && pagesize < 1) {
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai();
//			} else {
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize();
//			}
//
//			if(!StringUtils.isBlank(gpsVehiclePage.getCph())){
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize()+ "&cph=" + gpsVehiclePage.getCph();
//			}
//
//			if(!StringUtils.isBlank(gpsVehiclePage.getDeptname())){
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize();
//			}
//
//			if(!StringUtils.isBlank(gpsVehiclePage.getShiyongxingzhi())){
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize() + "&shiyongxingzhi=" + gpsVehiclePage.getShiyongxingzhi();
//			}
//
//			if(!StringUtils.isBlank(gpsVehiclePage.getCph()) && !StringUtils.isBlank(gpsVehiclePage.getDeptname())){
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize()+ "&cph=" + gpsVehiclePage.getCph();
//			}
//
//			if(!StringUtils.isBlank(gpsVehiclePage.getCph()) && !StringUtils.isBlank(gpsVehiclePage.getShiyongxingzhi())){
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize()+ "&cph=" + gpsVehiclePage.getCph()+ "&shiyongxingzhi=" + gpsVehiclePage.getShiyongxingzhi();
//			}
//
//			if(!StringUtils.isBlank(gpsVehiclePage.getShiyongxingzhi()) && !StringUtils.isBlank(gpsVehiclePage.getDeptname())){
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + "&shiyongxingzhi=" + gpsVehiclePage.getShiyongxingzhi();
//			}
//
//			if(!StringUtils.isBlank(gpsVehiclePage.getCph()) && !StringUtils.isBlank(gpsVehiclePage.getDeptname()) && !StringUtils.isBlank(gpsVehiclePage.getShiyongxingzhi())){
//				url = fileServer.getGpsVehiclePath() + "/GetList?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&zhuangtai=" + gpsVehiclePage.getZhuangtai() + "&page=" + gpsVehiclePage.getPage() + "&size=" + gpsVehiclePage.getPagesize()+ "&cph=" + gpsVehiclePage.getCph() + "&shiyongxingzhi=" + gpsVehiclePage.getShiyongxingzhi();
//			}
//
//			String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
//			JSONObject jsonArrays = JSONArray.parseObject(jsonstr);
//			return R.data(jsonArrays);
//		}
//	}

	@GetMapping(value = "/getVehicleList")
	@ApiLog("政府-获取统计车辆数据")
	@ApiOperation(value = "政府-获取统计车辆数据", notes = "传入相关参数",position = 1)
	public R getVehicleList(
			@ApiParam(value = "企业ID", required = true) @RequestParam String dept,
			@ApiParam(value = "在线状态（1:全部;2:上线;3:未上线;）") @RequestParam int zaixian,
			@ApiParam(value = "车辆状态（1:全部;2:监控车辆;3:停用;4:在用;）", required = false) @RequestParam int zhuangtai,
			@ApiParam(value = "第几页(从1开始)", required = false) @RequestParam int page,
			@ApiParam(value = "每页条数", required = false) @RequestParam int pagesize,
			@ApiParam(value = "车辆牌照", required = false) @RequestParam String cph,
			@ApiParam(value = "使用性质", required = false) @RequestParam String shiyongxingzhi,
			@ApiParam(value = "企业名称", required = false) @RequestParam String deptname,
			@ApiParam(value = "运营商名称", required = false) @RequestParam String yunyingshang) throws IOException {
		R rs = new R();
		VehiclePTPage vehiclePTPage = new VehiclePTPage();
		Integer date = fileServer.getMaxOfflineTime();
		vehiclePTPage.setDate(date);
		if(zaixian == 2){
			vehiclePTPage.setZaixian("在线");
		}
		if(zaixian == 3){
			vehiclePTPage.setZaixian("离线");
		}
		if(zhuangtai == 3){
			vehiclePTPage.setZhuangtai("停用");
		}
		if(zhuangtai == 4){
			vehiclePTPage.setZhuangtai("在用");
		}
		vehiclePTPage.setSize(page);
		vehiclePTPage.setCurrent(page);
		vehiclePTPage.setSize(pagesize);
		vehiclePTPage.setCpn(cph);
		vehiclePTPage.setShiyongxingzhi(shiyongxingzhi);
		vehiclePTPage.setDeptname(deptname);
		vehiclePTPage.setYunyingshang(yunyingshang);
		Organization jb = iOrganizationService.selectGetZFJB(dept);
		VehiclePTPage<ZFTpvehData> zfTpvehdataAllPage= null;
		if(!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())){
			vehiclePTPage.setProvince(dept);
			zfTpvehdataAllPage = iGpsPointDataClient.selectZFTpvehdataAllPage(vehiclePTPage);
			if(zfTpvehdataAllPage !=null){
				rs.setData(zfTpvehdataAllPage);
				rs.setCode(200);
				rs.setMsg("获取成功");
			}else{
				rs.setData("");
				rs.setCode(200);
				rs.setMsg("获取成功,暂无数据");
			}
		}

		if(!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())){
			vehiclePTPage.setCity(dept);
			zfTpvehdataAllPage = iGpsPointDataClient.selectZFTpvehdataAllPage(vehiclePTPage);
			if(zfTpvehdataAllPage !=null){
				rs.setData(zfTpvehdataAllPage);
				rs.setCode(200);
				rs.setMsg("获取成功");
			}else{
				rs.setData("");
				rs.setCode(200);
				rs.setMsg("获取成功,暂无数据");
			}
		}

		if(!StringUtils.isBlank(jb.getCountry())){
			vehiclePTPage.setCountry(dept);
			zfTpvehdataAllPage = iGpsPointDataClient.selectZFTpvehdataAllPage(vehiclePTPage);
			if(zfTpvehdataAllPage !=null){
				rs.setData(zfTpvehdataAllPage);
				rs.setCode(200);
				rs.setMsg("获取成功");
			}else{
				rs.setData("");
				rs.setCode(200);
				rs.setMsg("获取成功,暂无数据");
			}
		}
		return rs;
	}

	@GetMapping(value = "/getQYVehicleList")
	@ApiLog("企业-获取统计车辆数据")
	@ApiOperation(value = "企业-获取统计车辆数据", notes = "传入相关参数",position = 1)
	public R getQYVehicleList(@ApiParam(value = "企业ID", required = true) @RequestParam String dept, @ApiParam(value = "在线状态（1:全部;2:运行车辆;3:停置车辆;4:离线车辆;）") @RequestParam int zaixian, @ApiParam(value = "第几页(从1开始)", required = false) @RequestParam int page, @ApiParam(value = "每页条数", required = false) @RequestParam int pagesize, @ApiParam(value = "车辆牌照", required = false) @RequestParam String cph) throws IOException {
		GpsVehiclePage gpsVehiclePage = new GpsVehiclePage();
		gpsVehiclePage.setDept(dept);
		gpsVehiclePage.setZaixian(zaixian);
		gpsVehiclePage.setPage(page);
		gpsVehiclePage.setPagesize(pagesize);
		gpsVehiclePage.setCph(cph);
		String url="";
		if(page < 1 && pagesize < 1){
			url = fileServer.getGpsVehiclePath() + "/SpreadLocation?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian();
		}else{
			url = fileServer.getGpsVehiclePath() + "/SpreadLocation?dept=" + gpsVehiclePage.getDept() + "&zaixian=" + gpsVehiclePage.getZaixian() + "&page=" + gpsVehiclePage.getPage()+ "&size=" + gpsVehiclePage.getPagesize()+"&cph="+gpsVehiclePage.getCph().getBytes("utf-8");
		}
		String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
////		jsonstr = new String(jsonstr.getBytes(),"UTF-8");
////		jsonstr = new String(jsonstr.getBytes(),"GB2312");
//		JSONObject jsonArrays =  JSONArray.parseObject(jsonstr);
//		return R.data(jsonArrays);

		JSONObject jsonObject = JSONObject.parseObject(jsonstr);
		List<Map<String,Object>> mapListJson = (List)jsonObject.getJSONArray("data");
		for(int i = 0;i<mapListJson.size();i++){
			double lat = Double.parseDouble(mapListJson.get(i).get("Latitude").toString());
			double lon = Double.parseDouble(mapListJson.get(i).get("Longitude").toString());
			double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
			mapListJson.get(i).put("latitude",zuobiao[0]);
			mapListJson.get(i).put("longitude",zuobiao[1]);
		}
		return R.data(mapListJson);
	}

//	@GetMapping(value = "/getQYVehicleCount")
//	@ApiLog("企业-获取实时车辆状态统计")
//	@ApiOperation(value = "企业-获取实时车辆状态统计", notes = "传入相关参数",position = 1)
//	public R getQYVehicleCount(@ApiParam(value = "企业ID") @RequestParam String dept) throws IOException {
//		GpsVehiclePage gpsVehiclePage = new GpsVehiclePage();
//		gpsVehiclePage.setDept(dept);
//		String url = fileServer.getGpsVehiclePath() + "/GetMonitorInfoCo?dept=" + gpsVehiclePage.getDept() ;
//		String jsonstr = InterfaceUtil.getUrlData(url.replace(" ", "%20"));
//////		jsonstr = new String(jsonstr.getBytes(),"UTF-8");
//////		jsonstr = new String(jsonstr.getBytes(),"GB2312");
////		JSONObject jsonArrays =  JSONArray.parseObject(jsonstr);GetMonitorInfo
//		JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonstr);
//		return R.data(jsonArray);
//
////		JSONObject jsonObject = JSONObject.parseObject(jsonstr);
////		List<Map<String,Object>> mapListJson = (List)jsonObject.getJSONArray("data");
////		for(int i = 0;i<mapListJson.size();i++){
////			double lat = Double.parseDouble(mapListJson.get(i).get("Latitude").toString());
////			double lon = Double.parseDouble(mapListJson.get(i).get("Longitude").toString());
////			double[] zuobiao = GpsToBaiduUtil.wgs2bd(lat,lon);
////			mapListJson.get(i).put("latitude",zuobiao[0]);
////			mapListJson.get(i).put("longitude",zuobiao[1]);
////		}
////		return R.data(mapListJson);
//	}

	@GetMapping(value = "/getQYVehicleCount")
	@ApiLog("企业-获取实时车辆状态统计")
	@ApiOperation(value = "企业-获取实时车辆状态统计", notes = "传入相关参数",position = 1)
	public R getQYVehicleCount(@ApiParam(value = "企业ID") @RequestParam String dept){
		R rs = new R();
		Integer date = fileServer.getMaxOfflineTime();
		TpvehDataCount tpvehData = iGpsPointDataClient.selectTpvehdataCount(Integer.parseInt(dept),date);
		if (tpvehData == null){
			rs.setData("");
			rs.setSuccess(true);
			rs.setCode(200);
			rs.setMsg("获取成功，暂无数据");
		}else{
			rs.setData(tpvehData);
			rs.setSuccess(true);
			rs.setCode(200);
			rs.setMsg("获取成功");
		}
		return rs;
	}

}

