package org.springblade.anbiao.risk.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.protostuff.Request;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.alibaba.druid.sql.parser.Token.OF;

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

	private IAnbiaoRiskDetailService riskDetailService;
	private IJiaShiYuanService jiaShiYuanService;

	@PostMapping("/insert")
	@ApiLog("新增-风险统计信息")
	@ApiOperation(value = "新增-风险统计信息", notes = "传入jiaShiYuan", position = 1)
	public R insert(String id, BladeUser user) throws ParseException {
		R r = new R();
		int aa=0;
		int timeDifference=0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId,id);
		jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,0);
		JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);

		//验证身份证日期
		AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
		if (deail.getShenfenzhengyouxiaoqi()==null){
			riskDetail.setArdDeptIds(deail.getDeptId().toString());
			riskDetail.setArdMajorCategories("0");
			riskDetail.setArdSubCategory("003");
			riskDetail.setArdTitle("身份证有效截止日期");
			riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail.setArdIsRectification("0");
			riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
			riskDetail.setArdAssociationField("id");
			riskDetail.setArdAssociationValue(deail.getId());
			riskDetail.setArdContent("身份证有效期缺项");
			riskDetail.setArdType("缺项");
			int insert = riskDetailService.getBaseMapper().insert(riskDetail);
			if (insert > 0) {
				aa++;
			}
		}else {
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
				if (year2<year1){
					timeDifference = -1;
				}else {
					for (int i = year1; i < year2; i++) { //闰年
						if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
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
			riskDetail.setArdDeptIds(deail.getDeptId().toString());
			riskDetail.setArdMajorCategories("0");
			riskDetail.setArdSubCategory("003");
			riskDetail.setArdTitle("身份证有效截止日期");
			riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail.setArdIsRectification("0");
			riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
			riskDetail.setArdAssociationField("id");
			riskDetail.setArdAssociationValue(deail.getId());
			if (timeDifference <= 30 && timeDifference > 0) {
				riskDetail.setArdContent("身份证有效期预警");
				riskDetail.setArdType("预警");
			} else if (timeDifference < 0) {
				riskDetail.setArdContent("身份证有效期逾期");
				riskDetail.setArdType("逾期");
			}else {
				riskDetail.setArdType("正常");
			}
			if (riskDetail.getArdType().equals("预警") || riskDetail.getArdType().equals("逾期")) {
				int insert = riskDetailService.getBaseMapper().insert(riskDetail);
				if (insert > 0) {
					aa++;
				}
			}
		}

		//验证驾驶证日期
		AnbiaoRiskDetail riskDetail2 = new AnbiaoRiskDetail();
		if (deail.getJiashizhengyouxiaoqi()==null){
			riskDetail2.setArdDeptIds(deail.getDeptId().toString());
			riskDetail2.setArdMajorCategories("0");
			riskDetail2.setArdSubCategory("003");
			riskDetail2.setArdTitle("驾驶证有效截止日期");
			riskDetail2.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail2.setArdIsRectification("0");
			riskDetail2.setArdAssociationTable("anbiao_jiashiyuan");
			riskDetail2.setArdAssociationField("id");
			riskDetail2.setArdAssociationValue(deail.getId());
			riskDetail2.setArdContent("驾驶证有效截止日期缺项");
			riskDetail2.setArdType("缺项");
			int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
			if (insert2 > 0) {
				aa++;
			}
		}else {
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
				if (year4<year3){
					timeDifference = -1;
				}else {
					for (int i = year3; i < year4; i++) { //闰年
						if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
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
			riskDetail2.setArdDeptIds(deail.getDeptId().toString());
			riskDetail2.setArdMajorCategories("0");
			riskDetail2.setArdSubCategory("003");
			riskDetail2.setArdTitle("驾驶证有效截止日期");
			riskDetail2.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail2.setArdIsRectification("0");
			riskDetail2.setArdAssociationTable("anbiao_jiashiyuan");
			riskDetail2.setArdAssociationField("id");
			riskDetail2.setArdAssociationValue(deail.getId());
			if (timeDifference <= 30 && timeDifference > 0) {
				riskDetail2.setArdContent("驾驶证有效截止日期预警");
				riskDetail2.setArdType("预警");
			} else if (timeDifference < 0) {
				riskDetail2.setArdContent("驾驶证有效截止日期逾期");
				riskDetail2.setArdType("逾期");
			}else {
				riskDetail2.setArdType("正常");
			}
			if (riskDetail2.getArdType().equals("预警") || riskDetail2.getArdType().equals("逾期")) {
				int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
				if (insert2 > 0) {
					aa++;
				}
			}
		}


		//验证从业资格证日期
		AnbiaoRiskDetail riskDetail3 = new AnbiaoRiskDetail();
		if (deail.getCongyezhengyouxiaoqi()==null){
			riskDetail3.setArdDeptIds(deail.getDeptId().toString());
			riskDetail3.setArdMajorCategories("0");
			riskDetail3.setArdSubCategory("003");
			riskDetail3.setArdTitle("从业资格证有效截止日期");
			riskDetail3.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail3.setArdIsRectification("0");
			riskDetail3.setArdAssociationTable("anbiao_jiashiyuan");
			riskDetail3.setArdAssociationField("id");
			riskDetail3.setArdAssociationValue(deail.getId());
			riskDetail3.setArdContent("从业资格证有效期缺项");
			riskDetail3.setArdType("缺项");
			int insert3 = riskDetailService.getBaseMapper().insert(riskDetail3);
			if (insert3 > 0) {
				aa++;
			}
		}else {
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
				if (year6<year5){
					timeDifference = -1;
				}else {
					for (int i = year5; i < year6; i++) { //闰年
						if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
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
			riskDetail3.setArdDeptIds(deail.getDeptId().toString());
			riskDetail3.setArdMajorCategories("0");
			riskDetail3.setArdSubCategory("003");
			riskDetail3.setArdTitle("从业资格证有效截止日期");
			riskDetail3.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail3.setArdIsRectification("0");
			riskDetail3.setArdAssociationTable("anbiao_jiashiyuan");
			riskDetail3.setArdAssociationField("id");
			riskDetail3.setArdAssociationValue(deail.getId());
			if (timeDifference <= 30 && timeDifference > 0) {
				riskDetail3.setArdContent("从业资格证有效期预警");
				riskDetail3.setArdType("预警");
			} else if (timeDifference < 0) {
				riskDetail3.setArdContent("从业资格证有效期逾期");
				riskDetail3.setArdType("逾期");
			}else {
				riskDetail3.setArdType("正常");
			}
			if (riskDetail3.getArdType().equals("预警") || riskDetail3.getArdType().equals("逾期")) {
				int insert3 = riskDetailService.getBaseMapper().insert(riskDetail3);
				if (insert3 > 0) {
					aa++;
				}
			}
		}

		//验证体检日期
		AnbiaoRiskDetail riskDetail4 = new AnbiaoRiskDetail();
		if (deail.getTijianyouxiaoqi()==null){
			riskDetail4.setArdDeptIds(deail.getDeptId().toString());
			riskDetail4.setArdMajorCategories("0");
			riskDetail4.setArdSubCategory("003");
			riskDetail4.setArdTitle("体检有效截止日期");
			riskDetail4.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail4.setArdIsRectification("0");
			riskDetail4.setArdAssociationTable("anbiao_jiashiyuan");
			riskDetail4.setArdAssociationField("id");
			riskDetail4.setArdAssociationValue(deail.getId());
			riskDetail4.setArdContent("体检有效期缺项");
			riskDetail4.setArdType("缺项");
			int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
			if (insert4 > 0) {
				aa++;
			}
		}else {
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
				if (year8<year7){
					timeDifference = -1;
				}else {
					for (int i = year7; i < year8; i++) { //闰年
						if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
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
			riskDetail4.setArdMajorCategories("0");
			riskDetail4.setArdSubCategory("003");
			riskDetail4.setArdTitle("体检有效截止日期");
			riskDetail4.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
			riskDetail4.setArdIsRectification("0");
			riskDetail4.setArdAssociationTable("anbiao_jiashiyuan");
			riskDetail4.setArdAssociationField("id");
			riskDetail4.setArdAssociationValue(deail.getId());
			if (timeDifference <= 30 && timeDifference > 0) {
				riskDetail4.setArdContent("体检有效期预警");
				riskDetail4.setArdType("预警");
			} else if (timeDifference < 0) {
				riskDetail4.setArdContent("体检有效期逾期");
				riskDetail4.setArdType("逾期");
			}else {
				riskDetail4.setArdType("正常");
			}
			if (riskDetail4.getArdType().equals("预警") || riskDetail4.getArdType().equals("逾期")) {
				int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
				if (insert4 > 0) {
					aa++;
				}
			}
		}

		if (aa>0) {
			r.setMsg("风险新增成功");
			r.setCode(200);
			r.setSuccess(true);
		}else {
			r.setMsg("无风险新增");
			r.setCode(200);
			r.setSuccess(true);
		}
		return r;
	}


}
