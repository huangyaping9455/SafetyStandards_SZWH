package org.springblade.anbiao.guanlijigouherenyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "ImageFuJian对象", description = "ImageFuJian对象")
public class ImageFuJian implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private String id;

	@ApiModelProperty(value = "道路运输证附件")
	private String daoluyunshuzhengfujian;

	@ApiModelProperty(value = "经营许可证附件")
	private String jingyingxukezhengfujian;

	@ApiModelProperty(value = "工商营业执照附件")
	private String yingyezhizhaofujian;

	@ApiModelProperty(value = "人员影像附件数")
	private List<OrganizationsFuJian> personnelFuJianList;

	@ApiModelProperty(value = "人员ID")
	private String personId;

	@ApiModelProperty(value = "其他附件反面")
	private String qitafanmianfujian;

	@ApiModelProperty(value = "其他附件证明")
	private String qitazhengmianfujian;

	@ApiModelProperty(value = "身份证附件反面")
	private String shenfenzhengfanmianfujian;

	@ApiModelProperty(value = "身份证附件正面")
	private String shenfenzhengfujian;

	@ApiModelProperty(value = "行驶证ID")
	private String xszid;

	@ApiModelProperty(value = "行驶证正面附件")
	private String xszzmimg;

	@ApiModelProperty(value = "行驶证反面附件")
	private String xszfmimg;

	@ApiModelProperty(value = "道路运输证ID")
	private String yszid;

	@ApiModelProperty(value = "道路运输证附件")
	private String yszimg;

	@ApiModelProperty(value = "性能检测报告ID")
	private String xnbgid;

	@ApiModelProperty(value = "性能检测报告附件")
	private String xnbgimg;

	@ApiModelProperty(value = "车辆登记证书ID")
	private String djzid;

	@ApiModelProperty(value = "车辆登记证书附件")
	private String djzimg;

	@ApiModelProperty(value = "入职ID")
	private String ruzhiid;

	@ApiModelProperty(value = "入职头像附件")
	private String ruzhiimg;

	@ApiModelProperty(value = "驾驶员id")
	private String jiashiyuanId;

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

	@ApiModelProperty(value = "其他附件")
	private String qtimg;

	@ApiModelProperty(value = "无责证明ID")
	private String wzzmid;

	@ApiModelProperty(value = "无责证明附件")
	private String wzzmimg;

	@ApiModelProperty(value = "其他ID")
	private String qtid;

	@ApiModelProperty(value = "表id")
	private String tableId;

	@ApiModelProperty(value = "附件")
	private String attachments;

	@ApiModelProperty(value = "deptName")
	private String deptName;

}
