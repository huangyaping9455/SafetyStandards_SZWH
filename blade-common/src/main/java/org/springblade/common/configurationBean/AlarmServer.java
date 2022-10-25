package org.springblade.common.configurationBean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 呵呵哒
 * @description: nacos动态获取文件路径/url前缀
 */
@ConfigurationProperties("alarmserver")
@Component
@Data
public class AlarmServer {

	private String addressPath;

//	超期时间设置
	private Integer beyondthetime;

//	即将超期时间设置
	private Integer runoutthetime;

//	即将超期时间设置
	private Integer realtime;

	/**
	 * 下载模板地址
	 */
	private String templateUrl;
}
