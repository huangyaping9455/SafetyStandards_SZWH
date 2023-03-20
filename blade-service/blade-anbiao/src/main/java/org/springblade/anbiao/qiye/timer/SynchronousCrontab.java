package org.springblade.anbiao.qiye.timer;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDaoluyunshuzheng;
import org.springblade.anbiao.cheliangguanli.entity.VehicleXingshizheng;
import org.springblade.anbiao.cheliangguanli.service.IVehicleDaoluyunshuzhengService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleXingshizhengService;
import org.springblade.anbiao.cheliangguanli.vo.BaoYangWeiXiuVO;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;
import org.springblade.anbiao.jiashiyuan.entity.*;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.labor.entity.LaborlingquEntity;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
import org.springblade.anbiao.yinhuanpaicha.vo.AnbiaoHiddenDangerVO;
import org.springblade.core.tool.api.R;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author hyp
 * @description: TODO
 * @projectName SafetyStandards
 */
@Component
@Slf4j
@AllArgsConstructor
public class SynchronousCrontab {

	private static final Object KEY = new Object();
	private static boolean taskFlag = false;

	private IAnbiaoRiskDetailService riskDetailService;
	private IJiaShiYuanService jiaShiYuanService;
	private IOrganizationsService organizationsService;


	//获取驾驶员风险信息
	private void addQYDriverList() throws IOException, ParseException {
		QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<Organizations>();
		organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete,0);
		organizationsQueryWrapper.lambda().eq(Organizations::getJigouleixing,"qiye");
		List<Organizations> organizationsList = organizationsService.getBaseMapper().selectList(organizationsQueryWrapper);
		for (Organizations organizations:organizationsList) {
			Integer deptId = Integer.parseInt(organizations.getDeptId());
			QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper1 = new QueryWrapper<>();
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getDeptId,deptId);
			jiaShiYuanQueryWrapper1.lambda().eq(JiaShiYuan::getIsdelete,0);
			List<JiaShiYuan> jiaShiYuans = jiaShiYuanService.getBaseMapper().selectList(jiaShiYuanQueryWrapper1);
			for (JiaShiYuan jiaShiYuan: jiaShiYuans) {
				String jiaShiYuanId = jiaShiYuan.getId();
				int aa=0;
				int timeDifference=0;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				QueryWrapper<JiaShiYuan> jiaShiYuanQueryWrapper = new QueryWrapper<>();
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getId,jiaShiYuanId);
				jiaShiYuanQueryWrapper.lambda().eq(JiaShiYuan::getIsdelete,0);
				JiaShiYuan deail = jiaShiYuanService.getBaseMapper().selectOne(jiaShiYuanQueryWrapper);

