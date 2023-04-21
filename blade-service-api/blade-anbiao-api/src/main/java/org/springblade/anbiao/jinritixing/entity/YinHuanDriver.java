package org.springblade.anbiao.jinritixing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 * @create2022-09-15
 **/
@Data
@ApiModel(value = "YinHuanDriver对象", description = "YinHuanDriver对象")
public class YinHuanDriver implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "企业 id",required = true)
    private Integer deptId;

    @ApiModelProperty(value = "企业名称")
    private String deptName;

	@ApiModelProperty(value = "驾驶员id")
	private String jiashiyuanid;

	@ApiModelProperty(value = "驾驶证有效截至日期")
	private String jiashizhengyouxiaoqi;

	@ApiModelProperty(value = "驾驶员从业资格证有效截止日期")
	private String congyezhengyouxiaoqi;

	@ApiModelProperty(value = "下次诚信考核日期")
	private String xiacichengxinkaoheshijian;

	@ApiModelProperty(value = "体检有效截至日期")
	private String tijianyouxiaoqi;

	@ApiModelProperty(value = "身份证有效期截至时间")
	private String shenfenzhengyouxiaoqi;

	@ApiModelProperty(value = "从业资格证号")
	private String congyezigezheng;

}
