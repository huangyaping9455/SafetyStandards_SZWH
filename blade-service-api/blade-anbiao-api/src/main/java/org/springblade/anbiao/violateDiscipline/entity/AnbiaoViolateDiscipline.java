package org.springblade.anbiao.violateDiscipline.entity;

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
 * 违规违纪材料上传登记
 * </p>
 *
 * @author author
 * @since 2023-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_violate_discipline")
@ApiModel(value="AnbiaoViolateDiscipline对象", description="违规违纪材料上传登记")
public class AnbiaoViolateDiscipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vd_id", type = IdType.UUID)
    private String vdId;

    @ApiModelProperty(value = "驾驶员ID")
    @TableField("vd_jsy_id")
    private String vdJsyId;

    @ApiModelProperty(value = "企业ID")
    @TableField("vd_dept_id")
    private Integer vdDeptId;

    @ApiModelProperty(value = "最新上传日期")
    @TableField("vd_date")
    private String vdDate;

    @ApiModelProperty(value = "附件")
    @TableField("vd_fujian")
    private String vdFujian;

    @ApiModelProperty(value = "创建时间")
    @TableField("vd_createtime")
    private String vdCreatetime;

    @ApiModelProperty(value = "创建人ID")
    @TableField("vd_createid")
    private Integer vdCreateid;

    @ApiModelProperty(value = "创建人")
    @TableField("vd_createname")
    private String vdCreatename;

    @ApiModelProperty(value = "更新时间")
    @TableField("vd_updatetime")
    private String vdUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    @TableField("vd_updateid")
    private Integer vdUpdateid;

    @ApiModelProperty(value = "更新人")
    @TableField("vd_updatename")
    private String vdUpdatename;

    @ApiModelProperty(value = "删除标志，默认0,1：已删除")
    @TableField("vd_delete")
    private Integer vdDelete = 0;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String deptName;

	@ApiModelProperty(value = "上传状态")
	@TableField(exist = false)
	private String scstatus;

	@ApiModelProperty(value = "驾驶员姓名")
	@TableField(exist = false)
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "上传人数")
	@TableField(exist = false)
	private Integer scnum = 0;

	@ApiModelProperty(value = "未上传人数")
	@TableField(exist = false)
	private Integer wscnum = 0;

}