				//验证身份证日期
				AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
				riskDetail.setArdDeptIds(deail.getDeptId().toString());
				riskDetail.setArdMajorCategories("0");
				riskDetail.setArdSubCategory("003");
				riskDetail.setArdTitle("身份证有效截止日期");
				riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail.setArdIsRectification("0");
				riskDetail.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail.setArdAssociationField("id");
				riskDetail.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getShenfenzhengyouxiaoqi()) || deail.getShenfenzhengyouxiaoqi().equals("null")){
//					riskDetail.setArdContent("身份证有效期缺项");
//					riskDetail.setArdType("缺项");
//					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail.getArdAssociationValue());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail.getArdContent());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
//					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
//					if (riskDetail1==null) {
//						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
//						if (insert > 0) {
//							aa++;
//						}
//					}
				}else if (!deail.getShenfenzhengyouxiaoqi().equals("长期")){
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
					}else {
						riskDetail.setArdType("正常");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
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
				riskDetail2.setArdTitle("驾驶证有效截止日期");
				riskDetail2.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail2.setArdIsRectification("0");
				riskDetail2.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail2.setArdAssociationField("id");
				riskDetail2.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getJiashizhengyouxiaoqi()) || deail.getJiashizhengyouxiaoqi().equals("null")){
//					riskDetail2.setArdContent("驾驶证有效截止日期缺项");
//					riskDetail2.setArdType("缺项");
//					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail2.getArdDeptIds());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail2.getArdAssociationValue());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail2.getArdContent());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
//					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
//					if(riskDetail1==null){
//						int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
//						if (insert2 > 0) {
//							aa++;
//						}
//					}
				}else if (!deail.getJiashizhengyouxiaoqi().equals("长期")){
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
						riskDetail2.setArdContent("驾驶证有效截止日期预警");
						riskDetail2.setArdType("预警");
					} else if (timeDifference < 0) {
						riskDetail2.setArdContent("驾驶证有效截止日期逾期");
						riskDetail2.setArdType("逾期");
					}else {
						riskDetail2.setArdType("正常");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail2.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail2.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail2.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
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
				riskDetail3.setArdTitle("从业资格证有效截止日期");
				riskDetail3.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail3.setArdIsRectification("0");
				riskDetail3.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail3.setArdAssociationField("id");
				riskDetail3.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getCongyezhengyouxiaoqi()) || deail.getCongyezhengyouxiaoqi().equals("null")){
//					riskDetail3.setArdContent("从业资格证有效期缺项");
//					riskDetail3.setArdType("缺项");
//					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail3.getArdDeptIds());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail3.getArdAssociationValue());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail3.getArdContent());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
//					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
//					if (riskDetail1==null){
//						int insert3 = riskDetailService.getBaseMapper().insert(riskDetail3);
//						if (insert3 > 0) {
//							aa++;
//						}
//					}
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
					}else {
						riskDetail3.setArdType("正常");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail3.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail3.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail3.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
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
				riskDetail4.setArdTitle("体检有效截止日期");
				riskDetail4.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail4.setArdIsRectification("0");
				riskDetail4.setArdAssociationTable("anbiao_jiashiyuan");
				riskDetail4.setArdAssociationField("id");
				riskDetail4.setArdAssociationValue(deail.getId());
				if (StringUtils.isBlank(deail.getTijianyouxiaoqi()) || deail.getTijianyouxiaoqi().equals("null") ){
//					riskDetail4.setArdContent("体检日期缺项");
//					riskDetail4.setArdType("缺项");
//					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail4.getArdDeptIds());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail4.getArdAssociationValue());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail4.getArdContent());
//					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
//					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
//					if (riskDetail1==null){
//						int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
//						if (insert4 > 0) {
//							aa++;
//						}
//					}
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
					}else {
						riskDetail4.setArdType("正常");
					}
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail4.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail4.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail4.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
						if (riskDetail4.getArdType().equals("预警") || riskDetail4.getArdType().equals("逾期")) {
							int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
							if (insert4 > 0) {
								aa++;
							}
						}
					}
				}
			}
		}
	}

	//获取企业风险信息
	private void addQYList() throws IOException, ParseException{
		QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<Organizations>();
		organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete,0);
		organizationsQueryWrapper.lambda().eq(Organizations::getJigouleixing,"qiye");
		List<Organizations> organizationsList = organizationsService.getBaseMapper().selectList(organizationsQueryWrapper);
		int aa=0;
		int timeDifference=0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (Organizations organization:organizationsList){

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
			if (StringUtils.isBlank(organization.getJigoubianma()) || organization.getJigoubianma().equals("null")){
				riskDetail.setArdContent("统一社会信用代码缺项");
				riskDetail.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null) {
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
			if (StringUtils.isBlank(organization.getYyzzjzdate()) || organization.getYyzzjzdate().equals("null")){
				riskDetail2.setArdContent("统一社会信用代码有效截止日期缺项");
				riskDetail2.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail2.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail2.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail2.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if(riskDetail1==null){
					int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
					if (insert2 > 0) {
						aa++;
					}
				}
			}else if (!organization.getYyzzjzdate().equals("长期")){
				Date yyzzjzdate= formatter.parse(organization.getYyzzjzdate());
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
					if (year4<year3){
						timeDifference = -1;
					}else {
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
				}else {
					riskDetail2.setArdType("正常");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail2.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail2.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail2.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
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
			if (StringUtils.isBlank(organization.getDaoluxukezhenghao()) || organization.getDaoluxukezhenghao().equals("null")){
				riskDetail3.setArdContent("道路运输许可证号缺项");
				riskDetail3.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail3.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail3.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail3.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null) {
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
			if (StringUtils.isBlank(organization.getDaoluyunshuzhengjieshuriqi()) || organization.getDaoluyunshuzhengjieshuriqi().equals("null")){
				riskDetail4.setArdContent("道路运输许可证有效截止日期缺项");
				riskDetail4.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail4.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail4.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail4.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					int insert4 = riskDetailService.getBaseMapper().insert(riskDetail4);
					if (insert4 > 0) {
						aa++;
					}
				}
			}else {
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
					if (year8<year7){
						timeDifference = -1;
					}else {
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
				}else {
					riskDetail4.setArdType("正常");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail4.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail4.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail4.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
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
			if (StringUtils.isBlank(organization.getJingyingxukezhengbianma()) || organization.getJingyingxukezhengbianma().equals("null")){
				riskDetail5.setArdContent("经营许可证号缺项");
				riskDetail5.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail5.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail5.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail5.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null) {
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
			if (StringUtils.isBlank(organization.getJingyingxukezhengyouxiaoqi()) || organization.getJingyingxukezhengyouxiaoqi().equals("null")){
				riskDetail6.setArdContent("经营许可证有效截止日期缺项");
				riskDetail6.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail6.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail6.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail6.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					int insert6 = riskDetailService.getBaseMapper().insert(riskDetail6);
					if (insert6 > 0) {
						aa++;
					}
				}
			}else {
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
					if (year8<year7){
						timeDifference = -1;
					}else {
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
				}else {
					riskDetail6.setArdType("正常");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail6.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail6.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail6.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
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
			if (StringUtils.isBlank(organization.getLaodonghetongjieshuriqi()) || organization.getLaodonghetongjieshuriqi().equals("null")){
				riskDetail7.setArdContent("合同有效截止日期缺项");
				riskDetail7.setArdType("缺项");
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail7.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail7.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail7.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					int insert7 = riskDetailService.getBaseMapper().insert(riskDetail7);
					if (insert7 > 0) {
						aa++;
					}
				}
			}else {
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
					if (year8<year7){
						timeDifference = -1;
					}else {
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
				}else {
					riskDetail7.setArdType("正常");
				}
				QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail7.getArdDeptIds());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail7.getArdAssociationValue());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail7.getArdContent());
				riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
				AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
				if (riskDetail1==null){
					if (riskDetail7.getArdType().equals("预警") || riskDetail7.getArdType().equals("逾期")) {
						int insert7 = riskDetailService.getBaseMapper().insert(riskDetail7);
						if (insert7 > 0) {
							aa++;
						}
					}
				}
			}
		}
	}

	private IVehicleService vehicleService;
	private IVehicleXingshizhengService xingshizhengService;
	private IVehicleDaoluyunshuzhengService daoluyunshuzhengService;
	//获取车辆风险信息
	private void addQyVehicleList() throws IOException, ParseException {
		QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
		vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel,0);
		List<Vehicle> vehicleList = vehicleService.getBaseMapper().selectList(vehicleQueryWrapper);
		for (Vehicle vehicle:vehicleList) {
			//车辆行驶证
			QueryWrapper<VehicleXingshizheng> xingshizhengQueryWrapper = new QueryWrapper<>();
			xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxAvIds,vehicle.getId());
			xingshizhengQueryWrapper.lambda().eq(VehicleXingshizheng::getAvxDelete,0);
			VehicleXingshizheng xingshizheng = xingshizhengService.getBaseMapper().selectOne(xingshizhengQueryWrapper);
			if(xingshizheng != null) {
				//行驶证号
				AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
				riskDetail.setArdDeptIds(vehicle.getDeptId().toString());
				riskDetail.setArdMajorCategories("0");
				riskDetail.setArdSubCategory("001");
				riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail.setArdIsRectification("0");
				riskDetail.setArdAssociationTable("anbiao_vehicle");
				riskDetail.setArdAssociationField("id");
				riskDetail.setArdAssociationValue(xingshizheng.getAvxAvIds());
				riskDetail.setVehicleId(xingshizheng.getAvxAvIds());
				riskDetail.setCheliangpaizhao(xingshizheng.getAvxPlateNo());
				if(StringUtils.isBlank(xingshizheng.getAvxFileNo())) {
//					riskDetail.setArdTitle("行驶证号");
//					riskDetail.setArdContent("行驶证号缺项");
//					riskDetail.setArdType("缺项");
//					riskDetail.setArdRectificationField("avx_file_no");		//整改字段
//					riskDetail.setArdRectificationFieldType("string");
//					queryRiskOrInsert(riskDetail);
				}

				//注册日期
				if(xingshizheng.getAvxRegisterDate() != null) {
				} else {
//					riskDetail.setArdTitle("注册日期");
//					riskDetail.setArdContent("注册日期缺项");
//					riskDetail.setArdType("缺项");
//					riskDetail.setArdRectificationField("avx_register_date");		//整改字段
//					riskDetail.setArdRectificationFieldType("date");
//					queryRiskOrInsert(riskDetail);
				}

				//有效期
				if(xingshizheng.getAvxValidUntil() != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);
					int timeDifference = differentDays(DateUtil.now(),formatter.format(xingshizheng.getAvxValidUntil()));
					riskDetail.setArdTitle("行驶证有效期");
					riskDetail.setArdRectificationField("avx_valid_until");		//整改字段
					riskDetail.setArdRectificationFieldType("date");
					if (timeDifference <= 30 && timeDifference > 0) {
						riskDetail.setArdContent("行驶证有效期预警");
						riskDetail.setArdType("预警");
						queryRiskOrInsert(riskDetail);
					} else if (timeDifference < 0) {
						riskDetail.setArdContent("行驶证有效期逾期");
						riskDetail.setArdType("逾期");
						queryRiskOrInsert(riskDetail);
					}
				} else {
					riskDetail.setArdTitle("行驶证有效期");
//					riskDetail.setArdContent("行驶证有效期缺项");
//					riskDetail.setArdType("缺项");
//					riskDetail.setArdRectificationField("avx_valid_until");		//整改字段
//					riskDetail.setArdRectificationFieldType("date");
//					queryRiskOrInsert(riskDetail);
				}
			}

			//道路运输证
			QueryWrapper<VehicleDaoluyunshuzheng> daoluyunshuzhengQueryWrapper = new QueryWrapper<>();
			daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdAvIds,vehicle.getId());
			daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdDelete,0);
			VehicleDaoluyunshuzheng daoluyunshuzheng = daoluyunshuzhengService.getBaseMapper().selectOne(daoluyunshuzhengQueryWrapper);
			if(daoluyunshuzheng != null) {
				AnbiaoRiskDetail riskDetail = new AnbiaoRiskDetail();
				riskDetail.setArdDeptIds(vehicle.getDeptId().toString());
				riskDetail.setArdMajorCategories("0");
				riskDetail.setArdSubCategory("001");
				riskDetail.setArdDiscoveryDate(DateUtil.now().substring(0, 10));
				riskDetail.setArdIsRectification("0");
				riskDetail.setArdAssociationTable("anbiao_vehicle");
				riskDetail.setArdAssociationField("id");
				riskDetail.setArdAssociationValue(daoluyunshuzheng.getAvdAvIds());
				riskDetail.setVehicleId(daoluyunshuzheng.getAvdAvIds());
				riskDetail.setCheliangpaizhao(daoluyunshuzheng.getAvdPlateNo());
				if(StringUtils.isBlank(daoluyunshuzheng.getAvdRoadTransportCertificateNo())) {
//					riskDetail.setArdTitle("道路运输证号");
//					riskDetail.setArdContent("道路运输证号缺项");
//					riskDetail.setArdType("缺项");
//					riskDetail.setArdRectificationField("avd_road_transport_certificate_no");		//整改字段
//					riskDetail.setArdRectificationFieldType("string");
//					queryRiskOrInsert(riskDetail);
				}

				//注册日期
				if(daoluyunshuzheng.getAvdIssueDate() != null) {
				} else {
//					riskDetail.setArdTitle("发证日期");
//					riskDetail.setArdContent("发证日期缺项");
//					riskDetail.setArdType("缺项");
//					riskDetail.setArdRectificationField("avd_issue_date");		//整改字段
//					riskDetail.setArdRectificationFieldType("date");
//					queryRiskOrInsert(riskDetail);
				}

				//有效期
				if(daoluyunshuzheng.getAvdValidUntil() != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);
					int timeDifference = differentDays(DateUtil.now(),formatter.format(daoluyunshuzheng.getAvdValidUntil()));
					riskDetail.setArdTitle("道路运输证有效期");
					riskDetail.setArdRectificationField("avd_valid_until");		//整改字段
					riskDetail.setArdRectificationFieldType("date");
					if (timeDifference <= 30 && timeDifference > 0) {
						riskDetail.setArdContent("道路运输证有效期预警");
						riskDetail.setArdType("预警");
						queryRiskOrInsert(riskDetail);
					} else if (timeDifference < 0) {
						riskDetail.setArdContent("道路运输证有效期逾期");
						riskDetail.setArdType("逾期");
						queryRiskOrInsert(riskDetail);
					}
				} else {
//					riskDetail.setArdTitle("道路运输证有效期");
//					riskDetail.setArdContent("道路运输证有效期缺项");
//					riskDetail.setArdType("缺项");
//					riskDetail.setArdRectificationField("avd_valid_until");		//整改字段
//					riskDetail.setArdRectificationFieldType("date");
//					queryRiskOrInsert(riskDetail);
				}
			}
		}
	}

	//计算日期差
	private int differentDays(String d1,String d2) {
		int timeDifference = 0;
		Date date2 = DateUtil.parse(d2);
		Date date1 = DateUtil.parse(d1);

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
		int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
		int year1 = calendar1.get(Calendar.YEAR);
		int year2 = calendar2.get(Calendar.YEAR);
		if (year1 != year2) {//不同年
			int timeDistance = 0;
			if (year2<year1){
				timeDifference = -1;
			}else {
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
		return timeDifference;
	}

	private void queryRiskOrInsert(AnbiaoRiskDetail detail) {
		QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,detail.getArdDeptIds());
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationTable,detail.getArdAssociationTable());
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationField,detail.getArdAssociationField());
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,detail.getArdAssociationValue());
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,detail.getArdContent());
		riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,detail.getArdIsRectification());
		AnbiaoRiskDetail riskDetail = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
		if(riskDetail == null) {
			riskDetailService.getBaseMapper().insert(detail);
		}
	}

	//入职表风险
	private void RuZhiRiskinsert() throws IOException, ParseException {
		List<AnbiaoJiashiyuanRuzhi> anbiaoJiashiyuanRuzhis = riskDetailService.selectRuZhiRisk();
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
					riskDetailService.updateById(riskDetail);
				}
			}
		}
	}

	//身份证风险
	private void ShenFenZhengRiskinsert() throws IOException, ParseException {
		List<JiaShiYuan> jiaShiYuans = riskDetailService.selectShenFenZhengRisk();
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
					riskDetailService.updateById(riskDetail);
				}
			}
		}
	}

	//驾驶证风险
	private void JiaShiZhengRiskinsert() throws IOException, ParseException {
		List<AnbiaoJiashiyuanJiashizheng> jiashiyuanJiashizhengs = riskDetailService.selectJiaShiZhengRisk();
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
					A=A+"驾驶证正面照片、";
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
					A=A+"驾驶证正面照片、";
					riskDetail.setArdContent(A);
					a++;
				}
				if (a>0){
					A=A+"未完善";
					riskDetail.setArdContent(A);
					riskDetailService.updateById(riskDetail);
				}else if (a==0){
					riskDetail.setArdIsRectification("1");
					riskDetailService.updateById(riskDetail);

				}
			}
		}
	}

	//从业资格证风险
	private void CongYeZhengRiskinsert() throws IOException, ParseException {
		List<AnbiaoJiashiyuanCongyezigezheng> jiashiyuanCongyezigezhengs = riskDetailService.selectCongYeZhengRisk();
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
					riskDetailService.updateById(riskDetail);

				}
			}
		}
	}

	//体检表风险
	private void TiJianRiskinsert() throws IOException, ParseException {
		List<AnbiaoJiashiyuanTijian> tijians = riskDetailService.selectTiJianRisk();
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
					riskDetailService.updateById(riskDetail);

				}
			}
		}
	}

	//岗前培训风险
	private void GangQianPeiXunRiskinsert() throws IOException, ParseException {
		List<AnbiaoJiashiyuanGangqianpeixun> gangqianpeixuns = riskDetailService.selectGangQianPeiXunRisk();
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
					riskDetailService.updateById(riskDetail);

				}
			}
		}
	}

	//无责证明风险
	private void WuZeZhengMingRiskinsert() throws IOException, ParseException {
		List<AnbiaoJiashiyuanWuzezhengming> wuzezhengmings = riskDetailService.selectWuZeZhengMingRisk();
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
					riskDetailService.updateById(riskDetail);

				}
			}
		}
	}

	//安全责任书风险
	private void AnQuanZeRenShuRiskinsert() throws IOException, ParseException {
		List<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshus = riskDetailService.selectAnQuanZeRenShuRisk();
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
					riskDetailService.updateById(riskDetail);

				}
			}
		}
	}

	//危害告知书风险
	private void WeiHaiGaoZhiShuRiskinsert() throws IOException, ParseException {
		List<AnbiaoJiashiyuanAnquanzerenshu> anquanzerenshus = riskDetailService.selectAnQuanZeRenShuRisk();
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
					riskDetailService.updateById(riskDetail);

				}
			}
		}
	}

	//劳动合同风险
	private void LaoDongHeTongRiskinsert() throws IOException, ParseException {
		List<AnbiaoJiashiyuanLaodonghetong> laodonghetongs = riskDetailService.selectLaoDongHeTongRisk();
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
					riskDetailService.updateById(riskDetail);

				}
			}
		}
	}

	//安全会议风险
	private void AnQuanHuiYiRiskinsert() throws IOException, ParseException {
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
	}

	//安全培训风险
	private void AnQuanPeiXunRiskinsert() throws IOException, ParseException {
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
	}

	//隐患排查风险
	private void YinHuanPaiChaRiskinsert() throws IOException, ParseException {
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
	}

	//维修登记风险
	private void WeiXiuDengJiRiskinsert() throws IOException, ParseException {
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
	}

	//劳保用品风险
	private void LaBorRiskinsert() throws IOException, ParseException {
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
				riskDetail1.setArdDeptIds(laborlingqu.getAsiDeptIds());
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
	}

	//安全检查风险
	private void AnQuanJianChaRiskinsert() throws IOException, ParseException {
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
	}

	//每6小时执行一次
	@Scheduled(cron = "0 0 */6 * * ?")
	//每天凌晨5点执行一次
