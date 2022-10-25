package org.springblade.anbiao.jiashiyuan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by you on 2019/4/22.
 */
@Data
@TableName("anbiao_jiashiyuan_enterprise")
@ApiModel(value = "JiaShiYuanEnterprise对象", description = "JiaShiYuanEnterprise对象")
public class JiaShiYuanEnterprise implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	@ApiModelProperty(value = "驾驶员姓名",required = true)
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "性别")
	private String xingbie;

	@ApiModelProperty(value = "性别（用于展示）")
	private String xingbieshow;

	@ApiModelProperty(value = "身份证号", required = true)
	private String shenfenzhenghao;

	@ApiModelProperty(value = "出生时间")
	private String chushengshijian;

	@ApiModelProperty(value = "年龄")
	private String nianling;

	@ApiModelProperty(value = "手机号码", required = true)
	private String shoujihaoma;

	@ApiModelProperty(value = "从业人员类型",required = true)
	private String congyerenyuanleixing;

	@ApiModelProperty(value = "身份证有效期",required = true)
	private String shenfenzhengyouxiaoqi;

	@ApiModelProperty(value = "驾驶员类型",required = true)
	private String jiashiyuanleixing;

	@ApiModelProperty(value = "所属单位ID",required = true)
	private Integer deptId;

	@ApiModelProperty(value = "单位名称")
	private String deptName;

	@ApiModelProperty(value = "身份证复印件")
	private String shenfenzhengfujian;

	@ApiModelProperty(value = "从业证复印件")
	private String congyezhengfujian;

	@ApiModelProperty(value = "驾驶证复印件")
	private String jiashizhengfujian;

	@ApiModelProperty(value = "家庭住址")
	private String jiatingzhuzhi;

	@ApiModelProperty(value = "驾驶证号")
	private String jiashizhenghao;

	@ApiModelProperty(value = "准驾车型")
	private String zhunjiachexing;

	@ApiModelProperty(value = "驾驶证初领日期")
	private String jiashizhengchulingriqi;

	@ApiModelProperty(value = "驾驶证有效期",required = true)
	private String jiashizhengyouxiaoqi;

	@ApiModelProperty(value = "体检有效期",required = true)
	private String tijianyouxiaoqi;

	@ApiModelProperty(value = "体检日期",required = true)
	private String tijianriqi;

	@ApiModelProperty(value = "从业资格证",required = true)
	private String congyezigezheng;

	@ApiModelProperty(value = "从业类别")
	private String congyeleibie;

	@ApiModelProperty(value = "从业类别（用于展示）")
	private String congyeleibieshow;

	@ApiModelProperty(value = "从业证有效期",required = true)
	private String congyezhengyouxiaoqi;

	@ApiModelProperty(value = "从业证初领日期")
	private String congyezhengchulingri;

	@ApiModelProperty(value = "发证机关")
	private String fazhengjiguan;

	@ApiModelProperty(value = "诚信考核时间")
	private String chengxinkaoheshijian;

	@ApiModelProperty(value = "下次诚信考核时间")
	private String xiacichengxinkaoheshijian;

	@ApiModelProperty(value = "备注")
	private String beizhu;

	@ApiModelProperty(value = "操作人ID")
	private Integer caozuorenid;

	@ApiModelProperty(value = "操作时间")
	private String caozuoshijian;

	@ApiModelProperty(value = "操作人")
	private String caozuoren;

	@ApiModelProperty(value = "是否删除")
	private Integer isdelete;

	@ApiModelProperty(value = "创建时间",required = true)
	private String createtime;

	@ApiModelProperty(value = "导入错误信息")
	@TableField(exist = false)
	private String Msg;

	@ApiModelProperty(value = "导入错误信息2")
	@TableField(exist = false)
	private boolean Msg2;

	@ApiModelProperty(value = "导入错误信息图标")
	@TableField(exist = false)
	private String importUrl;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	@ApiModelProperty(value = "预警/超期提醒说明")
	private String shuoming;
}
