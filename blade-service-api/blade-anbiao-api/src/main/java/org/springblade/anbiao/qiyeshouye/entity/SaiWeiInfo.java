package org.springblade.anbiao.qiyeshouye.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hyp
 * @create 2022-05-23 10:14
 */
@Data
@ApiModel(value = "SaiWeiInfo对象", description = "SaiWeiInfo对象")
public class SaiWeiInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "token")
	private String token;
	@ApiModelProperty(value = "uuid")
	private String requestid;
	@ApiModelProperty(value = "signature")
	private String signature;
	@ApiModelProperty(value = "Api账号")
	private String account;
	@ApiModelProperty(value = "Api密码")
	private String accesskey;
}
