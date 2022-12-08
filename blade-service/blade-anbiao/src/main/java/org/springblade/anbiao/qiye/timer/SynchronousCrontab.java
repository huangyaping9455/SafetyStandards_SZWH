package org.springblade.anbiao.qiye.timer;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

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

    private IJiaShiYuanService jiaShiYuanService;

	//获取驾驶员信息
	private void addQYDriverList(int No,int Size) throws IOException {

	}

	//每5分钟执行一次
//	@Scheduled(cron = "0 */5 * * * ?")
	//每天凌晨5点执行一次
	@Scheduled(cron = "0 0 5 * * ?")
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

			//获取驾驶员信息
			addQYDriverList(0,0);


			System.out.println("执行完成");
		} catch (Exception e) {
			log.error("定时任务-执行同步预警数据-执行出错", e.getMessage());
		}
		SynchronousCrontab.taskFlag = false;
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}


}
