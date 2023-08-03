package org.springblade.anbiao.repairs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

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
@TableName("anbiao_repairs_info")
@ApiModel(value="AnbiaoRepairsInfo对象", description="保修单信息")
public class AnbiaoRepairsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rp_id", type = IdType.UUID)
    private String rpId;

    @ApiModelProperty(value = "企业客户ID")
    private Integer rpDeptId;

    @ApiModelProperty(value = "业务员")
    private String rpYwPerson;

    @ApiModelProperty(value = "业户名称")
    private String rpYhPerson;

    @ApiModelProperty(value = "报修车辆ID")
    private String rpVehid;

    @ApiModelProperty(value = "行驶证照片")
    private String rpVehDrvingLicense;

    @ApiModelProperty(value = "车辆类型")
    private String rpVehType;

    @ApiModelProperty(value = "备注描述")
    private String rpRemark;

    @ApiModelProperty(value = "设备ID，多个以英文逗号隔开")
    private String rpEquipment;

    @ApiModelProperty(value = "接入说明ID")
    private String rpExplain;

    @ApiModelProperty(value = "创建时间")
    private String rpCreatetime;

    @ApiModelProperty(value = "创建人ID")
    private Integer rpCreateid;

    @ApiModelProperty(value = "创建人")
    private String rpCreatename;

    @ApiModelProperty(value = "更新时间")
    private String rpUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    private Integer rpUpdateid;

    @ApiModelProperty(value = "更新人")
    private String rpUpdatename;

    @ApiModelProperty(value = "报修时间")
    private String rpDate;

    @ApiModelProperty(value = "工单编号")
    private String rpNo;

    @ApiModelProperty(value = "0:PC网站；1:小程序")
    private Integer rpSource;

    @ApiModelProperty(value = "0:待受理，1:待指派，2:已派单，3:转交工单，4:已接单，5:待预约，6:已预约，7:进行中，8:待审核，9:已驳回，10:已完成，11:已取消")
    private Integer rpStatus;

    @ApiModelProperty(value = "工单类型，1：新装工单，2：维修工单")
    private Integer rpType;

    @ApiModelProperty(value = "故障类型")
    private Integer rpTroubleType;

    @ApiModelProperty(value = "图片附件")
    private String rpImg;

    @ApiModelProperty(value = "视频附件")
    private String rpVideo;

    @ApiModelProperty(value = "是否收费")
    private Integer rpIsfree;

	@ApiModelProperty(value = "联系人")
	private String rpRelationPerson;

	@ApiModelProperty(value = "联系电话")
	private String rpRelationPhone;

	@ApiModelProperty(value = "上门地址")
	private String rpAddress;

	@ApiModelProperty(value = "详细地址")
	private String rpDetailAddress;

	@ApiModelProperty(value = "紧急程度")
	private String rpEmergencyDegree;

	@ApiModelProperty(value = "报修流程")
	@TableField(exist = false)
	private AnbiaoRepairsRemark remark;

	@ApiModelProperty(value = "所属企业名称")
	@TableField(exist = false)
	private String rpDeptName;

	@ApiModelProperty(value = "车辆牌照")
	@TableField(exist = false)
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	@TableField(exist = false)
	private String chepaiyanse;

	@ApiModelProperty(value = "工单数量")
	@TableField(exist = false)
	private Integer count = 0;

	@ApiModelProperty(value = "企业ID")
	@TableField(exist = false)
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String deptName;

	@ApiModelProperty(value = "报修流程详情")
	@TableField(exist = false)
	private List<AnbiaoRepairsRemark> repairsRemarkList;

}
