package org.springblade.anbiao.weixiu.VO;

import lombok.Data;

/**
 * @Description : 维修
 * @Author : long
 * @Date :2022/11/4 11:05
 */
@Data
public class MaintenanceVO {
	private String det_name;
	private String card;
	private String driverName;
	private String acb_maintenance_content;
	private String	acb_repair_reason;
	private String	acb_before_maintenance;
	private Integer maintain_Dict_Id;
	private String send_Date;
	private String maintenance_Dept_Name;
}
