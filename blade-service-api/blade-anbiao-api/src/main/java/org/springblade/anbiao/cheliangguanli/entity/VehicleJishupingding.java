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
 * 车辆技术评定信息实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_jishupingding")
@ApiModel(value = "VehicleJishupingding对象", description = "车辆技术评定信息")
public class VehicleJishupingding implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 技术评定主键
     */
	@TableId(value = "avj_ids", type = IdType.UUID)
    @ApiModelProperty(value = "技术评定主键")
    private String avjIds;

    @ApiModelProperty(value = "车辆主键")
    private String avjVehicleIds;
    /**
     * 评定机构
     */
    @ApiModelProperty(value = "评定机构")
    private String avjTechnicalEvaluation;
    /**
     * 评定日期
     */
    @ApiModelProperty(value = "评定日期")
    private LocalDate avjTechnicalDate;
    /**
     * 评定报告附件
     */
    @ApiModelProperty(value = "评定报告附件")
    private String avjEnclosure;
    /**
     * 逻辑删除(0=正常,1=删除)
     */
    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private String avjDelete;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime avjCreateTime;
    /**
     * 创建人主键
     */
    @ApiModelProperty(value = "创建人主键")
    private String avjCreateByIds;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String avjCreateByName;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime avjUpdateTime;
    /**
     * 更新人主键
     */
    @ApiModelProperty(value = "更新人主键")
    private String avjUpdateByIds;
    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String avjUpdateByName;

	@ApiModelProperty(value = "行驶里程记录")
	private String avjDrivingMileage;

	@ApiModelProperty(value = "其他检测/二级维护竣工质量检测")
	private String avjQualityInspection;

	@ApiModelProperty(value = "车辆技术等级")
	private String avjVehicleTechnicalGrade;

	@ApiModelProperty(value = "车辆类型等级")
	private String avjVehicleTypeClass;




}
