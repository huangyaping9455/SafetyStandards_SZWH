package org.springblade.anbiao.VehIccChange.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * sim卡小卡号修改记录表
 * </p>
 *
 * @author author
 * @since 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ts_veh_iccid_change_record")
@ApiModel(value="TsVehIccidChangeRecord对象", description="sim卡小卡号修改记录表")
public class TsVehIccidChangeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "t_id", type = IdType.AUTO)
    private Long tId;

    @ApiModelProperty(value = "车辆ID")
    @TableField("veh_id")
    private String vehId;

    @ApiModelProperty(value = "sim卡小卡号")
    @TableField("iccid")
    private String iccid;

    @ApiModelProperty(value = "创建人ID")
    @TableField("create_userid")
    private Integer createUserid;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private String createTime;

    @ApiModelProperty(value = "修改人ID")
    @TableField("update_userid")
    private Long updateUserid;

    @ApiModelProperty(value = "修改人")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    @TableField("udpate_time")
    private LocalDateTime udpateTime;


}
