/**
 * Author:   呵呵哒
 * Description:
 */
package org.springblade.anbiao.zhengfu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @描述
 */
@Data
@ApiModel(value = "ZhengFuShouYe对象", description = "ZhengFuShouYe对象")
public class ZhengFuShouYe implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private String id;

	/**
	 * 地区名称
	 */
	@ApiModelProperty(value = "地区名称")
	private String areaName;

	/**
	 * gps报警数
	 */
	@ApiModelProperty(value = "gps报警数")
	private Integer benYueGpsBaoJingShu;

	/**
	 * 设备报警数
	 */
	@ApiModelProperty(value = "设备报警数")
	private Integer benYueSheBeiBaoJingShu;

	/**
	 * 监控企业数
	 */
	@ApiModelProperty(value = "监控企业数")
	private Integer jianKongQiYeShu;

	/**
	 * 注册企业数
	 */
	@ApiModelProperty(value = "注册企业数")
	private Integer zhuCeQiYeShu;

	@ApiModelProperty(value = "日期")
	private String time;

	@ApiModelProperty(value = "监控车辆数")
	private Integer jkvehnum;

	@ApiModelProperty(value = "上线车辆数")
	private Integer sxvehnum;

	@ApiModelProperty(value = "停运车辆数")
	private Integer tyvehnum;

	@ApiModelProperty(value = "注册车辆数")
	private Integer zcvehnumb;

	@ApiModelProperty(value = "总报警数（近7天）")
	private Integer zongbaojingshu;

	@ApiModelProperty(value = "总处理报警数（近7天）")
	private Integer zongchulishu;

	@ApiModelProperty(value = "主动设备处理数（近7天）")
	private Integer shebeichulishu;

	@ApiModelProperty(value = "GPS处理数（近7天）")
	private Integer gpschulishu;

	@ApiModelProperty(value = "GPS报警数（近7天）")
	private Integer gpsbaojingshu;

	@ApiModelProperty(value = "主动设备报警数（近7天）")
	private Integer shebeibaojingshu;

	@ApiModelProperty(value = "处理率（近7天）")
	private String chulilv;

	@ApiModelProperty(value = "gps报警未处理数（近7天）")
	private Integer gpsweichulishu;

	@ApiModelProperty(value = "主动设备报警未处理数（近7天）")
	private Integer shebeiweichulishu;

	@ApiModelProperty(value = "政府运管局Id")
	private String zhengfuid;

	@ApiModelProperty(value = "政府运管局名称")
	private String zhengfuname;

}
