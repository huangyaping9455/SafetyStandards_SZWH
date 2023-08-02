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
@TableName("anbiao_repairs_dept")
@ApiModel(value="AnbiaoRepairsDept对象", description="企业维修单位绑定关系表")
public class AnbiaoRepairsDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rp_id", type = IdType.UUID)
    private String rpId;

    @ApiModelProperty(value = "所属企业ID")
    private Integer rpDeptId;

    @ApiModelProperty(value = "维修单位ID")
    private Integer rpRepDeptId;

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

	@ApiModelProperty(value = "所属企业ID")
	@TableField(exist = false)
	private String rpDeptIds;

	@ApiModelProperty(value = "所属企业名称")
	@TableField(exist = false)
	private String rpDeptName;

	@ApiModelProperty(value = "维修单位名称")
	@TableField(exist = false)
	private String rpRepDeptName;

	@ApiModelProperty(value = "报修企业ID")
	@TableField(exist = false)
	private String deptId;

	@ApiModelProperty(value = "报修企业名称")
	@TableField(exist = false)
	private String deptName;


}
