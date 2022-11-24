package org.springblade.train.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Description: [行业类型实体类]
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_trade_kind")
public class TradeKind implements Serializable {

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
	 * 行业名称
	 */
	@TableField("name")
	private String name;
	
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
	
	
}
