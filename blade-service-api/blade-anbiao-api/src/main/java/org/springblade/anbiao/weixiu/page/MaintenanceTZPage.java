package org.springblade.anbiao.weixiu.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @Description :
 * @Author : long
 * @Date :2022/11/8 18:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MaintenanceTZPage对象", description = "MaintenanceTZPage对象")
public class MaintenanceTZPage<T> extends BasePage<T> {

	private String	deptId;

	private String date;

	@ApiModelProperty(value = "企业名称")
	private String deptName;
}
