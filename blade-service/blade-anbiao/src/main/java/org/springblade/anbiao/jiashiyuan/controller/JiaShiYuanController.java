package org.springblade.anbiao.jiashiyuan.controller;

import cn.afterturn.easypoi.word.entity.WordImageEntity;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.service.*;
import org.springblade.anbiao.configure.service.IConfigureService;
import org.springblade.anbiao.guanlijigouherenyuan.service.IBladeDeptService;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.service.*;
import org.springblade.anbiao.jiashiyuan.vo.*;
import org.springblade.anbiao.risk.controller.AnbiaoRiskDetailController;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetailInfo;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailInfoService;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springblade.common.configurationBean.AlarmServer;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.common.constant.CommonConstant;
import org.springblade.common.constant.FilePathConstant;
import org.springblade.common.tool.*;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.system.entity.Dept;
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
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Float.NaN;

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
	private AlarmServer alarmServer;
	private FileServer fileServer;
	private IVehicleJishupingdingService jishupingdingService;
	private IVehicleDengjizhengshuService dengjizhengshuService;
	private IVehicleJingyingxukezhengService jingyingxukezhengService;
	private IVehicleXingnengbaogaoService xingnengbaogaoService;
	private IVehicleXingshizhengService xingshizhengService;
	private IVehicleDaoluyunshuzhengService daoluyunshuzhengService;
	@Autowired
	private AnbiaoRiskDetailController riskDetailController;


	@PostMapping("/insert")
	@ApiLog("新增-驾驶员资料管理")
	@ApiOperation(value = "新增-驾驶员资料管理", notes = "传入jiaShiYuan", position = 1)
	public R insert(@RequestBody JiaShiYuan jiaShiYuan, BladeUser user) throws ParseException {
		R r = new R();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, jiaShiYuan.getId());
