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
 * GPS报警数据 实体类
 */
@Data
@ApiModel(value = "AlarmInfo对象", description = "AlarmInfo对象")
public class AlarmInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键alarmreportid")
    private String alarmreportid;
    @ApiModelProperty(value = "报警id")
    private String alarmid;
    @ApiModelProperty(value = "gps车辆id")
    private Integer vehid;
    @ApiModelProperty(value = "公司名称")
    private String company;
    @ApiModelProperty(value = "车牌号")
    private String platenumber;
    @ApiModelProperty(value = "车牌颜色")
    private String color;
    @ApiModelProperty(value = "营运类型")
    private String operattype;
    @ApiModelProperty(value = "报警类型")
    private String alarmtype;
    @ApiModelProperty(value = "推送时间")
    private String cutofftime;
    @ApiModelProperty(value = "开始时间")
    private String beginTime;
    @ApiModelProperty(value = "超速百分比")
    private String chaosubaifenbi;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "道路名称")
    private String roadName;
    @ApiModelProperty(value = "最大速度")
    private Integer maxspeed;
    @ApiModelProperty(value = "道路限速")
    private Integer roadLimited;
    private String roadLimited2;
    @ApiModelProperty(value = "持续时间")
    private Integer keeptime;
    @ApiModelProperty(value = "是否处理")
    private String shifouchuli;
    @ApiModelProperty(value = "移动距离show")
    private String distanceShow;
    @ApiModelProperty(value = "处理时间")
    private String chulishijian;
    @ApiModelProperty(value = "核定时间")
    private String disposealarmtime;
    @ApiModelProperty(value = "处理形式")
    private String alarmcl;
    @ApiModelProperty(value = "处理信息")
    private String alarmclmsg;
    @ApiModelProperty(value = "备注")
    private String alarmhdremark;
    @ApiModelProperty(value = "是否申诉")
    private String shifoushenshu;
    @ApiModelProperty(value = "申诉描述")
    private String shenshumiaoshu;

    @ApiModelProperty(value = "离线时长")
    private String lixianshichang;
    @ApiModelProperty(value = "离线时间")
    private String offlineTime;
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;
    @ApiModelProperty(value = "速度")
    private Integer velocity;
    @ApiModelProperty(value = "角度")
    private Integer angle;
    @ApiModelProperty(value = "是否定位")
    private Integer local;

    private BigDecimal elevation;
    @ApiModelProperty(value = "终端限速")
    private Integer limited;
    @ApiModelProperty(value = "核定状态")
    private Integer passed;
    @ApiModelProperty(value = "系统时间")
    private String time;
    @ApiModelProperty(value = "道路等级")
    private String roadLevel;
    @ApiModelProperty(value = "推送状态")
    private Integer pushstate;
    @ApiModelProperty(value = "核定人")
    private String verifyname;
    @ApiModelProperty(value = "核定时间")
    private String verifytime;
    @ApiModelProperty(value = "终端/平台")
    private Integer analyzemode;
    @ApiModelProperty(value = "系统核定状态")
    private Integer syspassed;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "同步入库时间")
    private String syntime;
    @ApiModelProperty(value = "结束报警速度")
    private Integer endspeed;
    @ApiModelProperty(value = "移动距离")
    private Integer distance;

    private Integer issupplements;
    @ApiModelProperty(value = "二次核定时间")
    private String verifytime2;

    @ApiModelProperty(value = "处理人")
    private String chuliren;
    @ApiModelProperty(value = "处理限时")
    private String chulixianshi;
    @ApiModelProperty(value = "申述限时")
    private String shenshuxianshi;
    @ApiModelProperty(value = "驾驶员姓名")
    private String jiashiyuanming;
    @ApiModelProperty(value = "从业资格证")
    private String congyezigezheng;
    @ApiModelProperty(value = "行驶证")
    private String xinshizheng;
    @ApiModelProperty(value = "是否夜间")
    private String shifouyejian;

    //持续时间展示
    @ApiModelProperty(value = "持续时间")
    private String keeptimeshow;
    @ApiModelProperty(value = "核警账号")
    private String hejingzhanghao;
    @ApiModelProperty(value = "核警时间")
    private String hejingshijian;

    private String disposealarmname;
    @ApiModelProperty(value = "gps处警时间")
    private String gpschujingshijian;

    //不定位、不在线
    private	String xuhao;

    private String cid;

    private String danwei;

    private String chepaihao;

    private String zuihoushujushijian;

    private String zuihoudingweishijian;

    private String tongjiriqi;

    private String zuihouhuichuanshijian;

    private String chepaiyanse;

    private String leixing;

    private String chulitongji;

    private String chuli;

    private String shenshu;

    private String chulixingshi;

    private String chulimiaoshu;

    private String shenhezhuangtai;

    private String shensushenhebiaoshi;
    @ApiModelProperty(value = "公司id")
    private String deptId;
    @ApiModelProperty(value = "区域验证")
    private Integer isRegionV;

    private String isRegionVtitle;
    private String yunguanjushenheShow;

    private String yunguanjushenhe;
    private String flag;
    private int shenheBuTongGuoShu;
    private int butongguoShu;

    private String gongsimingcheng;
    private String yunguanming;
    private String yunguanId;

    //异常详情
    private String fujian;
    private String shensushenheren;
    private String fujian1;
    private String fujian2;
    private String shenshufujian;
    private String shenshuqingkuangqueren;
    private String shenshuchulijieguo;
    private String qingkuangqueren;
    private String chulijieguo;
    private String shenshushijian;
    private String shenshuren;
    private String shensusudu;
    private String shensushenheshijian;

}
