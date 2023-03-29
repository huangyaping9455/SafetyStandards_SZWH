/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuChaKanPage
 * Author:   呵呵哒
 * Date:     2020/6/20 16:13
 * Description:
 */
package org.springblade.anbiao.zhengfu.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/6/20
 * @描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "XinXiJiaoHuChaKanPage对象", description = "XinXiJiaoHuChaKanPage对象")
public class XinXiJiaoHuChaKanPage<T> extends BasePage<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "送达单位ID")
	private Integer songDaDanWeiId;

	@ApiModelProperty(value = "送达单位名称")
	private Integer songDaDanWeiMingCheng;

	@ApiModelProperty(value = "主题名称")
	private String zhuTiMingCheng;

}


