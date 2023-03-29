/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuZhuTiPage
 * Author:   呵呵哒
 * Date:     2020/6/20 16:14
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "QiYeDriverAlarmTongJiPage对象", description = "QiYeDriverAlarmTongJiPage对象")
public class QiYeDriverAlarmTongJiPage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID", required = true)
	private Integer deptId;

	@ApiModelProperty(value = "开始时间", required = true)
	private String beginTime;

	@ApiModelProperty(value = "结束时间", required = true)
	private String endTime;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "车辆牌照")
	private String plateNumber;

	@ApiModelProperty(value = "车牌颜色")
	private String color;

	@ApiModelProperty(value = "报警类型")
	private String alarmType;

	@ApiModelProperty(value = "从业资格证号")
	private String congyezigezheng;

	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	@ApiModelProperty(value = "驾驶员ID")
	private String driverId;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

}
