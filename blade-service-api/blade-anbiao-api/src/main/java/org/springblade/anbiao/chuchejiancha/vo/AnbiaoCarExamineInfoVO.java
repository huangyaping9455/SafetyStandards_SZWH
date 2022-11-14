package org.springblade.anbiao.chuchejiancha.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamineInfo;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AnbiaoCarExamineInfoVO对象", description = "AnbiaoCarExamineInfoVO对象")
public class AnbiaoCarExamineInfoVO extends AnbiaoCarExamineInfo {

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "检查状态")
	private String statusshow;




}
