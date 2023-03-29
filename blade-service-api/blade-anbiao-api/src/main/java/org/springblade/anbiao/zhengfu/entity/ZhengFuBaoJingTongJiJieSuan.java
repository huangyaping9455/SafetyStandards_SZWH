/**
 * Copyright (C), 2015-2020,
 * FileName: ZhengFuBaoJingTongJi
 * Author:   呵呵哒
 * Date:     2020/7/7 20:29
 * Description:
 */
package org.springblade.anbiao.zhengfu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/7/7
 * @描述
 */
@Data
@ApiModel(value = "ZhengFuBaoJingTongJiJieSuan对象", description = "ZhengFuBaoJingTongJiJieSuan对象")
public class ZhengFuBaoJingTongJiJieSuan implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "地区名称")
	private String areaname;

	@ApiModelProperty(value = "所属运管")
	private String zhengfuname;

	@ApiModelProperty(value = "所属运管ID")
	private String zhengfuid;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "报警总数")
	private Integer baojingzongshu;

	@ApiModelProperty(value = "GPS超速报警数")
	private Integer gpschaosu;

	@ApiModelProperty(value = "GPS疲劳驾驶报警数")
	private Integer gpspilao;

	@ApiModelProperty(value = "GPS夜间行驶报警数")
	private Integer gpsyejian;

	@ApiModelProperty(value = "GPS异常车辆报警数")
	private Integer gpsyichang;

	@ApiModelProperty(value = "GPS报警总数")
	private Integer gpsbaojingzongshu;

	@ApiModelProperty(value = "DMS疲劳驾驶报警数")
	private Integer dmspilao;

	@ApiModelProperty(value = "DMS抽烟报警数")
	private Integer dmschouyan;

	@ApiModelProperty(value = "DMS接打电话报警数")
	private Integer dmsjiedadianhua;

	@ApiModelProperty(value = "DMS分神驾驶报警数")
	private Integer dmsfenshen;

	@ApiModelProperty(value = "DMS报警总数")
	private Integer dmsbaojingzongshu;

	@ApiModelProperty(value = "单车报警比")
	private String danchebaojingbi;

	@ApiModelProperty(value = "车辆总数")
	private Integer cheliangshu;

	@ApiModelProperty(value = "时间")
	private String date;

	@ApiModelProperty(value = "报警车辆数")
	private Integer baojingcheliangshu;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;
}
