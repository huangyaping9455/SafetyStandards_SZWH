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
package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author hyp
 */
@Data
@TableName("blade_dept")
@ApiModel(value = "Dept对象", description = "Dept对象")
public class Dept implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id")
	private Integer id;

	/**
	 * 租户编号
	 */
	@ApiModelProperty(value = "租户编号")
	private String tenantCode;

	/**
	 * 父主键
	 */
	@ApiModelProperty(value = "父主键")
	private Integer parentId;

	/**
	 * 机构名
	 */
	@ApiModelProperty(value = "机构名")
	private String deptName;

	/**
	 * 机构全称
	 */
	@ApiModelProperty(value = "机构全称")
	private String fullName;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 是否已删除
	 */
	@TableLogic
	@ApiModelProperty(value = "是否已删除")
	private Integer isDeleted;

	/**
	 * 树形菜单
	 */
	@ApiModelProperty(value = "树形菜单")
	private String treeCode;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private String extendType;

	@ApiModelProperty(value = "机构类型")
	@TableField(exist = false)
	private String jigouleixing;

	@TableField(exist = false)
	private String table;
	@ApiModelProperty(value = "岗位职责")
	@TableField(exist = false)
	private String gangweizhize;
	@ApiModelProperty(value = "安全职责")
	@TableField(exist = false)
	private String anquanzhize;
	@ApiModelProperty(value = "类型(安全部,财务部)")
	@TableField(exist = false)
	private String leixing;

	@ApiModelProperty(value = "基本信息id")
	@TableField(exist = false)
	private String organtiationid;

	/**
	 * 是否存在下级
	 */
	@ApiModelProperty(value = "是否存在下级(1 存在 0不存在)")
	@TableField(exist = false)
	private Integer existChild;



}
