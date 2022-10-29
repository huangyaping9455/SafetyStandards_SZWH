package org.springblade.anbiao.jiashiyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 驾驶员诚信考核记录
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_jiashiyuan_chengxinkaohe")
@ApiModel(value="AnbiaoJiashiyuanChengxinkaohe对象", description="驾驶员诚信考核记录")
public class AnbiaoJiashiyuanChengxinkaohe implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ajc_ids", type = IdType.AUTO)
    private String ajcIds;

    @ApiModelProperty(value = "驾驶员信息表主键")
    @TableField("ajc_aj_ids")
    private String ajcAjIds;

    @ApiModelProperty(value = "从业资格类别")
    @TableField("ajc_category")
    private String ajcCategory;

    @ApiModelProperty(value = "年度")
    @TableField("ajc_year")
    private Integer ajcYear;

    @ApiModelProperty(value = "考核结果")
    @TableField("ajc_appraisal_results")
    private String ajcAppraisalResults;

    @ApiModelProperty(value = "诚信考核附件")
    @TableField("ajc_enclosure")
    private String ajcEnclosure;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    @TableField("ajc_delete")
    private String ajcDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("ajc_create_time")
    private LocalDateTime ajcCreateTime;

    @ApiModelProperty(value = "创建人主键")
    @TableField("ajc_create_by_ids")
    private String ajcCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("ajc_create_by_name")
    private String ajcCreateByName;

    @ApiModelProperty(value = "更新时间")
    @TableField("ajc_update_time")
    private LocalDateTime ajcUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    @TableField("ajc_update_by_ids")
    private String ajcUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("ajc_update_by_name")
    private String ajcUpdateByName;


}
