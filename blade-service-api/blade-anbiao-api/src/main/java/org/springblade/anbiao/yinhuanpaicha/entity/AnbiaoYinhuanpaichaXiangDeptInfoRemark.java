package org.springblade.anbiao.yinhuanpaicha.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springblade.anbiao.yinhuanpaicha.vo.RemarkVehicleVO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hyp
 * @since 2022-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_yinhuanpaicha_xiang_dept_info_remark")
@ApiModel(value="AnbiaoYinhuanpaichaXiangDeptInfoRemark对象", description="")
public class AnbiaoYinhuanpaichaXiangDeptInfoRemark implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "车辆ID")
    @TableField("vehid")
    private String vehid;

    @ApiModelProperty(value = "驾驶员ID")
    @TableField("jsyid")
    private String jsyid;

    @ApiModelProperty(value = "状态，默认0，未填写；1：待审查；2：已审查")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "派发时间")
    @TableField("pfdate")
    private String pfdate;

    @ApiModelProperty(value = "审查时间")
    @TableField("scdate")
    private String scdate;

    @ApiModelProperty(value = "派发人ID")
    @TableField("createid")
    private Integer createid;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private String createtime;

    @ApiModelProperty(value = "审核人ID")
    @TableField("updateid")
    private Integer updateid;

    @ApiModelProperty(value = "更新时间")
    @TableField("updatetime")
    private String updatetime;

    @TableField("isdelete")
    private Integer isdelete;

    @ApiModelProperty(value = "数据ID")
    @TableField("dataid")
    private Integer dataid;

	@TableField(exist = false)
	private List<RemarkVehicleVO> remarkVehicleVOList;


}
