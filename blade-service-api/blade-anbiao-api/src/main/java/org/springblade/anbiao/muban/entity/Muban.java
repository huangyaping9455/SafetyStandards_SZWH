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
package org.springblade.anbiao.muban.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 * @author 呵呵哒
 */
@Data
@TableName("anbiao_muban")
@ApiModel(value = "Muban对象", description = "Muban对象")
public class Muban implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    private String muban;
    /**
     * lietUrl
     */
    @ApiModelProperty(value = "lietUrl(配置)")
    private String viewField;
	/**
	 * 新增url
	 */
	@ApiModelProperty(value = "新增url(配置)")
	private String insertField;
	/**
	 * 编辑url
	 */
	@ApiModelProperty(value = "编辑url(配置)")
	private String updateField;
	/**
	 * 删除url
	 */
	@ApiModelProperty(value = "删除url(配置)")
    private String removeField;
	/**
	 * 逻辑删除
	 */
	@ApiModelProperty(value = "删除(配置)")
    private Integer is_deleted;
	/**
	 * 上级id
	 */
	@ApiModelProperty(value = "上级id")
	private Integer parentId;
	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型(0代表干 1代表枝)")
	private Integer leixing;

	/**
	 * lietUrl(数据)
	 */
	@ApiModelProperty(value = "lietUrl(数据)")
	private String viewModel;
	/**
	 * 新增url
	 */
	@ApiModelProperty(value = "新增url(数据)")
	private String insertModel;
	/**
	 * 编辑url
	 */
	@ApiModelProperty(value = "编辑url(数据)")
	private String updateModel;
	/**
	 * 删除url
	 */
	@ApiModelProperty(value = "删除url(数据)")
	private String removeModel;
	/**
	 * 详情url
	 */
	@ApiModelProperty(value = "详情url(数据)")
	private String detailModel;

	/**
	 * 菜单关联
	 */
	@ApiModelProperty(value = "菜单关联")
	private String token;
	/**
	 * 模块表名
	 */
	@ApiModelProperty(value = "模块表名")
	private String biaoming;
}
