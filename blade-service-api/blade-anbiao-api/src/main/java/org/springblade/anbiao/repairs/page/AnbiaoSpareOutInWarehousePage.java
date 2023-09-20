package org.springblade.anbiao.repairs.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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
@ApiModel(value = "AnbiaoSpareOutInWarehousePage对象", description = "AnbiaoSpareOutInWarehousePage对象")
public class AnbiaoSpareOutInWarehousePage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID" , required = true)
	private String deptId;

	@ApiModelProperty(value = "单号")
	private String soiNo;

	@ApiModelProperty(value = "备件编码")
	private String soiSpNo;

	@ApiModelProperty(value = "单据类型")
	private Integer soiType;

	@ApiModelProperty(value = "申请人名称")
	private String soiUserName;

	@ApiModelProperty(value = "申请时间")
	private String soiDate;

	@ApiModelProperty(value = "审核状态")
	private Integer soiAuditStatus;


}




