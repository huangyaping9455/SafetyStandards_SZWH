package org.springblade.anbiao.cheliangguanli.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "驾驶员保险详细信息")
public class JiashiyuanBaoxianInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "驾驶员保险主要信息")
	private JiashiyuanBaoxian baoxian;

	@ApiModelProperty(value = "驾驶员保险明细信息")
	private List<JiashiyuanBaoxianMingxi> baoxianMingxis;

	@ApiModelProperty(value = "驾驶员保险明细信息")
	private List<JiashiyuanBaoxianMingxi> mingxiList;

}
