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
 * @author hyp
 */
@Data
@TableName("anbiao_weeks_hidden_trouble")
@ApiModel(value = "AnBiaoWeeksHiddenTrouble对象", description = "AnBiaoWeeksHiddenTrouble对象")
public class AnBiaoWeeksHiddenTrouble implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private int id;

	@ApiModelProperty(value = "企业ID")
	private int deptId;

	@ApiModelProperty(value = "是否删除")
	private int isdelete = 0;

	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@ApiModelProperty(value = "创建人")
	private String createUser;

	@ApiModelProperty(value = "类型，1：企业；2：车辆")
	private int type;

	@ApiModelProperty(value = "车辆ID")
	private String vehId;

	@ApiModelProperty(value = "检查人员")
	private String examineUser;

	@ApiModelProperty(value = "检查附件")
	private String examineImg;

	@ApiModelProperty(value = "检查情况")
	private String examineSituation;

	@ApiModelProperty(value = "检查项")
	private String examineItem;

	@ApiModelProperty(value = "检查日期")
	private String date;

}
