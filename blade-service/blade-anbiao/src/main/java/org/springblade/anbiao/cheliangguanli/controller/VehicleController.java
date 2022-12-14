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
import org.springblade.anbiao.cheliangguanli.vo.VehicleListVO;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.anbiao.configure.entity.Configure;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IOrganizationsClient;
import org.springblade.anbiao.jiashiyuan.entity.AnbiaoCheliangJiashiyuan;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoCheliangJiashiyuanService;
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
import java.time.format.DateTimeFormatter;
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
@Api(value = "??????????????????", tags = "??????????????????")
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
	private IAnbiaoCheliangJiashiyuanService cheliangJiashiyuanService;

    @PostMapping("/list")
	@ApiLog("??????-??????????????????")
    @ApiOperation(value = "??????-??????????????????", notes = "??????VehiclePage", position = 1)
    public R<VehiclePage<VehicleListVO>> list(@RequestBody VehiclePage vehiclepage) {
        VehiclePage<VehicleListVO> pages = vehicleService.selectVehiclePage(vehiclepage);
//		List<VehicleVO>  list=pages.getRecords();
//		for (int i = 0; i <list.size() ; i++) {
//			//????????????
//			if(StrUtil.isNotEmpty(list.get(i).getCheliangzhaopian()) && list.get(i).getCheliangzhaopian().contains("http") == false){
//				list.get(i).setCheliangzhaopian(fileUploadClient.getUrl(list.get(i).getCheliangzhaopian()));
//			}
//
//			//??????????????????
//			if(StrUtil.isNotEmpty(list.get(i).getRanliaoxiaohaofujian()) && list.get(i).getRanliaoxiaohaofujian().contains("http") == false){
//				list.get(i).setRanliaoxiaohaofujian(fileUploadClient.getUrl(list.get(i).getRanliaoxiaohaofujian()));
//			}
//			//???????????????
//			if(StrUtil.isNotEmpty(list.get(i).getXingshifujian()) && list.get(i).getXingshifujian().contains("http") == false){
//				list.get(i).setXingshifujian(fileUploadClient.getUrl(list.get(i).getXingshifujian()));
//			}
//		}
        return R.data(pages);
    }

    @GetMapping("/detail")
	@ApiLog("??????-??????????????????")
    @ApiOperation(value = "??????-??????????????????", notes = "??????id", position = 2)
    public R<VehicleInfo> detail(String id) {
        VehicleVO detail = vehicleService.selectByKey(id);
		//????????????
		if(StrUtil.isNotEmpty(detail.getCheliangzhaopian()) && detail.getCheliangzhaopian().contains("http") == false){
			detail.setCheliangzhaopian(fileUploadClient.getUrl(detail.getCheliangzhaopian()));
		}
		//??????????????????
		if(StrUtil.isNotEmpty(detail.getRanliaoxiaohaofujian()) && detail.getRanliaoxiaohaofujian().contains("http") == false){
			detail.setRanliaoxiaohaofujian(fileUploadClient.getUrl(detail.getRanliaoxiaohaofujian()));
		}
		//???????????????
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
		if(detail.getDaoluyunshuzhengchulingriqi() != null){
			v.setJyxkzyouxiaoqiStart(LocalDate.parse(detail.getDaoluyunshuzhengchulingriqi(), fmt));
		}
		if(detail.getDaoluyunshuzhengyouxiaoqi() != null){
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
//				v.setShifouguochan("???");
//			}else{
//				v.setShifouguochan("???");
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
	@ApiLog("????????????")
	@ApiOperation(value = "????????????", notes = "??????postId,cheliangpaizhao", position = 2)
	@ApiImplicitParams({ @ApiImplicitParam(name = "postId", value = "??????id", required = true),
		@ApiImplicitParam(name = "cheliangpaizhao", value = "????????????", required = false)
	})
	public R<List<VehicleCP>> selectByCL(String postId,String cheliangpaizhao) {
		Dept dept=iSysClient.selectByJGBM("??????",postId);
		List<VehicleCP> detail = vehicleService.selectCL(dept.getId().toString(),cheliangpaizhao);
		return R.data(detail);
	}

	@GetMapping("/selectByCPYS")
	@ApiLog("???????????????-????????????")
	@ApiOperation(value = "???????????????-????????????", notes = "??????cheliangpaizhao???chepaiyanse", position = 2)
	public R<Map<String,Object>> selectByCPYS(String cheliangpaizhao,String chepaiyanse) {
		Map<String,Object> map = new HashMap<String,Object>();
		VehicleVO vehicleVO = vehicleService.selectByCPYS(cheliangpaizhao,chepaiyanse);
		//????????????
		if(StrUtil.isNotEmpty(vehicleVO.getCheliangzhaopian())){
			vehicleVO.setCheliangzhaopian(fileUploadClient.getUrl(vehicleVO.getCheliangzhaopian()));
		}
		//??????????????????
		if(StrUtil.isNotEmpty(vehicleVO.getRanliaoxiaohaofujian())){
			vehicleVO.setRanliaoxiaohaofujian(fileUploadClient.getUrl(vehicleVO.getRanliaoxiaohaofujian()));
		}
		//???????????????
		if(StrUtil.isNotEmpty(vehicleVO.getXingshifujian())){
			vehicleVO.setXingshifujian(fileUploadClient.getUrl(vehicleVO.getXingshifujian()));
		}

		map.put("cheliang",vehicleVO);

//		//??????????????????id?????????????????????id
//		CheliangrenyuanbangdingPage Page=new CheliangrenyuanbangdingPage();
//		Page.setDeptId(vehicleVO.getDeptId());
//		Page.setCheliangid(vehicleVO.getId());
//		Page.setShifoudangban("0");
//		CheliangrenyuanbangdingPage<CheliangrenyuanbangdingVO> pages = cheliangrenyuanbangdingService.selectPageList(Page);

//		JiaShiYuan detal=new JiaShiYuan();
//		if(pages!=null){
//			//?????????????????????
//			List<CheliangrenyuanbangdingVO> records = pages.getRecords();
//			if(records != null && records.size()>0){
//				detal=iJiaShiYuanService.selectByIds(records.get(0).getJiashiyuanid());
//			}
//			//??????
//			if(StrUtil.isNotEmpty(detal.getZhaopian())){
//				detal.setZhaopian(fileUploadClient.getUrl(detal.getZhaopian()));
//			}
//			//???????????????
//			if(StrUtil.isNotEmpty(detal.getShenfenzhengfujian())){
//				detal.setShenfenzhengfujian(fileUploadClient.getUrl(detal.getShenfenzhengfujian()));
//			}addSubtemplateFile
//			//???????????????
//			if(StrUtil.isNotEmpty(detal.getCongyezhengfujian())){
//				detal.setCongyezhengfujian(fileUploadClient.getUrl(detal.getCongyezhengfujian()));
//			}
//			//???????????????
//			if(StrUtil.isNotEmpty(detal.getJiashizhengfujian())){
//				detal.setJiashizhengfujian(fileUploadClient.getUrl(detal.getJiashizhengfujian()));
//			}
//			//?????????
//			if(StrUtil.isNotEmpty(detal.getFuyinjian())){
//				detal.setFuyinjian(fileUploadClient.getUrl(detal.getFuyinjian()));
//			}
//		}
//
//		map.put("jiashiyuan",detal);
		return R.data(map);
	}

	@PostMapping("/addSave")
	@ApiLog("??????-??????????????????????????????")
	@ApiOperation(value = "??????-??????????????????????????????", notes = "??????VehicleInfo", position = 30)
	public R addSave(@RequestBody VehicleInfo v,BladeUser user) {
		R r = new R();

		if(user == null) {
			r.setMsg("?????????");
			r.setCode(401);
			return r;
		}

		VehicleVO vehicleVO = vehicleService.selectCPYS(v.getCheliangpaizhao(),v.getChepaiyanse());
		if(vehicleVO!=null){
			r.setMsg(vehicleVO.getCheliangpaizhao()+"???????????????");
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
		if("???".equals(v.getShifouguochan())){
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
					VehicleJishupingding jishupingding = new VehicleJishupingding();		//????????????
					jishupingding.setAvjVehicleIds(vehicle.getId());
					jishupingding.setAvjDelete("0");
					jishupingding.setAvjCreateByName(user.getUserName());
					jishupingding.setAvjCreateByIds(user.getUserId().toString());
					jishupingding.setAvjCreateTime(LocalDateTime.now());
					jishupingdingService.save(jishupingding);

					VehicleDengjizhengshu dengjizhengshu = new VehicleDengjizhengshu();		//????????????
					dengjizhengshu.setAvdVehicleIds(vehicle.getId());
					dengjizhengshu.setAvdDelete("0");
					dengjizhengshu.setAvdCreateByName(user.getUserName());
					dengjizhengshu.setAvdCreateByIds(user.getUserId().toString());
					dengjizhengshu.setAvdCreateTime(LocalDateTime.now());
					dengjizhengshuService.save(dengjizhengshu);

					VehicleJingyingxukezheng jingyingxukezheng = new VehicleJingyingxukezheng();		//???????????????
					jingyingxukezheng.setAvjVehicleIds(dept.getId());
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

					VehicleXingnengbaogao xingnengbaogao = new VehicleXingnengbaogao();		//????????????
					xingnengbaogao.setAvxAvIds(vehicle.getId());
					xingnengbaogao.setAvxDelete("0");
					xingnengbaogao.setAvxCreateByName(user.getUserName());
					xingnengbaogao.setAvxCreateByIds(user.getUserId().toString());
					xingnengbaogao.setAvxCreateTime(LocalDateTime.now());
					xingnengbaogaoService.save(xingnengbaogao);

					VehicleXingshizheng xingshizheng = new VehicleXingshizheng();		//?????????
					xingshizheng.setAvxAvIds(vehicle.getId());
					xingshizheng.setAvxPlateNo(v.getCheliangpaizhao());
					xingshizheng.setAvxVehicleType(v.getCheliangleixing());
					xingshizheng.setAvxOwner(dept.getDeptName());
					xingshizheng.setAvxAddress("??????");
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
					xingshizheng.setAvxCurbWeight(0);		//????????????
					xingshizheng.setAvxOverallDimensions(v.getWaikuochicun());
					if(v.getZhunqianyinzongzhiliang() != null){
						xingshizheng.setAvxQuasiTractiveMass(Integer.parseInt(v.getZhunqianyinzongzhiliang()));
					}
					xingshizheng.setAvxDelete("0");
					xingshizheng.setAvxCreateByName(user.getUserName());
					xingshizheng.setAvxCreateByIds(user.getUserId().toString());
					xingshizheng.setAvxCreateTime(LocalDateTime.now());
					xingshizhengService.save(xingshizheng);


					VehicleDaoluyunshuzheng daoluyunshuzheng = new VehicleDaoluyunshuzheng(); //???????????????
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
					daoluyunshuzheng.setAvdCreateTime(LocalDateTime.now());
					daoluyunshuzhengService.save(daoluyunshuzheng);

					//??????-??????-?????????????????????
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
			r.setMsg("????????????");
			r.setCode(200);
		}else{
			r.setMsg("????????????");
			r.setCode(500);
		}
		return r;
	}

	@PostMapping("/updateSave")
	@ApiLog("??????-??????????????????????????????")
	@ApiOperation(value = "??????-??????????????????????????????", notes = "??????VehicleInfo", position = 31)
	public R updateSave(@RequestBody VehicleInfo v,BladeUser user) {
    	R r = new R();

    	if(user == null) {
    		r.setMsg("?????????");
    		r.setCode(401);
			return r;
		}

		VehicleVO vehicleVO = vehicleService.selectCPYS(v.getCheliangpaizhao(),v.getChepaiyanse());
		if(vehicleVO == null){
			r.setMsg(vehicleVO.getCheliangpaizhao()+"???????????????");
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

//		if("???".equals(v.getShifouguochan())){
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

//		String str="1";
//		//?????????
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
//							}
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
//					VehicleJishupingding jishupingding = jishupingdingService.selectVehicleJishupingdingByVehicleIds(v.getId());		//????????????
//					jishupingding.setAvjUpdateByName(user.getUserName());
//					jishupingding.setAvjUpdateByIds(user.getUserId().toString());
//					jishupingding.setAvjUpdateTime(LocalDateTime.now());
//					jishupingdingService.updateById(jishupingding);
//
//					VehicleDengjizhengshu dengjizhengshu = dengjizhengshuService.selectVehicleDengjizhengshuByVehicleIds(v.getId());		//????????????
//					dengjizhengshu.setAvdUpdateByName(user.getUserName());
//					dengjizhengshu.setAvdUpdateByIds(user.getUserId().toString());
//					dengjizhengshu.setAvdUpdateTime(LocalDateTime.now());
//					dengjizhengshuService.updateById(dengjizhengshu);
//
//					VehicleJingyingxukezheng jingyingxukezheng = jingyingxukezhengService.selectVehicleJingyingxukezhengByVehicleIds(v.getId());		//???????????????
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
//					VehicleXingnengbaogao xingnengbaogao = xingnengbaogaoService.selectVehicleXingnengbaogaoByVehicleIds(v.getId());		//????????????
//					xingnengbaogao.setAvxUpdateByName(user.getUserName());
//					xingnengbaogao.setAvxUpdateByIds(user.getUserId().toString());
//					xingnengbaogao.setAvxUpdateTime(LocalDateTime.now());
//					xingnengbaogaoService.updateById(xingnengbaogao);
//
//					VehicleXingshizheng xingshizheng = xingshizhengService.selectVehicleJishupingdingByVehicleIds(v.getId());		//?????????
//					xingshizheng.setAvxPlateNo(v.getCheliangpaizhao());
//					xingshizheng.setAvxVehicleType(v.getCheliangleixing());
//					xingshizheng.setAvxOwner(dept.getDeptName());
//					xingshizheng.setAvxAddress("??????");
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
//					xingshizheng.setAvxCurbWeight(0);		//????????????
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
//					VehicleDaoluyunshuzheng daoluyunshuzheng = daoluyunshuzhengService.selectVehicleDaoluyunshuzhengByVehicleIds(v.getId()); //???????????????
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
			r.setMsg("????????????");
			r.setCode(200);
		}else{
			r.setMsg("????????????");
			r.setCode(500);
		}
    	return r;
	}

	@PostMapping("/updateDetailedSave")
	@ApiLog("??????-??????????????????????????????")
	@ApiOperation(value = "??????-??????????????????????????????", notes = "??????Vehicle", position = 32)
	public R updateDetailed(@RequestBody VehicleDetailed vd,BladeUser user) {
    	R r = new R();
    	if(!StringUtil.isNotBlank(vd.getVehicleId())) {
    		r.setMsg("??????????????????????????????");
			r.setCode(500);
			return r;
		}

//    	Vehicle vehicle = new Vehicle();
//    	vehicle.setId(vd.getVehicleId());
		Vehicle vehicleVO = vehicleService.getById(vd.getVehicleId());
    	if(vehicleVO == null) {
			r.setMsg("????????????????????????????????????????????????????????????????????????");
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
				stringBuilder.append("????????????????????????????????????"+"\r\n");
			} else {
				stringBuilder.append("????????????????????????????????????"+"\r\n");
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
				dlysz.setAvdUpdateTime(LocalDateTime.now());
			} else {
				dlysz.setAvdCreateByName(user.getUserName());
				dlysz.setAvdCreateByIds(user.getUserId().toString());
				dlysz.setAvdCreateTime(LocalDateTime.now());
			}

			if(daoluyunshuzhengService.saveOrUpdate(dlysz)) {
				stringBuilder.append("????????????????????????????????????"+"\r\n");
			} else {
				stringBuilder.append("????????????????????????????????????"+"\r\n");
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
				stringBuilder.append("???????????????????????????"+"\r\n");
			} else {
				stringBuilder.append("???????????????????????????"+"\r\n");
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
				stringBuilder.append("?????????????????????????????????"+"\r\n");
			} else {
				stringBuilder.append("?????????????????????????????????"+"\r\n");
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
				stringBuilder.append("???????????????????????????"+"\r\n");
			} else {
				stringBuilder.append("???????????????????????????"+"\r\n");
			}
		}
		r.setMsg(stringBuilder.toString());
		r.setCode(200);
    	return r;
	}

    @PostMapping("/insert")
	@ApiLog("??????-??????????????????")
    @ApiOperation(value = "??????-??????????????????", notes = "??????Vehicle", position = 3)
    public R insert(@RequestBody Vehicle vehicle,BladeUser user) {
		R r = new R();

		if(user == null) {
			r.setMsg("?????????");
			r.setCode(401);
			return r;
		}

    	VehicleVO vehicleVO = vehicleService.selectCPYS(vehicle.getCheliangpaizhao(),vehicle.getChepaiyanse());
		if(vehicleVO!=null){
			r.setMsg(vehicleVO.getCheliangpaizhao()+"???????????????");
			r.setCode(500);
			return r;
		}
		VehicleVO vehicleVO1 = vehicleService.selectByZongDuan(vehicle.getZongduanid());
		if(vehicleVO1 !=null ){
			r.setMsg(vehicleVO1.getZongduanid()+"?????????ID?????????");
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
		//?????????
		if(StringUtil.isNotBlank(vehicle.getCheliangzhaopian())){
			fileUploadClient.updateCorrelation(vehicle.getCheliangzhaopian(),str);
		}
		boolean i = vehicleService.save(vehicle);
		if(i==true){
			r.setMsg("????????????");
			r.setCode(200);
		}else{
			r.setMsg("????????????");
			r.setCode(500);
		}
        return r;
    }

    @PostMapping("/update")
	@ApiLog("??????-??????????????????")
    @ApiOperation(value = "??????-??????????????????", notes = "??????Vehicle", position = 4)
    public R update(@RequestBody Vehicle vehicle,BladeUser user) {
		R r = new R();
		VehicleVO vehicleVO = vehicleService.selectCPYS(vehicle.getCheliangpaizhao(),vehicle.getChepaiyanse());
//		if(vehicleVO!=null){
//			r.setMsg(vehicleVO.getCheliangpaizhao()+"???????????????");
//			r.setCode(500);
//			return r;
//		}
//		VehicleVO vehicleVO1 = vehicleService.selectByZongDuan(vehicle.getZongduanid());
//		if(vehicleVO1 !=null ){
//			r.setMsg(vehicleVO1.getZongduanid()+"?????????ID?????????");
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
			r.setMsg("????????????");
			r.setCode(200);
		}else{
			r.setMsg("????????????");
			r.setCode(500);
		}
		return r;
    }

	@PostMapping("/del")
	@ApiLog("??????-??????????????????")
	@ApiOperation(value = "??????-??????????????????", notes = "????????????id", position = 5)
	public R del(@RequestParam String id) {
		R r = new R();
		String[] idsss = id.split(",");
		//??????????????????????????????
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//??????????????????????????????????????????????????????
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			boolean ss = vehicleService.deleleVehicle(idsss[i]);
			if (ss){
				r.setMsg("????????????");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("????????????");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

	@PostMapping("/updateVehicleOutStatus")
	@ApiLog("??????-??????????????????")
	@ApiOperation(value = "??????-??????????????????", notes = "????????????id", position = 15)
	public R updateVehicleOutStatus(@RequestParam String id) {
		R r = new R();
		String[] idsss = id.split(",");
		//??????????????????????????????
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//??????????????????????????????????????????????????????
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			boolean ss = vehicleService.updateVehicleOutStatus(idsss[i]);
			if (ss){
				r.setMsg("????????????");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("????????????");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

	@PostMapping("/updateVehicleSignStatus")
	@ApiLog("??????-??????????????????")
	@ApiOperation(value = "??????-??????????????????", notes = "????????????id", position = 15)
	public R updateVehicleSignStatus(@RequestParam String id) {
		R r = new R();
		String[] idsss = id.split(",");
		//??????????????????????????????
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//??????????????????????????????????????????????????????
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			boolean ss = vehicleService.updateVehicleSignStatus(idsss[i]);
			if (ss){
				r.setMsg("????????????");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("????????????");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

	@PostMapping("/updateVehicleScrapStatus")
	@ApiLog("??????-??????????????????")
	@ApiOperation(value = "??????-??????????????????", notes = "????????????id", position = 16)
	public R updateVehicleScrapStatus(@RequestParam String id) {
		R r = new R();
		String[] idsss = id.split(",");
		//??????????????????????????????
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//??????????????????????????????????????????????????????
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			boolean ss = vehicleService.updateVehicleScrapStatus(idsss[i]);
			if (ss){
				r.setMsg("????????????");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("????????????");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

    /********************************** ????????? ***********************/
    /**
     * ???????????????
     */
    @PostMapping("/insertMap")
	@ApiLog("???????????????-??????????????????")
    @ApiOperation(value = "???????????????-??????????????????", notes = "??????biaodancanshu???deptId", position = 6)
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
     * ???????????????
     */
    @PostMapping("/updateMap")
	@ApiLog("???????????????-??????????????????")
    @ApiOperation(value = "???????????????-??????????????????", notes = "??????biaodancanshu???id", position = 7)
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
     * ???????????????
     */
    @PostMapping("/delMap")
	@ApiLog("???????????????-??????????????????")
    @ApiOperation(value = "???????????????-??????????????????", notes = "??????id", position = 8)
    public R delMap(@ApiParam(value = "??????id", required = true) @RequestParam String id) {
        return R.status(mapService.delMap("anbiao_vehicle_map", id));
    }

    /**
     * @Description: ????????????id??????????????????????????????
     * @Param: [deptId]
     * @return: org.springblade.core.tool.api.R<java.util.List>
     * @Author: hyp
     * @date 2021-04-28
     */
    @GetMapping("/listMap")
	@ApiLog("??????????????????-??????????????????")
    @ApiOperation(value = "??????????????????-??????????????????", notes = "??????deptId", position = 9)
    public R<JSONArray> listMap(Integer deptId) {
        List<Configure> list1 = mapService.selectMapList("anbiao_vehicle_map", deptId);
        String str = "";
        for (int i = 0; i < list1.size(); i++) {
            //?????????json?????????put id
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

	@ApiLog("????????????-??????-????????????")
	@ApiOperation(value = "????????????-??????-??????", notes = "???????????? ????????????", position = 11)
	public  R vehicledetai(@ApiParam(value = "??????", required = true) @RequestParam String cheliangpaizhao,
						   @ApiParam(value = "????????????", required = true) @RequestParam String chepaiyanse,
						   @ApiParam(value = "deptId", required = true) @RequestParam Integer deptId){
		Vehicle vehicle = vehicleService.vehileOne(cheliangpaizhao, chepaiyanse,deptId);
		//????????????
		if(StrUtil.isNotEmpty(vehicle.getCheliangzhaopian())){
			vehicle.setCheliangzhaopian(fileUploadClient.getUrl(vehicle.getCheliangzhaopian()));
		}

		//??????????????????
		if(StrUtil.isNotEmpty(vehicle.getRanliaoxiaohaofujian())){
			vehicle.setRanliaoxiaohaofujian(fileUploadClient.getUrl(vehicle.getRanliaoxiaohaofujian()));
		}
		//???????????????
		if(StrUtil.isNotEmpty(vehicle.getXingshifujian())){
			vehicle.setXingshifujian(fileUploadClient.getUrl(vehicle.getXingshifujian()));
		}
		return R.data(vehicle);
	}

	@GetMapping("/getVehicleAll")
	@ApiLog("GPS??????????????????")
	@ApiOperation(value = "GPS??????????????????", notes = "??????caozuoshijian", position = 2)
	public R<List<VehicleVO>> getVehicleAll(String caozuoshijian) {
		List<VehicleVO> detail = vehicleService.selectVehicleAll(caozuoshijian);
		return R.data(detail);
	}

	@GetMapping("/getVehicleAll_FY")
	@ApiLog("GPS??????????????????-????????????")
	@ApiOperation(value = "GPS??????????????????-????????????", notes = "??????caozuoshijian", position = 2)
	public R<List<VehicleVO>> getVehicleAll_FY(String caozuoshijian,String deptId) {
		List<VehicleVO> detail = vehicleService.selectVehicleAll_FY(caozuoshijian,deptId);
		return R.data(detail);
	}

	@GetMapping("/getVehicleAll_NFY")
	@ApiLog("GPS??????????????????-????????????????????????????????????")
	@ApiOperation(value = "GPS??????????????????-????????????", notes = "??????caozuoshijian", position = 2)
	public R<List<VehicleVO>> getVehicleAll_NFY(String caozuoshijian,String deptId) {
		List<VehicleVO> detail = vehicleService.selectVehicleAll_NFY(caozuoshijian,deptId);
		return R.data(detail);
	}

	@PostMapping("/updateDeptId")
	@ApiLog("??????????????????-????????????")
	@ApiOperation(value = "??????????????????-????????????", notes = "????????????ID,??????ID", position = 10)
	public R updateDeptId(@RequestParam String id,@RequestParam String deptId) {
		R r = new R();
		String[] idsss = id.split(",");
		//??????????????????????????????
		List<String> listid = new ArrayList<String>();
		for (int i=0; i<idsss.length; i++) {
			if(!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//??????????????????????????????????????????????????????
		String[]  idss= listid.toArray(new String[1]);
		for(int i = 0;i< idss.length;i++){
			boolean ss = vehicleService.updateDeptId(deptId,idsss[i]);
			if (ss){
				r.setMsg("????????????");
				r.setCode(200);
				r.setData(ss);
			}else{
				r.setMsg("????????????");
				r.setCode(500);
				r.setData(null);
			}
		}
		return r;
	}

	@PostMapping("/selectGDSVehicleList")
	@ApiLog("??????-?????????????????????????????????")
	@ApiOperation(value = "??????-?????????????????????????????????", notes = "??????vehiclepage", position = 11)
	public R<VehiclePage<VehicleGDSTJ>> selectGDSVehicleList(@RequestBody VehiclePage vehiclepage) {
		VehiclePage<VehicleGDSTJ> pages = vehicleService.selectGDSVehiclePage(vehiclepage);
		return R.data(pages);
	}

	@PostMapping("/selectGDSMXVehicleList")
	@ApiLog("??????-???????????????????????????????????????")
	@ApiOperation(value = "??????-???????????????????????????????????????", notes = "??????vehiclepage", position = 12)
	public R<VehiclePage<VehicleGDSTJ>> selectGDSMXVehicleList(@RequestBody VehiclePage vehiclepage) {
		VehiclePage<VehicleGDSTJ> pages = vehicleService.selectGDSMXVehiclePage(vehiclepage);
		return R.data(pages);
	}

	@GetMapping("/getByIdVehicleList")
	@ApiLog("????????????ID????????????????????????")
	@ApiOperation(value = "????????????ID????????????????????????", notes = "??????deptId", position = 2)
	public R<List<Vehicle>> getByIdVehicleList(Integer deptId) {
		List<Vehicle> detail = vehicleService.vehileList(deptId);
		return R.data(detail);
	}

	@PostMapping("/updateVehicleZongDuanId")
	@ApiLog("??????????????????-????????????????????????")
	@ApiOperation(value = "??????????????????-????????????????????????", notes = "????????????id,?????????ID", position = 19)
	public R updateVehicleZongDuanId(@RequestParam String id,String newzongduanid,String username,String userid) {
		R r = new R();
		VehicleVO vehicleVO1 = vehicleService.selectByZongDuan(newzongduanid);
		if(vehicleVO1 !=null ){
			r.setMsg(vehicleVO1.getZongduanid()+"?????????ID?????????");
			r.setCode(500);
			return r;
		}
		boolean ss = vehicleService.updateVehicleZongDuanId(newzongduanid,username,userid,id);
		if (ss){
			r.setMsg("????????????");
			r.setCode(200);
			r.setSuccess(true);
		}else{
			r.setMsg("????????????");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	/**
	 * ?????????--??????????????????--??????
	 * @update: ????????? ????????????
	 */
	@PostMapping("vehicleDeptImport")
	@ApiLog("??????????????????--??????(?????????)")
	@ApiOperation(value = "??????????????????--??????(?????????)", notes = "file", position = 10)
	public R vehicleDeptImport(@RequestParam(value = "file") MultipartFile file,BladeUser user) throws ParseException {
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("????????????????????????");
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
		//??????????????????
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//????????????????????????
		int aa=0;
		//????????????????????????
		int bb=0;
		//????????????????????????????????????????????????false
		boolean isDataValidity=true;
		//????????????
		String errorStr="";

		List<Map<String,Object>> readAll = reader.readAll();
		if(readAll.size()>2000){
			errorStr+="??????????????????2000?????????????????????";
			rs.setMsg(errorStr);
			rs.setCode(500);
			return rs;
		}
		if(readAll.size() < 1){
			errorStr+="?????????????????????excel????????????";
			rs.setMsg(errorStr);
			rs.setCode(500);
			return rs;
		}

		List<Vehicle> vehicles=new ArrayList<Vehicle>();
		for(Map<String,Object> a:readAll){
			aa++;
			Vehicle vehicle= new Vehicle();
			Dept dept;
			String deptname =  String.valueOf(a.get("????????????")).trim();
			if(StringUtils.isBlank(deptname)){
				vehicle.setMsg("????????????????????????;");
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
				String cph = String.valueOf(a.get("????????????")).trim();
				vehicle.setMsg(deptname+"?????????????????????;");
				vehicle.setImportUrl("icon_cha.png");
				errorStr+=cph+"----"+deptname+"?????????????????????;";
				bb++;
			}
			//??????????????????
			String cheliangpaiz=String.valueOf(a.get("????????????")).trim();
			if(StringUtils.isBlank(cheliangpaiz) && !cheliangpaiz.equals("null")){
				vehicle.setMsg("????????????????????????;");
				vehicle.setImportUrl("icon_cha.png");
				bb++;
			}else{
				vehicle.setCheliangpaizhao(cheliangpaiz);
				vehicle.setImportUrl("icon_gou.png");
			}
			//??????????????????
			String chepaiyanse=String.valueOf(a.get("????????????")).trim();
			if(StringUtils.isBlank(chepaiyanse) && !chepaiyanse.equals("null")){
				vehicle.setMsg("????????????????????????;");
				errorStr+="????????????????????????;";
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
					errorStr += cheliangpaiz+",????????????????????????,????????????;";
					vehicle.setMsg(cheliangpaiz+",????????????????????????,?????????;");
					bb++;
				}
			}

			VehicleVO vehicleVO = vehicleService.selectDeptCPYS(cheliangpaiz,chepaiyanse,vehicle.getDeptId().toString());
			if(vehicleVO !=null){
				vehicle.setImportUrl("icon_cha.png");
				errorStr+=cheliangpaiz+"???????????????;";
				vehicle.setMsg(cheliangpaiz+"???????????????;");
				bb++;
			}else{
				vehicle.setImportUrl("icon_gou.png");
			}
			//??????Excel????????????????????????????????????
			for (Vehicle item : vehicles) {
				if (item.getCheliangpaizhao().equals(cheliangpaiz) && item.getChepaiyanse().equals(chepaiyanse)) {
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz + "??????????????????";
					vehicle.setMsg(cheliangpaiz + "??????????????????");
					bb++;
				}
			}
			//??????????????????
			String shiyongxingzhi = String.valueOf(a.get("????????????")).trim();
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
					errorStr += cheliangpaiz+",????????????????????????,????????????;";
					vehicle.setMsg(cheliangpaiz+",????????????????????????,?????????;");
					bb++;
				}
			}else{
				vehicle.setImportUrl("icon_cha.png");
				errorStr += cheliangpaiz+",????????????????????????,????????????;";
				vehicle.setMsg(cheliangpaiz+",????????????????????????,?????????;");
				bb++;
			}

			//????????????
			vehicle.setCheliangzhuangtai("0");
			//??????????????????
			String xinghao = String.valueOf(a.get("????????????")).trim();
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
					errorStr += cheliangpaiz+",????????????????????????,????????????;";
					vehicle.setMsg(cheliangpaiz+",????????????????????????,?????????;");
					bb++;
				}
			}
			//???????????????????????????
			String czname = String.valueOf(a.get("????????????"));
			String czphone = String.valueOf(a.get("????????????"));
			if(!StringUtils.isBlank(czphone) && !czphone.equals("null")){
				if(CheckPhoneUtil.isChinaPhoneLegal(czphone) == false){
					vehicle.setMsg("???????????????????????????;");
					errorStr+=czphone+"???????????????????????????;";
					vehicle.setImportUrl("icon_cha.png");
					vehicle.setChezhudianhua(String.valueOf(a.get("????????????")).trim());
					bb++;
				}else{
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setChezhudianhua(String.valueOf(a.get("????????????")).trim());
					vehicle.setJiashiyuandianhua(String.valueOf(a.get("????????????")).trim());
				}
			}
			if(StringUtils.isBlank(czname) || czname.equals("null")){
				vehicle.setChezhu("");
			}else{
				//????????????ID?????????????????????????????????????????????????????????????????????
				JiaShiYuan jiaShiYuan = iJiaShiYuanService.getjiaShiYuanByOne(vehicle.getDeptId().toString(), czname, czphone,null,"?????????");
				if (jiaShiYuan != null) {
					vehicle.setImportUrl("icon_gou.png");
					vehicle.setJiashiyuanid(jiaShiYuan.getId());
					vehicle.setChezhu(String.valueOf(a.get("????????????")).trim());
					vehicle.setJiashiyuanxingming(String.valueOf(a.get("????????????")).trim());
				}else{
					vehicle.setImportUrl("icon_cha.png");
					errorStr += cheliangpaiz+",????????????????????????,????????????;";
					vehicle.setMsg(cheliangpaiz+",????????????????????????,?????????;");
					bb++;
				}
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
			rs.setMsg("??????????????????");
			rs.setData(vehicles);
			rs.setSuccess(true);
			return rs;
		}
	}

	/**
	 * ????????????--????????????
	 */
	@PostMapping("vehicleDeptImportOk")
	@ApiLog("??????????????????--????????????(?????????)")
	@ApiOperation(value = "??????????????????--????????????(?????????)", notes = "vehicles", position = 10)
	public R vehicleDeptImportOk(@RequestParam(value = "vehicles") String vehicles,BladeUser user) {
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("????????????????????????");
			rs.setData(null);
			rs.setSuccess(false);
			return rs;
		}
		JSONArray json = JSONUtil.parseArray(vehicles);
		List<Map<String, Object>> lists = (List) json;
		//??????????????????
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//????????????????????????
		int aa = 0;
		//????????????????????????
		int bb = 0;
		//????????????????????????????????????????????????false
		boolean isDataValidity = true;
		//????????????
		String errorStr = "";

		if (lists.size() > 2000) {
			errorStr += "??????????????????2000?????????????????????";
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
			vehicle.setCheliangzhuangtai("0");
			if (StringUtils.isNotBlank(String.valueOf(a.get("xinghao")).trim()) && !String.valueOf(a.get("xinghao")).equals("null")) {
				vehicle.setXinghao(String.valueOf(a.get("xinghao")).trim());
				vehicle.setCheliangleixing(vehicle.getXinghao());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuanxingming")).trim()) && !String.valueOf(a.get("jiashiyuanxingming")).equals("null")) {
				vehicle.setJiashiyuanxingming(String.valueOf(a.get("jiashiyuanxingming")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashiyuandianhua")).trim()) && !String.valueOf(a.get("jiashiyuandianhua")).equals("null")) {
				vehicle.setJiashiyuandianhua(String.valueOf(a.get("jiashiyuandianhua")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("chezhu")).trim()) && !String.valueOf(a.get("chezhu")).equals("null")) {
				vehicle.setChezhu(String.valueOf(a.get("chezhu")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("chezhudianhua")).trim()) && !String.valueOf(a.get("chezhudianhua")).equals("null")) {
				vehicle.setChezhudianhua(String.valueOf(a.get("chezhudianhua")).trim());
			}
			if (user != null) {
				vehicle.setCaozuoren(user.getUserName());
				vehicle.setCaozuorenid(user.getUserId());
			}
			vehicle.setIsdel(0);
			vehicle.setJiashiyuanid(String.valueOf(a.get("jiashiyuanid")).trim());

			isDataValidity = vehicleService.insertSelective(vehicle);
			if(isDataValidity){
				new Thread(new Runnable() {
					@Override
					public void run() {
						Organizations dept = orrganizationsClient.selectByDeptId(vehicle.getDeptId().toString());
						VehicleJishupingding jishupingding = new VehicleJishupingding();		//????????????
						jishupingding.setAvjVehicleIds(vehicle.getId());
						jishupingding.setAvjDelete("0");
						jishupingding.setAvjCreateByName(user.getUserName());
						jishupingding.setAvjCreateByIds(user.getUserId().toString());
						jishupingding.setAvjCreateTime(LocalDateTime.now());
						jishupingdingService.save(jishupingding);

						VehicleDengjizhengshu dengjizhengshu = new VehicleDengjizhengshu();		//????????????
						dengjizhengshu.setAvdVehicleIds(vehicle.getId());
						dengjizhengshu.setAvdDelete("0");
						dengjizhengshu.setAvdCreateByName(user.getUserName());
						dengjizhengshu.setAvdCreateByIds(user.getUserId().toString());
						dengjizhengshu.setAvdCreateTime(LocalDateTime.now());
						dengjizhengshuService.save(dengjizhengshu);

						VehicleJingyingxukezheng jingyingxukezheng = new VehicleJingyingxukezheng();		//???????????????
						jingyingxukezheng.setAvjVehicleIds(dept.getId());
						jingyingxukezheng.setAvjOperatorName(dept.getDeptName());
						jingyingxukezheng.setAvjDelete("0");
						jingyingxukezheng.setAvjCreateByName(user.getUserName());
						jingyingxukezheng.setAvjCreateByIds(user.getUserId().toString());
						jingyingxukezheng.setAvjCreateTime(LocalDateTime.now());
						jingyingxukezhengService.save(jingyingxukezheng);

						VehicleXingnengbaogao xingnengbaogao = new VehicleXingnengbaogao();		//????????????
						xingnengbaogao.setAvxAvIds(vehicle.getId());
						xingnengbaogao.setAvxDelete("0");
						xingnengbaogao.setAvxCreateByName(user.getUserName());
						xingnengbaogao.setAvxCreateByIds(user.getUserId().toString());
						xingnengbaogao.setAvxCreateTime(LocalDateTime.now());
						xingnengbaogaoService.save(xingnengbaogao);

						VehicleXingshizheng xingshizheng = new VehicleXingshizheng();		//?????????
						xingshizheng.setAvxAvIds(vehicle.getId());
						xingshizheng.setAvxPlateNo(vehicle.getCheliangpaizhao());
						xingshizheng.setAvxVehicleType(vehicle.getCheliangleixing());
						xingshizheng.setAvxOwner(dept.getDeptName());
						xingshizheng.setAvxAddress("??????");
						xingshizheng.setAvxUseCharter(vehicle.getShiyongxingzhi());
						xingshizheng.setAvxModel(vehicle.getXinghao());
						xingshizheng.setAvxCurbWeight(0);		//????????????
						xingshizheng.setAvxDelete("0");
						xingshizheng.setAvxCreateByName(user.getUserName());
						xingshizheng.setAvxCreateByIds(user.getUserId().toString());
						xingshizheng.setAvxCreateTime(LocalDateTime.now());
						xingshizhengService.save(xingshizheng);


						VehicleDaoluyunshuzheng daoluyunshuzheng = new VehicleDaoluyunshuzheng(); //???????????????
						daoluyunshuzheng.setAvdAvIds(vehicle.getId());
						daoluyunshuzheng.setAvdBusinessOwner(dept.getDeptName());
						daoluyunshuzheng.setAvdPlateNo(vehicle.getCheliangpaizhao());
						daoluyunshuzheng.setAvdVehicleType(vehicle.getCheliangleixing());
						daoluyunshuzheng.setAvdPlateColor(vehicle.getChepaiyanse());
						daoluyunshuzheng.setAvdDelete("0");
						daoluyunshuzheng.setAvdCreateByName(user.getUserName());
						daoluyunshuzheng.setAvdCreateByIds(user.getUserId().toString());
						daoluyunshuzheng.setAvdCreateTime(LocalDateTime.now());
						daoluyunshuzhengService.save(daoluyunshuzheng);

						//??????-??????-?????????????????????
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
			}
		}
		if (isDataValidity == true) {
			rs.setCode(200);
			rs.setMsg("??????????????????");
			rs.setData(vehicles);
			rs.setSuccess(true);
			return rs;
		} else {
			rs.setCode(500);
			rs.setMsg("??????????????????");
			rs.setData(vehicles);
			rs.setSuccess(false);
			return rs;
		}
	}




}
