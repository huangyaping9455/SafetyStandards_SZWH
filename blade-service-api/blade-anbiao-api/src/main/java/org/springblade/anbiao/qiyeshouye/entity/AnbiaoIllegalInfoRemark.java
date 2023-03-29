package org.springblade.anbiao.qiyeshouye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@TableName("anbiao_illegal_info_remark")
@ApiModel(value = "AnbiaoIllegalInfoRemark对象", description = "AnbiaoIllegalInfoRemark对象")
public class AnbiaoIllegalInfoRemark implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.UUID)
	@ApiModelProperty(value = "ID")
  	private String id;
	@ApiModelProperty(value = "违法违规数据ID")
	@TableField("illegalid")
  	private Integer illegalid;
	@ApiModelProperty(value = "处理状态")
	@TableField("chulizhuangtai")
  	private Integer chulizhuangtai;
	@ApiModelProperty(value = "处置形式")
	@TableField("chulixingshi")
  	private String chulixingshi;
	@ApiModelProperty(value = "处置描述")
	@TableField("chulimiaoshu")
  	private String chulimiaoshu;
	@ApiModelProperty(value = "处置人ID")
	@TableField("chulirenid")
  	private Integer chulirenid;
	@ApiModelProperty(value = "处置人")
	@TableField("chuliren")
  	private String chuliren;
	@ApiModelProperty(value = "处置时间")
	@TableField("chulishijian")
  	private String chulishijian;
	@ApiModelProperty(value = "处置附件")
	@TableField("fujian")
  	private String fujian;
	@ApiModelProperty(value = "约谈时间")
	@TableField("yuetanshijian")
  	private String yuetanshijian;
	@ApiModelProperty(value = "约谈地点")
	@TableField("address")
  	private String address;
	@ApiModelProperty(value = "告诫人")
	@TableField("gaojieren")
  	private String gaojieren;
	@ApiModelProperty(value = "被告诫人")
	@TableField("beigaojieren")
  	private String beigaojieren;
	@ApiModelProperty(value = "告诫警示谈话原因")
	@TableField("cautionTalkReason")
  	private String cautionTalkReason;
	@ApiModelProperty(value = "告诫警示谈话内容")
	@TableField("cautionConversationContent")
  	private String cautionConversationContent;
	@ApiModelProperty(value = "告诫人签字")
	@TableField("gaojierenqianzi")
  	private String gaojierenqianzi;
	@ApiModelProperty(value = "被告诫人签字")
	@TableField("beigaojierenqianzi")
  	private String beigaojierenqianzi;
	@ApiModelProperty(value = "约谈备注")
	@TableField("yuetanbeizhu")
  	private String yuetanbeizhu;
	@ApiModelProperty(value = "登记时间")
	@TableField("dengjishijian")
  	private String dengjishijian;
	@ApiModelProperty(value = "违章事由")
	@TableField("weizhangshiyou")
  	private String weizhangshiyou;
	@ApiModelProperty(value = "违章人员")
	@TableField("weizhangrenyuan")
  	private String weizhangrenyuan;
	@ApiModelProperty(value = "编号")
	@TableField("bainhao")
  	private String bainhao;
	@ApiModelProperty(value = "记录人")
	@TableField("jiluren")
  	private String jiluren;
	@ApiModelProperty(value = "违章简要经过")
	@TableField("weizhangjingguo")
  	private String weizhangjingguo;
	@ApiModelProperty(value = "处理情况")
	@TableField("chuliqingkuang")
  	private String chuliqingkuang;
	@ApiModelProperty(value = "负责人签字")
	@TableField("fuzerenqianzi")
  	private String fuzerenqianzi;
	@ApiModelProperty(value = "当事人签字")
	@TableField("dangshirenqianzi")
  	private String dangshirenqianzi;
	@ApiModelProperty(value = "备注")
	@TableField("beizhu")
  	private String beizhu;
	@ApiModelProperty(value = "检讨书、承诺书附件")
	@TableField("jiantaoshu")
  	private String jiantaoshu;
	@ApiModelProperty(value = "罚款附件")
	@TableField("fakuanfujian")
  	private String fakuanfujian;
	@ApiModelProperty(value = "抄告单附件")
	@TableField("chaogaodan")
  	private String chaogaodan;
	@ApiModelProperty(value = "12123截图附件")
	@TableField("jietu")
  	private String jietu;
	@ApiModelProperty(value = "是否删除，默认0")
	@TableField("is_deleted")
  	private Integer isDeleted;
	@ApiModelProperty(value = "创建时间")
	@TableField("createtime")
  	private String createtime;
	@ApiModelProperty(value = "创建者ID")
	@TableField("createId")
	private Integer createId;
	@ApiModelProperty(value = "更新时间")
	@TableField("updateTime")
	private String updateTime;
	@ApiModelProperty(value = "更新者ID")
	@TableField("updateId")
	private Integer updateId;

}
