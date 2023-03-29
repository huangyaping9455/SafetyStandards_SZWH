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
@TableName("baobaio_patrol_info")
@ApiModel(value = "PatrolInfo对象", description = "PatrolInfo对象")
public class PatrolInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private int id;
	@ApiModelProperty(value = "车辆ID")
	private String vehid;
	@ApiModelProperty(value = "车辆牌照")
	private String vehno;
	@ApiModelProperty(value = "类型（0：早；1：中；2：晚）")
	private int type;
	@ApiModelProperty(value = "创建者ID")
	private int createid;
	@ApiModelProperty(value = "创建者")
	private String createname;
	@ApiModelProperty(value = "创建时间")
	private String createtime;
	@ApiModelProperty(value = "零点")
	private int zero;
	@ApiModelProperty(value = "一点")
	private int one;
	@ApiModelProperty(value = "二点")
	private int two;
	@ApiModelProperty(value = "三点")
	private int three;
	@ApiModelProperty(value = "四点")
	private int four;
	@ApiModelProperty(value = "五点")
	private int five;
	@ApiModelProperty(value = "六点")
	private int six;
	@ApiModelProperty(value = "七点")
	private int seven;
	@ApiModelProperty(value = "八点")
	private int eight;
	@ApiModelProperty(value = "九点")
	private int nine;
	@ApiModelProperty(value = "十点")
	private int ten;
	@ApiModelProperty(value = "十一点")
	private int eleven;
	@ApiModelProperty(value = "十二点")
	private int twelve;
	@ApiModelProperty(value = "十三点")
	private int thirteen;
	@ApiModelProperty(value = "十四点")
	private int fourteen;
	@ApiModelProperty(value = "十五点")
	private int fiveteen;
	@ApiModelProperty(value = "十六点")
	private int sixteen;
	@ApiModelProperty(value = "十七点")
	private int seventeen;
	@ApiModelProperty(value = "十八点")
	private int eighteen;
	@ApiModelProperty(value = "十九点")
	private int nineteen;
	@ApiModelProperty(value = "二十点")
	private int twenty;
	@ApiModelProperty(value = "二十一点")
	private int twentyone;
	@ApiModelProperty(value = "二十二点")
	private int twentytwo;
	@ApiModelProperty(value = "二十三点")
	private int twentythree;
	@ApiModelProperty(value = "企业ID")
	private int deptid;
	@ApiModelProperty(value = "更新时间")
	private String updatetime;
	@ApiModelProperty(value = "统计日期")
	private String date;

}
