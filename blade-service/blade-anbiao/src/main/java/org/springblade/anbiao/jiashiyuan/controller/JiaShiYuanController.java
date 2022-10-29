package org.springblade.anbiao.jiashiyuan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.springblade.anbiao.cheliangguanli.entity.CheliangJiashiyuan;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.anbiao.configure.entity.Configure;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.service.*;
import org.springblade.anbiao.jiashiyuan.service.impl.AnbiaoJiashiyuanTijianServiceImpl;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO;
import org.springblade.common.constant.CommonConstant;
import org.springblade.common.tool.IdCardUtil;
import org.springblade.common.tool.RegexUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.system.entity.Dept;
import org.springblade.system.feign.IDictClient;
import org.springblade.system.feign.ISysClient;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by you on 2019/4/22.
 */
@RestController
@RequestMapping("/anbiao/jiashiyuan")
@AllArgsConstructor
@Api(value = "驾驶员资料管理", tags = "驾驶员资料管理")
public class JiaShiYuanController {

	private IJiaShiYuanService iJiaShiYuanService;
	private IConfigureService mapService;
	private IFileUploadClient fileUploadClient;
	private ISysClient iSysClient;
	private IVehicleService vehicleService;
	private IDictClient iDictClient;
	private IAnbiaoJiashiyuanRuzhiService ruzhiService;
	private IAnbiaoJiashiyuanJiashizhengService jiashizhengService;
	private IAnbiaoJiashiyuanCongyezigezhengService congyezigezhengService;
	private IAnbiaoJiashiyuanTijianService tijianService;
	private AnbiaoJiashiyuanTijianServiceImpl tijianServiceImpl;


	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@ApiLog("新增-驾驶员资料管理")
	@ApiOperation(value = "新增-驾驶员资料管理", notes = "传入jiaShiYuan", position = 1)
	public R insert(@RequestBody JiaShiYuan jiaShiYuan,BladeUser user) {
		jiaShiYuan.setJiashiyuanleixing("驾驶员");
		if(user != null){
			jiaShiYuan.setCaozuoren(user.getUserName());
			jiaShiYuan.setCaozuorenid(user.getUserId());
		}else{
			jiaShiYuan.setCaozuoren(jiaShiYuan.getCaozuoren());
			jiaShiYuan.setCaozuorenid(jiaShiYuan.getCaozuorenid());
		}
		jiaShiYuan.setCreatetime(DateUtil.now());
		jiaShiYuan.setDenglumima(DigestUtil.encrypt(jiaShiYuan.getShoujihaoma().substring(jiaShiYuan.getShoujihaoma().length()-6)));
		if("男".equals(jiaShiYuan.getXingbie())){
			jiaShiYuan.setXingbie("1");
		}else if("女".equals(jiaShiYuan.getXingbie())){
			jiaShiYuan.setXingbie("2");
		}
		return R.status(iJiaShiYuanService.save(jiaShiYuan));
	}

	/**
	 * 编辑
	 */
	@PostMapping("/update")
	@ApiLog("编辑-驾驶员资料管理")
	@ApiOperation(value = "编辑-驾驶员资料管理", notes = "编辑jiaShiYuan", position = 2)
	public R update(@RequestBody JiaShiYuan jiaShiYuan, BladeUser user) {
		if("男".equals(jiaShiYuan.getXingbie())){
			jiaShiYuan.setXingbie("1");
		}else if("女".equals(jiaShiYuan.getXingbie())){
			jiaShiYuan.setXingbie("2");
		}
		if(user != null){
			jiaShiYuan.setCaozuoren(user.getUserName());
			jiaShiYuan.setCaozuorenid(user.getUserId());
		}else{
			jiaShiYuan.setCaozuoren(jiaShiYuan.getCaozuoren());
			jiaShiYuan.setCaozuorenid(jiaShiYuan.getCaozuorenid());
		}
		jiaShiYuan.setCaozuoshijian(DateUtil.now());
		return R.status(iJiaShiYuanService.updateById(jiaShiYuan));
	}

	/**
	 *  删除
	 */
	@PostMapping("/del")
	@ApiLog("删除-驾驶员资料管理")
	@ApiOperation(value = "删除-驾驶员资料管理", notes = "传入id", position = 3)
	public R del(String id, BladeUser user) {
		R r = new R();
		JiaShiYuan detal = iJiaShiYuanService.selectByIds(id);
		boolean i = false;

		///入职登记表///
		QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
		ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrIds, detal.getId());
		ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
		AnbiaoJiashiyuanRuzhi ruzhideail = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
		if(ruzhideail != null){
			AnbiaoJiashiyuanRuzhi ruzhi = new AnbiaoJiashiyuanRuzhi();
			ruzhi.setAjrUpdateByIds(user.getUserId().toString());
			ruzhi.setAjrUpdateByName(user.getUserName());
			ruzhi.setAjrUpdateTime(DateUtil.now());
			ruzhi.setAjrDelete("1");
			ruzhi.setAjrIds(ruzhideail.getAjrIds());
			i = ruzhiService.updateById(ruzhi);
		}

