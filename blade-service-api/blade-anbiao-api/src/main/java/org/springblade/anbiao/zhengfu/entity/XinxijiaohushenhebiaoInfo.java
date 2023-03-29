package org.springblade.anbiao.zhengfu.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hyp
 */
@Data
@ApiModel(value = "XinxijiaohushenhebiaoInfo对象",description = "XinxijiaohushenhebiaoInfo对象")
public class XinxijiaohushenhebiaoInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private String id;
	@ApiModelProperty(value = "主题ID")
	private String zhutiid;
	@ApiModelProperty(value = "企业ID")
	private Integer deptid;
	@ApiModelProperty(value = "整改状态，默认0，待审核；1：驳回；2：通过；")
	private Integer status;
	@ApiModelProperty(value = "整改描述")
	private String miaoshu;
	@ApiModelProperty(value = "整改附件")
	private String img;
	@ApiModelProperty(value = "整改时间")
	private String date;
	@ApiModelProperty(value = "整改人ID")
	private Integer userid;
	@ApiModelProperty(value = "是否删除，默认0")
	private Integer isdelete;
	@ApiModelProperty(value = "创建时间")
	private String createtime;
	@ApiModelProperty(value = "创建者ID")
	private Integer createid;
	@ApiModelProperty(value = "更新时间")
	private String updatetime;
	@ApiModelProperty(value = "更新者ID")
	private Integer updateid;
	@ApiModelProperty(value = "主题名称")
	private String zhutimingcheng;
	@ApiModelProperty(value = "发起时间")
	private String faqishijian;
	@ApiModelProperty(value = "企业名称")
	private String qiyemingcheng;
	@ApiModelProperty(value = "发送单位")
	private String fasongdanwei;
	@ApiModelProperty(value = "发送单位ID")
	private Integer fasongdanweiid;
	@ApiModelProperty(value = "审核状态，1：驳回；2：通过；")
	private Integer shstatus;
	@ApiModelProperty(value = "审核描述")
	private String shmiaoshu;
	@ApiModelProperty(value = "审核附件")
	private String shimg;
	@ApiModelProperty(value = "审核时间")
	private String shdate;
	@ApiModelProperty(value = "审核人ID")
	private Integer shuserid;

}
