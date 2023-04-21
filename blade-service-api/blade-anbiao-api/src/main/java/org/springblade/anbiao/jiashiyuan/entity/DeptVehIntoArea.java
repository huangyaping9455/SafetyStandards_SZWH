/**
 * Copyright (C), 2015-2020,
 * FileName: ZhengFuBaoJingTongJi
 * Author:   呵呵哒
 * Date:     2020/7/7 20:29
 * Description:
 */
package org.springblade.anbiao.jiashiyuan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2022/4/1
 * @描述
 */
@Data
@ApiModel(value = "DeptVehIntoArea对象", description = "DeptVehIntoArea对象")
public class DeptVehIntoArea implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "区域ID")
	private String areaId;

	@ApiModelProperty(value = "区域名称")
	private String area;

	@ApiModelProperty(value = "企业ID")
	private String qiyeid;

	@ApiModelProperty(value = "企业名称")
	private String qiyemingcheng;

	@ApiModelProperty(value = "报警总数")
	private Integer baojingzongshu;

	@ApiModelProperty(value = "报警处理数")
	private Integer chulinum;

	@ApiModelProperty(value = "统计周期")
	private String date;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "车辆型号")
	private String xinghao;

	@ApiModelProperty(value = "车辆id")
	private String VehicleId;
}
