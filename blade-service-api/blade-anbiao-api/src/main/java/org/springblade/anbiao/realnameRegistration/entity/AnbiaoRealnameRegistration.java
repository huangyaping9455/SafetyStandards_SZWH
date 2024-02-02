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
import java.util.List;

/**
 * <p>
 * 乘客信息登记表
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_realname_registration")
@ApiModel(value="AnbiaoRealnameRegistration对象", description="乘客信息登记表")
public class AnbiaoRealnameRegistration implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rnr_id", type = IdType.UUID)
    private String rnrId;

    @ApiModelProperty(value = "车辆ID")
    @TableField("rnr_veh_id")
    private String rnrVehId;

    @ApiModelProperty(value = "企业ID")
    @TableField("rnr_dept_id")
    private Integer rnrDeptId;

    @ApiModelProperty(value = "驾驶员ID")
    @TableField("rnr_jsy_id")
    private String rnrJsyId;

    @ApiModelProperty(value = "驾驶员姓名")
    @TableField("rnr_jsy_name")
    private String rnrJsyName;

    @ApiModelProperty(value = "联系电话")
    @TableField("rnr_phone")
    private String rnrPhone;

    @ApiModelProperty(value = "出发地")
    @TableField("rnr_begin_address")
    private String rnrBeginAddress;

    @ApiModelProperty(value = "出发时间")
    @TableField("rnr_begin_time")
    private String rnrBeginTime;

    @ApiModelProperty(value = "目的地")
    @TableField("rnr_end_address")
    private String rnrEndAddress;

    @ApiModelProperty(value = "到达目的地时间")
    @TableField("rnr_end_time")
    private String rnrEndTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("rnr_createtime")
    private String rnrCreatetime;

    @ApiModelProperty(value = "创建人ID")
    @TableField("rnr_createid")
    private Integer rnrCreateid;

    @ApiModelProperty(value = "创建人")
    @TableField("rnr_createname")
    private String rnrCreatename;

    @ApiModelProperty(value = "更新时间")
    @TableField("rnr_updatetime")
    private String rnrUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    @TableField("rnr_updateid")
    private Integer rnrUpdateid;

    @ApiModelProperty(value = "更新人")
    @TableField("rnr_updatename")
    private String rnrUpdatename;

    @ApiModelProperty(value = "删除标志，默认0,1：已删除")
    @TableField("rnr_delete")
    private Integer rnrDelete = 0;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String deptName;

	@ApiModelProperty(value = "车辆牌照")
	@TableField(exist = false)
	private String cheliangpaizhao;

	@ApiModelProperty(value = "驾驶员姓名")
	@TableField(exist = false)
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "乘客信息登记明细")
	@TableField(exist = false)
	private List<AnbiaoRealnameRegistrationInfo> realnameRegistrationInfoList;


}
