package org.springblade.anbiao.qiyeshouye.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("anbiao_jiashiyuan_daka_info")
@ApiModel(value = "JiashiyuanDakaInfo对象", description = "JiashiyuanDakaInfo对象")
public class JiashiyuanDakaInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
    private Integer id;

	@ApiModelProperty(value = "驾驶员姓名")
    private String jiashiyuan;

	@ApiModelProperty(value = "驾驶员ID")
    private String jiashiyuanid;

	@ApiModelProperty(value = "驾驶员经度")
    private BigDecimal jiashiyuanjingdu;

	@ApiModelProperty(value = "驾驶员维度")
    private BigDecimal jiashiyuanweidu;

	@ApiModelProperty(value = "驾驶员打卡地址")
    private String dakadizhi;

	@ApiModelProperty(value = "打卡时间")
    private Date dakashijian;

	@ApiModelProperty(value = "打卡类型")
    private String dakaleixing;

	@ApiModelProperty(value = "打卡状态，默认true，打卡成功")
    private String dakaimg;

	@ApiModelProperty(value = "车辆牌照")
    private String cheliangpaizhao;

	@ApiModelProperty(value = "车牌颜色")
    private String chepaiyanse;

	@ApiModelProperty(value = "车辆经度")
    private BigDecimal cheliangjingdu;

	@ApiModelProperty(value = "车辆维度")
    private BigDecimal cheliangweidu;

	@ApiModelProperty(value = "车辆ID")
    private String cheliangid;

	@ApiModelProperty(value = "手机号码")
	@TableField(exist = false)
	private String shoujihaoma;

	@ApiModelProperty(value = "企业ID")
	@TableField(exist = false)
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	@TableField(exist = false)
	private String deptName;

}
