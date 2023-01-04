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
package org.springblade.anbiao.jiashiyuan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 */
@Data
@ApiModel(value = "BaojingTJMX对象", description = "BaojingTJMX对象")
public class JiaShiYuanTJMX implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Integer xuhao;
    @ApiModelProperty(value = "企业名称")
    private String deptName;
	@ApiModelProperty(value = "姓名")
	private String a;
	@ApiModelProperty(value = "入职表")
	private String a1;
	@ApiModelProperty(value = "身份证")
	private String a2;
	@ApiModelProperty(value = "驾驶证")
	private String a3;
	@ApiModelProperty(value = "从业资格证")
	private String a4;
	@ApiModelProperty(value = "体检表")
	private String a5;

}
