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
package org.springblade.alarm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 驾驶员行为报警 实体类
 *
 * @author hyp
 * @since 2019-05-12
 */
@Data
@TableName("baobiao_driverbehavior")
@ApiModel(value = "Driverbehavior对象", description = "Driverbehavior对象")
public class Driverbehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;
    @ApiModelProperty(value = "报警id")
    private Long alarmReportID;
    @TableField("AlarmNumber")
    private String alarmNumber;
    @TableField("AlarmID")
    private Integer alarmID;
    /**
     * 车辆id
     */
    @ApiModelProperty(value = "车辆id")
    @TableField("VehId")
    private String vehId;
    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String plate;
    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色")
    private String color;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("FlagState")
    private String flagState;
    /**
     * 报警类型
     */
    @ApiModelProperty(value = "报警类型")
    @TableField("AlarmType")
    private String alarmType;
    /**
     * 报警等级
     */
    @ApiModelProperty(value = "报警等级")
    @TableField("Alarmlevel")
    private String alarmlevel;
    /**
     * 速度
     */
    @ApiModelProperty(value = "速度")
    @TableField("Velocity")
    private Integer velocity;
    /**
     * 海拔
     */
    @ApiModelProperty(value = "海拔")
    @TableField("High")
    private BigDecimal high;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @TableField("Lon")
    private BigDecimal longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @TableField("Lat")
    private BigDecimal latitude;
    /**
     * gps设备时间
     */
    @ApiModelProperty(value = "gps设备时间")
    @TableField("GpsTime")
    private LocalDateTime gpsTime;
    /**
     * 车辆状态
     */
    @ApiModelProperty(value = "车辆状态")
    @TableField("VehStatus")
    private String vehStatus;
    /**
     * 系统时间
     */
    @ApiModelProperty(value = "系统时间")
    @TableField("A_Time")
    private LocalDateTime aTime;
    @TableField("AlarmIDNumber")
    private String alarmIDNumber;
    /**
     * 自编号
     */
    @ApiModelProperty(value = "自编号")
    @TableField("OwnNo")
    private String ownNo;
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private String company;
    /**
     * 有无视频
     */
    @ApiModelProperty(value = "有无视频")
    @TableField("IsVideo")
    private Boolean isVideo;
    /**
     * 有无照片
     */
    @ApiModelProperty(value = "有无照片")
    @TableField("IsPicture")
    private Boolean isPicture;
    /**
     * 道路名称
     */
    @ApiModelProperty(value = "道路名称")
    @TableField("RoadName")
    private String roadName;

    @ApiModelProperty(value = "核定状态")
    @TableField("StateEx")
    private String stateEx;
    /**
     * 同步时间
     */
    @ApiModelProperty(value = "同步时间")
    @TableField("insertTime")
    private LocalDateTime insertTime;
	/**
	 * remark 判断是处理还是申述 0 申述 1处理
	 */
	@ApiModelProperty(value = "判断是处理还是申述 0 申述 1处理")
	private Integer remark;
}

