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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 车辆道路运输证实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_daoluyunshuzheng")
@ApiModel(value = "VehicleDaoluyunshuzheng对象", description = "车辆道路运输证")
public class VehicleDaoluyunshuzheng implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 道路运输证主键
     */
	@TableId(value = "avd_ids", type = IdType.UUID)
    @ApiModelProperty(value = "道路运输证主键")
    private String avdIds;
    /**
     * 车辆主键
     */
    @ApiModelProperty(value = "车辆主键")
    private String avdAvIds;
    /**
     * 业户名称
     */
    @ApiModelProperty(value = "业户名称")
    private String avdBusinessOwner;
    /**
     * 车牌号码
     */
    @ApiModelProperty(value = "车牌号码")
    private String avdPlateNo;
    /**
     * 车辆类型
     */
    @ApiModelProperty(value = "车辆类型")
    private String avdVehicleType;
    /**
     * 吨（座）位
     */
    @ApiModelProperty(value = "吨（座）位")
    private String avdTonnage;
    /**
     * 车辆尺寸长（单位：毫米）
     */
    @ApiModelProperty(value = "车辆尺寸长（单位：毫米）")
    private Integer avdVehicleLong;
    /**
     * 车辆尺寸宽（单位：毫米）
     */
    @ApiModelProperty(value = "车辆尺寸宽（单位：毫米）")
    private Integer avdVehicleWide;
    /**
     * 车辆尺寸高（单位：毫米）
     */
    @ApiModelProperty(value = "车辆尺寸高（单位：毫米）")
    private Integer avdVehicleHigh;
    /**
     * 核发机关
     */
    @ApiModelProperty(value = "核发机关")
    private String avdIssuingAuthority;
    /**
     * 道路运输证号
     */
    @ApiModelProperty(value = "道路运输证号")
    private String avdRoadTransportCertificateNo;
    /**
     * 发证日期
     */
    @ApiModelProperty(value = "发证日期")
    private String avdIssueDate;
    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色")
    private String avdPlateColor;
    /**
     * 经营许可证号
     */
    @ApiModelProperty(value = "经营许可证号")
    private String avdBusinessLicenseNo;
    /**
     * 经济类型
     */
    @ApiModelProperty(value = "经济类型")
    private String avdEconomicType;
    /**
     * 经营范围
     */
    @ApiModelProperty(value = "经营范围")
    private String avdNatureBusiness;

	/**
	 * 经营组织方式
	 */
	@ApiModelProperty(value = "经营组织方式")
	private String avdOperationOrganizationMode;
    /**
     * 有效期至
     */
    @ApiModelProperty(value = "有效期至")
    private String avdValidUntil;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String avdRemarks;
    /**
     * 附件(以英文分号分割)
     */
    @ApiModelProperty(value = "附件(以英文分号分割)")
    private String avdEnclosure;
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
	private String avxPlateNo;


}
