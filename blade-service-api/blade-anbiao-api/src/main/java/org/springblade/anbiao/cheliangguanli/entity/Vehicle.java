package org.springblade.anbiao.cheliangguanli.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.tool.utils.Func;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: SafetyStandards
 * @description: Vehicle
 * @author: elvis
 * @create2021-04-22 14:00
 **/
@Data
@TableName("anbiao_vehicle")
@ApiModel(value = "Vehicle对象", description = "Vehicle对象")
public class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.UUID)
	@ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "企业 id", required = true)
	@TableField("dept_id")
	private Integer deptId;

	@ApiModelProperty(value = "企业 名称")
	@TableField(exist = false)
	private String deptName;

	@ApiModelProperty(value = "车辆牌照", required = true)
	private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色", required = true)
	private String chepaiyanse;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "驾驶员id")
	private String jiashiyuanid;

	@ApiModelProperty(value = "押运员id")
	private String yayunyuanid;

	@ApiModelProperty(value = "厂牌")
	private String changpai;
	@ApiModelProperty(value = "型号")
	private String xinghao;
	@ApiModelProperty(value = "车架号")
	private String chejiahao;
	@ApiModelProperty(value = "轮胎规格")
	private String luntaiguige;
	@ApiModelProperty(value = "车身颜色")
	private String cheshenyanse;
	@ApiModelProperty(value = "核定载客")
	private String hedingzaike;
	@ApiModelProperty(value = "营运年限")
	private String yingyunnianxian;
	@ApiModelProperty(value = "登记证书编号")
	private String dengjizhengshubianhao;
	@ApiModelProperty(value = "车辆来源")
	private String chelianglaiyuan;
	@ApiModelProperty(value = "注册登记时间")
	private String zhucedengjishijian;
	@ApiModelProperty(value = "入户时间")
	private String ruhushijian;
	@ApiModelProperty(value = "过户时间")
	private String guohushijian;
	@ApiModelProperty(value = "退市时间", required = true)
	private String tuishishijian;
	@ApiModelProperty(value = "强制报废时间", required = true)
	private String qiangzhibaofeishijian;
	@ApiModelProperty(value = "接驳运输证号")
	private String jieboyunshuzhenghao;
	@ApiModelProperty(value = "原车辆牌照")
	private String yuancheliangpaizhao;
	@ApiModelProperty(value = "车辆状态")
	private String cheliangzhuangtai;
	@ApiModelProperty(value = "车辆停放地区")
	private String cheliangtingfangdiqu;
	@ApiModelProperty(value = "档案号")
	private String danganhao;
	@ApiModelProperty(value = "是否备用车辆")
	private String beiyongcheliang;
	@ApiModelProperty(value = "运营商")
	private String yunyingshang;
	@ApiModelProperty(value = "所属车队")
	private String suoshuchedui;
	@ApiModelProperty(value = "行驶证附件", required = true)
	private String xingshifujian;
	@ApiModelProperty(value = "附件")
	private String fujian;
	@ApiModelProperty(value = "发动机型号")
	private String fadongjixinghao;
	@ApiModelProperty(value = "发动机号")
	private String fadongjihao;
	@ApiModelProperty(value = "发动机功率")
	private String fadongjipailianggonglv;
	@ApiModelProperty(value = "燃料类别")
	private String ranliaoleibie;
	@ApiModelProperty(value = "燃料消耗")
	private String ranyouxiaohao;
	@ApiModelProperty(value = "排放标准")
	private String paifangbiaozhun;
	@ApiModelProperty(value = "转向方式")
	private String zhuanxiangfangshi;
	@ApiModelProperty(value = "车门设置")
	private String chemenshezhi;
	@ApiModelProperty(value = "轴距")
	private String zhouju;
	@ApiModelProperty(value = "车长")
	private String chechang;
	@ApiModelProperty(value = "车宽")
	private String chekuan;
	@ApiModelProperty(value = "车高")
	private String chegao;
	@ApiModelProperty(value = "轮胎数")
	private String luntaishu;
	@ApiModelProperty(value = "车轴数")
	private String chezhoushu;
	@ApiModelProperty(value = "钢板弹簧片数")
	private String gangbantanhuangpianshu;
	@ApiModelProperty(value = "底盘型号")
	private String dipanxinghao;
	@ApiModelProperty(value = "动力类型")
	private String donglileixing;
	@ApiModelProperty(value = "总质量")
	private String zongzhiliang;
	@ApiModelProperty(value = "整备质量")
	private String zhengbeizhiliang;
	@ApiModelProperty(value = "轮胎种类")
	private String luntaizonglei;
	@ApiModelProperty(value = "悬挂形式")
	private String xuanguaxingshi;
	@ApiModelProperty(value = "行车制动方式")
	private String xingchezhidongfangshi;
	@ApiModelProperty(value = "制动器前轮")
	private String zhidongqiqianlun;
	@ApiModelProperty(value = "制动器后轮")
	private String zhidongqihoulun;
	@ApiModelProperty(value = "ABS")
	private String abs;
	@ApiModelProperty(value = "空调系统")
	private String kongtiaoxitong;
	@ApiModelProperty(value = "缓速器")
	private String huanshuqi;
	@ApiModelProperty(value = "变速箱形式")
	private String biansuxiangxingshi;
	@ApiModelProperty(value = "制造厂商")
	private String zhizhaochangshang;
	@ApiModelProperty(value = "购置税证号")
	private String gouzhishuizhenghao;
	@ApiModelProperty(value = "出厂日期")
	private String chuchangriqi;
	@ApiModelProperty(value = "累计里程")
	private String leijilicheng;
	@ApiModelProperty(value = "终端服务器")
	private String zhongduanfuwuqi;
	@ApiModelProperty(value = "车辆等级")
	private String cheliangdengji;
	@ApiModelProperty(value = "卫生间")
	private String weishengjian;
	@ApiModelProperty(value = "发动机排量")
	private String fadongjipailiang;
	@ApiModelProperty(value = "车辆外廓尺寸")
	private String cheliangwaikuochicun;
	@ApiModelProperty(value = "燃料消耗附件")
	private String ranliaoxiaohaofujian;
	@ApiModelProperty(value = "备注")
	private String beizhu;
	@ApiModelProperty(value = "GPS安装时间")
	private String gpsanzhuangshijian;
	@ApiModelProperty(value = "智能化系统")
	private String zhinenghuaxitong;
	@ApiModelProperty(value = "是否安装GPS设备")
	private String gps;
	@ApiModelProperty(value = "行驶记录仪")
	private String xingshijiluyi;
	@ApiModelProperty(value = "终端id")
	private String zongduanid;
	@ApiModelProperty(value = "终端型号")
	private String zongduanxinghao;
	@ApiModelProperty(value = "车辆照片")
	private String cheliangzhaopian;
	@ApiModelProperty(value = "运输介质")
	private String yunshujiezhi;

	@ApiModelProperty(value = "运营商名称")
	private String yunyingshangmingcheng;

	@ApiModelProperty(value = "车主电话")
	private String chezhudianhua;

	@ApiModelProperty(value = "车主")
	private String chezhu;

	@ApiModelProperty(value = "驾驶员名称")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "驾驶员电话")
	private String jiashiyuandianhua;

	@ApiModelProperty(value = "押运员名称")
	private String yayunyuanxingming;

	@ApiModelProperty(value = "押运员电话")
	private String yayunyuandianhua;

	/**
	 * 是否删除
	 */
	@TableLogic
	@ApiModelProperty(value = "是否已删除")
	@TableField("is_deleted")
	private Integer isdel = 0;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", required = true)
	private LocalDateTime
		createtime;
	/**
	 * 操作人
	 */
	@ApiModelProperty(value = "操作人")
	private String caozuoren;
	/**
	 * 操作人id
	 */
	@ApiModelProperty(value = "操作人id")
	private Integer caozuorenid;
	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "操作时间")
	private LocalDateTime caozuoshijian;


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		Vehicle other = (Vehicle) obj;
		if (Func.equals(this.getId(), other.getId())) {
			return true;
		}
		return false;
	}

	/**
	 * 导入错误信息
	 */
	@ApiModelProperty(value = "导入错误信息")
	@TableField(exist = false)
	private String Msg;
	/**
	 * 导入错误信息2
	 */
	@ApiModelProperty(value = "导入错误信息2")
	@TableField(exist = false)
	private boolean Msg2;

	@ApiModelProperty(value = "导入错误信息图标")
	@TableField(exist = false)
	private String importUrl;

	@ApiModelProperty(value = "所属地市")
	private String area;

	@ApiModelProperty(value = "SIM卡号")
	private String simnum;

	@ApiModelProperty(value = "终端类型")
	private Integer zhongduanleixing;

	@TableField(exist = false)
	@ApiModelProperty(value = "企业安全负责人电话")
	private String securityofficertelephone;

	@ApiModelProperty(value = "终端协议类型")
	private String terminalprotocoltype;

	@ApiModelProperty(value = "视频通道数")
	private String videochannelnum;

	@ApiModelProperty(value = "平台连接方式  1直连 2 转发")
	private String platformconnectiontype;

	@ApiModelProperty(value = "终端厂商")
	private String zhongduanchangshang;

	@ApiModelProperty(value = "随车电话")
	@TableField(exist = false)
	private String accessoryphone;

	@ApiModelProperty(value = "道路运输证")
	private String daoluyunshuzheng;

	@ApiModelProperty(value = "道路运输证初领日期")
	private String daoluyunshuzhengchulingriqi;

	@ApiModelProperty(value = "道路运输证有效截止日期")
	private String daoluyunshuzhengyouxiaoqi;

	@ApiModelProperty(value = "本次车年审日期")
	private String bencinianshenriqi;

	@ApiModelProperty(value = "下次年审日期")
	private String xiacinianshenriqi;

	@ApiModelProperty(value = "本次年检日期")
	private String bencinianjianriqi;

	@ApiModelProperty(value = "下次年检日期")
	private String xiacinianjianriqi;

	@ApiModelProperty(value = "本次技术评定日期")
	private String bencijipingriqi;

	@ApiModelProperty(value = "下次技术评定日期")
	private String xiacijipingriqi;

	@ApiModelProperty(value = "报废日期")
	private String baofeiriqi;

	@ApiModelProperty(value = "车辆技术等级")
	private String cheliangjishudengji;

	@ApiModelProperty(value = "所属运管")
	private String suoshuyunguan;

	@ApiModelProperty(value = "线路号")
	private String teamno;

	@ApiModelProperty(value = "自编号")
	private String owenno;

	@ApiModelProperty(value = "道路运输证附件")
	private String daoluyunzhengfujian;

	@ApiModelProperty(value = "道路运输车辆达标核查记录表")
	private String dabiaojianchafujian;

	@ApiModelProperty(value = "车辆年审附件")
	private String nianshenfujian;

	@ApiModelProperty(value = "车辆行驶证附件（正页）")
	private String xingshizhengzhengyefujian;

	@ApiModelProperty(value = "车辆行驶证附件（副页）")
	private String xingshizhengfuyefujian;

	@ApiModelProperty(value = "车辆登记证书（正页）")
	private String cheliangdengjizhengshuzhengyefujian;

	@ApiModelProperty(value = "车辆登记证书（副页）")
	private String cheliangdengjizhengshufuyefujian;

	@ApiModelProperty(value = "保险到期时间")
	private String baoxiandaoqishijian;

	@ApiModelProperty(value = "行驶证到期时间")
	private String xingshizhengdaoqishijian;

	@ApiModelProperty(value = "车主居住地址")
	private String carowneraddress;

	@ApiModelProperty(value = "车辆获得方式")
	private String chelianghuoqufangshi;

	@ApiModelProperty(value = "维护周期")
	private String weihuzhouqi;

	@ApiModelProperty(value = "车辆类型")
	private String cheliangleixing;

	@ApiModelProperty(value = "车辆品牌")
	private String cheliangpinpai;

	@ApiModelProperty(value = "是否进口")
	private String shifoujinkou;

	@ApiModelProperty(value = "轮距")
	private String lunju;

	@ApiModelProperty(value = "货箱内部尺寸")
	private String huoxiangneibuchicun;

	@ApiModelProperty(value = "核定载质量")
	private String hedingzaizhiliang;

	@ApiModelProperty(value = "准牵引质量")
	private String zhunqianyinzongzhiliang;

	@ApiModelProperty(value = "驾驶室载客")
	private String jiashishizaike;

	@ApiModelProperty(value = "车辆运营类型")
	private String cheliangyunyingleixing;

	@ApiModelProperty(value = "经济类型")
	private String jingjileixing;

	@ApiModelProperty(value = "经营组织方式")
	private String jingyingzuzhifangshi;


}
