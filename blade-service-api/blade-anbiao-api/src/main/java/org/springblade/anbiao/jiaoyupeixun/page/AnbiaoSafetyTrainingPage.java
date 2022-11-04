package org.springblade.anbiao.jiaoyupeixun.page;

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
@ApiModel(value = "AnbiaoSafetyTrainingPage对象", description = "AnbiaoSafetyTrainingPage对象")
public class AnbiaoSafetyTrainingPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业名称")
	private String deptname;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "培训日期")
	private String date;

	@ApiModelProperty(value = "培训主题")
	private String astTrainingTopic;

	@ApiModelProperty(value = "培训类型")
	private String astTrainingCategory;

	@ApiModelProperty(value = "驾驶员ID")
	private String aadApIds;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;


}




