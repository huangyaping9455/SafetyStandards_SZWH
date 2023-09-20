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
 * 备件仓库管理
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_spare_warehouse_info")
@ApiModel(value="AnbiaoSpareWarehouseInfo对象", description="备件仓库管理")
public class AnbiaoSpareWarehouseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "wa_id", type = IdType.UUID)
    private String waId;

    @ApiModelProperty(value = "仓库名称")
    private String waName;

    @ApiModelProperty(value = "仓库编码")
    private String waNo;

    @ApiModelProperty(value = "仓库地址")
    private String waAddress;

    @ApiModelProperty(value = "创建时间")
    private String waCreatetime;

    @ApiModelProperty(value = "创建人ID")
    private Integer waCreateid;

    @ApiModelProperty(value = "创建人")
    private String waCreatename;

    @ApiModelProperty(value = "更新时间")
    private String waUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    private Integer waUpdateid;

    @ApiModelProperty(value = "更新人")
    private String waUpdatename;

    @ApiModelProperty(value = "删除标志，默认0,1：删除")
    private Integer waDelete = 0;

    @ApiModelProperty(value = "企业ID")
    private Integer waDeptId;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String wadeptName;



}