//	@Scheduled(cron = "0 52 18 * * ?")
	public void configureTasks_static_data() {
		synchronized (KEY) {
			if (SynchronousCrontab.taskFlag) {
				System.out.println("定时任务-执行同步预警数据已经启动"+DateUtil.now());
				log.info("定时任务-执行同步预警数据已经启动", DateUtil.now());
				return;
			}
			SynchronousCrontab.taskFlag = true;
		}
		log.warn("定时任务-执行同步预警数据更新开始", DateUtil.now());
		try {
			System.out.println("执行同步预警数据");

			//获取驾驶员风险信息
			addQYDriverList();

			//获取车辆风险信息
			addQyVehicleList();

			//入职表风险
			RuZhiRiskinsert();

			//身份证风险
			ShenFenZhengRiskinsert();

			//驾驶证风险
			JiaShiZhengRiskinsert();

			//从业资格证风险
			CongYeZhengRiskinsert();

			//体检表风险
			TiJianRiskinsert();

			//岗前培训风险
			GangQianPeiXunRiskinsert();

			//无责证明风险
			WuZeZhengMingRiskinsert();

			//安全责任书风险
			AnQuanZeRenShuRiskinsert();

			//危害告知书风险
			WeiHaiGaoZhiShuRiskinsert();

			//劳动合同风险
			LaoDongHeTongRiskinsert();

			//安全会议风险
			AnQuanHuiYiRiskinsert();

			//安全培训风险
			AnQuanPeiXunRiskinsert();

			//隐患排查风险
			YinHuanPaiChaRiskinsert();

			//维修登记风险
			WeiXiuDengJiRiskinsert();

			//劳保用品风险
			LaBorRiskinsert();

			//安全检查风险
			AnQuanJianChaRiskinsert();

			System.out.println("执行完成");
		} catch (Exception e) {
			log.error("定时任务-执行同步预警数据-执行出错", e.getMessage());
		}
		SynchronousCrontab.taskFlag = false;
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}


}
