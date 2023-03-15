package org.springblade.anbiao.labor.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

import java.net.URL;
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
	private String  alrReceiptDate;
	private String alrPersonAutograph;
	private String 	alrDelete;
	private String alrCreateTime;
	private String 	alrCreateByIds;
	private String alrCreateByName;
	private String	alrUpdateTime;
	private String alrUpdateByIds;
	private String 	alrUpdateByName;
	@TableField(exist = false)
	private String asiDeptIds;
	private String aliApplicationScope;
	@TableField(exist = false)
	private int sumReceive;
	@ColumnWidth(15)
	@TableField(exist = false)
	@ExcelProperty("图片")
	private URL imgUrl;
	@TableField(exist = false)
	private String aliName;
}
