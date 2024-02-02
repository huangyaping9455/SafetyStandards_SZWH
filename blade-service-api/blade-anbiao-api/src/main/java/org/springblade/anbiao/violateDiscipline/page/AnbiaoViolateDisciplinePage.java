package org.springblade.anbiao.violateDiscipline.page;

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
@ApiModel(value = "AnbiaoViolateDisciplinePage对象", description = "AnbiaoViolateDisciplinePage对象")
public class AnbiaoViolateDisciplinePage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "统计日期")
	private String date;

	@ApiModelProperty(value = "上传状态，0：未上传，1：已上传")
	private String scstatus;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;


}




