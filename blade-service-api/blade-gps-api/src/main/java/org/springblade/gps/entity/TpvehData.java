package org.springblade.gps.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hyp
 */
@Data
@ApiModel(value = "VehilePTData",description = "VehilePTData对象")
public class TpvehData {

	@ApiModelProperty(value = "企业ID")
	private String DeptID;
	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;
	@ApiModelProperty(value = "终端ID")
	private String deviceID;
	@ApiModelProperty(value = "车牌")
	private String cph;
	@ApiModelProperty(value = "车牌颜色")
	private String vehicleColor;
	@ApiModelProperty(value = "车牌颜色")
	private String platecolor;
	@ApiModelProperty(value = "方向")
	private String Angle;
	@ApiModelProperty(value = "最后定位时间")
	private String LastLocateTime;
	@ApiModelProperty(value = "系统时间")
	private String Systime;
	@ApiModelProperty(value = "报警状态")
	private String alarm;
	@ApiModelProperty(value = "报警类型")
	private String alarmNote;
	@ApiModelProperty(value = "上线状态")
	private String zaixian;
	@ApiModelProperty(value = "车辆ID")
	private String vehicleID;
	@ApiModelProperty(value = "企业ID")
	private String deptID;
	@ApiModelProperty(value = "车辆状态")
	private String VehState;
	@ApiModelProperty(value = "运营商")
	private String yunyingshang;
	@ApiModelProperty(value = "速度")
	private String Velocity;
	@ApiModelProperty(value = "定位时间")
	private String gpstime;
	@ApiModelProperty(value = "定位状态")
	private String LocalFlag;
	@ApiModelProperty(value = "地理位置")
	private String locationName;
	@ApiModelProperty(value = "经度")
	private double longitude;
	@ApiModelProperty(value = "纬度")
	private double latitude;
	@ApiModelProperty(value = "ACC状态")
	private String ACC;
	@ApiModelProperty(value = "信号强度")
	private String WiFiSignal;
	@ApiModelProperty(value = "收星数")
	private String SatelliteLen;
	@ApiModelProperty(value = "随车电话")
	private String accessoryphone;

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

	public String getYunyingshang() {
		return yunyingshang;
	}

	public void setYunyingshang(String yunyingshang) {
		this.yunyingshang = yunyingshang;
	}

	@JsonProperty("Velocity")
	public String getVelocity() {
		return Velocity;
	}

	public void setVelocity(String velocity) {
		Velocity = velocity;
	}

	public String getGpstime() {
		return gpstime;
	}

	public void setGpstime(String gpstime) {
		this.gpstime = gpstime;
	}

	@JsonProperty("LocalFlag")
	public String getLocalFlag() {
		return LocalFlag;
	}

	public void setLocalFlag(String localFlag) {
		LocalFlag = localFlag;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getShiyongxingzhi() {
		return shiyongxingzhi;
	}

	public void setShiyongxingzhi(String shiyongxingzhi) {
		this.shiyongxingzhi = shiyongxingzhi;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getCph() {
		return cph;
	}

	public void setCph(String cph) {
		this.cph = cph;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getPlatecolor() {
		return platecolor;
	}

	public void setPlatecolor(String platecolor) {
		this.platecolor = platecolor;
	}

	@JsonProperty("Angle")
	public String getAngle() {
		return Angle;
	}

	public void setAngle(String angle) {
		Angle = angle;
	}

	@JsonProperty("LastLocateTime")
	public String getLastLocateTime() {
		return LastLocateTime;
	}

	public void setLastLocateTime(String lastLocateTime) {
		LastLocateTime = lastLocateTime;
	}

	@JsonProperty("Systime")
	public String getSystime() {
		return Systime;
	}

	public void setSystime(String systime) {
		Systime = systime;
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public String getAlarmNote() {
		return alarmNote;
	}

	public void setAlarmNote(String alarmNote) {
		this.alarmNote = alarmNote;
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

	public String getACC() {
		return ACC;
	}

	public void setACC(String ACC) {
		this.ACC = ACC;
	}

	public String getWiFiSignal() {
		return WiFiSignal;
	}

	public void setWiFiSignal(String wiFiSignal) {
		WiFiSignal = wiFiSignal;
	}

	public String getSatelliteLen() {
		return SatelliteLen;
	}

	public void setSatelliteLen(String satelliteLen) {
		SatelliteLen = satelliteLen;
	}

	public String getAccessoryphone() {
		return accessoryphone;
	}

	public void setAccessoryphone(String accessoryphone) {
		this.accessoryphone = accessoryphone;
	}
}
