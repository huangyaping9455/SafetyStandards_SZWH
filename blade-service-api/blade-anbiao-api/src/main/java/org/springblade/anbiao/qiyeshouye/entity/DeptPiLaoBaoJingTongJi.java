/**
 * Copyright (C), 2015-2020,
 * FileName: ZhengFuBaoJingTongJi
 * Author:   呵呵哒
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 呵呵哒
 * @描述
 */
@Data
@ApiModel(value = "DeptPiLaoBaoJingTongJi对象", description = "DeptPiLaoBaoJingTongJi对象")
public class DeptPiLaoBaoJingTongJi implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private Integer deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "报警ID")
	private String AlarmReportID;

	@ApiModelProperty(value = "车辆ID")
	private String vehid;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "车辆型号")
	private String xinghao;

	@ApiModelProperty(value = "持续时间")
	private String keepTime;

	@ApiModelProperty(value = "疲劳驾驶报警次数")
	private Integer alarmNum;

	@ApiModelProperty(value = "报警最长持续时间")
	private String MaxKeepTime;

	@ApiModelProperty(value = "最长持续时间报警开始时间")
	private String beginTime;

	@ApiModelProperty(value = "最长持续时间报警结束时间")
	private String endTime;

	@ApiModelProperty(value = "最高持续时间位置")
	private String roadName;

}
