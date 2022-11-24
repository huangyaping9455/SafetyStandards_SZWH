/*
 * 	Copyright 2019-2020 www.01n16.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.springblade.train.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * <p>ClassName: LiveProperties
 * <p>Description: [直播相关配置属性(由开发商提供)]
 * @author system
 * @date 2020年03月13日 09:26:27
*/
@Data
@Component
@ConfigurationProperties(prefix = "face")
public class FaceProperties {

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