		///驾驶证///
		QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
		jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, detal.getId());
		jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
		AnbiaoJiashiyuanJiashizheng jiashizhengdeail = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
		if(jiashizhengdeail != null){
			AnbiaoJiashiyuanJiashizheng jiashizheng = new AnbiaoJiashiyuanJiashizheng();
			jiashizheng.setAjjUpdateByName(user.getUserName());
			jiashizheng.setAjjUpdateByIds(user.getUserId().toString());
			jiashizheng.setAjjUpdateTime(DateUtil.now());
			jiashizheng.setAjjDelete("1");
			jiashizheng.setAjjIds(jiashizheng.getAjjIds());
			i = jiashizhengService.updateById(jiashizheng);
		}

		///从业资格证///
		QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
		congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, detal.getId());
		congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
		AnbiaoJiashiyuanCongyezigezheng deail = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
		if(deail != null){
			AnbiaoJiashiyuanCongyezigezheng congyezigezheng = new AnbiaoJiashiyuanCongyezigezheng();
			congyezigezheng.setAjcUpdateByName(user.getUserName());
			congyezigezheng.setAjcUpdateByIds(user.getUserId().toString());
			congyezigezheng.setAjcUpdateTime(DateUtil.now());
			i = congyezigezhengService.updateById(congyezigezheng);
		}


		///驾驶员信息主表///
		i = iJiaShiYuanService.updateDel(id);
		if(i){
			r.setCode(200);
			r.setMsg("删除成功");
		}else{
			r.setCode(500);
			r.setMsg("删除失败");
		}
		return r;
	}

	/**
	 *  查询详情
	 */
	@GetMapping("/detail")
	@ApiLog("详情-驾驶员资料管理")
	@ApiOperation(value = "详情-驾驶员资料管理", notes = "传入id、type", position = 4)
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "驾驶员ID", required = true),
		@ApiImplicitParam(name = "type", value = "分类（1：入职登记表，2：身份证，3：驾驶证，4:从业资格证,5:体检表,6:岗前培训三级教育卡," +
			"7:三年无重大责任事故正面,8:驾驶员安全责任书,9:驾驶员职业危害告知书,10:劳动合同,11:其他,）", required = true),
	})
	public R detail(String id,int type) {
		R r = new R();
		JiaShiYuan detal = iJiaShiYuanService.selectByIds(id);
		if(detal != null){
			///入职登记表///
			if(type == 1){
				QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
				ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrIds, detal.getId());
				ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
				AnbiaoJiashiyuanRuzhi ruzhiInfo = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
				if(ruzhiInfo != null){
					//本人照片(人员头像)
					if(StrUtil.isNotEmpty(ruzhiInfo.getAjrHeadPortrait()) && ruzhiInfo.getAjrHeadPortrait().contains("http") == false){
						ruzhiInfo.setAjrHeadPortrait(fileUploadClient.getUrl(ruzhiInfo.getAjrHeadPortrait()));
					}
					r.setData(ruzhiInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				}else{
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///身份证///
			if(type == 2){
				JiaShiYuan shenfenzhengInfo = iJiaShiYuanService.selectByIds(id);
				if(shenfenzhengInfo != null){
					//照片(人员头像)
//					if(StrUtil.isNotEmpty(detal.getZhaopian()) && detal.getZhaopian().contains("http") == false){
//						detal.setZhaopian(fileUploadClient.getUrl(detal.getZhaopian()));
//					}
					//身份证附件
					if(StrUtil.isNotEmpty(detal.getShenfenzhengfujian()) && detal.getShenfenzhengfujian().contains("http") == false){
						detal.setShenfenzhengfujian(fileUploadClient.getUrl(detal.getShenfenzhengfujian()));
					}
					//身份证附件反面
					if(StrUtil.isNotEmpty(detal.getShenfenzhengfanmianfujian()) && detal.getShenfenzhengfanmianfujian().contains("http") == false){
						detal.setShenfenzhengfanmianfujian(fileUploadClient.getUrl(detal.getShenfenzhengfanmianfujian()));
					}

					r.setData(shenfenzhengInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				}else{
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///驾驶证///
			if(type == 3){
				QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, detal.getId());
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
				AnbiaoJiashiyuanJiashizheng jiashizhengInfo = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
				if(jiashizhengInfo != null){
					//驾驶证正面照片
					if(StrUtil.isNotEmpty(jiashizhengInfo.getAjjFrontPhotoAddress()) && jiashizhengInfo.getAjjFrontPhotoAddress().contains("http") == false){
						jiashizhengInfo.setAjjFrontPhotoAddress(fileUploadClient.getUrl(jiashizhengInfo.getAjjFrontPhotoAddress()));
					}
					//驾驶证反面照片
					if(StrUtil.isNotEmpty(jiashizhengInfo.getAjjAttachedPhotos()) && jiashizhengInfo.getAjjAttachedPhotos().contains("http") == false){
						jiashizhengInfo.setAjjAttachedPhotos(fileUploadClient.getUrl(jiashizhengInfo.getAjjAttachedPhotos()));
					}
					r.setData(jiashizhengInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				}else{
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///从业资格证///
			if(type == 4){
				QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
				congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, detal.getId());
				congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
				AnbiaoJiashiyuanCongyezigezheng congyezigezhengInfo = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
				if(congyezigezhengInfo != null){
					//从业资格证照片
					if(StrUtil.isNotEmpty(congyezigezhengInfo.getAjcLicence()) && congyezigezhengInfo.getAjcLicence().contains("http") == false){
						congyezigezhengInfo.setAjcLicence(fileUploadClient.getUrl(congyezigezhengInfo.getAjcLicence()));
					}
					r.setData(congyezigezhengInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				}else{
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}





		}else{
			r.setMsg("暂无数据");
			r.setCode(500);
			return r;
		}
		return r;
	}

	/**
	 * 分页
	 */
	@PostMapping("/list")
	@ApiLog("分页-驾驶员资料管理")
	@ApiOperation(value = "分页-驾驶员资料管理", notes = "传入JiaShiYuanPage", position = 5)
	public R<JiaShiYuanPage<JiaShiYuanVO>> list(@RequestBody JiaShiYuanPage jiaShiYuanPage) {
		jiaShiYuanPage.setJiashiyuanleixing("驾驶员");
		JiaShiYuanPage<JiaShiYuanVO> pages = iJiaShiYuanService.selectPageList(jiaShiYuanPage);
		return R.data(pages);
	}









	/********************************** 配置表 ***********************/

	/**
	 * 根据单位id获取配置模块数据
	 */
	@GetMapping("/listMap")
	@ApiLog("获取配置-驾驶员资料管理")
	@ApiOperation(value = "获取配置-驾驶员资料管理", notes = "传入deptId", position = 6)
	public R<JSONArray> listMap(Integer deptId) {
		List<Configure> list1=mapService.selectMapList("anbiao_jiashiyuan_map",deptId);
		String str="";
		for (int i = 0; i <list1.size() ; i++) {
			//转换成json数据并put id
			JSONObject jsonObject = JSONUtil.parseObj(list1.get(i).getBiaodancanshu());
			jsonObject.put("id",list1.get(i).getId());
			if(!str.equals("")){
				str=str+","+jsonObject.toString();
			}else{
				str=jsonObject.toString();
			}
		}
		str="["+str+"]";
		JSONArray json= JSONUtil.parseArray(str);
		return R.data(json);
	}

	/**
	 * 配置表新增
	 */
	@PostMapping("/insertMap")
	@ApiLog("配置表新增-驾驶员资料管理")
	@ApiOperation(value = "配置表新增-驾驶员资料管理", notes = "传入biaodancanshu与deptId", position = 7)
	public R insertMap(String biaodancanshu,String deptId) {
		Configure configure=new Configure();
		JSONObject jsonObject = JSONUtil.parseObj(biaodancanshu);
		configure.setLabel(jsonObject.getStr("label"));
		configure.setShujubiaoziduan(jsonObject.getStr("prop"));
		configure.setDeptId(Integer.parseInt(deptId));
		configure.setTableName("anbiao_jiashiyuan_map");
		configure.setBiaodancanshu(biaodancanshu);
		return R.status(mapService.insertMap(configure));
	}
	/**
	 * 配置表编辑
	 */
	@PostMapping("/updateMap")
	@ApiLog("配置表编辑-驾驶员资料管理")
	@ApiOperation(value = "配置表编辑-驾驶员资料管理", notes = "传入biaodancanshu与id", position = 9)
	public R updateMap(String biaodancanshu,String id) {
		Configure configure=new Configure();
		JSONObject jsonObject = JSONUtil.parseObj(biaodancanshu);
		configure.setId(id);
		configure.setLabel(jsonObject.getStr("label"));
		configure.setShujubiaoziduan(jsonObject.getStr("prop"));
		configure.setTableName("anbiao_jiashiyuan_map");
		configure.setBiaodancanshu(biaodancanshu);
		return R.status(mapService.updateMap(configure));
	}

	/**
	 * 配置表删除
	 */
	@PostMapping("/delMap")
	@ApiLog("配置表删除-驾驶员资料管理")
	@ApiOperation(value = "配置表删除-驾驶员资料管理", notes = "传入id", position = 8)
	public R delMap(@ApiParam(value = "主键id", required = true) @RequestParam String id) {
		return R.status(mapService.delMap("anbiao_jiashiyuan_map",id));
	}

	/**
	 * 驾驶员资料导入
	 * @author: Elvis
	 * @date: 2020/6/23 09:23
	 * @param
	 * @return: org.springblade.core.tool.api.R
	 */
	@PostMapping("driverImportOne")
	@ApiLog("驾驶员资料-导入(本企业)")
	@ApiOperation(value = "驾驶员资料-导入(本企业)", notes = "file", position = 10)
	public  R driverImportOne(@RequestParam(value = "file") MultipartFile file, BladeUser user, String DeptId, String DeptName){

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
		int aa=0;
		int bb=0;
		List<JiaShiYuan> drivers=new ArrayList<>();
		boolean b=true;
		for(Map<String,Object> a:readAll){
			aa++;
			JiaShiYuan driver=new JiaShiYuan();
			String id= IdUtil.simpleUUID();
			driver.setId(id);
			driver.setDeptId(Integer.valueOf(DeptId));

			String tmp=String.valueOf(a.get("身份证号"));
			driver.setShenfenzhenghao(tmp);
			JiaShiYuanVO driverVO = iJiaShiYuanService.selectByCardNo(driver.getDeptId().toString(),tmp,null,"驾驶员");
			if(driverVO!=null){
				driver.setMsg("该驾驶员已存在");
				driver.setMsg2(false);
				bb++;
			}else{
				driver.setMsg2(true);
			}
			tmp = String.valueOf(a.get("驾驶员姓名"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setJiashiyuanxingming(tmp);
			}
			tmp = String.valueOf(a.get("性别"));
			if("男".equals(tmp)){
				driver.setXingbie("1");
			}else if("女".equals(tmp)){
				driver.setXingbie("2");
			}
			tmp = String.valueOf(a.get("出生日期"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setChushengshijian(tmp);
			}
			tmp = String.valueOf(a.get("年龄"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setNianling(tmp);
			}
			tmp = String.valueOf(a.get("手机号码"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setShoujihaoma(tmp);
			}
			tmp = String.valueOf(a.get("从业类别"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setCongyerenyuanleixing(tmp);
			}
			tmp = String.valueOf(a.get("身份证有效期"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setShenfenzhengyouxiaoqi(tmp);
			}
			tmp = String.valueOf(a.get("文化程度"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setWenhuachengdu(tmp);
			}
			tmp = String.valueOf(a.get("聘用日期"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setPingyongriqi(tmp);
			}
			tmp = String.valueOf(a.get("机动驾驶员"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setJidongjiashiyuan(tmp);
			}
			tmp = String.valueOf(a.get("驾驶员类型"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setJiashiyuanleixing(tmp);
			}
			tmp = String.valueOf(a.get("家庭住址"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setJiatingzhuzhi(tmp);
			}
			tmp = String.valueOf(a.get("准驾车型"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setZhunjiachexing(tmp);
			}
			tmp = String.valueOf(a.get("驾驶证号"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setJiashizhenghao(tmp);
			}
			tmp = String.valueOf(a.get("驾驶证初领日期"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setJiashizhengchulingriqi(tmp);
			}
			tmp = String.valueOf(a.get("驾驶证有效期"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setJiashizhengyouxiaoqi(tmp);
			}
			tmp = String.valueOf(a.get("驾龄"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setJialing(tmp);
			}
			tmp = String.valueOf(a.get("从业资格证号"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setCongyezigezheng(tmp);
			}
			tmp = String.valueOf(a.get("部门"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setBumen(tmp);
			}
			tmp = String.valueOf(a.get("离辞职时间"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setLizhishijian(tmp);
			}
			tmp = String.valueOf(a.get("违法记分"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setWeifajifen(tmp);
			}
			tmp = String.valueOf(a.get("体检日期"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setTijianriqi(tmp);
			}
			tmp = String.valueOf(a.get("体检有效期"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setTijianyouxiaoqi(tmp);
			}
			tmp = String.valueOf(a.get("护照号码"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setHuzhaohaoma(tmp);
			}
			tmp = String.valueOf(a.get("护照类型"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setHuzhaoleibie(tmp);
			}
			tmp = String.valueOf(a.get("国家码"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setGuojiama(tmp);
			}
			tmp = String.valueOf(a.get("护照有效期"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setHuzhaoyouxiaoqi(tmp);
			}
			tmp = String.valueOf(a.get("准驾证号"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setZhunjiazhenghao(tmp);
			}
			tmp = String.valueOf(a.get("准驾类型"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setZhunjialeixing(tmp);
			}
			tmp = String.valueOf(a.get("准运线"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setZhunyunxian(tmp);
			}
			tmp = String.valueOf(a.get("准驾证有效期"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setZhunjiazhengyouxiaoqi(tmp);
			}
			tmp = String.valueOf(a.get("缴纳标准"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setJiaonabiaozhun(tmp);
			}
			tmp = String.valueOf(a.get("缴纳金额"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setJiaonajine(tmp);
			}
			tmp = String.valueOf(a.get("风险金缴纳"));
			if(!StringUtil.isNotEmpty(tmp)){
				driver.setShifoujiaona(tmp);
			}
			driver.setCreatetime(DateUtil.now());
			if(user!=null){
				driver.setCaozuoren(user.getUserName());
				driver.setCaozuorenid(user.getUserId());
			}
			driver.setCaozuoshijian(DateUtil.now());

			drivers.add(driver);

		}
		if(bb>0){
			return  R.data(400,drivers,"导入失败");
		}else{
			b=iJiaShiYuanService.saveBatch(drivers);
			if(b){
				return  R.data(drivers,"成功导入:"+aa+"条");
			}else{
				return  R.fail("导入失败");
			}
		}
	}

//	/**
//	 * 驾驶员信息-导入
//	 * @author: elvis
//	 * @date: 2020/06/23 10:23
//	 * @param
//	 * @return: org.springblade.core.tool.api.R
//	 */
//	@PostMapping("driverImport")
//	@ApiLog("驾驶员信息-导入")
//	@ApiOperation(value = "驾驶员信息-导入", notes = "file", position = 10)
//	public  R driverImport(@RequestParam(value = "file") MultipartFile file, BladeUser user){
//
//		ExcelReader reader = null;
//		try {
//			reader = ExcelUtil.getReader(file.getInputStream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		List<Map<String,Object>> readAll = reader.readAll();
//		int index=readAll.size();
//		if(index>5000){
//			return  R.fail("导入条目不能超过5000条");
//		}
//		int aa=0;
//		int bb=0;
//		List<JiaShiYuan> drivers=new ArrayList<JiaShiYuan>();
//		boolean b=true;
//		for(Map<String,Object> a:readAll){
//			aa++;
//			JiaShiYuan driver=new JiaShiYuan();
//			Dept dept;
//			String id=IdUtil.simpleUUID();
//			driver.setId(id);
//			String deptname =  String.valueOf(a.get("机构名称"));
//			dept = iSysClient.getDeptByName(deptname);
//			if (dept != null){
//				driver.setDeptId(Integer.valueOf(dept.getId()));
//
//			}else{
//				driver.setMsg("机构不存在");
//				driver.setMsg2(false);
//				bb++;
//			}
////			driver.setDeptName(dept.getDeptName());
//
//			String tmp=String.valueOf(a.get("身份证号"));
//			driver.setShenfenzhenghao(tmp);
//			JiaShiYuanVO driverVO = iJiaShiYuanService.selectByCardNo(tmp);
//			if(driverVO!=null){
//				driver.setMsg("该驾驶员已存在");
//				driver.setMsg2(false);
//				bb++;
//			}else{
//				driver.setMsg2(true);
//			}
//
//			driver.setJiashiyuanxingming(String.valueOf(a.get("驾驶员姓名")));
//
//			tmp = String.valueOf(a.get("性别"));
//			if("男".equals(tmp)){
//				driver.setXingbie("1");
//			}else if("女".equals(tmp)){
//				driver.setXingbie("2");
//			}
//			driver.setChushengshijian((a.get("出生日期") == null) ? null : a.get("出生日期").toString());
//			driver.setNianling(String.valueOf(a.get("年龄")));
//			driver.setShoujihaoma(String.valueOf(a.get("手机号码")));
//			driver.setCongyerenyuanleixing(String.valueOf(a.get("从业类别")));
//			driver.setShenfenzhengyouxiaoqi((a.get("身份证有效期") == null) ? null : a.get("身份证有效期").toString());
//			driver.setWenhuachengdu(String.valueOf(a.get("文化程度")));
//			driver.setPingyongriqi((a.get("聘用日期") == null) ? null : a.get("聘用日期").toString());
//
//			driver.setJidongjiashiyuan(String.valueOf(a.get("机动驾驶员")));
//			driver.setJiashiyuanleixing("驾驶员");
//			driver.setJiatingzhuzhi(String.valueOf(a.get("家庭住址")));
//			driver.setZhunjiachexing(String.valueOf(a.get("准驾车型")));
//			driver.setJiashizhenghao(String.valueOf(a.get("驾驶证号")));
//			driver.setJiashizhengchulingriqi((a.get("驾驶证初领日期") == null) ? null : a.get("驾驶证初领日期").toString());
//			driver.setJiashizhengyouxiaoqi((a.get("驾驶证有效期") == null) ? null : a.get("驾驶证有效期").toString());
//			driver.setJialing(String.valueOf(a.get("驾龄")));
//			driver.setCongyezigezheng(String.valueOf(a.get("从业资格证号")));
//			driver.setBumen(String.valueOf(a.get("部门")));
//			driver.setLizhishijian((a.get("离辞职时间") == null) ? null : a.get("离辞职时间").toString());
//			driver.setWeifajifen(String.valueOf(a.get("违法记分")));
//			driver.setTijianriqi((a.get("体检日期") == null) ? null : a.get("体检日期").toString());
//			driver.setTijianyouxiaoqi((a.get("体检有效期") == null) ? null : a.get("体检有效期").toString());
//			driver.setHuzhaohaoma(String.valueOf(a.get("护照号码")));
//			driver.setHuzhaoleibie(String.valueOf(a.get("护照类型")));
//			driver.setGuojiama(String.valueOf(a.get("国家码")));
//			driver.setHuzhaoyouxiaoqi((a.get("护照有效期") == null) ? null : a.get("护照有效期").toString());
//			driver.setZhunjiazhenghao(String.valueOf(a.get("准驾证号")));
//			driver.setZhunjialeixing(String.valueOf(a.get("准驾类型")));
//			driver.setZhunyunxian(String.valueOf(a.get("准运线")));
//			driver.setZhunjiazhengyouxiaoqi((a.get("准驾证有效期") == null) ? null : a.get("准驾证有效期").toString());
//			driver.setJiaonabiaozhun(String.valueOf(a.get("缴纳标准")));
//			driver.setJiaonajine(String.valueOf(a.get("缴纳金额")));
//			driver.setShifoujiaona(String.valueOf(a.get("风险金缴纳")));
//			driver.setCreatetime(DateUtil.now());
//			if(user!=null){
//				driver.setCaozuoren(user.getUserName());
//				driver.setCaozuorenid(user.getUserId());
//			}
//
//			driver.setCaozuoshijian(DateUtil.now());
//
//			drivers.add(driver);
//		}
//		if(bb>0){
//			return  R.data(400,drivers,"导入失败");
//		}else{
//			b=iJiaShiYuanService.saveBatch(drivers);
//			if(b){
//				return  R.data(drivers,"成功导入:"+aa+"条");
//			}else{
//				return  R.fail("导入失败");
//			}
//		}
//	}

	@GetMapping(value = "/getJSYByVehicle")
	@ApiLog("企业-查询驾驶员绑定车辆")
	@ApiOperation(value = "企业-查询驾驶员绑定车辆", notes = "传入jiashiyuanid",position = 13)
	public R<Vehicle> getJSYByVehicle(String jiashiyuanid) {
		R rs = new R();

		List<Vehicle> vehicleList = iJiaShiYuanService.selectByCar(jiashiyuanid);
		if (vehicleList != null) {
			rs.setCode(200);
			rs.setData(vehicleList);
			rs.setMsg("获取成功");
		} else {
			rs.setCode(500);
			rs.setMsg("获取失败");
		}
		return rs;
	}

	@PostMapping("/resetPassword")
	@ApiLog("初始化密码")
	@ApiOperation(value = "初始化密码", notes = "传入ids", position = 15)
	public R resetPassword(@ApiParam(value = "ids", required = true) @RequestParam String ids,BladeUser user) {
		JiaShiYuan jiaShiYuan = new JiaShiYuan();
		jiaShiYuan.setDenglumima(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
		jiaShiYuan.setCaozuoshijian(LocalDateTime.now().toString());
		jiaShiYuan.setCaozuoren(user.getUserName());
		jiaShiYuan.setCaozuorenid(user.getUserId());
		boolean temp = iJiaShiYuanService.updatePassWord(jiaShiYuan.getDenglumima(),ids);
		return R.status(temp);
	}

	@GetMapping(value = "/getJVPageList")
	@ApiLog("企业-驾驶员绑定车辆列表")
	@ApiOperation(value = "企业-驾驶员绑定车辆列表", notes = "传入jiaShiYuanPage",position = 16)
	public R<JiaShiYuanPage<JiaShiYuanVO>> getJVPageList(JiaShiYuanPage jiaShiYuanPage) throws IOException {
		R rs = new R();
		JiaShiYuanPage<JiaShiYuanVO> vehlist = iJiaShiYuanService.selectJVList(jiaShiYuanPage);
		if (vehlist != null) {
			rs.setCode(200);
			rs.setData(vehlist);
			rs.setMsg("获取成功");
		} else {
			rs.setCode(500);
			rs.setMsg("获取失败");
		}
		return rs;
	}

	@GetMapping("/getByIdJiaShiYuanList")
	@ApiLog("根据企业ID获取相应驾驶员信息")
	@ApiOperation(value = "根据企业ID获取相应驾驶员信息", notes = "传入deptId", position = 17)
	public R<List<JiaShiYuan>> getByIdJiaShiYuanList(String deptId) {
		List<JiaShiYuan> detail = iJiaShiYuanService.jiaShiYuanList(deptId);
		return R.data(detail);
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
	 * 驾驶员信息--验证
	 * @update: 黄亚平 添加验证
	 * @param
	 */
	@PostMapping("driverImport")
	@ApiLog("驾驶员信息-验证")
	@ApiOperation(value = "驾驶员信息-验证", notes = "file", position = 10)
	public R driverImport(@RequestParam(value = "file") MultipartFile file,BladeUser user){
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

		List<JiaShiYuan> drivers= new ArrayList<>();
		for(Map<String,Object> a:readAll){
			aa++;
			JiaShiYuan driver=new JiaShiYuan();
			SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
			Dept dept;
			JiaShiYuanVO driverVO = null;
			String deptname =  String.valueOf(a.get("所属单位")).trim();
			if(StringUtils.isBlank(deptname)){
				driver.setMsg("所属单位不能为空;");
				driver.setImportUrl("icon_cha.png");
				errorStr+="所属单位不能为空;";
				bb++;
			}
			dept = iSysClient.getDeptByName(deptname.trim());
			if (dept != null){
				driver.setDeptId(Integer.valueOf(dept.getId()));
				driver.setDeptName(deptname.trim());
				driver.setImportUrl("icon_gou.png");
			}else{
				driver.setMsg(deptname+"所属单位不存在;");
				driver.setImportUrl("icon_cha.png");
				errorStr+=deptname+"所属单位不存在;";
				bb++;
			}
			String driverName = String.valueOf(a.get("驾驶员姓名")).trim();
			if(StringUtils.isBlank(driverName)){
				driver.setMsg("驾驶员姓名不能为空;");
				driver.setImportUrl("icon_cha.png");
				errorStr+="驾驶员姓名不能为空;";
				bb++;
			}else{
				driver.setJiashiyuanxingming(driverName);
				driver.setImportUrl("icon_gou.png");
			}
			String tmp = String.valueOf(a.get("身份证号")).trim();
			if(StringUtils.isNotBlank(tmp)){
				//校验身份证是否合法
				if(IdCardUtil.isValidCard(tmp) == true){
					driver.setShenfenzhenghao(tmp);
					//根据企业ID、身份证号查询该身份证是否存在
					driverVO = iJiaShiYuanService.selectByCardNo(driver.getDeptId().toString(),tmp,null,"驾驶员");
					System.out.println(driverVO);
					if(driverVO!=null){
						driver.setMsg(driverVO.getShenfenzhenghao()+"该驾驶员身份证号已存在;");
						driver.setImportUrl("icon_cha.png");
						errorStr+=driverVO.getShenfenzhenghao()+"该驾驶员身份证号已存在;";
						bb++;
					}else{
						driver.setShenfenzhenghao(tmp);
						driver.setImportUrl("icon_gou.png");
					}
				}else{
					driver.setMsg(tmp+"该驾驶员身份证号不合法;");
					driver.setImportUrl("icon_cha.png");
					errorStr+=tmp+"该驾驶员身份证号不合法;";
					bb++;
				}
			}
			//验证手机号码是否合法
			String shoujihaoma = String.valueOf(a.get("手机号码")).trim();
			if(StringUtils.isBlank(shoujihaoma)){
				driver.setMsg("手机号码不能为空;");
				driver.setImportUrl("icon_cha.png");
				errorStr+="手机号码不能为空;";
				bb++;
			}else{
				if(RegexUtils.checkMobile(shoujihaoma)){
					driver.setShoujihaoma(shoujihaoma);
					driver.setImportUrl("icon_gou.png");
				}else{
					driver.setMsg(shoujihaoma+"该手机号码不合法;");
					errorStr+=shoujihaoma+"该手机号码不合法;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证驾驶证是否合法
			String jiashizheng = String.valueOf(a.get("驾驶证号")).trim();
			if(StringUtils.isNotBlank(jiashizheng) && jiashizheng != null){
				if(IdCardUtil.isValidCard(jiashizheng) == true){
					driver.setJiashizhenghao(jiashizheng);
					driverVO = iJiaShiYuanService.selectByCardNo(driver.getDeptId().toString(),null,jiashizheng,"驾驶员");
					if(driverVO!=null){
						driver.setMsg(driverVO.getJiashizhenghao()+"该驾驶证号已存在;");
						driver.setImportUrl("icon_cha.png");
						errorStr+=driverVO.getJiashizhenghao()+"该驾驶证号已存在;";
						bb++;
					}else{
						driver.setShenfenzhenghao(tmp);
						driver.setImportUrl("icon_gou.png");
					}
				}else{
					driver.setMsg(jiashizheng+"该驾驶证号不合法;");
					errorStr+=jiashizheng+"该驾驶证号不合法;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证从业资格证号
			String congyezigezheng = String.valueOf(a.get("从业资格证号")).trim();
			if(StringUtils.isNotBlank(congyezigezheng)){
				driver.setCongyezigezheng(congyezigezheng);
				driver.setImportUrl("icon_gou.png");
			}

			//验证从业证有效期
			String congyezigezhengyouxiaoqi = String.valueOf(a.get("从业证有效期")).trim();
			if(StringUtils.isNotBlank(congyezigezheng)){
				driver.setCongyezhengyouxiaoqi(congyezigezhengyouxiaoqi);
				driver.setImportUrl("icon_gou.png");
			}

			String cheliangpaiz = String.valueOf(a.get("车辆牌照")).trim();
			if(StringUtils.isNotBlank(cheliangpaiz) && !cheliangpaiz.equals("null")) {
				if (isCarnumberNO(cheliangpaiz) == false) {
					driver.setMsg("辆牌照格式不正确;");
					errorStr += cheliangpaiz + "辆牌照格式不正确;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				} else {
					driver.setImportUrl("icon_gou.png");
				}
			}

			String chepaiyanse = String.valueOf(a.get("车牌颜色")).trim();
			if (StringUtils.isNotBlank(chepaiyanse) && !chepaiyanse.equals("null")) {
				VehicleVO vehicleVO = vehicleService.selectVehicleColor(chepaiyanse);
				if (vehicleVO == null || vehicleVO.getChepaiyanse() == null) {
					driver.setMsg("车牌颜色输入错误;");
					errorStr += "车牌颜色输入错误;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				} else {
					driver.setImportUrl("icon_gou.png");
				}
			}

			if (StringUtils.isNotBlank(chepaiyanse) && StringUtils.isNotBlank(cheliangpaiz) && !cheliangpaiz.equals("null") && !cheliangpaiz.equals("null")) {
				Vehicle vehicleVO = vehicleService.vehileOne(cheliangpaiz,chepaiyanse,Integer.valueOf(dept.getId()));
				if(vehicleVO!=null){
					driver.setImportUrl("icon_gou.png");
				}else{
					driver.setImportUrl("icon_cha.png");
					errorStr+=cheliangpaiz+"该车不存在;";
					driver.setMsg(cheliangpaiz+"该车不存在;");
					bb++;
				}
				driver.setCheliangpaizhao(cheliangpaiz);
				driver.setChepaiyanse(chepaiyanse);
			}else{
				driver.setCheliangpaizhao("");
				driver.setChepaiyanse("");
			}
			drivers.add(driver);
		}
		if(bb>0){
			rs.setMsg(errorStr);
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setData(drivers);
			return rs;
		}else{
			rs.setCode(200);
			rs.setMsg("数据验证成功");
			rs.setData(drivers);
			rs.setSuccess(true);
			return rs;
		}
	}

	/**
	 * 驾驶员信息--确认导入
	 * @author: elvis
	 * @date: 2020/10/19 10:23
	 * @param
	 * @return: org.springblade.core.tool.api.R
	 */
	@PostMapping("driverImportOk")
	@ApiLog("驾驶员信息-确认导入")
	@ApiOperation(value = "驾驶员信息-确认导入", notes = "drivers", position = 10)
	public R driverImportOk(@RequestParam(value = "drivers") String drivers,BladeUser user,@RequestParam Integer userId,@RequestParam String userName){
		JSONArray json = JSONUtil.parseArray(drivers);
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

		for(Map<String,Object> a:lists){
			aa++;
			JiaShiYuan driver=new JiaShiYuan();
			SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
			Dept dept;
			String id=IdUtil.simpleUUID();
			driver.setId(id);
			driver.setDeptId(Integer.valueOf(String.valueOf(a.get("deptId")).trim()));
			driver.setJiashiyuanxingming(String.valueOf(a.get("jiashiyuanxingming")).trim());
			String tmp = String.valueOf(a.get("shenfenzhenghao")).trim();
			driver.setShenfenzhenghao(tmp);
			//通过身份证获取性别
			Integer sex = IdCardUtil.getGender(tmp);
//			if(1 == sex){
//				driver.setXingbie("男");
//			}else if(2 == sex){
//				driver.setXingbie("女");
//			}
			if(1 == sex){
				driver.setXingbie("1");
			}else if(0 == sex){
				driver.setXingbie("2");
			}
			//通过身份证获取年龄
			Integer age = IdCardUtil.getAgeByCard(tmp);
			driver.setNianling(age.toString());
			//通过身份证获取生日日期
			Date chushengshijian = IdCardUtil.getBirthDate(tmp);
			driver.setChushengshijian(dateFormat2.format(chushengshijian));
			//验证手机号码是否合法
			String shoujihaoma = String.valueOf(a.get("shoujihaoma")).trim();
			driver.setShoujihaoma(shoujihaoma);
			driver.setJiashiyuanleixing("驾驶员");
			driver.setCongyerenyuanleixing("驾驶员");
			driver.setJiashizhenghao(String.valueOf(a.get("jiashizhenghao")).trim());
			driver.setCongyezigezheng(String.valueOf(a.get("congyezigezheng")).trim());
			if(String.valueOf(a.get("congyezhengyouxiaoqi")) == "null"){
				driver.setCongyezhengyouxiaoqi("");
			}else{
				driver.setCongyezhengyouxiaoqi(String.valueOf(a.get("congyezhengyouxiaoqi")).trim());
			}
			driver.setDenglumima(DigestUtil.encrypt("123456"));
			driver.setIsdelete(0);
			driver.setCreatetime(DateUtil.now());
			driver.setCaozuoshijian(DateUtil.now());
			if(user != null){
				driver.setCaozuoren(user.getUserName());
				driver.setCaozuorenid(user.getUserId());
			}else{
				driver.setCaozuoren(userName);
				driver.setCaozuorenid(userId);
			}
			if(StringUtils.isNotBlank(String.valueOf(a.get("cheliangpaizhao"))) && StringUtils.isNotBlank(String.valueOf(a.get("chepaiyanse")))){
				driver.setCheliangpaizhao(String.valueOf(a.get("cheliangpaizhao")));
				driver.setChepaiyanse(String.valueOf(a.get("chepaiyanse")));
				isDataValidity = iJiaShiYuanService.insertSelective(driver);
				//向车辆驾驶员绑定关系表中添加记录
				Vehicle vehicleVO = vehicleService.vehileOne(driver.getCheliangpaizhao(),driver.getChepaiyanse(),Integer.valueOf(driver.getDeptId()));
				CheliangJiashiyuan cheliangJiashiyuan = vehicleService.getVehidAndJiashiyuanId(vehicleVO.getId(),driver.getId());
				if(cheliangJiashiyuan == null){
					CheliangJiashiyuan cheliangJiashiyuan1 = new CheliangJiashiyuan();
					cheliangJiashiyuan1.setDeptId(vehicleVO.getDeptId());
					cheliangJiashiyuan1.setCreatetime(DateUtil.now());
					cheliangJiashiyuan1.setJiashiyuanid(driver.getId());
					cheliangJiashiyuan1.setVehid(vehicleVO.getId());
					cheliangJiashiyuan1.setType("1");
					boolean vaj = vehicleService.insertVehidAndJiashiyuan(cheliangJiashiyuan1);
				}
				//更新车辆表相关数据信息
				Vehicle vehicle = new Vehicle();
				vehicle.setJiashiyuanxingming(driver.getJiashiyuanxingming());
				vehicle.setJiashiyuandianhua(driver.getShoujihaoma());
				vehicle.setDeptId(driver.getDeptId());
				vehicle.setJiashiyuanid(driver.getId());
				vehicle.setChepaiyanse(driver.getChepaiyanse());
				vehicle.setCheliangpaizhao(driver.getCheliangpaizhao());
				boolean v = vehicleService.updateSelective(vehicle);
			}else{
				isDataValidity = iJiaShiYuanService.insertSelective(driver);
			}
		}
		if(isDataValidity == true){
			rs.setCode(200);
			rs.setMsg("数据导入成功");
			rs.setData(drivers);
			return rs;
		}else{
			rs.setCode(500);
			rs.setMsg("数据导入失败");
			rs.setData(drivers);
			return rs;
		}
	}

}
