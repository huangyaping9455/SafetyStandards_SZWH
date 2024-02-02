package org.springblade.anbiao.realnameRegistration.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-10-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AnbiaoRealnameRegistrationPage对象", description = "AnbiaoRealnameRegistrationPage对象")
public class AnbiaoRealnameRegistrationPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "统计日期")
	private String date;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

}




