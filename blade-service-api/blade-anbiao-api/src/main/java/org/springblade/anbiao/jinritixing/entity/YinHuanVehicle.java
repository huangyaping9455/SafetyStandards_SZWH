package org.springblade.anbiao.jinritixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 * @create2022-09-15
 **/
@Data
@ApiModel(value = "YinHuanVehicle对象", description = "YinHuanVehicle对象")
public class YinHuanVehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "企业 id",required = true)
    @TableField("deptId")
    private Integer deptId;

    @ApiModelProperty(value = "企业名称")
    @TableField(exist = false)
    private String deptName;

    @ApiModelProperty(value = "车辆牌照",required = true)
    private String cheliangpaizhao;

    @ApiModelProperty(value = "车牌颜色",required = true)
    private String chepaiyanse;

    @ApiModelProperty(value = "使用性质")
    private String shiyongxingzhi;

    @ApiModelProperty(value = "强制报废时间", required = true)
    private String qiangzhibaofeishijian;

    @ApiModelProperty(value = "行驶证附件", required = true)
    private String xingshifujian;

    @ApiModelProperty(value = "GPS安装时间")
    private String gpsanzhuangshijian;

    @ApiModelProperty(value = "道路运输证")
    private String daoluyunshuzheng;

    @ApiModelProperty(value = "道路运输证有效截止日期")
    private String daoluyunshuzhengyouxiaoqi;

    @ApiModelProperty(value = "下次年审日期")
    private String xiacinianshenriqi;

    @ApiModelProperty(value = "下次年检日期")
    private String xiacinianjianriqi;

    @ApiModelProperty(value = "下次技术评定日期")
    private String xiacijipingriqi;

    @ApiModelProperty(value = "报废日期")
    private String baofeiriqi;

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

//	保险信息
	@ApiModelProperty(value = "终保时间")
	private String zhongbaoshijian;

	@ApiModelProperty(value = "投保类型")
	private String toubaoleixing;

//	车辆维护提醒
	@ApiModelProperty(value = "下次维护日期")
	private String xiaciweihuriqi;

	@ApiModelProperty(value = "维护类别")
	private String weihuleibie;

//	 驾驶员
	@ApiModelProperty(value = "驾驶员id")
	private String jiashiyuanid;

	@ApiModelProperty(value = "驾驶证有效截至日期")
	private String jiashizhengyouxiaoqi;

	@ApiModelProperty(value = "押运员id")
	private String yayunyuanid;

	@ApiModelProperty(value = "驾驶员从业资格证有效截止日期")
	private String congyezhengyouxiaoqi;


}
