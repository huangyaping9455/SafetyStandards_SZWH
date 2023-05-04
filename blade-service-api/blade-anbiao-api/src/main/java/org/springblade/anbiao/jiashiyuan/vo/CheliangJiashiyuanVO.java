package org.springblade.anbiao.jiashiyuan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "JiaShiYuanVO对象", description = "JiaShiYuanVO对象")
public class CheliangJiashiyuanVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "车辆ID")
	private String vehid;

	@ApiModelProperty(value = "驾驶员ID")
	private String jiashiyuanid;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "是否被绑定，1：已绑定")
	private int status = 0;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "企业名称")
	private String deptname;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

}
