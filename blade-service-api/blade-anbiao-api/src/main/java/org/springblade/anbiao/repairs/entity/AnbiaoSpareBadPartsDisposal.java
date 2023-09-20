package org.springblade.anbiao.repairs.entity;

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
 * 坏件处理记录
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_spare_bad_parts_disposal")
@ApiModel(value="AnbiaoSpareBadPartsDisposal对象", description="坏件处理记录")
public class AnbiaoSpareBadPartsDisposal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sbp_id", type = IdType.UUID)
    private String sbpId;

	@ApiModelProperty(value = "单号")
	private String sbpNo;

    @ApiModelProperty(value = "处理个数")
    private Integer sbpNum;

    @ApiModelProperty(value = "处理方式，1：返厂，2：报废")
    private Integer sbpWay;

    @ApiModelProperty(value = "处理时间")
    private String sbpDate;

    @ApiModelProperty(value = "创建时间")
    private String sbpCreatetime;

    @ApiModelProperty(value = "创建人ID")
    private Integer sbpCreateid;

    @ApiModelProperty(value = "创建人")
    private String sbpCreatename;

    @ApiModelProperty(value = "更新时间")
    private String sbpUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    private Integer sbpUpdateid;

    @ApiModelProperty(value = "更新人")
    private String sbpUpdatename;

    @ApiModelProperty(value = "删除标志，默认0,1：删除")
    private Integer sbpDelete = 0;

    @ApiModelProperty(value = "企业ID")
    private Integer sbpDeptId;

	@ApiModelProperty(value = "备件编码")
	private String sbpSpNo;
}
