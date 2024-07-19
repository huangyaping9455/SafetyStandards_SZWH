package org.springblade.anbiao.risk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @since 2024-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_risk_dept_configuration_plan")
@ApiModel(value="AnbiaoRiskDeptConfigurationPlan对象", description="")
public class AnbiaoRiskDeptConfigurationPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "企业风险配置表id")
    @TableId("rdc_id")
    private String rdcId;

    @ApiModelProperty(value = "状态；0：未启用，1：启用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "更新时间")
    @TableField("updatetime")
    private String updatetime;

    @ApiModelProperty(value = "操作人")
    @TableField("caozuoren")
    private String caozuoren;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private String createtime;

    @ApiModelProperty(value = "创建人")
    @TableField("chuangjianren")
    private String chuangjianren;

    @ApiModelProperty(value = "风险推送频率；1：按日，2：按月")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "日期，每月的几号（按月才有），多个以英文逗号隔开")
    @TableField("date")
    private String date;

    @ApiModelProperty(value = "推送等级，1,2,3")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "驾驶员ID")
    @TableField("driver_id")
    private String driverId;

	@ApiModelProperty(value = "是否删除，0默认正常")
	@TableField("is_deleted")
	private Integer isDeleted = 0;

	@ApiModelProperty(value = "提醒日期")
	@TableField("days")
	private Integer days;

	@ApiModelProperty(value = "小时，每日几点")
	@TableField("hours")
	private String hours;

	@TableField(exist = false)
	private String userName;

}
