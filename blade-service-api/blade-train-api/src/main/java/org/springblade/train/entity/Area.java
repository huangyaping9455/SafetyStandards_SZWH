package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ClassName: Area
 * Description: [行政区实体类]
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_area")
public class Area implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 地区编码
	 */
	@TableField("code")
	private String code;
	
	/**
	 * 上级id
	 */
	@TableId("pid")
	private Integer pid;
	
	/**
	 * fullid 按6位对齐，以点连接
	 */
	@TableId("fullid")
	private String fullid;
	
	/**
	 * 行政区简称
	 */
	@TableId("simple_name")
	private String simpleName;
	
	/**
	 * 行政区全称
	 */
	@TableId("full_name")
	private String fullName;


	
}
