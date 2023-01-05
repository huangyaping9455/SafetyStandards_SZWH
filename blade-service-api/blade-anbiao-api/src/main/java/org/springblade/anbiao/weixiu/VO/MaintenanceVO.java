package org.springblade.anbiao.weixiu.VO;

import lombok.Data;

/**
 * @Description : 维修
 * @Author : long
 * @Date :2022/11/4 11:05
 */
@Data
public class MaintenanceVO {

	private Long Id;
	private String deptName;
	private String carNum;
	private Integer	dept_Id;
	private String cheliangpaizhao;
	private String driverName;
	private String acbMaintenanceContent;
	private String	acbRepairReason;
	private String	acbBeforeMaintenance;
	private String driverId;
	private String acbBillAttachment;
	private Integer maintainDictId;
	private String acbAfterMaintenance;
	private String sendDate;
	private String maintenanceDeptName;
	private String afterMaintenance;
}
