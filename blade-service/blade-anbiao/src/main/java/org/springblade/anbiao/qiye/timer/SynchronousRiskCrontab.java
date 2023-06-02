package org.springblade.anbiao.qiye.timer;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.anquanhuiyi.entity.AnbiaoAnquanhuiyi;
import org.springblade.anbiao.cheliangguanli.entity.*;
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
public class SynchronousRiskCrontab {

	private static final Object KEY = new Object();
	private static boolean taskFlag = false;

	private IAnbiaoRiskDetailService riskDetailService;
	private IJiaShiYuanService jiaShiYuanService;
	private IOrganizationsService organizationsService;


	//获取驾驶员风险信息
	private void addQYDriverRisk() throws IOException, ParseException {

	}

	//每6小时执行一次
	@Scheduled(cron = "0 0 */6 * * ?")
	public void configureTasks_static_data() {
		synchronized (KEY) {
			if (SynchronousRiskCrontab.taskFlag) {
				System.out.println("定时任务-执行推送风险数据已经启动"+DateUtil.now());
				log.info("定时任务-执行推送风险数据已经启动", DateUtil.now());
				return;
			}
			SynchronousRiskCrontab.taskFlag = true;
		}
		log.warn("定时任务-执行推送风险数据更新开始", DateUtil.now());
		try {
			System.out.println("执行推送风险数据");

			//推送驾驶员风险
			addQYDriverRisk();

			System.out.println("执行完成");
		} catch (Exception e) {
			log.error("定时任务-执行推送风险数据-执行出错", e.getMessage());
		}
		SynchronousRiskCrontab.taskFlag = false;
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}


}
