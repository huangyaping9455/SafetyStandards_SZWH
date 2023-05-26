package org.springblade.anbiao.jiashiyuan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "DriverDataPerfectionVO对象", description = "DriverDataPerfectionVO对象")
public class DriverDataPerfectionVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "公司人数")
	private Integer gongsirenshu;

	@ApiModelProperty(value = "入职")
	private String ruzhi;

	@ApiModelProperty(value = "身份证")
	private String shenfenzheng;

	@ApiModelProperty(value = "驾驶证")
	private String jiashizheng;

	@ApiModelProperty(value = "从业资格证")
	private String congyezigezheng;

	@ApiModelProperty(value = "体检表")
	private String tijianbiao;

	@ApiModelProperty(value = "岗前培训")
	private String gangqianpeixun;

	@ApiModelProperty(value = "无责证明")
	private String wuzezhengming;

	@ApiModelProperty(value = "安全责任书")
	private String anquanzerenshu;

	@ApiModelProperty(value = "危害告知书")
	private String weihaigaozhishu;

	@ApiModelProperty(value = "劳动合同")
	private String laodonghetong;

	@ApiModelProperty(value = "项目")
	private String xiangmu;

	@ApiModelProperty(value = "数据")
	private String shuju;

}
