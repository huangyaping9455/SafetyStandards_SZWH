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
package org.springblade.anbiao.yingjijiuyuan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 * @author hyp
 * @since 2023-06-01
 */
@Data
@TableName("anbiao_yingjiduiwu")
@ApiModel(value = "Yingjiduiwu对象", description = "Yingjiduiwu对象")
public class Yingjiduiwu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 单位id
	 yingjiduiwu
    @ApiModelProperty(value = "单位id")
    @TableField("dept_id")
    private Integer deptId;

    @ApiModelProperty(value = "企业 名称")
    @TableField(exist = false)
    private String deptName;
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String caozuoren;
    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    private Integer caozuorenid;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private String caozuoshijian;
    /**
     * 应急救援队伍名称
     */
    @ApiModelProperty(value = "应急救援队伍名称")
    private String duiwumingcheng;
    /**
     * 负责部门
     */
    @ApiModelProperty(value = "负责部门")
    private String fuzebumen;
    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String fuzeren;
    /**
     * 负责人电话
     */
    @ApiModelProperty(value = "负责人电话")
    private String fuzerendianhua;
    /**
     * 负责人职务
     */
    @ApiModelProperty(value = "负责人职务")
    private String fuerenzhiwu;

    @ApiModelProperty(value = "是否删除")
    @TableField("is_deleted")
    private Integer isdel = 0;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间",required = true)
	private String createtime;
}
