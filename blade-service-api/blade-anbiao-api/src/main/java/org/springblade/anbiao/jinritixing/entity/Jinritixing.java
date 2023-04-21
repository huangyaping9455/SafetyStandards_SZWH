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
package org.springblade.anbiao.jinritixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("anbiao_jinritixing_jiesuan")
@ApiModel(value = "Jinritixing对象", description = "Jinritixing对象")
public class Jinritixing implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
	@TableId(value = "id", type = IdType.UUID)
    private String id;
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
     * 单位id
     */
    @ApiModelProperty(value = "单位id",required = true)
    private Integer deptId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createtime;
    /**
     * 提醒类型
     */
    @ApiModelProperty(value = "提醒类型",required = true)
    private String tixingleixing;
    /**
     * 提醒详情
     */
    @ApiModelProperty(value = "提醒详情",required = true)
    private String tixingxiangqing;
    /**
     * 提醒详情id
     */
    @ApiModelProperty(value = "提醒详情id",required = true)
    private String tixingxiangqingid;
    /**
     * 统计日期
     */
    @ApiModelProperty(value = "统计日期",required = true)
    private String tongjiriqi;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Integer isdelete;
    /**
     * 驾驶员id
     */
    @ApiModelProperty(value = "驾驶员id")
    private String jiashiyuanid;
    /**
     * 车辆id
     */
    @ApiModelProperty(value = "车辆id")
    private String cheliangid;

	/**
	 * 天数
	 */
	@ApiModelProperty(value = "天数",required = true)
	private String days;

	/**
	 * 表id
	 */
	@ApiModelProperty(value = "表字段",required = true)
	private String biaoid;

	@ApiModelProperty(value = "数据状态",required = true)
	private Integer status;

	@ApiModelProperty(value = "其他类型数据ID")
	private String shujuid;


}
