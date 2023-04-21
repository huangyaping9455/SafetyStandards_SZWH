package org.springblade.doc.safetyproductionfile.timer;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.anbiao.jinritixing.entity.YinHuanVehicle;
import org.springblade.common.configurationBean.FileServer;
import org.springblade.doc.safetyproductionfile.entity.AnbiaoSafetyproductionfileNum;
import org.springblade.doc.safetyproductionfile.service.IAnbiaoSafetyproductionfileNumService;
import org.springblade.doc.safetyproductionfile.service.ISafetyProductionFileService;
import org.springblade.doc.safetyproductionfile.vo.SafetyProductionFileVO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
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

	private FileServer fileServer;

	private ISafetyProductionFileService safetyProductionFileService;

	private IAnbiaoSafetyproductionfileNumService service;


	private void addDeptSafety(Integer deptId) throws IOException {
		SafetyProductionFileVO list= safetyProductionFileService.boxTreeNum(deptId);
		if(list != null){
			AnbiaoSafetyproductionfileNum safetyproductionfileNum = new AnbiaoSafetyproductionfileNum();
			safetyproductionfileNum.setFinshNum(list.getFinshNum());
			safetyproductionfileNum.setUploadedNum(list.getNum());
			safetyproductionfileNum.setCaozuoshijian(DateUtil.now());
			safetyproductionfileNum.setDeptId(deptId);
			safetyproductionfileNum.setDate(DateUtil.now().substring(0,10));
			double num1 = safetyproductionfileNum.getFinshNum();
			double num2 = safetyproductionfileNum.getUploadedNum();
			double ratio = num1*1.0 / num2;
			if(safetyproductionfileNum.getFinshNum() == 0){
				safetyproductionfileNum.setFinshRatio("0.00%");
			}else {
				DecimalFormat df = new DecimalFormat("#.##");
				String formattedRatio = df.format(ratio)+"%";
				safetyproductionfileNum.setFinshRatio(formattedRatio);
			}

			if(safetyproductionfileNum.getFinshNum() != 0){
				QueryWrapper<AnbiaoSafetyproductionfileNum> cheliangJiashiyuanQueryWrapper = new QueryWrapper<>();
				cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoSafetyproductionfileNum::getDeptId,safetyproductionfileNum.getDeptId());
				cheliangJiashiyuanQueryWrapper.lambda().eq(AnbiaoSafetyproductionfileNum::getDate,safetyproductionfileNum.getDate());
				AnbiaoSafetyproductionfileNum deail = service.getBaseMapper().selectOne(cheliangJiashiyuanQueryWrapper);
				if (deail == null){
					int i = service.getBaseMapper().insert(safetyproductionfileNum);
					if(i > 0){
						log.info("定时任务-执行生成生成台账上传情况监测数据成功", DateUtil.now());
					}else{
						log.info("定时任务-执行生成生成台账上传情况监测数据失败", DateUtil.now());
					}
				}
			}
		}
	}

	//每5分钟执行一次
//	@Scheduled(cron = "0 */2 * * * ?")
	//每天凌晨5点执行一次
	@Scheduled(cron = "0 0 5 * * ?")
	public void configureTasks_study() {
		synchronized (KEY) {
			if (SynchronousCrontab.taskFlag) {
				System.out.println("定时任务-执行生成台账上传情况监测数据已经启动" + DateUtil.now());
				log.info("定时任务-执行生成生成台账上传情况监测数据已经启动", DateUtil.now());
				return;
			}
			SynchronousCrontab.taskFlag = true;
		}
		log.warn("定时任务-执行生成生成台账上传情况监测数据更新开始", DateUtil.now());
		try {
			System.out.println("执行生成生成台账上传情况监测数据");

			List<AnbiaoSafetyproductionfileNum> safetyproductionfileNumList = service.getDeptTree(1);
			if(safetyproductionfileNumList != null){
				safetyproductionfileNumList.forEach(item->{
					//生成隐患
					try {
						addDeptSafety(item.getDeptId());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
			System.out.println("执行完成");
		} catch (Exception e) {
			log.error("定时任务-执行生成生成台账上传情况监测数据-执行出错", e.getMessage());
		}
		SynchronousCrontab.taskFlag = false;
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}


}

