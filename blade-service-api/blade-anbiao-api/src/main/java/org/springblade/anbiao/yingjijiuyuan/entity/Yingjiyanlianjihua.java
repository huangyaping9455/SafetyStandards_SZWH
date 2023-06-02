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
@TableName("anbiao_yingjiyanlianjihua")
@ApiModel(value = "Yingjiyanlianjihua对象", description = "Yingjiyanlianjihua对象")
public class Yingjiyanlianjihua implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
	@TableId(value = "id", type = IdType.UUID)
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 单位ID
     */
    @ApiModelProperty(value = "单位ID",required = true)
    private Integer deptId;
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String caozuoren;
    /**
     * 操作人ID
     */
    @ApiModelProperty(value = "操作人ID")
    private Integer caozuorenid;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private String caozuoshijian;
    /**
     * 应急预案ID
     */
    @ApiModelProperty(value = "应急预案ID",required = true)
    private String yingjiyuanid;
    /**
     * 应急预案名称
     */
    @ApiModelProperty(value = "应急预案名称")
    private String yingjiyuanmingcheng;
    /**
     * 演练计划名称
     */
    @ApiModelProperty(value = "演练计划名称")
    private String yanlianjihuamingcheng;
    /**
     * 应急队伍
     */
    @ApiModelProperty(value = "应急队伍")
    private String yingjiduiwu;
    /**
     * 应急队伍ID
     */
    @ApiModelProperty(value = "应急队伍ID",required = true)
    private String yingjiduiwuid;
    /**
     * 指挥人
     */
    @ApiModelProperty(value = "指挥人")
    private String zhihuiren;
    /**
     * 演练地点
     */
    @ApiModelProperty(value = "演练地点")
    private String yanliandidian;
    /**
     * 演练类型
     */
    @ApiModelProperty(value = "演练类型")
    private String yanlianleixing;
    /**
     * 演练日期
     */
    @ApiModelProperty(value = "演练日期")
    private String yanlianriqi;
    /**
     * 演练描述
     */
    @ApiModelProperty(value = "演练描述")
    private String yanlianmiaoshu;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String fujian;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Integer isdelete;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间",required = true)
	private String createtime;
}
