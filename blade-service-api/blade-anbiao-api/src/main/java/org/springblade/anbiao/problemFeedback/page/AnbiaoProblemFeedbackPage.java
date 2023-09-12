package org.springblade.anbiao.problemFeedback.page;

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
@ApiModel(value = "AnbiaoProblemFeedbackPage对象", description = "AnbiaoProblemFeedbackPage对象")
public class AnbiaoProblemFeedbackPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "企业ID",required = true)
	private String deptId;

	@ApiModelProperty(value = "统计日期",required = true)
	private String pfDate;

	@ApiModelProperty(value = "处理状态，0：未回复，1：已回复")
	private Integer pfStatus;

	@ApiModelProperty(value = "驾驶员ID")
	private String pfDriverId;

	@ApiModelProperty(value = "驾驶员姓名")
	private String pfDriverName;
}




