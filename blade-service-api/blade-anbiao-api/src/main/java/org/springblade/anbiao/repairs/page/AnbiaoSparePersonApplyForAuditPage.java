package org.springblade.anbiao.repairs.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AnbiaoSparePersonApplyForAuditPage对象", description = "AnbiaoSparePersonApplyForAuditPage对象")
public class AnbiaoSparePersonApplyForAuditPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID" , required = true)
	private String deptId;

	@ApiModelProperty(value = "单号")
	private String spNo;

	@ApiModelProperty(value = "备件编码")
	private String soiSpNo;

	@ApiModelProperty(value = "备件名称")
	private String spName;

	@ApiModelProperty(value = "申请人名称")
	private String spPersonName;

	@ApiModelProperty(value = "单据类型")
	private Integer spType;

	@ApiModelProperty(value = "审核状态")
	private Integer spAuditStatus;

	@ApiModelProperty(value = "申请人ID")
	private String spPersonId;

}




