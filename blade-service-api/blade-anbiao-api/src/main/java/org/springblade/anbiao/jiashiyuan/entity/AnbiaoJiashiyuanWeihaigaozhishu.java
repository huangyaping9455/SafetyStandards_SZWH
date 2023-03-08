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
 * 驾驶员危害告知书
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_jiashiyuan_weihaigaozhishu")
@ApiModel(value="AnbiaoJiashiyuanWeihaigaozhishu对象", description="驾驶员危害告知书")
public class AnbiaoJiashiyuanWeihaigaozhishu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "危害告知书主键")
    @TableId(value = "ajw_ids", type = IdType.UUID)
    private String ajwIds;

    @ApiModelProperty(value = "驾驶员信息主键")
    @TableField("ajw_aj_ids")
    private String ajwAjIds;

    @ApiModelProperty(value = "无责证明附件")
    @TableField("ajw_enclosure")
    private String ajwEnclosure;

    @ApiModelProperty(value = "无责证明签名附件")
    @TableField("ajw_autograph_enclosure")
    private String ajwAutographEnclosure;

    @ApiModelProperty(value = "签名状态(0=待签名,1=已签名)")
    @TableField("ajw_autograph_status")
    private String ajwAutographStatus;

    @ApiModelProperty(value = "签字人主键")
    @TableField("ajw_autograph_ids")
    private String ajwAutographIds;

    @ApiModelProperty(value = "签字人姓名")
    @TableField("ajw_autograph_name")
    private String ajwAutographName;

    @ApiModelProperty(value = "签字时间")
    @TableField("ajw_autograph_time")
    private String ajwAutographTime;

    @ApiModelProperty(value = "签字人签名")
    @TableField("ajw_autograph_autograph")
    private String ajwAutographAutograph;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    @TableField("ajw_delete")
    private String ajwDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("ajw_create_time")
    private String ajwCreateTime;

    @ApiModelProperty(value = "创建人主键")
    @TableField("ajw_create_by_ids")
    private String ajwCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("ajw_create_by_name")
    private String ajwCreateByName;

    @ApiModelProperty(value = "更新时间")
    @TableField("ajw_update_time")
    private String ajwUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    @TableField("ajw_update_by_ids")
    private String ajwUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("ajw_update_by_name")
    private String ajwUpdateByName;

	@ApiModelProperty(value = "起始日期")
	@TableField("ajw_start_date")
	private String ajwStartDate;

	@ApiModelProperty(value = "企业id")
	@TableField(exist = false)
	private String deptId;
}
