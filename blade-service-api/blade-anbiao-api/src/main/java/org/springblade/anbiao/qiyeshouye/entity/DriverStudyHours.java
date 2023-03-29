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
@TableName("anbiao_jiashiyuan_study_hours")
@ApiModel(value = "DriverStudyHours对象", description = "DriverStudyHours对象")
public class DriverStudyHours implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private Integer id;

	/**
	 * 企业ID
	 */
	@ApiModelProperty(value = "企业ID")
	private String deptid;

	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	private String pid;

	/**
	 * 驾驶员名称
	 */
	@ApiModelProperty(value = "驾驶员名称")
	private String name;

	/**
	 * 企业code（运安课堂）
	 */
	@ApiModelProperty(value = "企业code（运安课堂）")
	private String code;

	/**
	 * 企业名称
	 */
	@ApiModelProperty(value = "企业名称")
	private String deptname;

	/**
	 * 课程名称
	 */
	@ApiModelProperty(value = "课程名称")
	private String course;

	/**
	 * 统计日期
	 */
	@ApiModelProperty(value = "统计日期")
	private String date;

	/**
	 * 总学时
	 */
	@ApiModelProperty(value = "总学时")
	private Double totalhours;

	/**
	 * 已完成学时
	 */
	@ApiModelProperty(value = "已完成学时")
	private Double donehours;

	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "操作时间")
	private String caozuoshijian;

	/**
	 * id（运安课堂）
	 */
	@ApiModelProperty(value = "id（运安课堂）")
	private String comid;

	/**
	 * 驾驶员ID
	 */
	@ApiModelProperty(value = "驾驶员ID")
	private String jiashiyuanid;

}
