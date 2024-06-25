package org.springblade.common.configurationBean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 呵呵哒
 * @description: nacos动态获取文件路径/url前缀
 * @projectName SafetyStandards
 * @date 2019/5/2211:35
 */
@ConfigurationProperties("wechatserver")
@Component
@Data
public class WechatServer {

	/**
	 * 小程序的appid
	 */
	private String appId;
	/**
	 * 小程序密匙
	 */
	private String secret;

	/**
	 * 企管小程序的appid
	 */
	private String qyAppId;
	/**
	 * 企管小程序密匙
	 */
	private String qySecret;

	/**
	 * 微信公众号appid
	 */
	private String wxgzhAppId;
	/**
	 * 微信公众号密匙
	 */
	private String wxgzhSecret;


}
