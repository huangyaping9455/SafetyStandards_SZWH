package org.springblade.anbiao.shixun.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2022-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_bs_policy_info")
@ApiModel(value="AnbiaoBsPolicyInfo对象", description="最新时讯")
public class AnbiaoBsPolicyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "ID")
	private Integer id;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String biaoti;

	/**
	 * 正文
	 */
	@ApiModelProperty(value = "正文")
	private String zhengwen;

	/**
	 * 附件
	 */
	@ApiModelProperty(value = "附件")
	private String fujian;

	/**
	 * 标签类型
	 */
	@ApiModelProperty(value = "标签类型")
	private String biaoqian;

	/**
	 * 操作人
	 */
	@ApiModelProperty(value = "操作人")
	private String caozuoren;

	/**
	 * 操作人ID
	 */
	@ApiModelProperty(value = "操作人ID")
	private String caozuorenid;

	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "操作时间")
	private String caozuoshijian;

	/**
	 * 访问量
	 */
	@ApiModelProperty(value = "访问量")
	private String fangwenliang;

	/**
	 * http地址
	 */
	@ApiModelProperty(value = "http地址")
	private String http;

	/**
	 * 是否启用
	 */
	@ApiModelProperty(value = "是否启用（启用，禁用）")
	private String shifouqiyong;

	@ApiModelProperty(value = "是否删除，默认0,1：删除")
	private Integer isdelete;

	@ApiModelProperty(value = "企业ID")
	@TableField("deptid")
	private Integer deptId;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String deptName;

	/**
	 * 说明
	 */
	@ApiModelProperty(value = "说明")
	private String shuoming;

	/**
	 * 是否签字，是为1，否为0
	 */
	@ApiModelProperty(value = "是否签字，是为1，否为0")
	private String issign;

	@ApiModelProperty(value = "企业ID，多个以英文逗号隔开")
	@TableField(exist = false)
	private String deptIds;

}
