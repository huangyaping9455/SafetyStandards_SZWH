package org.springblade.train.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.common.BasePage;

import java.util.List;

/**
 * @author 呵呵哒
 */
@Data
@ApiModel(value = "ScholarEducationPage对象", description = "ScholarEducationPage对象")
public class ScholarEducationPage extends BasePage {

	@ApiModelProperty(value = "学员姓名")
	private String realName;

	@ApiModelProperty(value = "登录名")
	private String userName;

	@ApiModelProperty(value = "身份证号码")
	private String identifyNumber;

	@ApiModelProperty(value = "企业Id")
	private String unitId;

	@ApiModelProperty(value = "企业名称",required = true)
	private String unitName;

	@ApiModelProperty(value = "学员id")
	private String studentId;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
}
