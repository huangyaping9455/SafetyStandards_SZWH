package org.springblade.anbiao.weixiu.DTO;

import lombok.Data;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/4 11:06
 */
@Data
public class MaintenanceDTO {
	private Integer maintain_Dict_Id;
	private String	acb_repair_reason;
	private String acb_bill_attachment;
	private String	acb_before_maintenance;
	private String acb_after_maintenance;

	private String det_name;
	private String card;
	private String send_Date;

	private String cailiaomingcheng;
	private String xinghao;
}
