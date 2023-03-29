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
@ApiModel(value = "DeptChaoSuBaoJingTongJi对象", description = "DeptChaoSuBaoJingTongJi对象")
public class DeptChaoSuBaoJingTongJi implements Serializable {

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

	@ApiModelProperty(value = "超速报警次数")
	private Integer alarmNum;

	@ApiModelProperty(value = "最高速度")
	private String MaxSpeed;

	@ApiModelProperty(value = "超速比")
	private String ChaoSuBi;

	@ApiModelProperty(value = "限速")
	private String Limited;

	@ApiModelProperty(value = "最高报警速度位置")
	private String roadName;

}
