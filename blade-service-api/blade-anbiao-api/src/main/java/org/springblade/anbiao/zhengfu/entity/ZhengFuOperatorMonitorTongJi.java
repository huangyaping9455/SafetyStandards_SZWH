/**
 * Copyright (C), 2015-2020,
 * FileName: 北斗Vehicle
 * Author:   呵呵哒
 * Date:     2020/7/3 10:29
 * Description:
 */
package org.springblade.anbiao.zhengfu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/10/12
 * @描述
 */
@Data
@TableName("baobiao_operator_monitor")
@ApiModel(value = "ZhengFuOperatorMonitorTongJi对象", description = "ZhengFuOperatorMonitorTongJi对象")
public class ZhengFuOperatorMonitorTongJi implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String company;

	@ApiModelProperty(value = "车辆总数")
	private Integer vehicleCount;

	@ApiModelProperty(value = "上线车辆数")
	private Integer upLineCount;

	@ApiModelProperty(value = "离线车辆数")
	private Integer offLineCount;

	@ApiModelProperty(value = "上线率")
	private String onlineRate;

	@ApiModelProperty(value = "时间段")
	private String date;

	@ApiModelProperty(value = "定位车辆数")
	private Integer locateCount;

	@ApiModelProperty(value = "定位率")
	private String locateRate;

	@ApiModelProperty(value = "轨迹漂移率")
	private String DriftPositionRate;

	@ApiModelProperty(value = "轨迹完整率")
	private String IntactPositionRate;

	@ApiModelProperty(value = "数据合格率")
	private String QualifiedPositionRate;

	@ApiModelProperty(value = "运营商名称")
	private String operatorName;

	@ApiModelProperty(value = "所属地区")
	private String areaname;

	@ApiModelProperty(value = "所属运管")
	private String zhengfuname;

	@ApiModelProperty(value = "所属运管ID")
	private String zhengfuid;

}
