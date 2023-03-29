/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuZhuTiPage
 * Author:   呵呵哒
 * Date:     2020/6/20 16:14
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/11/30
 * @描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "BsPolicyInfoPage对象", description = "BsPolicyInfoPage对象")
public class BsPolicyInfoPage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "标题")
	private String biaoti;

	@ApiModelProperty(value = "启用状态")
	private String shifouqiyong;

	@ApiModelProperty(value = "标签类型")
	private String biaoqian;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

}
