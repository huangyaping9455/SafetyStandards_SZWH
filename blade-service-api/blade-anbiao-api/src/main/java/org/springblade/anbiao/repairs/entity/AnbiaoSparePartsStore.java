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
 * 备件库管理
 * </p>
 *
 * @author author
 * @since 2023-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_spare_parts_store")
@ApiModel(value="AnbiaoSparePartsStore对象", description="备件库管理")
public class AnbiaoSparePartsStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sp_id", type = IdType.UUID)
    private String spId;

    @ApiModelProperty(value = "备件编码")
    private String spNo;

    @ApiModelProperty(value = "备件名称")
    private String spName;

    @ApiModelProperty(value = "规格")
    private String spSpecification;

    @ApiModelProperty(value = "型号")
    private String spModel;

    @ApiModelProperty(value = "备件品牌")
    private String spBrand;

    @ApiModelProperty(value = "备件分类")
    private Integer spClassify = 1;

    @ApiModelProperty(value = "单位，对应数据字典值（个、条、台、套）")
    private Integer spUnit = 1;

    @ApiModelProperty(value = "成本价格")
    private Double spCostPrice = 0.00;

    @ApiModelProperty(value = "良品库存数量")
    private Integer spGoodProductsNum = 0;

    @ApiModelProperty(value = "领用备件总数量")
    private Integer spReceiveProductsNum = 0;

    @ApiModelProperty(value = "坏件库存数量")
    private Integer spBadProductsNum = 0;

    @ApiModelProperty(value = "仓库名称")
    private String spWarehouse;

    @ApiModelProperty(value = "仓库ID")
    private String spWarehouseId;

    @ApiModelProperty(value = "创建时间")
    private String spCreatetime;

    @ApiModelProperty(value = "创建人ID")
    private Integer spCreateid;

    @ApiModelProperty(value = "创建人")
    private String spCreatename;

    @ApiModelProperty(value = "更新时间")
    private String spUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    private Integer spUpdateid;

    @ApiModelProperty(value = "更新人")
    private String spUpdatename;

    @ApiModelProperty(value = "删除标志，默认0,1：删除")
    private Integer spDelete = 0;

    @ApiModelProperty(value = "备件附件")
    private String spImg;

    @ApiModelProperty(value = "企业ID")
    private Integer spDeptId;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String spdeptName;

}
