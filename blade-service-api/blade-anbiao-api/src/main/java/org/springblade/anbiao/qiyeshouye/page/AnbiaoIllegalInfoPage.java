/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuZhuTiPage
 * Author:   呵呵哒
 * Date:     2020/6/20 16:14
 * Description:
 */
package org.springblade.anbiao.qiyeshouye.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2022/08/09
 * @描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AnbiaoIllegalInfoPage对象", description = "AnbiaoIllegalInfoPage对象")
public class AnbiaoIllegalInfoPage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID", required = true)
	private Integer deptId;

	@ApiModelProperty(value = "开始时间", required = true)
	private String beginTime;

	@ApiModelProperty(value = "结束时间", required = true)
	private String endTime;

	@ApiModelProperty(value = "违法行为")
	private String unlawfulAct;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "违法状态")
	private Integer status;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

}
