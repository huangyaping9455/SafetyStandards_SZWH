package org.springblade.anbiao.problemFeedback.entity;

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
 * @since 2023-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_problem_feedback")
@ApiModel(value="AnbiaoProblemFeedback对象", description="驾驶员问题反馈")
public class AnbiaoProblemFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pf_id", type = IdType.AUTO)
    private String pfId;

    @ApiModelProperty(value = "驾驶员ID")
    @TableField("pf_driver_id")
    private String pfDriverId;

    @ApiModelProperty(value = "驾驶员姓名")
    @TableField("pf_driver_name")
    private String pfDriverName;

    @ApiModelProperty(value = "反馈时间")
    @TableField("pf_date")
    private String pfDate;

    @ApiModelProperty(value = "反馈问题")
    @TableField("pf_problem")
    private String pfProblem;

    @ApiModelProperty(value = "反馈建议")
    @TableField("pf_opinion")
    private String pfOpinion;

    @ApiModelProperty(value = "联系方式")
    @TableField("pf_relaxation")
    private String pfRelaxation;

    @ApiModelProperty(value = "处理状态，0：未回复，1：已回复")
    @TableField("pf_status")
    private Integer pfStatus = 0;

    @ApiModelProperty(value = "处理说明")
    @TableField("pf_way")
    private String pfWay;

    @ApiModelProperty(value = "处理说明附件")
    @TableField("pf_feedback_img")
    private String pfFeedbackImg;

    @ApiModelProperty(value = "反馈问题附件")
    @TableField("pf_img")
    private String pfImg;

    @ApiModelProperty(value = "创建时间")
    @TableField("pf_createtime")
    private String pfCreatetime;

    @ApiModelProperty(value = "处理人ID")
    @TableField("pf_updateid")
    private Integer pfUpdateid;

    @ApiModelProperty(value = "处理人名称")
    @TableField("pf_updatename")
    private String pfUpdatename;

    @ApiModelProperty(value = "更新时间")
    @TableField("pf_updatetime")
    private String pfUpdatetime;

	@ApiModelProperty(value = "企业ID")
	@TableField("pf_dept_id")
	private Integer pfDeptId;

	@ApiModelProperty(value = "删除标志，默认0,1：已删除")
	@TableField("pf_delete")
	private Integer pfDelete = 0;

}
