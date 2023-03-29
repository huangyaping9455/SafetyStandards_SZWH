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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 报警生命周期数据 实体类
 */
@Data
@ApiModel(value = "BaobiaoAlarmsummaryRecord对象", description = "BaobiaoAlarmsummaryRecord对象")
public class BaobiaoAlarmsummaryRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业ID")
    private String deptId;
    @ApiModelProperty(value = "公司名称")
    private String company;
    @ApiModelProperty(value = "车辆ID")
    private String VehId;
    @ApiModelProperty(value = "车牌号")
    private String platenumber;
    @ApiModelProperty(value = "车牌颜色")
    private String color;
    @ApiModelProperty(value = "营运类型")
    private String operattype;
    @ApiModelProperty(value = "报警ID")
    private String AlarmReportID;
    @ApiModelProperty(value = "开始时间")
    private String BeginTime;
    @ApiModelProperty(value = "报警类型")
    private String AlarmType;
    @ApiModelProperty(value = "经度")
    private String Longitude;
    @ApiModelProperty(value = "纬度")
    private String Latitude;
    @ApiModelProperty(value = "速度")
    private String Velocity;
    @ApiModelProperty(value = "方向")
    private String Angle;
    @ApiModelProperty(value = "道路等级")
    private String Road_Level;
    @ApiModelProperty(value = "道路限速")
    private String Road_Limited;
    @ApiModelProperty(value = "持续时间")
    private String KeepTime;
    @ApiModelProperty(value = "报警等级(99:预警；:1:一级;2:二级;3:三级)")
    private Integer level;
    @ApiModelProperty(value = "预警时间")
    private String warningTime;
    @ApiModelProperty(value = "一级报警时间")
    private String firstAlarmTime;
    @ApiModelProperty(value = "二级报警时间")
    private String secondAlarmTime;
    @ApiModelProperty(value = "三级报警时间")
    private String threeLevelAlarmTime;
    @ApiModelProperty(value = "四级报警时间")
    private String fourLevelAlarmTime;
    @ApiModelProperty(value = "五级报警时间")
    private String fiveLevelAlarmTime;
    @ApiModelProperty(value = "核警时间")
    private String verifyTime;
    @ApiModelProperty(value = "核警人")
    private String verifyName;
    @ApiModelProperty(value = "处警时间")
    private String chuJingTime;
    @ApiModelProperty(value = "处警人")
    private String chuJingName;
    @ApiModelProperty(value = "申诉时间")
    private String appealOfTime;
    @ApiModelProperty(value = "申诉人")
    private String appealOfName;
    @ApiModelProperty(value = "申诉审核时间")
    private String secondOfTime;
    @ApiModelProperty(value = "申诉审核人")
    private String secondOfName;
    @ApiModelProperty(value = "二次处理人")
    private String twicechuliren;
    @ApiModelProperty(value = "二次处理时间")
    private String twicechulishijian;
    @ApiModelProperty(value = "最终处理时间")
    private String finalProcessingTime;
    @ApiModelProperty(value = "最终处理人")
    private String finalProcessingName;
    @ApiModelProperty(value = "时间轴底部List")
    private BaobiaoAlarmsummaryRecord xiajiList;
    @ApiModelProperty(value = "预警消息")
    private String warningRemark;
    @ApiModelProperty(value = "预警消息时间")
    private String warningRemarkTime;
    @ApiModelProperty(value = "一级报警消息")
    private String firstAlarmRemark;
    @ApiModelProperty(value = "一级报警消息时间")
    private String firstAlarmRemarkTime;
    @ApiModelProperty(value = "二级报警消息")
    private String secondAlarmRemark;
    @ApiModelProperty(value = "二级报警消息时间")
    private String secondAlarmRemarkTime;
    @ApiModelProperty(value = "三级报警消息")
    private String threeLevelAlarmRemark;
    @ApiModelProperty(value = "三级报警消息时间")
    private String threeLevelAlarmRemarkTime;
    @ApiModelProperty(value = "四级报警消息")
    private String fourLevelAlarmRemark;
    @ApiModelProperty(value = "四级报警消息时间")
    private String fourLevelAlarmRemarkTime;
    @ApiModelProperty(value = "五级报警消息")
    private String fiveLevelAlarmRemark;
    @ApiModelProperty(value = "五级报警消息时间")
    private String fiveLevelAlarmRemarkTime;
    @ApiModelProperty(value = "核警消息")
    private String verifyRemark;
    @ApiModelProperty(value = "处警消息")
    private String chuJingRemark;
    @ApiModelProperty(value = "申诉审核消息")
    private String secondOfRemark;
    @ApiModelProperty(value = "二次处理消息")
    private String twicechulishiRemark;
}
