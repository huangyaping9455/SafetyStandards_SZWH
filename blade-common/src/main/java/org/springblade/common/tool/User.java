package org.springblade.common.tool;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hyp
 * @create 2023-03-07 11:16
 */
@Data
public class User {

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "地区")
	private String area;

	@ApiModelProperty(value = "性别")
	private String sex;

}
