package org.springblade.anbiao.yinhuanpaicha.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2022-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_yinhuanpaicha_xiang")
@ApiModel(value="AnbiaoYinhuanpaichaXiang对象", description="AnbiaoYinhuanpaichaXiang对象")
public class AnbiaoYinhuanpaichaXiang implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parentid;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "风险值")
    private String score;

    @ApiModelProperty(value = "类别")
    private Integer type;

    @ApiModelProperty(value = "严重程度")
    private Integer status;

    @ApiModelProperty(value = "判断规则（多个规则，以英文逗号分隔）")
    private String judgerules;

    @ApiModelProperty(value = "判断规则描述")
    private String judgerulesremark;

    @ApiModelProperty(value = "来源")
    private Integer source;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建者ID")
    private Integer createid;

    @ApiModelProperty(value = "创建时间")
    private String createtime;

    @ApiModelProperty(value = "更新者ID")
    private Integer updateid;

    @ApiModelProperty(value = "更新时间")
    private String updatetime;

    private Integer isdelete;

    @ApiModelProperty(value = "是否需要制定计划")
    private Integer isdrawupplan;

	@ApiModelProperty(value = "阈值")
	private String value;

	@ApiModelProperty(value = "是否需要后续处理操作(1:需要)")
	private Integer isdispose = 0;
}