//		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShoujihaoma, jiaShiYuan.getShoujihaoma());
//		if(StringUtils.isNotEmpty(jiaShiYuan.getShenfenzhenghao()) && jiaShiYuan.getShenfenzhenghao() != "null"){
//			jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShenfenzhenghao, jiaShiYuan.getShenfenzhenghao());
//		}
//		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId, jiaShiYuan.getDeptId());
//		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
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
						int tijianyouxiaoqi = Integer.parseInt(tijianriqi.substring(0, 4)) + 1;
						String tijianyouxiaoqis = String.valueOf(tijianyouxiaoqi);
						String tijianyouxiaoqiss = tijianyouxiaoqis + tijianriqi.substring(4, 10);
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

		jiaShiYuan.setXingbie("1");
		//验证身份证
		if (StringUtils.isNotEmpty(jiaShiYuan.getShenfenzhenghao()) && jiaShiYuan.getShenfenzhenghao() != null) {
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

		JiaShiYuan jiaShiYuan1 = jiaShiYuan;
		//新增
		if (deail == null) {
			//验证手机号码
			if (StringUtils.isBlank(jiaShiYuan.getShoujihaoma())) {
				r.setMsg("手机号码不能为空;");
				r.setCode(500);
				r.setSuccess(false);
				return r;
			} else {
				if (RegexUtils.checkMobile(jiaShiYuan.getShoujihaoma())) {
					QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper1 = new QueryWrapper<JiaShiYuan>();
					jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getShoujihaoma, jiaShiYuan.getShoujihaoma());
//					jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getDeptId, jiaShiYuan.getDeptId());
					jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getIsdelete, 0);
					JiaShiYuan jiaShiYuan2 = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper1);
					if (jiaShiYuan2 != null) {
						r.setMsg("该手机号码已存在;");
						r.setCode(500);
						r.setSuccess(false);
						return r;
					}
				} else {
					r.setMsg("该手机号码不合法;");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
			}

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
				QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper2 = new QueryWrapper<JiaShiYuan>();
				jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getShoujihaoma, jiaShiYuan.getShoujihaoma());
				if (StringUtils.isNotEmpty(jiaShiYuan.getShenfenzhenghao()) && jiaShiYuan.getShenfenzhenghao() != "null") {
					jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getShenfenzhenghao, jiaShiYuan.getShenfenzhenghao());
				}
				jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getDeptId, jiaShiYuan.getDeptId());
				jiaShiYuanQueryWrapper2.lambda().eq(JiaShiYuan::getIsdelete, 0);
				JiaShiYuan jiaShiYuan2 = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper2);
				jiaShiYuan.setId(jiaShiYuan2.getId());

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
					if (StringUtils.isNotEmpty(jiaShiYuan.getNianling()) && jiaShiYuan.getNianling() != null) {
						ruzhi.setAjrAge(Integer.valueOf(jiaShiYuan.getNianling()));
					}
					if (StringUtils.isNotEmpty(jiaShiYuan.getShenfenzhenghao()) && jiaShiYuan.getShenfenzhenghao() != null) {
						ruzhi.setAjrIdNumber(jiaShiYuan.getShenfenzhenghao());
					}
					ruzhi.setAjrApproverStatus("0");
					if (StringUtils.isNotEmpty(jiaShiYuan.getChushengshijian()) && jiaShiYuan.getChushengshijian() != null) {
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
			if (StringUtils.isNotEmpty(jiaShiYuan.getShenfenzhenghao()) && jiaShiYuan.getShenfenzhenghao() != null) {
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

			String jiashiyuanId = jiaShiYuan.getId();
			riskDetailController.jiashiyuanShenFenZhengRiskinsert(jiashiyuanId, user);

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
	public R<JiaShiYuanPage<JiaShiYuanListVO>> list(@RequestBody JiaShiYuanPage jiaShiYuanPage) {
		jiaShiYuanPage.setJiashiyuanleixing("驾驶员");
		JiaShiYuanPage<JiaShiYuanListVO> pages = iJiaShiYuanService.selectPageList(jiaShiYuanPage);
		return R.data(pages);
	}

	@GetMapping("/getDriverInfo")
	@ApiLog("根据驾驶员ID获取个人详细信息（司机小程序个人中心）")
	@ApiOperation(value = "根据驾驶员ID获取个人详细信息（司机小程序个人中心）", notes = "传入id", position = 1)
	public R getDriverInfo(String jsyId) {
		DriverInfoVO driverInfoVO = iJiaShiYuanService.selectDriverInfo(jsyId);
		if(driverInfoVO != null){
			if (StrUtil.isNotEmpty(driverInfoVO.getZhaopian()) && driverInfoVO.getZhaopian().contains("http") == false) {
				driverInfoVO.setZhaopian(fileUploadClient.getUrl(driverInfoVO.getZhaopian()));
			}
		}
		return R.data(driverInfoVO);
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
					driver.setXingbie("1");
					driver.setImportUrl("icon_gou.png");
				}
			}

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

			//验证驾驶员是否存在
			QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper1 = new QueryWrapper<>();
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getShoujihaoma, driver.getShoujihaoma());
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getJiashiyuanxingming, driver.getJiashiyuanxingming());
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getIsdelete, 0);
			JiaShiYuan jiaShiYuan = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper1);
			if (jiaShiYuan != null) {
				driver.setMsg(jiaShiYuan.getJiashiyuanxingming() + "该驾驶员已存在;");
				errorStr += jiaShiYuan.getJiashiyuanxingming() + "该驾驶员已存在;";
				driver.setImportUrl("icon_cha.png");
				bb++;
			}

			//验证驾驶车辆
			String vehicle = String.valueOf(a.get("驾驶车辆")).trim();
			if (StringUtils.isNotBlank(vehicle) && !vehicle.equals("null")) {
				driver.setCheliangpaizhao(vehicle);
				driver.setImportUrl("icon_gou.png");
			}

			//验证挂车号码
			String trailerNumber = String.valueOf(a.get("挂车号码")).trim();
			if (StringUtils.isNotBlank(trailerNumber) && !trailerNumber.equals("null")) {
				driver.setTrailerNumber(trailerNumber);
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
			} else if (StringUtils.isNotBlank(driver.getLaodonghetongkaishiriqi()) && !driver.getLaodonghetongkaishiriqi().equals("null")) {
				driver.setPingyongriqi(driver.getLaodonghetongkaishiriqi());
				driver.setImportUrl("icon_gou.png");
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
					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getShenfenzhenghao, tmp);
					jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
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
				if (shenfenzhengyouxiaoqi.length() >= 10 || shenfenzhengyouxiaoqi.equals("长期")) {
					if (!shenfenzhengyouxiaoqi.equals("长期")) {
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
					} else if (shenfenzhengyouxiaoqi.equals("长期")) {
						driver.setShenfenzhengyouxiaoqi(shenfenzhengyouxiaoqi);
						driver.setImportUrl("icon_gou.png");
					}
				} else {
					driver.setMsg(shenfenzhengyouxiaoqi + ",该身份证有效期,不是时间格式;");
					errorStr += shenfenzhengyouxiaoqi + ",该身份证有效期,不是时间格式;";
					driver.setImportUrl("icon_cha.png");
					bb++;
				}
			}

			//验证 身份证：开始日期 不能大于 身份证：结束日期
			if (StringUtils.isNotBlank(shenfenzhengchulingriqi) && !shenfenzhengchulingriqi.equals("null") && StringUtils.isNotBlank(shenfenzhengyouxiaoqi) && !shenfenzhengyouxiaoqi.equals("null") && !shenfenzhengyouxiaoqi.equals("长期")) {
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
					} else if (jiashizhengyouxiaoqi.equals("长期")) {
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
			if (StringUtils.isNotBlank(jiashizhengchulingriqi) && !jiashizhengchulingriqi.equals("null") && StringUtils.isNotBlank(jiashizhengyouxiaoqi) && !jiashizhengyouxiaoqi.equals("null") && !jiashizhengyouxiaoqi.equals("长期")) {
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
	public R driverDeptImportOk(@RequestParam(value = "drivers") String drivers, BladeUser user) {
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
			if (StringUtils.isNotBlank(String.valueOf(a.get("shenfenzhenghao")).trim()) && !String.valueOf(a.get("shenfenzhenghao")).equals("null")) {
				String tmp = String.valueOf(a.get("shenfenzhenghao")).trim();
				driver.setShenfenzhenghao(tmp);
				if (StringUtils.isNotBlank(tmp) && !tmp.equals("null")) {
					//通过身份证获取年龄
					Integer age = IdCardUtil.getAgeByCard(tmp);
					driver.setNianling(age.toString());
					//通过身份证获取生日日期
					Date chushengshijian = IdCardUtil.getBirthDate(tmp);
					driver.setChushengshijian(dateFormat2.format(chushengshijian));
				} else {
					driver.setNianling("0");
				}
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("xingbie")).trim()) && !String.valueOf(a.get("xingbie")).equals("null")) {
				driver.setXingbie(String.valueOf(a.get("xingbie")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("cheliangpaizhao")).trim()) && !String.valueOf(a.get("cheliangpaizhao")).equals("null")) {
				driver.setCheliangpaizhao(String.valueOf(a.get("cheliangpaizhao")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("trailerNumber")).trim()) && !String.valueOf(a.get("trailerNumber")).equals("null")) {
				driver.setTrailerNumber(String.valueOf(a.get("trailerNumber")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("pingyongriqi")).trim()) && !String.valueOf(a.get("pingyongriqi")).equals("null")) {
				driver.setPingyongriqi(String.valueOf(a.get("pingyongriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("laodonghetongkaishiriqi")).trim()) && !String.valueOf(a.get("laodonghetongkaishiriqi")).equals("null")) {
				driver.setLaodonghetongkaishiriqi(String.valueOf(a.get("laodonghetongkaishiriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("laodonghetongjieshuriqi")).trim()) && !String.valueOf(a.get("laodonghetongjieshuriqi")).equals("null")) {
				driver.setLaodonghetongjieshuriqi(String.valueOf(a.get("laodonghetongjieshuriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("anquanzerenshuqishiriqi")).trim()) && !String.valueOf(a.get("anquanzerenshuqishiriqi")).equals("null")) {
				driver.setAnquanzerenshuqishiriqi(String.valueOf(a.get("anquanzerenshuqishiriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("gangweigaozhishuqishiriqi")).trim()) && !String.valueOf(a.get("gangweigaozhishuqishiriqi")).equals("null")) {
				driver.setGangweigaozhishuqishiriqi(String.valueOf(a.get("gangweigaozhishuqishiriqi")).trim());
			}
			driver.setJiashiyuanleixing("驾驶员");
			String shoujihaoma = String.valueOf(a.get("shoujihaoma")).trim();
			driver.setShoujihaoma(shoujihaoma);
			if (StringUtils.isNotBlank(String.valueOf(a.get("congyerenyuanleixing")).trim()) && !String.valueOf(a.get("congyerenyuanleixing")).equals("null")) {
				driver.setCongyerenyuanleixing(String.valueOf(a.get("congyerenyuanleixing")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashizhenghao")).trim()) && !String.valueOf(a.get("jiashizhenghao")).equals("null")) {
				driver.setJiashizhenghao(String.valueOf(a.get("jiashizhenghao")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("congyezigezheng")).trim()) && !String.valueOf(a.get("congyezigezheng")).equals("null")) {
				driver.setCongyezigezheng(String.valueOf(a.get("congyezigezheng")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("congyezhengchulingri")).trim()) && !String.valueOf(a.get("congyezhengchulingri")).equals("null")) {
				driver.setCongyezhengchulingri(String.valueOf(a.get("congyezhengchulingri")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("congyezhengyouxiaoqi")).trim()) && !String.valueOf(a.get("congyezhengyouxiaoqi")).equals("null")) {
				driver.setCongyezhengyouxiaoqi(String.valueOf(a.get("congyezhengyouxiaoqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashizhengchulingriqi")).trim()) && !String.valueOf(a.get("jiashizhengchulingriqi")).equals("null")) {
				driver.setJiashizhengchulingriqi(String.valueOf(a.get("jiashizhengchulingriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("jiashizhengyouxiaoqi")).trim()) && !String.valueOf(a.get("jiashizhengyouxiaoqi")).equals("null")) {
				driver.setJiashizhengyouxiaoqi(String.valueOf(a.get("jiashizhengyouxiaoqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("shenfenzhengchulingriqi")).trim()) && !String.valueOf(a.get("shenfenzhengchulingriqi")).equals("null")) {
				driver.setShenfenzhengchulingriqi(String.valueOf(a.get("shenfenzhengchulingriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("shenfenzhengyouxiaoqi")).trim()) && !String.valueOf(a.get("shenfenzhengyouxiaoqi")).equals("null")) {
				driver.setShenfenzhengyouxiaoqi(String.valueOf(a.get("shenfenzhengyouxiaoqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("laodonghetongkaishiriqi")).trim()) && !String.valueOf(a.get("laodonghetongkaishiriqi")).equals("null")) {
				driver.setLaodonghetongkaishiriqi(String.valueOf(a.get("laodonghetongkaishiriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("laodonghetongjieshuriqi")).trim()) && !String.valueOf(a.get("laodonghetongjieshuriqi")).equals("null")) {
				driver.setLaodonghetongjieshuriqi(String.valueOf(a.get("laodonghetongjieshuriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("anquanzerenshuqishiriqi")).trim()) && !String.valueOf(a.get("anquanzerenshuqishiriqi")).equals("null")) {
				driver.setAnquanzerenshuqishiriqi(String.valueOf(a.get("anquanzerenshuqishiriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("gangweigaozhishuqishiriqi")).trim()) && !String.valueOf(a.get("gangweigaozhishuqishiriqi")).equals("null")) {
				driver.setGangweigaozhishuqishiriqi(String.valueOf(a.get("gangweigaozhishuqishiriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("wuzhongdazerenshiguqishiriqi")).trim()) && !String.valueOf(a.get("wuzhongdazerenshiguqishiriqi")).equals("null")) {
				driver.setWuzhongdazerenshiguqishiriqi(String.valueOf(a.get("wuzhongdazerenshiguqishiriqi")).trim());
			}
			if (StringUtils.isNotBlank(String.valueOf(a.get("tijianriqi")).trim()) && !String.valueOf(a.get("tijianriqi")).equals("null")) {
				driver.setTijianriqi(String.valueOf(a.get("tijianriqi")).trim());
			}
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
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getDeptId, driver.getDeptId());
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getJiashiyuanxingming, driver.getJiashiyuanxingming());
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getShoujihaoma, driver.getShoujihaoma());
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getIsdelete, 0);
			JiaShiYuan jiaShiYuan1 = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper1);
			if (jiaShiYuan1 != null) {
				driver.setId(jiaShiYuan1.getId());
				isDataValidity = iJiaShiYuanService.updateById(driver);
			} else {
				String id = IdUtil.simpleUUID();
				driver.setId(id);
				isDataValidity = iJiaShiYuanService.insertSelective(driver);
			}

			QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<JiaShiYuan>();
			jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, driver.getId());
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
				if (StringUtils.isNotBlank(jiaShiYuan.getXingbie()) && !jiaShiYuan.getXingbie().equals("null")) {
					ruzhi.setAjrSex(jiaShiYuan.getXingbie());
				}
				if (StringUtils.isNotBlank(jiaShiYuan.getNianling()) && !jiaShiYuan.getNianling().equals("null")) {
					ruzhi.setAjrAge(Integer.valueOf(jiaShiYuan.getNianling()));
				}
				if (StringUtils.isNotBlank(jiaShiYuan.getShenfenzhenghao()) && !jiaShiYuan.getShenfenzhenghao().equals("null")) {
					ruzhi.setAjrIdNumber(jiaShiYuan.getShenfenzhenghao());
				}
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
				if (StringUtils.isNotBlank(jiaShiYuan.getJiashizhenghao()) && !jiaShiYuan.getJiashizhenghao().equals("null")) {
					jiashizheng.setAjjFileNo(jiaShiYuan.getJiashizhenghao());
				}
				if (StringUtils.isNotBlank(jiaShiYuan.getJiashizhengchulingriqi()) && !jiaShiYuan.getJiashizhengchulingriqi().equals("null")) {
					jiashizheng.setAjjValidPeriodStart(jiaShiYuan.getJiashizhengchulingriqi());
				}
				if (StringUtils.isNotBlank(jiaShiYuan.getJiashizhengyouxiaoqi()) && !jiaShiYuan.getJiashizhengyouxiaoqi().equals("null")) {
					jiashizheng.setAjjValidPeriodEnd(jiaShiYuan.getJiashizhengyouxiaoqi());
				}
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
				if (StringUtils.isNotBlank(jiaShiYuan.getCongyezigezheng()) && !jiaShiYuan.getCongyezigezheng().equals("null")) {
					congyezigezheng.setAjcCertificateNo(jiaShiYuan.getCongyezigezheng());
				}
				if (StringUtils.isNotBlank(jiaShiYuan.getCongyezhengchulingri()) && !jiaShiYuan.getCongyezhengchulingri().equals("null")) {
					congyezigezheng.setAjcInitialIssuing(jiaShiYuan.getCongyezhengchulingri());
				}
				if (StringUtils.isNotBlank(jiaShiYuan.getCongyezhengyouxiaoqi()) && !jiaShiYuan.getCongyezhengyouxiaoqi().equals("null")) {
					congyezigezheng.setAjcValidUntil(jiaShiYuan.getCongyezhengyouxiaoqi());
				}
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
				if (StringUtils.isNotBlank(jiaShiYuan.getTijianriqi()) && !jiaShiYuan.getTijianriqi().equals("null")) {
					tijian.setAjtPhysicalExaminationDate(jiaShiYuan.getTijianriqi());
				}
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
				if (StringUtils.isNotBlank(driver.getWuzhongdazerenshiguqishiriqi()) && !driver.getWuzhongdazerenshiguqishiriqi().equals("null")) {
					wuzezhengming.setAjwStartDate(driver.getWuzhongdazerenshiguqishiriqi());
				}
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
				if (StringUtils.isNotBlank(driver.getAnquanzerenshuqishiriqi()) && !driver.getAnquanzerenshuqishiriqi().equals("null")) {
					anquanzerenshu.setAjaStartDate(driver.getAnquanzerenshuqishiriqi());
				}
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
				if (StringUtils.isNotBlank(driver.getGangweigaozhishuqishiriqi()) && !driver.getGangweigaozhishuqishiriqi().equals("null")) {
					weihaigaozhishu.setAjwStartDate(driver.getGangweigaozhishuqishiriqi());
				}
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
				if (StringUtils.isNotBlank(driver.getLaodonghetongkaishiriqi()) && !driver.getLaodonghetongkaishiriqi().equals("null")) {
					laodonghetong.setAjwStartDate(driver.getLaodonghetongkaishiriqi());
				}
				if (StringUtils.isNotBlank(driver.getLaodonghetongjieshuriqi()) && !driver.getLaodonghetongjieshuriqi().equals("null")) {
					laodonghetong.setAjwEndDate(driver.getLaodonghetongjieshuriqi());
				}
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

			//驾驶车辆
			if (StringUtils.isNotBlank(driver.getCheliangpaizhao()) && !driver.getCheliangpaizhao().equals("null")) {
				QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
				vehicleQueryWrapper.lambda().eq(Vehicle::getDeptId, driver.getDeptId());
				vehicleQueryWrapper.lambda().eq(Vehicle::getCheliangpaizhao, driver.getCheliangpaizhao());
				vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
				Vehicle vehicle = vehicleService.getBaseMapper().selectOne(vehicleQueryWrapper);
				if (vehicle != null) {
					vehicle.setJiashiyuanid(driver.getId());
					vehicle.setJiashiyuanxingming(driver.getJiashiyuanxingming());
					vehicle.setJiashiyuandianhua(driver.getShoujihaoma());
					vehicle.setChezhu(driver.getJiashiyuanxingming());
					vehicle.setChezhudianhua(driver.getShoujihaoma());
					vehicle.setCaozuoshijian(LocalDateTime.now());
					vehicleService.getBaseMapper().updateById(vehicle);
				} else {
					vehicle = new Vehicle();
					System.out.println(driver);
					System.out.println(driver.getDeptId());
					vehicle.setDeptId(driver.getDeptId());
					vehicle.setCheliangpaizhao(driver.getCheliangpaizhao());
					vehicle.setJiashiyuanid(driver.getId());
					vehicle.setJiashiyuanxingming(driver.getJiashiyuanxingming());
					vehicle.setJiashiyuandianhua(driver.getShoujihaoma());
					vehicle.setChezhu(driver.getJiashiyuanxingming());
					vehicle.setChezhudianhua(driver.getShoujihaoma());
					vehicle.setCaozuoren(driver.getCaozuoren());
					vehicle.setCaozuorenid(driver.getCaozuorenid());
					vehicle.setIsdel(0);
					vehicle.setCreatetime(LocalDateTime.now());
					vehicle.setCaozuoshijian(LocalDateTime.now());
					vehicleService.save(vehicle);

					QueryWrapper<Vehicle> vehicleQueryWrapper1 = new QueryWrapper<>();
					vehicleQueryWrapper1.lambda().eq(Vehicle::getCheliangpaizhao, vehicle.getCheliangpaizhao());
					vehicleQueryWrapper1.lambda().eq(Vehicle::getIsdel, "0");
					vehicleQueryWrapper1.lambda().eq(Vehicle::getDeptId, vehicle.getDeptId());
					Vehicle vehicle1 = vehicleService.getBaseMapper().selectOne(vehicleQueryWrapper1);

					VehicleJishupingding jishupingding = new VehicleJishupingding();        //技术评定
					jishupingding.setAvjVehicleIds(vehicle1.getId());
					jishupingding.setAvjDelete("0");
					jishupingding.setAvjCreateByName(user.getUserName());
					jishupingding.setAvjCreateByIds(user.getUserId().toString());
					jishupingding.setAvjCreateTime(LocalDateTime.now());
					jishupingdingService.save(jishupingding);

					VehicleDengjizhengshu dengjizhengshu = new VehicleDengjizhengshu();        //登记证书
					dengjizhengshu.setAvdVehicleIds(vehicle1.getId());
					dengjizhengshu.setAvdDelete("0");
					dengjizhengshu.setAvdCreateByName(user.getUserName());
					dengjizhengshu.setAvdCreateByIds(user.getUserId().toString());
					dengjizhengshu.setAvdCreateTime(LocalDateTime.now());
					dengjizhengshuService.save(dengjizhengshu);

					VehicleJingyingxukezheng jingyingxukezheng = new VehicleJingyingxukezheng();        //经营许可证
					jingyingxukezheng.setAvjVehicleIds(vehicle1.getId());
					jingyingxukezheng.setAvjOperatorName(driver.getDeptName());
					jingyingxukezheng.setAvjDelete("0");
					jingyingxukezheng.setAvjCreateByName(user.getUserName());
					jingyingxukezheng.setAvjCreateByIds(user.getUserId().toString());
					jingyingxukezheng.setAvjCreateTime(LocalDateTime.now());
					jingyingxukezhengService.save(jingyingxukezheng);

					VehicleXingnengbaogao xingnengbaogao = new VehicleXingnengbaogao();        //性能报告
					xingnengbaogao.setAvxAvIds(vehicle1.getId());
					xingnengbaogao.setAvxDelete("0");
					xingnengbaogao.setAvxCreateByName(user.getUserName());
					xingnengbaogao.setAvxCreateByIds(user.getUserId().toString());
					xingnengbaogao.setAvxCreateTime(LocalDateTime.now());
					xingnengbaogaoService.save(xingnengbaogao);

					VehicleXingshizheng xingshizheng = new VehicleXingshizheng();        //行驶证
					xingshizheng.setAvxAvIds(vehicle1.getId());
					xingshizheng.setAvxPlateNo(vehicle1.getCheliangpaizhao());
					xingshizheng.setAvxDelete("0");
					xingshizheng.setAvxCreateByName(user.getUserName());
					xingshizheng.setAvxCreateByIds(user.getUserId().toString());
					xingshizheng.setAvxCreateTime(LocalDateTime.now());
					xingshizhengService.save(xingshizheng);


					VehicleDaoluyunshuzheng daoluyunshuzheng = new VehicleDaoluyunshuzheng(); //道路运输证
					daoluyunshuzheng.setAvdAvIds(vehicle1.getId());
					daoluyunshuzheng.setAvdBusinessOwner(vehicle1.getDeptName());
					daoluyunshuzheng.setAvdPlateNo(vehicle1.getCheliangpaizhao());
					daoluyunshuzheng.setAvdDelete("0");
					daoluyunshuzheng.setAvdCreateByName(user.getUserName());
					daoluyunshuzheng.setAvdCreateByIds(user.getUserId().toString());
					daoluyunshuzheng.setAvdCreateTime(DateUtil.now());
					daoluyunshuzhengService.save(daoluyunshuzheng);
				}
			}


			//挂车号码
			if (StringUtils.isNotBlank(driver.getTrailerNumber()) && !driver.getTrailerNumber().equals("null")) {
				QueryWrapper<Vehicle> vehicleQueryWrapper2 = new QueryWrapper<>();
				vehicleQueryWrapper2.lambda().eq(Vehicle::getDeptId, driver.getDeptId());
				vehicleQueryWrapper2.lambda().eq(Vehicle::getCheliangpaizhao, driver.getTrailerNumber());
				vehicleQueryWrapper2.lambda().eq(Vehicle::getIsdel, 0);
				Vehicle vehicle2 = vehicleService.getBaseMapper().selectOne(vehicleQueryWrapper2);
				if (vehicle2 != null) {
					vehicle2.setJiashiyuanid(driver.getId());
					vehicle2.setJiashiyuanxingming(driver.getJiashiyuanxingming());
					vehicle2.setJiashiyuandianhua(driver.getShoujihaoma());
					vehicle2.setChezhu(driver.getJiashiyuanxingming());
					vehicle2.setChezhudianhua(driver.getShoujihaoma());
					vehicle2.setCaozuoshijian(LocalDateTime.now());
					vehicleService.getBaseMapper().updateById(vehicle2);
				} else {
					vehicle2 = new Vehicle();
					vehicle2.setDeptId(driver.getDeptId());
					vehicle2.setCheliangpaizhao(driver.getTrailerNumber());
					vehicle2.setJiashiyuanid(driver.getId());
					vehicle2.setJiashiyuanxingming(driver.getJiashiyuanxingming());
					vehicle2.setJiashiyuandianhua(driver.getShoujihaoma());
					vehicle2.setChezhu(driver.getJiashiyuanxingming());
					vehicle2.setChezhudianhua(driver.getShoujihaoma());
					vehicle2.setCaozuoren(driver.getCaozuoren());
					vehicle2.setCaozuorenid(driver.getCaozuorenid());
					vehicle2.setIsdel(0);
					vehicle2.setCreatetime(LocalDateTime.now());
					vehicle2.setCaozuoshijian(LocalDateTime.now());
					vehicleService.save(vehicle2);

					QueryWrapper<Vehicle> vehicleQueryWrapper3 = new QueryWrapper<>();
					vehicleQueryWrapper3.lambda().eq(Vehicle::getCheliangpaizhao, vehicle2.getCheliangpaizhao());
					vehicleQueryWrapper3.lambda().eq(Vehicle::getIsdel, "0");
					vehicleQueryWrapper3.lambda().eq(Vehicle::getDeptId, vehicle2.getDeptId());
					Vehicle vehicle3 = vehicleService.getBaseMapper().selectOne(vehicleQueryWrapper3);

					VehicleJishupingding jishupingding = new VehicleJishupingding();        //技术评定
					jishupingding.setAvjVehicleIds(vehicle3.getId());
					jishupingding.setAvjDelete("0");
					jishupingding.setAvjCreateByName(user.getUserName());
					jishupingding.setAvjCreateByIds(user.getUserId().toString());
					jishupingding.setAvjCreateTime(LocalDateTime.now());
					jishupingdingService.save(jishupingding);

					VehicleDengjizhengshu dengjizhengshu = new VehicleDengjizhengshu();        //登记证书
					dengjizhengshu.setAvdVehicleIds(vehicle3.getId());
					dengjizhengshu.setAvdDelete("0");
					dengjizhengshu.setAvdCreateByName(user.getUserName());
					dengjizhengshu.setAvdCreateByIds(user.getUserId().toString());
					dengjizhengshu.setAvdCreateTime(LocalDateTime.now());
					dengjizhengshuService.save(dengjizhengshu);

					VehicleJingyingxukezheng jingyingxukezheng = new VehicleJingyingxukezheng();        //经营许可证
					jingyingxukezheng.setAvjVehicleIds(vehicle3.getId());
					jingyingxukezheng.setAvjOperatorName(driver.getDeptName());
					jingyingxukezheng.setAvjDelete("0");
					jingyingxukezheng.setAvjCreateByName(user.getUserName());
					jingyingxukezheng.setAvjCreateByIds(user.getUserId().toString());
					jingyingxukezheng.setAvjCreateTime(LocalDateTime.now());
					jingyingxukezhengService.save(jingyingxukezheng);

					VehicleXingnengbaogao xingnengbaogao = new VehicleXingnengbaogao();        //性能报告
					xingnengbaogao.setAvxAvIds(vehicle3.getId());
					xingnengbaogao.setAvxDelete("0");
					xingnengbaogao.setAvxCreateByName(user.getUserName());
					xingnengbaogao.setAvxCreateByIds(user.getUserId().toString());
					xingnengbaogao.setAvxCreateTime(LocalDateTime.now());
					xingnengbaogaoService.save(xingnengbaogao);

					VehicleXingshizheng xingshizheng = new VehicleXingshizheng();        //行驶证
					xingshizheng.setAvxAvIds(vehicle3.getId());
					xingshizheng.setAvxPlateNo(vehicle3.getCheliangpaizhao());
					xingshizheng.setAvxDelete("0");
					xingshizheng.setAvxCreateByName(user.getUserName());
					xingshizheng.setAvxCreateByIds(user.getUserId().toString());
					xingshizheng.setAvxCreateTime(LocalDateTime.now());
					xingshizhengService.save(xingshizheng);


					VehicleDaoluyunshuzheng daoluyunshuzheng = new VehicleDaoluyunshuzheng(); //道路运输证
					daoluyunshuzheng.setAvdAvIds(vehicle3.getId());
					daoluyunshuzheng.setAvdBusinessOwner(vehicle3.getDeptName());
					daoluyunshuzheng.setAvdPlateNo(vehicle3.getCheliangpaizhao());
					daoluyunshuzheng.setAvdDelete("0");
					daoluyunshuzheng.setAvdCreateByName(user.getUserName());
					daoluyunshuzheng.setAvdCreateByIds(user.getUserId().toString());
					daoluyunshuzheng.setAvdCreateTime(DateUtil.now());
					daoluyunshuzhengService.save(daoluyunshuzheng);
				}
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
		if (user == null) {
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
		if (i) {
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("离职成功");
		} else {
			r.setSuccess(false);
			r.setCode(500);
			r.setMsg("离职失败");
		}
		return r;
	}

	@ApiLog("驾驶员信息统计表--导出")
	@ApiOperation(value = "驾驶员信息统计表--导出", notes = "传入jiaShiYuanPage", position = 7)
	@GetMapping(value = "/goExport_Get")
	public R goExport_Get(JiaShiYuanPage jiaShiYuanPage, HttpServletResponse response, BladeUser user) throws IOException {
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("未授权，请重新登录！");
			return rs;
		}
		jiaShiYuanPage.setSize(0);
		jiaShiYuanPage.setCurrent(0);
		jiaShiYuanService.selectAlarmTJMXPage(jiaShiYuanPage);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList = jiaShiYuanPage.getRecords();
		String templateFile = alarmServer.getTemplateUrl() + "模板\\A.xlsx";
		Map<String, Object> context = new HashMap<String, Object>();
		//Excel中的结果集ListData
		List<JiaShiYuanTJMX> ListData = new ArrayList<>();
		if (JiaShiYuanTJMXList.size() == 0) {

		} else if (JiaShiYuanTJMXList.size() > 30000) {
			rs.setMsg("数据超过30000条无法下载");
			rs.setCode(500);
			return rs;
		} else {
			int index = 1;
			DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			for (int i = 0; i < JiaShiYuanTJMXList.size(); i++) {
				JiaShiYuanTJMX t = JiaShiYuanTJMXList.get(i);
				JiaShiYuanTJMX JiaShiYuanTJMX = new JiaShiYuanTJMX();
				JiaShiYuanTJMX.setXuhao(index);
				JiaShiYuanTJMX.setDeptName(t.getDeptName());
				JiaShiYuanTJMX.setA(t.getA());
				if (t.getA1().equals("0")) {
					JiaShiYuanTJMX.setA1("√");
				} else {
					JiaShiYuanTJMX.setA1("×");
				}
				if (t.getA2().equals("0")) {
					JiaShiYuanTJMX.setA2("√");
				} else {
					JiaShiYuanTJMX.setA2("×");
				}
				if (t.getA3().equals("0")) {
					JiaShiYuanTJMX.setA3("√");
				} else {
					JiaShiYuanTJMX.setA3("×");
				}
				if (t.getA4().equals("0")) {
					JiaShiYuanTJMX.setA4("√");
				} else {
					JiaShiYuanTJMX.setA4("×");
				}
				if (t.getA5().equals("0")) {
					JiaShiYuanTJMX.setA5("√");
				} else {
					JiaShiYuanTJMX.setA5("×");
				}
				if (t.getA6().equals("0")) {
					JiaShiYuanTJMX.setA6("√");
				} else {
					JiaShiYuanTJMX.setA6("×");
				}
				if (t.getA7().equals("0")) {
					JiaShiYuanTJMX.setA7("√");
				} else {
					JiaShiYuanTJMX.setA7("×");
				}
				if (t.getA8().equals("0")) {
					JiaShiYuanTJMX.setA8("√");
				} else {
					JiaShiYuanTJMX.setA8("×");
				}
				if (t.getA9().equals("0")) {
					JiaShiYuanTJMX.setA9("√");
				} else {
					JiaShiYuanTJMX.setA9("×");
				}
				if (t.getA10().equals("0")) {
					JiaShiYuanTJMX.setA10("√");
				} else {
					JiaShiYuanTJMX.setA10("×");
				}
				if (t.getC1().equals("0")) {
					JiaShiYuanTJMX.setC1("√");
				} else {
					JiaShiYuanTJMX.setC1("×");
				}

				//车头
				JiaShiYuanTJMX jiaShiYuanTJMX = jiaShiYuanService.selectAlarmTJMXVehicle(JiaShiYuanTJMX);
				if (jiaShiYuanTJMX != null) {
					JiaShiYuanTJMX.setCheliangpaizhao(jiaShiYuanTJMX.getCheliangpaizhao());
					JiaShiYuanTJMX.setVehicleId(jiaShiYuanTJMX.getVehicleId());
					if (jiaShiYuanTJMX.getB1().equals("0")) {
						JiaShiYuanTJMX.setB1("√");
					} else {
						JiaShiYuanTJMX.setB1("×");
					}
					if (jiaShiYuanTJMX.getB2().equals("0")) {
						JiaShiYuanTJMX.setB2("√");
					} else {
						JiaShiYuanTJMX.setB2("×");
					}
					if (jiaShiYuanTJMX.getB3().equals("0")) {
						JiaShiYuanTJMX.setB3("√");
					} else {
						JiaShiYuanTJMX.setB3("×");
					}
					if (jiaShiYuanTJMX.getB4().equals("0")) {
						JiaShiYuanTJMX.setB4("√");
					} else {
						JiaShiYuanTJMX.setB4("×");
					}
					if (jiaShiYuanTJMX.getC2().equals("0")) {
						JiaShiYuanTJMX.setC2("√");
					} else {
						JiaShiYuanTJMX.setC2("×");
					}
				} else {
					JiaShiYuanTJMX.setCheliangpaizhao("未绑定");
					JiaShiYuanTJMX.setVehicleId("未绑定");
					JiaShiYuanTJMX.setB1("√");
					JiaShiYuanTJMX.setB2("√");
					JiaShiYuanTJMX.setB3("√");
					JiaShiYuanTJMX.setB4("√");
					JiaShiYuanTJMX.setC2("√");
				}

//				if (t.getB1().equals("0")){
//					JiaShiYuanTJMX.setB1("√");
//				}else {
//					JiaShiYuanTJMX.setB1("×");
//				}
//				if (t.getB2().equals("0")){
//					JiaShiYuanTJMX.setB2("√");
//				}else {
//					JiaShiYuanTJMX.setB2("×");
//				}
//				if (t.getB3().equals("0")){
//					JiaShiYuanTJMX.setB3("√");
//				}else {
//					JiaShiYuanTJMX.setB3("×");
//				}
//				if (t.getB4().equals("0")){
//					JiaShiYuanTJMX.setB4("√");
//				}else {
//					JiaShiYuanTJMX.setB4("×");
//				}
//				if (t.getC2().equals("0")){
//					JiaShiYuanTJMX.setC2("√");
//				}else {
//					JiaShiYuanTJMX.setC2("×");
//				}

				//挂车
				JiaShiYuanTJMX jiaShiYuanTJMX2 = jiaShiYuanService.selectAlarmTJMXVehicleGUA(JiaShiYuanTJMX);
				if (jiaShiYuanTJMX2 != null) {
					JiaShiYuanTJMX.setCheliangpaizhaoGUA(jiaShiYuanTJMX2.getCheliangpaizhaoGUA());

					JiaShiYuanTJMX.setVehicleIdGUA(jiaShiYuanTJMX2.getVehicleIdGUA());

					if (jiaShiYuanTJMX2.getB5().equals("0")) {
						JiaShiYuanTJMX.setB5("√");
					} else {
						JiaShiYuanTJMX.setB5("×");
					}
					if (jiaShiYuanTJMX2.getB6().equals("0")) {
						JiaShiYuanTJMX.setB6("√");
					} else {
						JiaShiYuanTJMX.setB6("×");
					}
					if (jiaShiYuanTJMX2.getB7().equals("0")) {
						JiaShiYuanTJMX.setB7("√");
					} else {
						JiaShiYuanTJMX.setB7("×");
					}
					if (jiaShiYuanTJMX2.getB8().equals("0")) {
						JiaShiYuanTJMX.setB8("√");
					} else {
						JiaShiYuanTJMX.setB8("×");
					}
					if (jiaShiYuanTJMX2.getC3().equals("0")) {
						JiaShiYuanTJMX.setC3("√");
					} else {
						JiaShiYuanTJMX.setC3("×");
					}
				} else {
					JiaShiYuanTJMX.setCheliangpaizhaoGUA("未绑定");
					JiaShiYuanTJMX.setVehicleIdGUA("未绑定");
					JiaShiYuanTJMX.setB5("√");
					JiaShiYuanTJMX.setB6("√");
					JiaShiYuanTJMX.setB7("√");
					JiaShiYuanTJMX.setB8("√");
					JiaShiYuanTJMX.setC3("√");
				}

//				if (t.getB5().equals("0")){
//					JiaShiYuanTJMX.setB5("√");
//				}else {
//					JiaShiYuanTJMX.setB5("×");
//				}
//				if (t.getB6().equals("0")){
//					JiaShiYuanTJMX.setB6("√");
//				}else {
//					JiaShiYuanTJMX.setB6("×");
//				}
//				if (t.getB7().equals("0")){
//					JiaShiYuanTJMX.setB7("√");
//				}else {
//					JiaShiYuanTJMX.setB7("×");
//				}
//				if (t.getB8().equals("0")){
//					JiaShiYuanTJMX.setB8("√");
//				}else {
//					JiaShiYuanTJMX.setB8("×");
//				}
//				if (t.getC3().equals("0")){
//					JiaShiYuanTJMX.setC3("√");
//				}else {
//					JiaShiYuanTJMX.setC3("×");
//				}


				ListData.add(JiaShiYuanTJMX);
				index++;
			}
		}
		String title = new String(jiaShiYuanPage.getDeptName().getBytes(StandardCharsets.UTF_8)) + "-驾驶员信息统计表";
		title = new String(title.getBytes(), "UTF-8");
		// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
		// {} 代表普通变量 {.} 代表是list的变量
		// 这里模板 删除了list以后的数据，也就是统计的这一行
		String templateFileName = templateFile;
//		alarmServer.getTemplateUrl()+
		String fileName = alarmServer.getTemplateUrl() + title + ".xlsx";
		ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
		WriteSheet writeSheet = EasyExcel.writerSheet().build();
		// 写入list之前的数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		excelWriter.fill(map, writeSheet);

		// 直接写入数据
		excelWriter.fill(ListData, writeSheet);
		excelWriter.finish();

//		ExportExcel ee=new ExportExcel();
//		ServletWebRequest servletContainer = null;
//		String msg=ee.exportExcel(templateFile,fileName, context,servletContainer);

//		ClassPathResource classPathResource = new ClassPathResource("templates/A.xlsx");
//		InputStream inputStream = classPathResource.getInputStream();
//		response.setContentType("application/vnd.ms-excel");
//		response.setCharacterEncoding("utf-8");
//		// 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
//		response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + new String(title.getBytes(StandardCharsets.UTF_8)) + ".xlsx");
//		// 如果不用模板的方式导出的话，是doWrite
//		EasyExcel.write(response.getOutputStream(),JiaShiYuanTJMX.class).withTemplate(inputStream).sheet("Sheet1").doFill(ListData);

		rs.setData(fileName);
		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setSuccess(true);
		return rs;
	}

	@ApiLog("驾驶员信息统计表--分页")
	@ApiOperation(value = "驾驶员信息统计表--分页", notes = "传入jiaShiYuanPage", position = 8)
	@PostMapping(value = "/getDriverTJ")
	public R getDriverTJ(@RequestBody JiaShiYuanPage jiaShiYuanPage) throws IOException {
		R rs = new R();
		jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList = jiaShiYuanPage.getRecords();
		JiaShiYuanTJMXList.forEach(item -> {
			if (item.getA1().equals("0")) {
				item.setA1("√");
			} else {
				item.setA1("×");
			}
			if (item.getA2().equals("0")) {
				item.setA2("√");
			} else {
				item.setA2("×");
			}
			if (item.getA3().equals("0")) {
				item.setA3("√");
			} else {
				item.setA3("×");
			}
			if (item.getA4().equals("0")) {
				item.setA4("√");
			} else {
				item.setA4("×");
			}
			if (item.getA5().equals("0")) {
				item.setA5("√");
			} else {
				item.setA5("×");
			}
			if (item.getA6().equals("0")) {
				item.setA6("√");
			} else {
				item.setA6("×");
			}
			if (item.getA7().equals("0")) {
				item.setA7("√");
			} else {
				item.setA7("×");
			}
			if (item.getA8().equals("0")) {
				item.setA8("√");
			} else {
				item.setA8("×");
			}
			if (item.getA9().equals("0")) {
				item.setA9("√");
			} else {
				item.setA9("×");
			}
			if (item.getA10().equals("0")) {
				item.setA10("√");
			} else {
				item.setA10("×");
			}
			if (item.getC1().equals("0")) {
				item.setC1("√");
			} else {
				item.setC1("×");
			}

			//车头
			JiaShiYuanTJMX jiaShiYuanTJMX = jiaShiYuanService.selectAlarmTJMXVehicle(item);
			if (jiaShiYuanTJMX != null) {
				item.setCheliangpaizhao(jiaShiYuanTJMX.getCheliangpaizhao());
				item.setVehicleId(jiaShiYuanTJMX.getVehicleId());
				if (jiaShiYuanTJMX.getB1().equals("0")) {
					item.setB1("√");
				} else {
					item.setB1("×");
				}
				if (jiaShiYuanTJMX.getB2().equals("0")) {
					item.setB2("√");
				} else {
					item.setB2("×");
				}
				if (jiaShiYuanTJMX.getB3().equals("0")) {
					item.setB3("√");
				} else {
					item.setB3("×");
				}
				if (jiaShiYuanTJMX.getB4().equals("0")) {
					item.setB4("√");
				} else {
					item.setB4("×");
				}
				if (jiaShiYuanTJMX.getC2().equals("0")) {
					item.setC2("√");
				} else {
					item.setC2("×");
				}
			} else {
				item.setCheliangpaizhao("未绑定");
				item.setVehicleId("未绑定");
				item.setB1("√");
				item.setB2("√");
				item.setB3("√");
				item.setB4("√");
				item.setC2("√");
			}

			//挂车
			JiaShiYuanTJMX jiaShiYuanTJMX2 = jiaShiYuanService.selectAlarmTJMXVehicleGUA(item);
			if (jiaShiYuanTJMX2 != null) {
				item.setCheliangpaizhaoGUA(jiaShiYuanTJMX2.getCheliangpaizhaoGUA());

				item.setVehicleIdGUA(jiaShiYuanTJMX2.getVehicleIdGUA());

				if (jiaShiYuanTJMX2.getB5().equals("0")) {
					item.setB5("√");
				} else {
					item.setB5("×");
				}
				if (jiaShiYuanTJMX2.getB6().equals("0")) {
					item.setB6("√");
				} else {
					item.setB6("×");
				}
				if (jiaShiYuanTJMX2.getB7().equals("0")) {
					item.setB7("√");
				} else {
					item.setB7("×");
				}
				if (jiaShiYuanTJMX2.getB8().equals("0")) {
					item.setB8("√");
				} else {
					item.setB8("×");
				}
				if (jiaShiYuanTJMX2.getC3().equals("0")) {
					item.setC3("√");
				} else {
					item.setC3("×");
				}
			} else {
				item.setCheliangpaizhaoGUA("未绑定");
				item.setVehicleIdGUA("未绑定");
				item.setB5("√");
				item.setB6("√");
				item.setB7("√");
				item.setB8("√");
				item.setC3("√");
			}
		});
		jiaShiYuanPage.setRecords(JiaShiYuanTJMXList);
		rs.setData(jiaShiYuanPage);
		rs.setMsg("获取成功");
		rs.setCode(200);
		return rs;
	}

	@ApiLog("驾驶员信息统计表--导出")
	@ApiOperation(value = "驾驶员信息统计表--导出", notes = "传入jiaShiYuanPage", position = 7)
	@GetMapping(value = "/goExport_GetTest")
	public R goExport_GetTest(JiaShiYuanPage jiaShiYuanPage, HttpServletResponse response, BladeUser user) throws IOException {
		R rs = new R();
		List<String> urlList = new ArrayList<>();

		String[] idsss = jiaShiYuanPage.getDeptId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i = 0; i < idsss.length; i++) {
			if (!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);
		for (int j = 0; j < idss.length; j++) {
			jiaShiYuanPage.setDeptName("");
			jiaShiYuanPage.setSize(0);
			jiaShiYuanPage.setCurrent(0);
			jiaShiYuanPage.setDeptId(idss[j]);
			jiaShiYuanService.selectAlarmTJMXPage(jiaShiYuanPage);
			List<JiaShiYuanTJMX> JiaShiYuanTJMXList = jiaShiYuanPage.getRecords();
			String templateFile = alarmServer.getTemplateUrl() + "模板\\A.xlsx";
			Map<String, Object> context = new HashMap<String, Object>();
			//Excel中的结果集ListData
			List<JiaShiYuanTJMX> ListData = new ArrayList<>();
			if (JiaShiYuanTJMXList.size() == 0) {

			} else if (JiaShiYuanTJMXList.size() > 30000) {
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			} else {
				int index = 1;
				DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				for (int i = 0; i < JiaShiYuanTJMXList.size(); i++) {
					JiaShiYuanTJMX t = JiaShiYuanTJMXList.get(i);
					JiaShiYuanTJMX JiaShiYuanTJMX = new JiaShiYuanTJMX();
					JiaShiYuanTJMX.setXuhao(index);
					JiaShiYuanTJMX.setDeptName(t.getDeptName());
					JiaShiYuanTJMX.setA(t.getA());
					if (t.getA1().equals("0")) {
						JiaShiYuanTJMX.setA1("√");
					} else {
						JiaShiYuanTJMX.setA1("×");
					}
					if (t.getA2().equals("0")) {
						JiaShiYuanTJMX.setA2("√");
					} else {
						JiaShiYuanTJMX.setA2("×");
					}
					if (t.getA3().equals("0")) {
						JiaShiYuanTJMX.setA3("√");
					} else {
						JiaShiYuanTJMX.setA3("×");
					}
					if (t.getA4().equals("0")) {
						JiaShiYuanTJMX.setA4("√");
					} else {
						JiaShiYuanTJMX.setA4("×");
					}
					if (t.getA5().equals("0")) {
						JiaShiYuanTJMX.setA5("√");
					} else {
						JiaShiYuanTJMX.setA5("×");
					}
					if (t.getA6().equals("0")) {
						JiaShiYuanTJMX.setA6("√");
					} else {
						JiaShiYuanTJMX.setA6("×");
					}
					if (t.getA7().equals("0")) {
						JiaShiYuanTJMX.setA7("√");
					} else {
						JiaShiYuanTJMX.setA7("×");
					}
					if (t.getA8().equals("0")) {
						JiaShiYuanTJMX.setA8("√");
					} else {
						JiaShiYuanTJMX.setA8("×");
					}
					if (t.getA9().equals("0")) {
						JiaShiYuanTJMX.setA9("√");
					} else {
						JiaShiYuanTJMX.setA9("×");
					}
					if (t.getA10().equals("0")) {
						JiaShiYuanTJMX.setA10("√");
					} else {
						JiaShiYuanTJMX.setA10("×");
					}
					if (t.getB1().equals("0")) {
						JiaShiYuanTJMX.setB1("√");
					} else {
						JiaShiYuanTJMX.setB1("×");
					}
					if (t.getB2().equals("0")) {
						JiaShiYuanTJMX.setB2("√");
					} else {
						JiaShiYuanTJMX.setB2("×");
					}
					if (t.getB3().equals("0")) {
						JiaShiYuanTJMX.setB3("√");
					} else {
						JiaShiYuanTJMX.setB3("×");
					}
					if (t.getB4().equals("0")) {
						JiaShiYuanTJMX.setB4("√");
					} else {
						JiaShiYuanTJMX.setB4("×");
					}
					if (t.getB5().equals("0")) {
						JiaShiYuanTJMX.setB5("√");
					} else {
						JiaShiYuanTJMX.setB5("×");
					}
					if (t.getB6().equals("0")) {
						JiaShiYuanTJMX.setB6("√");
					} else {
						JiaShiYuanTJMX.setB6("×");
					}
					if (t.getB7().equals("0")) {
						JiaShiYuanTJMX.setB7("√");
					} else {
						JiaShiYuanTJMX.setB7("×");
					}
					if (t.getB8().equals("0")) {
						JiaShiYuanTJMX.setB8("√");
					} else {
						JiaShiYuanTJMX.setB8("×");
					}
					if (t.getC1().equals("0")) {
						JiaShiYuanTJMX.setC1("√");
					} else {
						JiaShiYuanTJMX.setC1("×");
					}
					if (t.getC2().equals("0")) {
						JiaShiYuanTJMX.setC2("√");
					} else {
						JiaShiYuanTJMX.setC2("×");
					}
					if (t.getC3().equals("0")) {
						JiaShiYuanTJMX.setC3("√");
					} else {
						JiaShiYuanTJMX.setC3("×");
					}
					ListData.add(JiaShiYuanTJMX);
					index++;
					jiaShiYuanPage.setDeptName(t.getDeptName());
				}
			}
			String title = new String(jiaShiYuanPage.getDeptName().getBytes(StandardCharsets.UTF_8)) + "-驾驶员信息统计表";
			// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
			// {} 代表普通变量 {.} 代表是list的变量
			// 这里模板 删除了list以后的数据，也就是统计的这一行
			String templateFileName = templateFile;
//		alarmServer.getTemplateUrl()+
			String fileName = "D:\\ExcelTest\\" + title + idss[j] + ".xlsx";
			ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
			WriteSheet writeSheet = EasyExcel.writerSheet().build();
			// 写入list之前的数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title);
			excelWriter.fill(map, writeSheet);

			// 直接写入数据
			excelWriter.fill(ListData, writeSheet);
			excelWriter.finish();
			urlList.add(fileName);
		}

		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream("D:\\ExcelTest\\文件夹33.zip"));
		ApacheZipUtils.doCompress1(urlList, bizOut);
		//不要忘记调用
		bizOut.close();

		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setSuccess(true);
		return rs;
	}

	@GetMapping("/getDriverImg")
	@ApiLog("驾驶员-影像资料数据统计")
	@ApiOperation(value = "驾驶员-影像资料数据统计", notes = "传入jsyId", position = 31)
	public R<DriverImg> getDriverImg(String jsyId) {
		R rs = new R();
		DriverImg or = jiaShiYuanService.getByDriverImg(jsyId);
		if (or != null) {
			rs.setCode(200);
			rs.setData(or);
			rs.setMsg("获取成功");
		} else {
			rs.setCode(200);
			rs.setData(null);
			rs.setMsg("获取成功,暂无数据");
		}
		return rs;
	}

	@PostMapping("/uploadInsert")
	@ApiLog("驾驶员-影像资料数据-导入")
	@ApiOperation(value = "驾驶员-影像资料数据-导入", notes = "传入DriverImg")
	public R uploadInsert(@RequestBody DriverImg driverImg, BladeUser user) throws ParseException {
		R r = new R();
		if (user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		AnbiaoJiashiyuanRuzhi ruzhi = new AnbiaoJiashiyuanRuzhi();
		ruzhi.setAjrUpdateTime(DateUtil.now());
		ruzhi.setAjrUpdateByIds(user.getUserId().toString());
		ruzhi.setAjrUpdateByName(user.getUserName());
		ruzhi.setAjrIds(driverImg.getRuzhiid());
		if (StringUtils.isNotEmpty(driverImg.getRuzhiimg()) && driverImg.getRuzhiimg() != "null") {
			ruzhi.setAjrHeadPortrait(driverImg.getRuzhiimg());
		}
		JiaShiYuan jsy = new JiaShiYuan();
		jsy.setCaozuoshijian(DateUtil.now());
		jsy.setCaozuoren(user.getUserName());
		jsy.setCaozuorenid(user.getUserId());
		jsy.setId(driverImg.getId());
		if (StringUtils.isNotEmpty(driverImg.getSfzzmimg()) && driverImg.getSfzzmimg() != "null") {
			jsy.setShenfenzhengfujian(driverImg.getSfzzmimg());
		}
		if (StringUtils.isNotEmpty(driverImg.getSfzfmimg()) && driverImg.getSfzfmimg() != "null") {
			jsy.setShenfenzhengfanmianfujian(driverImg.getSfzfmimg());
		}
		AnbiaoJiashiyuanJiashizheng jiashizheng = new AnbiaoJiashiyuanJiashizheng();
		jiashizheng.setAjjUpdateTime(DateUtil.now());
		jiashizheng.setAjjUpdateByName(user.getUserName());
		jiashizheng.setAjjUpdateByIds(user.getUserId().toString());
		jiashizheng.setAjjIds(driverImg.getJszid());
		if (StringUtils.isNotEmpty(driverImg.getJszzmimg()) && driverImg.getJszzmimg() != "null") {
			jiashizheng.setAjjFrontPhotoAddress(driverImg.getJszzmimg());
		}
		if (StringUtils.isNotEmpty(driverImg.getJszfmimg()) && driverImg.getJszfmimg() != "null") {
			jiashizheng.setAjjAttachedPhotos(driverImg.getJszfmimg());
		}
		AnbiaoJiashiyuanCongyezigezheng congyezigezheng = new AnbiaoJiashiyuanCongyezigezheng();
		congyezigezheng.setAjcUpdateTime(DateUtil.now());
		congyezigezheng.setAjcUpdateByName(user.getUserName());
		congyezigezheng.setAjcUpdateByIds(user.getUserId().toString());
		congyezigezheng.setAjcIds(driverImg.getCyzid());
		if (StringUtils.isNotEmpty(driverImg.getCyzimg()) && driverImg.getCyzimg() != "null") {
			congyezigezheng.setAjcLicence(driverImg.getCyzimg());
		}
		AnbiaoJiashiyuanTijian tijian = new AnbiaoJiashiyuanTijian();
		tijian.setAjtUpdateTime(DateUtil.now());
		tijian.setAjtUpdateByName(user.getUserName());
		tijian.setAjtUpdateByIds(user.getUserId().toString());
		tijian.setAjtIds(driverImg.getTjid());
		if (StringUtils.isNotEmpty(driverImg.getTjimg()) && driverImg.getTjimg() != "null") {
			tijian.setAjtEnclosure(driverImg.getTjimg());
		}
		AnbiaoJiashiyuanGangqianpeixun gangqianpeixun = new AnbiaoJiashiyuanGangqianpeixun();
		gangqianpeixun.setAjgUpdateTime(DateUtil.now());
		gangqianpeixun.setAjgUpdateByName(user.getUserName());
		gangqianpeixun.setAjgUpdateByIds(user.getUserId().toString());
		gangqianpeixun.setAjgIds(driverImg.getGqid());
		if (StringUtils.isNotEmpty(driverImg.getQtimg()) && driverImg.getQtimg() != "null") {
			gangqianpeixun.setAjgTrainingEnclosure(driverImg.getQtimg());
		}
		AnbiaoJiashiyuanWuzezhengming wuzezhengming = new AnbiaoJiashiyuanWuzezhengming();
		wuzezhengming.setAjwUpdateTime(DateUtil.now());
		wuzezhengming.setAjwUpdateByName(user.getUserName());
		wuzezhengming.setAjwUpdateByIds(user.getUserId().toString());
		wuzezhengming.setAjwIds(driverImg.getWzzmid());
		if (StringUtils.isNotEmpty(driverImg.getWzzmimg()) && driverImg.getWzzmimg() != "null") {
			wuzezhengming.setAjwEnclosure(driverImg.getWzzmimg());
		}
		AnbiaoJiashiyuanQita qita = new AnbiaoJiashiyuanQita();
		qita.setAjtUpdateTime(DateUtil.now());
		qita.setAjtUpdateByName(user.getUserName());
		qita.setAjtUpdateByIds(user.getUserId().toString());
		qita.setAjtIds(driverImg.getQtid());
		if (StringUtils.isNotEmpty(driverImg.getQtimg()) && driverImg.getQtimg() != "null") {
			qita.setAjtEnclosure(driverImg.getQtimg());
		}
		//入职
		boolean ii = ruzhiService.updateById(ruzhi);
		//身份证
		ii = jiaShiYuanService.updateById(jsy);
		//驾驶证
		ii = jiashizhengService.updateById(jiashizheng);
		//从业资格证
		ii = congyezigezhengService.updateById(congyezigezheng);
		//体检
		ii = tijianService.updateById(tijian);
		//岗卡培训
		ii = gangqianpeixunService.updateById(gangqianpeixun);
		//无责证明
		ii = wuzezhengmingService.updateById(wuzezhengming);
		//其他
		ii = qitaService.updateById(qita);

		if (ii) {
			r.setMsg("导入成功");
			r.setCode(200);
			r.setSuccess(false);
		} else {
			r.setMsg("导入失败");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	@PostMapping("/exportDataWord")
	@ApiLog("驾驶员-影像资料数据-导出")
	@ApiOperation(value = "驾驶员-影像资料数据-导出", notes = "传入驾驶员ID", position = 29)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "jsyId", value = "驾驶员ID（多个以英文逗号隔开）", required = true),
	})
	public R exportDataWord(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsyId, @RequestParam String deptId, BladeUser user) throws IOException {
		R r = new R();
		if (user == null) {
			r.setMsg("未授权");
			r.setCode(401);
			return r;
		}
		Map<String, Object> params = new HashMap<>();
		String temDir = "";
		String fileName = "";
		String folder = "";
		String[] nyr = DateUtil.today().split("-");
		List<String> urlList = new ArrayList<>();
		try {
			// TODO 渲染其他类型的数据请参考官方文档
			DecimalFormat df = new DecimalFormat("######0.00");
			Calendar now = Calendar.getInstance();
			String[] idsss = jsyId.split(",");
			//去除素组中重复的数组
			List<String> listid = new ArrayList<String>();
			for (int i = 0; i < idsss.length; i++) {
				if (!listid.contains(idsss[i])) {
					listid.add(idsss[i]);
				}
			}
			//返回一个包含所有对象的指定类型的数组
			String[] idss = listid.toArray(new String[1]);
			for (int j = 0; j < idss.length; j++) {
				String url = "";
				//获取数据
				JiaShiYuan detail = iJiaShiYuanService.selectByIds(idss[j]);
				if (detail == null) {
					r.setMsg("导出失败，请校验资料数据");
					r.setCode(500);
					r.setSuccess(false);
					return r;
				}
				//word模板地址
				String templatePath = fileServer.getPathPrefix() + "muban\\" + "driverFile.docx";
				// 渲染文本
				params.put("driverName", detail.getJiashiyuanxingming());
				// 渲染图片
				WordImageEntity image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				QueryWrapper<AnbiaoJiashiyuanRuzhi> ruzhiQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanRuzhi>();
				ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrAjIds, detail.getId());
				ruzhiQueryWrapper.lambda().eq(AnbiaoJiashiyuanRuzhi::getAjrDelete, "0");
				AnbiaoJiashiyuanRuzhi ruzhiInfo = ruzhiService.getBaseMapper().selectOne(ruzhiQueryWrapper);
				if (ruzhiInfo != null) {
					//本人照片(人员头像)
					if (StrUtil.isNotEmpty(ruzhiInfo.getAjrHeadPortrait()) && ruzhiInfo.getAjrHeadPortrait().contains("http") == false) {
						ruzhiInfo.setAjrHeadPortrait(fileUploadClient.getUrl(ruzhiInfo.getAjrHeadPortrait()));
						url = ruzhiInfo.getAjrHeadPortrait();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a1", image);
					} else if (StringUtils.isNotEmpty(ruzhiInfo.getAjrHeadPortrait())) {
						url = ruzhiInfo.getAjrHeadPortrait();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a1", image);
					} else {
						params.put("a1", "未上传");
					}
				}
				//身份证
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				if (StrUtil.isNotEmpty(detail.getShenfenzhengfujian()) && detail.getShenfenzhengfujian().contains("http") == false) {
					detail.setShenfenzhengfujian(fileUploadClient.getUrl(detail.getShenfenzhengfujian()));
					url = detail.getShenfenzhengfujian();
					url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a2", image);
				} else if (StringUtils.isNotEmpty(detail.getShenfenzhengfujian())) {
					url = detail.getShenfenzhengfujian();
					url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a2", image);
				} else {
					params.put("a2", "未上传");
				}
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				if (StrUtil.isNotEmpty(detail.getShenfenzhengfanmianfujian()) && detail.getShenfenzhengfanmianfujian().contains("http") == false) {
					detail.setShenfenzhengfanmianfujian(fileUploadClient.getUrl(detail.getShenfenzhengfanmianfujian()));
					url = detail.getShenfenzhengfanmianfujian();
					url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a3", image);
				} else if (StringUtils.isNotEmpty(detail.getShenfenzhengfanmianfujian())) {
					url = detail.getShenfenzhengfanmianfujian();
					url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
					System.out.println(url);
					image.setUrl(url);
					image.setType(WordImageEntity.URL);
					params.put("a3", image);
				} else {
					params.put("a3", "未上传");
				}
				//驾驶证
				QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanJiashizheng>();
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, detail.getId());
				jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, "0");
				AnbiaoJiashiyuanJiashizheng jiashizheng = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
				if (jiashizheng != null) {
					//驾驶证正面照片
					image = new WordImageEntity();
					image.setHeight(240);
					image.setWidth(440);
					if (StrUtil.isNotEmpty(jiashizheng.getAjjFrontPhotoAddress()) && !jiashizheng.getAjjFrontPhotoAddress().contains("http")) {
						jiashizheng.setAjjFrontPhotoAddress(fileUploadClient.getUrl(jiashizheng.getAjjFrontPhotoAddress()));
						url = jiashizheng.getAjjFrontPhotoAddress();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a4", image);
					} else if (StrUtil.isNotEmpty(jiashizheng.getAjjFrontPhotoAddress())) {
						url = jiashizheng.getAjjFrontPhotoAddress();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a4", image);
					} else {
						params.put("a4", "未上传");
					}
					//驾驶证反面照片
					image = new WordImageEntity();
					image.setHeight(240);
					image.setWidth(440);
					if (StrUtil.isNotEmpty(jiashizheng.getAjjAttachedPhotos()) && !jiashizheng.getAjjAttachedPhotos().contains("http")) {
						jiashizheng.setAjjAttachedPhotos(fileUploadClient.getUrl(jiashizheng.getAjjAttachedPhotos()));
						url = jiashizheng.getAjjAttachedPhotos();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a5", image);
					} else if (StrUtil.isNotEmpty(jiashizheng.getAjjAttachedPhotos())) {
						url = jiashizheng.getAjjAttachedPhotos();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a5", image);
					} else {
						params.put("a5", "未上传");
					}
				}
				//从业资格证
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanCongyezigezheng>();
				congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, detail.getId());
				congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, "0");
				AnbiaoJiashiyuanCongyezigezheng congyezigezhengInfo = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
				if (congyezigezhengInfo != null) {
					//从业资格证照片
					if (StrUtil.isNotEmpty(congyezigezhengInfo.getAjcLicence()) && !congyezigezhengInfo.getAjcLicence().contains("http")) {
						congyezigezhengInfo.setAjcLicence(fileUploadClient.getUrl(congyezigezhengInfo.getAjcLicence()));
						url = congyezigezhengInfo.getAjcLicence();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a6", image);
					} else if (StrUtil.isNotEmpty(congyezigezhengInfo.getAjcLicence())) {
						url = congyezigezhengInfo.getAjcLicence();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a6", image);
					} else {
						params.put("a6", "未上传");
					}
				}
				//体检
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanTijian>();
				tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, detail.getId());
				tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, "0");
				AnbiaoJiashiyuanTijian tijian = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
				if (tijian != null) {
					//附件
					if (StrUtil.isNotEmpty(tijian.getAjtEnclosure()) && !tijian.getAjtEnclosure().contains("http")) {
						tijian.setAjtEnclosure(fileUploadClient.getUrl(tijian.getAjtEnclosure()));
						url = tijian.getAjtEnclosure();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a7", image);
					} else if (StrUtil.isNotEmpty(tijian.getAjtEnclosure())) {
						url = tijian.getAjtEnclosure();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a7", image);
					} else {
						params.put("a7", "未上传");
					}
				}
				//岗前培训
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				AnbiaoJiashiyuanGangqianpeixun gangqianpeixun = new AnbiaoJiashiyuanGangqianpeixun();
				QueryWrapper<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixunQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanGangqianpeixun>();
				gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgAjIds, detail.getId());
				gangqianpeixunQueryWrapper.lambda().eq(AnbiaoJiashiyuanGangqianpeixun::getAjgDelete, "0");
				AnbiaoJiashiyuanGangqianpeixun gqpxdeail = gangqianpeixunService.getBaseMapper().selectOne(gangqianpeixunQueryWrapper);
				if (gqpxdeail != null) {
					//附件
					if (StrUtil.isNotEmpty(gqpxdeail.getAjgTrainingEnclosure()) && !gqpxdeail.getAjgTrainingEnclosure().contains("http")) {
						gqpxdeail.setAjgTrainingEnclosure(fileUploadClient.getUrl(gqpxdeail.getAjgTrainingEnclosure()));
						url = gqpxdeail.getAjgTrainingEnclosure();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a8", image);
					} else if (StrUtil.isNotEmpty(gqpxdeail.getAjgTrainingEnclosure())) {
						url = gqpxdeail.getAjgTrainingEnclosure();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a8", image);
					} else {
						params.put("a8", "未上传");
					}
				}
				//无责证明
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				QueryWrapper<AnbiaoJiashiyuanWuzezhengming> wuzezhengmingQueryWrapper = new QueryWrapper<AnbiaoJiashiyuanWuzezhengming>();
				wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwAjIds, detail.getId());
				wuzezhengmingQueryWrapper.lambda().eq(AnbiaoJiashiyuanWuzezhengming::getAjwDelete, "0");
				AnbiaoJiashiyuanWuzezhengming wuzezhengming = wuzezhengmingService.getBaseMapper().selectOne(wuzezhengmingQueryWrapper);
				if (wuzezhengming != null) {
					//体检附件
					if (StrUtil.isNotEmpty(wuzezhengming.getAjwEnclosure()) && !wuzezhengming.getAjwEnclosure().contains("http")) {
						wuzezhengming.setAjwEnclosure(fileUploadClient.getUrl(wuzezhengming.getAjwEnclosure()));
						url = wuzezhengming.getAjwEnclosure();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a9", image);
					} else if (StrUtil.isNotEmpty(wuzezhengming.getAjwEnclosure())) {
						url = wuzezhengming.getAjwEnclosure();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a9", image);
					} else {
						params.put("a9", "未上传");
					}
				}
				//其他
				image = new WordImageEntity();
				image.setHeight(240);
				image.setWidth(440);
				QueryWrapper<AnbiaoJiashiyuanQita> qitaQueryWrapper = new QueryWrapper<>();
				qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtAjIds, detail.getId());
				qitaQueryWrapper.lambda().eq(AnbiaoJiashiyuanQita::getAjtDelete, "0");
				AnbiaoJiashiyuanQita qita = qitaService.getBaseMapper().selectOne(qitaQueryWrapper);
				if (qita != null) {
					//附件
					if (StrUtil.isNotEmpty(qita.getAjtEnclosure()) && !qita.getAjtEnclosure().contains("http")) {
						qita.setAjtEnclosure(fileUploadClient.getUrl(qita.getAjtEnclosure()));
						url = qita.getAjtEnclosure();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a10", image);
					} else if (StrUtil.isNotEmpty(qita.getAjtEnclosure())) {
						url = qita.getAjtEnclosure();
						url = fileServer.getPathPrefix() + org.springblade.common.tool.StringUtils.splits(url);
						System.out.println(url);
						image.setUrl(url);
						image.setType(WordImageEntity.URL);
						params.put("a10", image);
					} else {
						params.put("a10", "未上传");
					}
				}
				// TODO 渲染其他类型的数据请参考官方文档
				//=================生成文件保存在本地D盘某目录下=================
//			temDir = "D:/mimi/file/word/"; ;//生成临时文件存放地址
				nyr = DateUtil.today().split("-");
				//附件存放地址(服务器生成地址)
				temDir = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "/" + nyr[1] + "/" + nyr[2] + "/";
				//生成文件名
				Long time = new Date().getTime();
				// 生成的word格式
				String formatSuffix = ".docx";
				String wjName = detail.getJiashiyuanxingming() + "_" + "影像附件";
				// 拼接后的文件名
				fileName = wjName + formatSuffix;//文件名  带后缀
				//导出word
				String tmpPath = WordUtil2.exportDataWord3(templatePath, temDir, fileName, params, request, response);
				urlList.add(tmpPath);
			}
			folder = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "/" + nyr[1] + "/" + nyr[2] + "/" + "驾驶员影像.zip";
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

//	public static void main(String[] args) {
//		System.out.println(DigestUtil.encrypt("139788"));
//	}

	/**
	 * 修改驾驶员手机号码
	 */
	@PostMapping("/updatePhone")
	@ApiLog("修改-驾驶员手机号码")
	@ApiOperation(value = "修改-驾驶员手机号码", notes = "传入数据id，手机号码", position = 4)
	public R updatePhone(String Id, String Phone, BladeUser user) {
		R r = new R();
		if (user == null) {
			r.setCode(401);
			r.setMsg("未授权，请重新登录！");
			return r;
		}
		JiaShiYuan jiaShiYuan = new JiaShiYuan();
		jiaShiYuan.setCaozuoren(user.getUserName());
		jiaShiYuan.setCaozuorenid(user.getUserId());
		jiaShiYuan.setCaozuoshijian(DateUtil.now());
		jiaShiYuan.setId(Id);
		jiaShiYuan.setShoujihaoma(Phone);
		jiaShiYuan.setDenglumima(DigestUtil.encrypt(jiaShiYuan.getShoujihaoma().substring(jiaShiYuan.getShoujihaoma().length() - 6)));
		return R.status(jiaShiYuanService.updateById(jiaShiYuan));
	}

	@GetMapping("/goExport_getDriverTJMingXi")
	@ApiLog("驾驶员统计信息明细-导出")
	@ApiOperation(value = "驾驶员统计信息明细-导出", notes = "传入deptId，jiashiyuanId", position = 22)
	public R goExport_HiddenDanger(HttpServletRequest request, HttpServletResponse response, String deptId, String jiashiyuanId, BladeUser user) throws Exception {
		R rs = new R();
		String deptName = "";
		String uuid = UUID.randomUUID().toString().replace("-", "");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> urlList = new ArrayList<>();
		DriverTJMingXiVO driverTJMingXiVO = new DriverTJMingXiVO();
		driverTJMingXiVO.setDeptId(deptId);
		driverTJMingXiVO.setJiashiyuanId(jiashiyuanId);
		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath = fileServer.getPathPrefix() + "muban\\" + "jiashiyuanvehicle.docx";
		String templatePath2 = fileServer.getPathPrefix() + "muban\\" + "vehicle.docx";
		String folder = "";
		String[] nyr = DateUtil.today().split("-");
//		String[] idsss = driverTJMingXiVO.getDeptId().split(",");
		String[] idsss = driverTJMingXiVO.getJiashiyuanId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i = 0; i < idsss.length; i++) {
			if (!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);

		Thread thread = null;
		Thread thread1 = null;
		Thread thread2 = null;
		for (int j = 0; j < idss.length; j++) {
			driverTJMingXiVO.setDeptName("");
//			driverTJMingXiVO.setDeptId(idss[j]);
			driverTJMingXiVO.setJiashiyuanId(idss[j]);
			List<DriverTJMingXiVO> driverTJMingXiVOS = jiaShiYuanService.selectDriverTJMingXi(driverTJMingXiVO);

			if (driverTJMingXiVOS.size() == 0) {

			} else if (driverTJMingXiVOS.size() > 3000) {
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			} else {
				for (int i = 0; i < driverTJMingXiVOS.size(); i++) {
					Map<String, Object> map = new HashMap<>();
					String temDir = "";
					String fileName = "";
					String url = "";
					// 渲染文本
					DriverTJMingXiVO t = driverTJMingXiVOS.get(i);
					DriverTJMingXiVO driverTJMingXiVO1 = new DriverTJMingXiVO();
					if (StringUtils.isNotBlank(t.getJiashiyuanxingming()) && !t.getJiashiyuanxingming().equals("null")) {
						map.put("a1", t.getJiashiyuanxingming());
					} else {
						map.put("a1", "-");
					}
					driverTJMingXiVO1.setJiashiyuanxingming(t.getJiashiyuanxingming());
					if (t.getXingbie().equals("1")) {
						t.setXingbie("男");
					} else {
						t.setXingbie("女");
					}
					if (StringUtils.isNotBlank(t.getXingbie()) && !t.getXingbie().equals("null")) {
						map.put("a2", t.getXingbie());
					} else {
						map.put("a2", "-");
					}

//					map.put("a3", t.getAjrHeadPortrait());
					if (StringUtils.isNotBlank(t.getNation()) && !t.getNation().equals("null")) {
						map.put("a4", t.getNation());
					} else {
						map.put("a4", "-");
					}
					if (StringUtils.isNotBlank(t.getNativePlace()) && !t.getNativePlace().equals("null")) {
						map.put("a5", t.getNativePlace());
					} else {
						map.put("a5", "-");
					}
					if (StringUtils.isNotBlank(t.getBirth()) && !t.getBirth().equals("null")) {
						map.put("a6", t.getBirth());
					} else {
						map.put("a6", "-");
					}
					if (StringUtils.isNotBlank(t.getAge()) && !t.getAge().equals("null")) {
						map.put("a7", t.getAge());
					} else {
						map.put("a7", "-");
					}
					if (StringUtils.isNotBlank(t.getPoliticalOutlook()) && !t.getPoliticalOutlook().equals("null")) {
						map.put("a8", t.getPoliticalOutlook());
					} else {
						map.put("a8", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrEducation()) && !t.getAjrEducation().equals("null")) {
						map.put("a9", t.getAjrEducation());
					} else {
						map.put("a9", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrGraduationSchool()) && !t.getAjrGraduationSchool().equals("null")) {
						map.put("a10", t.getAjrGraduationSchool());
					} else {
						map.put("a10", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrGraduationDate()) && !t.getAjrGraduationDate().equals("null")) {
						map.put("a11", t.getAjrGraduationDate());
					} else {
						map.put("a11", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrIdNumber()) && !t.getAjrIdNumber().equals("null")) {
						map.put("a12", t.getAjrIdNumber());
					} else {
						map.put("a12", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrAddress()) && !t.getAjrAddress().equals("null")) {
						map.put("a13", t.getAjrAddress());
					} else {
						map.put("a13", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrReceiveDrivingLicense()) && !t.getAjrReceiveDrivingLicense().equals("null")) {
						map.put("a14", t.getAjrReceiveDrivingLicense());
					} else {
						map.put("a14", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrClass()) && !t.getAjrClass().equals("null")) {
						map.put("a15", t.getAjrClass());
					} else {
						map.put("a15", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrDrivingExperience()) && !t.getAjrDrivingExperience().equals("null")) {
						map.put("a16", t.getAjrDrivingExperience());
					} else {
						map.put("a16", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrHealthStatus()) && !t.getAjrHealthStatus().equals("null")) {
						map.put("a17", t.getAjrHealthStatus());
					} else {
						map.put("a17", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrWorkExperience()) && !t.getAjrWorkExperience().equals("null")) {
						map.put("a18", t.getAjrWorkExperience());
					} else {
						map.put("a18", "-");
					}
					if (t.getAjrSafeDrivingRecord1().equals("1")) {
						t.setAjrSafeDrivingRecord1("是");
					} else if (t.getAjrSafeDrivingRecord1().equals("2")) {
						t.setAjrSafeDrivingRecord1("否");
					} else {
						t.setAjrSafeDrivingRecord1(t.getAjrSafeDrivingRecord1());
					}
					if (StringUtils.isNotBlank(t.getAjrSafeDrivingRecord1()) && !t.getAjrSafeDrivingRecord1().equals("null")) {
						map.put("a19", t.getAjrSafeDrivingRecord1());
					} else {
						map.put("a19", "-");
					}
					if (t.getAjrSafeDrivingRecord2().equals("1")) {
						t.setAjrSafeDrivingRecord2("是");
					} else if (t.getAjrSafeDrivingRecord2().equals("2")) {
						t.setAjrSafeDrivingRecord2("否");
					} else {
						t.setAjrSafeDrivingRecord2(t.getAjrSafeDrivingRecord2());
					}
					if (StringUtils.isNotBlank(t.getAjrSafeDrivingRecord2()) && !t.getAjrSafeDrivingRecord2().equals("null")) {
						map.put("a20", t.getAjrSafeDrivingRecord2());
					} else {
						map.put("a20", "-");
					}
					if (t.getAjrSafeDrivingRecord3().equals("1")) {
						t.setAjrSafeDrivingRecord3("是");
					} else if (t.getAjrSafeDrivingRecord3().equals("2")) {
						t.setAjrSafeDrivingRecord3("否");
					} else {
						t.setAjrSafeDrivingRecord3(t.getAjrSafeDrivingRecord3());
					}
					if (StringUtils.isNotBlank(t.getAjrSafeDrivingRecord3()) && !t.getAjrSafeDrivingRecord3().equals("null")) {
						map.put("a21", t.getAjrSafeDrivingRecord3());
					} else {
						map.put("a21", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrPhysicalExaminationResults()) && !t.getAjrPhysicalExaminationResults().equals("null")) {
						map.put("a22", t.getAjrPhysicalExaminationResults());
					} else {
						map.put("a22", "-");
					}
					if (StringUtils.isNotBlank(t.getAjrIntegrityAssessmentResults()) && !t.getAjrIntegrityAssessmentResults().equals("null")) {
						map.put("a23", t.getAjrIntegrityAssessmentResults());
					} else {
						map.put("a23", "-");
					}

					String url1;

					// 本人照片
					WordImageEntity image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(180);
//					String jsonObject = t.getAjrHeadPortrait();
					if (StringUtils.isNotBlank(t.getAjrHeadPortrait()) && !t.getAjrHeadPortrait().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAjrHeadPortrait()) && t.getAjrHeadPortrait().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAjrHeadPortrait());
							t.setAjrHeadPortrait(url1);
							//添加图片到工作表的指定位置

//							String s = new URL(t.getAjcLicence()).toString();
							String url2 = new URL(t.getAjrHeadPortrait()).toString();
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
						} else if (StrUtil.isNotEmpty(t.getAjrHeadPortrait())) {
							String url2 = t.getAjrHeadPortrait();

							//获得第一个点的位置
							int index = url2.indexOf("/");
							System.out.println(index);
							//根据第一个点的位置 获得第二个点的位置
							index = url2.indexOf("/", index + 2);
							//根据第三个点的位置，截取 字符串。得到结果 result
							String result = url2.substring(index);
//						String subString = url2.substring(url2.indexOf("/", 3) + 1);  // 从第二个正斜杠位置往后截取整个路径
							result = result.replace("/", "\\");  // 将正斜杠替换为反斜杠

							url2 = fileServer.getPathPrefix() + result;
							System.out.println(url2);
							image.setUrl(url2);
							image.setType(WordImageEntity.URL);
							map.put("a3", image);
						}
					} else {
						map.put("a3", "-");
					}

					//身份证正面
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
					if (StringUtils.isNotBlank(t.getShenfenzhengfujian()) && !t.getShenfenzhengfujian().equals("null")) {
						if (StrUtil.isNotEmpty(t.getShenfenzhengfujian()) && t.getShenfenzhengfujian().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getShenfenzhengfujian());
							t.setShenfenzhengfujian(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getShenfenzhengfujian()).toString();
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
							map.put("b1", image);
						} else if (StrUtil.isNotEmpty(t.getShenfenzhengfujian())) {
							String url2 = t.getShenfenzhengfujian();
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
							map.put("b1", image);
						}
					} else {
						map.put("b1", "-");
					}

					//身份证反面
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
//					String jsonObject = t.getShenfenzhengfujian();
					if (StringUtils.isNotBlank(t.getShenfenzhengfanmianfujian()) && !t.getShenfenzhengfanmianfujian().equals("null")) {
						if (StrUtil.isNotEmpty(t.getShenfenzhengfanmianfujian()) && t.getShenfenzhengfanmianfujian().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getShenfenzhengfanmianfujian());
							t.setShenfenzhengfanmianfujian(url1);
							//添加图片到工作表的指定位置

//							String s = new URL(t.getAjcLicence()).toString();
							String url2 = new URL(t.getShenfenzhengfanmianfujian()).toString();
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
							map.put("b2", image);
						} else if (StrUtil.isNotEmpty(t.getShenfenzhengfanmianfujian())) {
							String url2 = t.getShenfenzhengfanmianfujian();
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
							map.put("b2", image);
						}
					} else {
						map.put("b2", "-");
					}

					//驾驶证正面
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
//					String jsonObject = t.getShenfenzhengfujian();
					if (StringUtils.isNotBlank(t.getAjjFrontPhotoAddress()) && !t.getAjjFrontPhotoAddress().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAjjFrontPhotoAddress()) && t.getAjjFrontPhotoAddress().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAjjFrontPhotoAddress());
							t.setAjjFrontPhotoAddress(url1);
							//添加图片到工作表的指定位置

//							String s = new URL(t.getAjcLicence()).toString();
							String url2 = new URL(t.getAjjFrontPhotoAddress()).toString();
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
							map.put("b3", image);
						} else if (StrUtil.isNotEmpty(t.getAjjFrontPhotoAddress())) {
							String url2 = t.getAjjFrontPhotoAddress();
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
							map.put("b3", image);
						}
					} else {
						map.put("b3", "-");
					}

					//驾驶证反面
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
//					String jsonObject = t.getShenfenzhengfujian();

					if (StringUtils.isNotBlank(t.getAjjAttachedPhotos()) && !t.getAjjAttachedPhotos().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAjjAttachedPhotos()) && t.getAjjAttachedPhotos().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAjjAttachedPhotos());
							t.setAjjAttachedPhotos(url1);
							//添加图片到工作表的指定位置

//							String s = new URL(t.getAjcLicence()).toString();
							String url2 = new URL(t.getAjjAttachedPhotos()).toString();
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
							map.put("b4", image);
						} else if (StrUtil.isNotEmpty(t.getAjjAttachedPhotos())) {
							String url2 = t.getAjjAttachedPhotos();
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
							map.put("b4", image);
						}
					} else {
						map.put("b4", "-");
					}

					//从业资格证
					image = new WordImageEntity();
					image.setHeight(850);
					image.setWidth(450);
//					String jsonObject = t.getShenfenzhengfujian();
					if (StringUtils.isNotBlank(t.getAjcLicence()) && !t.getAjcLicence().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAjcLicence()) && t.getAjcLicence().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAjcLicence());
							t.setAjcLicence(url1);
							//添加图片到工作表的指定位置

//							String s = new URL(t.getAjcLicence()).toString();
							String url2 = new URL(t.getAjcLicence()).toString();
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
							map.put("b5", image);
						} else if (StrUtil.isNotEmpty(t.getAjcLicence())) {
							String url2 = t.getAjcLicence();
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
							map.put("b5", image);
						}
					} else {
						map.put("b5", "-");
					}

					//体检表
					image = new WordImageEntity();
					image.setHeight(850);
					image.setWidth(450);
					if (StringUtils.isNotBlank(t.getAjtEnclosure()) && !t.getAjtEnclosure().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAjtEnclosure()) && t.getAjtEnclosure().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAjtEnclosure());
							t.setAjtEnclosure(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAjtEnclosure()).toString();
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
							map.put("c1", image);
						} else if (StrUtil.isNotEmpty(t.getAjtEnclosure())) {
							String url2 = t.getAjtEnclosure();
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
							map.put("c1", image);
						}
					} else {
						map.put("c1", "-");
					}

					//岗前培训
					image = new WordImageEntity();
					image.setHeight(850);
					image.setWidth(450);
					if (StringUtils.isNotBlank(t.getAjgTrainingEnclosure()) && !t.getAjgTrainingEnclosure().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAjgTrainingEnclosure()) && t.getAjgTrainingEnclosure().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAjgTrainingEnclosure());
							t.setAjgTrainingEnclosure(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAjgTrainingEnclosure()).toString();
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
							map.put("d1", image);
						} else if (StrUtil.isNotEmpty(t.getAjgTrainingEnclosure())) {
							String url2 = t.getAjgTrainingEnclosure();
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
							map.put("d1", image);
						}
					} else {
						map.put("d1", "-");
					}

					//三年无责
					image = new WordImageEntity();
					image.setHeight(270);
					image.setWidth(550);
					if (StringUtils.isNotBlank(t.getAjwEnclosure()) && !t.getAjwEnclosure().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAjwEnclosure()) && t.getAjwEnclosure().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAjwEnclosure());
							t.setAjwEnclosure(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAjwEnclosure()).toString();
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
							map.put("e1", image);
						} else if (StrUtil.isNotEmpty(t.getAjwEnclosure())) {
							String url2 = t.getAjwEnclosure();
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
							map.put("e1", image);
						}
					} else {
						map.put("e1", "-");
					}

					String year;
					String year2;
					String month;
					String day;

					//安全责任书
					map.put("f1", t.getDeptName());
					if (StringUtils.isNotBlank(t.getAjaAutographTime()) && !t.getAjaAutographTime().equals("null")) {
						Date date = sf.parse(t.getAjaAutographTime());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						year = String.valueOf(calendar.get(Calendar.YEAR));
						year2 = String.valueOf(calendar.get(Calendar.YEAR) + 1);
						month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
						day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

						map.put("f2", year);
						map.put("f3", month);
						map.put("f4", day);
						map.put("f5", year2);

					} else {
						map.put("f2", "-");
						map.put("f3", "-");
						map.put("f4", "-");
						map.put("f5", "-");
					}

					image = new WordImageEntity();
					image.setHeight(55);
					image.setWidth(80);
					if (StringUtils.isNotBlank(t.getAjaAutographEnclosure()) && !t.getAjaAutographEnclosure().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAjaAutographEnclosure()) && t.getAjaAutographEnclosure().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAjaAutographEnclosure());
							t.setAjaAutographEnclosure(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAjaAutographEnclosure()).toString();
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
							map.put("f6", image);
						} else if (StrUtil.isNotEmpty(t.getAjaAutographEnclosure())) {
							String url2 = t.getAjaAutographEnclosure();
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
							map.put("f6", image);
						}
					} else {
						map.put("f6", "-");
					}

					//危险告知书
					map.put("g1", t.getJiashiyuanxingming());
					image = new WordImageEntity();
					image.setHeight(55);
					image.setWidth(80);
					if (StringUtils.isNotBlank(t.getAjwAutographEnclosure()) && !t.getAjwAutographEnclosure().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAjwAutographEnclosure()) && t.getAjwAutographEnclosure().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAjwAutographEnclosure());
							t.setAjwAutographEnclosure(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAjwAutographEnclosure()).toString();
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
							map.put("g2", image);
						} else if (StrUtil.isNotEmpty(t.getAjwAutographEnclosure())) {
							String url2 = t.getAjwAutographEnclosure();
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
							map.put("g2", image);
						}
					} else {
						map.put("g2", "-");
					}
					if (StringUtils.isNotBlank(t.getAjwAutographTime()) && !t.getAjwAutographTime().equals("null")) {
						Date date = sf.parse(t.getAjwAutographTime());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						year = String.valueOf(calendar.get(Calendar.YEAR));
						month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
						day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

						map.put("g3", year);
						map.put("g4", month);
						map.put("g5", day);


					} else {
						map.put("g3", "-");
						map.put("g4", "-");
						map.put("g5", "-");
					}


					//劳动合同
					if (StringUtils.isNotBlank(t.getDeptName()) && !t.getDeptName().equals("null")) {
						map.put("h1", t.getDeptName());
					} else {
						map.put("h1", "-");
					}
					if (StringUtils.isNotBlank(t.getJiashiyuanxingming()) && !t.getJiashiyuanxingming().equals("null")) {
						map.put("h2", t.getJiashiyuanxingming());
					} else {
						map.put("h2", "-");
					}
					if (StringUtils.isNotBlank(t.getShenfenzhenghao()) && !t.getShenfenzhenghao().equals("null")) {
						map.put("h3", t.getShenfenzhenghao());
					} else {
						map.put("h3", "-");
					}
					if (StringUtils.isNotBlank(t.getJiatingzhuzhi()) && !t.getJiatingzhuzhi().equals("null")) {
						map.put("h4", t.getJiatingzhuzhi());
					} else {
						map.put("h4", "-");
					}
					if (StringUtils.isNotBlank(t.getShoujihaoma()) && !t.getShoujihaoma().equals("null")) {
						map.put("h5", t.getShoujihaoma());
					} else {
						map.put("h5", "-");
					}

					if (StringUtils.isNotBlank(t.getAjwStartDate()) && !t.getAjwStartDate().equals("null")) {
						Date date = sf.parse(t.getAjwStartDate());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						year = String.valueOf(calendar.get(Calendar.YEAR));
						month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
						day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

						map.put("h6", year);
						map.put("h7", month);
						map.put("h8", day);


					} else {
						map.put("h6", "-");
						map.put("h7", "-");
						map.put("h8", "-");
					}

					if (StringUtils.isNotBlank(t.getAjwEndDate()) && !t.getAjwEndDate().equals("null")) {
						Date date = sf.parse(t.getAjwEndDate());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						year = String.valueOf(calendar.get(Calendar.YEAR));
						month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
						day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

						map.put("h9", year);
						map.put("h10", month);
						map.put("h11", day);


					} else {
						map.put("h9", "-");
						map.put("h10", "-");
						map.put("h11", "-");
					}

					image = new WordImageEntity();
					image.setHeight(55);
					image.setWidth(80);
					if (StringUtils.isNotBlank(t.getAjwAutographEnclosure2()) && !t.getAjwAutographEnclosure2().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAjwAutographEnclosure2()) && t.getAjwAutographEnclosure2().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAjwAutographEnclosure2());
							t.setAjwAutographEnclosure2(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAjwAutographEnclosure2()).toString();
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
							map.put("h12", image);
						} else if (StrUtil.isNotEmpty(t.getAjwAutographEnclosure2())) {
							String url2 = t.getAjwAutographEnclosure2();
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
							map.put("h12", image);
						}
					} else {
						map.put("h12", "-");
					}

					if (StringUtils.isNotBlank(t.getAjwAutographTime2()) && !t.getAjwAutographTime2().equals("null")) {
						Date date = sf.parse(t.getAjwAutographTime2());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						year = String.valueOf(calendar.get(Calendar.YEAR));
						month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
						day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

						map.put("h13", year);
						map.put("h14", month);
						map.put("h15", day);


					} else {
						map.put("h13", "-");
						map.put("h14", "-");
						map.put("h15", "-");
					}


					//行驶证正面
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
					if (StringUtils.isNotBlank(t.getAvxOriginalEnclosure()) && !t.getAvxOriginalEnclosure().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAvxOriginalEnclosure()) && t.getAvxOriginalEnclosure().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAvxOriginalEnclosure());
							t.setAvxOriginalEnclosure(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAvxOriginalEnclosure()).toString();
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
							map.put("i1", image);
						} else if (StrUtil.isNotEmpty(t.getAvxOriginalEnclosure())) {
							String url2 = t.getAvxOriginalEnclosure();
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
							map.put("i1", image);
						}
					} else {
						map.put("i1", "-");
					}

					//行驶证反面
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
					if (StringUtils.isNotBlank(t.getAvxCopyEnclosure()) && !t.getAvxCopyEnclosure().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAvxCopyEnclosure()) && t.getAvxCopyEnclosure().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAvxCopyEnclosure());
							t.setAvxCopyEnclosure(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAvxCopyEnclosure()).toString();
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
							map.put("i2", image);
						} else if (StrUtil.isNotEmpty(t.getAvxCopyEnclosure())) {
							String url2 = t.getAvxCopyEnclosure();
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
							map.put("i2", image);
						}
					} else {
						map.put("i2", "-");
					}

					//道路运输证
					image = new WordImageEntity();
					image.setHeight(850);
					image.setWidth(450);
					if (StringUtils.isNotBlank(t.getAvdEnclosure()) && !t.getAvdEnclosure().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAvdEnclosure()) && t.getAvdEnclosure().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAvdEnclosure());
							t.setAvdEnclosure(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAvdEnclosure()).toString();
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
							map.put("i3", image);
						} else if (StrUtil.isNotEmpty(t.getAvdEnclosure())) {
							String url2 = t.getAvdEnclosure();
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
							map.put("i3", image);
						}
					} else {
						map.put("i3", "-");
					}

					//性能检测报告
					image = new WordImageEntity();
					image.setHeight(850);
					image.setWidth(450);
					if (StringUtils.isNotBlank(t.getAvxEnclosure()) && !t.getAvxEnclosure().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAvxEnclosure()) && t.getAvxEnclosure().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAvxEnclosure());
							t.setAvdEnclosure(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAvxEnclosure()).toString();
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
							map.put("i4", image);
						} else if (StrUtil.isNotEmpty(t.getAvxEnclosure())) {
							String url2 = t.getAvxEnclosure();
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
							map.put("i4", image);
						}
					} else {
						map.put("i4", "-");
					}

					//登记证书
					image = new WordImageEntity();
					image.setHeight(850);
					image.setWidth(450);
					if (StringUtils.isNotBlank(t.getAvdEnclosure2()) && !t.getAvdEnclosure2().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAvdEnclosure2()) && t.getAvdEnclosure2().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAvdEnclosure2());
							t.setAvdEnclosure(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAvdEnclosure2()).toString();
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
							map.put("i5", image);
						} else if (StrUtil.isNotEmpty(t.getAvdEnclosure2())) {
							String url2 = t.getAvdEnclosure2();
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
							map.put("i5", image);
						}
					} else {
						map.put("i5", "-");
					}

					Map<String, Object> map2 = new HashMap<>();
					//行驶证正面
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
					if (StringUtils.isNotBlank(t.getAvxOriginalEnclosureGUA()) && !t.getAvxOriginalEnclosureGUA().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAvxOriginalEnclosureGUA()) && t.getAvxOriginalEnclosureGUA().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAvxOriginalEnclosureGUA());
							t.setAvxOriginalEnclosureGUA(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAvxOriginalEnclosureGUA()).toString();
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
							map2.put("i1", image);
						} else if (StrUtil.isNotEmpty(t.getAvxOriginalEnclosureGUA())) {
							String url2 = t.getAvxOriginalEnclosureGUA();
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
							map2.put("i1", image);
						}
					} else {
						map2.put("i1", "-");
					}

					//行驶证反面
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
					if (StringUtils.isNotBlank(t.getAvxCopyEnclosureGUA()) && !t.getAvxCopyEnclosureGUA().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAvxCopyEnclosureGUA()) && t.getAvxCopyEnclosureGUA().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAvxCopyEnclosureGUA());
							t.setAvxCopyEnclosureGUA(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAvxCopyEnclosureGUA()).toString();
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
							map2.put("i2", image);
						} else if (StrUtil.isNotEmpty(t.getAvxCopyEnclosureGUA())) {
							String url2 = t.getAvxCopyEnclosureGUA();
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
							map2.put("i2", image);
						}
					} else {
						map2.put("i2", "-");
					}

					//道路运输证
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
					if (StringUtils.isNotBlank(t.getAvdEnclosureGUA()) && !t.getAvdEnclosureGUA().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAvdEnclosureGUA()) && t.getAvdEnclosureGUA().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAvdEnclosureGUA());
							t.setAvdEnclosureGUA(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAvdEnclosureGUA()).toString();
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
							map2.put("i3", image);
						} else if (StrUtil.isNotEmpty(t.getAvdEnclosureGUA())) {
							String url2 = t.getAvdEnclosureGUA();
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
							map2.put("i3", image);
						}
					} else {
						map2.put("i3", "-");
					}

					//性能检测报告
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
					if (StringUtils.isNotBlank(t.getAvxEnclosureGUA()) && !t.getAvxEnclosureGUA().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAvxEnclosureGUA()) && t.getAvxEnclosureGUA().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAvxEnclosureGUA());
							t.setAvxEnclosureGUA(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAvxEnclosureGUA()).toString();
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
							map2.put("i4", image);
						} else if (StrUtil.isNotEmpty(t.getAvxEnclosureGUA())) {
							String url2 = t.getAvxEnclosureGUA();
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
							map2.put("i4", image);
						}
					} else {
						map2.put("i4", "-");
					}

					//登记证书
					image = new WordImageEntity();
					image.setHeight(220);
					image.setWidth(350);
					if (StringUtils.isNotBlank(t.getAvdEnclosure2GUA()) && !t.getAvdEnclosure2GUA().equals("null")) {
						if (StrUtil.isNotEmpty(t.getAvdEnclosure2GUA()) && t.getAvdEnclosure2GUA().contains("http") == false) {
							url1 = fileUploadClient.getUrl(t.getAvdEnclosure2GUA());
							t.setAvdEnclosure2GUA(url1);
							//添加图片到工作表的指定位置

							String url2 = new URL(t.getAvdEnclosure2GUA()).toString();
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
							map2.put("i5", image);
						} else if (StrUtil.isNotEmpty(t.getAvdEnclosure2GUA())) {
							String url2 = t.getAvdEnclosure2GUA();
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
							map2.put("i5", image);
						}
					} else {
						map2.put("i5", "-");
					}


					// TODO 渲染其他类型的数据请参考官方文档
					//=================生成文件保存在本地D盘某目录下=================
					//			temDir = "D:/mimi/file/word/"; ;//生成临时文件存放地址
					nyr = DateUtil.today().split("-");
					//附件存放地址(服务器生成地址)
