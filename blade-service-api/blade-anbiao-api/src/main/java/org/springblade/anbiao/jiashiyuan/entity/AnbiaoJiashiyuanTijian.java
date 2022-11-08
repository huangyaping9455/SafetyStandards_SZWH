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
 * 驾驶员体检信息表
 * </p>
 *
 * @author hyp
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_jiashiyuan_tijian")
@ApiModel(value="AnbiaoJiashiyuanTijian对象", description="驾驶员体检信息表")
public class AnbiaoJiashiyuanTijian implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "体检主键")
    @TableId(value = "ajt_ids", type = IdType.UUID)
    private String ajtIds;

    @ApiModelProperty(value = "驾驶员信息表主键")
    @TableField("ajt_aj_ids")
    private String ajtAjIds;

    @ApiModelProperty(value = "体检日期")
    @TableField("ajt_physical_examination_date")
    private String ajtPhysicalExaminationDate;

    @ApiModelProperty(value = "体检有效期")
    @TableField("ajt_term_validity")
    private String ajtTermValidity;

    @ApiModelProperty(value = "体检附件")
    @TableField("ajt_enclosure")
    private String ajtEnclosure;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    @TableField("ajt_delete")
    private String ajtDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("ajt_create_time")
    private String ajtCreateTime;

    @ApiModelProperty(value = "创建人主键")
    @TableField("ajt_create_by_ids")
    private String ajtCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("ajt_create_by_name")
    private String ajtCreateByName;

    @ApiModelProperty(value = "更新时间")
    @TableField("ajt_update_time")
    private String ajtUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    @TableField("ajt_update_by_ids")
    private String ajtUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("ajt_update_by_name")
    private String ajtUpdateByName;


}
