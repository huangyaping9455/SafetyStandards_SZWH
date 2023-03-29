/**
 * Copyright (C), 2015-2020,
 * FileName: ZhengFuBaoJingTongJi
 * Author:   呵呵哒
 * Date:     2020/7/7 20:29
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
 * @创建时间 2020/7/7
 * @描述
 */
@Data
@ApiModel(value = "ZhengFuRiskRanking对象", description = "ZhengFuRiskRanking对象")
public class ZhengFuRiskRanking implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "所属运管")
	private String zhengfuname;

	@ApiModelProperty(value = "所属运管ID")
	private String zhengfuid;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "企业名称")
	private String deptName;

	@ApiModelProperty(value = "一级风险")
	private String RiskOne;

	@ApiModelProperty(value = "二级风险")
	private String RiskTwo;

	@ApiModelProperty(value = "三级风险")
	private String RiskThree;

	@ApiModelProperty(value = "风险总数")
	private String RiskCount;

	@ApiModelProperty(value = "统计日期")
	private String tongjiriqi;
}
