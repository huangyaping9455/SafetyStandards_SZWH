package org.springblade.anbiao.repairs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 人员备件审核
 * </p>
 *
 * @author author
 * @since 2023-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_spare_person_apply_for_audit")
@ApiModel(value="AnbiaoSparePersonApplyForAudit对象", description="人员备件审核")
public class AnbiaoSparePersonApplyForAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sp_id", type = IdType.UUID)
    private String spId;

    @ApiModelProperty(value = "申请人员ID")
    @TableField("sp_person_id")
    private String spPersonId;

    @ApiModelProperty(value = "申请人员名称")
    @TableField("sp_person_name")
    private String spPersonName;

    @ApiModelProperty(value = "申请时间")
    @TableField("sp_date")
    private String spDate;

    @ApiModelProperty(value = "单号")
    @TableField("sp_no")
    private String spNo;

    @ApiModelProperty(value = "1：员工归还，2：员工申请")
    @TableField("sp_type")
    private Integer spType;

    @ApiModelProperty(value = "备件编码")
    @TableField("soi_sp_no")
    private String soiSpNo;

    @ApiModelProperty(value = "申请数量")
    @TableField("sp_num")
    private Integer spNum = 0;

    @ApiModelProperty(value = "备注")
    @TableField("sp_remark")
    private String spRemark;

    @ApiModelProperty(value = "审核状态，0：待审核，1：审核通过，2：审核拒绝")
    @TableField("sp_audit_status")
    private Integer spAuditStatus = 0;

    @ApiModelProperty(value = "审核意见")
    @TableField("sp_audit_opinion")
    private String spAuditOpinion;

    @ApiModelProperty(value = "审核时间")
    @TableField("sp_audit_date")
    private String spAuditDate;

    @ApiModelProperty(value = "审核人ID")
    @TableField("sp_audit_user_id")
    private String spAuditUserId;

    @ApiModelProperty(value = "审核人名称")
    @TableField("sp_audit_user_name")
    private String spAuditUserName;

    @ApiModelProperty(value = "创建时间")
    @TableField("sp_createtime")
    private String spCreatetime;

    @ApiModelProperty(value = "创建人ID")
    @TableField("sp_createid")
    private Integer spCreateid;

    @ApiModelProperty(value = "创建人")
    @TableField("sp_createname")
    private String spCreatename;

    @ApiModelProperty(value = "更新时间")
    @TableField("sp_updatetime")
    private String spUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    @TableField("sp_updateid")
    private Integer spUpdateid;

    @ApiModelProperty(value = "更新人")
    @TableField("sp_updatename")
    private String spUpdatename;

    @ApiModelProperty(value = "删除标志，默认0,1：删除")
    @TableField("sp_delete")
    private Integer spDelete = 0;

    @ApiModelProperty(value = "企业ID")
    @TableField("sp_dept_id")
    private Integer spDeptId;

	@ApiModelProperty(value = "所属企业")
	@TableField(exist = false)
	private String rpDeptName;

	@ApiModelProperty(value = "备件名称")
	@TableField(exist = false)
	private String spName;
}
