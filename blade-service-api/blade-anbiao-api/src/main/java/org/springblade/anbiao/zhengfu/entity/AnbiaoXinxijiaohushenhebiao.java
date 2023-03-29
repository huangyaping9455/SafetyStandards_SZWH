package org.springblade.anbiao.zhengfu.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hyp
 */
@Data
@ApiModel(value = "AnbiaoXinxijiaohushenhebiao对象",description = "AnbiaoXinxijiaohushenhebiao对象")
@TableName("anbiao_xinxijiaohushenhebiao")
public class AnbiaoXinxijiaohushenhebiao implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	@ApiModelProperty(value = "主题ID")
	private String zhutiid;
	@ApiModelProperty(value = "整改信息ID")
	private String zhenggaiid;
	@ApiModelProperty(value = "企业ID")
	private Integer deptid;
	@ApiModelProperty(value = "审核状态，1：驳回；2：通过；")
	private Integer status;
	@ApiModelProperty(value = "审核描述")
	private String miaoshu;
	@ApiModelProperty(value = "审核附件")
	private String img;
	@ApiModelProperty(value = "审核时间")
	private String date;
	@ApiModelProperty(value = "审核人ID")
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


}
