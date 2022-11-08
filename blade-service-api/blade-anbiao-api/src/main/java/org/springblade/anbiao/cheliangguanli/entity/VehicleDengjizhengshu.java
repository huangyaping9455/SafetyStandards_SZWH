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

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车辆登记证书实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_dengjizhengshu")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VehicleDengjizhengshu对象", description = "车辆登记证书")
public class VehicleDengjizhengshu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登记证书主键
     */
    @ApiModelProperty(value = "登记证书主键")
    private String avdIds;
    /**
     * 登记机构
     */
    @ApiModelProperty(value = "登记机构")
    private String avdTechnicalEvaluation;
    /**
     * 登记日期
     */
    @ApiModelProperty(value = "登记日期")
    private LocalDate avdTechnicalDate;
    /**
     * 登记证书附件
     */
    @ApiModelProperty(value = "登记证书附件")
    private String avdEnclosure;
    /**
     * 报废日期
     */
    @ApiModelProperty(value = "报废日期")
    private LocalDate avdScrapDate;
    /**
     * 逻辑删除(0=正常,1=删除)
     */
    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private String avdDelete;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime avdCreateTime;
    /**
     * 创建人主键
     */
    @ApiModelProperty(value = "创建人主键")
    private String avdCreateByIds;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String avdCreateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime avdUpdateTime;
    /**
     * 更新人主键
     */
    @ApiModelProperty(value = "更新人主键")
    private String avdUpdateByIds;
    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String avdUpdateByName;


}