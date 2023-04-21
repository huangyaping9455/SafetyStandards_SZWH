package org.springblade.anbiao.jiashiyuan.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * Created by you on 2019/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IntoAreaPage对象", description = "IntoAreaPage对象")
public class IntoAreaPage<T> extends BasePage<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "单位id",required = true)
	private Integer deptId;

	@ApiModelProperty(value = "单位名称")
	private String deptName;

	@ApiModelProperty(value = "区域ID")
	private String areaId;

	@ApiModelProperty(value = "区域名称")
	private String area;

	@ApiModelProperty(value = "开始时间（yyyy-MM-dd）", required = true)
	private String beginTime;

	@ApiModelProperty(value = "结束时间（yyyy-MM-dd）", required = true)
	private String endTime;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "是否处理('',未处理，已处理)")
	private String shifouchuli;

	@ApiModelProperty(value = "是否申诉")
	private String shifoushenshu;

}
