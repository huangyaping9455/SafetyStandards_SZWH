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
import java.math.BigDecimal;

/**
 * 实体类
 *
 * @author hyp
 */
@Data
@TableName("baobiao_dynamic_random_info")
@ApiModel(value = "DynamicRandomInfo对象", description = "DynamicRandomInfo对象")
public class DynamicRandomInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private int id;
	@ApiModelProperty(value = "车辆ID")
	private String vehId;
	@ApiModelProperty(value = "处置措施")
	private String chuzhicuoshi;
	@ApiModelProperty(value = "处置时间")
	private String chuzhishijian;
	@ApiModelProperty(value = "受理人")
	private String shouliren;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "创建时间")
	private String createtime;
	@ApiModelProperty(value = "值班人员")
	private String createname;
	@ApiModelProperty(value = "反馈时间")
	private String fankuishijian;
	@ApiModelProperty(value = "受理结果")
	private String shoulijieguo;
	@ApiModelProperty(value = "创建者ID")
	private int createId;
	@ApiModelProperty(value = "企业ID")
	private int deptId;
	@ApiModelProperty(value = "道路名称")
	private String roadname;
	@ApiModelProperty(value = "是否超速")
	private int ischaosu;
	@ApiModelProperty(value = "是否疲劳")
	private int ispilao;
	@ApiModelProperty(value = "是否其他报警")
	private int isqita;
	@ApiModelProperty(value = "速度")
	private int velocity;
	@ApiModelProperty(value = "是否在线")
	private int iszaixian;
	@ApiModelProperty(value = "更新时间")
	private String systime;
	@ApiModelProperty(value = "车辆牌照")
	private String platenumber;
	@ApiModelProperty(value = "是否报警")
	private int alarm;
	@ApiModelProperty(value = "报警类型")
	private String alarmNote;
	@ApiModelProperty(value = "经度")
	private BigDecimal latitude;
	@ApiModelProperty(value = "维度")
	private BigDecimal longitude;
	@ApiModelProperty(value = "车牌颜色")
	private String veColor;
}
