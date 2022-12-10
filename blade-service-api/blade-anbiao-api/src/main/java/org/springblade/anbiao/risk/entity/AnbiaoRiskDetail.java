package org.springblade.anbiao.risk.entity;

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
import java.time.LocalDateTime;

/**
 * <p>
 * 安标风险统计信息
 * </p>
 *
 * @author hyp
 * @since 2022-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_risk_detail")
@ApiModel(value="AnbiaoRiskDetail对象", description="安标风险统计信息")
public class AnbiaoRiskDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ard_ids", type = IdType.AUTO)
    private Long ardIds;

    @ApiModelProperty(value = "企业主键")
    @TableField("ard_dept_ids")
    private String ardDeptIds;

    @ApiModelProperty(value = "风险大类(0=资质类;1.制度类,2=台账类)")
    @TableField("ard_major_categories")
    private String ardMajorCategories;

    @ApiModelProperty(value = "风险小类(000=企业资质,001=车辆资质,002=人员资质,003=驾驶员资质,004=保险资质;100=制度类,101=责任书,102=危险告知书,103=入职信息,104=劳动合同)")
    @TableField("ard_sub_category")
    private String ardSubCategory;

    @ApiModelProperty(value = "风险标题")
    @TableField("ard_title")
    private String ardTitle;

    @ApiModelProperty(value = "风险内容")
    @TableField("ard_content")
    private String ardContent;

    @ApiModelProperty(value = "风险类型")
    @TableField("ard_type")
    private String ardType;

    @ApiModelProperty(value = "发现日期")
    @TableField("ard_discovery_date")
    private String ardDiscoveryDate;

    @ApiModelProperty(value = "是否整改(0=未整改,1=已整改)")
    @TableField("ard_is_rectification")
    private String ardIsRectification;

    @ApiModelProperty(value = "关联表")
    @TableField("ard_association_table")
    private String ardAssociationTable;

    @ApiModelProperty(value = "关联字段")
    @TableField("ard_association_field")
    private String ardAssociationField;

    @ApiModelProperty(value = "关联值")
    @TableField("ard_association_value")
    private String ardAssociationValue;

    @ApiModelProperty(value = "整改人主键")
    @TableField("ard_rectification_by_ids")
    private String ardRectificationByIds;

    @ApiModelProperty(value = "整改人姓名")
    @TableField("ard_rectification_by_name")
    private String ardRectificationByName;

    @ApiModelProperty(value = "整改时间")
    @TableField("ard_rectification_date")
    private String ardRectificationDate;

    @ApiModelProperty(value = "模块名称")
    @TableField("ard_modular_name")
    private String ardModularName;

    @ApiModelProperty(value = "整改字段名称")
    @TableField("ard_rectification_field")
    private String ardRectificationField;

    @ApiModelProperty(value = "整改字段值")
    @TableField("ard_rectification_value")
    private String ardRectificationValue;

    @ApiModelProperty(value = "整改字段类型(date,string)")
    @TableField("ard_rectification_field_type")
    private String ardRectificationFieldType;

    @ApiModelProperty(value = "整改附件")
    @TableField("ard_rectification_enclosure")
    private String ardRectificationEnclosure;


}
