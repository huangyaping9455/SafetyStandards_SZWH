package org.springblade.anbiao.jiaoyupeixun.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.jiaoyupeixun.entity.AnbiaoSafetyTraining;

/**
 * @author hyp
 * @create 2022-10-31 18:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AnbiaoSafetyTrainingVO对象", description = "AnbiaoSafetyTrainingVO对象")
public class AnbiaoSafetyTrainingVO extends AnbiaoSafetyTraining {

	@ApiModelProperty(value = "企业名称")
	private String deptname;

}
