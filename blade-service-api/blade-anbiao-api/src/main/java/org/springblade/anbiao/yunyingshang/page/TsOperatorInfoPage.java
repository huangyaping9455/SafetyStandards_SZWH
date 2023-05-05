package org.springblade.anbiao.yunyingshang.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TsOperatorInfoPage对象",description = "TsOperatorInfoPage")
public class TsOperatorInfoPage<T> extends BasePage<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID",required = true)
	private String deptId;

	@ApiModelProperty(value = "运营商名称")
	private String opName;

	@ApiModelProperty(value = "接入码")
	private String opCode;

	@ApiModelProperty(value = "所属运管名称")
	private String yunguanName;

	@ApiModelProperty(value = "省ID ")
	private String province;

	@ApiModelProperty(value = "市ID ")
	private String city;

	@ApiModelProperty(value = "县ID ")
	private String country;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

}
