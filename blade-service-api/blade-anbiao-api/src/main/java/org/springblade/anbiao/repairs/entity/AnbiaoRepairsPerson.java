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
 *
 * </p>
 *
 * @author author
 * @since 2023-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_repairs_person")
@ApiModel(value="AnbiaoRepairsPerson对象", description="维修人员信息")
public class AnbiaoRepairsPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rp_id", type = IdType.UUID)
    private String rpId;

    @ApiModelProperty(value = "登录账号")
    private String rpAccount;

    @ApiModelProperty(value = "登录密码")
    private String rpPassword;

    @ApiModelProperty(value = "所属企业ID")
    private Integer rpDeptid;

    @ApiModelProperty(value = "联系人")
    private String rpName;

    @ApiModelProperty(value = "联系电话")
    private String rpPhone;

    @ApiModelProperty(value = "人员类型，1:维修人员")
    private Integer rpType;

    @ApiModelProperty(value = "创建时间")
    private String rpCreatetime;

    @ApiModelProperty(value = "创建人ID")
    private Integer rpCreateid;

    @ApiModelProperty(value = "创建人")
    private String rpCreatename;

    @ApiModelProperty(value = "更新时间")
    private String rpUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    private Integer rpUpdateid;

    @ApiModelProperty(value = "更新人")
    private String rpUpdatename;

    @ApiModelProperty(value = "是否删除，默认0,1：删除")
    private Integer rpDelete;

	@ApiModelProperty(value = "小程序openID")
	private String rpOpenid;

	@ApiModelProperty(value = "公众号openID")
	private String rpGzhOpenid;

	@ApiModelProperty(value = "所属企业名称")
	@TableField(exist = false)
	private String rpDeptName;


}
