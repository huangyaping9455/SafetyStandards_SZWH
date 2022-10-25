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
package org.springblade.anbiao.richenganpai.entity;

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
@TableName("baobiao_security_parameter_info_remark")
@ApiModel(value = "BaoBiaoSecurityParameterInfoRemark对象", description = "BaoBiaoSecurityParameterInfoRemark对象")
public class BaoBiaoSecurityParameterInfoRemark implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 台账名称
     */
    @ApiModelProperty(value = "安全台账名称")
    private String name;

	/**
	 * 周期类型 1：日；2：周；3：月；4：季；5：年
	 */
	@ApiModelProperty(value = "周期类型 1：日；2：周；3：月；4：季；5：年")
	private Integer remindtype;

    /**
     * 提醒时间
     */
    @ApiModelProperty(value = "提醒时间")
    private String remindtime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updatetime;

    /**
     * 更新用户ID
     */
    @ApiModelProperty(value = "更新用户ID")
    private Integer updateuser;

    /**
     * 备注
     */
	@ApiModelProperty(value = "备注")
	private String remark;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识，默认0；1删除")
    private Integer isdeleted;

    /**
     * 企业ID
     */
    @ApiModelProperty(value = "企业ID")
    private String deptId;

    /**
     * 安全台账配置项ID
     */
    @ApiModelProperty(value = "安全台账配置项ID")
    private Integer securityId;

    /**
     * 安全台账文档ID
     */
    @ApiModelProperty(value = "安全台账文档ID")
    private Integer safetyfileId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 周期时间
     */
    @ApiModelProperty(value = "周期时间")
    private String time;

}
