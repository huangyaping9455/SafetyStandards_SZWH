package org.springblade.anbiao.driverMoveInfo.entity;

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
 * @since 2024-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_jiashiyuan_move_info")
@ApiModel(value="AnbiaoJiashiyuanMoveInfo对象", description="")
public class AnbiaoJiashiyuanMoveInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "驾驶员ID")
    @TableField("jsyId")
    private String jsyId;

    @ApiModelProperty(value = "迁出企业ID")
    @TableField("outOfDeptId")
    private Integer outOfDeptId;

    @ApiModelProperty(value = "迁入企业ID")
    @TableField("inOfDeptId")
    private Integer inOfDeptId;

    @ApiModelProperty(value = "变更状态，0：在职，1：删除，2：离职，3：请假，4：机动，5：调离，6：迁出")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "操作时间")
    @TableField("updateTime")
    private String updateTime;

    @ApiModelProperty(value = "操作人ID")
    @TableField("updateUserId")
    private String updateUserId;

    @ApiModelProperty(value = "操作人")
    @TableField("updateUser")
    private String updateUser;

    @ApiModelProperty(value = "附件")
    @TableField("fuJian")
    private String fuJian;

    @ApiModelProperty(value = "描述")
    @TableField("remark")
    private String remark;


}
