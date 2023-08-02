package org.springblade.anbiao.jiashiyuan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 驾驶员从业资格证信息
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_jiashiyuan_congyezigezheng")
@ApiModel(value="AnbiaoJiashiyuanCongyezigezheng对象", description="驾驶员从业资格证信息")
public class AnbiaoJiashiyuanCongyezigezheng implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ajc_ids", type = IdType.UUID)
    private String ajcIds;

    @ApiModelProperty(value = "驾驶员信息表主键")
    @TableField("ajc_aj_ids")
    private String ajcAjIds;

    @ApiModelProperty(value = "从业资格证件号")
    @TableField("ajc_certificate_no")
    private String ajcCertificateNo;

    @ApiModelProperty(value = "从业资格类别")
    @TableField("ajc_category")
    private String ajcCategory;

    @ApiModelProperty(value = "初次发证时间")
    @TableField("ajc_initial_issuing")
    private String ajcInitialIssuing;

    @ApiModelProperty(value = "发证日期")
    @TableField("ajc_issue_date")
    private String ajcIssueDate;

    @ApiModelProperty(value = "有效期至")
    @TableField("ajc_valid_until")
    private String ajcValidUntil;

    @ApiModelProperty(value = "发证机关")
    @TableField("ajc_issuing_authority")
    private String ajcIssuingAuthority;

    @ApiModelProperty(value = "从业资格证照片")
    @TableField("ajc_licence")
    private String ajcLicence;

    @ApiModelProperty(value = "下次年审日期")
    @TableField("ajc_next_annual_review")
    private String ajcNextAnnualReview;

    @ApiModelProperty(value = "年审附件")
    @TableField("ajc_annual_review_attachment")
    private String ajcAnnualReviewAttachment;

    @ApiModelProperty(value = "驾驶证状态(0=有效,1=无效)")
    @TableField("ajc_status")
    private String ajcStatus;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    @TableField("ajc_delete")
    private String ajcDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("ajc_create_time")
    private String ajcCreateTime;

    @ApiModelProperty(value = "创建人主键")
    @TableField("ajc_create_by_ids")
    private String ajcCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("ajc_create_by_name")
    private String ajcCreateByName;

    @ApiModelProperty(value = "更新时间")
    @TableField("ajc_update_time")
    private String ajcUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    @TableField("ajc_update_by_ids")
    private String ajcUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("ajc_update_by_name")
    private String ajcUpdateByName;

	@ApiModelProperty(value = "企业id")
	@TableField(exist = false)
	private String deptId;

	@ApiModelProperty(value = "从业人员类型")
	@TableField(exist = false)
	private String congyerenyuanleixing;
}
