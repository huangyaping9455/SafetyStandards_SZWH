package org.springblade.anbiao.anquanhuiyi.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hyp
 * @create 2023-02-15 11:11
 */
@Data
@ApiModel(value = "AnbiaoAnquanhuiyiVO对象", description = "AnbiaoAnquanhuiyiVO对象")
public class AnbiaoAnquanhuiyiVO {

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "会议名称")
	private String huiyimingcheng;

	@ApiModelProperty(value = "会议时间")
	private String dateShow;

	@ApiModelProperty(value = "会议提示")
	private String message;
}
