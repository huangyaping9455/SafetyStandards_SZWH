package org.springblade.anbiao.labor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/9 17:30
 */
@Data
@TableName("anbiao_labor_receive")
public class LaborlingquEntity {
	@TableId(value = "alr_ids",type = IdType.UUID)
	private String  alrIds;
	private String 	alrAliIds;
	private String  alrPersonIds;
	private String 	alrPersonName;
	private Integer alrReceiptsNumber;
	private Date  alrReceiptDate;
	private String alrPersonAutograph;
	private String 	alrDelete;
	private Date alrCreateTime;
	private String 	alrCreateByIds;
	private String alrCreateByName;
	private Date	alrUpdateTime;
	private String alrUpdateByIds;
	private String 	alrUpdateByName;
	private String asiDeptIds;
	private String aliApplicationScope;
}
