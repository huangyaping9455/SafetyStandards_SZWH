package org.springblade.anbiao.repairs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @since 2023-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_repairs_remark")
@ApiModel(value="AnbiaoRepairsRemark对象", description="")
public class AnbiaoRepairsRemark implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rpdt_id", type = IdType.UUID)
    private String rpdtId;

    @ApiModelProperty(value = "0:待受理，1:待指派，2:已派单，3:转交工单，4:已接单，5:待预约，6:已预约，7:进行中，8:待审核，9:已驳回，10:已完成，11:已取消,12：远程维护,13：现场维护,14:远程维护完结")
    private Integer rpdtType;

    @ApiModelProperty(value = "状态更新时间")
    private String rpdtDate;

    @ApiModelProperty(value = "服务人员ID")
    private String rpdtPersonId;

    @ApiModelProperty(value = "创建时间")
    private String rpdtCreatetime;

    @ApiModelProperty(value = "创建人ID")
    private Integer rpdtCreateid;

    @ApiModelProperty(value = "创建人")
    private String rpdtCreatename;

    @ApiModelProperty(value = "更新时间")
    private String rpdtUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    private Integer rpdtUpdateid;

    @ApiModelProperty(value = "更新人")
    private String rpdtUpdatename;

    @ApiModelProperty(value = "预约地点")
    private String rpdtYwAddress;

    @ApiModelProperty(value = "备注")
    private String rpdtRemark;

    @ApiModelProperty(value = "报修单ID")
    private String rpdtRpId;

	@ApiModelProperty(value = "处理结果")
	private String rpdtDisposeRemark;

	@ApiModelProperty(value = "维修照片，多个以英文逗号隔开")
	private String rpdtDisposeImg;

	@ApiModelProperty(value = "更换配件ID，多个以英文逗号隔开")
	private String rpdtDisposeParts;

	@ApiModelProperty(value = "驾驶员签字")
	private String rpdtDisposeSign;


}
