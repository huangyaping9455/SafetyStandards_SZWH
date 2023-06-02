package org.springblade.anbiao.risk.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 安标风险统计信息
 * </p>
 *
 * @author hyp
 * @since 2022-12-06
 */
@Data
@ApiModel(value="AnbiaoRiskDetailVO对象", description="AnbiaoRiskDetailVO对象")
public class AnbiaoRiskDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long ardIds;

    @ApiModelProperty(value = "企业ID")
    private String ardDeptIds;

    @ApiModelProperty(value = "风险大类(0=资质类;1.制度类,2=台账类)")
    private String ardMajorCategories;

    @ApiModelProperty(value = "风险小类(000=企业资质,001=车辆资质,002=人员资质,003=驾驶员资质,004=保险资质;100=制度类,101=责任书,102=危险告知书,103=入职信息,104=劳动合同)")
    private String ardSubCategory;

	@ApiModelProperty(value = "风险类型")
	private String ardType;

    @ApiModelProperty(value = "风险标题")
    private String ardTitle;

    @ApiModelProperty(value = "风险内容")
    private String ardContent;

    @ApiModelProperty(value = "发现日期")
    private String ardDiscoveryDate;

    @ApiModelProperty(value = "关联表")
    private String ardAssociationTable;

    @ApiModelProperty(value = "关联字段")
    private String ardAssociationField;

    @ApiModelProperty(value = "关联值")
    private String ardAssociationValue;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "驾驶员联系电话")
	private String shoujihaoma;

	@ApiModelProperty(value = "驾驶员身份证号")
	private String shenfenzhenghao;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "驾驶员照片")
	private String ajrHeadPortrait;

	@ApiModelProperty(value = "预警数")
	private int num = 0;

	@ApiModelProperty(value = "openId")
	private String openid;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "Id")
	private String id;

}
