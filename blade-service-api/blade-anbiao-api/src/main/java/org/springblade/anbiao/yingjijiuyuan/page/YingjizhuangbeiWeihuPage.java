package org.springblade.anbiao.yingjijiuyuan.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @author hyp
 * @since 2023-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "YingjizhuangbeiWeihuPage对象", description = "YingjizhuangbeiWeihuPage对象")
public class YingjizhuangbeiWeihuPage<T> extends BasePage<T> {
	/**
	 * 应急装备ID
	 */
	@ApiModelProperty(value = "应急装备ID",required = true)
	private String yingjizhuangbeiid;
}
