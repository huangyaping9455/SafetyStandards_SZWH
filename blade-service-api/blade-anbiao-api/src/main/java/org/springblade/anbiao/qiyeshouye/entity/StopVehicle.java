/**
 * Copyright (C), 2015-2020,
 * FileName: 北斗Vehicle
 * Author:   呵呵哒
 * Date:     2020/7/3 10:29
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/8/31
 * @描述
 */
@Data
@ApiModel(value = "StopVehicle对象", description = "StopVehicle对象")
public class StopVehicle implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "车辆ID")
	private String vehId;

	@ApiModelProperty(value = "车牌号")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "停车开始时间")
	private String startTime;

	@ApiModelProperty(value = "停车结束时间")
	private String endTime;

	@ApiModelProperty(value = "停车时长 单位秒")
	private String stopLong;

	@ApiModelProperty(value = "停车时的里程")
	private String stopMileage;

	@ApiModelProperty(value = "停车类型 1 熄火停车 2 未熄火停车")
	private Integer stopType;

	@ApiModelProperty(value = "停车类型")
	private String stopTypeShow;

	@ApiModelProperty(value = "停车位置")
	private String stopLocatin;

	@ApiModelProperty(value = "停车次数")
	private Integer stopTimes;

	@ApiModelProperty(value = "停车未熄火次数")
	private Integer stopAccOnTimes;

	@ApiModelProperty(value = "停车未熄火时长")
	private String stopAccOnLong;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getVehId() {
		return vehId;
	}

	public void setVehId(String vehId) {
		this.vehId = vehId;
	}

	public String getCheliangpaizhao() {
		return cheliangpaizhao;
	}

	public void setCheliangpaizhao(String cheliangpaizhao) {
		this.cheliangpaizhao = cheliangpaizhao;
	}

	public String getChepaiyanse() {
		return chepaiyanse;
	}

	public void setChepaiyanse(String chepaiyanse) {
		this.chepaiyanse = chepaiyanse;
	}

	public String getShiyongxingzhi() {
		return shiyongxingzhi;
	}

	public void setShiyongxingzhi(String shiyongxingzhi) {
		this.shiyongxingzhi = shiyongxingzhi;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStopLong() {
		return stopLong;
	}

	public void setStopLong(String stopLong) {
		this.stopLong = stopLong;
	}

	public String getStopMileage() {
		return stopMileage;
	}

	public void setStopMileage(String stopMileage) {
		this.stopMileage = stopMileage;
	}

	public Integer getStopType() {
		return stopType;
	}

	public void setStopType(Integer stopType) {
		this.stopType = stopType;
	}

	public String getStopTypeShow() {
		return stopTypeShow;
	}

	public void setStopTypeShow(String stopTypeShow) {
		this.stopTypeShow = stopTypeShow;
	}

	public String getStopLocatin() {
		return stopLocatin;
	}

	public void setStopLocatin(String stopLocatin) {
		this.stopLocatin = stopLocatin;
	}

	public Integer getStopTimes() {
		return stopTimes;
	}

	public void setStopTimes(Integer stopTimes) {
		this.stopTimes = stopTimes;
	}

	public Integer getStopAccOnTimes() {
		return stopAccOnTimes;
	}

	public void setStopAccOnTimes(Integer stopAccOnTimes) {
		this.stopAccOnTimes = stopAccOnTimes;
	}

	public String getStopAccOnLong() {
		return stopAccOnLong;
	}

	public void setStopAccOnLong(String stopAccOnLong) {
		this.stopAccOnLong = stopAccOnLong;
	}
}
