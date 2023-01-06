package org.springblade.anbiao.risk.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * Created by you on 2019/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RiskPage对象", description = "RiskPage对象")
public class RiskPage<T> extends BasePage<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "单位ID",required = true)
	private Integer deptId;

	@ApiModelProperty(value = "统计日期")
	private String date;

	@ApiModelProperty(value = "风险小类")
	private String category;

	@ApiModelProperty(value = "风险内容")
	private String ardContent;


}
