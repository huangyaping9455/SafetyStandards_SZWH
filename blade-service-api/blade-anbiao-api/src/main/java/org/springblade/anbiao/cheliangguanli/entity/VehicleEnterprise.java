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
 * @description: VehicleEnterprise
 **/
@Data
@TableName("anbiao_vehicle_enterprise")
@ApiModel(value = "VehicleEnterprise对象", description = "VehicleEnterprise对象")
public class VehicleEnterprise implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "企业 id",required = true)
    @TableField("dept_id")
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

    @ApiModelProperty(value = "车辆状态")
    private String cheliangzhuangtai;

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

    @ApiModelProperty(value = "型号")
    private String xinghao;

    @ApiModelProperty(value = "厂牌")
    private String changpai;

    @ApiModelProperty(value = "核定吨位")
    private String hedingdunwei;

    @ApiModelProperty(value = "核定载客")
    private String hedingzaike;

    @ApiModelProperty(value = "车架号")
    private String chejiahao;

    @ApiModelProperty(value = "运营商名称")
    private String yunyingshangmingcheng;

    @ApiModelProperty(value = "运营商")
    private String yunyingshang;

    @ApiModelProperty(value = "终端id")
    private String zongduanid;

    @ApiModelProperty(value = "驾驶员ID")
    private String jiashiyuanid;

    @ApiModelProperty(value = "押运员ID")
    private String yayunyuanid;

    @ApiModelProperty(value = "行驶证附件")
    private String xingshizhengfujian;

    @ApiModelProperty(value = "车辆年审附件")
    private String nianshenfujian;

    @ApiModelProperty(value = "道路运输车辆达标核查记录表")
    private String dabiaojianchafujian;

    @ApiModelProperty(value = "路运输证附件")
    private String daoluyunzhengfujian;

    @ApiModelProperty(value = "备注")
    private String beizhu;

    @ApiModelProperty(value = "终端型号")
    private String zongduanxinghao;

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

    @ApiModelProperty(value = "所属运管")
    private String suoshuyunguan;

    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    @TableField("is_deleted")
    private Integer isdel = 0;

	@ApiModelProperty(value = "创建时间",required = true)
	private String createtime;

	@ApiModelProperty(value = "操作人")
	private String caozuoren;

	@ApiModelProperty(value = "操作人id")
	private Integer caozuorenid;

	@ApiModelProperty(value = "操作时间")
	private String caozuoshijian;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        VehicleEnterprise other = (VehicleEnterprise) obj;
        if (Func.equals(this.getId(), other.getId())) {
            return true;
        }
        return false;
    }

	@ApiModelProperty(value = "导入错误信息")
	@TableField(exist = false)
	private String Msg;

	@ApiModelProperty(value = "导入错误信息2")
	@TableField(exist = false)
	private boolean Msg2;

	@ApiModelProperty(value = "导入错误信息图标")
	@TableField(exist = false)
	private String importUrl;

	@ApiModelProperty(value = "所属区县")
	private String area;

	@ApiModelProperty(value = "终端类型")
	private Integer zhongduanleixing;

    @ApiModelProperty(value = "预警/超期提醒说明")
    private String shuoming;

}
