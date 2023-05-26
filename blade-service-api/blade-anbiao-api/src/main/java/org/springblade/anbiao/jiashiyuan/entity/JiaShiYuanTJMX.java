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
package org.springblade.anbiao.jiashiyuan.entity;

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
public class JiaShiYuanTJMX implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Integer xuhao = 0;
	@ApiModelProperty(value = "企业id")
	private String deptId;
    @ApiModelProperty(value = "企业名称")
    private String deptName;
	@ApiModelProperty(value = "驾驶员Id")
	private String jiashiyuanId;
	@ApiModelProperty(value = "姓名")
	private String a;
	@ApiModelProperty(value = "入职表")
	private String a1 = "0";
	@ApiModelProperty(value = "身份证")
	private String a2 = "0";
	@ApiModelProperty(value = "驾驶证")
	private String a3 = "0";
	@ApiModelProperty(value = "从业资格证")
	private String a4 = "0";
	@ApiModelProperty(value = "体检表")
	private String a5 = "0";
	@ApiModelProperty(value = "岗前培训表")
	private String a6 = "0";
	@ApiModelProperty(value = "三年无重大责任事故证明")
	private String a7 = "0";
	@ApiModelProperty(value = "安全责任书")
	private String a8 = "0";
	@ApiModelProperty(value = "危害告知书")
	private String a9 = "0";
	@ApiModelProperty(value = "劳动合同")
	private String a10 = "0";
	@ApiModelProperty(value = "行驶证（车头）")
	private String b1 = "0";
	@ApiModelProperty(value = "运输证（车头）")
	private String b2 = "0";
	@ApiModelProperty(value = "性能报告（车头）")
	private String b3 = "0";
	@ApiModelProperty(value = "登记证书（车头）")
	private String b4 = "0";
	@ApiModelProperty(value = "行驶证（挂车）")
	private String b5 = "0";
	@ApiModelProperty(value = "运输证（挂车）")
	private String b6 = "0";
	@ApiModelProperty(value = "性能报告（挂车）")
	private String b7 = "0";
	@ApiModelProperty(value = "登记证书（挂车）")
	private String b8 = "0";
	@ApiModelProperty(value = "驾驶员保险")
	private String c1 = "0";
	@ApiModelProperty(value = "车辆保险（车头）")
	private String c2 = "0";
	@ApiModelProperty(value = "车辆保险（挂车）")
	private String c3 = "0";

	@ApiModelProperty(value = "车头牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "挂车牌照")
	private String cheliangpaizhaoGUA;

	@ApiModelProperty(value = "车辆ID")
	private String vehicleId;

	@ApiModelProperty(value = "车辆ID")
	private String vehicleIdGUA;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "驾驶员类型")
	private String jiashiyuanleixing;

	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	@ApiModelProperty(value = "所属地市")
	private String areaName;

	@ApiModelProperty(value = "驾驶员Id")
	private String id;

	private String type;

}
