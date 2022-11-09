package org.springblade.anbiao.weixiu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/3 22:05
 */
@Data
@TableName("anbiao_cheliang_baoyangweixiu")
public class MaintenanceEntity {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private Integer	dept_Id;
	private Integer driver_Id;
	private Integer	vehicle_Id;
	private Integer maintain_Dict_Id;
	private String	expected_Completion;
	private String send_Date;
	private String	actual_Completion_Date;
	private double in_Range_Mileage;
	private double	in_The_Oil;
	private double next_Maintenance_Mileage;
	private String	next_Maintenance_Date;
	private String maintenance_Dept_Name;
	private String	fujian;
	private Integer send_Repair_Person_Id;
	private Integer	pick_Up_Vehicle_Driver_Id;
	private String pick_Up_Vehicle_Date;
	private Integer	caozuorenid;
	private String  caozuoren;
	private String 	caozuoshijian;
	private String  remark;
	private Integer	subtotal_Of_Warranty_Items;
	private Integer subtotal_Of_Material_Quantity;
	private Integer	material_Subtotal;
	private Integer is_Deleted;
	private String	createtime;
	private String createperson;
	private Integer	createid;
	private String acb_maintenance_content;
	private String	acb_repair_reason;
	private String acb_bill_attachment;
	private String	acb_before_maintenance;
	private String acb_after_maintenance;
}
