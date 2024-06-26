package org.springblade.anbiao.jiashiyuan.page;

import com.baomidou.mybatisplus.annotation.TableField;
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
	private String deptId;

	@ApiModelProperty(value = "单位名称")
	private String deptName;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "手机号码")
	private String shoujihaoma;

	@ApiModelProperty(value = "驾驶员类型")
	private String jiashiyuanleixing;

	@ApiModelProperty(value = "从业人员类型")
	private String congyerenyuanleixing;

	@ApiModelProperty(value = "身份证号")
	private String shenfenzhenghao;

	@ApiModelProperty(value = "所属地市")
	private String areaName;

	@ApiModelProperty(value = "驾驶员Id")
	private String id;

	private String type;

	@ApiModelProperty(value = "变更状态，1：离职，2：请假，3：机动，4：调离，5：迁出")
	private Integer status;

}
