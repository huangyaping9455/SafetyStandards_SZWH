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
import java.time.LocalDateTime;

/**
 * 实体类
 */
@Data
@ApiModel(value = "BaojingTJMX对象", description = "BaojingTJMX对象")
public class BaojingTJMX implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Integer xuhao;
    @ApiModelProperty(value = "报警ID")
    private String alarmReportID;
    @ApiModelProperty(value = "车辆ID")
    private String vehid;
    @ApiModelProperty(value = "企业ID")
    private Integer deptId;
    @ApiModelProperty(value = "企业名称")
    private String deptName;
    @ApiModelProperty(value = "违规驾驶员")
    private String jiashiyuanxingming;
    @ApiModelProperty(value = "车牌号")
    private String cheliangpaizhao;
    @ApiModelProperty(value = "车牌颜色")
    private String chepaiyanse;
    @ApiModelProperty(value = "报警类型")
    private String alarmtype;
    @ApiModelProperty(value = "开始时间")
    private String beginTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "推送时间")
    private LocalDateTime cutoffTime;
    @ApiModelProperty(value = "处理结果")
    private String endresult;
    @ApiModelProperty(value = "处理时间")
    private String chulishijian;
    @ApiModelProperty(value = "处理形式")
    private String chulixingshi;
    @ApiModelProperty(value = "处理描述")
    private String chulimiaoshu;
    @ApiModelProperty(value = "处理状态")
    private String chulizhuangtai;
    @ApiModelProperty(value = "处理人")
    private String chuliren;
    @ApiModelProperty(value = "处理人id")
    private Integer chulirenid;
    @ApiModelProperty(value = "申诉状态")
    private String shensuzhuangtai;
    @ApiModelProperty(value = "申诉形式")
    private String shensuxingshi;
    @ApiModelProperty(value = "申诉描述")
    private String shensumiaoshu;
    @ApiModelProperty(value = "申诉审核标识（0:申诉审核中;1:申诉通过;2:申诉驳回）")
    private Integer shensushenhebiaoshi;
    @ApiModelProperty(value = "申诉审核人")
    private String shensushenheren;
    @ApiModelProperty(value = "申诉审核时间")
    private String shensushenheshijian;
    @ApiModelProperty(value = "申诉审核意见")
    private String shensushenheyijian;
    @ApiModelProperty(value = "二次处理形式")
    private String twicechulixingshi;
    @ApiModelProperty(value = "二次处理描述")
    private String twicechulimiaoshu;
    @ApiModelProperty(value = "二次处理人")
    private String twicechuliren;
    @ApiModelProperty(value = "二次处理时间")
    private String twicechulishijian;
    @ApiModelProperty(value = "二次处理人ID")
    private String twicechulirenid;
    @ApiModelProperty(value = "处理类型1为处理0为申诉")
    private Integer remark;
    @ApiModelProperty(value = "速度")
    private Integer velocity;
    @ApiModelProperty(value = "违规时间")
    private String wgtime;
    @ApiModelProperty(value = "领导签名")
    private String qianming;

}
