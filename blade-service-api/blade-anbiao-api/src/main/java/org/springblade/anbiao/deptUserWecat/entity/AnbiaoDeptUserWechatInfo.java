package org.springblade.anbiao.deptUserWecat.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2024-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_dept_user_wechat_info")
@ApiModel(value="AnbiaoDeptUserWechatInfo对象", description="微信公众号信息表")
public class AnbiaoDeptUserWechatInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "用户ID")
    @TableField("yh_id")
    private String yhId;

    @ApiModelProperty(value = "用户公众号openId")
    @TableField("yh_gzh_openid")
    private String yhGzhOpenid;

    @ApiModelProperty(value = "操作时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "是否删除，默认0,1：删除")
    @TableField("is_deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "是否绑定，默认0,1：已绑定")
    @TableField("status")
    private Integer status;

	@ApiModelProperty(value = "角色类型，1：驾驶员；2：两类人员")
	@TableField("type")
	private Integer type;

	@ApiModelProperty(value = "人员ID")
	@TableField(exist = false)
	private String perId;

	@ApiModelProperty(value = "人员名称")
	@TableField(exist = false)
	private String perName;

	@ApiModelProperty(value = "企业ID")
	@TableField(exist = false)
	private String deptId;


}
