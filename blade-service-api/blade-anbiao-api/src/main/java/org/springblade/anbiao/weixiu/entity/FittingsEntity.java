package org.springblade.anbiao.weixiu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description : 维修配件实体
 * @Author : long
 * @Date :2022/11/4 11:32
 */
@Data
@TableName("anbiao_weixiu_peijian")
public class FittingsEntity {
	@TableId(value = "id", type = IdType.UUID)
	private Long awp_ids;
	private Long awp_fs_ids;
	private String awp_fs_name;
	private String	awp_name;
	private String awp_model;
	private String	awp_unit;
	private BigDecimal awp_purchase_price;
	private BigDecimal 	awp_unit_price;
	private String awp_remarks;

}
