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
package org.springblade.anbiao.cheliangguanli.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 */
@Data
@TableName("anbiao_cheliangnianshen")
@ApiModel(value = "Cheliangnianshen对象", description = "Cheliangnianshen对象")
public class Cheliangnianshen implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
	@TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 单位id
     */
    @ApiModelProperty(value = "单位id",required = true)
    private Integer deptId;
    /**
     * 车辆id
     */
    @ApiModelProperty(value = "车辆id",required = true)
    private String cheliangid;
    /**
     * 证件名称
     */
    @ApiModelProperty(value = "证件名称",required = true)
    private String zhengjianmingcheng;
    /**
     * 检验日期
     */
    @ApiModelProperty(value = "检验日期",required = true)
    private String jianyanriqi;
    /**
     * 检验有效期
     */
    @ApiModelProperty(value = "检验有效期",required = true)
    private String jianyanyouxiaoqi;
    /**
     * 是否合格
     */
    @ApiModelProperty(value = "是否合格",required = true)
    private String shifouhege;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String beizhu;
    /**
     * 照片
     */
    @ApiModelProperty(value = "照片")
    private String zhaopian;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String fujian;
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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createtime;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
	@TableLogic
    private Integer isDeleted;


}
