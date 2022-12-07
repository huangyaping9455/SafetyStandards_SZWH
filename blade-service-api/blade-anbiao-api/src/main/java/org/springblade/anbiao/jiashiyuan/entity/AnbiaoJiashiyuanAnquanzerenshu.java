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
 * 驾驶员安全责任书
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_jiashiyuan_anquanzerenshu")
@ApiModel(value="AnbiaoJiashiyuanAnquanzerenshu对象", description="驾驶员安全责任书")
public class AnbiaoJiashiyuanAnquanzerenshu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "无责证明主键")
    @TableId(value = "aja_ids", type = IdType.UUID)
    private String ajaIds;

    @ApiModelProperty(value = "驾驶员信息主键")
    @TableField("aja_aj_ids")
    private String ajaAjIds;

    @ApiModelProperty(value = "安全责任书附件")
    @TableField("aja_enclosure")
    private String ajaEnclosure;

    @ApiModelProperty(value = "安全责任书签名附件")
    @TableField("aja_autograph_enclosure")
    private String ajaAutographEnclosure;

    @ApiModelProperty(value = "签名状态(0=待签名,1=已签名)")
    @TableField("aja_autograph_status")
    private String ajaAutographStatus;

    @ApiModelProperty(value = "签字人主键")
    @TableField("aja_autograph_ids")
    private String ajaAutographIds;

    @ApiModelProperty(value = "签字人姓名")
    @TableField("aja_autograph_name")
    private String ajaAutographName;

    @ApiModelProperty(value = "签字时间")
    @TableField("aja_autograph_time")
    private String ajaAutographTime;

    @ApiModelProperty(value = "签字人签名")
    @TableField("aja_autograph_autograph")
    private String ajaAutographAutograph;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    @TableField("aja_delete")
    private String ajaDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("aja_create_time")
    private String ajaCreateTime;

    @ApiModelProperty(value = "创建人主键")
    @TableField("aja_create_by_ids")
    private String ajaCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("aja_create_by_name")
    private String ajaCreateByName;

    @ApiModelProperty(value = "更新时间")
    @TableField("aja_update_time")
    private String ajaUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    @TableField("aja_update_by_ids")
    private String ajaUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("aja_update_by_name")
    private String ajaUpdateByName;

	@ApiModelProperty(value = "起始日期")
	@TableField("aja_start_date")
	private String ajaStartDate;

}
