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
@TableName("anbiao_jiashiyuan_study_url")
@ApiModel(value = "DriverStudyUrl对象", description = "DriverStudyUrl对象")
public class DriverStudyUrl implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private Integer id;

	/**
	 * 驾驶员ID
	 */
	@ApiModelProperty(value = "驾驶员ID")
	private String jiashiyuanid;

	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	/**
	 * 企业ID
	 */
	@ApiModelProperty(value = "企业ID")
	private String deptid;

	/**
	 * 一键登录学习链接地址
	 */
	@ApiModelProperty(value = "一键登录学习链接地址")
	private String studyurl;

	/**
	 * yakt表示运安课堂，ykya表示粤考运安
	 */
	@ApiModelProperty(value = "yakt表示运安课堂，ykya表示粤考运安")
	private String type;

	/**
	 * 是否已注册
	 */
	@ApiModelProperty(value = "是否已注册")
	private String member;

	/**
	 * 是否已通过身份认证
	 */
	@ApiModelProperty(value = "是否已通过身份认证")
	private String verified;

	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "操作时间")
	private String caozuoshijian;


}
