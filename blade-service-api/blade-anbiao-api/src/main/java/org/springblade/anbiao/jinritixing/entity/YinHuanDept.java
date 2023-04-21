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
@ApiModel(value = "YinHuanDept对象", description = "YinHuanDept对象")
public class YinHuanDept implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "企业 id",required = true)
    private Integer deptId;

    @ApiModelProperty(value = "企业名称")
    private String deptName;

	@ApiModelProperty(value = "企业法人身份证")
	private String fujian;

	@ApiModelProperty(value = "企业工商营业执照")
	private String loginPhotoApp;

	@ApiModelProperty(value = "企业经营许可证")
	private String homePhotoApp;

	@ApiModelProperty(value = "企业安全负责人")
	private String logoRizhi;

	@ApiModelProperty(value = "企业营业许可证")
	private String logoPhoto;

	@ApiModelProperty(value = "道路运输许可证")
	private String profilePhoto;

}
