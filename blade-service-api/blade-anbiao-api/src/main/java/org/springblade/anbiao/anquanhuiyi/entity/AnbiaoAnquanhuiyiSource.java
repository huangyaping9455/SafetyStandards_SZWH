package org.springblade.anbiao.anquanhuiyi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author hyp
 * @since 2022-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_anquanhuiyi_source")
@ApiModel(value="AnbiaoAnquanhuiyiSource对象", description="")
public class AnbiaoAnquanhuiyiSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "主题")
    @TableField("zhuti")
    private String zhuti;

    @ApiModelProperty(value = "正文")
    @TableField("zhengwen")
    private String zhengwen;

    @ApiModelProperty(value = "1:日例会，2：周例会，3：月例会")
    @TableField("leixing")
    private Integer leixing;

    @ApiModelProperty(value = "删除标志，默认0")
    @TableField("isdeleted")
    private Integer isdeleted;

    @ApiModelProperty(value = "创建人ID")
    @TableField("createid")
    private Integer createid;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private String createtime;

    @ApiModelProperty(value = "创建人名称")
    @TableField("createname")
    private String createname;

    @ApiModelProperty(value = "更新人ID")
    @TableField("updateid")
    private Integer updateid;

    @ApiModelProperty(value = "更新时间")
    @TableField("updatetime")
    private String updatetime;

    @ApiModelProperty(value = "更新人名称")
    @TableField("updatename")
    private String updatename;

    @ApiModelProperty(value = "企业ID")
    @TableField("deptid")
    private Integer deptid;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String deptname;

	@ApiModelProperty(value = "会议纪要")
	@TableField(exist = false)
	private String huiyijiyao;

	@ApiModelProperty(value = "会议内容")
	@TableField(exist = false)
	private String huiyineirong;

}
