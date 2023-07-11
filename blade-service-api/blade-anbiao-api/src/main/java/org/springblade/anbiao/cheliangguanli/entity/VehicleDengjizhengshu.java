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
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 车辆登记证书实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_dengjizhengshu")
@ApiModel(value = "VehicleDengjizhengshu对象", description = "车辆登记证书")
public class VehicleDengjizhengshu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登记证书主键
     */
	@TableId(value = "avd_ids", type = IdType.UUID)
    @ApiModelProperty(value = "登记证书主键")
    private String avdIds;

    @ApiModelProperty(value = "车辆主键")
    private String avdVehicleIds;
    /**
     * 登记机构
     */
    @ApiModelProperty(value = "登记机构")
    private String avdTechnicalEvaluation;
    /**
     * 登记日期
     */
    @ApiModelProperty(value = "登记日期")
    private String avdTechnicalDate;
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
    private String avdCreateTime;
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
    private String avdUpdateTime;
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

	@ApiModelProperty(value = "车辆牌照")
	@TableField(exist = false)
	private String avxFileNo;

	/**
	 * 车辆型号
	 */
	@ApiModelProperty(value = "车辆型号")
	private String avdVehicleModel;

	@ApiModelProperty(value = "车辆id")
	@TableField(exist = false)
	private String vehicleId;

	@ApiModelProperty(value = "车辆牌照")
	@TableField(exist = false)
	private String cheliangpaizhao;

	@ApiModelProperty(value = "企业id")
	@TableField(exist = false)
	private String deptId;


}
