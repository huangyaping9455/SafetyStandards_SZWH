package org.springblade.anbiao.risk.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.sql.visitor.functions.Substring;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.AccidentReports.DTO.AccidentReportsDTO;
import org.springblade.anbiao.AccidentReports.service.AccidentReportsService;
import org.springblade.anbiao.SafeInvestment.DTO.SafeInvestmentDTO;
import org.springblade.anbiao.SafeInvestment.service.impl.SafeInvestmentServiceImpl;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.anquanhuiyi.service.IAnbiaoAnquanhuiyiService;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.BaoYangWeiXiuVO;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineInfoService;
import org.springblade.anbiao.chuchejiancha.service.IAnbiaoCarExamineService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.service.IBladeDeptService;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;
import org.springblade.anbiao.jiaoyupeixun.service.IAnbiaoSafetyTrainingService;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.jiashiyuan.page.JiaShiYuanPage;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanCongyezigezhengService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanJiashizhengService;
import org.springblade.anbiao.jiashiyuan.service.IAnbiaoJiashiyuanTijianService;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.jiashiyuan.vo.JiaShiYuanListVO;
import org.springblade.anbiao.labor.entity.LaborEntity;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.labor.service.laborLingquService;
import org.springblade.anbiao.labor.service.laborService;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetailInfo;
import org.springblade.anbiao.risk.page.*;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailInfoService;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springblade.anbiao.risk.vo.*;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;
import org.springblade.anbiao.yinhuanpaicha.service.IAnbiaoHiddenDangerService;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;
import org.springblade.common.tool.JSONUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 安标风险统计信息 前端控制器
 * </p>
 *
 * @author hyp
 * @since 2022-12-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/risk")
@Api(value = "风险统计信息", tags = "风险统计信息")
public class AnbiaoRiskDetailController {

	@Autowired
	private IAnbiaoRiskDetailService riskDetailService;
	@Autowired
	private IJiaShiYuanService jiaShiYuanService;
	@Autowired
	private IOrganizationsService organizationsService;
	@Autowired
	private IAnbiaoRiskDetailInfoService detailInfoService;
	@Autowired
	private IAnbiaoJiashiyuanJiashizhengService jiashizhengService;
	@Autowired
	private IAnbiaoJiashiyuanCongyezigezhengService congyezigezhengService;
	@Autowired
	private IAnbiaoJiashiyuanTijianService tijianService;
	@Autowired
	private IAnbiaoAnquanhuiyiService anquanhuiyiService;
	@Autowired
	private IBladeDeptService deptService;
	@Autowired
	private IAnbiaoHiddenDangerService hiddenDangerService;
	@Autowired
	private AccidentReportsService accidentReportsService;
	@Autowired
	private IAnbiaoSafetyTrainingService safetyTrainingService;
	@Autowired
	private SafeInvestmentServiceImpl safeInvestmentService;
	@Autowired
	private laborService laborService;
	@Autowired
	private laborLingquService lingquService;
	@Autowired
	private IAnbiaoCarExamineInfoService carExamineInfoService;
	@Autowired
	private IVehicleService vehicleService;

	public AnbiaoRiskDetailController() {

	}

