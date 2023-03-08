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
 * 驾驶员驾驶证信息
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_jiashiyuan_jiashizheng")
@ApiModel(value="AnbiaoJiashiyuanJiashizheng对象", description="驾驶员驾驶证信息")
public class AnbiaoJiashiyuanJiashizheng implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ajj_ids", type = IdType.UUID)
    private String ajjIds;

    @ApiModelProperty(value = "驾驶员信息表主键")
    @TableField("ajj_aj_ids")
    private String ajjAjIds;

    @ApiModelProperty(value = "初次领证日期")
    @TableField("ajj_first_issue")
    private String ajjFirstIssue;

    @ApiModelProperty(value = "准驾车型")
    @TableField("ajj_class")
    private String ajjClass;

    @ApiModelProperty(value = "有效期（起）")
    @TableField("ajj_valid_period_start")
    private String ajjValidPeriodStart;

    @ApiModelProperty(value = "有效期（止）")
    @TableField("ajj_valid_period_end")
    private String ajjValidPeriodEnd;

    @ApiModelProperty(value = "驾驶证正面照片")
    @TableField("ajj_front_photo_address")
    private String ajjFrontPhotoAddress;

    @ApiModelProperty(value = "发证机关")
    @TableField("ajj_issuing_authority")
    private String ajjIssuingAuthority;

    @ApiModelProperty(value = "档案编号")
    @TableField("ajj_file_no")
    private String ajjFileNo;

    @ApiModelProperty(value = "驾驶证记录信息")
    @TableField("ajj_record")
    private String ajjRecord;

    @ApiModelProperty(value = "驾驶证反面照片")
    @TableField("ajj_attached_photos")
    private String ajjAttachedPhotos;

    @ApiModelProperty(value = "下次年审日期")
    @TableField("ajj_next_annual_review")
    private String ajjNextAnnualReview;

    @ApiModelProperty(value = "年审附件")
    @TableField("ajj_annual_review_attachment")
    private String ajjAnnualReviewAttachment;

    @ApiModelProperty(value = "驾驶证状态(0=有效,1=无效)")
    @TableField("ajj_status")
    private String ajjStatus;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    @TableField("ajj_delete")
    private String ajjDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("ajj_create_time")
    private String ajjCreateTime;

    @ApiModelProperty(value = "创建人主键")
    @TableField("ajj_create_by_ids")
    private String ajjCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("ajj_create_by_name")
    private String ajjCreateByName;

    @ApiModelProperty(value = "更新时间")
    @TableField("ajj_update_time")
    private String ajjUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    @TableField("ajj_update_by_ids")
    private String ajjUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("ajj_update_by_name")
    private String ajjUpdateByName;

	@ApiModelProperty(value = "企业id")
	@TableField(exist = false)
	private String deptId;

}
