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
import org.apache.commons.lang.ArrayUtils;
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
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO;
import org.springblade.common.constant.CommonConstant;
import org.springblade.common.tool.DateUtils;
import org.springblade.common.tool.IdCardUtil;
import org.springblade.common.tool.RegexUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Dict;
import org.springblade.system.feign.IDictClient;
import org.springblade.system.feign.ISysClient;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
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
	private IAnbiaoJiashiyuanGangqianpeixunService gangqianpeixunService;
	private IAnbiaoJiashiyuanWuzezhengmingService wuzezhengmingService;
	private IAnbiaoJiashiyuanAnquanzerenshuService anquanzerenshuService;
	private IAnbiaoJiashiyuanWeihaigaozhishuService weihaigaozhishuService;
	private IAnbiaoJiashiyuanLaodonghetongService laodonghetongService;
	private IAnbiaoJiashiyuanQitaService qitaService;
	private IJiaShiYuanService jiaShiYuanService;


	@PostMapping("/insert")
	@ApiLog("新增-驾驶员资料管理")
	@ApiOperation(value = "新增-驾驶员资料管理", notes = "传入jiaShiYuan", position = 1)
	public R insert(@RequestBody JiaShiYuan jiaShiYuan, BladeUser user) throws ParseException {
		R r = new R();
		SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
		QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShoujihaoma, jiaShiYuan.getShoujihaoma());
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShenfenzhenghao, jiaShiYuan.getShenfenzhenghao());
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId, jiaShiYuan.getDeptId());
		JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);

		//验证身份证初领日期
		String shenfenzhengchulingriqi = jiaShiYuan.getShenfenzhengchulingriqi().toString();
		if (StringUtils.isNotBlank(shenfenzhengchulingriqi) && !shenfenzhengchulingriqi.equals("null")){
			if (DateUtils.isDateString(shenfenzhengchulingriqi,null) == true){
				jiaShiYuan.setShenfenzhengchulingriqi(jiaShiYuan.getShenfenzhengchulingriqi());
			}else {
				r.setMsg(jiaShiYuan.getShenfenzhengchulingriqi()+",该身份证初领日期，不是时间格式；");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证身份证有效截止日期
		String shenfenzhengyouxiaoqi = jiaShiYuan.getShenfenzhengyouxiaoqi().toString();
		if (StringUtils.isNotBlank(shenfenzhengyouxiaoqi) && !shenfenzhengyouxiaoqi.equals("null")){
			if(DateUtils.isDateString(shenfenzhengyouxiaoqi,null) == true){
				jiaShiYuan.setShenfenzhengyouxiaoqi(jiaShiYuan.getShenfenzhengyouxiaoqi());
			}else {
				r.setMsg(jiaShiYuan.getShenfenzhengchulingriqi()+",该身份证有效截止日期，不是时间格式；");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证 验证身份证初领日期 不能大于 身份证有效截止日期
		if(StringUtils.isNotBlank(shenfenzhengchulingriqi) && !shenfenzhengchulingriqi.equals("null") && StringUtils.isNotBlank(shenfenzhengyouxiaoqi) && !shenfenzhengyouxiaoqi.equals("null")){
			int a1 = shenfenzhengchulingriqi.length();
			int b1 = shenfenzhengyouxiaoqi.length();
			if (a1 == b1){
				if (a1 <= 10){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if(DateUtils.belongCalendar(format.parse(shenfenzhengchulingriqi),format.parse(shenfenzhengyouxiaoqi))){
						jiaShiYuan.setShenfenzhengchulingriqi(jiaShiYuan.getShenfenzhengchulingriqi());
						jiaShiYuan.setShenfenzhengyouxiaoqi(jiaShiYuan.getShenfenzhengyouxiaoqi());
					}else{
						r.setMsg("身份证初次发放日期,不能大于身份证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				if(a1 > 10){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(DateUtils.belongCalendar(format.parse(shenfenzhengchulingriqi),format.parse(shenfenzhengyouxiaoqi))){
						jiaShiYuan.setShenfenzhengchulingriqi(jiaShiYuan.getShenfenzhengchulingriqi());
						jiaShiYuan.setShenfenzhengyouxiaoqi(jiaShiYuan.getShenfenzhengyouxiaoqi());
					}else{
						r.setMsg("身份证初次发放日期,不能大于身份证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}else{
				r.setMsg("身份证初领日期与身份证有效截止日期,时间格式不一致;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证驾驶证初领日期
		String jiashizhengchulingriqi = jiaShiYuan.getJiashizhengchulingriqi().toString();
		if (StringUtils.isNotBlank(jiashizhengchulingriqi) && !jiashizhengchulingriqi.equals("null")){
			if (DateUtils.isDateString(jiashizhengchulingriqi,null) == true){
				jiaShiYuan.setJiashizhengchulingriqi(jiaShiYuan.getJiashizhengchulingriqi());
			}else {
				r.setMsg(jiaShiYuan.getJiashizhengchulingriqi()+",该驾驶证初领日期，不是时间格式；");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证驾驶证有效截止日期
		String jiashizhengyouxiaoqi = jiaShiYuan.getJiashizhengyouxiaoqi().toString();
		if (StringUtils.isNotBlank(jiashizhengyouxiaoqi) && !jiashizhengyouxiaoqi.equals("null")){
			if(DateUtils.isDateString(jiashizhengyouxiaoqi,null) == true){
				jiaShiYuan.setJiashizhengyouxiaoqi(jiaShiYuan.getJiashizhengyouxiaoqi());
			}else {
				r.setMsg(jiaShiYuan.getJiashizhengyouxiaoqi()+",该驾驶证有效截止日期，不是时间格式；");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证 驾驶证初领日期 不能大于 驾驶证有效截止日期
		if(StringUtils.isNotBlank(jiashizhengchulingriqi) && !jiashizhengchulingriqi.equals("null") && StringUtils.isNotBlank(jiashizhengyouxiaoqi) && !jiashizhengyouxiaoqi.equals("null")){
			int a1 = jiashizhengchulingriqi.length();
			int b1 = jiashizhengyouxiaoqi.length();
			if (a1 == b1){
				if (a1 <= 10){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if(DateUtils.belongCalendar(format.parse(jiashizhengchulingriqi),format.parse(jiashizhengyouxiaoqi))){
						jiaShiYuan.setJiashizhengchulingriqi(jiaShiYuan.getJiashizhengchulingriqi());
						jiaShiYuan.setJiashizhengyouxiaoqi(jiaShiYuan.getJiashizhengyouxiaoqi());
					}else{
						r.setMsg("驾驶证初次发放日期,不能大于驾驶证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				if(a1 > 10){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(DateUtils.belongCalendar(format.parse(shenfenzhengchulingriqi),format.parse(shenfenzhengyouxiaoqi))){
						jiaShiYuan.setJiashizhengchulingriqi(jiaShiYuan.getJiashizhengchulingriqi());
						jiaShiYuan.setJiashizhengyouxiaoqi(jiaShiYuan.getJiashizhengyouxiaoqi());
					}else{
						r.setMsg("驾驶证初次发放日期,不能大于驾驶证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}else{
				r.setMsg("驾驶证初领日期与驾驶证有效截止日期,时间格式不一致;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}


		//验证从业资格证初领日期
		String congyezhengchulingriqi = jiaShiYuan.getCongyezhengchulingri().toString();
		if (StringUtils.isNotBlank(congyezhengchulingriqi) && !congyezhengchulingriqi.equals("null")){
			if (DateUtils.isDateString(congyezhengchulingriqi,null) == true){
				jiaShiYuan.setCongyezhengchulingri(jiaShiYuan.getCongyezhengchulingri());
			}else {
				r.setMsg(jiaShiYuan.getCongyezhengchulingri()+",该从业资格证初领日期，不是时间格式；");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证从业资格证有效截止日期
		String congyezhengyouxiaoqi = jiaShiYuan.getCongyezhengyouxiaoqi().toString();
		if (StringUtils.isNotBlank(congyezhengyouxiaoqi) && !congyezhengyouxiaoqi.equals("null")){
			if(DateUtils.isDateString(congyezhengyouxiaoqi,null) == true){
				jiaShiYuan.setCongyezhengyouxiaoqi(jiaShiYuan.getCongyezhengyouxiaoqi());
			}else {
				r.setMsg(jiaShiYuan.getCongyezhengyouxiaoqi()+",该从业资格证有效截止日期，不是时间格式；");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证 从业资格证初领日期 不能大于 从业资格证有效截止日期
		if(StringUtils.isNotBlank(congyezhengchulingriqi) && !congyezhengchulingriqi.equals("null") && StringUtils.isNotBlank(congyezhengyouxiaoqi) && !congyezhengyouxiaoqi.equals("null")){
			int a1 = congyezhengchulingriqi.length();
			int b1 = congyezhengyouxiaoqi.length();
			if (a1 == b1){
				if (a1 <= 10){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if(DateUtils.belongCalendar(format.parse(congyezhengchulingriqi),format.parse(congyezhengyouxiaoqi))){
						jiaShiYuan.setCongyezhengchulingri(jiaShiYuan.getCongyezhengchulingri());
						jiaShiYuan.setCongyezhengyouxiaoqi(jiaShiYuan.getCongyezhengyouxiaoqi());
					}else{
						r.setMsg("从业资格证初次发放日期,不能大于从业资格证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				if(a1 > 10){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(DateUtils.belongCalendar(format.parse(congyezhengchulingriqi),format.parse(congyezhengyouxiaoqi))){
						jiaShiYuan.setCongyezhengchulingri(jiaShiYuan.getCongyezhengchulingri());
						jiaShiYuan.setCongyezhengyouxiaoqi(jiaShiYuan.getCongyezhengyouxiaoqi());
					}else{
						r.setMsg("从业资格证初次发放日期,不能大于从业资格证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}else{
				r.setMsg("从业资格证初领日期与从业资格证有效截止日期,时间格式不一致;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证手机号码
		if (StringUtils.isBlank(jiaShiYuan.getShoujihaoma())) {
			r.setMsg("手机号码不能为空;");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		} else {
			if (RegexUtils.checkMobile(jiaShiYuan.getShoujihaoma())) {
				jiaShiYuan.setShoujihaoma(jiaShiYuan.getShoujihaoma());
			} else {
				r.setMsg("该手机号码不合法;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证驾驶证
		if (StringUtils.isNotBlank(jiaShiYuan.getJiashizhenghao())&&jiaShiYuan.getJiashizhenghao() != null){
			if (IdCardUtil.isValidCard(jiaShiYuan.getJiashizhenghao())== true){
				jiaShiYuan.setJiashizhenghao(jiaShiYuan.getJiashizhenghao());
				if(deail != null){
					r.setMsg(deail.getJiashizhenghao()+"该驾驶证号已存在;");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}else{
					jiaShiYuan.setJiashizhenghao(jiaShiYuan.getJiashizhenghao());
				}
			}else{
				r.setMsg(jiaShiYuan.getJiashizhenghao()+"该驾驶证号不合法;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证身份证
		if (IdCardUtil.isValidCard(jiaShiYuan.getShenfenzhenghao()) == true){
			jiaShiYuan.setShenfenzhenghao(jiaShiYuan.getShenfenzhenghao());
			if (deail != null){
				r.setMsg(deail.getShenfenzhenghao()+"该驾驶员身份证号已存在");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}else {
				jiaShiYuan.setShenfenzhenghao(jiaShiYuan.getShenfenzhenghao());
				//通过身份证获取年龄
				Integer age = IdCardUtil.getAgeByCard(jiaShiYuan.getShenfenzhenghao());
				jiaShiYuan.setNianling(age.toString());
				//通过身份证获取生日日期
				Date chushengshijian = IdCardUtil.getBirthDate(jiaShiYuan.getShenfenzhenghao());
				jiaShiYuan.setChushengshijian(dateFormat2.format(chushengshijian));
			}
		}else {
			r.setMsg(jiaShiYuan.getShenfenzhenghao()+"该驾驶员身份证号不合法");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}

		//新增
		if (deail == null) {
			if(StringUtils.isBlank(jiaShiYuan.getJiashiyuanleixing())){
				jiaShiYuan.setCongyerenyuanleixing("驾驶员");
			}
			jiaShiYuan.setCaozuoren(user.getUserName());
			jiaShiYuan.setCaozuorenid(user.getUserId());
			jiaShiYuan.setCreatetime(DateUtil.now());
			jiaShiYuan.setDenglumima(DigestUtil.encrypt(jiaShiYuan.getShoujihaoma().substring(jiaShiYuan.getShoujihaoma().length() - 6)));
			jiaShiYuan.setXingbie(Integer.toString(IdCardUtil.getGender(jiaShiYuan.getShenfenzhenghao())));
			jiaShiYuan.setIsdelete(0);
			boolean i = iJiaShiYuanService.save(jiaShiYuan);
			if (i){
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShoujihaoma, jiaShiYuan.getShoujihaoma());
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShenfenzhenghao, jiaShiYuan.getShenfenzhenghao());
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId, jiaShiYuan.getDeptId());
				jiaShiYuan = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);

				//向入职登记表添加信息
				AnbiaoJiashiyuanRuzhi ruzhi = new AnbiaoJiashiyuanRuzhi();
				QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
				ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, jiaShiYuan.getId());
				ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
				AnbiaoJiashiyuanRuzhi rzdeail = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
				if(rzdeail == null){
					ruzhi.setAjrCreateByName(jiaShiYuan.getCaozuoren());
					ruzhi.setAjrCreateByIds(jiaShiYuan.getCaozuorenid().toString());
					ruzhi.setAjrCreateTime(jiaShiYuan.getCaozuoshijian());
					ruzhi.setAjrDelete("0");
					ruzhi.setAjrAjIds(jiaShiYuan.getId());
					ruzhi.setAjrName(jiaShiYuan.getJiashiyuanxingming());
					ruzhi.setAjrSex(jiaShiYuan.getXingbie());
					ruzhi.setAjrAge(Integer.valueOf(jiaShiYuan.getNianling()));
					ruzhi.setAjrIdNumber(jiaShiYuan.getShenfenzhenghao());
					ruzhi.setAjrApproverStatus("0");
					i = ruzhiService.save(ruzhi);
				}

				//向驾驶证信息表添加数据
				AnbiaoJiashiyuanJiashizheng jiashizheng = new AnbiaoJiashiyuanJiashizheng();
				QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, jiashizheng.getAjjAjIds());
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
				AnbiaoJiashiyuanJiashizheng jszdeail = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
				if(jszdeail == null){
					jiashizheng.setAjjAjIds(jiaShiYuan.getId());
					jiashizheng.setAjjFileNo(jiaShiYuan.getJiashizhenghao());
					jiashizheng.setAjjValidPeriodStart(jiaShiYuan.getJiashizhengchulingriqi());
					jiashizheng.setAjjValidPeriodEnd(jiaShiYuan.getJiashizhengyouxiaoqi());
					jiashizheng.setAjjFrontPhotoAddress(jiaShiYuan.getJiashizhengfujian());
					jiashizheng.setAjjAttachedPhotos(jiaShiYuan.getJiashizhengfujianfanmian());
					jiashizheng.setAjjStatus("0");
					jiashizheng.setAjjDelete("0");
					jiashizheng.setAjjCreateByIds(jiaShiYuan.getCaozuorenid().toString());
					jiashizheng.setAjjCreateByName(jiaShiYuan.getCaozuoren());
					jiashizheng.setAjjCreateTime(jiaShiYuan.getCaozuoshijian());
					i = jiashizhengService.save(jiashizheng);
				}

				//向从业资格证信息表添加数据
				AnbiaoJiashiyuanCongyezigezheng congyezigezheng = new AnbiaoJiashiyuanCongyezigezheng();
				QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
				congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, jiaShiYuan.getId());
				congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
				AnbiaoJiashiyuanCongyezigezheng cyzdeail = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
				if(cyzdeail == null){
					congyezigezheng.setAjcIds(jiaShiYuan.getId());
					congyezigezheng.setAjcCertificateNo(jiaShiYuan.getCongyezigezheng());
					congyezigezheng.setAjcIssueDate(jiaShiYuan.getCongyezhengchulingri());
					congyezigezheng.setAjcValidUntil(jiaShiYuan.getCongyezhengyouxiaoqi());
					congyezigezheng.setAjcLicence(jiaShiYuan.getCongyezhengfujian());
					congyezigezheng.setAjcStatus("0");
					congyezigezheng.setAjcCreateTime(DateUtil.now());
					congyezigezheng.setAjcDelete("0");
					i = congyezigezhengService.save(congyezigezheng);
				}
			}

			if (i){
				r.setMsg("添加成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}else{
				r.setMsg("添加失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		} else {
			r.setMsg("该驾驶员信息已存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 * 初始化密码
	 */
	@PostMapping("/initialize")
	@ApiLog("初始化-驾驶员密码")
	@ApiOperation(value = "初始化-驾驶员密码",notes = "传入id")
	public R initialize(String id,BladeUser user){
		R r = new R();
		JiaShiYuan detal = iJiaShiYuanService.selectByIds(id);
		if (detal !=null){
			detal.setDenglumima(DigestUtil.encrypt(detal.getShoujihaoma().substring(detal.getShoujihaoma().length() - 6)));
			return R.status(iJiaShiYuanService.updateById(detal));
		}else {
			r.setMsg("数据为空，不能初始化密码");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 *密码修改
	 */
	@PostMapping("/update-password")
	@ApiLog("密码修改")
	@ApiOperation(value = "密码修改", notes = "传入userId与新旧密码值")
	public R updatePassword(BladeUser bladeUser,String id,String passWord,String oldpassWord){
		R r = new R();
		JiaShiYuan detal = iJiaShiYuanService.selectByIds(id);
		if(StringUtils.isBlank(oldpassWord) || StringUtils.isBlank(passWord)){
			r.setMsg("密码不能为空");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		oldpassWord = DigestUtil.encrypt(oldpassWord);
		if (detal.getDenglumima().equals(oldpassWord)){
			r.setMsg("原密码不正确");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}else{
			passWord = DigestUtil.encrypt(passWord);
			boolean temp = jiaShiYuanService.updatePassWord(id, passWord);
			if (temp == true){
				r.setMsg("修改成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			}else {
				r.setMsg("修改失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}
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
		ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, detal.getId());
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
			jiashizheng.setAjjUpdateByIds(user.getUserId().toString());
			jiashizheng.setAjjUpdateByName(user.getUserName());
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
			congyezigezheng.setAjcUpdateByIds(user.getUserId().toString());
			congyezigezheng.setAjcUpdateByName(user.getUserName());
			congyezigezheng.setAjcUpdateTime(DateUtil.now());
			congyezigezheng.setAjcDelete("1");
			congyezigezheng.setAjcIds(deail.getAjcIds());
			i = congyezigezhengService.updateById(congyezigezheng);
		}

		///体检表///
		QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
		tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, detal.getId());
		tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
		AnbiaoJiashiyuanTijian tijiandeail = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
		if(tijiandeail != null){
			AnbiaoJiashiyuanTijian tijian = new AnbiaoJiashiyuanTijian();
			tijian.setAjtUpdateByIds(user.getUserId().toString());
			tijian.setAjtUpdateByName(user.getUserName());
			tijian.setAjtUpdateTime(LocalDateTime.now());
			tijian.setAjtDelete("1");
			tijian.setAjtIds(tijiandeail.getAjtIds());
			i = tijianService.updateById(tijian);
		}

		///岗前培训///
		QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
		gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, detal.getId());
		gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
		AnbiaoJiashiyuanGangqianpeixun gangqianpeixundeail = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
		if(gangqianpeixundeail != null){
			AnbiaoJiashiyuanGangqianpeixun gangqianpeixun = new AnbiaoJiashiyuanGangqianpeixun();
			gangqianpeixun.setAjgUpdateByIds(user.getUserId().toString());
			gangqianpeixun.setAjgUpdateByName(user.getUserName());
			gangqianpeixun.setAjgUpdateTime(LocalDateTime.now());
			gangqianpeixun.setAjgDelete("1");
			gangqianpeixun.setAjgIds(gangqianpeixundeail.getAjgIds());
			i = gangqianpeixunService.updateById(gangqianpeixun);
		}

		///无责证明表///
		QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
		wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, detal.getId());
		wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
		AnbiaoJiashiyuanWuzezhengming wuzezhengmingdeail = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
		if(wuzezhengmingdeail != null){
			AnbiaoJiashiyuanWuzezhengming wuzezhengming = new AnbiaoJiashiyuanWuzezhengming();
			wuzezhengming.setAjwUpdateByIds(user.getUserId().toString());
			wuzezhengming.setAjwUpdateByName(user.getUserName());
			wuzezhengming.setAjwUpdateTime(LocalDateTime.now());
			wuzezhengming.setAjwDelete("1");
			wuzezhengming.setAjwIds(wuzezhengmingdeail.getAjwIds());
			i = wuzezhengmingService.updateById(wuzezhengming);
		}

		///驾驶员安全责任书///
		QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu>();
		anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaAjIds, detal.getId());
		anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaDelete, "0");
		AnbiaoJiashiyuanAnquanzerenshu anquanzerenshudeail = anquanzerenshuService.getBaseMapper().selectOne(anquanzerenshuQueryWrapper);
		if(anquanzerenshudeail != null){
			AnbiaoJiashiyuanAnquanzerenshu anquanzerenshu = new AnbiaoJiashiyuanAnquanzerenshu();
			anquanzerenshu.setAjaUpdateByIds(user.getUserId().toString());
			anquanzerenshu.setAjaUpdateByName(user.getUserName());
			anquanzerenshu.setAjaUpdateTime(LocalDateTime.now());
			anquanzerenshu.setAjaDelete("1");
			anquanzerenshu.setAjaIds(anquanzerenshudeail.getAjaIds());
			i = anquanzerenshuService.updateById(anquanzerenshu);
		}

		///驾驶员职业危害告知书///
		QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
		weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwAjIds, detal.getId());
		weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwDelete, "0");
		AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishudeail = weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);
		if(weihaigaozhishudeail != null){
			AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishu = new AnbiaoJiashiyuanWeihaigaozhishu();
			weihaigaozhishu.setAjwUpdateByIds(user.getUserId().toString());
			weihaigaozhishu.setAjwUpdateByName(user.getUserName());
			weihaigaozhishu.setAjwUpdateTime(LocalDateTime.now());
			weihaigaozhishu.setAjwDelete("1");
			weihaigaozhishu.setAjwIds(weihaigaozhishudeail.getAjwIds());
			i = weihaigaozhishuService.updateById(weihaigaozhishu);
		}

		///劳动合同///
		QueryWrapper<AnbiaoJiashiyuanLaodonghetong> laodonghetongQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanLaodonghetong>();
		laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwAjIds,detal.getId());
		laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwDelete, "0");
		AnbiaoJiashiyuanLaodonghetong laodonghetongdeail = laodonghetongService.getBaseMapper().selectOne(laodonghetongQueryWrapper);
		if(laodonghetongdeail != null){
			AnbiaoJiashiyuanLaodonghetong laodonghetong = new AnbiaoJiashiyuanLaodonghetong();
			laodonghetong.setAjwUpdateByIds(user.getUserId().toString());
			laodonghetong.setAjwUpdateByName(user.getUserName());
			laodonghetong.setAjwUpdateTime(LocalDateTime.now());
			laodonghetong.setAjwDelete("1");
			laodonghetong.setAjwIds(laodonghetongdeail.getAjwIds());
			i = laodonghetongService.updateById(laodonghetong);
		}

		///其他///
		QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanQita>();
		qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds,detal.getId());
		qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
		AnbiaoJiashiyuanQita qitadeail = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);
		if(qitadeail != null){
			AnbiaoJiashiyuanQita qita = new AnbiaoJiashiyuanQita();
			qita.setAjtUpdateByIds(user.getUserId().toString());
			qita.setAjtUpdateByName(user.getUserName());
			qita.setAjtUpdateTime(LocalDateTime.now());
			qita.setAjtDelete("1");
			qita.setAjtIds(qitadeail.getAjtIds());
			i = qitaService.updateById(qita);
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
				ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, detal.getId());
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

			///体检表///
			if (type == 5){
				QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
				tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, detal.getId());
				tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
				AnbiaoJiashiyuanTijian tijianInfo = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
				if (tijianInfo != null){
					//体检附件
					if (StrUtil.isNotEmpty(tijianInfo.getAjtEnclosure()) && tijianInfo.getAjtEnclosure().contains("http") == false){
						tijianInfo.setAjtEnclosure(fileUploadClient.getUrl(tijianInfo.getAjtEnclosure()));
					}
					r.setData(tijianInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				}else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///岗前培训三级教育卡///
			if (type == 6){
				QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
				gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, detal.getId());
				gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
				AnbiaoJiashiyuanGangqianpeixun gangqianpeixunInfo = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
				if (gangqianpeixunInfo != null){
					//培训附件
					if (StrUtil.isNotEmpty(gangqianpeixunInfo.getAjgTrainingEnclosure()) && gangqianpeixunInfo.getAjgTrainingEnclosure().contains("http") == false){
						gangqianpeixunInfo.setAjgTrainingEnclosure(fileUploadClient.getUrl(gangqianpeixunInfo.getAjgTrainingEnclosure()));
					}
					r.setData(gangqianpeixunInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				}else{
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///三年无重大责任事故正面///
			if (type == 7){
				QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
				wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming :: getAjwAjIds,detal.getId());
				wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming :: getAjwDelete, "0");
				AnbiaoJiashiyuanWuzezhengming wuzezhengmingInfo = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
				if (wuzezhengmingInfo != null){
					//无责证明附件
					if (StrUtil.isNotEmpty(wuzezhengmingInfo.getAjwEnclosure()) && wuzezhengmingInfo.getAjwEnclosure().contains("http") == false){
						wuzezhengmingInfo.setAjwEnclosure(fileUploadClient.getUrl(wuzezhengmingInfo.getAjwEnclosure()));
					}
					r.setData(wuzezhengmingInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				}else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}

			}

			///驾驶员安全责任书///
			if (type == 8) {
				QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu>();
				anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaAjIds, detal.getId());
				anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaDelete, "0");
				AnbiaoJiashiyuanAnquanzerenshu anquanzerenshuInfo = anquanzerenshuService.getBaseMapper().selectOne(anquanzerenshuQueryWrapper);
				if (anquanzerenshuInfo != null) {
					//安全责任书附件
					if (StrUtil.isNotEmpty(anquanzerenshuInfo.getAjaEnclosure()) && anquanzerenshuInfo.getAjaEnclosure().contains("http") == false) {
						anquanzerenshuInfo.setAjaEnclosure(fileUploadClient.getUrl(anquanzerenshuInfo.getAjaEnclosure()));
					}
					//安全责任书签名附件
					if (StrUtil.isNotEmpty(anquanzerenshuInfo.getAjaAutographEnclosure()) && anquanzerenshuInfo.getAjaAutographEnclosure().contains("http") == false) {
						anquanzerenshuInfo.setAjaAutographEnclosure(fileUploadClient.getUrl(anquanzerenshuInfo.getAjaAutographEnclosure()));
					}
					r.setData(anquanzerenshuInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///驾驶员职业危害告知书///
			if (type == 9){
				QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
				weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu :: getAjwAjIds,detal.getId());
				weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu :: getAjwAjIds, "0");
				AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishuInfo = weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);

				if (weihaigaozhishuInfo != null){
					//无责证明附件
					if(StrUtil.isNotEmpty(weihaigaozhishuInfo.getAjwEnclosure()) && weihaigaozhishuInfo.getAjwEnclosure().contains("http") == false){
						weihaigaozhishuInfo.setAjwEnclosure(fileUploadClient.getUrl(weihaigaozhishuInfo.getAjwEnclosure()));
					}
					//无责证明签名附件
					if(StrUtil.isNotEmpty(weihaigaozhishuInfo.getAjwAutographEnclosure()) && weihaigaozhishuInfo.getAjwAutographEnclosure().contains("http") == false){
						weihaigaozhishuInfo.setAjwAutographEnclosure(fileUploadClient.getUrl(weihaigaozhishuInfo.getAjwAutographEnclosure()));
					}
					r.setData(weihaigaozhishuInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				}else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///劳动合同///
			if (type == 10){
				QueryWrapper<AnbiaoJiashiyuanLaodonghetong> laodonghetongQueryWrapper = new QueryWrapper<>();
				laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong :: getAjwAjIds,detal.getId());
				laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong :: getAjwAjIds, "0");
				AnbiaoJiashiyuanLaodonghetong laodonghetongInfo = laodonghetongService.getBaseMapper().selectOne(laodonghetongQueryWrapper);

				if (laodonghetongInfo != null){
					//劳动合同附件
					if(StrUtil.isNotEmpty(laodonghetongInfo.getAjwEnclosure()) && laodonghetongInfo.getAjwEnclosure().contains("http") == false){
						laodonghetongInfo.setAjwEnclosure(fileUploadClient.getUrl(laodonghetongInfo.getAjwEnclosure()));
					}
					//劳动合同签名附件
					if(StrUtil.isNotEmpty(laodonghetongInfo.getAjwAutographEnclosure()) && laodonghetongInfo.getAjwAutographEnclosure().contains("http") == false){
						laodonghetongInfo.setAjwAutographEnclosure(fileUploadClient.getUrl(laodonghetongInfo.getAjwAutographEnclosure()));
					}
					r.setData(laodonghetongInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				}else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///其他///
			if (type == 11){
				QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<>();
				qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita :: getAjtAjIds,detal.getId());
				qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita :: getAjtAjIds, "0");
				AnbiaoJiashiyuanQita qitaInfo = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);

				if (qitaInfo != null){
					//劳动合同附件
					if(StrUtil.isNotEmpty(qitaInfo.getAjtEnclosure()) && qitaInfo.getAjtEnclosure().contains("http") == false){
						qitaInfo.setAjtEnclosure(fileUploadClient.getUrl(qitaInfo.getAjtEnclosure()));
					}
					r.setData(qitaInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				}else {
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

	@GetMapping("/getDriverInfo")
	@ApiLog("根据驾驶员ID获取个人详细信息（司机小程序个人中心）")
	@ApiOperation(value = "根据驾驶员ID获取个人详细信息（司机小程序个人中心）", notes = "传入id", position = 1)
	public R getDriverInfo( String jsyId) {
		return R.data(iJiaShiYuanService.selectDriverInfo(jsyId));
	}


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
	 * 驾驶员档案信息--验证
	 * @update: 黄亚平 添加验证
	 * @param
	 */
	@PostMapping("driverDeptImport")
	@ApiLog("企业端--驾驶员档案信息--验证(最新)")
	@ApiOperation(value = "企业端--驾驶员档案信息--验证(最新)", notes = "file", position = 11)
	public R driverDeptImport(@RequestParam(value = "file") MultipartFile file,BladeUser user,@RequestParam String leixing) throws ParseException {
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
			rs.setCode(400);
			return rs;
		}

		List<JiaShiYuan> drivers= new ArrayList<>();
		for(Map<String,Object> a:readAll){
			aa++;
			Dept dept;
			JiaShiYuan driver=new JiaShiYuan();
			SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
			JiaShiYuanVO driverVO = null;
			//验证所属企业是否存在
			String deptname =  String.valueOf(a.get("所属企业")).trim();
			if(StringUtils.isBlank(deptname)){
				driver.setMsg("所属企业不能为空;");
				driver.setImportUrl("icon_cha.png");
				errorStr+="所属企业不能为空;";
				bb++;
			}
			dept = iSysClient.getDeptByName(deptname.trim());
			if (dept != null){
				driver.setDeptId(Integer.valueOf(dept.getId()));
				driver.setDeptName(deptname.trim());
				driver.setImportUrl("icon_gou.png");
			}else{
				driver.setMsg(deptname+"所属企业不存在;");
				driver.setImportUrl("icon_cha.png");
				errorStr+=deptname+"所属企业不存在;";
				rs.setMsg(errorStr);
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setData(drivers);
				return rs;
			}
			//验证驾驶员姓名不能为空
			String driverName = String.valueOf(a.get("驾驶员姓名")).trim();
			if(StringUtils.isBlank(driverName) && !driverName.equals("null")){
				driver.setMsg("驾驶员姓名不能为空;");
				driver.setImportUrl("icon_cha.png");
				errorStr+="驾驶员姓名不能为空;";
				bb++;
			}else{
				driver.setJiashiyuanxingming(driverName);
				driver.setImportUrl("icon_gou.png");
			}
			if(!"更新导入".equals(leixing)){
				//验证驾驶员性别
				String sex = String.valueOf(a.get("性别")).trim();
				if (StringUtils.isBlank(sex) && !sex.equals("null")) {
					driver.setMsg("性别不能为空;");
					driver.setImportUrl("icon_cha.png");
					errorStr += "性别不能为空;";
					bb++;
				} else {
					String[] arr = {"男", "女"};
					boolean ss = false;
					ss = ArrayUtils.contains(arr, sex);
					if (ss == true) {
						if ("男".equals(sex)) {
							driver.setXingbie("1");
							driver.setXingbieshow(sex);
							driver.setImportUrl("icon_gou.png");
						}
						if ("女".equals(sex)) {
							driver.setXingbie("2");
							driver.setXingbieshow(sex);
							driver.setImportUrl("icon_gou.png");
						}
					} else {
						driver.setMsg("性别填写异常;");
						driver.setImportUrl("icon_cha.png");
						errorStr += "性别填写异常;";
						bb++;
					}
				}
				//验证身份证号码
				String tmp = String.valueOf(a.get("身份证号码")).trim();
				if (StringUtils.isNotBlank(tmp) && !tmp.equals("null")) {
					//校验身份证号码是否合法
					if (IdCardUtil.isValidCard(tmp) == true) {
						driver.setShenfenzhenghao(tmp);
						//根据企业ID、身份证号、从业人员类型查询该身份证是否存在
						String congyerenyuanleixing = String.valueOf(a.get("从业人员类型")).trim();
						driverVO = iJiaShiYuanService.selectByCardNo(driver.getDeptId().toString(), tmp, null, congyerenyuanleixing);
						if (driverVO != null) {
							driver.setMsg(driverVO.getShenfenzhenghao() + "该企业驾驶员身份证号已存在;");
							driver.setImportUrl("icon_cha.png");
							errorStr += driverVO.getShenfenzhenghao() + "该企业驾驶员身份证号已存在;";
							bb++;
						} else {
							driver.setShenfenzhenghao(tmp);
							driver.setImportUrl("icon_gou.png");
						}
					} else {
						driver.setMsg(tmp + "该驾驶员身份证号不合法;");
						driver.setImportUrl("icon_cha.png");
						errorStr += tmp + "该驾驶员身份证号不合法;";
						bb++;
					}
				}
			}
			//验证手机号码是否合法
			String shoujihaoma = String.valueOf(a.get("手机号码")).trim();
			if(StringUtils.isBlank(shoujihaoma) && !shoujihaoma.equals("null")){
				driver.setMsg("手机号码不能为空;");
				driver.setImportUrl("icon_cha.png");
				errorStr+="手机号码不能为空;";
				bb++;
			}else{
				if(RegexUtils.checkMobile(shoujihaoma)){
					driver.setShoujihaoma(shoujihaoma);
//					if ("运维端".equals(type)) {
					if ("更新导入".equals(leixing)) {
						//根据企业ID、驾驶员姓名、手机号码、从业人员类型查询驾驶员是否存在
//							String congyerenyuanleixing = String.valueOf(a.get("从业人员类型")).trim();
						JiaShiYuan driverss = iJiaShiYuanService.getjiaShiYuan(driver.getDeptId().toString(),driver.getJiashiyuanxingming(),driver.getShoujihaoma(),null);
						if(driverss == null){
							driver.setMsg(shoujihaoma+"该企业驾驶员姓名及手机号码不存在;");
							driver.setImportUrl("icon_cha.png");
							errorStr+=shoujihaoma+"该企业驾驶员姓名及手机号码不存在;";
							bb++;
						}else{
							driver.setImportUrl("icon_gou.png");
						}
					}else{
						//根据企业ID、驾驶员姓名、手机号码、从业人员类型查询驾驶员是否存在
						String congyerenyuanleixing = String.valueOf(a.get("从业人员类型")).trim();
						JiaShiYuan driverss = iJiaShiYuanService.getjiaShiYuan(driver.getDeptId().toString(),driver.getJiashiyuanxingming(),driver.getShoujihaoma(),congyerenyuanleixing);
						if(driverss!=null){
							driver.setMsg(shoujihaoma+"该企业驾驶员姓名及手机号码已同时存在;");
							driver.setImportUrl("icon_cha.png");
							errorStr+=shoujihaoma+"该企业驾驶员姓名及手机号码已同时存在;";
							bb++;
						}else{
							driver.setImportUrl("icon_gou.png");
						}
					}
//					}
//					else{
//						if(driverss == null){
//							driver.setMsg(shoujihaoma+"该企业驾驶员姓名及手机号码不存在;");
//							driver.setImportUrl("icon_cha.png");
//							errorStr+=shoujihaoma+"该企业驾驶员姓名及手机号码不存在;";
//							bb++;
//						}else{
//							driver.setImportUrl("icon_gou.png");
//						}
//					}
				}else{
					driver.setMsg(shoujihaoma+"该手机号码不合法;");
					errorStr+=shoujihaoma+"该手机号码不合法;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证Excel导入时，是否存在重复数据
			for (JiaShiYuan item : drivers) {
				if (item.getJiashiyuanxingming().equals(driverName) && item.getShoujihaoma().equals(shoujihaoma) && item.getDeptName().equals(deptname)) {
					driver.setImportUrl("icon_cha.png");
					errorStr += driverName + "驾驶员重复；";
					driver.setMsg(driverName + "驾驶员重复；");
					bb++;
				}
			}
			if(!"更新导入".equals(leixing)){
				//验证从业资格证类别是否满足规则
				String congyeleibie = String.valueOf(a.get("从业资格证类别")).trim();
				if (StringUtils.isBlank(congyeleibie) && !congyeleibie.equals("null")) {
					driver.setMsg("从业资格证类别不能为空;");
					driver.setImportUrl("icon_cha.png");
					errorStr += "从业资格证类别不能为空;";
					bb++;
				} else {
					boolean ss = false;
					List<Dict> dictVOList = iDictClient.getDictByCode("congyezigezhengleibie", null);
					for (int i = 0; i < dictVOList.size(); i++) {
						ss = dictVOList.get(i).getDictValue().equals(congyeleibie);
						if (ss == true) {
							break;
						}
					}
					if (ss == true) {
						dictVOList = iDictClient.getDictByCode("congyezigezhengleibie", congyeleibie);
						driver.setImportUrl("icon_gou.png");
						driver.setCongyeleibie(dictVOList.get(0).getDictKey());
						driver.setCongyeleibieshow(dictVOList.get(0).getDictValue());
					} else {
						driver.setImportUrl("icon_cha.png");
						errorStr += congyeleibie + ",该从业资格证类别异常,请校验”;";
						driver.setMsg(congyeleibie + ",该从业资格证类别异常,请校验;");
						bb++;
					}
				}
				//验证从业人员类型
				String congyerenyuanleixing = String.valueOf(a.get("从业人员类型")).trim();
				if (StringUtils.isNotBlank(congyerenyuanleixing) && !congyerenyuanleixing.equals("null")) {
					boolean ss = false;
					List<Dict> dictVOList = iDictClient.getDictByCode("congyerenyuanleixing", null);
					for (int i = 0; i < dictVOList.size(); i++) {
						ss = dictVOList.get(i).getDictValue().equals(congyerenyuanleixing);
						if (ss == true) {
							break;
						}
					}
					if (ss == true) {
						dictVOList = iDictClient.getDictByCode("congyerenyuanleixing", congyerenyuanleixing);
						driver.setImportUrl("icon_gou.png");
						driver.setJiashiyuanleixing(dictVOList.get(0).getDictKey());
					} else {
						driver.setImportUrl("icon_cha.png");
						errorStr += congyerenyuanleixing + ",该从业人员类型异常,请校验”;";
						driver.setMsg(congyerenyuanleixing + ",该从业人员类型异常,请校验;");
						bb++;
					}
				} else {
					driver.setMsg("从业人员类型不能为空;");
					driver.setImportUrl("icon_cha.png");
					errorStr += "从业人员类型不能为空;";
					bb++;
				}
			}
			//验证驾驶证是否合法
			String jiashizheng = String.valueOf(a.get("驾驶证号")).trim();
			if(StringUtils.isNotBlank(jiashizheng) && jiashizheng != null && !jiashizheng.equals("null")){
				if(IdCardUtil.isValidCard(jiashizheng) == true){
					driver.setJiashizhenghao(jiashizheng);
					driverVO = iJiaShiYuanService.selectByCardNo(driver.getDeptId().toString(),null,jiashizheng,"驾驶员");
					if(driverVO!=null){
						driver.setMsg(driverVO.getJiashizhenghao()+"该企业驾驶证号已存在;");
						driver.setImportUrl("icon_cha.png");
						errorStr+=driverVO.getJiashizhenghao()+"该企业驾驶证号已存在;";
						bb++;
					}else{
						driver.setShenfenzhenghao(jiashizheng);
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
			if(StringUtils.isNotBlank(congyezigezheng) && !congyezigezheng.equals("null")){
				driver.setCongyezigezheng(congyezigezheng);
				driver.setImportUrl("icon_gou.png");
			}
			//验证准驾车型是否满足规则
			String zhunjiachexing = String.valueOf(a.get("准驾车型")).trim();
			if(StringUtils.isNotBlank(zhunjiachexing) && !zhunjiachexing.equals("null")){
				boolean ss = false;
				List<Dict> dictVOList = iDictClient.getDictByCode("zhunjiachexing",null);
				for(int i= 0;i<dictVOList.size();i++){
					ss = dictVOList.get(i).getDictValue().equals(zhunjiachexing);
					if(ss == true){
						break;
					}
				}
				if(ss == true){
					dictVOList = iDictClient.getDictByCode("zhunjiachexing",zhunjiachexing);
					driver.setImportUrl("icon_gou.png");
					driver.setZhunjiachexing(dictVOList.get(0).getDictKey());
				}else{
					driver.setImportUrl("icon_cha.png");
					errorStr += zhunjiachexing+",该从准驾车型异常,请校验”;";
					driver.setMsg(zhunjiachexing+",该从准驾车型异常,请校验;");
					bb++;
				}
			}
			//验证从业资格证发放日期
			String congyezhengchulingri = String.valueOf(a.get("从业资格证初次发放日期")).trim();
			if(StringUtils.isNotBlank(congyezhengchulingri) && !congyezhengchulingri.equals("null")){
				if(DateUtils.isDateString(congyezhengchulingri,null) == true){
					driver.setCongyezhengchulingri(congyezhengchulingri);
					driver.setImportUrl("icon_gou.png");
				}else{
					driver.setMsg(congyezhengchulingri+",该从业资格证初次发放日期,不是时间格式;");
					errorStr+=congyezhengchulingri+",该从业资格证初次发放日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证从业资格证有效截至日期
			String congyezhengyouxiaoqi = String.valueOf(a.get("从业资格证有效截至日期")).trim();
			if(StringUtils.isNotBlank(congyezhengyouxiaoqi) && !congyezhengyouxiaoqi.equals("null")){
				if(DateUtils.isDateString(congyezhengyouxiaoqi,null) == true){
					driver.setCongyezhengyouxiaoqi(congyezhengyouxiaoqi);
					driver.setImportUrl("icon_gou.png");
				}else{
					driver.setMsg(congyezhengyouxiaoqi+",该从业资格证有效截至日期,不是时间格式;");
					errorStr+=congyezhengyouxiaoqi+",该从业资格证有效截至日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证 从业资格证发放日期 不能大于 从业资格证有效截至日期
			if(StringUtils.isNotBlank(congyezhengchulingri) && !congyezhengchulingri.equals("null") && StringUtils.isNotBlank(congyezhengyouxiaoqi) && !congyezhengyouxiaoqi.equals("null")){
				int a1 = congyezhengchulingri.length();
				int b1 = congyezhengyouxiaoqi.length();
				if(a1 == b1){
					if(a1 <= 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if(DateUtils.belongCalendar(format.parse(congyezhengchulingri),format.parse(congyezhengyouxiaoqi))){
							driver.setImportUrl("icon_gou.png");
						}else{
							driver.setMsg("从业资格证发放日期,不能大于从业资格证有效截至日期;");
							errorStr+="从业资格证发放日期,不能大于从业资格证有效截至日期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if(a1 > 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if(DateUtils.belongCalendar(format.parse(congyezhengchulingri),format.parse(congyezhengyouxiaoqi))){
							driver.setImportUrl("icon_gou.png");
						}else{
							driver.setMsg("从业资格证发放日期,不能大于从业资格证有效截至日期;");
							errorStr+="从业资格证发放日期,不能大于从业资格证有效截至日期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				}else{
					driver.setMsg("从业资格证发放日期与从业资格证有效截至日期,时间格式不一致;");
					errorStr+="从业资格证发放日期与从业资格证有效截至日期,时间格式不一致;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证本次诚信考核日期
			String chengxinkaoheshijian = String.valueOf(a.get("本次诚信考核日期")).trim();
			if(StringUtils.isNotBlank(chengxinkaoheshijian) && !chengxinkaoheshijian.equals("null")){
				if(DateUtils.isDateString(chengxinkaoheshijian,null) == true){
					driver.setChengxinkaoheshijian(chengxinkaoheshijian);
					driver.setImportUrl("icon_gou.png");
				}else{
					driver.setMsg(chengxinkaoheshijian+",该本次诚信考核日期,不是时间格式;");
					errorStr+=chengxinkaoheshijian+",该本次诚信考核日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证下次诚信考核日期
			String xiacichengxinkaoheshijian = String.valueOf(a.get("下次诚信考核日期")).trim();
			if(StringUtils.isNotBlank(xiacichengxinkaoheshijian) && !xiacichengxinkaoheshijian.equals("null")){
				if(DateUtils.isDateString(xiacichengxinkaoheshijian,null) == true){
					driver.setXiacichengxinkaoheshijian(xiacichengxinkaoheshijian);
					driver.setImportUrl("icon_gou.png");
				}else{
					driver.setMsg(xiacichengxinkaoheshijian+",该下次诚信考核日期,不是时间格式;");
					errorStr+=xiacichengxinkaoheshijian+",该下次诚信考核日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证 本次诚信考核日期 不能大于 下次诚信考核日期
			if(StringUtils.isNotBlank(chengxinkaoheshijian) && !chengxinkaoheshijian.equals("null") && StringUtils.isNotBlank(xiacichengxinkaoheshijian) && !xiacichengxinkaoheshijian.equals("null")){
				int a1 = chengxinkaoheshijian.length();
				int b1 = xiacichengxinkaoheshijian.length();
				if(a1 == b1){
					if(a1 <= 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if(DateUtils.belongCalendar(format.parse(chengxinkaoheshijian),format.parse(xiacichengxinkaoheshijian))){
							driver.setImportUrl("icon_gou.png");
						}else{
							driver.setMsg("本次诚信考核日期,不能大于下次诚信考核日期;");
							errorStr+="本次诚信考核日期,不能大于下次诚信考核日期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if(a1 > 10){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if(DateUtils.belongCalendar(format.parse(chengxinkaoheshijian),format.parse(xiacichengxinkaoheshijian))){
							driver.setImportUrl("icon_gou.png");
						}else{
							driver.setMsg("本次诚信考核日期,不能大于下次诚信考核日期;");
							errorStr+="本次诚信考核日期,不能大于下次诚信考核日期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				}else{
					driver.setMsg("从业资格证发放日期与从业资格证有效截至日期,时间格式不一致;");
					errorStr+="从业资格证发放日期与从业资格证有效截至日期,时间格式不一致;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证驾驶证有效截至日期
			String jiashizhengyouxiaoqi = String.valueOf(a.get("驾驶证有效截至日期")).trim();
			if(StringUtils.isNotBlank(jiashizhengyouxiaoqi) && !jiashizhengyouxiaoqi.equals("null")){
				if(DateUtils.isDateString(jiashizhengyouxiaoqi,null) == true){
					driver.setJiashizhengyouxiaoqi(jiashizhengyouxiaoqi);
					driver.setImportUrl("icon_gou.png");
				}else{
					driver.setMsg(jiashizhengyouxiaoqi+",该驾驶证有效截至日期,不是时间格式;");
					errorStr+=jiashizhengyouxiaoqi+",该驾驶证有效截至日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证身份证有效截至日期
			String shenfenzhengyouxiaoqi = String.valueOf(a.get("身份证有效截至日期")).trim();
			if(StringUtils.isNotBlank(shenfenzhengyouxiaoqi) && !shenfenzhengyouxiaoqi.equals("null")){
				if(DateUtils.isDateString(shenfenzhengyouxiaoqi,null) == true){
					driver.setShenfenzhengyouxiaoqi(shenfenzhengyouxiaoqi);
					driver.setImportUrl("icon_gou.png");
				}else{
					driver.setMsg(shenfenzhengyouxiaoqi+",该驾驶证有效截至日期,不是时间格式;");
					errorStr+=shenfenzhengyouxiaoqi+",该驾驶证有效截至日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证体检有效截至日期
			String tijianyouxiaoqi = String.valueOf(a.get("体检有效截至日期")).trim();
			if(StringUtils.isNotBlank(tijianyouxiaoqi) && !tijianyouxiaoqi.equals("null")){
				if(DateUtils.isDateString(tijianyouxiaoqi,null) == true){
					driver.setTijianyouxiaoqi(tijianyouxiaoqi);
					driver.setImportUrl("icon_gou.png");
				}else{
					driver.setMsg(tijianyouxiaoqi+",该体检有效截至日期,不是时间格式;");
					errorStr+=tijianyouxiaoqi+",该体检有效截至日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}
			//验证发证机关
			String fazhengjiguan = String.valueOf(a.get("发证机关")).trim();
			if(StringUtils.isNotBlank(fazhengjiguan) && !fazhengjiguan.equals("null")){
				driver.setFazhengjiguan(fazhengjiguan);
				driver.setImportUrl("icon_gou.png");
			}
			//验证籍贯
			String jiatingzhuzhi = String.valueOf(a.get("籍贯")).trim();
			if(StringUtils.isNotBlank(jiatingzhuzhi) && !jiatingzhuzhi.equals("null")){
				driver.setJiatingzhuzhi(jiatingzhuzhi);
				driver.setImportUrl("icon_gou.png");
			}
			//验证备注
			String beizhu = String.valueOf(a.get("备注")).trim();
			if(StringUtils.isNotBlank(beizhu) && !beizhu.equals("null")){
				driver.setBeizhu(beizhu);
				driver.setImportUrl("icon_gou.png");
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
	 * 企业端--驾驶员档案信息--确认导入
	 * @param
	 */
	@PostMapping("driverDeptImportOk")
	@ApiLog("企业端--驾驶员档案信息--确认导入(最新)")
	@ApiOperation(value = "企业端--驾驶员档案信息--确认导入(最新)", notes = "drivers", position = 10)
	public R driverDeptImportOk(@RequestParam(value = "drivers") String drivers,BladeUser user,@RequestParam Integer userId,@RequestParam String userName){
		JSONArray json = JSONUtil.parseArray(drivers);
		List<Map<String,Object>> lists = (List)json;
		R rs = new R();
		if (user == null) {
			rs.setCode(500);
			rs.setMsg("用户权限验证失败");
			rs.setData(null);
			rs.setSuccess(false);
			return rs;
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

		if(lists.size()>2000){
			errorStr+="导入数据超过2000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(400);
			return rs;
		}

		for(Map<String,Object> a:lists){
			aa++;
			JiaShiYuan driver=new JiaShiYuan();
			SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
			String deptId = String.valueOf(a.get("deptId"));
			driver.setDeptId(Integer.valueOf(deptId));
			driver.setJiashiyuanxingming(String.valueOf(a.get("jiashiyuanxingming")).trim());
			driver.setXingbie(String.valueOf(a.get("xingbie")));
			String tmp = String.valueOf(a.get("shenfenzhenghao")).trim();
			driver.setShenfenzhenghao(tmp);
			//通过身份证获取年龄
			Integer age = IdCardUtil.getAgeByCard(tmp);
			driver.setNianling(age.toString());
			//通过身份证获取生日日期
			Date chushengshijian = IdCardUtil.getBirthDate(tmp);
			String shoujihaoma = String.valueOf(a.get("shoujihaoma")).trim();
			driver.setShoujihaoma(shoujihaoma);
			driver.setChushengshijian(dateFormat2.format(chushengshijian));
			driver.setCongyeleibie(String.valueOf(a.get("congyeleibie")).trim());
			driver.setJiashiyuanleixing(String.valueOf(a.get("jiashiyuanleixing")).trim());
			driver.setCongyerenyuanleixing(String.valueOf(a.get("jiashiyuanleixing")).trim());
			driver.setJiashizhenghao(String.valueOf(a.get("jiashizhenghao")).trim());
			driver.setCongyezigezheng(String.valueOf(a.get("congyezigezheng")).trim());
			driver.setZhunjiachexing(String.valueOf(a.get("zhunjiachexing")).trim());
			driver.setCongyezhengchulingri(String.valueOf(a.get("congyezhengchulingri")).trim());
			driver.setCongyezhengyouxiaoqi(String.valueOf(a.get("congyezhengyouxiaoqi")).trim());
			driver.setChengxinkaoheshijian(String.valueOf(a.get("chengxinkaoheshijian")).trim());
			driver.setXiacichengxinkaoheshijian(String.valueOf(a.get("xiacichengxinkaoheshijian")).trim());
			driver.setJiashizhengchulingriqi(String.valueOf(a.get("jiashizhengchulingriqi")).trim());
			driver.setJiashizhengyouxiaoqi(String.valueOf(a.get("jiashizhengyouxiaoqi")).trim());
			driver.setShenfenzhengyouxiaoqi(String.valueOf(a.get("shenfenzhengyouxiaoqi")).trim());
			driver.setTijianyouxiaoqi(String.valueOf(a.get("tijianyouxiaoqi")).trim());
			driver.setFazhengjiguan(String.valueOf(a.get("fazhengjiguan")).trim());
			driver.setBeizhu(String.valueOf(a.get("beizhu")).trim());
			driver.setIsdelete(0);
			driver.setCreatetime(DateUtil.now());
			driver.setCaozuoshijian(DateUtil.now());
			if(user != null){
				driver.setCaozuoren(user.getUserName());
				driver.setCaozuorenid(user.getUserId());
			}
			//登录密码
			driver.setDenglumima(DigestUtil.encrypt("123456"));
			JiaShiYuan driverInfo = iJiaShiYuanService.getjiaShiYuanByOne(driver.getDeptId().toString(),driver.getJiashiyuanxingming(), driver.getShoujihaoma(),driver.getShenfenzhenghao(),driver.getJiashiyuanleixing());
			if(driverInfo != null){
				driver.setId(driverInfo.getId());
				isDataValidity = iJiaShiYuanService.updateSelective(driver);
			}else{
				String id= IdUtil.simpleUUID();
				driver.setId(id);
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
