package org.springblade.anbiao.repairs.page;

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
@ApiModel(value = "AnbiaoRepairsDeptPage对象", description = "AnbiaoRepairsDeptPage对象")
public class AnbiaoRepairsDeptPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "报修日期",required = true)
	private String date;

	@ApiModelProperty(value = "报修日期结束日期",required = true)
	private String endDate;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "维修人员ID")
	private String driverId;

	@ApiModelProperty(value = "报修状态")
	private Integer rpStatus;

	@ApiModelProperty(value = "工单类型，1：新装工单，2：维修工单")
	private Integer rpType;

	@ApiModelProperty(value = "回访状态（已回访、未回访）")
	private String hfstatus;

	@ApiModelProperty(value = "客户单位名称")
	private String rpDeptName;

	@ApiModelProperty(value = "联系电话")
	private String rpRelationPhone;

	@ApiModelProperty(value = "人员姓名")
	private String rpName;

}




