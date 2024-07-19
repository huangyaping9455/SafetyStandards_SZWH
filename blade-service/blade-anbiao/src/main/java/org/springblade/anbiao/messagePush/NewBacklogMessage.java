package org.springblade.anbiao.messagePush;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther xiaofen
 * @Date2021/12/17
 * @apiNote
 **/
@Data
public class NewBacklogMessage implements Serializable {

	@ApiModelProperty(value = "企业ID")
	public Integer deptId;

	@ApiModelProperty(value = "模板ID")
	public String templateId;

	@ApiModelProperty(value = "小程序appID")
	public String appidUrl;

	@ApiModelProperty(value = "用户公众号openId")
	private String openId;

	@ApiModelProperty(value = "驾驶员ID")
    public String driverId;

	@ApiModelProperty(value = "企业名称")
    private String deptName;

	@ApiModelProperty(value = "报警类别")
	private String alarmType;

	@ApiModelProperty(value = "报警项目")
	private String alarmName;

	@ApiModelProperty(value = "报警时间")
	private String alarmTime;

	@ApiModelProperty(value = "报警原因")
	private String alarmRemark;

	@ApiModelProperty(value = "跳转路径")
	private String pageUrl;


}
