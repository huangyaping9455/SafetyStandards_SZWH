package org.springblade.anbiao.chuchejiancha.page;

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
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AnbiaoCarExamineInfoPage对象", description = "AnbiaoCarExamineInfoPage对象")
public class AnbiaoCarExamineInfoPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业名称")
	private String deptname;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;


}




