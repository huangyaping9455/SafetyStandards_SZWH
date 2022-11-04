package org.springblade.anbiao.anquanhuiyi.page;

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
@ApiModel(value = "AnQuanHuiYiPage对象",description = "AnQuanHuiYiPage")
public class AnQuanHuiYiPage<T> extends BasePage<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "会议名称",required = true)
	private Integer huiyimingcheng;

	@ApiModelProperty(value = "会议日期")
	private String huiyiriqi;

}
