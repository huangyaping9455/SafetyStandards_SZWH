package org.springblade.anbiao.yunyingshang.entity;

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
 * @author hyp
 * @since 2022-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ts_operator_info")
@ApiModel(value="TsOperatorInfo对象", description="运营商信息")
public class TsOperatorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "op_id", type = IdType.UUID)
    private String opId;

    @ApiModelProperty(value = "运营商名称")
    @TableField("op_name")
    private String opName;

    @ApiModelProperty(value = "接入码")
    @TableField("op_code")
    private String opCode;

    @ApiModelProperty(value = "登录用户")
    @TableField("op_loginname")
    private String opLoginname;

    @ApiModelProperty(value = "登录密码")
    @TableField("op_pwd")
    private String opPwd;

    @ApiModelProperty(value = "预留")
    @TableField("op_conncode")
    private String opConncode;

    @ApiModelProperty(value = "是否删除，默认0,1：删除")
    @TableField("is_deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建人ID")
    @TableField("create_id")
    private Integer createId;

    @ApiModelProperty(value = "创建人名称")
    @TableField("create_name")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty(value = "更新人ID")
    @TableField("update_id")
    private Integer updateId;

    @ApiModelProperty(value = "更新人名称")
    @TableField("update_name")
    private String updateName;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private String updateTime;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private Integer province;

    @ApiModelProperty(value = "市")
    @TableField("city")
    private Integer city;

    @ApiModelProperty(value = "区县")
    @TableField("country")
    private Integer country;

    @ApiModelProperty(value = "运管Id")
    @TableField("yunguanid")
    private Integer yunguanid;

	@ApiModelProperty(value = "省")
	@TableField(exist = false)
	private String provinceName;

	@ApiModelProperty(value = "市")
	@TableField(exist = false)
	private String cityName;

	@ApiModelProperty(value = "县")
	@TableField(exist = false)
	private String countryName;

	@ApiModelProperty(value = "运管名称")
	@TableField(exist = false)
	private String yunguanName;

	@ApiModelProperty(value = "是否加密，0：不加密，1：加密")
	@TableField("is_encrypted")
	private Integer isEncrypted;

	@ApiModelProperty(value = "加密IA1")
	@TableField("encrypted_ia")
	private String encryptedIa;

	@ApiModelProperty(value = "加密M1")
	@TableField("encrypted_m")
	private String encryptedM;

	@ApiModelProperty(value = "加密IC1")
	@TableField("encrypted_ic")
	private String encryptedIc;

	@ApiModelProperty(value = "企业ID")
	@TableField("dept_id")
	private Integer deptId;



}
