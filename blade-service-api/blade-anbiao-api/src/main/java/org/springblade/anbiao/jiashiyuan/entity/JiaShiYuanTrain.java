package org.springblade.anbiao.jiashiyuan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by you on 2022/08/16
 * @author Administrator
 */
@Data
@ApiModel(value = "JiaShiYuanTrain对象", description = "JiaShiYuanTrain对象")
public class JiaShiYuanTrain implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "驾驶员ID")
	private String id;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "企业ID")
	private Integer deptId;

	@ApiModelProperty(value = "电子签名")
	private String signatrue;
}
