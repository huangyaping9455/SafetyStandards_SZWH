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
@ApiModel(value = "AnbiaoSparePartsStorePersonPage对象", description = "AnbiaoSparePartsStorePersonPage对象")
public class AnbiaoSparePartsStorePersonPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID" , required = true)
	private String deptId;

	@ApiModelProperty(value = "单号")
	private String sppNo;

	@ApiModelProperty(value = "备件名称")
	private String sppName;

	@ApiModelProperty(value = "人员名称")
	private String sppPersonname;

	@ApiModelProperty(value = "人员名ID")
	private String sppPersonid;

}




