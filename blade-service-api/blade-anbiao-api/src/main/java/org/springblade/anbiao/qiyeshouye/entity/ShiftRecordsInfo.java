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
package org.springblade.anbiao.qiyeshouye.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author hyp
 */
@Data
@ApiModel(value = "ShiftRecordsInfo对象", description = "ShiftRecordsInfo对象")
public class ShiftRecordsInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private int id;
	@ApiModelProperty(value = "deptId")
	private int deptId;
	@ApiModelProperty(value = "停运车辆数")
	private int offstreamnum;
	@ApiModelProperty(value = "设备故障数")
	private int outofordernum;
	@ApiModelProperty(value = "其他原因数")
	private int otherreasonnum;
	@ApiModelProperty(value = "超速车辆数")
	private int chaosunum;
	@ApiModelProperty(value = "疲劳车辆数")
	private int pilaonum;
	@ApiModelProperty(value = "夜间车辆数")
	private int yejiannum;
	@ApiModelProperty(value = "异常车辆数")
	private int yichangnum;
	@ApiModelProperty(value = "抽烟车辆数")
	private int chouyannum;
	@ApiModelProperty(value = "打电话车辆数")
	private int dadianhuanum;
	@ApiModelProperty(value = "生理疲劳车辆数")
	private int shenglipilaonum;
	@ApiModelProperty(value = "分神车辆数")
	private int fenshennum;
	@ApiModelProperty(value = "主动安全违规处理")
	private String zhudonganquanchuli;
	@ApiModelProperty(value = "北斗违规处理")
	private String beidouweiguichuli;
	@ApiModelProperty(value = "北斗违规内容")
	private String beidouweigui;
	@ApiModelProperty(value = "主动安全违规内容")
	private String zhudonganquan;
	@ApiModelProperty(value = "异常处理记录")
	private String yichangchuli;
	@ApiModelProperty(value = "创建时间")
	private String createTime;
	@ApiModelProperty(value = "监控员")
	private String createMan;
	@ApiModelProperty(value = "监控员ID")
	private int createID;
	@ApiModelProperty(value = "预警信息次数（自动）")
	private int yujingxinxishu;
	@ApiModelProperty(value = "提醒信息次数（手动）")
	private int tixingxinxi;
	@ApiModelProperty(value = "车辆总数")
	private int cheliangzongshu;
	@ApiModelProperty(value = "在线车辆数")
	private int cheliangzaixianshu;
	@ApiModelProperty(value = "离线车辆数")
	private int chelianglixianshu;
	@ApiModelProperty(value = "车辆离线数4小时以上")
	private int chelianglixianshu4;
	@ApiModelProperty(value = "交班人")
	private String jiaobanren;
	@ApiModelProperty(value = "接班人")
	private String jiebanren;
	@ApiModelProperty(value = "交接时间")
	private String jiaojieshijian;
	@ApiModelProperty(value = "交接备注")
	private String jiaojiebeizhu;
	@ApiModelProperty(value = "天气")
	private String tianqi;
	@ApiModelProperty(value = "交接时间区间")
	private String dateShow;
	@ApiModelProperty(value = "北斗---车辆牌照及报警类型")
	private String bdalarmremark;
	@ApiModelProperty(value = "北斗---违规处理")
	private String bdremark;
	@ApiModelProperty(value = "主动---车辆牌照及报警类型")
	private String zdalarmremark;
	@ApiModelProperty(value = "主动---违规处理")
	private String zdremark;
	@ApiModelProperty(value = "报警类型")
	private String alarmType;
	@ApiModelProperty(value = "车辆ID")
	private String vehId;
	@ApiModelProperty(value = "班次")
	private String banci;
}
