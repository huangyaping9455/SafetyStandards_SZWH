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
@TableName("anbiao_xinxijiaohuhuifubiao")
@ApiModel(value = "XinXiJiaoHuHuiFu对象", description = "XinXiJiaoHuHuiFu对象")
public class XinXiJiaoHuHuiFu implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private String id;

	/**
	 * 主题ID
	 */
	@ApiModelProperty(value = "主题ID")
	private String zhutiid;

	/**
	 * 回复者ID
	 */
	@ApiModelProperty(value = "回复者ID")
	private String huifuzheid;

	/**
	 * 正文
	 */
	@ApiModelProperty(value = "正文")
	private String zhengwen;

	/**
	 * 回复时间
	 */
	@ApiModelProperty(value = "回复时间")
	private String huifushijian;

	/**
	 * 所属单位ID
	 */
	@ApiModelProperty(value = "所属单位ID")
	private String suoshudanweiid;


}
