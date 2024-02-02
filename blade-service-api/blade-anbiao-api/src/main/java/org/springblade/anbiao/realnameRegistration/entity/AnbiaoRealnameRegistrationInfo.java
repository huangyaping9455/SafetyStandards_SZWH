package org.springblade.anbiao.realnameRegistration.entity;

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
 *
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_realname_registration_info")
@ApiModel(value="AnbiaoRealnameRegistrationInfo对象", description="乘客信息登记明细")
public class AnbiaoRealnameRegistrationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rnri_id", type = IdType.UUID)
    private String rnriId;

    @ApiModelProperty(value = "主表ID")
    @TableField("rnri_rnr_id")
    private String rnriRnrId;

    @ApiModelProperty(value = "乘客姓名")
    @TableField("rnri_name")
    private String rnriName;

    @ApiModelProperty(value = "身份证号码")
    @TableField("rnri_card_number")
    private String rnriCardNumber;

    @ApiModelProperty(value = "联系电话")
    @TableField("rnri_phone")
    private String rnriPhone;

    @ApiModelProperty(value = "乘车时间")
    @TableField("rnri_date")
    private String rnriDate;

    @ApiModelProperty(value = "乘车地点")
    @TableField("rnri_address")
    private String rnriAddress;

    @ApiModelProperty(value = "下车时间")
    @TableField("rnri_end_date")
    private String rnriEndDate;

    @ApiModelProperty(value = "下车地点")
    @TableField("rnri_end_address")
    private String rnriEndAddress;

    @ApiModelProperty(value = "创建时间")
    @TableField("rnri_createtime")
    private String rnriCreatetime;

    @ApiModelProperty(value = "创建人ID")
    @TableField("rnri_createid")
    private Integer rnriCreateid;

    @ApiModelProperty(value = "创建人")
    @TableField("rnri_createname")
    private String rnriCreatename;

    @ApiModelProperty(value = "更新时间")
    @TableField("rnri_updatetime")
    private String rnriUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    @TableField("rnri_updateid")
    private Integer rnriUpdateid;

    @ApiModelProperty(value = "更新人")
    @TableField("rnri_updatename")
    private String rnriUpdatename;

    @ApiModelProperty(value = "删除标志，默认0,1：已删除")
    @TableField("rnri_delete")
    private Integer rnriDelete = 0;


}
