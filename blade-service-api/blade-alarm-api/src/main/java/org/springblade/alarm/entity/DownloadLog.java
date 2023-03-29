package org.springblade.alarm.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DownloadLog {
	private String id;

	private String userid;

	private String username;

	private String modularid;

	private String modelar;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss ")
	private Date createtime;
	private String createtimeshow;

	private Integer countsize;
	private String name;

	private String deptId;
	private String deptName;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	public String getModularid() {
		return modularid;
	}

	public void setModularid(String modularid) {
		this.modularid = modularid == null ? null : modularid.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getCountsize() {
		return countsize;
	}

	public void setCountsize(Integer countsize) {
		this.countsize = countsize;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getModelar() {
		return modelar;
	}

	public void setModelar(String modelar) {
		this.modelar = modelar;
	}

	public String getCreatetimeshow() {
		return createtimeshow;
	}

	public void setCreatetimeshow(String createtimeshow) {
		this.createtimeshow = createtimeshow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
