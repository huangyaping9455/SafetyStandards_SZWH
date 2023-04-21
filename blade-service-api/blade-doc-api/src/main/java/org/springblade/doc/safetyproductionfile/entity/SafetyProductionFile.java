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
package org.springblade.doc.safetyproductionfile.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.common.tool.JsonSerializerUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 * @author Blade
 */
@Data
@TableName("anbiao_SafetyProductionFile")
@ApiModel(value = "SafetyProductionFile对象", description = "SafetyProductionFile对象")
public class SafetyProductionFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.INPUT)
    private Integer id;
    /**
     * 上级id
     */
    @ApiModelProperty(value = "上级id")
    private Integer parentId;
    /**
     * 单位id
     */
    @ApiModelProperty(value = "单位id")
    private Integer deptId;
    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String name;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String path;
    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer sort;
    /**
     * 文件层级关系
     */
    @ApiModelProperty(value = "文件层级关系")
    private String tier;
    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    private Integer caozuorenid;
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String caozuoren;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private String caozuoshijian;
    /**
     * 手否删除
     */
    @ApiModelProperty(value = "手否删除")
	@TableLogic
    private Integer isDeleted;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createtime;
    /**
     * 文档编号
     */
    @ApiModelProperty(value = "文档编号")
    private String documentNumber;

	/**
	 * 文档类型
	 */
	@ApiModelProperty(value = "文档类型")
    private String type;

	/**
	 * 累积访问次数
	 */
	@ApiModelProperty(value = "累积访问次数")
//	@JsonSerialize(nullsUsing= JsonSerializerUtils.class)
	private  Integer cumulativeVisits;

	/**
	 * 最后访问时间
	 */
	@ApiModelProperty(value = "最后访问时间")
	private  String lastPreviewTime;


    @ApiModelProperty(value = "模板路径")
    private String mubanPath;

    @ApiModelProperty(value = "是否编辑")
    private Integer isEdit;

    @ApiModelProperty(value = "模板标识")
    private Integer is_muban;
	@TableField(exist = false)
    private Integer isMuban;
	@TableField(exist = false)
	private String isMubanShow;

    @ApiModelProperty(value = "上传时间周期")
    private String uploadcycle;

    @ApiModelProperty(value = "文件是否必填")
    private Integer isRequired;

	@ApiModelProperty(value = "是否更改了名字 0：否，1：是")
	@TableField(exist = false)
	private Integer nameshow;

	@ApiModelProperty(value = "台账ID")
	private Integer safeid;

}
