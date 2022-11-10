package org.springblade.anbiao.cheliangguanli.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.page.VehiclePage;
import org.springblade.anbiao.cheliangguanli.service.*;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.anbiao.configure.entity.Configure;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IOrganizationsClient;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.common.tool.CheckPhoneUtil;
import org.springblade.common.tool.DateUtils;
import org.springblade.common.tool.JSONUtils;
import org.springblade.common.tool.RegexUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Dict;
import org.springblade.system.feign.IDictClient;
import org.springblade.system.feign.ISysClient;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :hyp
 * @program : SafetyStandards
 * @description: VehicleController
 * @create 2021-04-22 17:50
 */
@RestController
@RequestMapping("/anbiao/vehicle")
@AllArgsConstructor
@Slf4j
@Api(value = "车辆资料管理", tags = "车辆资料管理")
public class VehicleController {
    private IVehicleService vehicleService;
    private IConfigureService mapService;
	private IFileUploadClient fileUploadClient;
	private IJiaShiYuanService iJiaShiYuanService;
	private ISysClient iSysClient;
	private IDictClient iDictClient;
	private IVehiclePhoneService vehiclePhoneService;
	private IVehicleBiangengjiluService vehicleBiangengjiluService;
	private IOrganizationsClient orrganizationsClient;
	private IVehicleDaoluyunshuzhengService daoluyunshuzhengService;
	private IVehicleXingshizhengService xingshizhengService;
	private IVehicleXingnengbaogaoService xingnengbaogaoService;
	private IVehicleJingyingxukezhengService jingyingxukezhengService;
	private IVehicleDengjizhengshuService dengjizhengshuService;
	private IVehicleJishupingdingService jishupingdingService;
	private IVehicleHegezhengService hegezhengService;

    @PostMapping("/list")
	@ApiLog("分页-车辆资料管理")
    @ApiOperation(value = "分页-车辆资料管理", notes = "传入VehiclePage", position = 1)
    public R<VehiclePage<VehicleVO>> list(@RequestBody VehiclePage vehiclepage) {
		vehiclepage.setCheliangleixing("2");
        VehiclePage<VehicleVO> pages = vehicleService.selectVehiclePage(vehiclepage);
//		List<VehicleVO>  list=pages.getRecords();
//		for (int i = 0; i <list.size() ; i++) {
//			//车辆照片
//			if(StrUtil.isNotEmpty(list.get(i).getCheliangzhaopian()) && list.get(i).getCheliangzhaopian().contains("http") == false){
//				list.get(i).setCheliangzhaopian(fileUploadClient.getUrl(list.get(i).getCheliangzhaopian()));
//			}
//
//			//燃料消耗附件
//			if(StrUtil.isNotEmpty(list.get(i).getRanliaoxiaohaofujian()) && list.get(i).getRanliaoxiaohaofujian().contains("http") == false){
//				list.get(i).setRanliaoxiaohaofujian(fileUploadClient.getUrl(list.get(i).getRanliaoxiaohaofujian()));
//			}
//			//行驶证附件
//			if(StrUtil.isNotEmpty(list.get(i).getXingshifujian()) && list.get(i).getXingshifujian().contains("http") == false){
//				list.get(i).setXingshifujian(fileUploadClient.getUrl(list.get(i).getXingshifujian()));
//			}
//		}
        return R.data(pages);
    }

    @GetMapping("/detail")
	@ApiLog("详情-车辆资料管理")
    @ApiOperation(value = "详情-车辆资料管理", notes = "传入id", position = 2)
    public R<VehicleVO> detail(String id) {
        VehicleVO detail = vehicleService.selectByKey(id);
		//车辆照片
		if(StrUtil.isNotEmpty(detail.getCheliangzhaopian()) && detail.getCheliangzhaopian().contains("http") == false){
			detail.setCheliangzhaopian(fileUploadClient.getUrl(detail.getCheliangzhaopian()));
		}
		//燃料消耗附件
		if(StrUtil.isNotEmpty(detail.getRanliaoxiaohaofujian()) && detail.getRanliaoxiaohaofujian().contains("http") == false){
			detail.setRanliaoxiaohaofujian(fileUploadClient.getUrl(detail.getRanliaoxiaohaofujian()));
		}
		//行驶证附件
		if(StrUtil.isNotEmpty(detail.getXingshifujian()) && detail.getXingshifujian().contains("http") == false){
			detail.setXingshifujian(fileUploadClient.getUrl(detail.getXingshifujian()));
		}
        return R.data(detail);
    }

	@GetMapping("/selectByCL")
	@ApiLog("车牌搜索")
	@ApiOperation(value = "车牌搜索", notes = "传入postId,cheliangpaizhao", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postId", value = "岗位id", required = true),
		@ApiImplicitParam(name = "cheliangpaizhao", value = "车辆牌照", required = false)
	})
	public R<List<VehicleCP>> selectByCL(String postId,String cheliangpaizhao) {
		Dept dept=iSysClient.selectByJGBM("机构",postId);
		List<VehicleCP> detail = vehicleService.selectCL(dept.getId().toString(),cheliangpaizhao);
		return R.data(detail);
	}

	@GetMapping("/selectByCPYS")
	@ApiLog("牌照和颜色-获取数据")
	@ApiOperation(value = "牌照和颜色-获取数据", notes = "传入cheliangpaizhao和chepaiyanse", position = 2)
	public R<Map<String,Object>> selectByCPYS(String cheliangpaizhao,String chepaiyanse) {
		Map<String,Object> map = new HashMap<String,Object>();
		VehicleVO vehicleVO = vehicleService.selectByCPYS(cheliangpaizhao,chepaiyanse);
		//车辆照片
		if(StrUtil.isNotEmpty(vehicleVO.getCheliangzhaopian())){
			vehicleVO.setCheliangzhaopian(fileUploadClient.getUrl(vehicleVO.getCheliangzhaopian()));
		}
		//燃料消耗附件
		if(StrUtil.isNotEmpty(vehicleVO.getRanliaoxiaohaofujian())){
			vehicleVO.setRanliaoxiaohaofujian(fileUploadClient.getUrl(vehicleVO.getRanliaoxiaohaofujian()));
		}
		//行驶证附件
		if(StrUtil.isNotEmpty(vehicleVO.getXingshifujian())){
			vehicleVO.setXingshifujian(fileUploadClient.getUrl(vehicleVO.getXingshifujian()));
		}

		map.put("cheliang",vehicleVO);

//		//根据当前车辆id获取当班驾驶员id
//		CheliangrenyuanbangdingPage Page=new CheliangrenyuanbangdingPage();
//		Page.setDeptId(vehicleVO.getDeptId());
//		Page.setCheliangid(vehicleVO.getId());
//		Page.setShifoudangban("0");
//		CheliangrenyuanbangdingPage<CheliangrenyuanbangdingVO> pages = cheliangrenyuanbangdingService.selectPageList(Page);

//		JiaShiYuan detal=new JiaShiYuan();
//		if(pages!=null){
//			//获取驾驶员信息
//			List<CheliangrenyuanbangdingVO> records = pages.getRecords();
//			if(records != null && records.size()>0){
//				detal=iJiaShiYuanService.selectByIds(records.get(0).getJiashiyuanid());
//			}
//			//照片
//			if(StrUtil.isNotEmpty(detal.getZhaopian())){
//				detal.setZhaopian(fileUploadClient.getUrl(detal.getZhaopian()));
//			}
//			//身份证附件
//			if(StrUtil.isNotEmpty(detal.getShenfenzhengfujian())){
//				detal.setShenfenzhengfujian(fileUploadClient.getUrl(detal.getShenfenzhengfujian()));
//			}addSubtemplateFile
//			//从业证附件
//			if(StrUtil.isNotEmpty(detal.getCongyezhengfujian())){
//				detal.setCongyezhengfujian(fileUploadClient.getUrl(detal.getCongyezhengfujian()));
//			}
//			//驾驶证附件
//			if(StrUtil.isNotEmpty(detal.getJiashizhengfujian())){
//				detal.setJiashizhengfujian(fileUploadClient.getUrl(detal.getJiashizhengfujian()));
//			}
//			//复印件
//			if(StrUtil.isNotEmpty(detal.getFuyinjian())){
//				detal.setFuyinjian(fileUploadClient.getUrl(detal.getFuyinjian()));
//			}
//		}
//
//		map.put("jiashiyuan",detal);
		return R.data(map);
	}

	@PostMapping("/addSave")
	@ApiLog("编辑-车辆资料管理【新版】")
	@ApiOperation(value = "新增-车辆资料管理【新版】", notes = "传入Vehicle", position = 30)
	public R addSave(@RequestBody VehicleInfo v,BladeUser user) {
		R r = new R();

		if(user == null) {
			r.setMsg("未授权");
			r.setCode(500);
			return r;
		}

		VehicleVO vehicleVO = vehicleService.selectCPYS(v.getCheliangpaizhao(),v.getChepaiyanse());
		if(vehicleVO!=null){
			r.setMsg(vehicleVO.getCheliangpaizhao()+"该车已存在");
			r.setCode(500);
			return r;
		}
		String jsonObject = JSONUtils.obj2StringPretty(v);
		Vehicle vehicle = JSONUtils.string2Obj(jsonObject,Vehicle.class);
		vehicle.setCaozuoren(user.getUserName());
		vehicle.setCaozuorenid(user.getUserId());
		vehicle.setCaozuoshijian(LocalDateTime.now());
		vehicle.setCreatetime(LocalDateTime.now());
		vehicle.setJiashiyuanxingming(v.getChezhu());
		vehicle.setJiashiyuandianhua(v.getChezhudianhua());
		if(v.getZhuceriqi() != null && !v.getZhuceriqi().equals("") && v.getZhuceriqi().toString().length() > 0) {
			vehicle.setZhucedengjishijian(v.getZhuceriqi().toString());
		} else {
			vehicle.setZhucedengjishijian(null);
		}

		if(v.getCheliangchuchangriqi() != null && !v.getCheliangchuchangriqi().equals("") && v.getCheliangchuchangriqi().toString().length() > 0) {
			vehicle.setChuchangriqi(v.getCheliangchuchangriqi());
		} else {
			vehicle.setChuchangriqi(null);
		}


		if("".equals(vehicle.getGpsanzhuangshijian())){
			vehicle.setGpsanzhuangshijian(null);
		}

		if(!"".equals(vehicle.getYunyingshang())){
			String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(vehicle.getYunyingshang()));
			vehicle.setYunyingshang(yys);
		}
		String str="1";
//		//登录页
//		if(StringUtil.isNotBlank(vehicle.getCheliangzhaopian())){
//			fileUploadClient.updateCorrelation(vehicle.getCheliangzhaopian(),str);
//		}
		boolean i = vehicleService.save(vehicle);

		if(i==true){
			new Thread(new Runnable() {
				@Override
				public void run() {
					if(v.getCheliangbiangengjilu() != null && v.getCheliangbiangengjilu().size() > 0) {
						for(VehicleBiangengjilu biangengjilu:v.getCheliangbiangengjilu()) {
							biangengjilu.setAvbjVehicleId(vehicle.getId());
							biangengjilu.setAvbjDelete("0");
							biangengjilu.setAvbjCreateByName(user.getUserName());
							biangengjilu.setAvbjCreateByIds(user.getUserId().toString());
							biangengjilu.setAvbjCreateTime(LocalDateTime.now());
							biangengjilu.setAvbjUpdateTime(LocalDateTime.now());
							vehicleBiangengjiluService.save(biangengjilu);
						}
					}

					Organizations dept = orrganizationsClient.selectByDeptId(v.getDeptId().toString());
					VehicleJishupingding jishupingding = new VehicleJishupingding();		//技术评定
					jishupingding.setAvjVehicleIds(vehicle.getId());
					jishupingding.setAvjDelete("0");
					jishupingding.setAvjCreateByName(user.getUserName());
					jishupingding.setAvjCreateByIds(user.getUserId().toString());
					jishupingding.setAvjCreateTime(LocalDateTime.now());
					jishupingdingService.save(jishupingding);

					VehicleDengjizhengshu dengjizhengshu = new VehicleDengjizhengshu();		//登记证书
					dengjizhengshu.setAvdVehicleIds(vehicle.getId());
					dengjizhengshu.setAvdDelete("0");
					dengjizhengshu.setAvdCreateByName(user.getUserName());
					dengjizhengshu.setAvdCreateByIds(user.getUserId().toString());
					dengjizhengshu.setAvdCreateTime(LocalDateTime.now());
					dengjizhengshuService.save(dengjizhengshu);

					VehicleJingyingxukezheng jingyingxukezheng = new VehicleJingyingxukezheng();		//经营许可证
					jingyingxukezheng.setAvjVehicleIds(dept.getId());
					jingyingxukezheng.setAvjOperatorName(dept.getDeptName());
					jingyingxukezheng.setAvjLicenseNo(v.getJingyingxukezhenghao());
					jingyingxukezheng.setAvjBusinessLicense(v.getJingyingxukezhenghao());
					jingyingxukezheng.setAvjOperationDeadline(v.getJyxkzyouxiaoqiEnd());
					jingyingxukezheng.setAvjOperatingLineName(v.getJingyingluxian());
					jingyingxukezheng.setAvjDelete("0");
					jingyingxukezheng.setAvjCreateByName(user.getUserName());
					jingyingxukezheng.setAvjCreateByIds(user.getUserId().toString());
					jingyingxukezheng.setAvjCreateTime(LocalDateTime.now());
					jingyingxukezhengService.save(jingyingxukezheng);

					VehicleXingnengbaogao xingnengbaogao = new VehicleXingnengbaogao();		//性能报告
					xingnengbaogao.setAvxAvIds(vehicle.getId());
					xingnengbaogao.setAvxDelete("0");
					xingnengbaogao.setAvxCreateByName(user.getUserName());
					xingnengbaogao.setAvxCreateByIds(user.getUserId().toString());
					xingnengbaogao.setAvxCreateTime(LocalDateTime.now());
					xingnengbaogaoService.save(xingnengbaogao);

					VehicleXingshizheng xingshizheng = new VehicleXingshizheng();		//行驶证
					xingshizheng.setAvxAvIds(vehicle.getId());
					xingshizheng.setAvxPlateNo(v.getCheliangpaizhao());
					xingshizheng.setAvxVehicleType(v.getCheliangleixing());
					xingshizheng.setAvxOwner(dept.getDeptName());
					xingshizheng.setAvxAddress("住址");
					xingshizheng.setAvxUseCharter(v.getShiyongxingzhi());
					xingshizheng.setAvxModel(v.getCheliangxinghao());
					xingshizheng.setAvxVin(v.getChejiahao());
					xingshizheng.setAvxEngineNo(v.getFadongjihao());
					xingshizheng.setAvxRegisterDate(v.getZhuceriqi());
					xingshizheng.setAvxIssueDate(v.getZhuceriqi());
					xingshizheng.setAvxAuthorizedSeatingCapacity(Integer.parseInt(v.getHedingzaikeshu()));
					xingshizheng.setAvxTotalMass(Integer.parseInt(v.getZongzhiliang()));
					xingshizheng.setAvxApprovedLoadCapacity(Integer.parseInt(v.getHedingzaizhiliang()));
					xingshizheng.setAvxCurbWeight(0);		//整备质量
					xingshizheng.setAvxOverallDimensions(v.getWaikuochicun());
					xingshizheng.setAvxQuasiTractiveMass(Integer.parseInt(v.getZhunqianyinzongzhiliang()));
					xingshizheng.setAvxDelete("0");
					xingshizheng.setAvxCreateByName(user.getUserName());
					xingshizheng.setAvxCreateByIds(user.getUserId().toString());
					xingshizheng.setAvxCreateTime(LocalDateTime.now());
					xingshizhengService.save(xingshizheng);


					VehicleDaoluyunshuzheng daoluyunshuzheng = new VehicleDaoluyunshuzheng(); //道路运输证
					daoluyunshuzheng.setAvdAvIds(vehicle.getId());
					daoluyunshuzheng.setAvdBusinessOwner(dept.getDeptName());
					daoluyunshuzheng.setAvdPlateNo(v.getCheliangpaizhao());
					daoluyunshuzheng.setAvdVehicleType(v.getCheliangleixing());
					daoluyunshuzheng.setAvdTonnage(v.getHedingzaikeshu());
					if(StringUtils.isNotEmpty(v.getWaikuochicun())) {
						daoluyunshuzheng.setAvdVehicleLong(Integer.parseInt(v.getWaikuochicun().split("X")[0].toString()));
						daoluyunshuzheng.setAvdVehicleWide(Integer.parseInt(v.getWaikuochicun().split("X")[1].toString()));
						daoluyunshuzheng.setAvdVehicleHigh(Integer.parseInt(v.getWaikuochicun().split("X")[2].toString()));
					}

					daoluyunshuzheng.setAvdRoadTransportCertificateNo(v.getDaoluyunshuzhenghao());
					daoluyunshuzheng.setAvdIssueDate(v.getDlyszyouxiaoqiStart());
					daoluyunshuzheng.setAvdValidUntil(v.getDlyszyouxiaoqiEnd());
					daoluyunshuzheng.setAvdPlateColor(v.getChepaiyanse());
					daoluyunshuzheng.setAvdBusinessLicenseNo(v.getDaoluyunshuzhenghao());
					daoluyunshuzheng.setAvdEconomicType(v.getJingjileixing());
					daoluyunshuzheng.setAvdOperationOrganizationMode(v.getJingyingzuzhifangshi());
					daoluyunshuzheng.setAvdNatureBusiness(v.getJingyingfanwei());
					daoluyunshuzheng.setAvdDelete("0");
					daoluyunshuzheng.setAvdCreateByName(user.getUserName());
					daoluyunshuzheng.setAvdCreateByIds(user.getUserId().toString());
					daoluyunshuzheng.setAvdCreateTime(LocalDateTime.now());
					daoluyunshuzhengService.save(daoluyunshuzheng);

				}
			}).start();
			r.setMsg("新增成功");
			r.setCode(200);
		}else{
			r.setMsg("新增失败");
			r.setCode(500);
		}
		return r;
	}

	@PostMapping("/updateSave")
	@ApiLog("编辑-车辆资料管理【新版】")
	@ApiOperation(value = "编辑-车辆资料管理【新版】", notes = "传入Vehicle", position = 31)
	public R updateSave(@RequestBody VehicleInfo v,BladeUser user) {
    	R r = new R();

    	if(user == null) {
    		r.setMsg("未授权");
    		r.setCode(500);
			return r;
		}

		VehicleVO vehicleVO = vehicleService.selectCPYS(v.getCheliangpaizhao(),v.getChepaiyanse());
		if(vehicleVO!=null){
			r.setMsg(vehicleVO.getCheliangpaizhao()+"该车已存在");
			r.setCode(500);
			return r;
		}
		String jsonObject = JSONUtils.obj2StringPretty(v);
		Vehicle vehicle = JSONUtils.string2Obj(jsonObject,Vehicle.class);
		vehicle.setCaozuoren(user.getUserName());
		vehicle.setCaozuorenid(user.getUserId());
		vehicle.setCaozuoshijian(LocalDateTime.now());
		vehicle.setCreatetime(LocalDateTime.now());
		if("".equals(v.getZhuceriqi())){
			vehicle.setZhucedengjishijian(null);
		} else {
			vehicle.setZhucedengjishijian(v.getZhuceriqi().toString());
		}
		if("".equals(v.getCheliangchuchangriqi())){
			vehicle.setChuchangriqi(null);
		} else {
			vehicle.setChuchangriqi(v.getCheliangchuchangriqi());
		}
		if("".equals(vehicle.getGpsanzhuangshijian())){
			vehicle.setGpsanzhuangshijian(null);
		}

		if(!"".equals(vehicle.getYunyingshang())){
			String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(vehicle.getYunyingshang()));
			vehicle.setYunyingshang(yys);
		}
