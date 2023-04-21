package org.springblade.anbiao.yinhuanpaicha.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.BasePage;

/**
 * @description: YinhuanpaichaXiangPage
 * @author: hyp
 * @create2021-04-28 16:00
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "YinhuanpaichaXiangPage对象", description = "YinhuanpaichaXiangPage对象")
public class YinhuanpaichaXiangPage<T> extends BasePage<T> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "隐患名称")
    private String name;

	@ApiModelProperty(value = "企业名称")
	private String deptname;

	@ApiModelProperty(value = "企业ID")
	private String deptId;

	@ApiModelProperty(value = "车辆牌照")
	private String cheliangpaizhao;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "数据ID")
	private String dataid;

	@ApiModelProperty(value = "排序字段")
	private String orderColumns;


}




