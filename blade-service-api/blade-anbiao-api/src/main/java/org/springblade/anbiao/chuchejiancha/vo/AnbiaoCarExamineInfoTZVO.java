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
@ApiModel(value = "AnbiaoCarExamineInfoTZVO对象", description = "AnbiaoCarExamineInfoTZVO对象")
public class AnbiaoCarExamineInfoTZVO {

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "日期")
	private String date;

	@ApiModelProperty(value = "检查状态")
	private String statusshow;


}
