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
@ApiModel(value = "DeptPersistentUnpositioningTongJi对象", description = "DeptPersistentUnpositioningTongJi对象")
public class DeptPersistentUnpositioningTongJi implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private Integer deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "车辆ID")
	private String vehicleID;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "车辆型号")
	private String xinghao;

	@ApiModelProperty(value = "最后定位时间")
	private String lastLocateTime;

	@ApiModelProperty(value = "最后上线时间")
	private String lastLineTime;

	@ApiModelProperty(value = "持续不定位时长")
	private String lastLocatekeepTime;

	@ApiModelProperty(value = "持续不在线时长")
	private String lastLinekeepTime;

}
