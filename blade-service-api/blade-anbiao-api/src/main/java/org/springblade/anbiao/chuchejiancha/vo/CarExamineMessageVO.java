package org.springblade.anbiao.chuchejiancha.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.chuchejiancha.entity.AnbiaoCarExamine;
import org.springblade.core.tool.node.INode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-09-27
 */
@Data
@ApiModel(value = "CarExamineMessageVO对象", description = "CarExamineMessageVO对象")
public class CarExamineMessageVO {

	@ApiModelProperty(value = "检查时间")
	private String dateShow;

	@ApiModelProperty(value = "车辆牌照")
	private String vehNo;

	@ApiModelProperty(value = "驾驶员姓名")
	private String driverName;

	@ApiModelProperty(value = "未做检查次数")
	private Integer count;

	@ApiModelProperty(value = "会议提示")
	private String message;

}
