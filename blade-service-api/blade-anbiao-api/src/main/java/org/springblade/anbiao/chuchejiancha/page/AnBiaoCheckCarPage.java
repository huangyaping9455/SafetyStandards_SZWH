package org.springblade.anbiao.chuchejiancha.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(value = "AnBiaoCheckCarPage对象", description = "AnBiaoCheckCarPage对象")
public class AnBiaoCheckCarPage<T> extends BasePage<T> {
	private static final long serialVersionUID=1L;

	@ApiModelProperty(value = "企业ID", required = true)
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "车辆ID")
	private String vehId;

	@ApiModelProperty(value = "统计日期",required = true)
	private String date;

	@ApiModelProperty(value = "创建人ID")
	private String createId;

	@ApiModelProperty(value = "开始日期")
	private String beginTime;

	@ApiModelProperty(value = "结束日期")
	private String endTime;

	private String[] vehIdidss;

}
