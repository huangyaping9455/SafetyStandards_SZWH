package org.springblade.anbiao.weixiu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springblade.anbiao.weixiu.DTO.FittingDTO;

import java.util.List;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/3 22:05
 */
@Data
@TableName("anbiao_cheliang_baoyangweixiu")
public class MaintenanceEntity {

	private Long id;
	private Integer	depID;
	private String  driverId;
	private String	vehicleId;
	private Integer maintainDictId;
	private String	expectedCompletion;
	private String sendDate;
	private String	actualCompletionDate;
	private double inRangeMileage;
	private double	inTheOil;
	private double nextMaintenanceMileage;
	private String	nextMaintenanceDate;
	private String maintenanceDeptName;
	private String	fujian;
	private Integer sendRepairPersonId;
	private Integer	pickUpVehicleDriverId;
	private String pickUpVehicleDate;
	private Integer	caozuorenid;
	private String  caozuoren;
	private String 	caozuoshijian;
	private String  remark;
	private Integer	subtotalOfWarrantyItems;
	private Integer subtotalOfMaterialQuantity;
	private Integer	materialSubtotal;
	private Integer isDeleted;
	private String	createtime;
	private String createperson;
	private Integer	createid;
	private String acbMaintenanceContent;
	private String	acbRepairReason;
	private String acbBillAttachment;
	private String	acbBeforeMaintenance;
	private String acbAfterMaintenance;

	private List<FittingsEntity> fittingDTOS;
}
