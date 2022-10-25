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
public class BaoBiaoSecurityParameterInfoTime implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 周期类型 1：日；2：周；3：月；4：季；5：年
     */
    @ApiModelProperty(value = "周期类型 1：日；2：周；3：月；4：季；5：年")
    private Integer type;

    /**
     * 周期时间
     */
    @ApiModelProperty(value = "周期时间")
    private String time;

    /**
     * 安全台账文档ID
     */
    @ApiModelProperty(value = "安全台账文档ID")
    private Integer safetyfileId;

    /**
     * 企业ID
     */
    @ApiModelProperty(value = "企业ID")
    private Integer deptId;

}
