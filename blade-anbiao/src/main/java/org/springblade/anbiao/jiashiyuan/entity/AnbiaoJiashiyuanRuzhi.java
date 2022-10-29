package org.springblade.anbiao.jiashiyuan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 驾驶员入职登记表
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_jiashiyuan_ruzhi")
@ApiModel(value="AnbiaoJiashiyuanRuzhi对象", description="驾驶员入职登记表")
public class AnbiaoJiashiyuanRuzhi implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "入职信息主键")
    @TableId(value = "ajr_ids", type = IdType.AUTO)
    private String ajrIds;

    @ApiModelProperty(value = "驾驶员信息主键")
    @TableField("ajr_aj_ids")
    private String ajrAjIds;

    @ApiModelProperty(value = "姓名")
    @TableField("ajr_name")
    private String ajrName;

    @ApiModelProperty(value = "性别(0=女,1=男)")
    @TableField("ajr_sex")
    private String ajrSex;

    @ApiModelProperty(value = "民族")
    @TableField("ajr_nation")
    private String ajrNation;

    @ApiModelProperty(value = "籍贯")
    @TableField("ajr_native_place")
    private String ajrNativePlace;

    @ApiModelProperty(value = "出生年月")
    @TableField("ajr_birth")
    private LocalDate ajrBirth;

    @ApiModelProperty(value = "年龄")
    @TableField("ajr_age")
    private Integer ajrAge;

    @ApiModelProperty(value = "政治面貌")
    @TableField("ajr_political_outlook")
    private String ajrPoliticalOutlook;

    @ApiModelProperty(value = "学历")
    @TableField("ajr_education")
    private String ajrEducation;

    @ApiModelProperty(value = "毕业学校")
    @TableField("ajr_graduation_school")
    private String ajrGraduationSchool;

    @ApiModelProperty(value = "毕业日期")
    @TableField("ajr_graduation_date")
    private LocalDate ajrGraduationDate;

    @ApiModelProperty(value = "身份证号码")
    @TableField("ajr_id_number")
    private String ajrIdNumber;

    @ApiModelProperty(value = "家庭住址")
    @TableField("ajr_address")
    private String ajrAddress;

    @ApiModelProperty(value = "领取驾照日期")
    @TableField("ajr_receive_driving_license")
    private LocalDate ajrReceiveDrivingLicense;

    @ApiModelProperty(value = "准驾车型")
    @TableField("ajr_class")
    private String ajrClass;

    @ApiModelProperty(value = "驾驶年龄")
    @TableField("ajr_driving_experience")
    private Integer ajrDrivingExperience;

    @ApiModelProperty(value = "健康状况")
    @TableField("ajr_health_status")
    private String ajrHealthStatus;

    @ApiModelProperty(value = "工作经历")
    @TableField("ajr_work_experience")
    private String ajrWorkExperience;

    @ApiModelProperty(value = "0=否,1=是")
    @TableField("ajr_safe_driving_record1")
    private String ajrSafeDrivingRecord1;

    @ApiModelProperty(value = "0=否,1=是")
    @TableField("ajr_safe_driving_record2")
    private String ajrSafeDrivingRecord2;

    @ApiModelProperty(value = "0=否,1=是")
    @TableField("ajr_safe_driving_record3")
    private String ajrSafeDrivingRecord3;

    @ApiModelProperty(value = "体检结果")
    @TableField("ajr_physical_examination_results")
    private String ajrPhysicalExaminationResults;

    @ApiModelProperty(value = "本人照片")
    @TableField("ajr_head_portrait")
    private String ajrHeadPortrait;

    @ApiModelProperty(value = "诚信考核结果")
    @TableField("ajr_integrity_assessment_results")
    private String ajrIntegrityAssessmentResults;

    @ApiModelProperty(value = "驾驶证状态(0=待审批,1=审批通过,2=审批拒绝)")
    @TableField("ajr_status")
    private String ajrStatus;

    @ApiModelProperty(value = "审批状态(0=待审批,1=已审批)")
    @TableField("ajr_approver_status")
    private String ajrApproverStatus;

    @ApiModelProperty(value = "审批人姓名")
    @TableField("ajr_approver_name")
    private String ajrApproverName;

    @ApiModelProperty(value = "审批人签名")
    @TableField("ajr_approver_autograph")
    private String ajrApproverAutograph;

    @ApiModelProperty(value = "审批时间")
    @TableField("ajr_approver_time")
    private LocalDateTime ajrApproverTime;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    @TableField("ajr_delete")
    private String ajrDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("ajr_create_time")
    private LocalDateTime ajrCreateTime;

    @ApiModelProperty(value = "审批人主键")
    @TableField("ajr_approver_ids")
    private String ajrApproverIds;

    @ApiModelProperty(value = "创建人主键")
    @TableField("ajr_create_by_ids")
    private String ajrCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("ajr_create_by_name")
    private String ajrCreateByName;

    @ApiModelProperty(value = "更新时间")
    @TableField("ajr_update_time")
    private LocalDateTime ajrUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    @TableField("ajr_update_by_ids")
    private String ajrUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("ajr_update_by_name")
    private String ajrUpdateByName;


}
