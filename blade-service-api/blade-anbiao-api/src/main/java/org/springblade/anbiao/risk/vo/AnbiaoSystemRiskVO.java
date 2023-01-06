package org.springblade.anbiao.risk.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="AnbiaoSystemRiskVO对象", description="AnbiaoSystemRiskVO对象")
public class AnbiaoSystemRiskVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业id")
	private String deptId;

	@ApiModelProperty(value = "驾驶员id")
	private String jiaShiYuanId;

	@ApiModelProperty(value = "驾驶员姓名")
	private String jiashiyuanxingming;

	@ApiModelProperty(value = "安全责任书")
	private String anquanzerenshu;

	@ApiModelProperty(value = "入职登记表")
	private String ruzhi;

	@ApiModelProperty(value = "危害告知书")
	private String weihaigaozhishu;

	@ApiModelProperty(value = "劳动合同")
	private String laodonghetong;

}