//					temDir = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+nyr[2]+"\\"+t.getDeptName()+"\\"+"驾驶员"+"\\";
//					String temDir2 = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+nyr[2]+"\\"+t.getDeptName()+"\\"+"车头"+"\\";
//					String temDir3 = fileServer.getPathPrefix()+ FilePathConstant.ENCLOSURE_PATH+nyr[0]+"\\"+nyr[1]+"\\"+nyr[2]+"\\"+t.getDeptName()+"\\"+"挂车"+"\\";

					temDir = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "\\" + nyr[1] + "\\" + uuid + "\\" + t.getDeptName() + "\\" + "驾驶员";
					String temDir2 = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "\\" + nyr[1] + "\\" + uuid + "\\" + t.getDeptName() + "\\" + "车头";
					String temDir3 = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "\\" + nyr[1] + "\\" + uuid + "\\" + t.getDeptName() + "\\" + "挂车";

					//生成文件名
					// 生成的word格式
					String formatSuffix = ".docx";
					String wjName = t.getJiashiyuanxingming();
					String wjName2 = t.getCheliangpaizhao();
					String wjName3 = t.getCheliangpaizhaoGUA();
					// 拼接后的文件名
					fileName = wjName + formatSuffix;//文件名  带后缀
					String fileName2 = wjName2 + formatSuffix;
					String fileName3 = wjName3 + formatSuffix;
					//导出word
					if (StringUtils.isNotEmpty(wjName) && wjName != "null") {
						WordUtil2.exportDataWord3(templatePath, temDir, fileName, map, request, response);
					}
					if (StringUtils.isNotEmpty(wjName2) && wjName2 != "null") {
						WordUtil2.exportDataWord3(templatePath2, temDir2, fileName2, map, request, response);
					}
					if (StringUtils.isNotEmpty(wjName3) && wjName3 != "null") {
						WordUtil2.exportDataWord3(templatePath2, temDir3, fileName3, map2, request, response);
					}
					urlList.add(temDir);


					//替换路径前缀,获得pdf文件路径
					String pdfPath = temDir + "\\" + wjName + ".pdf";
					//生成文件父级目录
					FileUtil.mkParentDirs(pdfPath);
					//生成pdf到pdf文件路径
					temDir = temDir + "\\" + wjName + formatSuffix;
					String finalTemDir = temDir;

					thread = new Thread(new Runnable() {
						@Override
						public void run() {
							// 线程执行的逻辑
							CommonUtil.world2pdf(finalTemDir, pdfPath);
							FileSystemUtils.deleteRecursively(new File(finalTemDir));
						}
					});
					thread.start();
					System.out.println("已生成驾驶员pdf" + pdfPath);
					//替换路径前缀,获得pdf文件路径
					if (StringUtils.isNotEmpty(wjName2) && wjName2 != "null") {
						String pdfPath2 = temDir2 + "\\" + wjName2 + ".pdf";
						//生成文件父级目录
						FileUtil.mkParentDirs(pdfPath2);
						//生成pdf到pdf文件路径
						temDir2 = temDir2 + "\\" + wjName2 + formatSuffix;
						String finalTemDir1 = temDir2;
						thread1 = new Thread(new Runnable() {
							@Override
							public void run() {
								// 线程执行的逻辑
								CommonUtil.world2pdf(finalTemDir1, pdfPath2);
								FileSystemUtils.deleteRecursively(new File(finalTemDir1));
							}
						});
						thread1.start();
						System.out.println("已生成车头pdf" + pdfPath2);
					}

					//替换路径前缀,获得pdf文件路径
					if (StringUtils.isNotEmpty(wjName3) && wjName3 != "null") {
						String pdfPath3 = temDir3 + "\\" + wjName3 + ".pdf";
						//生成文件父级目录
						FileUtil.mkParentDirs(pdfPath3);
						//生成pdf到pdf文件路径
						temDir3 = temDir3 + "\\" + wjName3 + formatSuffix;
						String finalTemDir2 = temDir3;
						thread2 = new Thread(new Runnable() {
							@Override
							public void run() {
								// 线程执行的逻辑
								CommonUtil.world2pdf(finalTemDir2, pdfPath3);
								FileSystemUtils.deleteRecursively(new File(finalTemDir2));
							}
						});
						thread2.start();
						System.out.println("已生成挂车pdf" + pdfPath3);
					}
					deptName = t.getDeptName();
					System.out.println(DateUtil.now());
				}
			}
		}
		// 等待子线程执行完毕
		try {
			if (thread != null) {
				thread.join();
			}
			if (thread1 != null) {
				thread1.join();
			}
			if (thread2 != null) {
				thread2.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 子线程执行完毕后的逻辑

		folder = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "/" + nyr[1] + "/" + deptName + "-人车台账.zip";
		ExcelUtils.deleteFile(folder);
//		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(folder));
//		ApacheZipUtils.doCompress1(urlList, bizOut);
		System.out.println(DateUtil.now());
		PackageToZIp.toZip(fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "\\" + nyr[1] + "\\" + uuid + "\\" + deptName, folder);
		System.out.println(DateUtil.now());
		//不要忘记调用
//		bizOut.close();

		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setData(folder);
		rs.setSuccess(true);
		return rs;
	}

	@PostMapping("/goExport_getDriver")
	@ApiLog("驾驶员信息-导出")
	@ApiOperation(value = "驾驶员信息-导出", notes = "传入jiaShiYuanPage", position = 22)
	public R goExport_getDriver(HttpServletRequest request, HttpServletResponse response, @RequestBody JiaShiYuanPage jiaShiYuanPage, BladeUser user) throws Exception {
		R rs = new R();
		String deptName = "";
		String uuid = UUID.randomUUID().toString().replace("-", "");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List<JiaShiYuanListVO> ListData = new ArrayList<>();

		// TODO 渲染其他类型的数据请参考官方文档
		DecimalFormat df = new DecimalFormat("######0.00");
		Calendar now = Calendar.getInstance();
		//word模板地址
		String templatePath = fileServer.getPathPrefix() + "muban\\" + "JiaShiYuanXinXi.xlsx";
		String folder = "";
		String[] nyr = DateUtil.today().split("-");
		String[] idsss = jiaShiYuanPage.getId().split(",");
		//去除素组中重复的数组
		List<String> listid = new ArrayList<String>();
		for (int i = 0; i < idsss.length; i++) {
			if (!listid.contains(idsss[i])) {
				listid.add(idsss[i]);
			}
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss = listid.toArray(new String[1]);

		for (int j = 0; j < idss.length; j++) {
			jiaShiYuanPage.setSize(0);
			jiaShiYuanPage.setCurrent(0);
			jiaShiYuanPage.setDeptId("1");
			jiaShiYuanPage.setId(idss[j]);
			JiaShiYuanPage<JiaShiYuanListVO> jiaShiYuanListVOJiaShiYuanPage = jiaShiYuanService.selectPageList(jiaShiYuanPage);
			List<JiaShiYuanListVO> Jiashiyuans = jiaShiYuanListVOJiaShiYuanPage.getRecords();
			if (Jiashiyuans.size() == 0) {

			} else if (Jiashiyuans.size() > 3000) {
				rs.setMsg("数据超过30000条无法下载");
				rs.setCode(500);
				return rs;
			} else {
				for (JiaShiYuanListVO t : Jiashiyuans) {
					JiaShiYuanListVO jiaShiYuanListVO = new JiaShiYuanListVO();
					jiaShiYuanListVO.setDeptName(t.getDeptName());
					jiaShiYuanListVO.setJiashiyuanxingming(t.getJiashiyuanxingming());

					if (StringUtils.isNotBlank(t.getXingbie()) && !t.getXingbie().equals("null")) {
						jiaShiYuanListVO.setXingbie(t.getXingbie());
					} else {
						jiaShiYuanListVO.setXingbie("/");
					}

					if (StringUtils.isNotBlank(t.getXingbie()) && !t.getXingbie().equals("null")) {
						jiaShiYuanListVO.setXingbie(t.getXingbie());
					} else {
						jiaShiYuanListVO.setXingbie("/");
					}

					jiaShiYuanListVO.setShoujihaoma(t.getShoujihaoma());

					if (StringUtils.isNotBlank(t.getCheliangpaizhao()) && !t.getCheliangpaizhao().equals("null")) {
						jiaShiYuanListVO.setCheliangpaizhao(t.getCheliangpaizhao());
					} else {
						jiaShiYuanListVO.setCheliangpaizhao("/");
					}

					if (StringUtils.isNotBlank(t.getSijiruzhiriqi()) && !t.getSijiruzhiriqi().equals("null")) {
						jiaShiYuanListVO.setSijiruzhiriqi(t.getSijiruzhiriqi());
					} else {
						jiaShiYuanListVO.setSijiruzhiriqi("/");
					}

					if (StringUtils.isNotBlank(t.getLaodonghetongkaishiriqi()) && !t.getLaodonghetongkaishiriqi().equals("null")) {
						jiaShiYuanListVO.setLaodonghetongkaishiriqi(t.getLaodonghetongkaishiriqi());
					} else {
						jiaShiYuanListVO.setLaodonghetongkaishiriqi("/");
					}

					if (StringUtils.isNotBlank(t.getLaodonghetongjieshuriqi()) && !t.getLaodonghetongjieshuriqi().equals("null")) {
						jiaShiYuanListVO.setLaodonghetongjieshuriqi(t.getLaodonghetongjieshuriqi());
					} else {
						jiaShiYuanListVO.setLaodonghetongjieshuriqi("/");
					}

					if (StringUtils.isNotBlank(t.getLaodonghetongshengyuyouxiaoqi()) && !t.getLaodonghetongshengyuyouxiaoqi().equals("null")) {
						jiaShiYuanListVO.setLaodonghetongshengyuyouxiaoqi(t.getLaodonghetongshengyuyouxiaoqi());
					} else {
						jiaShiYuanListVO.setLaodonghetongshengyuyouxiaoqi("/");
					}

					if (StringUtils.isNotBlank(t.getLaodonghetongshengyuyouxiaoqi()) && !t.getLaodonghetongshengyuyouxiaoqi().equals("null")) {
						jiaShiYuanListVO.setLaodonghetongshengyuyouxiaoqi(t.getLaodonghetongshengyuyouxiaoqi());
					} else {
						jiaShiYuanListVO.setLaodonghetongshengyuyouxiaoqi("/");
					}

					if (StringUtils.isNotBlank(t.getShenfenzhenghao()) && !t.getShenfenzhenghao().equals("null")) {
						jiaShiYuanListVO.setShenfenzhenghao(t.getShenfenzhenghao());
					} else {
						jiaShiYuanListVO.setShenfenzhenghao("/");
					}

					if (StringUtils.isNotBlank(t.getShenfenzhengchulingriqi()) && !t.getShenfenzhengchulingriqi().equals("null")) {
						jiaShiYuanListVO.setShenfenzhengchulingriqi(t.getShenfenzhengchulingriqi());
					} else {
						jiaShiYuanListVO.setShenfenzhengchulingriqi("/");
					}

					if (StringUtils.isNotBlank(t.getShenfenzhengyouxiaoqi()) && !t.getShenfenzhengyouxiaoqi().equals("null")) {
						jiaShiYuanListVO.setShenfenzhengyouxiaoqi(t.getShenfenzhengyouxiaoqi());
					} else {
						jiaShiYuanListVO.setShenfenzhengyouxiaoqi("/");
					}

					if (StringUtils.isNotBlank(t.getShenfenzhengshengyuyouxiaoqi()) && !t.getShenfenzhengshengyuyouxiaoqi().equals("null")) {
						jiaShiYuanListVO.setShenfenzhengshengyuyouxiaoqi(t.getShenfenzhengshengyuyouxiaoqi());
					} else {
						jiaShiYuanListVO.setShenfenzhengshengyuyouxiaoqi("/");
					}

					if (StringUtils.isNotBlank(t.getJiashizhenghao()) && !t.getJiashizhenghao().equals("null")) {
						jiaShiYuanListVO.setJiashizhenghao(t.getJiashizhenghao());
					} else {
						jiaShiYuanListVO.setJiashizhenghao("/");
					}

					if (StringUtils.isNotBlank(t.getJiashizhengkaishiriqi()) && !t.getJiashizhengkaishiriqi().equals("null")) {
						jiaShiYuanListVO.setJiashizhengkaishiriqi(t.getJiashizhengkaishiriqi());
					} else {
						jiaShiYuanListVO.setJiashizhengkaishiriqi("/");
					}

					if (StringUtils.isNotBlank(t.getJiashizhengjieshuriqi()) && !t.getJiashizhengjieshuriqi().equals("null")) {
						jiaShiYuanListVO.setJiashizhengjieshuriqi(t.getJiashizhengjieshuriqi());
					} else {
						jiaShiYuanListVO.setJiashizhengjieshuriqi("/");
					}

					if (StringUtils.isNotBlank(t.getJiashizhengjieshuriqi()) && !t.getJiashizhengjieshuriqi().equals("null")) {
						jiaShiYuanListVO.setJiashizhengjieshuriqi(t.getJiashizhengjieshuriqi());
					} else {
						jiaShiYuanListVO.setJiashizhengjieshuriqi("/");
					}

					if (StringUtils.isNotBlank(t.getJiashizhengshengyuyouxiaoqi()) && !t.getJiashizhengshengyuyouxiaoqi().equals("null")) {
						jiaShiYuanListVO.setJiashizhengshengyuyouxiaoqi(t.getJiashizhengshengyuyouxiaoqi());
					} else {
						jiaShiYuanListVO.setJiashizhengshengyuyouxiaoqi("/");
					}

					if (StringUtils.isNotBlank(t.getCongyezigezhenghao()) && !t.getCongyezigezhenghao().equals("null")) {
						jiaShiYuanListVO.setCongyezigezhenghao(t.getCongyezigezhenghao());
					} else {
						jiaShiYuanListVO.setCongyezigezhenghao("/");
					}

					if (StringUtils.isNotBlank(t.getCongyezigezhengkaishiriqi()) && !t.getCongyezigezhengkaishiriqi().equals("null")) {
						jiaShiYuanListVO.setCongyezigezhengkaishiriqi(t.getCongyezigezhengkaishiriqi());
					} else {
						jiaShiYuanListVO.setCongyezigezhengkaishiriqi("/");
					}

					if (StringUtils.isNotBlank(t.getCongyezigezhengjieshuriqi()) && !t.getCongyezigezhengjieshuriqi().equals("null")) {
						jiaShiYuanListVO.setCongyezigezhengjieshuriqi(t.getCongyezigezhengjieshuriqi());
					} else {
						jiaShiYuanListVO.setCongyezigezhengjieshuriqi("/");
					}

					if (StringUtils.isNotBlank(t.getCongyezigezhengshengyuyouxiaoqi()) && !t.getCongyezigezhengshengyuyouxiaoqi().equals("null")) {
						jiaShiYuanListVO.setCongyezigezhengshengyuyouxiaoqi(t.getCongyezigezhengshengyuyouxiaoqi());
					} else {
						jiaShiYuanListVO.setCongyezigezhengshengyuyouxiaoqi("/");
					}

					if (StringUtils.isNotBlank(t.getAnquanshengchanzerenshuqishiriqi()) && !t.getAnquanshengchanzerenshuqishiriqi().equals("null")) {
						jiaShiYuanListVO.setAnquanshengchanzerenshuqishiriqi(t.getAnquanshengchanzerenshuqishiriqi());
					} else {
						jiaShiYuanListVO.setAnquanshengchanzerenshuqishiriqi("/");
					}

					if (StringUtils.isNotBlank(t.getAnquanshengchanzerenshushengyuyouxiaoqi()) && !t.getAnquanshengchanzerenshushengyuyouxiaoqi().equals("null")) {
						jiaShiYuanListVO.setAnquanshengchanzerenshushengyuyouxiaoqi(t.getAnquanshengchanzerenshushengyuyouxiaoqi());
					} else {
						jiaShiYuanListVO.setAnquanshengchanzerenshushengyuyouxiaoqi("/");
					}

					if (StringUtils.isNotBlank(t.getGangweiweixiangaozhishuqishiriqi()) && !t.getGangweiweixiangaozhishuqishiriqi().equals("null")) {
						jiaShiYuanListVO.setGangweiweixiangaozhishuqishiriqi(t.getGangweiweixiangaozhishuqishiriqi());
					} else {
						jiaShiYuanListVO.setGangweiweixiangaozhishuqishiriqi("/");
					}

					if (StringUtils.isNotBlank(t.getGangweiweixiangaozhishushengyuyouxiaoqi()) && !t.getGangweiweixiangaozhishushengyuyouxiaoqi().equals("null")) {
						jiaShiYuanListVO.setGangweiweixiangaozhishushengyuyouxiaoqi(t.getGangweiweixiangaozhishushengyuyouxiaoqi());
					} else {
						jiaShiYuanListVO.setGangweiweixiangaozhishushengyuyouxiaoqi("/");
					}

					if (StringUtils.isNotBlank(t.getTijianbaogaoqishiriqi()) && !t.getTijianbaogaoqishiriqi().equals("null")) {
						jiaShiYuanListVO.setTijianbaogaoqishiriqi(t.getTijianbaogaoqishiriqi());
					} else {
						jiaShiYuanListVO.setTijianbaogaoqishiriqi("/");
					}

					if (StringUtils.isNotBlank(t.getTijianbaogaoshengyuyouxiaoqi()) && !t.getTijianbaogaoshengyuyouxiaoqi().equals("null")) {
						jiaShiYuanListVO.setTijianbaogaoshengyuyouxiaoqi(t.getTijianbaogaoshengyuyouxiaoqi());
					} else {
						jiaShiYuanListVO.setTijianbaogaoshengyuyouxiaoqi("/");
					}

					if (StringUtils.isNotBlank(t.getWuzhongdazerenshiguqishiriqi()) && !t.getWuzhongdazerenshiguqishiriqi().equals("null")) {
						jiaShiYuanListVO.setWuzhongdazerenshiguqishiriqi(t.getWuzhongdazerenshiguqishiriqi());
					} else {
						jiaShiYuanListVO.setWuzhongdazerenshiguqishiriqi("/");
					}

					ListData.add(jiaShiYuanListVO);
				}
			}
			// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
			// {} 代表普通变量 {.} 代表是list的变量
			// 这里模板 删除了list以后的数据，也就是统计的这一行
			String templateFileName = templatePath;
			//alarmServer.getTemplateUrl()+
			String fileName = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "/" + nyr[1] + "/" + nyr[2] + "/" + uuid + "/" + "驾驶员信息";
			File newFile = new File(fileName);
			//判断目标文件所在目录是否存在
			if (!newFile.exists()) {
				//如果目标文件所在的目录不存在，则创建父目录
				newFile.mkdirs();
			}
			fileName = fileName + "/" + "驾驶员信息.xlsx";
			ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
			WriteSheet writeSheet = EasyExcel.writerSheet().build();
//			// 写入list之前的数据
//			excelWriter.fill(map, writeSheet);
			// 直接写入数据
			excelWriter.fill(ListData, writeSheet);
			excelWriter.finish();
		}


		folder = fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "/" + nyr[1] + "/" + "驾驶员信息.zip";
		ExcelUtils.deleteFile(folder);
//		ZipOutputStream bizOut = new ZipOutputStream(new FileOutputStream(folder));
//		ApacheZipUtils.doCompress1(urlList, bizOut);
		System.out.println(DateUtil.now());
		PackageToZIp.toZip(fileServer.getPathPrefix() + FilePathConstant.ENCLOSURE_PATH + nyr[0] + "\\" + nyr[1] + "\\" + nyr[2] + "\\" + uuid + "\\" + "驾驶员信息", folder);
		System.out.println(DateUtil.now());
		//不要忘记调用
//		bizOut.close();

		rs.setMsg("下载成功");
		rs.setCode(200);
		rs.setData(folder);
		rs.setSuccess(true);
		return rs;
	}

	@ApiLog("驾驶员资料完善情况")
	@ApiOperation(value = "驾驶员资料完善情况", notes = "传入jiaShiYuanPage", position = 8)
	@PostMapping(value = "/getDriverDataPerfection")
	public R getDriverDataPerfection(@RequestBody JiaShiYuanPage jiaShiYuanPage) throws IOException {
		R rs = new R();
		String format;
		DriverDataPerfectionVO driverDataPerfectionVO = new DriverDataPerfectionVO();
		ArrayList<DriverDataPerfectionValueVO> driverDataPerfectionValueVOS = new ArrayList<>();
		QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getDeptId, jiaShiYuanPage.getDeptId());
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, "0");
		List<JiaShiYuan> jiaShiYuans = iJiaShiYuanService.getBaseMapper().selectList(jiaShiYuanQueryWrapper);
		int size = jiaShiYuans.size();

		//入职表
		JiaShiYuanPage jiaShiYuanPage1 = new JiaShiYuanPage();
		jiaShiYuanPage1.setDeptId(jiaShiYuanPage.getDeptId());
		jiaShiYuanPage1.setType("ruzhi_compeleted");
		JiaShiYuanPage<JiaShiYuanTJMX> jiaShiYuanTJMXJiaShiYuanPage = jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage1);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList = jiaShiYuanTJMXJiaShiYuanPage.getRecords();
		int sum1 = JiaShiYuanTJMXList.size();
		int sum2 = jiaShiYuans.size();
		double percentage = (double) sum1 / sum2;
		if (percentage==NaN){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			format = nt.format(percentage);
		}else {
			format="0.00%";
		}
		driverDataPerfectionVO.setRuzhi(format);
		DriverDataPerfectionValueVO driverDataPerfectionValueVO = new DriverDataPerfectionValueVO();
		driverDataPerfectionValueVO.setXiangmu("入职表");
		driverDataPerfectionValueVO.setShuju(driverDataPerfectionVO.getRuzhi());
		driverDataPerfectionValueVO.setGongsirenshu(size);
		driverDataPerfectionValueVOS.add(driverDataPerfectionValueVO);

		//身份证
		JiaShiYuanPage jiaShiYuanPage2 = new JiaShiYuanPage();
		jiaShiYuanPage2.setDeptId(jiaShiYuanPage.getDeptId());
		jiaShiYuanPage2.setType("sfz_compeleted");
		JiaShiYuanPage<JiaShiYuanTJMX> jiaShiYuanTJMXJiaShiYuanPage2 = jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage2);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList2 = jiaShiYuanTJMXJiaShiYuanPage2.getRecords();
		sum1 = JiaShiYuanTJMXList2.size();
		sum2 = jiaShiYuans.size();
		percentage = (double) sum1 / sum2;
		if (percentage==NaN){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			format = nt.format(percentage);
		}else {
			format="0.00%";
		}
