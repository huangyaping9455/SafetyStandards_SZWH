package org.springblade.anbiao.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @create 2023-10-07 10:51
 * @title: WechatProperties
 * @projectName material_cloud
 * @description: YML 微信配置信息映射
 */
@Data
@Component
@ConfigurationProperties("wechat")
public class WechatProperties {

	private String mappid;

	private String msecret;

	private String originalId;
}

