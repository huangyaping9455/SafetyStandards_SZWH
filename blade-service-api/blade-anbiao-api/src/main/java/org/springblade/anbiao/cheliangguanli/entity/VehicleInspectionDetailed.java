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
 * 车辆安全检查详细情况实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_inspection_detailed")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VehicleInspectionDetailed对象", description = "车辆安全检查详细情况")
public class VehicleInspectionDetailed extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 安全检查主键
     */
    @ApiModelProperty(value = "安全检查主键")
    private String vidIds;
    /**
     * 检查日期
     */
    @ApiModelProperty(value = "检查日期")
    private LocalDate vitInspectionDate;
    /**
     * 车辆主键
     */
    @ApiModelProperty(value = "车辆主键")
    private String vidAvIds;
    /**
     * 检查项目主键
     */
    @ApiModelProperty(value = "检查项目主键")
    private Long vidVitIds;
    /**
     * 检查部位
     */
    @ApiModelProperty(value = "检查部位")
    private String vidVitItem;
    /**
     * 具体位置
     */
    @ApiModelProperty(value = "具体位置")
    private String vidVitName;
    /**
     * 技术要求
     */
    @ApiModelProperty(value = "技术要求")
    private String vidVitDescribe;
    /**
     * 检查状态(0=正常,1=异常)
     */
    @ApiModelProperty(value = "检查状态(0=正常,1=异常)")
    private String vidIsAbnormal;
    /**
     * 异常描述
     */
    @ApiModelProperty(value = "异常描述")
    private String vidExceptionDescribe;
    /**
     * 异常附件
     */
    @ApiModelProperty(value = "异常附件")
    private String vidExceptionEnclosure;
    /**
     * 签名
     */
    @ApiModelProperty(value = "签名")
    private String vitAutograph;
    /**
     * 逻辑删除(0=正常,1=删除)
     */
    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private String vitDelete;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime vitCreateTime;
    /**
     * 创建人主键(即检查人主键)
     */
    @ApiModelProperty(value = "创建人主键(即检查人主键)")
    private String vitCreateByIds;
    /**
     * 创建人姓名(即检查人姓名)
     */
    @ApiModelProperty(value = "创建人姓名(即检查人姓名)")
    private String vitCreateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime vitUpdateTime;
    /**
     * 更新人主键
     */
    @ApiModelProperty(value = "更新人主键")
    private String vitUpdateByIds;
    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String vitUpdateByName;


}
