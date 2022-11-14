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
import java.util.List;

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
@TableName("anbiao_car_examine_info")
@ApiModel(value="AnbiaoCarExamineInfo对象", description="")
public class AnbiaoCarExamineInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "车辆ID")
    @TableField("vehid")
    private String vehid;

    @ApiModelProperty(value = "企业ID")
    @TableField("deptid")
    private Integer deptid;

    @ApiModelProperty(value = "数据状态，0：正常，1：异常，2：审核通过，3：审核驳回，4：已整改，5：归档")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "类型（普货 0、危货 1）")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "检查时间")
    @TableField("date")
    private String date;

    @ApiModelProperty(value = "车辆图片")
    @TableField("vehimg")
    private String vehimg;

    @ApiModelProperty(value = "是否删除，默认0")
    @TableField("isdelete")
    private Integer isdelete;

    @ApiModelProperty(value = "驾驶员ID")
    @TableField("jsyid")
    private String jsyid;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private String createtime;

    @ApiModelProperty(value = "检查意见")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "复检、验收情况")
    @TableField("fujianremark")
    private String fujianremark;

    @ApiModelProperty(value = "检查人电子签名")
    @TableField("jcrsignatrue")
    private String jcrsignatrue;

    @ApiModelProperty(value = "检查地点")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "检查方式")
    @TableField("jcway")
    private String jcway;

    @ApiModelProperty(value = "审核人电子签名")
    @TableField("shenhesignatrue")
    private String shenhesignatrue;

    @ApiModelProperty(value = "整改责任人电子签名")
    @TableField("zgzrrsignatrue")
    private String zgzrrsignatrue;

    @ApiModelProperty(value = "整改验收人电子签名")
    @TableField("zgysrsignatrue")
    private String zgysrsignatrue;

    @ApiModelProperty(value = "审核人ID")
    @TableField("shenherenid")
    private Integer shenherenid;

    @ApiModelProperty(value = "审核人名称")
    @TableField("shenhenrenname")
    private String shenhenrenname;

	@ApiModelProperty(value = "更新者")
	@TableField("updateid")
	private String updateid;

	@ApiModelProperty(value = "更新时间")
	@TableField("updatetime")
	private String updatetime;

	@ApiModelProperty(value = "审核时间")
	@TableField("shenheshijian")
	private String shenheshijian;

	@ApiModelProperty(value = "整改验收时间")
	@TableField("zgshenheshijian")
	private String zgshenheshijian;

	@ApiModelProperty(value = "数据ID")
	@TableField("dataid")
	private Integer dataid;

	@ApiModelProperty(value = "验收人ID")
	@TableField("yanshourenid")
	private Integer yanshourenid;

	@ApiModelProperty(value = "验收人名称")
	@TableField("yanshourenname")
	private String yanshourenname;

	@ApiModelProperty(value = "检查人ID")
	@TableField("createid")
	private String createid;

	@ApiModelProperty(value = "检查人名称")
	@TableField("createname")
	private String createname;

	@TableField(exist = false)
	List<AnbiaoCarExamineInfoRemark> anbiaoCarExamineInfoRemarkList;

}
