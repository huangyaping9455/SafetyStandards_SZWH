/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuZhuTiPage
 * Description:
 */
package org.springblade.anbiao.zhengfu.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GpsVehiclePage对象", description = "GpsVehiclePage对象")
public class GpsVehiclePage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业id ")
	private String dept;

	@ApiModelProperty(value = "政府ID ", required = true)
	private String zhengfuid;

	@ApiModelProperty(value = "在线状态（1:全部;2:上线;3:未上线;）")
	private Integer zaixian;

	@ApiModelProperty(value = "车辆状态（1:全部;2:监控车辆;3:停用;4:在用;）")
	private Integer zhuangtai;

	@ApiModelProperty(value = "使用性质")
	private String shiyongxingzhi;

	@ApiModelProperty(value = "第几页(从1开始)")
	private Integer page;

	@ApiModelProperty(value = "每页条数")
	private Integer pagesize;

	@ApiModelProperty(value = "车辆牌照")
	private String cph;

	@ApiModelProperty(value = "企业名称")
	private String deptname;
}
