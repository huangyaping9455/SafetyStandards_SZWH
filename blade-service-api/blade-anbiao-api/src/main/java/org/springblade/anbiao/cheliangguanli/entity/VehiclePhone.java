package org.springblade.anbiao.cheliangguanli.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * VehiclePhone
 */
@Data
@TableName("anbiao_vehicle_phone")
@ApiModel(value = "VehiclePhone对象", description = "VehiclePhone对象")
public class VehiclePhone implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "驾驶员ID")
    private String vehid;
    @ApiModelProperty(value = "是否删除")
    @TableField("is_deleted")
    private Integer isdeleted;
    @ApiModelProperty(value = "创建时间")
    private String cratetime;
    @ApiModelProperty(value = "创建者ID")
    private Integer createuserid;
    @ApiModelProperty(value = "随车电话")
    private String accessoryphone;
	@ApiModelProperty(value = "企业ID")
	@TableField("deptid")
	private Integer deptId;

}
