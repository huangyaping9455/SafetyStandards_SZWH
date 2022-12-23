package org.springblade.anbiao.jiashiyuan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.guanlijigouherenyuan.service.IBladeDeptService;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.service.*;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanVO;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetailInfo;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailInfoService;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
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
	private IBladeDeptService deptService;
	private IAnbiaoRiskDetailService riskDetailService;
	private IAnbiaoRiskDetailInfoService detailInfoService;

	@PostMapping("/insert")
	@ApiLog("新增-驾驶员资料管理")
	@ApiOperation(value = "新增-驾驶员资料管理", notes = "传入jiaShiYuan", position = 1)
	public R insert(@RequestBody JiaShiYuan jiaShiYuan, BladeUser user) throws ParseException {
		R r = new R();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShoujihaoma, jiaShiYuan.getShoujihaoma());
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShenfenzhenghao, jiaShiYuan.getShenfenzhenghao());
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId, jiaShiYuan.getDeptId());
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
		JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);

		//验证身份证初领日期
		if (StringUtils.isNotBlank(jiaShiYuan.getShenfenzhengchulingriqi()) && !jiaShiYuan.getShenfenzhengchulingriqi().equals("null")) {
			if (jiaShiYuan.getShenfenzhengchulingriqi().length() >= 10) {
				String shenfenzhengchulingriqi = jiaShiYuan.getShenfenzhengchulingriqi().substring(0, 10);
				if (StringUtils.isNotBlank(shenfenzhengchulingriqi) && !shenfenzhengchulingriqi.equals("null")) {
					if (DateUtils.isDateString(shenfenzhengchulingriqi, null) == true) {
						jiaShiYuan.setShenfenzhengchulingriqi(shenfenzhengchulingriqi);
					} else {
						r.setMsg(jiaShiYuan.getShenfenzhengchulingriqi() + ",该身份证初领日期，不是时间格式；");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}
		}

		//验证身份证有效截止日期
		if (StringUtils.isNotBlank(jiaShiYuan.getShenfenzhengyouxiaoqi()) && !jiaShiYuan.getShenfenzhengyouxiaoqi().equals("null")) {
			if (jiaShiYuan.getShenfenzhengyouxiaoqi().length() >= 10) {
				String shenfenzhengyouxiaoqi = jiaShiYuan.getShenfenzhengyouxiaoqi().substring(0, 10);
				if (StringUtils.isNotBlank(shenfenzhengyouxiaoqi) && !shenfenzhengyouxiaoqi.equals("null")) {
					if (DateUtils.isDateString(shenfenzhengyouxiaoqi, null) == true) {
						jiaShiYuan.setShenfenzhengyouxiaoqi(shenfenzhengyouxiaoqi);
					} else {
						r.setMsg(jiaShiYuan.getShenfenzhengyouxiaoqi() + ",该身份证有效截止日期，不是时间格式；");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}
		}

		//验证 验证身份证初领日期 不能大于 身份证有效截止日期
		if (StringUtils.isNotBlank(jiaShiYuan.getShenfenzhengchulingriqi()) && !jiaShiYuan.getShenfenzhengchulingriqi().equals("null") && StringUtils.isNotBlank(jiaShiYuan.getShenfenzhengyouxiaoqi()) && !jiaShiYuan.getShenfenzhengyouxiaoqi().equals("null")) {
			int a1 = jiaShiYuan.getShenfenzhengchulingriqi().length();
			int b1 = jiaShiYuan.getShenfenzhengyouxiaoqi().length();
			if (a1 == b1) {
				if (a1 <= 10) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if (DateUtils.belongCalendar(format.parse(jiaShiYuan.getShenfenzhengchulingriqi()), format.parse(jiaShiYuan.getShenfenzhengyouxiaoqi())) == false) {
						r.setMsg("身份证初次发放日期,不能大于身份证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				if (a1 > 10) {
					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (DateUtils.belongCalendar(format2.parse(jiaShiYuan.getShenfenzhengchulingriqi()), format2.parse(jiaShiYuan.getShenfenzhengyouxiaoqi())) == false) {
						r.setMsg("身份证初次发放日期,不能大于身份证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			} else {
				r.setMsg("身份证初领日期与身份证有效截止日期,时间格式不一致;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证驾驶证初领日期
		if (StringUtils.isNotBlank(jiaShiYuan.getJiashizhengchulingriqi()) && !jiaShiYuan.getJiashizhengchulingriqi().equals("null")) {
			if (jiaShiYuan.getJiashizhengchulingriqi().length() >= 10) {
				String jiashizhengchulingriqi = jiaShiYuan.getJiashizhengchulingriqi().substring(0, 10);
				if (StringUtils.isNotBlank(jiashizhengchulingriqi) && !jiashizhengchulingriqi.equals("null")) {
					if (DateUtils.isDateString(jiashizhengchulingriqi, null) == true) {
						jiaShiYuan.setJiashizhengchulingriqi(jiashizhengchulingriqi);
					} else {
						r.setMsg(jiaShiYuan.getJiashizhengchulingriqi() + ",该驾驶证初领日期，不是时间格式；");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}
		}

		//验证驾驶证有效截止日期
		if (StringUtils.isNotBlank(jiaShiYuan.getJiashizhengyouxiaoqi()) && !jiaShiYuan.getJiashizhengyouxiaoqi().equals("null")) {
			if (jiaShiYuan.getJiashizhengyouxiaoqi().length() >= 10 || jiaShiYuan.getJiashizhengyouxiaoqi().equals("长期")) {
				if (!jiaShiYuan.getJiashizhengyouxiaoqi().equals("长期")) {
					String jiashizhengyouxiaoqi = jiaShiYuan.getJiashizhengyouxiaoqi().substring(0, 10);
					if (StringUtils.isNotBlank(jiashizhengyouxiaoqi) && !jiashizhengyouxiaoqi.equals("null")) {
						if (DateUtils.isDateString(jiashizhengyouxiaoqi, null) == true) {
							jiaShiYuan.setJiashizhengyouxiaoqi(jiashizhengyouxiaoqi);
						} else {
							r.setMsg(jiaShiYuan.getJiashizhengyouxiaoqi() + ",该驾驶证有效截止日期，不是时间格式；");
							r.setCode(500);
							r.setSuccess(false);
							return r;
						}
					}
				} else if (jiaShiYuan.getJiashizhengyouxiaoqi().equals("长期")) {
					jiaShiYuan.setJiashizhengyouxiaoqi(jiaShiYuan.getJiashizhengyouxiaoqi());
				}
			}
		}

		//验证 驾驶证初领日期 不能大于 驾驶证有效截止日期
		if (StringUtils.isNotBlank(jiaShiYuan.getJiashizhengchulingriqi()) && !jiaShiYuan.getJiashizhengchulingriqi().equals("null") && StringUtils.isNotBlank(jiaShiYuan.getJiashizhengyouxiaoqi()) && !jiaShiYuan.getJiashizhengyouxiaoqi().equals("null") && !jiaShiYuan.getJiashizhengyouxiaoqi().equals("长期")) {
			int a2 = jiaShiYuan.getJiashizhengchulingriqi().length();
			int b2 = jiaShiYuan.getJiashizhengyouxiaoqi().length();
			if (a2 == b2) {
				if (a2 <= 10) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if (DateUtils.belongCalendar(format.parse(jiaShiYuan.getJiashizhengchulingriqi()), format.parse(jiaShiYuan.getJiashizhengyouxiaoqi())) == false) {
						r.setMsg("驾驶证初次发放日期,不能大于驾驶证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				if (a2 > 10) {
					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (DateUtils.belongCalendar(format2.parse(jiaShiYuan.getJiashizhengchulingriqi()), format2.parse(jiaShiYuan.getJiashizhengyouxiaoqi()))) {
						r.setMsg("驾驶证初次发放日期,不能大于驾驶证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			} else {
				r.setMsg("驾驶证初领日期与驾驶证有效截止日期,时间格式不一致;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证从业资格证初领日期
		if (StringUtils.isNotBlank(jiaShiYuan.getCongyezhengchulingri()) && !jiaShiYuan.getCongyezhengchulingri().equals("null")) {
			if (jiaShiYuan.getCongyezhengchulingri().length() >= 10) {
				String congyezhengchulingriqi = jiaShiYuan.getCongyezhengchulingri().substring(0, 10);
				if (StringUtils.isNotBlank(congyezhengchulingriqi) && !congyezhengchulingriqi.equals("null")) {
					if (DateUtils.isDateString(congyezhengchulingriqi, null) == true) {
						jiaShiYuan.setCongyezhengchulingri(congyezhengchulingriqi);
					} else {
						r.setMsg(jiaShiYuan.getCongyezhengchulingri() + ",该从业资格证初领日期，不是时间格式；");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}
		}

		//验证从业资格证有效截止日期
		if (StringUtils.isNotBlank(jiaShiYuan.getCongyezhengyouxiaoqi()) && !jiaShiYuan.getCongyezhengyouxiaoqi().equals("null")) {
			if (jiaShiYuan.getCongyezhengyouxiaoqi().length() >= 10) {
				String congyezhengyouxiaoqi = jiaShiYuan.getCongyezhengyouxiaoqi().substring(0, 10);
				if (StringUtils.isNotBlank(congyezhengyouxiaoqi) && !congyezhengyouxiaoqi.equals("null")) {
					if (DateUtils.isDateString(congyezhengyouxiaoqi, null) == true) {
						jiaShiYuan.setCongyezhengyouxiaoqi(congyezhengyouxiaoqi);
					} else {
						r.setMsg(jiaShiYuan.getCongyezhengyouxiaoqi() + ",该从业资格证有效截止日期，不是时间格式；");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}
		}

		//验证 从业资格证初领日期 不能大于 从业资格证有效截止日期
		if (StringUtils.isNotBlank(jiaShiYuan.getCongyezhengchulingri()) && !jiaShiYuan.getCongyezhengchulingri().equals("null") && StringUtils.isNotBlank(jiaShiYuan.getCongyezhengyouxiaoqi()) && !jiaShiYuan.getCongyezhengyouxiaoqi().equals("null")) {
			int a3 = jiaShiYuan.getCongyezhengchulingri().length();
			int b3 = jiaShiYuan.getCongyezhengyouxiaoqi().length();
			if (a3 == b3) {
				if (a3 <= 10) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if (DateUtils.belongCalendar(format.parse(jiaShiYuan.getCongyezhengchulingri()), format.parse(jiaShiYuan.getCongyezhengyouxiaoqi())) == false) {
						r.setMsg("从业资格证初次发放日期,不能大于从业资格证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
				if (a3 > 10) {
					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (DateUtils.belongCalendar(format2.parse(jiaShiYuan.getCongyezhengchulingri()), format2.parse(jiaShiYuan.getCongyezhengyouxiaoqi())) == false) {
						r.setMsg("从业资格证初次发放日期,不能大于从业资格证有效截止日期;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
			}
		}

		//验证体检日期
		if (StringUtils.isNotBlank(jiaShiYuan.getTijianriqi()) && !jiaShiYuan.getTijianriqi().equals("null")) {
			if (jiaShiYuan.getTijianriqi().length() >= 10) {
				String tijianriqi = jiaShiYuan.getTijianriqi().substring(0, 10);
				if (StringUtils.isNotBlank(tijianriqi) && !tijianriqi.equals("null")) {
					if (DateUtils.isDateString(tijianriqi, null) == true) {
						int tijianyouxiaoqi = Integer.parseInt(tijianriqi.substring(0, 4))+ 1;
						String tijianyouxiaoqis =String.valueOf(tijianyouxiaoqi);
						String tijianyouxiaoqiss =tijianyouxiaoqis+tijianriqi.substring(4, 10);
						jiaShiYuan.setTijianriqi(tijianriqi);
						jiaShiYuan.setTijianyouxiaoqi(tijianyouxiaoqiss);
					} else {
						r.setMsg(jiaShiYuan.getTijianriqi() + ",该体检日期，不是时间格式；");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				}
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
				QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper1 = new QueryWrapper<JiaShiYuan>();
				jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getShoujihaoma,jiaShiYuan.getShoujihaoma());
				JiaShiYuan jiaShiYuan1 = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper1);
				if (jiaShiYuan1!=null){
				}else {
					jiaShiYuan.setShoujihaoma(jiaShiYuan.getShoujihaoma());
				}
			} else {
				r.setMsg("该手机号码不合法;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证驾驶证
		if (StringUtils.isNotBlank(jiaShiYuan.getJiashizhenghao()) && jiaShiYuan.getJiashizhenghao() != null) {
			if (IdCardUtil.isValidCard(jiaShiYuan.getJiashizhenghao()) == true) {
				jiaShiYuan.setJiashizhenghao(jiaShiYuan.getJiashizhenghao());
//				if (deail != null) {
//					r.setMsg(deail.getJiashizhenghao() + "该驾驶证号已存在;");
//					r.setCode(500);
//					r.setSuccess(false);
//					return r;
//				} else {
//					jiaShiYuan.setJiashizhenghao(jiaShiYuan.getJiashizhenghao());
//				}
			} else {
				r.setMsg(jiaShiYuan.getJiashizhenghao() + "该驾驶证号不合法;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		//验证身份证
		if(StringUtils.isNotEmpty(jiaShiYuan.getShenfenzhenghao()) && jiaShiYuan.getShenfenzhenghao() != null){
			if (IdCardUtil.isValidCard(jiaShiYuan.getShenfenzhenghao()) == true) {
				jiaShiYuan.setShenfenzhenghao(jiaShiYuan.getShenfenzhenghao());
//			if (deail != null) {
//				r.setMsg(deail.getShenfenzhenghao() + "该驾驶员身份证号已存在");
//				r.setCode(500);
//				r.setSuccess(false);
//				return r;
//			} else {
				jiaShiYuan.setShenfenzhenghao(jiaShiYuan.getShenfenzhenghao());
				//通过身份证获取年龄
				Integer age = IdCardUtil.getAgeByCard(jiaShiYuan.getShenfenzhenghao());
				jiaShiYuan.setNianling(age.toString());
				//通过身份证获取生日日期
				Date chushengshijian = IdCardUtil.getBirthDate(jiaShiYuan.getShenfenzhenghao());
				jiaShiYuan.setChushengshijian(dateFormat2.format(chushengshijian));
				jiaShiYuan.setXingbie(Integer.toString(IdCardUtil.getGender(jiaShiYuan.getShenfenzhenghao())));
//			}
			} else {
				r.setMsg(jiaShiYuan.getShenfenzhenghao() + "该驾驶员身份证号不合法");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}

		JiaShiYuan jiaShiYuan1=jiaShiYuan;
		//新增
		if (deail == null) {
			if (StringUtils.isBlank(jiaShiYuan.getJiashiyuanleixing())) {
				jiaShiYuan.setCongyerenyuanleixing("驾驶员");
			}
			jiaShiYuan.setCaozuoren(user.getUserName());
			jiaShiYuan.setCaozuorenid(user.getUserId());
			jiaShiYuan.setCreatetime(DateUtil.now());
			jiaShiYuan.setDenglumima(DigestUtil.encrypt(jiaShiYuan.getShoujihaoma().substring(jiaShiYuan.getShoujihaoma().length() - 6)));
			jiaShiYuan.setIsdelete(0);
			jiaShiYuan.setStatus(0);
			boolean i = iJiaShiYuanService.save(jiaShiYuan);
			if (i) {
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
				if (rzdeail == null) {
					if (StringUtils.isNotBlank(jiaShiYuan.getJialing()) && !jiaShiYuan.getJialing().equals("null")) {
						ruzhi.setAjrDrivingExperience(Integer.parseInt(jiaShiYuan.getJialing()));
					}
					ruzhi.setAjrCreateByName(jiaShiYuan.getCaozuoren());
					ruzhi.setAjrCreateByIds(jiaShiYuan.getCaozuorenid().toString());
					ruzhi.setAjrCreateTime(jiaShiYuan.getCaozuoshijian());
					ruzhi.setAjrDelete("0");
					ruzhi.setAjrAjIds(jiaShiYuan.getId());
					ruzhi.setAjrName(jiaShiYuan.getJiashiyuanxingming());
					ruzhi.setAjrSex(jiaShiYuan.getXingbie());
					if(StringUtils.isNotEmpty(jiaShiYuan.getNianling()) && jiaShiYuan.getNianling() != null){
						ruzhi.setAjrAge(Integer.valueOf(jiaShiYuan.getNianling()));
					}
					if(StringUtils.isNotEmpty(jiaShiYuan.getShenfenzhenghao()) && jiaShiYuan.getShenfenzhenghao() != null) {
						ruzhi.setAjrIdNumber(jiaShiYuan.getShenfenzhenghao());
					}
					ruzhi.setAjrApproverStatus("0");
					if(StringUtils.isNotEmpty(jiaShiYuan.getChushengshijian()) && jiaShiYuan.getChushengshijian() != null) {
						ruzhi.setAjrBirth(jiaShiYuan.getChushengshijian());
					}
					i = ruzhiService.save(ruzhi);
				}

				//向驾驶证信息表添加数据
				AnbiaoJiashiyuanJiashizheng jiashizheng = new AnbiaoJiashiyuanJiashizheng();
				QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, jiaShiYuan.getId());
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
				AnbiaoJiashiyuanJiashizheng jszdeail = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
				if (jszdeail == null) {
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
				if (cyzdeail == null) {
					congyezigezheng.setAjcAjIds(jiaShiYuan.getId());
					congyezigezheng.setAjcCertificateNo(jiaShiYuan.getCongyezigezheng());
					congyezigezheng.setAjcInitialIssuing(jiaShiYuan.getCongyezhengchulingri());
					congyezigezheng.setAjcValidUntil(jiaShiYuan.getCongyezhengyouxiaoqi());
					congyezigezheng.setAjcLicence(jiaShiYuan.getCongyezhengfujian());
					congyezigezheng.setAjcStatus("0");
					congyezigezheng.setAjcCreateTime(DateUtil.now());
					congyezigezheng.setAjcDelete("0");
					i = congyezigezhengService.save(congyezigezheng);
				}

				//向体检信息表添加数据
				AnbiaoJiashiyuanTijian tijian = new AnbiaoJiashiyuanTijian();
				QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
				tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, jiaShiYuan.getId());
				tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
				AnbiaoJiashiyuanTijian tjdeail = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
				if (tjdeail == null) {
					tijian.setAjtCreateByName(jiaShiYuan.getCaozuoren());
					tijian.setAjtCreateByIds(jiaShiYuan.getCaozuorenid().toString());
					tijian.setAjtCreateTime(jiaShiYuan.getCaozuoshijian());
					tijian.setAjtDelete("0");
					tijian.setAjtAjIds(jiaShiYuan.getId());
					i = tijianService.save(tijian);
				}

				//向岗前培训信息表添加数据
				AnbiaoJiashiyuanGangqianpeixun gangqianpeixun = new AnbiaoJiashiyuanGangqianpeixun();
				QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
				gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, jiaShiYuan.getId());
				gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
				AnbiaoJiashiyuanGangqianpeixun gqpxdeail = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
				if (gqpxdeail == null) {
					gangqianpeixun.setAjgCreateByName(jiaShiYuan.getCaozuoren());
					gangqianpeixun.setAjgCreateByIds(jiaShiYuan.getCaozuorenid().toString());
					gangqianpeixun.setAjgCreateTime(jiaShiYuan.getCaozuoshijian());
					gangqianpeixun.setAjgDelete("0");
					gangqianpeixun.setAjgAjIds(jiaShiYuan.getId());
					i = gangqianpeixunService.save(gangqianpeixun);
				}

				//向三年无重大责任事故证明信息表添加数据
				AnbiaoJiashiyuanWuzezhengming wuzezhengming = new AnbiaoJiashiyuanWuzezhengming();
				QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
				wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, jiaShiYuan.getId());
				wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
				AnbiaoJiashiyuanWuzezhengming wzzmdeail = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
				if (wzzmdeail == null) {
					wuzezhengming.setAjwCreateByName(jiaShiYuan.getCaozuoren());
					wuzezhengming.setAjwCreateByIds(jiaShiYuan.getCaozuorenid().toString());
					wuzezhengming.setAjwCreateTime(DateUtil.now());
					wuzezhengming.setAjwDelete("0");
					wuzezhengming.setAjwAjIds(jiaShiYuan.getId());
					i = wuzezhengmingService.save(wuzezhengming);
				}

				//向驾驶员安全责任书信息表添加数据
				AnbiaoJiashiyuanAnquanzerenshu anquanzerenshu = new AnbiaoJiashiyuanAnquanzerenshu();
				QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu>();
				anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaAjIds, jiaShiYuan.getId());
				anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaDelete, "0");
				AnbiaoJiashiyuanAnquanzerenshu aqzesdeail = anquanzerenshuService.getBaseMapper().selectOne(anquanzerenshuQueryWrapper);
				if (aqzesdeail == null) {
					anquanzerenshu.setAjaCreateByName(jiaShiYuan.getCaozuoren());
					anquanzerenshu.setAjaCreateByIds(jiaShiYuan.getCaozuorenid().toString());
					anquanzerenshu.setAjaCreateTime(DateUtil.now());
					anquanzerenshu.setAjaDelete("0");
					anquanzerenshu.setAjaAjIds(jiaShiYuan.getId());
					i = anquanzerenshuService.save(anquanzerenshu);
				}

				//向驾驶员职业危害告知书信息表添加数据
				AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishu = new AnbiaoJiashiyuanWeihaigaozhishu();
				QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
				weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwAjIds, jiaShiYuan.getId());
				weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwDelete, "0");
				AnbiaoJiashiyuanWeihaigaozhishu whgzsdeail = weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);
				if (whgzsdeail == null) {
					weihaigaozhishu.setAjwCreateByName(jiaShiYuan.getCaozuoren());
					weihaigaozhishu.setAjwCreateByIds(jiaShiYuan.getCaozuorenid().toString());
					weihaigaozhishu.setAjwCreateTime(DateUtil.now());
					weihaigaozhishu.setAjwDelete("0");
					weihaigaozhishu.setAjwAjIds(jiaShiYuan.getId());
					i = weihaigaozhishuService.save(weihaigaozhishu);
				}

				//向劳动合同信息表添加数据
				AnbiaoJiashiyuanLaodonghetong laodonghetong = new AnbiaoJiashiyuanLaodonghetong();
				QueryWrapper<AnbiaoJiashiyuanLaodonghetong> laodonghetongQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanLaodonghetong>();
				laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwAjIds, jiaShiYuan.getId());
				laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwDelete, "0");
				AnbiaoJiashiyuanLaodonghetong ldhtdeail = laodonghetongService.getBaseMapper().selectOne(laodonghetongQueryWrapper);
				if (ldhtdeail == null) {
					laodonghetong.setAjwCreateByName(jiaShiYuan.getCaozuoren());
					laodonghetong.setAjwCreateByIds(jiaShiYuan.getCaozuorenid().toString());
					laodonghetong.setAjwCreateTime(DateUtil.now());
					laodonghetong.setAjwDelete("0");
					laodonghetong.setAjwAjIds(jiaShiYuan.getId());
					i = laodonghetongService.save(laodonghetong);
				}

				//向其他信息表添加数据
				AnbiaoJiashiyuanQita qita = new AnbiaoJiashiyuanQita();
				QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanQita>();
				qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, jiaShiYuan.getId());
				qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
				AnbiaoJiashiyuanQita qtdeail = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);
				if (qtdeail == null) {
					qita.setAjtCreateByName(jiaShiYuan.getCaozuoren());
					qita.setAjtCreateByIds(jiaShiYuan.getCaozuorenid().toString());
					qita.setAjtCreateTime(DateUtil.now());
					qita.setAjtDelete("0");
					qita.setAjtAjIds(jiaShiYuan.getId());
					i = qitaService.save(qita);
				}

			}
			if (i) {
				r.setMsg("添加成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			} else {
				r.setMsg("添加失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		} else {
			jiaShiYuan.setId(deail.getId());

			//身份证有效期风险
			AnbiaoRiskDetailInfo anbiaoRiskDetailInfo = new AnbiaoRiskDetailInfo();
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, jiaShiYuan.getDeptId());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, jiaShiYuan.getId());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle, "身份证有效截止日期");
			List<AnbiaoRiskDetail> anbiaoRiskDetails = riskDetailService.getBaseMapper().selectList(riskDetailQueryWrapper);
			for (AnbiaoRiskDetail riskDetail :
				anbiaoRiskDetails) {
				if (riskDetail != null && StringUtils.isNotBlank(jiaShiYuan1.getShenfenzhengyouxiaoqi()) && !jiaShiYuan1.getShenfenzhengyouxiaoqi().equals("null")) {
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationByIds(user.getUserId().toString());
					riskDetail.setArdRectificationByName(user.getUserName());
					riskDetail.setArdRectificationDate(DateUtil.now());
					riskDetail.setArdModularName("身份证有效截止日期");
					riskDetail.setArdRectificationField("shenfenzhengyouxiaoqi");
					riskDetail.setArdRectificationValue(jiaShiYuan1.getShenfenzhengyouxiaoqi());
					riskDetail.setArdRectificationFieldType("String");
					riskDetail.setArdRectificationEnclosure(jiaShiYuan1.getShenfenzhengfujian());
					boolean b = riskDetailService.updateById(riskDetail);
					if (b == true) {
						//整改内容
						anbiaoRiskDetailInfo.setArdRiskIds(riskDetail.getArdIds().toString());
						anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
						anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
						anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
						anbiaoRiskDetailInfo.setArdRectificationField("shenfenzhengyouxiaoqi");
						anbiaoRiskDetailInfo.setArdRectificationValue(jiaShiYuan1.getShenfenzhengyouxiaoqi());
						anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
						detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					}
				}
			}

			//驾驶证有效期风险
			AnbiaoRiskDetailInfo anbiaoRiskDetailInfo2 = new AnbiaoRiskDetailInfo();
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper2 = new QueryWrapper<>();
			riskDetailQueryWrapper2.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, jiaShiYuan.getDeptId());
			riskDetailQueryWrapper2.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, jiaShiYuan.getId());
			riskDetailQueryWrapper2.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
			riskDetailQueryWrapper2.lambda().eq(AnbiaoRiskDetail::getArdTitle, "驾驶证有效截止日期");
			List<AnbiaoRiskDetail> anbiaoRiskDetails2 = riskDetailService.getBaseMapper().selectList(riskDetailQueryWrapper2);
			for (AnbiaoRiskDetail riskDetail2 : anbiaoRiskDetails2) {
				if (riskDetail2 != null && StringUtils.isNotBlank(jiaShiYuan1.getJiashizhengyouxiaoqi()) && !jiaShiYuan1.getJiashizhengyouxiaoqi().equals("null")) {
					riskDetail2.setArdIsRectification("1");
					riskDetail2.setArdRectificationByIds(user.getUserId().toString());
					riskDetail2.setArdRectificationByName(user.getUserName());
					riskDetail2.setArdRectificationDate(DateUtil.now());
					riskDetail2.setArdModularName("驾驶证有效截止日期");
					riskDetail2.setArdRectificationField("jiashizhengyouxiaoqi");
					riskDetail2.setArdRectificationValue(jiaShiYuan1.getJiashizhengyouxiaoqi());
					riskDetail2.setArdRectificationFieldType("String");
					riskDetail2.setArdRectificationEnclosure(jiaShiYuan1.getJiashizhengfujian());
					boolean b = riskDetailService.updateById(riskDetail2);
					if (b == true) {
						//整改内容
						anbiaoRiskDetailInfo2.setArdRiskIds(riskDetail2.getArdIds().toString());
						anbiaoRiskDetailInfo2.setArdRectificationByIds(user.getUserId().toString());
						anbiaoRiskDetailInfo2.setArdRectificationByName(user.getUserName());
						anbiaoRiskDetailInfo2.setArdRectificationDate(DateUtil.now());
						anbiaoRiskDetailInfo2.setArdRectificationField("jiashizhengyouxiaoqi");
						anbiaoRiskDetailInfo2.setArdRectificationValue(jiaShiYuan1.getJiashizhengyouxiaoqi());
						anbiaoRiskDetailInfo2.setArdRectificationFieldType("String");
						detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo2);
					}
				}
			}

			//从业资格证有效期风险
			AnbiaoRiskDetailInfo anbiaoRiskDetailInfo3 = new AnbiaoRiskDetailInfo();
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper3 = new QueryWrapper<>();
			riskDetailQueryWrapper3.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, jiaShiYuan.getDeptId());
			riskDetailQueryWrapper3.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, jiaShiYuan.getId());
			riskDetailQueryWrapper3.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
			riskDetailQueryWrapper3.lambda().eq(AnbiaoRiskDetail::getArdTitle, "从业资格证有效截止日期");
			List<AnbiaoRiskDetail> anbiaoRiskDetails3 = riskDetailService.getBaseMapper().selectList(riskDetailQueryWrapper3);
			for (AnbiaoRiskDetail riskDetail3 :
				anbiaoRiskDetails3) {
				if (riskDetail3 != null && StringUtils.isNotBlank(jiaShiYuan1.getCongyezhengyouxiaoqi()) && !jiaShiYuan1.getCongyezhengyouxiaoqi().equals("null")) {
					riskDetail3.setArdIsRectification("1");
					riskDetail3.setArdRectificationByIds(user.getUserId().toString());
					riskDetail3.setArdRectificationByName(user.getUserName());
					riskDetail3.setArdRectificationDate(DateUtil.now());
					riskDetail3.setArdModularName("从业资格证有效截止日期");
					riskDetail3.setArdRectificationField("congyezhengyouxiaoqi");
					riskDetail3.setArdRectificationValue(jiaShiYuan1.getCongyezhengyouxiaoqi());
					riskDetail3.setArdRectificationFieldType("String");
					riskDetail3.setArdRectificationEnclosure(jiaShiYuan1.getCongyezhengfujian());
					boolean b = riskDetailService.updateById(riskDetail3);
					if (b == true) {
						//整改内容
						anbiaoRiskDetailInfo3.setArdRiskIds(riskDetail3.getArdIds().toString());
						anbiaoRiskDetailInfo3.setArdRectificationByIds(user.getUserId().toString());
						anbiaoRiskDetailInfo3.setArdRectificationByName(user.getUserName());
						anbiaoRiskDetailInfo3.setArdRectificationDate(DateUtil.now());
						anbiaoRiskDetailInfo3.setArdRectificationField("congyezhengyouxiaoqi");
						anbiaoRiskDetailInfo3.setArdRectificationValue(jiaShiYuan1.getCongyezhengyouxiaoqi());
						anbiaoRiskDetailInfo3.setArdRectificationFieldType("String");
						detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo3);
					}
				}
			}

			//体检有效期风险
			AnbiaoRiskDetailInfo anbiaoRiskDetailInfo4 = new AnbiaoRiskDetailInfo();
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper4 = new QueryWrapper<>();
			riskDetailQueryWrapper4.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, jiaShiYuan.getDeptId());
			riskDetailQueryWrapper4.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, jiaShiYuan.getId());
			riskDetailQueryWrapper4.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
			riskDetailQueryWrapper4.lambda().eq(AnbiaoRiskDetail::getArdTitle, "体检有效截止日期");
			List<AnbiaoRiskDetail> anbiaoRiskDetails4 = riskDetailService.getBaseMapper().selectList(riskDetailQueryWrapper4);
			for (AnbiaoRiskDetail riskDetail4 : anbiaoRiskDetails4) {
				if (riskDetail4 != null && StringUtils.isNotBlank(jiaShiYuan1.getTijianyouxiaoqi()) && !jiaShiYuan1.getTijianyouxiaoqi().equals("null")) {
					riskDetail4.setArdIsRectification("1");
					riskDetail4.setArdRectificationByIds(user.getUserId().toString());
					riskDetail4.setArdRectificationByName(user.getUserName());
					riskDetail4.setArdRectificationDate(DateUtil.now());
					riskDetail4.setArdModularName("体检有效截止日期");
					riskDetail4.setArdRectificationField("tijianyouxiaoqi");
					riskDetail4.setArdRectificationValue(jiaShiYuan1.getTijianyouxiaoqi());
					riskDetail4.setArdRectificationFieldType("String");
					boolean b = riskDetailService.updateById(riskDetail4);
					if (b == true) {
						//整改内容
						anbiaoRiskDetailInfo4.setArdRiskIds(riskDetail4.getArdIds().toString());
						anbiaoRiskDetailInfo4.setArdRectificationByIds(user.getUserId().toString());
						anbiaoRiskDetailInfo4.setArdRectificationByName(user.getUserName());
						anbiaoRiskDetailInfo4.setArdRectificationDate(DateUtil.now());
						anbiaoRiskDetailInfo4.setArdRectificationField("tijianyouxiaoqi");
						anbiaoRiskDetailInfo4.setArdRectificationValue(jiaShiYuan1.getTijianyouxiaoqi());
						anbiaoRiskDetailInfo4.setArdRectificationFieldType("String");
						detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo4);
					}
				}
			}

			//验证身份证
			if(StringUtils.isNotEmpty(jiaShiYuan.getShenfenzhenghao()) && jiaShiYuan.getShenfenzhenghao() != null){
				if (IdCardUtil.isValidCard(jiaShiYuan.getShenfenzhenghao()) == true) {
					jiaShiYuan.setShenfenzhenghao(jiaShiYuan.getShenfenzhenghao());
					jiaShiYuan.setShenfenzhenghao(jiaShiYuan.getShenfenzhenghao());
					//通过身份证获取年龄
					Integer age = IdCardUtil.getAgeByCard(jiaShiYuan.getShenfenzhenghao());
					jiaShiYuan.setNianling(age.toString());
					//通过身份证获取生日日期
					Date chushengshijian = IdCardUtil.getBirthDate(jiaShiYuan.getShenfenzhenghao());
					jiaShiYuan.setChushengshijian(dateFormat2.format(chushengshijian));
					jiaShiYuan.setXingbie(Integer.toString(IdCardUtil.getGender(jiaShiYuan.getShenfenzhenghao())));
				} else {
					r.setMsg(jiaShiYuan.getShenfenzhenghao() + "该驾驶员身份证号不合法");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}
			boolean i = iJiaShiYuanService.updateById(jiaShiYuan);
			if (i) {
				r.setMsg("更新成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			} else {
				r.setMsg("更新失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}
	}


	/**
	 * 初始化密码
	 */
	@PostMapping("/initialize")
	@ApiLog("初始化-驾驶员密码")
	@ApiOperation(value = "初始化-驾驶员密码", notes = "传入id")
	public R initialize(String id, BladeUser user) {
		R r = new R();
		JiaShiYuan detal = iJiaShiYuanService.selectByIds(id);
		if (detal != null) {
			detal.setDenglumima(DigestUtil.encrypt(detal.getShoujihaoma().substring(detal.getShoujihaoma().length() - 6)));
			return R.status(iJiaShiYuanService.updateById(detal));
		} else {
			r.setMsg("数据为空，不能初始化密码");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
	}

	/**
	 * 密码修改
	 */
	@PostMapping("/update-password")
	@ApiLog("密码修改")
	@ApiOperation(value = "密码修改", notes = "传入userId与新旧密码值")
	public R updatePassword(BladeUser bladeUser, String id, String passWord, String oldpassWord) {
		R r = new R();
		JiaShiYuan detal = iJiaShiYuanService.selectByIds(id);
		if (StringUtils.isBlank(oldpassWord) || StringUtils.isBlank(passWord)) {
			r.setMsg("密码不能为空");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		oldpassWord = DigestUtil.encrypt(oldpassWord);
		if (!(detal.getDenglumima().equals(oldpassWord))) {
			r.setMsg("原密码不正确");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		} else {
			passWord = DigestUtil.encrypt(passWord);
			boolean temp = jiaShiYuanService.updatePassWord(passWord, id);
			if (temp == true) {
				r.setMsg("修改成功");
				r.setCode(200);
				r.setSuccess(true);
				return r;
			} else {
				r.setMsg("修改失败");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			}
		}
	}

	/**
	 * 删除
	 */
	@GetMapping("/del")
	@ApiLog("删除-驾驶员资料管理")
	@ApiOperation(value = "删除-驾驶员资料管理", notes = "传入id", position = 3)
	public R del(String id, BladeUser user) {
		R r = new R();
		boolean i = false;
		JiaShiYuan detal = iJiaShiYuanService.selectByIds(id);
		if (detal == null) {
			r.setMsg("该数据不存在");
			r.setCode(500);
			r.setSuccess(false);
			return r;
		}
		///入职登记表///
		QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
		ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, detal.getId());
		ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
		AnbiaoJiashiyuanRuzhi ruzhideail = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
		if (ruzhideail != null) {
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
		if (jiashizhengdeail != null) {
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
		if (deail != null) {
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
		if (tijiandeail != null) {
			AnbiaoJiashiyuanTijian tijian = new AnbiaoJiashiyuanTijian();
			tijian.setAjtUpdateByIds(user.getUserId().toString());
			tijian.setAjtUpdateByName(user.getUserName());
			tijian.setAjtUpdateTime(DateUtil.now());
			tijian.setAjtDelete("1");
			tijian.setAjtIds(tijiandeail.getAjtIds());
			i = tijianService.updateById(tijian);
		}

		///岗前培训///
		QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
		gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, detal.getId());
		gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
		AnbiaoJiashiyuanGangqianpeixun gangqianpeixundeail = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
		if (gangqianpeixundeail != null) {
			AnbiaoJiashiyuanGangqianpeixun gangqianpeixun = new AnbiaoJiashiyuanGangqianpeixun();
			gangqianpeixun.setAjgUpdateByIds(user.getUserId().toString());
			gangqianpeixun.setAjgUpdateByName(user.getUserName());
			gangqianpeixun.setAjgUpdateTime(DateUtil.now());
			gangqianpeixun.setAjgDelete("1");
			gangqianpeixun.setAjgIds(gangqianpeixundeail.getAjgIds());
			i = gangqianpeixunService.updateById(gangqianpeixun);
		}

		///无责证明表///
		QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
		wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, detal.getId());
		wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
		AnbiaoJiashiyuanWuzezhengming wuzezhengmingdeail = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
		if (wuzezhengmingdeail != null) {
			AnbiaoJiashiyuanWuzezhengming wuzezhengming = new AnbiaoJiashiyuanWuzezhengming();
			wuzezhengming.setAjwUpdateByIds(user.getUserId().toString());
			wuzezhengming.setAjwUpdateByName(user.getUserName());
			wuzezhengming.setAjwUpdateTime(DateUtil.now());
			wuzezhengming.setAjwDelete("1");
			wuzezhengming.setAjwIds(wuzezhengmingdeail.getAjwIds());
			i = wuzezhengmingService.updateById(wuzezhengming);
		}

		///驾驶员安全责任书///
		QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu>();
		anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaAjIds, detal.getId());
		anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaDelete, "0");
		AnbiaoJiashiyuanAnquanzerenshu anquanzerenshudeail = anquanzerenshuService.getBaseMapper().selectOne(anquanzerenshuQueryWrapper);
		if (anquanzerenshudeail != null) {
			AnbiaoJiashiyuanAnquanzerenshu anquanzerenshu = new AnbiaoJiashiyuanAnquanzerenshu();
			anquanzerenshu.setAjaUpdateByIds(user.getUserId().toString());
			anquanzerenshu.setAjaUpdateByName(user.getUserName());
			anquanzerenshu.setAjaUpdateTime(DateUtil.now());
			anquanzerenshu.setAjaDelete("1");
			anquanzerenshu.setAjaIds(anquanzerenshudeail.getAjaIds());
			i = anquanzerenshuService.updateById(anquanzerenshu);
		}

		///驾驶员职业危害告知书///
		QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
		weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwAjIds, detal.getId());
		weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwDelete, "0");
		AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishudeail = weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);
		if (weihaigaozhishudeail != null) {
			AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishu = new AnbiaoJiashiyuanWeihaigaozhishu();
			weihaigaozhishu.setAjwUpdateByIds(user.getUserId().toString());
			weihaigaozhishu.setAjwUpdateByName(user.getUserName());
			weihaigaozhishu.setAjwUpdateTime(DateUtil.now());
			weihaigaozhishu.setAjwDelete("1");
			weihaigaozhishu.setAjwIds(weihaigaozhishudeail.getAjwIds());
			i = weihaigaozhishuService.updateById(weihaigaozhishu);
		}

		///劳动合同///
		QueryWrapper<AnbiaoJiashiyuanLaodonghetong> laodonghetongQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanLaodonghetong>();
		laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwAjIds, detal.getId());
		laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwDelete, "0");
		AnbiaoJiashiyuanLaodonghetong laodonghetongdeail = laodonghetongService.getBaseMapper().selectOne(laodonghetongQueryWrapper);
		if (laodonghetongdeail != null) {
			AnbiaoJiashiyuanLaodonghetong laodonghetong = new AnbiaoJiashiyuanLaodonghetong();
			laodonghetong.setAjwUpdateByIds(user.getUserId().toString());
			laodonghetong.setAjwUpdateByName(user.getUserName());
			laodonghetong.setAjwUpdateTime(DateUtil.now());
			laodonghetong.setAjwDelete("1");
			laodonghetong.setAjwIds(laodonghetongdeail.getAjwIds());
			i = laodonghetongService.updateById(laodonghetong);
		}

		///其他///
		QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanQita>();
		qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, detal.getId());
		qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
		AnbiaoJiashiyuanQita qitadeail = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);
		if (qitadeail != null) {
			AnbiaoJiashiyuanQita qita = new AnbiaoJiashiyuanQita();
			qita.setAjtUpdateByIds(user.getUserId().toString());
			qita.setAjtUpdateByName(user.getUserName());
			qita.setAjtUpdateTime(DateUtil.now());
			qita.setAjtDelete("1");
			qita.setAjtIds(qitadeail.getAjtIds());
			i = qitaService.updateById(qita);
		}

		///驾驶员信息主表///
		JiaShiYuan jiaShiYuan = new JiaShiYuan();
		jiaShiYuan.setCaozuorenid(user.getUserId());
		jiaShiYuan.setCaozuoren(user.getUserName());
		jiaShiYuan.setCaozuoshijian(DateUtil.now());
		jiaShiYuan.setIsdelete(1);
		jiaShiYuan.setId(id);
		i = iJiaShiYuanService.updateById(jiaShiYuan);
		if (i) {
			r.setCode(200);
			r.setSuccess(true);
			r.setMsg("删除成功");
		} else {
			r.setCode(500);
			r.setSuccess(false);
			r.setMsg("删除失败");
		}
		return r;
	}

	/**
	 * 查询详情
	 */
	@GetMapping("/detail")
	@ApiLog("详情-驾驶员资料管理")
	@ApiOperation(value = "详情-驾驶员资料管理", notes = "传入id、type", position = 4)
	@ApiImplicitParams({@ApiImplicitParam(name = "id", value = "驾驶员ID", required = true),
		@ApiImplicitParam(name = "type", value = "分类（1：入职登记表，2：身份证，3：驾驶证，4:从业资格证,5:体检表,6:岗前培训三级教育卡," +
			"7:三年无重大责任事故正面,8:驾驶员安全责任书,9:驾驶员职业危害告知书,10:劳动合同,11:其他,）", required = true),
	})
	public R detail(String id, int type) {
		R r = new R();
		JiaShiYuan detal = iJiaShiYuanService.selectByIds(id);
		if (detal != null) {
			///入职登记表///
			if (type == 1) {
				QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
				ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, detal.getId());
				ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
				AnbiaoJiashiyuanRuzhi ruzhiInfo = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
				if (ruzhiInfo != null) {
					//本人照片(人员头像)
					if (StrUtil.isNotEmpty(ruzhiInfo.getAjrHeadPortrait()) && ruzhiInfo.getAjrHeadPortrait().contains("http") == false) {
						ruzhiInfo.setAjrHeadPortrait(fileUploadClient.getUrl(ruzhiInfo.getAjrHeadPortrait()));
					}
					ruzhiInfo.setAjrAjIds(detal.getId());
					r.setData(ruzhiInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}

			}

			///身份证///
			if (type == 2) {
				JiaShiYuan shenfenzhengInfo = iJiaShiYuanService.selectByIds(id);
				if (shenfenzhengInfo != null) {
					//照片(人员头像)
//					if(StrUtil.isNotEmpty(detal.getZhaopian()) && detal.getZhaopian().contains("http") == false){
//						detal.setZhaopian(fileUploadClient.getUrl(detal.getZhaopian()));
//					}
					//身份证附件
					if (StrUtil.isNotEmpty(shenfenzhengInfo.getShenfenzhengfujian()) && shenfenzhengInfo.getShenfenzhengfujian().contains("http") == false) {
						shenfenzhengInfo.setShenfenzhengfujian(fileUploadClient.getUrl(shenfenzhengInfo.getShenfenzhengfujian()));
					}
					//身份证附件反面
					if (StrUtil.isNotEmpty(shenfenzhengInfo.getShenfenzhengfanmianfujian()) && shenfenzhengInfo.getShenfenzhengfanmianfujian().contains("http") == false) {
						shenfenzhengInfo.setShenfenzhengfanmianfujian(fileUploadClient.getUrl(shenfenzhengInfo.getShenfenzhengfanmianfujian()));
					}

					r.setData(shenfenzhengInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///驾驶证///
			if (type == 3) {
				QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, detal.getId());
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
				AnbiaoJiashiyuanJiashizheng jiashizhengInfo = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
				if (jiashizhengInfo != null) {
					//驾驶证正面照片
					if (StrUtil.isNotEmpty(jiashizhengInfo.getAjjFrontPhotoAddress()) && jiashizhengInfo.getAjjFrontPhotoAddress().contains("http") == false) {
						jiashizhengInfo.setAjjFrontPhotoAddress(fileUploadClient.getUrl(jiashizhengInfo.getAjjFrontPhotoAddress()));
					}
					//驾驶证反面照片
					if (StrUtil.isNotEmpty(jiashizhengInfo.getAjjAttachedPhotos()) && jiashizhengInfo.getAjjAttachedPhotos().contains("http") == false) {
						jiashizhengInfo.setAjjAttachedPhotos(fileUploadClient.getUrl(jiashizhengInfo.getAjjAttachedPhotos()));
					}
					r.setData(jiashizhengInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///从业资格证///
			if (type == 4) {
				QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
				congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, detal.getId());
				congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
				AnbiaoJiashiyuanCongyezigezheng congyezigezhengInfo = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
				if (congyezigezhengInfo != null) {
					//从业资格证照片
					if (StrUtil.isNotEmpty(congyezigezhengInfo.getAjcLicence()) && congyezigezhengInfo.getAjcLicence().contains("http") == false) {
						congyezigezhengInfo.setAjcLicence(fileUploadClient.getUrl(congyezigezhengInfo.getAjcLicence()));
					}
					congyezigezhengInfo.setAjcAjIds(detal.getId());
					r.setData(congyezigezhengInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///体检表///
			if (type == 5) {
				QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
				tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, detal.getId());
				tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
				AnbiaoJiashiyuanTijian tijianInfo = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
				if (tijianInfo != null) {
					//体检附件
					if (StrUtil.isNotEmpty(tijianInfo.getAjtEnclosure()) && tijianInfo.getAjtEnclosure().contains("http") == false) {
						tijianInfo.setAjtEnclosure(fileUploadClient.getUrl(tijianInfo.getAjtEnclosure()));
					}
					tijianInfo.setAjtAjIds(detal.getId());
					r.setData(tijianInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///岗前培训三级教育卡///
			if (type == 6) {
				QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
				gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, detal.getId());
				gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
				AnbiaoJiashiyuanGangqianpeixun gangqianpeixunInfo = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
				if (gangqianpeixunInfo != null) {
					//培训附件
					if (StrUtil.isNotEmpty(gangqianpeixunInfo.getAjgTrainingEnclosure()) && gangqianpeixunInfo.getAjgTrainingEnclosure().contains("http") == false) {
						gangqianpeixunInfo.setAjgTrainingEnclosure(fileUploadClient.getUrl(gangqianpeixunInfo.getAjgTrainingEnclosure()));
					}
					gangqianpeixunInfo.setAjgAjIds(detal.getId());
					r.setData(gangqianpeixunInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///三年无重大责任事故正面///
			if (type == 7) {
				QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
				wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, detal.getId());
				wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
				AnbiaoJiashiyuanWuzezhengming wuzezhengmingInfo = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
				if (wuzezhengmingInfo != null) {
					//无责证明附件
					if (StrUtil.isNotEmpty(wuzezhengmingInfo.getAjwEnclosure()) && wuzezhengmingInfo.getAjwEnclosure().contains("http") == false) {
						wuzezhengmingInfo.setAjwEnclosure(fileUploadClient.getUrl(wuzezhengmingInfo.getAjwEnclosure()));
					}
					wuzezhengmingInfo.setAjwAjIds(detal.getId());
					r.setData(wuzezhengmingInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
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
					anquanzerenshuInfo.setAjaAjIds(detal.getId());
					anquanzerenshuInfo.setDeptName(detal.getDeptName());
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
			if (type == 9) {
				QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
				weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwAjIds, detal.getId());
				weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwDelete, "0");
				AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishuInfo = weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);

				if (weihaigaozhishuInfo != null) {
					//无责证明附件
					if (StrUtil.isNotEmpty(weihaigaozhishuInfo.getAjwEnclosure()) && weihaigaozhishuInfo.getAjwEnclosure().contains("http") == false) {
						weihaigaozhishuInfo.setAjwEnclosure(fileUploadClient.getUrl(weihaigaozhishuInfo.getAjwEnclosure()));
					}
					//无责证明签名附件
					if (StrUtil.isNotEmpty(weihaigaozhishuInfo.getAjwAutographEnclosure()) && weihaigaozhishuInfo.getAjwAutographEnclosure().contains("http") == false) {
						weihaigaozhishuInfo.setAjwAutographEnclosure(fileUploadClient.getUrl(weihaigaozhishuInfo.getAjwAutographEnclosure()));
					}
					weihaigaozhishuInfo.setAjwAjIds(detal.getId());
					r.setData(weihaigaozhishuInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///劳动合同///
			if (type == 10) {
				QueryWrapper<AnbiaoJiashiyuanLaodonghetong> laodonghetongQueryWrapper = new QueryWrapper<>();
				laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwAjIds, detal.getId());
				laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwDelete, "0");
				AnbiaoJiashiyuanLaodonghetong laodonghetongInfo = laodonghetongService.getBaseMapper().selectOne(laodonghetongQueryWrapper);

				if (laodonghetongInfo != null) {
					//劳动合同附件
					if (StrUtil.isNotEmpty(laodonghetongInfo.getAjwEnclosure()) && laodonghetongInfo.getAjwEnclosure().contains("http") == false) {
						laodonghetongInfo.setAjwEnclosure(fileUploadClient.getUrl(laodonghetongInfo.getAjwEnclosure()));
					}
					//劳动合同签名附件
					if (StrUtil.isNotEmpty(laodonghetongInfo.getAjwAutographEnclosure()) && laodonghetongInfo.getAjwAutographEnclosure().contains("http") == false) {
						laodonghetongInfo.setAjwAutographEnclosure(fileUploadClient.getUrl(laodonghetongInfo.getAjwAutographEnclosure()));
					}
					laodonghetongInfo.setAjwAjIds(detal.getId());
					laodonghetongInfo.setDeptName(detal.getDeptName());
					QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
					ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, detal.getId());
					ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
					AnbiaoJiashiyuanRuzhi ruzhiInfo = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
					if (ruzhiInfo != null) {
						laodonghetongInfo.setDriverAddress(ruzhiInfo.getAjrAddress());
					}
					laodonghetongInfo.setDriverNo(detal.getShenfenzhenghao());
					laodonghetongInfo.setDriverPhone(detal.getShoujihaoma());

					r.setData(laodonghetongInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

			///其他///
			if (type == 11) {
				QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<>();
				qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, detal.getId());
				qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
				AnbiaoJiashiyuanQita qitaInfo = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);

				if (qitaInfo != null) {
					//劳动合同附件
					if (StrUtil.isNotEmpty(qitaInfo.getAjtEnclosure()) && qitaInfo.getAjtEnclosure().contains("http") == false) {
						qitaInfo.setAjtEnclosure(fileUploadClient.getUrl(qitaInfo.getAjtEnclosure()));
					}
					qitaInfo.setAjtAjIds(detal.getId());
					r.setData(qitaInfo);
					r.setCode(200);
					r.setMsg("获取成功");
					return r;
				} else {
					r.setCode(500);
					r.setMsg("暂无数据");
					return r;
				}
			}

		} else {
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
	public R getDriverInfo(String jsyId) {
		return R.data(iJiaShiYuanService.selectDriverInfo(jsyId));
	}


	@GetMapping(value = "/getJSYByVehicle")
	@ApiLog("企业-查询驾驶员绑定车辆")
	@ApiOperation(value = "企业-查询驾驶员绑定车辆", notes = "传入jiashiyuanid", position = 13)
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
	public R resetPassword(@ApiParam(value = "ids", required = true) @RequestParam String ids, BladeUser user) {
		JiaShiYuan jiaShiYuan = new JiaShiYuan();
		jiaShiYuan.setDenglumima(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
		jiaShiYuan.setCaozuoshijian(LocalDateTime.now().toString());
		jiaShiYuan.setCaozuoren(user.getUserName());
		jiaShiYuan.setCaozuorenid(user.getUserId());
		boolean temp = iJiaShiYuanService.updatePassWord(jiaShiYuan.getDenglumima(), ids);
		return R.status(temp);
	}

	@GetMapping(value = "/getJVPageList")
	@ApiLog("企业-驾驶员绑定车辆列表")
	@ApiOperation(value = "企业-驾驶员绑定车辆列表", notes = "传入jiaShiYuanPage", position = 16)
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
		} else {
			boolean ss = carnumber.matches(carnumRegex);
			return ss;
		}
	}


	/**
	 * 驾驶员档案信息--验证
	 *
	 * @param
	 * @update: 黄亚平 添加验证
	 */
	@PostMapping("/driverDeptImport")
	@ApiLog("企业端--驾驶员档案信息--验证(最新)")
	@ApiOperation(value = "企业端--驾驶员档案信息--验证(最新)", notes = "file", position = 11)
	public R driverDeptImport(@RequestParam(value = "file") MultipartFile file, BladeUser user, @RequestParam String leixing) throws ParseException {
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
		//验证数据成功条数
		int aa = 0;
		//验证数据错误条数
		int bb = 0;
		//全局变量，只要一条数据不对，就为false
		boolean isDataValidity = true;
		//错误信息
		String errorStr = "";

		List<Map<String, Object>> readAll = reader.readAll();
		if (readAll.size() > 2000) {
			errorStr += "导入数据超过2000条，无法导入！";
			rs.setMsg(errorStr);
			rs.setCode(400);
			return rs;
		}

		List<JiaShiYuan> drivers = new ArrayList<>();
		for (Map<String, Object> a : readAll) {
			aa++;
			Dept dept;
			JiaShiYuan driver = new JiaShiYuan();
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
			JiaShiYuanVO driverVO = null;

			//

			//验证所属企业是否存在
			String deptname = String.valueOf(a.get("所属企业")).trim();
			if (StringUtils.isBlank(deptname)) {
				driver.setMsg("所属企业不能为空;");
				driver.setImportUrl("icon_cha.png");
				errorStr += "所属企业不能为空;";
				bb++;
			}
			dept = iSysClient.getDeptByName(deptname.trim());
			if (dept != null) {
				driver.setDeptId(Integer.valueOf(dept.getId()));
				driver.setDeptName(deptname.trim());
				driver.setImportUrl("icon_gou.png");
			} else {
				driver.setMsg(deptname + "所属企业不存在;");
				driver.setImportUrl("icon_cha.png");
				errorStr += deptname + "所属企业不存在;";
				rs.setMsg(errorStr);
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setData(drivers);
				return rs;
			}

			//验证司机姓名不能为空
			String driverName = String.valueOf(a.get("司机姓名")).trim();
			if (StringUtils.isBlank(driverName) && !driverName.equals("null")) {
				driver.setMsg("司机姓名不能为空;");
				driver.setImportUrl("icon_cha.png");
				errorStr += "司机姓名不能为空;";
				bb++;
			} else {
				driver.setJiashiyuanxingming(driverName);
				driver.setImportUrl("icon_gou.png");
			}

//			//验证从业人员类型不能为空
//			String congyerenyuanleixing1 = String.valueOf(a.get("从业人员类型")).trim();
//			if (StringUtils.isBlank(congyerenyuanleixing1) && !congyerenyuanleixing1.equals("null")) {
//				driver.setMsg("从业人员类型不能为空;");
//				driver.setImportUrl("icon_cha.png");
//				errorStr += "从业人员类型不能为空;";
//				bb++;
//			} else {
//				driver.setCongyerenyuanleixing(congyerenyuanleixing1);
//				driver.setImportUrl("icon_gou.png");
//			}

			//验证驾驶员性别
			String sex = String.valueOf(a.get("性别")).trim();
			if (StringUtils.isNotBlank(sex) && !driverName.equals("null")) {
				if (sex.equals("男") || sex.equals("女")) {
					if (sex.equals("男")) {
						driver.setXingbie("1");
						driver.setImportUrl("icon_gou.png");
					} else {
						driver.setXingbie("0");
						driver.setImportUrl("icon_gou.png");
					}
				} else {
					driver.setMsg("驾驶员性别应为“男”或“女”;");
					driver.setImportUrl("icon_cha.png");
					errorStr += "驾驶员性别应为“男”或“女”;";
					bb++;
				}
			}

//			//验证驾驶员类型不能为空
//			String jiashiyuanleixing = String.valueOf(a.get("驾驶员类型")).trim();
//			if (StringUtils.isBlank(jiashiyuanleixing) && !jiashiyuanleixing.equals("null")) {
//				driver.setMsg("驾驶员类型不能为空;");
//				driver.setImportUrl("icon_cha.png");
//				errorStr += "驾驶员类型不能为空;";
//				bb++;
//			} else {
//				driver.setJiashiyuanleixing(jiashiyuanleixing);
//				driver.setImportUrl("icon_gou.png");
//			}

			//验证手机号码是否合法
			String shoujihaoma = String.valueOf(a.get("手机号码")).trim();
			if (StringUtils.isBlank(shoujihaoma) && shoujihaoma.equals("null")) {
				driver.setMsg("手机号码不能为空;");
				driver.setImportUrl("icon_cha.png");
				errorStr += "手机号码不能为空;";
				bb++;
			} else {
				if (RegexUtils.checkMobile(shoujihaoma)) {
					driver.setShoujihaoma(shoujihaoma);
					if ("更新导入".equals(leixing)) {
						JiaShiYuan driverss = iJiaShiYuanService.getjiaShiYuan(driver.getDeptId().toString(), driver.getJiashiyuanxingming(), driver.getShoujihaoma(), null);
						if (driverss == null) {
							driver.setMsg(shoujihaoma + "该企业驾驶员姓名及手机号码不存在;");
							driver.setImportUrl("icon_cha.png");
							errorStr += shoujihaoma + "该企业驾驶员姓名及手机号码不存在;";
							bb++;
						} else {
							driver.setImportUrl("icon_gou.png");
						}
					} else {
						//根据企业ID、驾驶员姓名、手机号码、从业人员类型查询驾驶员是否存在
						String congyerenyuanleixing = String.valueOf(a.get("从业人员类型")).trim();
						JiaShiYuan driverss = iJiaShiYuanService.getjiaShiYuan(driver.getDeptId().toString(), driver.getJiashiyuanxingming(), driver.getShoujihaoma(), congyerenyuanleixing);
						if (driverss != null) {
							driver.setMsg(shoujihaoma + "该企业驾驶员姓名及手机号码已同时存在;");
							driver.setImportUrl("icon_cha.png");
							errorStr += shoujihaoma + "该企业驾驶员姓名及手机号码已同时存在;";
							bb++;
						} else {
							driver.setImportUrl("icon_gou.png");
						}
					}
				} else {
					driver.setMsg(shoujihaoma + "该手机号码不合法;");
					errorStr += shoujihaoma + "该手机号码不合法;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证驾驶车辆
			String vehicle = String.valueOf(a.get("驾驶车辆")).trim();
			if (StringUtils.isNotBlank(vehicle) && !vehicle.equals("null")) {
				driver.setCheliangpaizhao(vehicle);
				driver.setImportUrl("icon_gou.png");
			}

			//验证司机入职日期
			String sijiruzhiriqi = String.valueOf(a.get("司机入职日期")).trim();
			if (StringUtils.isNotBlank(sijiruzhiriqi) && !sijiruzhiriqi.equals("null")) {
				if (sijiruzhiriqi.length() >= 10) {
					sijiruzhiriqi = sijiruzhiriqi.substring(0, 10);
					if (StringUtils.isNotBlank(sijiruzhiriqi) && !sijiruzhiriqi.equals("null")) {
						if (DateUtils.isDateString(sijiruzhiriqi, null) == true) {
							driver.setPingyongriqi(sijiruzhiriqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(sijiruzhiriqi + ",该司机入职日期,不是时间格式;");
							errorStr += sijiruzhiriqi + ",该司机入职日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(sijiruzhiriqi + ",该司机入职日期,不是时间格式;");
					errorStr += sijiruzhiriqi + ",该司机入职日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证劳动合同开始日期
			String laodonghetongkaishiriqi = String.valueOf(a.get("劳动合同开始日期")).trim();
			if (StringUtils.isNotBlank(laodonghetongkaishiriqi) && !laodonghetongkaishiriqi.equals("null")) {
				if (laodonghetongkaishiriqi.length() >= 10) {
					laodonghetongkaishiriqi = laodonghetongkaishiriqi.substring(0, 10);
					if (StringUtils.isNotBlank(laodonghetongkaishiriqi) && !laodonghetongkaishiriqi.equals("null")) {
						if (DateUtils.isDateString(laodonghetongkaishiriqi, null) == true) {
							driver.setLaodonghetongkaishiriqi(laodonghetongkaishiriqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(laodonghetongkaishiriqi + ",该劳动合同开始日期,不是时间格式;");
							errorStr += laodonghetongkaishiriqi + ",该劳动合同开始日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(laodonghetongkaishiriqi + ",该劳动合同开始日期,不是时间格式;");
					errorStr += laodonghetongkaishiriqi + ",该劳动合同开始日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证劳动合同结束日期
			String laodonghetongjieshuriqi = String.valueOf(a.get("劳动合同结束日期")).trim();
			if (StringUtils.isNotBlank(laodonghetongjieshuriqi) && !laodonghetongjieshuriqi.equals("null")) {
				if (laodonghetongjieshuriqi.length() >= 10) {
					laodonghetongjieshuriqi = laodonghetongjieshuriqi.substring(0, 10);
					if (StringUtils.isNotBlank(laodonghetongjieshuriqi) && !laodonghetongjieshuriqi.equals("null")) {
						if (DateUtils.isDateString(laodonghetongjieshuriqi, null) == true) {
							driver.setLaodonghetongjieshuriqi(laodonghetongjieshuriqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(laodonghetongjieshuriqi + ",该劳动合同结束日期,不是时间格式;");
							errorStr += laodonghetongjieshuriqi + ",该劳动合同结束日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(laodonghetongjieshuriqi + ",该劳动合同结束日期,不是时间格式;");
					errorStr += laodonghetongjieshuriqi + ",该劳动合同结束日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证 劳动合同开始日期 不能大于 劳动合同结束日期
			if (StringUtils.isNotBlank(laodonghetongkaishiriqi) && !laodonghetongkaishiriqi.equals("null") && StringUtils.isNotBlank(laodonghetongjieshuriqi) && !laodonghetongjieshuriqi.equals("null")) {
				int a1 = laodonghetongkaishiriqi.length();
				int b1 = laodonghetongjieshuriqi.length();
				if (a1 == b1) {
					if (a1 <= 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if (DateUtils.belongCalendar(format.parse(laodonghetongkaishiriqi), format.parse(laodonghetongjieshuriqi))) {
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg("劳动合同开始日期,不能大于劳动合同结束日期;");
							errorStr += "劳动合同开始日期,不能大于劳动合同结束日期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if (a1 > 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (DateUtils.belongCalendar(format.parse(laodonghetongkaishiriqi), format.parse(laodonghetongjieshuriqi))) {
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg("劳动合同开始日期,不能大于劳动合同结束日期;");
							errorStr += "劳动合同开始日期,不能大于劳动合同结束日期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg("劳动合同开始日期与劳动合同结束日期,时间格式不一致;");
					errorStr += "劳动合同开始日期与劳动合同结束日期,时间格式不一致;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证身份证：证件号码
			String tmp = String.valueOf(a.get("身份证：证件号码")).trim();
			if (StringUtils.isNotBlank(tmp) && !tmp.equals("null") && !tmp.equals("空")) {
				//校验身份证号码是否合法
				if (IdCardUtil.isValidCard(tmp) == true) {
					driver.setShenfenzhenghao(tmp);
					//根据企业ID、身份证号查询该身份证是否存在
					QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId, driver.getDeptId());
					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShenfenzhenghao, tmp);
					JiaShiYuan jiaShiYuan2 = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
					if (jiaShiYuan2 != null) {
						driver.setMsg(tmp + "该企业驾驶员身份证号已存在;");
						driver.setImportUrl("icon_cha.png");
						errorStr += tmp + "该企业驾驶员身份证号已存在;";
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

			//验证身份证：开始日期
			String shenfenzhengchulingriqi = String.valueOf(a.get("身份证：开始日期")).trim();
			if (StringUtils.isNotBlank(shenfenzhengchulingriqi) && !shenfenzhengchulingriqi.equals("null")) {
				if (shenfenzhengchulingriqi.length() >= 10) {
					shenfenzhengchulingriqi = shenfenzhengchulingriqi.substring(0, 10);
					if (StringUtils.isNotBlank(shenfenzhengchulingriqi) && !shenfenzhengchulingriqi.equals("null")) {
						if (DateUtils.isDateString(shenfenzhengchulingriqi, null) == true) {
							driver.setShenfenzhengchulingriqi(shenfenzhengchulingriqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(shenfenzhengchulingriqi + ",该身份证初领日期,不是时间格式;");
							errorStr += shenfenzhengchulingriqi + ",该身份证初领日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(shenfenzhengchulingriqi + ",该身份证初领日期,不是时间格式;");
					errorStr += shenfenzhengchulingriqi + ",该身份证初领日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证身份证：结束日期
			String shenfenzhengyouxiaoqi = String.valueOf(a.get("身份证：结束日期")).trim();
			if (StringUtils.isNotBlank(shenfenzhengyouxiaoqi) && !shenfenzhengyouxiaoqi.equals("null")) {
				if (shenfenzhengyouxiaoqi.length() >= 10) {
					shenfenzhengyouxiaoqi = shenfenzhengyouxiaoqi.substring(0, 10);
					if (StringUtils.isNotBlank(shenfenzhengyouxiaoqi) && !shenfenzhengyouxiaoqi.equals("null")) {
						if (DateUtils.isDateString(shenfenzhengyouxiaoqi, null) == true) {
							driver.setShenfenzhengyouxiaoqi(shenfenzhengyouxiaoqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(shenfenzhengyouxiaoqi + ",该身份证有效期,不是时间格式;");
							errorStr += shenfenzhengyouxiaoqi + ",该身份证有效期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(shenfenzhengyouxiaoqi + ",该身份证有效期,不是时间格式;");
					errorStr += shenfenzhengyouxiaoqi + ",该身份证有效期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证 身份证：开始日期 不能大于 身份证：结束日期
			if (StringUtils.isNotBlank(shenfenzhengchulingriqi) && !shenfenzhengchulingriqi.equals("null") && StringUtils.isNotBlank(shenfenzhengyouxiaoqi) && !shenfenzhengyouxiaoqi.equals("null")) {
				int a1 = shenfenzhengchulingriqi.length();
				int b1 = shenfenzhengyouxiaoqi.length();
				if (a1 == b1) {
					if (a1 <= 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if (DateUtils.belongCalendar(format.parse(shenfenzhengchulingriqi), format.parse(shenfenzhengyouxiaoqi))) {
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg("身份证初领日期,不能大于身份证有效期;");
							errorStr += "身份证初领日期,不能大于身份证有效期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if (a1 > 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (DateUtils.belongCalendar(format.parse(shenfenzhengchulingriqi), format.parse(shenfenzhengyouxiaoqi))) {
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg("身份证初领日期,不能大于身份证有效期;");
							errorStr += "身份证初领日期,不能大于身份证有效期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg("身份证初领日期与身份证有效期,时间格式不一致;");
					errorStr += "身份证初领日期与身份证有效期,时间格式不一致;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证驾驶证：证件号码是否合法
			String jiashizheng = String.valueOf(a.get("驾驶证：证件号码")).trim();
			if (StringUtils.isNotBlank(jiashizheng) && !jiashizheng.equals("null") && !jiashizheng.equals("空")) {
				if (IdCardUtil.isValidCard(jiashizheng) == true) {
					driver.setJiashizhenghao(jiashizheng);
					driverVO = iJiaShiYuanService.selectByCardNo(driver.getDeptId().toString(), null, jiashizheng, "驾驶员");
					if (driverVO != null) {
						driver.setMsg(driverVO.getJiashizhenghao() + "该企业驾驶证号已存在;");
						driver.setImportUrl("icon_cha.png");
						errorStr += driverVO.getJiashizhenghao() + "该企业驾驶证号已存在;";
						bb++;
					} else {
						driver.setShenfenzhenghao(jiashizheng);
						driver.setImportUrl("icon_gou.png");
					}
				} else {
					driver.setMsg(jiashizheng + "该驾驶证号不合法;");
					errorStr += jiashizheng + "该驾驶证号不合法;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证驾驶证：开始日期
			String jiashizhengchulingriqi = String.valueOf(a.get("驾驶证：开始日期")).trim();
			if (StringUtils.isNotBlank(jiashizhengchulingriqi) && !jiashizhengchulingriqi.equals("null")) {
				if (jiashizhengchulingriqi.length() >= 10) {
					jiashizhengchulingriqi = jiashizhengchulingriqi.substring(0, 10);
					if (StringUtils.isNotBlank(jiashizhengchulingriqi) && !jiashizhengchulingriqi.equals("null")) {
						if (DateUtils.isDateString(jiashizhengchulingriqi, null) == true) {
							driver.setJiashizhengchulingriqi(jiashizhengchulingriqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(jiashizhengchulingriqi + ",该驾驶证开始日期,不是时间格式;");
							errorStr += jiashizhengchulingriqi + ",该驾驶证开始日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(jiashizhengchulingriqi + ",该驾驶证开始日期,不是时间格式;");
					errorStr += jiashizhengchulingriqi + ",该驾驶证开始日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证驾驶证：结束日期
			String jiashizhengyouxiaoqi = String.valueOf(a.get("驾驶证：结束日期")).trim();
			if (StringUtils.isNotBlank(jiashizhengyouxiaoqi) && !jiashizhengyouxiaoqi.equals("null")) {
				if (jiashizhengyouxiaoqi.length() >= 10 || jiashizhengyouxiaoqi.equals("长期")) {
					if (!jiashizhengyouxiaoqi.equals("长期")) {
						jiashizhengyouxiaoqi = jiashizhengyouxiaoqi.substring(0, 10);
						if (StringUtils.isNotBlank(jiashizhengyouxiaoqi) && !jiashizhengyouxiaoqi.equals("null")) {
							if (DateUtils.isDateString(jiashizhengyouxiaoqi, null) == true) {
								driver.setJiashizhengyouxiaoqi(jiashizhengyouxiaoqi);
								driver.setImportUrl("icon_gou.png");
							} else {
								driver.setMsg(jiashizhengyouxiaoqi + ",该驾驶证结束日期,不是时间格式;");
								errorStr += jiashizhengyouxiaoqi + ",该驾驶证结束日期,不是时间格式;";
								driver.setImportUrl("icon_cha.png");
								bb++;
							}
						}
					}else if (jiashizhengyouxiaoqi.equals("长期")){
						driver.setJiashizhengyouxiaoqi(jiashizhengyouxiaoqi);
						driver.setImportUrl("icon_gou.png");
					}
				} else {
					driver.setMsg(jiashizhengyouxiaoqi + ",该驾驶证结束日期,不是时间格式;");
					errorStr += jiashizhengyouxiaoqi + ",该驾驶证结束日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证 驾驶证开始日期 不能大于 驾驶证结束日期
			if (StringUtils.isNotBlank(jiashizhengchulingriqi) && !jiashizhengchulingriqi.equals("null") && StringUtils.isNotBlank(jiashizhengyouxiaoqi) && !jiashizhengyouxiaoqi.equals("null")) {
				int a1 = jiashizhengchulingriqi.length();
				int b1 = jiashizhengyouxiaoqi.length();
				if (a1 == b1) {
					if (a1 <= 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if (DateUtils.belongCalendar(format.parse(jiashizhengchulingriqi), format.parse(jiashizhengyouxiaoqi))) {
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg("驾驶证开始日期,不能大于驾驶证结束日期;");
							errorStr += "驾驶证开始日期,不能大于驾驶证结束日期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if (a1 > 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (DateUtils.belongCalendar(format.parse(jiashizhengchulingriqi), format.parse(jiashizhengyouxiaoqi))) {
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg("驾驶证开始日期,不能大于驾驶证结束日期;");
							errorStr += "驾驶证开始日期,不能大于驾驶证结束日期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg("驾驶证开始日期与驾驶证结束日期,时间格式不一致;");
					errorStr += "驾驶证开始日期与驾驶证结束日期,时间格式不一致;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证从业资格证：证件号码
			String congyezigezheng = String.valueOf(a.get("从业资格证：证件号码")).trim();
			if (StringUtils.isNotBlank(congyezigezheng) && !congyezigezheng.equals("null")) {
				driver.setCongyezigezheng(congyezigezheng);
				driver.setImportUrl("icon_gou.png");
			}

			//验证从业资格证：开始日期
			String congyezhengchulingri = String.valueOf(a.get("从业资格证：开始日期")).trim();
			if (StringUtils.isNotBlank(congyezhengchulingri) && !congyezhengchulingri.equals("null")) {
				if (congyezhengchulingri.length() >= 10) {
					congyezhengchulingri = congyezhengchulingri.substring(0, 10);
					if (StringUtils.isNotBlank(congyezhengchulingri) && !congyezhengchulingri.equals("null")) {
						if (DateUtils.isDateString(congyezhengchulingri, null) == true) {
							driver.setCongyezhengchulingri(congyezhengchulingri);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(congyezhengchulingri + ",该从业资格证：开始日期,不是时间格式;");
							errorStr += congyezhengchulingri + ",该从业资格证：开始日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(congyezhengchulingri + ",该从业资格证：开始日期,不是时间格式;");
					errorStr += congyezhengchulingri + ",该从业资格证：开始日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证从业资格证：结束日期
			String congyezhengyouxiaoqi = String.valueOf(a.get("从业资格证：结束日期")).trim();
			if (StringUtils.isNotBlank(congyezhengyouxiaoqi) && !congyezhengyouxiaoqi.equals("null")) {
				if (congyezhengyouxiaoqi.length() >= 10) {
					congyezhengyouxiaoqi = congyezhengyouxiaoqi.substring(0, 10);
					if (StringUtils.isNotBlank(congyezhengyouxiaoqi) && !congyezhengyouxiaoqi.equals("null")) {
						if (DateUtils.isDateString(congyezhengyouxiaoqi, null) == true) {
							driver.setCongyezhengyouxiaoqi(congyezhengyouxiaoqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(congyezhengyouxiaoqi + ",该从业资格证：结束日期,不是时间格式;");
							errorStr += congyezhengyouxiaoqi + ",该从业资格证：结束日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(congyezhengyouxiaoqi + ",该从业资格证：结束日期,不是时间格式;");
					errorStr += congyezhengyouxiaoqi + ",该从业资格证：结束日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证 从业资格证发放日期 不能大于 从业资格证有效截至日期
			if (StringUtils.isNotBlank(congyezhengchulingri) && !congyezhengchulingri.equals("null") && StringUtils.isNotBlank(congyezhengyouxiaoqi) && !congyezhengyouxiaoqi.equals("null")) {
				int a1 = congyezhengchulingri.length();
				int b1 = congyezhengyouxiaoqi.length();
				if (a1 == b1) {
					if (a1 <= 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if (DateUtils.belongCalendar(format.parse(congyezhengchulingri), format.parse(congyezhengyouxiaoqi))) {
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg("从业资格证发放日期,不能大于从业资格证有效截至日期;");
							errorStr += "从业资格证发放日期,不能大于从业资格证有效截至日期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
					if (a1 > 10) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (DateUtils.belongCalendar(format.parse(congyezhengchulingri), format.parse(congyezhengyouxiaoqi))) {
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg("从业资格证发放日期,不能大于从业资格证有效截至日期;");
							errorStr += "从业资格证发放日期,不能大于从业资格证有效截至日期;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg("从业资格证发放日期与从业资格证有效截至日期,时间格式不一致;");
					errorStr += "从业资格证发放日期与从业资格证有效截至日期,时间格式不一致;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证安全生产责任书：起始日期
			String anquanzerenshuqishiriqi = String.valueOf(a.get("安全生产责任书：起始日期")).trim();
			if (StringUtils.isNotBlank(anquanzerenshuqishiriqi) && !anquanzerenshuqishiriqi.equals("null")) {
				if (anquanzerenshuqishiriqi.length() >= 10) {
					anquanzerenshuqishiriqi = anquanzerenshuqishiriqi.substring(0, 10);
					if (StringUtils.isNotBlank(anquanzerenshuqishiriqi) && !anquanzerenshuqishiriqi.equals("null")) {
						if (DateUtils.isDateString(anquanzerenshuqishiriqi, null) == true) {
							driver.setAnquanzerenshuqishiriqi(anquanzerenshuqishiriqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(anquanzerenshuqishiriqi + ",该安全生产责任书：起始日期,不是时间格式;");
							errorStr += anquanzerenshuqishiriqi + ",该安全生产责任书：起始日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(anquanzerenshuqishiriqi + ",该安全生产责任书：起始日期,不是时间格式;");
					errorStr += anquanzerenshuqishiriqi + ",该安全生产责任书：起始日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证岗位危险告知书：起始日期
			String gangweigaozhishuqishiriqi = String.valueOf(a.get("岗位危险告知书：起始日期")).trim();
			if (StringUtils.isNotBlank(gangweigaozhishuqishiriqi) && !gangweigaozhishuqishiriqi.equals("null")) {
				if (gangweigaozhishuqishiriqi.length() >= 10) {
					gangweigaozhishuqishiriqi = gangweigaozhishuqishiriqi.substring(0, 10);
					if (StringUtils.isNotBlank(gangweigaozhishuqishiriqi) && !gangweigaozhishuqishiriqi.equals("null")) {
						if (DateUtils.isDateString(gangweigaozhishuqishiriqi, null) == true) {
							driver.setGangweigaozhishuqishiriqi(gangweigaozhishuqishiriqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(gangweigaozhishuqishiriqi + ",该岗位危险告知书：起始日期,不是时间格式;");
							errorStr += gangweigaozhishuqishiriqi + ",该岗位危险告知书：起始日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(gangweigaozhishuqishiriqi + ",该岗位危险告知书：起始日期,不是时间格式;");
					errorStr += gangweigaozhishuqishiriqi + ",该岗位危险告知书：起始日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证体检报告：起始日期
			String tijianriqi = String.valueOf(a.get("体检报告：起始日期")).trim();
			if (StringUtils.isNotBlank(tijianriqi) && !tijianriqi.equals("null")) {
				if (tijianriqi.length() >= 10) {
					tijianriqi = tijianriqi.substring(0, 10);
					if (StringUtils.isNotBlank(tijianriqi) && !tijianriqi.equals("null")) {
						if (DateUtils.isDateString(tijianriqi, null) == true) {
							driver.setTijianriqi(tijianriqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(tijianriqi + ",该体检报告：起始日期,不是时间格式;");
							errorStr += tijianriqi + ",该体检报告：起始日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(tijianriqi + ",该体检报告：起始日期,不是时间格式;");
					errorStr += tijianriqi + ",该体检报告：起始日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证无重大责任事故：起始日期
			String wuzhogndazerenshiguqishiriqi = String.valueOf(a.get("无重大责任事故：起始日期")).trim();
			if (StringUtils.isNotBlank(wuzhogndazerenshiguqishiriqi) && !wuzhogndazerenshiguqishiriqi.equals("null")) {
				if (wuzhogndazerenshiguqishiriqi.length() >= 10) {
					wuzhogndazerenshiguqishiriqi = wuzhogndazerenshiguqishiriqi.substring(0, 10);
					if (StringUtils.isNotBlank(wuzhogndazerenshiguqishiriqi) && !wuzhogndazerenshiguqishiriqi.equals("null")) {
						if (DateUtils.isDateString(wuzhogndazerenshiguqishiriqi, null) == true) {
							driver.setWuzhongdazerenshiguqishiriqi(wuzhogndazerenshiguqishiriqi);
							driver.setImportUrl("icon_gou.png");
						} else {
							driver.setMsg(wuzhogndazerenshiguqishiriqi + ",该无重大责任事故：起始日期,不是时间格式;");
							errorStr += wuzhogndazerenshiguqishiriqi + ",该无重大责任事故：起始日期,不是时间格式;";
							driver.setImportUrl("icon_cha.png");
							bb++;
						}
					}
				} else {
					driver.setMsg(wuzhogndazerenshiguqishiriqi + ",该无重大责任事故：起始日期,不是时间格式;");
					errorStr += wuzhogndazerenshiguqishiriqi + ",该无重大责任事故：起始日期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

//			//验证体检有效期
//			String tijianyouxiaoqi = String.valueOf(a.get("体检有效期")).trim();
//			if (tijianyouxiaoqi.length() >= 10) {
//				tijianyouxiaoqi=tijianyouxiaoqi.substring(0,10);
//				if (StringUtils.isNotBlank(tijianyouxiaoqi) && !tijianyouxiaoqi.equals("null")) {
//					if (DateUtils.isDateString(tijianyouxiaoqi, null) == true) {
//						driver.setTijianyouxiaoqi(tijianyouxiaoqi);
//						driver.setImportUrl("icon_gou.png");
//					} else {
//						driver.setMsg(tijianyouxiaoqi + ",该体检有效期,不是时间格式;");
//						errorStr += tijianyouxiaoqi + ",该体检有效期,不是时间格式;";
//						driver.setImportUrl("icon_cha.png");
//						bb++;
//					}
//				}
//			}else {
//				driver.setMsg(tijianyouxiaoqi + ",该体检有效期,不是时间格式;");
//				errorStr += tijianyouxiaoqi + ",该体检有效期,不是时间格式;";
//				driver.setImportUrl("icon_cha.png");
//				bb++;
//			}

//			//验证 体检日期 不能大于 体检有效期
//			if (StringUtils.isNotBlank(tijianriqi) && !tijianriqi.equals("null") && StringUtils.isNotBlank(tijianyouxiaoqi) && !tijianyouxiaoqi.equals("null")) {
//				int a1 = tijianriqi.length();
//				int b1 = tijianyouxiaoqi.length();
//				if (a1 == b1) {
//					if (a1 <= 10) {
//						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//						if (DateUtils.belongCalendar(format.parse(tijianriqi), format.parse(tijianyouxiaoqi))) {
//							driver.setImportUrl("icon_gou.png");
//						} else {
//							driver.setMsg("体检日期,不能大于体检有效期;");
//							errorStr += "体检日期,不能大于体检有效期;";
//							driver.setImportUrl("icon_cha.png");
//							bb++;
//						}
//					}
//					if (a1 > 10) {
//						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						if (DateUtils.belongCalendar(format.parse(shenfenzhengchulingriqi), format.parse(shenfenzhengyouxiaoqi))) {
//							driver.setImportUrl("icon_gou.png");
//						} else {
//							driver.setMsg("体检日期,不能大于体检有效期;");
//							errorStr += "体检日期,不能大于体检有效期;";
//							driver.setImportUrl("icon_cha.png");
//							bb++;
//						}
//					}
//				} else {
//					driver.setMsg("体检日期与体检有效期,时间格式不一致;");
//					errorStr += "体检日期与体检有效期,时间格式不一致;";
//					driver.setImportUrl("icon_cha.png");
//					bb++;
//				}
//			}

			//验证Excel导入时，是否存在重复数据
			for (JiaShiYuan item : drivers) {
				if (item.getJiashiyuanxingming().equals(driverName) && item.getShoujihaoma().equals(shoujihaoma) && item.getDeptName().equals(deptname)) {
					driver.setImportUrl("icon_cha.png");
					errorStr += driverName + "驾驶员重复；";
					driver.setMsg(driverName + "驾驶员重复；");
					bb++;
				}
			}
			drivers.add(driver);
		}

		if (bb > 0) {
			rs.setMsg(errorStr);
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setData(drivers);
			return rs;
		} else {
			rs.setCode(200);
			rs.setMsg("数据验证成功");
			rs.setData(drivers);
			rs.setSuccess(true);
			return rs;
		}
	}

//	/**
//	 * 企业端--驾驶员档案信息--确认导入
//	 * @param
//	 */
//	@PostMapping("driverDeptImportOk")
//	@ApiLog("企业端--驾驶员档案信息--确认导入(最新)")
//	@ApiOperation(value = "企业端--驾驶员档案信息--确认导入(最新)", notes = "drivers", position = 10)
//	public R driverDeptImportOk(@RequestParam(value = "drivers") String drivers,BladeUser user,@RequestParam Integer userId,@RequestParam String userName){
//		JSONArray json = JSONUtil.parseArray(drivers);
//		List<Map<String,Object>> lists = (List)json;
//		R rs = new R();
//		if (user == null) {
//			rs.setCode(500);
//			rs.setMsg("用户权限验证失败");
//			rs.setData(null);
//			rs.setSuccess(false);
//			return rs;
//		}
//		//时间默认格式
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		//验证数据成功条数
//		int aa=0;
//		//验证数据错误条数
//		int bb=0;
//		//全局变量，只要一条数据不对，就为false
//		boolean isDataValidity=true;
//		//错误信息
//		String errorStr="";
//
//		if(lists.size()>2000){
//			errorStr+="导入数据超过2000条，无法导入！";
//			rs.setMsg(errorStr);
//			rs.setCode(400);
//			return rs;
//		}
//
//		for(Map<String,Object> a:lists){
//			aa++;
//			JiaShiYuan driver=new JiaShiYuan();
//			SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
//			String deptId = String.valueOf(a.get("deptId"));
//			driver.setDeptId(Integer.valueOf(deptId));
//			driver.setJiashiyuanxingming(String.valueOf(a.get("jiashiyuanxingming")).trim());
//			driver.setXingbie(String.valueOf(a.get("xingbie")));
//			String tmp = String.valueOf(a.get("shenfenzhenghao")).trim();
//			driver.setShenfenzhenghao(tmp);
//			//通过身份证获取年龄
//			Integer age = IdCardUtil.getAgeByCard(tmp);
//			driver.setNianling(age.toString());
//			//通过身份证获取生日日期
//			Date chushengshijian = IdCardUtil.getBirthDate(tmp);
//			String shoujihaoma = String.valueOf(a.get("shoujihaoma")).trim();
//			driver.setShoujihaoma(shoujihaoma);
//			driver.setChushengshijian(dateFormat2.format(chushengshijian));
//			driver.setCongyeleibie(String.valueOf(a.get("congyeleibie")).trim());
//			driver.setJiashiyuanleixing(String.valueOf(a.get("jiashiyuanleixing")).trim());
//			driver.setCongyerenyuanleixing(String.valueOf(a.get("jiashiyuanleixing")).trim());
//			driver.setJiashizhenghao(String.valueOf(a.get("jiashizhenghao")).trim());
//			driver.setCongyezigezheng(String.valueOf(a.get("congyezigezheng")).trim());
//			driver.setZhunjiachexing(String.valueOf(a.get("zhunjiachexing")).trim());
//			driver.setCongyezhengchulingri(String.valueOf(a.get("congyezhengchulingri")).trim());
//			driver.setCongyezhengyouxiaoqi(String.valueOf(a.get("congyezhengyouxiaoqi")).trim());
//			driver.setChengxinkaoheshijian(String.valueOf(a.get("chengxinkaoheshijian")).trim());
//			driver.setXiacichengxinkaoheshijian(String.valueOf(a.get("xiacichengxinkaoheshijian")).trim());
//			driver.setJiashizhengchulingriqi(String.valueOf(a.get("jiashizhengchulingriqi")).trim());
//			driver.setJiashizhengyouxiaoqi(String.valueOf(a.get("jiashizhengyouxiaoqi")).trim());
//			driver.setShenfenzhengyouxiaoqi(String.valueOf(a.get("shenfenzhengyouxiaoqi")).trim());
//			driver.setTijianyouxiaoqi(String.valueOf(a.get("tijianyouxiaoqi")).trim());
//			driver.setFazhengjiguan(String.valueOf(a.get("fazhengjiguan")).trim());
//			driver.setBeizhu(String.valueOf(a.get("beizhu")).trim());
//			driver.setIsdelete(0);
//			driver.setCreatetime(DateUtil.now());
//			driver.setCaozuoshijian(DateUtil.now());
//			if(user != null){
//				driver.setCaozuoren(user.getUserName());
//				driver.setCaozuorenid(user.getUserId());
//			}
//			//登录密码
//			driver.setDenglumima(DigestUtil.encrypt("123456"));
//			JiaShiYuan driverInfo = iJiaShiYuanService.getjiaShiYuanByOne(driver.getDeptId().toString(),driver.getJiashiyuanxingming(), driver.getShoujihaoma(),driver.getShenfenzhenghao(),driver.getJiashiyuanleixing());
//			if(driverInfo != null){
//				driver.setId(driverInfo.getId());
//				isDataValidity = iJiaShiYuanService.updateSelective(driver);
//			}else{
//				String id= IdUtil.simpleUUID();
//				driver.setId(id);
//				isDataValidity = iJiaShiYuanService.insertSelective(driver);
//			}
//		}
//		if(isDataValidity == true){
//			rs.setCode(200);
//			rs.setMsg("数据导入成功");
//			rs.setData(drivers);
//			return rs;
//		}else{
//			rs.setCode(500);
//			rs.setMsg("数据导入失败");
//			rs.setData(drivers);
//			return rs;
//		}
//	}

	@GetMapping(value = "/getJiaShiYuanByDept")
	@ApiLog("企业-根据企业ID获取从业人员信息")
	@ApiOperation(value = "企业-根据企业ID获取从业人员信息", notes = "传入deptId", position = 21)
	public R<JiaShiYuan> getJiaShiYuanByDept(Integer deptId) {
		R rs = new R();
		List<JiaShiYuan> jiaShiYuanList = iJiaShiYuanService.getJiaShiYuanByDept(deptId);
		if (jiaShiYuanList != null) {
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(jiaShiYuanList);
			rs.setMsg("获取成功");
		} else {
			rs.setCode(500);
			rs.setMsg("获取失败");
		}
		return rs;
	}

	/**
	 * 企业端--驾驶员档案信息--确认导入
	 *
	 * @param
	 */
	@PostMapping("/driverDeptImportOk")
	@ApiLog("企业端--驾驶员档案信息--确认导入(最新)")
	@ApiOperation(value = "企业端--驾驶员档案信息--确认导入(最新)", notes = "drivers", position = 10)
	public R driverDeptImportOk(@RequestParam(value = "drivers") String drivers,BladeUser user){
		JSONArray json = JSONUtil.parseArray(drivers);
		List<Map<String, Object>> lists = (List) json;
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("用户权限验证失败");
			rs.setData(null);
			rs.setSuccess(false);
			return rs;
		}
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
			rs.setCode(400);
			return rs;
		}

		for (Map<String, Object> a : lists) {
			aa++;
			JiaShiYuan driver = new JiaShiYuan();
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
			String deptId = String.valueOf(a.get("deptId"));
			driver.setDeptId(Integer.valueOf(deptId));
			driver.setDeptName(String.valueOf(a.get("deptName")).trim());
			driver.setJiashiyuanxingming(String.valueOf(a.get("jiashiyuanxingming")).trim());
			String tmp = String.valueOf(a.get("shenfenzhenghao")).trim();
			driver.setShenfenzhenghao(tmp);
			driver.setXingbie(String.valueOf(a.get("xingbie")).trim());
			driver.setCheliangpaizhao(String.valueOf(a.get("cheliangpaizhao")).trim());
			driver.setPingyongriqi(String.valueOf(a.get("pingyongriqi")).trim());
			driver.setLaodonghetongkaishiriqi(String.valueOf(a.get("laodonghetongkaishiriqi")).trim());
			driver.setLaodonghetongjieshuriqi(String.valueOf(a.get("laodonghetongjieshuriqi")).trim());
			driver.setAnquanzerenshuqishiriqi(String.valueOf(a.get("anquanzerenshuqishiriqi")).trim());
			driver.setGangweigaozhishuqishiriqi(String.valueOf(a.get("gangweigaozhishuqishiriqi")).trim());
			driver.setJiashiyuanleixing("驾驶员");
			if (StringUtils.isNotBlank(tmp) && !tmp.equals("null")){
				//通过身份证获取年龄
				Integer age = IdCardUtil.getAgeByCard(tmp);
				driver.setNianling(age.toString());
				//通过身份证获取生日日期
				Date chushengshijian = IdCardUtil.getBirthDate(tmp);
				driver.setChushengshijian(dateFormat2.format(chushengshijian));
			} else {
				driver.setNianling("0");
			}
			String shoujihaoma = String.valueOf(a.get("shoujihaoma")).trim();
			driver.setShoujihaoma(shoujihaoma);
			driver.setCongyerenyuanleixing(String.valueOf(a.get("congyerenyuanleixing")).trim());
			driver.setJiashizhenghao(String.valueOf(a.get("jiashizhenghao")).trim());
			driver.setCongyezigezheng(String.valueOf(a.get("congyezigezheng")).trim());
			driver.setCongyezhengchulingri(String.valueOf(a.get("congyezhengchulingri")).trim());
			driver.setCongyezhengyouxiaoqi(String.valueOf(a.get("congyezhengyouxiaoqi")).trim());
			driver.setJiashizhengchulingriqi(String.valueOf(a.get("jiashizhengchulingriqi")).trim());
			driver.setJiashizhengyouxiaoqi(String.valueOf(a.get("jiashizhengyouxiaoqi")).trim());
			driver.setShenfenzhengchulingriqi(String.valueOf(a.get("shenfenzhengchulingriqi")).trim());
			driver.setShenfenzhengyouxiaoqi(String.valueOf(a.get("shenfenzhengyouxiaoqi")).trim());
			driver.setLaodonghetongkaishiriqi(String.valueOf(a.get("laodonghetongkaishiriqi")).trim());
			driver.setLaodonghetongjieshuriqi(String.valueOf(a.get("laodonghetongjieshuriqi")).trim());
			driver.setAnquanzerenshuqishiriqi(String.valueOf(a.get("anquanzerenshuqishiriqi")).trim());
			driver.setGangweigaozhishuqishiriqi(String.valueOf(a.get("gangweigaozhishuqishiriqi")).trim());
			driver.setWuzhongdazerenshiguqishiriqi(String.valueOf(a.get("wuzhongdazerenshiguqishiriqi")).trim());
			driver.setTijianriqi(String.valueOf(a.get("tijianriqi")).trim());
			driver.setCongyerenyuanleixing("驾驶员");
			driver.setIsdelete(0);
			driver.setCreatetime(DateUtil.now());
			driver.setCaozuoshijian(DateUtil.now());
			if (user != null) {
				driver.setCaozuoren(user.getUserName());
				driver.setCaozuorenid(user.getUserId());
			}

			//登录密码
			driver.setDenglumima(DigestUtil.encrypt(driver.getShoujihaoma().substring(driver.getShoujihaoma().length() - 6)));
			QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper1 = new QueryWrapper<>();
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getDeptId,driver.getDeptId());
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getJiashiyuanxingming,driver.getJiashiyuanxingming());
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getShoujihaoma,driver.getShoujihaoma());
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getShenfenzhenghao,driver.getShenfenzhenghao());
			JiaShiYuan jiaShiYuan1 = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper1);
			if (jiaShiYuan1 != null) {
				driver.setId(jiaShiYuan1.getId());
				isDataValidity = iJiaShiYuanService.updateSelective(driver);
			} else {
				String id = IdUtil.simpleUUID();
				driver.setId(id);
				isDataValidity = iJiaShiYuanService.insertSelective(driver);
			}

			QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
			jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, driver.getId());
			jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShoujihaoma, driver.getShoujihaoma());
			jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId, driver.getDeptId());
			JiaShiYuan jiaShiYuan = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);

			//向入职登记表添加信息
			AnbiaoJiashiyuanRuzhi ruzhi = new AnbiaoJiashiyuanRuzhi();
			QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
			ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, jiaShiYuan.getId());
			ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
			AnbiaoJiashiyuanRuzhi rzdeail = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
			if (rzdeail == null) {
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
				isDataValidity = ruzhiService.save(ruzhi);
			}

			//向驾驶证信息表添加数据
			AnbiaoJiashiyuanJiashizheng jiashizheng = new AnbiaoJiashiyuanJiashizheng();
			QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
			jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, jiaShiYuan.getId());
			jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
			AnbiaoJiashiyuanJiashizheng jszdeail = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
			if (jszdeail == null) {
				jiashizheng.setAjjAjIds(jiaShiYuan.getId());
				jiashizheng.setAjjFileNo(jiaShiYuan.getJiashizhenghao());
				jiashizheng.setAjjValidPeriodStart(jiaShiYuan.getJiashizhengchulingriqi());
				jiashizheng.setAjjValidPeriodEnd(jiaShiYuan.getJiashizhengyouxiaoqi());
				jiashizheng.setAjjStatus("0");
				jiashizheng.setAjjDelete("0");
				jiashizheng.setAjjCreateByIds(jiaShiYuan.getCaozuorenid().toString());
				jiashizheng.setAjjCreateByName(jiaShiYuan.getCaozuoren());
				jiashizheng.setAjjCreateTime(jiaShiYuan.getCaozuoshijian());
				isDataValidity = jiashizhengService.save(jiashizheng);
			}

			//向从业资格证信息表添加数据
			AnbiaoJiashiyuanCongyezigezheng congyezigezheng = new AnbiaoJiashiyuanCongyezigezheng();
			QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
			congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, jiaShiYuan.getId());
			congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
			AnbiaoJiashiyuanCongyezigezheng cyzdeail = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
			if (cyzdeail == null) {
				congyezigezheng.setAjcAjIds(jiaShiYuan.getId());
				congyezigezheng.setAjcCertificateNo(jiaShiYuan.getCongyezigezheng());
				congyezigezheng.setAjcIssueDate(jiaShiYuan.getCongyezhengchulingri());
				congyezigezheng.setAjcValidUntil(jiaShiYuan.getCongyezhengyouxiaoqi());
				congyezigezheng.setAjcStatus("0");
				congyezigezheng.setAjcCreateTime(DateUtil.now());
				congyezigezheng.setAjcDelete("0");
				isDataValidity = congyezigezhengService.save(congyezigezheng);
			}

			//向体检信息表添加数据
			AnbiaoJiashiyuanTijian tijian = new AnbiaoJiashiyuanTijian();
			QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
			tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, jiaShiYuan.getId());
			tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
			AnbiaoJiashiyuanTijian tjdeail = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
			if (tjdeail == null) {
				tijian.setAjtCreateByName(jiaShiYuan.getCaozuoren());
				tijian.setAjtCreateByIds(jiaShiYuan.getCaozuorenid().toString());
				tijian.setAjtCreateTime(jiaShiYuan.getCaozuoshijian());
				tijian.setAjtDelete("0");
				tijian.setAjtAjIds(jiaShiYuan.getId());
				tijian.setAjtPhysicalExaminationDate(jiaShiYuan.getTijianriqi());
				isDataValidity = tijianService.save(tijian);
			}

			//向岗前培训信息表添加数据
			AnbiaoJiashiyuanGangqianpeixun gangqianpeixun = new AnbiaoJiashiyuanGangqianpeixun();
			QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
			gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, jiaShiYuan.getId());
			gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
			AnbiaoJiashiyuanGangqianpeixun gqpxdeail = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
			if (gqpxdeail == null) {
				gangqianpeixun.setAjgCreateByName(jiaShiYuan.getCaozuoren());
				gangqianpeixun.setAjgCreateByIds(jiaShiYuan.getCaozuorenid().toString());
				gangqianpeixun.setAjgCreateTime(jiaShiYuan.getCaozuoshijian());
				gangqianpeixun.setAjgDelete("0");
				gangqianpeixun.setAjgAjIds(jiaShiYuan.getId());
				isDataValidity = gangqianpeixunService.save(gangqianpeixun);
			}

			//向三年无重大责任事故证明信息表添加数据
			AnbiaoJiashiyuanWuzezhengming wuzezhengming = new AnbiaoJiashiyuanWuzezhengming();
			QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
			wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, jiaShiYuan.getId());
			wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
			AnbiaoJiashiyuanWuzezhengming wzzmdeail = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
			if (wzzmdeail == null) {
				wuzezhengming.setAjwCreateByName(jiaShiYuan.getCaozuoren());
				wuzezhengming.setAjwCreateByIds(jiaShiYuan.getCaozuorenid().toString());
				wuzezhengming.setAjwCreateTime(DateUtil.now());
				wuzezhengming.setAjwDelete("0");
				wuzezhengming.setAjwAjIds(jiaShiYuan.getId());
				wuzezhengming.setAjwStartDate(driver.getWuzhongdazerenshiguqishiriqi());
				isDataValidity = wuzezhengmingService.save(wuzezhengming);
			}

			//向驾驶员安全责任书信息表添加数据
			AnbiaoJiashiyuanAnquanzerenshu anquanzerenshu = new AnbiaoJiashiyuanAnquanzerenshu();
			QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanAnquanzerenshu>();
			anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaAjIds, jiaShiYuan.getId());
			anquanzerenshuQueryWrapper.lambda().eq(AnbiaoJiashiyuanAnquanzerenshu::getAjaDelete, "0");
			AnbiaoJiashiyuanAnquanzerenshu aqzesdeail = anquanzerenshuService.getBaseMapper().selectOne(anquanzerenshuQueryWrapper);
			if (aqzesdeail == null) {
				anquanzerenshu.setAjaCreateByName(jiaShiYuan.getCaozuoren());
				anquanzerenshu.setAjaCreateByIds(jiaShiYuan.getCaozuorenid().toString());
				anquanzerenshu.setAjaCreateTime(DateUtil.now());
				anquanzerenshu.setAjaDelete("0");
				anquanzerenshu.setAjaAjIds(jiaShiYuan.getId());
				anquanzerenshu.setAjaStartDate(driver.getAnquanzerenshuqishiriqi());
				isDataValidity = anquanzerenshuService.save(anquanzerenshu);
			}

			//向驾驶员职业危害告知书信息表添加数据
			AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishu = new AnbiaoJiashiyuanWeihaigaozhishu();
			QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishuQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWeihaigaozhishu>();
			weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwAjIds, jiaShiYuan.getId());
			weihaigaozhishuQueryWrapper.lambda().eq(AnbiaoJiashiyuanWeihaigaozhishu::getAjwDelete, "0");
			AnbiaoJiashiyuanWeihaigaozhishu whgzsdeail = weihaigaozhishuService.getBaseMapper().selectOne(weihaigaozhishuQueryWrapper);
			if (whgzsdeail == null) {
				weihaigaozhishu.setAjwCreateByName(jiaShiYuan.getCaozuoren());
				weihaigaozhishu.setAjwCreateByIds(jiaShiYuan.getCaozuorenid().toString());
				weihaigaozhishu.setAjwCreateTime(DateUtil.now());
				weihaigaozhishu.setAjwDelete("0");
				weihaigaozhishu.setAjwAjIds(jiaShiYuan.getId());
				weihaigaozhishu.setAjwStartDate(driver.getGangweigaozhishuqishiriqi());
				isDataValidity = weihaigaozhishuService.save(weihaigaozhishu);
			}

			//向劳动合同信息表添加数据
			AnbiaoJiashiyuanLaodonghetong laodonghetong = new AnbiaoJiashiyuanLaodonghetong();
			QueryWrapper<AnbiaoJiashiyuanLaodonghetong> laodonghetongQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanLaodonghetong>();
			laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwAjIds, jiaShiYuan.getId());
			laodonghetongQueryWrapper.lambda().eq(AnbiaoJiashiyuanLaodonghetong::getAjwDelete, "0");
			AnbiaoJiashiyuanLaodonghetong ldhtdeail = laodonghetongService.getBaseMapper().selectOne(laodonghetongQueryWrapper);
			if (ldhtdeail == null) {
				laodonghetong.setAjwCreateByName(jiaShiYuan.getCaozuoren());
				laodonghetong.setAjwCreateByIds(jiaShiYuan.getCaozuorenid().toString());
				laodonghetong.setAjwCreateTime(DateUtil.now());
				laodonghetong.setAjwDelete("0");
				laodonghetong.setAjwAjIds(jiaShiYuan.getId());
				laodonghetong.setAjwStartDate(driver.getLaodonghetongkaishiriqi());
				laodonghetong.setAjwEndDate(driver.getLaodonghetongjieshuriqi());
				isDataValidity = laodonghetongService.save(laodonghetong);
			}

			//向其他信息表添加数据
			AnbiaoJiashiyuanQita qita = new AnbiaoJiashiyuanQita();
			QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanQita>();
			qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, jiaShiYuan.getId());
			qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
			AnbiaoJiashiyuanQita qtdeail = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);
			if (qtdeail == null) {
				qita.setAjtCreateByName(jiaShiYuan.getCaozuoren());
				qita.setAjtCreateByIds(jiaShiYuan.getCaozuorenid().toString());
				qita.setAjtCreateTime(DateUtil.now());
				qita.setAjtDelete("0");
				qita.setAjtAjIds(jiaShiYuan.getId());
				isDataValidity = qitaService.save(qita);

			}
		}
		if (isDataValidity == true) {
			rs.setCode(200);
			rs.setMsg("数据导入成功");
			rs.setSuccess(true);
			rs.setData(drivers);
			return rs;
		} else {
			rs.setCode(500);
			rs.setMsg("数据导入失败");
			rs.setData(drivers);
			return rs;
		}
	}

	/**
	 * 删除
	 */
	@GetMapping("/dimission")
	@ApiLog("离职")
	@ApiOperation(value = "离职", notes = "传入id", position = 33)
	public R dimission(String id, BladeUser user) {
		R r = new R();
		if(user == null) {
			r.setCode(401);
			r.setMsg("未授权，请重新登录！");
			return r;
		}
		JiaShiYuan jiaShiYuan = new JiaShiYuan();
		jiaShiYuan.setStatus(1);
		jiaShiYuan.setId(id);
		jiaShiYuan.setCaozuorenid(user.getUserId());
		jiaShiYuan.setCaozuoren(user.getUserName());
		jiaShiYuan.setCaozuoshijian(DateUtil.now());
		boolean i = iJiaShiYuanService.updateById(jiaShiYuan);
		if(i){
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("离职成功");
		}else{
			r.setSuccess(false);
			r.setCode(500);
			r.setMsg("离职失败");
		}
		return r;
	}

}