//		nt = NumberFormat.getPercentInstance();
//		nt.setMinimumFractionDigits(2);
//		format = nt.format(percentage);
//		if (format.equals("�")){
//			format="0.00%";
//		}else{
//			format = nt.format(percentage);
//		}
		driverDataPerfectionVO.setShenfenzheng(format);
		DriverDataPerfectionValueVO driverDataPerfectionValueVO2 = new DriverDataPerfectionValueVO();
		driverDataPerfectionValueVO2.setXiangmu("身份证");
		driverDataPerfectionValueVO2.setShuju(driverDataPerfectionVO.getShenfenzheng());
		driverDataPerfectionValueVO2.setGongsirenshu(size);
		driverDataPerfectionValueVOS.add(driverDataPerfectionValueVO2);

		//驾驶证
		JiaShiYuanPage jiaShiYuanPage3 = new JiaShiYuanPage();
		jiaShiYuanPage3.setDeptId(jiaShiYuanPage.getDeptId());
		jiaShiYuanPage3.setType("jsz_compeleted");
		JiaShiYuanPage<JiaShiYuanTJMX> jiaShiYuanTJMXJiaShiYuanPage3 = jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage3);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList3 = jiaShiYuanTJMXJiaShiYuanPage3.getRecords();
		sum1 = JiaShiYuanTJMXList3.size();
		sum2 = jiaShiYuans.size();
		percentage = (double) sum1 / sum2;
		if (percentage==NaN){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			format = nt.format(percentage);
		}else {
			format="0.00%";
		}
