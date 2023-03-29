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

/**
 * 报警生命周期数据 实体类
 */
@Data
@ApiModel(value = "MonitorContral对象", description = "MonitorContral对象")
public class MonitorContral implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID") private Integer ID;
    @ApiModelProperty(value = "车辆ID") private String vehId;
    @ApiModelProperty(value = "车牌号码") private String cheliangpaizhao;
    @ApiModelProperty(value = "车牌颜色") private String chepaiyanse;
    @ApiModelProperty(value = "部门ID") private Integer deptId;
    @ApiModelProperty(value = "部门名称") private String deptName;
    @ApiModelProperty(value = "报警ID") private String alarmGuid;
    @ApiModelProperty(value = "报警类型") private String alarmType;
    @ApiModelProperty(value = "报警时间") private String alarmTime;
    @ApiModelProperty(value = "报警地点") private String alarmLocation;
    @ApiModelProperty(value = "报警等级") private Integer alarmLevel;
    @ApiModelProperty(value = "处理方式 1 电话  2 TTS") private Integer contralType;
    @ApiModelProperty(value = "处理内容") private String contralMsg;
    @ApiModelProperty(value = "创建时间") private String createtime;
    @ApiModelProperty(value = "创建人ID") private Integer createID;
    @ApiModelProperty(value = "创建人") private String createMan;
}
