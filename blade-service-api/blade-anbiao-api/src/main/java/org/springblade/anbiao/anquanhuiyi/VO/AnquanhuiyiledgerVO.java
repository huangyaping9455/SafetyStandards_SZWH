package org.springblade.anbiao.anquanhuiyi.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "AnquanhuiyiledgerVO", description = "AnquanhuiyiledgerVO对象")
public class AnquanhuiyiledgerVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "会议主键")
	private String id;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "会议名称")
	private String huiyimingcheng;

	@ApiModelProperty(value = "会议类型")
	private String huiyixingshi;

	@ApiModelProperty(value = "会议日期")
	private String huiyikaishishijian;

	@ApiModelProperty(value = "主持人")
	private String zhuchiren;

	@ApiModelProperty(value = "参会人数")
	private String canhuirenshu;

	@ApiModelProperty(value = "会议纪要")
	private String huiyineirong;

	@ApiModelProperty(value = "姓名")
	private String aadApName;

	@ApiModelProperty(value = "签到时间")
	private String addTime;

	@ApiModelProperty(value = "人脸照片")
	private String addApHeadPortrait;

	@ApiModelProperty(value = "签名")
	private String addApAutograph;

	@ApiModelProperty(value = "时间")
	private String date;

	@ApiModelProperty(value = "年份")
	private String year;

	@ApiModelProperty(value = "月份")
	private String month;

	@ApiModelProperty(value = "企业Id")
	private String deptId;
}
