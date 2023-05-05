package org.springblade.anbiao.yunyingshang.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.anbiao.yunyingshang.entity.TsOperatorInfo;

/**
 * @author hyp
 * @create 2022-11-21 15:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TsOperatorInfoVo对象", description = "TsOperatorInfoVo对象")
public class TsOperatorInfoVo extends TsOperatorInfo {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "政府ID")
	private int zhengfuid;

	@ApiModelProperty(value = "政府名称")
	private String zhengfuname;



}
