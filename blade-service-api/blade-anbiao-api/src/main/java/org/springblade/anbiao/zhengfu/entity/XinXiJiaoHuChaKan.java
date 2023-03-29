/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuZhuTi
 * Author:   呵呵哒
 * Date:     2020/6/20 15:52
 * Description:
 */
package org.springblade.anbiao.zhengfu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/6/20
 * @描述
 */
@Data
@TableName("anbiao_xinxijiaohuchakanbiao")
@ApiModel(value = "XinXiJiaoHuChaKan对象", description = "XinXiJiaoHuChaKan对象")
public class XinXiJiaoHuChaKan implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private String id;

	/**
	 * 政府用户ID
	 */
	@ApiModelProperty(value = "政府用户ID")
	private String zhengfuyonghuid;

	/**
	 * 企业用户ID
	 */
	@ApiModelProperty(value = "企业用户ID")
	private String qiyeyonghuid;

	/**
	 * 主题ID
	 */
	@ApiModelProperty(value = "主题ID")
	private String zhutiid;

	/**
	 * 政府ID
	 */
	@ApiModelProperty(value = "政府ID")
	private String zhengfuid;

	/**
	 * 企业ID
	 */
	@ApiModelProperty(value = "企业ID")
	private String qiyeid;


}
