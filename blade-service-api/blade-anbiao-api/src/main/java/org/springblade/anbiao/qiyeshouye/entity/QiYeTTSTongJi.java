/**
 * Copyright (C), 2015-2020,
 * FileName: 北斗Vehicle
 * Author:   呵呵哒
 * Date:     2020/7/3 10:29
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @描述
 */
@Data
@ApiModel(value = "QiYeTTSTongJi对象", description = "QiYeTTSTongJi对象")
public class QiYeTTSTongJi implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private Integer deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "车辆ID")
	private String vehId;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "车辆型号")
	private String xinghao;

	@ApiModelProperty(value = "接收设备号")
	private String zongduanid;

	@ApiModelProperty(value = "接收设备")
	private String zhongduanleixing;

	@ApiModelProperty(value = "设备型号")
	private String zongduanxinghao;

	@ApiModelProperty(value = "发送时间")
	private String time;

	@ApiModelProperty(value = "短信内容")
	private String message;

	@ApiModelProperty(value = "短信类型")
	private String messageType;

	@ApiModelProperty(value = "发送类型")
	private String sendType;

	@ApiModelProperty(value = "发送平台")
	private String sendPort;

	@ApiModelProperty(value = "报警类型")
	private String alarmType;

	@ApiModelProperty(value = "发送人")
	private String sendperson;

	@ApiModelProperty(value = "速度")
	private String lastSpeed;

}
