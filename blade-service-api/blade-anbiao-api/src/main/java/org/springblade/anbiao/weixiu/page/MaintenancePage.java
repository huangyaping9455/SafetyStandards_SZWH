package org.springblade.anbiao.weixiu.page;

import lombok.Data;
import org.springblade.common.BasePage;

import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/8 18:52
 */
@Data
public class MaintenancePage extends BasePage {

	private Date startTime;

	private Date endTime;

	private Integer	dept_Id;

	private Integer	vehicle_Id;

	private String maintenance_Dept_Name;

	private String acb_maintenance_content;

	private String	acb_repair_reason;

	private String	acb_before_maintenance;
}
