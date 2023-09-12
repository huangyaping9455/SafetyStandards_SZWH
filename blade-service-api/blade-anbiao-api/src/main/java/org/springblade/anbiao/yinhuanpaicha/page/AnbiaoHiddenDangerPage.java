package org.springblade.anbiao.yinhuanpaicha.page;

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
@ApiModel(value = "AnbiaoHiddenDangerPage对象", description = "AnbiaoHiddenDangerPage对象")
public class AnbiaoHiddenDangerPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业名称")
	private String deptname;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "发现时间")
	private String date;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "隐患类别")
	private String ahdType;

	@ApiModelProperty(value = "排查现场")
	private String ahdAddress;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

	@ApiModelProperty(value = "驾驶员id")
	private String jiashiyuanid;

	@ApiModelProperty(value = "车辆ID")
	private String vehId;
}




