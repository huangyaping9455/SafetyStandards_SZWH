package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@TableName("base_unit")
public class Unit implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 企业简称
	 */
	@TableField("simple_name")
	private String simpleName;
	
	/**
	 * 企业全称
	 */
	@TableField("full_name")
	private String fullName;
	
	/**
	 * 政府部门名称
	 */
	@TableField("department_name")
	private String departmentName;
	
	/**
	 * 所属行业
	 */
	@TableField("trade_kind_id")
	private Integer tradeKindId;
	
	/**
	 * 所属区域
	 */
	@TableField("area_id")
	private Integer areaId;
	
	/**
	 * 联系人
	 */
	@TableField("contact")
	private String contact;
	
	/**
	 * 联系电话
	 */
	@TableField("telephone")
	private String telephone;
	
	/**
	 * 联系地址
	 */
	@TableField("address")
	private String address;
	
	/**
	 * 所属服务商 默认0，无服务商
	 */
	@TableField("server_id")
	private Integer serverId;
	
	/**
	 * 所属政府id
	 */
	@TableField("government_id")
	private Integer governmentId;
	
	/**
	 * 类型 营运商-0，政府-1，代理商-2，企业-3
	 */
	@TableField("type")
	private Integer type;
	
	/**
	 * 状态 正常-1，暂停-2
	 */
	@TableField("status")
	private Integer status;

	/**
	 * 企业Logo
	 */
	@TableField("logo")
	private String logo;
	
	/**
	 * 删除标识 删除为1，默认为0
	 */
	@TableField("deleted")
	private Integer deleted;
	
	/**
	 * 创建人
	 */
	@TableField("created_by")
	private String createdBy;
	
	/**
	 * 创建时间
	 */
	@TableField("created_time")
	private String createdTime;
	
	/**
	 * 更新人
	 */
	@TableField("updated_by")
	private String updatedBy;
	
	/**
	 * 更新时间
	 */
	@TableField("updated_time")
	private String updatedTime;
	
	
	/**
	 * 所属区域
	 */
	@TableField(exist = false)
	private String areaName;
	
	/**
	 * 所属区域
	 */
	@TableField(exist = false)
	private String tradeKindName;
	
	/**
	 * 企业数
	 */
	@TableField(exist = false)
	private Integer companyCount= 0;
	
	/**
	 * 课程数
	 */
	@TableField(exist = false)
	private Integer courseCount= 0;
	
	/**
	 * 学员数
	 */
	@TableField(exist = false)
	private Integer studentCount= 0;
	
	/**
	 * 所属政府名称
	 */
	@TableField(exist = false)
	private String governmentName;
	
	/**
	 * 所属服务商名称
	 */
	@TableField(exist = false)
	private String serverName;

}
