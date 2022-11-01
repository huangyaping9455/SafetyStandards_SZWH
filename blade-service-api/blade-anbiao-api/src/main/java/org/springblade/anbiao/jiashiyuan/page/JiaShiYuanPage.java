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
@ApiModel(value = "JiaShiYuanPage对象", description = "JiaShiYuanPage对象")
public class JiaShiYuanPage<T> extends BasePage<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "单位ID",required = true)
	private Integer deptId;

	@ApiModelProperty(value = "单位名称")
	private String deptName;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "驾驶员类型",required = true)
	private String jiashiyuanleixing;

	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	@ApiModelProperty(value = "所属地市")
	private String areaName;




}