//		fant = NumberFormat.getPercentInstance();
//		nt.setMinimumFractionDigits(2);
//		format = nt.format(percentage);
//		if (format.equals("�")){
//			format="0.00%";
//		}
		DriverDataPerfectionValueVO driverDataPerfectionValueVO3 = new DriverDataPerfectionValueVO();
		driverDataPerfectionVO.setJiashizheng(format);
		driverDataPerfectionValueVO3.setXiangmu("驾驶证");
		driverDataPerfectionValueVO3.setShuju(driverDataPerfectionVO.getJiashizheng());
		driverDataPerfectionValueVO3.setGongsirenshu(size);
		driverDataPerfectionValueVOS.add(driverDataPerfectionValueVO3);

		//从业资格证
		JiaShiYuanPage jiaShiYuanPage4 = new JiaShiYuanPage();
		jiaShiYuanPage4.setDeptId(jiaShiYuanPage.getDeptId());
		jiaShiYuanPage4.setType("cyzgz_compeleted");
		JiaShiYuanPage<JiaShiYuanTJMX> jiaShiYuanTJMXJiaShiYuanPage4 = jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage4);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList4 = jiaShiYuanTJMXJiaShiYuanPage4.getRecords();
		sum1 = JiaShiYuanTJMXList4.size();
		sum2 = jiaShiYuans.size();
		percentage = (double) sum1 / sum2;
		if (percentage==NaN){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			format = nt.format(percentage);
		}else {
			format="0.00%";
		}
