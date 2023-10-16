package org.springblade.anbiao.repairs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @author author
 * @since 2023-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_repairs_spare_info")
@ApiModel(value="AnbiaoRepairsSpareInfo对象", description="维修设备使用记录表")
public class AnbiaoRepairsSpareInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rpps_id", type = IdType.UUID)
    private String rppsId;

    @ApiModelProperty(value = "报修单ID")
    private String rppsRpId;

    @ApiModelProperty(value = "员工备件单号")
    private String rppsSppNo;

    @ApiModelProperty(value = "备件名称")
    private String rppsSppName;

    @ApiModelProperty(value = "数量")
    private Integer rppsNum = 0;

    @ApiModelProperty(value = "创建时间")
    private String rppsCreatetime;

    @ApiModelProperty(value = "创建人ID")
    private String rppsCreateid;

    @ApiModelProperty(value = "创建人")
    private String rppsCreatename;

    @ApiModelProperty(value = "删除标志，默认0,1：删除")
    private Integer rppsDelete = 0;

    @ApiModelProperty(value = "备件编码")
    private String rppsSpNo;

	@ApiModelProperty(value = "报修单状态")
	private Integer rppsStatus = 0;

	@ApiModelProperty(value = "数据状态，1：通过，2：驳回")
	private Integer rppsType = 0;
}
