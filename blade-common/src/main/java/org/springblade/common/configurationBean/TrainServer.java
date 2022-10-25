package org.springblade.common.configurationBean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 呵呵哒
 * @description: nacos动态获取文件路径/url前缀
 */
@ConfigurationProperties("trainserver")
@Component
@Data
public class TrainServer {

	/**
	 * 文件地址
	 */
	private String fileserver;

	/**
	 * 是否启用人脸识别
	 */
	private boolean enable;

	/**
	 * API Key
	 */
	private String clientId;

	/**
	 * Secret Key
	 */
	private String clientSecret;

	/**
	 * 获取token
	 */
	private String accessTokenUrl;

	/**
	 * 添加用户组
	 */
	private String groupAddUrl;

	/**
	 * 删除用户组
	 */
	private String groupDeleteUrl;

	/**
	 * 注册用户
	 */
	private String userAddUrl;

	/**
	 * 删除用户
	 */
	private String userDeleteUrl;

	/**
	 * 匹配用户
	 */
	private String searchFaceUrl;

	/**
	 * 图片类型
	 */
	private String imageType;

	/**
	 * 平台编号id
	 */
	private String facePlatformId;

	/**
	 * 识别分数
	 */
	private String searchScore;

	/**
	 * 获取用户信息的URL
	 */
	private String userGetUrl;

}
