package org.springblade.anbiao.qiyeshouye.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("anbiao_illegal_info")
@ApiModel(value = "AnbiaoIllegalInfo对象", description = "AnbiaoIllegalInfo对象")
public class AnbiaoIllegalInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
  	private Integer id;
	@ApiModelProperty(value = "驾驶员ID")
	@TableField("jsyId")
	private String jsyId;
	@ApiModelProperty(value = "企业ID")
	@TableField("deptId")
	private Integer deptId;
	@ApiModelProperty(value = "车辆ID")
	@TableField("vehId")
	private String vehId;
	@ApiModelProperty(value = "违规日期")
	private String date;
	@ApiModelProperty(value = "违规地点")
	private String address;
	@ApiModelProperty(value = "违法行为")
	@TableField("unlawfulAct")
	private Integer unlawfulAct;
	@ApiModelProperty(value = "扣分")
	private Integer deduct = 0;
	@ApiModelProperty(value = "罚款")
	private double penalty;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "状态，默认0；1：已处理；2：已生成文档")
	private Integer status = 0;
	@ApiModelProperty(value = "等级")
	private Integer grade;
	@ApiModelProperty(value = "创建时间")
	@TableField("createTime")
	private String createTime;
	@ApiModelProperty(value = "创建者ID")
	@TableField("createId")
	private Integer createId;
	@ApiModelProperty(value = "更新时间")
	@TableField("updateTime")
	private String updateTime;
	@ApiModelProperty(value = "更新者ID")
	@TableField("updateId")
	private Integer updateId;
	@ApiModelProperty(value = "是否删除")
	private Integer isdelete = 0;
	@ApiModelProperty(value = "违法编码")
	@TableField("illegalCode")
	private String illegalCode;
	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String deptName;
	@ApiModelProperty(value = "车辆牌照")
	@TableField(exist = false)
	private String cheliangpaizhao;
	@ApiModelProperty(value = "车牌颜色")
	@TableField(exist = false)
	private String chepaiyanse;
	@ApiModelProperty(value = "使用性质")
	@TableField(exist = false)
	private String shiyongxingzhi;
	@ApiModelProperty(value = "驾驶员姓名")
	@TableField(exist = false)
	private String jiashiyuanxingming;
	@ApiModelProperty(value = "手机号码")
	@TableField(exist = false)
	private String shoujihaoma;
	@ApiModelProperty(value = "违法违章数据来源")
	@TableField("dataSources")
	private Integer dataSources;
	@ApiModelProperty(value = "违法违章附件，多个以英文分号分隔")
	@TableField("illegalImg")
	private String illegalImg;
	@ApiModelProperty(value = "违法违章处理数据信息")
	@TableField(exist = false)
	private AnbiaoIllegalInfoRemark anbiaoIllegalInfoRemark;
	@ApiModelProperty(value = "处理状态，建议处置：1,；约谈处理：2")
	@TableField(exist = false)
	private String chulizhuangtai;
}
