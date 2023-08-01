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
 * 车辆经营许可证实体类
 *
 * @author Blade
 * @since 2022-10-28
 */
@Data
@TableName("anbiao_vehicle_jingyingxukezheng")
@ApiModel(value = "VehicleJingyingxukezheng对象", description = "车辆经营许可证")
public class VehicleJingyingxukezheng implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 经营许可证主键
     */
	@TableId(value = "avj_ids", type = IdType.UUID)
    @ApiModelProperty(value = "经营许可证主键")
    private String avjIds;

    @ApiModelProperty(value = "车辆主键")
    private String avjVehicleIds;
    /**
     * 经营者名称
     */
    @ApiModelProperty(value = "经营者名称")
    private String avjOperatorName;
    /**
     * 许可证编号
     */
    @ApiModelProperty(value = "许可证编号")
    private String avjLicenseNo;
    /**
     * 经营许可证号
     */
    @ApiModelProperty(value = "经营许可证号")
    private String avjBusinessLicense;
    /**
     * 经营截至日期
     */
    @ApiModelProperty(value = "经营截至日期")
    private String avjOperationDeadline;

    @ApiModelProperty(value = "经营许可线路名称")
	private String avjOperatingLineName;
    /**
     * 起点
     */
    @ApiModelProperty(value = "起点")
    private String avjStartingPoint;
    /**
     * 讫点
     */
    @ApiModelProperty(value = "终点")
    private String avjEnding;
    /**
     * 始发站名
     */
    @ApiModelProperty(value = "始发站名")
    private String avjDepartureStationName;
    /**
     * 终到站名
     */
    @ApiModelProperty(value = "终到站名")
    private String avjTerminalStationName;
    /**
     * 始发配载站
     */
    @ApiModelProperty(value = "始发配载站")
    private String avjDepartureLoadingStation;
    /**
     * 终到配载站
     */
    @ApiModelProperty(value = "终到配载站")
    private String avjFinalArrivalAtLoadingStation;
    /**
     * 主要途经地
     */
    @ApiModelProperty(value = "主要途经地")
    private String avjMainRoute;
    /**
     * 停靠站点
     */
    @ApiModelProperty(value = "停靠站点")
    private String avjDockingStation;
    /**
     * 班线客运类型
     */
    @ApiModelProperty(value = "班线客运类型")
    private String avjPassengerTransportType;
    /**
     * 班线类别
     */
    @ApiModelProperty(value = "班线类别")
    private String avjShiftLineCategory;
    /**
     * 日发班次
     */
    @ApiModelProperty(value = "日发班次")
    private String avjDailyDeparture;
    /**
     * 车牌号码
     */
    @ApiModelProperty(value = "车牌号码")
    private String avjLicensePlate;
    /**
     * 客车类型等级
     */
    @ApiModelProperty(value = "客车类型等级")
    private String avjPassengerCarTypeAndGrade;
    /**
     * 客车类型等级有效期
     */
    @ApiModelProperty(value = "客车类型等级有效期")
    private LocalDate avjValidPeriodPassengerCarType;
    /**
     * 车辆技术等级
     */
    @ApiModelProperty(value = "车辆技术等级")
    private String avjTechnicalGradeVehicle;
    /**
     * 班车客运标准编号
     */
    @ApiModelProperty(value = "班车客运标准编号")
    private String avjSerialBusPassengerTransportSign;
    /**
     * 车辆审验起（日期）
     */
    @ApiModelProperty(value = "车辆审验起（日期）")
    private LocalDate avjAnnualReviewVehicles;
    /**
     * 车辆审验止（日期）
     */
    @ApiModelProperty(value = "车辆审验止（日期）")
    private LocalDate avjEndReviewVehicles;
    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    private String avjRemarks;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String avjEnclosure;
    /**
     * 运营机构(盖章)
     */
    @ApiModelProperty(value = "运营机构(盖章)")
    private String avjOperatingAgency;
    /**
     * 许可机关(盖章)
     */
    @ApiModelProperty(value = "许可机关(盖章)")
    private String avjLicensingAuthority;
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


}
