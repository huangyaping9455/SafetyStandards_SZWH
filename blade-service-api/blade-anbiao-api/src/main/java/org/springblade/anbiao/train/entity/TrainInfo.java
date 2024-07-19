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
package org.springblade.anbiao.train.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 */
@Data
public class TrainInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程ID")
	private Integer id;

	@ApiModelProperty(value = "课程名称")
	private String account;

	@ApiModelProperty(value = "课程名称")
	private Integer yue;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

}
