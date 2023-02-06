package org.springblade.anbiao.cheliangguanli.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "VehicleListVO对象", description = "VehicleListVO对象")
public class VehicleListVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "车辆id")
	private String id;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	/**
	 * 车辆牌照
	 */
	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	/**
	 * 车牌颜色
	 */
	@ApiModelProperty(value = "车牌颜色")
	private String chepaiyanse;

	/**
	 * 驾驶员姓名
	 */
	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "联系电话")
	private String shoujihaoma;

	/**
	 * 行驶证注册日期
	 */
	@ApiModelProperty(value = "行驶证注册日期")
	private String xingshizhengzhuceriqi;

	/**
	 * 行驶证发证日期
	 */
	@ApiModelProperty(value = "行驶证发证日期")
	private String xingshizhengfazhengriqi;

	/**
	 * 行驶证：结束日期
	 */
	@ApiModelProperty(value = "行驶证：结束日期")
	private String xingshizhengjieshuriqi;

	/**
	 * 行驶证检验剩余有效期
	 */
	@ApiModelProperty(value = "行驶证检验剩余有效期")
	private String xingshizhengshengyuyouxiaoqi;

	/**
	 * 强制报废日期
	 */
	@ApiModelProperty(value = "强制报废日期")
	private String qiangzhibaofeiriqi;

	/**
	 * 强制报废剩余有效期
	 */
	@ApiModelProperty(value = "强制报废剩余有效期")
	private String qiangzhibaofeishengyuyouxiaoqi;

	/**
	 * 车辆类型
	 */
	@ApiModelProperty(value = "车辆类型")
	private String xinghao;

	/**
	 * 车辆型号
	 */
	@ApiModelProperty(value = "车辆型号")
	private String cheliangxinghao;

	/**
	 * 车辆所有人
	 */
	@ApiModelProperty(value = "车辆所有人")
	private String cheliangsuoyouren;

	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	private String address;

	/**
	 * 使用性质
	 */
	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	/**
	 * 档案编号
	 */
	@ApiModelProperty(value = "档案编号")
	private String danganbianhao;

	/**
	 * 品牌型号
	 */
	@ApiModelProperty(value = "品牌型号")
	private String pinpaixinghao;

	/**
	 * 车辆识别代号
	 */
	@ApiModelProperty(value = "车辆识别代号")
	private String cheliangshibiedaima;

	/**
	 * 车身颜色
	 */
	@ApiModelProperty(value = "车身颜色")
	private String cheshenyanse;

	/**
	 * 经营许可证号
	 */
	@ApiModelProperty(value = "经营许可证号")
	private String jingyingxukezhengbianma;

	/**
	 * 经济类型
	 */
	@ApiModelProperty(value = "经济类型")
	private String jingjileixing;

	/**
	 * 经营组织方式
	 */
	@ApiModelProperty(value = "经营组织方式")
	private String jingyingzuzhifangshi;

	/**
	 * 经营范围
	 */
	@ApiModelProperty(value = "经营范围")
	private String cheliangyunyingleixing;

	/**
	 * 运输路线
	 */
	@ApiModelProperty(value = "运输路线")
	private String teamno;

	/**
	 * 车辆获得方式
	 */
	@ApiModelProperty(value = "车辆获得方式")
	private String chelianghuoqufangshi;

	/**
	 * 维护周期
	 */
	@ApiModelProperty(value = "维护周期")
	private String weihuzhouqi;

	/**
	 * 车架号
	 */
	@ApiModelProperty(value = "车架号")
	private String chejiahao;

	/**
	 * 国产/进口
	 */
	@ApiModelProperty(value = "国产/进口")
	private String shifoujinkou;

	/**
	 * 燃料种类
	 */
	@ApiModelProperty(value = "燃料种类")
	private String ranliaoleibie;

	/**
	 * 排量/功率
	 */
	@ApiModelProperty(value = "排量/功率")
	private String fadongjipailianggonglv;

	/**
	 * 转向形式
	 */
	@ApiModelProperty(value = "转向形式")
	private String zhuanxiangfangshi;

	/**
	 * 制造产名称
	 */
	@ApiModelProperty(value = "制造产名称")
	private String zhizhaochangshang;

	/**
	 * 轮距
	 */
	@ApiModelProperty(value = "轮距")
	private String lunju;

	/**
	 * 后轮距
	 */
	@ApiModelProperty(value = "后轮距")
	private String frontlunju;

	/**
	 * 轮胎数
	 */
	@ApiModelProperty(value = "轮胎数")
	private String luntaishu;

	/**
	 * 轮胎规格
	 */
	@ApiModelProperty(value = "轮胎规格")
	private String luntaiguige;

	/**
	 * 钢板弹簧片数
	 */
	@ApiModelProperty(value = "钢板弹簧片数")
	private String gangbantanhuangpianshu;

	/**
	 * 轴距
	 */
	@ApiModelProperty(value = "轴距")
	private String zhouju;

	/**
	 * 轴数
	 */
	@ApiModelProperty(value = "轴数")
	private String chezhoushu;

	/**
	 * 货箱内部尺寸
	 */
	@ApiModelProperty(value = "货箱内部尺寸")
	private String huoxiangneibuchicun;

	/**
	 * 核定载人数
	 */
	@ApiModelProperty(value = "核定载人数")
	private String hedingzaike;

	/**
	 * 驾驶室载客
	 */
	@ApiModelProperty(value = "驾驶室载客")
	private String jiashishizaike;

	/**
	 * 总质量
	 */
	@ApiModelProperty(value = "总质量")
	private String zongzhiliang;

	/**
	 * 核定载质量
	 */
	@ApiModelProperty(value = "核定载质量")
	private String hedingzaizhiliang;

	/**
	 * 准牵引总质量
	 */
	@ApiModelProperty(value = "准牵引总质量")
	private String zhunqianyinzongzhiliang;

	/**
	 * 外廓尺寸
	 */
	@ApiModelProperty(value = "外廓尺寸")
	private String cheliangwaikuochicun;

	/**
	 * 车辆出厂日期
	 */
	@ApiModelProperty(value = "车辆出厂日期")
	private String chuchangriqi;

	/**
	 * 综合性能检测、技术等级评定信息:开始日期
	 */
	@ApiModelProperty(value = "综合性能检测、技术等级评定信息:开始日期")
	private String bencijipingriqi;

	/**
	 * 综合性能检测、技术等级评定信息剩余有效期
	 */
	@ApiModelProperty(value = "综合性能检测、技术等级评定信息剩余有效期")
	private String jishupingdingshengyuyouxiaoqi;

	/**
	 * 道路运输证:证件号码
	 */
	@ApiModelProperty(value = "道路运输证:证件号码")
	private String daoluyunshuzhenghao;

	/**
	 * 道路运输证:开始日期
	 */
	@ApiModelProperty(value = "道路运输证:开始日期")
	private String daoluyunshuzhengkaishiriqi;

	/**
	 * 道路运输证:结束日期
	 */
	@ApiModelProperty(value = "道路运输证:结束日期")
	private String daoluyunshuzhengjieshuriqi;

	/**
	 * 道路运输证剩余有效期
	 */
	@ApiModelProperty(value = "道路运输证剩余有效期")
	private String daoluyunshuzhengshengyuyouxiaoqi;


}
