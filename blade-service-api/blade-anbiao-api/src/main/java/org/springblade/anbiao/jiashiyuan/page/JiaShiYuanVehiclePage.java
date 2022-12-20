package org.springblade.anbiao.jiashiyuan.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * Created by you on 2019/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "JiaShiYuanVehiclePage对象", description = "JiaShiYuanVehiclePage对象")
public class JiaShiYuanVehiclePage<T> extends BasePage<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "单位ID",required = true)
	private Integer deptId;

	@ApiModelProperty(value = "使用性质（车头、挂车）",required = true)
	private String shiyongxingzhi;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

}
