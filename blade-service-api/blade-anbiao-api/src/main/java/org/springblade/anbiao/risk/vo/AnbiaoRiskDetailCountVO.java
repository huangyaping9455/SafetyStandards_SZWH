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
@ApiModel(value="AnbiaoRiskDetailCountVO对象", description="AnbiaoRiskDetailCountVO对象")
public class AnbiaoRiskDetailCountVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "风险大类(0=资质类;1.制度类,2=台账类)")
    private String ardMajorCategories;

    @ApiModelProperty(value = "风险小类(000=企业资质,001=车辆资质,002=人员资质,003=驾驶员资质,004=保险资质;100=制度类,101=责任书,102=危险告知书,103=入职信息,104=劳动合同)")
    private String ardSubCategory;

	@ApiModelProperty(value = "发现日期")
	@TableField("ard_discovery_date")
	private String ardDiscoveryDate;

	@ApiModelProperty(value = "预警数")
	private int num = 0;



}
