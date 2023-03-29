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
import java.time.LocalDateTime;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @描述
 */
@Data
@ApiModel(value = "QiYeDriverAlarmTongJi对象", description = "QiYeDriverAlarmTongJi对象")
public class QiYeDriverAlarmTongJi implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private Integer deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "违规次数")
	private Integer alarmnum;

	@ApiModelProperty(value = "从业资格证")
	private String congyezigezheng;

	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	@ApiModelProperty(value = "车辆牌照")
	private String plate;

	@ApiModelProperty(value = "车牌颜色")
	private String color;

	@ApiModelProperty(value = "报警类型")
	private String alarmType;

	@ApiModelProperty(value = "报警ID")
	private String alarmId;

	@ApiModelProperty(value = "开始时间")
	private String beginTime;

	@ApiModelProperty(value = "结束时间")
	private String endTime;

	@ApiModelProperty(value = "纬度")
	private double lat;

	@ApiModelProperty(value = "经度")
	private double lon;

	@ApiModelProperty(value = "道路名称")
	private String roadname;

	@ApiModelProperty(value = "是否处理")
	private Integer remark;

	@ApiModelProperty(value = "处理形式")
	private String chulixingshi;

	@ApiModelProperty(value = "处理描述")
	private String chulimiaoshu;

	@ApiModelProperty(value = "处理凭证")
	private String fujian;

	@ApiModelProperty(value = "处理状态")
	private String chulizhuangtai;

	@ApiModelProperty(value = "申诉状态")
	private String shensuzhuangtai;

	@ApiModelProperty(value = "最终处理结果")
	private String endresult;

	@ApiModelProperty(value = "报警推送时间")
	private LocalDateTime CutoffTime;

	@ApiModelProperty(value = "申诉审核标识（0:申诉审核中;1:申诉通过;2:申诉驳回）")
	private Integer shensushenhebiaoshi;

	@ApiModelProperty(value = "申诉审核人")
	private String shensushenheren;

	@ApiModelProperty(value = "申诉审核时间")
	private String shensushenheshijian;

	@ApiModelProperty(value = "申诉审核意见")
	private String shensushenheyijian;

	@ApiModelProperty(value = "二次处理形式")
	private String twicechulixingshi;

	@ApiModelProperty(value = "二次处理描述")
	private String twicechulimiaoshu;

	@ApiModelProperty(value = "二次处理附件")
	private String twicefujian;

	@ApiModelProperty(value = "二次处理人")
	private String twicechuliren;

	@ApiModelProperty(value = "二次处理时间")
	private String twicechulishijian;

	@ApiModelProperty(value = "二次处理人ID")
	private String twicechulirenid;

	@ApiModelProperty(value = "申诉形式")
	private String shensuxingshi;

	@ApiModelProperty(value = "申诉描述")
	private String shensumiaoshu;

	@ApiModelProperty(value = "驾驶员ID")
	private String id;

	@ApiModelProperty(value = "是否需要学习")
	private String studystatus;

	@ApiModelProperty(value = "学习结果")
	private String studyremark;

}
