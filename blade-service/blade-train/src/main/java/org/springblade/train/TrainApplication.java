package org.springblade.train;

import org.springblade.core.launch.BladeApplication;
import org.springblade.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 教育模块服务启动器
 */
@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class TrainApplication {

	public static void main(String[] args) {
		BladeApplication.run("blade-train", TrainApplication.class, args);
	}

}

