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

	@ApiModelProperty(value = "企业ID",required = true)
	private String deptId;

	@ApiModelProperty(value = "检查时间",required = true)
	private String date;

	@ApiModelProperty(value = "驾驶员ID（司机小程序，必传）",required = true)
	private String jsyId;

	@ApiModelProperty(value = "检查状态（已完成、未完成）")
	private String statusshow;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车辆ID")
	private String vehId;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

	@ApiModelProperty(value = "开始日期")
	private String beginTime;

	@ApiModelProperty(value = "结束日期")
	private String endTime;


}




