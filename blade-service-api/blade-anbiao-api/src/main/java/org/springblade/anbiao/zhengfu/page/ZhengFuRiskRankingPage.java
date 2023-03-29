/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuZhuTiPage
 * Author:   呵呵哒
 * Date:     2020/6/20 16:14
 * Description:
 */
package org.springblade.anbiao.zhengfu.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @创建人 hyp
 * @创建时间 2020/6/20
 * @描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ZhengFuRiskRankingPage对象", description = "ZhengFuRiskRankingPage对象")
public class ZhengFuRiskRankingPage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "deptId")
	private String deptId;

	@ApiModelProperty(value = "deptName")
	private String deptName;

	@ApiModelProperty(value = "统计日期", required = true)
	private String date;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

	@ApiModelProperty(value = "政府ID ")
	private String zhengfuid;

	@ApiModelProperty(value = "省ID ")
	private String province;

	@ApiModelProperty(value = "市ID ")
	private String city;

	@ApiModelProperty(value = "县ID ")
	private String country;

}
