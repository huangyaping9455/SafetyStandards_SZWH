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
@ApiModel(value = "AnBiaoWeeksHiddenTroublePage对象", description = "AnBiaoWeeksHiddenTroublePage对象")
public class AnBiaoWeeksHiddenTroublePage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID", required = true)
	private int deptId;

	@ApiModelProperty(value = "检查日期")
	private String date;

	@ApiModelProperty(value = "类型，1：企业；2：车辆")
	private int type;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

}
