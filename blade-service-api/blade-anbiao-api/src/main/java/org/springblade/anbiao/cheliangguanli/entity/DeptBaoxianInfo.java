package org.springblade.anbiao.cheliangguanli.entity;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "企业保险详细信息")
public class DeptBaoxianInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "企业保险主要信息")
	private DeptBaoxian baoxian;

	@ApiModelProperty(value = "企业保险明细信息")
	private List<DeptBaoxianMingxi> mingxiList;
}
