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
package org.springblade.anbiao.guanlijigouherenyuan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 */
@Data
@TableName("anbiao_huiyirenyuan")
@ApiModel(value = "Huiyirenyuan对象", description = "Huiyirenyuan对象")
public class Huiyirenyuan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id",type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 会议id
     */
    @ApiModelProperty(value = "会议id")
    private String huiyiid;
    /**
     * 人员名称
     */
    @ApiModelProperty(value = "人员名称")
    private String renyuanmingcheng;
    /**
     * 部门
     */
    @ApiModelProperty(value = "部门")
    private String bumen;
    /**
     * 职务
     */
    @ApiModelProperty(value = "职务")
    private String zhiwu;
    /**
     * 是否参加会议
     */
    @ApiModelProperty(value = "是否参加会议")
    private String shifoucanjiahuiyi;
    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer isDeleted;


}
