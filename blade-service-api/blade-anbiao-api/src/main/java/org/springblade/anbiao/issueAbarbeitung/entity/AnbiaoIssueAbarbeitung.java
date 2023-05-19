package org.springblade.anbiao.issueAbarbeitung.entity;

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
 * @since 2023-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_issue_abarbeitung")
@ApiModel(value="AnbiaoIssueAbarbeitung对象", description="下发整改")
public class AnbiaoIssueAbarbeitung implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "企业ID,多个企业ID以英文逗号隔开")
    @TableField("dept_id")
    private String deptId;

    @ApiModelProperty(value = "企业名称,多个企业名称以英文逗号隔开")
    @TableField("dept_name")
    private String deptName;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "限期整改时间")
    @TableField("rectification_time")
    private String rectificationTime;

    @ApiModelProperty(value = "是否强制时间整改，1；")
    @TableField("is_abarbeitung")
    private Integer isAbarbeitung = 0;

    @ApiModelProperty(value = "存在问题")
    @TableField("existing_problem")
    private String existingProblem;

    @ApiModelProperty(value = "整改要求")
    @TableField("rectification_requirement")
    private String rectificationRequirement;

    @ApiModelProperty(value = "附件")
    @TableField("fujian")
    private String fujian;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_name")
    private String createName;

    @ApiModelProperty(value = "创建人ID")
    @TableField("careate_id")
    private Integer careateId;

    @ApiModelProperty(value = "发送单位ID")
    @TableField("fasongdanweiid")
    private Integer fasongdanweiid;

	@ApiModelProperty(value = "下发企业数")
	@TableField(exist = false)
	private Integer count = 0;

	@ApiModelProperty(value = "已读企业数")
	@TableField(exist = false)
	private Integer ydcount = 0;

	@ApiModelProperty(value = "未读企业数")
	@TableField(exist = false)
	private Integer wdcount = 0;

	@ApiModelProperty(value = "状态")
	@TableField(exist = false)
	private Integer status = 0;

	@ApiModelProperty(value = "发送单位名称")
	@TableField(exist = false)
	private String fasongdanwei;
}
