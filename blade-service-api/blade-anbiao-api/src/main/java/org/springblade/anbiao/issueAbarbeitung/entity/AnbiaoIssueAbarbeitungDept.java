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
@TableName("anbiao_issue_abarbeitung_dept")
@ApiModel(value="AnbiaoIssueAbarbeitungDept对象", description="下发整改企业信息")
public class AnbiaoIssueAbarbeitungDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "企业ID")
    @TableField("dept_id")
    private Integer deptId;

    @ApiModelProperty(value = "企业名称")
    @TableField("dept_name")
    private String deptName;

    @ApiModelProperty(value = "审核状态，0：待处理，1：待审核，2：审核通过，3：审核不通过")
    @TableField("status")
    private Integer status = 0;

    @ApiModelProperty(value = "阅读状态，0：未读，1：已读")
    @TableField("is_read")
    private Integer isRead = 0;

    @ApiModelProperty(value = "回复情况")
    @TableField("response_situation")
    private String responseSituation;

    @ApiModelProperty(value = "附件")
    @TableField("fujian")
    private String fujian;

    @ApiModelProperty(value = "备注")
    @TableField("beizhu")
    private String beizhu;

    @ApiModelProperty(value = "阅读时间")
    @TableField("read_time")
    private String readTime;

    @ApiModelProperty(value = "阅读人ID")
    @TableField("read_id")
    private Integer readId;

    @ApiModelProperty(value = "阅读人")
    @TableField("read_name")
    private String readName;

    @ApiModelProperty(value = "操作时间")
    @TableField("caozuoshijian")
    private String caozuoshijian;

    @ApiModelProperty(value = "操作人")
    @TableField("caozuoren")
    private String caozuoren;

    @ApiModelProperty(value = "操作人ID")
    @TableField("caozuorenid")
    private Integer caozuorenid;

    @ApiModelProperty(value = "整改日期")
    @TableField("response_time")
    private String responseTime;

    @ApiModelProperty(value = "审核不通过理由")
    @TableField("no_pass_text")
    private String noPassText;

	@ApiModelProperty(value = "整改数据ID")
	@TableField("issue_id")
	private String issueId;

	@ApiModelProperty(value = "整改数据ID")
	@TableField("shenhe_time")
	private String shenheTime;

	@ApiModelProperty(value = "审核人")
	@TableField("shenhe_name")
	private String shenheName;

	@ApiModelProperty(value = "审核人ID")
	@TableField("shenhe_id")
	private Integer shenheId;

	@ApiModelProperty(value = "发送单位名称")
	@TableField(exist = false)
	private String fasongdanwei;

}
