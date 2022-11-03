package org.springblade.anbiao.dianziwendang.entity;

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
 * 电子文档表
 * </p>
 *
 * @author hyp
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_electronic_file")
@ApiModel(value="AnbiaoElectronicFile对象", description="电子文档表")
public class AnbiaoElectronicFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "aef_id", type = IdType.UUID)
    private String aefId;

	@ApiModelProperty(value = "企业ID")
	private String aefDeptId;

    @ApiModelProperty(value = "文件名称")
    private String aefName;

    @ApiModelProperty(value = "文件类型（1：岗位职责、2：安全生产制度:3：安全生产工作计划与目标）")
    private Integer aefType;

    @ApiModelProperty(value = "人员类型（1：管理员、2：驾驶员）")
    private Integer aefPersonType;

    @ApiModelProperty(value = "正文")
    private String aefNeirong;

    @ApiModelProperty(value = "附件")
    private String aefImg;

    @ApiModelProperty(value = "逻辑删除(0=正常,1=删除)")
    private Integer aefDelete;

    @ApiModelProperty(value = "创建时间")
    private String aefCreateTime;

    @ApiModelProperty(value = "创建人主键")
    private String aefCreateByIds;

    @ApiModelProperty(value = "创建人姓名")
    private String aefCreateByName;

    @ApiModelProperty(value = "更新时间")
    private String aefUpdateTime;

    @ApiModelProperty(value = "更新人主键")
    private String aefUpdateByIds;

    @ApiModelProperty(value = "更新人姓名")
    private String aefUpdateByName;


}
