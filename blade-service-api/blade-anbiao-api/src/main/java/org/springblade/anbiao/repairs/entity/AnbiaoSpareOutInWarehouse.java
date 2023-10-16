package org.springblade.anbiao.repairs.entity;

import java.math.BigDecimal;

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
 * 出入备件库审核
 * </p>
 *
 * @author author
 * @since 2023-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_spare_out_in_warehouse")
@ApiModel(value="AnbiaoSpareOutInWarehouse对象", description="出入备件库审核")
public class AnbiaoSpareOutInWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "soi_id", type = IdType.UUID)
    private String soiId;

    @ApiModelProperty(value = "单号")
    private String soiNo;

    @ApiModelProperty(value = "单据类型，对应数据字典")
    private Integer soiType = 1;

    @ApiModelProperty(value = "申请人ID")
    private Integer soiUserId;

    @ApiModelProperty(value = "申请人名称")
    private String soiUserName;

    @ApiModelProperty(value = "申请时间")
    private String soiDate;

    @ApiModelProperty(value = "审核状态，0：待审核，1：审核通过，2：审核拒绝")
    private Integer soiAuditStatus = 0;

    @ApiModelProperty(value = "审核意见")
    private String soiAuditOpinion;

    @ApiModelProperty(value = "审核时间")
    private String soiAuditDate;

    @ApiModelProperty(value = "备件编码")
    private String soiSpNo;

    @ApiModelProperty(value = "备件名称")
    private String soiSpName;

    @ApiModelProperty(value = "规格")
    private String soiSpSpecification;

    @ApiModelProperty(value = "型号")
    private String soiSpModel;

    @ApiModelProperty(value = "良品数量")
    private Integer soiSpGoodProductsNum = 0;

    @ApiModelProperty(value = "坏件数量")
    private Integer soiSpBadProductsNum = 0;

    @ApiModelProperty(value = "审核人ID")
    private String soiAuditUserId;

    @ApiModelProperty(value = "审核人名称")
    private String soiAuditUserName;

    @ApiModelProperty(value = "企业ID")
    private Integer soiDeptId;

    @ApiModelProperty(value = "删除标志，默认0,1：删除")
    private Integer soiDelete = 0;

    @ApiModelProperty(value = "创建时间")
    private String soiCreatetime;

    @ApiModelProperty(value = "创建人ID")
    private Integer soiCreateid;

    @ApiModelProperty(value = "创建人")
    private String soiCreatename;

    @ApiModelProperty(value = "更新时间")
    private String soiUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    private Integer soiUpdateid;

    @ApiModelProperty(value = "更新人")
    private String soiUpdatename;

    @ApiModelProperty(value = "备件品牌")
    private String soiBrand;

    @ApiModelProperty(value = "备件分类")
    private Integer soiClassify = 1;

    @ApiModelProperty(value = "单位，对应数据字典值（个、条、台、套）")
    private Integer soiUnit = 1;

    @ApiModelProperty(value = "成本价格")
    private Double soiCostPrice = 0.00;

	@ApiModelProperty(value = "仓库名称")
	private String soiWarehouse;

	@ApiModelProperty(value = "仓库ID")
	private String soiWarehouseId;

	@ApiModelProperty(value = "错误消息")
	@TableField(exist = false)
	private String msg;


}
