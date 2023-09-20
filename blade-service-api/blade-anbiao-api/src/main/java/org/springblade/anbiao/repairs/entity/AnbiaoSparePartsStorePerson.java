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
 * 人员备件库管理
 * </p>
 *
 * @author author
 * @since 2023-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_spare_parts_store_person")
@ApiModel(value="AnbiaoSparePartsStorePerson对象", description="人员备件库管理")
public class AnbiaoSparePartsStorePerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "spp_id", type = IdType.UUID)
    private String sppId;

    @ApiModelProperty(value = "单号")
    @TableField("spp_no")
    private String sppNo;

    @ApiModelProperty(value = "备件名称")
    @TableField("spp_name")
    private String sppName;

    @ApiModelProperty(value = "规格")
    @TableField("spp_specification")
    private String sppSpecification;

    @ApiModelProperty(value = "型号")
    @TableField("spp_model")
    private String sppModel;

    @ApiModelProperty(value = "备件品牌")
    @TableField("spp_brand")
    private String sppBrand;

    @ApiModelProperty(value = "备件分类")
    @TableField("spp_classify")
    private Integer sppClassify;

    @ApiModelProperty(value = "单位，对应数据字典值（个、条、台、套）")
    @TableField("spp_unit")
    private Integer sppUnit;

    @ApiModelProperty(value = "良品库存数量")
    @TableField("spp_good_products_num")
    private Integer sppGoodProductsNum;

    @ApiModelProperty(value = "坏件库存数量")
    @TableField("spp_bad_products_num")
    private Integer sppBadProductsNum;

    @ApiModelProperty(value = "创建时间")
    @TableField("spp_createtime")
    private String sppCreatetime;

    @ApiModelProperty(value = "创建人ID")
    @TableField("spp_createid")
    private Integer sppCreateid;

    @ApiModelProperty(value = "创建人")
    @TableField("spp_createname")
    private String sppCreatename;

    @ApiModelProperty(value = "更新时间")
    @TableField("spp_updatetime")
    private String sppUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    @TableField("spp_updateid")
    private Integer sppUpdateid;

    @ApiModelProperty(value = "更新人")
    @TableField("spp_updatename")
    private String sppUpdatename;

    @ApiModelProperty(value = "删除标志，默认0,1：删除")
    @TableField("spp_delete")
    private Integer sppDelete = 0;

    @ApiModelProperty(value = "企业ID")
    @TableField("spp_dept_id")
    private Integer sppDeptId;

    @ApiModelProperty(value = "人员ID")
    @TableField("spp_personid")
    private String sppPersonid;

    @ApiModelProperty(value = "人员姓名")
    @TableField("spp_personname")
    private String sppPersonname;

    @ApiModelProperty(value = "增加时间")
    @TableField("spp_date")
    private String sppDate;

    @ApiModelProperty(value = "备件编码")
    @TableField("spp_sp_no")
    private String sppSpNo;


}
