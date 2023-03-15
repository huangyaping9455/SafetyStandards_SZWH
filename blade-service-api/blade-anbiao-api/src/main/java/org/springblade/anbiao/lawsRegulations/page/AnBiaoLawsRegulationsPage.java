/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuZhuTiPage
 * Author:   呵呵哒
 * Date:     2020/6/20 16:14
 * Description:
 */
package org.springblade.anbiao.lawsRegulations.page;

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
@ApiModel(value = "AnBiaoLawsRegulationsPage对象", description = "AnBiaoLawsRegulationsPage对象")
public class AnBiaoLawsRegulationsPage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "发布日期")
	private String releaseDate;

	@ApiModelProperty(value = "实施日期")
	private String materialDate;

	@ApiModelProperty(value = "文件名称")
	private String name;

	@ApiModelProperty(value = "分类")
	private int type;

	@ApiModelProperty(value = "文号")
	private String number;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

}
