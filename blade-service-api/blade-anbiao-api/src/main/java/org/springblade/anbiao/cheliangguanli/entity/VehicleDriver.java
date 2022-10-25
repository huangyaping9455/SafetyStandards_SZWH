/**
 * Copyright (C), 2015-2021
 * FileName: CheliangJiashiyuan
 * Author:   呵呵哒
 * Date:     2021/6/28 14:27
 * Description:
 */
package org.springblade.anbiao.cheliangguanli.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @创建人 hyp
 * @创建时间 2021/08/16
 * @描述
 */
@Data
@ApiModel(value = "VehicleDriver对象", description = "VehicleDriver对象")
public class VehicleDriver implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "驾驶员ID")
	private String id;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	public String getShenfenzhenghao() {
		return shenfenzhenghao;
	}

	public void setShenfenzhenghao(String shenfenzhenghao) {
		this.shenfenzhenghao = shenfenzhenghao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJiashiyuanxingming() {
		return jiashiyuanxingming;
	}

	public void setJiashiyuanxingming(String jiashiyuanxingming) {
		this.jiashiyuanxingming = jiashiyuanxingming;
	}

	public String getShoujihaoma() {
		return shoujihaoma;
	}

	public void setShoujihaoma(String shoujihaoma) {
		this.shoujihaoma = shoujihaoma;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
