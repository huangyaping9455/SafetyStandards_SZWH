/**
 * Copyright (C), 2015-2020,
 * FileName: GpsVehicle
 * Author:   呵呵哒
 * Date:     2020/7/3 10:29
 * Description:
 */
package org.springblade.anbiao.zhengfu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/7/21
 * @描述
 */
@Data
@ApiModel(value = "ZhengFuShouYeNew对象", description = "ZhengFuShouYeNew对象")
public class ZhengFuShouYeNew implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "地区名称")
	private String areaname;

	@ApiModelProperty(value = "政府运管局Id")
	private String zhengfuid;

	@ApiModelProperty(value = "政府运管局名称")
	private String zhengfuname;

	@ApiModelProperty(value = "个体数")
	private Integer getishu;

	@ApiModelProperty(value = "企业数")
	private Integer qiyeshu;

	@ApiModelProperty(value = "车辆总数")
	private Integer zcvehnumb;

	@ApiModelProperty(value = "在线车辆数")
	private Integer sxvehnum;

	@ApiModelProperty(value = "报警总次数")
	private Integer baojingcishu;

	@ApiModelProperty(value = "超速报警数")
	private Integer chaosu;

	@ApiModelProperty(value = "疲劳报警数")
	private Integer pilao;

	@ApiModelProperty(value = "夜间报警数")
	private Integer yejian;

	@ApiModelProperty(value = "异常报警数")
	private Integer yichang;

	@ApiModelProperty(value = "日期")
	private String date;

	@ApiModelProperty(value = "超速报警处理数")
	private Integer chaosucl;

	@ApiModelProperty(value = "疲劳报警处理数")
	private Integer pilaocl;

	@ApiModelProperty(value = "夜间报警处理数")
	private Integer yejiancl;

	@ApiModelProperty(value = "异常报警处理数")
	private Integer yichangcl;

	@ApiModelProperty(value = "疲劳驾驶报警处理数（主动安全设备）")
	private Integer pilaoshipin;

	@ApiModelProperty(value = "疲劳驾驶报警处理数（主动安全设备）")
	private Integer pilaoshipincl;

	@ApiModelProperty(value = "打电话报警数")
	private Integer dadianhua;

	@ApiModelProperty(value = "打电话报警处理数")
	private Integer dadianhuacl;

	@ApiModelProperty(value = "抽烟报警数")
	private Integer chouyan;

	@ApiModelProperty(value = "抽烟报警处理数")
	private Integer chouyancl;

	@ApiModelProperty(value = "分神报警数")
	private Integer fenshen;

	@ApiModelProperty(value = "分神报警处理数")
	private Integer fenshencl;

	@ApiModelProperty(value = "报警总处理数")
	private Integer baojingclcishu;

	@ApiModelProperty(value = "北斗报警数")
	private Integer beidoubj;

	@ApiModelProperty(value = "北斗报警处理数")
	private Integer beidoubjcl;

	@ApiModelProperty(value = "主动报警数（主动安全设备）")
	private Integer zhudongbj;

	@ApiModelProperty(value = "主动报警处理数（主动安全设备）")
	private Integer zhudongbjcl;

	@ApiModelProperty(value = "车辆数")
	private Integer cheliangshu;

	@ApiModelProperty(value = "报警车辆数")
	private Integer bjcheliangshu;

	@ApiModelProperty(value = "报警总处理率")
	private String baojingcishuchulilv;

	@ApiModelProperty(value = "北斗报警处理率")
	private String beidoubjchulilv;

	@ApiModelProperty(value = "主动设备报警处理率")
	private String zhudongbjchulilv;

	@ApiModelProperty(value = "主动安全报警次数")
	private Integer zdbaojingcishu;

	@ApiModelProperty(value = "下级list数据")
	private List<ZhengFuShouYeNew> xjlist;

	@ApiModelProperty(value = "安全达标率")
	private String dabiaolv;

	@ApiModelProperty(value = "离线车辆数")
	private Integer lxvehnum;

	@ApiModelProperty(value = "上线率")
	private String onlineRate = "0.00%";

	@ApiModelProperty(value = "定位率")
	private String locateRate = "0.00%";

	@ApiModelProperty(value = "轨迹漂移率")
	private String DriftPositionRate = "0.00%";

	@ApiModelProperty(value = "轨迹完整率")
	private String IntactPositionRate = "0.00%";

	@ApiModelProperty(value = "数据合格率")
	private String QualifiedPositionRate = "0.00%";

	@ApiModelProperty(value = "一级风险")
	private int RiskOne = 0;

	@ApiModelProperty(value = "二级风险")
	private int RiskTwo = 0;

	@ApiModelProperty(value = "三级风险")
	private int RiskThree = 0;

	@ApiModelProperty(value = "风险总数")
	private int RiskCount = 0;

	@ApiModelProperty(value = "考核分数")
	private int assessmentScore = 0;

	@ApiModelProperty(value = "一级隐患")
	private int troubleOne = 0;

	@ApiModelProperty(value = "二级隐患")
	private int troubleTwo = 0;

	@ApiModelProperty(value = "三级隐患")
	private int troubleThree = 0;

	@ApiModelProperty(value = "四级隐患")
	private int troubleFour = 0;

	@ApiModelProperty(value = "四级隐患")
	private int troubleCount = 0;

}
