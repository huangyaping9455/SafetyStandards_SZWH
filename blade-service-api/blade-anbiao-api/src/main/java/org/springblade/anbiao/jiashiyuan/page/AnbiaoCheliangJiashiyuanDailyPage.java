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
@ApiModel(value = "AnbiaoCheliangJiashiyuanDailyPage对象", description = "AnbiaoCheliangJiashiyuanDailyPage对象")
public class AnbiaoCheliangJiashiyuanDailyPage<T> extends BasePage<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "单位ID",required = true)
	private String deptId;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;


}
