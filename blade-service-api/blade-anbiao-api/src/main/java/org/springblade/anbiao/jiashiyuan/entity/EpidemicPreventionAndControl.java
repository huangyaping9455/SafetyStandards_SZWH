package org.springblade.anbiao.jiashiyuan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by you on 2019/4/22.
 */
@Data
@TableName("anbiao_epidemic_prevention_and_control_info")
@ApiModel(value = "EpidemicPreventionAndControl对象", description = "EpidemicPreventionAndControl对象")
public class EpidemicPreventionAndControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID") private int id;
	@ApiModelProperty(value = "驾驶员ID") private String jiashiyuanid;
	@ApiModelProperty(value = "驾驶员姓名") private String jiashiyuanxingming;
	@ApiModelProperty(value = "身份证号") private String shenfenzhenghao;
	@ApiModelProperty(value = "手机号码") private String shoujihaoma;
	@ApiModelProperty(value = "所属单位ID") private Integer deptId;
	@ApiModelProperty(value = "单位名称") private String deptName;
	@ApiModelProperty(value = "车辆牌照") private String cheliangpaizhao;
	@ApiModelProperty(value = "车牌颜色") private String chepaiyanse;
	@ApiModelProperty(value = "所属省") private String province;
	@ApiModelProperty(value = "所属市") private String city;
	@ApiModelProperty(value = "所属县") private String country;
	@ApiModelProperty(value = "所属街道") private String street;
	@ApiModelProperty(value = "所属社区") private String community;
	@ApiModelProperty(value = "一周内到访地区") private String afterregion;
	@ApiModelProperty(value = "核酸检测时间") private String nattime;
	@ApiModelProperty(value = "核酸检测地点") private String natarea;
	@ApiModelProperty(value = "健康码状态(默认0,1,2)") private int healthcodestatus;
	@ApiModelProperty(value = "行程卡状态(默认0，1代表异常)") private int travelitinerarystatus;
	@ApiModelProperty(value = "是否到过风险区域(默认0，1代表异常)") private int isriskyarea;
	@ApiModelProperty(value = "风险区域名称") private String riskyarea;
	@ApiModelProperty(value = "健康码附件") private String healthcodeimg;
	@ApiModelProperty(value = "行程卡附件") private String travelitineraryimg;
	@ApiModelProperty(value = "核酸检测记录附件") private String natimg;
	@ApiModelProperty(value = "创建时间") private String createtime;
	@ApiModelProperty(value = "是否删除") private String isdeleted;
	@ApiModelProperty(value = "更新时间") private String updatetime;
	@ApiModelProperty(value = "更新者") private String updateuser;
	@ApiModelProperty(value = "车辆ID") private String vehid;

}
