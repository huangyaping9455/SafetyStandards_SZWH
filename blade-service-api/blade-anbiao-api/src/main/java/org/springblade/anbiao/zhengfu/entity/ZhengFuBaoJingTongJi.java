/**
 * Copyright (C), 2015-2020,
 * FileName: ZhengFuBaoJingTongJi
 * Description:
 */
package org.springblade.anbiao.zhengfu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 呵呵哒
 * @描述
 */
@Data
@ApiModel(value = "ZhengFuBaoJingTongJi对象", description = "ZhengFuBaoJingTongJi对象")
public class ZhengFuBaoJingTongJi implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "地区名称")
	private String areaname;

	@ApiModelProperty(value = "企业id")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "报警数")
	private Integer baojingshu;

	@ApiModelProperty(value = "年份")
	private Integer years;

	@ApiModelProperty(value = "未处理数")
	private Integer weichulishu;

	@ApiModelProperty(value = "处理率")
	private String chulilv;

	@ApiModelProperty(value = "月份")
	private String yue;

	@ApiModelProperty(value = "GPS报警数")
	private Integer gpsbaojingshu;

	@ApiModelProperty(value = "设备报警数")
	private Integer shebeibaojingshu;

	@ApiModelProperty(value = "GPS处理数")
	private Integer gpschulishu;

	@ApiModelProperty(value = "设备处理数")
	private Integer shebeichulishu;

	@ApiModelProperty(value = "序号")
	private Integer xuhao;

	@ApiModelProperty(value = "政府运管局Id")
	private String zhengfuid;

	@ApiModelProperty(value = "政府运管局名称")
	private String zhengfuname;

	/**
	 *下级list数据
	 */
	@ApiModelProperty(value = "下级list数据")
	private List<ZhengFuBaoJingTongJi> xjlist;
}
