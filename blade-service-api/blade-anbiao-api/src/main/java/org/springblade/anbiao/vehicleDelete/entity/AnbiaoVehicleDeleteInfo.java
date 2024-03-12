package org.springblade.anbiao.vehicleDelete.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @since 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_vehicle_delete_info")
@ApiModel(value="AnbiaoVehicleDeleteInfo对象", description="车辆删除日志记录表")
public class AnbiaoVehicleDeleteInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vd_id", type = IdType.UUID)
    private String vdId;

    @ApiModelProperty(value = "车辆ID")
    @TableField("vd_veh_id")
    private String vdVehId;

    @ApiModelProperty(value = "车辆牌照")
    @TableField("vd_veh_plate")
    private String vdVehPlate;

	@ApiModelProperty(value = "车牌颜色")
	@TableField("vd_veh_color")
	private String vdVehColor;

    @ApiModelProperty(value = "删除时间")
    @TableField("vd_date")
    private String vdDate;

    @ApiModelProperty(value = "创建时间")
    @TableField("vd_createtime")
    private String vdCreatetime;

    @ApiModelProperty(value = "创建人ID")
    @TableField("vd_createid")
    private Integer vdCreateid;

    @ApiModelProperty(value = "创建人")
    @TableField("vd_createname")
    private String vdCreatename;

	@ApiModelProperty(value = "企业ID")
	@TableField("vd_dept_id")
	private Integer vdDeptId;

	@ApiModelProperty(value = "说明")
	@TableField("vd_remark")
	private String vdRemark;

    @ApiModelProperty(value = "状态，默认0，1：已还原，2：更新信息")
    @TableField("vd_status")
    private Integer vdStatus = 0;

    @ApiModelProperty(value = "更新时间")
    @TableField("vd_updatetime")
    private String vdUpdatetime;

    @ApiModelProperty(value = "更新人ID")
    @TableField("vd_updateid")
    private Integer vdUpdateid;

    @ApiModelProperty(value = "更新人")
    @TableField("vd_updatename")
    private String vdUpdatename;

	@ApiModelProperty(value = "终端编号")
	@TableField("vd_zhongduanhao")
	private String vdZhongduanhao;

	@ApiModelProperty(value = "新终端编号")
	@TableField("vd_new_zhongduanhao")
	private String vdNewZhongduanhao;

	@ApiModelProperty(value = "sim卡号")
	@TableField("vd_sim")
	private String vdSim;

	@ApiModelProperty(value = "新sim卡号")
	@TableField("vd_new_sim")
	private String vdNewSim;

	@ApiModelProperty(value = "新车辆牌照")
	@TableField("vd_new_veh_plate")
	private String vdNewVehPlate;

	@ApiModelProperty(value = "新车牌颜色")
	@TableField("vd_new_veh_color")
	private String vdNewVehColor;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String rpDeptName;


}
