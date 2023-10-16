package org.springblade.anbiao.config.wechat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: Weather
 * @projectName maku-cloud
 * @description: 天气
 * @author Administrator
 * @create 2023-10-07 10:53
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherModel {

	/** 省 */
	private String province;
	/** 市 */
	private String city;
	/** 区 */
	private String name;
	/** 天气 */
	private String text;
	/** 级 */
	private String wind_class;
	/** 风向 */
	private String wind_dir;

	private String textNight;
	/** 最高温度 */
	private String high;
	/** 最低温度 */
	private String low;
	/** 日期 */
	private String date;
	/** 星期几 */
	private String week;
	/** 当前温度 */
	private String temp;

}