//		nt = NumberFormat.getPercentInstance();
//		nt.setMinimumFractionDigits(2);
//		format = nt.format(percentage);
//		if (format.equals("�")){
//			format="0.00%";
//		}
		DriverDataPerfectionValueVO driverDataPerfectionValueVO4 = new DriverDataPerfectionValueVO();
		driverDataPerfectionVO.setCongyezigezheng(format);
		driverDataPerfectionValueVO4.setXiangmu("从业资格证");
		driverDataPerfectionValueVO4.setShuju(driverDataPerfectionVO.getCongyezigezheng());
		driverDataPerfectionValueVO4.setGongsirenshu(size);
		driverDataPerfectionValueVOS.add(driverDataPerfectionValueVO4);

		//体检表
		JiaShiYuanPage jiaShiYuanPage5 = new JiaShiYuanPage();
		jiaShiYuanPage5.setDeptId(jiaShiYuanPage.getDeptId());
		jiaShiYuanPage5.setType("tj_compeleted");
		JiaShiYuanPage<JiaShiYuanTJMX> jiaShiYuanTJMXJiaShiYuanPage5 = jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage5);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList5 = jiaShiYuanTJMXJiaShiYuanPage5.getRecords();
		sum1 = JiaShiYuanTJMXList5.size();
		sum2 = jiaShiYuans.size();
		percentage = (double) sum1 / sum2;
		if (percentage==NaN){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			format = nt.format(percentage);
		}else {
			format="0.00%";
		}
