package org.springblade.anbiao.guanlijigouherenyuan.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @program: SafetyStandards
 * @description: Huiyirenyuan分页
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "HuiyirenyuanPage对象", description = "HuiyirenyuanPage对象")
public class HuiyirenyuanPage <T> extends BasePage<T> {


	private static final long serialVersionUID = 1L;
	/**
	 * 企业 id
	 */
	@ApiModelProperty(value = "单位id")
	private Integer deptId;
}
