package org.springblade.anbiao.weixiu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
	@TableId(value = "awp_ids",type = IdType.AUTO)
	private Long awpIds;
	private Long awpFsIds;
	private Integer id;
	private String awpFsName;
	private String	awpName;
	private String awpModel;
	private String	awpUnit;
	private BigDecimal awpPurchasePrice;
	private BigDecimal 	awpUnitPrice;
	private String awpRemarks;
	private Long peijianId;
	@TableField(exist = false)
	private Long peijianID;

	public Long getPeijianId() {
		return peijianId;
	}

	public void setPeijianId(Long peijianId) {
		this.peijianId = peijianId;
	}

	public Long getPeijianID() {
		return peijianID;
	}

	public void setPeijianID(Long peijianID) {
		this.peijianID = peijianID;
	}
}
