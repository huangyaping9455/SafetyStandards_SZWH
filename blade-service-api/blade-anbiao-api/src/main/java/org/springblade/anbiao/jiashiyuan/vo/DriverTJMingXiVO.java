package org.springblade.anbiao.jiashiyuan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.net.URL;

@Data
@ApiModel(value = "JiaShiYuanVO对象", description = "JiaShiYuanVO对象")
public class DriverTJMingXiVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "驾驶员ID")
	private String jiashiyuanId;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "本人照片")
	private String ajrHeadPortrait;

	@ApiModelProperty(value = "性别")
	private String xingbie;

	@ApiModelProperty(value = "民族")
	private String nation;

	@ApiModelProperty(value = "籍贯")
	private String nativePlace;

	@ApiModelProperty(value = "出生年月")
	private String birth;

	@ApiModelProperty(value = "年龄")
	private String age;

	@ApiModelProperty(value = "政治面貌")
	private String politicalOutlook;

	@ApiModelProperty(value = "学历")
	private String ajrEducation;

	@ApiModelProperty(value = "毕业学校")
	private String ajrGraduationSchool;

	@ApiModelProperty(value = "毕业日期")
	private String ajrGraduationDate;

	@ApiModelProperty(value = "身份证号码")
	private String ajrIdNumber;

	@ApiModelProperty(value = "家庭住址")
	private String ajrAddress;

	@ApiModelProperty(value = "领取驾照时间")
	private String ajrReceiveDrivingLicense;

	@ApiModelProperty(value = "驾照等级")
	private String ajrClass;

	@ApiModelProperty(value = "驾驶年龄")
	private String ajrDrivingExperience;

	@ApiModelProperty(value = "健康状况")
	private String ajrHealthStatus;

	@ApiModelProperty(value = "工作经历")
	private String ajrWorkExperience;

	private String ajrSafeDrivingRecord1;

	private String ajrSafeDrivingRecord2;

	private String ajrSafeDrivingRecord3;

	@ApiModelProperty(value = "体检结果")
	private String ajrPhysicalExaminationResults;

	@ApiModelProperty(value = "诚信考核结果")
	private String ajrIntegrityAssessmentResults;

	@ApiModelProperty(value = "身份证正面附件")
	private String shenfenzhengfujian;

	@ApiModelProperty(value = "身份证反面附件")
	private String shenfenzhengfanmianfujian;

	@ApiModelProperty(value = "驾驶证正面附件")
	private String ajjFrontPhotoAddress;

	@ApiModelProperty(value = "驾驶证反面附件")
	private String ajjAttachedPhotos;

	@ApiModelProperty(value = "从业资格证附件")
	private String ajcLicence;

	@ApiModelProperty(value = "体检附件")
	private String ajtEnclosure;

	@ApiModelProperty(value = "岗前培训附件")
	private String ajgTrainingEnclosure;

	@ApiModelProperty(value = "无责证明附件")
	private String ajwEnclosure;

	private URL AjrHeadPortraitUrl;

	@ApiModelProperty(value = "安全责任书签名附件")
	private String ajaAutographEnclosure;

	@ApiModelProperty(value = "安全责任书签字时间")
	private String ajaAutographTime;

	@ApiModelProperty(value = "危害告知书签名附件")
	private String ajwAutographEnclosure;

	@ApiModelProperty(value = "危害告知书签字时间")
	private String ajwAutographTime;

	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	@ApiModelProperty(value = "住址")
	private String jiatingzhuzhi;

	@ApiModelProperty(value = "手机号")
	private String shoujihaoma;

	@ApiModelProperty(value = "劳动合同开始时间")
	private String ajwStartDate;

	@ApiModelProperty(value = "劳动合同结束时间")
	private String ajwEndDate;

	@ApiModelProperty(value = "劳动合同签字时间")
	private String ajwAutographTime2;

	@ApiModelProperty(value = "劳动合同签字附件")
	private String ajwAutographEnclosure2;


}
