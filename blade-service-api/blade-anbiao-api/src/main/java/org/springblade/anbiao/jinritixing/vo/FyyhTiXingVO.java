/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.jinritixing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.jinritixing.entity.Jinritixing;

/**
 * 视图实体类
 * @author 呵呵哒
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FyyhTiXingVO对象", description = "FyyhTiXingVO对象")
public class FyyhTiXingVO extends Jinritixing {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "隐患名称")
	private String name;

	@ApiModelProperty(value = "隐患分类")
	private Integer type;

	@ApiModelProperty(value = "隐患数量")
	private Integer counts = 0;

	@ApiModelProperty(value = "隐患项分值")
	private Integer countscore = 0;

	@ApiModelProperty(value = "已消除隐患项分值")
	private Integer finshcountscore = 0;

	@ApiModelProperty(value = "统计日期")
	private String tongjiriqi;

	@ApiModelProperty(value = "提醒类型")
	private String tixingleixing;

	@ApiModelProperty(value = "提醒详情")
	private String tixingxiangqing;
}
