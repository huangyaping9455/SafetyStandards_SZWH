package org.springblade.anbiao.qiye.timer;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDaoluyunshuzheng;
import org.springblade.anbiao.cheliangguanli.entity.VehicleXingshizheng;
import org.springblade.anbiao.cheliangguanli.service.IVehicleDaoluyunshuzhengService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleXingshizhengService;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.service.IOrganizationsService;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.risk.entity.AnbiaoRiskDetail;
import org.springblade.anbiao.risk.service.IAnbiaoRiskDetailService;
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
					riskDetail.setArdContent("身份证有效期缺项");
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
					riskDetail2.setArdContent("驾驶证有效截止日期缺项");
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
					riskDetail3.setArdContent("从业资格证有效期缺项");
					riskDetail3.setArdType("缺项");
					QueryWrapper<AnbiaoRiskDetail> riskDetailQueryWrapper = new QueryWrapper<>();
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdDeptIds,riskDetail3.getArdDeptIds());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdAssociationValue,riskDetail3.getArdAssociationValue());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdContent,riskDetail3.getArdContent());
					riskDetailQueryWrapper.lambda().eq(AnbiaoRiskDetail::getArdIsRectification,"0");
					AnbiaoRiskDetail riskDetail1 = riskDetailService.getBaseMapper().selectOne(riskDetailQueryWrapper);
					if (riskDetail1==null){
						int insert3 = riskDetailService.getBaseMapper().insert(riskDetail3);
						if (insert3 > 0) {
							aa++;
						}
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
				if (StringUtils.isBlank(deail.getTijianriqi()) || deail.getTijianriqi().equals("null") ){
					riskDetail4.setArdContent("体检日期缺项");
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
				if(StringUtils.isBlank(xingshizheng.getAvxFileNo())) {
					riskDetail.setArdTitle("行驶证号");
					riskDetail.setArdContent("行驶证号缺项");
					riskDetail.setArdType("缺项");
					riskDetail.setArdRectificationField("avx_file_no");		//整改字段
					riskDetail.setArdRectificationFieldType("string");
					queryRiskOrInsert(riskDetail);
				}

				//注册日期
				if(xingshizheng.getAvxRegisterDate() != null) {
				} else {
					riskDetail.setArdTitle("注册日期");
					riskDetail.setArdContent("注册日期缺项");
					riskDetail.setArdType("缺项");
					riskDetail.setArdRectificationField("avx_register_date");		//整改字段
					riskDetail.setArdRectificationFieldType("date");
					queryRiskOrInsert(riskDetail);
				}

				//有效期
				if(xingshizheng.getAvxValidUntil() != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);
					int timeDifference = differentDays(DateUtil.now(),xingshizheng.getAvxValidUntil());
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
					riskDetail.setArdContent("行驶证有效期缺项");
					riskDetail.setArdType("缺项");
					riskDetail.setArdRectificationField("avx_valid_until");		//整改字段
					riskDetail.setArdRectificationFieldType("date");
					queryRiskOrInsert(riskDetail);
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
				if(StringUtils.isBlank(daoluyunshuzheng.getAvdRoadTransportCertificateNo())) {
					riskDetail.setArdTitle("道路运输证号");
					riskDetail.setArdContent("道路运输证号缺项");
					riskDetail.setArdType("缺项");
					riskDetail.setArdRectificationField("avd_road_transport_certificate_no");		//整改字段
					riskDetail.setArdRectificationFieldType("string");
					queryRiskOrInsert(riskDetail);
				}

				//注册日期
				if(daoluyunshuzheng.getAvdIssueDate() != null) {
				} else {
					riskDetail.setArdTitle("发证日期");
					riskDetail.setArdContent("发证日期缺项");
					riskDetail.setArdType("缺项");
					riskDetail.setArdRectificationField("avd_issue_date");		//整改字段
					riskDetail.setArdRectificationFieldType("date");
					queryRiskOrInsert(riskDetail);
				}

				//有效期
				if(daoluyunshuzheng.getAvdValidUntil() != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);
					int timeDifference = differentDays(DateUtil.now(),daoluyunshuzheng.getAvdValidUntil());
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
					riskDetail.setArdTitle("道路运输证有效期");
					riskDetail.setArdContent("道路运输证有效期缺项");
					riskDetail.setArdType("缺项");
					riskDetail.setArdRectificationField("avd_valid_until");		//整改字段
					riskDetail.setArdRectificationFieldType("date");
					queryRiskOrInsert(riskDetail);
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


	//每5分钟执行一次
	@Scheduled(cron = "0 */5 * * * ?")
	//每天凌晨5点执行一次
//	@Scheduled(cron = "0 0 5 * * ?")
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


			System.out.println("执行完成");
		} catch (Exception e) {
			log.error("定时任务-执行同步预警数据-执行出错", e.getMessage());
		}
		SynchronousCrontab.taskFlag = false;
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}


}
