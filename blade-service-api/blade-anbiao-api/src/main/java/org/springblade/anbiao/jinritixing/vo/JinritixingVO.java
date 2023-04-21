/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.jinritixing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.jinritixing.entity.Jinritixing;

/**
 * 视图实体类
 * @author 呵呵哒
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "JinritixingVO对象", description = "JinritixingVO对象")
public class JinritixingVO extends Jinritixing {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "企业 id", required = true)
	private Integer deptId;
	@ApiModelProperty(value = "企业名称")
	private String deptName;
	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;
	@ApiModelProperty(value = "车辆颜色")
	private String chepaiyanse;
	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;
	@ApiModelProperty(value = "数量")
	private Integer counts;
	@ApiModelProperty(value = "路径")
	private String url;
	@ApiModelProperty(value = "预警说明")
	private String shuoming;
	@ApiModelProperty(value = "风险一级数量")
	private Integer	one=0;
	@ApiModelProperty(value = "风险二级数量")
	private Integer	two=0;
	@ApiModelProperty(value = "风险三级数量")
	private Integer	three=0;
	@ApiModelProperty(value = "预警项")
	private String yujingxiang;
	@ApiModelProperty(value = "预警分类")
	private String yujingfenlei;
	@ApiModelProperty(value = "级别")
	private int type;
	@ApiModelProperty(value = "级别value")
	private String typevalue;

	@ApiModelProperty(value = "风险等级提示（一级）")
	private String riskType;
	@ApiModelProperty(value = "风险数提示（一级）")
	private int riskValue;

	@ApiModelProperty(value = "风险等级提示（二级）")
	private String twoRiskType;
	@ApiModelProperty(value = "风险数提示（二级）")
	private int twoRiskValue;

	@ApiModelProperty(value = "风险等级提示（三级）")
	private String threeRiskType;
	@ApiModelProperty(value = "风险数提示（三级）")
	private int threeRiskValue;
}
