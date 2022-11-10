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
 * 车辆合格证书实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_hegezheng")
@ApiModel(value = "VehicleHegezheng对象", description = "车辆合格证书")
public class VehicleHegezheng implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 合格证书主键
     */
	@TableId(value = "avh_ids", type = IdType.UUID)
    @ApiModelProperty(value = "合格证书主键")
    private String avhIds;
    /**
     * 发证日期
     */
    @ApiModelProperty(value = "发证日期")
    private LocalDate avhIssueDate;
    /**
     * 合格证书附件
     */
    @ApiModelProperty(value = "合格证书附件")
    private String avhEnclosure;
    /**
     * 制造日期
     */
    @ApiModelProperty(value = "制造日期")
    private LocalDate avhManufactureDate;
    /**
     * 逻辑删除(0=正常,1=删除)
     */
    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private String avhDelete;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime avhCreateTime;
    /**
     * 创建人主键
     */
    @ApiModelProperty(value = "创建人主键")
    private String avhCreateByIds;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String avhCreateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime avhUpdateTime;
    /**
     * 更新人主键
     */
    @ApiModelProperty(value = "更新人主键")
    private String avhUpdateByIds;
    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String avhUpdateByName;


}