//		nt = NumberFormat.getPercentInstance();
//		nt.setMinimumFractionDigits(2);
//		format = nt.format(percentage);
//		if (format.equals("�")){
//			format="0.00%";
//		}
		DriverDataPerfectionValueVO driverDataPerfectionValueVO5 = new DriverDataPerfectionValueVO();
		driverDataPerfectionVO.setTijianbiao(format);
		driverDataPerfectionValueVO5.setXiangmu("体检表");
		driverDataPerfectionValueVO5.setShuju(driverDataPerfectionVO.getTijianbiao());
		driverDataPerfectionValueVO5.setGongsirenshu(size);
		driverDataPerfectionValueVOS.add(driverDataPerfectionValueVO5);

		//岗前培训
		JiaShiYuanPage jiaShiYuanPage6 = new JiaShiYuanPage();
		jiaShiYuanPage6.setDeptId(jiaShiYuanPage.getDeptId());
		jiaShiYuanPage6.setType("gqpx_compeleted");
		JiaShiYuanPage<JiaShiYuanTJMX> jiaShiYuanTJMXJiaShiYuanPage6 = jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage6);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList6 = jiaShiYuanTJMXJiaShiYuanPage6.getRecords();
		sum1 = JiaShiYuanTJMXList6.size();
		sum2 = jiaShiYuans.size();
		percentage = (double) sum1 / sum2;
		if (percentage==NaN){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			format = nt.format(percentage);
		}else {
			format="0.00%";
		}
