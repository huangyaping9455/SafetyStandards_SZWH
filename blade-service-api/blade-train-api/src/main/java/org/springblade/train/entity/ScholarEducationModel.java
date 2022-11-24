package org.springblade.train.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ScholarEducationModel implements Serializable {

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Integer id;

	/**
	 * 学员姓名
	 */
	@ApiModelProperty(value = "学员姓名")
	private String realName;
	
	/**
	 * 登录名
	 */
	@ApiModelProperty(value = "登录名")
	private String userName;

	/**
	 * 性别 0:未知 1:男 2:女
	 */
	@ApiModelProperty(value = "性别 0:未知 1:男 2:女")
	private Integer sex;

	/**
	 * 正面照
	 */
	@ApiModelProperty(value = "登录名")
	private String fullFacePhoto;

	/**
	 * 左侧面照
	 */
	@ApiModelProperty(value = "左侧面照")
	private String leftFacePhoto;

	/**
	 * 右侧面照
	 */
	@ApiModelProperty(value = "右侧面照")
	private String rightFacePhoto;

	/**
	 * 身份证号码
	 */
	@ApiModelProperty(value = "身份证号码")
	private String identifyNumber;

	/**
	 * 所属企业
	 */
	@ApiModelProperty(value = "所属企业Id")
	private Integer unitId;

	/**
	 * 所属企业名称
	 */
	@ApiModelProperty(value = "所属企业名称")
	private String unitName;

	/**
	 * 所属部门
	 */
	@ApiModelProperty(value = "所属部门")
	private String departmentName;

	/**
	 * 岗位
	 */
	@ApiModelProperty(value = "岗位")
	private String station;

	/**
	 * 车牌号码
	 */
	@ApiModelProperty(value = "车牌号码")
	private String plateNumber;

	/**
	 * 运输资格证号
	 */
	@ApiModelProperty(value = "运输资格证号")
	private String transPermitCode;

	/**
	 * 区域
	 */
	@ApiModelProperty(value = "区域")
	private Integer areaId;

	/**
	 * 区域名称
	 */
	@ApiModelProperty(value = "区域名称")
	private String areaName;

	/**
	 * 状态 未审核-0，审核通过-1，审核不通过-2
	 */
	@ApiModelProperty(value = "状态 未审核-0，审核通过-1，审核不通过-2")
	private Integer status;

	/**
	 * 删除标识 删除为1，默认为0
	 */
	@ApiModelProperty(value = "删除标识 删除为1，默认为0")
	private String deleted;

	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	private String udpatedBy;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private String udpatedTime;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createdBy;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private String createdTime;

	/**
	 * 课程id
	 */
	@ApiModelProperty(value = "课程Id")
	private Integer courseId;

	/**
	 * 课程名称
	 */
	@ApiModelProperty(value = "登录名")
	private String courseName;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private String beginTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "登录名")
	private String endTime;

	/**
	 * 课程状态(0未完成，1已完成，2未通过，3通过)
	 */
	@ApiModelProperty(value = "课程状态(0未完成，1已完成，2未通过，3通过)")
	private Integer courseStatus;

	/**
	 * 行业id
	 */
	@ApiModelProperty(value = "行业Id")
	private Integer tradeKindId;

	/**
	 * 行业名称
	 */
	@ApiModelProperty(value = "行业名称")
	private String tradeKindName;
	
	/**
	 * 是否可打印证明
	 */
	@ApiModelProperty(value = "是否可打印证明")
	private int enable;
	
	/**
	 * 学员ID
	 */
	@ApiModelProperty(value = "学员ID")
	private int studentId;

}
