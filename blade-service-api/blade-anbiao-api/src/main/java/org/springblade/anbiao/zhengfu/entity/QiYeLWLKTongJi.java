/**
 * Copyright (C); 2015-2020;
 * FileName: 北斗Vehicle
 * Author:   呵呵哒
 * Date:     2020/7/3 10:29
 * Description:
 */
package org.springblade.anbiao.zhengfu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/10/12
 * @描述
 */
@Data
@ApiModel(value = "QiYeLWLKTongJi对象", description = "QiYeLWLKTongJi对象")
public class QiYeLWLKTongJi implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "所属运管")
	private String zhengfuname;

	@ApiModelProperty(value = "所属运管ID")
	private Integer zhengfuid;

	@ApiModelProperty(value = "企业ID")
	private Integer qiyeid;

	@ApiModelProperty(value = "企业名称")
	private String qiyemingcheng;

	@ApiModelProperty(value = "日期")
	private String date;

	@ApiModelProperty(value = "车辆上线率分数")
	private String uplinerateScore;

	@ApiModelProperty(value = "车辆入网率分数")
	private String RuWangLvScore;

	@ApiModelProperty(value = "轨迹漂移率分数")
	private String DriftPositionRateScore;

	@ApiModelProperty(value = "轨迹完整率分数")
	private String IntactPositionRateScore;

	@ApiModelProperty(value = "数据合格率分数")
	private String QualifiedPositionRateScore;

	@ApiModelProperty(value = "平台查岗响应率分数")
	private String CheckPostResponseRateScore;

	@ApiModelProperty(value = "平均车辆超速次数分数")
	private String chaosuScore;

	@ApiModelProperty(value = "平均疲劳驾驶时长分数")
	private String pilaotimesScore;

	@ApiModelProperty(value = "总分")
	private String countScore;

	@ApiModelProperty(value = "轨迹漂移率")
	private String DriftPositionRate;

	@ApiModelProperty(value = "轨迹完整率")
	private String IntactPositionRate;

	@ApiModelProperty(value = "数据合格率")
	private String QualifiedPositionRate;

	@ApiModelProperty(value = "上线率")
	private String onlineRate;

	@ApiModelProperty(value = "平台连通率")
	private String CheckPostResponseRate;

	@ApiModelProperty(value = "入网率")
	private String RuWangLv;

	@ApiModelProperty(value = "运政基础数据合规率")
	private String yunzhengjichuRate;

	@ApiModelProperty(value = "运政基础数据合规率分数")
	private String yunzhengjichuScore;

	@ApiModelProperty(value = "逾期注销率")
	private String yuqizhuxiaoRate;

	@ApiModelProperty(value = "逾期注销率分数")
	private String yuqizhuxiaoScore;

	@ApiModelProperty(value = "超期年审处理率")
	private String chaoqinianshenRate;

	@ApiModelProperty(value = "超期年审处理率分数")
	private String chaoqinianshenScore;

	@ApiModelProperty(value = "在运行车辆离线率")
	private String zaiyunxingchelianglixianRate;

	@ApiModelProperty(value = "在运行车辆离线率分数")
	private String zaiyunxingchelianglixianScore;

	@ApiModelProperty(value = "超速次数")
	private String chaosualarm;

	@ApiModelProperty(value = "疲劳时长")
	private String pilaotimes;

}
