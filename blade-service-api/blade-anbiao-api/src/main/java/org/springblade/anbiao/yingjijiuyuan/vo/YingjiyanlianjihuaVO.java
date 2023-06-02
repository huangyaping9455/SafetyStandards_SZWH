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
package org.springblade.anbiao.yingjijiuyuan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.yingjijiuyuan.entity.Yingjiyanlianjihua;

/**
 * 视图实体类
 * @author hyp
 * @since 2023-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "YingjiyanlianjihuaVO对象", description = "YingjiyanlianjihuaVO对象")
public class YingjiyanlianjihuaVO extends Yingjiyanlianjihua {
	private static final long serialVersionUID = 1L;
	/**
	 * 单位名称
	 */
	@ApiModelProperty(value = "单位名称")
	private String deptname;
}
