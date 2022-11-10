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
 * 车辆变更记录实体类
 *
 * @author Blade
 * @since 2022-11-06
 */
@Data
@TableName("anbiao_vehicle_biangengjilu")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VehicleBiangengjilu对象", description = "车辆变更记录")
public class VehicleBiangengjilu extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId(value = "avbj_ids", type = IdType.UUID)
	@ApiModelProperty(value = "车辆变更记录主键")
	private String avbjIds;

	@ApiModelProperty(value = "车辆主键")
	private String avbjVehicleId;
	/**
	 * 车辆牌照
	 */
	@ApiModelProperty(value = "车辆牌照")
	private String avbjLicensePlate;
	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "颜色")
	private String avbjLicenseColour;
	/**
	 * 变更日期
	 */
	@ApiModelProperty(value = "变更日期")
	private LocalDate avbjChangeDate;
	/**
	 * 逻辑删除(0=正常,1=删除)
	 */
	@ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
	private String avbjDelete;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime avbjCreateTime;
	/**
	 * 创建人主键
	 */
	@ApiModelProperty(value = "创建人主键")
	private String avbjCreateByIds;
	/**
	 * 创建人姓名
	 */
	@ApiModelProperty(value = "创建人姓名")
	private String avbjCreateByName;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime avbjUpdateTime;
	/**
	 * 更新人主键
	 */
	@ApiModelProperty(value = "更新人主键")
	private String avbjUpdateByIds;
	/**
	 * 更新人姓名
	 */
	@ApiModelProperty(value = "更新人姓名")
	private String avbjUpdateByName;


}
