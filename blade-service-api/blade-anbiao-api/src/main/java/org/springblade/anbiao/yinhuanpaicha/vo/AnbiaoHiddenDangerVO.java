package org.springblade.anbiao.yinhuanpaicha.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.yinhuanpaicha.entity.AnbiaoHiddenDanger;

/**
 * @author hyp
 * @create 2022-10-31 18:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AnbiaoHiddenDangerVO对象", description = "AnbiaoHiddenDangerVO对象")
public class AnbiaoHiddenDangerVO extends AnbiaoHiddenDanger {

	@ApiModelProperty(value = "企业名称")
	private String deptname;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

}
