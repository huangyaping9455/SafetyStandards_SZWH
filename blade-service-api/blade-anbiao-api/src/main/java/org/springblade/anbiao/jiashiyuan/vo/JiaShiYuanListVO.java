package org.springblade.anbiao.jiashiyuan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;

@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "JiaShiYuanListVO对象", description = "JiaShiYuanListVO对象")
public class JiaShiYuanListVO {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "驾驶员id")
	private String id;

	/**
	 * 企业名称
	 */
	@ApiModelProperty(value = "企业名称")
	private String deptName;

	/**
	 * 驾驶员姓名
	 */
	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private String xingbie;

	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	/**
	 * 车辆牌照
	 */
	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	/**
	 * 车辆牌照（挂车）
	 */
	@ApiModelProperty(value = "车辆牌照（挂车）")
	private String cheliangpaizhaoguache;

	/**
	 * 司机入职日期
	 */
	@ApiModelProperty(value = "司机入职日期")
	private String sijiruzhiriqi;

	/**
	 * 劳动合同开始日期
	 */
	@ApiModelProperty(value = "劳动合同开始日期")
	private String laodonghetongkaishiriqi;

	/**
	 * 劳动合同结束日期
	 */
	@ApiModelProperty(value = "劳动合同结束日期")
	private String laodonghetongjieshuriqi;

	/**
	 * 劳动合同剩余有效期
	 */
	@ApiModelProperty(value = "劳动合同剩余有效期")
	private String laodonghetongshengyuyouxiaoqi;

	/**
	 * 身份证：证件号码
	 */
	@ApiModelProperty(value = "身份证：证件号码")
	private String shenfenzhenghao;

	/**
	 * 身份证：开始日期
	 */
	@ApiModelProperty(value = "身份证：开始日期")
	private String shenfenzhengchulingriqi;

	/**
	 * 身份证：结束日期
	 */
	@ApiModelProperty(value = "身份证：结束日期")
	private String shenfenzhengyouxiaoqi;

	/**
	 * 身份证剩余有效期
	 */
	@ApiModelProperty(value = "身份证剩余有效期")
	private String shenfenzhengshengyuyouxiaoqi;

	/**
	 * 驾驶证：证件号码
	 */
	@ApiModelProperty(value = "驾驶证：证件号码")
	private String jiashizhenghao;

	/**
	 * 驾驶证：开始日期
	 */
	@ApiModelProperty(value = "驾驶证：开始日期")
	private String jiashizhengkaishiriqi;

	/**
	 * 驾驶证：结束日期
	 */
	@ApiModelProperty(value = "驾驶证：结束日期")
	private String jiashizhengjieshuriqi;

	/**
	 * 驾驶证剩余有效期
	 */
	@ApiModelProperty(value = "驾驶证剩余有效期")
	private String jiashizhengshengyuyouxiaoqi;

	/**
	 * 从业资格证：证件号码
	 */
	@ApiModelProperty(value = "从业资格证：证件号码")
	private String congyezigezhenghao;

	/**
	 * 从业资格证：开始日期
	 */
	@ApiModelProperty(value = "从业资格证：开始日期")
	private String congyezigezhengkaishiriqi;

	/**
	 * 从业资格证：结束日期
	 */
	@ApiModelProperty(value = "从业资格证：结束日期")
	private String congyezigezhengjieshuriqi;

	/**
	 * 从业资格证剩余有效期
	 */
	@ApiModelProperty(value = "从业资格证剩余有效期")
	private String congyezigezhengshengyuyouxiaoqi;

	/**
	 * 安全生产责任书：起始日期
	 */
	@ApiModelProperty(value = "安全生产责任书：起始日期")
	private String anquanshengchanzerenshuqishiriqi;

	/**
	 * 安全生产责任书剩余有效期
	 */
	@ApiModelProperty(value = "安全生产责任书剩余有效期")
	private String anquanshengchanzerenshushengyuyouxiaoqi;

	/**
	 * 岗位危险告知书：起始日期
	 */
	@ApiModelProperty(value = "岗位危险告知书：起始日期")
	private String gangweiweixiangaozhishuqishiriqi;

	/**
	 * 岗位危险告知书剩余有效期
	 */
	@ApiModelProperty(value = "岗位危险告知书剩余有效期")
	private String gangweiweixiangaozhishushengyuyouxiaoqi;

	/**
	 * 体检报告：起始日期
	 */
	@ApiModelProperty(value = "体检报告：起始日期")
	private String tijianbaogaoqishiriqi;

	/**
	 * 体检报告剩余有效期
	 */
	@ApiModelProperty(value = "体检报告剩余有效期")
	private String tijianbaogaoshengyuyouxiaoqi;

	/**
	 * 无重大责任事故：起始日期
	 */
	@ApiModelProperty(value = "无重大责任事故：起始日期")
	private String wuzhongdazerenshiguqishiriqi;

	/**
	 * 无重大责任事故：结束日期
	 */
	@ApiModelProperty(value = "无重大责任事故：结束日期")
	private String wuzhongdazerenshigujieshuriqi;
}
