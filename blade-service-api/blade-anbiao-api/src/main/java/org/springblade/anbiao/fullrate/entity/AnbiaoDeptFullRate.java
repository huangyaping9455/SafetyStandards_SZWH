package org.springblade.anbiao.fullrate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2023-05-15
 */
@Data
@TableName("anbiao_dept_full_rate")
@ApiModel(value="AnbiaoDeptFullRate对象", description="AnbiaoDeptFullRate对象")
public class AnbiaoDeptFullRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "企业ID")
	@TableField("deptId")
    private Integer deptId;

    @ApiModelProperty(value = "车辆信息完整率")
	@TableField("vehFullRate")
    private String vehFullRate = "0.00";

    @ApiModelProperty(value = "驾驶员信息完整率")
	@TableField("jsyFullRate")
    private String jsyFullRate = "0.00";

    @ApiModelProperty(value = "业户信息完整率")
	@TableField("deptFullRate")
    private String deptFullRate = "0.00";

    @ApiModelProperty(value = "平均完整率")
	@TableField(exist = false)
    private String avgFullRate = "0.00";

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String deptName;

	@ApiModelProperty(value = "创建时间")
	@TableField("createTime")
	private String createTime;


}
