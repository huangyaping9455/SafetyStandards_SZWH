package org.springblade.gps.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hyp
 */
@Data
@ApiModel(value = "ZFTpvehData",description = "ZFTpvehData对象")
public class ZFTpvehData {

	@ApiModelProperty(value = "车牌颜色")
	private String vehicleColor;

	@ApiModelProperty(value = "运管名称")
	private String yunguanmingcheng;

	@ApiModelProperty(value = "县级")
	private String country;

	@ApiModelProperty(value = "车辆状态")
	private String VehState;

	@ApiModelProperty(value = "报警类型")
	private String alarmtype;

	@ApiModelProperty(value = "市级")
	private String city;

	@ApiModelProperty(value = "终端ID")
	private String DeviceID;

	@ApiModelProperty(value = "企业ID")
	private String DeptID;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "车辆牌照")
	private String vehicleNo;

	@ApiModelProperty(value = "机构类型")
	private String jigouleixing;

	@ApiModelProperty(value = "省级")
	private String province;

	@ApiModelProperty(value = "所属地市")
	private String areaName;

	@ApiModelProperty(value = "运管ID")
	private String yunguanid;

	@ApiModelProperty(value = "车辆ID")
	private String vehicleID;

	@ApiModelProperty(value = "企业名称")
	private String DeptName;

	@ApiModelProperty(value = "车辆状态")
	private String zhuangtai;

	@ApiModelProperty(value = "在线状态")
	private String zaixian;

	@ApiModelProperty(value = "所属地市")
	private String area;

	@ApiModelProperty(value = "经度")
	private String longitude;

	@ApiModelProperty(value = "纬度")
	private String latitude;

	@JsonProperty("Velocity")
	@ApiModelProperty(value = "速度")
	private String Velocity;

	@JsonProperty("Angle")
	@ApiModelProperty(value = "方向")
	private String Angle;

	@ApiModelProperty(value = "GPS时间")
	private String gpstime;

	@JsonProperty("DeptID")
	public String getDeptID() {
		return DeptID;
	}

	public void setDeptID(String deptID) {
		DeptID = deptID;
	}

	@JsonProperty("VehState")
	public String getVehState() {
		return VehState;
	}

	public void setVehState(String vehState) {
		VehState = vehState;
	}

	public String getShiyongxingzhi() {
		return shiyongxingzhi;
	}

	public void setShiyongxingzhi(String shiyongxingzhi) {
		this.shiyongxingzhi = shiyongxingzhi;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getZaixian() {
		return zaixian;
	}

	public void setZaixian(String zaixian) {
		this.zaixian = zaixian;
	}

	public String getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}

	public String getYunguanmingcheng() {
		return yunguanmingcheng;
	}

	public void setYunguanmingcheng(String yunguanmingcheng) {
		this.yunguanmingcheng = yunguanmingcheng;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAlarmtype() {
		return alarmtype;
	}

	public void setAlarmtype(String alarmtype) {
		this.alarmtype = alarmtype;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@JsonProperty("DeviceID")
	public String getDeviceID() {
		return DeviceID;
	}

	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getJigouleixing() {
		return jigouleixing;
	}

	public void setJigouleixing(String jigouleixing) {
		this.jigouleixing = jigouleixing;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getYunguanid() {
		return yunguanid;
	}

	public void setYunguanid(String yunguanid) {
		this.yunguanid = yunguanid;
	}

	@JsonProperty("DeptName")
	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}

	public String getZhuangtai() {
		return zhuangtai;
	}

	public void setZhuangtai(String zhuangtai) {
		this.zhuangtai = zhuangtai;
	}
}
