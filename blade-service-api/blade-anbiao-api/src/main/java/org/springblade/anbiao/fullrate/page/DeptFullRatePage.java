package org.springblade.anbiao.fullrate.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @author hyp
 * @create 2023-03-02 18:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeptFullRatePage对象", description = "DeptFullRatePage对象")
public class DeptFullRatePage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "deptId", required = true)
	private String deptId;

	@ApiModelProperty(value = "deptName")
	private String deptName;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

	@ApiModelProperty(value = "省ID ")
	private String province;

	@ApiModelProperty(value = "市ID ")
	private String city;

	@ApiModelProperty(value = "县ID ")
	private String country;

}

