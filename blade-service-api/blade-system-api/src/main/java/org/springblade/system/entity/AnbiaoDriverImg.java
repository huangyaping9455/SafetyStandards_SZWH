package org.springblade.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "DriverImg对象", description = "DriverImg对象")
public class AnbiaoDriverImg implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "入职ID")
	private String ruzhiid;

	@ApiModelProperty(value = "入职头像附件")
	private String ruzhiimg;

	@ApiModelProperty(value = "身份证正面附件")
	private String sfzzmimg;

	@ApiModelProperty(value = "身份证反面附件")
	private String sfzfmimg;

	@ApiModelProperty(value = "驾驶证ID")
	private String jszid;

	@ApiModelProperty(value = "驾驶证正面附件")
	private String jszzmimg;

	@ApiModelProperty(value = "驾驶证反面附件")
	private String jszfmimg;

	@ApiModelProperty(value = "从业资格证ID")
	private String cyzid;

	@ApiModelProperty(value = "从业资格证附件")
	private String cyzimg;

	@ApiModelProperty(value = "体检表ID")
	private String tjid;

	@ApiModelProperty(value = "体检表附件")
	private String tjimg;

	@ApiModelProperty(value = "岗前培训ID")
	private String gqid;

	@ApiModelProperty(value = "岗前培训附件")
	private String gqimg;

	@ApiModelProperty(value = "无责证明ID")
	private String wzzmid;

	@ApiModelProperty(value = "无责证明附件")
	private String wzzmimg;

	@ApiModelProperty(value = "其他ID")
	private String qtid;

	@ApiModelProperty(value = "其他附件")
	private String qtimg;

	@ApiModelProperty(value = "影像附件数")
	private Integer count = 0;

	@ApiModelProperty(value = "入职影像附件数")
	private Integer ruzhiimgzcount = 0;

	@ApiModelProperty(value = "身份证正面影像附件数")
	private Integer sfzzmimgzcount = 0;

	@ApiModelProperty(value = "身份证反面影像附件数")
	private Integer sfzfmimgzcount = 0;

	@ApiModelProperty(value = "身份证影像附件数")
	private Integer sfzimgzcount = 0;

	@ApiModelProperty(value = "驾驶证影像附件数")
	private Integer jszimgcount = 0;

	@ApiModelProperty(value = "驾驶证正面影像附件数")
	private Integer jszzmimgcount = 0;

	@ApiModelProperty(value = "驾驶证反面影像附件数")
	private Integer jszfmimgcount = 0;

	@ApiModelProperty(value = "从业资格证影像附件数")
	private Integer cyzcount = 0;

	@ApiModelProperty(value = "车辆登记证书影像附件数")
	private Integer djzimgcount = 0;

	@ApiModelProperty(value = "体检表影像附件数")
	private Integer tjcount = 0;

	@ApiModelProperty(value = "岗前培训影像附件数")
	private Integer gqimgcount = 0;

	@ApiModelProperty(value = "无责证明影像附件数")
	private Integer wzzmimgcount = 0;

	@ApiModelProperty(value = "其他影像附件数")
	private Integer qtimgcount = 0;

}

