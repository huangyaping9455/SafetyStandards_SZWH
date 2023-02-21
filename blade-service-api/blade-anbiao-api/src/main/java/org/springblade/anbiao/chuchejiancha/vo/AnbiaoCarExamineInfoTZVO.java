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

	@ApiModelProperty(value = "企业Id")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "车辆Id")
	private String vehId;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "驾驶员")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "检查日期")
	private String date;

	@ApiModelProperty(value = "检查状态，0：正常，1：异常，2：审核通过，3：审核驳回，4：已整改，5：验收归档，6：未出车")
	private Integer status;

	@ApiModelProperty(value = "检查情况")
	private String statusshow;

	@ApiModelProperty(value = "是否签名")
	private String jcrsignatrueshow;

	@ApiModelProperty(value = "检查天数")
	private Integer count = 0;


}
