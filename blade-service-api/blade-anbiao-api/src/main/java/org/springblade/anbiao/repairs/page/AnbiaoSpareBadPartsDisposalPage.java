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
@ApiModel(value = "AnbiaoSpareBadPartsDisposalPage对象", description = "AnbiaoSpareBadPartsDisposalPage对象")
public class AnbiaoSpareBadPartsDisposalPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID" , required = true)
	private String deptId;

	@ApiModelProperty(value = "备件编码", required = true)
	private String sbpSpNo;

	@ApiModelProperty(value = "处理方式，1：返厂，2：报废")
	private Integer sbpWay;

}




