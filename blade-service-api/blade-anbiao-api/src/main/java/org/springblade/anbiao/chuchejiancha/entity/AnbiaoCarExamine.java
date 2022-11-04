package org.springblade.anbiao.chuchejiancha.entity;

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
 * @since 2022-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_car_examine")
@ApiModel(value="AnbiaoCarExamine对象", description="")
public class AnbiaoCarExamine implements Serializable {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	@TableId(value = "id", type = IdType.INPUT)
	private Integer id;

	@ApiModelProperty(value = "企业ID")
	@TableField("deptid")
	private Integer deptid;

	@ApiModelProperty(value = "父级ID")
	@TableField("parentid")
	private Integer parentid;

	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "内容")
	@TableField("remark")
	private String remark;

	@ApiModelProperty(value = "数据状态（1正常 2停用）")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "类型（普货 0、危货 1）")
	@TableField("type")
	private Integer type;

	@ApiModelProperty(value = "是否删除，默认0")
	@TableField("isdelete")
	private Integer isdelete;

	@ApiModelProperty(value = "排序")
	@TableField("sort")
	private Integer sort;

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

	@ApiModelProperty(value = "树形code")
	@TableField("treecode")
	private String treecode;


}