	@PostMapping("/insert")
	@ApiLog("新增-风险统计信息")
	@ApiOperation(value = "新增-风险统计信息", position = 1)
	public R insert(BladeUser user) throws ParseException {
		R r = new R();
		QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<Organizations>();
		organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
		organizationsQueryWrapper.lambda().eq(Organizations::getJigouleixing, "qiye");
		List<Organizations> organizationsList = organizationsService.getBaseMapper().selectList(organizationsQueryWrapper);
		for (Organizations organizations : organizationsList) {
			Integer deptId = Integer.parseInt(organizations.getDeptId());
			QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper1 = new QueryWrapper<>();
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getDeptId, deptId);
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getIsdelete, 0);
			List<JiaShiYuan> jiaShiYuans = jiaShiYuanService.getBaseMapper().selectList(jiaShiYuanQueryWrapper1);
			for (JiaShiYuan jiaShiYuan : jiaShiYuans) {
				String jiaShiYuanId = jiaShiYuan.getId();
				int aa = 0;
				int timeDifference = 0;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, jiaShiYuanId);
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
				JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);

				//验证身份证日期
				AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
				riskDetail.setArdDeptIds(deail.getDeptId().toString());
				riskDetail.setArdMajorCategories("0");
				riskDetail.setArdSubCategory("003");
				riskDetail.setArdTitle("身份证有效期");
				riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail.setArdIsRectification("0");
				riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail.setArdAssociationField("id");
				riskDetail.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getShenfenzhengyouxiaoqi()) || deail.getShenfenzhengyouxiaoqi().equals("null")) {
//					riskDetail.setArdContent("身份证有效期缺项");
//					riskDetail.setArdType("缺项");
//					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail.getArdDeptIds());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail.getArdContent());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
//					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
//					if (riskDetail1 == null) {
//						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
//						if (insert > 0) {
//							aa++;
//						}
//					}
				} else if (!deail.getShenfenzhengyouxiaoqi().equals("长期")) {
					Date shenfenzhengyouxiaoqi = formatter.parse(deail.getShenfenzhengyouxiaoqi());
					Date now = formatter.parse(DateUtil.now());
					Calendar calendar1 = Calendar.getInstance();
					calendar1.setTime(now);
					Calendar calendar2 = Calendar.getInstance();
					calendar2.setTime(shenfenzhengyouxiaoqi);
					int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
					int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
					int year1 = calendar1.get(Calendar.YEAR);
					int year2 = calendar2.get(Calendar.YEAR);
					if (year1 != year2) {//不同年
						int timeDistance = 0;
						if (year2 < year1) {
							timeDifference = -1;
						} else {
							for (int x = year1; x < year2; x++) { //闰年
								if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
									timeDistance += 366;
								} else { // 不是闰年
									timeDistance += 365;
								}
							}
							timeDifference = timeDistance + (day2 - day1);
						}
					} else {
						timeDifference = day2 - day1;
					}
					if (timeDifference <= 30 && timeDifference > 0) {
						riskDetail.setArdContent("身份证有效期预警");
						riskDetail.setArdType("预警");
					} else if (timeDifference < 0) {
						riskDetail.setArdContent("身份证有效期逾期");
						riskDetail.setArdType("逾期");
					} else {
						riskDetail.setArdType("正常");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1 == null) {
						if (riskDetail.getArdType().equals("预警") || riskDetail.getArdType().equals("逾期")) {
							int insert = riskDetailService.getBaseMapper().insert(riskDetail);
							if (insert > 0) {
								aa++;
							}
						}
					}
				}

				//验证驾驶证日期
				AnbiaoRiskDetail riskDetail2 = new AnbiaoRiskDetail();
				riskDetail2.setArdDeptIds(deail.getDeptId().toString());
				riskDetail2.setArdMajorCategories("0");
				riskDetail2.setArdSubCategory("003");
				riskDetail2.setArdTitle("驾驶证有效期");
				riskDetail2.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail2.setArdIsRectification("0");
				riskDetail2.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail2.setArdAssociationField("id");
				riskDetail2.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getJiashizhengyouxiaoqi()) || deail.getJiashizhengyouxiaoqi().equals("null")) {
//					riskDetail2.setArdContent("驾驶证有效截止日期缺项");
//					riskDetail2.setArdType("缺项");
//					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail2.getArdDeptIds());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail2.getArdAssociationValue());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail2.getArdContent());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
//					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
//					if (riskDetail1 == null) {
//						int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
//						if (insert2 > 0) {
//							aa++;
//						}
//					}
				} else if (!deail.getJiashizhengyouxiaoqi().equals("长期")) {
					Date jiashizhengyouxiaoqi = formatter.parse(deail.getJiashizhengyouxiaoqi());
					Date now2 = formatter.parse(DateUtil.now());
					Calendar calendar3 = Calendar.getInstance();
					calendar3.setTime(now2);
					Calendar calendar4 = Calendar.getInstance();
					calendar4.setTime(jiashizhengyouxiaoqi);
					int day3 = calendar3.get(Calendar.DAY_OF_YEAR);
					int day4 = calendar4.get(Calendar.DAY_OF_YEAR);
					int year3 = calendar3.get(Calendar.YEAR);
					int year4 = calendar4.get(Calendar.YEAR);
					if (year3 != year4) {//不同年
						int timeDistance = 0;
						if (year4 < year3) {
							timeDifference = -1;
						} else {
							for (int x = year3; x < year4; x++) { //闰年
								if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
									timeDistance += 366;
								} else { // 不是闰年
									timeDistance += 365;
								}
							}
							timeDifference = timeDistance + (day4 - day3);
						}
					} else {
						timeDifference = day4 - day3;
					}
					if (timeDifference <= 30 && timeDifference > 0) {
						riskDetail2.setArdContent("驾驶证有效期预警");
						riskDetail2.setArdType("预警");
					} else if (timeDifference < 0) {
						riskDetail2.setArdContent("驾驶证有效期逾期");
						riskDetail2.setArdType("逾期");
					} else {
						riskDetail2.setArdType("正常");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail2.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail2.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail2.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1 == null) {
						if (riskDetail2.getArdType().equals("预警") || riskDetail2.getArdType().equals("逾期")) {
							int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
							if (insert2 > 0) {
								aa++;
							}
						}
					}
				}

				//验证从业资格证日期
				AnbiaoRiskDetail riskDetail3 = new AnbiaoRiskDetail();
				riskDetail3.setArdDeptIds(deail.getDeptId().toString());
				riskDetail3.setArdMajorCategories("0");
				riskDetail3.setArdSubCategory("003");
				riskDetail3.setArdTitle("从业资格证有效期");
				riskDetail3.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail3.setArdIsRectification("0");
				riskDetail3.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail3.setArdAssociationField("id");
				riskDetail3.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getCongyezhengyouxiaoqi()) || deail.getCongyezhengyouxiaoqi().equals("null")) {
//					riskDetail3.setArdContent("从业资格证有效期缺项");
//					riskDetail3.setArdType("缺项");
//					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail3.getArdDeptIds());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail3.getArdAssociationValue());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail3.getArdContent());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
//					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
//					if (riskDetail1 == null) {
//						int insert3 = riskDetailService.getBaseMapper().insert(riskDetail3);
//						if (insert3 > 0) {
//							aa++;
//						}
//					}
				} else {
					Date congyezhengyouxiaoqi = formatter.parse(deail.getCongyezhengyouxiaoqi());
					Date now3 = formatter.parse(DateUtil.now());
					Calendar calendar5 = Calendar.getInstance();
					calendar5.setTime(now3);
					Calendar calendar6 = Calendar.getInstance();
					calendar6.setTime(congyezhengyouxiaoqi);
					int day5 = calendar5.get(Calendar.DAY_OF_YEAR);
					int day6 = calendar6.get(Calendar.DAY_OF_YEAR);
					int year5 = calendar5.get(Calendar.YEAR);
					int year6 = calendar6.get(Calendar.YEAR);
					if (year5 != year6) {//不同年
						int timeDistance = 0;
						if (year6 < year5) {
							timeDifference = -1;
						} else {
							for (int x = year5; x < year6; x++) { //闰年
								if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
									timeDistance += 366;
								} else { // 不是闰年
									timeDistance += 365;
								}
							}
							timeDifference = timeDistance + (day6 - day5);
						}
					} else {
						timeDifference = day6 - day5;
					}
					if (timeDifference <= 30 && timeDifference > 0) {
						riskDetail3.setArdContent("从业资格证有效期预警");
						riskDetail3.setArdType("预警");
					} else if (timeDifference < 0) {
						riskDetail3.setArdContent("从业资格证有效期逾期");
						riskDetail3.setArdType("逾期");
					} else {
						riskDetail3.setArdType("正常");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail3.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail3.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail3.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1 == null) {
						if (riskDetail3.getArdType().equals("预警") || riskDetail3.getArdType().equals("逾期")) {
							int insert3 = riskDetailService.getBaseMapper().insert(riskDetail3);
							if (insert3 > 0) {
								aa++;
							}
						}
					}
				}

				//验证体检日期
				AnbiaoRiskDetail riskDetail4 = new AnbiaoRiskDetail();
				riskDetail4.setArdDeptIds(deail.getDeptId().toString());
				riskDetail4.setArdMajorCategories("0");
				riskDetail4.setArdSubCategory("003");
				riskDetail4.setArdTitle("体检有效期");
				riskDetail4.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail4.setArdIsRectification("0");
				riskDetail4.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail4.setArdAssociationField("id");
				riskDetail4.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getTijianyouxiaoqi()) || deail.getTijianyouxiaoqi().equals("null")) {
//					riskDetail4.setArdContent("体检日期缺项");
//					riskDetail4.setArdType("缺项");
//					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail4.getArdDeptIds());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail4.getArdAssociationValue());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail4.getArdContent());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
//					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
//					if (riskDetail1 == null) {
//						int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
//						if (insert4 > 0) {
//							aa++;
//						}
//					}
				} else {
					Date tijianyouxiaoqi = formatter.parse(deail.getTijianyouxiaoqi());
					Date now4 = formatter.parse(DateUtil.now());
					Calendar calendar7 = Calendar.getInstance();
					calendar7.setTime(now4);
					Calendar calendar8 = Calendar.getInstance();
					calendar8.setTime(tijianyouxiaoqi);
					int day7 = calendar7.get(Calendar.DAY_OF_YEAR);
					int day8 = calendar8.get(Calendar.DAY_OF_YEAR);
					int year7 = calendar7.get(Calendar.YEAR);
					int year8 = calendar8.get(Calendar.YEAR);
					if (year7 != year8) {//不同年
						int timeDistance = 0;
						if (year8 < year7) {
							timeDifference = -1;
						} else {
							for (int x = year7; x < year8; x++) { //闰年
								if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
									timeDistance += 366;
								} else { // 不是闰年
									timeDistance += 365;
								}
							}
							timeDifference = timeDistance + (day8 - day7);
						}
					} else {
						timeDifference = day8 - day7;
					}
					riskDetail4.setArdDeptIds(deail.getDeptId().toString());
					if (timeDifference <= 30 && timeDifference > 0) {
						riskDetail4.setArdContent("体检有效期预警");
						riskDetail4.setArdType("预警");
					} else if (timeDifference < 0) {
						riskDetail4.setArdContent("体检有效期逾期");
						riskDetail4.setArdType("逾期");
					} else {
						riskDetail4.setArdType("正常");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail4.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail4.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail4.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1 == null) {
						if (riskDetail4.getArdType().equals("预警") || riskDetail4.getArdType().equals("逾期")) {
							int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
							if (insert4 > 0) {
								aa++;
							}
						}
					}
				}

				if (aa > 0) {
					r.setMsg("风险新增成功");
					r.setCode(200);
					r.setSuccess(true);
				} else {
					r.setMsg("无风险新增");
					r.setCode(200);
					r.setSuccess(true);
				}
			}
		}
		return r;
	}

	@PostMapping("/detail")
	@ApiLog("详情-风险统计信息")
	@ApiOperation(value = "详情-风险统计信息", notes = "传入jiaShiYuan", position = 1)
	public R detail(String id, String deptId, BladeUser user) {
		R r = new R();
		QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, id);
		JiaShiYuan jiaShiYuan = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
		String jiashiyuanxingming = jiaShiYuan.getJiashiyuanxingming();
		QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, id);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, deptId);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, 0);
		List<AnbiaoRiskDetail> anbiaoRiskDetails = riskDetailService.getBaseMapper().selectList(riskDetailQueryWrapper);
		for (AnbiaoRiskDetail anbiaoRiskDetail :
			anbiaoRiskDetails) {
			anbiaoRiskDetail.setJiashiyuanxingming(jiashiyuanxingming);
		}
		if (anbiaoRiskDetails.size() != 0) {
			r.setMsg("查询成功");
			r.setCode(200);
			r.setSuccess(true);
			r.setData(anbiaoRiskDetails);
		} else {
			r.setMsg("无风险数据");
			r.setCode(200);
			r.setSuccess(true);
		}

		return r;
	}

	@PostMapping("/update")
	@ApiLog("处理-风险统计信息")
	@ApiOperation(value = "处理-风险统计信息", notes = "传入ardIds", position = 1)
	public R deal(@RequestBody String json, BladeUser user) {
		R r = new R();
		int aa = 0;
		//获取参数
		JsonNode node = JSONUtils.string2JsonNode(json);
		String ardIds = node.get("ardIds").asText();
		String date = node.get("date").asText();
		String fujian = node.get("fujian").asText();
		QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIds, ardIds);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
		AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
		if (riskDetail != null) {

			String substring = riskDetail.getArdTitle().substring(0, 2);
			AnbiaoRiskDetailInfo anbiaoRiskDetailInfo = new AnbiaoRiskDetailInfo();

			//身份证有效日期
			if (substring.equals("身份")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("身份证有效期");
				riskDetail.setArdRectificationField("shenfenzhengyouxiaoqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("shenfenzhengyouxiaoqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//驾驶员表
						QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, riskDetail.getArdAssociationValue());
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
						JiaShiYuan deal = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
						deal.setShenfenzhengyouxiaoqi(date);
						deal.setShenfenzhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = jiaShiYuanService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//驾驶证有效日期
			if (substring.equals("驾驶")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("驾驶证有效期");
				riskDetail.setArdRectificationField("jiashizhengyouxiaoqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("jiashizhengyouxiaoqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//驾驶员表
						QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, riskDetail.getArdAssociationValue());
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
						JiaShiYuan deal = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
						deal.setJiashizhengyouxiaoqi(date);
						deal.setJiashizhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = jiaShiYuanService.updateById(deal);
						if (b1 == true) {
							//驾驶证表
							QueryWrapper<AnbiaoJiashiyuanJiashizheng> jiashizhengQueryWrapper = new QueryWrapper<>();
							jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjAjIds, riskDetail.getArdAssociationValue());
							jiashizhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanJiashizheng::getAjjDelete, 0);
							AnbiaoJiashiyuanJiashizheng jiashizheng = jiashizhengService.getBaseMapper().selectOne(jiashizhengQueryWrapper);
							jiashizheng.setAjjValidPeriodEnd(date);
							jiashizheng.setAjjFrontPhotoAddress(fujian);
							jiashizheng.setAjjUpdateTime(DateUtil.now());
							jiashizheng.setAjjUpdateByIds(user.getUserId().toString());
							jiashizheng.setAjjUpdateByName(user.getUserName());
							boolean b2 = jiashizhengService.updateById(jiashizheng);
							if (b2 == true) {
								aa++;
							}
						}
					}
				}
			}

			//从业资格证有效日期
			if (substring.equals("从业")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("从业资格证有效期");
				riskDetail.setArdRectificationField("congyezhengyouxiaoqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("congyezhengyouxiaoqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//驾驶员表
						QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, riskDetail.getArdAssociationValue());
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
						JiaShiYuan deal = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
						deal.setCongyezhengyouxiaoqi(date);
						deal.setCongyezhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = jiaShiYuanService.updateById(deal);
						if (b1 == true) {
							//从业资格证表
							QueryWrapper<AnbiaoJiashiyuanCongyezigezheng> congyezigezhengQueryWrapper = new QueryWrapper<>();
							congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcAjIds, riskDetail.getArdAssociationValue());
							congyezigezhengQueryWrapper.lambda().eq(AnbiaoJiashiyuanCongyezigezheng::getAjcDelete, 0);
							AnbiaoJiashiyuanCongyezigezheng congyezigezheng = congyezigezhengService.getBaseMapper().selectOne(congyezigezhengQueryWrapper);
							congyezigezheng.setAjcValidUntil(date);
							congyezigezheng.setAjcLicence(fujian);
							congyezigezheng.setAjcUpdateTime(DateUtil.now());
							congyezigezheng.setAjcUpdateByIds(user.getUserId().toString());
							congyezigezheng.setAjcUpdateByName(user.getUserName());
							boolean b2 = congyezigezhengService.updateById(congyezigezheng);
							if (b2 == true) {
								aa++;
							}
						}
					}
				}
			}

			//体检表有效日期
			if (substring.equals("体检")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("体检有效期");
				riskDetail.setArdRectificationField("tijianriqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("tijianriqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//驾驶员表
						QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId, riskDetail.getArdAssociationValue());
						jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete, 0);
						JiaShiYuan deal = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);
						int tijianyouxiaoqi = Integer.parseInt(date.substring(0, 4)) + 1;
						String tijianyouxiaoqis = String.valueOf(tijianyouxiaoqi);
						String tijianyouxiaoqiss = tijianyouxiaoqis + date.substring(4, 10);
						deal.setTijianriqi(date);
						deal.setTijianyouxiaoqi(tijianyouxiaoqiss);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = jiaShiYuanService.updateById(deal);
						if (b1 == true) {
							//体检表
							QueryWrapper<AnbiaoJiashiyuanTijian> tijianQueryWrapper = new QueryWrapper<>();
							tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtAjIds, riskDetail.getArdAssociationValue());
							tijianQueryWrapper.lambda().eq(AnbiaoJiashiyuanTijian::getAjtDelete, 0);
							AnbiaoJiashiyuanTijian tijian = tijianService.getBaseMapper().selectOne(tijianQueryWrapper);
							int tijianyouxiaoqi2 = Integer.parseInt(date.substring(0, 4)) + 1;
							String tijianyouxiaoqi22 = String.valueOf(tijianyouxiaoqi2);
							String tijianyouxiaoqi222 = tijianyouxiaoqi22 + date.substring(4, 10);
							tijian.setAjtPhysicalExaminationDate(date);
							tijian.setAjtTermValidity(tijianyouxiaoqi222);
							tijian.setAjtTermValidity(date);
							tijian.setAjtEnclosure(fujian);
							tijian.setAjtUpdateTime(DateUtil.now());
							tijian.setAjtUpdateByIds(user.getUserId().toString());
							tijian.setAjtUpdateByName(user.getUserName());
							boolean b2 = tijianService.updateById(tijian);
							if (b2 == true) {
								aa++;
							}
						}
					}
				}
			}
		}
		if (aa > 0) {
			r.setMsg("风险处理成功");
			r.setCode(200);
			r.setSuccess(true);
		} else {
			r.setMsg("风险处理失败");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	@PostMapping("/organizationInsert")
	@ApiLog("新增-企业风险统计信息")
	@ApiOperation(value = "新增-企业风险统计信息", position = 1)
	public R organizationInsert(BladeUser user) throws ParseException {
		R r = new R();
		QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<Organizations>();
		organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
		organizationsQueryWrapper.lambda().eq(Organizations::getJigouleixing, "qiye");
		List<Organizations> organizationsList = organizationsService.getBaseMapper().selectList(organizationsQueryWrapper);
		int aa = 0;
		int timeDifference = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (Organizations organization : organizationsList) {

			//验证统一社会信用代码
			AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
			riskDetail.setArdDeptIds(organization.getDeptId());
			riskDetail.setArdMajorCategories("0");
			riskDetail.setArdSubCategory("000");
			riskDetail.setArdTitle("统一社会信用代码");
			riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail.setArdIsRectification("0");
			riskDetail.setArdAssociationTable("anbiao_organization");
			riskDetail.setArdAssociationField("id");
			riskDetail.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getJigoubianma()) || organization.getJigoubianma().equals("null")) {
				riskDetail.setArdContent("统一社会信用代码缺项");
				riskDetail.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					int insert = riskDetailService.getBaseMapper().insert(riskDetail);
					if (insert > 0) {
						aa++;
					}
				}
			}

			//验证统一社会信用代码日期
			AnbiaoRiskDetail riskDetail2 = new AnbiaoRiskDetail();
			riskDetail2.setArdDeptIds(organization.getDeptId());
			riskDetail2.setArdMajorCategories("0");
			riskDetail2.setArdSubCategory("000");
			riskDetail2.setArdTitle("统一社会信用代码有效截止日期");
			riskDetail2.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail2.setArdIsRectification("0");
			riskDetail2.setArdAssociationTable("anbiao_organization");
			riskDetail2.setArdAssociationField("id");
			riskDetail2.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getYyzzjzdate()) || organization.getYyzzjzdate().equals("null")) {
				riskDetail2.setArdContent("统一社会信用代码有效截止日期缺项");
				riskDetail2.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail2.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail2.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail2.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
					if (insert2 > 0) {
						aa++;
					}
				}
			} else if (!organization.getYyzzjzdate().equals("长期")) {
				Date yyzzjzdate = formatter.parse(organization.getYyzzjzdate());
				Date now2 = formatter.parse(DateUtil.now());
				Calendar calendar3 = Calendar.getInstance();
				calendar3.setTime(now2);
				Calendar calendar4 = Calendar.getInstance();
				calendar4.setTime(yyzzjzdate);
				int day3 = calendar3.get(Calendar.DAY_OF_YEAR);
				int day4 = calendar4.get(Calendar.DAY_OF_YEAR);
				int year3 = calendar3.get(Calendar.YEAR);
				int year4 = calendar4.get(Calendar.YEAR);
				if (year3 != year4) {//不同年
					int timeDistance = 0;
					if (year4 < year3) {
						timeDifference = -1;
					} else {
						for (int x = year3; x < year4; x++) { //闰年
							if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
								timeDistance += 366;
							} else { // 不是闰年
								timeDistance += 365;
							}
						}
						timeDifference = timeDistance + (day4 - day3);
					}
				} else {
					timeDifference = day4 - day3;
				}
				if (timeDifference <= 30 && timeDifference > 0) {
					riskDetail2.setArdContent("统一社会信用代码有效截止日期预警");
					riskDetail2.setArdType("预警");
				} else if (timeDifference < 0) {
					riskDetail2.setArdContent("统一社会信用代码有效截止日期逾期");
					riskDetail2.setArdType("逾期");
				} else {
					riskDetail2.setArdType("正常");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail2.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail2.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail2.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					if (riskDetail2.getArdType().equals("预警") || riskDetail2.getArdType().equals("逾期")) {
						int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
						if (insert2 > 0) {
							aa++;
						}
					}
				}
			}

			//验证道路运输许可证号
			AnbiaoRiskDetail riskDetail3 = new AnbiaoRiskDetail();
			riskDetail3.setArdDeptIds(organization.getDeptId());
			riskDetail3.setArdMajorCategories("0");
			riskDetail3.setArdSubCategory("000");
			riskDetail3.setArdTitle("道路运输许可证号");
			riskDetail3.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail3.setArdIsRectification("0");
			riskDetail3.setArdAssociationTable("anbiao_organization");
			riskDetail3.setArdAssociationField("id");
			riskDetail3.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getDaoluxukezhenghao()) || organization.getDaoluxukezhenghao().equals("null")) {
				riskDetail3.setArdContent("道路运输许可证号缺项");
				riskDetail3.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail3.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail3.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail3.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					int insert = riskDetailService.getBaseMapper().insert(riskDetail3);
					if (insert > 0) {
						aa++;
					}
				}
			}

			//验证道路运输许可证日期
			AnbiaoRiskDetail riskDetail4 = new AnbiaoRiskDetail();
			riskDetail4.setArdDeptIds(organization.getDeptId());
			riskDetail4.setArdMajorCategories("0");
			riskDetail4.setArdSubCategory("000");
			riskDetail4.setArdTitle("道路运输许可证有效截止日期");
			riskDetail4.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail4.setArdIsRectification("0");
			riskDetail4.setArdAssociationTable("anbiao_organization");
			riskDetail4.setArdAssociationField("id");
			riskDetail4.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getDaoluyunshuzhengjieshuriqi()) || organization.getDaoluyunshuzhengjieshuriqi().equals("null")) {
				riskDetail4.setArdContent("道路运输许可证有效截止日期缺项");
				riskDetail4.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail4.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail4.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail4.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
					if (insert4 > 0) {
						aa++;
					}
				}
			} else {
				Date daoluyunshuzhengjieshuriqi = formatter.parse(organization.getDaoluyunshuzhengjieshuriqi());
				Date now4 = formatter.parse(DateUtil.now());
				Calendar calendar7 = Calendar.getInstance();
				calendar7.setTime(now4);
				Calendar calendar8 = Calendar.getInstance();
				calendar8.setTime(daoluyunshuzhengjieshuriqi);
				int day7 = calendar7.get(Calendar.DAY_OF_YEAR);
				int day8 = calendar8.get(Calendar.DAY_OF_YEAR);
				int year7 = calendar7.get(Calendar.YEAR);
				int year8 = calendar8.get(Calendar.YEAR);
				if (year7 != year8) {//不同年
					int timeDistance = 0;
					if (year8 < year7) {
						timeDifference = -1;
					} else {
						for (int x = year7; x < year8; x++) { //闰年
							if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
								timeDistance += 366;
							} else { // 不是闰年
								timeDistance += 365;
							}
						}
						timeDifference = timeDistance + (day8 - day7);
					}
				} else {
					timeDifference = day8 - day7;
				}
				if (timeDifference <= 30 && timeDifference > 0) {
					riskDetail4.setArdContent("道路运输许可证有效截止日期预警");
					riskDetail4.setArdType("预警");
				} else if (timeDifference < 0) {
					riskDetail4.setArdContent("道路运输许可证有效截止日期逾期");
					riskDetail4.setArdType("逾期");
				} else {
					riskDetail4.setArdType("正常");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail4.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail4.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail4.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					if (riskDetail4.getArdType().equals("预警") || riskDetail4.getArdType().equals("逾期")) {
						int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
						if (insert4 > 0) {
							aa++;
						}
					}
				}
			}

			//验证经营许可证号
			AnbiaoRiskDetail riskDetail5 = new AnbiaoRiskDetail();
			riskDetail5.setArdDeptIds(organization.getDeptId());
			riskDetail5.setArdMajorCategories("0");
			riskDetail5.setArdSubCategory("000");
			riskDetail5.setArdTitle("经营许可证号");
			riskDetail5.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail5.setArdIsRectification("0");
			riskDetail5.setArdAssociationTable("anbiao_organization");
			riskDetail5.setArdAssociationField("id");
			riskDetail5.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getJingyingxukezhengbianma()) || organization.getJingyingxukezhengbianma().equals("null")) {
				riskDetail5.setArdContent("经营许可证号缺项");
				riskDetail5.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail5.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail5.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail5.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					int insert = riskDetailService.getBaseMapper().insert(riskDetail5);
					if (insert > 0) {
						aa++;
					}
				}
			}

			//验证经营许可证日期
			AnbiaoRiskDetail riskDetail6 = new AnbiaoRiskDetail();
			riskDetail6.setArdDeptIds(organization.getDeptId());
			riskDetail6.setArdMajorCategories("0");
			riskDetail6.setArdSubCategory("000");
			riskDetail6.setArdTitle("经营许可证有效截止日期");
			riskDetail6.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail6.setArdIsRectification("0");
			riskDetail6.setArdAssociationTable("anbiao_organization");
			riskDetail6.setArdAssociationField("id");
			riskDetail6.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getJingyingxukezhengyouxiaoqi()) || organization.getJingyingxukezhengyouxiaoqi().equals("null")) {
				riskDetail6.setArdContent("经营许可证有效截止日期缺项");
				riskDetail6.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail6.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail6.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail6.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					int insert6 = riskDetailService.getBaseMapper().insert(riskDetail6);
					if (insert6 > 0) {
						aa++;
					}
				}
			} else {
				Date jingyingxukezhengyouxiaoqi = formatter.parse(organization.getJingyingxukezhengyouxiaoqi());
				Date now4 = formatter.parse(DateUtil.now());
				Calendar calendar7 = Calendar.getInstance();
				calendar7.setTime(now4);
				Calendar calendar8 = Calendar.getInstance();
				calendar8.setTime(jingyingxukezhengyouxiaoqi);
				int day7 = calendar7.get(Calendar.DAY_OF_YEAR);
				int day8 = calendar8.get(Calendar.DAY_OF_YEAR);
				int year7 = calendar7.get(Calendar.YEAR);
				int year8 = calendar8.get(Calendar.YEAR);
				if (year7 != year8) {//不同年
					int timeDistance = 0;
					if (year8 < year7) {
						timeDifference = -1;
					} else {
						for (int x = year7; x < year8; x++) { //闰年
							if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
								timeDistance += 366;
							} else { // 不是闰年
								timeDistance += 365;
							}
						}
						timeDifference = timeDistance + (day8 - day7);
					}
				} else {
					timeDifference = day8 - day7;
				}
				if (timeDifference <= 30 && timeDifference > 0) {
					riskDetail6.setArdContent("经营许可证有效截止日期预警");
					riskDetail6.setArdType("预警");
				} else if (timeDifference < 0) {
					riskDetail6.setArdContent("经营许可证有效截止日期逾期");
					riskDetail6.setArdType("逾期");
				} else {
					riskDetail6.setArdType("正常");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail6.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail6.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail6.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					if (riskDetail6.getArdType().equals("预警") || riskDetail6.getArdType().equals("逾期")) {
						int insert6 = riskDetailService.getBaseMapper().insert(riskDetail6);
						if (insert6 > 0) {
							aa++;
						}
					}
				}
			}

			//验证合同日期
			AnbiaoRiskDetail riskDetail7 = new AnbiaoRiskDetail();
			riskDetail7.setArdDeptIds(organization.getDeptId());
			riskDetail7.setArdMajorCategories("0");
			riskDetail7.setArdSubCategory("000");
			riskDetail7.setArdTitle("合同有效截止日期");
			riskDetail7.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail7.setArdIsRectification("0");
			riskDetail7.setArdAssociationTable("anbiao_organization");
			riskDetail7.setArdAssociationField("id");
			riskDetail7.setArdAssociationValue(organization.getId());
			if (StringUtils.isBlank(organization.getLaodonghetongjieshuriqi()) || organization.getLaodonghetongjieshuriqi().equals("null")) {
				riskDetail7.setArdContent("合同有效截止日期缺项");
				riskDetail7.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail7.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail7.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail7.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					int insert7 = riskDetailService.getBaseMapper().insert(riskDetail7);
					if (insert7 > 0) {
						aa++;
					}
				}
			} else {
				Date laodonghetongjieshuriqi = formatter.parse(organization.getLaodonghetongjieshuriqi());
				Date now4 = formatter.parse(DateUtil.now());
				Calendar calendar7 = Calendar.getInstance();
				calendar7.setTime(now4);
				Calendar calendar8 = Calendar.getInstance();
				calendar8.setTime(laodonghetongjieshuriqi);
				int day7 = calendar7.get(Calendar.DAY_OF_YEAR);
				int day8 = calendar8.get(Calendar.DAY_OF_YEAR);
				int year7 = calendar7.get(Calendar.YEAR);
				int year8 = calendar8.get(Calendar.YEAR);
				if (year7 != year8) {//不同年
					int timeDistance = 0;
					if (year8 < year7) {
						timeDifference = -1;
					} else {
						for (int x = year7; x < year8; x++) { //闰年
							if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
								timeDistance += 366;
							} else { // 不是闰年
								timeDistance += 365;
							}
						}
						timeDifference = timeDistance + (day8 - day7);
					}
				} else {
					timeDifference = day8 - day7;
				}
				if (timeDifference <= 30 && timeDifference > 0) {
					riskDetail7.setArdContent("合同有效截止日期预警");
					riskDetail7.setArdType("预警");
				} else if (timeDifference < 0) {
					riskDetail7.setArdContent("合同有效截止日期逾期");
					riskDetail7.setArdType("逾期");
				} else {
					riskDetail7.setArdType("正常");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail7.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail7.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail7.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					if (riskDetail7.getArdType().equals("预警") || riskDetail7.getArdType().equals("逾期")) {
						int insert7 = riskDetailService.getBaseMapper().insert(riskDetail7);
						if (insert7 > 0) {
							aa++;
						}
					}
				}
			}

			if (aa > 0) {
				r.setMsg("风险新增成功");
				r.setCode(200);
				r.setSuccess(true);
			} else {
				r.setMsg("无风险新增");
				r.setCode(200);
				r.setSuccess(true);
			}
		}
		return r;
	}

	@PostMapping("/organizationDetail")
	@ApiLog("详情-企业风险统计信息")
	@ApiOperation(value = "详情-企业风险统计信息", notes = "传入Organizations", position = 1)
	public R organizationDetail(String id, String deptId, BladeUser user) {
		R r = new R();
		QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
		organizationsQueryWrapper.lambda().eq(Organizations::getId, id);
		Organizations organization = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
		String qiyemingcheng = organization.getDeptName();
		QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, id);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, deptId);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, 0);
		List<AnbiaoRiskDetail> anbiaoRiskDetails = riskDetailService.getBaseMapper().selectList(riskDetailQueryWrapper);
		for (AnbiaoRiskDetail anbiaoRiskDetail :
			anbiaoRiskDetails) {
			anbiaoRiskDetail.setQiyemingcheng(qiyemingcheng);
		}
		if (anbiaoRiskDetails.size() != 0) {
			r.setMsg("查询成功");
			r.setCode(200);
			r.setSuccess(true);
			r.setData(anbiaoRiskDetails);
		} else {
			r.setMsg("无风险数据");
			r.setCode(200);
			r.setSuccess(true);
		}

		return r;
	}

	@PostMapping("/organizationUpdate")
	@ApiLog("处理-企业风险统计信息")
	@ApiOperation(value = "处理-企业风险统计信息", notes = "传入ardIds", position = 1)
	public R organizationDeal(@RequestBody String json, BladeUser user) {
		R r = new R();
		int aa = 0;
		//获取参数
		JsonNode node = JSONUtils.string2JsonNode(json);
		String ardIds = node.get("ardIds").asText();
		String date = node.get("date").asText();
		String fujian = node.get("fujian").asText();
		QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIds, ardIds);
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
		AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
		if (riskDetail != null) {
			String ardTitle = riskDetail.getArdTitle();
			AnbiaoRiskDetailInfo anbiaoRiskDetailInfo = new AnbiaoRiskDetailInfo();

			//统一社会信用代码
			if (ardTitle.equals("统一社会信用代码")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("统一社会信用代码");
				riskDetail.setArdRectificationField("jigoubianma");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("jigoubianma");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//企业表
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setJigoubianma(date);
						deal.setYingyezhizhaofujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//统一社会信用代码有效截止日期
			if (ardTitle.equals("统一社会信用代码有效截止日期")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("统一社会信用代码有效截止日期");
				riskDetail.setArdRectificationField("yyzzjzdate");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("yyzzjzdate");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//企业表
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setYyzzjzdate(date);
						deal.setYingyezhizhaofujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//道路运输许可证号
			if (ardTitle.equals("道路运输许可证号")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("道路运输许可证号");
				riskDetail.setArdRectificationField("daoluxukezhenghao");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("daoluxukezhenghao");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//企业表
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setDaoluxukezhenghao(date);
						deal.setDaoluyunshuzhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//道路运输许可证有效截止日期
			if (ardTitle.equals("道路运输许可证有效截止日期")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("道路运输许可证有效截止日期");
				riskDetail.setArdRectificationField("daoluyunshuzhengjieshuriqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("daoluyunshuzhengjieshuriqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//企业表
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setDaoluyunshuzhengjieshuriqi(date);
						deal.setDaoluyunshuzhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//经营许可证号
			if (ardTitle.equals("经营许可证号")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("经营许可证号");
				riskDetail.setArdRectificationField("jingyingxukezhengbianma");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("jingyingxukezhengbianma");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//企业表
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setJingyingxukezhengbianma(date);
						deal.setJingyingxukezhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//合同有效截止日期
			if (ardTitle.equals("合同有效截止日期")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("合同有效截止日期");
				riskDetail.setArdRectificationField("htlastdate");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("htlastdate");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//企业表
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setLaodonghetongjieshuriqi(date);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

			//经营许可证有效截止日期
			if (ardTitle.equals("经营许可证有效截止日期")) {
				riskDetail.setArdIsRectification("1");
				riskDetail.setArdRectificationByIds(user.getUserId().toString());
				riskDetail.setArdRectificationByName(user.getUserName());
				riskDetail.setArdRectificationDate(DateUtil.now());
				riskDetail.setArdModularName("经营许可证有效截止日期");
				riskDetail.setArdRectificationField("jingyingxukezhengyouxiaoqi");
				riskDetail.setArdRectificationValue(date);
				riskDetail.setArdRectificationFieldType("String");
				riskDetail.setArdRectificationEnclosure(fujian);
				boolean b = riskDetailService.updateById(riskDetail);
				if (b == true) {
					//整改内容
					anbiaoRiskDetailInfo.setArdRiskIds(ardIds);
					anbiaoRiskDetailInfo.setArdRectificationByIds(user.getUserId().toString());
					anbiaoRiskDetailInfo.setArdRectificationByName(user.getUserName());
					anbiaoRiskDetailInfo.setArdRectificationDate(DateUtil.now());
					anbiaoRiskDetailInfo.setArdRectificationField("jingyingxukezhengyouxiaoqi");
					anbiaoRiskDetailInfo.setArdRectificationValue(date);
					anbiaoRiskDetailInfo.setArdRectificationFieldType("String");
					int insert = detailInfoService.getBaseMapper().insert(anbiaoRiskDetailInfo);
					if (insert > 0) {
						//企业表
						QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<>();
						organizationsQueryWrapper.lambda().eq(Organizations::getId, riskDetail.getArdAssociationValue());
						organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete, 0);
						Organizations deal = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
						deal.setJingyingxukezhengyouxiaoqi(date);
						deal.setJingyingxukezhengfujian(fujian);
						deal.setCaozuorenid(user.getUserId());
						deal.setCaozuoshijian(DateUtil.now());
						deal.setCaozuoren(user.getUserName());
						boolean b1 = organizationsService.updateById(deal);
						if (b1 == true) {
							aa++;
						}
					}
				}
			}

		}
		if (aa > 0) {
			r.setMsg("风险处理成功");
			r.setCode(200);
			r.setSuccess(true);
		} else {
			r.setMsg("风险处理失败");
			r.setCode(500);
			r.setSuccess(false);
		}
		return r;
	}

	@GetMapping("/getByCount")
	@ApiLog("风险--首页数据统计")
	@ApiOperation(value = "风险--首页数据统计", notes = "传入企业ID,日期", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "企业ID", required = true),
		@ApiImplicitParam(name = "date", value = "日期(yyyy-MM)", required = true)
	})
	public R<List<AnbiaoRiskDetailVO>> getByCount(String deptId, String date, BladeUser user) {
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("未授权");
			rs.setSuccess(false);
			return rs;
		}
		List<AnbiaoRiskDetailVO> list = riskDetailService.selectByCount(deptId, date);
		if (list.size() < 1) {
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功，暂无数据");
		} else {
			rs.setData(list);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功");
		}
		return rs;
	}

	@GetMapping("/getByDateCount")
	@ApiLog("风险--一级穿透--折线图")
	@ApiOperation(value = "风险--一级穿透--折线图", notes = "传入企业ID,日期", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "企业ID", required = true),
		@ApiImplicitParam(name = "date", value = "日期(yyyy-MM)", required = true)
	})
	public R<List<AnbiaoRiskDetailVO>> getByDateCount(String deptId, String date, BladeUser user) {
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("未授权");
			rs.setSuccess(false);
			return rs;
		}
		List<AnbiaoRiskDetailVO> list = riskDetailService.selectByDateCount(deptId, date);
		if (list.size() < 1) {
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功，暂无数据");
		} else {
			rs.setData(list);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功");
		}
		return rs;
	}

	@GetMapping("/getByCategoryCount")
	@ApiLog("风险--一级穿透--隐患列表")
	@ApiOperation(value = "风险--一级穿透--隐患列表", notes = "传入企业ID,日期,隐患类别", position = 6)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "企业ID", required = true),
		@ApiImplicitParam(name = "date", value = "日期(yyyy-MM)", required = true),
		@ApiImplicitParam(name = "category", value = "隐患类别", required = true)
	})
	public R<List<AnbiaoRiskDetailVO>> getByCategoryCount(String deptId, String date, String category, BladeUser user) {
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("未授权");
			rs.setSuccess(false);
			return rs;
		}
		List<AnbiaoRiskDetailVO> list = riskDetailService.selectByCategoryCount(deptId, date, category);
		if (list.size() < 1) {
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功，暂无数据");
		} else {
			rs.setData(list);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功");
		}
		return rs;
	}

	@GetMapping("/selectByCategoryMXList")
	@ApiLog("风险--二级穿透--隐患明细列表")
	@ApiOperation(value = "风险--二级穿透--隐患明细列表", notes = "传入企业ID,日期,隐患类别", position = 7)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptId", value = "企业ID", required = true),
		@ApiImplicitParam(name = "date", value = "日期(yyyy-MM)", required = true),
		@ApiImplicitParam(name = "category", value = "隐患类别", required = true),
		@ApiImplicitParam(name = "ardContent", value = "风险内容", required = true)
	})
	public R<List<AnbiaoRiskDetailVO>> selectByCategoryMXList(String deptId, String date, String category, String ardContent, BladeUser user) {
		R rs = new R();
		if (user == null) {
			rs.setCode(401);
			rs.setMsg("未授权");
			rs.setSuccess(false);
			return rs;
		}
		List<AnbiaoRiskDetailVO> list = riskDetailService.selectByCategoryMXCount(deptId, date, category, ardContent);
		if (list.size() < 1) {
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功，暂无数据");
		} else {
			rs.setData(list);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取成功");
		}
		return rs;
	}

	@PostMapping("/getByCategoryMXList")
	@ApiLog("风险--二级穿透--隐患明细列表")
	@ApiOperation(value = "风险--二级穿透--隐患明细列表", notes = "传入RiskPage", position = 8)
	public R<RiskPage<AnbiaoRiskDetailVO>> getByCategoryMXList(@RequestBody RiskPage riskPage) {
		RiskPage<AnbiaoRiskDetailVO> pages = riskDetailService.selectByCategoryMXCountPage(riskPage);
		return R.data(pages);
	}


	@PostMapping("/systemInsert")
	@ApiLog("新增-制度风险统计信息")
	@ApiOperation(value = "新增-制度风险统计信息", position = 1)
	public R systemInsert(BladeUser user) throws ParseException {
		R r = new R();
		int aa = 0;
		List<AnbiaoSystemRiskVO> riskVOList = riskDetailService.selectSystemRisk();
		if (riskVOList.size() > 0) {
			for (AnbiaoSystemRiskVO riskVO : riskVOList) {

				//安全责任书
				if (Double.doubleToLongBits(Double.parseDouble(riskVO.getAnquanzerenshu())) < Double.doubleToLongBits(100.00)) {
					AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
					riskDetail.setArdDeptIds(riskVO.getDeptId());
					riskDetail.setArdMajorCategories("1");
					riskDetail.setArdSubCategory("101");
					riskDetail.setArdTitle("安全责任书信息");
					riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
					riskDetail.setArdIsRectification("0");
					riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
					riskDetail.setArdAssociationField("id");
					riskDetail.setArdAssociationValue(riskVO.getJiaShiYuanId());
					riskDetail.setArdContent("安全责任书信息未完善");
					riskDetail.setArdType("未完善");
					riskDetail.setArdPercentage(riskVO.getAnquanzerenshu() + "%");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1 == null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert > 0) {
							aa++;
						}
					} else {
						int update = riskDetailService.getBaseMapper().updateById(riskDetail);
						if (update > 0) {
							aa++;
						}
					}
				}

				//入职表
				if (Double.doubleToLongBits(Double.parseDouble(riskVO.getRuzhi())) < Double.doubleToLongBits(100.00)) {
					AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
					riskDetail.setArdDeptIds(riskVO.getDeptId());
					riskDetail.setArdMajorCategories("1");
					riskDetail.setArdSubCategory("103");
					riskDetail.setArdTitle("入职信息");
					riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
					riskDetail.setArdIsRectification("0");
					riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
					riskDetail.setArdAssociationField("id");
					riskDetail.setArdAssociationValue(riskVO.getJiaShiYuanId());
					riskDetail.setArdContent("入职信息未完善");
					riskDetail.setArdType("未完善");
					riskDetail.setArdPercentage(riskVO.getRuzhi() + "%");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1 == null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert > 0) {
							aa++;
						}
					} else {
						int update = riskDetailService.getBaseMapper().updateById(riskDetail);
						if (update > 0) {
							aa++;
						}
					}
				}

				//危害告知书
				if (Double.doubleToLongBits(Double.parseDouble(riskVO.getWeihaigaozhishu())) < Double.doubleToLongBits(100.00)) {
					AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
					riskDetail.setArdDeptIds(riskVO.getDeptId());
					riskDetail.setArdMajorCategories("1");
					riskDetail.setArdSubCategory("102");
					riskDetail.setArdTitle("危害告知书信息");
					riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
					riskDetail.setArdIsRectification("0");
					riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
					riskDetail.setArdAssociationField("id");
					riskDetail.setArdAssociationValue(riskVO.getJiaShiYuanId());
					riskDetail.setArdContent("危害告知书信息未完善");
					riskDetail.setArdType("未完善");
					riskDetail.setArdPercentage(riskVO.getWeihaigaozhishu() + "%");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1 == null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert > 0) {
							aa++;
						}
					} else {
						int update = riskDetailService.getBaseMapper().updateById(riskDetail);
						if (update > 0) {
							aa++;
						}
					}
				}

				//劳动合同
				if (Double.doubleToLongBits(Double.parseDouble(riskVO.getLaodonghetong())) < Double.doubleToLongBits(100.00)) {
					AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
					riskDetail.setArdDeptIds(riskVO.getDeptId());
					riskDetail.setArdMajorCategories("1");
					riskDetail.setArdSubCategory("104");
					riskDetail.setArdTitle("劳动合同信息");
					riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
					riskDetail.setArdIsRectification("0");
					riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
					riskDetail.setArdAssociationField("id");
					riskDetail.setArdAssociationValue(riskVO.getJiaShiYuanId());
					riskDetail.setArdContent("劳动合同信息未完善");
					riskDetail.setArdType("未完善");
					riskDetail.setArdPercentage(riskVO.getLaodonghetong() + "%");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds, riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification, "0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1 == null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert > 0) {
							aa++;
						}
					} else {
						int update = riskDetailService.getBaseMapper().updateById(riskDetail);
						if (update > 0) {
							aa++;
						}
					}
				}

			}
		}
		if (aa > 0) {
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("新增成功");
		} else {
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("无风险新增");
		}
		return r;
	}

	@PostMapping("/ledgerInsert")
	@ApiLog("新增-台账风险统计信息")
	@ApiOperation(value = "新增-台账风险统计信息", position = 1)
	public R ledgerInsert(BladeUser user) throws ParseException {
		R r = new R();
		int aa = 0;
		int bb=0;
		int timeDifference = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<String> allMonths = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
		List<Dept> depts = organizationsService.selectDept();
		for (Dept dept : depts) {

			String now = DateUtil.now();
			String time2 = now.substring(0, 10);
			String localDateTime = LocalDateTime.now().minusDays(1).toString();
			String time = localDateTime.substring(0, 10);
			int month = Integer.parseInt(now.substring(5, 7));
			int year = Integer.parseInt(now.substring(0, 4));
			ArrayList<String> months = new ArrayList<>();
			for (int i = 0; i < month; i++) {
				months.add(allMonths.get(i));
			}

			//培训
			List<AnbiaoSafetyTraining> anbiaoSafetyTrainings = safetyTrainingService.selectSafetyTrainingMonth(year,"5503");
				ArrayList<String> trainingMonths = new ArrayList<>();
				for (AnbiaoSafetyTraining safetyTraining: anbiaoSafetyTrainings) {
						String i2 = safetyTraining.getMonths().substring(5, 7);
						trainingMonths.add(i2);
				}
				if (trainingMonths.size() == 0 ) {
					trainingMonths.add("0");
				}
					for (String a : months) {
						boolean contains = trainingMonths.contains(a);
						if (contains != true) {
							AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
							riskDetail.setArdDeptIds(dept.getId().toString());
							riskDetail.setArdMajorCategories("2");
							riskDetail.setArdSubCategory("200");
							riskDetail.setArdTitle("培训计划");
							riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
							riskDetail.setArdIsRectification("0");
							riskDetail.setArdAssociationTable("anbiao_safety_training");
							riskDetail.setArdAssociationField("ast_training_start_time");
							String s = String.valueOf(year);
							riskDetail.setArdAssociationValue(s + "-" + a);
							riskDetail.setArdContent("未开展周期性培训计划");
							riskDetail.setArdType("未开展");
							QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
							riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
							riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail.getArdContent());
							riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,dept.getId());
							AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
							if (riskDetail1 == null) {
								int insert = riskDetailService.getBaseMapper().insert(riskDetail);
								if (insert > 0) {
									aa++;
								}
							}
						}
					}



			//检查
			QueryWrapper<AnbiaoCarExamineInfo> carExamineInfoQueryWrapper = new QueryWrapper<>();
			carExamineInfoQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getDeptid,dept.getId());
			carExamineInfoQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getStatus,1);
			carExamineInfoQueryWrapper.lambda().eq(AnbiaoCarExamineInfo::getIsdelete,0);
			List<AnbiaoCarExamineInfo> carExamineInfos = carExamineInfoService.getBaseMapper().selectList(carExamineInfoQueryWrapper);
			for (AnbiaoCarExamineInfo carExamineInfo : carExamineInfos) {
				AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
				riskDetail.setArdDeptIds(dept.getId().toString());
				riskDetail.setArdMajorCategories("2");
				riskDetail.setArdSubCategory("201");
				riskDetail.setArdTitle("安全检查");
				riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail.setArdIsRectification("0");
				riskDetail.setArdAssociationTable("anbiao_car_examine_info");
				riskDetail.setArdAssociationField("id");
				riskDetail.setArdAssociationValue(carExamineInfo.getId().toString());
				riskDetail.setArdContent("未按要求进行安全检查");
				riskDetail.setArdType("未检查");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					int insert = riskDetailService.getBaseMapper().insert(riskDetail);
					if (insert > 0) {
						aa++;
					}
				}
			}
			QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
			vehicleQueryWrapper.lambda().eq(Vehicle::getDeptId,dept.getId());
			vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel,0);
			List<Vehicle> vehicles = vehicleService.getBaseMapper().selectList(vehicleQueryWrapper);
			for (Vehicle vehicle : vehicles) {
				String vehicleId = vehicle.getId();
				QueryWrapper<AnbiaoCarExamineInfo> carExamineInfoQueryWrapper1 = new QueryWrapper<>();
				carExamineInfoQueryWrapper1.lambda().eq(AnbiaoCarExamineInfo::getVehid,vehicleId);
				carExamineInfoQueryWrapper1.lambda().eq(AnbiaoCarExamineInfo::getDate,time);
				AnbiaoCarExamineInfo carExamineInfo = carExamineInfoService.getBaseMapper().selectOne(carExamineInfoQueryWrapper1);
				if (carExamineInfo==null){
					AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
					riskDetail.setArdDeptIds(dept.getId().toString());
					riskDetail.setArdMajorCategories("2");
					riskDetail.setArdSubCategory("201");
					riskDetail.setArdTitle("安全检查");
					riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
					riskDetail.setArdIsRectification("0");
					riskDetail.setArdAssociationTable("anbiao_vehicle");
					riskDetail.setArdAssociationField("id");
					riskDetail.setArdAssociationValue(vehicleId);
					riskDetail.setArdContent("未按要求进行安全检查");
					riskDetail.setArdType("未检查");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDiscoveryDate,time2);
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1 == null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert > 0) {
							aa++;
						}
					}
				}
			}


			//安全会议
			List<AnbiaoAnquanhuiyi> anbiaoAnquanhuiyis = anquanhuiyiService.selectAnquanHuiYiMonth(year,dept.getId().toString());
				ArrayList<String> anquanhuiyiMonths = new ArrayList<>();
				for (AnbiaoAnquanhuiyi anquanhuiyi: anbiaoAnquanhuiyis) {
						String i2 = anquanhuiyi.getMonths().substring(5, 7);
						anquanhuiyiMonths.add(i2);
				}
				if (anquanhuiyiMonths.size() == 0) {
					anquanhuiyiMonths.add("0");
				}
					for (String a : months) {
						boolean contains = anquanhuiyiMonths.contains(a);
						if (contains != true) {
						AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
						riskDetail.setArdDeptIds(dept.getId().toString());
						riskDetail.setArdMajorCategories("2");
						riskDetail.setArdSubCategory("202");
						riskDetail.setArdTitle("安全会议");
						riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
						riskDetail.setArdIsRectification("0");
						riskDetail.setArdAssociationTable("anbiao_anquanhuiyi");
						riskDetail.setArdAssociationField("huiyikaishishijian");
						String s = String.valueOf(year);
						riskDetail.setArdAssociationValue(s+"-"+a);
						riskDetail.setArdContent("未开展周期性安全会议");
						riskDetail.setArdType("未开展");
						QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
						riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
						riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent, riskDetail.getArdContent());
						riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,dept.getId());
						AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
						if (riskDetail1 == null) {
							int insert = riskDetailService.getBaseMapper().insert(riskDetail);
							if (insert > 0) {
								aa++;
							}
						}
						}
					}



			//劳保
			List<LaborEntity> laborEntities = laborService.selectInsurance(dept.getId());
			for (LaborEntity labor: laborEntities) {
				Date fafangriqi = formatter.parse(labor.getAliIssueDate());
				Date now2 = formatter.parse(DateUtil.now());
				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(now2);
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(fafangriqi);
				int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
				int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
				int year1 = calendar1.get(Calendar.YEAR);
				int year2 = calendar2.get(Calendar.YEAR);
				if (year1 != year2) {//不同年
					int timeDistance = 0;
					if (year2 < year1) {
						timeDifference = -1;
					} else {
						for (int x = year1; x < year2; x++) { //闰年
							if (x % 4 == 0 && x % 100 != 0 || x % 400 == 0) {
								timeDistance += 366;
							} else { // 不是闰年
								timeDistance += 365;
							}
						}
						timeDifference = timeDistance + (day2 - day1);
					}
				} else {
					timeDifference = day2 - day1;
				}
				if (timeDifference < 0) {
					Integer aliIssueQuantity = labor.getAliIssueQuantity();
					LaborlingquEntity laborlingquEntity = lingquService.selectSumReceive(labor.getAliIds());
					int sumReceive = laborlingquEntity.getSumReceive();
					int i = aliIssueQuantity - sumReceive;
						if (i != 0) {
						AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
						riskDetail.setArdDeptIds(dept.getId().toString());
						riskDetail.setArdMajorCategories("2");
						riskDetail.setArdSubCategory("203");
						riskDetail.setArdTitle("劳保用品");
						riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
						riskDetail.setArdIsRectification("0");
						riskDetail.setArdAssociationTable("anbiao_labor_insurance");
						riskDetail.setArdAssociationField("ali_ids");
						riskDetail.setArdAssociationValue(labor.getAliIds());
						riskDetail.setArdContent("发放劳保产品未领取完毕");
						riskDetail.setArdType("未领取完毕");
						QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
						riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
						AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
						if (riskDetail1 == null) {
							int insert = riskDetailService.getBaseMapper().insert(riskDetail);
							if (insert > 0) {
								aa++;
							}
						}
					}

				}
			}

			//投入
			List<SafeInvestmentDTO> safeInvestmentDTOS = safeInvestmentService.selectYears(year,"5498");
