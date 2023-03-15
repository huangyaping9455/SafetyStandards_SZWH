package org.springblade.anbiao.anquanhuiyi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 安全会议记录表
 * </p>
 *
 * @author lmh
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("anbiao_anquanhuiyi")
@ApiModel(value="AnbiaoAnquanhuiyi对象", description="安全会议记录表")
public class AnbiaoAnquanhuiyi implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "安全会议主键")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "企业主键",required = true)
    @TableField("dept_id")
    private Integer deptId;

    @ApiModelProperty(value = "操作人")
    @TableField("caozuoren")
    private String caozuoren;

    @ApiModelProperty(value = "操作人主键")
    @TableField("caozuorenid")
    private Integer caozuorenid;

    @ApiModelProperty(value = "操作时间")
    @TableField("caozuoshijian")
    private String caozuoshijian;

    @ApiModelProperty(value = "会议名称",required = true)
    @TableField("huiyimingcheng")
    private String huiyimingcheng;

    @ApiModelProperty(value = "会议编号")
    @TableField("huiyibianhao")
    private String huiyibianhao;

//    @ApiModelProperty(value = "会议类型")
//    @TableField("huiyileixing")
//    private String huiyileixing;

    @ApiModelProperty(value = "会议形式(0=线上,1=线上)",required = true)
    @TableField("huiyixingshi")
    private String huiyixingshi;

//    @ApiModelProperty(value = "会议日期")
//    @TableField("huiyiriqi")
//    private String huiyiriqi;

    @ApiModelProperty(value = "主持人")
    @TableField("zhuchiren")
    private String zhuchiren;

    @ApiModelProperty(value = "记录人")
    @TableField("jiluren")
    private String jiluren;

    @ApiModelProperty(value = "会议地点")
    @TableField("huiyididian")
    private String huiyididian;

    @ApiModelProperty(value = "会议开始时间",required = true)
    @TableField("huiyikaishishijian")
    private String huiyikaishishijian;

    @ApiModelProperty(value = "会议结束时间",required = true)
    @TableField("huiyijieshushijian")
    private String huiyijieshushijian;

    @ApiModelProperty(value = "会议内容")
    @TableField("huiyineirong")
    private String huiyineirong;

    @ApiModelProperty(value = "备注")
    @TableField("beizhu")
    private String beizhu;

    @ApiModelProperty(value = "会议照片")
    @TableField("huiyizhaopian")
    private String huiyizhaopian;

    @ApiModelProperty(value = "是否删除(0=否,1=是)")
    @TableField("is_deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private String createtime;

    @ApiModelProperty(value = "参会人数")
    @TableField("canhuirenshu")
    private Integer canhuirenshu = 0;

    @ApiModelProperty(value = "附件")
	@TableField("fujian")
	private String fujian;

    @ApiModelProperty(value = "会议人员信息")
	@TableField(exist = false)
	private List<AnbiaoAnquanhuiyiDetail> anquanhuiyiDetails;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String deptname;

	@ApiModelProperty(value = "参会明细主键")
	@TableField(exist = false)
	private String aadIds;

	@ApiModelProperty(value = "参会人员类别")
	@TableField(exist = false)
	private String type;

	@ApiModelProperty(value = "状态，0：未签到，1：已签到")
	@TableField(exist = false)
	private Integer status;

	@ApiModelProperty(value = "月份")
	@TableField(exist = false)
	private String months;

	@ApiModelProperty(value = "会议日期区间")
	@TableField(exist = false)
	private String dateShow;

	@ApiModelProperty(value = "实际参会人数")
	@TableField(exist = false)
	private Integer shijicanhuirenshu = 0;

	@ApiModelProperty(value = "企业ID字符串（多个以英文逗号隔开）")
	@TableField(exist = false)
	private String deptIds;

	@ApiModelProperty(value = "会议ID")
	@TableField(exist = false)
	private String huiYiId;

}