//		String str="1";
//		//登录页
//		if(StringUtil.isNotBlank(vehicle.getCheliangzhaopian())){
//			fileUploadClient.updateCorrelation(vehicle.getCheliangzhaopian(),str);
//		}
		boolean i = vehicleService.updateById(vehicle);
		if(i==true){
			new Thread(new Runnable() {
				@Override
				public void run() {
					if(v.getCheliangbiangengjilu() != null && v.getCheliangbiangengjilu().size() > 0) {
						for(VehicleBiangengjilu biangengjilu:v.getCheliangbiangengjilu()) {
							biangengjilu.setAvbjVehicleId(vehicle.getId());
							biangengjilu.setAvbjDelete("0");
							biangengjilu.setAvbjCreateByName(user.getUserName());
							biangengjilu.setAvbjCreateByIds(user.getUserId().toString());
							biangengjilu.setAvbjCreateTime(LocalDateTime.now());
							vehicleBiangengjiluService.save(biangengjilu);
						}
					}

					Organizations dept = orrganizationsClient.selectByDeptId(v.getDeptId().toString());
					VehicleJishupingding jishupingding = jishupingdingService.selectVehicleJishupingdingByVehicleIds(v.getId());		//技术评定
					jishupingding.setAvjUpdateByName(user.getUserName());
					jishupingding.setAvjUpdateByIds(user.getUserId().toString());
					jishupingding.setAvjUpdateTime(LocalDateTime.now());
					jishupingdingService.updateById(jishupingding);

					VehicleDengjizhengshu dengjizhengshu = dengjizhengshuService.selectVehicleDengjizhengshuByVehicleIds(v.getId());		//登记证书
					dengjizhengshu.setAvdUpdateByName(user.getUserName());
					dengjizhengshu.setAvdUpdateByIds(user.getUserId().toString());
					dengjizhengshu.setAvdUpdateTime(LocalDateTime.now());
					dengjizhengshuService.updateById(dengjizhengshu);

					VehicleJingyingxukezheng jingyingxukezheng = jingyingxukezhengService.selectVehicleJingyingxukezhengByVehicleIds(v.getId());		//经营许可证
					jingyingxukezheng.setAvjVehicleIds(dept.getId());
					jingyingxukezheng.setAvjOperatorName(dept.getDeptName());
					jingyingxukezheng.setAvjLicenseNo(v.getJingyingxukezhenghao());
					jingyingxukezheng.setAvjBusinessLicense(v.getJingyingxukezhenghao());
					jingyingxukezheng.setAvjOperationDeadline(v.getJyxkzyouxiaoqiEnd());
					jingyingxukezheng.setAvjOperatingLineName(v.getJingyingluxian());
					jingyingxukezheng.setAvjUpdateByName(user.getUserName());
					jingyingxukezheng.setAvjUpdateByIds(user.getUserId().toString());
					jingyingxukezheng.setAvjUpdateTime(LocalDateTime.now());
					jingyingxukezhengService.updateById(jingyingxukezheng);

					VehicleXingnengbaogao xingnengbaogao = xingnengbaogaoService.selectVehicleXingnengbaogaoByVehicleIds(v.getId());		//性能报告
					xingnengbaogao.setAvxUpdateByName(user.getUserName());
					xingnengbaogao.setAvxUpdateByIds(user.getUserId().toString());
					xingnengbaogao.setAvxUpdateTime(LocalDateTime.now());
					xingnengbaogaoService.updateById(xingnengbaogao);

					VehicleXingshizheng xingshizheng = xingshizhengService.selectVehicleJishupingdingByVehicleIds(v.getId());		//行驶证
					xingshizheng.setAvxPlateNo(v.getCheliangpaizhao());
					xingshizheng.setAvxVehicleType(v.getCheliangleixing());
					xingshizheng.setAvxOwner(dept.getDeptName());
					xingshizheng.setAvxAddress("住址");
					xingshizheng.setAvxUseCharter(v.getShiyongxingzhi());
					xingshizheng.setAvxModel(v.getCheliangxinghao());
					xingshizheng.setAvxVin(v.getChejiahao());
					xingshizheng.setAvxEngineNo(v.getFadongjihao());
					xingshizheng.setAvxRegisterDate(v.getZhuceriqi());
					xingshizheng.setAvxIssueDate(v.getZhuceriqi());
					xingshizheng.setAvxAuthorizedSeatingCapacity(Integer.parseInt(v.getHedingzaikeshu()));
					xingshizheng.setAvxTotalMass(Integer.parseInt(v.getZongzhiliang()));
					xingshizheng.setAvxApprovedLoadCapacity(Integer.parseInt(v.getHedingzaizhiliang()));
					xingshizheng.setAvxCurbWeight(0);		//整备质量
					xingshizheng.setAvxOverallDimensions(v.getWaikuochicun());
					xingshizheng.setAvxQuasiTractiveMass(Integer.parseInt(v.getZhunqianyinzongzhiliang()));
					xingshizheng.setAvxUpdateByName(user.getUserName());
					xingshizheng.setAvxUpdateByIds(user.getUserId().toString());
					xingshizheng.setAvxUpdateTime(LocalDateTime.now());
					xingshizhengService.updateById(xingshizheng);


					VehicleDaoluyunshuzheng daoluyunshuzheng = daoluyunshuzhengService.selectVehicleDaoluyunshuzhengByVehicleIds(v.getId()); //道路运输证
					daoluyunshuzheng.setAvdBusinessOwner(dept.getDeptName());
					daoluyunshuzheng.setAvdPlateNo(v.getCheliangpaizhao());
					daoluyunshuzheng.setAvdVehicleType(v.getCheliangleixing());
					daoluyunshuzheng.setAvdTonnage(v.getHedingzaikeshu());
					if(StringUtils.isNotEmpty(v.getWaikuochicun())) {
						daoluyunshuzheng.setAvdVehicleLong(Integer.parseInt(v.getWaikuochicun().split("X")[0].toString()));
						daoluyunshuzheng.setAvdVehicleWide(Integer.parseInt(v.getWaikuochicun().split("X")[1].toString()));
						daoluyunshuzheng.setAvdVehicleHigh(Integer.parseInt(v.getWaikuochicun().split("X")[2].toString()));
					}

					daoluyunshuzheng.setAvdRoadTransportCertificateNo(v.getDaoluyunshuzhenghao());
					daoluyunshuzheng.setAvdIssueDate(v.getDlyszyouxiaoqiStart());
					daoluyunshuzheng.setAvdValidUntil(v.getDlyszyouxiaoqiEnd());
					daoluyunshuzheng.setAvdPlateColor(v.getChepaiyanse());
					daoluyunshuzheng.setAvdBusinessLicenseNo(v.getDaoluyunshuzhenghao());
					daoluyunshuzheng.setAvdEconomicType(v.getJingjileixing());
					daoluyunshuzheng.setAvdOperationOrganizationMode(v.getJingyingzuzhifangshi());
					daoluyunshuzheng.setAvdNatureBusiness(v.getJingyingfanwei());
					daoluyunshuzheng.setAvdUpdateByName(user.getUserName());
					daoluyunshuzheng.setAvdUpdateByIds(user.getUserId().toString());
					daoluyunshuzheng.setAvdUpdateTime(LocalDateTime.now());
					daoluyunshuzhengService.updateById(daoluyunshuzheng);

				}
			}).start();
			r.setMsg("新增成功");
			r.setCode(200);
		}else{
			r.setMsg("新增失败");
			r.setCode(500);
		}
    	return r;
	}

	@PostMapping("/updateDetailedSave")
	@ApiLog("更新-车辆详细档案【新版】")
	@ApiOperation(value = "更新-车辆详细档案【新版】", notes = "传入Vehicle", position = 32)
	public R updateDetailed(@RequestBody VehicleDetailed vd,BladeUser user) {
    	R r = new R();
    	if(!StringUtil.isNotBlank(vd.getVehicleId())) {
    		r.setMsg("请传入车辆主键信息！");
			r.setCode(500);
			return r;
		}

//    	Vehicle vehicle = new Vehicle();
//    	vehicle.setId(vd.getVehicleId());
		Vehicle vehicleVO = vehicleService.getById(vd.getVehicleId());
    	if(vehicleVO == null) {
			r.setMsg("未搜索到您需更新的车辆信息，请查证信息是否正确！");
			r.setCode(500);
			return r;
		}
    	StringBuilder stringBuilder = new StringBuilder();
    	if(vd.getXingshizheng() != null) {
    		VehicleXingshizheng xingshizheng = xingshizhengService.selectVehicleJishupingdingByVehicleIds(vd.getVehicleId());
    		xingshizheng.setAvxFileNo(vd.getXingshizheng().getAvxFileNo());
			xingshizheng.setAvxRegisterDate(vd.getXingshizheng().getAvxRegisterDate());
			xingshizheng.setAvxValidUntil(vd.getXingshizheng().getAvxValidUntil());
			xingshizheng.setAvxOriginalEnclosure(vd.getXingshizheng().getAvxOriginalEnclosure());
			xingshizheng.setAvxCopyEnclosure(vd.getXingshizheng().getAvxCopyEnclosure());
			xingshizheng.setAvxUpdateByName(user.getUserName());
			xingshizheng.setAvxUpdateByIds(user.getUserId().toString());
			xingshizheng.setAvxUpdateTime(LocalDateTime.now());
			if (xingshizhengService.updateById(xingshizheng)) {
				stringBuilder.append("更新车辆行驶证信息成功！"+"\r\n");
			} else {
				stringBuilder.append("更新车辆行驶证信息失败！"+"\r\n");
			}
		}

    	if(vd.getDaoluyunshuzheng() != null) {

			String jsonObject = JSONUtils.obj2StringPretty(vd.getDaoluyunshuzheng());
			VehicleDaoluyunshuzheng dlysz = JSONUtils.string2Obj(jsonObject,VehicleDaoluyunshuzheng.class);

			VehicleDaoluyunshuzheng vdlysz = new VehicleDaoluyunshuzheng();
			vdlysz.setAvdAvIds(vd.getVehicleId());
			vdlysz.setAvdDelete("0");

    		VehicleDaoluyunshuzheng daoluyunshuzheng = daoluyunshuzhengService.getOne(Condition.getQueryWrapper(vdlysz));
    		if(daoluyunshuzheng != null) {
				dlysz.setAvdUpdateByName(user.getUserName());
				dlysz.setAvdUpdateByIds(user.getUserId().toString());
				dlysz.setAvdUpdateTime(LocalDateTime.now());
			} else {
				dlysz.setAvdCreateByName(user.getUserName());
				dlysz.setAvdCreateByIds(user.getUserId().toString());
				dlysz.setAvdCreateTime(LocalDateTime.now());
			}

			if(daoluyunshuzhengService.saveOrUpdate(dlysz)) {
				stringBuilder.append("更新道路运输证信息成功！"+"\r\n");
			} else {
				stringBuilder.append("更新道路运输证信息失败！"+"\r\n");
			}
		}
    	if(vd.getXingnengbaogao() != null) {
    		VehicleXingnengbaogao xingnengbaogao = xingnengbaogaoService.selectVehicleXingnengbaogaoByVehicleIds(vd.getVehicleId());
    		xingnengbaogao.setAvxEnclosure(vd.getXingnengbaogao().getAvxEnclosure());
			xingnengbaogao.setAvxUpdateByName(user.getUserName());
			xingnengbaogao.setAvxUpdateByIds(user.getUserId().toString());
			xingnengbaogao.setAvxUpdateTime(LocalDateTime.now());
			if(xingnengbaogaoService.updateById(xingnengbaogao)) {
				stringBuilder.append("更新性能报告成功！"+"\r\n");
			} else {
				stringBuilder.append("更新性能报告失败！"+"\r\n");
			}
		}

		if(vd.getJishupingding() != null) {
			VehicleJishupingding jishupingding = jishupingdingService.selectVehicleJishupingdingByVehicleIds(vd.getVehicleId());
			jishupingding.setAvjEnclosure(vd.getXingnengbaogao().getAvxEnclosure());
			jishupingding.setAvjUpdateByName(user.getUserName());
			jishupingding.setAvjUpdateByIds(user.getUserId().toString());
			jishupingding.setAvjUpdateTime(LocalDateTime.now());
			if(jishupingdingService.updateById(jishupingding)) {
				stringBuilder.append("更新技术评定报告成功！"+"\r\n");
			} else {
				stringBuilder.append("更新技术评定报告失败！"+"\r\n");
			}
		}

		if(vd.getDengjizhengshu() != null) {
			VehicleDengjizhengshu dengjizhengshu = dengjizhengshuService.selectVehicleDengjizhengshuByVehicleIds(vd.getVehicleId());
			dengjizhengshu.setAvdEnclosure(vd.getXingnengbaogao().getAvxEnclosure());
			dengjizhengshu.setAvdUpdateByName(user.getUserName());
			dengjizhengshu.setAvdUpdateByIds(user.getUserId().toString());
			dengjizhengshu.setAvdUpdateTime(LocalDateTime.now());
			if(dengjizhengshuService.updateById(dengjizhengshu)) {
				stringBuilder.append("更新登记证书成功！"+"\r\n");
			} else {
				stringBuilder.append("更新登记证书失败！"+"\r\n");
			}
		}
		r.setMsg(stringBuilder.toString());
		r.setCode(200);
    	return r;
	}

    @PostMapping("/insert")
	@ApiLog("新增-车辆资料管理")
    @ApiOperation(value = "新增-车辆资料管理", notes = "传入Vehicle", position = 3)
    public R insert(@RequestBody Vehicle vehicle,BladeUser user) {
		R r = new R();

		if(user == null) {
			r.setMsg("未授权");
			r.setCode(500);
			return r;
		}

    	VehicleVO vehicleVO = vehicleService.selectCPYS(vehicle.getCheliangpaizhao(),vehicle.getChepaiyanse());
		if(vehicleVO!=null){
			r.setMsg(vehicleVO.getCheliangpaizhao()+"该车已存在");
			r.setCode(500);
			return r;
		}
		VehicleVO vehicleVO1 = vehicleService.selectByZongDuan(vehicle.getZongduanid());
		if(vehicleVO1 !=null ){
			r.setMsg(vehicleVO1.getZongduanid()+"该终端ID已存在");
			r.setCode(500);
			return r;
		}

		vehicle.setCaozuoren(user.getUserName());
		vehicle.setCaozuorenid(user.getUserId());
		vehicle.setCaozuoshijian(LocalDateTime.now());
		vehicle.setCreatetime(LocalDateTime.now());
		if("".equals(vehicle.getRuhushijian())){
			vehicle.setRuhushijian(null);
		}
		if("".equals(vehicle.getZhucedengjishijian())){
			vehicle.setZhucedengjishijian(null);
		}
		if("".equals(vehicle.getGuohushijian())){
			vehicle.setGuohushijian(null);
		}
		if("".equals(vehicle.getTuishishijian())){
			vehicle.setTuishishijian(null);
		}
		if("".equals(vehicle.getQiangzhibaofeishijian())){
			vehicle.setQiangzhibaofeishijian(null);
		}
		if("".equals(vehicle.getChuchangriqi())){
			vehicle.setChuchangriqi(null);
		}
		if("".equals(vehicle.getGpsanzhuangshijian())){
			vehicle.setGpsanzhuangshijian(null);
		}

		if(!"".equals(vehicle.getYunyingshang())){
			String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(vehicle.getYunyingshang()));
			vehicle.setYunyingshang(yys);
		}
		String str="1";
		//登录页
		if(StringUtil.isNotBlank(vehicle.getCheliangzhaopian())){
			fileUploadClient.updateCorrelation(vehicle.getCheliangzhaopian(),str);
		}
		boolean i = vehicleService.save(vehicle);
		if(i==true){
			r.setMsg("新增成功");
			r.setCode(200);
		}else{
			r.setMsg("新增失败");
			r.setCode(500);
		}
        return r;
    }

    @PostMapping("/update")
	@ApiLog("修改-车辆资料管理")
    @ApiOperation(value = "修改-车辆资料管理", notes = "传入Vehicle", position = 4)
    public R update(@RequestBody Vehicle vehicle,BladeUser user) {
		R r = new R();
		VehicleVO vehicleVO = vehicleService.selectCPYS(vehicle.getCheliangpaizhao(),vehicle.getChepaiyanse());
//		if(vehicleVO!=null){
//			r.setMsg(vehicleVO.getCheliangpaizhao()+"该车已存在");
//			r.setCode(500);
//			return r;
//		}
//		VehicleVO vehicleVO1 = vehicleService.selectByZongDuan(vehicle.getZongduanid());
//		if(vehicleVO1 !=null ){
//			r.setMsg(vehicleVO1.getZongduanid()+"该终端ID已存在");
//			r.setCode(500);
//			return r;
//		}

		vehicle.setCaozuoren(user.getUserName());
		vehicle.setCaozuorenid(user.getUserId());
		vehicle.setCaozuoshijian(LocalDateTime.now());
		if("".equals(vehicle.getCreatetime())){
			vehicle.setCreatetime(LocalDateTime.now());
		}
		if("".equals(vehicle.getRuhushijian())){
			vehicle.setRuhushijian("");
		}
		if("".equals(vehicle.getZhucedengjishijian())){
			vehicle.setZhucedengjishijian("");
		}
		if("".equals(vehicle.getGuohushijian())){
			vehicle.setGuohushijian("");
		}
		if("".equals(vehicle.getTuishishijian())){
			vehicle.setTuishishijian("");
		}
		if("".equals(vehicle.getQiangzhibaofeishijian())){
			vehicle.setQiangzhibaofeishijian("");
		}
		if("".equals(vehicle.getChuchangriqi())){
			vehicle.setChuchangriqi("");
		}
		if("".equals(vehicle.getGpsanzhuangshijian())){
			vehicle.setGpsanzhuangshijian("");
		}

		if(!"".equals(vehicle.getYunyingshang())){
			String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(vehicle.getYunyingshang()));
			vehicle.setYunyingshang(yys);
		}
		boolean i = vehicleService.updateById(vehicle);
		if(i==true){
			r.setMsg("编辑成功");
			r.setCode(200);
		}else{
			r.setMsg("编辑失败");
			r.setCode(500);
		}
		return r;
    }

	@PostMapping("/del")
	@ApiLog("删除-车辆资料管理")
	@ApiOperation(value = "删除-车辆资料管理", notes = "传入车辆id", position = 5)
	public R del(@RequestParam String id) {
		R r = new R();
		String[] idsss = id.split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			boolean ss = vehicleService.deleleVehicle(idsss[i]);
			if (ss){
				r.setMsg("删除成功");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("删除失败");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

	@PostMapping("/updateVehicleOutStatus")
	@ApiLog("停用-车辆资料管理")
	@ApiOperation(value = "停用-车辆资料管理", notes = "传入车辆id", position = 15)
	public R updateVehicleOutStatus(@RequestParam String id) {
		R r = new R();
		String[] idsss = id.split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			boolean ss = vehicleService.updateVehicleOutStatus(idsss[i]);
			if (ss){
				r.setMsg("停用成功");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("停用失败");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

	@PostMapping("/updateVehicleSignStatus")
	@ApiLog("启用-车辆资料管理")
	@ApiOperation(value = "启用-车辆资料管理", notes = "传入车辆id", position = 15)
	public R updateVehicleSignStatus(@RequestParam String id) {
		R r = new R();
		String[] idsss = id.split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			boolean ss = vehicleService.updateVehicleSignStatus(idsss[i]);
			if (ss){
				r.setMsg("启用成功");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("启用失败");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

	@PostMapping("/updateVehicleScrapStatus")
	@ApiLog("报废-车辆资料管理")
	@ApiOperation(value = "报废-车辆资料管理", notes = "传入车辆id", position = 16)
	public R updateVehicleScrapStatus(@RequestParam String id) {
		R r = new R();
		String[] idsss = id.split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			boolean ss = vehicleService.updateVehicleScrapStatus(idsss[i]);
			if (ss){
				r.setMsg("启用成功");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("启用失败");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

    /********************************** 配置表 ***********************/
    /**
     * 配置表新增
     */
    @PostMapping("/insertMap")
	@ApiLog("配置表新增-车辆资料管理")
    @ApiOperation(value = "配置表新增-车辆资料管理", notes = "传入biaodancanshu与deptId", position = 6)
    public R insertMap(String biaodancanshu,String deptId) {
		Configure configure=new Configure();
		JSONObject jsonObject = JSONUtil.parseObj(biaodancanshu);
		configure.setLabel(jsonObject.getStr("label"));
		configure.setShujubiaoziduan(jsonObject.getStr("prop"));
		configure.setDeptId(Integer.parseInt(deptId));
		configure.setTableName("anbiao_vehicle_map");
		configure.setBiaodancanshu(biaodancanshu);
		return R.status(mapService.insertMap(configure));
    }

    /**
     * 配置表编辑
     */
    @PostMapping("/updateMap")
	@ApiLog("配置表编辑-车辆资料管理")
    @ApiOperation(value = "配置表编辑-车辆资料管理", notes = "传入biaodancanshu与id", position = 7)
    public R updateMap(String biaodancanshu, String id) {
        Configure configure = new Configure();
        JSONObject jsonObject = JSONUtil.parseObj(biaodancanshu);
		configure.setId(id);
		configure.setLabel(jsonObject.getStr("label"));
		configure.setShujubiaoziduan(jsonObject.getStr("prop"));
		configure.setTableName("anbiao_vehicle_map");
		configure.setBiaodancanshu(biaodancanshu);
        return R.status(mapService.updateMap(configure));
    }

    /**
     * 配置表删除
     */
    @PostMapping("/delMap")
	@ApiLog("配置表删除-车辆资料管理")
    @ApiOperation(value = "配置表删除-车辆资料管理", notes = "传入id", position = 8)
    public R delMap(@ApiParam(value = "主键id", required = true) @RequestParam String id) {
        return R.status(mapService.delMap("anbiao_vehicle_map", id));
    }

    /**
     * @Description: 根据岗位id获取车辆配置模块数据
     * @Param: [deptId]
     * @return: org.springblade.core.tool.api.R<java.util.List>
     * @Author: hyp
     * @date 2021-04-28
     */
    @GetMapping("/listMap")
	@ApiLog("获取车辆配置-车辆资料管理")
    @ApiOperation(value = "获取车辆配置-车辆资料管理", notes = "传入deptId", position = 9)
    public R<JSONArray> listMap(Integer deptId) {
        List<Configure> list1 = mapService.selectMapList("anbiao_vehicle_map", deptId);
        String str = "";
        for (int i = 0; i < list1.size(); i++) {
            //转换成json数据并put id
            JSONObject jsonObject = JSONUtil.parseObj(list1.get(i).getBiaodancanshu());
            jsonObject.put("id", list1.get(i).getId());
            if (!str.equals("")) {
                str = str + "," + jsonObject.toString();
            } else {
                str = jsonObject.toString();
            }
        }
        str = "[" + str + "]";
        JSONArray json = JSONUtil.parseArray(str);
        return R.data(json);
    }
	/**
	 * 车辆资料导入
	 * @author: LH
	 * @date: 2019/8/19 16:23
	 * @param
	 * @return: org.springblade.core.tool.api.R
	 */
	@PostMapping("vehicleImportOne")
	@ApiLog("车辆资料-导入(本企业)")
	@ApiOperation(value = "车辆资料-导入(本企业)", notes = "file", position = 10)
	public  R vehicleImportOne(@RequestParam(value = "file") MultipartFile file,BladeUser user,String DeptId,String DeptName){

		ExcelReader reader = null;
		try {
			reader = ExcelUtil.getReader(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Map<String,Object>> readAll = reader.readAll();
		int index=readAll.size();
		if(index>5000){

			return  R.fail("导入条目不能超过5000条");

		}
		//导入数据成功条数
		int aa=0;
		int bb=0;
		List<Vehicle> vehicles=new ArrayList<Vehicle>();
		boolean b=true;
		for(Map<String,Object> a:readAll){
			aa++;
			Vehicle vehicle=new Vehicle();
			String id=IdUtil.simpleUUID();
			vehicle.setId(id);
			vehicle.setDeptId(Integer.valueOf(DeptId));
			vehicle.setDeptName(DeptName);

			String cheliangpaiz=String.valueOf(a.get("车辆牌照"));
			String chepaiyanse=String.valueOf(a.get("车牌颜色"));
			vehicle.setCheliangpaizhao(cheliangpaiz);
			vehicle.setChepaiyanse(chepaiyanse);
			VehicleVO vehicleVO = vehicleService.selectCPYS(cheliangpaiz,chepaiyanse);
			if(vehicleVO!=null){
				vehicle.setMsg("该车已存在");
				vehicle.setMsg2(false);
				bb++;
			}else{
				vehicle.setMsg2(true);
			}
			vehicle.setShiyongxingzhi(String.valueOf(a.get("使用性质")));
			vehicle.setJiashiyuanid(null);
			vehicle.setChangpai(String.valueOf(a.get("厂牌")));
			vehicle.setXinghao(String.valueOf(a.get("型号")));
			vehicle.setChejiahao(String.valueOf(a.get("车架号")));
			vehicle.setLuntaiguige(String.valueOf(a.get("轮胎规格")));
			vehicle.setCheshenyanse(String.valueOf(a.get("车身颜色")));
			vehicle.setHedingzaike(String.valueOf(a.get("核定载客")));
			vehicle.setYingyunnianxian(null);
			vehicle.setDengjizhengshubianhao(String.valueOf(a.get("车辆登记证书编号")));
			vehicle.setChelianglaiyuan(String.valueOf(a.get("车辆来源")));
			vehicle.setZhucedengjishijian(String.valueOf(a.get("注册登记日期")));
			vehicle.setRuhushijian(null);
			vehicle.setGuohushijian(null);
			vehicle.setTuishishijian(null);
			vehicle.setQiangzhibaofeishijian(String.valueOf(a.get("强制报废日期")));
			vehicle.setJieboyunshuzhenghao(String.valueOf(a.get("接驳运输证号")));
			vehicle.setYuancheliangpaizhao(String.valueOf(a.get("原车辆牌照")));
			vehicle.setCheliangzhuangtai("0");
			vehicle.setCheliangtingfangdiqu(null);
			vehicle.setDanganhao(String.valueOf(a.get("档案号")));
			vehicle.setBeiyongcheliang(String.valueOf(a.get("备用车辆")));
			vehicle.setYunyingshang(null);
			vehicle.setSuoshuchedui(null);
			vehicle.setXingshifujian(null);
			vehicle.setFujian(null);
			vehicle.setFadongjixinghao(String.valueOf(a.get("发动机型号")));
			vehicle.setFadongjihao(String.valueOf(a.get("发动机号")));
			vehicle.setFadongjipailianggonglv(String.valueOf(a.get("排量功率")));
			vehicle.setRanliaoleibie(String.valueOf(a.get("燃料类别")));
			vehicle.setRanyouxiaohao(null);
			vehicle.setPaifangbiaozhun(null);
			vehicle.setZhuanxiangfangshi(String.valueOf(a.get("转向方式")));
			vehicle.setChemenshezhi(String.valueOf(a.get("车门设置")));
			vehicle.setZhouju(String.valueOf(a.get("轴距")));
			vehicle.setChechang(String.valueOf(a.get("车长")));
			vehicle.setChekuan(String.valueOf(a.get("车宽")));
			vehicle.setChegao(String.valueOf(a.get("车高")));
			vehicle.setLuntaishu(null);
			vehicle.setChezhoushu(String.valueOf(a.get("车轴数")));
			vehicle.setGangbantanhuangpianshu(null);
			vehicle.setDipanxinghao(null);
			vehicle.setDonglileixing(null);
			vehicle.setZongzhiliang(String.valueOf(a.get("总质量")));
			vehicle.setZhengbeizhiliang(String.valueOf(a.get("整备质量")));
			vehicle.setLuntaizonglei(null);
			vehicle.setXuanguaxingshi(String.valueOf(a.get("悬挂形式")));
			vehicle.setXingchezhidongfangshi(null);
			vehicle.setZhidongqiqianlun(null);
			vehicle.setZhidongqihoulun(null);
			vehicle.setAbs(String.valueOf(a.get("ABS")));
			vehicle.setKongtiaoxitong(String.valueOf(a.get("空调系统")));
			vehicle.setHuanshuqi(String.valueOf(a.get("缓速器")));
			vehicle.setBiansuxiangxingshi(null);
			vehicle.setZhizhaochangshang(String.valueOf(a.get("制造厂商名称")));
			vehicle.setGouzhishuizhenghao(null);
			vehicle.setChuchangriqi(null);
			vehicle.setLeijilicheng(null);
			vehicle.setZhongduanfuwuqi(null);
//			vehicle.setCheliangdengji(String.valueOf(a.get("车辆等级")));
			vehicle.setWeishengjian(null);
			vehicle.setFadongjipailiang(null);
			vehicle.setCheliangwaikuochicun(null);
			vehicle.setRanliaoxiaohaofujian(null);
			vehicle.setBeizhu(null);
			vehicle.setGpsanzhuangshijian(null);
			vehicle.setZhinenghuaxitong(String.valueOf(a.get("智能化系统")));
			vehicle.setGps(null);
			vehicle.setXingshijiluyi(null);
			vehicle.setZongduanid(null);
			vehicle.setZongduanxinghao(null);
			vehicle.setCheliangzhaopian(null);
//			vehicle.setYunshujiezhi(String.valueOf(a.get("运输介质")));
			vehicle.setCreatetime(LocalDateTime.now());
			if(user!=null){
				vehicle.setCaozuoren(user.getUserName());
				vehicle.setCaozuorenid(user.getUserId());
			}
			vehicle.setCaozuoshijian(LocalDateTime.now());

			vehicles.add(vehicle);

		}
		if(bb>0){
			return  R.data(400, vehicles,"导入失败");
		}else{
			b=vehicleService.saveBatch(vehicles);
			if(b){
				return  R.success("成功导入:"+aa+"条");
			}else{
				return  R.fail("导入失败");
			}
		}
	}

	public static boolean isCarnumberNO(String carnumber) {
		String carnumRegex = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1})";

		if (TextUtils.isEmpty(carnumber)) {
			return false;
		}else {
			boolean ss = carnumber.matches(carnumRegex);
			return ss;
		}
	}

	/**
	 * 车辆信息--验证
	 * @author: elvis
	 * @date: 2020/06/19 10:23
	 * @update: 黄亚平 添加验证
	 * @param
	 * @return: org.springblade.core.tool.api.R
	 */
	@PostMapping("vehicleImport")
	@ApiLog("车辆信息-验证")
	@ApiOperation(value = "车辆信息-验证", notes = "file", position = 10)
	public R vehicleImport(@RequestParam(value = "file") MultipartFile file,BladeUser user,@RequestParam Integer userId,@RequestParam String userName,@RequestParam int type){

		R rs = new R();
		ExcelReader reader = null;
		try {
			reader = ExcelUtil.getReader(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//时间默认格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//验证数据成功条数
		int aa=0;
		//验证数据错误条数
		int bb=0;
		//全局变量，只要一条数据不对，就为false
		boolean isDataValidity=true;
		//错误信息
		String errorStr="";

		List<Map<String,Object>> readAll = reader.readAll();
		if(readAll.size()>5000){
			errorStr+="导入数据超过5000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(500);
			return rs;
		}

		List<Vehicle> vehicles=new ArrayList<Vehicle>();
		if (type == 1){
			for(Map<String,Object> a:readAll){
				aa++;
				Vehicle vehicle=new Vehicle();
				Dept dept;
				String id=IdUtil.simpleUUID();
				vehicle.setId(id);
				String deptname =  String.valueOf(a.get("所属单位"));
				dept = iSysClient.getDeptByName(deptname);
				vehicle.setDeptId(Integer.valueOf(dept.getId()));
				vehicle.setCheliangpaizhao(String.valueOf(a.get("车辆牌照")));
				vehicle.setChepaiyanse(String.valueOf(a.get("车牌颜色")));
				if(StringUtils.isBlank((String) a.get("使用性质"))){
					vehicle.setShiyongxingzhi("");
				}else{
					vehicle.setShiyongxingzhi(String.valueOf(a.get("使用性质")).trim());
				}

				if(StringUtils.isBlank(String.valueOf(a.get("车辆类型")))){
					vehicle.setXinghao("");
				}else{
					vehicle.setXinghao(String.valueOf(a.get("车辆类型")).trim());
				}

				if(StringUtils.isBlank(String.valueOf(a.get("厂牌")))){
					vehicle.setChangpai("");
				}else{
					vehicle.setChangpai(String.valueOf(a.get("厂牌")).trim());
				}

				if(StringUtils.isBlank(String.valueOf(a.get("车架号")))){
					vehicle.setChejiahao("");
				}else{
					vehicle.setChejiahao(String.valueOf(a.get("车架号")).trim());
				}

				if(StringUtils.isBlank(String.valueOf(a.get("4G视频地址")))){
					vehicle.setYunyingshang("");
				}else{
					String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(String.valueOf(a.get("4G视频地址")).trim()));
					vehicle.setYunyingshang(yys);
				}

				if(StringUtils.isBlank(String.valueOf(a.get("运营商名称")))){
					vehicle.setYunyingshangmingcheng("");
				}else{
					vehicle.setYunyingshangmingcheng(String.valueOf(a.get("运营商名称")).trim());
				}

				if(StringUtils.isBlank(String.valueOf(a.get("终端编号")))){
					vehicle.setZongduanid("");
				}else{
					vehicle.setZongduanid(String.valueOf(a.get("终端编号")).trim());
				}

				if(StringUtils.isBlank(String.valueOf(a.get("驾驶员")))){
					vehicle.setJiashiyuanxingming("");
				}else{
					vehicle.setJiashiyuanxingming(String.valueOf(a.get("驾驶员")).trim());
				}
				if(StringUtils.isBlank(String.valueOf(a.get("驾驶员电话")))){
					vehicle.setJiashiyuandianhua("");
				}else{
					vehicle.setJiashiyuandianhua(String.valueOf(a.get("驾驶员电话")).trim());
				}
				if(StringUtils.isBlank(String.valueOf(a.get("押运员")))){
					vehicle.setYayunyuanxingming("");
				}else{
					vehicle.setYayunyuanxingming(String.valueOf(a.get("押运员")).trim());
				}
				if(StringUtils.isBlank(String.valueOf(a.get("押运员电话")))){
					vehicle.setYayunyuandianhua("");
				}else{
					vehicle.setYayunyuandianhua(String.valueOf(a.get("押运员电话")).trim());
				}
				if(StringUtils.isBlank(String.valueOf(a.get("车主")))){
					vehicle.setChezhu("");
				}else{
					vehicle.setChezhu(String.valueOf(a.get("车主")).trim());
				}
				if(StringUtils.isBlank(String.valueOf(a.get("车主电话")))) {
					vehicle.setChezhudianhua("");
				}else{
					vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
				}

				vehicle.setCreatetime(LocalDateTime.now());
				vehicle.setCaozuoshijian(LocalDateTime.now());
				if(user != null){
					vehicle.setCaozuoren(user.getUserName());
					vehicle.setCaozuorenid(user.getUserId());
				}else{
					vehicle.setCaozuoren(userName);
					vehicle.setCaozuorenid(userId);
				}
				vehicle.setCheliangzhuangtai("0");
				vehicles.add(vehicle);
				isDataValidity = vehicleService.insertSelective(vehicle);
			}
			if(isDataValidity == true){
				rs.setCode(200);
				rs.setMsg("数据导入成功");
				rs.setData(vehicles);
				return rs;
			}else{
				rs.setCode(500);
				rs.setMsg("数据导入失败");
				rs.setData(vehicles);
				return rs;
			}
		}else{
			for(Map<String,Object> a:readAll){
				aa++;
				Vehicle vehicle=new Vehicle();
				Dept dept;
				String deptname =  String.valueOf(a.get("所属单位")).trim();
				if(StringUtils.isBlank(deptname)){
					vehicle.setMsg("所属单位不能为空;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
				dept = iSysClient.getDeptByName(deptname.trim());
				if (dept != null){
					vehicle.setDeptId(Integer.valueOf(dept.getId()));
					vehicle.setDeptName(deptname);
					vehicle.setImportUrl("icon_gou.png");

					String cheliangpaiz=String.valueOf(a.get("车辆牌照")).trim();
					if(StringUtils.isBlank(cheliangpaiz)){
						vehicle.setMsg("车辆牌照不能为空;");
						vehicle.setImportUrl("icon_cha.png");
						bb++;
					}
					else{
//						if(isCarnumberNO(cheliangpaiz) == false){
//							vehicle.setMsg("辆牌照格式不正确;");
//							errorStr+=cheliangpaiz+"辆牌照格式不正确;";
//							vehicle.setImportUrl("icon_cha.png");
//							bb++;
//						}else{
//							vehicle.setImportUrl("icon_gou.png");
//						}
						vehicle.setImportUrl("icon_gou.png");
					}
					String chepaiyanse=String.valueOf(a.get("车牌颜色")).trim();
					if(StringUtils.isBlank(chepaiyanse)){
						vehicle.setMsg("车牌颜色不能为空;");
						errorStr+="车牌颜色不能为空;";
						vehicle.setImportUrl("icon_cha.png");
						bb++;
					}else{
						VehicleVO vehicleVO = vehicleService.selectVehicleColor(chepaiyanse);
						if (vehicleVO == null || vehicleVO.getChepaiyanse() == null) {
							vehicle.setMsg("车牌颜色输入错误;");
							errorStr+="车牌颜色输入错误;";
							vehicle.setImportUrl("icon_cha.png");
							bb++;
						}else{
							vehicle.setImportUrl("icon_gou.png");
						}
					}
					vehicle.setCheliangpaizhao(cheliangpaiz);
					vehicle.setChepaiyanse(chepaiyanse);
					VehicleVO vehicleVO = vehicleService.selectCPYS(cheliangpaiz,chepaiyanse);
					if(vehicleVO!=null){
						vehicle.setImportUrl("icon_cha.png");
						errorStr+=vehicleVO.getCheliangpaizhao()+"该车已存在;";
						vehicle.setMsg(vehicleVO.getCheliangpaizhao()+"该车已存在;");
						bb++;
					}else{
						vehicle.setImportUrl("icon_gou.png");
					}
					for(Vehicle item : vehicles){
						if(item.getCheliangpaizhao().equals(cheliangpaiz) && item.getChepaiyanse().equals(chepaiyanse)){
							vehicle.setImportUrl("icon_cha.png");
							errorStr+=cheliangpaiz+"车牌号重复；";
							vehicle.setMsg(cheliangpaiz+"车牌号重复；");
							bb++;
						}
					}
//					if(bb>0){
//						vehicle.setImportUrl("icon_cha.png");
//						errorStr+=cheliangpaiz+"车牌号重复；";
//						vehicle.setMsg(cheliangpaiz+"车牌号重复；");
//						bb++;
//					}

					//验证使用性质
					String shiyongxingzhi = String.valueOf(a.get("使用性质")).trim();
					if(StringUtils.isNotBlank(shiyongxingzhi) && !shiyongxingzhi.equals("null")){
						boolean ss = false;
						List<Dict> dictVOList = iDictClient.getDictByCode("shiyongxingzhi",null);
						for(int i= 0;i<dictVOList.size();i++){
							ss = dictVOList.get(i).getDictValue().contains(shiyongxingzhi);
							if(ss == true){
								break;
							}
						}
						if(ss == true){
							dictVOList = iDictClient.getDictByCode("shiyongxingzhi",shiyongxingzhi);
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setShiyongxingzhi(dictVOList.get(0).getDictKey());
						}else{
							vehicle.setImportUrl("icon_cha.png");
							errorStr += cheliangpaiz+",车辆使用性质输入错误,请校验”;";
							vehicle.setMsg(cheliangpaiz+",车辆使用性质输入错误,请校验;");
							bb++;
						}
					}else{
						vehicle.setImportUrl("icon_cha.png");
						errorStr += cheliangpaiz+",车辆使用性质不能为空,请校验”;";
						vehicle.setMsg(cheliangpaiz+",车辆使用性质不能为空,请校验;");
						bb++;
					}
					vehicle.setXinghao(String.valueOf(a.get("车辆类型")).trim());
					vehicle.setChangpai(String.valueOf(a.get("厂牌")).trim());
					vehicle.setChejiahao(String.valueOf(a.get("车架号")).trim());
					vehicle.setYunyingshangmingcheng(String.valueOf(a.get("运营商名称")).trim());
					String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(String.valueOf(a.get("4G视频地址")).trim()));
					vehicle.setYunyingshang(yys);
					vehicle.setChezhu(String.valueOf(a.get("车主")).trim());

					String zongduanid = String.valueOf(a.get("终端编号")).trim();
					if(zongduanid.length() != 12 && zongduanid.length() != 20){
						vehicle.setImportUrl("icon_cha.png");
						errorStr += zongduanid + "该终端号不是标准终端位（12位/20位）;";
						vehicle.setZongduanid(zongduanid);
						vehicle.setMsg(zongduanid + "该终端号不是标准终端位（12位/20位）;");
						bb++;
					}else {
						VehicleVO vehicleVO1 = vehicleService.selectByZongDuan(zongduanid);
						if (vehicleVO1 != null) {
							vehicle.setImportUrl("icon_cha.png");
							errorStr += zongduanid + "该终端ID已存在;";
							vehicle.setZongduanid(zongduanid);
							vehicle.setMsg(zongduanid + "该终端ID已存在;");
							bb++;
						} else {
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setZongduanid(zongduanid);
						}
					}

					String zhongduanleixing = String.valueOf(a.get("终端类型")).trim();
					if(StringUtils.isBlank(zhongduanleixing)){
						vehicle.setImportUrl("icon_cha.png");
						errorStr = vehicleVO.getCheliangpaizhao()+"终端类型不能为空;";
						vehicle.setMsg(vehicleVO.getCheliangpaizhao() + "终端类型不能为空;");
						bb++;
					}else {
						boolean ss = false;
						List<Dict> dictVOList = iDictClient.getDictByCode("zhongduanleixing",null);
						for(int i= 0;i<dictVOList.size();i++){
							ss = dictVOList.get(i).getDictValue().contains(zhongduanleixing);
							if(ss == true){
								break;
							}
						}
						if(ss == true){
							dictVOList = iDictClient.getDictByCode("zhongduanleixing",zhongduanleixing);
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setZhongduanleixing(Integer.parseInt(dictVOList.get(0).getDictKey()));
						}else{
							vehicle.setImportUrl("icon_cha.png");
							errorStr += zhongduanleixing+"该终端类型异常，应为:“主动安全设备/2G设备”;";
							vehicle.setMsg(zhongduanleixing+"该终端类型异常,应为:“主动安全设备/2G设备”;");
							bb++;
						}
					}

					if(StringUtils.isBlank(String.valueOf(a.get("驾驶员"))) || String.valueOf(a.get("驾驶员")).equals("null")){
						vehicle.setJiashiyuanxingming("");
					}else{
						vehicle.setJiashiyuanxingming(String.valueOf(a.get("驾驶员")).trim());
					}

					if(StringUtils.isBlank(String.valueOf(a.get("押运员"))) || String.valueOf(a.get("押运员")).equals("null")){
						vehicle.setYayunyuanxingming("");
					}else{
						vehicle.setYayunyuanxingming(String.valueOf(a.get("押运员")).trim());
					}

					if(StringUtils.isBlank(String.valueOf(a.get("车主"))) || String.valueOf(a.get("车主")).equals("null")){
						vehicle.setChezhu("");
					}else{
						vehicle.setChezhu(String.valueOf(a.get("车主")).trim());
					}

					String phone = String.valueOf(a.get("驾驶员电话"));
					if(!StringUtils.isBlank(phone) && !phone.equals("null")){
						if(CheckPhoneUtil.isChinaPhoneLegal(phone) == false){
							vehicle.setMsg("驾驶员电话格式不正确;");
							errorStr+=phone+"驾驶员电话格式不正确;";
							vehicle.setImportUrl("icon_cha.png");
							vehicle.setJiashiyuandianhua(String.valueOf(a.get("驾驶员电话")).trim());
							bb++;
						}else{
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setJiashiyuandianhua(String.valueOf(a.get("驾驶员电话")).trim());
						}
					}else{
						vehicle.setJiashiyuandianhua(String.valueOf(a.get("驾驶员电话")).trim());
					}

					String yyphone = String.valueOf(a.get("押运员电话"));
					if(!StringUtils.isBlank(yyphone) && !yyphone.equals("null")){
						if(CheckPhoneUtil.isChinaPhoneLegal(yyphone) == false){
							vehicle.setMsg("押运员电话格式不正确;");
							errorStr+=yyphone+"押运员电话格式不正确;";
							vehicle.setImportUrl("icon_cha.png");
							vehicle.setYayunyuandianhua(String.valueOf(a.get("押运员电话")).trim());
							bb++;
						}else{
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setYayunyuandianhua(String.valueOf(a.get("押运员电话")).trim());
						}
					}else{
						vehicle.setYayunyuandianhua(String.valueOf(a.get("押运员电话")).trim());
					}

					String czphone = String.valueOf(a.get("车主电话"));
					if(!StringUtils.isBlank(czphone) && !czphone.equals("null")){
						if(CheckPhoneUtil.isChinaPhoneLegal(czphone) == false){
							vehicle.setMsg("车主电话格式不正确;");
							errorStr+=czphone+"车主电话格式不正确;";
							vehicle.setImportUrl("icon_cha.png");
							vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
							bb++;
						}else{
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
						}
					}else{
						vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
					}

					vehicles.add(vehicle);
				}else{
					String cph = String.valueOf(a.get("车辆牌照")).trim();
					vehicle.setMsg(deptname+"机构不存在;");
					vehicle.setImportUrl("icon_cha.png");
					errorStr+=cph+"----"+deptname+"机构不存在;";
					bb++;
				}
			}
			if(bb>0){
				rs.setMsg(errorStr);
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setData(vehicles);
				return rs;
			}else{
				rs.setCode(200);
				rs.setMsg("数据验证成功");
				rs.setData(vehicles);
				rs.setSuccess(true);
				return rs;
			}
		}
	}

	/**
	 * 车辆信息--确认导入
	 * @author: elvis
	 * @date: 2020/10/19 10:23
	 * @param
	 * @return: org.springblade.core.tool.api.R
	 */
	@PostMapping("vehicleImportOk")
	@ApiLog("车辆信息-确认导入")
	@ApiOperation(value = "车辆信息-确认导入", notes = "vehicles", position = 10)
	public R vehicleImportOk(@RequestParam(value = "vehicles") String vehicles,BladeUser user,@RequestParam Integer userId,@RequestParam String userName){
		System.out.println("vehicles:"+vehicles);
		JSONArray json = JSONUtil.parseArray(vehicles);
		List<Map<String,Object>> lists = (List)json;
		R rs = new R();
		//时间默认格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//验证数据成功条数
		int aa=0;
		//验证数据错误条数
		int bb=0;
		//全局变量，只要一条数据不对，就为false
		boolean isDataValidity=true;
		//错误信息
		String errorStr="";

		if(lists.size()>5000){
			errorStr+="导入数据超过5000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(400);
			return rs;
		}

		List<Vehicle> vehlist=new ArrayList<Vehicle>();
		for(Map<String,Object> a:lists){
			aa++;
			Vehicle vehicle=new Vehicle();
			Dept dept;
			String id=IdUtil.simpleUUID();
			vehicle.setId(id);
			vehicle.setDeptId(Integer.parseInt(String.valueOf(a.get("deptId")).trim()));
			vehicle.setDeptName(String.valueOf(a.get("deptName")).trim());
			vehicle.setCheliangpaizhao(String.valueOf(a.get("cheliangpaizhao")).trim());
			vehicle.setChepaiyanse(String.valueOf(a.get("chepaiyanse")).trim());
			vehicle.setShiyongxingzhi(String.valueOf(a.get("shiyongxingzhi")).trim());
			if(StringUtils.isNotBlank(String.valueOf(a.get("xinghao")).trim())){
				vehicle.setXinghao(String.valueOf(a.get("xinghao")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("changpai")).trim())){
				vehicle.setChangpai(String.valueOf(a.get("changpai")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("chejiahao")).trim())){
				vehicle.setChejiahao(String.valueOf(a.get("chejiahao")).trim());
			}
			String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(String.valueOf(a.get("yunyingshang")).trim()));
			if(StringUtils.isNotBlank(yys)){
				vehicle.setYunyingshang(yys);
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("yunyingshangmingcheng")).trim())){
				vehicle.setYunyingshangmingcheng(String.valueOf(a.get("yunyingshangmingcheng")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("zongduanid")).trim())){
				vehicle.setZongduanid(String.valueOf(a.get("zongduanid")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("zhongduanleixing")).trim())){
				vehicle.setZhongduanleixing(Integer.parseInt(String.valueOf(a.get("zhongduanleixing")).trim()));
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuanxingming")).trim())){
				vehicle.setJiashiyuanxingming(String.valueOf(a.get("jiashiyuanxingming")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuandianhua")).trim())){
				vehicle.setJiashiyuandianhua(String.valueOf(a.get("jiashiyuandianhua")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("yayunyuanxingming")).trim())){
				vehicle.setYayunyuanxingming(String.valueOf(a.get("yayunyuanxingming")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("yayunyuandianhua")).trim())) {
				vehicle.setYayunyuandianhua(String.valueOf(a.get("yayunyuandianhua")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("chezhu")).trim())) {
				vehicle.setChezhu(String.valueOf(a.get("chezhu")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("chezhudianhua")).trim())) {
				vehicle.setChezhudianhua(String.valueOf(a.get("chezhudianhua")).trim());
			}
			vehicle.setCreatetime(LocalDateTime.now());
			vehicle.setCaozuoshijian(LocalDateTime.now());
			if(user != null){
				vehicle.setCaozuoren(user.getUserName());
				vehicle.setCaozuorenid(user.getUserId());
			}else{
				vehicle.setCaozuoren(userName);
				vehicle.setCaozuorenid(userId);
			}
			vehicle.setCheliangzhuangtai("0");
			vehicle.setIsdel(0);
			vehlist.add(vehicle);
			isDataValidity = vehicleService.insertSelective(vehicle);
			if(StringUtils.isNotBlank(vehicle.getJiashiyuanxingming()) && !vehicle.getJiashiyuanxingming().equals("null") && StringUtils.isNotBlank(vehicle.getJiashiyuandianhua()) && !vehicle.getJiashiyuandianhua().equals("null")){
				JiaShiYuan driver=new JiaShiYuan();
				SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
				String ids = IdUtil.simpleUUID();
				driver.setId(ids);
				driver.setDeptId(vehicle.getDeptId());
				driver.setJiashiyuanxingming(vehicle.getJiashiyuanxingming());
				driver.setShoujihaoma(vehicle.getJiashiyuandianhua());
				driver.setNianling("");
				driver.setJiashiyuanleixing("驾驶员");
				driver.setCongyerenyuanleixing("驾驶员");
				driver.setDenglumima(DigestUtil.encrypt("123456"));
				driver.setIsdelete(0);
				driver.setCreatetime(DateUtil.now());
				driver.setCaozuoshijian(DateUtil.now());
				if(user!=null){
					driver.setCaozuoren(user.getUserName());
					driver.setCaozuorenid(user.getUserId());
				}
				boolean jsy = iJiaShiYuanService.insertSelective(driver);
				//向车辆驾驶员绑定关系表中添加记录
				CheliangJiashiyuan cheliangJiashiyuan = vehicleService.getVehidAndJiashiyuanId(vehicle.getId(),driver.getId());
				if(cheliangJiashiyuan == null){
					CheliangJiashiyuan cheliangJiashiyuan1 = new CheliangJiashiyuan();
					cheliangJiashiyuan1.setDeptId(vehicle.getDeptId());
					cheliangJiashiyuan1.setCreatetime(DateUtil.now());
					cheliangJiashiyuan1.setJiashiyuanid(driver.getId());
					cheliangJiashiyuan1.setVehid(vehicle.getId());
					cheliangJiashiyuan1.setType("1");
					boolean vaj = vehicleService.insertVehidAndJiashiyuan(cheliangJiashiyuan1);
				}
			}
			if(StringUtils.isNotBlank(vehicle.getYayunyuanxingming()) && !vehicle.getYayunyuanxingming().equals("null") && StringUtils.isNotBlank(vehicle.getYayunyuandianhua()) && !vehicle.getYayunyuandianhua().equals("null")){
				JiaShiYuan driver=new JiaShiYuan();
				SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
				String ids = IdUtil.simpleUUID();
				driver.setId(ids);
				driver.setDeptId(vehicle.getDeptId());
				driver.setJiashiyuanxingming(vehicle.getYayunyuanxingming());
				driver.setShoujihaoma(vehicle.getYayunyuandianhua());
				driver.setJiashiyuanleixing("押运员");
				driver.setCongyerenyuanleixing("押运员");
				driver.setNianling("");
				driver.setDenglumima(DigestUtil.encrypt("123456"));
				driver.setIsdelete(0);
				driver.setCreatetime(DateUtil.now());
				driver.setCaozuoshijian(DateUtil.now());
				if(user!=null){
					driver.setCaozuoren(user.getUserName());
					driver.setCaozuorenid(user.getUserId());
				}
				boolean yyy = iJiaShiYuanService.insertSelective(driver);
			}
		}
		if(isDataValidity == true){
			rs.setCode(200);
			rs.setMsg("数据导入成功");
			rs.setData(vehicles);
			return rs;
		}else{
			rs.setCode(500);
			rs.setMsg("数据导入失败");
			rs.setData(vehicles);
			return rs;
		}
	}

	@PostMapping("vehicledetai")
	@ApiLog("车辆详情-车牌-车牌颜色")
	@ApiOperation(value = "车辆详情-车牌-颜色", notes = "传入车牌 车牌颜色", position = 11)
	public  R vehicledetai(@ApiParam(value = "车牌", required = true) @RequestParam String cheliangpaizhao,
						   @ApiParam(value = "车牌颜色", required = true) @RequestParam String chepaiyanse,
						   @ApiParam(value = "deptId", required = true) @RequestParam Integer deptId){
		Vehicle vehicle = vehicleService.vehileOne(cheliangpaizhao, chepaiyanse,deptId);
		//车辆照片
		if(StrUtil.isNotEmpty(vehicle.getCheliangzhaopian())){
			vehicle.setCheliangzhaopian(fileUploadClient.getUrl(vehicle.getCheliangzhaopian()));
		}

		//燃料消耗附件
		if(StrUtil.isNotEmpty(vehicle.getRanliaoxiaohaofujian())){
			vehicle.setRanliaoxiaohaofujian(fileUploadClient.getUrl(vehicle.getRanliaoxiaohaofujian()));
		}
		//行驶证附件
		if(StrUtil.isNotEmpty(vehicle.getXingshifujian())){
			vehicle.setXingshifujian(fileUploadClient.getUrl(vehicle.getXingshifujian()));
		}
		return R.data(vehicle);
	}

	@GetMapping("/getVehicleAll")
	@ApiLog("GPS获取车辆资料")
	@ApiOperation(value = "GPS获取车辆资料", notes = "传入caozuoshijian", position = 2)
	public R<List<VehicleVO>> getVehicleAll(String caozuoshijian) {
		List<VehicleVO> detail = vehicleService.selectVehicleAll(caozuoshijian);
		return R.data(detail);
	}

	@GetMapping("/getVehicleAll_FY")
	@ApiLog("GPS获取车辆资料-阜阳测试")
	@ApiOperation(value = "GPS获取车辆资料-阜阳测试", notes = "传入caozuoshijian", position = 2)
	public R<List<VehicleVO>> getVehicleAll_FY(String caozuoshijian,String deptId) {
		List<VehicleVO> detail = vehicleService.selectVehicleAll_FY(caozuoshijian,deptId);
		return R.data(detail);
	}

	@GetMapping("/getVehicleAll_NFY")
	@ApiLog("GPS获取车辆资料-阜阳测试（除去阜阳数据）")
	@ApiOperation(value = "GPS获取车辆资料-阜阳测试", notes = "传入caozuoshijian", position = 2)
	public R<List<VehicleVO>> getVehicleAll_NFY(String caozuoshijian,String deptId) {
		List<VehicleVO> detail = vehicleService.selectVehicleAll_NFY(caozuoshijian,deptId);
		return R.data(detail);
	}

	@PostMapping("/updateDeptId")
	@ApiLog("车辆资料管理-车辆异动")
	@ApiOperation(value = "车辆资料管理-车辆异动", notes = "传入车辆ID,企业ID", position = 10)
	public R updateDeptId(@RequestParam String id,@RequestParam String deptId) {
		R r = new R();
		String[] idsss = id.split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			boolean ss = vehicleService.updateDeptId(deptId,idsss[i]);
			if (ss){
				r.setMsg("更新成功");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("更新失败");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

	@PostMapping("/selectGDSVehicleList")
	@ApiLog("分页-各地市车辆变更统计列表")
	@ApiOperation(value = "分页-各地市车辆变更统计列表", notes = "传入vehiclepage", position = 11)
	public R<VehiclePage<VehicleGDSTJ>> selectGDSVehicleList(@RequestBody VehiclePage vehiclepage) {
		VehiclePage<VehicleGDSTJ> pages = vehicleService.selectGDSVehiclePage(vehiclepage);
		return R.data(pages);
	}

	@PostMapping("/selectGDSMXVehicleList")
	@ApiLog("分页-各地市车辆变更明细统计列表")
	@ApiOperation(value = "分页-各地市车辆变更明细统计列表", notes = "传入vehiclepage", position = 12)
	public R<VehiclePage<VehicleGDSTJ>> selectGDSMXVehicleList(@RequestBody VehiclePage vehiclepage) {
		VehiclePage<VehicleGDSTJ> pages = vehicleService.selectGDSMXVehiclePage(vehiclepage);
		return R.data(pages);
	}

	@GetMapping("/getByIdVehicleList")
	@ApiLog("根据企业ID获取相应车辆信息")
	@ApiOperation(value = "根据企业ID获取相应车辆信息", notes = "传入deptId", position = 2)
	public R<List<Vehicle>> getByIdVehicleList(Integer deptId) {
		List<Vehicle> detail = vehicleService.vehileList(deptId);
		return R.data(detail);
	}

	@PostMapping("/updateVehicleZongDuanId")
	@ApiLog("车辆资料管理-编辑车辆终端编号")
	@ApiOperation(value = "车辆资料管理-编辑车辆终端编号", notes = "传入车辆id,新终端ID", position = 19)
	public R updateVehicleZongDuanId(@RequestParam String id,String newzongduanid,String username,String userid) {
		R r = new R();
		VehicleVO vehicleVO1 = vehicleService.selectByZongDuan(newzongduanid);
		if(vehicleVO1 !=null ){
			r.setMsg(vehicleVO1.getZongduanid()+"该终端ID已存在");
			r.setCode(500);
			return r;
		}
		boolean ss = vehicleService.updateVehicleZongDuanId(newzongduanid,username,userid,id);
		if (ss){
			r.setMsg("编辑成功");
			r.setCode(200);
			r.setSuccess(true);
		}else{
			r.setMsg("编辑失败");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	/**
	 * 更新车辆信息--验证
	 * @author: elvis
	 * @date: 2020/06/19 10:23
	 * @update: 黄亚平 更新车辆信息
	 * @param
	 */
	@PostMapping("vehicleUpdateImport")
	@ApiLog("更新车辆信息-验证")
	@ApiOperation(value = "更新车辆信息-验证", notes = "file", position = 20)
	public R vehicleUpdateImport(@RequestParam(value = "file") MultipartFile file){

		R rs = new R();
		ExcelReader reader = null;
		try {
			reader = ExcelUtil.getReader(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//时间默认格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//验证数据成功条数
		int aa=0;
		//验证数据错误条数
		int bb=0;
		//全局变量，只要一条数据不对，就为false
		boolean isDataValidity=true;
		//错误信息
		String errorStr="";

		List<Map<String,Object>> readAll = reader.readAll();
		if(readAll.size()>5000){
			errorStr+="导入数据超过5000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(400);
			return rs;
		}

		List<Vehicle> vehicles=new ArrayList<Vehicle>();
			for(Map<String,Object> a:readAll){
				aa++;
				Vehicle vehicle=new Vehicle();
				Dept dept;
				String deptname =  String.valueOf(a.get("所属单位")).trim();
				if(StringUtils.isBlank(deptname)){
					vehicle.setMsg("所属单位不能为空;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
				dept = iSysClient.getDeptByName(deptname.trim());
				if (dept != null){
					vehicle.setDeptId(Integer.valueOf(dept.getId()));
					vehicle.setDeptName(deptname);
					vehicle.setImportUrl("icon_gou.png");

					String cheliangpaiz=String.valueOf(a.get("车辆牌照")).trim();
					cheliangpaiz = RegexUtils.replaceBlank(cheliangpaiz);
					if(StringUtils.isBlank(cheliangpaiz)){
						vehicle.setMsg("车辆牌照不能为空;");
						vehicle.setImportUrl("icon_cha.png");
						bb++;
					}else{
//						if(isCarnumberNO(cheliangpaiz) == false){
//							vehicle.setMsg("辆牌照格式不正确;");
//							errorStr+=cheliangpaiz+"辆牌照格式不正确;";
//							vehicle.setImportUrl("icon_cha.png");
//							bb++;
//						}else{
//							vehicle.setImportUrl("icon_gou.png");
//						}
                        vehicle.setImportUrl("icon_gou.png");
					}
					String chepaiyanse=String.valueOf(a.get("车牌颜色")).trim();
					if(StringUtils.isBlank(chepaiyanse)){
						vehicle.setMsg("车牌颜色不能为空;");
						errorStr+="车牌颜色不能为空;";
						vehicle.setImportUrl("icon_cha.png");
						bb++;
					}else{
						VehicleVO vehicleVO = vehicleService.selectVehicleColor(chepaiyanse);
						if (vehicleVO == null || vehicleVO.getChepaiyanse() == null) {
							vehicle.setMsg("车牌颜色输入错误;");
							errorStr+="车牌颜色输入错误;";
							vehicle.setImportUrl("icon_cha.png");
							bb++;
						}else{
							vehicle.setImportUrl("icon_gou.png");
						}
					}
					vehicle.setCheliangpaizhao(cheliangpaiz);
					vehicle.setChepaiyanse(chepaiyanse);
					VehicleVO vehicleVO = vehicleService.selectCPYS(cheliangpaiz,chepaiyanse);
					if(vehicleVO == null ){
						vehicle.setImportUrl("icon_cha.png");
						errorStr+=cheliangpaiz+"该车不存在;";
						vehicle.setMsg(cheliangpaiz+"该车不存在;");
						bb++;
					}else{
						vehicle.setImportUrl("icon_gou.png");
					}
					for(Vehicle item : vehicles){
						if(item.getCheliangpaizhao().equals(cheliangpaiz) && item.getChepaiyanse().equals(chepaiyanse)){
							vehicle.setImportUrl("icon_cha.png");
							errorStr+=cheliangpaiz+"车牌号重复；";
							vehicle.setMsg(cheliangpaiz+"车牌号重复；");
							bb++;
						}
					}
					vehicle.setShiyongxingzhi(String.valueOf(a.get("使用性质")).trim());
					vehicle.setXinghao(String.valueOf(a.get("车辆类型")).trim());
					vehicle.setChangpai(String.valueOf(a.get("厂牌")).trim());
					vehicle.setChejiahao(String.valueOf(a.get("车架号")).trim());
					vehicle.setYunyingshangmingcheng(String.valueOf(a.get("运营商名称")).trim());
					String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(String.valueOf(a.get("4G视频地址")).trim()));
					vehicle.setYunyingshang(yys);
					vehicle.setChezhu(String.valueOf(a.get("车主")).trim());
					String zongduanid = String.valueOf(a.get("终端编号")).trim();
					VehicleVO vehicleVO1 = vehicleService.selectByZongDuan(zongduanid);
					if(StringUtils.isNotBlank(vehicleVO1.getZongduanid())){
						vehicle.setImportUrl("icon_cha.png");
						errorStr+=zongduanid+"该终端ID已存在;";
						vehicle.setZongduanid(zongduanid);
						vehicle.setMsg(zongduanid+"该终端ID已存在;");
						bb++;
					}else{
						vehicle.setImportUrl("icon_gou.png");
						vehicle.setZongduanid(zongduanid);
					}

					String zhongduanleixing = String.valueOf(a.get("终端类型")).trim();
					if(StringUtils.isBlank(zhongduanleixing)){
						vehicle.setImportUrl("icon_cha.png");
						errorStr = vehicleVO.getCheliangpaizhao()+"终端类型不能为空;";
						vehicle.setMsg(vehicleVO.getCheliangpaizhao() + "终端类型不能为空;");
						bb++;
					}else {
						List<Dict> dictVOList = iDictClient.getDictByCode("zhongduanleixing",null);
						boolean ss = dictVOList.contains(zhongduanleixing);
						if(ss == true){
							dictVOList = iDictClient.getDictByCode("zhongduanleixing",zhongduanleixing);
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setZhongduanleixing(Integer.parseInt(dictVOList.get(0).getDictKey()));
						}else{
							vehicle.setImportUrl("icon_cha.png");
							errorStr = zhongduanleixing+"该终端类型异常，应为:“主动安全设备/2G设备”;";
							vehicle.setMsg(zhongduanleixing+"该终端类型异常,应为:“主动安全设备/2G设备”;");
							bb++;
						}
					}

					if(StringUtils.isBlank(String.valueOf(a.get("驾驶员")))){
						vehicle.setJiashiyuanxingming("");
					}else{
						vehicle.setJiashiyuanxingming(String.valueOf(a.get("驾驶员")).trim());
					}

					if(StringUtils.isBlank(String.valueOf(a.get("押运员")))){
						vehicle.setYayunyuanxingming("");
					}else{
						vehicle.setYayunyuanxingming(String.valueOf(a.get("押运员")).trim());
					}

					if(StringUtils.isBlank(String.valueOf(a.get("车主")))){
						vehicle.setChezhu("");
					}else{
						vehicle.setChezhu(String.valueOf(a.get("车主")).trim());
					}

					String phone = String.valueOf(a.get("驾驶员电话"));
					if(!StringUtils.isBlank(phone) && !phone.equals("null")){
						if(CheckPhoneUtil.isChinaPhoneLegal(phone) == false){
							vehicle.setMsg("驾驶员电话格式不正确;");
							errorStr+=phone+"驾驶员电话格式不正确;";
							vehicle.setImportUrl("icon_cha.png");
							vehicle.setJiashiyuandianhua(String.valueOf(a.get("驾驶员电话")).trim());
							bb++;
						}else{
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setJiashiyuandianhua(String.valueOf(a.get("驾驶员电话")).trim());
						}
					}else{
						vehicle.setJiashiyuandianhua(String.valueOf(a.get("驾驶员电话")).trim());
					}

					String yyphone = String.valueOf(a.get("押运员电话"));
					if(!StringUtils.isBlank(yyphone) && !yyphone.equals("null")){
						if(CheckPhoneUtil.isChinaPhoneLegal(yyphone) == false){
							vehicle.setMsg("押运员电话格式不正确;");
							errorStr+=yyphone+"押运员电话格式不正确;";
							vehicle.setImportUrl("icon_cha.png");
							vehicle.setYayunyuandianhua(String.valueOf(a.get("押运员电话")).trim());
							bb++;
						}else{
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setYayunyuandianhua(String.valueOf(a.get("押运员电话")).trim());
						}
					}else{
						vehicle.setYayunyuandianhua(String.valueOf(a.get("押运员电话")).trim());
					}

					String czphone = String.valueOf(a.get("车主电话"));
					if(!StringUtils.isBlank(czphone) && !czphone.equals("null")){
						if(CheckPhoneUtil.isChinaPhoneLegal(czphone) == false){
							vehicle.setMsg("车主电话格式不正确;");
							errorStr+=czphone+"车主电话格式不正确;";
							vehicle.setImportUrl("icon_cha.png");
							vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
							bb++;
						}else{
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
						}
					}else{
						vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
					}
				}else{
					vehicle.setMsg(deptname+"机构不存在;");
					errorStr+=deptname+"机构不存在;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
				vehicles.add(vehicle);
			}
		if(bb>0){
			rs.setMsg(errorStr);
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setData(vehicles);
			return rs;
		}else{
			rs.setCode(200);
			rs.setMsg("数据验证成功");
			rs.setData(vehicles);
			rs.setSuccess(true);
			return rs;
		}
	}

	/**
	 * 更新车辆信息--确认导入
	 * @author: hyp
	 * @date: 2021/04/09 21:23
	 */
	@PostMapping("vehicleUpdateImportOk")
	@ApiLog("更新车辆信息-确认导入")
	@ApiOperation(value = "更新车辆信息-确认导入", notes = "vehicles", position = 21)
	public R vehicleUpdateImportOk(@RequestParam(value = "vehicles") String vehicles,BladeUser user,@RequestParam Integer userId,@RequestParam String userName){
		System.out.println("vehicles:"+vehicles);
		JSONArray json = JSONUtil.parseArray(vehicles);
		List<Map<String,Object>> lists = (List)json;
		R rs = new R();
		//时间默认格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//验证数据成功条数
		int aa=0;
		//验证数据错误条数
		int bb=0;
		//全局变量，只要一条数据不对，就为false
		boolean isDataValidity=true;
		//错误信息
		String errorStr="";

		if(lists.size()>5000){
			errorStr+="导入数据超过5000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(400);
			return rs;
		}

		List<Vehicle> vehlist=new ArrayList<Vehicle>();
		for(Map<String,Object> a:lists){
			aa++;
			Vehicle vehicle=new Vehicle();
			Dept dept;
			String id=IdUtil.simpleUUID();
			vehicle.setId(id);
			vehicle.setDeptId(Integer.parseInt(String.valueOf(a.get("deptId")).trim()));
			vehicle.setDeptName(String.valueOf(a.get("deptName")).trim());
			vehicle.setCheliangpaizhao(String.valueOf(a.get("cheliangpaizhao")).trim());
			vehicle.setChepaiyanse(String.valueOf(a.get("chepaiyanse")).trim());
			vehicle.setShiyongxingzhi(String.valueOf(a.get("shiyongxingzhi")).trim());
			if(StringUtils.isNotBlank(String.valueOf(a.get("xinghao")).trim())){
				vehicle.setXinghao(String.valueOf(a.get("xinghao")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("changpai")).trim())){
				vehicle.setChangpai(String.valueOf(a.get("changpai")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("chejiahao")).trim())){
				vehicle.setChejiahao(String.valueOf(a.get("chejiahao")).trim());
			}
			String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(String.valueOf(a.get("yunyingshang")).trim()));
			if(StringUtils.isNotBlank(yys)){
				vehicle.setYunyingshang(yys);
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("yunyingshangmingcheng")).trim())){
				vehicle.setYunyingshangmingcheng(String.valueOf(a.get("yunyingshangmingcheng")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("zongduanid")).trim())){
				vehicle.setZongduanid(String.valueOf(a.get("zongduanid")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("zhongduanleixing")).trim())){
				vehicle.setZhongduanleixing(Integer.parseInt(String.valueOf(a.get("zhongduanleixing")).trim()));
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuanxingming")).trim())){
				vehicle.setJiashiyuanxingming(String.valueOf(a.get("jiashiyuanxingming")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuandianhua")).trim())){
				vehicle.setJiashiyuandianhua(String.valueOf(a.get("jiashiyuandianhua")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("yayunyuanxingming")).trim())){
				vehicle.setYayunyuanxingming(String.valueOf(a.get("yayunyuanxingming")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("yayunyuandianhua")).trim())) {
				vehicle.setYayunyuandianhua(String.valueOf(a.get("yayunyuandianhua")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("chezhu")).trim())) {
				vehicle.setChezhu(String.valueOf(a.get("chezhu")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("chezhudianhua")).trim())) {
				vehicle.setChezhudianhua(String.valueOf(a.get("chezhudianhua")).trim());
			}
			vehicle.setCreatetime(LocalDateTime.now());
			vehicle.setCaozuoshijian(LocalDateTime.now());
			if(user != null){
				vehicle.setCaozuoren(user.getUserName());
				vehicle.setCaozuorenid(user.getUserId());
			}else{
				vehicle.setCaozuoren(userName);
				vehicle.setCaozuorenid(userId);
			}
			vehicle.setCheliangzhuangtai("0");
			vehicle.setIsdel(0);
			vehlist.add(vehicle);
			isDataValidity = vehicleService.insertSelective(vehicle);
			if(StringUtils.isNotBlank(vehicle.getJiashiyuanxingming()) && StringUtils.isNotBlank(vehicle.getJiashiyuandianhua())){
				JiaShiYuan driver=new JiaShiYuan();
				SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
				String ids = IdUtil.simpleUUID();
				driver.setId(ids);
				driver.setDeptId(vehicle.getDeptId());
				driver.setJiashiyuanxingming(vehicle.getJiashiyuanxingming());
				driver.setShoujihaoma(vehicle.getJiashiyuandianhua());
				driver.setJiashiyuanleixing("驾驶员");
				driver.setCongyerenyuanleixing("驾驶员");
				driver.setDenglumima(DigestUtil.encrypt("123456"));
				driver.setIsdelete(0);
				driver.setCreatetime(DateUtil.now());
				driver.setCaozuoshijian(DateUtil.now());
				if(user!=null){
					driver.setCaozuoren(user.getUserName());
					driver.setCaozuorenid(user.getUserId());
				}
				boolean jsy = iJiaShiYuanService.insertSelective(driver);
				//向车辆驾驶员绑定关系表中添加记录
				CheliangJiashiyuan cheliangJiashiyuan = vehicleService.getVehidAndJiashiyuanId(vehicle.getId(),driver.getId());
				if(cheliangJiashiyuan == null){
					CheliangJiashiyuan cheliangJiashiyuan1 = new CheliangJiashiyuan();
					cheliangJiashiyuan1.setDeptId(vehicle.getDeptId());
					cheliangJiashiyuan1.setCreatetime(DateUtil.now());
					cheliangJiashiyuan1.setJiashiyuanid(driver.getId());
					cheliangJiashiyuan1.setVehid(vehicle.getId());
					cheliangJiashiyuan1.setType("1");
					boolean vaj = vehicleService.insertVehidAndJiashiyuan(cheliangJiashiyuan1);
				}
			}
			if(StringUtils.isNotBlank(vehicle.getYayunyuanxingming()) && StringUtils.isNotBlank(vehicle.getYayunyuandianhua())){
				JiaShiYuan driver=new JiaShiYuan();
				SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
				String ids = IdUtil.simpleUUID();
				driver.setId(ids);
				driver.setDeptId(vehicle.getDeptId());
				driver.setJiashiyuanxingming(vehicle.getYayunyuanxingming());
				driver.setShoujihaoma(vehicle.getYayunyuandianhua());
				driver.setJiashiyuanleixing("押运员");
				driver.setCongyerenyuanleixing("押运员");
				driver.setDenglumima(DigestUtil.encrypt("123456"));
				driver.setIsdelete(0);
				driver.setCreatetime(DateUtil.now());
				driver.setCaozuoshijian(DateUtil.now());
				if(user!=null){
					driver.setCaozuoren(user.getUserName());
					driver.setCaozuorenid(user.getUserId());
				}
				boolean yyy = iJiaShiYuanService.insertSelective(driver);
			}
		}
		if(isDataValidity == true){
			rs.setCode(200);
			rs.setMsg("数据更新导入成功");
			rs.setData(vehicles);
			return rs;
		}else{
			rs.setCode(500);
			rs.setMsg("数据更新导入失败");
			rs.setData(vehicles);
			return rs;
		}
	}


	/**
	 * 企业端--车辆档案信息--验证
	 * @update: 黄亚平 添加验证
	 */
	@PostMapping("vehicleDeptImport")
	@ApiLog("车辆档案信息--验证(最新版)")
	@ApiOperation(value = "车辆档案信息--验证(最新版)", notes = "file", position = 10)
	public R vehicleDeptImport(@RequestParam(value = "file") MultipartFile file,BladeUser user,@RequestParam String type,@RequestParam String leixing) throws ParseException {
		R rs = new R();
		if (user == null) {
			rs.setCode(500);
			rs.setMsg("用户权限验证失败");
			rs.setData(null);
			rs.setSuccess(false);
			return rs;
		}
		ExcelReader reader = null;
		try {
			reader = ExcelUtil.getReader(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//时间默认格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//验证数据成功条数
		int aa=0;
		//验证数据错误条数
		int bb=0;
		//全局变量，只要一条数据不对，就为false
		boolean isDataValidity=true;
		//错误信息
		String errorStr="";

		List<Map<String,Object>> readAll = reader.readAll();
		if(readAll.size()>2000){
			errorStr+="导入数据超过2000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(500);
			return rs;
		}

		List<Vehicle> vehicles=new ArrayList<Vehicle>();
		for(Map<String,Object> a:readAll){
			aa++;
			Vehicle vehicle= new Vehicle();
			Dept dept;
			String deptname =  String.valueOf(a.get("所属单位")).trim();
			if(StringUtils.isBlank(deptname)){
				vehicle.setMsg("所属单位不能为空;");
				vehicle.setImportUrl("icon_cha.png");
				bb++;
			}
			dept = iSysClient.getDeptByName(deptname.trim());
			if (dept != null) {
				vehicle.setDeptId(Integer.valueOf(dept.getId()));
				vehicle.setDeptName(deptname);
				vehicle.setImportUrl("icon_gou.png");
				vehicle.setDeptId(dept.getId());
			}else{
				String cph = String.valueOf(a.get("车牌号码")).trim();
				vehicle.setMsg(deptname+"机构不存在;");
				vehicle.setImportUrl("icon_cha.png");
				errorStr+=cph+"----"+deptname+"机构不存在;";
				bb++;
			}
			//验证车牌号码
			String cheliangpaiz=String.valueOf(a.get("车牌号码")).trim();
			if(StringUtils.isBlank(cheliangpaiz) && !cheliangpaiz.equals("null")){
				vehicle.setMsg("车牌号码不能为空;");
				vehicle.setImportUrl("icon_cha.png");
				bb++;
			}else{
				vehicle.setCheliangpaizhao(cheliangpaiz);
				vehicle.setImportUrl("icon_gou.png");
			}
			//验证车牌颜色
			String chepaiyanse=String.valueOf(a.get("车牌颜色")).trim();
			if(StringUtils.isBlank(chepaiyanse) && !chepaiyanse.equals("null")){
				vehicle.setMsg("车牌颜色不能为空;");
				errorStr+="车牌颜色不能为空;";
				vehicle.setImportUrl("icon_cha.png");
				bb++;
			}else{
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("colour",null);
				for(int i= 0;i<dictVOList.size();i++){
					ss = dictVOList.get(i).getDictValue().equals(chepaiyanse);
					if(ss == true){
						break;
					}
				}
				if(ss == true){
					dictVOList = iDictClient.getDictByCode("colour",chepaiyanse);
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setChepaiyanse(dictVOList.get(0).getDictKey());
				}else{
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz+",车牌颜色输入错误,请校验”;";
					vehicle.setMsg(cheliangpaiz+",车牌颜色输入错误,请校验;");
					bb++;
				}
			}
			if("运维端".equals(type)) {
				if("更新导入".equals(leixing)){
					VehicleVO vehicleVO = vehicleService.selectCPYS(cheliangpaiz,chepaiyanse);
					if(vehicleVO ==null){
						vehicle.setImportUrl("icon_cha.png");
						errorStr+=cheliangpaiz+"该车不存在;";
						vehicle.setMsg(cheliangpaiz+"该车不存在;");
						bb++;
					}else{
						vehicle.setImportUrl("icon_gou.png");
					}
				}else{
					VehicleVO vehicleVO = vehicleService.selectCPYS(cheliangpaiz, chepaiyanse);
					if (vehicleVO != null) {
						vehicle.setImportUrl("icon_cha.png");
						errorStr += vehicleVO.getCheliangpaizhao() + "该车已存在;";
						vehicle.setMsg(vehicleVO.getCheliangpaizhao() + "该车已存在;");
						bb++;
					} else {
						vehicle.setImportUrl("icon_gou.png");
					}
				}
			}else{
				VehicleVO vehicleVO = vehicleService.selectCPYS(cheliangpaiz,chepaiyanse);
				if(vehicleVO ==null){
					vehicle.setImportUrl("icon_cha.png");
					errorStr+=cheliangpaiz+"该车不存在;";
					vehicle.setMsg(cheliangpaiz+"该车不存在;");
					bb++;
				}else{
					vehicle.setImportUrl("icon_gou.png");
				}
			}
			//验证Excel导入时，是否存在重复数据
			for (Vehicle item : vehicles) {
				if (item.getCheliangpaizhao().equals(cheliangpaiz) && item.getChepaiyanse().equals(chepaiyanse)) {
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz + "车牌号重复；";
					vehicle.setMsg(cheliangpaiz + "车牌号重复；");
					bb++;
				}
			}
			//验证行业类别
			String shiyongxingzhi = String.valueOf(a.get("行业类别")).trim();
			if(StringUtils.isNotBlank(shiyongxingzhi) && !shiyongxingzhi.equals("null")){
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("shiyongxingzhi",null);
				for(int i= 0;i<dictVOList.size();i++){
					ss = dictVOList.get(i).getDictValue().equals(shiyongxingzhi);
					if(ss == true){
						break;
					}
				}
				if(ss == true){
					dictVOList = iDictClient.getDictByCode("shiyongxingzhi",shiyongxingzhi);
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setShiyongxingzhi(dictVOList.get(0).getDictKey());
				}else{
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz+",车辆行业类别输入错误,请校验”;";
					vehicle.setMsg(cheliangpaiz+",车辆行业类别输入错误,请校验;");
					bb++;
				}
			}else{
				vehicle.setImportUrl("icon_cha.png");
				errorStr += cheliangpaiz+",车辆行业类别不能为空,请校验”;";
				vehicle.setMsg(cheliangpaiz+",车辆行业类别不能为空,请校验;");
				bb++;
			}
			if("运维端".equals(type)){
				if("更新导入".equals(leixing)) {
				}else{
					//验证终端编号
					String zongduanid = String.valueOf(a.get("终端编号")).trim();
					if(StringUtils.isNotBlank(zongduanid) && !zongduanid.equals("null")){
						if(zongduanid.length() != 12 && zongduanid.length() != 20){
							vehicle.setImportUrl("icon_cha.png");
							vehicle.setZongduanid(zongduanid);
							errorStr += zongduanid + "该终端号不是标准终端位（12位/20位）;";
							vehicle.setMsg(zongduanid + "该终端号不是标准终端位（12位/20位）;");
							bb++;
						}else {
							Vehicle vehicleVO1 = vehicleService.selectByZongDuan(zongduanid);
							if (vehicleVO1 != null) {
								vehicle.setImportUrl("icon_cha.png");
								errorStr += zongduanid + "该终端ID已存在;";
								vehicle.setZongduanid(zongduanid);
								vehicle.setMsg(zongduanid + "该终端ID已存在;");
								bb++;
							} else {
								vehicle.setImportUrl("icon_gou.png");
								vehicle.setZongduanid(zongduanid);
							}
						}
					}else{
						vehicle.setImportUrl("icon_cha.png");
						vehicle.setZongduanid(zongduanid);
						errorStr += cheliangpaiz + "该终端号不是标准终端位（12位/20位）;";
						vehicle.setMsg(cheliangpaiz + "该终端号不是标准终端位（12位/20位）;");
						bb++;
					}
					//验证Excel导入时，是否存在重复数据
					for(Vehicle item : vehicles){
						if(item.getZongduanid().equals(zongduanid)){
							vehicle.setImportUrl("icon_cha.png");
							errorStr+=zongduanid+"终端编号重复；";
							vehicle.setMsg(zongduanid+"终端编号重复；");
							bb++;
						}
					}
					//验证终端类型
					String zhongduanleixing = String.valueOf(a.get("终端类型")).trim();
					if(StringUtils.isBlank(zhongduanleixing)){
						vehicle.setImportUrl("icon_cha.png");
						errorStr = cheliangpaiz+"终端类型不能为空;";
						vehicle.setMsg(cheliangpaiz + "终端类型不能为空;");
						bb++;
					}else {
						boolean ss = false;
						List<Dict> dictVOList = iDictClient.getDictByCode("zhongduanleixing",null);
						for(int i= 0;i<dictVOList.size();i++){
							ss = dictVOList.get(i).getDictValue().equals(zhongduanleixing);
							if(ss == true){
								break;
							}
						}
						if(ss == true){
							dictVOList = iDictClient.getDictByCode("zhongduanleixing",zhongduanleixing);
							vehicle.setImportUrl("icon_gou.png");
							vehicle.setZhongduanleixing(Integer.parseInt(dictVOList.get(0).getDictKey()));
						}else{
							vehicle.setImportUrl("icon_cha.png");
							errorStr += zhongduanleixing+"该终端类型异常，应为:“主动安全设备/2G设备”;";
							vehicle.setMsg(zhongduanleixing+"该终端类型异常,应为:“主动安全设备/2G设备”;");
							bb++;
						}
					}
				}
			}
			//验证运营状态
			String vehstatus = String.valueOf(a.get("运营状态")).trim();
			if(StringUtils.isNotBlank(vehstatus) && !vehstatus.equals("null")){
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("vehicle_state",null);
				for(int i= 0;i<dictVOList.size();i++){
					ss = dictVOList.get(i).getDictValue().equals(vehstatus);
					if(ss == true){
						break;
					}
				}
				if(ss == true){
					dictVOList = iDictClient.getDictByCode("vehicle_state",vehstatus);
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setCheliangzhuangtai(dictVOList.get(0).getDictKey());
				}else{
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz+",运营状态输入错误,请校验”;";
					vehicle.setMsg(cheliangpaiz+",运营状态输入错误,请校验;");
					bb++;
				}
			}else{
				vehicle.setCheliangzhuangtai("0");
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("道路运输证号"))) && !String.valueOf(a.get("道路运输证号")).equals("null")){
				vehicle.setDaoluyunshuzheng(String.valueOf(a.get("道路运输证号")).trim());
			}
			//验证道路运输证初次发放日期
			String daoluyunshuzhengchulingriqi = String.valueOf(a.get("道路运输证初次发放日期")).trim();
			if(StringUtils.isNotBlank(daoluyunshuzhengchulingriqi) && !daoluyunshuzhengchulingriqi.equals("null")){
				if(DateUtils.isDateString(daoluyunshuzhengchulingriqi,null) == true){
					vehicle.setDaoluyunshuzhengchulingriqi(daoluyunshuzhengchulingriqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(daoluyunshuzhengchulingriqi+",该道路运输证初次发放日期,不是时间格式;");
					errorStr+=daoluyunshuzhengchulingriqi+",该道路运输证初次发放日期,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证道路运输证有效截至日期
			String daoluyunshuzhengyouxiaoqi = String.valueOf(a.get("道路运输证有效截至日期")).trim();
			if(StringUtils.isNotBlank(daoluyunshuzhengyouxiaoqi) && !daoluyunshuzhengyouxiaoqi.equals("null")){
				if(DateUtils.isDateString(daoluyunshuzhengyouxiaoqi,null) == true){
					vehicle.setDaoluyunshuzhengyouxiaoqi(daoluyunshuzhengyouxiaoqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(daoluyunshuzhengyouxiaoqi+",该道路运输证有效截至日期,不是时间格式;");
					errorStr+=daoluyunshuzhengyouxiaoqi+",该道路运输证有效截至日期,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证 道路运输证初次发放日期 不能大于 道路运输证有效截至日期
			if(StringUtils.isNotBlank(daoluyunshuzhengchulingriqi) && !daoluyunshuzhengchulingriqi.equals("null") && StringUtils.isNotBlank(daoluyunshuzhengyouxiaoqi) && !daoluyunshuzhengyouxiaoqi.equals("null")){
				int a1 = daoluyunshuzhengchulingriqi.length();
				int b1 = daoluyunshuzhengyouxiaoqi.length();
				if(a1 == b1){
					if(a1 <= 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if(DateUtils.belongCalendar(format.parse(daoluyunshuzhengchulingriqi),format.parse(daoluyunshuzhengyouxiaoqi))){
							vehicle.setImportUrl("icon_gou.png");
						}else{
							vehicle.setMsg("道路运输证初次发放日期,不能大于道路运输证有效截至日期;");
							errorStr+="道路运输证初次发放日期,不能大于道路运输证有效截至日期;";
							vehicle.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if(a1 > 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if(DateUtils.belongCalendar(format.parse(daoluyunshuzhengchulingriqi),format.parse(daoluyunshuzhengyouxiaoqi))){
							vehicle.setImportUrl("icon_gou.png");
						}else{
							vehicle.setMsg("道路运输证初次发放日期,不能大于道路运输证有效截至日期;");
							errorStr+="道路运输证初次发放日期,不能大于道路运输证有效截至日期;";
							vehicle.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				}else{
					vehicle.setMsg("道路运输证初次发放日期与道路运输证有效截至日期,时间格式不一致;");
					errorStr+="道路运输证初次发放日期与道路运输证有效截至日期,时间格式不一致;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证本次年审日期
			String bencinianshenriqi = String.valueOf(a.get("本次年审日期")).trim();
			if(StringUtils.isNotBlank(bencinianshenriqi) && !bencinianshenriqi.equals("null")){
				if(DateUtils.isDateString(bencinianshenriqi,null) == true){
					vehicle.setBencinianshenriqi(bencinianshenriqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(bencinianshenriqi+",该本次年审日期,不是时间格式;");
					errorStr+=bencinianshenriqi+",该本次年审日期,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证下次年审日期
			String xiacinianshenriqi = String.valueOf(a.get("下次年审日期")).trim();
			if(StringUtils.isNotBlank(xiacinianshenriqi) && !xiacinianshenriqi.equals("null")){
				if(DateUtils.isDateString(xiacinianshenriqi,null) == true){
					vehicle.setXiacinianshenriqi(xiacinianshenriqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(xiacinianshenriqi+",该下次年审日期,不是时间格式;");
					errorStr+=xiacinianshenriqi+",该下次年审日期,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证 本次年审日期 不能大于 下次年审日期
			if(StringUtils.isNotBlank(bencinianshenriqi) && !bencinianshenriqi.equals("null") && StringUtils.isNotBlank(xiacinianshenriqi) && !xiacinianshenriqi.equals("null")){
				int a1 = bencinianshenriqi.length();
				int b1 = xiacinianshenriqi.length();
				if(a1 == b1){
					if(a1 <= 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if(DateUtils.belongCalendar(format.parse(bencinianshenriqi),format.parse(xiacinianshenriqi))){
							vehicle.setImportUrl("icon_gou.png");
						}else{
							vehicle.setMsg("本次年审日期,不能大于下次年审日期;");
							errorStr+="本次年审日期,不能大于下次年审日期;";
							vehicle.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if(a1 > 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if(DateUtils.belongCalendar(format.parse(bencinianshenriqi),format.parse(xiacinianshenriqi))){
							vehicle.setImportUrl("icon_gou.png");
						}else{
							vehicle.setMsg("本次年审日期,不能大于下次年审日期;");
							errorStr+="本次年审日期,不能大于下次年审日期;";
							vehicle.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				}else{
					vehicle.setMsg("本次年审日期与下次年审日期,时间格式不一致;");
					errorStr+="本次年审日期与下次年审日期,时间格式不一致;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证本次年检日期
			String bencinianjianriqi = String.valueOf(a.get("本次年检日期")).trim();
			if(StringUtils.isNotBlank(bencinianjianriqi) && !bencinianjianriqi.equals("null")){
				if(DateUtils.isDateString(bencinianjianriqi,null) == true){
					vehicle.setBencinianjianriqi(bencinianjianriqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(bencinianjianriqi+",该本次年检日期,不是时间格式;");
					errorStr+=bencinianjianriqi+",该本次年检日期,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证下次年检日期
			String xiacinianjianriqi = String.valueOf(a.get("下次年检日期")).trim();
			if(StringUtils.isNotBlank(xiacinianjianriqi) && !xiacinianjianriqi.equals("null")){
				if(DateUtils.isDateString(xiacinianjianriqi,null) == true){
					vehicle.setXiacinianjianriqi(xiacinianjianriqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(xiacinianjianriqi+",该下次年检日期,不是时间格式;");
					errorStr+=xiacinianjianriqi+",该下次年检日期,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证 道路运输证初次发放日期 不能大于 下次年检日期
			if(StringUtils.isNotBlank(bencinianjianriqi) && !bencinianjianriqi.equals("null") && StringUtils.isNotBlank(xiacinianjianriqi) && !xiacinianjianriqi.equals("null")){
				int a1 = bencinianjianriqi.length();
				int b1 = xiacinianjianriqi.length();
				if(a1 == b1){
					if(a1 <= 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if(DateUtils.belongCalendar(format.parse(bencinianjianriqi),format.parse(xiacinianjianriqi))){
							vehicle.setImportUrl("icon_gou.png");
						}else{
							vehicle.setMsg("本次年检日期,不能大于下次年检日期;");
							errorStr+="本次年检日期,不能大于下次年检日期;";
							vehicle.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if(a1 > 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if(DateUtils.belongCalendar(format.parse(bencinianjianriqi),format.parse(xiacinianjianriqi))){
							vehicle.setImportUrl("icon_gou.png");
						}else{
							vehicle.setMsg("本次年检日期,不能大于下次年检日期;");
							errorStr+="本次年检日期,不能大于下次年检日期;";
							vehicle.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				}else{
					vehicle.setMsg("本次年检日期与下次年检日期,时间格式不一致;");
					errorStr+="本次年检日期与下次年检日期,时间格式不一致;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证本次技评日期
			String bencijipingriqi = String.valueOf(a.get("本次技评日期")).trim();
			if(StringUtils.isNotBlank(bencijipingriqi) && !bencijipingriqi.equals("null")){
				if(DateUtils.isDateString(bencijipingriqi,null) == true){
					vehicle.setBencijipingriqi(bencijipingriqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(bencijipingriqi+",该本次技评日期,不是时间格式;");
					errorStr+=bencijipingriqi+",该本次技评日期,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证下次技评日期
			String xiacijipingriqi = String.valueOf(a.get("下次技评日期")).trim();
			if(StringUtils.isNotBlank(xiacijipingriqi) && !xiacijipingriqi.equals("null")){
				if(DateUtils.isDateString(xiacijipingriqi,null) == true){
					vehicle.setXiacijipingriqi(xiacijipingriqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(xiacijipingriqi+",该下次技评日期,不是时间格式;");
					errorStr+=xiacijipingriqi+",该下次技评日期,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证 本次技评日期 不能大于 下次技评日期
			if(StringUtils.isNotBlank(bencijipingriqi) && !bencijipingriqi.equals("null") && StringUtils.isNotBlank(xiacijipingriqi) && !xiacijipingriqi.equals("null")){
				int a1 = bencijipingriqi.length();
				int b1 = xiacijipingriqi.length();
				if(a1 == b1){
					if(a1 <= 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if(DateUtils.belongCalendar(format.parse(bencijipingriqi),format.parse(xiacijipingriqi))){
							vehicle.setImportUrl("icon_gou.png");
						}else{
							vehicle.setMsg("本次技评日期,不能大于下次技评日期;");
							errorStr+="本次技评日期,不能大于下次技评日期;";
							vehicle.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if(a1 > 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if(DateUtils.belongCalendar(format.parse(bencijipingriqi),format.parse(xiacijipingriqi))){
							vehicle.setImportUrl("icon_gou.png");
						}else{
							vehicle.setMsg("本次技评日期,不能大于下次技评日期;");
							errorStr+="本次技评日期,不能大于下次技评日期;";
							vehicle.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				}else{
					vehicle.setMsg("本次技评日期与下次技评日期,时间格式不一致;");
					errorStr+="本次技评日期与下次技评日期,时间格式不一致;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证报废日期
			String baofeiriqi = String.valueOf(a.get("报废日期")).trim();
			if(StringUtils.isNotBlank(baofeiriqi) && !baofeiriqi.equals("null")){
				if(DateUtils.isDateString(baofeiriqi,null) == true){
					vehicle.setBaofeiriqi(baofeiriqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(baofeiriqi+",该报废日期,不是时间格式;");
					errorStr+=baofeiriqi+",该报废日期,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证保险到期日期
			String baoxiandaoqi = String.valueOf(a.get("保险到期日期")).trim();
			if(StringUtils.isNotBlank(baoxiandaoqi) && !baoxiandaoqi.equals("null")){
				if(DateUtils.isDateString(baoxiandaoqi,null) == true){
					vehicle.setBaofeiriqi(baoxiandaoqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(baofeiriqi+",该保险到期日期,不是时间格式;");
					errorStr+=baofeiriqi+",该保险到期日期,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证行驶证到期时间
			String xingshizhengdaoqishijian = String.valueOf(a.get("行驶证到期时间")).trim();
			if(StringUtils.isNotBlank(xingshizhengdaoqishijian) && !xingshizhengdaoqishijian.equals("null")){
				if(DateUtils.isDateString(xingshizhengdaoqishijian,null) == true){
					vehicle.setXingshizhengdaoqishijian(xingshizhengdaoqishijian);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(baofeiriqi+",行驶证到期时间,不是时间格式;");
					errorStr+=xingshizhengdaoqishijian+",行驶证到期时间,不是时间格式;";
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			vehicle.setCheliangjishudengji(String.valueOf(a.get("车辆技术等级")).trim());
			//验证车辆类型
			String xinghao = String.valueOf(a.get("车辆类型")).trim();
			if(StringUtils.isNotBlank(xinghao) && !xinghao.equals("null")){
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("xinghao",null);
				for(int i= 0;i<dictVOList.size();i++){
					ss = dictVOList.get(i).getDictValue().equals(xinghao);
					if(ss == true){
						break;
					}
				}
				if(ss == true){
					dictVOList = iDictClient.getDictByCode("xinghao",xinghao);
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setXinghao(dictVOList.get(0).getDictKey());
				}else{
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz+",车辆类型输入错误,请校验”;";
					vehicle.setMsg(cheliangpaiz+",车辆类型输入错误,请校验;");
					bb++;
				}
			}
			vehicle.setChangpai(String.valueOf(a.get("厂牌")).trim());
			vehicle.setZongzhiliang(String.valueOf(a.get("核定吨位")).trim());
			vehicle.setHedingzaike(String.valueOf(a.get("核定座位数")).trim());
			vehicle.setChejiahao(String.valueOf(a.get("车架号")).trim());
			vehicle.setYunyingshangmingcheng(String.valueOf(a.get("卫星定位服务商名称")).trim());
			String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(String.valueOf(a.get("4G视频地址")).trim()));
			vehicle.setYunyingshang(yys);
			//验证平台连接方式
			String platformConnectionType = String.valueOf(a.get("平台连接方式")).trim();
			if(StringUtils.isNotBlank(platformConnectionType) && !platformConnectionType.equals("null")){
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("platformConnectionType",null);
				for(int i= 0;i<dictVOList.size();i++){
					ss = dictVOList.get(i).getDictValue().equals(platformConnectionType);
					if(ss == true){
						break;
					}
				}
				if(ss == true){
					dictVOList = iDictClient.getDictByCode("platformConnectionType",platformConnectionType);
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setPlatformconnectiontype(dictVOList.get(0).getDictKey());
				}else{
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz+",平台连接方式输入错误,请校验”;";
					vehicle.setMsg(cheliangpaiz+",平台连接方式输入错误,请校验;");
					bb++;
				}
			}
			//验证终端协议类型
			String terminalProtocolType = String.valueOf(a.get("终端协议类型")).trim();
			if(StringUtils.isNotBlank(terminalProtocolType) && !terminalProtocolType.equals("null")){
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("terminalProtocolType",null);
				for(int i= 0;i<dictVOList.size();i++){
					ss = dictVOList.get(i).getDictValue().equals(terminalProtocolType);
					if(ss == true){
						break;
					}
				}
				if(ss == true){
					dictVOList = iDictClient.getDictByCode("terminalProtocolType",terminalProtocolType);
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setTerminalprotocoltype(dictVOList.get(0).getDictKey());
				}else{
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz+",终端协议类型输入错误,请校验”;";
					vehicle.setMsg(cheliangpaiz+",终端协议类型输入错误,请校验;");
					bb++;
				}
			}
			//验证视频通道数
			String videoChannelNum = String.valueOf(a.get("视频通道数")).trim();
			if(StringUtils.isNotBlank(videoChannelNum) && !videoChannelNum.equals("null")){
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("videoChannelNum",null);
				for(int i= 0;i<dictVOList.size();i++){
					ss = dictVOList.get(i).getDictValue().equals(videoChannelNum);
					if(ss == true){
						break;
					}
				}
				if(ss == true){
					dictVOList = iDictClient.getDictByCode("videoChannelNum",videoChannelNum);
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setVideochannelnum(dictVOList.get(0).getDictKey());
				}else{
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz+",视频通道数输入错误,请校验”;";
					vehicle.setMsg(cheliangpaiz+",视频通道数输入错误,请校验;");
					bb++;
				}
			}
			if(StringUtils.isBlank(String.valueOf(a.get("驾驶员"))) || String.valueOf(a.get("驾驶员")).equals("null")){
				vehicle.setJiashiyuanxingming("");
			}else{
				vehicle.setJiashiyuanxingming(String.valueOf(a.get("驾驶员")).trim());
			}
			//验证驾驶员电话
			String phone = String.valueOf(a.get("驾驶员电话"));
			if(!StringUtils.isBlank(phone) && !phone.equals("null")){
				if(CheckPhoneUtil.isChinaPhoneLegal(phone) == false){
					vehicle.setMsg("驾驶员电话格式不正确;");
					errorStr+=phone+"驾驶员电话格式不正确;";
					vehicle.setImportUrl("icon_cha.png");
					vehicle.setJiashiyuandianhua(String.valueOf(a.get("驾驶员电话")).trim());
					bb++;
				}else{
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setJiashiyuandianhua(String.valueOf(a.get("驾驶员电话")).trim());
				}
			}
			//验证随车电话
			String scphone = String.valueOf(a.get("随车电话"));
			if(!StringUtils.isBlank(scphone) && !scphone.equals("null")){
				if(CheckPhoneUtil.isChinaPhoneLegal(scphone) == false){
					vehicle.setMsg("随车电话格式不正确;");
					errorStr+=scphone+"随车电话格式不正确;";
					vehicle.setImportUrl("icon_cha.png");
					vehicle.setAccessoryphone(String.valueOf(a.get("随车电话")).trim());
					bb++;
				}else{
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setAccessoryphone(String.valueOf(a.get("随车电话")).trim());
				}
			}
			if(StringUtils.isBlank(String.valueOf(a.get("押运员"))) || String.valueOf(a.get("押运员")).equals("null")){
				vehicle.setYayunyuanxingming("");
			}else{
				vehicle.setYayunyuanxingming(String.valueOf(a.get("押运员")).trim());
			}
			//验证押运员电话
			String yyphone = String.valueOf(a.get("押运员电话"));
			if(!StringUtils.isBlank(yyphone) && !yyphone.equals("null")){
				if(CheckPhoneUtil.isChinaPhoneLegal(yyphone) == false){
					vehicle.setMsg("押运员电话格式不正确;");
					errorStr+=yyphone+"押运员电话格式不正确;";
					vehicle.setImportUrl("icon_cha.png");
					vehicle.setYayunyuandianhua(String.valueOf(a.get("押运员电话")).trim());
					bb++;
				}else{
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setYayunyuandianhua(String.valueOf(a.get("押运员电话")).trim());
				}
			}
			if(StringUtils.isBlank(String.valueOf(a.get("车主"))) || String.valueOf(a.get("车主")).equals("null")){
				vehicle.setChezhu("");
			}else{
				vehicle.setChezhu(String.valueOf(a.get("车主")).trim());
			}
			//验证车主电话
			String czphone = String.valueOf(a.get("车主电话"));
			if(!StringUtils.isBlank(czphone) && !czphone.equals("null")){
				if(CheckPhoneUtil.isChinaPhoneLegal(czphone) == false){
					vehicle.setMsg("车主电话格式不正确;");
					errorStr+=czphone+"车主电话格式不正确;";
					vehicle.setImportUrl("icon_cha.png");
					vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
					bb++;
				}else{
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
				}
			}
			if(StringUtils.isBlank(String.valueOf(a.get("车主居住地址"))) || String.valueOf(a.get("车主居住地址")).equals("null")){
				vehicle.setCarowneraddress("");
			}else{
				vehicle.setCarowneraddress(String.valueOf(a.get("车主居住地址")).trim());
			}
			if(StringUtils.isBlank(String.valueOf(a.get("所属区县"))) || String.valueOf(a.get("所属区县")).equals("null")){
				vehicle.setArea("");
			}else{
				vehicle.setArea(String.valueOf(a.get("所属区县")).trim());
			}
			if(StringUtils.isBlank(String.valueOf(a.get("所属运管机构"))) || String.valueOf(a.get("所属运管机构")).equals("null")){
				vehicle.setSuoshuyunguan("");
			}else{
				vehicle.setSuoshuyunguan(String.valueOf(a.get("所属运管机构")).trim());
			}
			if(StringUtils.isBlank(String.valueOf(a.get("线路号"))) || String.valueOf(a.get("线路号")).equals("null")){
				vehicle.setTeamno("");
			}else{
				vehicle.setTeamno(String.valueOf(a.get("线路号")).trim());
			}
			if(StringUtils.isBlank(String.valueOf(a.get("自编号"))) || String.valueOf(a.get("自编号")).equals("null")){
				vehicle.setOwenno("");
			}else{
				vehicle.setOwenno(String.valueOf(a.get("自编号")).trim());
			}
			if(StringUtils.isBlank(String.valueOf(a.get("SIM卡号"))) || String.valueOf(a.get("SIM卡号")).equals("null")){
				vehicle.setSimnum("");
			}else{
				vehicle.setSimnum(String.valueOf(a.get("SIM卡号")).trim());
			}
			if(StringUtils.isBlank(String.valueOf(a.get("发动机号"))) || String.valueOf(a.get("发动机号")).equals("null")){
				vehicle.setFadongjihao("");
			}else{
				vehicle.setFadongjihao(String.valueOf(a.get("发动机号")).trim());
			}
			if(StringUtils.isBlank(String.valueOf(a.get("终端厂家"))) || String.valueOf(a.get("终端厂家")).equals("null")){
				vehicle.setZhongduanchangshang("");
			}else{
				vehicle.setZhongduanchangshang(String.valueOf(a.get("终端厂家")).trim());
			}
			if(StringUtils.isBlank(String.valueOf(a.get("终端型号"))) || String.valueOf(a.get("终端型号")).equals("null")){
				vehicle.setZongduanxinghao("");
			}else{
				vehicle.setZongduanxinghao(String.valueOf(a.get("终端型号")).trim());
			}
			if(StringUtils.isBlank(String.valueOf(a.get("备注"))) || String.valueOf(a.get("备注")).equals("null")){
				vehicle.setBeizhu("");
			}else{
				vehicle.setBeizhu(String.valueOf(a.get("备注")).trim());
			}
			vehicles.add(vehicle);
		}
		if(bb>0){
			rs.setMsg(errorStr);
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setData(vehicles);
			return rs;
		}else{
			rs.setCode(200);
			rs.setMsg("数据验证成功");
			rs.setData(vehicles);
			rs.setSuccess(true);
			return rs;
		}
	}

	/**
	 * 车辆信息--确认导入
	 */
	@PostMapping("vehicleDeptImportOk")
	@ApiLog("车辆档案信息--确认导入(最新版)")
	@ApiOperation(value = "车辆档案信息--确认导入(最新版)", notes = "vehicles", position = 10)
	public R vehicleDeptImportOk(@RequestParam(value = "vehicles") String vehicles,BladeUser user) {
		R rs = new R();
		if (user == null) {
			rs.setCode(500);
			rs.setMsg("用户权限验证失败");
			rs.setData(null);
			rs.setSuccess(false);
			return rs;
		}
		JSONArray json = JSONUtil.parseArray(vehicles);
		List<Map<String, Object>> lists = (List) json;
		//时间默认格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//验证数据成功条数
		int aa = 0;
		//验证数据错误条数
		int bb = 0;
		//全局变量，只要一条数据不对，就为false
		boolean isDataValidity = true;
		//错误信息
		String errorStr = "";

		if (lists.size() > 2000) {
			errorStr += "导入数据超过2000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(500);
			return rs;
		}

		for (Map<String, Object> a : lists) {
			aa++;
			Vehicle vehicle = new Vehicle();
			String id=IdUtil.simpleUUID();
			vehicle.setId(id);
			vehicle.setDeptId(Integer.parseInt(String.valueOf(a.get("deptId")).trim()));
			vehicle.setCheliangpaizhao(String.valueOf(a.get("cheliangpaizhao")).trim());
			vehicle.setChepaiyanse(String.valueOf(a.get("chepaiyanse")).trim());
			if (StringUtils.isNotBlank(String.valueOf(a.get("shiyongxingzhi")).trim())  && !String.valueOf(a.get("shiyongxingzhi")).equals("null")) {
				vehicle.setShiyongxingzhi(String.valueOf(a.get("shiyongxingzhi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("zongduanid")).trim()) && !String.valueOf(a.get("zongduanid")).equals("null")) {
				vehicle.setZongduanid(String.valueOf(a.get("zongduanid")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("zhongduanleixing")).trim()) && !String.valueOf(a.get("zhongduanleixing")).equals("null")){
				vehicle.setZhongduanleixing(Integer.parseInt(String.valueOf(a.get("zhongduanleixing")).trim()));
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("cheliangzhuangtai")).trim()) && !String.valueOf(a.get("cheliangzhuangtai")).equals("null")) {
				vehicle.setCheliangzhuangtai(String.valueOf(a.get("cheliangzhuangtai")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("daoluyunshuzheng")).trim()) && !String.valueOf(a.get("daoluyunshuzheng")).equals("null")) {
				vehicle.setDaoluyunshuzheng(String.valueOf(a.get("daoluyunshuzheng")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("daoluyunshuzhengchulingriqi")).trim()) && !String.valueOf(a.get("daoluyunshuzhengchulingriqi")).equals("null")) {
				vehicle.setDaoluyunshuzhengchulingriqi(String.valueOf(a.get("daoluyunshuzhengchulingriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("daoluyunshuzhengyouxiaoqi")).trim()) && !String.valueOf(a.get("daoluyunshuzhengyouxiaoqi")).equals("null")) {
				vehicle.setDaoluyunshuzhengyouxiaoqi(String.valueOf(a.get("daoluyunshuzhengyouxiaoqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("bencinianshenriqi")).trim()) && !String.valueOf(a.get("bencinianshenriqi")).equals("null")) {
				vehicle.setBencinianshenriqi(String.valueOf(a.get("bencinianshenriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("xiacinianshenriqi")).trim()) && !String.valueOf(a.get("xiacinianshenriqi")).equals("null")) {
				vehicle.setXiacinianshenriqi(String.valueOf(a.get("xiacinianshenriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("bencinianjianriqi")).trim()) && !String.valueOf(a.get("bencinianjianriqi")).equals("null")) {
				vehicle.setBencinianjianriqi(String.valueOf(a.get("bencinianjianriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("xiacinianjianriqi")).trim()) && !String.valueOf(a.get("xiacinianjianriqi")).equals("null")) {
				vehicle.setXiacinianjianriqi(String.valueOf(a.get("xiacinianjianriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("bencijipingriqi")).trim()) && !String.valueOf(a.get("bencijipingriqi")).equals("null")) {
				vehicle.setBencijipingriqi(String.valueOf(a.get("bencijipingriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("xiacijipingriqi")).trim()) && !String.valueOf(a.get("xiacijipingriqi")).equals("null")) {
				vehicle.setXiacijipingriqi(String.valueOf(a.get("xiacijipingriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("baofeiriqi")).trim()) && !String.valueOf(a.get("baofeiriqi")).equals("null")) {
				vehicle.setBaofeiriqi(String.valueOf(a.get("baofeiriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("baoxiandaoqishijian")).trim()) && !String.valueOf(a.get("baoxiandaoqishijian")).equals("null")) {
				vehicle.setBaoxiandaoqishijian(String.valueOf(a.get("baoxiandaoqishijian")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("xingshizhengdaoqishijian")).trim()) && !String.valueOf(a.get("xingshizhengdaoqishijian")).equals("null")) {
				vehicle.setXingshizhengdaoqishijian(String.valueOf(a.get("xingshizhengdaoqishijian")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("cheliangjishudengji")).trim()) && !String.valueOf(a.get("cheliangjishudengji")).equals("null")) {
				vehicle.setCheliangjishudengji(String.valueOf(a.get("cheliangjishudengji")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("xinghao")).trim()) && !String.valueOf(a.get("xinghao")).equals("null")) {
				vehicle.setXinghao(String.valueOf(a.get("xinghao")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("changpai")).trim()) && !String.valueOf(a.get("changpai")).equals("null")) {
				vehicle.setChangpai(String.valueOf(a.get("changpai")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("zongzhiliang")).trim()) && !String.valueOf(a.get("zongzhiliang")).equals("null")) {
				vehicle.setZongzhiliang(String.valueOf(a.get("zongzhiliang")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("hedingzaike")).trim()) && !String.valueOf(a.get("hedingzaike")).equals("null")) {
				vehicle.setHedingzaike(String.valueOf(a.get("hedingzaike")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("chejiahao")).trim()) && !String.valueOf(a.get("chejiahao")).equals("null")) {
				vehicle.setChejiahao(String.valueOf(a.get("chejiahao")).trim());
			}
			String yys = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(String.valueOf(a.get("yunyingshang")).trim()));
			if (StringUtils.isNotBlank(yys) && yys != null) {
				vehicle.setYunyingshang(yys);
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("yunyingshangmingcheng")).trim()) && !String.valueOf(a.get("yunyingshangmingcheng")).equals("null")) {
				vehicle.setYunyingshangmingcheng(String.valueOf(a.get("yunyingshangmingcheng")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("platformconnectiontype")).trim()) && !String.valueOf(a.get("platformconnectiontype")).equals("null")) {
				vehicle.setPlatformconnectiontype(String.valueOf(a.get("platformconnectiontype")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("terminalprotocoltype")).trim()) && !String.valueOf(a.get("terminalprotocoltype")).equals("null")) {
				vehicle.setTerminalprotocoltype(String.valueOf(a.get("terminalprotocoltype")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("videochannelnum")).trim()) && !String.valueOf(a.get("videochannelnum")).equals("null")) {
				vehicle.setVideochannelnum(String.valueOf(a.get("videochannelnum")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuanxingming")).trim()) && !String.valueOf(a.get("jiashiyuanxingming")).equals("null")) {
				vehicle.setJiashiyuanxingming(String.valueOf(a.get("jiashiyuanxingming")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuandianhua")).trim()) && !String.valueOf(a.get("jiashiyuandianhua")).equals("null")) {
				vehicle.setJiashiyuandianhua(String.valueOf(a.get("jiashiyuandianhua")).trim());
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("accessoryphone")).trim()) && !String.valueOf(a.get("accessoryphone")).equals("null")){
				VehiclePhone vehiclePhone = new VehiclePhone();
				vehiclePhone.setIsdeleted(0);
				vehiclePhone.setAccessoryphone(String.valueOf(a.get("accessoryphone")).trim());
				vehiclePhone.setVehid(vehicle.getId());
				vehiclePhone.setDeptId(vehicle.getDeptId());
				if(user != null){
					vehiclePhone.setCreateuserid(user.getUserId());
				}
				QueryWrapper<VehiclePhone> vehiclePhoneQueryWrapper = new QueryWrapper<VehiclePhone>();
				vehiclePhoneQueryWrapper.lambda().eq(VehiclePhone::getVehid,vehicle.getId());
				vehiclePhoneQueryWrapper.lambda().eq(VehiclePhone::getIsdeleted,0);
				VehiclePhone deail = vehiclePhoneService.getOne(vehiclePhoneQueryWrapper);
				if(deail != null){
					deail.setIsdeleted(1);
					vehiclePhoneService.updateById(deail);
					vehiclePhoneService.save(vehiclePhone);
				}else{
					vehiclePhoneService.save(vehiclePhone);
				}
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("yayunyuanxingming")).trim()) && !String.valueOf(a.get("yayunyuanxingming")).equals("null")) {
				vehicle.setYayunyuanxingming(String.valueOf(a.get("yayunyuanxingming")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("yayunyuandianhua")).trim()) && !String.valueOf(a.get("yayunyuandianhua")).equals("null")) {
				vehicle.setYayunyuandianhua(String.valueOf(a.get("yayunyuandianhua")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("chezhu")).trim()) && !String.valueOf(a.get("chezhu")).equals("null")) {
				vehicle.setChezhu(String.valueOf(a.get("chezhu")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("chezhudianhua")).trim()) && !String.valueOf(a.get("chezhudianhua")).equals("null")) {
				vehicle.setChezhudianhua(String.valueOf(a.get("chezhudianhua")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("carowneraddress")).trim()) && !String.valueOf(a.get("carowneraddress")).equals("null")) {
				vehicle.setCarowneraddress(String.valueOf(a.get("carowneraddress")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("area")).trim()) && !String.valueOf(a.get("area")).equals("null")) {
				vehicle.setArea(String.valueOf(a.get("area")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("suoshuyunguan")).trim()) && !String.valueOf(a.get("suoshuyunguan")).equals("null")) {
				vehicle.setSuoshuyunguan(String.valueOf(a.get("suoshuyunguan")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("teamno")).trim()) && !String.valueOf(a.get("teamno")).equals("null")) {
				vehicle.setTeamno(String.valueOf(a.get("teamno")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("owenno")).trim()) && !String.valueOf(a.get("owenno")).equals("null")) {
				vehicle.setOwenno(String.valueOf(a.get("owenno")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("simnum")).trim()) && !String.valueOf(a.get("simnum")).equals("null")) {
				vehicle.setSimnum(String.valueOf(a.get("simnum")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("fadongjihao")).trim()) && !String.valueOf(a.get("fadongjihao")).equals("null")) {
				vehicle.setFadongjihao(String.valueOf(a.get("fadongjihao")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("zhongduanchangshang")).trim()) && !String.valueOf(a.get("zhongduanchangshang")).equals("null")) {
				vehicle.setZhongduanchangshang(String.valueOf(a.get("zhongduanchangshang")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("zongduanxinghao")).trim()) && !String.valueOf(a.get("zongduanxinghao")).equals("null")) {
				vehicle.setZongduanxinghao(String.valueOf(a.get("zongduanxinghao")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("beizhu")).trim()) && !String.valueOf(a.get("beizhu")).equals("null")) {
				vehicle.setBeizhu(String.valueOf(a.get("beizhu")).trim());
			}
			if (user != null) {
				vehicle.setCaozuoren(user.getUserName());
				vehicle.setCaozuorenid(user.getUserId());
			}
			vehicle.setIsdel(0);

			if(StringUtils.isNotBlank(vehicle.getJiashiyuanxingming()) && StringUtils.isNotBlank(vehicle.getJiashiyuandianhua())) {
				//根据企业ID、驾驶员姓名、驾驶员电话查询驾驶员信息是否存在
				JiaShiYuan jiaShiYuan = iJiaShiYuanService.getjiaShiYuanByOne(vehicle.getDeptId().toString(), vehicle.getJiashiyuanxingming(), vehicle.getJiashiyuandianhua(),null,"驾驶员");
				if (jiaShiYuan != null) {
					vehicle.setJiashiyuanid(jiaShiYuan.getId());
				}
			}

			if(StringUtils.isNotBlank(vehicle.getYayunyuanxingming()) && StringUtils.isNotBlank(vehicle.getYayunyuandianhua())) {
				//根据企业ID、押运员姓名、押运员电话查询押运员信息是否存在
				JiaShiYuan jiaShiYuan = iJiaShiYuanService.getjiaShiYuanByOne(vehicle.getDeptId().toString(), vehicle.getJiashiyuanxingming(), vehicle.getJiashiyuandianhua(),null,"押运员");
				if (jiaShiYuan != null) {
					vehicle.setYayunyuanid(jiaShiYuan.getId());
				}
			}

			VehicleVO vehicleVO = vehicleService.selectCPYS(vehicle.getCheliangpaizhao(),vehicle.getChepaiyanse());
			if(vehicleVO!=null){
				log.info("vehicle:"+vehicle);
				isDataValidity = vehicleService.updateSelective(vehicle);
			}else{
				log.info("vehicle:"+vehicle);
				isDataValidity = vehicleService.insertSelective(vehicle);
			}
		}
		if (isDataValidity == true) {
			rs.setCode(200);
			rs.setMsg("数据导入成功");
			rs.setData(vehicles);
			return rs;
		} else {
			rs.setCode(500);
			rs.setMsg("数据导入失败");
			rs.setData(vehicles);
			return rs;
		}
	}




}
