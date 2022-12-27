package org.springblade.anbiao.weixiu.page;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.common.BasePage;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/8 18:52
 */
@Data
public class MaintenancePage extends BasePage {


	private Long id;

	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private String asiYear;

	private String deptName;

	private String carNum;

	private Integer	deptId;

	private Integer	dept_Id;

	private String	vehicleId;

	private String maintenanceDeptName;

	private String acbMaintenanceContent;

	private String	acbRepairReason;

	private String	acbBeforeMaintenance;

	private String driverId;

}
