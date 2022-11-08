package org.springblade.anbiao.cheliangguanli.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "车辆对象",description = "车辆对象")
public class VehicleInfo implements Serializable {

	@ApiModelProperty(value = "车辆主键")
	private String id;

	@ApiModelProperty(value = "所属企业主键",required = true)
	private String deptId;

	@ApiModelProperty(value = "车辆号码",required = true)
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色",required = true)
	private String chepaiyanse;

	@ApiModelProperty(value = "车主姓名",required = true)
	private String chezhuxingming;

	@ApiModelProperty(value = "车主电话",required = true)
	private String chezhudianhua;

	@ApiModelProperty(value = "车辆变更记录")
	private List<VehicleBiangengjilu> cheliangbiangengjilu;

	@ApiModelProperty(value = "注册日期")
	private String zhuceriqi;

	@ApiModelProperty(value = "经营许可证号")
	private String jingyingxukezhenghao;

	@ApiModelProperty(value = "道路运输证号")
	private String daoluyunshuzhenghao;

	@ApiModelProperty(value = "经济类型")
	private String jingjileixing;

	@ApiModelProperty(value = "经营组织方式")
	private String jingyingzuzhifangshi;

	@ApiModelProperty(value = "经营范围")
	private String jingyingfanwei;

	@ApiModelProperty(value = "经营路线")
	private String jingyingluxian;

	@ApiModelProperty(value = "车辆获得方式")
	private String chelianghuodefangshi;

	@ApiModelProperty(value = "维护周期")
	private String weihuzhouqi;

	@ApiModelProperty(value = "车辆照片",required = true)
	private String cheliangzhaopian;


	@ApiModelProperty(value = "车辆类型")
	private String cheliangleixing;

	@ApiModelProperty(value = "车辆品牌")
	private String cheliangpinpai;

	@ApiModelProperty(value = "车辆型号")
	private String cheliangxinghao;

	@ApiModelProperty(value = "车辆颜色")
	private String cheliangyanse;

	@ApiModelProperty(value = "车架号")
	private String chejiahao;

	@ApiModelProperty(value = "是否国产")
	private String shifouguochan;

	@ApiModelProperty(value = "发动机号")
	private String fadongjihao;

	@ApiModelProperty(value = "发动机型号")
	private String fadongjixinghao;

	@ApiModelProperty(value = "燃料种类")
	private String ranliaozhonglei;

	@ApiModelProperty(value = "排量/功率")
	private String pailianggonglü;

	@ApiModelProperty(value = "制造厂名称")
	private String zhizaochangmingcheng;

	@ApiModelProperty(value = "转向形式")
	private String zhuanxiangxingshi;

	@ApiModelProperty(value = "轮距")
	private String lunju;

	@ApiModelProperty(value = "轮胎数")
	private String luntaishu;

	@ApiModelProperty(value = "轮胎规格")
	private String luntaiguige;

	@ApiModelProperty(value = "铜板弹簧片数")
	private String tongbantanhuangpianshu;

	@ApiModelProperty(value = "轴距")
	private String zhouju;

	@ApiModelProperty(value = "轴数")
	private String zhoushu;

	@ApiModelProperty(value = "外廓尺寸")
	private String waikuochicun;

	@ApiModelProperty(value = "货箱内部尺寸")
	private String huoxiangneibuchicun;

	@ApiModelProperty(value = "总质量")
	private String zongzhiliang;

	@ApiModelProperty(value = "核定载质量")
	private String hedingzaizhiliang;

	@ApiModelProperty(value = "核定载客数")
	private String hedingzaikeshu;

	@ApiModelProperty(value = "准牵引总质量")
	private String zhunqianyinzongzhiliang;

	@ApiModelProperty(value = "驾驶室载客数")
	private String jiashishizaikeshu;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "车辆出厂日期")
	private String cheliangchuchangriqi;

}