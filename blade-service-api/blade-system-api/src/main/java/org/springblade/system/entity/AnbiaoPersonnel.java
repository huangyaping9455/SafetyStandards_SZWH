package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
 * @author lmh
 * @since 2023-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_personnel")
@ApiModel(value="AnbiaoPersonnel对象", description="")
public class AnbiaoPersonnel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private Integer userid;

    private String caozuoren;

    private Integer caozuorenid;

    private LocalDateTime caozuoshijian;

    private String xingming;

    private String shenfenzheng;

    private String shoujihao;

    private String chushengriqi;

    private String qitalianxifangshi;

    private String youxiang;

    private String jiatingdizhi;

    private String gonghao;

    private String ruzhiriqi;

    private String gongzuojingli;

    private String beizhu;

    private String fujian;

    private Integer isDeleted;

    private LocalDateTime createtime;

    private String deptId;

    private String postId;

    @ApiModelProperty(value = "身份证附件正面")
    private String shenfenzhengfujian;

    @ApiModelProperty(value = "身份证附件反面")
    private String shenfenzhengfanmianfujian;

    @ApiModelProperty(value = "其他附件证明")
    private String qitazhengmianfujian;

    @ApiModelProperty(value = "其他附件反面")
    private String qitafanmianfujian;

	@ApiModelProperty(value = "身份证正面附件count")
	@TableField(exist = false)
	private Integer SFZZMcount=0;

	@ApiModelProperty(value = "身份证反面附件count")
	@TableField(exist = false)
	private Integer SFZFMcount=0;

	@ApiModelProperty(value = "其他正面附件count")
	@TableField(exist = false)
	private Integer QTZMcount=0;

	@ApiModelProperty(value = "其他反面附件count")
	@TableField(exist = false)
	private Integer QTFMcount=0;

	@ApiModelProperty(value = "是否统计")
	@TableField(exist = false)
	private Integer isCount=0;

	@ApiModelProperty(value = "节点id")
	@TableField(exist = false)
	private String nodeId;

	@ApiModelProperty(value = "count")
	@TableField(exist = false)
	private Integer count;

	@TableField(exist = false)
	private String jigouleixing;

	@ApiModelProperty(value = "deptName")
	@TableField(exist = false)
	private String deptName;

	@ApiModelProperty(value = "附件")
	@TableField(exist = false)
	private String attachments;

	@ApiModelProperty(value = "表id")
	@TableField(exist = false)
	private String tableId;

	@ApiModelProperty(value = "是否分配")
	@TableField(exist = false)
	private Integer isDistribution=0;

	@ApiModelProperty(value = "showBotton")
	@TableField(exist = false)
	private Boolean showBotton;

}
