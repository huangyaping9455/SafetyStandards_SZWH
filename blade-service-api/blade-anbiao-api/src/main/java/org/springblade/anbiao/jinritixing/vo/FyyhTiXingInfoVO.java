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
@ApiModel(value = "FyyhTiXingInfoVO对象", description = "FyyhTiXingInfoVO对象")
public class FyyhTiXingInfoVO extends Jinritixing {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "驾驶员手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "从业类别")
	private String congyeliebie;

	@ApiModelProperty(value = "驾驶员ID")
	private String jiashiyuanid;

	@ApiModelProperty(value = "车辆ID")
	private String cheliangid;

	@ApiModelProperty(value = "数据ID")
	private String shujuid;

	@ApiModelProperty(value = "有效期")
	private String youxiaoqi;

	@ApiModelProperty(value = "是否需要后续处理操作(1:需要)")
	private Integer isdispose = 0;

}
