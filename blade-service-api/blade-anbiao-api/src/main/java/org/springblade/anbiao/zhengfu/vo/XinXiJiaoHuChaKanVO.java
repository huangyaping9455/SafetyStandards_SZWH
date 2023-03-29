/**
 * Copyright (C), 2015-2020,
 * FileName: XinXiJiaoHuChaKanVO
 * Author:   呵呵哒
 * Date:     2020/6/20 16:10
 * Description:
 */
package org.springblade.anbiao.zhengfu.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.zhengfu.entity.XinXiJiaoHuChaKan;

/**
 * @author 呵呵哒
 * @创建人 hyp
 * @创建时间 2020/6/20
 * @描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "XinXiJiaoHuChaKanVO对象", description = "XinXiJiaoHuChaKanVO对象")
public class XinXiJiaoHuChaKanVO extends XinXiJiaoHuChaKan {

	private static final long serialVersionUID = 1L;

}
