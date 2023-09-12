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

import java.util.List;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/8/30
 * @描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "QiYeShouYePage对象", description = "QiYeShouYePage对象")
public class QiYeShouYePage<T> extends BasePage<T> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业ID", required = true)
	private String deptId;

	@ApiModelProperty(value = "企业名称", required = true)
	private String deptName;

	@ApiModelProperty(value = "学习年份", required = true)
	private String lyear;

	@ApiModelProperty(value = "学习月份", required = true)
	private String lmonth;

	@ApiModelProperty(value = "姓名")
	private String user_name;

	@ApiModelProperty(value = "学习状态")
	private String status;

	@ApiModelProperty(value = "学习平台")
	private String luser;

	@ApiModelProperty(value = "学习方式")
	private String learnStudy;

	@ApiModelProperty(value = "省")
	private String province;

	@ApiModelProperty(value = "市")
	private String city;

	@ApiModelProperty(value = "县")
	private String country;

	@ApiModelProperty(value = "企业名称列表")
	private String[] list;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;

}
