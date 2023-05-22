package org.springblade.anbiao.cheliangguanli.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "车辆对象",description = "车辆对象")
public class VehicleInfo implements Serializable {

	@ApiModelProperty(value = "车辆主键")
	private String id;

	@ApiModelProperty(value = "所属企业主键",required = true)
	private Integer deptId;

	@ApiModelProperty(value = "车辆号码",required = true)
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色",required = true)
	private String chepaiyanse;

	@ApiModelProperty(value = "驾驶员主键")
	private String jiashiyuanid;

	@ApiModelProperty(value = "车主姓名",required = true)
	private String chezhu;

	@ApiModelProperty(value = "车主电话",required = true)
	private String chezhudianhua;

	@ApiModelProperty(value = "车辆变更记录")
	@TableField(exist = false)
	private List<VehicleBiangengjilu> cheliangbiangengjilu;

	@ApiModelProperty(value = "注册日期")
	private LocalDate zhuceriqi;

	@ApiModelProperty(value = "经营许可证号")
	private String jingyingxukezhenghao;

	@ApiModelProperty(value = "经营许可证有效期（起）")
	private LocalDate jyxkzyouxiaoqiStart;

	@ApiModelProperty(value = "经营许可证有效期（止）")
	private LocalDate jyxkzyouxiaoqiEnd;

	@ApiModelProperty(value = "经营许可证照片")
	private String jyxkzzhaopian;

	@ApiModelProperty(value = "道路运输证号")
	private String daoluyunshuzhenghao;

	@ApiModelProperty(value = "道路运输证有效期（起）")
	private LocalDate dlyszyouxiaoqiStart;

	@ApiModelProperty(value = "道路运输证有效期（止）")
	private LocalDate dlyszyouxiaoqiEnd;

	@ApiModelProperty(value = "道路运输证照片")
	private String dlyszzhaopian;

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
	private String pailianggonglv;

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
	private String zongzhiliang="0";

	@ApiModelProperty(value = "核定载质量")
	private String hedingzaizhiliang="0";

	@ApiModelProperty(value = "核定载客数")
	private String hedingzaikeshu="0";

	@ApiModelProperty(value = "准牵引总质量")
	private String zhunqianyinzongzhiliang="0";

	@ApiModelProperty(value = "驾驶室载客数")
	private String jiashishizaikeshu;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "车辆出厂日期")
	private String cheliangchuchangriqi;

	@ApiModelProperty(value = "强制报废时间")
	private String qiangzhibaofeishijian;

	@ApiModelProperty(value = "后轮距")
	private String frontlunju;

	@ApiModelProperty(value = "终端id")
	private String zongduanid;

	@ApiModelProperty(value = "终端型号")
	private String zongduanxinghao;

	@ApiModelProperty(value = "终端协议类型")
	private String terminalprotocoltype;

	@ApiModelProperty(value = "视频通道数")
	private String videochannelnum;

	@ApiModelProperty(value = "平台连接方式  1直连 2 转发")
	private String platformconnectiontype;

	@ApiModelProperty(value = "终端厂商")
	private String zhongduanchangshang;

	@ApiModelProperty(value = "SIM卡号")
	private String simnum;

	@ApiModelProperty(value = "终端类型")
	private Integer zhongduanleixing;

	@ApiModelProperty(value = "运营商")
	private String yunyingshang;

	@ApiModelProperty(value = "运营商名称")
	private String yunyingshangmingcheng;

	@ApiModelProperty(value = "运营商接入码")
	private String yunyingshangjieruma;

}
