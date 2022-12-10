package org.springblade.anbiao.qiye.timer;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
		for (int i = 0; i <= organizationsList.size(); i++) {
			Integer deptId = Integer.parseInt(organizationsList.get(i).getDeptId());
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
				if (deail.getShenfenzhengyouxiaoqi()==null){
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
					if (riskDetail.getArdType().equals("预警") || riskDetail.getArdType().equals("逾期")) {
						int insert = riskDetailService.getBaseMapper().insert(riskDetail);
						if (insert > 0) {
							aa++;
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
				if (deail.getJiashizhengyouxiaoqi()==null){
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
					if (riskDetail2.getArdType().equals("预警") || riskDetail2.getArdType().equals("逾期")) {
						int insert2 = riskDetailService.getBaseMapper().insert(riskDetail2);
						if (insert2 > 0) {
							aa++;
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
				if (deail.getCongyezhengyouxiaoqi()==null){
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
					if (riskDetail3.getArdType().equals("预警") || riskDetail3.getArdType().equals("逾期")) {
						int insert3 = riskDetailService.getBaseMapper().insert(riskDetail3);
						if (insert3 > 0) {
							aa++;
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
				if (deail.getTijianyouxiaoqi()==null){
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

	//每5分钟执行一次
//	@Scheduled(cron = "0 */3 * * * ?")
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


			System.out.println("执行完成");
		} catch (Exception e) {
			log.error("定时任务-执行同步预警数据-执行出错", e.getMessage());
		}
		SynchronousCrontab.taskFlag = false;
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}


}