//			ArrayList<String> safeInvestmentYears = new ArrayList<>();
//			for (SafeInvestmentDTO safeInvestmentDTO: safeInvestmentDTOS) {
//				safeInvestmentYears.add(safeInvestmentDTO.getAsiYear().toString());
//			}
//			for (int i = 2023; i <=year ; i++) {
//				String s = String.valueOf(year-1);
//				boolean contains = safeInvestmentYears.contains(s);
//				if (contains != true)
				if (safeInvestmentDTOS.size()==0){
					AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
					riskDetail.setArdDeptIds(dept.getId().toString());
					riskDetail.setArdMajorCategories("2");
					riskDetail.setArdSubCategory("204");
					riskDetail.setArdTitle("安全投入");
					riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
					riskDetail.setArdIsRectification("0");
					riskDetail.setArdAssociationTable("anbiao_safety_input");
					riskDetail.setArdAssociationField("asi_year");
					String s=String.valueOf(year-1);
					riskDetail.setArdAssociationValue(s);
					riskDetail.setArdContent("未指定周期性安全投入");
					riskDetail.setArdType("未投入");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1 == null) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert > 0) {
							aa++;
						}
					}
				}
//			}

			//隐患
			QueryWrapper<AnbiaoHiddenDanger> hiddenDangerQueryWrapper = new QueryWrapper<>();
			hiddenDangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdDeptIds, dept.getId());
			hiddenDangerQueryWrapper.lambda().eq(AnbiaoHiddenDanger::getAhdDelete, 0);
			List<AnbiaoHiddenDanger> anbiaoHiddenDangers = hiddenDangerService.getBaseMapper().selectList(hiddenDangerQueryWrapper);
			for (AnbiaoHiddenDanger hiddenDanger : anbiaoHiddenDangers) {
				AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
				riskDetail.setArdDeptIds(hiddenDanger.getAhdDeptIds());
				riskDetail.setArdMajorCategories("2");
				riskDetail.setArdSubCategory("206");
				riskDetail.setArdTitle("隐患记录");
				riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail.setArdIsRectification("0");
				riskDetail.setArdAssociationTable("anbiao_hidden_danger");
				riskDetail.setArdAssociationField("ahd_ids");
				riskDetail.setArdAssociationValue(hiddenDanger.getAhdIds());
				riskDetail.setArdContent("隐患记录增加");
				riskDetail.setArdType("增加");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					int insert = riskDetailService.getBaseMapper().insert(riskDetail);
					if (insert > 0) {
						aa++;
					}
				}
			}

			//事故
			List<AccidentReportsDTO> accidentReportsDTOS =accidentReportsService.selectshigubaogao(dept.getId().toString());
			for (AccidentReportsDTO accidentReportsDTO : accidentReportsDTOS) {
				AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
				riskDetail.setArdDeptIds(accidentReportsDTO.getDeptId().toString());
				riskDetail.setArdMajorCategories("2");
				riskDetail.setArdSubCategory("207");
				riskDetail.setArdTitle("事故记录");
				riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail.setArdIsRectification("0");
				riskDetail.setArdAssociationTable("anbiao_shigubaogao");
				riskDetail.setArdAssociationField("id");
				riskDetail.setArdAssociationValue(accidentReportsDTO.getId());
				riskDetail.setArdContent("事故记录增加");
				riskDetail.setArdType("增加");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue, riskDetail.getArdAssociationValue());
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1 == null) {
					int insert = riskDetailService.getBaseMapper().insert(riskDetail);
					if (insert > 0) {
						aa++;
					}
				}
			}

		}
		if (aa > 0) {
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("新增成功");
		} else {
			r.setSuccess(true);
			r.setCode(200);
			r.setMsg("无风险新增");
		}
		return r;
	}


	@PostMapping("/ledgerDetail")
	@ApiLog("详情-台账风险统计信息")
	@ApiOperation(value = "详情-台账风险统计信息", position = 1)
	public R ledgerDetail( BladeUser user) throws ParseException{
		R r = new R();
		String deptId = "1";
		List<LedgerDetailVO> ledgerDetailVOS = riskDetailService.ledgerDetail(deptId);
		return r;
	}

	@PostMapping("/jiashiyuanRiskAll")
	@ApiLog("详情-驾驶员风险统计信息")
	@ApiOperation(value = "详情-驾驶员风险统计信息", position = 1)
	public R<JiashiyuanRiskAllPage<JiashiyuanRiskAllVO>> jiashiyuanRiskAll(@RequestBody JiashiyuanRiskAllPage jiashiyuanRiskAllPage) {
		JiashiyuanRiskAllPage<JiashiyuanRiskAllVO> pages = riskDetailService.selectJiashiyuanRiskAll(jiashiyuanRiskAllPage);
		return R.data(pages);
	}

	@PostMapping("/vehicleRiskAll")
	@ApiLog("详情-车辆风险统计信息")
	@ApiOperation(value = "详情-车辆风险统计信息", position = 1)
	public R<VehicleRiskAllPage<VehicleRiskAllVO>> vehicleRiskAll(@RequestBody VehicleRiskAllPage vehicleRiskAllPage) {
		VehicleRiskAllPage<VehicleRiskAllVO> pages = riskDetailService.selectVehicleRiskAll(vehicleRiskAllPage);
		return R.data(pages);
	}

	@PostMapping("/jiashiyuanRuZhiRiskinsert")
	@ApiLog("新增-驾驶员入职表风险统计信息")
	@ApiOperation(value = "新增-驾驶员入职表风险统计信息", position = 1)
	public R jiashiyuanRuZhiRiskinsert(String jiashiyuanId, BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoJiashiyuanRuzhi> anbiaoJiashiyuanRuzhis = riskDetailService.selectRuZhiRisk(jiashiyuanId);
		for (AnbiaoJiashiyuanRuzhi ruzhi:anbiaoJiashiyuanRuzhis) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"入职表信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,ruzhi.getAjrAjIds());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(ruzhi.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("003");
				riskDetail1.setArdTitle("入职表信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(ruzhi.getAjrAjIds());
				riskDetail1.setArdIsRectification("0");
				if (!ruzhi.getAjrName().equals("0")){
					A=A+"姓名、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrSex().equals("0")){
					A=A+"性别、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrNation().equals("0")){
					A=A+"民族、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrNativePlace().equals("0")){
					A=A+"籍贯、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrBirth().equals("0")){
					A=A+"出生年月、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (ruzhi.getAjrAge()!=0){
					A=A+"年龄、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrPoliticalOutlook().equals("0")){
					A=A+"政治面貌、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrEducation().equals("0")){
					A=A+"学历、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrGraduationSchool().equals("0")){
					A=A+"毕业学校、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrGraduationDate().equals("0")){
					A=A+"毕业时间、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrIdNumber().equals("0")){
					A=A+"身份证号、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrAddress().equals("0")){
					A=A+"家庭地址、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrReceiveDrivingLicense().equals("0")){
					A=A+"领取驾照日期、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrClass().equals("0")){
					A=A+"准驾车型、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (ruzhi.getAjrDrivingExperience() !=0 ){
					A=A+"驾驶年龄、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrHealthStatus().equals("0")){
					A=A+"健康状况、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrHeadPortrait().equals("0")){
					A=A+"本人照片、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrWorkExperience().equals("0")){
					A=A+"工作经历、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrSafeDrivingRecord1().equals("0")){
					A=A+"安全驾驶记录1、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrSafeDrivingRecord2().equals("0")){
					A=A+"安全驾驶记录2、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrSafeDrivingRecord3().equals("0")){
					A=A+"安全驾驶记录3、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrIntegrityAssessmentResults().equals("0")){
					A=A+"诚信考核结果、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}

			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!ruzhi.getAjrName().equals("0")){
					A=A+"姓名、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrSex().equals("0")){
					A=A+"性别、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrNation().equals("0")){
					A=A+"民族、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrNativePlace().equals("0")){
					A=A+"籍贯、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrBirth().equals("0")){
					A=A+"出生年月、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (ruzhi.getAjrAge()!=0){
					A=A+"年龄、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrPoliticalOutlook().equals("0")){
					A=A+"政治面貌、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrEducation().equals("0")){
					A=A+"学历、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrGraduationSchool().equals("0")){
					A=A+"毕业学校、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrGraduationDate().equals("0")){
					A=A+"毕业时间、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrIdNumber().equals("0")){
					A=A+"身份证号、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrAddress().equals("0")){
					A=A+"家庭地址、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrReceiveDrivingLicense().equals("0")){
					A=A+"领取驾照日期、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrClass().equals("0")){
					A=A+"准驾车型、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (ruzhi.getAjrDrivingExperience()!=0){
					A=A+"驾驶年龄、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrHealthStatus().equals("0")){
					A=A+"健康状况、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrHeadPortrait().equals("0")){
					A=A+"本人照片、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrWorkExperience().equals("0")){
					A=A+"工作经历、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrSafeDrivingRecord1().equals("0")){
					A=A+"安全驾驶记录1、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrSafeDrivingRecord2().equals("0")){
					A=A+"安全驾驶记录2、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrSafeDrivingRecord3().equals("0")){
					A=A+"安全驾驶记录3、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!ruzhi.getAjrIntegrityAssessmentResults().equals("0")){
					A=A+"诚信考核结果、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);
				}
			}
		}
			return r;
	}


	@PostMapping("/jiashiyuanShenFenZhengRiskinsert")
	@ApiLog("新增-驾驶员身份证风险统计信息")
	@ApiOperation(value = "新增-驾驶员身份证风险统计信息", position = 1)
	public R jiashiyuanShenFenZhengRiskinsert(String jiashiyuanId,BladeUser user) throws ParseException{
		R r = new R();
		List<JiaShiYuan> jiaShiYuans = riskDetailService.selectShenFenZhengRisk(jiashiyuanId);
		for (JiaShiYuan jiaShiYuan:jiaShiYuans) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"身份证信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,jiaShiYuan.getId());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(jiaShiYuan.getDeptId().toString());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("003");
				riskDetail1.setArdTitle("身份证信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(jiaShiYuan.getId());
				riskDetail1.setArdIsRectification("0");
				if (!jiaShiYuan.getShenfenzhenghao().equals("0")){
					A=A+"身份证号、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!jiaShiYuan.getShenfenzhengchulingriqi().equals("0")){
					A=A+"身份证初领日期、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!jiaShiYuan.getShenfenzhengyouxiaoqi().equals("0")){
					A=A+"身份证有效期、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!jiaShiYuan.getShenfenzhengfujian().equals("0")){
					A=A+"身份证附件正面、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!jiaShiYuan.getShenfenzhengfanmianfujian().equals("0")){
					A=A+"身份证附件反面、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!jiaShiYuan.getShenfenzhenghao().equals("0")){
					A=A+"身份证号、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!jiaShiYuan.getShenfenzhengchulingriqi().equals("0")){
					A=A+"身份证初领日期、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!jiaShiYuan.getShenfenzhengyouxiaoqi().equals("0")){
					A=A+"身份证有效期、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!jiaShiYuan.getShenfenzhengfujian().equals("0")){
					A=A+"身份证附件正面、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!jiaShiYuan.getShenfenzhengfanmianfujian().equals("0")){
					A=A+"身份证附件反面、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);
				}
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanJiaShiZhengRiskinsert")
	@ApiLog("新增-驾驶员驾驶证风险统计信息")
	@ApiOperation(value = "新增-驾驶员驾驶证风险统计信息", position = 1)
	public R jiashiyuanJiaShiZhengRiskinsert(String jiashiyuanId,BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoJiashiyuanJiashizheng> jiashiyuanJiashizhengs = riskDetailService.selectJiaShiZhengRisk(jiashiyuanId);
		for (AnbiaoJiashiyuanJiashizheng jiashizheng:jiashiyuanJiashizhengs) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"驾驶证信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,jiashizheng.getAjjAjIds());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(jiashizheng.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("003");
				riskDetail1.setArdTitle("驾驶证信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(jiashizheng.getAjjAjIds());
				riskDetail1.setArdIsRectification("0");
				if (!jiashizheng.getAjjFileNo().equals("0")){
					A=A+"档案编号、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!jiashizheng.getAjjValidPeriodStart().equals("0")){
					A=A+"有效期（起）、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!jiashizheng.getAjjValidPeriodEnd().equals("0")){
					A=A+"有效期（止）、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!jiashizheng.getAjjFrontPhotoAddress().equals("0")){
					A=A+"驾驶证正面照片、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!jiashizheng.getAjjAttachedPhotos().equals("0")){
					A=A+"驾驶证反面照片、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!jiashizheng.getAjjFileNo().equals("0")){
					A=A+"档案编号、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!jiashizheng.getAjjValidPeriodStart().equals("0")){
					A=A+"有效期（起）、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!jiashizheng.getAjjValidPeriodEnd().equals("0")){
					A=A+"有效期（止）、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!jiashizheng.getAjjFrontPhotoAddress().equals("0")){
					A=A+"驾驶证正面照片、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!jiashizheng.getAjjAttachedPhotos().equals("0")){
					A=A+"驾驶证反面照片、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanCongYeZhengRiskinsert")
	@ApiLog("新增-驾驶员从业资格证风险统计信息")
	@ApiOperation(value = "新增-驾驶员从业资格证风险统计信息", position = 1)
	public R jiashiyuanCongYeZhengRiskinsert(String jiashiyuanId,BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoJiashiyuanCongyezigezheng> jiashiyuanCongyezigezhengs = riskDetailService.selectCongYeZhengRisk(jiashiyuanId);
		for (AnbiaoJiashiyuanCongyezigezheng congyezigezheng:jiashiyuanCongyezigezhengs) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"从业资格证信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,congyezigezheng.getAjcAjIds());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(congyezigezheng.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("003");
				riskDetail1.setArdTitle("从业资格证信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(congyezigezheng.getAjcAjIds());
				riskDetail1.setArdIsRectification("0");
				if (!congyezigezheng.getAjcCertificateNo().equals("0")){
					A=A+"从业资格证件号、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!congyezigezheng.getAjcInitialIssuing().equals("0")){
					A=A+"初次发证时间、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!congyezigezheng.getAjcValidUntil().equals("0")){
					A=A+"有效期至、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!congyezigezheng.getAjcLicence().equals("0")){
					A=A+"从业资格证照片、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!congyezigezheng.getAjcCertificateNo().equals("0")){
					A=A+"从业资格证件号、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!congyezigezheng.getAjcInitialIssuing().equals("0")){
					A=A+"初次发证时间、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!congyezigezheng.getAjcValidUntil().equals("0")){
					A=A+"有效期至、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!congyezigezheng.getAjcLicence().equals("0")){
					A=A+"从业资格证照片、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanTiJianRiskinsert")
	@ApiLog("新增-驾驶员体检表风险统计信息")
	@ApiOperation(value = "新增-驾驶员体检表风险统计信息", position = 1)
	public R jiashiyuanTiJianRiskinsert(String jiashiyuanId,BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoJiashiyuanTijian> tijians = riskDetailService.selectTiJianRisk(jiashiyuanId);
		for (AnbiaoJiashiyuanTijian tijian:tijians) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"体检表信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,tijian.getAjtAjIds());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(tijian.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("003");
				riskDetail1.setArdTitle("体检表信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(tijian.getAjtAjIds());
				riskDetail1.setArdIsRectification("0");
				if (!tijian.getAjtPhysicalExaminationDate().equals("0")){
					A=A+"体检日期、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!tijian.getAjtEnclosure().equals("0")){
					A=A+"体检附件、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!tijian.getAjtPhysicalExaminationDate().equals("0")){
					A=A+"体检日期、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!tijian.getAjtEnclosure().equals("0")){
					A=A+"体检附件、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanGangQianPeiXunRiskinsert")
	@ApiLog("新增-驾驶员岗前培训风险统计信息")
	@ApiOperation(value = "新增-驾驶员岗前培训风险统计信息", position = 1)
	public R jiashiyuanGangQianPeiXunRiskinsert(String jiashiyuanId,BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixuns = riskDetailService.selectGangQianPeiXunRisk(jiashiyuanId);
		for (AnbiaoJiashiyuanGangqianpeixun gangqianpeixun:gangqianpeixuns) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"岗前培训信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,gangqianpeixun.getAjgAjIds());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(gangqianpeixun.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("003");
				riskDetail1.setArdTitle("岗前培训信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(gangqianpeixun.getAjgAjIds());
				riskDetail1.setArdIsRectification("0");
				if (!gangqianpeixun.getAjgTrainingEnclosure().equals("0")){
					A=A+"培训附件";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!gangqianpeixun.getAjgTrainingEnclosure().equals("0")){
					A=A+"培训附件";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}

	@PostMapping("/jiashiyuanWuZeZhengMingRiskinsert")
	@ApiLog("新增-驾驶员无责证明风险统计信息")
	@ApiOperation(value = "新增-驾驶员无责证明风险统计信息", position = 1)
	public R jiashiyuanWuZeZhengMingRiskinsert(String jiashiyuanId,BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoJiashiyuanWuzezhengming> wuzezhengmings = riskDetailService.selectWuZeZhengMingRisk(jiashiyuanId);
		for (AnbiaoJiashiyuanWuzezhengming wuzezhengming:wuzezhengmings) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"无责证明信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,wuzezhengming.getAjwAjIds());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(wuzezhengming.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("003");
				riskDetail1.setArdTitle("无责证明信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(wuzezhengming.getAjwAjIds());
				riskDetail1.setArdIsRectification("0");
				if (!wuzezhengming.getAjwDate().equals("0")){
					A=A+"报告日期、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!wuzezhengming.getAjwEnclosure().equals("0")){
					A=A+"无责证明附件、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!wuzezhengming.getAjwDate().equals("0")){
					A=A+"报告日期、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!wuzezhengming.getAjwEnclosure().equals("0")){
					A=A+"无责证明附件、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}

	@PostMapping("/jiashiyuanAnQuanZeRenShuRiskinsert")
	@ApiLog("新增-驾驶员安全责任书风险统计信息")
	@ApiOperation(value = "新增-驾驶员安全责任书风险统计信息", position = 1)
	public R jiashiyuanAnQuanZeRenShuRiskinsert(String jiashiyuanId,BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshus = riskDetailService.selectAnQuanZeRenShuRisk(jiashiyuanId);
		for (AnbiaoJiashiyuanAnquanzerenshu anquanzerenshu:anquanzerenshus) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"安全责任书信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,anquanzerenshu.getAjaAjIds());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(anquanzerenshu.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("003");
				riskDetail1.setArdTitle("安全责任书信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(anquanzerenshu.getAjaAjIds());
				riskDetail1.setArdIsRectification("0");
				if (!anquanzerenshu.getAjaAutographTime().equals("0")){
					A=A+"签字时间、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!anquanzerenshu.getAjaAutographEnclosure().equals("0")){
					A=A+"签名附件、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!anquanzerenshu.getAjaAutographTime().equals("0")){
					A=A+"签字时间、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!anquanzerenshu.getAjaAutographEnclosure().equals("0")){
					A=A+"签名附件、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanWeiHaiGaoZhiShuRiskinsert")
	@ApiLog("新增-驾驶员危害告知书风险统计信息")
	@ApiOperation(value = "新增-驾驶员危害告知书风险统计信息", position = 1)
	public R jiashiyuanWeiHaiGaoZhiShuRiskinsert(String jiashiyuanId,BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoJiashiyuanWeihaigaozhishu> weihaigaozhishus = riskDetailService.selectWeiHaiGaoZhiShuRisk(jiashiyuanId);
		for (AnbiaoJiashiyuanWeihaigaozhishu weihaigaozhishu:weihaigaozhishus) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"危害告知书信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,weihaigaozhishu.getAjwAjIds());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(weihaigaozhishu.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("003");
				riskDetail1.setArdTitle("危害告知书信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(weihaigaozhishu.getAjwAjIds());
				riskDetail1.setArdIsRectification("0");
				if (!weihaigaozhishu.getAjwAutographTime().equals("0")){
					A=A+"签字时间、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!weihaigaozhishu.getAjwAutographEnclosure().equals("0")){
					A=A+"签名附件、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!weihaigaozhishu.getAjwAutographTime().equals("0")){
					A=A+"签字时间、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!weihaigaozhishu.getAjwAutographEnclosure().equals("0")){
					A=A+"签名附件、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}

	@PostMapping("/jiashiyuanLaoDongHeTongRiskinsert")
	@ApiLog("新增-驾驶员劳动合同风险统计信息")
	@ApiOperation(value = "新增-驾驶员劳动合同风险统计信息", position = 1)
	public R jiashiyuanLaoDongHeTongRiskinsert(String jiashiyuanId,BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoJiashiyuanLaodonghetong> laodonghetongs = riskDetailService.selectLaoDongHeTongRisk(jiashiyuanId);
		for (AnbiaoJiashiyuanLaodonghetong laodonghetong:laodonghetongs) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"劳动合同信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,laodonghetong.getAjwAjIds());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(laodonghetong.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("003");
				riskDetail1.setArdTitle("劳动合同信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(laodonghetong.getAjwAjIds());
				riskDetail1.setArdIsRectification("0");
				if (!laodonghetong.getAjwAutographTime().equals("0")){
					A=A+"签字时间、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!laodonghetong.getAjwAutographEnclosure().equals("0")){
					A=A+"签名附件、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!laodonghetong.getAjwAutographTime().equals("0")){
					A=A+"签字时间、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!laodonghetong.getAjwAutographEnclosure().equals("0")){
					A=A+"签名附件、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanAnQuanHuiYiRiskinsert")
	@ApiLog("新增-驾驶员安全会议风险统计信息")
	@ApiOperation(value = "新增-驾驶员安全会议风险统计信息", position = 1)
	public R jiashiyuanAnQuanHuiYiRiskinsert(BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoAnquanhuiyi> anbiaoAnquanhuiyis = riskDetailService.selectAnQuanHuiYiRisk();
		for (AnbiaoAnquanhuiyi anquanhuiyi:anbiaoAnquanhuiyis) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"未按时参加安全会议");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,anquanhuiyi.getJiashiyuanid());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,anquanhuiyi.getHuiyimingcheng());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(anquanhuiyi.getDeptId().toString());
				riskDetail1.setArdMajorCategories("2");
				riskDetail1.setArdSubCategory("202");
				riskDetail1.setArdTitle("未按时参加安全会议");
				riskDetail1.setArdType("未按时参加");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(anquanhuiyi.getJiashiyuanid());
				riskDetail1.setArdIsRectification("0");
				riskDetail1.setArdContent(anquanhuiyi.getHuiyimingcheng());
				riskDetailService.getBaseMapper().insert(riskDetail1);
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanAnQuanPeiXunRiskinsert")
	@ApiLog("新增-驾驶员安全培训风险统计信息")
	@ApiOperation(value = "新增-驾驶员安全培训风险统计信息", position = 1)
	public R jiashiyuanAnQuanPeiXunRiskinsert(BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoSafetyTraining> safetyTrainings = riskDetailService.selectAnQuanPeiXunRisk();
		for (AnbiaoSafetyTraining safetyTraining:safetyTrainings) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"未按时参加安全培训");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,safetyTraining.getJiashiyuanid());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,safetyTraining.getAstTrainingTopic());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(safetyTraining.getAstDeptIds());
				riskDetail1.setArdMajorCategories("2");
				riskDetail1.setArdSubCategory("200");
				riskDetail1.setArdTitle("未按时参加安全培训");
				riskDetail1.setArdType("未按时参加");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(safetyTraining.getJiashiyuanid());
				riskDetail1.setArdIsRectification("0");
				String A=safetyTraining.getAstTrainingTopic();
				riskDetail1.setArdContent(A);
				riskDetailService.getBaseMapper().insert(riskDetail1);
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanYinHuanPaiChaRiskinsert")
	@ApiLog("新增-驾驶员隐患排查风险统计信息")
	@ApiOperation(value = "新增-驾驶员隐患排查风险统计信息", position = 1)
	public R jiashiyuanYinHuanPaiChaRiskinsert(BladeUser user) throws ParseException{
		R r = new R();
		List<AnbiaoHiddenDangerVO> yinHuanPaiChas = riskDetailService.selectYinHuanPaiChaRisk();
		for (AnbiaoHiddenDangerVO yinHuanPaiCha:yinHuanPaiChas) {
			if (StringUtils.isBlank(yinHuanPaiCha.getAhdDescribe()) || yinHuanPaiCha.getAhdDescribe().equals("null")){
				yinHuanPaiCha.setAhdDescribe("");
			}
			String s = yinHuanPaiCha.getCheliangpaizhao() + yinHuanPaiCha.getAhdDescribe();
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"隐患排查");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,yinHuanPaiCha.getJiashiyuanid());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,s);
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(yinHuanPaiCha.getAhdDeptIds());
				riskDetail1.setArdMajorCategories("2");
				riskDetail1.setArdSubCategory("206");
				riskDetail1.setArdTitle("隐患排查");
				riskDetail1.setArdType("未整改");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(yinHuanPaiCha.getJiashiyuanid());
				riskDetail1.setArdIsRectification("0");
				riskDetail1.setArdContent(s);
				riskDetailService.getBaseMapper().insert(riskDetail1);
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanWeiXiuDengJiRiskinsert")
	@ApiLog("新增-驾驶员维修登记风险统计信息")
	@ApiOperation(value = "新增-驾驶员维修登记风险统计信息", position = 1)
	public R jiashiyuanWeiXiuDengJiRiskinsert(BladeUser user) throws ParseException{
		R r = new R();
		List<BaoYangWeiXiuVO> baoYangWeiXius = riskDetailService.selectWeiXiuDengJiRisk();
		for (BaoYangWeiXiuVO baoYangWeiXiu:baoYangWeiXius) {
			if (StringUtils.isBlank(baoYangWeiXiu.getAcbRepairReason()) || baoYangWeiXiu.getAcbRepairReason().equals("null")){
				baoYangWeiXiu.setAcbRepairReason("");
			}
			String s = baoYangWeiXiu.getCheliangpaizhao() + baoYangWeiXiu.getAcbRepairReason();
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"维修登记");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,baoYangWeiXiu.getDriverId());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,s);
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(baoYangWeiXiu.getDeptId().toString());
				riskDetail1.setArdMajorCategories("2");
				riskDetail1.setArdSubCategory("208");
				riskDetail1.setArdTitle("维修登记");
				riskDetail1.setArdType("未整改");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(baoYangWeiXiu.getDriverId());
				riskDetail1.setArdIsRectification("0");
				riskDetail1.setArdContent(s);
				riskDetailService.getBaseMapper().insert(riskDetail1);
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanLaBorRiskinsert")
	@ApiLog("新增-驾驶员劳保用品风险统计信息")
	@ApiOperation(value = "新增-驾驶员劳保用品风险统计信息", position = 1)
	public R jiashiyuanLaBorRiskinsert(BladeUser user) throws ParseException{
		R r = new R();
		List<LaborlingquEntity> laborlingquEntities = riskDetailService.selectLaBorRisk();
		for (LaborlingquEntity laborlingqu:laborlingquEntities) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"劳保用品未领取");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,laborlingqu.getAlrPersonIds());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,laborlingqu.getAliName());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(laborlingqu.getDeptId());
				riskDetail1.setArdMajorCategories("2");
				riskDetail1.setArdSubCategory("203");
				riskDetail1.setArdTitle("劳保用品未领取");
				riskDetail1.setArdType("用品未领取");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(laborlingqu.getAlrPersonIds());
				riskDetail1.setArdIsRectification("0");
				riskDetail1.setArdContent(laborlingqu.getAliName());
				riskDetailService.getBaseMapper().insert(riskDetail1);
			}
		}
		return r;
	}


	@PostMapping("/jiashiyuanAnQuanJianChaRiskinsert")
	@ApiLog("新增-驾驶员安全检查风险统计信息")
	@ApiOperation(value = "新增-驾驶员安全检查风险统计信息", position = 1)
	public R jiashiyuanAnQuanJianChaRiskinsert(BladeUser user) throws ParseException{
		R r = new R();
		String today = DateUtil.now().substring(0, 10);
		List<AnbiaoCheliangJiashiyuanDaily> anQuanJianChaRisks = riskDetailService.selectAnQuanJianChaRisk();
		for (AnbiaoCheliangJiashiyuanDaily anQuanJianChaRisk:anQuanJianChaRisks) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"未按时进行安全检查");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,anQuanJianChaRisk.getJiashiyuanid());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDiscoveryDate,today);
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,anQuanJianChaRisk.getCheliangpaizhao());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				if (StringUtils.isNotBlank(anQuanJianChaRisk.getCreatetime()) && !anQuanJianChaRisk.getCreatetime().equals("null")){
					String createtime = anQuanJianChaRisk.getCreatetime().substring(0, 10);
					if (!createtime.equals(today)){
						AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
						riskDetail1.setArdDeptIds(anQuanJianChaRisk.getDeptId());
						riskDetail1.setArdMajorCategories("2");
						riskDetail1.setArdSubCategory("201");
						riskDetail1.setArdTitle("未按时进行安全检查");
						riskDetail1.setArdType("未按时检查");
						riskDetail1.setArdDiscoveryDate(today);
						riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
						riskDetail1.setArdAssociationField("id");
						riskDetail1.setArdAssociationValue(anQuanJianChaRisk.getJiashiyuanid());
						riskDetail1.setArdIsRectification("0");
						riskDetail1.setArdContent(anQuanJianChaRisk.getCheliangpaizhao());
						riskDetailService.getBaseMapper().insert(riskDetail1);}
					}else if (StringUtils.isBlank(anQuanJianChaRisk.getCreatetime()) || anQuanJianChaRisk.getCreatetime().equals("null")){
								AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
								riskDetail1.setArdDeptIds(anQuanJianChaRisk.getDeptId());
								riskDetail1.setArdMajorCategories("2");
								riskDetail1.setArdSubCategory("201");
								riskDetail1.setArdTitle("未按时进行安全检查");
								riskDetail1.setArdType("未按时检查");
								riskDetail1.setArdDiscoveryDate(today);
								riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
								riskDetail1.setArdAssociationField("id");
								riskDetail1.setArdAssociationValue(anQuanJianChaRisk.getJiashiyuanid());
								riskDetail1.setArdIsRectification("0");
								riskDetail1.setArdContent(anQuanJianChaRisk.getCheliangpaizhao());
								riskDetailService.getBaseMapper().insert(riskDetail1);
					}
				}
			}
			return r;
		}


	@PostMapping("/vehicleXingShiZhengRiskinsert")
	@ApiLog("新增-行驶证风险统计信息")
	@ApiOperation(value = "新增-车头行驶证风险统计信息", position = 1)
	public R vehicleXingShiZhengRiskinsert(String vehicleId,BladeUser user) throws ParseException{
		R r = new R();
		List<VehicleXingshizheng> vehicleXingshizhengs = riskDetailService.selectXingShiZhengRisk(vehicleId);
		for (VehicleXingshizheng vehicleXingshizheng:vehicleXingshizhengs) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"车辆行驶证信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,vehicleXingshizheng.getVehicleId());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(vehicleXingshizheng.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("001");
				riskDetail1.setArdTitle("车辆行驶证信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_vehicle");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(vehicleXingshizheng.getVehicleId());
				riskDetail1.setArdIsRectification("0");
				riskDetail1.setVehicleId(vehicleXingshizheng.getVehicleId());
				riskDetail1.setCheliangpaizhao(vehicleXingshizheng.getCheliangpaizhao());
				if (!vehicleXingshizheng.getAvxPlateNo().equals("0")){
					A=A+"车辆牌照、";
					riskDetail1.setArdContent(A);
					a++;
				}else {
					A=vehicleXingshizheng.getCheliangpaizhao();
				}
				if (!vehicleXingshizheng.getAvxOwner().equals("0")){
					A=A+"所有人、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxUseCharter().equals("0")){
					A=A+"使用性质、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxModel().equals("0")){
					A=A+"品牌型号、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxVin().equals("0")){
					A=A+"车辆识别代码、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxEngineNo().equals("0")){
					A=A+"发动机号码、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxRegisterDate2().equals("0")){
					A=A+"注册日期、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (vehicleXingshizheng.getAvxAuthorizedSeatingCapacity()!=0){
					A=A+"核定载人数、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (vehicleXingshizheng.getAvxTotalMass()!=0){
					A=A+"总质量、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (vehicleXingshizheng.getAvxCurbWeight()!=0){
					A=A+"整备质量、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxOverallDimensions().equals("0")){
					A=A+"外廓尺寸、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (vehicleXingshizheng.getAvxQuasiTractiveMass()!=0){
					A=A+"准牵引质量、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxFileNo().equals("0")){
					A=A+"行驶证号、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxValidUntil2().equals("0")){
					A=A+"检验有效期、有效日期结束日期";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxIssueDate2().equals("0")){
					A=A+"发证日期、有效日期开始日期、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxOriginalEnclosure().equals("0")){
					A=A+"行驶证正面照片、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxCopyEnclosure().equals("0")){
					A=A+"行驶证反面照片、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				if (!vehicleXingshizheng.getAvxPlateNo().equals("0")){
					A=A+"车辆牌照、";
					riskDetail.setArdContent(A);
					a++;
				}else {
					A=vehicleXingshizheng.getCheliangpaizhao();
				}
				if (!vehicleXingshizheng.getAvxOwner().equals("0")){
					A=A+"所有人、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxUseCharter().equals("0")){
					A=A+"使用性质、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxModel().equals("0")){
					A=A+"品牌型号、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxVin().equals("0")){
					A=A+"车辆识别代码、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxEngineNo().equals("0")){
					A=A+"发动机号码、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxRegisterDate2().equals("0")){
					A=A+"注册日期、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (vehicleXingshizheng.getAvxAuthorizedSeatingCapacity()!=0){
					A=A+"核定载人数、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (vehicleXingshizheng.getAvxTotalMass()!=0){
					A=A+"总质量、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (vehicleXingshizheng.getAvxCurbWeight()!=0){
					A=A+"整备质量、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxOverallDimensions().equals("0")){
					A=A+"外廓尺寸、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (vehicleXingshizheng.getAvxQuasiTractiveMass()!=0){
					A=A+"准牵引质量、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxFileNo().equals("0")){
					A=A+"行驶证号、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxValidUntil2().equals("0")){
					A=A+"检验有效期、有效日期结束日期";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxIssueDate2().equals("0")){
					A=A+"发证日期、有效日期开始日期、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxOriginalEnclosure().equals("0")){
					A=A+"行驶证正面照片、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleXingshizheng.getAvxCopyEnclosure().equals("0")){
					A=A+"行驶证反面照片、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}


	@PostMapping("/vehicleDaoLuYunShuZhengRiskinsert")
	@ApiLog("新增-道路运输证风险统计信息")
	@ApiOperation(value = "新增-道路运输证风险统计信息", position = 1)
	public R vehicleDaoLuYunShuZhengRiskinsert(String vehicleId,BladeUser user) throws ParseException{
		R r = new R();
		List<VehicleDaoluyunshuzheng> vehicleDaoluyunshuzhengs = riskDetailService.selectDaoLuYunShuZhengRisk(vehicleId);
		for (VehicleDaoluyunshuzheng vehicleDaoluyunshuzheng:vehicleDaoluyunshuzhengs) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"道路运输证信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,vehicleDaoluyunshuzheng.getVehicleId());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(vehicleDaoluyunshuzheng.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("001");
				riskDetail1.setArdTitle("道路运输证信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_vehicle");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(vehicleDaoluyunshuzheng.getVehicleId());
				riskDetail1.setArdIsRectification("0");
				riskDetail1.setVehicleId(vehicleDaoluyunshuzheng.getVehicleId());
				riskDetail1.setCheliangpaizhao(vehicleDaoluyunshuzheng.getCheliangpaizhao());
				A=vehicleDaoluyunshuzheng.getCheliangpaizhao();
				if (!vehicleDaoluyunshuzheng.getAvdRoadTransportCertificateNo().equals("0")){
					A=A+"道路运输证号、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleDaoluyunshuzheng.getAvdIssueDate2().equals("0")){
					A=A+"有效日期开始日期、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleDaoluyunshuzheng.getAvdValidUntil2().equals("0")){
					A=A+"有效日期结束日期、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if (!vehicleDaoluyunshuzheng.getAvdEnclosure().equals("0")){
					A=A+"证件照片、";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				A=vehicleDaoluyunshuzheng.getCheliangpaizhao();
				if (!vehicleDaoluyunshuzheng.getAvdRoadTransportCertificateNo().equals("0")){
					A=A+"道路运输证号、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleDaoluyunshuzheng.getAvdIssueDate2().equals("0")){
					A=A+"有效日期开始日期、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleDaoluyunshuzheng.getAvdValidUntil2().equals("0")){
					A=A+"有效日期结束日期、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (!vehicleDaoluyunshuzheng.getAvdEnclosure().equals("0")){
					A=A+"证件照片、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}


	@PostMapping("/vehicleXingNengBaoGaoRiskinsert")
	@ApiLog("新增-性能报告风险统计信息")
	@ApiOperation(value = "新增-性能报告风险统计信息", position = 1)
	public R vehicleXingNengBaoGaoRiskinsert(String vehicleId,BladeUser user) throws ParseException{
		R r = new R();
		List<VehicleXingnengbaogao> xingnengbaogaos = riskDetailService.selectXingNengBaoGaoRisk(vehicleId);
		for (VehicleXingnengbaogao xingnengbaogao:xingnengbaogaos) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"车辆性能检测报告信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,xingnengbaogao.getVehicleId());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(xingnengbaogao.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("001");
				riskDetail1.setArdTitle("车辆性能检测报告信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_vehicle");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(xingnengbaogao.getVehicleId());
				riskDetail1.setArdIsRectification("0");
				riskDetail1.setVehicleId(xingnengbaogao.getVehicleId());
				riskDetail1.setCheliangpaizhao(xingnengbaogao.getCheliangpaizhao());
				A=xingnengbaogao.getCheliangpaizhao();
				if (!xingnengbaogao.getAvxEnclosure().equals("0")){
					A=A+"性能报告附件";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				A=xingnengbaogao.getCheliangpaizhao();
				if (!xingnengbaogao.getAvxEnclosure().equals("0")){
					A=A+"性能报告附件";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}


	@PostMapping("/vehicleDengJiZhengShuRiskinsert")
	@ApiLog("新增-登记证书风险统计信息")
	@ApiOperation(value = "新增-登记证书风险统计信息", position = 1)
	public R vehicleDengJiZhengShuRiskinsert(String vehicleId,BladeUser user) throws ParseException{
		R r = new R();
		List<VehicleDengjizhengshu> dengjizhengshus = riskDetailService.selectDengJiZhengShuRisk(vehicleId);
		for (VehicleDengjizhengshu dengjizhengshu:dengjizhengshus) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"登记证书信息未完善");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,dengjizhengshu.getVehicleId());
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				int a=0;
				String A="";
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(dengjizhengshu.getDeptId());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("001");
				riskDetail1.setArdTitle("登记证书信息未完善");
				riskDetail1.setArdType("信息未完善");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_vehicle");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(dengjizhengshu.getVehicleId());
				riskDetail1.setArdIsRectification("0");
				riskDetail1.setVehicleId(dengjizhengshu.getVehicleId());
				riskDetail1.setCheliangpaizhao(dengjizhengshu.getCheliangpaizhao());
				A=dengjizhengshu.getCheliangpaizhao();
				if (!dengjizhengshu.getAvdEnclosure().equals("0")){
					A=A+"登记证书附件";
					riskDetail1.setArdContent(A);
					a++;
				}
				if(a>0){
					A=A+"未完善";
					riskDetail1.setArdContent(A);
					riskDetailService.getBaseMapper().insert(riskDetail1);
				}
			}else {
				int a=0;
				String A="";
				riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
				A=dengjizhengshu.getCheliangpaizhao();
				if (!dengjizhengshu.getAvdEnclosure().equals("0")){
					A=A+"登记证书附件";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetail.setArdRectificationDate(DateUtil.now().substring(0,10));
					riskDetailService.updateById(riskDetail);

				}
			}
		}
		return r;
	}


	@PostMapping("/JiaShiYuanBaoXianRiskinsert")
	@ApiLog("新增-驾驶员保险风险统计信息")
	@ApiOperation(value = "新增-驾驶员保险风险统计信息", position = 1)
	public R JiaShiYuanBaoXianRiskinsert(String jiashiyuanId,BladeUser user) throws ParseException{
		R r = new R();
		List<JiaShiYuan> jiaShiYuanBaoXianRisks = riskDetailService.selectJiaShiYuanBaoXianRisk(jiashiyuanId);
		for (JiaShiYuan jiaShiYuanBaoXianRisk:jiaShiYuanBaoXianRisks) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"驾驶员保险");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,jiaShiYuanBaoXianRisk.getId());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,"无保险");
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(jiaShiYuanBaoXianRisk.getDeptId().toString());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("004");
				riskDetail1.setArdTitle("驾驶员保险");
				riskDetail1.setArdType("未购买");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(jiaShiYuanBaoXianRisk.getId());
				riskDetail1.setArdIsRectification("0");
				riskDetail1.setArdContent("无保险");
				riskDetailService.getBaseMapper().insert(riskDetail1);
			}
		}
		return r;
	}


	@PostMapping("/VehicleBaoXianRiskinsert")
	@ApiLog("新增-车辆保险风险统计信息")
	@ApiOperation(value = "新增-车辆保险风险统计信息", position = 1)
	public R VehicleBaoXianRiskinsert(String vehicleId,BladeUser user) throws ParseException{
		R r = new R();
		List<Vehicle> vehicleBaoXianRisks = riskDetailService.selectVehicleBaoXianRisk(vehicleId);
		for (Vehicle vehicleBaoXianRisk:vehicleBaoXianRisks) {
			QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdTitle,"车辆保险");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,vehicleBaoXianRisk.getId());
			riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,"无保险");
			AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
			if (riskDetail==null){
				AnbiaoRiskDetail riskDetail1 = new AnbiaoRiskDetail();
				riskDetail1.setArdDeptIds(vehicleBaoXianRisk.getDeptId().toString());
				riskDetail1.setArdMajorCategories("0");
				riskDetail1.setArdSubCategory("004");
				riskDetail1.setArdTitle("车辆保险");
				riskDetail1.setArdType("未购买");
				riskDetail1.setArdDiscoveryDate(DateUtil.now().substring(0,10));
				riskDetail1.setArdAssociationTable("anbiao_vehicle");
				riskDetail1.setArdAssociationField("id");
				riskDetail1.setArdAssociationValue(vehicleBaoXianRisk.getId());
				riskDetail1.setArdIsRectification("0");
				riskDetail1.setArdContent("无保险");
				riskDetail1.setVehicleId(vehicleBaoXianRisk.getId());
				riskDetail1.setCheliangpaizhao(vehicleBaoXianRisk.getCheliangpaizhao());
				riskDetailService.getBaseMapper().insert(riskDetail1);
			}
		}
		return r;
	}


	}



















