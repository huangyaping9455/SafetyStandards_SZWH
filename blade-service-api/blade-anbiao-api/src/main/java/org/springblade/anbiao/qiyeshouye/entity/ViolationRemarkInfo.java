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

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("baobiao_violation_remark_info")
@ApiModel(value = "ViolationRemarkInfo对象", description = "ViolationRemarkInfo对象")
public class ViolationRemarkInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "AlarmGuid")
	private String AlarmGuid;
	@ApiModelProperty(value = "车辆ID")
	private String vehId;
	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;
	@ApiModelProperty(value = "报警ID")
	private String AlarmReportID;
	@ApiModelProperty(value = "车辆牌照")
	private String plateNumber;
	@ApiModelProperty(value = "消息内容")
	private String message;
	@ApiModelProperty(value = "接话人")
	private String playphone;
	@ApiModelProperty(value = "电话内容")
	private String phonemessage;
	@ApiModelProperty(value = "处置情况（违章报警）")
	private String violationdisposalsituation;
	@ApiModelProperty(value = "故障及异常类型")
	private String malfunction;
	@ApiModelProperty(value = "处置情况（GPS故障及异常）")
	private String gpsdisposalsituation;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "创建时间")
	private String createtime;
	@ApiModelProperty(value = "监控员")
	private String createname;
	@ApiModelProperty(value = "监控员ID")
	private int createid;
	@ApiModelProperty(value = "企业ID")
	private int deptid;
	@ApiModelProperty(value = "报警时间")
	private String cutofftime;
	@ApiModelProperty(value = "道路名称")
	private String roadname;
	@ApiModelProperty(value = "日")
	private String days;
	@ApiModelProperty(value = "时")
	private String hours;
	@ApiModelProperty(value = "分")
	private String minutes;
    @ApiModelProperty(value = "超速")
    private int ischaosu;
    @ApiModelProperty(value = "疲劳")
    private int ispilao;
    @ApiModelProperty(value = "其他紧急报警")
    private int isqita;
	@ApiModelProperty(value = "报警类型")
	private String alarmType;

}