//		nt = NumberFormat.getPercentInstance();
//		nt.setMinimumFractionDigits(2);
//		format = nt.format(percentage);
//		if (format.equals("�")){
//			format="0.00%";
//		}
		DriverDataPerfectionValueVO driverDataPerfectionValueVO6 = new DriverDataPerfectionValueVO();
		driverDataPerfectionVO.setGangqianpeixun(format);
		driverDataPerfectionValueVO6.setXiangmu("岗前培训");
		driverDataPerfectionValueVO6.setShuju(driverDataPerfectionVO.getGangqianpeixun());
		driverDataPerfectionValueVO6.setGongsirenshu(size);
		driverDataPerfectionValueVOS.add(driverDataPerfectionValueVO6);

		//无责证明
		JiaShiYuanPage jiaShiYuanPage7 = new JiaShiYuanPage();
		jiaShiYuanPage7.setDeptId(jiaShiYuanPage.getDeptId());
		jiaShiYuanPage7.setType("wzzm_compeleted");
		JiaShiYuanPage<JiaShiYuanTJMX> jiaShiYuanTJMXJiaShiYuanPage7 = jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage7);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList7 = jiaShiYuanTJMXJiaShiYuanPage7.getRecords();
		sum1 = JiaShiYuanTJMXList7.size();
		sum2 = jiaShiYuans.size();
		percentage = (double) sum1 / sum2;
		if (percentage==NaN){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			format = nt.format(percentage);
		}else {
			format="0.00%";
		}
//		nt = NumberFormat.getPercentInstance();
//		nt.setMinimumFractionDigits(2);
//		format = nt.format(percentage);
//		if (format.equals("�")){
//			format="0.00%";
//		}
		DriverDataPerfectionValueVO driverDataPerfectionValueVO7 = new DriverDataPerfectionValueVO();
		driverDataPerfectionVO.setWuzezhengming(format);
		driverDataPerfectionValueVO7.setXiangmu("三年无重大责任事故证明");
		driverDataPerfectionValueVO7.setShuju(driverDataPerfectionVO.getWuzezhengming());
		driverDataPerfectionValueVO7.setGongsirenshu(size);
		driverDataPerfectionValueVOS.add(driverDataPerfectionValueVO7);

		//安全责任书
		JiaShiYuanPage jiaShiYuanPage8 = new JiaShiYuanPage();
		jiaShiYuanPage8.setDeptId(jiaShiYuanPage.getDeptId());
		jiaShiYuanPage8.setType("aqzrs_compeleted");
		JiaShiYuanPage<JiaShiYuanTJMX> jiaShiYuanTJMXJiaShiYuanPage8 = jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage8);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList8 = jiaShiYuanTJMXJiaShiYuanPage8.getRecords();
		sum1 = JiaShiYuanTJMXList8.size();
		sum2 = jiaShiYuans.size();
		percentage = (double) sum1 / sum2;
		if (percentage==NaN){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			format = nt.format(percentage);
		}else {
			format="0.00%";
		}
//		nt = NumberFormat.getPercentInstance();
//		nt.setMinimumFractionDigits(2);
//		format = nt.format(percentage);
//		if (format.equals("�")){
//			format="0.00%";
//		}
		DriverDataPerfectionValueVO driverDataPerfectionValueVO8 = new DriverDataPerfectionValueVO();
		driverDataPerfectionVO.setAnquanzerenshu(format);
		driverDataPerfectionValueVO8.setXiangmu("驾驶员安全责任书");
		driverDataPerfectionValueVO8.setShuju(driverDataPerfectionVO.getAnquanzerenshu());
		driverDataPerfectionValueVO8.setGongsirenshu(size);
		driverDataPerfectionValueVOS.add(driverDataPerfectionValueVO8);

		//危害告知书
		JiaShiYuanPage jiaShiYuanPage9 = new JiaShiYuanPage();
		jiaShiYuanPage9.setDeptId(jiaShiYuanPage.getDeptId());
		jiaShiYuanPage9.setType("whgzs_compeleted");
		JiaShiYuanPage<JiaShiYuanTJMX> jiaShiYuanTJMXJiaShiYuanPage9 = jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage9);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList9 = jiaShiYuanTJMXJiaShiYuanPage9.getRecords();
		sum1 = JiaShiYuanTJMXList9.size();
		sum2 = jiaShiYuans.size();
		percentage = (double) sum1 / sum2;
		if (percentage==NaN){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			format = nt.format(percentage);
		}else {
			format="0.00%";
		}
//		nt = NumberFormat.getPercentInstance();
//		nt.setMinimumFractionDigits(2);
//		format = nt.format(percentage);
//		if (format.equals("�")){
//			format="0.00%";
//		}
		DriverDataPerfectionValueVO driverDataPerfectionValueVO9 = new DriverDataPerfectionValueVO();
		driverDataPerfectionVO.setWeihaigaozhishu(format);
		driverDataPerfectionValueVO9.setXiangmu("驾驶员职业危害告知书");
		driverDataPerfectionValueVO9.setShuju(driverDataPerfectionVO.getWeihaigaozhishu());
		driverDataPerfectionValueVO9.setGongsirenshu(size);
		driverDataPerfectionValueVOS.add(driverDataPerfectionValueVO9);

		//劳动合同
		JiaShiYuanPage jiaShiYuanPage10 = new JiaShiYuanPage();
		jiaShiYuanPage10.setDeptId(jiaShiYuanPage.getDeptId());
		jiaShiYuanPage10.setType("ldht_compeleted");
		JiaShiYuanPage<JiaShiYuanTJMX> jiaShiYuanTJMXJiaShiYuanPage10 = jiaShiYuanService.selectAlarmTJMXPage2(jiaShiYuanPage10);
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList10 = jiaShiYuanTJMXJiaShiYuanPage10.getRecords();
		sum1 = JiaShiYuanTJMXList10.size();
		sum2 = jiaShiYuans.size();
		percentage = (double) sum1 / sum2;
		if (percentage==NaN){
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			format = nt.format(percentage);
		}else {
			format="0.00%";
		}
//		nt = NumberFormat.getPercentInstance();
//		nt.setMinimumFractionDigits(2);
//		format = nt.format(percentage);
//		if (format.equals("�")){
//			format="0.00%";
//		}
		DriverDataPerfectionValueVO driverDataPerfectionValueVO10 = new DriverDataPerfectionValueVO();
		driverDataPerfectionVO.setLaodonghetong(format);
		driverDataPerfectionValueVO10.setXiangmu("劳动合同");
		driverDataPerfectionValueVO10.setShuju(driverDataPerfectionVO.getLaodonghetong());
		driverDataPerfectionValueVO10.setGongsirenshu(size);
		driverDataPerfectionValueVOS.add(driverDataPerfectionValueVO10);

		rs.setData(driverDataPerfectionValueVOS);
		return rs;
	}

	@ApiLog("基础台账完成度")
	@ApiOperation(value = "基础台账完成度", notes = "传入JiaShiYuanTJMX", position = 8)
	@PostMapping(value = "/getCompletionDegree")
	public List<JiaShiYuanTJMX> getCompletionDegree(@RequestBody JiaShiYuanTJMX jiaShiYuanTJMX3) throws IOException {
		R rs = new R();
		List<JiaShiYuanTJMX> JiaShiYuanTJMXList = jiaShiYuanService.selectAlarmTJMXPage3(jiaShiYuanTJMX3);
		JiaShiYuanTJMXList.forEach(item -> {
			if (item.getA1().equals("0")) {
				item.setA1("√");
			} else {
				item.setA1("×");
			}
			if (item.getA2().equals("0")) {
				item.setA2("√");
			} else {
				item.setA2("×");
			}
			if (item.getA3().equals("0")) {
				item.setA3("√");
			} else {
				item.setA3("×");
			}
			if (item.getA4().equals("0")) {
				item.setA4("√");
			} else {
				item.setA4("×");
			}
			if (item.getA5().equals("0")) {
				item.setA5("√");
			} else {
				item.setA5("×");
			}
			if (item.getA6().equals("0")) {
				item.setA6("√");
			} else {
				item.setA6("×");
			}
			if (item.getA7().equals("0")) {
				item.setA7("√");
			} else {
				item.setA7("×");
			}
			if (item.getA8().equals("0")) {
				item.setA8("√");
			} else {
				item.setA8("×");
			}
			if (item.getA9().equals("0")) {
				item.setA9("√");
			} else {
				item.setA9("×");
			}
			if (item.getA10().equals("0")) {
				item.setA10("√");
			} else {
				item.setA10("×");
			}
			if (item.getC1().equals("0")) {
				item.setC1("√");
			} else {
				item.setC1("×");
			}

			//车头
			JiaShiYuanTJMX jiaShiYuanTJMX = jiaShiYuanService.selectAlarmTJMXVehicle(item);
			if (jiaShiYuanTJMX != null) {
				item.setCheliangpaizhao(jiaShiYuanTJMX.getCheliangpaizhao());
				item.setVehicleId(jiaShiYuanTJMX.getVehicleId());
				if (jiaShiYuanTJMX.getB1().equals("0")) {
					item.setB1("√");
				} else {
					item.setB1("×");
				}
				if (jiaShiYuanTJMX.getB2().equals("0")) {
					item.setB2("√");
				} else {
					item.setB2("×");
				}
				if (jiaShiYuanTJMX.getB3().equals("0")) {
					item.setB3("√");
				} else {
					item.setB3("×");
				}
				if (jiaShiYuanTJMX.getB4().equals("0")) {
					item.setB4("√");
				} else {
					item.setB4("×");
				}
				if (jiaShiYuanTJMX.getC2().equals("0")) {
					item.setC2("√");
				} else {
					item.setC2("×");
				}
			} else {
				item.setCheliangpaizhao("未绑定");
				item.setVehicleId("未绑定");
				item.setB1("√");
				item.setB2("√");
				item.setB3("√");
				item.setB4("√");
				item.setC2("√");
			}

			//挂车
			JiaShiYuanTJMX jiaShiYuanTJMX2 = jiaShiYuanService.selectAlarmTJMXVehicleGUA(item);
			if (jiaShiYuanTJMX2 != null) {
				item.setCheliangpaizhaoGUA(jiaShiYuanTJMX2.getCheliangpaizhaoGUA());

				item.setVehicleIdGUA(jiaShiYuanTJMX2.getVehicleIdGUA());

				if (jiaShiYuanTJMX2.getB5().equals("0")) {
					item.setB5("√");
				} else {
					item.setB5("×");
				}
				if (jiaShiYuanTJMX2.getB6().equals("0")) {
					item.setB6("√");
				} else {
					item.setB6("×");
				}
				if (jiaShiYuanTJMX2.getB7().equals("0")) {
					item.setB7("√");
				} else {
					item.setB7("×");
				}
				if (jiaShiYuanTJMX2.getB8().equals("0")) {
					item.setB8("√");
				} else {
					item.setB8("×");
				}
				if (jiaShiYuanTJMX2.getC3().equals("0")) {
					item.setC3("√");
				} else {
					item.setC3("×");
				}
			} else {
				item.setCheliangpaizhaoGUA("未绑定");
				item.setVehicleIdGUA("未绑定");
				item.setB5("√");
				item.setB6("√");
				item.setB7("√");
				item.setB8("√");
				item.setC3("√");
			}
		});

		rs.setData(JiaShiYuanTJMXList);
		return JiaShiYuanTJMXList;
	}

}
