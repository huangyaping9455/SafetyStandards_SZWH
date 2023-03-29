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
 * @创建时间 2020/8/31
 * @描述
 */
@Data
@ApiModel(value = "PersonLearnRemark对象", description = "PersonLearnRemark对象")
public class PersonLearnRemark implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Integer id;
	@ApiModelProperty(value = "企业ID")
	private Integer deptid;
	@ApiModelProperty(value = "企业名称")
	private String deptname;
	@ApiModelProperty(value = "学员ID")
	private String userid;
	@ApiModelProperty(value = "姓名")
	private String username;
	@ApiModelProperty(value = "手机号")
	private String userphone;
	@ApiModelProperty(value = "学习时间")
	private String learntime;
	@ApiModelProperty(value = "学习状态")
	private String status;
	@ApiModelProperty(value = "学习年份")
	private Integer lyear;
	@ApiModelProperty(value = "学习月份")
	private Integer lmonth;
	@ApiModelProperty(value = "学习平台")
	private String luser;
	@ApiModelProperty(value = "更新时间")
	private String updatetime;
	@ApiModelProperty(value = "学习方式")
	private String learnStudy;
	@ApiModelProperty(value = "学习主题")
	private String learnTheme;
	@ApiModelProperty(value = "主持人")
	private String compere;
	@ApiModelProperty(value = "学习附件")
	private String img;
	@ApiModelProperty(value = "学习内容")
	private String learncontent;

}
