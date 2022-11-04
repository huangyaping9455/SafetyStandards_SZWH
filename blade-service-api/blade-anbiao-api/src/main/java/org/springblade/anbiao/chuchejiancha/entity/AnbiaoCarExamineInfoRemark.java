package org.springblade.anbiao.chuchejiancha.entity;

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
 * @author hyp
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_car_examine_info_remark")
@ApiModel(value="AnbiaoCarExamineInfoRemark对象", description="")
public class AnbiaoCarExamineInfoRemark implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "数据ID")
    @TableField("examid")
    private Integer examid;

    @ApiModelProperty(value = "数据状态，0：正常,，1：异常")
    @TableField("status")
    private Integer status;

	@ApiModelProperty(value = "检查项ID")
	@TableField("xiangid")
	private Integer xiangid;

    @ApiModelProperty(value = "创建者")
    @TableField("createid")
    private String createid;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private String createtime;

    @ApiModelProperty(value = "更新者")
    @TableField("updateid")
    private String updateid;

    @ApiModelProperty(value = "更新时间")
    @TableField("updatetime")
    private String updatetime;

    @ApiModelProperty(value = "异常图片")
    @TableField("flgimg")
    private String flgimg;

    @ApiModelProperty(value = "整改图片")
    @TableField("trueimg")
    private String trueimg;

    @ApiModelProperty(value = "异常情况")
    @TableField("flgremark")
    private String flgremark;

    @ApiModelProperty(value = "整改情况")
    @TableField("trueremark")
    private String trueremark;

	@ApiModelProperty(value = "整改时间")
	@TableField("zgshijian")
	private String zgshijian;

	@ApiModelProperty(value = "检查项名称")
	@TableField(exist = false)
	private String xiangname;


}
