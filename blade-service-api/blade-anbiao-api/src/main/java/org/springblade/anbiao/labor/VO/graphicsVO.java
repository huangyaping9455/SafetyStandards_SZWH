package org.springblade.anbiao.labor.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description :劳保图形
 * @Author : long
 * @Date :2022/11/4 22:22
 */
@Data
public class graphicsVO {

	@ApiModelProperty(value = "发放数量")
	private Integer aliissuequantity;

	@ApiModelProperty(value = "用品名称")
	private String aliname;

	@ApiModelProperty(value = "发放人数")
	private Integer aliissuepeoplenumber;

}
