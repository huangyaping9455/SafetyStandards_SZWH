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
@ApiModel(value = "AnbiaoSparePartsStorePage对象", description = "AnbiaoSparePartsStorePage对象")
public class AnbiaoSparePartsStorePage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID" , required = true)
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "备件名称")
	private String spName;

	@ApiModelProperty(value = "仓库ID")
	private String spWarehouseId;

	@ApiModelProperty(value = "备件编码")
	private String spNo;

}




