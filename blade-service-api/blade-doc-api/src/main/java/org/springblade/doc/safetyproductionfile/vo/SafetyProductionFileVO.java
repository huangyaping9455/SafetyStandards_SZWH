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
package org.springblade.doc.safetyproductionfile.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tool.node.INode;
import org.springblade.doc.safetyproductionfile.entity.SafetyProductionFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图实体类
 *
 * @author Blade
 * @since 2019-05-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SafetyProductionFileVO对象", description = "SafetyProductionFileVO对象")
public class SafetyProductionFileVO extends SafetyProductionFile implements INode  {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 下级文件数量
	 */
	@ApiModelProperty(value = "下级文件数")
	private Integer fileNum;

	/**
	 * 父节点ID
	 */
	private Integer parentId;

	@ApiModelProperty(value = "图片列表")
	private List<String> ImgList;
	@ApiModelProperty(value = "是否已绑定")
	private Integer isBind;



	/**
	 * 子孙节点
	 */
	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<INode> children;

	@Override
	public List<INode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}

	private String isOnlyDir;

	@ApiModelProperty(value = "文件是否必填")
	private Integer isRequired;

	@ApiModelProperty(value = "是否新增一级节点 0：否，1：是")
	private Integer isParent;

	private Integer num;

	private Integer finshNum;

}
