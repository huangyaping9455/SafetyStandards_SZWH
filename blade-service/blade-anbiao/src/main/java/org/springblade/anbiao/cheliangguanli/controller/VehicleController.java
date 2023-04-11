package org.springblade.anbiao.cheliangguanli.controller;

import cn.afterturn.easypoi.word.entity.WordImageEntity;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
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
import org.apache.tools.zip.ZipOutputStream;
import org.springblade.anbiao.AccidentReports.entity.AccidentReportsEntity;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.page.VehiclePage;
import org.springblade.anbiao.cheliangguanli.service.*;
import org.springblade.anbiao.cheliangguanli.vo.JiShuDangAnVO;
import org.springblade.anbiao.cheliangguanli.vo.VehicleListVO;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.anbiao.configure.entity.Configure;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IOrganizationsClient;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.jiashiyuan.service.*;
import org.springblade.anbiao.risk.controller.AnbiaoRiskDetailController;
import org.springblade.anbiao.weixiu.entity.MaintenanceEntity;
import org.springblade.anbiao.weixiucheliang.service.MaintenanceService;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
	private IVehicleBiangengjiluService vehicleBiangengjiluService;
	private IOrganizationsClient orrganizationsClient;
	private IVehicleDaoluyunshuzhengService daoluyunshuzhengService;
	private IVehicleXingshizhengService xingshizhengService;
	private IVehicleXingnengbaogaoService xingnengbaogaoService;
	private IVehicleJingyingxukezhengService jingyingxukezhengService;
	private IVehicleDengjizhengshuService dengjizhengshuService;
	private IVehicleJishupingdingService jishupingdingService;
	private IAnbiaoCheliangJiashiyuanService cheliangJiashiyuanService;
	private FileServer fileServer;
	private IVehicleXingshizhengService vehicleXingshizhengService;
	private IVehicleDaoluyunshuzhengService vehicleDaoluyunshuzhengService;
	private IVehicleXingnengbaogaoService vehicleXingnengbaogaoService;
	private IVehicleDengjizhengshuService vehicleDengjizhengshuService;
	private IAnbiaoJiashiyuanRuzhiService ruzhiService;
	private IAnbiaoJiashiyuanJiashizhengService jiashizhengService;
	private IAnbiaoJiashiyuanCongyezigezhengService congyezigezhengService;
	private IAnbiaoJiashiyuanTijianService tijianService;
	private IAnbiaoJiashiyuanGangqianpeixunService gangqianpeixunService;
	private IAnbiaoJiashiyuanWuzezhengmingService wuzezhengmingService;
	private IAnbiaoJiashiyuanAnquanzerenshuService anquanzerenshuService;
	private IAnbiaoJiashiyuanWeihaigaozhishuService weihaigaozhishuService;
	private IAnbiaoJiashiyuanLaodonghetongService laodonghetongService;
	private IAnbiaoJiashiyuanQitaService qitaService;
	@Autowired
	private AnbiaoRiskDetailController riskDetailController;
	private MaintenanceService maintenanceService;
	private IDictClient iDictClient;
	private IVehiclePhoneService vehiclePhoneService;


	@PostMapping("/list")
	@ApiLog("分页-车辆资料管理")
    @ApiOperation(value = "分页-车辆资料管理", notes = "传入VehiclePage", position = 1)
    public R<VehiclePage<VehicleListVO>> list(@RequestBody VehiclePage vehiclepage) {
        VehiclePage<VehicleListVO> pages = vehicleService.selectVehiclePage(vehiclepage);
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
    public R<VehicleInfo> detail(String id) {
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

		VehicleInfo v = new VehicleInfo();
		v.setId(detail.getId());
		v.setDeptId(detail.getDeptId());
		v.setCheliangpaizhao(detail.getCheliangpaizhao());
		v.setChepaiyanse(detail.getChepaiyanse());
		v.setJiashiyuanid(detail.getJiashiyuanid());
		v.setChezhu(detail.getChezhu());
		v.setChezhudianhua(detail.getChezhudianhua());
//		v.setZhuceriqi(detail.getZhucedengjishijian());
		v.setJingyingxukezhenghao(detail.getDaoluyunshuzheng());
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(StringUtils.isNotBlank(detail.getDaoluyunshuzhengchulingriqi()) && !detail.getDaoluyunshuzhengchulingriqi().equals("null")){
			v.setJyxkzyouxiaoqiStart(LocalDate.parse(detail.getDaoluyunshuzhengchulingriqi(), fmt));
		}
		if(StringUtils.isNotBlank(detail.getDaoluyunshuzhengyouxiaoqi()) && !detail.getDaoluyunshuzhengyouxiaoqi().equals("null")){
			v.setJyxkzyouxiaoqiEnd(LocalDate.parse(detail.getDaoluyunshuzhengyouxiaoqi(), fmt));
		}
		v.setJingjileixing(detail.getJingjileixing());
		v.setJingyingzuzhifangshi(detail.getJingyingzuzhifangshi());
		v.setJingyingfanwei(detail.getCheliangyunyingleixing());
		v.setJingyingluxian(detail.getTeamno());
		v.setChelianghuodefangshi(detail.getChelianghuoqufangshi());
		v.setWeihuzhouqi(detail.getWeihuzhouqi());
		v.setCheliangzhaopian(detail.getCheliangzhaopian());
		v.setCheliangleixing(detail.getCheliangleixing());
		v.setCheliangxinghao(detail.getXinghao());
		v.setCheliangyanse(detail.getCheshenyanse());
		v.setChejiahao(detail.getChejiahao());
		v.setShifouguochan(detail.getShifoujinkou());
//		if(detail.getShifoujinkou() != null){
//			if(detail.getShifoujinkou().equals("0")){
//				v.setShifouguochan("是");
//			}else{
//				v.setShifouguochan("否");
//			}
//		}
		v.setFadongjihao(detail.getFadongjihao());
		v.setRanliaozhonglei(detail.getRanliaoleibie());
		v.setFadongjixinghao(detail.getFadongjixinghao());
		v.setPailianggonglv(detail.getPaifangbiaozhun());
		v.setZhizaochangmingcheng(detail.getZhizhaochangshang());
		v.setZhuanxiangxingshi(detail.getZhuanxiangfangshi());
		v.setLunju(detail.getLunju());
		v.setLuntaishu(detail.getLuntaishu());
		v.setLuntaiguige(detail.getLuntaiguige());
		v.setTongbantanhuangpianshu(detail.getGangbantanhuangpianshu());
		v.setZhouju(detail.getZhouju());
		v.setZhoushu(detail.getChezhoushu());
		v.setWaikuochicun(detail.getCheliangwaikuochicun());
		v.setHuoxiangneibuchicun(detail.getHuoxiangneibuchicun());
		v.setZongzhiliang(detail.getZongzhiliang());
		v.setHedingzaizhiliang(detail.getHedingzaizhiliang());
		v.setHedingzaikeshu(detail.getHedingzaike());
		v.setZhunqianyinzongzhiliang(detail.getZhunqianyinzongzhiliang());
		v.setJiashishizaikeshu(detail.getJiashishizaike());
		v.setShiyongxingzhi(detail.getShiyongxingzhi());
		v.setCheliangchuchangriqi(detail.getChuchangriqi());
		v.setCheliangpinpai(detail.getCheliangpinpai());
		v.setFrontlunju(detail.getFrontlunju());
		v.setZongduanid(detail.getZongduanid());
		v.setZongduanxinghao(detail.getZongduanxinghao());
		v.setPlatformconnectiontype(detail.getPlatformconnectiontype());
		v.setTerminalprotocoltype(detail.getTerminalprotocoltype());
		v.setZhongduanchangshang(detail.getZhongduanchangshang());
		v.setVideochannelnum(detail.getVideochannelnum());

		QueryWrapper<VehicleBiangengjilu> biangengjiluQueryWrapper = new QueryWrapper<VehicleBiangengjilu>();
		biangengjiluQueryWrapper.lambda().eq(VehicleBiangengjilu::getAvbjVehicleId,detail.getId());
		biangengjiluQueryWrapper.lambda().eq(VehicleBiangengjilu::getAvbjDelete,"0");
		List<VehicleBiangengjilu> deail = vehicleBiangengjiluService.getBaseMapper().selectList(biangengjiluQueryWrapper);
		if(deail != null){
			v.setCheliangbiangengjilu(deail);
		}
        return R.data(v);
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
	@ApiLog("新增-车辆资料管理【新版】")
	@ApiOperation(value = "新增-车辆资料管理【新版】", notes = "传入VehicleInfo", position = 30)
	public R addSave(@RequestBody VehicleInfo v,BladeUser user) {
		R r = new R();

		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
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
		if (v.getQiangzhibaofeishijian() !=null && !v.getQiangzhibaofeishijian().equals("") && v.getQiangzhibaofeishijian().toString().length() > 0){
			vehicle.setQiangzhibaofeishijian(null);
		}else {
			vehicle.setQiangzhibaofeishijian(v.getQiangzhibaofeishijian());
		}
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
		if("是".equals(v.getShifouguochan())){
			vehicle.setShifoujinkou("0");
		}else{
			vehicle.setShifoujinkou("1");
		}
		vehicle.setXinghao(v.getCheliangxinghao());
		vehicle.setCheshenyanse(v.getCheliangyanse());
		vehicle.setRanliaoleibie(v.getRanliaozhonglei());
		vehicle.setPaifangbiaozhun(v.getPailianggonglv());
		vehicle.setZhizhaochangshang(v.getZhizaochangmingcheng());
		vehicle.setZhuanxiangfangshi(v.getZhuanxiangxingshi());
		vehicle.setGangbantanhuangpianshu(v.getTongbantanhuangpianshu());
		vehicle.setChezhoushu(v.getZhoushu());
		vehicle.setCheliangwaikuochicun(v.getWaikuochicun());
		vehicle.setHedingzaike(v.getHedingzaikeshu());
		vehicle.setJiashishizaike(v.getJiashishizaikeshu());
		vehicle.setChuchangriqi(v.getCheliangchuchangriqi());
		vehicle.setCheliangzhaopian(v.getCheliangzhaopian());
		vehicle.setChelianghuoqufangshi(v.getChelianghuodefangshi());
		vehicle.setTeamno(v.getJingyingluxian());
		vehicle.setCheliangyunyingleixing(v.getJingyingfanwei());
		vehicle.setJingyingzuzhifangshi(v.getJingyingzuzhifangshi());
		vehicle.setJingjileixing(v.getJingjileixing());
		vehicle.setDaoluyunshuzheng(v.getJingyingxukezhenghao());
		if(v.getJyxkzyouxiaoqiStart() != null){
			vehicle.setDaoluyunshuzhengchulingriqi(v.getJyxkzyouxiaoqiStart().toString());
		}
		if(v.getJyxkzyouxiaoqiEnd() != null){
			vehicle.setDaoluyunshuzhengyouxiaoqi(v.getJyxkzyouxiaoqiEnd().toString());
		}
		vehicle.setCheliangpinpai(v.getCheliangpinpai());

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
					jingyingxukezheng.setAvjVehicleIds(vehicle.getId());
					jingyingxukezheng.setAvjOperatorName(dept.getDeptName());
					jingyingxukezheng.setAvjLicenseNo(v.getJingyingxukezhenghao());
					jingyingxukezheng.setAvjBusinessLicense(v.getJingyingxukezhenghao());
					jingyingxukezheng.setAvjOperationDeadline(v.getJyxkzyouxiaoqiEnd());
					jingyingxukezheng.setAvjOperatingLineName(v.getJingyingluxian());
					jingyingxukezheng.setAvjEnclosure(v.getJyxkzzhaopian());
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
					if(v.getHedingzaikeshu() != null){
						xingshizheng.setAvxAuthorizedSeatingCapacity(Integer.parseInt(v.getHedingzaikeshu()));
					}
					if(v.getZongzhiliang() != null){
						xingshizheng.setAvxTotalMass(Integer.parseInt(v.getZongzhiliang()));
					}
					if(v.getHedingzaizhiliang() != null){
						xingshizheng.setAvxApprovedLoadCapacity(Integer.parseInt(v.getHedingzaizhiliang()));
					}
					xingshizheng.setAvxCurbWeight(0);		//整备质量
					xingshizheng.setAvxOverallDimensions(v.getWaikuochicun());
					if(v.getZhunqianyinzongzhiliang() != null){
						xingshizheng.setAvxQuasiTractiveMass(Integer.parseInt(v.getZhunqianyinzongzhiliang()));
					}
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
					daoluyunshuzheng.setAvdEnclosure(v.getDlyszzhaopian());
					daoluyunshuzheng.setAvdDelete("0");
					daoluyunshuzheng.setAvdCreateByName(user.getUserName());
					daoluyunshuzheng.setAvdCreateByIds(user.getUserId().toString());
					daoluyunshuzheng.setAvdCreateTime(DateUtil.now());
					daoluyunshuzhengService.save(daoluyunshuzheng);

					//新增-车辆-驾驶员绑定信息
					AnbiaoCheliangJiashiyuan cheliangJiashiyuan = new AnbiaoCheliangJiashiyuan();
					QueryWrapper<AnbiaoCheliangJiashiyuan> cheliangJiashiyuanQueryWrapper = new QueryWrapper<>();
					cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuan::getJiashiyuanid,vehicle.getJiashiyuanid());
					cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuan::getVehid,vehicle.getId());
					AnbiaoCheliangJiashiyuan deail = cheliangJiashiyuanService.getBaseMapper().selectOne(cheliangJiashiyuanQueryWrapper);
					if (deail == null){
						cheliangJiashiyuan.setJiashiyuanid(vehicle.getJiashiyuanid());
						cheliangJiashiyuan.setVehid(vehicle.getId());
						cheliangJiashiyuan.setCreatetime(DateUtil.now());
						cheliangJiashiyuanService.save(cheliangJiashiyuan);
					}

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
	@ApiOperation(value = "编辑-车辆资料管理【新版】", notes = "传入VehicleInfo", position = 31)
	public R updateSave(@RequestBody VehicleInfo v,BladeUser user) {
    	R r = new R();

    	if(user == null) {
    		r.setMsg("未授权");
    		r.setCode(401);
			return r;
		}

		VehicleVO vehicleVO = vehicleService.selectCPYS(v.getCheliangpaizhao(),v.getChepaiyanse());
		if(vehicleVO == null){
			r.setMsg(vehicleVO.getCheliangpaizhao()+"该车不存在");
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
			if(v.getZhuceriqi() != null){
				vehicle.setZhucedengjishijian(v.getZhuceriqi().toString());
			}
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

		vehicle.setShifoujinkou(v.getShifouguochan());

//		if("是".equals(v.getShifouguochan())){
//			vehicle.setShifoujinkou("0");
//		}else{
//			vehicle.setShifoujinkou("1");
//		}
		vehicle.setXinghao(v.getCheliangxinghao());
		vehicle.setCheshenyanse(v.getCheliangyanse());
		vehicle.setRanliaoleibie(v.getRanliaozhonglei());
		vehicle.setPaifangbiaozhun(v.getPailianggonglv());
		vehicle.setZhizhaochangshang(v.getZhizaochangmingcheng());
		vehicle.setZhuanxiangfangshi(v.getZhuanxiangxingshi());
		vehicle.setGangbantanhuangpianshu(v.getTongbantanhuangpianshu());
		vehicle.setChezhoushu(v.getZhoushu());
		vehicle.setCheliangwaikuochicun(v.getWaikuochicun());
		vehicle.setHedingzaike(v.getHedingzaikeshu());
		vehicle.setJiashishizaike(v.getJiashishizaikeshu());
		vehicle.setChuchangriqi(v.getCheliangchuchangriqi());
		vehicle.setCheliangzhaopian(v.getCheliangzhaopian());
		vehicle.setChelianghuoqufangshi(v.getChelianghuodefangshi());
		vehicle.setTeamno(v.getJingyingluxian());
		vehicle.setCheliangyunyingleixing(v.getJingyingfanwei());
		vehicle.setJingyingzuzhifangshi(v.getJingyingzuzhifangshi());
		vehicle.setJingjileixing(v.getJingjileixing());
		vehicle.setDaoluyunshuzheng(v.getJingyingxukezhenghao());
		if(v.getJyxkzyouxiaoqiStart() != null){
			vehicle.setDaoluyunshuzhengchulingriqi(v.getJyxkzyouxiaoqiStart().toString());
		}
		if(v.getJyxkzyouxiaoqiEnd() != null){
			vehicle.setDaoluyunshuzhengyouxiaoqi(v.getJyxkzyouxiaoqiEnd().toString());
		}
		vehicle.setCheliangpinpai(v.getCheliangpinpai());
		vehicle.setFadongjipailianggonglv(v.getPailianggonglv());

//		String str="1";
//		//登录页
//		if(StringUtil.isNotBlank(vehicle.getCheliangzhaopian())){
//			fileUploadClient.updateCorrelation(vehicle.getCheliangzhaopian(),str);
//		}
		boolean i = vehicleService.updateById(vehicle);
		if(i==true){
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					if(v.getCheliangbiangengjilu() != null && v.getCheliangbiangengjilu().size() > 0) {
//						for(VehicleBiangengjilu biangengjilu:v.getCheliangbiangengjilu()) {
//
//							if(biangengjilu.getAvbjIds() != null){
//								VehicleBiangengjilu change = vehicleBiangengjiluService.getById(biangengjilu.getAvbjIds());
//								change.setAvbjVehicleId(vehicle.getId());
//								change.setAvbjDelete("0");
//								change.setAvbjCreateByName(user.getUserName());
//								change.setAvbjCreateByIds(user.getUserId().toString());
//								change.setAvbjCreateTime(LocalDateTime.now());
//								vehicleBiangengjiluService.updateById(change);
//							} else {
//								biangengjilu.setAvbjVehicleId(vehicle.getId());
//								biangengjilu.setAvbjDelete("0");
//								biangengjilu.setAvbjCreateByName(user.getUserName());
//								biangengjilu.setAvbjCreateByIds(user.getUserId().toString());
//								biangengjilu.setAvbjCreateTime(LocalDateTime.now());
//								vehicleBiangengjiluService.save(biangengjilu);
//							}=
//
//
////							QueryWrapper<VehicleBiangengjilu> biangengjiluQueryWrapper = new QueryWrapper<VehicleBiangengjilu>();
////							biangengjiluQueryWrapper.lambda().eq(VehicleBiangengjilu::getAvbjVehicleId,vehicle.getId());
////							biangengjiluQueryWrapper.lambda().eq(VehicleBiangengjilu::getAvbjDelete,"0");
////							biangengjiluQueryWrapper.lambda().eq(VehicleBiangengjilu::getAvbjLicensePlate,vehicle.getCheliangpaizhao());
////							VehicleBiangengjilu deail = vehicleBiangengjiluService.getBaseMapper().selectOne(biangengjiluQueryWrapper);
//						}
//					}
//
//					Organizations dept = orrganizationsClient.selectByDeptId(v.getDeptId().toString());
//					VehicleJishupingding jishupingding = jishupingdingService.selectVehicleJishupingdingByVehicleIds(v.getId());		//技术评定
//					jishupingding.setAvjUpdateByName(user.getUserName());
//					jishupingding.setAvjUpdateByIds(user.getUserId().toString());
//					jishupingding.setAvjUpdateTime(LocalDateTime.now());
//					jishupingdingService.updateById(jishupingding);
//
//					VehicleDengjizhengshu dengjizhengshu = dengjizhengshuService.selectVehicleDengjizhengshuByVehicleIds(v.getId());		//登记证书
//					dengjizhengshu.setAvdUpdateByName(user.getUserName());
//					dengjizhengshu.setAvdUpdateByIds(user.getUserId().toString());
//					dengjizhengshu.setAvdUpdateTime(LocalDateTime.now());
//					dengjizhengshuService.updateById(dengjizhengshu);
//
//					VehicleJingyingxukezheng jingyingxukezheng = jingyingxukezhengService.selectVehicleJingyingxukezhengByVehicleIds(v.getId());		//经营许可证
//					if(jingyingxukezheng == null){
//						jingyingxukezheng = new VehicleJingyingxukezheng();
//						jingyingxukezheng.setAvjDelete("0");
//						jingyingxukezheng.setAvjVehicleIds(v.getId());
//						jingyingxukezheng.setAvjOperatorName(dept.getDeptName());
//						jingyingxukezheng.setAvjLicenseNo(v.getJingyingxukezhenghao());
//						jingyingxukezheng.setAvjBusinessLicense(v.getJingyingxukezhenghao());
//						jingyingxukezheng.setAvjOperationDeadline(v.getJyxkzyouxiaoqiEnd());
//						jingyingxukezheng.setAvjOperatingLineName(v.getJingyingluxian());
//						jingyingxukezheng.setAvjUpdateByName(user.getUserName());
//						jingyingxukezheng.setAvjUpdateByIds(user.getUserId().toString());
//						jingyingxukezheng.setAvjUpdateTime(LocalDateTime.now());
//						jingyingxukezhengService.save(jingyingxukezheng);
//					}else{
//						jingyingxukezheng.setAvjVehicleIds(v.getId());
//						jingyingxukezheng.setAvjOperatorName(dept.getDeptName());
//						jingyingxukezheng.setAvjLicenseNo(v.getJingyingxukezhenghao());
//						jingyingxukezheng.setAvjBusinessLicense(v.getJingyingxukezhenghao());
//						jingyingxukezheng.setAvjOperationDeadline(v.getJyxkzyouxiaoqiEnd());
//						jingyingxukezheng.setAvjOperatingLineName(v.getJingyingluxian());
//						jingyingxukezheng.setAvjUpdateByName(user.getUserName());
//						jingyingxukezheng.setAvjUpdateByIds(user.getUserId().toString());
//						jingyingxukezheng.setAvjUpdateTime(LocalDateTime.now());
//						jingyingxukezhengService.updateById(jingyingxukezheng);
//					}
//
//					VehicleXingnengbaogao xingnengbaogao = xingnengbaogaoService.selectVehicleXingnengbaogaoByVehicleIds(v.getId());		//性能报告
//					xingnengbaogao.setAvxUpdateByName(user.getUserName());
//					xingnengbaogao.setAvxUpdateByIds(user.getUserId().toString());
//					xingnengbaogao.setAvxUpdateTime(LocalDateTime.now());
//					xingnengbaogaoService.updateById(xingnengbaogao);
//
//					VehicleXingshizheng xingshizheng = xingshizhengService.selectVehicleJishupingdingByVehicleIds(v.getId());		//行驶证
//					xingshizheng.setAvxPlateNo(v.getCheliangpaizhao());
//					xingshizheng.setAvxVehicleType(v.getCheliangleixing());
//					xingshizheng.setAvxOwner(dept.getDeptName());
//					xingshizheng.setAvxAddress("住址");
//					xingshizheng.setAvxUseCharter(v.getShiyongxingzhi());
//					xingshizheng.setAvxModel(v.getCheliangxinghao());
//					xingshizheng.setAvxVin(v.getChejiahao());
//					xingshizheng.setAvxEngineNo(v.getFadongjihao());
//					xingshizheng.setAvxRegisterDate(v.getZhuceriqi());
//					xingshizheng.setAvxIssueDate(v.getZhuceriqi());
//					if(v.getHedingzaikeshu() != null && v.getHedingzaikeshu() != ""){
//						xingshizheng.setAvxAuthorizedSeatingCapacity(Integer.parseInt(v.getHedingzaikeshu()));
//					}
//					if(v.getZongzhiliang() != null && v.getZongzhiliang() != ""){
//						xingshizheng.setAvxTotalMass(Integer.parseInt(v.getZongzhiliang()));
//					}
//					if(v.getHedingzaizhiliang() != null && v.getHedingzaizhiliang() != ""){
//						xingshizheng.setAvxApprovedLoadCapacity(Integer.parseInt(v.getHedingzaizhiliang()));
//					}
//
//					xingshizheng.setAvxCurbWeight(0);		//整备质量
//					xingshizheng.setAvxOverallDimensions(v.getWaikuochicun());
//					if(v.getZhunqianyinzongzhiliang() != null){
//						xingshizheng.setAvxQuasiTractiveMass(Integer.parseInt(v.getZhunqianyinzongzhiliang()));
//					}
//					xingshizheng.setAvxUpdateByName(user.getUserName());
//					xingshizheng.setAvxUpdateByIds(user.getUserId().toString());
//					xingshizheng.setAvxUpdateTime(LocalDateTime.now());
//					xingshizhengService.updateById(xingshizheng);
//
//
//					VehicleDaoluyunshuzheng daoluyunshuzheng = daoluyunshuzhengService.selectVehicleDaoluyunshuzhengByVehicleIds(v.getId()); //道路运输证
//					daoluyunshuzheng.setAvdBusinessOwner(dept.getDeptName());
//					daoluyunshuzheng.setAvdPlateNo(v.getCheliangpaizhao());
//					daoluyunshuzheng.setAvdVehicleType(v.getCheliangleixing());
//					daoluyunshuzheng.setAvdTonnage(v.getHedingzaikeshu());
//					if(StringUtils.isNotEmpty(v.getWaikuochicun())) {
//						daoluyunshuzheng.setAvdVehicleLong(Integer.parseInt(v.getWaikuochicun().split("X")[0].toString()));
//						daoluyunshuzheng.setAvdVehicleWide(Integer.parseInt(v.getWaikuochicun().split("X")[1].toString()));
//						daoluyunshuzheng.setAvdVehicleHigh(Integer.parseInt(v.getWaikuochicun().split("X")[2].toString()));
//					}
//
//					daoluyunshuzheng.setAvdRoadTransportCertificateNo(v.getDaoluyunshuzhenghao());
//					daoluyunshuzheng.setAvdIssueDate(v.getDlyszyouxiaoqiStart());
//					daoluyunshuzheng.setAvdValidUntil(v.getDlyszyouxiaoqiEnd());
//					daoluyunshuzheng.setAvdPlateColor(v.getChepaiyanse());
//					daoluyunshuzheng.setAvdBusinessLicenseNo(v.getDaoluyunshuzhenghao());
//					daoluyunshuzheng.setAvdEconomicType(v.getJingjileixing());
//					daoluyunshuzheng.setAvdOperationOrganizationMode(v.getJingyingzuzhifangshi());
//					daoluyunshuzheng.setAvdNatureBusiness(v.getJingyingfanwei());
//					daoluyunshuzheng.setAvdUpdateByName(user.getUserName());
//					daoluyunshuzheng.setAvdUpdateByIds(user.getUserId().toString());
//					daoluyunshuzheng.setAvdUpdateTime(LocalDateTime.now());
//					daoluyunshuzhengService.updateById(daoluyunshuzheng);
//
//				}
//			}).start();
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
	public R updateDetailed(@RequestBody VehicleDetailed vd,BladeUser user) throws ParseException {
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
    		VehicleXingshizheng xsz = vd.getXingshizheng();
    		xsz.setAvxAvIds(vd.getVehicleId());
    		xsz.setAvxDelete("0");
			xsz.setAvxPlateNo(vehicleVO.getCheliangpaizhao());
			xsz.setAvxVehicleType(vehicleVO.getShiyongxingzhi());
			xsz.setAvxOwner(vehicleVO.getChezhu());

			VehicleXingshizheng vxsz = new VehicleXingshizheng();
			vxsz.setAvxAvIds(vd.getVehicleId());
			vxsz.setAvxDelete("0");
			VehicleXingshizheng xingshizheng = xingshizhengService.getOne(Condition.getQueryWrapper(vxsz));
			if(xingshizheng != null) {
				xsz.setAvxIds(xingshizheng.getAvxIds());
				xsz.setAvxUpdateByName(user.getUserName());
				xsz.setAvxUpdateByIds(user.getUserId().toString());
				xsz.setAvxUpdateTime(LocalDateTime.now());
			} else {
				xsz.setAvxCreateByName(user.getUserName());
				xsz.setAvxCreateByIds(user.getUserId().toString());
				xsz.setAvxCreateTime(LocalDateTime.now());
			}
			if (xingshizhengService.saveOrUpdate(xsz)) {
				stringBuilder.append("更新车辆行驶证信息成功！"+"\r\n");
				String vehicleId=vd.getVehicleId();
				riskDetailController.vehicleXingShiZhengRiskinsert(vehicleId,user);

			} else {
				stringBuilder.append("更新车辆行驶证信息失败！"+"\r\n");
			}
		}

    	if(vd.getDaoluyunshuzheng() != null) {
			VehicleDaoluyunshuzheng dlysz = vd.getDaoluyunshuzheng();
			dlysz.setAvdAvIds(vd.getVehicleId());
			dlysz.setAvdPlateNo(vehicleVO.getCheliangpaizhao());
			dlysz.setAvdPlateColor(vehicleVO.getChepaiyanse());
			dlysz.setAvdVehicleType(vehicleVO.getShiyongxingzhi());
			dlysz.setAvdBusinessOwner(vehicleVO.getChezhu());
			dlysz.setAvdDelete("0");

			VehicleDaoluyunshuzheng vdlysz = new VehicleDaoluyunshuzheng();
			vdlysz.setAvdAvIds(vd.getVehicleId());
			vdlysz.setAvdDelete("0");
    		VehicleDaoluyunshuzheng daoluyunshuzheng = daoluyunshuzhengService.getOne(Condition.getQueryWrapper(vdlysz));
    		if(daoluyunshuzheng != null) {
				dlysz.setAvdIds(daoluyunshuzheng.getAvdIds());
				dlysz.setAvdUpdateByName(user.getUserName());
				dlysz.setAvdUpdateByIds(user.getUserId().toString());
				dlysz.setAvdUpdateTime(DateUtil.now());
			} else {
				dlysz.setAvdCreateByName(user.getUserName());
				dlysz.setAvdCreateByIds(user.getUserId().toString());
				dlysz.setAvdCreateTime(DateUtil.now());
			}

			if(daoluyunshuzhengService.saveOrUpdate(dlysz)) {
				stringBuilder.append("更新道路运输证信息成功！"+"\r\n");
				String vehicleId=vd.getVehicleId();
				riskDetailController.vehicleDaoLuYunShuZhengRiskinsert(vehicleId,user);

			} else {
				stringBuilder.append("更新道路运输证信息失败！"+"\r\n");
			}
		}
    	if(vd.getXingnengbaogao() != null) {
    		VehicleXingnengbaogao xlbg = vd.getXingnengbaogao();
			xlbg.setAvxAvIds(vd.getVehicleId());
			xlbg.setAvxDelete("0");

			VehicleXingnengbaogao qXlbg = new VehicleXingnengbaogao();
			qXlbg.setAvxAvIds(vd.getVehicleId());
			qXlbg.setAvxDelete("0");
    		VehicleXingnengbaogao xingnengbaogao = xingnengbaogaoService.getOne(Condition.getQueryWrapper(qXlbg));

    		if(xingnengbaogao != null) {
				xlbg.setAvxIds(xingnengbaogao.getAvxIds());
				xlbg.setAvxUpdateByName(user.getUserName());
				xlbg.setAvxUpdateByIds(user.getUserId().toString());
				xlbg.setAvxUpdateTime(LocalDateTime.now());
			} else {
				xlbg.setAvxCreateByName(user.getUserName());
				xlbg.setAvxCreateByIds(user.getUserId().toString());
				xlbg.setAvxCreateTime(LocalDateTime.now());
			}

			if(xingnengbaogaoService.saveOrUpdate(xlbg)) {
				stringBuilder.append("更新性能报告成功！"+"\r\n");
				String vehicleId=vd.getVehicleId();
				riskDetailController.vehicleXingNengBaoGaoRiskinsert(vehicleId,user);

			} else {
				stringBuilder.append("更新性能报告失败！"+"\r\n");
			}
		}

		if(vd.getJishupingding() != null) {
			VehicleJishupingding jspd = vd.getJishupingding();
			jspd.setAvjVehicleIds(vd.getVehicleId());
			jspd.setAvjDelete("0");

			VehicleJishupingding qJspd = new VehicleJishupingding();
			qJspd.setAvjVehicleIds(vd.getVehicleId());
			qJspd.setAvjDelete("0");
			VehicleJishupingding jishupingding = jishupingdingService.getOne(Condition.getQueryWrapper(qJspd));
			if(jishupingding != null) {
				jspd.setAvjIds(jishupingding.getAvjIds());
				jspd.setAvjUpdateByName(user.getUserName());
				jspd.setAvjUpdateByIds(user.getUserId().toString());
				jspd.setAvjUpdateTime(LocalDateTime.now());
			} else {
				jspd.setAvjCreateByName(user.getUserName());
				jspd.setAvjCreateByIds(user.getUserId().toString());
				jspd.setAvjCreateTime(LocalDateTime.now());
			}
			if(jishupingdingService.saveOrUpdate(jspd)) {
				stringBuilder.append("更新技术评定报告成功！"+"\r\n");
			} else {
				stringBuilder.append("更新技术评定报告失败！"+"\r\n");
			}
		}

		if(vd.getDengjizhengshu() != null) {
			VehicleDengjizhengshu djzs = vd.getDengjizhengshu();
			djzs.setAvdVehicleIds(vd.getVehicleId());
			djzs.setAvdDelete("0");

			VehicleDengjizhengshu qDjzs = new VehicleDengjizhengshu();
			qDjzs.setAvdVehicleIds(vd.getVehicleId());
			qDjzs.setAvdDelete("0");
			VehicleDengjizhengshu dengjizhengshu = dengjizhengshuService.getOne(Condition.getQueryWrapper(qDjzs));
			if(dengjizhengshu != null) {
				djzs.setAvdIds(dengjizhengshu.getAvdIds());
				djzs.setAvdUpdateByName(user.getUserName());
				djzs.setAvdUpdateByIds(user.getUserId().toString());
				djzs.setAvdUpdateTime(LocalDateTime.now());
			} else {
				djzs.setAvdCreateByName(user.getUserName());
				djzs.setAvdCreateByIds(user.getUserId().toString());
				djzs.setAvdCreateTime(LocalDateTime.now());
			}
			if(dengjizhengshuService.saveOrUpdate(djzs)) {
				stringBuilder.append("更新登记证书成功！"+"\r\n");
				String vehicleId=vd.getVehicleId();
				riskDetailController.vehicleDengJiZhengShuRiskinsert(vehicleId,user);

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
			r.setCode(401);
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
	 * 企业端--车辆档案信息--验证
	 * @update: 黄亚平 添加验证
	 */
	@PostMapping("vehicleDeptImport")
	@ApiLog("车辆档案信息--验证(最新版)")
	@ApiOperation(value = "车辆档案信息--验证(最新版)", notes = "file", position = 10)
	public R vehicleDeptImport(@RequestParam(value = "file") MultipartFile file,BladeUser user) throws ParseException {
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
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
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
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
		if(readAll.size() < 1){
			errorStr+="无数据导入，该excel无数据！";
			rs.setMsg(errorStr);
			rs.setCode(500);
			return rs;
		}

		List<Vehicle> vehicles=new ArrayList<Vehicle>();
		for(Map<String,Object> a:readAll){
			aa++;
			Vehicle vehicle= new Vehicle();
			Dept dept;

			//验证所属企业
			String deptname =  String.valueOf(a.get("所属企业")).trim();
			if(StringUtils.isBlank(deptname)){
				vehicle.setMsg("所属企业不能为空;");
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
				vehicle.setMsg(deptname+"所属企业不存在;");
				vehicle.setImportUrl("icon_cha.png");
				errorStr+=cph+"----"+deptname+"所属企业不存在;";
				bb++;
			}

			//验证车牌号码
			String cheliangpaiz=String.valueOf(a.get("车牌号码")).trim();
			if(StringUtils.isBlank(cheliangpaiz) && !cheliangpaiz.equals("null")){
				errorStr+="车牌号码不能为空;";
				vehicle.setMsg("车牌号码不能为空;");
				vehicle.setImportUrl("icon_cha.png");
				bb++;
			}else{
				vehicle.setCheliangpaizhao(cheliangpaiz);
				vehicle.setImportUrl("icon_gou.png");
			}

			//车牌颜色
			String chepaiyanse = String.valueOf(a.get("车牌颜色")).trim();
			if(StringUtils.isNotBlank(chepaiyanse) && !chepaiyanse.equals("null")){
				vehicle.setChepaiyanse(chepaiyanse);
			}

			//验证电话
			String phoneNumber = String.valueOf(a.get("联系电话")).trim();
			if(StringUtils.isNotBlank(phoneNumber) && !phoneNumber.equals("null")){
				if (RegexUtils.checkMobile(phoneNumber)){
					vehicle.setJiashiyuandianhua(phoneNumber);
				}else {
					errorStr+=phoneNumber+"该号码不合法;";
					vehicle.setMsg(phoneNumber+"该号码不合法;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证驾驶员
			String jiashiyuan = String.valueOf(a.get("驾驶员")).trim();
			if(StringUtils.isNotBlank(jiashiyuan) && !jiashiyuan.equals("null")){
//				QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
//				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId,vehicle.getDeptId());
//				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShoujihaoma,vehicle.getJiashiyuandianhua());
//				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getJiashiyuanxingming,jiashiyuan);
//				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,0);
//				JiaShiYuan jiaShiYuan = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
//				if (jiaShiYuan != null){
//					vehicle.setJiashiyuanid(jiaShiYuan.getId());
					vehicle.setJiashiyuanxingming(jiashiyuan);
//				}else {
//					errorStr+=jiashiyuan+"该驾驶员不存在;";
//					vehicle.setMsg(jiashiyuan+"该驾驶员不存在;");
//					vehicle.setImportUrl("icon_cha.png");
//					bb++;
//				}
			}

			//验证行驶证注册日期
			String avxRegisterDate = String.valueOf(a.get("行驶证注册日期")).trim();
			if(StringUtils.isNotBlank(avxRegisterDate) && !avxRegisterDate.equals("null")){
				String s = DateUtils.formatDateZero(avxRegisterDate);
				if (DateUtils.isDateString(s, null) == true){
					LocalDate parse = LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					vehicle.setAvxRegisterDate(parse);
				}else {
					errorStr+=avxRegisterDate+"该行驶证注册日期不是时间格式;";
					vehicle.setMsg(avxRegisterDate+"该行驶证注册日期不是时间格式;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证行驶证发证日期
			String avxIssueDate = String.valueOf(a.get("行驶证发证日期")).trim();
			if(StringUtils.isNotBlank(avxIssueDate) && !avxIssueDate.equals("null")){
				String s = DateUtils.formatDateZero(avxIssueDate);
				if (DateUtils.isDateString(s, null) == true){
					LocalDate parse = LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					vehicle.setAvxIssueDate(parse);
				}else {
					errorStr+=avxIssueDate+"该行驶证发证日期不是时间格式;";
					vehicle.setMsg(avxIssueDate+"该行驶证发证日期不是时间格式;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证行驶证：结束日期
			String xingshizhengdaoqishijian = String.valueOf(a.get("行驶证：结束日期")).trim();
			if(StringUtils.isNotBlank(xingshizhengdaoqishijian) && !xingshizhengdaoqishijian.equals("null")){
				String s = DateUtils.formatDateZero(xingshizhengdaoqishijian);
				if (DateUtils.isDateString(s, null) == true){
					vehicle.setXingshizhengdaoqishijian(s);
				}else {
					errorStr+=xingshizhengdaoqishijian+"该行驶证：结束日期不是时间格式;";
					vehicle.setMsg(xingshizhengdaoqishijian+"该行驶证：结束日期不是时间格式;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证强制报废日期
			String qiangzhibaofeishijian = String.valueOf(a.get("强制报废日期")).trim();
			if(StringUtils.isNotBlank(qiangzhibaofeishijian) && !qiangzhibaofeishijian.equals("null")){
				String s = DateUtils.formatDateZero(qiangzhibaofeishijian);
				if (DateUtils.isDateString(s, null) == true){
					vehicle.setQiangzhibaofeishijian(s);
				}else {
					errorStr+=qiangzhibaofeishijian+"该强制报废日期不是时间格式;";
					vehicle.setMsg(qiangzhibaofeishijian+"该强制报废日期不是时间格式;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证车辆类型
			String cheliangleixing = String.valueOf(a.get("车辆类型")).trim();
			if(StringUtils.isNotBlank(cheliangleixing) && !cheliangleixing.equals("null")){
//				if (cheliangleixing.equals("重型半挂牵引车") || cheliangleixing.equals("重型集装箱半挂车")){
					vehicle.setXinghao(cheliangleixing);
//				} else{
//					vehicle.setImportUrl("icon_cha.png");
//					errorStr += cheliangpaiz+",车辆类型输入错误,请校验”;";
//					vehicle.setMsg(cheliangpaiz+",车辆类型输入错误,请校验;");
//					bb++;
//				}
			}

			//验证车辆型号
			String cheliangxinghao = String.valueOf(a.get("车辆型号")).trim();
			if(StringUtils.isNotBlank(cheliangxinghao) && !cheliangxinghao.equals("null")){
				vehicle.setCheliangleixing(cheliangxinghao);
			}

			//验证车辆所有人
			String owner = String.valueOf(a.get("车辆所有人")).trim();
			if(StringUtils.isNotBlank(owner) && !owner.equals("null")){
				vehicle.setOwner(owner);
			}else {
				vehicle.setOwner(vehicle.getDeptName());
			}

			//验证地址
			String dizhi = String.valueOf(a.get("地址")).trim();
			if(StringUtils.isNotBlank(dizhi) && !dizhi.equals("null")){
				vehicle.setCarowneraddress(dizhi);
			}

			//验证车辆品牌
			String model = String.valueOf(a.get("车辆品牌")).trim();
			if(StringUtils.isNotBlank(model) && !model.equals("null")){
				vehicle.setCheliangpinpai(model);
			}

			//验证使用性质
			String natureOfUse = String.valueOf(a.get("使用性质")).trim();
			if(StringUtils.isNotBlank(natureOfUse) && !natureOfUse.equals("null")){
				vehicle.setShiyongxingzhi(natureOfUse);
			}else {
				vehicle.setShiyongxingzhi("货运");
			}

			//验证档案编号
			String fileNumber = String.valueOf(a.get("档案编号")).trim();
			if(StringUtils.isNotBlank(fileNumber) && !fileNumber.equals("null")){
				vehicle.setFileNo(fileNumber);
			}

			//验证品牌型号
			String brandModel = String.valueOf(a.get("品牌型号")).trim();
			if(StringUtils.isNotBlank(brandModel) && !brandModel.equals("null")){
				vehicle.setBrandModel(brandModel);
			}

			//验证车辆识别代码
			String cheliangshibiedaima = String.valueOf(a.get("车辆识别代号")).trim();
			if(StringUtils.isNotBlank(cheliangshibiedaima) && !cheliangshibiedaima.equals("null")){
				vehicle.setCheliangshibiedaima(cheliangshibiedaima);
			}

			//验证车身颜色
			String cheshenyanse = String.valueOf(a.get("车身颜色")).trim();
			if(StringUtils.isNotBlank(cheshenyanse) && !cheshenyanse.equals("null")){
				if (cheshenyanse.equals("黄色") || cheshenyanse.equals("蓝色") || cheshenyanse.equals("白色") || cheshenyanse.equals("红色") || cheshenyanse.equals("绿色")){
					vehicle.setCheshenyanse(cheshenyanse);
				}else {
					errorStr+=cheshenyanse+"------车身颜色应为黄色、蓝色、白色、红色或绿色;";
					vehicle.setMsg(cheshenyanse+"------车身颜色应为黄色、蓝色、白色、红色或绿色;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证经营许可证号
			String jingyingxukezhenghao = String.valueOf(a.get("经营许可证号")).trim();
			if(StringUtils.isNotBlank(jingyingxukezhenghao) && !jingyingxukezhenghao.equals("null")){
				vehicle.setDaoluyunshuzheng(jingyingxukezhenghao);
			}

			//验证经济类型
			String jingjileixing = String.valueOf(a.get("经济类型")).trim();
			if(StringUtils.isNotBlank(jingjileixing) && !jingjileixing.equals("null")){
				if (jingjileixing.equals("有限责任公司") || jingjileixing.equals("有限公司") ||jingjileixing.equals("股份有限公司") ||jingjileixing.equals("集体所有制") ||jingjileixing.equals("股份合作制") ||jingjileixing.equals("国有企业") ||jingjileixing.equals("个体工商户") ||jingjileixing.equals("个人独资企业") || jingjileixing.equals("有限合伙") ||jingjileixing.equals("普通合伙") ||jingjileixing.equals("外商投资企业") ||jingjileixing.equals("港") ||jingjileixing.equals("澳") ||jingjileixing.equals("台") ||jingjileixing.equals("联营企业") ||jingjileixing.equals("私营企业") ){
					vehicle.setJingjileixing(jingjileixing);
				}else {
					errorStr+=jingjileixing+"------经济类型应为有限责任公司、有限公司、股份有限公司、集体所有制、股份合作制、国有企业、个体工商户、个人独资企业、有限合伙、普通合伙、外商投资企业、港、澳、台、联营企业或私营企业;";
					vehicle.setMsg(jingjileixing+"------经济类型应为有限责任公司、有限公司、股份有限公司、集体所有制、股份合作制、国有企业、个体工商户、个人独资企业、有限合伙、普通合伙、外商投资企业、港、澳、台、联营企业或私营企业;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证经营组织方式
			String jingyingzuzhifangshi = String.valueOf(a.get("经营组织方式")).trim();
			if(StringUtils.isNotBlank(jingyingzuzhifangshi) && !jingyingzuzhifangshi.equals("null")){
				if (jingyingzuzhifangshi.equals("私营") || jingyingzuzhifangshi.equals("国营")){
					vehicle.setJingyingzuzhifangshi(jingyingzuzhifangshi);
				}else {
					errorStr+=jingyingzuzhifangshi+"------经营组织方式应为国营或私营;";
					vehicle.setMsg(jingyingzuzhifangshi+"------经营组织方式应为国营或私营;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证经营范围
			String jingyingfanwei = String.valueOf(a.get("经营范围")).trim();
			if(StringUtils.isNotBlank(jingyingfanwei) && !jingyingfanwei.equals("null")){
					vehicle.setCheliangyunyingleixing(jingyingfanwei);
			}else {
				vehicle.setCheliangyunyingleixing("普通货运");
			}

			//验证运输路线
			String yunshuluxian = String.valueOf(a.get("运输路线")).trim();
			if(StringUtils.isNotBlank(yunshuluxian) && !yunshuluxian.equals("null")){
				vehicle.setTeamno(yunshuluxian);
			}

			//验证车辆获得方式
			String chelianghuodefangshi = String.valueOf(a.get("车辆获得方式")).trim();
			if(StringUtils.isNotBlank(chelianghuodefangshi) && !chelianghuodefangshi.equals("null")){
				vehicle.setChelianghuoqufangshi(chelianghuodefangshi);
			}else {
				vehicle.setChelianghuoqufangshi("购买");
			}

			//验证维护周期
			String weihuzhouqi = String.valueOf(a.get("维护周期")).trim();
			if(StringUtils.isNotBlank(weihuzhouqi) && !weihuzhouqi.equals("null")){
				vehicle.setWeihuzhouqi(weihuzhouqi);
			}

			//验证车架号
			String chejiahao = String.valueOf(a.get("车架号")).trim();
			if(StringUtils.isNotBlank(chejiahao) && !chejiahao.equals("null")){
				vehicle.setChejiahao(chejiahao);
			}

			//验证国产/进口
			String guochanjinkou = String.valueOf(a.get("国产/进口")).trim();
			if(StringUtils.isNotBlank(guochanjinkou) && !guochanjinkou.equals("null")){
				if (guochanjinkou.equals("国产")){
					vehicle.setShifoujinkou("0");
				}else if(guochanjinkou.equals("进口")){
					vehicle.setShifoujinkou("1");
				}
			}

			//验证燃料种类
			String ranliaozhonglei = String.valueOf(a.get("燃料种类")).trim();
			if(StringUtils.isNotBlank(ranliaozhonglei) && !ranliaozhonglei.equals("null")){
				vehicle.setRanliaoleibie(ranliaozhonglei);
			}

			//验证排量/功率
			String pailianggonglv = String.valueOf(a.get("排量/功率")).trim();
			if(StringUtils.isNotBlank(pailianggonglv) && !pailianggonglv.equals("null")){
				vehicle.setFadongjipailianggonglv(pailianggonglv);
			}

			//验证转向形式
			String zhuanxiangxingshi = String.valueOf(a.get("转向形式")).trim();
			if(StringUtils.isNotBlank(zhuanxiangxingshi) && !zhuanxiangxingshi.equals("null")){
				vehicle.setZhuanxiangfangshi(zhuanxiangxingshi);
			}

			//验证制造厂名称
			String zhizaochangmingcheng = String.valueOf(a.get("制造厂名称")).trim();
			if(StringUtils.isNotBlank(zhizaochangmingcheng) && !zhizaochangmingcheng.equals("null")){
				vehicle.setZhizhaochangshang(zhizaochangmingcheng);
			}

			//验证前轮距
			String lunju = String.valueOf(a.get("前轮距")).trim();
			if(StringUtils.isNotBlank(lunju) && !lunju.equals("null")){
				vehicle.setLunju(lunju);
			}

			//验证后轮距
			String frontlunju = String.valueOf(a.get("后轮距")).trim();
			if(StringUtils.isNotBlank(frontlunju) && !frontlunju.equals("null")){
				vehicle.setFrontlunju(frontlunju);
			}

			//验证轮胎数
			String luntaishu = String.valueOf(a.get("轮胎数")).trim();
			if(StringUtils.isNotBlank(luntaishu) && !luntaishu.equals("null")){
				vehicle.setLuntaishu(luntaishu);
			}

			//验证轮胎规格
			String luntaiguige = String.valueOf(a.get("轮胎规格")).trim();
			if(StringUtils.isNotBlank(luntaiguige) && !luntaiguige.equals("null")){
				vehicle.setLuntaiguige(luntaiguige);
			}

			//验证钢板弹簧片数
			String gangbantanhuangpianshu = String.valueOf(a.get("钢板弹簧片数")).trim();
			if(StringUtils.isNotBlank(gangbantanhuangpianshu) && !gangbantanhuangpianshu.equals("null")){
				vehicle.setGangbantanhuangpianshu(gangbantanhuangpianshu);
			}

			//验证轴距
			String zhouju = String.valueOf(a.get("轴距")).trim();
			if(StringUtils.isNotBlank(zhouju) && !zhouju.equals("null")){
				vehicle.setZhouju(zhouju);
			}

			//验证轴数
			String zhoushu = String.valueOf(a.get("轴数")).trim();
			if(StringUtils.isNotBlank(zhoushu) && !zhoushu.equals("null")){
				vehicle.setChezhoushu(zhoushu);
			}

			//验证货箱内部尺寸
			String huoxiangneibuchicun = String.valueOf(a.get("货箱内部尺寸")).trim();
			if(StringUtils.isNotBlank(huoxiangneibuchicun) && !huoxiangneibuchicun.equals("null")){
				vehicle.setHuoxiangneibuchicun(huoxiangneibuchicun);
			}

			//验证核定载人数
			String hedingzairenshu = String.valueOf(a.get("核定载人数")).trim();
			if(StringUtils.isNotBlank(hedingzairenshu) && !hedingzairenshu.equals("null")){
				if(StringUtils.isNumeric(hedingzairenshu)){
					vehicle.setHedingzaike(hedingzairenshu);
				}else {
					vehicle.setHedingzaike("0");
				}
			}

			//验证驾驶室载客
			String jiashishizaike = String.valueOf(a.get("驾驶室载客")).trim();
			if(StringUtils.isNotBlank(jiashishizaike) && !jiashishizaike.equals("null")){
				if(StringUtils.isNumeric(jiashishizaike)){
					vehicle.setJiashishizaike(jiashishizaike);
				}else {
					vehicle.setJiashishizaike("0");
				}
			}

			//验证总质量
			String zongzhiliang = String.valueOf(a.get("总质量")).trim();
			if(StringUtils.isNotBlank(zongzhiliang) && !zongzhiliang.equals("null")){
				zongzhiliang=zongzhiliang.trim();
				String zongzhiliang2="";
				if(zongzhiliang != null && !"".equals(zongzhiliang)){
					for(int i = 0; i < zongzhiliang.length(); i++){
						if(zongzhiliang.charAt(i) >= 48 && zongzhiliang.charAt(i) <= 57){
							zongzhiliang2 += zongzhiliang.charAt(i);
						}
					}
					vehicle.setZongzhiliang(zongzhiliang2);
				}
			}

			//验证核定载质量
			String hedingzaizhiliang = String.valueOf(a.get("核定载质量")).trim();
			if(StringUtils.isNotBlank(hedingzaizhiliang) && !hedingzaizhiliang.equals("null")){
				hedingzaizhiliang=hedingzaizhiliang.trim();
				String hedingzaizhiliang2="";
				if(hedingzaizhiliang != null && !"".equals(hedingzaizhiliang)){
					for(int i = 0; i < hedingzaizhiliang.length(); i++){
						if(hedingzaizhiliang.charAt(i) >= 48 && hedingzaizhiliang.charAt(i) <= 57){
							hedingzaizhiliang2 += hedingzaizhiliang.charAt(i);
						}
					}
					vehicle.setHedingzaizhiliang(hedingzaizhiliang2);
				}
			}

			//验证准牵引总质量
			String zhunqianyinzongzhiliang = String.valueOf(a.get("准牵引总质量")).trim();
			if(StringUtils.isNotBlank(zhunqianyinzongzhiliang) && !zhunqianyinzongzhiliang.equals("null")){
				zhunqianyinzongzhiliang=zhunqianyinzongzhiliang.trim();
				String zhunqianyinzongzhiliang2="";
				if(zhunqianyinzongzhiliang != null && !"".equals(zhunqianyinzongzhiliang)){
					for(int i = 0; i < zhunqianyinzongzhiliang.length(); i++){
						if(zhunqianyinzongzhiliang.charAt(i) >= 48 && zhunqianyinzongzhiliang.charAt(i) <= 57){
							zhunqianyinzongzhiliang2 += zhunqianyinzongzhiliang.charAt(i);
						}
					}
					vehicle.setZhunqianyinzongzhiliang(zhunqianyinzongzhiliang2);
				}
			}

			//验证外廓尺寸
			String waikuochicun = String.valueOf(a.get("外廓尺寸")).trim();
			if(StringUtils.isNotBlank(waikuochicun) && !waikuochicun.equals("null")){
				vehicle.setCheliangwaikuochicun(waikuochicun);
			}

			//验证车辆出厂日期
			String cheliangchuchangriqi = String.valueOf(a.get("车辆出厂日期")).trim();
			if(StringUtils.isNotBlank(cheliangchuchangriqi) && !cheliangchuchangriqi.equals("null")){
				String s = DateUtils.formatDateZero(cheliangchuchangriqi);
				if (DateUtils.isDateString(s, null) == true){
					vehicle.setChuchangriqi(s);
				}else {
					errorStr+=qiangzhibaofeishijian+"该车辆出厂日期不是时间格式;";
					vehicle.setMsg(qiangzhibaofeishijian+"该车辆出厂日期不是时间格式;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证综合性能检测、技术等级评定信息:开始日期
			String bencijipingriqi = String.valueOf(a.get("综合性能检测、技术等级评定信息:开始日期")).trim();
			if(StringUtils.isNotBlank(bencijipingriqi) && !bencijipingriqi.equals("null")){
				String s = DateUtils.formatDateZero(bencijipingriqi);
				if (DateUtils.isDateString(s, null) == true){
					vehicle.setBencijipingriqi(s);
				}else {
					errorStr+=qiangzhibaofeishijian+"该车辆出厂日期不是时间格式;";
					vehicle.setMsg(qiangzhibaofeishijian+"该车辆出厂日期不是时间格式;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证道路运输证:证件号码
			String daolunyunshuzhenghao = String.valueOf(a.get("道路运输证:证件号码")).trim();
			if(StringUtils.isNotBlank(daolunyunshuzhenghao) && !daolunyunshuzhenghao.equals("null")){
				vehicle.setDaoluyunshuzhenghao(daolunyunshuzhenghao);
			}

			//验证道路运输证:开始日期
			String daoluyunshuzhengchulingriqi = String.valueOf(a.get("道路运输证:开始日期")).trim();
			if(StringUtils.isNotBlank(daoluyunshuzhengchulingriqi) && !daoluyunshuzhengchulingriqi.equals("null")){
				String s = DateUtils.formatDateZero(daoluyunshuzhengchulingriqi);
				if (DateUtils.isDateString(s, null) == true){
					vehicle.setDaoluyunshuzhengchulingriqi(s);
				}else {
					errorStr+=daoluyunshuzhengchulingriqi+"该道路运输证:开始日期不是时间格式;";
					vehicle.setMsg(daoluyunshuzhengchulingriqi+"该道路运输证:开始日期不是时间格式;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证道路运输证:结束日期
			String daoluyunshuzhengyouxiaoqi = String.valueOf(a.get("道路运输证:结束日期")).trim();
			if(StringUtils.isNotBlank(daoluyunshuzhengyouxiaoqi) && !daoluyunshuzhengyouxiaoqi.equals("null")){
				String s = DateUtils.formatDateZero(daoluyunshuzhengchulingriqi);
				if (DateUtils.isDateString(s, null) == true){
					vehicle.setDaoluyunshuzhengyouxiaoqi(s);
				}else {
					errorStr+=daoluyunshuzhengyouxiaoqi+"该道路运输证:结束日期不是时间格式;";
					vehicle.setMsg(daoluyunshuzhengyouxiaoqi+"该道路运输证:结束日期不是时间格式;");
					vehicle.setImportUrl("icon_cha.png");
					bb++;
				}
			}

//			//验证车牌颜色
//			String chepaiyanse=String.valueOf(a.get("车牌颜色")).trim();
//			if(StringUtils.isBlank(chepaiyanse) && !chepaiyanse.equals("null")){
//				vehicle.setMsg("车牌颜色不能为空;");
//				errorStr+="车牌颜色不能为空;";
//				vehicle.setImportUrl("icon_cha.png");
//				bb++;
//			}else{
//				boolean ss = false;
//				List<Dict> dictVOList = iDictClient.getDictByCode("colour",null);
//				for(int i= 0;i<dictVOList.size();i++){
//					ss = dictVOList.get(i).getDictValue().equals(chepaiyanse);
//					if(ss == true){
//						break;
//					}
//				}
//				if(ss == true){
//					dictVOList = iDictClient.getDictByCode("colour",chepaiyanse);
//					vehicle.setImportUrl("icon_gou.png");
//					vehicle.setChepaiyanse(dictVOList.get(0).getDictKey());
//				}else{
//					vehicle.setImportUrl("icon_cha.png");
//					errorStr += cheliangpaiz+",车牌颜色输入错误,请校验”;";
//					vehicle.setMsg(cheliangpaiz+",车牌颜色输入错误,请校验;");
//					bb++;
//				}
//			}

			//验证Excel导入时，是否存在重复数据
			for (Vehicle item : vehicles) {
				if (item.getCheliangpaizhao().equals(cheliangpaiz)) {
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz + "车牌号重复；";
					vehicle.setMsg(cheliangpaiz + "车牌号重复；");
					bb++;
				}
			}

			//运营状态
			vehicle.setCheliangzhuangtai("0");

//			//验证车主、车主电话
//			String czname = String.valueOf(a.get("车主姓名"));
//			String czphone = String.valueOf(a.get("车主电话"));
//			if(!StringUtils.isBlank(czphone) && !czphone.equals("null")){
//				if(CheckPhoneUtil.isChinaPhoneLegal(czphone) == false){
//					vehicle.setMsg("车主电话格式不正确;");
//					errorStr+=czphone+"车主电话格式不正确;";
//					vehicle.setImportUrl("icon_cha.png");
//					vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
//					bb++;
//				}else{
//					vehicle.setImportUrl("icon_gou.png");
//					vehicle.setChezhudianhua(String.valueOf(a.get("车主电话")).trim());
//					vehicle.setJiashiyuandianhua(String.valueOf(a.get("车主电话")).trim());
//				}
//			}
//			if(StringUtils.isBlank(czname) || czname.equals("null")){
//				vehicle.setChezhu("");
//			}else{
//				//根据企业ID、驾驶员姓名、驾驶员电话查询驾驶员信息是否存在
//				JiaShiYuan jiaShiYuan = iJiaShiYuanService.getjiaShiYuanByOne(vehicle.getDeptId().toString(), czname, czphone,null,"驾驶员");
//				if (jiaShiYuan != null) {
//					vehicle.setImportUrl("icon_gou.png");
//					vehicle.setJiashiyuanid(jiaShiYuan.getId());
//					vehicle.setChezhu(String.valueOf(a.get("车主姓名")).trim());
//					vehicle.setJiashiyuanxingming(String.valueOf(a.get("车主姓名")).trim());
//				}else{
//					vehicle.setImportUrl("icon_cha.png");
//					errorStr += cheliangpaiz+",该车的车主不存在,请校验”;";
//					vehicle.setMsg(cheliangpaiz+",该车的车主不存在,请校验;");
//					bb++;
//				}
//			}

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
			rs.setCode(401);
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
			if (StringUtils.isNotBlank(String.valueOf(a.get("chepaiyanse")).trim())  && !String.valueOf(a.get("chepaiyanse")).equals("null")) {
				vehicle.setChepaiyanse(String.valueOf(a.get("chepaiyanse")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("shiyongxingzhi")).trim())  && !String.valueOf(a.get("shiyongxingzhi")).equals("null")) {
				vehicle.setShiyongxingzhi(String.valueOf(a.get("shiyongxingzhi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("xinghao")).trim())  && !String.valueOf(a.get("xinghao")).equals("null")) {
				vehicle.setXinghao(String.valueOf(a.get("xinghao")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("chejiahao")).trim())  && !String.valueOf(a.get("chejiahao")).equals("null")) {
				vehicle.setChejiahao(String.valueOf(a.get("chejiahao")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("luntaiguige")).trim())  && !String.valueOf(a.get("luntaiguige")).equals("null")) {
				vehicle.setLuntaiguige(String.valueOf(a.get("luntaiguige")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("cheshenyanse")).trim())  && !String.valueOf(a.get("cheshenyanse")).equals("null")) {
				vehicle.setCheshenyanse(String.valueOf(a.get("cheshenyanse")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("hedingzaike")).trim())  && !String.valueOf(a.get("hedingzaike")).equals("null")) {
				vehicle.setHedingzaike(String.valueOf(a.get("hedingzaike")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("qiangzhibaofeishijian")).trim())  && !String.valueOf(a.get("qiangzhibaofeishijian")).equals("null")) {
				vehicle.setQiangzhibaofeishijian(String.valueOf(a.get("qiangzhibaofeishijian")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("qiangzhibaofeishijian")).trim())  && !String.valueOf(a.get("qiangzhibaofeishijian")).equals("null")) {
				vehicle.setBaofeiriqi(String.valueOf(a.get("qiangzhibaofeishijian")).trim());
			}
			vehicle.setCheliangzhuangtai("0");
			if (StringUtils.isNotBlank(String.valueOf(a.get("fadongjipailianggonglv")).trim())  && !String.valueOf(a.get("fadongjipailianggonglv")).equals("null")) {
				vehicle.setFadongjipailianggonglv(String.valueOf(a.get("fadongjipailianggonglv")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("ranliaoleibie")).trim())  && !String.valueOf(a.get("ranliaoleibie")).equals("null")) {
				vehicle.setRanliaoleibie(String.valueOf(a.get("ranliaoleibie")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("zhuanxiangfangshi")).trim())  && !String.valueOf(a.get("zhuanxiangfangshi")).equals("null")) {
				vehicle.setZhuanxiangfangshi(String.valueOf(a.get("zhuanxiangfangshi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("zhouju")).trim())  && !String.valueOf(a.get("zhouju")).equals("null")) {
				vehicle.setZhouju(String.valueOf(a.get("zhouju")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("luntaishu")).trim())  && !String.valueOf(a.get("luntaishu")).equals("null")) {
				vehicle.setLuntaishu(String.valueOf(a.get("luntaishu")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("chezhoushu")).trim())  && !String.valueOf(a.get("chezhoushu")).equals("null")) {
				vehicle.setChezhoushu(String.valueOf(a.get("chezhoushu")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("gangbantanhuangpianshu")).trim())  && !String.valueOf(a.get("gangbantanhuangpianshu")).equals("null")) {
				vehicle.setGangbantanhuangpianshu(String.valueOf(a.get("gangbantanhuangpianshu")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("zongzhiliang")).trim())  && !String.valueOf(a.get("zongzhiliang")).equals("null")) {
				vehicle.setZongzhiliang(String.valueOf(a.get("zongzhiliang")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("zhizhaochangshang")).trim())  && !String.valueOf(a.get("zhizhaochangshang")).equals("null")) {
				vehicle.setZhizhaochangshang(String.valueOf(a.get("zhizhaochangshang")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("chuchangriqi")).trim())  && !String.valueOf(a.get("chuchangriqi")).equals("null")) {
				vehicle.setChuchangriqi(String.valueOf(a.get("chuchangriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("cheliangwaikuochicun")).trim())  && !String.valueOf(a.get("cheliangwaikuochicun")).equals("null")) {
				vehicle.setCheliangwaikuochicun(String.valueOf(a.get("cheliangwaikuochicun")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuanxingming")).trim())  && !String.valueOf(a.get("jiashiyuanxingming")).equals("null")) {
				vehicle.setJiashiyuanxingming(String.valueOf(a.get("jiashiyuanxingming")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuanxingming")).trim())  && !String.valueOf(a.get("jiashiyuanxingming")).equals("null")) {
				vehicle.setChezhu(String.valueOf(a.get("jiashiyuanxingming")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuandianhua")).trim())  && !String.valueOf(a.get("jiashiyuandianhua")).equals("null")) {
				vehicle.setJiashiyuandianhua(String.valueOf(a.get("jiashiyuandianhua")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuandianhua")).trim())  && !String.valueOf(a.get("jiashiyuandianhua")).equals("null")) {
				vehicle.setChezhudianhua(String.valueOf(a.get("jiashiyuandianhua")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("daoluyunshuzheng")).trim())  && !String.valueOf(a.get("daoluyunshuzheng")).equals("null")) {
				vehicle.setDaoluyunshuzheng(String.valueOf(a.get("daoluyunshuzheng")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("daoluyunshuzhengchulingriqi")).trim())  && !String.valueOf(a.get("daoluyunshuzhengchulingriqi")).equals("null")) {
				vehicle.setDaoluyunshuzhengchulingriqi(String.valueOf(a.get("daoluyunshuzhengchulingriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("daoluyunshuzhengyouxiaoqi")).trim())  && !String.valueOf(a.get("daoluyunshuzhengyouxiaoqi")).equals("null")) {
				vehicle.setDaoluyunshuzhengyouxiaoqi(String.valueOf(a.get("daoluyunshuzhengyouxiaoqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("daoluyunshuzhenghao")).trim())  && !String.valueOf(a.get("daoluyunshuzhenghao")).equals("null")) {
				vehicle.setDaoluyunshuzhenghao(String.valueOf(a.get("daoluyunshuzhenghao")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("bencijipingriqi")).trim())  && !String.valueOf(a.get("bencijipingriqi")).equals("null")) {
				vehicle.setBencijipingriqi(String.valueOf(a.get("bencijipingriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("teamno")).trim())  && !String.valueOf(a.get("teamno")).equals("null")) {
				vehicle.setTeamno(String.valueOf(a.get("teamno")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("xingshizhengdaoqishijian")).trim())  && !String.valueOf(a.get("xingshizhengdaoqishijian")).equals("null")) {
				vehicle.setXingshizhengdaoqishijian(String.valueOf(a.get("xingshizhengdaoqishijian")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("avxRegisterDate")).trim())  && !String.valueOf(a.get("avxRegisterDate")).equals("null")) {
				vehicle.setAvxRegisterDate(LocalDate.parse(String.valueOf(a.get("avxRegisterDate")).trim()));
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("avxIssueDate")).trim())  && !String.valueOf(a.get("avxIssueDate")).equals("null")) {
				vehicle.setAvxIssueDate(LocalDate.parse(String.valueOf(a.get("avxIssueDate")).trim()));
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("carowneraddress")).trim())  && !String.valueOf(a.get("carowneraddress")).equals("null")) {
				vehicle.setCarowneraddress(String.valueOf(a.get("carowneraddress")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("chelianghuoqufangshi")).trim())  && !String.valueOf(a.get("chelianghuoqufangshi")).equals("null")) {
				vehicle.setChelianghuoqufangshi(String.valueOf(a.get("chelianghuoqufangshi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("weihuzhouqi")).trim())  && !String.valueOf(a.get("weihuzhouqi")).equals("null")) {
				vehicle.setWeihuzhouqi(String.valueOf(a.get("weihuzhouqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("cheliangleixing")).trim())  && !String.valueOf(a.get("cheliangleixing")).equals("null")) {
				vehicle.setCheliangleixing(String.valueOf(a.get("cheliangleixing")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("cheliangpinpai")).trim())  && !String.valueOf(a.get("cheliangpinpai")).equals("null")) {
				vehicle.setCheliangpinpai(String.valueOf(a.get("cheliangpinpai")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("shifoujinkou")).trim())  && !String.valueOf(a.get("shifoujinkou")).equals("null")) {
				vehicle.setShifoujinkou(String.valueOf(a.get("shifoujinkou")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("lunju")).trim())  && !String.valueOf(a.get("lunju")).equals("null")) {
				vehicle.setLunju(String.valueOf(a.get("lunju")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("frontlunju")).trim())  && !String.valueOf(a.get("frontlunju")).equals("null")) {
				vehicle.setFrontlunju(String.valueOf(a.get("frontlunju")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("huoxiangneibuchicun")).trim())  && !String.valueOf(a.get("huoxiangneibuchicun")).equals("null")) {
				vehicle.setHuoxiangneibuchicun(String.valueOf(a.get("huoxiangneibuchicun")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("hedingzaizhiliang")).trim())  && !String.valueOf(a.get("hedingzaizhiliang")).equals("null")) {
				vehicle.setHedingzaizhiliang(String.valueOf(a.get("hedingzaizhiliang")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashishizaike")).trim())  && !String.valueOf(a.get("jiashishizaike")).equals("null")) {
				vehicle.setJiashishizaike(String.valueOf(a.get("jiashishizaike")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("cheliangyunyingleixing")).trim())  && !String.valueOf(a.get("cheliangyunyingleixing")).equals("null")) {
				vehicle.setCheliangyunyingleixing(String.valueOf(a.get("cheliangyunyingleixing")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jingjileixing")).trim())  && !String.valueOf(a.get("jingjileixing")).equals("null")) {
				vehicle.setJingjileixing(String.valueOf(a.get("jingjileixing")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jingyingzuzhifangshi")).trim())  && !String.valueOf(a.get("jingyingzuzhifangshi")).equals("null")) {
				vehicle.setJingyingzuzhifangshi(String.valueOf(a.get("jingyingzuzhifangshi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("owner")).trim())  && !String.valueOf(a.get("owner")).equals("null")) {
				vehicle.setOwner(String.valueOf(a.get("owner")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("fileNo")).trim())  && !String.valueOf(a.get("fileNo")).equals("null")) {
				vehicle.setFileNo(String.valueOf(a.get("fileNo")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("brandModel")).trim())  && !String.valueOf(a.get("brandModel")).equals("null")) {
				vehicle.setBrandModel(String.valueOf(a.get("brandModel")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("cheliangshibiedaima")).trim())  && !String.valueOf(a.get("cheliangshibiedaima")).equals("null")) {
				vehicle.setCheliangshibiedaima(String.valueOf(a.get("cheliangshibiedaima")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("zhunqianyinzongzhiliang")).trim())  && !String.valueOf(a.get("zhunqianyinzongzhiliang")).equals("null")) {
				vehicle.setZhunqianyinzongzhiliang(String.valueOf(a.get("zhunqianyinzongzhiliang")).trim());
			}
//			vehicle.setCheliangzhuangtai("0");
//			if (StringUtils.isNotBlank(String.valueOf(a.get("xinghao")).trim()) && !String.valueOf(a.get("xinghao")).equals("null")) {
//				vehicle.setXinghao(String.valueOf(a.get("xinghao")).trim());
//				vehicle.setCheliangleixing(vehicle.getXinghao());
//			}
//			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuanxingming")).trim()) && !String.valueOf(a.get("jiashiyuanxingming")).equals("null")) {
//				vehicle.setJiashiyuanxingming(String.valueOf(a.get("jiashiyuanxingming")).trim());
//			}
//			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuandianhua")).trim()) && !String.valueOf(a.get("jiashiyuandianhua")).equals("null")) {
//				vehicle.setJiashiyuandianhua(String.valueOf(a.get("jiashiyuandianhua")).trim());
//			}
//			if (StringUtils.isNotBlank(String.valueOf(a.get("chezhu")).trim()) && !String.valueOf(a.get("chezhu")).equals("null")) {
//				vehicle.setChezhu(String.valueOf(a.get("chezhu")).trim());
//			}
//			if (StringUtils.isNotBlank(String.valueOf(a.get("chezhudianhua")).trim()) && !String.valueOf(a.get("chezhudianhua")).equals("null")) {
//				vehicle.setChezhudianhua(String.valueOf(a.get("chezhudianhua")).trim());
//			}
			if (user != null) {
				vehicle.setCaozuoren(user.getUserName());
				vehicle.setCaozuorenid(user.getUserId());
			}
			vehicle.setIsdel(0);
//			vehicle.setJiashiyuanid(String.valueOf(a.get("jiashiyuanid")).trim());
			vehicle.setCreatetime(LocalDateTime.now());
			vehicle.setCaozuoshijian(LocalDateTime.now());



			QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
			vehicleQueryWrapper.lambda().eq(Vehicle::getDeptId,vehicle.getDeptId());
			vehicleQueryWrapper.lambda().eq(Vehicle::getCheliangpaizhao,vehicle.getCheliangpaizhao());
//			vehicleQueryWrapper.lambda().eq(Vehicle::getChepaiyanse,vehicle.getChepaiyanse());
			vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel,0);
			Vehicle vehicle1 = vehicleService.getBaseMapper().selectOne(vehicleQueryWrapper);
			if(vehicle1 !=null){
				vehicle.setId(vehicle1.getId());
				isDataValidity = vehicleService.updateById(vehicle);
			}else{
				String s = IdUtil.simpleUUID();
				vehicle.setId(s);
				isDataValidity = vehicleService.insertSelective(vehicle);
			}

			if(isDataValidity){
				new Thread(new Runnable() {
					@Override
					public void run() {
						Organizations dept = orrganizationsClient.selectByDeptId(vehicle.getDeptId().toString());
						VehicleJishupingding jishupingding = new VehicleJishupingding();		//技术评定
						jishupingding.setAvjVehicleIds(vehicle.getId());
						jishupingding.setAvjDelete("0");
						jishupingding.setAvjCreateByName(user.getUserName());
						jishupingding.setAvjCreateByIds(user.getUserId().toString());
						jishupingding.setAvjCreateTime(LocalDateTime.now());
						QueryWrapper<VehicleJishupingding> vehicleJishupingdingQueryWrapper = new QueryWrapper<>();
						vehicleJishupingdingQueryWrapper.lambda().eq(VehicleJishupingding::getAvjVehicleIds,jishupingding.getAvjVehicleIds());
						vehicleJishupingdingQueryWrapper.lambda().eq(VehicleJishupingding::getAvjDelete,0);
						VehicleJishupingding vehicleJishupingding = jishupingdingService.getBaseMapper().selectOne(vehicleJishupingdingQueryWrapper);
						if (vehicleJishupingding !=null){
							jishupingding.setAvjIds(vehicleJishupingding.getAvjIds());
							jishupingdingService.updateById(jishupingding);
						}else {
							jishupingdingService.save(jishupingding);
						}

						VehicleDengjizhengshu dengjizhengshu = new VehicleDengjizhengshu();		//登记证书
						dengjizhengshu.setAvdVehicleIds(vehicle.getId());
						dengjizhengshu.setAvdDelete("0");
						dengjizhengshu.setAvdCreateByName(user.getUserName());
						dengjizhengshu.setAvdCreateByIds(user.getUserId().toString());
						dengjizhengshu.setAvdCreateTime(LocalDateTime.now());
						QueryWrapper<VehicleDengjizhengshu> dengjizhengshuQueryWrapper = new QueryWrapper<>();
						dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdVehicleIds,dengjizhengshu.getAvdVehicleIds());
						dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdDelete,0);
						VehicleDengjizhengshu vehicleDengjizhengshu = dengjizhengshuService.getBaseMapper().selectOne(dengjizhengshuQueryWrapper);
						if (vehicleDengjizhengshu !=null){
							dengjizhengshu.setAvdIds(vehicleDengjizhengshu.getAvdIds());
							dengjizhengshuService.updateById(dengjizhengshu);
						}else {
							dengjizhengshuService.save(dengjizhengshu);
						}

						VehicleJingyingxukezheng jingyingxukezheng = new VehicleJingyingxukezheng();		//经营许可证
						jingyingxukezheng.setAvjVehicleIds(vehicle.getId());
						if (StringUtils.isNotBlank(dept.getDeptName())  && !dept.getDeptName().equals("null")){
						jingyingxukezheng.setAvjOperatorName(dept.getDeptName());
						}
						jingyingxukezheng.setAvjDelete("0");
						jingyingxukezheng.setAvjCreateByName(user.getUserName());
						jingyingxukezheng.setAvjCreateByIds(user.getUserId().toString());
						jingyingxukezheng.setAvjCreateTime(LocalDateTime.now());
						QueryWrapper<VehicleJingyingxukezheng> jingyingxukezhengQueryWrapper = new QueryWrapper<>();
						jingyingxukezhengQueryWrapper.lambda().eq(VehicleJingyingxukezheng::getAvjVehicleIds,jingyingxukezheng.getAvjVehicleIds());
						jingyingxukezhengQueryWrapper.lambda().eq(VehicleJingyingxukezheng::getAvjDelete,0);
						VehicleJingyingxukezheng vehicleJingyingxukezheng = jingyingxukezhengService.getBaseMapper().selectOne(jingyingxukezhengQueryWrapper);
						if (vehicleJingyingxukezheng !=null){
							jingyingxukezheng.setAvjIds(vehicleJingyingxukezheng.getAvjIds());
							jingyingxukezhengService.updateById(jingyingxukezheng);
						}else {
							jingyingxukezhengService.save(jingyingxukezheng);
						}

						VehicleXingnengbaogao xingnengbaogao = new VehicleXingnengbaogao();		//性能报告
						xingnengbaogao.setAvxAvIds(vehicle.getId());
						xingnengbaogao.setAvxDelete("0");
						xingnengbaogao.setAvxCreateByName(user.getUserName());
						xingnengbaogao.setAvxCreateByIds(user.getUserId().toString());
						xingnengbaogao.setAvxCreateTime(LocalDateTime.now());
						QueryWrapper<VehicleXingnengbaogao> xingnengbaogaoQueryWrapper = new QueryWrapper<>();
						xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxAvIds,xingnengbaogao.getAvxAvIds());
						xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxDelete,0);
						VehicleXingnengbaogao vehicleXingnengbaogao = xingnengbaogaoService.getBaseMapper().selectOne(xingnengbaogaoQueryWrapper);
						if (vehicleXingnengbaogao !=null){
							xingnengbaogao.setAvxIds(vehicleXingnengbaogao.getAvxIds());
							xingnengbaogaoService.updateById(xingnengbaogao);
						}else {
							xingnengbaogaoService.save(xingnengbaogao);
						}

						VehicleXingshizheng xingshizheng = new VehicleXingshizheng();		//行驶证
						xingshizheng.setAvxAvIds(vehicle.getId());
						xingshizheng.setAvxPlateNo(vehicle.getCheliangpaizhao());
						xingshizheng.setAvxVehicleType(vehicle.getCheliangleixing());
						xingshizheng.setAvxOwner(dept.getDeptName());
						xingshizheng.setAvxAddress("住址");
						xingshizheng.setAvxUseCharter(vehicle.getShiyongxingzhi());
						xingshizheng.setAvxModel(vehicle.getXinghao());
//						xingshizheng.setAvxOwner(vehicle.getOwner());
						xingshizheng.setAvxFileNo(vehicle.getFileNo());
						xingshizheng.setAvxModel(vehicle.getBrandModel());
						xingshizheng.setAvxVin(vehicle.getCheliangshibiedaima());
						xingshizheng.setAvxCurbWeight(0);		//整备质量
						xingshizheng.setAvxDelete("0");
						xingshizheng.setAvxCreateByName(user.getUserName());
						xingshizheng.setAvxCreateByIds(user.getUserId().toString());
						xingshizheng.setAvxCreateTime(LocalDateTime.now());
						if (vehicle.getAvxRegisterDate() != null){
							xingshizheng.setAvxRegisterDate(vehicle.getAvxRegisterDate());
						}
						if ( vehicle.getAvxIssueDate() != null){
							xingshizheng.setAvxIssueDate(vehicle.getAvxIssueDate());
						}
						if (StringUtils.isNotBlank(vehicle.getXingshizhengdaoqishijian())  && !vehicle.getXingshizhengdaoqishijian().equals("null")){
							xingshizheng.setAvxValidUntil(LocalDate.parse(vehicle.getXingshizhengdaoqishijian(),DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						}
						if (StringUtils.isNotBlank(vehicle.getZongzhiliang())  && !vehicle.getZongzhiliang().equals("null")){
							xingshizheng.setAvxTotalMass(Integer.parseInt(vehicle.getZongzhiliang()));
						}
						if (StringUtils.isNotBlank(vehicle.getHedingzaike())  && !vehicle.getHedingzaike().equals("null")){
							if (StringUtils.isNumeric(vehicle.getHedingzaike())){
								xingshizheng.setAvxAuthorizedSeatingCapacity(Integer.parseInt(vehicle.getHedingzaike()));
							}
						}
						if (StringUtils.isNotBlank(vehicle.getCheliangwaikuochicun())  && !vehicle.getCheliangwaikuochicun().equals("null")){
							xingshizheng.setAvxOverallDimensions(vehicle.getCheliangwaikuochicun());
						}
						if (StringUtils.isNotBlank(vehicle.getZhunqianyinzongzhiliang())  && !vehicle.getZhunqianyinzongzhiliang().equals("null")){
							xingshizheng.setAvxQuasiTractiveMass(Integer.parseInt(vehicle.getZhunqianyinzongzhiliang()));
						}
						if (StringUtils.isNotBlank(vehicle.getHedingzaizhiliang())  && !vehicle.getHedingzaizhiliang().equals("null")){
							xingshizheng.setAvxApprovedLoadCapacity(Integer.parseInt(vehicle.getHedingzaizhiliang()));
						}
						if (StringUtils.isNotBlank(vehicle.getQiangzhibaofeishijian())  && !vehicle.getQiangzhibaofeishijian().equals("null")){
							xingshizheng.setAvxBaofeiTime(vehicle.getQiangzhibaofeishijian());
						}
						QueryWrapper<VehicleXingshizheng> xingshizhengQueryWrapper = new QueryWrapper<>();
						xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxAvIds,xingshizheng.getAvxAvIds());
						xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxDelete,0);
						VehicleXingshizheng vehicleXingshizheng = xingshizhengService.getBaseMapper().selectOne(xingshizhengQueryWrapper);
						if (vehicleXingshizheng !=null){
							xingshizheng.setAvxIds(vehicleXingshizheng.getAvxIds());
							xingshizhengService.updateById(xingshizheng);
						}else {
							xingshizhengService.save(xingshizheng);
						}

						VehicleDaoluyunshuzheng daoluyunshuzheng = new VehicleDaoluyunshuzheng(); //道路运输证
						daoluyunshuzheng.setAvdAvIds(vehicle.getId());
						daoluyunshuzheng.setAvdBusinessOwner(dept.getDeptName());
						daoluyunshuzheng.setAvdPlateNo(vehicle.getCheliangpaizhao());
						daoluyunshuzheng.setAvdVehicleType(vehicle.getXinghao());
						daoluyunshuzheng.setAvdPlateColor(vehicle.getChepaiyanse());
						daoluyunshuzheng.setAvdRoadTransportCertificateNo(vehicle.getDaoluyunshuzhenghao());
						daoluyunshuzheng.setAvdDelete("0");
						daoluyunshuzheng.setAvdCreateByName(user.getUserName());
						daoluyunshuzheng.setAvdCreateByIds(user.getUserId().toString());
						daoluyunshuzheng.setAvdCreateTime(DateUtil.now());
						if (StringUtils.isNotBlank(vehicle.getDaoluyunshuzhengchulingriqi())  && !vehicle.getDaoluyunshuzhengchulingriqi().equals("null")){
							daoluyunshuzheng.setAvdIssueDate(LocalDate.parse(vehicle.getDaoluyunshuzhengchulingriqi() , DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						}
						if (StringUtils.isNotBlank(vehicle.getDaoluyunshuzhengyouxiaoqi())  && !vehicle.getDaoluyunshuzhengyouxiaoqi().equals("null")){
							daoluyunshuzheng.setAvdValidUntil(LocalDate.parse(vehicle.getDaoluyunshuzhengyouxiaoqi() , DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						}
						QueryWrapper<VehicleDaoluyunshuzheng> daoluyunshuzhengQueryWrapper = new QueryWrapper<>();
						daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdAvIds,daoluyunshuzheng.getAvdAvIds());
						daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdDelete,0);
						VehicleDaoluyunshuzheng vehicleDaoluyunshuzheng = daoluyunshuzhengService.getBaseMapper().selectOne(daoluyunshuzhengQueryWrapper);
						if (vehicleDaoluyunshuzheng !=null){
							daoluyunshuzheng.setAvdIds(vehicleDaoluyunshuzheng.getAvdIds());
							daoluyunshuzhengService.updateById(daoluyunshuzheng);
						}else {
							daoluyunshuzhengService.save(daoluyunshuzheng);
						}

//						//新增-车辆-驾驶员绑定信息
//						AnbiaoCheliangJiashiyuan cheliangJiashiyuan = new AnbiaoCheliangJiashiyuan();
//						QueryWrapper<AnbiaoCheliangJiashiyuan> cheliangJiashiyuanQueryWrapper = new QueryWrapper<>();
//						cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuan::getJiashiyuanid,vehicle.getJiashiyuanid());
//						cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuan::getVehid,vehicle.getId());
//						AnbiaoCheliangJiashiyuan deail = cheliangJiashiyuanService.getBaseMapper().selectOne(cheliangJiashiyuanQueryWrapper);
//						if (deail == null){
//							cheliangJiashiyuan.setJiashiyuanid(vehicle.getJiashiyuanid());
//							cheliangJiashiyuan.setVehid(vehicle.getId());
//							cheliangJiashiyuan.setCreatetime(DateUtil.now());
//							cheliangJiashiyuanService.save(cheliangJiashiyuan);
//						}

						if (StringUtils.isNotBlank(vehicle.getJiashiyuanxingming())  && !vehicle.getJiashiyuanxingming().equals("null")){
							QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getJiashiyuanxingming,vehicle.getJiashiyuanxingming());
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShoujihaoma,vehicle.getJiashiyuandianhua());
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,"0");
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId,vehicle.getDeptId());
							JiaShiYuan jiaShiYuan = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
							if (jiaShiYuan==null){
								jiaShiYuan = new JiaShiYuan();
								jiaShiYuan.setJiashiyuanxingming(vehicle.getJiashiyuanxingming());
								jiaShiYuan.setDeptId(vehicle.getDeptId());
								jiaShiYuan.setShoujihaoma(vehicle.getJiashiyuandianhua());
								jiaShiYuan.setCaozuoren(user.getUserName());
								jiaShiYuan.setCaozuorenid(user.getUserId());
								jiaShiYuan.setCreatetime(DateUtil.now());
								jiaShiYuan.setDenglumima(DigestUtil.encrypt(jiaShiYuan.getShoujihaoma().substring(jiaShiYuan.getShoujihaoma().length() - 6)));
								jiaShiYuan.setIsdelete(0);
								jiaShiYuan.setStatus(0);
								iJiaShiYuanService.save(jiaShiYuan);

								QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper1 = new QueryWrapper<>();
								jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getJiashiyuanxingming,vehicle.getJiashiyuanxingming());
								jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getShoujihaoma,vehicle.getJiashiyuandianhua());
								jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getIsdelete,"0");
								jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getDeptId,vehicle.getDeptId());
								JiaShiYuan jiaShiYuan1 = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);

								//向入职登记表添加信息
								AnbiaoJiashiyuanRuzhi ruzhi = new AnbiaoJiashiyuanRuzhi();
								QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
								ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, jiaShiYuan1.getId());
								ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
								AnbiaoJiashiyuanRuzhi rzdeail = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
								if (rzdeail == null) {
									ruzhi.setAjrCreateByName(user.getUserName());
									ruzhi.setAjrCreateByIds(user.getUserId().toString());
									ruzhi.setAjrCreateTime(DateUtil.now());
									ruzhi.setAjrDelete("0");
									ruzhi.setAjrAjIds(jiaShiYuan1.getId());
									ruzhi.setAjrName(jiaShiYuan1.getJiashiyuanxingming());
									ruzhi.setAjrApproverStatus("0");
									ruzhiService.save(ruzhi);
								}

								//向驾驶证信息表添加数据
								AnbiaoJiashiyuanJiashizheng jiashizheng = new AnbiaoJiashiyuanJiashizheng();
								QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
								jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, jiaShiYuan1.getId());
								jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
								AnbiaoJiashiyuanJiashizheng jszdeail = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
								if (jszdeail == null) {
									jiashizheng.setAjjAjIds(jiaShiYuan1.getId());
									jiashizheng.setAjjStatus("0");
									jiashizheng.setAjjDelete("0");
									jiashizheng.setAjjCreateByIds(user.getUserId().toString());
									jiashizheng.setAjjCreateByName(user.getUserName());
									jiashizheng.setAjjCreateTime(DateUtil.now());
									jiashizhengService.save(jiashizheng);
								}

								//向从业资格证信息表添加数据
								AnbiaoJiashiyuanCongyezigezheng congyezigezheng = new AnbiaoJiashiyuanCongyezigezheng();
								QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
								congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, jiaShiYuan1.getId());
								congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
								AnbiaoJiashiyuanCongyezigezheng cyzdeail = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
								if (cyzdeail == null) {
									congyezigezheng.setAjcAjIds(jiaShiYuan1.getId());
									congyezigezheng.setAjcStatus("0");
									congyezigezheng.setAjcCreateTime(DateUtil.now());
									congyezigezheng.setAjcDelete("0");
									congyezigezhengService.save(congyezigezheng);
								}

								//向体检信息表添加数据
								AnbiaoJiashiyuanTijian tijian = new AnbiaoJiashiyuanTijian();
								QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
								tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, jiaShiYuan1.getId());
								tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
								AnbiaoJiashiyuanTijian tjdeail = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
								if (tjdeail == null) {
									tijian.setAjtCreateByName(user.getUserName());
									tijian.setAjtCreateByIds(user.getUserId().toString());
									tijian.setAjtCreateTime(DateUtil.now());
									tijian.setAjtDelete("0");
									tijian.setAjtAjIds(jiaShiYuan1.getId());
									tijianService.save(tijian);
								}

								//向岗前培训信息表添加数据
								AnbiaoJiashiyuanGangqianpeixun gangqianpeixun = new AnbiaoJiashiyuanGangqianpeixun();
								QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
								gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, jiaShiYuan1.getId());
								gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
								AnbiaoJiashiyuanGangqianpeixun gqpxdeail = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
								if (gqpxdeail == null) {
									gangqianpeixun.setAjgCreateByName(user.getUserName());
									gangqianpeixun.setAjgCreateByIds(user.getUserId().toString());
									gangqianpeixun.setAjgCreateTime(DateUtil.now());
									gangqianpeixun.setAjgDelete("0");
									gangqianpeixun.setAjgAjIds(jiaShiYuan1.getId());
									gangqianpeixunService.save(gangqianpeixun);
								}

								//向三年无重大责任事故证明信息表添加数据
								AnbiaoJiashiyuanWuzezhengming wuzezhengming = new AnbiaoJiashiyuanWuzezhengming();
								QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
								wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, jiaShiYuan1.getId());
								wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
								AnbiaoJiashiyuanWuzezhengming wzzmdeail = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
								if (wzzmdeail == null) {
									wuzezhengming.setAjwCreateByName(user.getUserName());
									wuzezhengming.setAjwCreateByIds(user.getUserId().toString());
									wuzezhengming.setAjwCreateTime(DateUtil.now());
									wuzezhengming.setAjwDelete("0");
									wuzezhengming.setAjwAjIds(jiaShiYuan1.getId());
									wuzezhengmingService.save(wuzezhengming);
								}

								//向驾驶员安全责任书信息表添加数据
								AnbiaoJiashiyuanAnquanzerenshu anquanzerenshu = new AnbiaoJiashiyuanAnquanzerenshu();
								QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu>();
								anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaAjIds, jiaShiYuan1.getId());
								anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaDelete, "0");
								AnbiaoJiashiyuanAnquanzerenshu aqzesdeail = anquanzerenshuService.getBaseMapper().selectOne(anquanzerenshuQueryWrapper);
								if (aqzesdeail == null) {
									anquanzerenshu.setAjaCreateByName(user.getUserName());
									anquanzerenshu.setAjaCreateByIds(user.getUserId().toString());
									anquanzerenshu.setAjaCreateTime(DateUtil.now());
									anquanzerenshu.setAjaDelete("0");
									anquanzerenshu.setAjaAjIds(jiaShiYuan1.getId());
									anquanzerenshuService.save(anquanzerenshu);
								}

								//向驾驶员职业危害告知书信息表添加数据
								AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishu = new AnbiaoJiashiyuanWeihaigaozhishu();
								QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
								weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwAjIds, jiaShiYuan1.getId());
								weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwDelete, "0");
								AnbiaoJiashiyuanWeihaigaozhishu whgzsdeail = weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);
								if (whgzsdeail == null) {
									weihaigaozhishu.setAjwCreateByName(user.getUserName());
									weihaigaozhishu.setAjwCreateByIds(user.getUserId().toString());
									weihaigaozhishu.setAjwCreateTime(DateUtil.now());
									weihaigaozhishu.setAjwDelete("0");
									weihaigaozhishu.setAjwAjIds(jiaShiYuan1.getId());
									weihaigaozhishuService.save(weihaigaozhishu);
								}

								//向劳动合同信息表添加数据
								AnbiaoJiashiyuanLaodonghetong laodonghetong = new AnbiaoJiashiyuanLaodonghetong();
								QueryWrapper<AnbiaoJiashiyuanLaodonghetong> laodonghetongQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanLaodonghetong>();
								laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwAjIds, jiaShiYuan1.getId());
								laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwDelete, "0");
								AnbiaoJiashiyuanLaodonghetong ldhtdeail = laodonghetongService.getBaseMapper().selectOne(laodonghetongQueryWrapper);
								if (ldhtdeail == null) {
									laodonghetong.setAjwCreateByName(user.getUserName());
									laodonghetong.setAjwCreateByIds(user.getUserId().toString());
									laodonghetong.setAjwCreateTime(DateUtil.now());
									laodonghetong.setAjwDelete("0");
									laodonghetong.setAjwAjIds(jiaShiYuan1.getId());
									laodonghetongService.save(laodonghetong);
								}

								//向其他信息表添加数据
								AnbiaoJiashiyuanQita qita = new AnbiaoJiashiyuanQita();
								QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanQita>();
								qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, jiaShiYuan1.getId());
								qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
								AnbiaoJiashiyuanQita qtdeail = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);
								if (qtdeail == null) {
									qita.setAjtCreateByName(user.getUserName());
									qita.setAjtCreateByIds(user.getUserId().toString());
									qita.setAjtCreateTime(DateUtil.now());
									qita.setAjtDelete("0");
									qita.setAjtAjIds(jiaShiYuan1.getId());
									qitaService.save(qita);
								}

								QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper2 = new QueryWrapper<>();
								jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getJiashiyuanxingming,vehicle.getJiashiyuanxingming());
								jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getIsdelete,"0");
								jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getShoujihaoma,vehicle.getJiashiyuandianhua());
								jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getDeptId,vehicle.getDeptId());
								JiaShiYuan jiaShiYuan2 = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper2);
								vehicle.setJiashiyuanid(jiaShiYuan2.getId());
								vehicleService.getBaseMapper().updateById(vehicle);

							}else {
								vehicle.setJiashiyuanid(jiaShiYuan.getId());
								vehicleService.getBaseMapper().updateById(vehicle);
							}
						}


					}
				}).start();
			}
		}
		if (isDataValidity == true) {
			rs.setCode(200);
			rs.setMsg("数据导入成功");
			rs.setData(vehicles);
			rs.setSuccess(true);
			return rs;
		} else {
			rs.setCode(500);
			rs.setMsg("数据导入失败");
			rs.setData(vehicles);
			rs.setSuccess(false);
			return rs;
		}
	}

	@GetMapping("/getVehImg")
	@ApiLog("车辆-影像资料数据统计")
	@ApiOperation(value = "车辆-影像资料数据统计", notes = "传入vehId", position = 31)
	public R<VehicleImg> getVehImg(String vehId) {
		R rs = new R();
		VehicleImg or = vehicleService.getByVehImg(vehId);
		if(or != null){
			rs.setCode(200);
			rs.setData(or);
			rs.setMsg("获取成功");
		}else{
			rs.setCode(200);
			rs.setData(null);
			rs.setMsg("获取成功,暂无数据");
		}
		return rs;
	}

	@PostMapping("/uploadInsert")
	@ApiLog("车辆-影像资料数据-导入")
	@ApiOperation(value = "车辆-影像资料数据-导入", notes = "传入VehicleImg")
	public R uploadInsert(@RequestBody VehicleImg vehicleImg, BladeUser user) throws ParseException {
		R r = new R();
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		VehicleXingshizheng vxs = new VehicleXingshizheng();
		vxs.setAvxUpdateTime(LocalDateTime.now());
		vxs.setAvxUpdateByName(user.getUserName());
		vxs.setAvxUpdateByIds(user.getUserId().toString());
		vxs.setAvxIds(vehicleImg.getXszid());
		if (StringUtils.isNotEmpty(vehicleImg.getXszzmimg()) && vehicleImg.getXszzmimg() != "null"){
			vxs.setAvxOriginalEnclosure(vehicleImg.getXszzmimg());
		}
		if (StringUtils.isNotEmpty(vehicleImg.getXszfmimg()) && vehicleImg.getXszfmimg() != "null"){
			vxs.setAvxCopyEnclosure(vehicleImg.getXszfmimg());
		}
		VehicleDaoluyunshuzheng vdlyxs = new VehicleDaoluyunshuzheng();
		vdlyxs.setAvdUpdateTime(DateUtil.now());
		vdlyxs.setAvdUpdateByName(user.getUserName());
		vdlyxs.setAvdUpdateByIds(user.getUserId().toString());
		vdlyxs.setAvdIds(vehicleImg.getYszid());
		if (StringUtils.isNotEmpty(vehicleImg.getYszimg()) && vehicleImg.getYszimg() != "null"){
			vdlyxs.setAvdEnclosure(vehicleImg.getYszimg());
		}
		VehicleXingnengbaogao vxnbg = new VehicleXingnengbaogao();
		vxnbg.setAvxUpdateTime(LocalDateTime.now());
		vxnbg.setAvxUpdateByName(user.getUserName());
		vxnbg.setAvxUpdateByIds(user.getUserId().toString());
		vxnbg.setAvxIds(vehicleImg.getXnbgid());
		if (StringUtils.isNotEmpty(vehicleImg.getXnbgimg()) && vehicleImg.getXnbgimg() != "null"){
			vxnbg.setAvxEnclosure(vehicleImg.getXnbgimg());
		}
		VehicleDengjizhengshu vdjz = new VehicleDengjizhengshu();
		vdjz.setAvdUpdateTime(LocalDateTime.now());
		vdjz.setAvdUpdateByName(user.getUserName());
		vdjz.setAvdUpdateByIds(user.getUserId().toString());
		vdjz.setAvdIds(vehicleImg.getDjzid());
		if (StringUtils.isNotEmpty(vehicleImg.getDjzimg()) && vehicleImg.getDjzimg() != "null"){
			vdjz.setAvdEnclosure(vehicleImg.getDjzimg());
		}
		//行驶证
		boolean ii = xingshizhengService.updateById(vxs);
		//道路运输证
		ii = daoluyunshuzhengService.updateById(vdlyxs);
		//性能检测报告
		ii = xingnengbaogaoService.updateById(vxnbg);
		//登记证书
		ii = dengjizhengshuService.updateById(vdjz);
		if (ii){
			r.setMsg("导入成功");
			r.setCode(200);
			r.setSuccess(false);
		}else {
			r.setMsg("导入失败");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	@PostMapping("/exportDataWord")
	@ApiLog("车辆-影像资料数据-导出")
	@ApiOperation(value = "车辆-影像资料数据-导出", notes = "传入车辆ID", position = 29)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "vehId", value = "车辆ID（多个以英文逗号隔开）", required = true),
	})
	public R exportDataWord(HttpServletRequest request, HttpServletResponse response, @RequestParam String vehId, @RequestParam String deptId,BladeUser user) throws IOException{
		R r = new R();
		if(user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		Map<String, Object> params = new HashMap<>();
		String temDir = "";
		String fileName = "";
		String folder = "";
		String [] nyr=DateUtil.today().split("-");
		List<String> urlList = new ArrayList<>();
		try {
			// TODO 渲染其他类型的数据请参考官方文档
			DecimalFormat df = new DecimalFormat("######0.00");
			Calendar now = Calendar.getInstance();
			String[] idsss = vehId.split(",");
			//去除素组中重复的数组
			List<String> listid = new ArrayList<String>();
			for (int i=0; i<idsss.length; i++) {
				if(!listid.contains(idsss[i])) {
					listid.add(idsss[i]);
				}
			}
			//返回一个包含所有对象的指定类型的数组
			String[] idss= listid.toArray(new String[1]);
			for(int j = 0;j< idss.length;j++){
				String url = "";
				//获取数据
				Vehicle detail = vehicleService.getBaseMapper().selectById(idss[j]);
				if(detail == null){
					r.setMsg("导出失败，请校验资料数据");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
				//word模板地址
				String templatePath =fileServer.getPathPrefix()+"muban\\"+"vehicleFile.docx";
				// 渲染文本
				params.put("vehNo", detail.getCheliangpaizhao());
				// 渲染图片
				//车辆照片
				WordImageEntity image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				if(StrUtil.isNotEmpty(detail.getCheliangzhaopian()) && !detail.getCheliangzhaopian().contains("http")){
					detail.setCheliangzhaopian(fileUploadClient.getUrl(detail.getCheliangzhaopian()));
					url = detail.getCheliangzhaopian();
					url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a1", image);
				}else if(StringUtils.isNotEmpty(detail.getCheliangzhaopian())){
					url = detail.getCheliangzhaopian();
					url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a1", image);
				}else{
					params.put("a1", "未上传");
				}
				//行驶证
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				VehicleXingshizheng qXsz = new VehicleXingshizheng();
				qXsz.setAvxAvIds(detail.getId());
				qXsz.setAvxDelete("0");
				VehicleXingshizheng xingshizheng = vehicleXingshizhengService.getOne(Condition.getQueryWrapper(qXsz));
				if(xingshizheng != null){
					if(StrUtil.isNotEmpty(xingshizheng.getAvxCopyEnclosure()) && !xingshizheng.getAvxCopyEnclosure().contains("http")){
						xingshizheng.setAvxCopyEnclosure(fileUploadClient.getUrl(xingshizheng.getAvxCopyEnclosure()));
						url = xingshizheng.getAvxCopyEnclosure();
						url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a2", image);
					}else if(StrUtil.isNotEmpty(xingshizheng.getAvxCopyEnclosure())){
						url = xingshizheng.getAvxOriginalEnclosure();
						url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a2", image);
					}else{
						params.put("a2", "未上传");
					}
					image = new WordImageEntity();
					image.setHeight(240);
					image.setWidth(440);
					if(StrUtil.isNotEmpty(xingshizheng.getAvxOriginalEnclosure()) && !xingshizheng.getAvxOriginalEnclosure().contains("http")){
						xingshizheng.setAvxOriginalEnclosure(fileUploadClient.getUrl(xingshizheng.getAvxOriginalEnclosure()));
						url = xingshizheng.getAvxOriginalEnclosure();
						url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a3", image);
					}else if(StrUtil.isNotEmpty(xingshizheng.getAvxOriginalEnclosure())){
						url = xingshizheng.getAvxOriginalEnclosure();
						url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a3", image);
					}else{
						params.put("a3", "未上传");
					}
				}
				//道路运输证
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				VehicleDaoluyunshuzheng qDlysz = new VehicleDaoluyunshuzheng();
				qDlysz.setAvdAvIds(detail.getId());
				qDlysz.setAvdDelete("0");
				VehicleDaoluyunshuzheng daoluyunshuzheng= vehicleDaoluyunshuzhengService.getOne(Condition.getQueryWrapper(qDlysz));
				if(daoluyunshuzheng != null){
					if(StrUtil.isNotEmpty(daoluyunshuzheng.getAvdEnclosure()) && !daoluyunshuzheng.getAvdEnclosure().contains("http")){
						daoluyunshuzheng.setAvdEnclosure(fileUploadClient.getUrl(daoluyunshuzheng.getAvdEnclosure()));
						url = daoluyunshuzheng.getAvdEnclosure();
						url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a4", image);
					}else if(StrUtil.isNotEmpty(daoluyunshuzheng.getAvdEnclosure())){
						url = daoluyunshuzheng.getAvdEnclosure();
						url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a4", image);
					}else{
						params.put("a4", "未上传");
					}
				}
				//性能检测报告
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				VehicleXingnengbaogao qXlbg = new VehicleXingnengbaogao();
				qXlbg.setAvxAvIds(detail.getId());
				qXlbg.setAvxDelete("0");
				VehicleXingnengbaogao xingnengbaogao= vehicleXingnengbaogaoService.getOne(Condition.getQueryWrapper(qXlbg));
				if(xingnengbaogao != null) {
					if (StrUtil.isNotEmpty(xingnengbaogao.getAvxEnclosure()) && !xingnengbaogao.getAvxEnclosure().contains("http")) {
						xingnengbaogao.setAvxEnclosure(fileUploadClient.getUrl(xingnengbaogao.getAvxEnclosure()));
						url = xingnengbaogao.getAvxEnclosure();
						url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a5", image);
					} else if (StrUtil.isNotEmpty(xingnengbaogao.getAvxEnclosure())) {
						url = xingnengbaogao.getAvxEnclosure();
						url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a5", image);
					} else {
						params.put("a5", "未上传");
					}
				}

				//登记证
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				VehicleDengjizhengshu qDjzs = new VehicleDengjizhengshu();
					qDjzs.setAvdVehicleIds(detail.getId());
					qDjzs.setAvdDelete("0");
					VehicleDengjizhengshu dengjizhengshu= vehicleDengjizhengshuService.getOne(Condition.getQueryWrapper(qDjzs));
					if(dengjizhengshu != null) {
						if (StrUtil.isNotEmpty(dengjizhengshu.getAvdEnclosure()) && !dengjizhengshu.getAvdEnclosure().contains("http")) {
							dengjizhengshu.setAvdEnclosure(fileUploadClient.getUrl(dengjizhengshu.getAvdEnclosure()));
							url = dengjizhengshu.getAvdEnclosure();
							url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
							System.out.println(url);
							image.setUrl(url);
							image.setType(WordImageEntity.URL);
							params.put("a6", image);
						} else if (StrUtil.isNotEmpty(dengjizhengshu.getAvdEnclosure())) {
							url = dengjizhengshu.getAvdEnclosure();
							url = fileServer.getPathPrefix()+org.springblade.common.tool.StringUtils.splits(url);
							System.out.println(url);
							image.setUrl(url);
							image.setType(WordImageEntity.URL);
							params.put("a6", image);
						} else {
							params.put("a6", "未上传");
						}
					}

//				image.setUrl("D:\\BS\\static\\SafetyStandards\\安全生产档案\\图片文件\\清远市清城区丰烨物流有限公司\\车辆档案\\C1车辆资料\\车辆信息\\车辆信息-1.png");
//				image.setType(WordImageEntity.URL);
//				params.put("a1", image);
				// TODO 渲染其他类型的数据请参考官方文档
				//=================生成文件保存在本地D盘某目录下=================
//			temDir = "D:/mimi/file/word/"; ;//生成临时文件存放地址
				nyr=DateUtil.today().split("-");
				//附件存放地址(服务器生成地址)
				temDir = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2]+"/";
				//生成文件名
				Long time = new Date().getTime();
				// 生成的word格式
				String formatSuffix = ".docx";
				String wjName = detail.getCheliangpaizhao()+"_"+"影像附件";
				// 拼接后的文件名
				fileName = wjName + formatSuffix;//文件名  带后缀
				//导出word
				String tmpPath = WordUtil2.exportDataWord3(templatePath, temDir, fileName, params, request, response);
				urlList.add(tmpPath);
			}
			folder = fileServer.getPathPrefix()+FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+nyr[2]+"/"+"车辆影像.zip";
			ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(folder));
			ApacheZipUtils.doCompress1(urlList, bizOut);
			//不要忘记调用
			bizOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		r.setMsg("导出成功");
		r.setCode(200);
		r.setData(folder);
		r.setSuccess(true);
		return r;
	}

	@GetMapping("/getDriverVehicle")
	@ApiLog("根据驾驶员ID获取绑定车辆")
	@ApiOperation(value = "根据驾驶员ID获取绑定车辆", notes = "传入jsyId", position = 32)
	public R<List<VehicleVO>> getDriverVehicle(String jsyId) {
		List<VehicleVO> detail = vehicleService.selectDriverVehicle(jsyId);
		for (int i = 0; i <detail.size() ; i++) {
			//车辆照片
			if (StrUtil.isNotEmpty(detail.get(i).getCheliangzhaopian()) && detail.get(i).getCheliangzhaopian().contains("http") == false) {
				detail.get(i).setCheliangzhaopian(fileUploadClient.getUrl(detail.get(i).getCheliangzhaopian()));
			}
		}
		return R.data(detail);
	}

	private AlarmServer alarmServer;
	public void ss() {
		if(!alarmServer.getAddressPath().equals("swh")){

		}
	}

	@GetMapping("/goExport_getTechnicalArchives")
	@ApiLog("车辆技术档案-导出")
	@ApiOperation(value = "车辆技术档案-导出", notes = "传入VehicleId", position = 22)
	public R goExport_HiddenDanger(HttpServletRequest request, HttpServletResponse response, String VehicleId, BladeUser user) throws Exception {
		R rs = new R();
		String deptName="";
		String uuid = UUID.randomUUID().toString().replace("-", "");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> urlList = new ArrayList<>();
		JiShuDangAnVO jiShuDangAnVO = new JiShuDangAnVO();
		jiShuDangAnVO.setId(VehicleId);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath =fileServer.getPathPrefix()+"muban\\"+"JiShuDangAn.docx";
		String folder = "";
		String [] nyr=DateUtil.today().split("-");
		String[] idsss = jiShuDangAnVO.getId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss= listid.toArray(new String[1]);
		for(int j = 0;j< idss.length;j++){
			jiShuDangAnVO.setId(idss[j]);
			List<JiShuDangAnVO> jiShuDangAnVOS = vehicleService.selectJiShuDangAn(jiShuDangAnVO);

			if(jiShuDangAnVOS.size()==0){

			}else if(jiShuDangAnVOS.size()>3000){
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			}else{
				for( int i = 0 ; i < jiShuDangAnVOS.size() ; i++) {
					Map<String, Object> map = new HashMap<>();
					List<Map<String, Object>> ListData = new ArrayList<>();
					List<Map<String, Object>> ListData2 = new ArrayList<>();
					List<Map<String, Object>> ListData3 = new ArrayList<>();
					String temDir = "";
					String fileName = "";
					String url = "";
					// 渲染文本
					JiShuDangAnVO t = jiShuDangAnVOS.get(i);
					JiShuDangAnVO jiShuDangAnVO1 = new JiShuDangAnVO();
					if (StringUtils.isNotBlank(t.getCheliangpaizhao()) && !t.getCheliangpaizhao().equals("null")){
						map.put("a1", t.getCheliangpaizhao());
					}else {
						map.put("a1", "-");
					}

					if (StringUtils.isNotBlank(t.getXinghao()) && !t.getXinghao().equals("null")){
						map.put("a2", t.getXinghao());
					}else {
						map.put("a2", "-");
					}
					if (StringUtils.isNotBlank(t.getChejiahao()) && !t.getChejiahao().equals("null")){
						map.put("a3", t.getChejiahao());
					}else {
						map.put("a3", "-");
					}
					if (StringUtils.isNotBlank(t.getCreatetime()) && !t.getCreatetime().equals("null")){
						map.put("a4", t.getCreatetime());
					}else {
						map.put("a4", "-");
					}
					if (StringUtils.isNotBlank(t.getChepaiyanse()) && !t.getChepaiyanse().equals("null")){
						map.put("b2", t.getChepaiyanse());
					}else {
						map.put("b2", "-");
					}
					if (StringUtils.isNotBlank(t.getAvxRegisterDate()) && !t.getAvxRegisterDate().equals("null")){
						map.put("b3", t.getAvxRegisterDate());
					}else {
						map.put("b3", "-");
					}
					if (StringUtils.isNotBlank(t.getChezhu()) && !t.getChezhu().equals("null")){
						map.put("b5", t.getChezhu());
					}else {
						map.put("b5", "-");
					}
					if (StringUtils.isNotBlank(t.getDaoluyunshuzheng()) && !t.getDaoluyunshuzheng().equals("null")){
						map.put("b6", t.getDaoluyunshuzheng());
					}else {
						map.put("b6", "-");
					}
					if (StringUtils.isNotBlank(t.getJingjileixing()) && !t.getJingjileixing().equals("null")){
						map.put("b7", t.getJingjileixing());
					}else {
						map.put("b7", "-");
					}
					if (StringUtils.isNotBlank(t.getJingyingzuzhifangshi()) && !t.getJingyingzuzhifangshi().equals("null")){
						map.put("b8", t.getJingyingzuzhifangshi());
					}else {
						map.put("b8", "-");
					}
					if (StringUtils.isNotBlank(t.getCheliangyunyingleixing()) && !t.getCheliangyunyingleixing().equals("null")){
						map.put("b9", t.getCheliangyunyingleixing());
					}else {
						map.put("b9", "-");
					}
					if (StringUtils.isNotBlank(t.getAvdRoadTransportCertificateNo()) && !t.getAvdRoadTransportCertificateNo().equals("null")){
						map.put("b10", t.getAvdRoadTransportCertificateNo());
					}else {
						map.put("b10", "-");
					}
					if (StringUtils.isNotBlank(t.getTeamno()) && !t.getTeamno().equals("null")){
						map.put("b11", t.getTeamno());
					}else {
						map.put("b11", "-");
					}
					if (StringUtils.isNotBlank(t.getChelianghuoqufangshi()) && !t.getChelianghuoqufangshi().equals("null")){
						map.put("b12", t.getChelianghuoqufangshi());
					}else {
						map.put("b12", "-");
					}
					if (StringUtils.isNotBlank(t.getWeihuzhouqi()) && !t.getWeihuzhouqi().equals("null")){
						map.put("b13", t.getWeihuzhouqi());
					}else {
						map.put("b13", "-");
					}
					if (StringUtils.isNotBlank(t.getCheliangpinpai()) && !t.getCheliangpinpai().equals("null")){
						map.put("c4", t.getCheliangpinpai());
					}else {
						map.put("c4", "-");
					}
					if (StringUtils.isNotBlank(t.getCheshenyanse()) && !t.getCheshenyanse().equals("null")){
						map.put("c5", t.getCheshenyanse());
					}else {
						map.put("c5", "-");
					}
					if (StringUtils.isNotBlank(t.getShifoujinkou()) && !t.getShifoujinkou().equals("null")){
						map.put("c7", t.getShifoujinkou());
					}else {
						map.put("c7", "-");
					}
					if (StringUtils.isNotBlank(t.getFadongjihao()) && !t.getFadongjihao().equals("null")){
						map.put("c8", t.getFadongjihao());
					}else {
						map.put("c8", "-");
					}
					if (StringUtils.isNotBlank(t.getFadongjixinghao()) && !t.getFadongjixinghao().equals("null")){
						map.put("c9", t.getFadongjixinghao());
					}else {
						map.put("c9", "-");
					}
					if (StringUtils.isNotBlank(t.getRanliaoleibie()) && !t.getRanliaoleibie().equals("null")){
						map.put("c10", t.getRanliaoleibie());
					}else {
						map.put("c10", "-");
					}
					if (StringUtils.isNotBlank(t.getFadongjipailianggonglv()) && !t.getFadongjipailianggonglv().equals("null")){
						map.put("c11", t.getFadongjipailianggonglv());
					}else {
						map.put("c11", "-");
					}
					if (StringUtils.isNotBlank(t.getZhizhaochangshang()) && !t.getZhizhaochangshang().equals("null")){
						map.put("c12", t.getZhizhaochangshang());
					}else {
						map.put("c12", "-");
					}
					if (StringUtils.isNotBlank(t.getZhuanxiangfangshi()) && !t.getZhuanxiangfangshi().equals("null")){
						map.put("c13", t.getZhuanxiangfangshi());
					}else {
						map.put("c13", "-");
					}
					if (StringUtils.isNotBlank(t.getLunju()) && !t.getLunju().equals("null")){
						map.put("c14", t.getLunju());
					}else {
						map.put("c14", "-");
					}
					if (StringUtils.isNotBlank(t.getLuntaishu()) && !t.getLuntaishu().equals("null")){
						map.put("c15", t.getLuntaishu());
					}else {
						map.put("c15", "-");
					}
					if (StringUtils.isNotBlank(t.getLuntaiguige()) && !t.getLuntaiguige().equals("null")){
						map.put("c16", t.getLuntaiguige());
					}else {
						map.put("c16", "-");
					}
					if (StringUtils.isNotBlank(t.getGangbantanhuangpianshu()) && !t.getGangbantanhuangpianshu().equals("null")){
						map.put("c17", t.getGangbantanhuangpianshu());
					}else {
						map.put("c17", "-");
					}
					if (StringUtils.isNotBlank(t.getZhouju()) && !t.getZhouju().equals("null")){
						map.put("c18", t.getZhouju());
					}else {
						map.put("c18", "-");
					}
					if (StringUtils.isNotBlank(t.getChezhoushu()) && !t.getChezhoushu().equals("null")){
						map.put("c19", t.getChezhoushu());
					}else {
						map.put("c19", "-");
					}
					if (StringUtils.isNotBlank(t.getCheliangwaikuochicun()) && !t.getCheliangwaikuochicun().equals("null")){
						map.put("c20", t.getCheliangwaikuochicun());
					}else {
						map.put("c20", "-");
					}
					if (StringUtils.isNotBlank(t.getHuoxiangneibuchicun()) && !t.getHuoxiangneibuchicun().equals("null")){
						map.put("c21", t.getHuoxiangneibuchicun());
					}else {
						map.put("c21", "-");
					}
					if (StringUtils.isNotBlank(t.getZongzhiliang()) && !t.getZongzhiliang().equals("null")){
						map.put("c22", t.getZongzhiliang());
					}else {
						map.put("c22", "-");
					}
					if (StringUtils.isNotBlank(t.getHedingzaizhiliang()) && !t.getHedingzaizhiliang().equals("null")){
						map.put("c23", t.getHedingzaizhiliang());
					}else {
						map.put("c23", "-");
					}
					if (StringUtils.isNotBlank(t.getHedingzaike()) && !t.getHedingzaike().equals("null")){
						map.put("c24", t.getHedingzaike());
					}else {
						map.put("c24", "-");
					}
					if (StringUtils.isNotBlank(t.getZhunqianyinzongzhiliang()) && !t.getZhunqianyinzongzhiliang().equals("null")){
						map.put("c25", t.getZhunqianyinzongzhiliang());
					}else {
						map.put("c25", "-");
					}
					if (StringUtils.isNotBlank(t.getJiashishizaike()) && !t.getJiashishizaike().equals("null")){
						map.put("c26", t.getJiashishizaike());
					}else {
						map.put("c26", "-");
					}
					if (StringUtils.isNotBlank(t.getShiyongxingzhi()) && !t.getShiyongxingzhi().equals("null")){
						map.put("c27", t.getShiyongxingzhi());
					}else {
						map.put("c27", "-");
					}
					if (StringUtils.isNotBlank(t.getChuchangriqi()) && !t.getChuchangriqi().equals("null")){
						map.put("c29", t.getChuchangriqi());
					}else {
						map.put("c29", "-");
					}

					String url1;

					// 本人照片
					WordImageEntity image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(180);
//					String jsonObject = t.getAjrHeadPortrait();
					if (StringUtils.isNotBlank(t.getCheliangzhaopian()) && !t.getCheliangzhaopian().equals("null")){
						if (StrUtil.isNotEmpty(t.getCheliangzhaopian()) && t.getCheliangzhaopian().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getCheliangzhaopian());
							t.setCheliangzhaopian(url1);
							//添加图片到工作表的指定位置

//							String s = new URL(t.getAjcLicence()).toString();
							String url2 = new URL(t.getCheliangzhaopian()).toString();
							//获得第一个点的位置
							int index = url2.indexOf("/");
							System.out.println(index);
							//根据第一个点的位置 获得第二个点的位置
							index = url2.indexOf("/", index + 2);
							//根据第三个点的位置，截取 字符串。得到结果 result
							String result = url2.substring(index);
							result = result.replace("/", "\\");  // 将正斜杠替换为反斜杠

							url2 = fileServer.getPathPrefix() + result;
							System.out.println(url2);
							image.setUrl(url2);
							image.setType(WordImageEntity.URL);
							map.put("a3", image);
						}else if (StrUtil.isNotEmpty(t.getCheliangzhaopian())){
							String url2 = t.getCheliangzhaopian();

							//获得第一个点的位置
							int index = url2.indexOf("/");
							System.out.println(index);
							//根据第一个点的位置 获得第二个点的位置
							index = url2.indexOf("/", index+2);
							//根据第三个点的位置，截取 字符串。得到结果 result
							String result = url2.substring(index);
//						String subString = url2.substring(url2.indexOf("/", 3) + 1);  // 从第二个正斜杠位置往后截取整个路径
							result = result.replace("/", "\\");  // 将正斜杠替换为反斜杠

							url2 = fileServer.getPathPrefix()+result;
							System.out.println(url2);
							image.setUrl(url2);
							image.setType(WordImageEntity.URL);
							map.put("b4", image);
						}
					}else{
						map.put("b4", "-");
					}

					MaintenanceEntity maintenanceEntity = new MaintenanceEntity();
					maintenanceEntity.setVehicleId(t.getId());
					List<MaintenanceEntity> maintenanceEntities = maintenanceService.selectNotAssemblyMaintenance(maintenanceEntity);
					for (MaintenanceEntity aa:maintenanceEntities) {
						HashMap<String, Object> data = new HashMap<>();
						data.put("SendDate",aa.getSendDate().substring(0,10));
						if (aa.getMaintainDictId()==0){
							data.put("MaintainDictId","一级维护");
						}else {
							data.put("MaintainDictId","二级维护");
						}

						data.put("acbMaintenanceContent",aa.getAcbMaintenanceContent());
						data.put("MaintenanceDeptName",aa.getMaintenanceDeptName());
						data.put("Jiashiyuanxingming",aa.getJiashiyuanxingming());
						ListData.add(data);

					}
					map.put("ListData",ListData);


					MaintenanceEntity maintenanceEntity2 = new MaintenanceEntity();
					maintenanceEntity2.setVehicleId(t.getId());
					List<MaintenanceEntity> maintenanceEntities2 = maintenanceService.selectAssemblyMaintenance(maintenanceEntity);
					for (MaintenanceEntity aa:maintenanceEntities2) {
						HashMap<String, Object> data = new HashMap<>();
						data.put("SendDate2",aa.getSendDate().substring(0,10));
						data.put("acbMaintenanceContent2",aa.getAcbMaintenanceContent());
						data.put("MaintenanceDeptName2",aa.getMaintenanceDeptName());
						data.put("Jiashiyuanxingming2",aa.getJiashiyuanxingming());
						ListData2.add(data);

					}
					map.put("ListData2",ListData2);

					AccidentReportsEntity accidentReportsEntity = new AccidentReportsEntity();
					accidentReportsEntity.setChepaihao(t.getCheliangpaizhao());
					List<AccidentReportsEntity> accidentReportsEntities = maintenanceService.selectShiGu(accidentReportsEntity);
					for (AccidentReportsEntity aa:accidentReportsEntities) {
						HashMap<String, Object> data = new HashMap<>();
						String format = sf.format(aa.getShigufashengshijian());
						data.put("Shigufashengshijian",format);
						data.put("Shigufashengdidian",aa.getShigufashengdidian());
						data.put("Shiguxingzhi",aa.getShiguxingzhi());
						data.put("Shiguzeren",aa.getShiguzeren());
						data.put("Shigufenlei","种类："+aa.getShigufenlei()+",情况："+aa.getShigugaikuang());
						data.put("Caichansunshi",aa.getCaichansunshi());
						data.put("Jiashiyuan",aa.getJiashiyuan());
						ListData3.add(data);

					}
					map.put("ListData3",ListData3);

					// TODO 渲染其他类型的数据请参考官方文档
					//=================生成文件保存在本地D盘某目录下=================
					//			temDir = "D:/mimi/file/word/"; ;//生成临时文件存放地址
					nyr=DateUtil.today().split("-");
					//附件存放地址(服务器生成地址)

					temDir = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+uuid+"\\"+t.getCheliangpaizhao()+"\\";
//					String temDir2 = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+uuid+"\\"+t.getDeptName()+"\\"+"车头"+"\\";
//					String temDir3 = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+uuid+"\\"+t.getDeptName()+"\\"+"挂车"+"\\";

					//生成文件名
					// 生成的word格式
					String formatSuffix = ".docx";
					String wjName = t.getCheliangpaizhao();
//					String wjName2 = t.getCheliangpaizhao();
//					String wjName3 = t.getCheliangpaizhaoGUA();
					// 拼接后的文件名
					fileName = wjName + formatSuffix;//文件名  带后缀
//					String fileName2 = wjName2 + formatSuffix;
//					String fileName3 = wjName3 + formatSuffix;
					//导出word
					WordUtil2.exportDataWord3(templatePath, temDir, fileName, map, request, response);
//					WordUtil2.exportDataWord3(templatePath2, temDir2, fileName2, map, request, response);
//					WordUtil2.exportDataWord3(templatePath2, temDir3, fileName3, map2, request, response);
					urlList.add(temDir);


					//替换路径前缀,获得pdf文件路径
					String pdfPath = temDir+"\\"+wjName+".pdf";
					//生成文件父级目录
					FileUtil.mkParentDirs(pdfPath);
					//生成pdf到pdf文件路径
					temDir=temDir+"\\"+wjName+formatSuffix;
					CommonUtil.world2pdf(temDir,pdfPath);
					System.out.println("已生成技术档案pdf"+pdfPath);

//					//替换路径前缀,获得pdf文件路径
//					String pdfPath2 = temDir2+"\\"+wjName2+".pdf";
//					//生成文件父级目录
//					FileUtil.mkParentDirs(pdfPath2);
//					//生成pdf到pdf文件路径
//					temDir2=temDir2+"\\"+wjName2+formatSuffix;
//					CommonUtil.world2pdf(temDir2,pdfPath2);
//					System.out.println("已生成车头pdf"+pdfPath2);
//
//					//替换路径前缀,获得pdf文件路径
//					String pdfPath3 = temDir3+"\\"+wjName3+".pdf";
//					//生成文件父级目录
//					FileUtil.mkParentDirs(pdfPath3);
//					//生成pdf到pdf文件路径
//					temDir3=temDir3+"\\"+wjName3+formatSuffix;
//					CommonUtil.world2pdf(temDir3,pdfPath3);
//					System.out.println("已生成挂车pdf"+pdfPath3);
//
					FileSystemUtils.deleteRecursively(new File(temDir));
//					FileSystemUtils.deleteRecursively(new File(temDir2));
//					FileSystemUtils.deleteRecursively(new File(temDir3));

					deptName = t.getCheliangpaizhao();
				}
			}
		}
		folder = fileServer.getPathPrefix()+FilePathConstant.ENCLOSURE_PATH+nyr[0]+"/"+nyr[1]+"/"+deptName+"车辆技术档案.zip";
		ExcelUtils.deleteFile(folder);
//		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(folder));
//		ApacheZipUtils.doCompress1(urlList, bizOut);
		PackageToZIp.toZip(fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+uuid+"\\"+deptName, folder);
		//不要忘记调用
//		bizOut.close();

		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setData(folder);
		rs.setSuccess(true);
		return rs;
	}

	/**
	 * 企业端--车辆档案信息--验证
	 * @update: 黄亚平 添加验证
	 */
	@PostMapping("vehicleDeptThirdImport")
	@ApiLog("车辆档案信息--验证(第三方)")
	@ApiOperation(value = "车辆档案信息--验证(第三方)", notes = "file", position = 10)
	public R vehicleDeptThirdImport(@RequestParam(value = "file") MultipartFile file,BladeUser user,@RequestParam String type,@RequestParam String leixing,Integer deptId) throws ParseException {
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
			VehicleVO vehicleVO = null;
			String deptname =  String.valueOf(a.get("所属单位")).trim();
			if(StringUtils.isBlank(deptname)){
				vehicle.setMsg("所属单位不能为空;");
				vehicle.setImportUrl("icon_cha.png");
				bb++;
			}
			dept = iSysClient.getDeptByName(deptname.trim());
			if (dept != null) {
				if("企业端".equals(type)){
					if(dept.getId().equals(deptId)){
						vehicle.setDeptId(Integer.valueOf(dept.getId()));
						vehicle.setDeptName(deptname);
						vehicle.setImportUrl("icon_gou.png");
						vehicle.setDeptId(dept.getId());
					}else{
						String cph = String.valueOf(a.get("车牌号码")).trim();
						vehicle.setMsg(deptname+"机构不存在;");
						vehicle.setImportUrl("icon_cha.png");
						errorStr+=cph+"----"+deptname+"机构与当前账号企业不一致;";
						bb++;
					}
				}else{
					vehicle.setDeptId(Integer.valueOf(dept.getId()));
					vehicle.setDeptName(deptname);
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setDeptId(dept.getId());
				}
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
			// || "企业端".equals(type)

			vehicleVO = vehicleService.selectCPYS(cheliangpaiz,chepaiyanse);
//			vehicleVO = vehicleService.selectDeptCPYS(cheliangpaiz,chepaiyanse,vehicle.getDeptId().toString());
			if("运维端".equals(type) || "企业端".equals(type)){
				if("更新导入".equals(leixing)){
//					vehicleVO = vehicleService.selectCPYS(cheliangpaiz,chepaiyanse);
					vehicleVO = vehicleService.selectDeptCPYS(cheliangpaiz,chepaiyanse,vehicle.getDeptId().toString());
					if(vehicleVO ==null){
						vehicle.setImportUrl("icon_cha.png");
						errorStr+=cheliangpaiz+"企业车辆不存在;";
						vehicle.setMsg(cheliangpaiz+"企业车辆不存在;");
						bb++;
					}else{
						vehicle.setImportUrl("icon_gou.png");
					}
				}else{
					vehicleVO = vehicleService.selectCPYS(cheliangpaiz, chepaiyanse);
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
				vehicleVO = vehicleService.selectDeptCPYS(cheliangpaiz,chepaiyanse,vehicle.getDeptId().toString());
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

			// || "企业端".equals(type)
			if("运维端".equals(type) || "企业端".equals(type)){
				if("更新导入".equals(leixing)) {
//					vehicleVO = vehicleService.selectCPYS(cheliangpaiz, chepaiyanse);
					vehicleVO = vehicleService.selectDeptCPYS(cheliangpaiz,chepaiyanse,vehicle.getDeptId().toString());
					if(vehicleVO != null){
						vehicle.setZhongduanleixing(vehicleVO.getZhongduanleixing());
					}
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
					vehicle.setBaoxiandaoqishijian(baoxiandaoqi);
					vehicle.setImportUrl("icon_gou.png");
				}else{
					vehicle.setMsg(baoxiandaoqi+",该保险到期日期,不是时间格式;");
					errorStr+=baoxiandaoqi+",该保险到期日期,不是时间格式;";
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
	@PostMapping("vehicleDeptThirdImportOk")
	@ApiLog("车辆档案信息--确认导入(第三方)")
	@ApiOperation(value = "车辆档案信息--确认导入(第三方)", notes = "vehicles", position = 10)
	public R vehicleDeptThirdImportOk(@RequestParam(value = "vehicles") String vehicles,BladeUser user) {
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
			if (StringUtils.isNotBlank(yys) && yys != null && !"null".equals(yys)) {
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

			VehicleVO vehicleVO = vehicleService.selectDeptCPYS(vehicle.getCheliangpaizhao(),vehicle.getChepaiyanse(),vehicle.getDeptId().toString());
			if(vehicleVO!=null){
				log.info("vehicle:"+vehicle);
				isDataValidity = vehicleService.updateSelective(vehicle);
			}else{
				log.info("vehicle:"+vehicle);
				isDataValidity = vehicleService.insertSelective(vehicle);
			}
			if(isDataValidity){
				new Thread(new Runnable() {
					@Override
					public void run() {
						Organizations dept = orrganizationsClient.selectByDeptId(vehicle.getDeptId().toString());
						VehicleJishupingding jishupingding = new VehicleJishupingding();		//技术评定
						jishupingding.setAvjVehicleIds(vehicle.getId());
						jishupingding.setAvjDelete("0");
						jishupingding.setAvjCreateByName(user.getUserName());
						jishupingding.setAvjCreateByIds(user.getUserId().toString());
						jishupingding.setAvjCreateTime(LocalDateTime.now());
						QueryWrapper<VehicleJishupingding> vehicleJishupingdingQueryWrapper = new QueryWrapper<>();
						vehicleJishupingdingQueryWrapper.lambda().eq(VehicleJishupingding::getAvjVehicleIds,jishupingding.getAvjVehicleIds());
						vehicleJishupingdingQueryWrapper.lambda().eq(VehicleJishupingding::getAvjDelete,0);
						VehicleJishupingding vehicleJishupingding = jishupingdingService.getBaseMapper().selectOne(vehicleJishupingdingQueryWrapper);
						if (vehicleJishupingding !=null){
							jishupingding.setAvjIds(vehicleJishupingding.getAvjIds());
							jishupingdingService.updateById(jishupingding);
						}else {
							jishupingdingService.save(jishupingding);
						}

						VehicleDengjizhengshu dengjizhengshu = new VehicleDengjizhengshu();		//登记证书
						dengjizhengshu.setAvdVehicleIds(vehicle.getId());
						dengjizhengshu.setAvdDelete("0");
						dengjizhengshu.setAvdCreateByName(user.getUserName());
						dengjizhengshu.setAvdCreateByIds(user.getUserId().toString());
						dengjizhengshu.setAvdCreateTime(LocalDateTime.now());
						QueryWrapper<VehicleDengjizhengshu> dengjizhengshuQueryWrapper = new QueryWrapper<>();
						dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdVehicleIds,dengjizhengshu.getAvdVehicleIds());
						dengjizhengshuQueryWrapper.lambda().eq(VehicleDengjizhengshu::getAvdDelete,0);
						VehicleDengjizhengshu vehicleDengjizhengshu = dengjizhengshuService.getBaseMapper().selectOne(dengjizhengshuQueryWrapper);
						if (vehicleDengjizhengshu !=null){
							dengjizhengshu.setAvdIds(vehicleDengjizhengshu.getAvdIds());
							dengjizhengshuService.updateById(dengjizhengshu);
						}else {
							dengjizhengshuService.save(dengjizhengshu);
						}

						VehicleJingyingxukezheng jingyingxukezheng = new VehicleJingyingxukezheng();		//经营许可证
						jingyingxukezheng.setAvjVehicleIds(vehicle.getId());
						if (StringUtils.isNotBlank(dept.getDeptName())  && !dept.getDeptName().equals("null")){
							jingyingxukezheng.setAvjOperatorName(dept.getDeptName());
						}
						jingyingxukezheng.setAvjDelete("0");
						jingyingxukezheng.setAvjCreateByName(user.getUserName());
						jingyingxukezheng.setAvjCreateByIds(user.getUserId().toString());
						jingyingxukezheng.setAvjCreateTime(LocalDateTime.now());
						QueryWrapper<VehicleJingyingxukezheng> jingyingxukezhengQueryWrapper = new QueryWrapper<>();
						jingyingxukezhengQueryWrapper.lambda().eq(VehicleJingyingxukezheng::getAvjVehicleIds,jingyingxukezheng.getAvjVehicleIds());
						jingyingxukezhengQueryWrapper.lambda().eq(VehicleJingyingxukezheng::getAvjDelete,0);
						VehicleJingyingxukezheng vehicleJingyingxukezheng = jingyingxukezhengService.getBaseMapper().selectOne(jingyingxukezhengQueryWrapper);
						if (vehicleJingyingxukezheng !=null){
							jingyingxukezheng.setAvjIds(vehicleJingyingxukezheng.getAvjIds());
							jingyingxukezhengService.updateById(jingyingxukezheng);
						}else {
							jingyingxukezhengService.save(jingyingxukezheng);
						}

						VehicleXingnengbaogao xingnengbaogao = new VehicleXingnengbaogao();		//性能报告
						xingnengbaogao.setAvxAvIds(vehicle.getId());
						xingnengbaogao.setAvxDelete("0");
						xingnengbaogao.setAvxCreateByName(user.getUserName());
						xingnengbaogao.setAvxCreateByIds(user.getUserId().toString());
						xingnengbaogao.setAvxCreateTime(LocalDateTime.now());
						QueryWrapper<VehicleXingnengbaogao> xingnengbaogaoQueryWrapper = new QueryWrapper<>();
						xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxAvIds,xingnengbaogao.getAvxAvIds());
						xingnengbaogaoQueryWrapper.lambda().eq(VehicleXingnengbaogao::getAvxDelete,0);
						VehicleXingnengbaogao vehicleXingnengbaogao = xingnengbaogaoService.getBaseMapper().selectOne(xingnengbaogaoQueryWrapper);
						if (vehicleXingnengbaogao !=null){
							xingnengbaogao.setAvxIds(vehicleXingnengbaogao.getAvxIds());
							xingnengbaogaoService.updateById(xingnengbaogao);
						}else {
							xingnengbaogaoService.save(xingnengbaogao);
						}

						VehicleXingshizheng xingshizheng = new VehicleXingshizheng();		//行驶证
						xingshizheng.setAvxAvIds(vehicle.getId());
						xingshizheng.setAvxPlateNo(vehicle.getCheliangpaizhao());
						xingshizheng.setAvxVehicleType(vehicle.getCheliangleixing());
						xingshizheng.setAvxOwner(dept.getDeptName());
						xingshizheng.setAvxAddress("住址");
						xingshizheng.setAvxUseCharter(vehicle.getShiyongxingzhi());
						xingshizheng.setAvxModel(vehicle.getXinghao());
//						xingshizheng.setAvxOwner(vehicle.getOwner());
						xingshizheng.setAvxFileNo(vehicle.getFileNo());
						xingshizheng.setAvxModel(vehicle.getBrandModel());
						xingshizheng.setAvxVin(vehicle.getCheliangshibiedaima());
						xingshizheng.setAvxCurbWeight(0);		//整备质量
						xingshizheng.setAvxDelete("0");
						xingshizheng.setAvxCreateByName(user.getUserName());
						xingshizheng.setAvxCreateByIds(user.getUserId().toString());
						xingshizheng.setAvxCreateTime(LocalDateTime.now());
						if (vehicle.getAvxRegisterDate() != null){
							xingshizheng.setAvxRegisterDate(vehicle.getAvxRegisterDate());
						}
						if ( vehicle.getAvxIssueDate() != null){
							xingshizheng.setAvxIssueDate(vehicle.getAvxIssueDate());
						}
						if (StringUtils.isNotBlank(vehicle.getXingshizhengdaoqishijian())  && !vehicle.getXingshizhengdaoqishijian().equals("null")){
							xingshizheng.setAvxValidUntil(LocalDate.parse(vehicle.getXingshizhengdaoqishijian(),DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						}
						if (StringUtils.isNotBlank(vehicle.getZongzhiliang())  && !vehicle.getZongzhiliang().equals("null")){
							xingshizheng.setAvxTotalMass(Integer.parseInt(vehicle.getZongzhiliang()));
						}
						if (StringUtils.isNotBlank(vehicle.getHedingzaike())  && !vehicle.getHedingzaike().equals("null")){
							if (StringUtils.isNumeric(vehicle.getHedingzaike())){
								xingshizheng.setAvxAuthorizedSeatingCapacity(Integer.parseInt(vehicle.getHedingzaike()));
							}
						}
						if (StringUtils.isNotBlank(vehicle.getCheliangwaikuochicun())  && !vehicle.getCheliangwaikuochicun().equals("null")){
							xingshizheng.setAvxOverallDimensions(vehicle.getCheliangwaikuochicun());
						}
						if (StringUtils.isNotBlank(vehicle.getZhunqianyinzongzhiliang())  && !vehicle.getZhunqianyinzongzhiliang().equals("null")){
							xingshizheng.setAvxQuasiTractiveMass(Integer.parseInt(vehicle.getZhunqianyinzongzhiliang()));
						}
						if (StringUtils.isNotBlank(vehicle.getHedingzaizhiliang())  && !vehicle.getHedingzaizhiliang().equals("null")){
							xingshizheng.setAvxApprovedLoadCapacity(Integer.parseInt(vehicle.getHedingzaizhiliang()));
						}
						if (StringUtils.isNotBlank(vehicle.getQiangzhibaofeishijian())  && !vehicle.getQiangzhibaofeishijian().equals("null")){
							xingshizheng.setAvxBaofeiTime(vehicle.getQiangzhibaofeishijian());
						}
						QueryWrapper<VehicleXingshizheng> xingshizhengQueryWrapper = new QueryWrapper<>();
						xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxAvIds,xingshizheng.getAvxAvIds());
						xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxDelete,0);
						VehicleXingshizheng vehicleXingshizheng = xingshizhengService.getBaseMapper().selectOne(xingshizhengQueryWrapper);
						if (vehicleXingshizheng !=null){
							xingshizheng.setAvxIds(vehicleXingshizheng.getAvxIds());
							xingshizhengService.updateById(xingshizheng);
						}else {
							xingshizhengService.save(xingshizheng);
						}

						VehicleDaoluyunshuzheng daoluyunshuzheng = new VehicleDaoluyunshuzheng(); //道路运输证
						daoluyunshuzheng.setAvdAvIds(vehicle.getId());
						daoluyunshuzheng.setAvdBusinessOwner(dept.getDeptName());
						daoluyunshuzheng.setAvdPlateNo(vehicle.getCheliangpaizhao());
						daoluyunshuzheng.setAvdVehicleType(vehicle.getXinghao());
						daoluyunshuzheng.setAvdPlateColor(vehicle.getChepaiyanse());
						daoluyunshuzheng.setAvdRoadTransportCertificateNo(vehicle.getDaoluyunshuzhenghao());
						daoluyunshuzheng.setAvdDelete("0");
						daoluyunshuzheng.setAvdCreateByName(user.getUserName());
						daoluyunshuzheng.setAvdCreateByIds(user.getUserId().toString());
						daoluyunshuzheng.setAvdCreateTime(DateUtil.now());
						if (StringUtils.isNotBlank(vehicle.getDaoluyunshuzhengchulingriqi())  && !vehicle.getDaoluyunshuzhengchulingriqi().equals("null")){
							daoluyunshuzheng.setAvdIssueDate(LocalDate.parse(vehicle.getDaoluyunshuzhengchulingriqi() , DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						}
						if (StringUtils.isNotBlank(vehicle.getDaoluyunshuzhengyouxiaoqi())  && !vehicle.getDaoluyunshuzhengyouxiaoqi().equals("null")){
							daoluyunshuzheng.setAvdValidUntil(LocalDate.parse(vehicle.getDaoluyunshuzhengyouxiaoqi() , DateTimeFormatter.ofPattern("yyyy-MM-dd")));
						}
						QueryWrapper<VehicleDaoluyunshuzheng> daoluyunshuzhengQueryWrapper = new QueryWrapper<>();
						daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdAvIds,daoluyunshuzheng.getAvdAvIds());
						daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdDelete,0);
						VehicleDaoluyunshuzheng vehicleDaoluyunshuzheng = daoluyunshuzhengService.getBaseMapper().selectOne(daoluyunshuzhengQueryWrapper);
						if (vehicleDaoluyunshuzheng !=null){
							daoluyunshuzheng.setAvdIds(vehicleDaoluyunshuzheng.getAvdIds());
							daoluyunshuzhengService.updateById(daoluyunshuzheng);
						}else {
							daoluyunshuzhengService.save(daoluyunshuzheng);
						}

//						//新增-车辆-驾驶员绑定信息
//						AnbiaoCheliangJiashiyuan cheliangJiashiyuan = new AnbiaoCheliangJiashiyuan();
//						QueryWrapper<AnbiaoCheliangJiashiyuan> cheliangJiashiyuanQueryWrapper = new QueryWrapper<>();
//						cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuan::getJiashiyuanid,vehicle.getJiashiyuanid());
//						cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoCheliangJiashiyuan::getVehid,vehicle.getId());
//						AnbiaoCheliangJiashiyuan deail = cheliangJiashiyuanService.getBaseMapper().selectOne(cheliangJiashiyuanQueryWrapper);
//						if (deail == null){
//							cheliangJiashiyuan.setJiashiyuanid(vehicle.getJiashiyuanid());
//							cheliangJiashiyuan.setVehid(vehicle.getId());
//							cheliangJiashiyuan.setCreatetime(DateUtil.now());
//							cheliangJiashiyuanService.save(cheliangJiashiyuan);
//						}

						if (StringUtils.isNotBlank(vehicle.getJiashiyuanxingming())  && !vehicle.getJiashiyuanxingming().equals("null")){
							QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getJiashiyuanxingming,vehicle.getJiashiyuanxingming());
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShoujihaoma,vehicle.getJiashiyuandianhua());
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,"0");
							jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId,vehicle.getDeptId());
							JiaShiYuan jiaShiYuan = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
							if (jiaShiYuan==null){
								jiaShiYuan = new JiaShiYuan();
								jiaShiYuan.setJiashiyuanxingming(vehicle.getJiashiyuanxingming());
								jiaShiYuan.setDeptId(vehicle.getDeptId());
								jiaShiYuan.setShoujihaoma(vehicle.getJiashiyuandianhua());
								jiaShiYuan.setCaozuoren(user.getUserName());
								jiaShiYuan.setCaozuorenid(user.getUserId());
								jiaShiYuan.setCreatetime(DateUtil.now());
								jiaShiYuan.setDenglumima(DigestUtil.encrypt(jiaShiYuan.getShoujihaoma().substring(jiaShiYuan.getShoujihaoma().length() - 6)));
								jiaShiYuan.setIsdelete(0);
								jiaShiYuan.setStatus(0);
								iJiaShiYuanService.save(jiaShiYuan);

								QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper1 = new QueryWrapper<>();
								jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getJiashiyuanxingming,vehicle.getJiashiyuanxingming());
								jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getShoujihaoma,vehicle.getJiashiyuandianhua());
								jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getIsdelete,"0");
								jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getDeptId,vehicle.getDeptId());
								JiaShiYuan jiaShiYuan1 = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);

								//向入职登记表添加信息
								AnbiaoJiashiyuanRuzhi ruzhi = new AnbiaoJiashiyuanRuzhi();
								QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
								ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, jiaShiYuan1.getId());
								ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
								AnbiaoJiashiyuanRuzhi rzdeail = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
								if (rzdeail == null) {
									ruzhi.setAjrCreateByName(user.getUserName());
									ruzhi.setAjrCreateByIds(user.getUserId().toString());
									ruzhi.setAjrCreateTime(DateUtil.now());
									ruzhi.setAjrDelete("0");
									ruzhi.setAjrAjIds(jiaShiYuan1.getId());
									ruzhi.setAjrName(jiaShiYuan1.getJiashiyuanxingming());
									ruzhi.setAjrApproverStatus("0");
									ruzhiService.save(ruzhi);
								}

								//向驾驶证信息表添加数据
								AnbiaoJiashiyuanJiashizheng jiashizheng = new AnbiaoJiashiyuanJiashizheng();
								QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
								jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, jiaShiYuan1.getId());
								jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
								AnbiaoJiashiyuanJiashizheng jszdeail = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
								if (jszdeail == null) {
									jiashizheng.setAjjAjIds(jiaShiYuan1.getId());
									jiashizheng.setAjjStatus("0");
									jiashizheng.setAjjDelete("0");
									jiashizheng.setAjjCreateByIds(user.getUserId().toString());
									jiashizheng.setAjjCreateByName(user.getUserName());
									jiashizheng.setAjjCreateTime(DateUtil.now());
									jiashizhengService.save(jiashizheng);
								}

								//向从业资格证信息表添加数据
								AnbiaoJiashiyuanCongyezigezheng congyezigezheng = new AnbiaoJiashiyuanCongyezigezheng();
								QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
								congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, jiaShiYuan1.getId());
								congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
								AnbiaoJiashiyuanCongyezigezheng cyzdeail = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
								if (cyzdeail == null) {
									congyezigezheng.setAjcAjIds(jiaShiYuan1.getId());
									congyezigezheng.setAjcStatus("0");
									congyezigezheng.setAjcCreateTime(DateUtil.now());
									congyezigezheng.setAjcDelete("0");
									congyezigezhengService.save(congyezigezheng);
								}

								//向体检信息表添加数据
								AnbiaoJiashiyuanTijian tijian = new AnbiaoJiashiyuanTijian();
								QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
								tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, jiaShiYuan1.getId());
								tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
								AnbiaoJiashiyuanTijian tjdeail = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
								if (tjdeail == null) {
									tijian.setAjtCreateByName(user.getUserName());
									tijian.setAjtCreateByIds(user.getUserId().toString());
									tijian.setAjtCreateTime(DateUtil.now());
									tijian.setAjtDelete("0");
									tijian.setAjtAjIds(jiaShiYuan1.getId());
									tijianService.save(tijian);
								}

								//向岗前培训信息表添加数据
								AnbiaoJiashiyuanGangqianpeixun gangqianpeixun = new AnbiaoJiashiyuanGangqianpeixun();
								QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
								gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, jiaShiYuan1.getId());
								gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
								AnbiaoJiashiyuanGangqianpeixun gqpxdeail = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
								if (gqpxdeail == null) {
									gangqianpeixun.setAjgCreateByName(user.getUserName());
									gangqianpeixun.setAjgCreateByIds(user.getUserId().toString());
									gangqianpeixun.setAjgCreateTime(DateUtil.now());
									gangqianpeixun.setAjgDelete("0");
									gangqianpeixun.setAjgAjIds(jiaShiYuan1.getId());
									gangqianpeixunService.save(gangqianpeixun);
								}

								//向三年无重大责任事故证明信息表添加数据
								AnbiaoJiashiyuanWuzezhengming wuzezhengming = new AnbiaoJiashiyuanWuzezhengming();
								QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
								wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, jiaShiYuan1.getId());
								wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
								AnbiaoJiashiyuanWuzezhengming wzzmdeail = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
								if (wzzmdeail == null) {
									wuzezhengming.setAjwCreateByName(user.getUserName());
									wuzezhengming.setAjwCreateByIds(user.getUserId().toString());
									wuzezhengming.setAjwCreateTime(DateUtil.now());
									wuzezhengming.setAjwDelete("0");
									wuzezhengming.setAjwAjIds(jiaShiYuan1.getId());
									wuzezhengmingService.save(wuzezhengming);
								}

								//向驾驶员安全责任书信息表添加数据
								AnbiaoJiashiyuanAnquanzerenshu anquanzerenshu = new AnbiaoJiashiyuanAnquanzerenshu();
								QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu>();
								anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaAjIds, jiaShiYuan1.getId());
								anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaDelete, "0");
								AnbiaoJiashiyuanAnquanzerenshu aqzesdeail = anquanzerenshuService.getBaseMapper().selectOne(anquanzerenshuQueryWrapper);
								if (aqzesdeail == null) {
									anquanzerenshu.setAjaCreateByName(user.getUserName());
									anquanzerenshu.setAjaCreateByIds(user.getUserId().toString());
									anquanzerenshu.setAjaCreateTime(DateUtil.now());
									anquanzerenshu.setAjaDelete("0");
									anquanzerenshu.setAjaAjIds(jiaShiYuan1.getId());
									anquanzerenshuService.save(anquanzerenshu);
								}

								//向驾驶员职业危害告知书信息表添加数据
								AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishu = new AnbiaoJiashiyuanWeihaigaozhishu();
								QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
								weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwAjIds, jiaShiYuan1.getId());
								weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwDelete, "0");
								AnbiaoJiashiyuanWeihaigaozhishu whgzsdeail = weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);
								if (whgzsdeail == null) {
									weihaigaozhishu.setAjwCreateByName(user.getUserName());
									weihaigaozhishu.setAjwCreateByIds(user.getUserId().toString());
									weihaigaozhishu.setAjwCreateTime(DateUtil.now());
									weihaigaozhishu.setAjwDelete("0");
									weihaigaozhishu.setAjwAjIds(jiaShiYuan1.getId());
									weihaigaozhishuService.save(weihaigaozhishu);
								}

								//向劳动合同信息表添加数据
								AnbiaoJiashiyuanLaodonghetong laodonghetong = new AnbiaoJiashiyuanLaodonghetong();
								QueryWrapper<AnbiaoJiashiyuanLaodonghetong> laodonghetongQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanLaodonghetong>();
								laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwAjIds, jiaShiYuan1.getId());
								laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwDelete, "0");
								AnbiaoJiashiyuanLaodonghetong ldhtdeail = laodonghetongService.getBaseMapper().selectOne(laodonghetongQueryWrapper);
								if (ldhtdeail == null) {
									laodonghetong.setAjwCreateByName(user.getUserName());
									laodonghetong.setAjwCreateByIds(user.getUserId().toString());
									laodonghetong.setAjwCreateTime(DateUtil.now());
									laodonghetong.setAjwDelete("0");
									laodonghetong.setAjwAjIds(jiaShiYuan1.getId());
									laodonghetongService.save(laodonghetong);
								}

								//向其他信息表添加数据
								AnbiaoJiashiyuanQita qita = new AnbiaoJiashiyuanQita();
								QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanQita>();
								qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, jiaShiYuan1.getId());
								qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
								AnbiaoJiashiyuanQita qtdeail = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);
								if (qtdeail == null) {
									qita.setAjtCreateByName(user.getUserName());
									qita.setAjtCreateByIds(user.getUserId().toString());
									qita.setAjtCreateTime(DateUtil.now());
									qita.setAjtDelete("0");
									qita.setAjtAjIds(jiaShiYuan1.getId());
									qitaService.save(qita);
								}

								QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper2 = new QueryWrapper<>();
								jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getJiashiyuanxingming,vehicle.getJiashiyuanxingming());
								jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getIsdelete,"0");
								jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getShoujihaoma,vehicle.getJiashiyuandianhua());
								jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getDeptId,vehicle.getDeptId());
								JiaShiYuan jiaShiYuan2 = iJiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper2);
								vehicle.setJiashiyuanid(jiaShiYuan2.getId());
								vehicleService.getBaseMapper().updateById(vehicle);

							}else {
								vehicle.setJiashiyuanid(jiaShiYuan.getId());
								vehicleService.getBaseMapper().updateById(vehicle);
							}
						}


					}
				}).start();
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
