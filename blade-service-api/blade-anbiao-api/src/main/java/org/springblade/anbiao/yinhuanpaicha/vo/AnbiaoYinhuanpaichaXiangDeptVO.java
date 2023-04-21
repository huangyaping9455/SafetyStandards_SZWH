/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.yinhuanpaicha.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoYinhuanpaichaXiangDept;

/**
 * @author hyp
 * @since 2022-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AnbiaoYinhuanpaichaXiangDeptVO对象", description = "AnbiaoYinhuanpaichaXiangDeptVO对象")
public class AnbiaoYinhuanpaichaXiangDeptVO extends AnbiaoYinhuanpaichaXiangDept {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "项目名称")
	private String name;

	@ApiModelProperty(value = "风险值")
	private Integer score;

	@ApiModelProperty(value = "类别")
	private Integer type;

	@ApiModelProperty(value = "严重程度")
	private Integer status;

	@ApiModelProperty(value = "判断规则（多个规则，以英文逗号分隔）")
	private String judgerules;

	@ApiModelProperty(value = "判断规则描述")
	private String judgerulesremark;

	@ApiModelProperty(value = "来源")
	private Integer source;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "企业名称")
	private String deptname;

	@ApiModelProperty(value = "排查年、月份")
	private String pcyf;

	private Integer id;

	@ApiModelProperty(value = "企业ID")
	private String deptid;
}
