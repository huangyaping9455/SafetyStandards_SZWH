package org.springblade.gps.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hyp
 */
@Data
@ApiModel(value = "TpvehDataCount",description = "TpvehDataCount对象")
public class TpvehDataCount {

	@ApiModelProperty(value = "企业ID")
	private String deptID;

	@ApiModelProperty(value = "注册车数")
	private Integer MonitorCount;

	@ApiModelProperty(value = "停用车辆数")
	private Integer DecommissioningCount;

	@ApiModelProperty(value = "在用车辆数")
	private Integer RegisterCount;

	@ApiModelProperty(value = "离线车辆数")
	private Integer offLineCount;

	@ApiModelProperty(value = "在线车辆数")
	private Integer OnLineCount;

	@JsonProperty("DeptID")
	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	@JsonProperty("MonitorCount")
	public Integer getMonitorCount() {
		return MonitorCount;
	}

	public void setMonitorCount(Integer monitorCount) {
		MonitorCount = monitorCount;
	}

	@JsonProperty("DecommissioningCount")
	public Integer getDecommissioningCount() {
		return DecommissioningCount;
	}

	public void setDecommissioningCount(Integer decommissioningCount) {
		DecommissioningCount = decommissioningCount;
	}

	@JsonProperty("RegisterCount")
	public Integer getRegisterCount() {
		return RegisterCount;
	}

	public void setRegisterCount(Integer registerCount) {
		RegisterCount = registerCount;
	}

	public Integer getOffLineCount() {
		return offLineCount;
	}

	public void setOffLineCount(Integer offLineCount) {
		this.offLineCount = offLineCount;
	}

	@JsonProperty("OnLineCount")
	public Integer getOnLineCount() {
		return OnLineCount;
	}

	public void setOnLineCount(Integer onLineCount) {
		OnLineCount = onLineCount;
	}
}
