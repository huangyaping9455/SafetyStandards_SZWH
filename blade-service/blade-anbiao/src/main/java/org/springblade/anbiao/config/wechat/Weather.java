package org.springblade.anbiao.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @title: Weather
 * @projectName maku-cloud
 * @description: 获取百度天气数据
 * @author Administrator
 * @create 2023-10-07 10:52
 */
@Data
@Component
@ConfigurationProperties("weather")
public class Weather {

	private String districtId;

	private String dataType;

	private String ak;
}

