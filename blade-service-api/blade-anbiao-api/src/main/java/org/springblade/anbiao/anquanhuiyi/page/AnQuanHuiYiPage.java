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
	@ApiModelProperty(value = "企业名称")
	private String deptname;

	@ApiModelProperty(value = "会议开始时间")
	private String huiyikaishishijian;

}
